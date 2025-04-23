/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui.CustomsAgentRole;

import Business.ConfigureASystem;
import Business.DB4OUtil.DB4OUtil;
import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Enterprise.LogisticsGroupEnterprise;
import Business.Logistics.CustomsDeclaration;
import Business.Logistics.CustomsDeclarationDirectory;
import Business.Network.Network;
import Business.Organization.CustomsLiaisonOrganization;
import Business.Organization.LogisticsOrganization;
import Business.Organization.Organization;
import Business.UserAccount.UserAccount;
import Business.WorkQueue.LogisticsWorkRequest;
import Business.WorkQueue.WorkRequest;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author zhuchenyan
 */
public class DocumentReview extends javax.swing.JPanel {

    private JPanel userProcessContainer;
    private UserAccount userAccount;
    private CustomsLiaisonOrganization organization;
    private CustomsDeclaration selectedDeclaration;
    private String selectedDeclarationId;
    private JPanel parentPanel;

    /**
     * Creates new form DocumentReview
     */
    public DocumentReview(JPanel userProcessContainer, UserAccount account,
            CustomsLiaisonOrganization organization) {
        initComponents();

        try {
        this.userProcessContainer = userProcessContainer;
        this.userAccount = account;
                this.organization = organization;

        // Initialize parent panel reference
        if (userProcessContainer.getParent() instanceof JPanel) {
            this.parentPanel = (JPanel) userProcessContainer.getParent();
        }

        // Ensure organization reference is valid
        if (this.organization == null) {
            System.out.println("Warning: No customs organization provided");
            
            // Try to find customs organization in the system
            if (ConfigureASystem.logisticsOrg != null) {
                Enterprise logisticsEnterprise = ConfigureASystem.findEnterpriseForOrganization(ConfigureASystem.logisticsOrg);
                
                if (logisticsEnterprise != null) {
                    for (Organization org : logisticsEnterprise.getOrganizationDirectory().getOrganizationList()) {
                        if (org instanceof CustomsLiaisonOrganization) {
                            this.organization = (CustomsLiaisonOrganization) org;
                            System.out.println("Found existing CustomsLiaisonOrganization");
                            break;
                        }
                    }
                }
            }
            
            // If still null, create a new one
            if (this.organization == null) {
                System.out.println("Creating new CustomsLiaisonOrganization");
                this.organization = new CustomsLiaisonOrganization();
            }
        }

        // Ensure organizations share the same customs declaration directory
        if (ConfigureASystem.logisticsOrg != null && 
            ConfigureASystem.logisticsOrg.getCustomsDeclarationDirectory() != null) {
            System.out.println("Using customs declarations from global logistics organization");
            this.organization.setCustomsDeclarationDirectory(
                ConfigureASystem.logisticsOrg.getCustomsDeclarationDirectory());
        }

        // Display debug info
        if (this.organization.getCustomsDeclarationDirectory() != null) {
            System.out.println("DocumentReview initialized with organization: " + this.organization);
            System.out.println("Number of declarations: " + 
                this.organization.getCustomsDeclarationDirectory().getCustomsDeclarationList().size());
        }

        // Count work requests for debugging
        if (this.organization.getWorkQueue() != null) {
            int workRequestCount = 0;
            for (WorkRequest req : this.organization.getWorkQueue().getWorkRequestList()) {
                if (req instanceof LogisticsWorkRequest) {
                    workRequestCount++;
                }
            }
            System.out.println("Current work queue has " + workRequestCount + " logistics work requests");
        }

        // Initialize UI components
        setupTable();
        populateTable();
        clearFields();
        
    } catch (Exception e) {
        System.out.println("Error initializing DocumentReview: " + e.getMessage());
        e.printStackTrace();
    }
    }

    public void setParentPanel(JPanel panel) {
        this.parentPanel = panel;
    }

    private void setupTable() {
        DefaultTableModel model = (DefaultTableModel) tblList.getModel();
        model.setColumnIdentifiers(new Object[]{"Document ID", "Type", "Submission Date", "Status"});

        // Set up table selection listener
        tblList.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = tblList.getSelectedRow();
                if (selectedRow >= 0) {
                    String docId = tblList.getValueAt(selectedRow, 0).toString();
                    displayDeclarationDetails(docId);
                }
            }
        });
    }

    private void initializeTable() {
        DefaultTableModel model = (DefaultTableModel) tblList.getModel();
        model.setRowCount(0);

        // 设置列标题
        String[] columns = {"Declaration ID", "Type", "Status", "Submission Date"};
        model.setColumnIdentifiers(columns);
    }

    private void populateTable() {
        DefaultTableModel model = (DefaultTableModel) tblList.getModel();
        model.setRowCount(0);

        // 设置列标题
        String[] columns = {"Declaration ID", "Type", "Status", "Submission Date"};
        model.setColumnIdentifiers(columns);

        // 获取当前选择的过滤选项
        String filterStatus = comBoFilter.getSelectedItem().toString();

        // 直接从 CustomsDeclarationDirectory 获取数据
        if (organization != null && organization.getCustomsDeclarationDirectory() != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            int matchCount = 0;

            for (CustomsDeclaration declaration : organization.getCustomsDeclarationDirectory().getCustomsDeclarationList()) {
                // 检查是否匹配过滤条件
                String status = declaration.getStatus() != null ? declaration.getStatus() : "Pending";

                // 跳过状态为Draft的条目
                if ("Draft".equals(status)) {
                    continue;
                }

                // 如果筛选条件是"All"或者状态匹配当前筛选条件
                if ("All".equals(filterStatus) || filterStatus.equals(status)) {
                Object[] row = new Object[4];
                row[0] = declaration.getDeclarationId();
                row[1] = declaration.getDeclarationType() != null 
                       ? declaration.getDeclarationType() : "Standard";
                    row[2] = status;
                row[3] = declaration.getSubmissionDate() != null 
                       ? dateFormat.format(declaration.getSubmissionDate()) 
                       : dateFormat.format(declaration.getDeclarationDate());

                model.addRow(row);
                    matchCount++;
                }
            }
            // 如果没有匹配记录，显示提示信息
            if (matchCount == 0) {
                Object[] row = {"No declarations with status: " + filterStatus, "", "", ""};
                model.addRow(row);
            }
        } else {
        // 如果表格为空，添加一条信息
            Object[] row = {"No declarations found", "", "", ""};
            model.addRow(row);
        }
    
    }

    private void displayDeclarationDetails(String declarationId) {
        selectedDeclaration = organization.getCustomsDeclarationDirectory().findDeclarationById(declarationId);

        if (selectedDeclaration != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            txtDocID.setText(selectedDeclaration.getDeclarationId());
            txtDocName.setText("Customs Declaration - " + selectedDeclaration.getDeclarationId());
            txtDocType.setText("Customs Declaration");
            txtSubmissionDate.setText(dateFormat.format(selectedDeclaration.getSubmissionDate()));
            txtSubBy.setText(selectedDeclaration.getConsignor());

            // Populate document preview
            StringBuilder preview = new StringBuilder();
            preview.append("Declaration Details:\n\n");
            preview.append("Consignor: ").append(selectedDeclaration.getConsignor()).append("\n");
            preview.append("Consignee: ").append(selectedDeclaration.getConsignee()).append("\n");
            preview.append("Origin Country: ").append(selectedDeclaration.getCountryOfOrigin()).append("\n");
            preview.append("Destination Country: ").append(selectedDeclaration.getDestinationCountry()).append("\n");
            preview.append("\nDeclared Items:\n");

            if (selectedDeclaration.getItems() != null) {
                for (CustomsDeclaration.CustomsLineItem item : selectedDeclaration.getItems()) {
                    preview.append("\n- ").append(item.getDescription())
                            .append("\n  HS Code: ").append(item.getHsCode())
                            .append("\n  Quantity: ").append(item.getQuantity()).append(" ").append(item.getUnit())
                            .append("\n  Value: $").append(String.format("%.2f", item.getTotalValue()));
                }
            }

            txtDocPreview.setText(preview.toString());

            // Enable/disable buttons based on status
            boolean isPending = "Pending".equals(selectedDeclaration.getStatus())
                    || "Submitted".equals(selectedDeclaration.getStatus());
            btnApprove.setEnabled(isPending);
            btnReject.setEnabled(isPending);
            btnRequestInfo.setEnabled(isPending);
        }
    }

    private void clearFields() {
        txtDocID.setText("");
        txtDocName.setText("");
        txtDocType.setText("");
        txtSubmissionDate.setText("");
        txtSubBy.setText("");
        txtDocPreview.setText("");
        txtReviewNotes.setText("");

        btnApprove.setEnabled(false);
        btnReject.setEnabled(false);
        btnRequestInfo.setEnabled(false);
    }

    public void setSelectedDeclarationId(String declarationId) {
        this.selectedDeclarationId = declarationId;

        // 如果ID不为空，立即加载对应的申报单详情
        if (declarationId != null && !declarationId.isEmpty()) {
            loadDeclarationById(declarationId);
        }
    }

    // 根据ID加载申报单
    private void loadDeclarationById(String declarationId) {
        // 首先确保表格已加载所有数据
        populateTable();

        // 然后在表格中查找并选择对应ID的行
        DefaultTableModel model = (DefaultTableModel) tblList.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            String id = model.getValueAt(i, 0).toString();
            if (declarationId.equals(id)) {
                tblList.setRowSelectionInterval(i, i);

                // 自动加载详情
                String docId = tblList.getValueAt(i, 0).toString();
                loadDeclarationDetails(docId);
                break;
            }
        }
    }

// 加载申报单详情
    private void loadDeclarationDetails(String declarationId) {
        for (WorkRequest request : organization.getWorkQueue().getWorkRequestList()) {
            if (request instanceof LogisticsWorkRequest) {
                LogisticsWorkRequest customsRequest = (LogisticsWorkRequest) request;
                if (customsRequest.getDeclarationId().equals(declarationId)) {
                    displayRequestDetails(customsRequest);
                    break;
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

        btnBack = new javax.swing.JButton();
        lblTitle = new javax.swing.JLabel();
        lblSearchID = new javax.swing.JLabel();
        txtSearchBox = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        DocQueueJPanel = new javax.swing.JPanel();
        lblListTitle = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblList = new javax.swing.JTable();
        comBoFilter = new javax.swing.JComboBox<>();
        declarDetailsJPanel = new javax.swing.JPanel();
        lblReviewDoc = new javax.swing.JLabel();
        btnApprove = new javax.swing.JButton();
        btnReject = new javax.swing.JButton();
        docContenJPanel = new javax.swing.JPanel();
        lblDocContent = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtDocPreview = new javax.swing.JTextArea();
        docInfoJPanel = new javax.swing.JPanel();
        lblDocInfo = new javax.swing.JLabel();
        lblDocID = new javax.swing.JLabel();
        txtDocID = new javax.swing.JTextField();
        lblSubmissionDate = new javax.swing.JLabel();
        txtSubmissionDate = new javax.swing.JTextField();
        lblDocType = new javax.swing.JLabel();
        txtDocType = new javax.swing.JTextField();
        lblSubBy = new javax.swing.JLabel();
        txtSubBy = new javax.swing.JTextField();
        txtDocName = new javax.swing.JTextField();
        btnRequestInfo = new javax.swing.JButton();
        reviewNotesJPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtReviewNotes = new javax.swing.JTextField();

        setMaximumSize(new java.awt.Dimension(1450, 800));
        setMinimumSize(new java.awt.Dimension(1450, 800));

        btnBack.setText("<< Back");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        lblTitle.setText("Document Review");

        lblSearchID.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSearchID.setText("Search by ID");

        btnSearch.setText("Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        DocQueueJPanel.setBackground(new java.awt.Color(255, 255, 255));
        DocQueueJPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblListTitle.setText("Document Queue");

        tblList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "", "", ""
            }
        ));
        jScrollPane2.setViewportView(tblList);

        comBoFilter.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "Pending", "Submitted", "Approved", "Rejected", "Information Needed" }));
        comBoFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comBoFilterActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout DocQueueJPanelLayout = new javax.swing.GroupLayout(DocQueueJPanel);
        DocQueueJPanel.setLayout(DocQueueJPanelLayout);
        DocQueueJPanelLayout.setHorizontalGroup(
            DocQueueJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DocQueueJPanelLayout.createSequentialGroup()
                .addGroup(DocQueueJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DocQueueJPanelLayout.createSequentialGroup()
                        .addComponent(lblListTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(81, 81, 81)
                        .addComponent(comBoFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(DocQueueJPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 419, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        DocQueueJPanelLayout.setVerticalGroup(
            DocQueueJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DocQueueJPanelLayout.createSequentialGroup()
                .addGroup(DocQueueJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblListTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comBoFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 625, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 162, Short.MAX_VALUE))
        );

        declarDetailsJPanel.setBackground(new java.awt.Color(255, 255, 255));
        declarDetailsJPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblReviewDoc.setText("Review Document:");

        btnApprove.setBackground(new java.awt.Color(102, 204, 0));
        btnApprove.setForeground(new java.awt.Color(255, 255, 255));
        btnApprove.setText("Approve");
        btnApprove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnApproveActionPerformed(evt);
            }
        });

        btnReject.setBackground(new java.awt.Color(255, 0, 0));
        btnReject.setForeground(new java.awt.Color(255, 255, 255));
        btnReject.setText("Reject");
        btnReject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRejectActionPerformed(evt);
            }
        });

        docContenJPanel.setBackground(new java.awt.Color(255, 255, 255));
        docContenJPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblDocContent.setText("Document Content");

        txtDocPreview.setColumns(20);
        txtDocPreview.setRows(5);
        jScrollPane3.setViewportView(txtDocPreview);

        javax.swing.GroupLayout docContenJPanelLayout = new javax.swing.GroupLayout(docContenJPanel);
        docContenJPanel.setLayout(docContenJPanelLayout);
        docContenJPanelLayout.setHorizontalGroup(
            docContenJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(docContenJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(docContenJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(docContenJPanelLayout.createSequentialGroup()
                .addComponent(lblDocContent)
                        .addContainerGap(828, Short.MAX_VALUE))
                    .addGroup(docContenJPanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane3)
                        .addGap(18, 18, 18))))
        );
        docContenJPanelLayout.setVerticalGroup(
            docContenJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(docContenJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblDocContent)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        docInfoJPanel.setBackground(new java.awt.Color(255, 255, 255));
        docInfoJPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblDocInfo.setText("Document Information");

        lblDocID.setText("Document ID:");

        txtDocID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDocIDActionPerformed(evt);
            }
        });

        lblSubmissionDate.setText("Submission Date:");

        txtSubmissionDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSubmissionDateActionPerformed(evt);
            }
        });

        lblDocType.setText("Document Type:");

        lblSubBy.setText("Submitted By:");

        javax.swing.GroupLayout docInfoJPanelLayout = new javax.swing.GroupLayout(docInfoJPanel);
        docInfoJPanel.setLayout(docInfoJPanelLayout);
        docInfoJPanelLayout.setHorizontalGroup(
            docInfoJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(docInfoJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblDocInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(docInfoJPanelLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(docInfoJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(docInfoJPanelLayout.createSequentialGroup()
                        .addComponent(lblDocID, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(txtDocID, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(docInfoJPanelLayout.createSequentialGroup()
                        .addComponent(lblSubmissionDate, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(txtSubmissionDate, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addGroup(docInfoJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(docInfoJPanelLayout.createSequentialGroup()
                        .addComponent(lblDocType, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(txtDocType, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(docInfoJPanelLayout.createSequentialGroup()
                        .addComponent(lblSubBy, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(txtSubBy, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        docInfoJPanelLayout.setVerticalGroup(
            docInfoJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(docInfoJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblDocInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(docInfoJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDocID)
                    .addComponent(txtDocID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDocType)
                    .addComponent(txtDocType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(docInfoJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSubmissionDate)
                    .addComponent(txtSubmissionDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSubBy)
                    .addComponent(txtSubBy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(35, Short.MAX_VALUE))
        );

        btnRequestInfo.setBackground(new java.awt.Color(255, 153, 0));
        btnRequestInfo.setForeground(new java.awt.Color(255, 255, 255));
        btnRequestInfo.setText("Request Info");
        btnRequestInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRequestInfoActionPerformed(evt);
            }
        });

        reviewNotesJPanel.setBackground(new java.awt.Color(255, 255, 255));
        reviewNotesJPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("Review Notes");

        javax.swing.GroupLayout reviewNotesJPanelLayout = new javax.swing.GroupLayout(reviewNotesJPanel);
        reviewNotesJPanel.setLayout(reviewNotesJPanelLayout);
        reviewNotesJPanelLayout.setHorizontalGroup(
            reviewNotesJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(reviewNotesJPanelLayout.createSequentialGroup()
                        .addContainerGap()
                .addGroup(reviewNotesJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(txtReviewNotes, javax.swing.GroupLayout.PREFERRED_SIZE, 879, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        reviewNotesJPanelLayout.setVerticalGroup(
            reviewNotesJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(reviewNotesJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtReviewNotes, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout declarDetailsJPanelLayout = new javax.swing.GroupLayout(declarDetailsJPanel);
        declarDetailsJPanel.setLayout(declarDetailsJPanelLayout);
        declarDetailsJPanelLayout.setHorizontalGroup(
            declarDetailsJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(declarDetailsJPanelLayout.createSequentialGroup()
                .addGroup(declarDetailsJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(declarDetailsJPanelLayout.createSequentialGroup()
                        .addComponent(lblReviewDoc)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDocName, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnApprove, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnReject, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnRequestInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(declarDetailsJPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(declarDetailsJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(docInfoJPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(docContenJPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(reviewNotesJPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        declarDetailsJPanelLayout.setVerticalGroup(
            declarDetailsJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(declarDetailsJPanelLayout.createSequentialGroup()
                .addGroup(declarDetailsJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblReviewDoc, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnApprove, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReject, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDocName, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRequestInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addComponent(docInfoJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(docContenJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(reviewNotesJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(DocQueueJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(declarDetailsJPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 593, Short.MAX_VALUE)
                                .addComponent(lblSearchID, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtSearchBox, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(24, 24, 24))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(btnBack)
                .addGap(18, 18, 18)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtSearchBox)
                    .addComponent(lblSearchID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearch))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(declarDetailsJPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(DocQueueJPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        userProcessContainer.remove(this);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.previous(userProcessContainer);
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        String searchId = txtSearchBox.getText().trim();
        if (!searchId.isEmpty()) {
            CustomsDeclaration declaration = organization.getCustomsDeclarationDirectory()
                    .findDeclarationById(searchId);
            if (declaration != null) {
                displayDeclarationDetails(searchId);
            } else {
                JOptionPane.showMessageDialog(this,
                        "No declaration found with ID: " + searchId,
                        "Search Result",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnApproveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnApproveActionPerformed
        // TODO add your handling code here:
        updateRequestStatus("Approved");
    }//GEN-LAST:event_btnApproveActionPerformed

    private void btnRejectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRejectActionPerformed
        // TODO add your handling code here:
        updateRequestStatus("Rejected");
    }//GEN-LAST:event_btnRejectActionPerformed

    private void txtDocIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDocIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDocIDActionPerformed

    private void comBoFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comBoFilterActionPerformed
        // TODO add your handling code here:
        String selectedFilter = comBoFilter.getSelectedItem().toString();
        System.out.println("Filter selected: " + selectedFilter);
        populateTable();
    }//GEN-LAST:event_comBoFilterActionPerformed

    private void btnRequestInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRequestInfoActionPerformed
        // TODO add your handling code here:
        updateRequestStatus("Information Needed");
    }//GEN-LAST:event_btnRequestInfoActionPerformed

    private void txtSubmissionDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSubmissionDateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSubmissionDateActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel DocQueueJPanel;
    private javax.swing.JButton btnApprove;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnReject;
    private javax.swing.JButton btnRequestInfo;
    private javax.swing.JButton btnSearch;
    private javax.swing.JComboBox<String> comBoFilter;
    private javax.swing.JPanel declarDetailsJPanel;
    private javax.swing.JPanel docContenJPanel;
    private javax.swing.JPanel docInfoJPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblDocContent;
    private javax.swing.JLabel lblDocID;
    private javax.swing.JLabel lblDocInfo;
    private javax.swing.JLabel lblDocType;
    private javax.swing.JLabel lblListTitle;
    private javax.swing.JLabel lblReviewDoc;
    private javax.swing.JLabel lblSearchID;
    private javax.swing.JLabel lblSubBy;
    private javax.swing.JLabel lblSubmissionDate;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel reviewNotesJPanel;
    private javax.swing.JTable tblList;
    private javax.swing.JTextField txtDocID;
    private javax.swing.JTextField txtDocName;
    private javax.swing.JTextArea txtDocPreview;
    private javax.swing.JTextField txtDocType;
    private javax.swing.JTextField txtReviewNotes;
    private javax.swing.JTextField txtSearchBox;
    private javax.swing.JTextField txtSubBy;
    private javax.swing.JTextField txtSubmissionDate;
    // End of variables declaration//GEN-END:variables

    public void refreshData() {
        System.out.println("Refreshing document review data");
        populateTable();
        clearFields();
    }

    private void tblListMouseClicked(java.awt.event.MouseEvent evt) {
        int selectedRow = tblList.getSelectedRow();
        if (selectedRow < 0) {
            return;
        }

        String declarationId = tblList.getValueAt(selectedRow, 0).toString();
        if (declarationId.equals("No declarations found")) {
            return;
        }

        LogisticsWorkRequest selectedRequest = null;

        // 查找选中的请求
        for (WorkRequest request : organization.getWorkQueue().getWorkRequestList()) {
            if (request instanceof LogisticsWorkRequest) {
                LogisticsWorkRequest customsRequest = (LogisticsWorkRequest) request;
                if (customsRequest.getDeclarationId().equals(declarationId)) {
                    selectedRequest = customsRequest;
                    break;
                }
            }
        }

        if (selectedRequest != null) {
            // 显示请求信息
            displayRequestDetails(selectedRequest);
        }
    }

    private void displayRequestDetails(LogisticsWorkRequest request) {
        // Display basic info in docInfoJPanel
        txtDocID.setText(request.getDeclarationId());
        txtDocType.setText(request.getDeclarationType() != null
                ? request.getDeclarationType() : "Standard");
        txtDocName.setText("Customs Declaration - " + request.getDeclarationId());

        // Format date properly
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        txtSubmissionDate.setText(dateFormat.format(request.getRequestDate()));

        // Show sender information
        txtSubBy.setText(request.getSender() != null
                ? request.getSender().getUsername() : "Unknown");

        // Enable/disable buttons based on status
        boolean isPending = "Pending".equals(request.getStatus())
                || "Submitted".equals(request.getStatus());
        btnApprove.setEnabled(isPending);
        btnReject.setEnabled(isPending);
        btnRequestInfo.setEnabled(isPending);

        // Display detailed content in docContenJPanel
        StringBuilder content = new StringBuilder();
        content.append("Declaration ID: ").append(request.getDeclarationId()).append("\n");
        content.append("Shipment ID: ").append(request.getShipmentId()).append("\n\n");

        content.append("Consignor: ").append(request.getConsignor()).append("\n");
        content.append("Consignee: ").append(request.getConsignee()).append("\n");
        content.append("Country of Origin: ").append(request.getCountryOfOrigin()).append("\n");
        content.append("Destination Country: ").append(request.getDestinationCountry()).append("\n\n");

        // Current status and dates
        content.append("Current Status: ").append(request.getStatus()).append("\n");
        content.append("Submission Date: ").append(dateFormat.format(request.getRequestDate())).append("\n");

        // Show processing date if available
        if (request.getResolveDate() != null) {
            content.append("Processing Date: ").append(dateFormat.format(request.getResolveDate())).append("\n");
        }

        if (request.getMessage() != null && !request.getMessage().isEmpty()) {
            content.append("\nReview Notes: ").append(request.getMessage()).append("\n");
        }

        if (request.getNotes() != null && !request.getNotes().isEmpty()) {
            content.append("\nDeclaration Notes: ").append(request.getNotes()).append("\n");
        }

        txtDocPreview.setText(content.toString());
    }

    private void updateRequestStatus(String newStatus) {
        try {
        int selectedRow = tblList.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a declaration first");
            return;
        }

        String declarationId = tblList.getValueAt(selectedRow, 0).toString();
        if (declarationId == null || declarationId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Invalid declaration ID");
            return;
        }

        System.out.println("Attempting to update declaration ID: " + declarationId + " to status: " + newStatus);

        // First look in the current organization's declaration directory
        CustomsDeclaration declaration = null;
        if (organization != null && organization.getCustomsDeclarationDirectory() != null) {
            declaration = organization.getCustomsDeclarationDirectory().findDeclarationById(declarationId);
        }

        // If not found, try in global logistics organization
        if (declaration == null && ConfigureASystem.logisticsOrg != null && 
            ConfigureASystem.logisticsOrg.getCustomsDeclarationDirectory() != null) {
            declaration = ConfigureASystem.logisticsOrg.getCustomsDeclarationDirectory()
                .findDeclarationById(declarationId);
            
            // If found in logistics org but not in customs org, establish the link
            if (declaration != null && organization != null) {
                // Ensure customs org uses the same declaration directory
                organization.setCustomsDeclarationDirectory(
                    ConfigureASystem.logisticsOrg.getCustomsDeclarationDirectory());
                System.out.println("Connected customs organization to logistics declaration directory");
            }
        }

        if (declaration == null) {
            System.out.println("Declaration not found: " + declarationId);
            JOptionPane.showMessageDialog(this, "Could not find the selected declaration");
            return;
        }

        // Update declaration status
        declaration.setStatus(newStatus);
        declaration.setProcessingDate(new Date());
        
        // Add review notes if provided
        if (txtReviewNotes.getText() != null && !txtReviewNotes.getText().isEmpty()) {
            String existingNotes = declaration.getNotes();
            String reviewNotes = txtReviewNotes.getText();
            
            if (existingNotes != null && !existingNotes.isEmpty()) {
                declaration.setNotes(existingNotes + "\n\nReview notes: " + reviewNotes);
            } else {
                declaration.setNotes("Review notes: " + reviewNotes);
            }
        }

        // Update work request in queue if found
        boolean workRequestUpdated = false;
        for (WorkRequest request : organization.getWorkQueue().getWorkRequestList()) {
            if (request instanceof LogisticsWorkRequest) {
                LogisticsWorkRequest customsRequest = (LogisticsWorkRequest) request;
                if (customsRequest.getDeclarationId().equals(declarationId)) {
                    customsRequest.setStatus(newStatus);
                    customsRequest.setResolveDate(new Date());

                    if (txtReviewNotes.getText() != null && !txtReviewNotes.getText().isEmpty()) {
                        customsRequest.setMessage(txtReviewNotes.getText());
                    }

                    workRequestUpdated = true;
                    break;
                }
            }
        }

        // If no work request was found, create one to maintain consistency
        if (!workRequestUpdated) {
            System.out.println("No work request found for declaration " + declarationId + ", creating one");
            LogisticsWorkRequest newRequest = new LogisticsWorkRequest();
            newRequest.setDeclarationId(declarationId);
            newRequest.setShipmentId(declaration.getShipmentId());
            newRequest.setStatus(newStatus);
            newRequest.setConsignor(declaration.getConsignor());
            newRequest.setConsignee(declaration.getConsignee());
            newRequest.setCountryOfOrigin(declaration.getCountryOfOrigin());
            newRequest.setDestinationCountry(declaration.getDestinationCountry());
            newRequest.setNotes(declaration.getNotes());
            newRequest.setRequestDate(declaration.getDeclarationDate());
            newRequest.setResolveDate(new Date());
            
            if (txtReviewNotes.getText() != null && !txtReviewNotes.getText().isEmpty()) {
                newRequest.setMessage(txtReviewNotes.getText());
            }
            
            organization.getWorkQueue().getWorkRequestList().add(newRequest);
            workRequestUpdated = true;
        }
        
        System.out.println("Declaration status updated: " + newStatus + 
                ", Work request updated: " + (workRequestUpdated ? "Yes" : "No"));

        // Save changes to database
        try {
            DB4OUtil.getInstance().storeSystem(EcoSystem.getInstance());
            System.out.println("Changes saved to database");
        } catch (Exception e) {
            System.out.println("Error saving changes: " + e.getMessage());
            e.printStackTrace();
        }
        
            JOptionPane.showMessageDialog(this, "Declaration status updated to: " + newStatus);

        // Refresh UI
            populateTable();
            clearFields();

                } catch (Exception e) {
        System.out.println("Error in updateRequestStatus: " + e.getMessage());
                    e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error updating status: " + e.getMessage());
        }
    }

    private void updateOriginalDeclaration(String declarationId, String newStatus) {
        // 查找并更新物流组织中的原始报关单
        try {
            // Find the logistics organization
            LogisticsOrganization logisticsOrg = findLogisticsOrganization();

            if (logisticsOrg != null && logisticsOrg.getCustomsDeclarationDirectory() != null) {
                // Find the original declaration
                CustomsDeclaration declaration
                        = logisticsOrg.getCustomsDeclarationDirectory().findDeclarationById(declarationId);

                if (declaration != null) {
                    // Update the status
                    declaration.setStatus(newStatus);
                    declaration.setProcessingDate(new Date());

                    // Update notes if we have them
                    if (txtReviewNotes.getText() != null && !txtReviewNotes.getText().isEmpty()) {
                        String existingNotes = declaration.getNotes();
                        String reviewNotes = txtReviewNotes.getText();

                        // Append review notes to existing notes
                        if (existingNotes != null && !existingNotes.isEmpty()) {
                            declaration.setNotes(existingNotes + "\n\nReview notes: " + reviewNotes);
                        } else {
                            declaration.setNotes("Review notes: " + reviewNotes);
                        }
                    }

                    System.out.println("Updated original declaration status to: " + newStatus);
                } else {
                    System.out.println("WARNING: Original declaration not found with ID: " + declarationId);
                }
            } else {
                System.out.println("WARNING: Logistics organization or its declaration directory is null");
            }
        } catch (Exception e) {
            System.out.println("Error updating original declaration: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private LogisticsOrganization findLogisticsOrganization() {
        if (ConfigureASystem.logisticsOrg != null) {
            return ConfigureASystem.logisticsOrg;
        }

        // Fallback: search through the system
        EcoSystem system = EcoSystem.getInstance();
        if (system != null && system.getNetworkList() != null) {
            for (Network network : system.getNetworkList()) {
                if (network.getEnterpriseDirectory() != null) {
                    for (Enterprise enterprise : network.getEnterpriseDirectory().getEnterpriseList()) {
                        if (enterprise instanceof LogisticsGroupEnterprise
                                && enterprise.getOrganizationDirectory() != null) {
                            for (Organization org : enterprise.getOrganizationDirectory().getOrganizationList()) {
                                if (org instanceof LogisticsOrganization) {
                                    return (LogisticsOrganization) org;
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Apply consistent UI theme to all components
     */
    private void setupTheme() {
        // Set panel background color
        this.setBackground(new Color(240, 245, 255));

        // Style all panels with the same background color
        DocQueueJPanel.setBackground(new Color(240, 245, 255));
        declarDetailsJPanel.setBackground(new Color(240, 245, 255));
        docInfoJPanel.setBackground(new Color(240, 245, 255));
        docContenJPanel.setBackground(new Color(240, 245, 255));
        reviewNotesJPanel.setBackground(new Color(240, 245, 255));

        // Style all buttons
        styleButton(btnBack);
        styleButton(btnSearch);
        styleButton(btnApprove);
        styleButton(btnReject);
        styleButton(btnRequestInfo);

        // Style all labels
        styleTitleLabel(lblTitle);
        styleTitleLabel(lblReviewDoc);
        styleTitleLabel(lblListTitle);

        styleLabel(lblDocInfo);
        styleLabel(lblDocContent);
        styleLabel(jLabel1); // Review Notes label
        styleLabel(lblSearchID);

        styleLabel(lblDocID);
        styleLabel(lblSubmissionDate);
        styleLabel(lblDocType);
        styleLabel(lblSubBy);

        // Style all text fields
        styleTextField(txtSearchBox);
        styleTextField(txtDocID);
        styleTextField(txtDocName);
        styleTextField(txtDocType);
        styleTextField(txtSubmissionDate);
        styleTextField(txtSubBy);
        styleTextField(txtReviewNotes);

        // Style tables
        styleTable(tblList);

        // Style textareas
        txtDocPreview.setBackground(new Color(245, 245, 250));
        txtDocPreview.setForeground(new Color(13, 25, 51));
        txtDocPreview.setFont(new java.awt.Font("Helvetica Neue", java.awt.Font.PLAIN, 14));
        txtDocPreview.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(90, 141, 224), 1));
    }

    /**
     * Apply consistent styling to a button
     *
     * @param button Button to style
     */
    private void styleButton(JButton button) {
        // Check if it's a special button (like Delete or Submit)
        if (button == btnReject) {
            // Delete/Reject button keeps its red background
            button.setBackground(new Color(255, 0, 0));
        } else if (button == btnApprove) {
            // Approve/Submit buttons keep their green background
            button.setBackground(new Color(102, 204, 0));
        } else if (button == btnRequestInfo) {
            // Request Info button keeps its orange background
            button.setBackground(new Color(255, 153, 0));
        } else {
            // Standard buttons get the blue theme
            button.setBackground(new Color(26, 79, 156)); // Medium blue
        }

        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);

        // Add a subtle border with rounded corners
        button.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(new Color(13, 60, 130), 1),
                javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        button.setFont(new java.awt.Font("Helvetica Neue", java.awt.Font.BOLD, 14));

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (button == btnReject) {
                    button.setBackground(new Color(255, 51, 51)); // Lighter red on hover
                } else if (button == btnApprove) {
                    button.setBackground(new Color(115, 230, 0)); // Lighter green on hover
                } else if (button == btnRequestInfo) {
                    button.setBackground(new Color(255, 178, 102)); // Lighter orange on hover
                } else {
                    button.setBackground(new Color(35, 100, 190)); // Lighter blue on hover
                }
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (button == btnReject) {
                    button.setBackground(new Color(255, 0, 0)); // Back to normal red
                } else if (button == btnApprove) {
                    button.setBackground(new Color(102, 204, 0)); // Back to normal green
                } else if (button == btnRequestInfo) {
                    button.setBackground(new Color(255, 153, 0)); // Back to normal orange
                } else {
                    button.setBackground(new Color(26, 79, 156)); // Back to normal blue
                }
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                if (button == btnReject) {
                    button.setBackground(new Color(204, 0, 0)); // Darker when pressed
                } else if (button == btnApprove) {
                    button.setBackground(new Color(85, 170, 0)); // Darker green when pressed
                } else if (button == btnRequestInfo) {
                    button.setBackground(new Color(204, 102, 0)); // Darker orange when pressed
                } else {
                    button.setBackground(new Color(13, 60, 130)); // Darker blue when pressed
                }
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                // Return to hover state
                mouseEntered(evt);
            }
        });
    }

    /**
     * Apply consistent styling to a text field
     *
     * @param textField TextField to style
     */
    private void styleTextField(JTextField textField) {
        textField.setBackground(new Color(245, 245, 250)); // Light gray-white background
        textField.setForeground(new Color(13, 25, 51));    // Dark blue text
        textField.setCaretColor(new Color(26, 79, 156));   // Medium blue cursor
        textField.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(90, 141, 224), 1));
        textField.setFont(new java.awt.Font("Helvetica Neue", java.awt.Font.PLAIN, 14));
    }

    /**
     * Apply title label styling
     *
     * @param label Label to style
     */
    private void styleTitleLabel(JLabel label) {
        label.setFont(new java.awt.Font("Helvetica Neue", java.awt.Font.BOLD, 18));
        label.setForeground(new Color(13, 25, 51)); // Dark blue text
        label.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 5, 10, 5));
    }

    /**
     * Apply regular label styling
     *
     * @param label Label to style
     */
    private void styleLabel(JLabel label) {
        label.setFont(new java.awt.Font("Helvetica Neue", java.awt.Font.PLAIN, 14));
        label.setForeground(new Color(13, 25, 51)); // Dark blue text
        label.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 2, 5, 2));
    }

    /**
     * Style the table with consistent formatting
     *
     * @param table Table to style
     */
    private void styleTable(JTable table) {
        // Style the header
        if (table.getTableHeader() != null) {
            table.getTableHeader().setBackground(new Color(26, 79, 156)); // Medium blue
            table.getTableHeader().setForeground(Color.WHITE);
            table.getTableHeader().setFont(new java.awt.Font("Helvetica Neue", java.awt.Font.BOLD, 14));
            table.getTableHeader().setBorder(javax.swing.BorderFactory.createLineBorder(new Color(13, 60, 130), 1));
        }

        // Style the table
        table.setBackground(Color.WHITE);
        table.setForeground(new Color(13, 25, 51)); // Dark blue text
        table.setGridColor(new Color(230, 230, 230));
        table.setRowHeight(25);
        table.setFont(new java.awt.Font("Helvetica Neue", java.awt.Font.PLAIN, 14));
        table.setSelectionBackground(new Color(232, 242, 254)); // Very light blue
        table.setSelectionForeground(new Color(13, 25, 51)); // Keep text dark

        // Add custom cell renderer for alternating row colors
        table.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(245, 245, 250));
                }

                return c;
            }
        });
    }

}
