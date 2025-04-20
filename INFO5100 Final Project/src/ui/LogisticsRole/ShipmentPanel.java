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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
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
import javafx.application.Platform;


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
    private WebView webView;
private WebEngine webEngine;
    private Map<String, double[]> locationMap; // 定义位置映射

    /**
     * Creates new form Shipment
     */
    public ShipmentPanel(JPanel userProcessContainer, UserAccount account,
            Enterprise enterprise, LogisticsOrganization organization, JPanel parentPanel) {

        initComponents();
        initializeMapComponents();
        setupDetailsCards();
        initializeLocationMap();

        this.organization = ConfigureASystem.logisticsOrg;
        this.userProcessContainer = userProcessContainer;
        this.userAccount = account;
        this.enterprise = enterprise;
        this.parentPanel = parentPanel;

        populateTable();

    }

    private void initializeMapComponents() {
        // 确保trackPathJPanel使用BorderLayout布局
    trackPathJPanel.setLayout(new BorderLayout());
    
    // 初始化JavaFX线程和面板
    final JFXPanel jfxPanel = new JFXPanel();
    
    // 创建跟踪信息面板
    JPanel infoPanel = new JPanel();
    infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
    infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    infoPanel.setPreferredSize(new Dimension(300, 0)); // 设置信息面板宽度
    
    // 添加JavaFX面板到trackPathJPanel中央
    trackPathJPanel.add(jfxPanel, BorderLayout.CENTER);
    
    // 添加信息面板到trackPathJPanel右侧
    trackPathJPanel.add(infoPanel, BorderLayout.EAST);
    
    // 在JavaFX线程中初始化WebView
    Platform.runLater(new Runnable() {
    @Override
    public void run() {
        initFX(jfxPanel);
    }
});
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
                                            .addGap(39, 39, 39)
                                            .addComponent(btnPackageInfo)
                                            .addGap(45, 45, 45)
                                            .addComponent(btnFinancialInfo)))))))
                    .addGroup(basicInfoJPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
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
        if (shipment == null) {
        JOptionPane.showMessageDialog(this, 
                "未选择运输单", 
                "错误", 
                JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    // 获取跟踪历史
    List<TrackingInfo> trackingHistory = shipment.getTrackingHistory();
    
    if (trackingHistory == null || trackingHistory.isEmpty()) {
        // 如果没有跟踪点，显示基于原点和目的地的默认地图
        showDefaultMapForShipment(shipment);
        return;
    }
    
    // 创建带跟踪点的地图
    Platform.runLater(() -> {
        updateMapWithTrackingPoints(shipment, trackingHistory);
    });
    
    // 更新右侧信息面板
    JPanel infoPanel = (JPanel)trackPathJPanel.getComponent(1);
    updateTrackingInfo(shipment, trackingHistory, infoPanel);
    }

    
    
    /**
 * 显示以上海仓库和目的地为中心的默认地图
 * @param shipment 运输对象
 */
private void showDefaultMapForShipment(Shipment shipment) {
    Platform.runLater(() -> {
        // 上海仓库为固定起点
        String origin = "上海";
        String destination = shipment.getDestination();
        
        // 创建要在WebView中执行的JavaScript
        StringBuilder script = new StringBuilder();
        script.append("var map = window.map;");
        script.append("if (!map) return;");
        
        // 清除之前的标记和路径
        script.append("if (window.markers) {");
        script.append("  for (var i = 0; i < window.markers.length; i++) {");
        script.append("    window.markers[i].setMap(null);");
        script.append("  }");
        script.append("}");
        script.append("window.markers = [];");
        
        script.append("if (window.path) { window.path.setMap(null); }");
        
        // 添加上海仓库位置（固定）
        script.append("var shanghaiPos = {lat: 31.2304, lng: 121.4737};");
        
        // 添加上海仓库标记
        script.append("var originMarker = new google.maps.Marker({");
        script.append("  map: map,");
        script.append("  position: shanghaiPos,");
        script.append("  title: '").append(escapeJavaScript(origin)).append("',");
        script.append("  label: 'A',");
        script.append("  icon: {");
        script.append("    url: 'http://maps.google.com/mapfiles/ms/icons/blue-dot.png'");
        script.append("  }");
        script.append("});");
        
        script.append("window.markers.push(originMarker);");
        
        // 根据不同目的地设置位置
        script.append("var destPos;");
        script.append("var zoomLevel = 5;"); // 默认缩放级别
        
        if (destination.contains("Boston") || destination.contains("波士顿")) {
            script.append("destPos = {lat: 42.3601, lng: -71.0589};");
            script.append("zoomLevel = 4;"); // 全球视图
        } else if (destination.contains("LA") || destination.contains("Los Angeles") || destination.contains("洛杉矶")) {
            script.append("destPos = {lat: 34.0522, lng: -118.2437};");
            script.append("zoomLevel = 4;"); // 全球视图
        } else if (destination.contains("New York") || destination.contains("纽约")) {
            script.append("destPos = {lat: 40.7128, lng: -74.0060};");
            script.append("zoomLevel = 4;"); // 全球视图
        } else if (destination.contains("Cancun") || destination.contains("坎昆")) {
            script.append("destPos = {lat: 21.1619, lng: -86.8515};");
            script.append("zoomLevel = 4;"); // 全球视图
        } else if (destination.contains("Guanajuato")) {
            script.append("destPos = {lat: 21.0190, lng: -101.2574};");
            script.append("zoomLevel = 4;"); // 全球视图
        } else {
            // 如果是未知目的地，使用地理编码
            script.append("var geocoder = new google.maps.Geocoder();");
            script.append("geocoder.geocode({'address': '").append(escapeJavaScript(destination)).append("'}, function(results, status) {");
            script.append("  if (status === 'OK') {");
            script.append("    destPos = results[0].geometry.location;");
            script.append("    createDestinationMarker(destPos);");
            script.append("  } else {");
            script.append("    // 如果地理编码失败，使用默认位置");
            script.append("    destPos = {lat: 35.0, lng: -100.0};");
            script.append("    createDestinationMarker(destPos);");
            script.append("  }");
            script.append("});");
        }
        
        // 如果是已知目的地，直接创建标记
        if (!destination.contains("未知")) {
            script.append("createDestinationMarker(destPos);");
        }
        
        // 创建目的地标记的函数
        script.append("function createDestinationMarker(position) {");
        script.append("  var destinationMarker = new google.maps.Marker({");
        script.append("    map: map,");
        script.append("    position: position,");
        script.append("    title: '").append(escapeJavaScript(destination)).append("',");
        script.append("    label: 'B',");
        script.append("    icon: {");
        script.append("      url: 'http://maps.google.com/mapfiles/ms/icons/red-dot.png'");
        script.append("    }");
        script.append("  });");
        
        script.append("  window.markers.push(destinationMarker);");
        
        // 添加信息窗口
        script.append("  var infoContent = '<div><strong>目的地: ").append(escapeJavaScript(destination)).append("</strong><br>';");
        script.append("  infoContent += '状态: ' + getStatusBadge('").append(escapeJavaScript(shipment.getShipmentStatus())).append("') + '<br>';");
        if (shipment.getEstimatedDeliveryDate() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            script.append("  infoContent += '预计送达: ").append(escapeJavaScript(sdf.format(shipment.getEstimatedDeliveryDate()))).append("';");
        }
        script.append("  infoContent += '</div>';");
        
        script.append("  var infowindow = new google.maps.InfoWindow({");
        script.append("    content: infoContent");
        script.append("  });");
        
        script.append("  destinationMarker.addListener('click', function() {");
        script.append("    infowindow.open(map, destinationMarker);");
        script.append("  });");
        
        // 创建路径（如果不是Pending状态）
        if (!shipment.getShipmentStatus().equals("Pending") && !shipment.getShipmentStatus().equals("Processing")) {
            script.append("  // 添加行程路径");
            script.append("  var path = new google.maps.Polyline({");
            script.append("    path: [shanghaiPos, position],");
            script.append("    geodesic: true,");
            script.append("    strokeColor: '#FF0000',");
            script.append("    strokeOpacity: 0.7,");
            script.append("    strokeWeight: 2");
            script.append("  });");
            script.append("  path.setMap(map);");
            script.append("  window.path = path;");
        }
        
        // 调整地图以显示所有标记
        script.append("  var bounds = new google.maps.LatLngBounds();");
        script.append("  bounds.extend(shanghaiPos);");
        script.append("  bounds.extend(position);");
        script.append("  map.fitBounds(bounds);");
        script.append("  // 限制最大缩放级别");
        script.append("  var listener = google.maps.event.addListener(map, 'idle', function() {");
        script.append("    if (map.getZoom() > zoomLevel) map.setZoom(zoomLevel);");
        script.append("    google.maps.event.removeListener(listener);");
        script.append("  });");
        script.append("}");
        
        webEngine.executeScript(script.toString());
    });
    
    // 更新信息面板
    JPanel infoPanel = (JPanel)trackPathJPanel.getComponent(1);
    infoPanel.removeAll();
    
    // 添加基本运输信息
    JLabel titleLabel = new JLabel("运输信息");
    titleLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 16));
    titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    infoPanel.add(titleLabel);
    infoPanel.add(Box.createVerticalStrut(10));
    
    addInfoLabel(infoPanel, "运单号: " + shipment.getTrackingNumber());
    
    if (shipment.getShipDate() != null) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        addInfoLabel(infoPanel, "发货日期: " + dateFormat.format(shipment.getShipDate()));
    }
    
    addInfoLabel(infoPanel, "发货方式: " + shipment.getShippingMethod());
    addInfoLabel(infoPanel, "状态: " + shipment.getShipmentStatus());
    addInfoLabel(infoPanel, "发货地: 上海仓库");
    addInfoLabel(infoPanel, "目的地: " + shipment.getDestination());
    addInfoLabel(infoPanel, "当前位置: " + shipment.getCurrentLocation());
    
    if (shipment.getEstimatedDeliveryDate() != null) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        addInfoLabel(infoPanel, "预计送达: " + dateFormat.format(shipment.getEstimatedDeliveryDate()));
    }
    
    infoPanel.revalidate();
    infoPanel.repaint();
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
    private void updateTrackingInfo(Shipment shipment, List<TrackingInfo> trackingHistory, JPanel infoPanel) {
        infoPanel.removeAll();
    infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
    
    // 添加运输基本信息
    JLabel titleLabel = new JLabel("Shipment Information");
    titleLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 16));
    titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    infoPanel.add(titleLabel);
    infoPanel.add(Box.createVerticalStrut(10));

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
    
    /**
 * 初始化JavaFX WebView
 */
private void initFX(JFXPanel jfxPanel) {
    // 创建WebView和WebEngine
    webView = new WebView();
    webEngine = webView.getEngine();
    
    // 设置WebView填充容器
    webView.setPrefSize(800, 600);
    
    // 创建场景并设置到JFXPanel
    Scene scene = new Scene(webView);
    jfxPanel.setScene(scene);
    
    // 加载初始地图HTML
    loadMapHTML();
}

/**
 * 加载Google Maps到WebView
 */
private void loadMapHTML() {
    try {
        URL url = getClass().getResource("/src/map.html");
        if (url != null) {
            webEngine.load(url.toString());
        } else {
            // 如果无法找到HTML文件，使用内联HTML内容
            String mapHTML = createMapHTML();
            webEngine.loadContent(mapHTML);
        }
    } catch (Exception e) {
        e.printStackTrace();
        // 使用内联HTML作为备份
        String mapHTML = createMapHTML();
        webEngine.loadContent(mapHTML);
    }
}

/**
 * 创建Google Maps HTML内容
 */
private String createMapHTML() {
    StringBuilder html = new StringBuilder();
    html.append("<!DOCTYPE html>");
    html.append("<html>");
    html.append("<head>");
    html.append("<meta charset=\"UTF-8\">");
    html.append("<title>物流跟踪地图</title>");
    html.append("<style>");
    html.append("html, body, #map { height: 100%; margin: 0; padding: 0; }");
    html.append(".status-badge {");
    html.append("  display: inline-block;");
    html.append("  padding: 2px 8px;");
    html.append("  border-radius: 12px;");
    html.append("  color: white;");
    html.append("  font-weight: bold;");
    html.append("  font-size: 12px;");
    html.append("}");
    html.append(".status-pending { background-color: #f39c12; }");
    html.append(".status-processing { background-color: #3498db; }");
    html.append(".status-shipped { background-color: #2ecc71; }");
    html.append(".status-transit { background-color: #9b59b6; }");
    html.append(".status-delivering { background-color: #1abc9c; }");
    html.append(".status-delivered { background-color: #27ae60; }");
    html.append(".status-exception { background-color: #e74c3c; }");
    html.append("</style>");
    html.append("</head>");
    html.append("<body>");
    html.append("<div id=\"map\"></div>");
    html.append("<script>");
    
    // 初始化地图
    html.append("function initMap() {");
    html.append("  var map = new google.maps.Map(document.getElementById('map'), {");
    html.append("    zoom: 4,");
    html.append("    center: {lat: 31.2304, lng: 121.4737}"); // 默认以上海为中心
    html.append("  });");
    
    // 将map存储在window对象中以便后续访问
    html.append("  window.map = map;");
    html.append("}");

    // 定义获取状态样式的函数
    html.append("function getStatusBadge(status) {");
    html.append("  var statusClass = '';");
    html.append("  switch(status.toLowerCase()) {");
    html.append("    case 'pending': statusClass = 'status-pending'; break;");
    html.append("    case 'processing': statusClass = 'status-processing'; break;");
    html.append("    case 'shipped': statusClass = 'status-shipped'; break;");
    html.append("    case 'in transit': statusClass = 'status-transit'; break;");
    html.append("    case 'delivering': statusClass = 'status-delivering'; break;");
    html.append("    case 'delivered': statusClass = 'status-delivered'; break;");
    html.append("    case 'exception': statusClass = 'status-exception'; break;");
    html.append("    default: statusClass = 'status-pending';");
    html.append("  }");
    html.append("  return '<span class=\"status-badge ' + statusClass + '\">' + status + '</span>';");
    html.append("}");
    
    html.append("</script>");
    html.append("<script src=\"https://maps.googleapis.com/maps/api/js?key=").append(GOOGLE_MAPS_API_KEY).append("&callback=initMap\" async defer></script>");
    html.append("</body>");
    html.append("</html>");
    
    return html.toString();
}

    

    /**
 * 使用跟踪点更新地图
 * @param shipment 运输对象
 * @param trackingHistory 跟踪历史
 */
private void updateMapWithTrackingPoints(Shipment shipment, List<TrackingInfo> trackingHistory) {
    StringBuilder script = new StringBuilder();
    script.append("var map = window.map;");
    script.append("if (!map) return;");
    
    // 清除之前的标记
    script.append("if (window.markers) {");
    script.append("  for (var i = 0; i < window.markers.length; i++) {");
    script.append("    window.markers[i].setMap(null);");
    script.append("  }");
    script.append("}");
    script.append("window.markers = [];");
    
    // 清除之前的路径
    script.append("if (window.path) { window.path.setMap(null); }");
    
    // 预设位置映射
    script.append("var locationMap = {");
    script.append("  '上海': {lat: 31.2304, lng: 121.4737},");
    script.append("  '上海仓库': {lat: 31.2304, lng: 121.4737},");
    script.append("  '北京': {lat: 39.9042, lng: 116.4074},");
    script.append("  '广州': {lat: 23.1291, lng: 113.2644},");
    script.append("  '深圳': {lat: 22.5431, lng: 114.0579},");
    script.append("  '杭州': {lat: 30.2741, lng: 120.1551},");
    script.append("  '成都': {lat: 30.5728, lng: 104.0668},");
    script.append("  '重庆': {lat: 29.4316, lng: 106.9123},");
    script.append("  'Boston': {lat: 42.3601, lng: -71.0589},");
    script.append("  '波士顿': {lat: 42.3601, lng: -71.0589},");
    script.append("  'Los Angeles': {lat: 34.0522, lng: -118.2437},");
    script.append("  'LA': {lat: 34.0522, lng: -118.2437},");
    script.append("  '洛杉矶': {lat: 34.0522, lng: -118.2437},");
    script.append("  'New York': {lat: 40.7128, lng: -74.0060},");
    script.append("  '纽约': {lat: 40.7128, lng: -74.0060},");
    script.append("  'Cancun': {lat: 21.1619, lng: -86.8515},");
    script.append("  '坎昆': {lat: 21.1619, lng: -86.8515},");
    script.append("  'Guanajuato': {lat: 21.0190, lng: -101.2574}");
    script.append("};");
    
    // 创建用于路径的坐标数组
    script.append("var pathCoordinates = [];");
    
    // 添加起点标记（上海仓库）
    script.append("var originPos = locationMap['上海仓库'];");
    script.append("var originMarker = new google.maps.Marker({");
    script.append("  position: originPos,");
    script.append("  map: map,");
    script.append("  title: '上海仓库',");
    script.append("  label: 'A',");
    script.append("  icon: {");
    script.append("    url: 'http://maps.google.com/mapfiles/ms/icons/blue-dot.png'");
    script.append("  }");
    script.append("});");
    script.append("window.markers.push(originMarker);");
    script.append("pathCoordinates.push(originPos);");
    
    // 添加中间跟踪点
    script.append("var trackPoints = [];");
    for (int i = 0; i < trackingHistory.size(); i++) {
        TrackingInfo info = trackingHistory.get(i);
        String location = info.getLocation();
        String status = info.getStatus();
        String description = info.getDescription();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = dateFormat.format(info.getTimestamp());
        
        // 判断是否有经纬度数据
        if (info.getLatitude() != 0 && info.getLongitude() != 0) {
            script.append("trackPoints.push({");
            script.append("  location: '").append(escapeJavaScript(location)).append("',");
            script.append("  lat: ").append(info.getLatitude()).append(",");
            script.append("  lng: ").append(info.getLongitude()).append(",");
            script.append("  status: '").append(escapeJavaScript(status)).append("',");
            script.append("  description: '").append(escapeJavaScript(description)).append("',");
            script.append("  time: '").append(escapeJavaScript(time)).append("',");
            script.append("  index: ").append(i + 1);
            script.append("});");
        } else if (locationMap.containsKey(location)) {
            // 使用预设位置
            script.append("trackPoints.push({");
            script.append("  location: '").append(escapeJavaScript(location)).append("',");
            script.append("  lat: locationMap['").append(escapeJavaScript(location)).append("'].lat,");
            script.append("  lng: locationMap['").append(escapeJavaScript(location)).append("'].lng,");
            script.append("  status: '").append(escapeJavaScript(status)).append("',");
            script.append("  description: '").append(escapeJavaScript(description)).append("',");
            script.append("  time: '").append(escapeJavaScript(time)).append("',");
            script.append("  index: ").append(i + 1);
            script.append("});");
        }
    }
    
    // 添加目的地标记
    String destination = shipment.getDestination();
    script.append("var destPos;");
    script.append("if (locationMap['").append(escapeJavaScript(destination)).append("']) {");
    script.append("  destPos = locationMap['").append(escapeJavaScript(destination)).append("'];");
    script.append("} else {");
    // 不在预设位置中，添加一些常见目的地的处理
    script.append("  switch('").append(escapeJavaScript(destination)).append("') {");
    script.append("    case 'Boston':case '波士顿': destPos = {lat: 42.3601, lng: -71.0589}; break;");
    script.append("    case 'LA':case 'Los Angeles':case '洛杉矶': destPos = {lat: 34.0522, lng: -118.2437}; break;");
    script.append("    case 'New York':case '纽约': destPos = {lat: 40.7128, lng: -74.0060}; break;");
    script.append("    case 'Cancun':case '坎昆': destPos = {lat: 21.1619, lng: -86.8515}; break;");
    script.append("    case 'Guanajuato': destPos = {lat: 21.0190, lng: -101.2574}; break;");
    script.append("    default: destPos = {lat: 35.0, lng: -100.0};"); // 默认位置（美国中部）
    script.append("  }");
    script.append("}");
    
    script.append("var destinationMarker = new google.maps.Marker({");
    script.append("  position: destPos,");
    script.append("  map: map,");
    script.append("  title: '").append(escapeJavaScript(destination)).append("',");
    script.append("  label: 'B',");
    script.append("  icon: {");
    script.append("    url: 'http://maps.google.com/mapfiles/ms/icons/red-dot.png'");
    script.append("  }");
    script.append("});");
    script.append("window.markers.push(destinationMarker);");
    
    // 添加目的地信息窗口
    script.append("var destInfoContent = '<div><strong>目的地: ").append(escapeJavaScript(destination)).append("</strong><br>';");
    if (shipment.getEstimatedDeliveryDate() != null) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        script.append("destInfoContent += '预计送达: ").append(escapeJavaScript(sdf.format(shipment.getEstimatedDeliveryDate()))).append("';");
    }
    script.append("destInfoContent += '</div>';");
    
    script.append("var destInfoWindow = new google.maps.InfoWindow({");
    script.append("  content: destInfoContent");
    script.append("});");
    
    script.append("destinationMarker.addListener('click', function() {");
    script.append("  destInfoWindow.open(map, destinationMarker);");
    script.append("});");
    
    // 处理跟踪点
    script.append("for (var i = 0; i < trackPoints.length; i++) {");
    script.append("  var point = trackPoints[i];");
    script.append("  var position = {lat: point.lat, lng: point.lng};");
    script.append("  pathCoordinates.push(position);");
    
    script.append("  var marker = new google.maps.Marker({");
    script.append("    position: position,");
    script.append("    map: map,");
    script.append("    title: point.location,");
    script.append("    label: point.index.toString()");
    script.append("  });");
    
    script.append("  window.markers.push(marker);");
    
    // 添加信息窗口
    script.append("  var infoContent = '<div><strong>' + point.location + '</strong><br>';");
    script.append("  infoContent += '时间: ' + point.time + '<br>';");
    script.append("  infoContent += '状态: ' + getStatusBadge(point.status) + '<br>';");
    script.append("  infoContent += point.description + '</div>';");
    
    script.append("  var infowindow = new google.maps.InfoWindow({");
    script.append("    content: infoContent");
    script.append("  });");
    
    script.append("  marker.addListener('click', function() {");
    script.append("    infowindow.open(map, marker);");
    script.append("  });");
    script.append("}");
    
    // 如果有目的地且运输状态不是Delivered，添加目的地到路径
    script.append("if ('").append(escapeJavaScript(shipment.getShipmentStatus())).append("' !== 'Delivered') {");
    script.append("  pathCoordinates.push(destPos);");
    script.append("}");
    
    // 创建路径
    script.append("if (pathCoordinates.length > 1) {");
    script.append("  window.path = new google.maps.Polyline({");
    script.append("    path: pathCoordinates,");
    script.append("    geodesic: true,");
    script.append("    strokeColor: '#FF0000',");
    script.append("    strokeOpacity: 0.7,");
    script.append("    strokeWeight: 2");
    script.append("  });");
    script.append("  window.path.setMap(map);");
    
    // 调整地图以显示所有标记
    script.append("  var bounds = new google.maps.LatLngBounds();");
    script.append("  for (var i = 0; i < pathCoordinates.length; i++) {");
    script.append("    bounds.extend(pathCoordinates[i]);");
    script.append("  }");
    script.append("  map.fitBounds(bounds);");
    script.append("}");
    
    webEngine.executeScript(script.toString());
}

private void initializeLocationMap() {
        locationMap = new HashMap<>();
        locationMap.put("上海", new double[]{31.2304, 121.4737});
        locationMap.put("上海仓库", new double[]{31.2304, 121.4737});
        locationMap.put("北京", new double[]{39.9042, 116.4074});
        locationMap.put("广州", new double[]{23.1291, 113.2644});
        locationMap.put("深圳", new double[]{22.5431, 114.0579});
        locationMap.put("杭州", new double[]{30.2741, 120.1551});
        locationMap.put("成都", new double[]{30.5728, 104.0668});
        locationMap.put("重庆", new double[]{29.4316, 106.9123});
        locationMap.put("Boston", new double[]{42.3601, -71.0589});
        locationMap.put("波士顿", new double[]{42.3601, -71.0589});
        locationMap.put("Los Angeles", new double[]{34.0522, -118.2437});
        locationMap.put("LA", new double[]{34.0522, -118.2437});
        locationMap.put("洛杉矶", new double[]{34.0522, -118.2437});
        locationMap.put("New York", new double[]{40.7128, -74.0060});
        locationMap.put("纽约", new double[]{40.7128, -74.0060});
        locationMap.put("Cancun", new double[]{21.1619, -86.8515});
        locationMap.put("坎昆", new double[]{21.1619, -86.8515});
        locationMap.put("Guanajuato", new double[]{21.0190, -101.2574});
    }
    
    


}
