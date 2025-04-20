/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui.WarehouseManager;

import ui.CustomerServiceRole.*;
import java.awt.CardLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


import Business.Customer.ComplaintDirectory;
import Business.Customer.CustomerComplaint;
import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Network.Network;
import Business.Organization.CustomerExperienceOrganization;
import Business.Organization.Organization;
import Business.Warehouse.Warehouse;
import java.awt.CardLayout;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author wangsiting
 */
public class WarehouseCustomerComplaintContent extends javax.swing.JPanel {

    private JPanel userProcessContainer;
    private ComplaintDirectory complaintDirectory;
    private CustomerComplaint selectedComplaint;
    private EcoSystem system;
    
    /**
     * Creates new form CustomerComplaintContent
     */
    public WarehouseCustomerComplaintContent(JPanel userProcessContainer) {
        initComponents();
        this.userProcessContainer = userProcessContainer;
        this.system = EcoSystem.getInstance();
        this.complaintDirectory = findComplaintDirectory();
        populateTable();
    }

    public WarehouseCustomerComplaintContent(JPanel userProcessContainer, Warehouse warehouse) {
        initComponents();
        this.userProcessContainer = userProcessContainer;
        this.system = EcoSystem.getInstance();
        this.complaintDirectory = findComplaintDirectory();
        populateTable();
        System.out.println("Initialized WarehouseCustomerComplaintContent with warehouse instance");
    }
    
    // 查找投诉目录
    private ComplaintDirectory findComplaintDirectory() {
        if (system == null) {
            System.out.println("Error: System instance is null");
            return null;
        }
        
        System.out.println("Searching for complaint directory...");
        if (system.getNetworkList() == null || system.getNetworkList().isEmpty()) {
            System.out.println("Error: System network list is empty");
            return null;
        }
        
        System.out.println("Network count: " + system.getNetworkList().size());
        
        // 遍历networks和enterprises寻找CustomerExperienceOrganization
        for (Network network : system.getNetworkList()) {
            System.out.println("Checking network: " + network.getName());
            
            if (network.getEnterpriseDirectory() == null || 
                network.getEnterpriseDirectory().getEnterpriseList() == null ||
                network.getEnterpriseDirectory().getEnterpriseList().isEmpty()) {
                System.out.println("No enterprises in network");
                continue;
            }
            
            for (Enterprise enterprise : network.getEnterpriseDirectory().getEnterpriseList()) {
                System.out.println("Checking enterprise: " + enterprise.getName());
                
                if (enterprise.getOrganizationDirectory() == null || 
                    enterprise.getOrganizationDirectory().getOrganizationList() == null ||
                    enterprise.getOrganizationDirectory().getOrganizationList().isEmpty()) {
                    System.out.println("No organizations in enterprise");
                    continue;
                }
                
                for (Organization organization : enterprise.getOrganizationDirectory().getOrganizationList()) {
                    System.out.println("Checking organization: " + organization.getName() + ", type: " + organization.getClass().getSimpleName());
                    
                    if (organization instanceof CustomerExperienceOrganization) {
                        CustomerExperienceOrganization custOrg = (CustomerExperienceOrganization) organization;
                        ComplaintDirectory dir = custOrg.getComplaintDirectory();
                        if (dir != null) {
                            System.out.println("Found complaint directory, complaint count: " + dir.getComplaintCount());
                            return dir;
                        } else {
                            System.out.println("CustomerExperienceOrganization's ComplaintDirectory is null");
                        }
                    }
                }
            }
        }
        
        System.out.println("Could not find complaint directory");
        return null;
    }
    
    // 填充表格数据
    private void populateTable() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0); // 清空表格
        
        if (complaintDirectory == null) {
            System.out.println("Complaint directory is null, cannot populate table");
            // 创建示例投诉以进行测试
            addSampleComplaints();
            return;
        }
        
        // 获取所有投诉
        ArrayList<CustomerComplaint> allComplaints = complaintDirectory.getComplaints();
        
        // 仅显示转发给仓库部门的投诉
        ArrayList<CustomerComplaint> warehouseComplaints = new ArrayList<>();
        for (CustomerComplaint complaint : allComplaints) {
            // 根据投诉描述或状态判断是否是转发给仓库的投诉
            if (complaint.getDescription().contains("[Warehouse]") || 
                complaint.getStatus().equals("Forwarded to Warehouse") ||
                (complaint.getDescription().contains("warehouse") && complaint.getStatus().contains("Forwarded")) ||
                (complaint.getDescription().contains("Warehouse") && complaint.getStatus().contains("Forwarded")) ||
                complaint.getDescription().toLowerCase().contains("[warehouse]")) {
                warehouseComplaints.add(complaint);
                System.out.println("Found warehouse complaint: " + complaint.getComplaintId() + 
                                  ", Status: " + complaint.getStatus() + 
                                  ", Description: " + complaint.getDescription());
            }
        }
        
        // 填充表格
        if (warehouseComplaints.isEmpty()) {
            System.out.println("No complaints found for warehouse department");
            // 如果没有真实的仓库投诉数据，添加示例数据以便测试
            if (model.getRowCount() == 0) {
                addSampleComplaints();
            }
        } else {
            for (CustomerComplaint complaint : warehouseComplaints) {
                try {
                    Object[] row = new Object[3];
                    row[0] = complaint.getComplaintId();
                    row[1] = complaint.getCustomerId();
                    row[2] = "Warehouse Issue";  // 投诉类型
                    model.addRow(row);
                    System.out.println("Added row for complaint: " + complaint.getComplaintId());
                } catch (Exception e) {
                    System.out.println("Error adding row: " + e.getMessage());
                }
            }
        }
    }
    
    // 刷新表格
    private void refreshRequestTable() {
        populateTable();
    }
    
    // 添加示例投诉数据（仅在找不到投诉目录或没有仓库相关投诉时使用）
    private void addSampleComplaints() {
        System.out.println("Creating sample complaints for testing");
        
        // 如果还没有创建，则创建投诉目录
        if (this.complaintDirectory == null) {
            this.complaintDirectory = new ComplaintDirectory();
        }
        
        // 添加几个示例投诉
        CustomerComplaint complaint1 = complaintDirectory.createComplaint(
                "W001", "CUST001", "[Warehouse] Package received damaged from warehouse, requesting replacement");
        complaint1.setStatus("Forwarded to Warehouse");
        
        CustomerComplaint complaint2 = complaintDirectory.createComplaint(
                "W002", "CUST002", "[Warehouse] Ordered items not matching warehouse inventory, missing items");
        complaint2.setStatus("Forwarded to Warehouse");
        
        CustomerComplaint complaint3 = complaintDirectory.createComplaint(
                "W003", "CUST003", "[Warehouse] Warehouse shipped wrong product, need to exchange");
        complaint3.setStatus("Forwarded to Warehouse");
        
        // 填充表格
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0); // 清空表格
        for (CustomerComplaint complaint : complaintDirectory.getComplaints()) {
            Object[] row = new Object[3];
            row[0] = complaint.getComplaintId();
            row[1] = complaint.getCustomerId();
            row[2] = "Warehouse Issue";
            model.addRow(row);
        }
        
        System.out.println("Added " + complaintDirectory.getComplaintCount() + " sample complaints");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        txtSearchComplaintID = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnDetailed = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        handlePanel = new javax.swing.JPanel();
        lblTitle1 = new javax.swing.JLabel();
        lblCompaintID = new javax.swing.JLabel();
        lblPN = new javax.swing.JLabel();
        lblPrice1 = new javax.swing.JLabel();
        lblPrice2 = new javax.swing.JLabel();
        txtContent = new javax.swing.JTextField();
        txtComplaintType = new javax.swing.JTextField();
        txtCustomertName = new javax.swing.JTextField();
        txtCompaintID = new javax.swing.JTextField();

        lblTitle.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setText("Customer Complaint Managment");

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        txtSearchComplaintID.setText("Saerch Complaint ID...");
        txtSearchComplaintID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchComplaintIDActionPerformed(evt);
            }
        });

        btnSearch.setText("Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(txtSearchComplaintID, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(btnSearch)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearchComplaintID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearch))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        btnBack.setText("<< Back");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        jTable1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Complaint ID", "Customer Name", "Complaint Type"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jScrollPane2.setViewportView(jScrollPane1);

        btnDetailed.setText("Detailed");
        btnDetailed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDetailedActionPerformed(evt);
            }
        });

        jPanel3.setLayout(new java.awt.CardLayout());

        lblTitle1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblTitle1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle1.setText(" Customers Complaint Detailed");

        lblCompaintID.setText("Compaint ID:");

        lblPN.setText("Customer Name:");

        lblPrice1.setText("Complaint Type:");

        lblPrice2.setText("Customer Complaint Content:");

        txtContent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtContentActionPerformed(evt);
            }
        });

        txtComplaintType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtComplaintTypeActionPerformed(evt);
            }
        });

        txtCustomertName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCustomertNameActionPerformed(evt);
            }
        });

        txtCompaintID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCompaintIDActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout handlePanelLayout = new javax.swing.GroupLayout(handlePanel);
        handlePanel.setLayout(handlePanelLayout);
        handlePanelLayout.setHorizontalGroup(
            handlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTitle1, javax.swing.GroupLayout.DEFAULT_SIZE, 1444, Short.MAX_VALUE)
            .addGroup(handlePanelLayout.createSequentialGroup()
                .addGap(538, 538, 538)
                .addGroup(handlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblPrice2)
                    .addComponent(lblCompaintID)
                    .addComponent(lblPrice1)
                    .addComponent(lblPN))
                .addGap(37, 37, 37)
                .addGroup(handlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(handlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtCompaintID, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                        .addComponent(txtComplaintType)
                        .addComponent(txtCustomertName))
                    .addComponent(txtContent, javax.swing.GroupLayout.PREFERRED_SIZE, 479, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        handlePanelLayout.setVerticalGroup(
            handlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(handlePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitle1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(15, 15, 15)
                .addGroup(handlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCompaintID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCompaintID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(handlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPN, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCustomertName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(handlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtComplaintType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPrice1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(handlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(handlePanelLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(lblPrice2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(handlePanelLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(txtContent, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(55, 55, 55))
        );

        jPanel3.add(handlePanel, "card2");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDetailed, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBack)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnDetailed)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1462, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 887, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(47, 47, 47)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtSearchComplaintIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchComplaintIDActionPerformed
        // 直接调用搜索按钮的事件处理器
        btnSearchActionPerformed(evt);
    }//GEN-LAST:event_txtSearchComplaintIDActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        String searchQuery = txtSearchComplaintID.getText().trim();
        if (searchQuery.isEmpty() || searchQuery.equals("Saerch Complaint ID...")) {
            populateTable();
            return;
        }
        
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0); // 清空表格
        
        if (complaintDirectory == null) {
            System.out.println("Complaint directory is null, cannot search");
            JOptionPane.showMessageDialog(this, "Unable to search: Complaint directory is not available", 
                                          "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // 根据ID直接查找
        CustomerComplaint foundComplaint = complaintDirectory.findComplaintById(searchQuery);
        if (foundComplaint != null && isWarehouseComplaint(foundComplaint)) {
            // 如果找到匹配的ID且属于仓库投诉，添加到表格
            Object[] row = new Object[3];
            row[0] = foundComplaint.getComplaintId();
            row[1] = foundComplaint.getCustomerId();
            row[2] = "Warehouse Issue";
            model.addRow(row);
            System.out.println("Found complaint by ID: " + foundComplaint.getComplaintId());
            return;
        }
        
        // 如果没有匹配的ID，尝试搜索其他字段
        ArrayList<CustomerComplaint> allComplaints = complaintDirectory.getComplaints();
        boolean found = false;
        
        for (CustomerComplaint complaint : allComplaints) {
            // 只处理仓库相关投诉
            if (!isWarehouseComplaint(complaint)) {
                continue;
            }
            
            // 搜索客户ID、描述或状态
            if (complaint.getCustomerId().toLowerCase().contains(searchQuery.toLowerCase()) ||
                complaint.getDescription().toLowerCase().contains(searchQuery.toLowerCase()) ||
                complaint.getStatus().toLowerCase().contains(searchQuery.toLowerCase())) {
                
                Object[] row = new Object[3];
                row[0] = complaint.getComplaintId();
                row[1] = complaint.getCustomerId();
                row[2] = "Warehouse Issue";
                model.addRow(row);
                found = true;
                System.out.println("Found complaint matching query: " + complaint.getComplaintId());
            }
        }
        
        if (!found) {
            JOptionPane.showMessageDialog(this, "No warehouse complaints found matching: " + searchQuery,
                                          "Information", JOptionPane.INFORMATION_MESSAGE);
            populateTable(); // 如果没找到，恢复显示所有仓库投诉
        }
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        userProcessContainer.remove(this);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.previous(userProcessContainer);
    }//GEN-LAST:event_btnBackActionPerformed

    private void txtContentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtContentActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtContentActionPerformed

    private void txtComplaintTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtComplaintTypeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtComplaintTypeActionPerformed

    private void txtCustomertNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCustomertNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCustomertNameActionPerformed

    private void txtCompaintIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCompaintIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCompaintIDActionPerformed

    private void btnDetailedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDetailedActionPerformed
        // TODO add your handling code here:
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a complaint record");
            return;
        }
        
        String complaintId = jTable1.getValueAt(selectedRow, 0).toString();
        selectedComplaint = complaintDirectory.findComplaintById(complaintId);
        
        if (selectedComplaint != null) {
            // Fill form data
            txtCompaintID.setText(selectedComplaint.getComplaintId());
            txtCustomertName.setText(selectedComplaint.getCustomerId());
            txtComplaintType.setText("Customer Complaint"); // In a real application, this might be extracted from the complaint
            txtContent.setText(selectedComplaint.getDescription());
            
            // Disable text field editing
            txtCompaintID.setEditable(false);
            txtCustomertName.setEditable(false);
            txtComplaintType.setEditable(false);
            txtContent.setEditable(false);
        }
        
        
        
        
        
        
        
    }//GEN-LAST:event_btnDetailedActionPerformed

    // 辅助方法：检查投诉是否与仓库相关
    private boolean isWarehouseComplaint(CustomerComplaint complaint) {
        return complaint.getDescription().contains("[Warehouse]") || 
               complaint.getStatus().equals("Forwarded to Warehouse") ||
               (complaint.getDescription().contains("warehouse") && complaint.getStatus().contains("Forwarded")) ||
               (complaint.getDescription().contains("Warehouse") && complaint.getStatus().contains("Forwarded")) ||
               complaint.getDescription().toLowerCase().contains("[warehouse]");
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnDetailed;
    private javax.swing.JButton btnSearch;
    private javax.swing.JPanel handlePanel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblCompaintID;
    private javax.swing.JLabel lblPN;
    private javax.swing.JLabel lblPrice1;
    private javax.swing.JLabel lblPrice2;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblTitle1;
    private javax.swing.JTextField txtCompaintID;
    private javax.swing.JTextField txtComplaintType;
    private javax.swing.JTextField txtContent;
    private javax.swing.JTextField txtCustomertName;
    private javax.swing.JTextField txtSearchComplaintID;
    // End of variables declaration//GEN-END:variables
}
