/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui.CustomsAgentRole;

import Business.ConfigureASystem;
import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Enterprise.LogisticsGroupEnterprise;
import Business.Logistics.CustomsDeclaration;
import Business.Network.Network;
import Business.Organization.CustomsLiaisonOrganization;
import Business.Organization.LogisticsOrganization;
import Business.Organization.Organization;
import Business.UserAccount.UserAccount;
import Business.WorkQueue.LogisticsWorkRequest;
import Business.WorkQueue.WorkRequest;
import java.awt.CardLayout;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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

        this.userProcessContainer = userProcessContainer;
        this.userAccount = account;

        // 初始化父面板引用（为了后续通知刷新）
        this.parentPanel = userProcessContainer.getParent() instanceof JPanel
                ? (JPanel) userProcessContainer.getParent() : null;

        // 如果传入的组织为空，尝试使用物流组织的数据
        if (ConfigureASystem.logisticsOrg != null && 
            ConfigureASystem.logisticsOrg.getCustomsDeclarationDirectory() != null) {
            
            // 如果传入的组织为空，创建一个新的
            if (organization == null) {
                this.organization = new CustomsLiaisonOrganization();
            } else {
                this.organization = organization;
            }
            
            // 重要：使用 ConfigureASystem 中的同一数据源
            this.organization.setCustomsDeclarationDirectory(
                ConfigureASystem.logisticsOrg.getCustomsDeclarationDirectory());
            
            System.out.println("DocumentReview using customs declarations from ConfigureASystem");
        } else if (organization != null) {
            this.organization = organization;
        } else {
            System.out.println("Warning: No data source available for DocumentReview");
            this.organization = new CustomsLiaisonOrganization();
        }

        // 添加调试信息
        System.out.println("DocumentReview initialized with organization: " + this.organization);
        if (this.organization != null && this.organization.getCustomsDeclarationDirectory() != null) {
            System.out.println("Number of declarations: "
                    + this.organization.getCustomsDeclarationDirectory().getCustomsDeclarationList().size());
        }

        // Initialize the UI
        setupTable();
        populateTable();
        clearFields();

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

        // 直接从 CustomsDeclarationDirectory 获取数据
        if (organization != null && organization.getCustomsDeclarationDirectory() != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            for (CustomsDeclaration declaration : organization.getCustomsDeclarationDirectory().getCustomsDeclarationList()) {
                Object[] row = new Object[4];
                row[0] = declaration.getDeclarationId();
                row[1] = declaration.getDeclarationType() != null 
                       ? declaration.getDeclarationType() : "Standard";
                row[2] = declaration.getStatus() != null 
                       ? declaration.getStatus() : "Pending";
                row[3] = declaration.getSubmissionDate() != null 
                       ? dateFormat.format(declaration.getSubmissionDate()) 
                       : dateFormat.format(declaration.getDeclarationDate());

                model.addRow(row);
            }
        }

        // 如果表格为空，添加一条信息
        if (model.getRowCount() == 0) {
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
            boolean isPending = "Pending".equals(selectedDeclaration.getStatus());
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

        comBoFilter.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pending", "Approved", "Canceled" }));
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
                .addComponent(lblDocContent)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, docContenJPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 874, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
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
                .addGroup(reviewNotesJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(reviewNotesJPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1))
                    .addGroup(reviewNotesJPanelLayout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(txtReviewNotes, javax.swing.GroupLayout.PREFERRED_SIZE, 851, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 580, Short.MAX_VALUE)
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtSearchBox)
                        .addComponent(lblSearchID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
        boolean isPending = "Submitted".equals(request.getStatus());
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

        // Remove this section as getResolver() method doesn't exist
        // if (request.getResolver() != null) {
        //     content.append("Processed By: ").append(request.getResolver().getUsername()).append("\n");
        // }
        // Show notes if available
        if (request.getMessage() != null && !request.getMessage().isEmpty()) {
            content.append("\nReview Notes: ").append(request.getMessage()).append("\n");
        }

        if (request.getNotes() != null && !request.getNotes().isEmpty()) {
            content.append("\nDeclaration Notes: ").append(request.getNotes()).append("\n");
        }

        txtDocPreview.setText(content.toString());
    }

    private void updateRequestStatus(String newStatus) {
        int selectedRow = tblList.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a declaration first");
            return;
        }

        String declarationId = tblList.getValueAt(selectedRow, 0).toString();
        if (declarationId.equals("No declarations found")) {
            return;
        }

        // Update request status
        boolean updated = false;

        for (WorkRequest request : organization.getWorkQueue().getWorkRequestList()) {
            if (request instanceof LogisticsWorkRequest) {
                LogisticsWorkRequest customsRequest = (LogisticsWorkRequest) request;
                if (customsRequest.getDeclarationId().equals(declarationId)) {
                    // Set new status
                    customsRequest.setStatus(newStatus);

                    // Important: Set resolve date to track when it was processed
                    customsRequest.setResolveDate(new Date());

                    // Remove this line as the method doesn't exist
//                 customsRequest.setResolver(userAccount);
                    // Add review notes if provided
                    if (txtReviewNotes.getText() != null && !txtReviewNotes.getText().isEmpty()) {
                        customsRequest.setMessage(txtReviewNotes.getText());
                    }

                    // Also update the original declaration in logistics org
                    updateOriginalDeclaration(customsRequest.getDeclarationId(), newStatus);

                    updated = true;
                    break;
                }
            }
        }

        if (updated) {
            JOptionPane.showMessageDialog(this, "Declaration status updated to: " + newStatus);

            // Refresh tables
            populateTable();
            clearFields();

            // Notify parent panel to refresh (if possible)
            if (parentPanel != null && parentPanel instanceof CustomsLiaisonOfficeHP) {
                try {
                    // This will refresh both pending and recent activities tables
                    ((CustomsLiaisonOfficeHP) parentPanel).refreshData();
                    System.out.println("Parent panel refreshed");
                } catch (Exception e) {
                    System.out.println("Error refreshing parent panel: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Could not find the selected declaration");
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

}
