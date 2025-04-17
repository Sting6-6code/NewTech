/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui.CustomsAgentRole;

import Business.Enterprise.Enterprise;
import Business.Organization.Organization;
import Business.UserAccount.UserAccount;
import java.awt.CardLayout;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;

/**
 *
 * @author zhuchenyan
 */
public class CustomsLiaisionOfficeHP extends javax.swing.JPanel {

    private JPanel userProcessContainer;
    private UserAccount userAccount;
    private Enterprise enterprise;
    private Organization organization;
    
    /**
     * Creates new form CustomsLiaisionOfficeHP
     */
    public CustomsLiaisionOfficeHP(JPanel userProcessContainer, UserAccount account, 
                                Enterprise enterprise, Organization organization) {
        
        initComponents();
        
        this.userProcessContainer = userProcessContainer;
        this.userAccount = account;
        this.enterprise = enterprise;
        this.organization = organization;
        
        // Set up the dashboard
        populateDashboard();
    }
    
    private void populateDashboard() {
        // Update statistics
        updateStatistics();
        
        // Populate tables
        populatePendingDeclarationsTable();
        populateInspectionsTable();
        
        // Update alerts
        updateAlertsPanel();
    }

    private void updateStatistics() {
        int pendingDeclarations = 0;
        int pendingInspections = 0;
        int completedToday = 0;
        
        Date today = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String todayStr = sdf.format(today);
        
        if (organization != null && organization.getCustomsDeclarationDirectory() != null) {
            // Count pending declarations
            for (CustomsDeclaration declaration : organization.getCustomsDeclarationDirectory().getDeclarations()) {
                if ("Pending".equals(declaration.getStatus())) {
                    pendingDeclarations++;
                }
                
                // Count completed today
                if ("Approved".equals(declaration.getStatus()) || "Rejected".equals(declaration.getStatus())) {
                    String declDateStr = sdf.format(declaration.getDeclarationDate());
                    if (todayStr.equals(declDateStr)) {
                        completedToday++;
                    }
                }
            }
            
            // Count pending inspections
            if (organization.getCustomsInspectionDirectory() != null) {
                for (CustomsInspection inspection : organization.getCustomsInspectionDirectory().getInspections()) {
                    if ("Scheduled".equals(inspection.getStatus())) {
                        pendingInspections++;
                    }
                }
            }
        }
        
        // Update labels
        lblPendingDeclarations.setText("Pending Declarations: " + pendingDeclarations);
        lblPendingInspections.setText("Pending Inspections: " + pendingInspections);
        lblCompletedToday.setText("Completed Today: " + completedToday);
    }
    
    private void populatePendingDeclarationsTable() {
        DefaultTableModel model = (DefaultTableModel) tblPendingDeclarations.getModel();
        model.setRowCount(0);
        
        if (organization != null && organization.getCustomsDeclarationDirectory() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            
            for (CustomsDeclaration declaration : 
                    organization.getCustomsDeclarationDirectory().getPendingDeclarations()) {
                Object[] row = new Object[6];
                row[0] = declaration.getDeclarationId();
                row[1] = declaration.getShipmentId();
                row[2] = sdf.format(declaration.getDeclarationDate());
                row[3] = declaration.getCountryOfOrigin();
                row[4] = declaration.getDestinationCountry();
                row[5] = declaration.getStatus();
                model.addRow(row);
            }
        }
    }
    
    private void populateInspectionsTable() {
        DefaultTableModel model = (DefaultTableModel) tblInspections.getModel();
        model.setRowCount(0);
        
        if (organization != null && organization.getCustomsInspectionDirectory() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            
            for (CustomsInspection inspection : 
                    organization.getCustomsInspectionDirectory().getPendingInspections()) {
                Object[] row = new Object[5];
                row[0] = inspection.getInspectionId();
                row[1] = inspection.getDeclarationId();
                row[2] = sdf.format(inspection.getInspectionDate());
                row[3] = inspection.getInspectionType();
                row[4] = inspection.getStatus();
                model.addRow(row);
            }
        }
    }
    
    private void updateAlertsPanel() {
        StringBuilder alertsText = new StringBuilder("<html>");
        boolean hasAlerts = false;
        
        if (organization != null && organization.getCustomsDeclarationDirectory() != null) {
            // Check for urgent declarations (more than 48 hours old)
            int urgentDeclarations = 0;
            Date currentDate = new Date();
            long fortyEightHours = 48 * 60 * 60 * 1000;
            
            for (CustomsDeclaration declaration : 
                    organization.getCustomsDeclarationDirectory().getPendingDeclarations()) {
                if (currentDate.getTime() - declaration.getDeclarationDate().getTime() > fortyEightHours) {
                    urgentDeclarations++;
                }
            }
            
            if (urgentDeclarations > 0) {
                alertsText.append("<p style='color: red;'>⚠ ")
                         .append(urgentDeclarations)
                         .append(" declaration(s) pending for more than 48 hours</p>");
                hasAlerts = true;
            }
            
            // Check for today's inspections
            if (organization.getCustomsInspectionDirectory() != null) {
                int todayInspections = 0;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String todayStr = sdf.format(currentDate);
                
                for (CustomsInspection inspection : 
                        organization.getCustomsInspectionDirectory().getInspections()) {
                    if ("Scheduled".equals(inspection.getStatus()) && 
                        todayStr.equals(sdf.format(inspection.getInspectionDate()))) {
                        todayInspections++;
                    }
                }
                
                if (todayInspections > 0) {
                    alertsText.append("<p style='color: blue;'>ℹ ")
                             .append(todayInspections)
                             .append(" inspection(s) scheduled for today</p>");
                    hasAlerts = true;
                }
            }
        }
        
        if (!hasAlerts) {
            alertsText.append("<p style='color: green;'>✓ No urgent alerts at this time</p>");
        }
        
        alertsText.append("</html>");
        lblAlerts.setText(alertsText.toString());
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
        cusHPControlJPanel = new javax.swing.JPanel();
        btnDashBoard = new javax.swing.JButton();
        btnDocReview = new javax.swing.JButton();
        btnSubmitDocs = new javax.swing.JButton();
        btnReturnTax = new javax.swing.JButton();
        btnProfile = new javax.swing.JButton();
        btnLogout = new javax.swing.JButton();
        cusHPWorkspaceJPanel = new javax.swing.JPanel();
        pendingRevsJPanel = new javax.swing.JPanel();
        lblPendingReviews = new javax.swing.JLabel();
        ApprovedDocumentsJPanel = new javax.swing.JPanel();
        lblApprovedDocs = new javax.swing.JLabel();
        RejectedDocsJPanel = new javax.swing.JPanel();
        lblRejectedDocs = new javax.swing.JLabel();
        TaxReturnsJPanel = new javax.swing.JPanel();
        lblTaxReturns = new javax.swing.JLabel();
        pendingDocsJPanel = new javax.swing.JPanel();
        lblPendingDocs = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblPendingDocs = new javax.swing.JTable();
        AlertJPanel = new javax.swing.JPanel();
        lblAlerts = new javax.swing.JLabel();
        recentActivitiesJPanel = new javax.swing.JPanel();
        lblRecentActivities = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblRecentActivities = new javax.swing.JTable();

        setMaximumSize(new java.awt.Dimension(1450, 800));
        setMinimumSize(new java.awt.Dimension(1450, 800));

        btnDashBoard.setText("DashBoard");

        btnDocReview.setText("Document Review");

        btnSubmitDocs.setText("Submit Documents");
        btnSubmitDocs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitDocsActionPerformed(evt);
            }
        });

        btnReturnTax.setText("Return Tax");

        btnProfile.setText("My Profile");

        btnLogout.setText("Logout");

        javax.swing.GroupLayout cusHPControlJPanelLayout = new javax.swing.GroupLayout(cusHPControlJPanel);
        cusHPControlJPanel.setLayout(cusHPControlJPanelLayout);
        cusHPControlJPanelLayout.setHorizontalGroup(
            cusHPControlJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cusHPControlJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(cusHPControlJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnDashBoard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDocReview, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSubmitDocs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnReturnTax, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnProfile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLogout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        cusHPControlJPanelLayout.setVerticalGroup(
            cusHPControlJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cusHPControlJPanelLayout.createSequentialGroup()
                .addGap(161, 161, 161)
                .addComponent(btnDashBoard, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(btnDocReview, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(btnSubmitDocs, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(btnReturnTax, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(btnProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 262, Short.MAX_VALUE)
                .addComponent(btnLogout)
                .addGap(47, 47, 47))
        );

        jSplitPane.setLeftComponent(cusHPControlJPanel);

        cusHPWorkspaceJPanel.setBackground(new java.awt.Color(255, 255, 255));
        cusHPWorkspaceJPanel.setMaximumSize(new java.awt.Dimension(1000, 800));

        pendingRevsJPanel.setBackground(new java.awt.Color(255, 255, 255));
        pendingRevsJPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblPendingReviews.setText("Pending Reviews");

        javax.swing.GroupLayout pendingRevsJPanelLayout = new javax.swing.GroupLayout(pendingRevsJPanel);
        pendingRevsJPanel.setLayout(pendingRevsJPanelLayout);
        pendingRevsJPanelLayout.setHorizontalGroup(
            pendingRevsJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pendingRevsJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblPendingReviews)
                .addContainerGap(188, Short.MAX_VALUE))
        );
        pendingRevsJPanelLayout.setVerticalGroup(
            pendingRevsJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pendingRevsJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblPendingReviews)
                .addContainerGap(153, Short.MAX_VALUE))
        );

        ApprovedDocumentsJPanel.setBackground(new java.awt.Color(255, 255, 255));
        ApprovedDocumentsJPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblApprovedDocs.setText("Approved Documents");

        javax.swing.GroupLayout ApprovedDocumentsJPanelLayout = new javax.swing.GroupLayout(ApprovedDocumentsJPanel);
        ApprovedDocumentsJPanel.setLayout(ApprovedDocumentsJPanelLayout);
        ApprovedDocumentsJPanelLayout.setHorizontalGroup(
            ApprovedDocumentsJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ApprovedDocumentsJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblApprovedDocs)
                .addContainerGap(187, Short.MAX_VALUE))
        );
        ApprovedDocumentsJPanelLayout.setVerticalGroup(
            ApprovedDocumentsJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ApprovedDocumentsJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblApprovedDocs)
                .addContainerGap(153, Short.MAX_VALUE))
        );

        RejectedDocsJPanel.setBackground(new java.awt.Color(255, 255, 255));
        RejectedDocsJPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblRejectedDocs.setText("Rejected Documents");

        javax.swing.GroupLayout RejectedDocsJPanelLayout = new javax.swing.GroupLayout(RejectedDocsJPanel);
        RejectedDocsJPanel.setLayout(RejectedDocsJPanelLayout);
        RejectedDocsJPanelLayout.setHorizontalGroup(
            RejectedDocsJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RejectedDocsJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblRejectedDocs)
                .addContainerGap(165, Short.MAX_VALUE))
        );
        RejectedDocsJPanelLayout.setVerticalGroup(
            RejectedDocsJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RejectedDocsJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblRejectedDocs)
                .addContainerGap(153, Short.MAX_VALUE))
        );

        TaxReturnsJPanel.setBackground(new java.awt.Color(255, 255, 255));
        TaxReturnsJPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblTaxReturns.setText("Tax Returns");

        javax.swing.GroupLayout TaxReturnsJPanelLayout = new javax.swing.GroupLayout(TaxReturnsJPanel);
        TaxReturnsJPanel.setLayout(TaxReturnsJPanelLayout);
        TaxReturnsJPanelLayout.setHorizontalGroup(
            TaxReturnsJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TaxReturnsJPanelLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(lblTaxReturns)
                .addContainerGap(163, Short.MAX_VALUE))
        );
        TaxReturnsJPanelLayout.setVerticalGroup(
            TaxReturnsJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TaxReturnsJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTaxReturns)
                .addContainerGap(153, Short.MAX_VALUE))
        );

        pendingDocsJPanel.setBackground(new java.awt.Color(255, 255, 255));
        pendingDocsJPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblPendingDocs.setText("Pending Documents");

        tblPendingDocs.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        tblPendingDocs.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Document ID", "Type", "Submitted", "Status", "Action"
            }
        ));
        jScrollPane2.setViewportView(tblPendingDocs);

        javax.swing.GroupLayout pendingDocsJPanelLayout = new javax.swing.GroupLayout(pendingDocsJPanel);
        pendingDocsJPanel.setLayout(pendingDocsJPanelLayout);
        pendingDocsJPanelLayout.setHorizontalGroup(
            pendingDocsJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pendingDocsJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pendingDocsJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPendingDocs)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 630, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        pendingDocsJPanelLayout.setVerticalGroup(
            pendingDocsJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pendingDocsJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblPendingDocs)
                .addGap(26, 26, 26)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(41, Short.MAX_VALUE))
        );

        AlertJPanel.setBackground(new java.awt.Color(255, 255, 255));
        AlertJPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblAlerts.setText("Notifications");

        javax.swing.GroupLayout AlertJPanelLayout = new javax.swing.GroupLayout(AlertJPanel);
        AlertJPanel.setLayout(AlertJPanelLayout);
        AlertJPanelLayout.setHorizontalGroup(
            AlertJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AlertJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblAlerts)
                .addContainerGap(479, Short.MAX_VALUE))
        );
        AlertJPanelLayout.setVerticalGroup(
            AlertJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AlertJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblAlerts)
                .addContainerGap(243, Short.MAX_VALUE))
        );

        recentActivitiesJPanel.setBackground(new java.awt.Color(255, 255, 255));
        recentActivitiesJPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblRecentActivities.setText("Recent Activities");

        tblRecentActivities.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        tblRecentActivities.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Activity", "Document", "Time", "User", "Status", "Actions"
            }
        ));
        jScrollPane1.setViewportView(tblRecentActivities);

        javax.swing.GroupLayout recentActivitiesJPanelLayout = new javax.swing.GroupLayout(recentActivitiesJPanel);
        recentActivitiesJPanel.setLayout(recentActivitiesJPanelLayout);
        recentActivitiesJPanelLayout.setHorizontalGroup(
            recentActivitiesJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(recentActivitiesJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(recentActivitiesJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblRecentActivities)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1208, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        recentActivitiesJPanelLayout.setVerticalGroup(
            recentActivitiesJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(recentActivitiesJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblRecentActivities)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(9, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout cusHPWorkspaceJPanelLayout = new javax.swing.GroupLayout(cusHPWorkspaceJPanel);
        cusHPWorkspaceJPanel.setLayout(cusHPWorkspaceJPanelLayout);
        cusHPWorkspaceJPanelLayout.setHorizontalGroup(
            cusHPWorkspaceJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cusHPWorkspaceJPanelLayout.createSequentialGroup()
                .addContainerGap(56, Short.MAX_VALUE)
                .addGroup(cusHPWorkspaceJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(cusHPWorkspaceJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(cusHPWorkspaceJPanelLayout.createSequentialGroup()
                            .addComponent(pendingDocsJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(AlertJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(cusHPWorkspaceJPanelLayout.createSequentialGroup()
                            .addComponent(pendingRevsJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(ApprovedDocumentsJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(RejectedDocsJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(TaxReturnsJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(cusHPWorkspaceJPanelLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(recentActivitiesJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        cusHPWorkspaceJPanelLayout.setVerticalGroup(
            cusHPWorkspaceJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cusHPWorkspaceJPanelLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(cusHPWorkspaceJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pendingRevsJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ApprovedDocumentsJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(RejectedDocsJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TaxReturnsJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(54, 54, 54)
                .addGroup(cusHPWorkspaceJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(AlertJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pendingDocsJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(recentActivitiesJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jSplitPane.setRightComponent(cusHPWorkspaceJPanel);

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

    private void btnSubmitDocsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitDocsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSubmitDocsActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AlertJPanel;
    private javax.swing.JPanel ApprovedDocumentsJPanel;
    private javax.swing.JPanel RejectedDocsJPanel;
    private javax.swing.JPanel TaxReturnsJPanel;
    private javax.swing.JButton btnDashBoard;
    private javax.swing.JButton btnDocReview;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnProfile;
    private javax.swing.JButton btnReturnTax;
    private javax.swing.JButton btnSubmitDocs;
    private javax.swing.JPanel cusHPControlJPanel;
    private javax.swing.JPanel cusHPWorkspaceJPanel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane;
    private javax.swing.JLabel lblAlerts;
    private javax.swing.JLabel lblApprovedDocs;
    private javax.swing.JLabel lblPendingDocs;
    private javax.swing.JLabel lblPendingReviews;
    private javax.swing.JLabel lblRecentActivities;
    private javax.swing.JLabel lblRejectedDocs;
    private javax.swing.JLabel lblTaxReturns;
    private javax.swing.JPanel pendingDocsJPanel;
    private javax.swing.JPanel pendingRevsJPanel;
    private javax.swing.JPanel recentActivitiesJPanel;
    private javax.swing.JTable tblPendingDocs;
    private javax.swing.JTable tblRecentActivities;
    // End of variables declaration//GEN-END:variables
}
