/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui.LogisticsRole;

import Business.ConfigureASystem;
import Business.DB4OUtil.DB4OUtil;
import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Enterprise.LogisticsGroupEnterprise;
import Business.Logistics.CustomsDeclaration;
import Business.Logistics.CustomsDeclaration.CustomsLineItem;
import Business.Logistics.CustomsDeclarationDirectory;
import Business.Logistics.Shipment;
import Business.Network.Network;
import Business.Organization.CustomsLiaisonOrganization;
import Business.Organization.LogisticsOrganization;
import Business.Organization.Organization;
import Business.UserAccount.UserAccount;
import Business.WorkQueue.LogisticsWorkRequest;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author zhuchenyan
 */
public class DocumentationDetails extends javax.swing.JPanel {

    private JPanel userProcessContainer;
    private UserAccount userAccount;
    private Enterprise enterprise;
    private LogisticsOrganization organization;
    private CustomsDeclarationDirectory declarationDirectory;
    private Shipment currentShipment;
    private CustomsDeclaration currentDeclaration;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private JPanel parentPanel;

    // 声明UI组件
    private JTable tblItems;
    private JLabel lblDeclarationId;
    private JTextField txtDeclarationId;
    private JLabel lblDeclarationNumber;
    private JTextField txtDeclarationNumber;
    private JLabel lblShipmentId;
    private JTextField txtShipmentId;
    private JLabel lblDeclarationType;
    private JComboBox<String> cmbDeclarationType;
    private JComboBox<String> cmbStatus;
    private JLabel lblDeclarationDate;
    private JTextField txtDeclarationDate;
    private JLabel lblSubmissionDate;
    private JTextField txtSubmissionDate;
    private JLabel lblCountryOfOrigin;
    private JTextField txtCountryOfOrigin;
    private JLabel lblDestinationCountry;
    private JTextField txtDestinationCountry;
    private JLabel lblHsCode;
    private JTextField txtHsCode;
    private JLabel lblNotes;
    private JTextArea txtNotes;
    private JButton btnRemoveItem;

    /**
     * Creates new form Documentation
     */
    public DocumentationDetails(JPanel userProcessContainer, UserAccount account,
            Enterprise enterprise, LogisticsOrganization organization, Shipment shipment, JPanel parentPanel) {
        initComponents();

        this.userProcessContainer = userProcessContainer;
        this.userAccount = account;
        this.enterprise = enterprise;
        this.organization = ConfigureASystem.getLogisticsOrganization();
        if (this.organization != null) {
            this.declarationDirectory = this.organization.getCustomsDeclarationDirectory();
            System.out.println("共加载了 " + this.declarationDirectory.getCustomsDeclarationList().size() + " 个报关单");
        }

        this.declarationDirectory = organization.getCustomsDeclarationDirectory();
        this.currentShipment = shipment;
        this.parentPanel = parentPanel;

        if (this.currentShipment != null) {
            this.currentDeclaration = currentShipment.getCustomsDeclaration();

            // 如果没有海关文档，创建一个新的
            if (this.currentDeclaration == null) {
                this.currentDeclaration = new CustomsDeclaration();
                this.currentDeclaration.setDeclarationId("CD" + System.currentTimeMillis());
                this.currentDeclaration.setShipmentId(currentShipment.getShipmentId());
                this.currentDeclaration.setCountryOfOrigin("China");
                this.currentDeclaration.setConsignor("Shanghai Warehouse");
                this.currentDeclaration.setConsignee(currentShipment.getDestination());
//                this.currentDeclaration.setDestinationCountry(extractCountry(currentShipment.getDestination()));
                this.currentDeclaration.setDeclarationDate(new Date());
                this.currentDeclaration.setStatus("Pending");

                currentShipment.setCustomsDeclaration(this.currentDeclaration);

                // 保存到系统
                EcoSystem system = EcoSystem.getInstance();
                DB4OUtil.getInstance().storeSystem(system);
            }
        }

        initComponents();
        populateFields();
    }

    private void populateFields() {
        if (currentDeclaration != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            // 基本信息
            txtDeclarationId.setText(currentDeclaration.getDeclarationId());
            txtDeclarationNumber.setText(currentDeclaration.getDeclarationNumber() != null
                    ? currentDeclaration.getDeclarationNumber() : "");
            txtShipmentId.setText(currentDeclaration.getShipmentId());

            if (currentDeclaration.getDeclarationType() != null) {
                for (int i = 0; i < cmbDeclarationType.getItemCount(); i++) {
                    if (cmbDeclarationType.getItemAt(i).equals(currentDeclaration.getDeclarationType())) {
                        cmbDeclarationType.setSelectedIndex(i);
                        break;
                    }
                }
            }

            if (currentDeclaration.getStatus() != null) {
                for (int i = 0; i < cmbStatus.getItemCount(); i++) {
                    if (cmbStatus.getItemAt(i).equals(currentDeclaration.getStatus())) {
                        cmbStatus.setSelectedIndex(i);
                        break;
                    }
                }
            }

            txtDeclarationDate.setText(currentDeclaration.getDeclarationDate() != null
                    ? dateFormat.format(currentDeclaration.getDeclarationDate()) : "");
            txtSubmissionDate.setText(currentDeclaration.getSubmissionDate() != null
                    ? dateFormat.format(currentDeclaration.getSubmissionDate()) : "");
            txtProcessingDate.setText(currentDeclaration.getProcessingDate() != null
                    ? dateFormat.format(currentDeclaration.getProcessingDate()) : "");

            // 详细信息
            txtConsignor.setText(currentDeclaration.getConsignor() != null
                    ? currentDeclaration.getConsignor() : "");
            txtConsignee.setText(currentDeclaration.getConsignee() != null
                    ? currentDeclaration.getConsignee() : "");
            txtCountryOfOrigin.setText(currentDeclaration.getCountryOfOrigin() != null
                    ? currentDeclaration.getCountryOfOrigin() : "");
            txtDestinationCountry.setText(currentDeclaration.getDestinationCountry() != null
                    ? currentDeclaration.getDestinationCountry() : "");
            txtCustomsOffice.setText(currentDeclaration.getCustomsOffice() != null
                    ? currentDeclaration.getCustomsOffice() : "");
            txtHsCode.setText(currentDeclaration.getHsCode() != null
                    ? currentDeclaration.getHsCode() : "");

            // 备注
            txtNotes.setText(currentDeclaration.getNotes() != null
                    ? currentDeclaration.getNotes() : "");

            // 物品列表
            DefaultTableModel model = (DefaultTableModel) tblItems.getModel();
            model.setRowCount(0);

            if (currentDeclaration.getItems() != null) {
                for (CustomsLineItem item : currentDeclaration.getItems()) {
                    model.addRow(new Object[]{
                        item.getDescription(),
                        item.getHsCode(),
                        item.getQuantity(),
                        item.getUnit(),
                        item.getUnitValue(),
                        item.getTotalValue(),
                        item.getGrossWeight()
                    });
                }
            }
        }
    }

    // 初始化报关单列表表格
    private void setupDeclarationListTable() {
        String[] columnNames = {"Declaration ID", "Status", "Date"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblList.setModel(model);
        refreshDeclarationList();

        tblList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
                    int selectedRow = tblList.getSelectedRow();
                    if (selectedRow >= 0) {
                        String declarationId = tblList.getValueAt(selectedRow, 0).toString();
                        displayDeclarationDetails(declarationId);
                    }
                }
            }
        });
    }

    // 初始化商品信息表格
    private void setupGoodsInfoTable() {
        String[] columnNames = {"Description", "HS Code", "Quantity", "Unit",
            "Unit Value", "Total Value", "Gross Weight"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        tblGoodsInfo.setModel(model);
    }

    // 刷新报关单列表
    private void refreshDeclarationList() {
        DefaultTableModel model = (DefaultTableModel) tblList.getModel();
        model.setRowCount(0);

        if (declarationDirectory != null) {
            for (CustomsDeclaration declaration : declarationDirectory.getCustomsDeclarationList()) {
                Object[] row = {
                    declaration.getDeclarationId(),
                    declaration.getStatus(),
                    new SimpleDateFormat("yyyy-MM-dd").format(declaration.getDeclarationDate())
                };
                model.addRow(row);
            }
        }
    }

    // 显示报关单详情
    private void displayDeclarationDetails(String declarationId) {
        currentDeclaration = declarationDirectory.findDeclarationById(declarationId);
        if (currentDeclaration != null) {
            // 填充基本信息
            txtDeclarID.setText(currentDeclaration.getDeclarationId());
            txtShipmentNumber.setText(currentDeclaration.getShipmentId());
            txtConsignor.setText(currentDeclaration.getConsignor());
            txtConsignee.setText(currentDeclaration.getConsignee());
            txtOrigin.setText(currentDeclaration.getCountryOfOrigin());
            txtDestination.setText(currentDeclaration.getDestinationCountry());
            txtStatus.setText(currentDeclaration.getStatus());
            txtDeclarDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(currentDeclaration.getDeclarationDate()));

            // 填充处理信息
            txtCustomsOffice.setText(currentDeclaration.getCustomsOffice());
            if (currentDeclaration.getProcessingDate() != null) {
                txtProcessingDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(currentDeclaration.getProcessingDate()));
            } else {
                txtProcessingDate.setText("");
            }
            txtComments.setText(currentDeclaration.getNotes());

            // 更新商品信息表格
            refreshGoodsInfoTable();

            // 更新按钮状态
            updateButtonStates();
        }
    }

    private void refreshGoodsInfoTable() {
        DefaultTableModel model = (DefaultTableModel) tblGoodsInfo.getModel();
        model.setRowCount(0);

        if (currentDeclaration != null && currentDeclaration.getItems() != null) {
            for (CustomsDeclaration.CustomsLineItem item : currentDeclaration.getItems()) {
                Object[] row = {
                    item.getDescription(),
                    item.getHsCode(),
                    item.getQuantity(),
                    item.getUnit(),
                    item.getUnitValue(),
                    item.getTotalValue(),
                    item.getGrossWeight()
                };
                model.addRow(row);
            }
        }
    }

    // 更新当前报关单的商品信息
    private void updateGoodsInfo() {
        if (currentDeclaration != null) {
            DefaultTableModel model = (DefaultTableModel) tblGoodsInfo.getModel();
            ArrayList<CustomsDeclaration.CustomsLineItem> items = new ArrayList<>();

            for (int i = 0; i < model.getRowCount(); i++) {
                CustomsDeclaration.CustomsLineItem item = new CustomsDeclaration.CustomsLineItem();
                item.setDescription((String) model.getValueAt(i, 0));
                item.setHsCode((String) model.getValueAt(i, 1));
                item.setQuantity((Integer) model.getValueAt(i, 2));
                item.setUnit((String) model.getValueAt(i, 3));
                item.setUnitValue((Double) model.getValueAt(i, 4));
                // Total value will be calculated automatically
                item.setGrossWeight((Double) model.getValueAt(i, 6));
                items.add(item);
            }

            currentDeclaration.setItems(items);
        }
    }

// 更新按钮状态
    private void updateButtonStates() {
        boolean isDraft = currentDeclaration != null
                && "Draft".equals(currentDeclaration.getStatus());
        btnSave.setEnabled(isDraft);
        btnSubmit.setEnabled(isDraft);
        btnDelete.setEnabled(currentDeclaration != null);
        btnPrint.setEnabled(currentDeclaration != null);
        btnAddItem.setEnabled(isDraft);
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
        txtSearchBox = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        lblSearchID = new javax.swing.JLabel();
        DeclaListJPanel = new javax.swing.JPanel();
        lblListTitle = new javax.swing.JLabel();
        btnCreateNew = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblList = new javax.swing.JTable();
        declarDetailsJPanel = new javax.swing.JPanel();
        lblDetailsTitle = new javax.swing.JLabel();
        btnSave = new javax.swing.JButton();
        btnSubmit = new javax.swing.JButton();
        btnPrint = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        goodsInfoJPanel = new javax.swing.JPanel();
        lblGoodsInfo = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblGoodsInfo = new javax.swing.JTable();
        btnAddItem = new javax.swing.JButton();
        basicInfoJPanel = new javax.swing.JPanel();
        lblBasicInfo = new javax.swing.JLabel();
        lblDeclarID = new javax.swing.JLabel();
        txtDeclarID = new javax.swing.JTextField();
        lblShipNo = new javax.swing.JLabel();
        txtShipmentNumber = new javax.swing.JTextField();
        lblConsignor = new javax.swing.JLabel();
        txtConsignor = new javax.swing.JTextField();
        lblOrigin = new javax.swing.JLabel();
        txtOrigin = new javax.swing.JTextField();
        lblStatus = new javax.swing.JLabel();
        txtStatus = new javax.swing.JTextField();
        lblDeclarDate = new javax.swing.JLabel();
        txtDeclarDate = new javax.swing.JTextField();
        lblConsignee = new javax.swing.JLabel();
        txtConsignee = new javax.swing.JTextField();
        lblDestination = new javax.swing.JLabel();
        txtDestination = new javax.swing.JTextField();
        processingInfoJPanel = new javax.swing.JPanel();
        lblProcessingInfo = new javax.swing.JLabel();
        lblCustomsOffice = new javax.swing.JLabel();
        txtCustomsOffice = new javax.swing.JTextField();
        lblProcessingDate = new javax.swing.JLabel();
        txtProcessingDate = new javax.swing.JTextField();
        lblComments = new javax.swing.JLabel();
        txtComments = new javax.swing.JTextField();

        setMaximumSize(new java.awt.Dimension(1450, 800));
        setMinimumSize(new java.awt.Dimension(1450, 800));

        btnBack.setText("<< Back");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        lblTitle.setText("Customs Declaration Management");

        btnSearch.setText("Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        lblSearchID.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSearchID.setText("Search by ID");

        DeclaListJPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblListTitle.setText("Customs Declaration List");

        btnCreateNew.setBackground(new java.awt.Color(102, 204, 0));
        btnCreateNew.setForeground(new java.awt.Color(255, 255, 255));
        btnCreateNew.setText("Create New");
        btnCreateNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateNewActionPerformed(evt);
            }
        });

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
        if (tblList.getColumnModel().getColumnCount() > 0) {
            tblList.getColumnModel().getColumn(0).setResizable(false);
        }

        javax.swing.GroupLayout DeclaListJPanelLayout = new javax.swing.GroupLayout(DeclaListJPanel);
        DeclaListJPanel.setLayout(DeclaListJPanelLayout);
        DeclaListJPanelLayout.setHorizontalGroup(
            DeclaListJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DeclaListJPanelLayout.createSequentialGroup()
                .addComponent(lblListTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCreateNew)
                .addGap(17, 17, 17))
            .addGroup(DeclaListJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 419, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );
        DeclaListJPanelLayout.setVerticalGroup(
            DeclaListJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DeclaListJPanelLayout.createSequentialGroup()
                .addGroup(DeclaListJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblListTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCreateNew, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        declarDetailsJPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblDetailsTitle.setText("Customs Declaration Details");

        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnSubmit.setBackground(new java.awt.Color(102, 204, 0));
        btnSubmit.setForeground(new java.awt.Color(255, 255, 255));
        btnSubmit.setText("Submit");
        btnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitActionPerformed(evt);
            }
        });

        btnPrint.setText("Print");
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });

        btnDelete.setBackground(new java.awt.Color(255, 0, 0));
        btnDelete.setForeground(new java.awt.Color(255, 255, 255));
        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        goodsInfoJPanel.setBackground(new java.awt.Color(255, 255, 255));
        goodsInfoJPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblGoodsInfo.setText("Goods Information");

        tblGoodsInfo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Description", "HS Code", "Quantity", "Unit", "Unit Value", "Total Value", "Gross Weight"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Double.class, java.lang.Object.class, java.lang.Double.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblGoodsInfo);

        btnAddItem.setText("Add Item");
        btnAddItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddItemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout goodsInfoJPanelLayout = new javax.swing.GroupLayout(goodsInfoJPanel);
        goodsInfoJPanel.setLayout(goodsInfoJPanelLayout);
        goodsInfoJPanelLayout.setHorizontalGroup(
            goodsInfoJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(goodsInfoJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(goodsInfoJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(goodsInfoJPanelLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jScrollPane1))
                    .addGroup(goodsInfoJPanelLayout.createSequentialGroup()
                        .addComponent(lblGoodsInfo)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, goodsInfoJPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAddItem, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42))
        );
        goodsInfoJPanelLayout.setVerticalGroup(
            goodsInfoJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(goodsInfoJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblGoodsInfo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnAddItem, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                .addGap(12, 12, 12))
        );

        basicInfoJPanel.setBackground(new java.awt.Color(255, 255, 255));
        basicInfoJPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblBasicInfo.setText("Basic Information");

        lblDeclarID.setText("Declaration ID:");

        txtDeclarID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDeclarIDActionPerformed(evt);
            }
        });

        lblShipNo.setText("Shipment Number:");

        lblConsignor.setText("Consignor:");

        txtConsignor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtConsignorActionPerformed(evt);
            }
        });

        lblOrigin.setText("Country of Origin:");

        txtOrigin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtOriginActionPerformed(evt);
            }
        });

        lblStatus.setText("Status:");

        lblDeclarDate.setText("Declaration Date:");

        lblConsignee.setText("Consignee:");

        txtConsignee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtConsigneeActionPerformed(evt);
            }
        });

        lblDestination.setText("Destination Country:");

        javax.swing.GroupLayout basicInfoJPanelLayout = new javax.swing.GroupLayout(basicInfoJPanel);
        basicInfoJPanel.setLayout(basicInfoJPanelLayout);
        basicInfoJPanelLayout.setHorizontalGroup(
            basicInfoJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(basicInfoJPanelLayout.createSequentialGroup()
                .addGroup(basicInfoJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(basicInfoJPanelLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(basicInfoJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(basicInfoJPanelLayout.createSequentialGroup()
                                .addComponent(lblDeclarID, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(36, 36, 36)
                                .addComponent(txtDeclarID, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(basicInfoJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(basicInfoJPanelLayout.createSequentialGroup()
                                    .addComponent(lblConsignor, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(36, 36, 36)
                                    .addComponent(txtConsignor, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(basicInfoJPanelLayout.createSequentialGroup()
                                    .addComponent(lblShipNo, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(36, 36, 36)
                                    .addComponent(txtShipmentNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(basicInfoJPanelLayout.createSequentialGroup()
                                    .addComponent(lblOrigin, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(36, 36, 36)
                                    .addComponent(txtOrigin, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(basicInfoJPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblBasicInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addGroup(basicInfoJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(basicInfoJPanelLayout.createSequentialGroup()
                        .addComponent(lblStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(txtStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(basicInfoJPanelLayout.createSequentialGroup()
                        .addComponent(lblDeclarDate, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(txtDeclarDate, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(basicInfoJPanelLayout.createSequentialGroup()
                        .addComponent(lblConsignee, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(txtConsignee, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(basicInfoJPanelLayout.createSequentialGroup()
                        .addComponent(lblDestination, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(txtDestination, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(29, 29, 29))
        );
        basicInfoJPanelLayout.setVerticalGroup(
            basicInfoJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(basicInfoJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblBasicInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(basicInfoJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDeclarID)
                    .addComponent(txtDeclarID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblStatus)
                    .addComponent(txtStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(basicInfoJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblShipNo)
                    .addComponent(txtShipmentNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDeclarDate)
                    .addComponent(txtDeclarDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(basicInfoJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblConsignor)
                    .addComponent(txtConsignor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblConsignee)
                    .addComponent(txtConsignee, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(basicInfoJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblOrigin)
                    .addComponent(txtOrigin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDestination)
                    .addComponent(txtDestination, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        processingInfoJPanel.setBackground(new java.awt.Color(255, 255, 255));
        processingInfoJPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblProcessingInfo.setText("Customs Processing Information");

        lblCustomsOffice.setText("Customs Office:");

        lblProcessingDate.setText("Processing Date:");

        lblComments.setText("Notes/Comments:");

        txtComments.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCommentsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout processingInfoJPanelLayout = new javax.swing.GroupLayout(processingInfoJPanel);
        processingInfoJPanel.setLayout(processingInfoJPanelLayout);
        processingInfoJPanelLayout.setHorizontalGroup(
            processingInfoJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(processingInfoJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblProcessingInfo)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(processingInfoJPanelLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(processingInfoJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(processingInfoJPanelLayout.createSequentialGroup()
                        .addComponent(lblCustomsOffice, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtCustomsOffice, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblProcessingDate, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtProcessingDate, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(processingInfoJPanelLayout.createSequentialGroup()
                        .addComponent(lblComments, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtComments)))
                .addGap(41, 41, 41))
        );
        processingInfoJPanelLayout.setVerticalGroup(
            processingInfoJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(processingInfoJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblProcessingInfo)
                .addGap(18, 18, 18)
                .addGroup(processingInfoJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCustomsOffice)
                    .addComponent(txtCustomsOffice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblProcessingDate)
                    .addComponent(txtProcessingDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(processingInfoJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblComments)
                    .addComponent(txtComments, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(75, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout declarDetailsJPanelLayout = new javax.swing.GroupLayout(declarDetailsJPanel);
        declarDetailsJPanel.setLayout(declarDetailsJPanelLayout);
        declarDetailsJPanelLayout.setHorizontalGroup(
            declarDetailsJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(declarDetailsJPanelLayout.createSequentialGroup()
                .addComponent(lblDetailsTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
            .addGroup(declarDetailsJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(declarDetailsJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(basicInfoJPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(goodsInfoJPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(processingInfoJPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        declarDetailsJPanelLayout.setVerticalGroup(
            declarDetailsJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(declarDetailsJPanelLayout.createSequentialGroup()
                .addGroup(declarDetailsJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDetailsTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addComponent(basicInfoJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(goodsInfoJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(processingInfoJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(DeclaListJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(declarDetailsJPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblSearchID, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtSearchBox, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(24, 24, 24))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                    .addComponent(DeclaListJPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
            CustomsDeclaration declaration = declarationDirectory.findDeclarationById(searchId);
            if (declaration != null) {
                displayDeclarationDetails(searchId);
                // 选中列表中对应的行
                for (int i = 0; i < tblList.getRowCount(); i++) {
                    if (tblList.getValueAt(i, 0).toString().equals(searchId)) {
                        tblList.setRowSelectionInterval(i, i);
                        break;
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "No declaration found with ID: " + searchId,
                        "Search Result",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        if (currentDeclaration != null) {
            updateCurrentDeclaration();
            if (declarationDirectory.findDeclarationById(currentDeclaration.getDeclarationId()) == null) {
                declarationDirectory.addCustomsDeclaration(currentDeclaration);
            }
            refreshDeclarationList();
            JOptionPane.showMessageDialog(this, "Declaration saved successfully.");
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitActionPerformed
        // TODO add your handling code here:
        if (currentDeclaration != null && validateDeclaration()) {
            currentDeclaration.setStatus("Submitted");
            currentDeclaration.setSubmissionDate(new Date());
            updateCurrentDeclaration();

            if (declarationDirectory.findDeclarationById(currentDeclaration.getDeclarationId()) == null) {
                declarationDirectory.addCustomsDeclaration(currentDeclaration);
            }

            // 创建并发送 LogisticsWorkRequest 到海关
            sendCustomsWorkRequest(currentDeclaration);

            refreshDeclarationList();
            updateButtonStates();
            JOptionPane.showMessageDialog(this, "Declaration submitted successfully and sent to Customs Office.");
        }
    }//GEN-LAST:event_btnSubmitActionPerformed

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
        // TODO add your handling code here:
        if (currentDeclaration == null) {
            JOptionPane.showMessageDialog(this, "Please select a declaration to print");
            return;
        }

        // 创建要打印的文本内容
        StringBuilder printContent = new StringBuilder();
        printContent.append("CUSTOMS DECLARATION REPORT\n");
        printContent.append("=========================\n\n");

        // 基本信息
        printContent.append("BASIC INFORMATION\n");
        printContent.append("-----------------\n");
        printContent.append("Declaration ID: ").append(currentDeclaration.getDeclarationId()).append("\n");
        printContent.append("Shipment Number: ").append(currentDeclaration.getShipmentId()).append("\n");
        printContent.append("Status: ").append(currentDeclaration.getStatus()).append("\n");
        printContent.append("Declaration Date: ").append(dateFormat.format(currentDeclaration.getDeclarationDate())).append("\n\n");

        // 当事人信息
        printContent.append("PARTY INFORMATION\n");
        printContent.append("-----------------\n");
        printContent.append("Consignor: ").append(currentDeclaration.getConsignor()).append("\n");
        printContent.append("Consignee: ").append(currentDeclaration.getConsignee()).append("\n");
        printContent.append("Country of Origin: ").append(currentDeclaration.getCountryOfOrigin()).append("\n");
        printContent.append("Destination Country: ").append(currentDeclaration.getDestinationCountry()).append("\n\n");

        // 商品信息
        printContent.append("GOODS INFORMATION\n");
        printContent.append("-----------------\n");
        if (currentDeclaration.getItems() != null) {
            for (CustomsDeclaration.CustomsLineItem item : currentDeclaration.getItems()) {
                printContent.append("\nDescription: ").append(item.getDescription());
                printContent.append("\nHS Code: ").append(item.getHsCode());
                printContent.append("\nQuantity: ").append(item.getQuantity()).append(" ").append(item.getUnit());
                printContent.append("\nUnit Value: $").append(String.format("%.2f", item.getUnitValue()));
                printContent.append("\nTotal Value: $").append(String.format("%.2f", item.getTotalValue()));
                printContent.append("\nGross Weight: ").append(String.format("%.2f", item.getGrossWeight())).append(" kg");
                printContent.append("\n");
            }
        }
        printContent.append("\n");

        // 处理信息
        printContent.append("PROCESSING INFORMATION\n");
        printContent.append("---------------------\n");
        printContent.append("Customs Office: ").append(currentDeclaration.getCustomsOffice()).append("\n");
        if (currentDeclaration.getProcessingDate() != null) {
            printContent.append("Processing Date: ").append(dateFormat.format(currentDeclaration.getProcessingDate())).append("\n");
        }
        printContent.append("Notes: ").append(currentDeclaration.getNotes()).append("\n");

        // 创建一个临时的 JTextArea 来打印内容
        JTextArea printArea = new JTextArea(printContent.toString());
        printArea.setEditable(false);

        try {
            boolean complete = printArea.print();
            if (complete) {
                JOptionPane.showMessageDialog(this, "Print completed successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Print was cancelled");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error printing: " + e.getMessage());
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnPrintActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        if (currentDeclaration == null) {
            JOptionPane.showMessageDialog(this,
                    "Please select a declaration to delete",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 检查报关单状态，只有Draft状态的报关单可以删除
        if (!"Draft".equals(currentDeclaration.getStatus())) {
            JOptionPane.showMessageDialog(this,
                    "Only declarations in 'Draft' status can be deleted.\n"
                    + "Current status: " + currentDeclaration.getStatus(),
                    "Cannot Delete",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 显示确认对话框
        int response = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this declaration?\n\n"
                + "Declaration ID: " + currentDeclaration.getDeclarationId() + "\n"
                + "Shipment Number: " + currentDeclaration.getShipmentId() + "\n"
                + "Status: " + currentDeclaration.getStatus() + "\n\n"
                + "This action cannot be undone.",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (response == JOptionPane.YES_OPTION) {
            try {
                // 从目录中删除报关单
                declarationDirectory.removeCustomsDeclaration(currentDeclaration);

                // 刷新列表
                refreshDeclarationList();

                // 清空表单
                clearForm();

                // 禁用按钮
                btnSave.setEnabled(false);
                btnSubmit.setEnabled(false);
                btnPrint.setEnabled(false);
                btnDelete.setEnabled(false);
                btnAddItem.setEnabled(false);

                // 清除当前选中的报关单
                currentDeclaration = null;

                // 显示成功消息
                JOptionPane.showMessageDialog(this,
                        "Declaration deleted successfully",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception e) {
                // 如果删除过程中发生错误
                JOptionPane.showMessageDialog(this,
                        "Error deleting declaration: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void txtDeclarIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDeclarIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDeclarIDActionPerformed

    private void txtConsignorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtConsignorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtConsignorActionPerformed

    private void txtOriginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtOriginActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtOriginActionPerformed

    private void txtConsigneeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtConsigneeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtConsigneeActionPerformed

    private void txtCommentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCommentsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCommentsActionPerformed

    private void btnCreateNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateNewActionPerformed
        // TODO add your handling code here:
        clearForm();
        currentDeclaration = new CustomsDeclaration();
        currentDeclaration.setDeclarationId(generateNewDeclarationId());
        currentDeclaration.setStatus("Draft");
        currentDeclaration.setDeclarationDate(new Date());

        displayDeclarationDetails(currentDeclaration.getDeclarationId());
        btnSave.setEnabled(true);
        btnSubmit.setEnabled(true);
        btnAddItem.setEnabled(true);
    }//GEN-LAST:event_btnCreateNewActionPerformed

    private void btnAddItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddItemActionPerformed
        // TODO add your handling code here:
        // 创建对话框
        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblDescription = new JLabel("Description:");
        JTextField txtDescription = new JTextField(20);

        JLabel lblHsCode = new JLabel("HS Code:");
        JTextField txtHsCode = new JTextField(20);

        JLabel lblQuantity = new JLabel("Quantity:");
        JTextField txtQuantity = new JTextField(20);

        JLabel lblUnit = new JLabel("Unit:");
        JTextField txtUnit = new JTextField(20);
        txtUnit.setText("PCS");

        JLabel lblUnitValue = new JLabel("Unit Value:");
        JTextField txtUnitValue = new JTextField(20);

        JLabel lblGrossWeight = new JLabel("Gross Weight:");
        JTextField txtGrossWeight = new JTextField(20);

        panel.add(lblDescription);
        panel.add(txtDescription);
        panel.add(lblHsCode);
        panel.add(txtHsCode);
        panel.add(lblQuantity);
        panel.add(txtQuantity);
        panel.add(lblUnit);
        panel.add(txtUnit);
        panel.add(lblUnitValue);
        panel.add(txtUnitValue);
        panel.add(lblGrossWeight);
        panel.add(txtGrossWeight);

        int result = JOptionPane.showConfirmDialog(null, panel, "Add Declaration Item",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String description = txtDescription.getText();
                String hsCode = txtHsCode.getText();
                int quantity = Integer.parseInt(txtQuantity.getText());
                String unit = txtUnit.getText();
                double unitValue = Double.parseDouble(txtUnitValue.getText());
                double grossWeight = Double.parseDouble(txtGrossWeight.getText());

                double totalValue = quantity * unitValue;

                DefaultTableModel model = (DefaultTableModel) tblItems.getModel();
                model.addRow(new Object[]{
                    description, hsCode, quantity, unit, unitValue, totalValue, grossWeight
                });
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter valid numbers for quantity, unit value, and gross weight.",
                        "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnAddItemActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel DeclaListJPanel;
    private javax.swing.JPanel basicInfoJPanel;
    private javax.swing.JButton btnAddItem;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnCreateNew;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnPrint;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnSubmit;
    private javax.swing.JPanel declarDetailsJPanel;
    private javax.swing.JPanel goodsInfoJPanel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblBasicInfo;
    private javax.swing.JLabel lblComments;
    private javax.swing.JLabel lblConsignee;
    private javax.swing.JLabel lblConsignor;
    private javax.swing.JLabel lblCustomsOffice;
    private javax.swing.JLabel lblDeclarDate;
    private javax.swing.JLabel lblDeclarID;
    private javax.swing.JLabel lblDestination;
    private javax.swing.JLabel lblDetailsTitle;
    private javax.swing.JLabel lblGoodsInfo;
    private javax.swing.JLabel lblListTitle;
    private javax.swing.JLabel lblOrigin;
    private javax.swing.JLabel lblProcessingDate;
    private javax.swing.JLabel lblProcessingInfo;
    private javax.swing.JLabel lblSearchID;
    private javax.swing.JLabel lblShipNo;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel processingInfoJPanel;
    private javax.swing.JTable tblGoodsInfo;
    private javax.swing.JTable tblList;
    private javax.swing.JTextField txtComments;
    private javax.swing.JTextField txtConsignee;
    private javax.swing.JTextField txtConsignor;
    private javax.swing.JTextField txtCustomsOffice;
    private javax.swing.JTextField txtDeclarDate;
    private javax.swing.JTextField txtDeclarID;
    private javax.swing.JTextField txtDestination;
    private javax.swing.JTextField txtOrigin;
    private javax.swing.JTextField txtProcessingDate;
    private javax.swing.JTextField txtSearchBox;
    private javax.swing.JTextField txtShipmentNumber;
    private javax.swing.JTextField txtStatus;
    // End of variables declaration//GEN-END:variables

    private void clearForm() {
        txtDeclarID.setText("");
        txtShipmentNumber.setText("");
        txtConsignor.setText("");
        txtConsignee.setText("");
        txtOrigin.setText("");
        txtDestination.setText("");
        txtStatus.setText("");
        txtDeclarDate.setText("");
        txtCustomsOffice.setText("");
        txtProcessingDate.setText("");
        txtComments.setText("");

        DefaultTableModel model = (DefaultTableModel) tblGoodsInfo.getModel();
        model.setRowCount(0);
    }

    private void updateCurrentDeclaration() {
        if (currentDeclaration != null) {
            currentDeclaration.setShipmentId(txtShipmentNumber.getText().trim());
            currentDeclaration.setConsignor(txtConsignor.getText().trim());
            currentDeclaration.setConsignee(txtConsignee.getText().trim());
            currentDeclaration.setCountryOfOrigin(txtOrigin.getText().trim());
            currentDeclaration.setDestinationCountry(txtDestination.getText().trim());
            currentDeclaration.setCustomsOffice(txtCustomsOffice.getText().trim());
            currentDeclaration.setNotes(txtComments.getText().trim());

            try {
                if (!txtProcessingDate.getText().trim().isEmpty()) {
                    currentDeclaration.setProcessingDate(new SimpleDateFormat("yyyy-MM-dd").parse(txtProcessingDate.getText().trim()));
                }
            } catch (Exception e) {
                System.err.println("Error parsing processing date: " + e.getMessage());
            }

            // 更新商品信息
            updateGoodsInfo();
        }
    }

    private String generateNewDeclarationId() {
        String docType = "CD"; // Customs Declaration
        String dateStr = new SimpleDateFormat("yyyyMMdd").format(new Date());
        return String.format("%s%s%04d", docType, dateStr,
                declarationDirectory.getCustomsDeclarationList().size() + 1);
    }

    private boolean validateDeclaration() {
        // 验证必填字段
        if (txtShipmentNumber.getText().trim().isEmpty()
                || txtConsignor.getText().trim().isEmpty()
                || txtConsignee.getText().trim().isEmpty()
                || txtOrigin.getText().trim().isEmpty()
                || txtDestination.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all required fields.");
            return false;
        }

        // 验证商品信息
        DefaultTableModel model = (DefaultTableModel) tblGoodsInfo.getModel();
        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Please add at least one goods item.");
            return false;
        }

        return true;
    }

    public void initializeUI() {
        // 确保declarationDirectory已正确初始化
        if (this.organization != null) {
            this.declarationDirectory = this.organization.getCustomsDeclarationDirectory();

            // 调试信息
            System.out.println("初始化UI...");
            if (this.declarationDirectory != null) {
                System.out.println("报关单数量: " + this.declarationDirectory.getCustomsDeclarationList().size());
            } else {
                System.out.println("报关单目录为空");
            }
        }

        // 设置表格
        setupDeclarationListTable();

        // 如果有报关单，选择第一个
        if (tblList != null && tblList.getModel().getRowCount() > 0) {
            tblList.setRowSelectionInterval(0, 0);
            String declarationId = tblList.getValueAt(0, 0).toString();
            displayDeclarationDetails(declarationId);
        }
    }

    private void sendCustomsWorkRequest(CustomsDeclaration declaration) {
        try {
            // 创建物流工作请求
            LogisticsWorkRequest request = new LogisticsWorkRequest();

            // 设置报关单信息
            request.setDeclarationId(declaration.getDeclarationId());
            request.setShipmentId(declaration.getShipmentId());
            request.setDeclarationType(declaration.getDeclarationType());
            request.setConsignor(declaration.getConsignor());
            request.setConsignee(declaration.getConsignee());
            request.setCountryOfOrigin(declaration.getCountryOfOrigin());
            request.setDestinationCountry(declaration.getDestinationCountry());
            request.setNotes(declaration.getNotes());

            // 设置发送者和接收者
            request.setSender(userAccount);

            // 查找海关组织
            Organization customsOrg = null;
            for (Network network : EcoSystem.getInstance().getNetworkList()) {
                for (Enterprise e : network.getEnterpriseDirectory().getEnterpriseList()) {
                    if (e instanceof LogisticsGroupEnterprise) {
                        for (Organization org : e.getOrganizationDirectory().getOrganizationList()) {
                            if (org instanceof CustomsLiaisonOrganization) {
                                customsOrg = org;
                                break;
                            }
                        }
                    }
                }
                if (customsOrg != null) {
                    break;
                }
            }

            // 将请求添加到海关组织的工作队列
            if (customsOrg != null) {
                customsOrg.getWorkQueue().getWorkRequestList().add(request);
                System.out.println("Logistics work request sent to customs: " + request.getDeclarationId());
            } else {
                throw new Exception("No customs organization found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error sending customs request: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
