/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui.MerchantRole;

import Business.ConfigureASystem;
import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Logistics.Shipment;
import Business.Logistics.TrackingInfo;
import Business.Network.Network;
import Business.Organization.LogisticsOrganization;
import Business.Organization.Organization;
import Business.UserAccount.UserAccount;
import Business.WorkQueue.ReceivingWorkRequest;
import Business.WorkQueue.WarehouseWorkRequest;
import Business.WorkQueue.WorkRequest;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.JTableHeader;

/**
 *
 * @author wangsiting
 */
public class MerchantTrackingJPanel extends javax.swing.JPanel {

    private JPanel detailsCardsPanel;
    private JPanel userProcessContainer;
    private UserAccount userAccount;
    private Enterprise enterprise;
    private LogisticsOrganization organization;
    private JPanel mapPanel;
   
    private CardLayout detailsCardLayout;
    private final JPanel parentPanel;

    // Remove the other button declarations, keep only necessary ones
    /**
     * Creates new form MerchantTrackingJPanel
     */
    public MerchantTrackingJPanel(JPanel userProcessContainer, UserAccount account,
            Enterprise enterprise, LogisticsOrganization organization, JPanel parentPanel) {

        initComponents();
        
        setupDetailsCards();

        this.organization = ConfigureASystem.logisticsOrg;
        this.userProcessContainer = userProcessContainer;
        this.userAccount = account;
        this.enterprise = enterprise;
        this.parentPanel = parentPanel;

        populateTable();
        
        // Apply unified UI style
        setupTheme();
    }
    
    /**
     * Apply consistent UI theme to all components
     */
    private void setupTheme() {
        // Set panel background colors
        this.setBackground(new Color(240, 245, 255));
        jPanel1.setBackground(new Color(240, 245, 255));
        basicInfoJPanel.setBackground(new Color(240, 245, 255));
        
        // Style buttons
        styleAllButtons();
        
        // Style text fields
        styleAllTextFields();
        
        // Style labels
        styleAllLabels();
        
        // Style table
        styleTable(tblShipment);
    }
    
    /**
     * Apply consistent styling to all buttons
     */
    private void styleAllButtons() {
        styleButton(btnBack);
        styleButton(btnRefresh);
        styleButton(btnSearch);
        styleButton(btnViewDetails);
        styleButton(btnBasicInfo);
        styleButton(btnUpdate);
        styleButton(btnSave3);
    }
    
    /**
     * Apply consistent styling to a button
     *
     * @param button Button to style
     */
    private void styleButton(JButton button) {
        button.setBackground(new Color(26, 79, 156)); // 中蓝色
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        // Add a subtle border with rounded corners
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(13, 60, 130), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        button.setFont(new Font("Helvetica Neue", Font.BOLD, 14));
    }
    
    /**
     * Apply consistent styling to all text fields
     */
    private void styleAllTextFields() {
        styleTextField(txtSearchBox);
        styleTextField(txtTrcNo);
        styleTextField(txtShippingDate);
        styleTextField(txtShippingMethod);
        styleTextField(txtDestination);
        styleTextField(txtStatus);
    }
    
    /**
     * Apply consistent styling to a text field
     *
     * @param textField TextField to style
     */
    private void styleTextField(JTextField textField) {
        textField.setBackground(new Color(245, 245, 250)); // 浅灰白色背景
        textField.setForeground(new Color(13, 25, 51)); // 深蓝色文字
        textField.setCaretColor(new Color(26, 79, 156)); // 中蓝色光标
        textField.setBorder(BorderFactory.createLineBorder(new Color(90, 141, 224), 1));
        textField.setFont(new Font("Helvetica Neue", Font.PLAIN, 14));
    }
    
    /**
     * Apply consistent styling to all labels
     */
    private void styleAllLabels() {
        // Style title labels
        styleTitleLabel(lblLogisticsTracking);
        styleTitleLabel(lblTrackDetails);
        
        // Style regular labels
        styleLabel(lblTrackingNo);
        styleLabel(lblTraNo);
        styleLabel(lblShippingDate);
        styleLabel(lblShippingMethod);
        styleLabel(lblDestination);
        styleLabel(lblStatus);
    }
    
    /**
     * Apply title label styling
     *
     * @param label Label to style
     */
    private void styleTitleLabel(JLabel label) {
        label.setForeground(new Color(26, 79, 156));
        label.setFont(new Font("Helvetica Neue", Font.BOLD, 20));
    }
    
    /**
     * Apply regular label styling
     *
     * @param label Label to style
     */
    private void styleLabel(JLabel label) {
        label.setForeground(new Color(26, 79, 156));
        label.setFont(new Font("Helvetica Neue", Font.BOLD, 14));
    }
    
    /**
     * Style table with consistent theme
     *
     * @param table Table to style
     */
    private void styleTable(JTable table) {
        // Style table header
        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(26, 79, 156));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Helvetica Neue", Font.BOLD, 14));
        header.setBorder(new LineBorder(new Color(13, 60, 130)));
        
        // Style table - using darker colors for better visibility
        table.setBackground(new Color(240, 240, 250)); // Slightly darker background
        table.setForeground(new Color(0, 0, 0)); // Black text for maximum contrast
        table.setGridColor(new Color(180, 195, 235)); // Darker grid lines
        table.setSelectionBackground(new Color(90, 141, 224));
        table.setSelectionForeground(Color.WHITE);
        table.setFont(new Font("Helvetica Neue", Font.BOLD, 14)); // Bold font for better visibility
        table.setRowHeight(30); // Increase row height for better readability
        
        // Set alternating row colors with more contrast
        table.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? new Color(240, 240, 250) : new Color(220, 220, 235));
                    c.setForeground(new Color(0, 0, 0)); // Ensure text is always black for maximum contrast
                }
                return c;
            }
        });
        
        // Style scroll pane
        jScrollPane1.setBorder(new LineBorder(new Color(26, 79, 156)));
    }

    private void setupDetailsCards() {
        // Create details content cards panel
        detailsCardsPanel = new JPanel();
        detailsCardLayout = new CardLayout();
        detailsCardsPanel.setLayout(detailsCardLayout);

        // 设置标题样式
        lblTrackDetails.setFont(new Font("Helvetica Neue", Font.BOLD, 20));
        lblTrackDetails.setHorizontalAlignment(JLabel.CENTER);
        
        // 设置标签和输入框样式
        Font labelFont = new Font("Helvetica Neue", Font.BOLD, 14);
        lblTraNo.setFont(labelFont);
        lblShippingDate.setFont(labelFont);
        lblShippingMethod.setFont(labelFont);
        lblDestination.setFont(labelFont);
        lblStatus.setFont(labelFont);
        
        // 美化按钮
        btnBasicInfo.setFont(new Font("Helvetica Neue", Font.BOLD, 14));
        btnBasicInfo.setBackground(new java.awt.Color(51, 153, 255));
        btnBasicInfo.setForeground(java.awt.Color.WHITE);
        btnBasicInfo.setFocusPainted(false);
        
        btnUpdate.setFont(new Font("Helvetica Neue", Font.BOLD, 14));
        btnUpdate.setBackground(new java.awt.Color(0, 102, 204));
        btnUpdate.setForeground(java.awt.Color.WHITE);
        btnUpdate.setFocusPainted(false);
        
        btnSave3.setFont(new Font("Helvetica Neue", Font.BOLD, 14));
        btnSave3.setBackground(new java.awt.Color(46, 139, 87));
        btnSave3.setForeground(java.awt.Color.WHITE);
        btnSave3.setFocusPainted(false);
        
        // 直接在basicInfoJPanel中创建面板，不使用桌面设计器生成的布局
        basicInfoJPanel.removeAll();
        basicInfoJPanel.setLayout(new BorderLayout(10, 10));
        basicInfoJPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // 顶部面板 - 标题和导航
        JPanel topPanel = new JPanel(new BorderLayout(0, 10));
        topPanel.add(lblTrackDetails, BorderLayout.NORTH);
        
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        navPanel.add(btnBasicInfo);
        topPanel.add(navPanel, BorderLayout.CENTER);
        
        // 中间面板 - 字段信息
        JPanel fieldsPanel = new JPanel(new GridLayout(5, 2, 20, 15));
        fieldsPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // 使所有文本框不可编辑
        txtTrcNo.setEditable(false);
        txtShippingDate.setEditable(false);
        txtShippingMethod.setEditable(false);
        txtDestination.setEditable(false);
        txtStatus.setEditable(false);
        
        // 文本框样式
        txtTrcNo.setFont(new Font("Helvetica Neue", Font.PLAIN, 14));
        txtShippingDate.setFont(new Font("Helvetica Neue", Font.PLAIN, 14));
        txtShippingMethod.setFont(new Font("Helvetica Neue", Font.PLAIN, 14));
        txtDestination.setFont(new Font("Helvetica Neue", Font.PLAIN, 14));
        txtStatus.setFont(new Font("Helvetica Neue", Font.PLAIN, 14));
        
        // 添加字段到面板
        fieldsPanel.add(lblTraNo);
        fieldsPanel.add(txtTrcNo);
        fieldsPanel.add(lblShippingDate);
        fieldsPanel.add(txtShippingDate);
        fieldsPanel.add(lblShippingMethod);
        fieldsPanel.add(txtShippingMethod);
        fieldsPanel.add(lblDestination);
        fieldsPanel.add(txtDestination);
        fieldsPanel.add(lblStatus);
        fieldsPanel.add(txtStatus);
        
        // 底部面板 - 按钮
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnSave3);
        
        // 将所有面板添加到主面板
        basicInfoJPanel.add(topPanel, BorderLayout.NORTH);
        basicInfoJPanel.add(fieldsPanel, BorderLayout.CENTER);
        basicInfoJPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // 添加面板到卡片布局
        detailsCardsPanel.add(basicInfoJPanel, "BasicInfo");
        detailsCardLayout.show(detailsCardsPanel, "BasicInfo");
        
        // 刷新面板
        basicInfoJPanel.revalidate();
        basicInfoJPanel.repaint();
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
        btnBack = new javax.swing.JButton();
        lblLogisticsTracking = new javax.swing.JLabel();
        btnRefresh = new javax.swing.JButton();
        lblTrackingNo = new javax.swing.JLabel();
        txtSearchBox = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblShipment = new javax.swing.JTable();
        btnViewDetails = new javax.swing.JButton();
        basicInfoJPanel = new javax.swing.JPanel();
        lblTraNo = new javax.swing.JLabel();
        txtTrcNo = new javax.swing.JTextField();
        lblShippingDate = new javax.swing.JLabel();
        txtShippingDate = new javax.swing.JTextField();
        lblShippingMethod = new javax.swing.JLabel();
        txtShippingMethod = new javax.swing.JTextField();
        lblDestination = new javax.swing.JLabel();
        txtDestination = new javax.swing.JTextField();
        lblStatus = new javax.swing.JLabel();
        txtStatus = new javax.swing.JTextField();
        btnUpdate = new javax.swing.JButton();
        lblTrackDetails = new javax.swing.JLabel();
        btnSave3 = new javax.swing.JButton();
        btnBasicInfo = new javax.swing.JButton();

        jPanel1.setMaximumSize(new java.awt.Dimension(1450, 800));
        jPanel1.setMinimumSize(new java.awt.Dimension(1450, 800));

        btnBack.setText("<< Back");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        lblLogisticsTracking.setFont(new java.awt.Font("Helvetica", 0, 18)); // NOI18N
        lblLogisticsTracking.setText("Logistics Tracking:");

        btnRefresh.setText("Refresh List");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        lblTrackingNo.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        lblTrackingNo.setText("Tracking No.");

        btnSearch.setText("Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        tblShipment.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        tblShipment.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Shipping Method", "Destination", "Status", "Shipping Date", "Estimated Delivery Date"
            }
        ));
        jScrollPane1.setViewportView(tblShipment);

        btnViewDetails.setText("View Details");
        btnViewDetails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewDetailsActionPerformed(evt);
            }
        });

        basicInfoJPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblTraNo.setText("Tracking No:");

        txtTrcNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTrcNoActionPerformed(evt);
            }
        });

        lblShippingDate.setText("Shipping Date:");

        txtShippingDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtShippingDateActionPerformed(evt);
            }
        });

        lblShippingMethod.setText("Shipping Method:");

        txtShippingMethod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtShippingMethodActionPerformed(evt);
            }
        });

        lblDestination.setText("Destination:");

        txtDestination.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDestinationActionPerformed(evt);
            }
        });

        lblStatus.setText("Status:");

        txtStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStatusActionPerformed(evt);
            }
        });

        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        lblTrackDetails.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTrackDetails.setText("Tracking Details");

        btnSave3.setText("Save");
        btnSave3.setEnabled(false);
        btnSave3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSave3ActionPerformed(evt);
            }
        });

        btnBasicInfo.setText("Basic Info");
        btnBasicInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBasicInfoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout basicInfoJPanelLayout = new javax.swing.GroupLayout(basicInfoJPanel);
        basicInfoJPanel.setLayout(basicInfoJPanelLayout);
        basicInfoJPanelLayout.setHorizontalGroup(
            basicInfoJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(basicInfoJPanelLayout.createSequentialGroup()
                .addContainerGap(47, Short.MAX_VALUE)
                .addGroup(basicInfoJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnBasicInfo)
                    .addComponent(lblTrackDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 681, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(basicInfoJPanelLayout.createSequentialGroup()
                        .addGroup(basicInfoJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblTraNo)
                            .addComponent(lblShippingDate)
                            .addComponent(lblShippingMethod)
                            .addComponent(lblStatus)
                            .addComponent(lblDestination))
                        .addGap(37, 37, 37)
                        .addGroup(basicInfoJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtShippingDate, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTrcNo, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtShippingMethod, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDestination, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(basicInfoJPanelLayout.createSequentialGroup()
                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48)
                        .addComponent(btnSave3, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(60, Short.MAX_VALUE))
        );
        basicInfoJPanelLayout.setVerticalGroup(
            basicInfoJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(basicInfoJPanelLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(lblTrackDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(btnBasicInfo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addGroup(basicInfoJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTrcNo, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTraNo, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(basicInfoJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtShippingDate, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblShippingDate, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(basicInfoJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtShippingMethod, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblShippingMethod, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(basicInfoJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDestination, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDestination, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(basicInfoJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(basicInfoJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSave3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblLogisticsTracking)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnRefresh)
                                .addGap(78, 78, 78)
                                .addComponent(lblTrackingNo)
                                .addGap(18, 18, 18)
                                .addComponent(txtSearchBox, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(btnSearch))
                            .addComponent(btnViewDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1403, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(basicInfoJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 800, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(btnBack)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnRefresh)
                            .addComponent(lblLogisticsTracking)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSearchBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTrackingNo)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(btnSearch)))
                .addGap(21, 21, 21)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnViewDetails)
                .addGap(30, 30, 30)
                .addComponent(basicInfoJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 31, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnViewDetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewDetailsActionPerformed
        int selectedRow = tblShipment.getSelectedRow();
        if (selectedRow >= 0) {
            displayShipmentDetails(selectedRow);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a shipment first", "Information", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnViewDetailsActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        String searchQuery = txtSearchBox.getText().trim();
        if (!searchQuery.isEmpty()) {
            searchShipment(searchQuery);
        }
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        populateTable();
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        if (parentPanel instanceof MerchantHP) {
            ((MerchantHP) parentPanel).refreshTables();
        }

        userProcessContainer.remove(this);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.previous(userProcessContainer);
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnBasicInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBasicInfoActionPerformed
        // TODO add your handling code here:
        detailsCardLayout.show(detailsCardsPanel, "BasicInfo");
    }//GEN-LAST:event_btnBasicInfoActionPerformed

    private void btnSave3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSave3ActionPerformed
        String trackingNumber = txtTrcNo.getText();
        if (trackingNumber == null || trackingNumber.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a shipment first", "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        if (organization != null && organization.getShipmentDirectory() != null) {
            Shipment shipment = organization.getShipmentDirectory().findShipmentByTrackingNumber(trackingNumber);
            if (shipment != null) {
                String newStatus = txtStatus.getText();
                if (newStatus != null && !newStatus.isEmpty()) {
                    shipment.setShipmentStatus(newStatus);
                    
                    TrackingInfo trackingInfo = new TrackingInfo();
                    trackingInfo.setLocation("Merchant Update");
                    trackingInfo.setStatus(newStatus);
                    trackingInfo.setTimestamp(new java.util.Date());
                    trackingInfo.setDescription("Status updated by merchant: " + newStatus);
                    
                    shipment.addTrackingInfo(trackingInfo);
                    
                    // 当状态为"Package Received"时，更新产品状态
                    if ("Package Received".equals(newStatus)) {
                        updateProductInventory(shipment);
                    }
                    
                    populateTable();
                    
                    JOptionPane.showMessageDialog(this, 
                        "Shipment status has been updated to: " + newStatus,
                        "Status Updated", 
                        JOptionPane.INFORMATION_MESSAGE);
                    
                    txtStatus.setEditable(false);
                    btnSave3.setEnabled(false);
                }
            }
        }
    }//GEN-LAST:event_btnSave3ActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        String trackingNumber = txtTrcNo.getText();
        if (trackingNumber == null || trackingNumber.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a shipment first", "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        txtStatus.setEditable(true);
        btnSave3.setEnabled(true);
        
        String[] merchantStatusOptions = {
            "Package Received",
            "Package Not Received",
            "Wrong Item Received",
            "Damaged Package",
            "Return Initiated",
            "Other Issue"
        };
        
        String selectedStatus = (String) JOptionPane.showInputDialog(
            this,
            "Select shipment status to update:",
            "Update Status",
            JOptionPane.QUESTION_MESSAGE,
            null,
            merchantStatusOptions,
            txtStatus.getText());
        
        if (selectedStatus != null && !selectedStatus.isEmpty()) {
            txtStatus.setText(selectedStatus);
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void txtStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStatusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStatusActionPerformed

    private void txtDestinationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDestinationActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDestinationActionPerformed

    private void txtShippingMethodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtShippingMethodActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtShippingMethodActionPerformed

    private void txtShippingDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtShippingDateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtShippingDateActionPerformed

    private void txtTrcNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTrcNoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTrcNoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel basicInfoJPanel;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnBasicInfo;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSave3;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btnViewDetails;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDestination;
    private javax.swing.JLabel lblLogisticsTracking;
    private javax.swing.JLabel lblShippingDate;
    private javax.swing.JLabel lblShippingMethod;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblTraNo;
    private javax.swing.JLabel lblTrackDetails;
    private javax.swing.JLabel lblTrackingNo;
    private javax.swing.JTable tblShipment;
    private javax.swing.JTextField txtDestination;
    private javax.swing.JTextField txtSearchBox;
    private javax.swing.JTextField txtShippingDate;
    private javax.swing.JTextField txtShippingMethod;
    private javax.swing.JTextField txtStatus;
    private javax.swing.JTextField txtTrcNo;
    // End of variables declaration//GEN-END:variables

    private void populateTable() {
        DefaultTableModel model = (DefaultTableModel) tblShipment.getModel();
        model.setRowCount(0);

        LogisticsOrganization logisticsOrg = ConfigureASystem.getLogisticsOrganization();
        if (logisticsOrg == null) {
            JOptionPane.showMessageDialog(this, 
                    "Unable to connect to logistics system.",
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        System.out.println("Loading shipments from logistics organization...");
        
        // Load existing shipments
        if (logisticsOrg.getShipmentDirectory() != null && logisticsOrg.getShipmentDirectory().getShipments() != null) {
            int count = 0;
            for (Shipment shipment : logisticsOrg.getShipmentDirectory().getShipments()) {
                if (shipment == null) {
                    continue;
                }
                
                Object[] row = new Object[6];
                row[0] = shipment.getTrackingNumber();
                row[1] = shipment.getShippingMethod() != null ? shipment.getShippingMethod() : "Standard";
                row[2] = shipment.getDestination() != null ? shipment.getDestination() : "Not specified";
                
                String status = shipment.getShipmentStatus();
                if (status == null || status.isEmpty()) {
                    status = "Processing";
                }
                
                boolean merchantUpdated = false;
                List<TrackingInfo> trackingInfoList = shipment.getTrackingHistory();
                if (trackingInfoList != null) {
                    for (TrackingInfo info : trackingInfoList) {
                        if (info != null && "Merchant Update".equals(info.getLocation())) {
                            merchantUpdated = true;
                            status = info.getStatus() + " (Merchant Updated)";
                            break;
                        }
                    }
                }
                
                row[3] = status;
                
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                row[4] = shipment.getShipDate() != null
                        ? dateFormat.format(shipment.getShipDate()) : "Not shipped yet";
                row[5] = shipment.getEstimatedDeliveryDate() != null
                        ? dateFormat.format(shipment.getEstimatedDeliveryDate()) : "To be determined";
                
                model.addRow(row);
                count++;
            }
            System.out.println("Loaded " + count + " shipments from shipment directory");
        } else {
            System.out.println("Shipment directory is null or empty");
        }

        // Load pending warehouse work requests
        if (logisticsOrg.getWorkQueue() != null && logisticsOrg.getWorkQueue().getWorkRequestList() != null) {
            int count = 0;
            for (WorkRequest request : logisticsOrg.getWorkQueue().getWorkRequestList()) {
                if (request instanceof WarehouseWorkRequest) {
                    WarehouseWorkRequest warehouseRequest = (WarehouseWorkRequest) request;

                    if ("Pending".equals(warehouseRequest.getStatus())) {
                        Object[] row = new Object[6];
                        row[0] = "Pending-" + warehouseRequest.getShipmentId();
                        row[1] = warehouseRequest.getShippingMethod() != null
                                ? warehouseRequest.getShippingMethod() : "To be assigned";
                        row[2] = warehouseRequest.getDestination() != null
                                ? warehouseRequest.getDestination() : "To be assigned";
                        row[3] = "Pending Shipment";
                        
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        row[4] = dateFormat.format(warehouseRequest.getRequestDate());
                        row[5] = "To be determined";
                        
                        model.addRow(row);
                        count++;
                    }
                }
            }
            System.out.println("Loaded " + count + " pending requests from work queue");
        }

        // Find and load receiving work requests for current user's organization
        if (userAccount != null) {
            // Get organization based on user role
            Organization currentOrg = null;
            for (Network network : EcoSystem.getInstance().getNetworkList()) {
                for (Enterprise enterprise : network.getEnterpriseDirectory().getEnterpriseList()) {
                    for (Organization org : enterprise.getOrganizationDirectory().getOrganizationList()) {
                        // Check if this user belongs to this organization by checking user accounts
                        for (UserAccount account : org.getUserAccountDirectory().getUserAccountList()) {
                            if (account.getUsername().equals(userAccount.getUsername())) {
                                currentOrg = org;
                                break;
                            }
                        }
                        if (currentOrg != null) {
                            break;
                        }
                    }
                    if (currentOrg != null) {
                        break;
                    }
                }
                if (currentOrg != null) {
                    break;
                }
            }

            // If found current organization, check its work queue for receiving requests
            if (currentOrg != null) {
                int count = 0;
                for (WorkRequest request : currentOrg.getWorkQueue().getWorkRequestList()) {
                    if (request instanceof ReceivingWorkRequest) {
                        ReceivingWorkRequest receivingRequest = (ReceivingWorkRequest) request;

                        Object[] row = new Object[6];
                        row[0] = receivingRequest.getTrackingNumber();
                        row[1] = receivingRequest.getShippingMethod() != null
                                ? receivingRequest.getShippingMethod() : "Standard";
                        row[2] = receivingRequest.getDestination() != null
                                ? receivingRequest.getDestination() : "Not specified";
                        row[3] = receivingRequest.getStatus();

                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        row[4] = dateFormat.format(receivingRequest.getRequestDate());
                        row[5] = receivingRequest.getEstimatedDeliveryDate() != null
                                ? dateFormat.format(receivingRequest.getEstimatedDeliveryDate()) : "To be determined";

                        model.addRow(row);
                        count++;
                    }
                }
                System.out.println("Loaded " + count + " receiving notifications for " + currentOrg.getName());
        } else {
                System.out.println("Could not determine current user's organization");
            }

            // Also check user's personal work queue for notifications
            int count = 0;
            for (WorkRequest request : userAccount.getWorkQueue().getWorkRequestList()) {
                if (request instanceof ReceivingWorkRequest) {
                    ReceivingWorkRequest receivingRequest = (ReceivingWorkRequest) request;

                    Object[] row = new Object[6];
                    row[0] = receivingRequest.getTrackingNumber();
                    row[1] = receivingRequest.getShippingMethod() != null
                            ? receivingRequest.getShippingMethod() : "Standard";
                    row[2] = receivingRequest.getDestination() != null
                            ? receivingRequest.getDestination() : "Not specified";
                    row[3] = receivingRequest.getStatus();

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    row[4] = dateFormat.format(receivingRequest.getRequestDate());
                    row[5] = receivingRequest.getEstimatedDeliveryDate() != null
                            ? dateFormat.format(receivingRequest.getEstimatedDeliveryDate()) : "To be determined";

                    model.addRow(row);
                    count++;
                }
            }
            System.out.println("Loaded " + count + " receiving notifications from user's personal work queue");
        }
        
        if (model.getRowCount() == 0) {
            System.out.println("No shipments found");
            JOptionPane.showMessageDialog(this, 
                    "No shipments found in the system.",
                    "Information", 
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            System.out.println("Total shipments loaded: " + model.getRowCount());
        }
    }

    private void searchShipment(String query) {
        DefaultTableModel model = (DefaultTableModel) tblShipment.getModel();
        model.setRowCount(0);

        if (organization != null && organization.getShipmentDirectory() != null) {
            ArrayList<Shipment> shipments = organization.getShipmentDirectory().getShipments();

            for (Shipment shipment : shipments) {
                if (shipment.getTrackingNumber().contains(query)
                        || shipment.getDestination().contains(query)
                        || shipment.getShipmentStatus().contains(query)) {

                    Object[] row = new Object[6];
                    row[0] = shipment.getTrackingNumber();
                    row[1] = shipment.getShipDate();
                    row[2] = shipment.getShippingMethod();
                    row[3] = shipment.getDestination();
                    row[4] = shipment.getShipmentStatus();
                    row[5] = shipment.getEstimatedDeliveryDate();

                    model.addRow(row);
                }
            }
        }
    }

    private void displayShipmentDetails(int selectedRow) {
        String trackingNumber = tblShipment.getValueAt(selectedRow, 0).toString();

        Shipment shipment = findShipmentByTrackingNumber(trackingNumber);
        if (shipment != null) {
            txtTrcNo.setText(shipment.getTrackingNumber());
            txtShippingDate.setText(shipment.getShipDate() != null
                    ? new SimpleDateFormat("yyyy-MM-dd").format(shipment.getShipDate()) : "");
            txtShippingMethod.setText(shipment.getShippingMethod());
            txtDestination.setText(shipment.getDestination());
            txtStatus.setText(shipment.getShipmentStatus());

            txtTrcNo.setEditable(false);
            txtShippingDate.setEditable(false);
            txtShippingMethod.setEditable(false); 
            txtDestination.setEditable(false);
            txtStatus.setEditable(false);
            btnSave3.setEnabled(false);

            // Ensure we're showing basic info (only one card in our layout)
            detailsCardLayout.show(detailsCardsPanel, "BasicInfo");
        }
    }

    private void updateShipmentStatus(int selectedRow) {
        String orderId = tblShipment.getValueAt(selectedRow, 0).toString();

        Shipment shipment = organization.getShipmentDirectory().findShipmentByOrderId(orderId);

        if (shipment != null) {
            String[] statuses = {
                Shipment.STATUS_PENDING,
                Shipment.STATUS_PROCESSING,
                Shipment.STATUS_SHIPPED,
                Shipment.STATUS_IN_TRANSIT,
                Shipment.STATUS_DELIVERING,
                Shipment.STATUS_DELIVERED,
                Shipment.STATUS_EXCEPTION
            };

            String newStatus = (String) JOptionPane.showInputDialog(
                    this,
                    "Select new status:",
                    "Update Status",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    statuses,
                    shipment.getShipmentStatus());

            if (newStatus != null && !newStatus.isEmpty()) {
                shipment.setShipmentStatus(newStatus);

                populateTable();
                JOptionPane.showMessageDialog(this,
                        "Status has been updated successfully",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void updateShipmentInfo() {
        btnUpdateActionPerformed(null);
    }

    private Shipment findShipmentByTrackingNumber(String trackingNumber) {
        if (trackingNumber == null || trackingNumber.isEmpty()) {
            return null;
        }
        
        // Handle pending shipments (from warehouse work requests)
        if (trackingNumber.startsWith("Pending-")) {
            String shipmentId = trackingNumber.substring("Pending-".length());
            
            if (organization != null && organization.getWorkQueue() != null) {
                for (WorkRequest request : organization.getWorkQueue().getWorkRequestList()) {
                    if (request instanceof WarehouseWorkRequest) {
                        WarehouseWorkRequest warehouseRequest = (WarehouseWorkRequest) request;
                        if (warehouseRequest.getShipmentId() != null
                                && warehouseRequest.getShipmentId().equals(shipmentId)) {
                            
                            Shipment pendingShipment = new Shipment();
                            pendingShipment.setTrackingNumber(trackingNumber);
                            pendingShipment.setShipmentStatus("Pending");
                            pendingShipment.setDestination(warehouseRequest.getDestination());
                            pendingShipment.setShippingMethod(warehouseRequest.getShippingMethod());
                            
                            return pendingShipment;
                        }
                    }
                }
            }
            return null;
        }
        
        // Check shipment directory first
        if (organization != null && organization.getShipmentDirectory() != null) {
            Shipment shipment = organization.getShipmentDirectory().findShipmentByTrackingNumber(trackingNumber);
            if (shipment != null) {
                return shipment;
            }
        }

        // Find current organization
        Organization currentOrg = null;
        for (Network network : EcoSystem.getInstance().getNetworkList()) {
            for (Enterprise enterprise : network.getEnterpriseDirectory().getEnterpriseList()) {
                for (Organization org : enterprise.getOrganizationDirectory().getOrganizationList()) {
                    // Check if user belongs to this organization
                    for (UserAccount account : org.getUserAccountDirectory().getUserAccountList()) {
                        if (account.getUsername().equals(userAccount.getUsername())) {
                            currentOrg = org;
                            break;
                        }
                    }
                    if (currentOrg != null) {
                        break;
                    }
                }
                if (currentOrg != null) {
                    break;
                }
            }
            if (currentOrg != null) {
                break;
            }
        }

        // Check organization's work queue for receiving requests
        if (currentOrg != null) {
            for (WorkRequest request : currentOrg.getWorkQueue().getWorkRequestList()) {
                if (request instanceof ReceivingWorkRequest) {
                    ReceivingWorkRequest receivingRequest = (ReceivingWorkRequest) request;
                    if (trackingNumber.equals(receivingRequest.getTrackingNumber())) {
                        // Create shipment object from receiving work request
                        Shipment receivingShipment = new Shipment();
                        receivingShipment.setShipmentId(receivingRequest.getShipmentId());
                        receivingShipment.setTrackingNumber(receivingRequest.getTrackingNumber());
                        receivingShipment.setProductName(receivingRequest.getProductName());
                        receivingShipment.setQuantity(receivingRequest.getQuantity());
                        receivingShipment.setDestination(receivingRequest.getDestination());
                        receivingShipment.setShippingMethod(receivingRequest.getShippingMethod());
                        receivingShipment.setShipmentStatus(receivingRequest.getStatus());
                        receivingShipment.setShipDate(receivingRequest.getRequestDate());
                        receivingShipment.setEstimatedDeliveryDate(receivingRequest.getEstimatedDeliveryDate());

                        return receivingShipment;
                    }
                }
            }
        }

        // Check user's personal work queue for notifications
        if (userAccount != null && userAccount.getWorkQueue() != null) {
            for (WorkRequest request : userAccount.getWorkQueue().getWorkRequestList()) {
                if (request instanceof ReceivingWorkRequest) {
                    ReceivingWorkRequest receivingRequest = (ReceivingWorkRequest) request;
                    if (trackingNumber.equals(receivingRequest.getTrackingNumber())) {
                        // Create shipment object from receiving work request
                        Shipment receivingShipment = new Shipment();
                        receivingShipment.setShipmentId(receivingRequest.getShipmentId());
                        receivingShipment.setTrackingNumber(receivingRequest.getTrackingNumber());
                        receivingShipment.setProductName(receivingRequest.getProductName());
                        receivingShipment.setQuantity(receivingRequest.getQuantity());
                        receivingShipment.setDestination(receivingRequest.getDestination());
                        receivingShipment.setShippingMethod(receivingRequest.getShippingMethod());
                        receivingShipment.setShipmentStatus(receivingRequest.getStatus());
                        receivingShipment.setShipDate(receivingRequest.getRequestDate());
                        receivingShipment.setEstimatedDeliveryDate(receivingRequest.getEstimatedDeliveryDate());

                        return receivingShipment;
                    }
                }
            }
        }

        return null;
    }

    private void updateShipmentLocation(int selectedRow) {
        String orderId = tblShipment.getValueAt(selectedRow, 0).toString();
        Shipment shipment = organization.getShipmentDirectory().findShipmentByOrderId(orderId);

        if (shipment != null) {
            String newLocation = JOptionPane.showInputDialog(
                    this,
                    "Enter new location:",
                    "Update Location",
                    JOptionPane.QUESTION_MESSAGE);

            if (newLocation != null && !newLocation.trim().isEmpty()) {
                shipment.updateLocation(newLocation.trim());
                populateTable();
                JOptionPane.showMessageDialog(this,
                        "Location has been updated successfully",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    // 添加新方法用于更新产品库存和状态
    private void updateProductInventory(Shipment shipment) {
        try {
            // 直接获取供应商实例
            Business.Supplier.Supplier supplier = Business.Role.MerchantRole.getDemoSupplier();
            
            if (supplier == null) {
                System.out.println("无法获取供应商实例");
                return;
            }
            
            System.out.println("找到供应商: " + supplier.getSupplyName());
            
            if (supplier.getProductCatalog() != null) {
                for (Business.Product.Product product : supplier.getProductCatalog()) {
                    // 检查是否匹配shipment中的产品名称
                    if (shipment.getProductName() != null && shipment.getProductName().equals(product.getProductName())) {
                        // 如果产品状态为"Requested"，则更新状态和数量
                        if ("Requested".equals(product.getStockStatus())) {
                            int currentQuantity = product.getQuantity();
                            int receivedQuantity = shipment.getQuantity();
                            
                            // 更新库存并重置状态
                            product.setQuantity(currentQuantity + receivedQuantity);
                            product.updateStockStatus(); // 自动设置为"Normal"或其他适当状态
                            product.setLastUpdated(new java.util.Date());
                            
                            System.out.println("产品状态已更新: " + product.getProductName() 
                                + " 从 Requested 到 " + product.getStockStatus()
                                + ", 数量从 " + currentQuantity + " 到 " + product.getQuantity());
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("更新产品库存时发生错误: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
