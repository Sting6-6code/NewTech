/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui.CustomerServiceRole;

import Business.Customer.ComplaintDirectory;
import Business.Customer.CustomerComplaint;
import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Network.Network;
import Business.Organization.CustomerExperienceOrganization;
import Business.Organization.Organization;
import java.awt.CardLayout;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;





/**
 *
 * @author wangsiting
 */
public class ComplaintManagementJpanel extends javax.swing.JPanel {

    private JPanel userProcessContainer;
    private ComplaintDirectory complaintDirectory;
    private CustomerComplaint selectedComplaint;
    private EcoSystem system;
    
    // No-arg constructor, complies with JavaBean standards
    public ComplaintManagementJpanel() {
        initComponents();
        this.system = EcoSystem.getInstance();
        this.complaintDirectory = findComplaintDirectory();
        populateTable();
    }
    
    public ComplaintManagementJpanel(JPanel userProcessContainer) {
        initComponents();
        this.userProcessContainer = userProcessContainer;
        this.system = EcoSystem.getInstance();
        
        // Find the customer experience organization and get the complaint directory
        this.complaintDirectory = findComplaintDirectory();
        if (this.complaintDirectory == null) {
            System.out.println("Warning: Could not find complaint directory in the system, creating a new empty directory");
            this.complaintDirectory = new ComplaintDirectory();
        } else {
            System.out.println("Successfully found complaint directory, complaint count: " + this.complaintDirectory.getComplaintCount());
        }
        
        // Initialize combobox
        StatusjComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "New", "In Progress", "Resolved" }));
        
        populateTable();
    }
    
    /**
     * Adjust panel size to make it more compact
     */
    private void adjustPanelSize() {
        // 设置合适的面板尺寸
        this.setPreferredSize(new java.awt.Dimension(1200, 700));
        
        // 调整表格显示
        jScrollPane2.setPreferredSize(new java.awt.Dimension(1150, 150));
        jTable1.setRowHeight(25);
        
        // 固定表头
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.getTableHeader().setResizingAllowed(true);
        
        // 减小处理面板的尺寸
        handlePanel.setPreferredSize(new java.awt.Dimension(1150, 260));
        
        // 确保内容文本框大小适中
        txtContent.setPreferredSize(new java.awt.Dimension(350, 60));
        
        // 优化表格列宽
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(100); // ID
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(120); // Customer Name
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(120); // Status
            jTable1.getColumnModel().getColumn(3).setPreferredWidth(300); // Description
        }
        
        // 启用自动滚动以处理大内容
        this.setAutoscrolls(true);
        
        // 调整标题大小
        lblTitle.setPreferredSize(new java.awt.Dimension(1150, 35));
        
        // 重新验证和重绘
        revalidate();
        repaint();
    }
    
    /**
     * Apply consistent UI theme to all components
     */
    private void setupTheme() {
        // Set panel background colors
        this.setBackground(new java.awt.Color(240, 245, 255));
        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBackground(new java.awt.Color(240, 245, 255));
        handlePanel.setBackground(new java.awt.Color(255, 255, 255));
        
        // Style the title labels
        styleHeaderLabel(lblTitle);
        styleHeaderLabel(lblTitle1);
        
        // Apply shadow border to panels
        jPanel1.setBorder(createShadowBorder());
        handlePanel.setBorder(createShadowBorder());
        
        // Style buttons
        styleButton(btnBack);
        styleButton(btnSearch);
        styleButton(btnRefresh);
        styleButton(jButton1); // View details button
        styleButton(btnSubmit);
        
        // Ensure jButton1 is properly sized and positioned
        jButton1.setText("Process Selected");
        
        // Style the search and refresh buttons differently
        btnSearch.setBackground(new java.awt.Color(30, 144, 255));
        btnRefresh.setBackground(new java.awt.Color(70, 130, 180));
        btnSubmit.setBackground(new java.awt.Color(0, 153, 102));
        
        // Process button should stand out
        jButton1.setBackground(new java.awt.Color(0, 130, 200));
        
        // Style table
        styleTable(jTable1);
        
        // Style form labels
        styleFormLabel(lblCompaintID);
        styleFormLabel(lblPN);
        styleFormLabel(lblPrice1);
        styleFormLabel(lblPrice2);
        styleFormLabel(lblPrice3);
        styleFormLabel(jLabel1);
        
        // Style combo boxes
        styleComboBox(StatusjComboBox);
        styleComboBox(jComboBoxRole);
        
        // Style text fields
        styleTextField(txtSearchComplaintID);
        styleTextField(txtCompaintID);
        styleTextField(txtCustomertName);
        styleTextField(txtComplaintType);
        styleTextField(txtContent);
        
        // Make content text field multiline appearance
        txtContent.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 1),
                javax.swing.BorderFactory.createEmptyBorder(8, 8, 8, 8)));
        
        // 重新布局handlePanel，使其更紧凑
        try {
            javax.swing.GroupLayout handlePanelLayout = new javax.swing.GroupLayout(handlePanel);
            handlePanel.setLayout(handlePanelLayout);
            
            // 水平布局 - 减少间距，适应屏幕宽度
            handlePanelLayout.setHorizontalGroup(
                handlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(lblTitle1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(handlePanelLayout.createSequentialGroup()
                    .addGap(250, 250, 250) // 调整整体居中位置
                    .addGroup(handlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(lblCompaintID)
                        .addComponent(lblPN)
                        .addComponent(lblPrice1)
                        .addComponent(lblPrice3)
                        .addComponent(lblPrice2))
                    .addGap(15, 15, 15) // 减少标签和字段间距
                    .addGroup(handlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtCompaintID, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtCustomertName, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtComplaintType, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBoxRole, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtContent, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(250, Short.MAX_VALUE))
            );
            
            // 垂直布局 - 减少行间距
            handlePanelLayout.setVerticalGroup(
                handlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(handlePanelLayout.createSequentialGroup()
                    .addComponent(lblTitle1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(15, 15, 15) // 减少间距
                    .addGroup(handlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblCompaintID)
                        .addComponent(txtCompaintID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(10, 10, 10) // 减少间距
                    .addGroup(handlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblPN)
                        .addComponent(txtCustomertName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(10, 10, 10) // 减少间距
                    .addGroup(handlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblPrice1)
                        .addComponent(txtComplaintType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(10, 10, 10) // 减少间距
                    .addGroup(handlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblPrice3)
                        .addComponent(jComboBoxRole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(10, 10, 10) // 减少间距
                    .addGroup(handlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblPrice2)
                        .addComponent(txtContent, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(10, 10, 10) // 减少间距
                    .addComponent(btnSubmit)
                    .addContainerGap(15, Short.MAX_VALUE))
            );
        } catch (Exception e) {
            System.out.println("Warning: Could not adjust handlePanel layout: " + e.getMessage());
        }
    }
    
    /**
     * Style header labels with consistent formatting
     */
    private void styleHeaderLabel(javax.swing.JLabel label) {
        label.setFont(new java.awt.Font("SansSerif", 1, 24));
        label.setForeground(new java.awt.Color(0, 102, 153));
    }
    
    /**
     * Create a shadow border for panels
     */
    private javax.swing.border.Border createShadowBorder() {
        return javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 235, 245), 1),
                javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }
    
    /**
     * Style form labels with consistent formatting
     */
    private void styleFormLabel(javax.swing.JLabel label) {
        label.setFont(new java.awt.Font("SansSerif", 0, 16));
        label.setForeground(new java.awt.Color(51, 51, 51));
    }
    
    /**
     * Style combo boxes with consistent formatting
     */
    private void styleComboBox(javax.swing.JComboBox comboBox) {
        comboBox.setFont(new java.awt.Font("SansSerif", 0, 14));
        comboBox.setBackground(new java.awt.Color(255, 255, 255));
        comboBox.setForeground(new java.awt.Color(13, 25, 51));
        comboBox.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(90, 141, 224), 1),
                javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
    }
    
    /**
     * Style text fields with consistent formatting
     */
    private void styleTextField(javax.swing.JTextField textField) {
        textField.setFont(new java.awt.Font("SansSerif", 0, 14));
        textField.setBackground(new java.awt.Color(245, 245, 250));
        textField.setForeground(new java.awt.Color(13, 25, 51));
        textField.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(90, 141, 224), 1),
                javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
    }
    
    /**
     * Style buttons with consistent formatting and hover effects
     */
    private void styleButton(javax.swing.JButton button) {
        button.setBackground(new java.awt.Color(26, 79, 156));
        button.setForeground(java.awt.Color.WHITE);
        button.setFont(new java.awt.Font("SansSerif", 1, 14));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        
        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (button.isEnabled()) {
                    java.awt.Color originalColor = button.getBackground();
                    button.setBackground(originalColor.brighter());
                }
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (button.isEnabled()) {
                    if (button == btnSearch) {
                        button.setBackground(new java.awt.Color(30, 144, 255));
                    } else if (button == btnRefresh) {
                        button.setBackground(new java.awt.Color(70, 130, 180));
                    } else if (button == btnSubmit) {
                        button.setBackground(new java.awt.Color(0, 153, 102));
                    } else if (button == jButton1) {
                        button.setBackground(new java.awt.Color(0, 130, 200));
                    } else {
                        button.setBackground(new java.awt.Color(26, 79, 156));
                    }
                }
            }
        });
    }
    
    /**
     * Style table with consistent formatting
     */
    private void styleTable(javax.swing.JTable table) {
        // Set row height
        table.setRowHeight(30);
        
        // Set selection colors
        table.setSelectionBackground(new java.awt.Color(173, 216, 230));
        table.setSelectionForeground(new java.awt.Color(0, 0, 0));
        
        // Style header
        table.getTableHeader().setFont(new java.awt.Font("SansSerif", 1, 14));
        table.getTableHeader().setBackground(new java.awt.Color(26, 79, 156));
        table.getTableHeader().setForeground(java.awt.Color.WHITE);
        
        // Set grid colors
        table.setGridColor(new java.awt.Color(230, 230, 230));
        
        // Set font
        table.setFont(new java.awt.Font("SansSerif", 0, 14));
    }
    
    // Find complaint directory from the system
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
        
        // Traverse networks and enterprises to find customer experience organization
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
    
    // Get complaint directory
    public ComplaintDirectory getComplaintDirectory() {
        return complaintDirectory;
    }
    
    // Set complaint directory
    public void setComplaintDirectory(ComplaintDirectory complaintDirectory) {
        this.complaintDirectory = complaintDirectory;
        populateTable();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitle = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        StatusjComboBox = new javax.swing.JComboBox<>();
        txtSearchComplaintID = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        handlePanel = new javax.swing.JPanel();
        lblTitle1 = new javax.swing.JLabel();
        lblCompaintID = new javax.swing.JLabel();
        lblPN = new javax.swing.JLabel();
        lblPrice1 = new javax.swing.JLabel();
        lblPrice2 = new javax.swing.JLabel();
        lblPrice3 = new javax.swing.JLabel();
        jComboBoxRole = new javax.swing.JComboBox<>();
        btnSubmit = new javax.swing.JButton();
        txtContent = new javax.swing.JTextField();
        txtComplaintType = new javax.swing.JTextField();
        txtCustomertName = new javax.swing.JTextField();
        txtCompaintID = new javax.swing.JTextField();

        setPreferredSize(new java.awt.Dimension(1450, 800));

        lblTitle.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setText("Customer Service Management System");

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel1.setText("Status：");

        StatusjComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pending", "In Progress", " " }));

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

        btnRefresh.setText("Refresh List");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(btnRefresh)
                .addGap(107, 107, 107)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(StatusjComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(223, 223, 223)
                .addComponent(txtSearchComplaintID, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(btnSearch)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(StatusjComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSearchComplaintID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearch)
                    .addComponent(btnRefresh))
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
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Complaint ID", "Customer Name", "Status", "Complaint Type"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jScrollPane2.setViewportView(jScrollPane1);

        jButton1.setText("Process Selected");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel2.setLayout(new java.awt.CardLayout());

        lblTitle1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblTitle1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle1.setText("Process Customers Complaint");

        lblCompaintID.setText("Compaint ID:");

        lblPN.setText("Customer Name:");

        lblPrice1.setText("Complaint Type:");

        lblPrice2.setText("Customer Complaint Content:");

        lblPrice3.setText("Select Department Role:");

        jComboBoxRole.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Warehouse", "Customs Agent", "Logistics" }));
        jComboBoxRole.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxRoleActionPerformed(evt);
            }
        });

        btnSubmit.setText("Submit");
        btnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitActionPerformed(evt);
            }
        });

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
                .addGap(509, 509, 509)
                .addGroup(handlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblPrice2)
                    .addComponent(lblCompaintID)
                    .addComponent(lblPrice1)
                    .addComponent(lblPN)
                    .addComponent(lblPrice3))
                .addGap(37, 37, 37)
                .addGroup(handlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(handlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jComboBoxRole, 0, 220, Short.MAX_VALUE)
                        .addComponent(txtCompaintID, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                        .addComponent(txtComplaintType)
                        .addComponent(txtCustomertName)
                        .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtContent, javax.swing.GroupLayout.PREFERRED_SIZE, 469, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        handlePanelLayout.setVerticalGroup(
            handlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(handlePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitle1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(handlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCompaintID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCompaintID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(handlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPN, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCustomertName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(handlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPrice1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtComplaintType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(handlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPrice3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxRole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(handlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPrice2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtContent, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSubmit)
                .addGap(49, 49, 49))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(handlePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBack)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(handlePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtSearchComplaintIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchComplaintIDActionPerformed
        populateTable();
    }//GEN-LAST:event_txtSearchComplaintIDActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        String searchId = txtSearchComplaintID.getText().trim();
        if (searchId.equals("") || searchId.equals("Saerch Complaint ID...")) {
            populateTable();
            return;
        }
        
        CustomerComplaint foundComplaint = complaintDirectory.findComplaintById(searchId);
        if (foundComplaint != null) {
            // Clear table
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);
            
            // Add found complaint
            Object[] row = new Object[4];
            row[0] = foundComplaint.getComplaintId();
            row[1] = foundComplaint.getCustomerId();
            row[2] = foundComplaint.getStatus();
            row[3] = foundComplaint.getDescription();
            model.addRow(row);
        } else {
            JOptionPane.showMessageDialog(this, "No complaint found with ID: " + searchId);
            populateTable();
        }
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        populateTable();
        txtSearchComplaintID.setText("Saerch Complaint ID...");
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        userProcessContainer.remove(this);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.previous(userProcessContainer);
    }//GEN-LAST:event_btnBackActionPerformed

    private void jComboBoxRoleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxRoleActionPerformed
        // No implementation needed
    }//GEN-LAST:event_jComboBoxRoleActionPerformed

    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitActionPerformed
        if (selectedComplaint == null) {
            JOptionPane.showMessageDialog(this, "Please select a complaint to process first");
            return;
        }
        
        // Get selected department
        String selectedDepartment = jComboBoxRole.getSelectedItem().toString();
        
        // Update complaint status to include the forwarded department
        complaintDirectory.updateComplaintStatus(
            selectedComplaint.getComplaintId(), 
            "Forwarded to " + selectedDepartment
        );
        
        System.out.println("Complaint " + selectedComplaint.getComplaintId() + 
                " status updated to 'Forwarded to " + selectedDepartment + "'");
        
        // In a real application, this would forward the complaint to the respective department
        JOptionPane.showMessageDialog(this, 
                "Complaint " + selectedComplaint.getComplaintId() + 
                " has been forwarded to " + selectedDepartment + 
                " department, status updated to 'Forwarded to " + selectedDepartment + "'");
        
        // Clear form and refresh table
        clearForm();
        populateTable();
    }//GEN-LAST:event_btnSubmitActionPerformed

    private void txtContentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtContentActionPerformed
        // No implementation needed
    }//GEN-LAST:event_txtContentActionPerformed

    private void txtComplaintTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtComplaintTypeActionPerformed
        // No implementation needed
    }//GEN-LAST:event_txtComplaintTypeActionPerformed

    private void txtCustomertNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCustomertNameActionPerformed
        // No implementation needed
    }//GEN-LAST:event_txtCustomertNameActionPerformed

    private void txtCompaintIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCompaintIDActionPerformed
        // No implementation needed
    }//GEN-LAST:event_txtCompaintIDActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
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
    }

    private void refreshRequestTable() {
        populateTable();
    }
    
    // Populate table with data
    private void populateTable() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0); // Clear table
        
        if (complaintDirectory == null) {
            System.out.println("Complaint directory is null, cannot populate table");
            return;
        }
        
        // Get selected status
        String statusFilter = StatusjComboBox.getSelectedItem().toString();
        
        ArrayList<CustomerComplaint> filteredComplaints;
        
        // Filter complaints by status
        if (statusFilter.equals("New")) {
            filteredComplaints = complaintDirectory.getNewComplaints();
        } else if (statusFilter.equals("In Progress")) {
            filteredComplaints = complaintDirectory.getInProgressComplaints();
        } else if (statusFilter.equals("Resolved")) {
            filteredComplaints = complaintDirectory.getResolvedComplaints();
        } else {
            // Show all complaints
            filteredComplaints = complaintDirectory.getComplaints();
        }
        
        // Populate table
        if (filteredComplaints.isEmpty()) {
            System.out.println("No complaints found matching the filter");
        } else {
            for (CustomerComplaint complaint : filteredComplaints) {
                try {
                    Object[] row = new Object[4];
                    row[0] = complaint.getComplaintId();
                    row[1] = complaint.getCustomerId();
                    row[2] = complaint.getStatus();
                    row[3] = complaint.getDescription();
                    model.addRow(row);
                } catch (Exception e) {
                    System.out.println("Error adding row: " + e.getMessage());
                }
            }
        }
    }
    
    // Clear form
    private void clearForm() {
        txtCompaintID.setText("");
        txtCustomertName.setText("");
        txtComplaintType.setText("");
        txtContent.setText("");
        selectedComplaint = null;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> StatusjComboBox;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnSubmit;
    private javax.swing.JPanel handlePanel;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBoxRole;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblCompaintID;
    private javax.swing.JLabel lblPN;
    private javax.swing.JLabel lblPrice1;
    private javax.swing.JLabel lblPrice2;
    private javax.swing.JLabel lblPrice3;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblTitle1;
    private javax.swing.JTextField txtCompaintID;
    private javax.swing.JTextField txtComplaintType;
    private javax.swing.JTextField txtContent;
    private javax.swing.JTextField txtCustomertName;
    private javax.swing.JTextField txtSearchComplaintID;
    // End of variables declaration//GEN-END:variables
}
