/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui.LogisticsRole;

import Business.ConfigureASystem;
import Business.DB4OUtil.DB4OUtil;
import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Organization.LogisticsOrganization;
import Business.UserAccount.UserAccount;
import Business.Logistics.Shipment;
import Business.Logistics.TrackingInfo;
import Business.Organization.Organization;
import Business.WorkQueue.WarehouseWorkRequest;
import Business.WorkQueue.WorkRequest;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import jdk.tools.jlink.internal.Platform;

/**
 *
 * @author zhuchenyan
 */
public class ShipmentPanel extends javax.swing.JPanel {

    private JPanel userProcessContainer;
    private UserAccount userAccount;
    private Enterprise enterprise;
    private LogisticsOrganization organization;
    private JPanel mapPanel;
    private JEditorPane mapView;
    private static final String GOOGLE_MAPS_API_KEY = "AIzaSyCsxjLs6wmSHnIQDKTxAtynpNfMAecSWqY";
    private JPanel detailsCardsPanel;
    private CardLayout detailsCardLayout;
    private JPanel parentPanel;

    /**
     * Creates new form Shipment
     */
    public ShipmentPanel(JPanel userProcessContainer, UserAccount account,
            Enterprise enterprise, LogisticsOrganization organization, JPanel parentPanel) {

        initComponents();
        initializeMapComponents();
        setupDetailsCards();

        this.organization = ConfigureASystem.logisticsOrg;
        this.userProcessContainer = userProcessContainer;
        this.userAccount = account;
        this.enterprise = enterprise;
        this.parentPanel = parentPanel;

        populateTable();

    }

    private void initializeMapComponents() {
        // Ensure trackPathJPanel uses BorderLayout
        trackPathJPanel.setLayout(new BorderLayout());

        // Create map view component
        mapView = new JEditorPane();
        mapView.setContentType("text/html");
        mapView.setEditable(false);

        // Add scroll support
        JScrollPane scrollPane = new JScrollPane(mapView);

        // Add map view to trackPathJPanel
        trackPathJPanel.add(scrollPane, BorderLayout.CENTER);

        // Add panel for tracking information
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        infoPanel.setPreferredSize(new Dimension(300, 0)); // Set info panel width

        // Add to the right side of trackPathJPanel
        trackPathJPanel.add(infoPanel, BorderLayout.EAST);
    }

    private void setupDetailsCards() {
        // 创建主要的详情容器面板
        JPanel shipmentDetailsContainer = new JPanel(new BorderLayout());

        // 创建导航按钮面板
        JPanel detailsNavigationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        detailsNavigationPanel.add(btnBasicInfo);
        detailsNavigationPanel.add(btnCustomsInfo);
        detailsNavigationPanel.add(btnPackageInfo);
        detailsNavigationPanel.add(btnFinancialInfo);

        // 创建详情内容的卡片布局面板
        detailsCardsPanel = new JPanel();
        detailsCardLayout = new CardLayout();
        detailsCardsPanel.setLayout(detailsCardLayout);

        // 创建基本信息内容面板
        JPanel shipmentBasicInfoContent = new JPanel(new GridLayout(0, 2, 10, 10));
        shipmentBasicInfoContent.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 添加基本信息字段到内容面板
        shipmentBasicInfoContent.add(lblTraNo);
        shipmentBasicInfoContent.add(txtTrcNo);
        shipmentBasicInfoContent.add(lblShippingDate);
        shipmentBasicInfoContent.add(txtShippingDate);
        shipmentBasicInfoContent.add(lblShippingMethod);
        shipmentBasicInfoContent.add(txtShippingMethod);
        shipmentBasicInfoContent.add(lblDestination);
        shipmentBasicInfoContent.add(txtDestination);
        shipmentBasicInfoContent.add(lblStatus);
        shipmentBasicInfoContent.add(txtStatus);

        // 创建其他详情面板
        JPanel shipmentCustomsContent = createCustomsInfoPanel();
        JPanel shipmentPackageContent = createPackageInfoPanel();
        JPanel shipmentFinancialContent = createFinancialInfoPanel();

        // 将所有内容面板添加到卡片布局
        detailsCardsPanel.add(shipmentBasicInfoContent, "BasicInfo");
        detailsCardsPanel.add(shipmentCustomsContent, "CustomsInfo");
        detailsCardsPanel.add(shipmentPackageContent, "PackageInfo");
        detailsCardsPanel.add(shipmentFinancialContent, "FinancialInfo");

        // 组装主容器面板
        shipmentDetailsContainer.add(detailsNavigationPanel, BorderLayout.NORTH);
        shipmentDetailsContainer.add(detailsCardsPanel, BorderLayout.CENTER);

        // 创建并添加更新按钮面板
        JPanel actionButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actionButtonPanel.add(btnUpdate);
        shipmentDetailsContainer.add(actionButtonPanel, BorderLayout.SOUTH);

        // 清除并更新basicInfoJPanel
        basicInfoJPanel.removeAll();
        basicInfoJPanel.setLayout(new BorderLayout());
        basicInfoJPanel.add(shipmentDetailsContainer);

        // 显示基本信息面板
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

        btnBack = new javax.swing.JButton();
        lblLogisticsTracking = new javax.swing.JLabel();
        btnRefresh = new javax.swing.JButton();
        lblTrackingNo = new javax.swing.JLabel();
        txtSearchBox = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblShipment = new javax.swing.JTable();
        btnViewDetails = new javax.swing.JButton();
        btnAddNewShip = new javax.swing.JButton();
        btnUpdateStatus = new javax.swing.JButton();
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
        btnBasicInfo = new javax.swing.JButton();
        btnCustomsInfo = new javax.swing.JButton();
        btnPackageInfo = new javax.swing.JButton();
        btnFinancialInfo = new javax.swing.JButton();
        trackPathJPanel = new javax.swing.JPanel();
        lblTrackPath = new javax.swing.JLabel();
        btnShip = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(1450, 800));
        setMinimumSize(new java.awt.Dimension(1450, 800));

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

        btnAddNewShip.setText("Add New Shipment");
        btnAddNewShip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddNewShipActionPerformed(evt);
            }
        });

        btnUpdateStatus.setText("Update Status");
        btnUpdateStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateStatusActionPerformed(evt);
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

        btnBasicInfo.setText("Basic Info");
        btnBasicInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBasicInfoActionPerformed(evt);
            }
        });

        btnCustomsInfo.setText("Customs Info");
        btnCustomsInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCustomsInfoActionPerformed(evt);
            }
        });

        btnPackageInfo.setText("Package Info");
        btnPackageInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPackageInfoActionPerformed(evt);
            }
        });

        btnFinancialInfo.setText("Financial Info");
        btnFinancialInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFinancialInfoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout basicInfoJPanelLayout = new javax.swing.GroupLayout(basicInfoJPanel);
        basicInfoJPanel.setLayout(basicInfoJPanelLayout);
        basicInfoJPanelLayout.setHorizontalGroup(
            basicInfoJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, basicInfoJPanelLayout.createSequentialGroup()
                .addGroup(basicInfoJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(basicInfoJPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(basicInfoJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(basicInfoJPanelLayout.createSequentialGroup()
                                .addGroup(basicInfoJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblTraNo)
                                    .addComponent(lblShippingDate)
                                    .addComponent(lblShippingMethod))
                                .addGap(37, 37, 37)
                                .addGroup(basicInfoJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtShippingDate, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTrcNo, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtShippingMethod, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, basicInfoJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(basicInfoJPanelLayout.createSequentialGroup()
                                    .addGap(34, 34, 34)
                                    .addGroup(basicInfoJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(basicInfoJPanelLayout.createSequentialGroup()
                                            .addComponent(lblDestination)
                                            .addGap(37, 37, 37)
                                            .addComponent(txtDestination, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(basicInfoJPanelLayout.createSequentialGroup()
                                            .addComponent(lblStatus)
                                            .addGap(37, 37, 37)
                                            .addComponent(txtStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGroup(basicInfoJPanelLayout.createSequentialGroup()
                                    .addComponent(btnBasicInfo)
                                    .addGap(32, 32, 32)
                                    .addGroup(basicInfoJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblTrackDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(basicInfoJPanelLayout.createSequentialGroup()
                                            .addComponent(btnCustomsInfo)
                                            .addGap(43, 43, 43)
                                            .addComponent(btnPackageInfo)
                                            .addGap(45, 45, 45)
                                            .addComponent(btnFinancialInfo)))))))
                    .addGroup(basicInfoJPanelLayout.createSequentialGroup()
                        .addGap(0, 464, Short.MAX_VALUE)
                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(93, 93, 93))
        );
        basicInfoJPanelLayout.setVerticalGroup(
            basicInfoJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(basicInfoJPanelLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(lblTrackDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(basicInfoJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBasicInfo)
                    .addComponent(btnCustomsInfo)
                    .addComponent(btnPackageInfo)
                    .addComponent(btnFinancialInfo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addGroup(basicInfoJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTrcNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTraNo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(basicInfoJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtShippingDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblShippingDate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(basicInfoJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtShippingMethod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblShippingMethod, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(basicInfoJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDestination, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDestination, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(basicInfoJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnUpdate)
                .addGap(17, 17, 17))
        );

        trackPathJPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblTrackPath.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTrackPath.setText("Track Path（Google Map API）");

        javax.swing.GroupLayout trackPathJPanelLayout = new javax.swing.GroupLayout(trackPathJPanel);
        trackPathJPanel.setLayout(trackPathJPanelLayout);
        trackPathJPanelLayout.setHorizontalGroup(
            trackPathJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(trackPathJPanelLayout.createSequentialGroup()
                .addGap(149, 149, 149)
                .addComponent(lblTrackPath, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(181, Short.MAX_VALUE))
        );
        trackPathJPanelLayout.setVerticalGroup(
            trackPathJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(trackPathJPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblTrackPath, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(317, Short.MAX_VALUE))
        );

        btnShip.setText("Ship");
        btnShip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShipActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblLogisticsTracking)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnRefresh)
                        .addGap(78, 78, 78)
                        .addComponent(lblTrackingNo)
                        .addGap(18, 18, 18)
                        .addComponent(txtSearchBox, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(btnSearch))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnViewDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(btnAddNewShip, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnUpdateStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnShip, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGap(6, 6, 6)
                            .addComponent(basicInfoJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(trackPathJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 1403, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnShip, btnUpdateStatus});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(btnBack)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnRefresh)
                            .addComponent(lblLogisticsTracking)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSearchBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTrackingNo)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(btnSearch)))
                .addGap(21, 21, 21)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnViewDetails)
                    .addComponent(btnAddNewShip)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnUpdateStatus)
                        .addComponent(btnShip)))
                .addGap(61, 61, 61)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(basicInfoJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(trackPathJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 31, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        // 返回上一个面板前，刷新物流协调员主页的表格
    if (parentPanel instanceof LogisticsCoordinatorHP) {
        ((LogisticsCoordinatorHP) parentPanel).refreshTables();
    }
    
    // 返回上一个面板
    userProcessContainer.remove(this);
    CardLayout layout = (CardLayout) userProcessContainer.getLayout();
    layout.previous(userProcessContainer);

    }//GEN-LAST:event_btnBackActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        // TODO add your handling code here:
        populateTable();
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        String searchQuery = txtSearchBox.getText().trim();
        if (!searchQuery.isEmpty()) {
            searchShipment(searchQuery);
        }

    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnViewDetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewDetailsActionPerformed
        int selectedRow = tblShipment.getSelectedRow();
        if (selectedRow >= 0) {
            displayShipmentDetails(selectedRow);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a shipment first", "Information", JOptionPane.INFORMATION_MESSAGE);
        }

    }//GEN-LAST:event_btnViewDetailsActionPerformed

    private void btnAddNewShipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddNewShipActionPerformed

//        
    }//GEN-LAST:event_btnAddNewShipActionPerformed

    private void btnUpdateStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateStatusActionPerformed
        int selectedRow = tblShipment.getSelectedRow();
        if (selectedRow >= 0) {
            updateShipmentStatus(selectedRow);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a shipment first", "Information", JOptionPane.INFORMATION_MESSAGE);
        }

    }//GEN-LAST:event_btnUpdateStatusActionPerformed

    private void txtTrcNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTrcNoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTrcNoActionPerformed

    private void txtShippingDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtShippingDateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtShippingDateActionPerformed

    private void txtShippingMethodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtShippingMethodActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtShippingMethodActionPerformed

    private void txtDestinationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDestinationActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDestinationActionPerformed

    private void txtStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStatusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStatusActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        updateShipmentInfo();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnShipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShipActionPerformed
        // TODO add your handling code here:
        int selectedRow = tblShipment.getSelectedRow();
    if (selectedRow < 0) {
        JOptionPane.showMessageDialog(this, "Please select a shipment to process");
        return;
    }

    // 获取选中行的信息
    String id = tblShipment.getValueAt(selectedRow, 0).toString();
    
    // 只处理 Pending 状态的订单
    if (!id.startsWith("Pending-")) {
        JOptionPane.showMessageDialog(this, "Can only ship pending orders");
        return;
    }
    
    // 获取表格中已有的目的地信息（这很关键）
    String existingDestination = tblShipment.getValueAt(selectedRow, 2).toString();

    // 获取物流组织实例
    LogisticsOrganization logisticsOrg = ConfigureASystem.getLogisticsOrganization();
    if (logisticsOrg == null) {
        return;
    }

    try {
        // 1. 获取运输方式
        String[] methods = {"Express", "Air Freight", "Sea Freight", "Ground"};
        String shippingMethod = (String) JOptionPane.showInputDialog(
            this,
            "Select shipping method:",
            "Shipping Method",
            JOptionPane.QUESTION_MESSAGE,
            null,
            methods,
            methods[0]
        );
        
        if (shippingMethod == null) {
            return; // 用户取消选择
        }
        
        // 2. 确认目的地，如果是默认值则请求用户输入
        String destination = existingDestination;
        if (destination == null || destination.isEmpty() || 
            destination.equals("Not specified") || destination.equals("To be assigned")) {
            
            destination = JOptionPane.showInputDialog(this, 
                "Enter the destination for this shipment:", 
                "Destination", 
                JOptionPane.QUESTION_MESSAGE);
            
            if (destination == null || destination.trim().isEmpty()) {
                destination = "Not specified";
            }
        }

        // 3. 创建新的 Shipment
        String shipmentId = id.replace("Pending-", "");
        String trackingNumber = "TRK" + System.currentTimeMillis();
        
        Shipment shipment = new Shipment();
        shipment.setTrackingNumber(trackingNumber);
        shipment.setShipDate(new Date());
        shipment.setShippingMethod(shippingMethod);
        shipment.setDestination(destination);  // 设置目的地
        shipment.setShipmentStatus("Shipped");
        
        // 4. 计算预计送达时间
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        switch (shippingMethod) {
            case "Express":
                cal.add(Calendar.DAY_OF_MONTH, 3);  // Express 是 3 天
                break;
            case "Air Freight":
                cal.add(Calendar.DAY_OF_MONTH, 7);  // Air Freight 是 7 天
                break;
            case "Sea Freight":
                cal.add(Calendar.DAY_OF_MONTH, 30); // Sea Freight 是 30 天
                break;
            case "Ground":
                cal.add(Calendar.DAY_OF_MONTH, 15); // Ground 是 15 天
                break;
        }
        shipment.setEstimatedDeliveryDate(cal.getTime());

        // 5. 添加初始追踪信息
        TrackingInfo trackInfo = new TrackingInfo();
        trackInfo.setShipmentId(shipment.getShipmentId());
        trackInfo.setTimestamp(new Date());
        trackInfo.setLocation("Shanghai Warehouse");
        trackInfo.setDescription("Package processed and ready for shipping");
        trackInfo.setStatus("Shipped");
        shipment.addTrackingInfo(trackInfo);

        // 6. 将 Shipment 添加到目录
        logisticsOrg.getShipmentDirectory().addShipment(shipment);

        // 7. 更新对应的工作请求状态
        for (WorkRequest request : logisticsOrg.getWorkQueue().getWorkRequestList()) {
            if (request instanceof WarehouseWorkRequest) {
                WarehouseWorkRequest warehouseRequest = (WarehouseWorkRequest) request;
                if (warehouseRequest.getShipmentId().equals(shipmentId)) {
                    
                    // 更新工作请求中的信息
                    warehouseRequest.setStatus("Shipped");
                    warehouseRequest.setTrackingNumber(trackingNumber);
                    warehouseRequest.setShippingMethod(shippingMethod);
                    warehouseRequest.setDestination(destination);  // 更新目的地
                    warehouseRequest.setEstimatedDeliveryDate(shipment.getEstimatedDeliveryDate());
                    warehouseRequest.setResolveDate(new Date());
                    
                    // 从工作请求复制信息到 Shipment
                    if (warehouseRequest.getProductName() != null) {
                        shipment.setProductName(warehouseRequest.getProductName());
                    }
                    
                    if (warehouseRequest.getQuantity() > 0) {
                        shipment.setQuantity(warehouseRequest.getQuantity());
                    }
                    
                    break;
                }
            }
        }

        // 8. 保存系统更新
        EcoSystem system = EcoSystem.getInstance();
        DB4OUtil.getInstance().storeSystem(system);
        
        // 9. 刷新表格
        populateTable();
        
        // 10. 刷新 LogisticsCoordinatorHP 的表格
        if (parentPanel instanceof LogisticsCoordinatorHP) {
            ((LogisticsCoordinatorHP) parentPanel).refreshTables();
        }

        // 11. 显示成功消息
        JOptionPane.showMessageDialog(this,
            "Shipment processed successfully!\nTracking Number: " + trackingNumber + 
            "\nDestination: " + destination);

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this,
            "Error processing shipment: " + e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_btnShipActionPerformed

    private void btnCustomsInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCustomsInfoActionPerformed
        // TODO add your handling code here:
        detailsCardLayout.show(detailsCardsPanel, "CustomsInfo");
    }//GEN-LAST:event_btnCustomsInfoActionPerformed

    private void btnPackageInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPackageInfoActionPerformed
        // TODO add your handling code here:
        detailsCardLayout.show(detailsCardsPanel, "PackageInfo");
    }//GEN-LAST:event_btnPackageInfoActionPerformed

    private void btnFinancialInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFinancialInfoActionPerformed
        // TODO add your handling code here:
        detailsCardLayout.show(detailsCardsPanel, "FinancialInfo");
    }//GEN-LAST:event_btnFinancialInfoActionPerformed

    private void btnBasicInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBasicInfoActionPerformed
        // TODO add your handling code here:
        detailsCardLayout.show(detailsCardsPanel, "BasicInfo");
    }//GEN-LAST:event_btnBasicInfoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel basicInfoJPanel;
    private javax.swing.JButton btnAddNewShip;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnBasicInfo;
    private javax.swing.JButton btnCustomsInfo;
    private javax.swing.JButton btnFinancialInfo;
    private javax.swing.JButton btnPackageInfo;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnShip;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btnUpdateStatus;
    private javax.swing.JButton btnViewDetails;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDestination;
    private javax.swing.JLabel lblLogisticsTracking;
    private javax.swing.JLabel lblShippingDate;
    private javax.swing.JLabel lblShippingMethod;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblTraNo;
    private javax.swing.JLabel lblTrackDetails;
    private javax.swing.JLabel lblTrackPath;
    private javax.swing.JLabel lblTrackingNo;
    private javax.swing.JTable tblShipment;
    private javax.swing.JPanel trackPathJPanel;
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

        // 获取物流组织实例
        LogisticsOrganization logisticsOrg = ConfigureASystem.getLogisticsOrganization();
        if (logisticsOrg == null) {
            return;
        }

        // 1. 添加所有已有的 Shipments（历史订单）
        if (logisticsOrg.getShipmentDirectory() != null) {
            for (Shipment shipment : logisticsOrg.getShipmentDirectory().getShipments()) {
                Object[] row = new Object[6];
                row[0] = shipment.getTrackingNumber();
                row[1] = shipment.getShippingMethod();
                row[2] = shipment.getDestination() != null ? shipment.getDestination() : "Not specified";;
                row[3] = shipment.getShipmentStatus();
                row[4] = shipment.getShipDate() != null
                        ? new SimpleDateFormat("yyyy-MM-dd").format(shipment.getShipDate()) : "";
                row[5] = shipment.getEstimatedDeliveryDate() != null
                        ? new SimpleDateFormat("yyyy-MM-dd").format(shipment.getEstimatedDeliveryDate()) : "";
                model.addRow(row);
            }
        }

        // 2. 添加待处理的订单（从 tblPendingTasks）
        if (logisticsOrg.getWorkQueue() != null) {
            for (WorkRequest request : logisticsOrg.getWorkQueue().getWorkRequestList()) {
                if (request instanceof WarehouseWorkRequest) {
                    WarehouseWorkRequest warehouseRequest = (WarehouseWorkRequest) request;

                    // 只添加状态为 Pending 的请求
                    if ("Pending".equals(warehouseRequest.getStatus())) {
                        Object[] row = new Object[6];
                        row[0] = "Pending-" + warehouseRequest.getShipmentId(); // 使用 shipmentId
                        row[1] = warehouseRequest.getShippingMethod() != null ? 
                            warehouseRequest.getShippingMethod() : "To be assigned";  // 待分配运输方式
                        row[2] = warehouseRequest.getDestination() != null
                                ? warehouseRequest.getDestination() : "To be assigned";
                        row[3] = warehouseRequest.getStatus();
                        row[4] = new SimpleDateFormat("yyyy-MM-dd").format(warehouseRequest.getRequestDate());
                        row[5] = "To be determined";
                        model.addRow(row);
                    }
                }
            }
        }

    }

    private void searchShipment(String query) {
        DefaultTableModel model = (DefaultTableModel) tblShipment.getModel();
        model.setRowCount(0); // 清空表格

        if (organization != null && organization.getShipmentDirectory() != null) {
            ArrayList<Shipment> shipments = organization.getShipmentDirectory().getShipments();

            for (Shipment shipment : shipments) {
                // 搜索匹配运单号或目的地或状态
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

        // 获取货件
        Shipment shipment = findShipmentByTrackingNumber(trackingNumber);
        if (shipment != null) {
            // 在详情面板显示信息
            txtTrcNo.setText(shipment.getTrackingNumber());
            txtShippingDate.setText(shipment.getShipDate() != null ? shipment.getShipDate().toString() : "");
            txtShippingMethod.setText(shipment.getShippingMethod());
            txtDestination.setText(shipment.getDestination());
            txtStatus.setText(shipment.getShipmentStatus());

            // 激活基本信息Tab
            btnBasicInfo.doClick();
        }
    }

    private void updateShipmentStatus(int selectedRow) {
        String orderId = tblShipment.getValueAt(selectedRow, 0).toString();

        // 获取货件
        Shipment shipment = organization.getShipmentDirectory().findShipmentByOrderId(orderId);

        if (shipment != null) {
            // 显示状态更新对话框
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
                // 更新状态（会自动添加跟踪记录）
                shipment.setShipmentStatus(newStatus);

                // 刷新表格
                populateTable();
                JOptionPane.showMessageDialog(this,
                        "Status has been updated successfully",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void updateShipmentInfo() {
        String trackingNumber = txtTrcNo.getText();
        if (trackingNumber == null || trackingNumber.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a shipment first", "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Shipment shipment = findShipmentByTrackingNumber(trackingNumber);
        if (shipment != null) {
            // Update shipment information
            shipment.setShipmentStatus(txtStatus.getText());
            shipment.setDestination(txtDestination.getText());
            shipment.setShippingMethod(txtShippingMethod.getText());

            // More field updates...
            // Refresh table
            populateTable();
            JOptionPane.showMessageDialog(this, "Shipment information has been updated", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private Shipment findShipmentByTrackingNumber(String trackingNumber) {
        if (organization != null && organization.getShipmentDirectory() != null) {
            return organization.getShipmentDirectory().findShipmentByTrackingNumber(trackingNumber);
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

    private void showTrackingMap(Shipment shipment) {
        try {
            // 清除现有内容
            trackPathJPanel.removeAll();
            trackPathJPanel.setLayout(new BorderLayout());

            // 创建标题
            JLabel titleLabel = new JLabel("Shipment Tracking Map", JLabel.CENTER);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
            trackPathJPanel.add(titleLabel, BorderLayout.NORTH);

            // 测试一个最简单的静态地图URL
            String apiKey = "AIzaSyCsxjLs6wmSHnIQDKTxAtynpNfMAecSWqY";
            String staticMapUrl = "https://maps.googleapis.com/maps/api/staticmap?center=31.2304,121.4737&zoom=10&size=600x400&key=" + apiKey;

            System.out.println("Testing simple static map URL: " + staticMapUrl);

            // 请求并显示地图图像
            URL url = new URL(staticMapUrl);
            BufferedImage image = ImageIO.read(url);

            if (image != null) {
                JLabel mapLabel = new JLabel(new ImageIcon(image));
                trackPathJPanel.add(new JScrollPane(mapLabel), BorderLayout.CENTER);

                // 添加信息面板（如果shipment不为空）
                if (shipment != null && shipment.getTrackingHistory() != null && !shipment.getTrackingHistory().isEmpty()) {
                    JPanel infoPanel = createSimpleTrackingInfoPanel(shipment);
                    trackPathJPanel.add(infoPanel, BorderLayout.EAST);
                }
            } else {
                JLabel errorLabel = new JLabel("Unable to load map image.", JLabel.CENTER);
                trackPathJPanel.add(errorLabel, BorderLayout.CENTER);
            }

            // 刷新面板
            trackPathJPanel.revalidate();
            trackPathJPanel.repaint();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error displaying map: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // 简化版的跟踪信息面板
    private JPanel createSimpleTrackingInfoPanel(Shipment shipment) {
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        infoPanel.setPreferredSize(new Dimension(250, 400));

        JLabel titleLabel = new JLabel("Shipment Information");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        infoPanel.add(titleLabel);
        infoPanel.add(Box.createVerticalStrut(10));

        infoPanel.add(new JLabel("Tracking #: " + shipment.getTrackingNumber()));
        infoPanel.add(new JLabel("Status: " + shipment.getShipmentStatus()));
        infoPanel.add(new JLabel("Origin: " + shipment.getOrigin()));
        infoPanel.add(new JLabel("Destination: " + shipment.getDestination()));

        return infoPanel;
    }

    // 创建跟踪信息面板
    private JPanel createTrackingInfoPanel(Shipment shipment, List<TrackingInfo> trackingHistory) {
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 添加基本运输信息
        JLabel titleLabel = new JLabel("Shipment Information");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        infoPanel.add(titleLabel);

        JPanel basicInfoPanel = new JPanel();
        basicInfoPanel.setLayout(new GridLayout(0, 1));
        basicInfoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        basicInfoPanel.setBorder(BorderFactory.createEtchedBorder());

        basicInfoPanel.add(new JLabel("Tracking Number: " + shipment.getTrackingNumber()));
        basicInfoPanel.add(new JLabel("Order ID: " + shipment.getOrderId()));
        basicInfoPanel.add(new JLabel("Status: " + shipment.getShipmentStatus()));
        basicInfoPanel.add(new JLabel("Origin: " + shipment.getOrigin()));
        basicInfoPanel.add(new JLabel("Destination: " + shipment.getDestination()));
        basicInfoPanel.add(new JLabel("Current Location: " + shipment.getCurrentLocation()));

        infoPanel.add(basicInfoPanel);
        infoPanel.add(Box.createVerticalStrut(20));

        // 添加跟踪历史标题
        JLabel historyLabel = new JLabel("Tracking History");
        historyLabel.setFont(new Font("Arial", Font.BOLD, 16));
        historyLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        infoPanel.add(historyLabel);

        // 创建一个带滚动条的跟踪历史面板
        JPanel historyListPanel = new JPanel();
        historyListPanel.setLayout(new BoxLayout(historyListPanel, BoxLayout.Y_AXIS));

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for (int i = 0; i < trackingHistory.size(); i++) {
            TrackingInfo info = trackingHistory.get(i);

            JPanel trackPointPanel = new JPanel();
            trackPointPanel.setLayout(new GridLayout(0, 1));
            trackPointPanel.setBorder(BorderFactory.createTitledBorder((i + 1) + ". " + info.getLocation()));
            trackPointPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

            trackPointPanel.add(new JLabel("Time: " + dateFormat.format(info.getTimestamp())));
            trackPointPanel.add(new JLabel("Status: " + info.getStatus()));
            trackPointPanel.add(new JLabel("Description: " + info.getDescription()));

            historyListPanel.add(trackPointPanel);
            historyListPanel.add(Box.createVerticalStrut(10));
        }

        JScrollPane historyScrollPane = new JScrollPane(historyListPanel);
        historyScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        infoPanel.add(historyScrollPane);

        return infoPanel;
    }

    private JPanel createControlPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // 添加返回按钮
        JButton btnBack = new JButton("Back to list");
        btnBack.addActionListener(e -> {
            CardLayout layout = (CardLayout) getLayout();
            layout.show(this, "listPanel");
        });
        panel.add(btnBack);

        // 添加刷新按钮
        JButton btnRefresh = new JButton("Refresh Map");
        btnRefresh.addActionListener(e -> {
            int selectedRow = tblShipment.getSelectedRow();
            if (selectedRow >= 0) {
                String orderId = tblShipment.getValueAt(selectedRow, 0).toString();
                Shipment shipment = organization.getShipmentDirectory().findShipmentByOrderId(orderId);
                if (shipment != null) {
                    showTrackingMap(shipment);
                }
            }
        });
        panel.add(btnRefresh);

        return panel;
    }

    // Update right side information panel
    private void updateTrackingInfo(Shipment shipment, List<TrackingInfo> trackingHistory) {
        JPanel infoPanel = (JPanel) trackPathJPanel.getComponent(1); // Get right side info panel
        infoPanel.removeAll();

        // Add shipment basic information
        JLabel titleLabel = new JLabel("Shipment Information");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        infoPanel.add(titleLabel);

        addInfoLabel(infoPanel, "Order ID: " + shipment.getOrderId());
        addInfoLabel(infoPanel, "Tracking Number: " + shipment.getTrackingNumber());
        addInfoLabel(infoPanel, "Current Status: " + shipment.getShipmentStatus());
        addInfoLabel(infoPanel, "Destination: " + shipment.getDestination());

        // Add separator
        infoPanel.add(new JSeparator());

        // Add tracking history
        JLabel historyLabel = new JLabel("Tracking History");
        historyLabel.setFont(new Font("Arial", Font.BOLD, 16));
        infoPanel.add(historyLabel);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (TrackingInfo info : trackingHistory) {
            JPanel trackPoint = new JPanel();
            trackPoint.setLayout(new BoxLayout(trackPoint, BoxLayout.Y_AXIS));
            trackPoint.setBorder(BorderFactory.createEtchedBorder());

            addInfoLabel(trackPoint, "Time: " + dateFormat.format(info.getTimestamp()));
            addInfoLabel(trackPoint, "Location: " + info.getLocation());
            addInfoLabel(trackPoint, "Status: " + info.getStatus());
            addInfoLabel(trackPoint, "Description: " + info.getDescription());

            infoPanel.add(trackPoint);
            infoPanel.add(Box.createVerticalStrut(10));
        }

        infoPanel.revalidate();
        infoPanel.repaint();
    }

    private void addInfoLabel(JPanel panel, String text) {
        JLabel label = new JLabel(text);
        label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panel.add(label);
    }

    // Helper method to escape JavaScript strings
    private String escapeJavaScript(String str) {
        if (str == null) {
            return "";
        }
        return str.replace("\\", "\\\\")
                .replace("'", "\\'")
                .replace("\"", "\\\"")
                .replace("\r", "\\r")
                .replace("\n", "\\n");
    }

    private JPanel createCustomsInfoPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10));

        // Add customs information fields
        JLabel lblDeclarationNo = new JLabel("Declaration No:");
        JTextField txtDeclarationNo = new JTextField();

        JLabel lblDeclarationStatus = new JLabel("Declaration Status:");
        JTextField txtDeclarationStatus = new JTextField();

        JLabel lblImportDuty = new JLabel("Import Duty:");
        JTextField txtImportDuty = new JTextField();

        JLabel lblInspectionRequired = new JLabel("Inspection Required:");
        JTextField txtInspectionRequired = new JTextField();

        panel.add(lblDeclarationNo);
        panel.add(txtDeclarationNo);
        panel.add(lblDeclarationStatus);
        panel.add(txtDeclarationStatus);
        panel.add(lblImportDuty);
        panel.add(txtImportDuty);
        panel.add(lblInspectionRequired);
        panel.add(txtInspectionRequired);

        return panel;
    }

    private JPanel createPackageInfoPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10));

        // Add package information fields
        JLabel lblWeight = new JLabel("Weight (kg):");
        JTextField txtWeight = new JTextField();

        JLabel lblDimensions = new JLabel("Dimensions (cm):");
        JTextField txtDimensions = new JTextField();

        JLabel lblPackageType = new JLabel("Package Type:");
        JTextField txtPackageType = new JTextField();

        JLabel lblSpecialHandling = new JLabel("Special Handling:");
        JTextField txtSpecialHandling = new JTextField();

        panel.add(lblWeight);
        panel.add(txtWeight);
        panel.add(lblDimensions);
        panel.add(txtDimensions);
        panel.add(lblPackageType);
        panel.add(txtPackageType);
        panel.add(lblSpecialHandling);
        panel.add(txtSpecialHandling);

        return panel;
    }

    private JPanel createFinancialInfoPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10));

        // Add financial information fields
        JLabel lblShippingCost = new JLabel("Shipping Cost:");
        JTextField txtShippingCost = new JTextField();

        JLabel lblInsuranceCost = new JLabel("Insurance Cost:");
        JTextField txtInsuranceCost = new JTextField();

        JLabel lblTaxes = new JLabel("Taxes:");
        JTextField txtTaxes = new JTextField();

        JLabel lblTotalCost = new JLabel("Total Cost:");
        JTextField txtTotalCost = new JTextField();

        panel.add(lblShippingCost);
        panel.add(txtShippingCost);
        panel.add(lblInsuranceCost);
        panel.add(txtInsuranceCost);
        panel.add(lblTaxes);
        panel.add(txtTaxes);
        panel.add(lblTotalCost);
        panel.add(txtTotalCost);

        return panel;
    }

    private void processNewShipment(Shipment shipment) {
        // 1. 验证订单信息
        if (shipment == null || shipment.getOrderId() == null) {
            return;
        }

        // 2. 分配运输方式
        String[] methods = {"Air Freight", "Sea Freight", "Ground", "Express"};
        String method = (String) JOptionPane.showInputDialog(
                this,
                "Select shipping method:",
                "Process New Shipment",
                JOptionPane.QUESTION_MESSAGE,
                null,
                methods,
                "Ground");

        if (method != null) {
            // 3. 更新运输信息
            shipment.setShippingMethod(method);

            // 4. 计算预计送达时间
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            switch (method) {
                case "Air Freight":
                    cal.add(Calendar.DAY_OF_MONTH, 7);
                    break;
                case "Sea Freight":
                    cal.add(Calendar.DAY_OF_MONTH, 30);
                    break;
                case "Express":
                    cal.add(Calendar.DAY_OF_MONTH, 3);
                    break;
                default:
                    cal.add(Calendar.DAY_OF_MONTH, 15);
            }
            shipment.setEstimatedDeliveryDate(cal.getTime());

            // 5. 更新状态
            shipment.setShipmentStatus(Shipment.STATUS_PROCESSING);

            // 6. 刷新显示
            populateTable();
        }
    }

    /**
     * 根据行号显示货运详情
     *
     * @param rowIndex 要显示的行号
     */
    public void showShipmentDetails(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < tblShipment.getRowCount()) {
            displayShipmentDetails(rowIndex);
        }
    }

    public JTable getTblShipment() {
        return tblShipment;
    }
}
