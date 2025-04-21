/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui.CustomsAgentRole;

import Business.ConfigureASystem;
import Business.Enterprise.Enterprise;
import Business.Logistics.CustomsDeclaration;
import Business.Organization.CustomsLiaisonOrganization;
import Business.Organization.Organization;
import Business.UserAccount.UserAccount;
import Business.WorkQueue.LogisticsWorkRequest;
import Business.WorkQueue.WorkRequest;
import java.awt.CardLayout;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.JOptionPane;


/**
 *
 * @author zhuchenyan
 */
public class CustomsLiaisonOfficeHP extends javax.swing.JPanel {

    private JPanel userProcessContainer;
    private UserAccount userAccount;
    private Enterprise enterprise;
    private CustomsLiaisonOrganization organization;

    /**
     * Creates new form CustomsLiaisionOfficeHP
     */
    public CustomsLiaisonOfficeHP() {

        initComponents();
        this.setPreferredSize(new java.awt.Dimension(1450, 800));

        // 设置默认值或展示静态内容，不依赖于 organization 或 userAccount
        lblPendingReviews.setText("Pending Reviews: 0");
        lblApprovedDocs.setText("Approved Documents: 0");
        lblRejectedDocs.setText("Rejected Documents: 0");
        lblTaxReturns.setText("Tax Returns: 0");
    }

    public CustomsLiaisonOfficeHP(JPanel userProcessContainer, UserAccount account,
            Enterprise enterprise, CustomsLiaisonOrganization organization) {

        initComponents();

        this.userProcessContainer = userProcessContainer;
        this.userAccount = account;
        this.enterprise = enterprise;
        this.organization = organization;

        // 如果传入的组织为空，尝试使用物流组织的数据
        if (organization == null) {
            System.out.println("Warning: Null organization passed to CustomsLiaisonOfficeHP");

            if (ConfigureASystem.logisticsOrg != null
                    && ConfigureASystem.logisticsOrg.getCustomsDeclarationDirectory() != null) {

                // 创建新的海关组织实例
                this.organization = new CustomsLiaisonOrganization();
                // 使用物流组织的报关数据
                this.organization.setCustomsDeclarationDirectory(
                        ConfigureASystem.logisticsOrg.getCustomsDeclarationDirectory());

                System.out.println("Using customs declarations from logistics organization");
            } else {
                System.out.println("Warning: No logistics organization data available");
//                lblAlerts.setText("Error: Customs organization data not available");
                return;
            }
        } else {
            this.organization = organization;
        }
        populateDashboard();

    }

    private void populateDashboard() {
        // Update statistics panels
        updateStatisticsPanels();

        // Update pending documents table
        populatePendingDocsTable();

        // Update recent activities table
        populateRecentActivitiesTable();

    }

    private void updateStatisticsPanels() {
        // Check if organization is null
        if (organization == null || organization.getCustomsDeclarationDirectory() == null) {
            System.out.println("Warning: Organization or customs declaration directory is null");
            // Set default values
            lblPendingReviews.setText("Pending Reviews: 0");
            lblApprovedDocs.setText("Approved Documents: 0");
            lblRejectedDocs.setText("Rejected Documents: 0");
            lblTaxReturns.setText("Tax Returns: 0");
            return;
        }

        // Get counts from the organization's customs declaration directory
        int pendingCount = organization.getCustomsDeclarationDirectory().getDeclarationCountByStatus("Pending");
        int approvedCount = organization.getCustomsDeclarationDirectory().getDeclarationCountByStatus("Approved");
        int rejectedCount = organization.getCustomsDeclarationDirectory().getDeclarationCountByStatus("Rejected");

        // Update the labels with counts
        lblPendingReviews.setText("Pending Reviews: " + pendingCount);
        lblApprovedDocs.setText("Approved Documents: " + approvedCount);
        lblRejectedDocs.setText("Rejected Documents: " + rejectedCount);

        // Count declarations requiring tax returns
        List<CustomsDeclaration> taxReturnDeclarations = organization.getCustomsDeclarationDirectory().getTaxReturnDeclarations();
        lblTaxReturns.setText("Tax Returns: " + taxReturnDeclarations.size());
    }

    private void populatePendingDocsTable() {
        // Check if organization is null
        DefaultTableModel model = (DefaultTableModel) tblPendingDocs.getModel();
        model.setRowCount(0);

        // 设置列标题
        String[] columns = {"Declaration ID", "Type", "Status", "Submission Date"};
        model.setColumnIdentifiers(columns);

        if (organization != null && organization.getWorkQueue() != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            for (WorkRequest request : organization.getWorkQueue().getWorkRequestList()) {
                if (request instanceof LogisticsWorkRequest) {
                    LogisticsWorkRequest customsRequest = (LogisticsWorkRequest) request;

                    // 只显示提交状态为"Submitted"的请求，但显示为"Pending"
                    if ("Submitted".equals(customsRequest.getStatus())) {
                        Object[] row = new Object[4];
                        row[0] = customsRequest.getDeclarationId();
                        row[1] = customsRequest.getDeclarationType() != null
                                ? customsRequest.getDeclarationType() : "Standard";
                        row[2] = "Pending"; // 显示为Pending
                        row[3] = dateFormat.format(customsRequest.getRequestDate());

                        model.addRow(row);
                    }
                }
            }
        }
    }

    private void populateRecentActivitiesTable() {
        DefaultTableModel model = (DefaultTableModel) tblRecentActivities.getModel();
        model.setRowCount(0);

        if (organization != null && organization.getWorkQueue() != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            for (WorkRequest request : organization.getWorkQueue().getWorkRequestList()) {
                if (request instanceof LogisticsWorkRequest) {
                    LogisticsWorkRequest customsRequest = (LogisticsWorkRequest) request;

                    // 只显示已处理的请求(状态为Approved、Rejected或Information Needed)
                    if ("Approved".equals(customsRequest.getStatus())
                            || "Rejected".equals(customsRequest.getStatus())
                            || "Information Needed".equals(customsRequest.getStatus())) {

                        Object[] row = new Object[4];
                        row[0] = customsRequest.getDeclarationId();
                        row[1] = customsRequest.getDeclarationType() != null
                                ? customsRequest.getDeclarationType() : "Standard";
                        row[2] = customsRequest.getStatus();
                        row[3] = customsRequest.getResolveDate() != null
                                ? dateFormat.format(customsRequest.getResolveDate())
                                : dateFormat.format(new Date());

                        model.addRow(row);
                    }
                }
            }
        }
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
        btnViewDetails = new javax.swing.JButton();
        recentActivitiesJPanel = new javax.swing.JPanel();
        lblRecentActivities = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblRecentActivities = new javax.swing.JTable();
        cusHPControlJPanel = new javax.swing.JPanel();
        btnDashBoard = new javax.swing.JButton();
        btnDocReview = new javax.swing.JButton();
        btnSubmitDocs = new javax.swing.JButton();
        btnReturnTax = new javax.swing.JButton();
        btnProfile = new javax.swing.JButton();
        btnLogout = new javax.swing.JButton();
        btnCusComplaint = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(1450, 800));
        setMinimumSize(new java.awt.Dimension(1450, 800));

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
                .addGap(177, 177, 177)
                .addComponent(lblPendingReviews)
                .addContainerGap(188, Short.MAX_VALUE))
        );
        pendingRevsJPanelLayout.setVerticalGroup(
            pendingRevsJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pendingRevsJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblPendingReviews)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        btnViewDetails.setText("View Details");
        btnViewDetails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewDetailsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pendingDocsJPanelLayout = new javax.swing.GroupLayout(pendingDocsJPanel);
        pendingDocsJPanel.setLayout(pendingDocsJPanelLayout);
        pendingDocsJPanelLayout.setHorizontalGroup(
            pendingDocsJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pendingDocsJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pendingDocsJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPendingDocs)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1267, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(37, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pendingDocsJPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnViewDetails)
                .addGap(64, 64, 64))
        );
        pendingDocsJPanelLayout.setVerticalGroup(
            pendingDocsJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pendingDocsJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblPendingDocs)
                .addGap(22, 22, 22)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnViewDetails)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1268, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(34, Short.MAX_VALUE))
        );
        recentActivitiesJPanelLayout.setVerticalGroup(
            recentActivitiesJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(recentActivitiesJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblRecentActivities)
                .addGap(20, 20, 20)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(9, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout cusHPWorkspaceJPanelLayout = new javax.swing.GroupLayout(cusHPWorkspaceJPanel);
        cusHPWorkspaceJPanel.setLayout(cusHPWorkspaceJPanelLayout);
        cusHPWorkspaceJPanelLayout.setHorizontalGroup(
            cusHPWorkspaceJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cusHPWorkspaceJPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(cusHPWorkspaceJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(cusHPWorkspaceJPanelLayout.createSequentialGroup()
                        .addComponent(pendingRevsJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(ApprovedDocumentsJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(RejectedDocsJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(TaxReturnsJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(cusHPWorkspaceJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(recentActivitiesJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(pendingDocsJPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        cusHPWorkspaceJPanelLayout.setVerticalGroup(
            cusHPWorkspaceJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cusHPWorkspaceJPanelLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(cusHPWorkspaceJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ApprovedDocumentsJPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(RejectedDocsJPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(TaxReturnsJPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pendingRevsJPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(54, 54, 54)
                .addComponent(pendingDocsJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(recentActivitiesJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jSplitPane.setRightComponent(cusHPWorkspaceJPanel);

        btnDashBoard.setText("DashBoard");
        btnDashBoard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDashBoardActionPerformed(evt);
            }
        });

        btnDocReview.setText("Document Review");
        btnDocReview.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDocReviewActionPerformed(evt);
            }
        });

        btnSubmitDocs.setText("Submit Documents");
        btnSubmitDocs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitDocsActionPerformed(evt);
            }
        });

        btnReturnTax.setText("Return Tax");
        btnReturnTax.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReturnTaxActionPerformed(evt);
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

        btnCusComplaint.setText("Customer Complaint");
        btnCusComplaint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCusComplaintActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout cusHPControlJPanelLayout = new javax.swing.GroupLayout(cusHPControlJPanel);
        cusHPControlJPanel.setLayout(cusHPControlJPanelLayout);
        cusHPControlJPanelLayout.setHorizontalGroup(
            cusHPControlJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cusHPControlJPanelLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(cusHPControlJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnDashBoard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLogout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCusComplaint, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnProfile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnReturnTax, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSubmitDocs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDocReview, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addGap(26, 26, 26)
                .addComponent(btnCusComplaint, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 195, Short.MAX_VALUE)
                .addComponent(btnLogout)
                .addGap(47, 47, 47))
        );

        jSplitPane.setLeftComponent(cusHPControlJPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnSubmitDocsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitDocsActionPerformed
        // TODO add your handling code here:
        // 检查 userProcessContainer 和 organization 是否为 null
        if (userProcessContainer == null) {
            System.out.println("Warning: userProcessContainer is null");
            JOptionPane.showMessageDialog(this,
                    "Error: Cannot navigate to Submit Documents. Application not properly initialized.",
                    "Navigation Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 创建并显示提交文档面板
        SubmitDoc submitPanel = new SubmitDoc(userProcessContainer, userAccount, organization);
        userProcessContainer.add("SubmitDocuments", submitPanel);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.show(userProcessContainer, "SubmitDocuments");
    }//GEN-LAST:event_btnSubmitDocsActionPerformed

    private void btnDashBoardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDashBoardActionPerformed
        // TODO add your handling code here:
        populateDashboard();
    }//GEN-LAST:event_btnDashBoardActionPerformed

    private void btnDocReviewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDocReviewActionPerformed
        // TODO add your handling code here:
        // Create and display document review panel
        // 检查 userProcessContainer 和 organization 是否为 null
        if (userProcessContainer == null) {
            System.out.println("Warning: userProcessContainer is null");
            JOptionPane.showMessageDialog(this,
                    "Error: Cannot navigate to Document Review. Application not properly initialized.",
                    "Navigation Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (organization == null) {
            System.out.println("Warning: organization is null");
            JOptionPane.showMessageDialog(this,
                    "Error: Cannot access document review. Customs data not available.",
                    "Organization Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        DocumentReview reviewPanel = new DocumentReview(userProcessContainer, userAccount, organization);
        userProcessContainer.add("DocumentReview", reviewPanel);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.show(userProcessContainer, "DocumentReview");
    }//GEN-LAST:event_btnDocReviewActionPerformed

    private void btnReturnTaxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReturnTaxActionPerformed
        // TODO add your handling code here:
        // 检查 userProcessContainer 和 organization 是否为 null
        if (userProcessContainer == null) {
            System.out.println("Warning: userProcessContainer is null");
            JOptionPane.showMessageDialog(this,
                    "Error: Cannot navigate to Return Tax. Application not properly initialized.",
                    "Navigation Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 创建并显示退税面板
        ReturnTax taxPanel = new ReturnTax(userProcessContainer, userAccount, organization);
        userProcessContainer.add("ReturnTax", taxPanel);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.show(userProcessContainer, "ReturnTax");
    }//GEN-LAST:event_btnReturnTaxActionPerformed

    private void btnProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProfileActionPerformed
        // TODO add your handling code here:
        // Create and display profile panel
        ManageOwnProfile profilePanel = new ManageOwnProfile(userProcessContainer, userAccount);
        userProcessContainer.add("Profile", profilePanel);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.show(userProcessContainer, "Profile");
    }//GEN-LAST:event_btnProfileActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        // TODO add your handling code here:
        userProcessContainer.remove(this);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.previous(userProcessContainer);

        JOptionPane.showMessageDialog(null, "Logged out successfully");

    }//GEN-LAST:event_btnLogoutActionPerformed

    private void btnCusComplaintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCusComplaintActionPerformed
        // TODO add your handling code here:

        CustomsComplaintContent complaintPanel = new CustomsComplaintContent(userProcessContainer);
        complaintPanel.setSize(1450, 800);
        userProcessContainer.add("CustomsComplaintContent", complaintPanel);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.show(userProcessContainer, "CustomsComplaintContent");


    }//GEN-LAST:event_btnCusComplaintActionPerformed

    private void btnViewDetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewDetailsActionPerformed
        // TODO add your handling code here:
        // 获取Pending Documents表格中选中的行
        int selectedRow = tblPendingDocs.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a document to view");
            return;
        }

        // 获取选中的申报单ID
        String declarationId = tblPendingDocs.getValueAt(selectedRow, 0).toString();

        // 跳转到DocumentReview页面
        DocumentReview documentReview = new DocumentReview(userProcessContainer, userAccount, organization);
        documentReview.setSelectedDeclarationId(declarationId); // 设置选中的申报单ID，便于在目标页面加载
        userProcessContainer.add("DocumentReview", documentReview);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.next(userProcessContainer);
    }//GEN-LAST:event_btnViewDetailsActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ApprovedDocumentsJPanel;
    private javax.swing.JPanel RejectedDocsJPanel;
    private javax.swing.JPanel TaxReturnsJPanel;
    private javax.swing.JButton btnCusComplaint;
    private javax.swing.JButton btnDashBoard;
    private javax.swing.JButton btnDocReview;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnProfile;
    private javax.swing.JButton btnReturnTax;
    private javax.swing.JButton btnSubmitDocs;
    private javax.swing.JButton btnViewDetails;
    private javax.swing.JPanel cusHPControlJPanel;
    private javax.swing.JPanel cusHPWorkspaceJPanel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane;
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

    /**
     * 刷新所有数据表格和面板 在状态变更后调用此方法以更新UI
     */
    public void refreshData() {
        System.out.println("Refreshing data in CustomsLiaisonOfficeHP");

        // 刷新待处理文档表格
        populatePendingDocumentsTable();

        // 刷新最近活动表格
        populateRecentActivitiesTable();

        // 刷新其他可能需要更新的UI组件
        refreshStatistics();

        // 重绘面板
        this.repaint();
        this.revalidate();
    }

    /**
     * 刷新待处理文档表格
     */
    private void populatePendingDocumentsTable() {
        DefaultTableModel model = (DefaultTableModel) tblPendingDocs.getModel();
        model.setRowCount(0);

        if (organization != null && organization.getWorkQueue() != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            for (WorkRequest request : organization.getWorkQueue().getWorkRequestList()) {
                if (request instanceof LogisticsWorkRequest) {
                    LogisticsWorkRequest customsRequest = (LogisticsWorkRequest) request;

                    // 只显示提交状态为"Submitted"的请求，显示为"Pending"
                    if ("Submitted".equals(customsRequest.getStatus())) {
                        Object[] row = new Object[4];
                        row[0] = customsRequest.getDeclarationId();
                        row[1] = customsRequest.getDeclarationType() != null
                                ? customsRequest.getDeclarationType() : "Standard";
                        row[2] = "Pending"; // 显示为Pending
                        row[3] = dateFormat.format(customsRequest.getRequestDate());

                        model.addRow(row);
                    }
                }
            }
        }
    }

    /**
     * 刷新统计数据
     */
    private void refreshStatistics() {
        // 计算各种状态的文档数量
        int pendingCount = 0;
        int approvedCount = 0;
        int rejectedCount = 0;
        int infoNeededCount = 0;

        if (organization != null && organization.getWorkQueue() != null) {
            for (WorkRequest request : organization.getWorkQueue().getWorkRequestList()) {
                if (request instanceof LogisticsWorkRequest) {
                    LogisticsWorkRequest customsRequest = (LogisticsWorkRequest) request;

                    if ("Submitted".equals(customsRequest.getStatus())) {
                        pendingCount++;
                    } else if ("Approved".equals(customsRequest.getStatus())) {
                        approvedCount++;
                    } else if ("Rejected".equals(customsRequest.getStatus())) {
                        rejectedCount++;
                    } else if ("Information Needed".equals(customsRequest.getStatus())) {
                        infoNeededCount++;
                    }
                }
            }
        }

        // 更新统计标签（如果有的话）
        // 如果您有显示统计数据的标签，请取消注释并修改以下代码
        /*
    lblPendingCount.setText(String.valueOf(pendingCount));
    lblApprovedCount.setText(String.valueOf(approvedCount));
    lblRejectedCount.setText(String.valueOf(rejectedCount));
    lblInfoNeededCount.setText(String.valueOf(infoNeededCount));
         */
        // 如果您使用图表，请在此更新图表数据
    }
}
