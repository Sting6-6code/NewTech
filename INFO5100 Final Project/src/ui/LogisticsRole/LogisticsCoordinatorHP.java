/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui.LogisticsRole;

import Business.ConfigureASystem;
import Business.Enterprise.Enterprise;
import Business.Logistics.Shipment;
import Business.Organization.LogisticsOrganization;
import Business.UserAccount.UserAccount;
import java.awt.CardLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import ui.LogisticsRole.ShipmentPanel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import Business.Customer.ComplaintDirectory;
import Business.Customer.CustomerComplaint;
import Business.Logistics.Task;
import Business.Organization.CustomerExperienceOrganization;
import Business.Organization.Organization;
import java.util.Date;
import ui.MainJFrame;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.LayoutManager;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.Box;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import Business.Logistics.CustomsDeclaration;
import Business.Logistics.CustomsDeclarationDirectory;
import Business.WorkQueue.WarehouseWorkRequest;
import Business.WorkQueue.WorkRequest;
import javax.swing.JTable;

/**
 *
 * @author zhuchenyan
 */
public class LogisticsCoordinatorHP extends javax.swing.JPanel {

    private JPanel userProcessContainer;
    private UserAccount userAccount;
    private Enterprise enterprise;
    private LogisticsOrganization organization;
    private JLabel deltaLabel;
    private CustomsDeclarationDirectory declarationDirectory;
    private CustomsDeclaration currentDeclaration;
    private JPanel parentPanel;

    /**
     * Creates new form LogisticsCoordinatorHP
     */
    public LogisticsCoordinatorHP() {
        initComponents();
        this.setPreferredSize(new java.awt.Dimension(1450, 800));
        logisticsWorkAreajPanel.setLayout(new CardLayout());
    }

    public LogisticsCoordinatorHP(JPanel userProcessContainer, UserAccount account,
            Enterprise enterprise, LogisticsOrganization organization) {

        initComponents();

        this.userProcessContainer = userProcessContainer;
        this.userAccount = account;
        this.enterprise = enterprise;

        this.organization = ConfigureASystem.logisticsOrg;
        this.parentPanel = parentPanel;

        if (this.organization != null) {
            this.declarationDirectory = this.organization.getCustomsDeclarationDirectory();
            System.out.println("LogisticsCoordinatorHP using global LogisticsOrganization with "
                    + (this.organization.getShipmentDirectory() != null
                    ? this.organization.getShipmentDirectory().getShipments().size() : 0) + " shipments");
        } else {
            System.out.println("WARNING: Global LogisticsOrganization is null!");
        }

        this.declarationDirectory = organization.getCustomsDeclarationDirectory();

        // 设置所有统计卡片
        setupStatisticsPanel(actShipmentJPanel, "Active Shipments", "24", "+12% from last week", new Color(51, 51, 255));
        setupStatisticsPanel(CompletedDeliveriesJPanel, "Pending Customs", "18", "+5% from last week", new Color(255, 165, 0));
        setupStatisticsPanel(pendingCustomsJPanel, "Completed Deliveries", "156", "+8% from last month", new Color(46, 204, 113));
        setupStatisticsPanel(CustomerCompJPanel, "Customer Complaints", "3", "-25% from last week", new Color(231, 76, 60));

        this.setPreferredSize(new java.awt.Dimension(1450, 800));

        // Initialize table models
        DefaultTableModel recentShipmentModel = new DefaultTableModel();
        tblRecentShipment.setModel(recentShipmentModel);
        recentShipmentModel.setColumnIdentifiers(new String[]{"Tracking Number", "Ship Date", "Method", "Destination", "Status", "Est. Delivery"});

        DefaultTableModel pendingTasksModel = new DefaultTableModel();
        tblPendingTasks.setModel(pendingTasksModel);
        pendingTasksModel.setColumnIdentifiers(new String[]{"Task ID", "Type", "Priority", "Status", "Due Date"});

        // Populate tables
        populateDashboard();
        populateRecentShipmentsTable();
        populatePendingTasksTable();

        // Debug output
        System.out.println("LogisticsCoordinatorHP initialized");
        System.out.println("Organization: " + (organization != null ? "exists" : "null"));
        if (organization != null && organization.getShipmentDirectory() != null) {
            System.out.println("Number of shipments: " + organization.getShipmentDirectory().getShipments().size());
        }
        System.out.println("Recent shipments table rows: " + tblRecentShipment.getRowCount());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane = new javax.swing.JSplitPane();
        logisticsControljPanel = new javax.swing.JPanel();
        btnDashBoard = new javax.swing.JButton();
        btnCustomsDeclar = new javax.swing.JButton();
        btnCusComplaint = new javax.swing.JButton();
        btnProfile = new javax.swing.JButton();
        btnLogout = new javax.swing.JButton();
        btnShipmentTra = new javax.swing.JButton();
        logisticsWorkAreajPanel = new javax.swing.JPanel();
        CustomerCompJPanel = new javax.swing.JPanel();
        lblCustomerComplaint = new javax.swing.JLabel();
        actShipmentJPanel = new javax.swing.JPanel();
        lblActiveShipments = new javax.swing.JLabel();
        CompletedDeliveriesJPanel = new javax.swing.JPanel();
        lblPendingCustoms = new javax.swing.JLabel();
        pendingCustomsJPanel = new javax.swing.JPanel();
        lblCompletedDeliveries = new javax.swing.JLabel();
        pendingTasksJPanel = new javax.swing.JPanel();
        lblPendingTasks = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblPendingTasks = new javax.swing.JTable();
        btnViewDetails = new javax.swing.JButton();
        recentShipJPanel = new javax.swing.JPanel();
        lblRecentShip = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblRecentShipment = new javax.swing.JTable();

        setMaximumSize(new java.awt.Dimension(1450, 800));
        setMinimumSize(new java.awt.Dimension(1450, 800));

        btnDashBoard.setText("DashBoard");
        btnDashBoard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDashBoardActionPerformed(evt);
            }
        });

        btnCustomsDeclar.setText("Customs Declaration");
        btnCustomsDeclar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCustomsDeclarActionPerformed(evt);
            }
        });

        btnCusComplaint.setText("Customer Complaint");
        btnCusComplaint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCusComplaintActionPerformed(evt);
            }
        });

        btnProfile.setText("My Profile");
        btnProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProfileActionPerformed(evt);
            }
        });

        btnLogout.setText("Logout");
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

        btnShipmentTra.setText("Shipment Tracking");
        btnShipmentTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShipmentTraActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout logisticsControljPanelLayout = new javax.swing.GroupLayout(logisticsControljPanel);
        logisticsControljPanel.setLayout(logisticsControljPanelLayout);
        logisticsControljPanelLayout.setHorizontalGroup(
            logisticsControljPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(logisticsControljPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(logisticsControljPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnLogout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(logisticsControljPanelLayout.createSequentialGroup()
                        .addGroup(logisticsControljPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnCusComplaint)
                            .addComponent(btnProfile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnDashBoard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnShipmentTra, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnCustomsDeclar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        logisticsControljPanelLayout.setVerticalGroup(
            logisticsControljPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(logisticsControljPanelLayout.createSequentialGroup()
                .addGap(161, 161, 161)
                .addComponent(btnDashBoard, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(btnShipmentTra, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(btnCustomsDeclar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(btnCusComplaint, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(btnProfile)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 236, Short.MAX_VALUE)
                .addComponent(btnLogout)
                .addGap(47, 47, 47))
        );

        logisticsControljPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnDashBoard, btnProfile, btnShipmentTra});

        jSplitPane.setLeftComponent(logisticsControljPanel);

        logisticsWorkAreajPanel.setBackground(new java.awt.Color(255, 255, 255));
        logisticsWorkAreajPanel.setMaximumSize(new java.awt.Dimension(1250, 800));
        logisticsWorkAreajPanel.setMinimumSize(new java.awt.Dimension(1000, 800));
        logisticsWorkAreajPanel.setLayout(null);

        CustomerCompJPanel.setBackground(new java.awt.Color(255, 255, 255));
        CustomerCompJPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblCustomerComplaint.setText("Customer Complaint");

        javax.swing.GroupLayout CustomerCompJPanelLayout = new javax.swing.GroupLayout(CustomerCompJPanel);
        CustomerCompJPanel.setLayout(CustomerCompJPanelLayout);
        CustomerCompJPanelLayout.setHorizontalGroup(
            CustomerCompJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CustomerCompJPanelLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(lblCustomerComplaint)
                .addContainerGap(163, Short.MAX_VALUE))
        );
        CustomerCompJPanelLayout.setVerticalGroup(
            CustomerCompJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CustomerCompJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblCustomerComplaint)
                .addContainerGap(152, Short.MAX_VALUE))
        );

        logisticsWorkAreajPanel.add(CustomerCompJPanel);
        CustomerCompJPanel.setBounds(970, 40, 300, 180);

        actShipmentJPanel.setBackground(new java.awt.Color(255, 255, 255));
        actShipmentJPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblActiveShipments.setText("Active Shipments");

        javax.swing.GroupLayout actShipmentJPanelLayout = new javax.swing.GroupLayout(actShipmentJPanel);
        actShipmentJPanel.setLayout(actShipmentJPanelLayout);
        actShipmentJPanelLayout.setHorizontalGroup(
            actShipmentJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(actShipmentJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblActiveShipments)
                .addContainerGap(188, Short.MAX_VALUE))
        );
        actShipmentJPanelLayout.setVerticalGroup(
            actShipmentJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(actShipmentJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblActiveShipments)
                .addContainerGap(153, Short.MAX_VALUE))
        );

        logisticsWorkAreajPanel.add(actShipmentJPanel);
        actShipmentJPanel.setBounds(40, 40, 300, 180);

        CompletedDeliveriesJPanel.setBackground(new java.awt.Color(255, 255, 255));
        CompletedDeliveriesJPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblPendingCustoms.setText("Pending Customs");

        javax.swing.GroupLayout CompletedDeliveriesJPanelLayout = new javax.swing.GroupLayout(CompletedDeliveriesJPanel);
        CompletedDeliveriesJPanel.setLayout(CompletedDeliveriesJPanelLayout);
        CompletedDeliveriesJPanelLayout.setHorizontalGroup(
            CompletedDeliveriesJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CompletedDeliveriesJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblPendingCustoms)
                .addContainerGap(187, Short.MAX_VALUE))
        );
        CompletedDeliveriesJPanelLayout.setVerticalGroup(
            CompletedDeliveriesJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CompletedDeliveriesJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblPendingCustoms)
                .addContainerGap(153, Short.MAX_VALUE))
        );

        logisticsWorkAreajPanel.add(CompletedDeliveriesJPanel);
        CompletedDeliveriesJPanel.setBounds(350, 40, 300, 180);

        pendingCustomsJPanel.setBackground(new java.awt.Color(255, 255, 255));
        pendingCustomsJPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblCompletedDeliveries.setText("Completed Deliveries");

        javax.swing.GroupLayout pendingCustomsJPanelLayout = new javax.swing.GroupLayout(pendingCustomsJPanel);
        pendingCustomsJPanel.setLayout(pendingCustomsJPanelLayout);
        pendingCustomsJPanelLayout.setHorizontalGroup(
            pendingCustomsJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pendingCustomsJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblCompletedDeliveries)
                .addContainerGap(165, Short.MAX_VALUE))
        );
        pendingCustomsJPanelLayout.setVerticalGroup(
            pendingCustomsJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pendingCustomsJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblCompletedDeliveries)
                .addContainerGap(153, Short.MAX_VALUE))
        );

        logisticsWorkAreajPanel.add(pendingCustomsJPanel);
        pendingCustomsJPanel.setBounds(660, 40, 300, 180);

        pendingTasksJPanel.setBackground(new java.awt.Color(255, 255, 255));
        pendingTasksJPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblPendingTasks.setText("Pending Tasks");

        tblPendingTasks.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        tblPendingTasks.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Task", "Priority", "Due Date", "Status"
            }
        ));
        jScrollPane2.setViewportView(tblPendingTasks);
        if (tblPendingTasks.getColumnModel().getColumnCount() > 0) {
            tblPendingTasks.getColumnModel().getColumn(1).setResizable(false);
        }

        btnViewDetails.setText("View Details");
        btnViewDetails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewDetailsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pendingTasksJPanelLayout = new javax.swing.GroupLayout(pendingTasksJPanel);
        pendingTasksJPanel.setLayout(pendingTasksJPanelLayout);
        pendingTasksJPanelLayout.setHorizontalGroup(
            pendingTasksJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pendingTasksJPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnViewDetails)
                .addGap(45, 45, 45))
            .addGroup(pendingTasksJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pendingTasksJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPendingTasks)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1206, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        pendingTasksJPanelLayout.setVerticalGroup(
            pendingTasksJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pendingTasksJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblPendingTasks)
                .addGap(20, 20, 20)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnViewDetails)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        logisticsWorkAreajPanel.add(pendingTasksJPanel);
        pendingTasksJPanel.setBounds(40, 260, 1230, 270);

        recentShipJPanel.setBackground(new java.awt.Color(255, 255, 255));
        recentShipJPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblRecentShip.setText("Recent Shipment");

        tblRecentShipment.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        tblRecentShipment.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Tracking Number", "Shipping Date", "Shipping Method", "Destination", "Status", "Estimated Delivery Date"
            }
        ));
        jScrollPane1.setViewportView(tblRecentShipment);

        javax.swing.GroupLayout recentShipJPanelLayout = new javax.swing.GroupLayout(recentShipJPanel);
        recentShipJPanel.setLayout(recentShipJPanelLayout);
        recentShipJPanelLayout.setHorizontalGroup(
            recentShipJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(recentShipJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(recentShipJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblRecentShip)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1208, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        recentShipJPanelLayout.setVerticalGroup(
            recentShipJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(recentShipJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblRecentShip)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(9, Short.MAX_VALUE))
        );

        logisticsWorkAreajPanel.add(recentShipJPanel);
        recentShipJPanel.setBounds(40, 550, 1230, 230);

        jSplitPane.setRightComponent(logisticsWorkAreajPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane, javax.swing.GroupLayout.DEFAULT_SIZE, 1450, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnCustomsDeclarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCustomsDeclarActionPerformed
        // TODO add your handling code here:
        // Create and display customs declaration panel
        DocumentationDetails documentPanel = new DocumentationDetails(userProcessContainer, userAccount, enterprise, organization, null,this);
        documentPanel.setSize(1450, 800);
        userProcessContainer.add("CustomsDeclaration", documentPanel);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.show(userProcessContainer, "CustomsDeclaration");
    }//GEN-LAST:event_btnCustomsDeclarActionPerformed

    private void btnDashBoardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDashBoardActionPerformed
        // TODO add your handling code here:
        // Refresh dashboard data
        populateDashboard();

        // Display the dashboard panel (which is this panel)
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.show(userProcessContainer, "LogisticsCoordinator");
    }//GEN-LAST:event_btnDashBoardActionPerformed


    private void btnCusComplaintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCusComplaintActionPerformed
        // TODO add your handling code here:
        // Create and display customer complaint panel
        LogisticComplaintContent complaintPanel = new LogisticComplaintContent(userProcessContainer);
        complaintPanel.setSize(1450, 800);
        userProcessContainer.add("CustomerComplaint", complaintPanel);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.show(userProcessContainer, "CustomerComplaint");

    }//GEN-LAST:event_btnCusComplaintActionPerformed

    private void btnProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProfileActionPerformed
        // TODO add your handling code here:
        // Create and display profile management panel
        ManageOwnProfile profilePanel = new ManageOwnProfile(userProcessContainer, userAccount);
        profilePanel.setSize(1450, 800);
        userProcessContainer.add("ManageProfile", profilePanel);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.show(userProcessContainer, "ManageProfile");
    }//GEN-LAST:event_btnProfileActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        // TODO add your handling code here:
        // Logout operation
        userProcessContainer.removeAll();

        // Create a blank panel to return to login screen
        JPanel blankJP = new JPanel();
        userProcessContainer.add("blank", blankJP);

        // Return to the first card (login screen)
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.first(userProcessContainer);
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void btnShipmentTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShipmentTraActionPerformed
        // TODO add your handling code here:
        // First check if the organization reference is valid
        LogisticsOrganization logisticsOrg = this.organization;
        if (logisticsOrg == null || logisticsOrg.getShipmentDirectory() == null) {
            // If invalid, get the global one
            logisticsOrg = ConfigureASystem.getLogisticsOrganization();
            System.out.println("Using global LogisticsOrganization");
        }

        // Debug print
        System.out.println("Creating ShipmentPanel with organization: "
                + (logisticsOrg != null ? "Yes" : "No"));
        if (logisticsOrg != null && logisticsOrg.getShipmentDirectory() != null) {
            System.out.println("Number of shipments before creating ShipmentPanel: "
                    + logisticsOrg.getShipmentDirectory().getShipments().size());
        }

        ShipmentPanel shipmentPanel = new ShipmentPanel(userProcessContainer, userAccount, enterprise, logisticsOrg, parentPanel);
        shipmentPanel.setSize(1450, 800);
        userProcessContainer.add("Shipment", shipmentPanel);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.show(userProcessContainer, "Shipment");

    }//GEN-LAST:event_btnShipmentTraActionPerformed

    private void btnViewDetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewDetailsActionPerformed
        // TODO add your handling code here:
        int selectedRow = tblPendingTasks.getSelectedRow();
    if (selectedRow < 0) {
        JOptionPane.showMessageDialog(this, "Please select a task");
        return;
    }
    
    String trackingNumber = tblPendingTasks.getValueAt(selectedRow, 0).toString();
    
    // 创建 ShipmentPanel 并传递 this 作为父面板
    ShipmentPanel shipmentPanel = new ShipmentPanel(userProcessContainer, userAccount, enterprise, organization, this);
    userProcessContainer.add("ShipmentPanel", shipmentPanel);
    
    // 自动选中对应的货件
    selectShipmentInPanel(shipmentPanel, trackingNumber);
    
    CardLayout layout = (CardLayout) userProcessContainer.getLayout();
    layout.next(userProcessContainer);
    }//GEN-LAST:event_btnViewDetailsActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel CompletedDeliveriesJPanel;
    private javax.swing.JPanel CustomerCompJPanel;
    private javax.swing.JPanel actShipmentJPanel;
    private javax.swing.JButton btnCusComplaint;
    private javax.swing.JButton btnCustomsDeclar;
    private javax.swing.JButton btnDashBoard;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnProfile;
    private javax.swing.JButton btnShipmentTra;
    private javax.swing.JButton btnViewDetails;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane;
    private javax.swing.JLabel lblActiveShipments;
    private javax.swing.JLabel lblCompletedDeliveries;
    private javax.swing.JLabel lblCustomerComplaint;
    private javax.swing.JLabel lblPendingCustoms;
    private javax.swing.JLabel lblPendingTasks;
    private javax.swing.JLabel lblRecentShip;
    private javax.swing.JPanel logisticsControljPanel;
    private javax.swing.JPanel logisticsWorkAreajPanel;
    private javax.swing.JPanel pendingCustomsJPanel;
    private javax.swing.JPanel pendingTasksJPanel;
    private javax.swing.JPanel recentShipJPanel;
    private javax.swing.JTable tblPendingTasks;
    private javax.swing.JTable tblRecentShipment;
    // End of variables declaration//GEN-END:variables

    /**
     * Populate dashboard with relevant data from the organization
     */
    private void populateDashboard() {
        try {
            // Get actual data
            int activeShipments = 0;
            int pendingCustoms = 0;
            int completedDeliveries = 0;
            int customerComplaints = 0;

            if (organization != null && organization.getShipmentDirectory() != null) {
                System.out.println("Found organization with "
                        + organization.getShipmentDirectory().getShipments().size() + " shipments");

                for (Shipment shipment : organization.getShipmentDirectory().getShipments()) {
                    String status = shipment.getShipmentStatus();
                    System.out.println("Processing shipment with status: " + status);

                    // Consider any non-delivered, non-exception shipment as active
                    if (Shipment.STATUS_PENDING.equals(status)
                            || Shipment.STATUS_PROCESSING.equals(status)
                            || Shipment.STATUS_SHIPPED.equals(status)
                            || Shipment.STATUS_IN_TRANSIT.equals(status)
                            || Shipment.STATUS_DELIVERING.equals(status)
                            || "Active".equals(status)) {
                        activeShipments++;
                    } // Count customs-related statuses
                    else if ("Pending Customs".equals(status)
                            || "Customs Clearance".equals(status)) {
                        pendingCustoms++;
                    } // Count deliveries
                    else if (Shipment.STATUS_DELIVERED.equals(status)
                            || "Delivered".equals(status)) {
                        completedDeliveries++;
                    }
                }

                System.out.println("Counted shipments - Active: " + activeShipments
                        + ", Customs: " + pendingCustoms + ", Delivered: " + completedDeliveries);
            } else {
                System.out.println("Organization or ShipmentDirectory is null");
            }

            // If no real data found, use sample values
            if (activeShipments == 0 && pendingCustoms == 0 && completedDeliveries == 0) {
                System.out.println("No matching shipment data found, using sample values");
                activeShipments = 24;
                pendingCustoms = 18;
                completedDeliveries = 156;
            }

            // 获取客户投诉数量
            if (enterprise != null) {
                for (Organization org : enterprise.getOrganizationDirectory().getOrganizationList()) {
                    if (org instanceof CustomerExperienceOrganization) {
                        CustomerExperienceOrganization ceo = (CustomerExperienceOrganization) org;
                        customerComplaints = ceo.getComplaintDirectory().getComplaints().size();
                        break;
                    }
                }
            }

            // 更新统计卡片
            setupStatisticsPanel(actShipmentJPanel, "Active Shipments",
                    String.valueOf(activeShipments), "+12% from last week", new Color(51, 51, 255));

            setupStatisticsPanel(CompletedDeliveriesJPanel, "Pending Customs",
                    String.valueOf(pendingCustoms), "+5% from last week", new Color(255, 165, 0));

            setupStatisticsPanel(pendingCustomsJPanel, "Completed Deliveries",
                    String.valueOf(completedDeliveries), "+8% from last month", new Color(46, 204, 113));

            setupStatisticsPanel(CustomerCompJPanel, "Customer Complaints",
                    String.valueOf(customerComplaints), "-25% from last week", new Color(231, 76, 60));

            // ... 其他现有的仪表板更新代码 ...
        } catch (Exception e) {
            System.out.println("Error in populateDashboard: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void populatePendingTasksTable() {
        // 创建表格模型
        DefaultTableModel model = (DefaultTableModel) tblPendingTasks.getModel();
    model.setRowCount(0);
    
    if (organization != null && organization.getWorkQueue() != null) {
        for (WorkRequest request : organization.getWorkQueue().getWorkRequestList()) {
            if (request instanceof WarehouseWorkRequest) {
                WarehouseWorkRequest warehouseRequest = (WarehouseWorkRequest) request;
                
                // 只显示状态为 Pending 的请求
                if ("Pending".equals(warehouseRequest.getStatus())) {
                    Object[] row = new Object[5];
                    row[0] = warehouseRequest.getTrackingNumber();
                    row[1] = "Logistics transport request";
                    row[2] = "Normal";
                    row[3] = warehouseRequest.getRequestDate();
                    row[4] = warehouseRequest.getStatus();
                    
                    model.addRow(row);
                }
            }
        }
    }

    }

    private void populateRecentShipmentsTable() {
        DefaultTableModel model = (DefaultTableModel) tblRecentShipment.getModel();
    model.setRowCount(0);
    
    if (organization != null && organization.getWorkQueue() != null) {
        for (WorkRequest request : organization.getWorkQueue().getWorkRequestList()) {
            if (request instanceof WarehouseWorkRequest) {
                WarehouseWorkRequest warehouseRequest = (WarehouseWorkRequest) request;
                
                // 只显示状态为 Shipped 的请求
                if ("Shipped".equals(warehouseRequest.getStatus())) {
                    Object[] row = new Object[6];
                    row[0] = warehouseRequest.getTrackingNumber();
                    row[1] = warehouseRequest.getShippingMethod();
                    row[2] = warehouseRequest.getDestination();
                    row[3] = warehouseRequest.getStatus();
                    row[4] = warehouseRequest.getRequestDate();
                    row[5] = warehouseRequest.getEstimatedDeliveryDate();
                    
                    model.addRow(row);
                }
            }
        }
    }
    }

    private void addSampleShipmentData(javax.swing.table.DefaultTableModel model) {
        java.util.Date today = new java.util.Date();
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(today);

        // Yesterday
        cal.add(java.util.Calendar.DAY_OF_MONTH, -1);
        java.util.Date yesterday = cal.getTime();

        // Two days ago
        cal.add(java.util.Calendar.DAY_OF_MONTH, -1);
        java.util.Date twoDaysAgo = cal.getTime();

        // Three days ago
        cal.add(java.util.Calendar.DAY_OF_MONTH, -1);
        java.util.Date threeDaysAgo = cal.getTime();

        // Future date (estimated delivery)
        cal.setTime(today);
        cal.add(java.util.Calendar.DAY_OF_MONTH, 5);
        java.util.Date futureDate = cal.getTime();

        // Tomorrow
        cal.setTime(today);
        cal.add(java.util.Calendar.DAY_OF_MONTH, 1);
        java.util.Date tomorrow = cal.getTime();

        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM/dd/yyyy");

        model.addRow(new Object[]{"TRK123456", sdf.format(yesterday), "Air Freight", "Shanghai, China", "In Transit", sdf.format(futureDate)});
        model.addRow(new Object[]{"TRK789012", sdf.format(twoDaysAgo), "Sea Freight", "Rotterdam, Netherlands", "Customs Clearance", sdf.format(futureDate)});
        model.addRow(new Object[]{"TRK345678", sdf.format(threeDaysAgo), "Ground", "New York, USA", "Delivered", sdf.format(yesterday)});
        model.addRow(new Object[]{"TRK901234", sdf.format(threeDaysAgo), "Express Air", "Tokyo, Japan", "In Transit", sdf.format(tomorrow)});
    }

    private void setupActiveShipmentsPanel() {
        try {
            // 设置面板样式
            actShipmentJPanel.setBackground(Color.WHITE);
            actShipmentJPanel.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230), 1, true));
            actShipmentJPanel.setLayout(new BoxLayout(actShipmentJPanel, BoxLayout.Y_AXIS));

            // 清除现有组件
            actShipmentJPanel.removeAll();

            // 创建面板来包含所有组件
            javax.swing.JPanel contentPanel = new javax.swing.JPanel();
            contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
            contentPanel.setBackground(Color.WHITE);
            contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            // 添加标题
            JLabel titleLabel = new JLabel("Active Shipments");
            titleLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
            titleLabel.setForeground(new Color(128, 128, 128));
            titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            contentPanel.add(titleLabel);
            contentPanel.add(Box.createVerticalStrut(10));

            // 设置数字标签
            lblActiveShipments.setFont(new Font("Tahoma", Font.BOLD, 36));
            lblActiveShipments.setForeground(new Color(51, 51, 255));
            lblActiveShipments.setAlignmentX(Component.LEFT_ALIGNMENT);
            contentPanel.add(lblActiveShipments);
            contentPanel.add(Box.createVerticalStrut(5));

            // 添加增长率标签
            deltaLabel = new JLabel("+12% from last week");
            deltaLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
            deltaLabel.setForeground(new Color(128, 128, 128));
            deltaLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            contentPanel.add(deltaLabel);

            // 将内容面板添加到主面板
            actShipmentJPanel.add(contentPanel);

            // 刷新面板
            actShipmentJPanel.revalidate();
            actShipmentJPanel.repaint();

        } catch (Exception e) {
            System.out.println("Error in setupActiveShipmentsPanel: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void setupStatisticsPanel(JPanel panel, String title, String value, String deltaText, Color valueColor) {
        try {
            // 保持原有的边框样式
            panel.setBorder(BorderFactory.createEtchedBorder());
            panel.setBackground(Color.WHITE);
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

            // 清除现有组件
            panel.removeAll();

            // 创建内容面板
            JPanel contentPanel = new JPanel();
            contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
            contentPanel.setBackground(Color.WHITE);
            contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            // 添加标题
            JLabel titleLabel = new JLabel(title);
            titleLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
            titleLabel.setForeground(new Color(128, 128, 128));
            titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            contentPanel.add(titleLabel);
            contentPanel.add(Box.createVerticalStrut(10));

            // 添加数值
            JLabel valueLabel = new JLabel(value);
            valueLabel.setFont(new Font("Tahoma", Font.BOLD, 36));
            valueLabel.setForeground(valueColor);
            valueLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            contentPanel.add(valueLabel);
            contentPanel.add(Box.createVerticalStrut(5));

            // 添加增长率文本
            JLabel deltaLabel = new JLabel(deltaText);
            deltaLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
            deltaLabel.setForeground(new Color(128, 128, 128));
            deltaLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            contentPanel.add(deltaLabel);

            // 将内容面板添加到主面板
            panel.add(contentPanel);

            // 刷新面板
            panel.revalidate();
            panel.repaint();
        } catch (Exception e) {
            System.out.println("Error in setupStatisticsPanel: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void refreshShipments() {
        if (organization != null && organization.getShipmentDirectory() != null) {
            // 更新活跃订单数量
            int activeShipments = 0;
            for (Shipment shipment : organization.getShipmentDirectory().getShipments()) {
                if (!Shipment.STATUS_DELIVERED.equals(shipment.getShipmentStatus())
                        && !Shipment.STATUS_EXCEPTION.equals(shipment.getShipmentStatus())) {
                    activeShipments++;
                }
            }

            // 更新显示
            populateRecentShipmentsTable();
            updateStatistics(activeShipments);
        }
    }

    private void updateStatistics(int activeShipments) {
        // 更新统计面板显示
        setupStatisticsPanel(actShipmentJPanel,
                "Active Shipments",
                String.valueOf(activeShipments),
                "",
                new Color(51, 51, 255));
    }

    /**
     * 在ShipmentPanel中选中指定运单号的运输单
     */
    private void selectShipmentInPanel(ShipmentPanel shipmentPanel, String trackingNumber) {
        try {
            if (shipmentPanel == null) {
                System.err.println("Error: ShipmentPanel is null");
                return;
            }

            // 获取ShipmentPanel中的表格
            JTable tblShipment = shipmentPanel.getTblShipment();
            if (tblShipment == null) {
                System.err.println("Error: Shipment table is null");
                return;
            }

            // 遍历表格查找匹配的运单号
            DefaultTableModel model = (DefaultTableModel) tblShipment.getModel();
            for (int i = 0; i < model.getRowCount(); i++) {
                Object value = model.getValueAt(i, 0);
                if (value != null && trackingNumber.equals(value.toString())) {
                    // 选中该行
                    tblShipment.setRowSelectionInterval(i, i);
                    // 确保选中的行可见
                    tblShipment.scrollRectToVisible(tblShipment.getCellRect(i, 0, true));
                    System.out.println("Selected shipment with tracking number: " + trackingNumber);
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println("Error selecting shipment: " + e.getMessage());
            e.printStackTrace();
        }
    }

    
    public void refreshTables() {
    populatePendingTasksTable();
    populateRecentShipmentsTable();
}

}
