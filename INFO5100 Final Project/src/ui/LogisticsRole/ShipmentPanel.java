/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui.LogisticsRole;

import Business.ConfigureASystem;
import Business.DB4OUtil.DB4OUtil;
import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Logistics.CustomsDeclaration;
import Business.Logistics.CustomsDeclarationDirectory;
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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
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
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.event.ActionListener;

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
    private static final String GOOGLE_MAPS_API_KEY = "AIzaSyDaIUhyHf9iwswA2CuN740sO4eJqPu3aWw";
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

        // Apply UI theme
        setupTheme();
    }

    private void initializeMapComponents() {
        // 确保trackPathJPanel使用BorderLayout布局
        trackPathJPanel.setLayout(new BorderLayout());

        // 创建跟踪信息面板
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        infoPanel.setPreferredSize(new Dimension(300, 0)); // 设置信息面板宽度

        // 添加信息面板到trackPathJPanel右侧
        trackPathJPanel.add(infoPanel, BorderLayout.EAST);

        // 添加默认提示信息
        JLabel defaultLabel = new JLabel("Please select a shipping order to view detailed map and tracking information.");
        defaultLabel.setHorizontalAlignment(JLabel.CENTER);
        defaultLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 16));
        trackPathJPanel.add(defaultLabel, BorderLayout.CENTER);
    }

    private void setupDetailsCards() {
        // 创建主容器面板，使用BorderLayout
        JPanel shipmentDetailsContainer = new JPanel(new BorderLayout());

        // 创建导航按钮面板
        JPanel detailsNavigationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        styleButton(btnBasicInfo); // Apply consistent styling
        styleButton(btnCustomsInfo);
        styleButton(btnPackageInfo);
        styleButton(btnFinancialInfo);

        detailsNavigationPanel.add(btnBasicInfo);
        detailsNavigationPanel.add(btnCustomsInfo);
        detailsNavigationPanel.add(btnPackageInfo);
        detailsNavigationPanel.add(btnFinancialInfo);

        // 创建详情内容的卡片布局面板
        detailsCardsPanel = new JPanel();
        detailsCardLayout = new CardLayout();
        detailsCardsPanel.setLayout(detailsCardLayout);

        // 创建基本信息面板，使用GridBagLayout（与PackageInfo和FinancialInfo一致）
        JPanel shipmentBasicInfoContent = createBasicInfoPanel();
        JPanel shipmentCustomsContent = createCustomsInfoPanel();
        JPanel shipmentPackageContent = createPackageInfoPanel();
        JPanel shipmentFinancialContent = createFinancialInfoPanel();

        // 将所有面板添加到卡片布局中
        detailsCardsPanel.add(shipmentBasicInfoContent, "BasicInfo");
        detailsCardsPanel.add(shipmentCustomsContent, "CustomsInfo");
        detailsCardsPanel.add(shipmentPackageContent, "PackageInfo");
        detailsCardsPanel.add(shipmentFinancialContent, "FinancialInfo");

        // 组装主容器
        shipmentDetailsContainer.add(detailsNavigationPanel, BorderLayout.NORTH);
        shipmentDetailsContainer.add(detailsCardsPanel, BorderLayout.CENTER);

        // 创建并添加更新按钮面板
        JPanel actionButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        styleButton(btnUpdate); // Apply consistent styling
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

        // Configure button actions
        btnBasicInfo.addActionListener(e -> detailsCardLayout.show(detailsCardsPanel, "BasicInfo"));

        // Remove the existing action listener and add our custom one
        for (ActionListener al : btnCustomsInfo.getActionListeners()) {
            btnCustomsInfo.removeActionListener(al);
        }
        btnCustomsInfo.addActionListener(e -> showCustomsInfo());

        btnPackageInfo.addActionListener(e -> detailsCardLayout.show(detailsCardsPanel, "PackageInfo"));
        btnFinancialInfo.addActionListener(e -> detailsCardLayout.show(detailsCardsPanel, "FinancialInfo"));
    }

    // Method to show customs info for the currently selected shipment
    private void showCustomsInfo() {
        // Get the currently selected shipment
        int selectedRow = tblShipment.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a shipment first",
                    "No Selection", JOptionPane.INFORMATION_MESSAGE);
            // Switch back to basic info
            detailsCardLayout.show(detailsCardsPanel, "BasicInfo");
            return;
        }

        String trackingNumber = tblShipment.getValueAt(selectedRow, 0).toString();
        Shipment shipment = findShipmentByTrackingNumber(trackingNumber);

        if (shipment == null) {
            JOptionPane.showMessageDialog(this, "Cannot find shipment details",
                    "Error", JOptionPane.ERROR_MESSAGE);
            // Switch back to basic info
            detailsCardLayout.show(detailsCardsPanel, "BasicInfo");
            return;
        }

        // Ensure customs information exists for this shipment
        ensureCustomsInfoExists(shipment);

        try {
            // The simplest approach: fully recreate all panels
            // Remove all existing panels
            detailsCardsPanel.removeAll();

            // Add fresh panels
            detailsCardsPanel.add(createBasicInfoPanel(), "BasicInfo");
            detailsCardsPanel.add(createCustomsInfoPanel(), "CustomsInfo");
            detailsCardsPanel.add(createPackageInfoPanel(), "PackageInfo");
            detailsCardsPanel.add(createFinancialInfoPanel(), "FinancialInfo");

            // Show the customs panel
            detailsCardLayout.show(detailsCardsPanel, "CustomsInfo");

            // Refresh UI
            detailsCardsPanel.revalidate();
            detailsCardsPanel.repaint();
        } catch (Exception e) {
            System.err.println("Error while showing customs info: " + e.getMessage());
            e.printStackTrace();

            // If we encounter an error, just show the basic info panel
            try {
                detailsCardLayout.show(detailsCardsPanel, "BasicInfo");
            } catch (Exception ex) {
                // Last resort if even that fails
                System.err.println("Critical UI error: " + ex.getMessage());
            }
        }

        // Debug output
        System.out.println("Showing customs info for shipment: " + trackingNumber);
        CustomsDeclaration customs = shipment.getCustomsDeclaration();
        if (customs != null) {
            System.out.println("Customs declaration loaded: " + customs.getDeclarationId());
            System.out.println("Consignor: " + customs.getConsignor());
            System.out.println("Consignee: " + customs.getConsignee());
            System.out.println("Status: " + customs.getStatus());
            System.out.println("Items: " + (customs.getItems() != null ? customs.getItems().size() : 0));
        } else {
            System.out.println("No customs declaration found for this shipment");
        }
    }

    // Create the basic info panel
    private JPanel createBasicInfoPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 设置约束条件
        GridBagConstraints labelConstraints = new GridBagConstraints();
        labelConstraints.gridx = 0;
        labelConstraints.gridy = 0;
        labelConstraints.anchor = GridBagConstraints.EAST;
        labelConstraints.insets = new Insets(10, 10, 10, 10);

        GridBagConstraints fieldConstraints = new GridBagConstraints();
        fieldConstraints.gridx = 1;
        fieldConstraints.gridy = 0;
        fieldConstraints.fill = GridBagConstraints.HORIZONTAL;
        fieldConstraints.weightx = 1.0;
        fieldConstraints.insets = new Insets(10, 0, 10, 10);

        // 设置文本框的标准大小
        Dimension textFieldSize = new Dimension(250, 30);
        txtTrcNo.setPreferredSize(textFieldSize);
        txtShippingDate.setPreferredSize(textFieldSize);
        txtShippingMethod.setPreferredSize(textFieldSize);
        txtDestination.setPreferredSize(textFieldSize);
        txtStatus.setPreferredSize(textFieldSize);

        // 添加组件到基本信息面板
        panel.add(lblTraNo, labelConstraints);
        panel.add(txtTrcNo, fieldConstraints);

        labelConstraints.gridy++;
        fieldConstraints.gridy++;
        panel.add(lblShippingDate, labelConstraints);
        panel.add(txtShippingDate, fieldConstraints);

        labelConstraints.gridy++;
        fieldConstraints.gridy++;
        panel.add(lblShippingMethod, labelConstraints);
        panel.add(txtShippingMethod, fieldConstraints);

        labelConstraints.gridy++;
        fieldConstraints.gridy++;
        panel.add(lblDestination, labelConstraints);
        panel.add(txtDestination, fieldConstraints);

        labelConstraints.gridy++;
        fieldConstraints.gridy++;
        panel.add(lblStatus, labelConstraints);
        panel.add(txtStatus, fieldConstraints);

        // 添加填充空间，将内容推到顶部
        GridBagConstraints fillerConstraints = new GridBagConstraints();
        fillerConstraints.gridx = 0;
        fillerConstraints.gridy = labelConstraints.gridy + 1;
        fillerConstraints.weighty = 1.0;
        fillerConstraints.fill = GridBagConstraints.BOTH;
        fillerConstraints.gridwidth = 2;
        panel.add(Box.createVerticalGlue(), fillerConstraints);

        return panel;
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
        btnDelete = new javax.swing.JButton();

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
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
                .addContainerGap(185, Short.MAX_VALUE))
        );
        trackPathJPanelLayout.setVerticalGroup(
            trackPathJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(trackPathJPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblTrackPath, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(340, Short.MAX_VALUE))
        );

        btnShip.setText("Ship");
        btnShip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShipActionPerformed(evt);
            }
        });

        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
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
                            .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(basicInfoJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(52, 52, 52)
                                .addComponent(trackPathJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1419, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(16, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnViewDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(btnAddNewShip, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnUpdateStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnShip, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(186, 186, 186))))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnDelete, btnShip, btnUpdateStatus});

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
                        .addComponent(btnShip)
                        .addComponent(btnDelete)))
                .addGap(47, 47, 47)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(basicInfoJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(trackPathJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 22, Short.MAX_VALUE))
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

        // 获取物流组织实例
        LogisticsOrganization logisticsOrg = ConfigureASystem.getLogisticsOrganization();
        if (logisticsOrg == null) {
            JOptionPane.showMessageDialog(this, "Error: Cannot access logistics organization");
            return;
        }

        try {
            // 1. 获取产品名称
            String productName = JOptionPane.showInputDialog(this,
                    "Enter product name:",
                    "Product Information",
                    JOptionPane.QUESTION_MESSAGE);

            if (productName == null || productName.trim().isEmpty()) {
                return; // 用户取消
            }

            // 2. 获取数量
            String quantityStr = JOptionPane.showInputDialog(this,
                    "Enter quantity:",
                    "Product Information",
                    JOptionPane.QUESTION_MESSAGE);

            if (quantityStr == null || quantityStr.trim().isEmpty()) {
                return; // 用户取消
            }

            int quantity;
            try {
                quantity = Integer.parseInt(quantityStr);
                if (quantity <= 0) {
                    JOptionPane.showMessageDialog(this, "Quantity must be greater than 0");
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number for quantity");
                return;
            }

            // 3. 获取目的地
            String destination = JOptionPane.showInputDialog(this,
                    "Enter destination:",
                    "Shipment Information",
                    JOptionPane.QUESTION_MESSAGE);

            if (destination == null || destination.trim().isEmpty()) {
                destination = "Not specified";
            }

            // 4. 创建新的 Shipment，状态设为 Pending
            String shipmentId = "SHP" + System.currentTimeMillis();
            String trackingNumber = "TRK" + System.currentTimeMillis();

            // 创建新的Shipment
            Shipment shipment = new Shipment();
            shipment.setShipmentId(shipmentId);
            shipment.setTrackingNumber(trackingNumber);
            shipment.setProductName(productName);
            shipment.setQuantity(quantity);
            shipment.setDestination(destination);
            shipment.setShipDate(new Date());
            shipment.setShippingMethod("To be assigned");
            shipment.setShipmentStatus("Pending");

            // 创建并关联海关文档
            CustomsDeclaration customs = new CustomsDeclaration();
            customs.setDeclarationId("CD" + System.currentTimeMillis());
            customs.setShipmentId(shipmentId);
            customs.setCountryOfOrigin("China");
            customs.setConsignor("Shanghai Warehouse");
            customs.setConsignee(destination);
            customs.setDestinationCountry(extractCountry(destination));
            customs.setStatus("Pending");

            // 创建默认物品（基于货物信息）
            CustomsDeclaration.CustomsLineItem item = new CustomsDeclaration.CustomsLineItem();
            item.setDescription(productName);
            item.setQuantity(quantity);
            item.setUnit("PCS");
            item.setUnitValue(0.0); // 需要设置实际价值
            item.setTotalValue(0.0); // 会在设置单价时自动计算
            item.setGrossWeight(0.5 * quantity); // 估计重量
            customs.addItem(item);

            shipment.setCustomsDeclaration(customs);

            // 注意：不设置预计送达时间，让它保持为 null
            // 在实际发货时再设置
            // 5. 添加初始追踪信息
            TrackingInfo trackInfo = new TrackingInfo();
            trackInfo.setShipmentId(shipment.getShipmentId());
            trackInfo.setTimestamp(new Date());
            trackInfo.setLocation("Shanghai Warehouse");
            trackInfo.setDescription("New shipment created, waiting for processing");
            trackInfo.setStatus("Pending");
            shipment.addTrackingInfo(trackInfo);

            // 6. 将 Shipment 添加到目录
            logisticsOrg.getShipmentDirectory().addShipment(shipment);

            // 7. 创建对应的工作请求
            WarehouseWorkRequest request = new WarehouseWorkRequest();
            request.setShipmentId(shipmentId);
            request.setTrackingNumber(trackingNumber);
            request.setProductName(productName);
            request.setQuantity(quantity);
            request.setDestination(destination);
            request.setStatus("Pending");
            request.setRequestDate(new Date());

            // 8. 添加工作请求到工作队列
            logisticsOrg.getWorkQueue().getWorkRequestList().add(request);

            // 9. 保存系统更新
            EcoSystem system = EcoSystem.getInstance();
            DB4OUtil.getInstance().storeSystem(system);

            // 10. 刷新表格
            populateTable();

            // 11. 如果 LogisticsCoordinatorHP 是可访问的，也刷新它的表格
            if (parentPanel instanceof LogisticsCoordinatorHP) {
                ((LogisticsCoordinatorHP) parentPanel).refreshTables();
            }

            // 12. 显示成功消息
            JOptionPane.showMessageDialog(this,
                    "New shipment created successfully!\nTracking Number: " + trackingNumber);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error creating shipment: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnAddNewShipActionPerformed

    private void btnUpdateStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateStatusActionPerformed
        int selectedRow = tblShipment.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a shipment to update",
                    "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // 获取选中行的信息
        String trackingNumber = tblShipment.getValueAt(selectedRow, 0).toString();

        // 如果是 Pending 状态的新订单，需要先处理发货
        if (trackingNumber.startsWith("Pending-")) {
            JOptionPane.showMessageDialog(this,
                    "This is a pending shipment. Please use the Ship button to process it first.",
                    "Information",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // 从表格获取当前信息
        String currentStatus = tblShipment.getValueAt(selectedRow, 3).toString();
        String currentShippingMethod = tblShipment.getValueAt(selectedRow, 1).toString();
        String currentDestination = tblShipment.getValueAt(selectedRow, 2).toString();

        // 创建一个面板来包含输入字段
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10));

        // 状态选择下拉框
        JLabel statusLabel = new JLabel("Status:");
        String[] statuses = {"Processing", "Shipped", "In Transit", "Out for Delivery", "Delivered", "Exception"};
        JComboBox<String> statusBox = new JComboBox<>(statuses);
        statusBox.setSelectedItem(currentStatus);
        panel.add(statusLabel);
        panel.add(statusBox);

        // 运输方式选择下拉框
        JLabel methodLabel = new JLabel("Shipping Method:");
        String[] methods = {"Express", "Air Freight", "Sea Freight", "Ground"};
        JComboBox<String> methodBox = new JComboBox<>(methods);
        methodBox.setSelectedItem(currentShippingMethod);
        panel.add(methodLabel);
        panel.add(methodBox);

        // 目的地输入框
        JLabel destLabel = new JLabel("Destination:");
        JTextField destField = new JTextField(currentDestination);
        panel.add(destLabel);
        panel.add(destField);

        // 显示对话框
        int result = JOptionPane.showConfirmDialog(this, panel,
                "Update Shipment Information",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        // 如果用户点击了确定
        if (result == JOptionPane.OK_OPTION) {
            // 获取用户输入的新值
            String newStatus = statusBox.getSelectedItem().toString();
            String newShippingMethod = methodBox.getSelectedItem().toString();
            String newDestination = destField.getText();

            // 显示确认信息
            StringBuilder confirmMessage = new StringBuilder("Confirm the following changes:\n\n");

            if (!currentStatus.equals(newStatus)) {
                confirmMessage.append("Status: ").append(currentStatus).append(" → ").append(newStatus).append("\n");
            }

            if (!currentShippingMethod.equals(newShippingMethod)) {
                confirmMessage.append("Shipping Method: ").append(currentShippingMethod).append(" → ").append(newShippingMethod).append("\n");
            }

            if (!currentDestination.equals(newDestination)) {
                confirmMessage.append("Destination: ").append(currentDestination).append(" → ").append(newDestination).append("\n");
            }

            // 如果没有任何变化
            if (currentStatus.equals(newStatus)
                    && currentShippingMethod.equals(newShippingMethod)
                    && currentDestination.equals(newDestination)) {
                JOptionPane.showMessageDialog(this, "No changes were made", "Information", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // 显示确认对话框
            int confirmResult = JOptionPane.showConfirmDialog(this,
                    confirmMessage.toString(),
                    "Confirm Updates",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            // 如果用户确认了更改
            if (confirmResult == JOptionPane.YES_OPTION) {
                // 获取物流组织实例
                LogisticsOrganization logisticsOrg = ConfigureASystem.getLogisticsOrganization();
                if (logisticsOrg == null) {
                    JOptionPane.showMessageDialog(this, "Error: Cannot access logistics organization");
                    return;
                }

                // 更新 ShipmentDirectory 中的 Shipment
                Shipment shipment = logisticsOrg.getShipmentDirectory().findShipmentByTrackingNumber(trackingNumber);
                if (shipment != null) {
                    // 更新 Shipment 信息
                    shipment.setShipmentStatus(newStatus);
                    shipment.setShippingMethod(newShippingMethod);
                    shipment.setDestination(newDestination);

                    // 如果状态改变，添加新的追踪信息
                    if (!currentStatus.equals(newStatus)) {
                        TrackingInfo trackInfo = new TrackingInfo();
                        trackInfo.setShipmentId(shipment.getShipmentId());
                        trackInfo.setTimestamp(new Date());
                        trackInfo.setStatus(newStatus);

                        // 根据状态设置不同的位置和描述
                        switch (newStatus) {
                            case "Processing":
                                trackInfo.setLocation("Warehouse");
                                trackInfo.setDescription("Shipment is being processed");
                                break;
                            case "Shipped":
                                trackInfo.setLocation("Warehouse");
                                trackInfo.setDescription("Shipment has been shipped");
                                break;
                            case "In Transit":
                                trackInfo.setLocation("Transit Facility");
                                trackInfo.setDescription("Shipment is in transit");
                                break;
                            case "Out for Delivery":
                                trackInfo.setLocation("Local Delivery Facility");
                                trackInfo.setDescription("Shipment is out for delivery");
                                break;
                            case "Delivered":
                                trackInfo.setLocation(newDestination);
                                trackInfo.setDescription("Shipment has been delivered");
                                break;
                            case "Exception":
                                trackInfo.setLocation("Unknown");
                                trackInfo.setDescription("Issue with shipment, requiring attention");
                                break;
                        }

                        shipment.addTrackingInfo(trackInfo);
                    }

                    // 如果状态变为"已送达"，设置解决日期
                    if (newStatus.equals("Delivered")) {
                        // 更新对应的工作请求
                        for (WorkRequest request : logisticsOrg.getWorkQueue().getWorkRequestList()) {
                            if (request instanceof WarehouseWorkRequest) {
                                WarehouseWorkRequest warehouseRequest = (WarehouseWorkRequest) request;
                                if (trackingNumber.equals(warehouseRequest.getTrackingNumber())) {
                                    warehouseRequest.setStatus("Completed");
                                    warehouseRequest.setResolveDate(new Date());
                                    break;
                                }
                            }
                        }
                    }
                }

                // 保存系统更新
                EcoSystem system = EcoSystem.getInstance();
                DB4OUtil.getInstance().storeSystem(system);

                // 刷新表格
                populateTable();

                // 如果 LogisticsCoordinatorHP 是可访问的，也刷新它的表格
                if (parentPanel instanceof LogisticsCoordinatorHP) {
                    ((LogisticsCoordinatorHP) parentPanel).refreshTables();
                }

                // 显示成功消息
                JOptionPane.showMessageDialog(this, "Shipment information updated successfully",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
            }
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
            if (destination == null || destination.isEmpty()
                    || destination.equals("Not specified") || destination.equals("To be assigned")) {

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

            // 创建海关文档
            CustomsDeclaration customs = new CustomsDeclaration();
            customs.setDeclarationId("CD" + System.currentTimeMillis());
            customs.setShipmentId(shipment.getShipmentId());
            customs.setCountryOfOrigin("China");
            customs.setConsignor("Shanghai Warehouse");
            customs.setConsignee(destination);
            customs.setDestinationCountry(extractCountry(destination));
            customs.setStatus("Pending");

            // 如果有产品信息，添加默认物品
            if (shipment.getProductName() != null && !shipment.getProductName().isEmpty()) {
                CustomsDeclaration.CustomsLineItem item = new CustomsDeclaration.CustomsLineItem();
                item.setDescription(shipment.getProductName());
                item.setQuantity(shipment.getQuantity());
                item.setUnit("PCS");
                item.setUnitValue(0.0); // 需要设置实际价值
                item.setTotalValue(0.0);
                item.setGrossWeight(0.5 * shipment.getQuantity()); // 估计重量
                customs.addItem(item);
            }

            shipment.setCustomsDeclaration(customs);

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
                    "Shipment processed successfully!\nTracking Number: " + trackingNumber
                    + "\nDestination: " + destination);

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

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        int selectedRow = tblShipment.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a shipment to delete",
                    "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // 获取选中行的信息
        String trackingNumber = tblShipment.getValueAt(selectedRow, 0).toString();
        String shippingMethod = tblShipment.getValueAt(selectedRow, 1).toString();
        String destination = tblShipment.getValueAt(selectedRow, 2).toString();
        String status = tblShipment.getValueAt(selectedRow, 3).toString();

        // 如果是 Pending 状态的新订单，处理特殊情况
        boolean isPendingRequest = trackingNumber.startsWith("Pending-");
        String shipmentId = isPendingRequest ? trackingNumber.replace("Pending-", "") : null;

        // 构建确认信息
        StringBuilder confirmMessage = new StringBuilder("Are you sure you want to delete the following shipment?\n\n");
        confirmMessage.append("Tracking Number: ").append(trackingNumber).append("\n");
        confirmMessage.append("Shipping Method: ").append(shippingMethod).append("\n");
        confirmMessage.append("Destination: ").append(destination).append("\n");
        confirmMessage.append("Status: ").append(status).append("\n");

        // 显示确认对话框
        int confirmResult = JOptionPane.showConfirmDialog(this,
                confirmMessage.toString(),
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        // 如果用户确认了删除
        if (confirmResult == JOptionPane.YES_OPTION) {
            // 获取物流组织实例
            LogisticsOrganization logisticsOrg = ConfigureASystem.getLogisticsOrganization();
            if (logisticsOrg == null) {
                JOptionPane.showMessageDialog(this, "Error: Cannot access logistics organization");
                return;
            }

            boolean deleted = false;

            // 1. 从 ShipmentDirectory 中删除 Shipment（如果不是 Pending 请求）
            if (!isPendingRequest) {
                deleted = logisticsOrg.getShipmentDirectory().removeShipmentByTrackingNumber(trackingNumber);
            }

            // 2. 从工作队列中删除相关请求
            ArrayList<WorkRequest> requestsToRemove = new ArrayList<>();
            for (WorkRequest request : logisticsOrg.getWorkQueue().getWorkRequestList()) {
                if (request instanceof WarehouseWorkRequest) {
                    WarehouseWorkRequest warehouseRequest = (WarehouseWorkRequest) request;

                    if ((isPendingRequest && warehouseRequest.getShipmentId() != null
                            && warehouseRequest.getShipmentId().equals(shipmentId))
                            || (!isPendingRequest && trackingNumber.equals(warehouseRequest.getTrackingNumber()))) {

                        requestsToRemove.add(request);
                        deleted = true;
                    }
                }
            }

            // 从工作队列中移除请求
            for (WorkRequest request : requestsToRemove) {
                logisticsOrg.getWorkQueue().getWorkRequestList().remove(request);
            }

            if (deleted) {
                // 保存系统更新
                EcoSystem system = EcoSystem.getInstance();
                DB4OUtil.getInstance().storeSystem(system);

                // 刷新表格
                populateTable();

                // 如果 LogisticsCoordinatorHP 是可访问的，也刷新它的表格
                if (parentPanel instanceof LogisticsCoordinatorHP) {
                    ((LogisticsCoordinatorHP) parentPanel).refreshTables();
                }

                // 显示成功消息
                JOptionPane.showMessageDialog(this, "Shipment deleted successfully",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete shipment. It may have been already removed.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnDeleteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel basicInfoJPanel;
    private javax.swing.JButton btnAddNewShip;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnBasicInfo;
    private javax.swing.JButton btnCustomsInfo;
    private javax.swing.JButton btnDelete;
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
                        row[1] = warehouseRequest.getShippingMethod() != null
                                ? warehouseRequest.getShippingMethod() : "To be assigned";  // 待分配运输方式
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
            txtShippingDate.setText(shipment.getShipDate() != null
                    ? new SimpleDateFormat("yyyy-MM-dd").format(shipment.getShipDate()) : "");
            txtShippingMethod.setText(shipment.getShippingMethod());
            txtDestination.setText(shipment.getDestination());
            txtStatus.setText(shipment.getShipmentStatus());

            // 确保商品有海关信息
            ensureCustomsInfoExists(shipment);

            // 使用静态地图显示
            displayStaticMap(shipment);

            // 激活基本信息Tab
            btnBasicInfo.doClick();
        }
    }

    // 确保货件有关联的海关信息
    private void ensureCustomsInfoExists(Shipment shipment) {
        if (shipment == null) {
            return;
        }

        // 如果已有关联的海关信息，无需处理
        if (shipment.getCustomsDeclaration() != null) {
            return;
        }

        // 尝试从海关目录中查找关联的报关单
        if (organization != null && organization.getCustomsDeclarationDirectory() != null) {
            CustomsDeclarationDirectory declarationDir = organization.getCustomsDeclarationDirectory();
            for (CustomsDeclaration declaration : declarationDir.getCustomsDeclarationList()) {
                if (shipment.getShipmentId() != null
                        && shipment.getShipmentId().equals(declaration.getShipmentId())) {
                    // 找到匹配的报关单，关联到货件
                    shipment.setCustomsDeclaration(declaration);
                    System.out.println("已关联现有报关单到货件 " + shipment.getShipmentId());
                    return;
                }
            }
        }
        // 如果找不到匹配的报关单，创建一个新的
        CustomsDeclaration customs = new CustomsDeclaration();
        customs.setDeclarationId("CD" + System.currentTimeMillis());
        customs.setShipmentId(shipment.getShipmentId());
        customs.setDeclarationDate(new Date());

        // 设置默认值
        customs.setCountryOfOrigin("China");
        customs.setConsignor("Shanghai Warehouse");
        customs.setConsignee(shipment.getDestination());
        customs.setDestinationCountry(extractCountry(shipment.getDestination()));
        customs.setStatus("Draft");
        customs.setNotes("Auto-generated customs declaration");

        // 添加默认物品
        if (shipment.getProductName() != null && !shipment.getProductName().isEmpty()) {
            ArrayList<CustomsDeclaration.CustomsLineItem> items = new ArrayList<>();

            CustomsDeclaration.CustomsLineItem item = new CustomsDeclaration.CustomsLineItem();
            item.setDescription(shipment.getProductName());
            item.setQuantity(shipment.getQuantity());
            item.setUnit("PCS");
            item.setUnitValue(100.0); // 默认单价
            item.setTotalValue(item.getQuantity() * item.getUnitValue());
            item.setGrossWeight(item.getQuantity() * 0.5); // 估计重量
            items.add(item);

            customs.setItems(items);
        }

        // 关联到货件
        shipment.setCustomsDeclaration(customs);

        // 添加到海关目录
        if (organization != null && organization.getCustomsDeclarationDirectory() != null) {
            organization.getCustomsDeclarationDirectory().addCustomsDeclaration(customs);
        }

        // 保存到系统
        EcoSystem system = EcoSystem.getInstance();
        DB4OUtil.getInstance().storeSystem(system);

        System.out.println("已为货件 " + shipment.getShipmentId() + " 创建新报关单");
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
        // Special handling for pending shipments
        if (trackingNumber != null && trackingNumber.startsWith("Pending-")) {
            String shipmentId = trackingNumber.replace("Pending-", "");

            // Search for work request with this shipment ID
            if (organization != null && organization.getWorkQueue() != null) {
                for (WorkRequest request : organization.getWorkQueue().getWorkRequestList()) {
                    if (request instanceof WarehouseWorkRequest) {
                        WarehouseWorkRequest warehouseRequest = (WarehouseWorkRequest) request;
                        if (shipmentId.equals(warehouseRequest.getShipmentId())) {
                            // Create a temporary shipment object with the request data
                            Shipment tempShipment = new Shipment();
                            tempShipment.setShipmentId(warehouseRequest.getShipmentId());
                            tempShipment.setProductName(warehouseRequest.getProductName());
                            tempShipment.setQuantity(warehouseRequest.getQuantity());
                            tempShipment.setDestination(warehouseRequest.getDestination());
                            tempShipment.setShipmentStatus(warehouseRequest.getStatus());
                            // Use setShipDate instead of non-existent setRequestDate
                            tempShipment.setShipDate(warehouseRequest.getRequestDate());
                            return tempShipment;
                        }
                    }
                }
            }
            return null;
        }

        // Regular shipment lookup
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
        JPanel infoPanel = (JPanel) trackPathJPanel.getComponent(1);
        updateTrackingInfo(shipment, trackingHistory, infoPanel);
    }

    /**
     * 显示以上海仓库和目的地为中心的默认地图
     *
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
        JPanel infoPanel = (JPanel) trackPathJPanel.getComponent(1);
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
        JPanel customsPanel = new JPanel(new BorderLayout());

        // 创建主内容面板，使用 GridBagLayout
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 设置约束条件
        GridBagConstraints labelConstraints = new GridBagConstraints();
        labelConstraints.gridx = 0;
        labelConstraints.gridy = 0;
        labelConstraints.anchor = GridBagConstraints.EAST;
        labelConstraints.insets = new Insets(10, 10, 10, 10);

        GridBagConstraints fieldConstraints = new GridBagConstraints();
        fieldConstraints.gridx = 1;
        fieldConstraints.gridy = 0;
        fieldConstraints.fill = GridBagConstraints.HORIZONTAL;
        fieldConstraints.weightx = 1.0;
        fieldConstraints.insets = new Insets(10, 0, 10, 10);

        // 设置文本框的标准大小
        Dimension textFieldSize = new Dimension(250, 30);

        // 只保留重要信息
        JLabel lblDeclarationId = new JLabel("Declaration ID:");
        JTextField txtDeclarationId = new JTextField(20);
        txtDeclarationId.setName("txtDeclarationId");
        txtDeclarationId.setEditable(false);
        txtDeclarationId.setPreferredSize(textFieldSize);

        JLabel lblConsignor = new JLabel("Consignor:");
        JTextField txtConsignor = new JTextField(20);
        txtConsignor.setName("txtConsignor");
        txtConsignor.setPreferredSize(textFieldSize);

        JLabel lblConsignee = new JLabel("Consignee:");
        JTextField txtConsignee = new JTextField(20);
        txtConsignee.setName("txtConsignee");
        txtConsignee.setPreferredSize(textFieldSize);

        JLabel lblCountryOfOrigin = new JLabel("Country of Origin:");
        JTextField txtCountryOfOrigin = new JTextField(20);
        txtCountryOfOrigin.setName("txtCountryOfOrigin");
        txtCountryOfOrigin.setPreferredSize(textFieldSize);

        JLabel lblDestinationCountry = new JLabel("Destination Country:");
        JTextField txtDestinationCountry = new JTextField(20);
        txtDestinationCountry.setName("txtDestinationCountry");
        txtDestinationCountry.setPreferredSize(textFieldSize);

        JLabel lblStatus = new JLabel("Status:");
        String[] statuses = {"Draft", "Submitted", "Approved", "Rejected", "Information Needed"};
        JComboBox<String> cmbStatus = new JComboBox<>(statuses);
        cmbStatus.setName("cmbStatus");
        cmbStatus.setPreferredSize(textFieldSize);

        JLabel lblCustomsOffice = new JLabel("Customs Office:");
        JTextField txtCustomsOffice = new JTextField(20);
        txtCustomsOffice.setName("txtCustomsOffice");
        txtCustomsOffice.setPreferredSize(textFieldSize);

        JLabel lblNotes = new JLabel("Notes:");
        JTextArea txtNotes = new JTextArea(3, 20);
        txtNotes.setName("txtNotes");
        txtNotes.setLineWrap(true);
        txtNotes.setWrapStyleWord(true);
        JScrollPane scrollNotes = new JScrollPane(txtNotes);
        scrollNotes.setPreferredSize(new Dimension(250, 60));

        // 添加组件到内容面板
        contentPanel.add(lblDeclarationId, labelConstraints);
        contentPanel.add(txtDeclarationId, fieldConstraints);

        labelConstraints.gridy++;
        fieldConstraints.gridy++;
        contentPanel.add(lblConsignor, labelConstraints);
        contentPanel.add(txtConsignor, fieldConstraints);

        labelConstraints.gridy++;
        fieldConstraints.gridy++;
        contentPanel.add(lblConsignee, labelConstraints);
        contentPanel.add(txtConsignee, fieldConstraints);

        labelConstraints.gridy++;
        fieldConstraints.gridy++;
        contentPanel.add(lblCountryOfOrigin, labelConstraints);
        contentPanel.add(txtCountryOfOrigin, fieldConstraints);

        labelConstraints.gridy++;
        fieldConstraints.gridy++;
        contentPanel.add(lblDestinationCountry, labelConstraints);
        contentPanel.add(txtDestinationCountry, fieldConstraints);

        labelConstraints.gridy++;
        fieldConstraints.gridy++;
        contentPanel.add(lblStatus, labelConstraints);
        contentPanel.add(cmbStatus, fieldConstraints);

        labelConstraints.gridy++;
        fieldConstraints.gridy++;
        contentPanel.add(lblCustomsOffice, labelConstraints);
        contentPanel.add(txtCustomsOffice, fieldConstraints);

        labelConstraints.gridy++;
        fieldConstraints.gridy++;
        contentPanel.add(lblNotes, labelConstraints);
        contentPanel.add(scrollNotes, fieldConstraints);

        // 添加填充空间，将内容推到顶部
        GridBagConstraints fillerConstraints = new GridBagConstraints();
        fillerConstraints.gridx = 0;
        fillerConstraints.gridy = labelConstraints.gridy + 1;
        fillerConstraints.weighty = 1.0;
        fillerConstraints.fill = GridBagConstraints.BOTH;
        fillerConstraints.gridwidth = 2;
        contentPanel.add(Box.createVerticalGlue(), fillerConstraints);

        // 创建简化版物品表格（只保留一个简单的表格）
        JPanel itemsPanel = new JPanel(new BorderLayout());
        itemsPanel.setBorder(BorderFactory.createTitledBorder("Declaration Items"));

        // 创建表格模型（简化列）
        DefaultTableModel itemsTableModel = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Description", "Quantity", "Unit Value"}
        );

        JTable itemsTable = new JTable(itemsTableModel);
        JScrollPane itemsScrollPane = new JScrollPane(itemsTable);
        itemsScrollPane.setPreferredSize(new Dimension(0, 150));

        // 添加物品操作按钮
        JPanel itemButtonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnAddItem = new JButton("Add Item");
        JButton btnRemoveItem = new JButton("Remove Item");
        itemButtonsPanel.add(btnAddItem);
        itemButtonsPanel.add(btnRemoveItem);

        // 设置按钮事件
        btnAddItem.addActionListener(e -> addCustomsLineItem(itemsTable));
        btnRemoveItem.addActionListener(e -> removeCustomsLineItem(itemsTable));

        itemsPanel.add(itemsScrollPane, BorderLayout.CENTER);
        itemsPanel.add(itemButtonsPanel, BorderLayout.SOUTH);

        // 创建主面板布局
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(contentPanel, BorderLayout.NORTH);
        mainPanel.add(itemsPanel, BorderLayout.CENTER);

        // 创建按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnSaveCustoms = new JButton("Save");
        btnSaveCustoms.setBackground(new Color(26, 79, 156)); // Medium blue
        btnSaveCustoms.setForeground(Color.WHITE);
        buttonPanel.add(btnSaveCustoms);

        // 组装主面板
        customsPanel.add(mainPanel, BorderLayout.CENTER);
        customsPanel.add(buttonPanel, BorderLayout.SOUTH);

        // 设置面板引用（保持与原有方法兼容）
        final JTextField[] fieldsRef = {
            txtDeclarationId,
            null, // 可能是declarationNumber
            null, // 可能是hsCode
            txtConsignor,
            txtConsignee,
            txtCountryOfOrigin,
            txtDestinationCountry,
            txtCustomsOffice
        };

        final JComboBox[] combosRef = {null, cmbStatus};
        final JTextArea[] textAreasRef = {txtNotes};
        final JTable[] tablesRef = {itemsTable};

        // 加载数据
        int selectedRow = tblShipment.getSelectedRow();
        if (selectedRow >= 0) {
            String trackingNumber = tblShipment.getValueAt(selectedRow, 0).toString();
            Shipment shipment = findShipmentByTrackingNumber(trackingNumber);
            if (shipment != null) {
                loadCustomsInfo(shipment, fieldsRef, combosRef, textAreasRef, tablesRef[0]);
            }
        }

        // 保存按钮事件
        btnSaveCustoms.addActionListener(e -> {
            saveCustomsInfo(customsPanel, fieldsRef, combosRef, textAreasRef, tablesRef[0]);
        });

        return customsPanel;
    }

    // 添加海关明细项
    private void addCustomsLineItem(JTable itemsTable) {
        // 创建对话框
        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblDescription = new JLabel("Description:");
        JTextField txtDescription = new JTextField(20);

        JLabel lblQuantity = new JLabel("Quantity:");
        JTextField txtQuantity = new JTextField(20);

        JLabel lblUnit = new JLabel("Unit:");
        JTextField txtUnit = new JTextField(20);
        txtUnit.setText("PCS"); // 默认值

        JLabel lblUnitValue = new JLabel("Unit Value (USD):");
        JTextField txtUnitValue = new JTextField(20);

        panel.add(lblDescription);
        panel.add(txtDescription);
        panel.add(lblQuantity);
        panel.add(txtQuantity);
        panel.add(lblUnit);
        panel.add(txtUnit);
        panel.add(lblUnitValue);
        panel.add(txtUnitValue);

        int result = JOptionPane.showConfirmDialog(null, panel, "Add Declaration Item",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String description = txtDescription.getText();
                if (description == null || description.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Description cannot be empty.",
                            "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int quantity = Integer.parseInt(txtQuantity.getText());
                if (quantity <= 0) {
                    JOptionPane.showMessageDialog(null, "Quantity must be greater than zero.",
                            "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String unit = txtUnit.getText();
                if (unit == null || unit.trim().isEmpty()) {
                    unit = "PCS"; // Default unit
                }

                double unitValue = Double.parseDouble(txtUnitValue.getText());
                if (unitValue < 0) {
                    JOptionPane.showMessageDialog(null, "Unit value cannot be negative.",
                            "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // 获取表格模型
                DefaultTableModel model = (DefaultTableModel) itemsTable.getModel();

                // 根据表格列数添加数据
                if (model.getColumnCount() == 3) {
                    // 简化版表格
                    model.addRow(new Object[]{
                        description,
                        quantity,
                        unitValue
                    });
                } else if (model.getColumnCount() >= 7) {
                    // 完整版表格
                    double totalValue = quantity * unitValue;
                    double grossWeight = quantity * 0.5; // 默认每个单位0.5kg

                    model.addRow(new Object[]{
                        description,
                        "", // HS Code (empty for now)
                        quantity,
                        unit,
                        unitValue,
                        totalValue,
                        grossWeight
                    });
                }

                JOptionPane.showMessageDialog(null, "Item added successfully!");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter valid numbers for quantity and unit value.",
                        "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

// 移除海关明细项
    private void removeCustomsLineItem(JTable itemsTable) {
        int selectedRow = itemsTable.getSelectedRow();
        if (selectedRow >= 0) {
            // 确认删除
            int confirm = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to remove this item?",
                    "Confirm Removal",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                DefaultTableModel model = (DefaultTableModel) itemsTable.getModel();
                model.removeRow(selectedRow);
                JOptionPane.showMessageDialog(null, "Item removed successfully!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select an item to remove.",
                    "No Selection", JOptionPane.WARNING_MESSAGE);
        }
    }

// 加载海关信息到表单
    private void loadCustomsInfo(Shipment shipment, JTextField[] fields, JComboBox[] combos,
            JTextArea[] textAreas, JTable itemsTable) {

        // 获取海关信息
        CustomsDeclaration customs = null;

        // 直接从shipment获取海关信息
        if (shipment != null) {
            customs = shipment.getCustomsDeclaration();
        }

        // 如果shipment中没有，尝试从LogisticsOrganization中获取
        if (customs == null && organization != null && organization.getCustomsDeclarationDirectory() != null) {
            for (CustomsDeclaration declaration : organization.getCustomsDeclarationDirectory().getCustomsDeclarationList()) {
                if (declaration.getShipmentId() != null
                        && declaration.getShipmentId().equals(shipment.getShipmentId())) {
                    customs = declaration;
                    // 顺便关联到shipment
                    shipment.setCustomsDeclaration(customs);
                    break;
                }
            }
        }

        // 如果没有海关信息，创建一个新的
        if (customs == null) {
            customs = new CustomsDeclaration();
            customs.setDeclarationId("CD" + System.currentTimeMillis());
            customs.setShipmentId(shipment.getShipmentId());

            // 设置默认值
            customs.setCountryOfOrigin("China");
            customs.setConsignor("Shanghai Warehouse");
            customs.setConsignee(shipment.getDestination());
            customs.setDestinationCountry(extractCountry(shipment.getDestination()));
            customs.setStatus("Draft"); // 设置默认状态为草稿

            // 关联到shipment
            shipment.setCustomsDeclaration(customs);

            // 如果有目录，添加到目录中
            if (organization != null && organization.getCustomsDeclarationDirectory() != null) {
                organization.getCustomsDeclarationDirectory().addCustomsDeclaration(customs);
            }

            // 保存到系统
            EcoSystem system = EcoSystem.getInstance();
            DB4OUtil.getInstance().storeSystem(system);
        }

        // 填充基本信息字段
        if (fields.length > 0) {
            fields[0].setText(customs.getDeclarationId() != null ? customs.getDeclarationId() : "");
        }
        if (fields.length > 1 && fields[1] != null) {
            fields[1].setText(customs.getDeclarationNumber() != null ? customs.getDeclarationNumber() : "");
        }
        if (fields.length > 2 && fields[2] != null) {
            fields[2].setText(customs.getHsCode() != null ? customs.getHsCode() : "");
        }
        if (fields.length > 3 && fields[3] != null) {
            fields[3].setText(customs.getConsignor() != null ? customs.getConsignor() : "");
        }
        if (fields.length > 4 && fields[4] != null) {
            fields[4].setText(customs.getConsignee() != null ? customs.getConsignee() : "");
        }
        if (fields.length > 5 && fields[5] != null) {
            fields[5].setText(customs.getCountryOfOrigin() != null ? customs.getCountryOfOrigin() : "");
        }
        if (fields.length > 6 && fields[6] != null) {
            fields[6].setText(customs.getDestinationCountry() != null ? customs.getDestinationCountry() : "");
        }
        if (fields.length > 7 && fields[7] != null) {
            fields[7].setText(customs.getCustomsOffice() != null ? customs.getCustomsOffice() : "");
        }

        // 设置下拉框
        if (combos != null && combos.length > 0 && combos[0] != null && customs.getDeclarationType() != null) {
            for (int i = 0; i < combos[0].getItemCount(); i++) {
                if (combos[0].getItemAt(i).equals(customs.getDeclarationType())) {
                    combos[0].setSelectedIndex(i);
                    break;
                }
            }
        }

        if (combos != null && combos.length > 1 && combos[1] != null && customs.getStatus() != null) {
            for (int i = 0; i < combos[1].getItemCount(); i++) {
                if (combos[1].getItemAt(i).equals(customs.getStatus())) {
                    combos[1].setSelectedIndex(i);
                    break;
                }
            }
        }

        // 设置文本区域
        if (textAreas != null && textAreas.length > 0 && textAreas[0] != null) {
            textAreas[0].setText(customs.getNotes() != null ? customs.getNotes() : "");
        }

        // 加载物品表格
        if (itemsTable != null) {
            DefaultTableModel model = (DefaultTableModel) itemsTable.getModel();
            model.setRowCount(0);

            if (customs.getItems() != null) {
                for (CustomsDeclaration.CustomsLineItem item : customs.getItems()) {
                    // 根据表格列数调整添加的数据
                    if (model.getColumnCount() == 3) {
                        // 简化版表格
                        model.addRow(new Object[]{
                            item.getDescription(),
                            item.getQuantity(),
                            item.getUnitValue()
                        });
                    } else {
                        // 完整版表格
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

        System.out.println("已加载海关信息 - ID: " + customs.getDeclarationId()
                + ", Status: " + customs.getStatus()
                + ", 物品数量: " + (customs.getItems() != null ? customs.getItems().size() : 0));
    }

// 保存海关信息
    private void saveCustomsInfo(JPanel panel, JTextField[] fields, JComboBox[] combos,
            JTextArea[] textAreas, JTable itemsTable) {
        int selectedRow = tblShipment.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(panel, "Please select a shipment first",
                    "No Shipment Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String trackingNumber = tblShipment.getValueAt(selectedRow, 0).toString();
        Shipment shipment = findShipmentByTrackingNumber(trackingNumber);

        if (shipment != null) {
            CustomsDeclaration customs = shipment.getCustomsDeclaration();
            if (customs == null) {
                customs = new CustomsDeclaration();
                customs.setDeclarationId("CD" + System.currentTimeMillis());
                customs.setShipmentId(shipment.getShipmentId());
                shipment.setCustomsDeclaration(customs);

                // 如果有目录，添加到目录中
                if (organization != null && organization.getCustomsDeclarationDirectory() != null) {
                    organization.getCustomsDeclarationDirectory().addCustomsDeclaration(customs);
                }
            }

            // 设置字段值
            if (fields.length > 0 && fields[0] != null && !fields[0].getText().isEmpty()) {
                customs.setDeclarationId(fields[0].getText());
            }

            if (fields.length > 1 && fields[1] != null) {
                customs.setDeclarationNumber(fields[1].getText());
            }

            if (fields.length > 2 && fields[2] != null) {
                customs.setHsCode(fields[2].getText());
            }

            if (fields.length > 3 && fields[3] != null) {
                customs.setConsignor(fields[3].getText());
            }

            if (fields.length > 4 && fields[4] != null) {
                customs.setConsignee(fields[4].getText());
            }

            if (fields.length > 5 && fields[5] != null) {
                customs.setCountryOfOrigin(fields[5].getText());
            }

            if (fields.length > 6 && fields[6] != null) {
                customs.setDestinationCountry(fields[6].getText());
            }

            if (fields.length > 7 && fields[7] != null) {
                customs.setCustomsOffice(fields[7].getText());
            }

            if (combos != null && combos.length > 0 && combos[0] != null) {
                customs.setDeclarationType(combos[0].getSelectedItem().toString());
            }

            if (combos != null && combos.length > 1 && combos[1] != null) {
                customs.setStatus(combos[1].getSelectedItem().toString());
            }

            if (textAreas != null && textAreas.length > 0 && textAreas[0] != null) {
                customs.setNotes(textAreas[0].getText());
            }

            // 处理物品
            ArrayList<CustomsDeclaration.CustomsLineItem> items = new ArrayList<>();
            DefaultTableModel model = (DefaultTableModel) itemsTable.getModel();

            for (int i = 0; i < model.getRowCount(); i++) {
                CustomsDeclaration.CustomsLineItem item = new CustomsDeclaration.CustomsLineItem();

                // 根据表格列数提取数据
                if (model.getColumnCount() == 3) {
                    // 简化版表格 (Description, Quantity, Unit Value)
                    item.setDescription((String) model.getValueAt(i, 0));
                    item.setQuantity(Integer.parseInt(model.getValueAt(i, 1).toString()));
                    item.setUnit("PCS"); // 默认单位
                    item.setUnitValue(Double.parseDouble(model.getValueAt(i, 2).toString()));
                    item.setTotalValue(item.getQuantity() * item.getUnitValue());
                    item.setGrossWeight(item.getQuantity() * 0.5); // 估计重量
                    item.setHsCode(""); // 空HS代码
                } else if (model.getColumnCount() >= 7) {
                    // 完整版表格
                    item.setDescription((String) model.getValueAt(i, 0));
                    item.setHsCode((String) model.getValueAt(i, 1));
                    item.setQuantity(Integer.parseInt(model.getValueAt(i, 2).toString()));
                    item.setUnit((String) model.getValueAt(i, 3));
                    item.setUnitValue(Double.parseDouble(model.getValueAt(i, 4).toString()));
                    item.setTotalValue(Double.parseDouble(model.getValueAt(i, 5).toString()));
                    item.setGrossWeight(Double.parseDouble(model.getValueAt(i, 6).toString()));
                }

                items.add(item);
            }
            customs.setItems(items);

            // 设置申报日期（如果未设置）
            if (customs.getDeclarationDate() == null) {
                customs.setDeclarationDate(new Date());
            }

            // 如果状态为"Submitted"且未设置提交日期，则设置
            if ("Submitted".equals(customs.getStatus()) && customs.getSubmissionDate() == null) {
                customs.setSubmissionDate(new Date());
            }

            // 如果状态为"Approved"或"Rejected"且未设置处理日期，则设置
            if (("Approved".equals(customs.getStatus()) || "Rejected".equals(customs.getStatus()))
                    && customs.getProcessingDate() == null) {
                customs.setProcessingDate(new Date());
            }

            // 保存到系统
            EcoSystem system = EcoSystem.getInstance();
            DB4OUtil.getInstance().storeSystem(system);

            JOptionPane.showMessageDialog(panel, "Customs information saved successfully",
                    "Save Successful", JOptionPane.INFORMATION_MESSAGE);

            System.out.println("已保存海关信息 - ID: " + customs.getDeclarationId()
                    + ", Status: " + customs.getStatus()
                    + ", 物品数量: " + (customs.getItems() != null ? customs.getItems().size() : 0));
        }
    }

// 提取国家名称（简单方法）
    private String extractCountry(String destination) {
        if (destination == null || destination.isEmpty()) {
            return "";
        }

        // 假设目的地的最后一个词是国家
        String[] parts = destination.split(",");
        if (parts.length > 0) {
            String lastPart = parts[parts.length - 1].trim();

            // 处理一些常见的城市和国家
            if (lastPart.contains("China") || lastPart.contains("Beijing")
                    || lastPart.contains("Shanghai") || lastPart.contains("Guangzhou")) {
                return "China";
            } else if (lastPart.contains("USA") || lastPart.contains("US")
                    || lastPart.contains("United States") || lastPart.contains("New York")
                    || lastPart.contains("Los Angeles") || lastPart.contains("Boston")
                    || lastPart.contains("Chicago") || lastPart.contains("Houston")
                    || lastPart.contains("Phoenix")) {
                return "United States";
            } else if (lastPart.contains("UK") || lastPart.contains("London")
                    || lastPart.contains("England") || lastPart.contains("United Kingdom")) {
                return "United Kingdom";
            } else if (lastPart.contains("Japan") || lastPart.contains("Tokyo")) {
                return "Japan";
            } else if (lastPart.contains("Germany") || lastPart.contains("Berlin")) {
                return "Germany";
            } else if (lastPart.contains("France") || lastPart.contains("Paris")) {
                return "France";
            } else if (lastPart.contains("Italy") || lastPart.contains("Rome")) {
                return "Italy";
            } else if (lastPart.contains("Canada") || lastPart.contains("Toronto")
                    || lastPart.contains("Vancouver") || lastPart.contains("Montreal")) {
                return "Canada";
            } else if (lastPart.contains("Australia") || lastPart.contains("Sydney")
                    || lastPart.contains("Melbourne")) {
                return "Australia";
            } else if (lastPart.contains("Brazil") || lastPart.contains("Rio")
                    || lastPart.contains("Sao Paulo")) {
                return "Brazil";
            } else if (lastPart.contains("India") || lastPart.contains("Mumbai")
                    || lastPart.contains("Delhi")) {
                return "India";
            } else if (lastPart.contains("Russia") || lastPart.contains("Moscow")) {
                return "Russia";
            } else if (lastPart.contains("Spain") || lastPart.contains("Madrid")) {
                return "Spain";
            } else if (lastPart.contains("Mexico") || lastPart.contains("Mexico City")) {
                return "Mexico";
            } else if (lastPart.contains("South Korea") || lastPart.contains("Seoul")) {
                return "South Korea";
            } else {
                return lastPart;  // 返回最后一部分作为国家
            }
        }

        return "";
    }

    private JPanel createPackageInfoPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // 创建主内容面板，使用 GridBagLayout
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 设置约束条件
        GridBagConstraints labelConstraints = new GridBagConstraints();
        labelConstraints.gridx = 0;
        labelConstraints.gridy = 0;
        labelConstraints.anchor = GridBagConstraints.EAST;
        labelConstraints.insets = new Insets(10, 10, 10, 10);

        GridBagConstraints fieldConstraints = new GridBagConstraints();
        fieldConstraints.gridx = 1;
        fieldConstraints.gridy = 0;
        fieldConstraints.fill = GridBagConstraints.HORIZONTAL;
        fieldConstraints.weightx = 1.0;
        fieldConstraints.insets = new Insets(10, 0, 10, 10);

        // 设置文本框的标准大小
        Dimension textFieldSize = new Dimension(250, 30);

        // 添加包裹信息字段
        JLabel lblWeight = new JLabel("Weight (kg):");
        JTextField txtWeight = new JTextField();
        txtWeight.setPreferredSize(textFieldSize);

        JLabel lblDimensions = new JLabel("Dimensions (cm):");
        JTextField txtDimensions = new JTextField();
        txtDimensions.setPreferredSize(textFieldSize);

        JLabel lblPackageType = new JLabel("Package Type:");
        JTextField txtPackageType = new JTextField();
        txtPackageType.setPreferredSize(textFieldSize);

        JLabel lblSpecialHandling = new JLabel("Special Handling:");
        JTextField txtSpecialHandling = new JTextField();
        txtSpecialHandling.setPreferredSize(textFieldSize);

        contentPanel.add(lblWeight, labelConstraints);
        contentPanel.add(txtWeight, fieldConstraints);

        labelConstraints.gridy++;
        fieldConstraints.gridy++;
        contentPanel.add(lblDimensions, labelConstraints);
        contentPanel.add(txtDimensions, fieldConstraints);

        labelConstraints.gridy++;
        fieldConstraints.gridy++;
        contentPanel.add(lblPackageType, labelConstraints);
        contentPanel.add(txtPackageType, fieldConstraints);

        labelConstraints.gridy++;
        fieldConstraints.gridy++;
        contentPanel.add(lblSpecialHandling, labelConstraints);
        contentPanel.add(txtSpecialHandling, fieldConstraints);

        // 添加填充空间，将内容推到顶部
        GridBagConstraints fillerConstraints = new GridBagConstraints();
        fillerConstraints.gridx = 0;
        fillerConstraints.gridy = labelConstraints.gridy + 1;
        fillerConstraints.weighty = 1.0;
        fillerConstraints.fill = GridBagConstraints.BOTH;
        fillerConstraints.gridwidth = 2;
        contentPanel.add(Box.createVerticalGlue(), fillerConstraints);

        // 将内容面板添加到主面板
        panel.add(contentPanel, BorderLayout.NORTH);

        return panel;
    }

    private JPanel createFinancialInfoPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // 创建主内容面板，使用 GridBagLayout
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 设置约束条件
        GridBagConstraints labelConstraints = new GridBagConstraints();
        labelConstraints.gridx = 0;
        labelConstraints.gridy = 0;
        labelConstraints.anchor = GridBagConstraints.EAST;
        labelConstraints.insets = new Insets(10, 10, 10, 10);

        GridBagConstraints fieldConstraints = new GridBagConstraints();
        fieldConstraints.gridx = 1;
        fieldConstraints.gridy = 0;
        fieldConstraints.fill = GridBagConstraints.HORIZONTAL;
        fieldConstraints.weightx = 1.0;
        fieldConstraints.insets = new Insets(10, 0, 10, 10);

        // 设置文本框的标准大小
        Dimension textFieldSize = new Dimension(250, 30);

        // 添加财务信息字段
        JLabel lblShippingCost = new JLabel("Shipping Cost:");
        JTextField txtShippingCost = new JTextField();
        txtShippingCost.setPreferredSize(textFieldSize);

        JLabel lblInsuranceCost = new JLabel("Insurance Cost:");
        JTextField txtInsuranceCost = new JTextField();
        txtInsuranceCost.setPreferredSize(textFieldSize);

        JLabel lblTaxes = new JLabel("Taxes:");
        JTextField txtTaxes = new JTextField();
        txtTaxes.setPreferredSize(textFieldSize);

        JLabel lblTotalCost = new JLabel("Total Cost:");
        JTextField txtTotalCost = new JTextField();
        txtTotalCost.setPreferredSize(textFieldSize);

        contentPanel.add(lblShippingCost, labelConstraints);
        contentPanel.add(txtShippingCost, fieldConstraints);

        labelConstraints.gridy++;
        fieldConstraints.gridy++;
        contentPanel.add(lblInsuranceCost, labelConstraints);
        contentPanel.add(txtInsuranceCost, fieldConstraints);

        labelConstraints.gridy++;
        fieldConstraints.gridy++;
        contentPanel.add(lblTaxes, labelConstraints);
        contentPanel.add(txtTaxes, fieldConstraints);

        labelConstraints.gridy++;
        fieldConstraints.gridy++;
        contentPanel.add(lblTotalCost, labelConstraints);
        contentPanel.add(txtTotalCost, fieldConstraints);

        // 添加填充空间，将内容推到顶部
        GridBagConstraints fillerConstraints = new GridBagConstraints();
        fillerConstraints.gridx = 0;
        fillerConstraints.gridy = labelConstraints.gridy + 1;
        fillerConstraints.weighty = 1.0;
        fillerConstraints.fill = GridBagConstraints.BOTH;
        fillerConstraints.gridwidth = 2;
        contentPanel.add(Box.createVerticalGlue(), fillerConstraints);

        // 将内容面板添加到主面板
        panel.add(contentPanel, BorderLayout.NORTH);

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
        try {
            System.out.println("正在初始化WebView...");

            // 创建WebView和WebEngine
            webView = new WebView();
            webEngine = webView.getEngine();

            // 添加JavaScript控制台输出处理
            webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
                System.out.println("页面加载状态: " + newState);

                if (newState == javafx.concurrent.Worker.State.SUCCEEDED) {
                    System.out.println("页面加载成功");

                    // 不要使用java对象桥接，改用简单的console日志重定向
                    try {
                        webEngine.executeScript(
                                "console.originalLog = console.log;"
                                + "console.log = function(message) { "
                                + "  console.originalLog('JS日志: ' + message);"
                                + "};"
                                + "console.originalError = console.error;"
                                + "console.error = function(message) { "
                                + "  console.originalError('JS错误: ' + message);"
                                + "};"
                                + "window.onerror = function(message, source, lineno, colno, error) { "
                                + "  console.error('页面错误: ' + message + ' 在 ' + source + ' 的第 ' + lineno + ' 行');"
                                + "  document.getElementById('error-container').style.display = 'block';"
                                + "  document.getElementById('error-container').innerHTML = "
                                + "    '<div style=\"color:white;font-size:14px;\">"
                                + "      <b>JavaScript错误</b><br>"
                                + "      ' + message + '<br>"
                                + "      位置: 第 ' + lineno + ' 行"
                                + "    </div>';"
                                + "  return true;"
                                + "};"
                                + "console.log('JavaScript错误处理已设置');"
                        );
                    } catch (Exception e) {
                        System.err.println("设置JavaScript错误处理时出错: " + e.getMessage());
                    }
                } else if (newState == javafx.concurrent.Worker.State.FAILED) {
                    Throwable ex = webEngine.getLoadWorker().getException();
                    System.err.println("页面加载失败: " + (ex != null ? ex.getMessage() : "未知错误"));
                    if (ex != null) {
                        ex.printStackTrace();
                    }
                }
            });

            // 设置WebView填充容器
            webView.setPrefSize(800, 600);

            // 创建场景并设置到JFXPanel
            Scene scene = new Scene(webView);
            jfxPanel.setScene(scene);

            System.out.println("准备加载地图HTML...");

            // 使用更简单的HTML，避免任何Java桥接
            String mapHTML = createSimpleMapHTML();
            System.out.println("HTML长度: " + mapHTML.length() + " 字符");

            // 直接使用loadContent而不是load URL
            webEngine.loadContent(mapHTML);
            System.out.println("已请求加载HTML内容");

        } catch (Exception e) {
            System.err.println("初始化WebView时出错: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String createSimpleMapHTML() {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>");
        html.append("<html>");
        html.append("<head>");
        html.append("<meta charset=\"UTF-8\">");
        html.append("<title>物流跟踪地图</title>");
        html.append("<style>");
        html.append("html, body, #map { height: 100%; margin: 0; padding: 0; }");
        html.append("#error-container { ");
        html.append("  position: absolute; top: 10px; left: 10px; right: 10px; padding: 10px;");
        html.append("  background-color: rgba(255,0,0,0.7); color: white; z-index: 9999;");
        html.append("  text-align: center; border-radius: 5px; display: none; }");
        html.append("</style>");
        html.append("</head>");
        html.append("<body>");

        html.append("<div id=\"map\"></div>");
        html.append("<div id=\"error-container\"></div>");

        html.append("<script>");
        // 最简单的地图初始化代码
        html.append("function initMap() {");
        html.append("  try {");
        html.append("    console.log('初始化Google Maps...');");
        html.append("    var mapOptions = {");
        html.append("      center: {lat: 31.2304, lng: 121.4737},");
        html.append("      zoom: 5");
        html.append("    };");
        html.append("    var mapDiv = document.getElementById('map');");
        html.append("    if (!mapDiv) {");
        html.append("      console.error('找不到地图容器元素');");
        html.append("      return;");
        html.append("    }");
        html.append("    window.map = new google.maps.Map(mapDiv, mapOptions);");
        html.append("    console.log('地图初始化完成');");
        html.append("  } catch(e) {");
        html.append("    console.error('地图初始化错误: ' + e.message);");
        html.append("    var errorDiv = document.getElementById('error-container');");
        html.append("    if (errorDiv) {");
        html.append("      errorDiv.style.display = 'block';");
        html.append("      errorDiv.innerHTML = '<b>地图初始化错误:</b> ' + e.message;");
        html.append("    }");
        html.append("  }");
        html.append("}");

        // 添加标记的简单函数
        html.append("function addMarker(lat, lng, title) {");
        html.append("  if (!window.map) return;");
        html.append("  new google.maps.Marker({");
        html.append("    position: {lat: lat, lng: lng},");
        html.append("    map: window.map,");
        html.append("    title: title");
        html.append("  });");
        html.append("}");

        // 检查Google Maps API是否加载
        html.append("window.setTimeout(function() {");
        html.append("  if (typeof google === 'undefined') {");
        html.append("    var errorDiv = document.getElementById('error-container');");
        html.append("    if (errorDiv) {");
        html.append("      errorDiv.style.display = 'block';");
        html.append("      errorDiv.innerHTML = '<b>错误</b>: Google Maps API未能加载，请检查API密钥和网络连接';");
        html.append("    }");
        html.append("  }");
        html.append("}, 5000);"); // 5秒后检查

        html.append("</script>");

        // 加载Google Maps API
        html.append("<script src=\"https://maps.googleapis.com/maps/api/js?key=");
        html.append(GOOGLE_MAPS_API_KEY);
        html.append("&callback=initMap\" async defer></script>");

        html.append("</body>");
        html.append("</html>");

        return html.toString();
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
        html.append("<style>");
        html.append("html, body, #map { height: 100%; margin: 0; padding: 0; }");
        html.append("#error-container { ");
        html.append("  position: absolute; top: 10px; left: 10px; right: 10px; padding: 10px;");
        html.append("  background-color: rgba(255,0,0,0.7); color: white; z-index: 9999;");
        html.append("  border-radius: 5px; display: none; }");
        html.append("</style>");
        html.append("</head>");
        html.append("<body>");

        html.append("<div id=\"map\"></div>");
        html.append("<div id=\"error-container\"></div>");

        html.append("<script>");
        // 简化的错误处理
        html.append("window.onerror = function(msg, url, line, col, error) {");
        html.append("  var container = document.getElementById('error-container');");
        html.append("  container.style.display = 'block';");
        html.append("  container.innerHTML = '<b>错误：</b> ' + msg + '<br><b>位置：</b> 第 ' + line + ' 行';");
        html.append("  console.error('JavaScript错误: ' + msg + ' at ' + line);");
        html.append("  return false;");
        html.append("};");

        // 最简单的地图初始化代码
        html.append("function initMap() {");
        html.append("  try {");
        html.append("    console.log('初始化地图...');");
        html.append("    var mapOptions = {");
        html.append("      center: {lat: 31.2304, lng: 121.4737},"); // 上海
        html.append("      zoom: 4");
        html.append("    };");
        html.append("    window.map = new google.maps.Map(document.getElementById('map'), mapOptions);");
        html.append("    console.log('地图初始化成功');");
        html.append("  } catch(e) {");
        html.append("    console.error('地图初始化失败: ' + e.message);");
        html.append("    document.getElementById('error-container').style.display = 'block';");
        html.append("    document.getElementById('error-container').innerHTML = '<b>地图初始化失败：</b> ' + e.message;");
        html.append("  }");
        html.append("}");

        html.append("function checkGoogleMapsLoaded() {");
        html.append("  if (typeof google === 'undefined') {");
        html.append("    console.error('Google Maps API未加载');");
        html.append("    document.getElementById('error-container').style.display = 'block';");
        html.append("    document.getElementById('error-container').innerHTML = '<b>错误：</b> Google Maps API未加载，可能是API密钥问题或网络连接问题';");
        html.append("  } else {");
        html.append("    console.log('Google Maps API已加载');");
        html.append("  }");
        html.append("}");

        html.append("setTimeout(checkGoogleMapsLoaded, 5000);"); // 5秒后检查API是否加载

        html.append("</script>");

        // 加载Google Maps API，确保API密钥正确
        html.append("<script src=\"https://maps.googleapis.com/maps/api/js?key=");
        html.append(GOOGLE_MAPS_API_KEY);
        html.append("&callback=initMap\" async defer></script>");

        html.append("</body>");
        html.append("</html>");

        System.out.println("生成的HTML内容片段: " + html.substring(0, 300) + "...");
        System.out.println("Google Maps API密钥: " + GOOGLE_MAPS_API_KEY);

        return html.toString();

    }

    /**
     * 使用跟踪点更新地图
     *
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

    private void showStaticMap(String location) {
        try {
            // Create a static map URL
            String encodedLocation = java.net.URLEncoder.encode(location, "UTF-8");
            String staticMapUrl = "https://maps.googleapis.com/maps/api/staticmap?"
                    + "center=" + encodedLocation
                    + "&zoom=5&size=600x400"
                    + "&markers=color:red%7C" + encodedLocation
                    + "&key=" + GOOGLE_MAPS_API_KEY;
            System.out.println("Static Map URL: " + staticMapUrl);
            // Load static map image
            URL url = new URL(staticMapUrl);
            BufferedImage img = ImageIO.read(url);
            if (img != null) {
                // Create image display component
                JLabel mapLabel = new JLabel(new ImageIcon(img));
                mapLabel.setHorizontalAlignment(JLabel.CENTER);
                // Clear trackPathJPanel and add static map
                trackPathJPanel.removeAll();
                trackPathJPanel.setLayout(new BorderLayout());
                trackPathJPanel.add(mapLabel, BorderLayout.CENTER);
                trackPathJPanel.revalidate();
                trackPathJPanel.repaint();
                System.out.println("Static map loaded successfully");
            } else {
                System.err.println("Static map image loading failed");
                // Display error message
                JLabel errorLabel = new JLabel("Map loading failed: Unable to get map image");
                errorLabel.setHorizontalAlignment(JLabel.CENTER);
                errorLabel.setForeground(java.awt.Color.RED);
                trackPathJPanel.removeAll();
                trackPathJPanel.add(errorLabel, BorderLayout.CENTER);
                trackPathJPanel.revalidate();
                trackPathJPanel.repaint();
            }
        } catch (Exception e) {
            System.err.println("Error loading static map: " + e.getMessage());
            e.printStackTrace();
            // Display error message
            JLabel errorLabel = new JLabel("Map loading error: " + e.getMessage());
            errorLabel.setHorizontalAlignment(JLabel.CENTER);
            errorLabel.setForeground(java.awt.Color.RED);
            trackPathJPanel.removeAll();
            trackPathJPanel.add(errorLabel, BorderLayout.CENTER);
            trackPathJPanel.revalidate();
            trackPathJPanel.repaint();
        }
    }

    private void displayStaticMap(Shipment shipment) {
        try {
            // Clear container panel
            trackPathJPanel.removeAll();
            trackPathJPanel.setLayout(new BorderLayout());
            // Add loading indicator
            JLabel loadingLabel = new JLabel("Loading map...");
            loadingLabel.setHorizontalAlignment(JLabel.CENTER);
            loadingLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 16));
            trackPathJPanel.add(loadingLabel, BorderLayout.CENTER);
            trackPathJPanel.revalidate();
            trackPathJPanel.repaint();
            // Load map in background thread
            new Thread(() -> {
                try {
                    // Ensure destination information exists
                    String destination = shipment.getDestination();
                    if (destination == null || destination.isEmpty()) {
                        destination = "Unknown";
                    }
                    // Encode destination
                    String encodedDestination = java.net.URLEncoder.encode(destination, "UTF-8");
                    // Build static map URL
                    String staticMapUrl = "https://maps.googleapis.com/maps/api/staticmap?"
                            + "center=" + encodedDestination
                            + "&zoom=3&size=600x400"
                            + "&markers=color:red%7Clabel:B%7C" + encodedDestination
                            + "&markers=color:blue%7Clabel:A%7C31.2304,121.4737"
                            + // Shanghai location
                            "&path=color:red%7Cweight:3%7C31.2304,121.4737%7C" + encodedDestination
                            + "&key=" + GOOGLE_MAPS_API_KEY;
                    System.out.println("Static Map URL: " + staticMapUrl);
                    // Load map image
                    URL url = new URL(staticMapUrl);
                    final BufferedImage image = ImageIO.read(url);
                    // Update UI
                    SwingUtilities.invokeLater(() -> {
                        if (image != null) {
                            JLabel mapLabel = new JLabel(new ImageIcon(image));
                            mapLabel.setHorizontalAlignment(JLabel.CENTER);
                            trackPathJPanel.removeAll();
                            trackPathJPanel.add(mapLabel, BorderLayout.CENTER);
                            // Add information panel
                            JPanel infoPanel = createTrackingInfoPanel(shipment);
                            trackPathJPanel.add(infoPanel, BorderLayout.EAST);
                            trackPathJPanel.revalidate();
                            trackPathJPanel.repaint();
                        } else {
                            displayErrorMessage("Unable to load map image");
                        }
                    });
                } catch (Exception e) {
                    System.err.println("Error loading static map: " + e.getMessage());
                    e.printStackTrace();
                    SwingUtilities.invokeLater(() -> displayErrorMessage("Error loading map: " + e.getMessage()));
                }
            }).start();
        } catch (Exception e) {
            System.err.println("Error displaying static map: " + e.getMessage());
            e.printStackTrace();
            displayErrorMessage("Error displaying map: " + e.getMessage());
        }
    }

// 创建跟踪信息面板
    private JPanel createTrackingInfoPanel(Shipment shipment) {
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        infoPanel.setPreferredSize(new Dimension(300, 0));
        JLabel titleLabel = new JLabel("Shipment Route Information");
        titleLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 16));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        infoPanel.add(titleLabel);
        infoPanel.add(Box.createVerticalStrut(10));
        // Add shipping information
        addInfoLabel(infoPanel, "Tracking No: " + shipment.getTrackingNumber());
        addInfoLabel(infoPanel, "Origin: Shanghai Warehouse");
        addInfoLabel(infoPanel, "Destination: " + shipment.getDestination());
        addInfoLabel(infoPanel, "Current Status: " + shipment.getShipmentStatus());
        if (shipment.getShipDate() != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            addInfoLabel(infoPanel, "Ship Date: " + dateFormat.format(shipment.getShipDate()));
        }
        if (shipment.getEstimatedDeliveryDate() != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            addInfoLabel(infoPanel, "Estimated Delivery: " + dateFormat.format(shipment.getEstimatedDeliveryDate()));
        }
        // Add separator
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(new JSeparator());
        infoPanel.add(Box.createVerticalStrut(10));
        // Add tracking history
        JLabel historyLabel = new JLabel("Tracking History");
        historyLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 16));
        historyLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        infoPanel.add(historyLabel);
        infoPanel.add(Box.createVerticalStrut(10));
        List<TrackingInfo> trackingHistory = shipment.getTrackingHistory();
        if (trackingHistory != null && !trackingHistory.isEmpty()) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            for (TrackingInfo info : trackingHistory) {
                JPanel trackPoint = new JPanel();
                trackPoint.setLayout(new BoxLayout(trackPoint, BoxLayout.Y_AXIS));
                trackPoint.setBorder(BorderFactory.createEtchedBorder());
                trackPoint.setAlignmentX(Component.LEFT_ALIGNMENT);
                addInfoLabel(trackPoint, "Time: " + dateFormat.format(info.getTimestamp()));
                addInfoLabel(trackPoint, "Location: " + info.getLocation());
                addInfoLabel(trackPoint, "Status: " + info.getStatus());
                addInfoLabel(trackPoint, "Description: " + info.getDescription());
                infoPanel.add(trackPoint);
                infoPanel.add(Box.createVerticalStrut(10));
            }
        } else {
            addInfoLabel(infoPanel, "No tracking records available");
        }
        return infoPanel;
    }

// 显示错误信息
    private void displayErrorMessage(String message) {
        JLabel errorLabel = new JLabel(message);
        errorLabel.setHorizontalAlignment(JLabel.CENTER);
        errorLabel.setForeground(java.awt.Color.RED);
        errorLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 14));

        trackPathJPanel.removeAll();
        trackPathJPanel.add(errorLabel, BorderLayout.CENTER);
        trackPathJPanel.revalidate();
        trackPathJPanel.repaint();
    }

    // Add this method near other UI-related methods
    /**
     * Apply consistent UI theme to all components
     */
    private void setupTheme() {
        // Set panel background color
        this.setBackground(new Color(240, 245, 255));

        // Style all buttons
        styleAllButtons();

        // Style table
        styleTable(tblShipment);

        // Style text fields
        styleAllTextFields();

        // Style labels
        styleAllLabels();
    }

    /**
     * Style all buttons in the panel
     */
    private void styleAllButtons() {
        styleButton(btnBack);
        styleButton(btnRefresh);
        styleButton(btnSearch);
        styleButton(btnViewDetails);
        styleButton(btnAddNewShip);
        styleButton(btnUpdateStatus);
        styleButton(btnUpdate);
        styleButton(btnShip);
        styleButton(btnDelete);
        styleButton(btnBasicInfo);
        styleButton(btnCustomsInfo);
        styleButton(btnPackageInfo);
        styleButton(btnFinancialInfo);
    }

    /**
     * Apply consistent styling to a button
     *
     * @param button Button to style
     */
    private void styleButton(JButton button) {
        button.setBackground(new Color(26, 79, 156)); // Medium blue
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        // Add a subtle border with rounded corners
        button.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
        button.setFont(new Font("Helvetica Neue", Font.BOLD, 14));

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(35, 100, 190)); // Lighter blue on hover
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(26, 79, 156)); // Back to normal
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(13, 60, 130)); // Darker when pressed
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(35, 100, 190)); // Back to hover
            }
        });
    }

    /**
     * Style all text fields in the panel
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
        textField.setBackground(new Color(245, 245, 250)); // Light gray-white background
        textField.setForeground(new Color(13, 25, 51));    // Dark blue text
        textField.setCaretColor(new Color(26, 79, 156));   // Medium blue cursor
        textField.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(90, 141, 224), 1));
        textField.setFont(new Font("Helvetica Neue", Font.PLAIN, 14));
    }

    /**
     * Style all labels in the panel
     */
    private void styleAllLabels() {
        styleTitleLabel(lblLogisticsTracking);
        styleTitleLabel(lblTrackPath);
        styleTitleLabel(lblTrackDetails);
        styleLabel(lblTraNo);
        styleLabel(lblShippingDate);
        styleLabel(lblShippingMethod);
        styleLabel(lblDestination);
        styleLabel(lblStatus);
        styleLabel(lblTrackingNo);
    }

    /**
     * Apply title label styling
     *
     * @param label Label to style
     */
    private void styleTitleLabel(JLabel label) {
        label.setFont(new Font("Helvetica Neue", Font.BOLD, 18));
        label.setForeground(new Color(13, 25, 51)); // Dark blue text
    }

    /**
     * Apply regular label styling
     *
     * @param label Label to style
     */
    private void styleLabel(JLabel label) {
        label.setFont(new Font("Helvetica Neue", Font.PLAIN, 14));
        label.setForeground(new Color(13, 25, 51)); // Dark blue text
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
            table.getTableHeader().setFont(new Font("Helvetica Neue", Font.BOLD, 14));
        }

        // Style the table
        table.setBackground(Color.WHITE);
        table.setForeground(new Color(13, 25, 51)); // Dark blue text
        table.setGridColor(new Color(230, 230, 230));
        table.setRowHeight(25);
        table.setFont(new Font("Helvetica Neue", Font.PLAIN, 14));
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
