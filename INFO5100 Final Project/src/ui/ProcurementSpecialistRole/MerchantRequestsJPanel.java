/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui.ProcurementSpecialistRole;

import Business.Enterprise.Enterprise;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.CardLayout;
import Business.Order.Order;
import Business.Order.OrderDirectory;
import Business.Warehouse.Warehouse;
import Business.WorkQueue.MerchantWorkRequest;
import Business.WorkQueue.WorkRequest;
import java.util.List;
import java.util.ArrayList;
import Business.Product.Product;
import Business.UserAccount.UserAccount;
import Business.WorkQueue.ProcurementWorkRequest;
import java.util.Date;

/**
 *
 * @author wangsiting
 */
public class MerchantRequestsJPanel extends javax.swing.JPanel {

    private OrderDirectory orderDirectory;
    private DefaultTableModel cartTableModel;
    private JPanel userProcessContainer;
    private Warehouse warehouse;
    private Enterprise enterprise;          // 添加
    private UserAccount userAccount;        // 添加

    /**
     * Creates new form WarehouseRequestsJPanel
     */
    public MerchantRequestsJPanel(JPanel userProcessContainer, Enterprise enterprise, UserAccount account) {
        initComponents();
        this.userProcessContainer = userProcessContainer;
        this.enterprise = enterprise;
        this.userAccount = account;
        orderDirectory = new OrderDirectory();
        warehouse = Warehouse.getInstance();
        setupCartTable();

        // 加载所有商家请求
        loadMerchantRequests();
    }

    private void setupCartTable() {
        String[] columnNames = {"Product Name", "Purchase Cost", "Quantity", "Total Amount"};
        cartTableModel = (DefaultTableModel) tblCart.getModel();
        cartTableModel.setColumnIdentifiers(columnNames);
    }

    private void loadMerchantRequests() {
        // 清空表格
        DefaultTableModel model = (DefaultTableModel) RequestTable1.getModel();
        model.setRowCount(0);

        // 打印调试信息
        System.out.println("Starting to load merchant request table...");

        // 从EcoSystem获取商家请求
        List<WorkRequest> requests = new ArrayList<>();
        Business.EcoSystem system = Business.EcoSystem.getInstance();
        if (system.getWorkQueue() != null) {
            System.out.println("System work queue exists, total requests: " + system.getWorkQueue().getWorkRequestList().size());
            for (WorkRequest req : system.getWorkQueue().getWorkRequestList()) {
                if (req instanceof MerchantWorkRequest) {
                    MerchantWorkRequest merchantReq = (MerchantWorkRequest) req;
                    // 确保至少有产品ID才添加请求
                    if (merchantReq.getProductId() != null && !merchantReq.getProductId().trim().isEmpty()) {
                        requests.add(req);
                        System.out.println("Found valid merchant request: Product=" + merchantReq.getProductName()
                                + ", ID=" + merchantReq.getProductId()
                                + ", Status=" + merchantReq.getStatus());
                    } else {
                        System.out.println("Skipping invalid request: Product ID is empty");
                    }
                }
            }
        } else {
            System.err.println("Error: System work queue is empty");
        }

        if (requests.isEmpty()) {
            System.out.println("No valid merchant requests found, showing empty table!");
            return;
        }

        System.out.println("Found " + requests.size() + " valid merchant requests");

        // 过滤请求（根据下拉框状态）
        String selectedStatus = StatusjComboBox.getSelectedItem().toString();
        String searchId = txtSearchRequestID.getText().trim();

        System.out.println("Filter criteria - Status: " + selectedStatus + ", Search text: " + searchId);

        // 如果搜索框包含默认提示文本，则视为空搜索
        if ("Saerch Request ID...".equals(searchId)) {
            searchId = "";
        }

        int addedRows = 0;
        for (WorkRequest request : requests) {
            MerchantWorkRequest merchantRequest = (MerchantWorkRequest) request;

            // 创建请求ID：REQ-产品ID
            String requestId = "REQ-" + merchantRequest.getProductId();

            // 根据状态筛选
            boolean statusMatch = "All".equals(selectedStatus) || selectedStatus.isEmpty()
                    || merchantRequest.getStatus() == null || merchantRequest.getStatus().equals(selectedStatus);

            // 根据ID或产品名称筛选
            boolean searchMatch = searchId.isEmpty()
                    || requestId.toLowerCase().contains(searchId.toLowerCase())
                    || (merchantRequest.getProductName() != null
                    && merchantRequest.getProductName().toLowerCase().contains(searchId.toLowerCase()));

            System.out.println("Checking request " + requestId + " - Status match: " + statusMatch + ", Search match: " + searchMatch);

            if (statusMatch && searchMatch) {
                // 防止空值引起的错误
                String displayProductName = merchantRequest.getProductName() != null
                        ? merchantRequest.getProductName() : "未命名产品";
                String displayStatus = merchantRequest.getStatus() != null
                        ? merchantRequest.getStatus() : "Pending";

                // 创建表格行
                Object[] row = new Object[5];
                // 表格列: Request ID, Product Name, Quantity, Update Date, Status
                row[0] = requestId;                        // 请求ID
                row[1] = displayProductName;               // 产品名称
                row[2] = merchantRequest.getRequestedAmount(); // 请求数量
                row[3] = merchantRequest.getRequestDate(); // 更新日期
                row[4] = displayStatus;                    // 状态

                model.addRow(row);
                addedRows++;

                System.out.println("Added to table: " + displayProductName
                        + ", Status: " + displayStatus
                        + ", Requested quantity: " + merchantRequest.getRequestedAmount());
            }
        }

        System.out.println("Table loading complete, added " + addedRows + " rows of data");
    }

    private void refreshRequestTable() {
        loadMerchantRequests();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitle = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        StatusjComboBox = new javax.swing.JComboBox<>();
        txtSearchRequestID = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        RequestTable1 = new javax.swing.JTable();
        btnProcessOrder = new javax.swing.JButton();
        btnreject = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblCart = new javax.swing.JTable();
        lblTitle1 = new javax.swing.JLabel();
        btnRemoveOrderItem = new javax.swing.JButton();
        btnModifyQuantity = new javax.swing.JButton();
        txtNewQuantity = new javax.swing.JTextField();
        btnCheckOut = new javax.swing.JButton();

        lblTitle.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setText("Merchant Requests Management");

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel1.setText("Status：");

        StatusjComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "Pending", "Finished", "Processing", "Rejected", " ", " " }));

        txtSearchRequestID.setText("Saerch Request ID...");
        txtSearchRequestID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchRequestIDActionPerformed(evt);
            }
        });

        btnSearch.setText("Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        btnRefresh.setText("Refresh List");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(btnRefresh)
                .addGap(107, 107, 107)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(StatusjComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(187, 187, 187)
                .addComponent(txtSearchRequestID, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(btnSearch)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(StatusjComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSearchRequestID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearch)
                    .addComponent(btnRefresh))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        btnBack.setText("<< Back");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        RequestTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Merchant Request ID", "Product Name", "Quantity", "Update Date", "Status"
            }
        ));
        jScrollPane1.setViewportView(RequestTable1);

        btnProcessOrder.setText("Add in Cart");
        btnProcessOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProcessOrderActionPerformed(evt);
            }
        });

        btnreject.setText("Reject");
        btnreject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnrejectActionPerformed(evt);
            }
        });

        tblCart.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Product Name", "Purchase Cost", "Quantity", "Total Amount"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblCart);

        lblTitle1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblTitle1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle1.setText("Items In Cart");

        btnRemoveOrderItem.setText("Remove");
        btnRemoveOrderItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveOrderItemActionPerformed(evt);
            }
        });

        btnModifyQuantity.setText("Modify Quantity");
        btnModifyQuantity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModifyQuantityActionPerformed(evt);
            }
        });

        btnCheckOut.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        btnCheckOut.setText("Check out");
        btnCheckOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCheckOutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 1476, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane2)
                    .addComponent(lblTitle1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1464, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnProcessOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(52, 52, 52)
                                .addComponent(btnreject, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnModifyQuantity)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtNewQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnRemoveOrderItem, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCheckOut, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(21, 21, 21)
                .addComponent(btnBack)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnProcessOrder)
                    .addComponent(btnreject))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTitle1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnModifyQuantity)
                    .addComponent(txtNewQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnRemoveOrderItem)
                .addGap(13, 13, 13)
                .addComponent(btnCheckOut, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed

        userProcessContainer.remove(this);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.previous(userProcessContainer);
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        loadMerchantRequests();
    }//GEN-LAST:event_btnSearchActionPerformed

    private void txtSearchRequestIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchRequestIDActionPerformed
        loadMerchantRequests();
    }//GEN-LAST:event_txtSearchRequestIDActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        loadMerchantRequests();
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnrejectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnrejectActionPerformed
        int selectedRow = RequestTable1.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this,
                    "Please select a request to reject",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String requestId = RequestTable1.getValueAt(selectedRow, 0).toString();

        // 检查请求状态
        String status = RequestTable1.getValueAt(selectedRow, 4).toString();
        if ("Completed".equals(status) || "Rejected".equals(status)) {
            JOptionPane.showMessageDialog(this,
                    "This request has already been " + status.toLowerCase(),
                    "Info",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // 更新请求状态为已拒绝
        updateRequestStatus(requestId, "Rejected");

        JOptionPane.showMessageDialog(this,
                "Request has been rejected",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnrejectActionPerformed

    private void btnRemoveOrderItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveOrderItemActionPerformed
        int selectedRow = tblCart.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select an item to remove");
            return;
        }

        orderDirectory.getOrderList().remove(selectedRow);
        updateCartTable();
    }//GEN-LAST:event_btnRemoveOrderItemActionPerformed

    private void btnModifyQuantityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModifyQuantityActionPerformed
        int selectedRow = tblCart.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select an item to modify");
            return;
        }

        try {
            int newQuantity = Integer.parseInt(txtNewQuantity.getText());
            if (newQuantity <= 0) {
                JOptionPane.showMessageDialog(this, "Quantity must be greater than 0");
                return;
            }

            Order order = orderDirectory.getOrderList().get(selectedRow);
            order.setQuantity(newQuantity);
            order.setTotalAmount(order.getPurchaseCost() * newQuantity);
            updateCartTable();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid quantity");
        }
    }//GEN-LAST:event_btnModifyQuantityActionPerformed

    private void btnCheckOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheckOutActionPerformed
        if (orderDirectory.getOrderList().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Cart is empty!");
            return;
        }

        try {
            // 处理结账逻辑
            for (Order order : orderDirectory.getOrderList()) {
                // 基本订单处理
                order.setStatus("Completed");
                order.setProcessDate(new java.util.Date());

                // 从请求ID中提取产品ID
                String requestId = order.getRequestId();
                String productId = "";
                if (requestId.startsWith("REQ-")) {
                    productId = requestId.substring(4); // 移除"REQ-"前缀
                } else {
                    productId = "PROD-" + System.currentTimeMillis(); // 创建一个临时ID
                }

                // 创建仓库采购工作请求
                ProcurementWorkRequest procRequest = new ProcurementWorkRequest();
                procRequest.setProductId(productId);
                procRequest.setProductName(order.getProductName());
                procRequest.setRequestedAmount(order.getQuantity());
                procRequest.setCurrentAmount(0); // 初始数量为0
                procRequest.setStatus("Pending");
                procRequest.setMessage("PROC-" + requestId + ": Merchant order procurement");

                // 设置发送者
                if (userAccount != null) {
                    procRequest.setSender(userAccount);
                    System.out.println("Set sender for procurement request: " + userAccount.getUsername());
                } else {
                    System.out.println("Warning: userAccount is null, sender not set for procurement request");
                }

                // 设置请求日期
                procRequest.setRequestDate(new Date());

                // 添加到仓库的工作队列
                // 注意：这里有两种可能的方式添加请求
                // 方式1：直接添加到仓库实例的工作队列
                Warehouse.getInstance().getWorkQueue().getWorkRequestList().add(procRequest);

                // 方式2：添加到系统全局工作队列
                Business.EcoSystem system = Business.EcoSystem.getInstance();
                if (system.getWorkQueue() != null) {
                    system.getWorkQueue().getWorkRequestList().add(procRequest);
                }

                System.out.println("Created procurement request: " + procRequest.getMessage());

                // 在这里直接更新商家请求状态为"Completed"
                updateRequestStatus(requestId, "Completed");
                System.out.println("Updated merchant request status to Completed: " + requestId);

                // 通知仓库（如果需要额外处理）
                notifyWarehouse(order);
            }

            // 清空购物车
            orderDirectory = new OrderDirectory();
            updateCartTable();

            // 刷新请求列表
            loadMerchantRequests();

            JOptionPane.showMessageDialog(this, "Orders have been processed successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error processing orders: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnCheckOutActionPerformed

    private void btnProcessOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProcessOrderActionPerformed
        // 获取选中的行
        int selectedRow = RequestTable1.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this,
                    "Please select a request",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String requestId = RequestTable1.getValueAt(selectedRow, 0).toString();
        String productName = RequestTable1.getValueAt(selectedRow, 1).toString();
        int quantity = Integer.parseInt(RequestTable1.getValueAt(selectedRow, 2).toString());

        // 检查请求状态
        String status = RequestTable1.getValueAt(selectedRow, 4).toString();
        if ("Completed".equals(status) || "Rejected".equals(status)) {
            JOptionPane.showMessageDialog(this,
                    "This request has already been " + status.toLowerCase(),
                    "Info",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // 创建订单项
        Order order = new Order();
        order.setRequestId(requestId);
        order.setProductName(productName);
        order.setQuantity(quantity);
        order.setStatus("Processing");

        // 获取产品实际价格，而不是使用固定价格
        double actualPrice = getPriceForProduct(productName);
        order.setPurchaseCost(actualPrice);
        order.setTotalAmount(actualPrice * quantity);

        // 添加到购物车
        orderDirectory.addOrder(order);
        updateCartTable();

        // 将请求状态更新为处理中
        updateRequestStatus(requestId, "Processing");

        JOptionPane.showMessageDialog(this,
                "Request added to processing cart",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnProcessOrderActionPerformed

    private void updateCartTable() {
        cartTableModel.setRowCount(0);
        for (Order order : orderDirectory.getOrderList()) {
            Object[] row = {
                order.getProductName(),
                order.getPurchaseCost(),
                order.getQuantity(),
                order.getTotalAmount()
            };
            cartTableModel.addRow(row);
        }
    }

    private void updateRequestStatus(String requestId, String newStatus) {
        // Extract product ID from requestId
        String productId = "";
        if (requestId.startsWith("REQ-")) {
            productId = requestId.substring(4); // Remove "REQ-" prefix
        } else {
            productId = requestId; // If no prefix, use as is
        }
        
        // Get work queue from EcoSystem
        Business.EcoSystem system = Business.EcoSystem.getInstance();
        List<WorkRequest> requests = new ArrayList<>();
        if (system.getWorkQueue() != null) {
            for (WorkRequest req : system.getWorkQueue().getWorkRequestList()) {
                if (req instanceof MerchantWorkRequest) {
                    requests.add(req);
                }
            }
        }

        // Match merchant request using product ID
        boolean found = false;
        for (WorkRequest request : requests) {
            MerchantWorkRequest merchantRequest = (MerchantWorkRequest) request;
            if (productId.equals(merchantRequest.getProductId())) {
                merchantRequest.setStatus(newStatus);
                System.out.println("Successfully updated merchant request status: ProductID=" + productId + 
                               ", New Status=" + newStatus);
                found = true;
                break;
            }
        }
        
        if (!found) {
            System.out.println("Warning: No matching merchant request found for ProductID=" + productId);
        }

        loadMerchantRequests(); // Refresh table
    }

    private void notifyWarehouse(Order order) {
        // 处理订单并更新库存
        String requestId = order.getRequestId();
        String productName = order.getProductName();
        int actualAmount = order.getQuantity();

        // 更新请求状态为已完成
        updateRequestStatus(requestId, "Completed");

        // 检查仓库中是否有对应产品
        boolean found = false;
        
        System.out.println("Processing order: " + requestId + ", Product: " + productName + ", Quantity: " + actualAmount);
        System.out.println("Available products in warehouse: " + warehouse.getAvailableProducts().size());
        
        // First try to find product by ID (extracted from requestId)
        String productId = null;
        if (requestId.startsWith("REQ-")) {
            productId = requestId.substring(4); // Remove "REQ-" prefix
            System.out.println("Extracted product ID from request ID: " + productId);
        }
        
        // First try to find product by ID
        if (productId != null && !productId.isEmpty()) {
            Product productById = warehouse.findProductById(productId);
            if (productById != null) {
                System.out.println("Found product in warehouse (by ID): " + productById.getProductName() + 
                               ", Current stock: " + warehouse.getProductAmount(productId));
                
                // If warehouse has enough stock, directly get from warehouse
                if (warehouse.getProductAmount(productId) >= actualAmount) {
                    boolean updated = warehouse.decreaseStock(productId, actualAmount);
                    if (updated) {
                        System.out.println("Successfully decreased warehouse stock, remaining: " + warehouse.getProductAmount(productId));
                        found = true;
                    } else {
                        System.out.println("Failed to decrease stock, possibly insufficient inventory");
                    }
                } else {
                    System.out.println("Insufficient stock to fulfill request. Current stock: " + warehouse.getProductAmount(productId) + 
                                   ", Requested quantity: " + actualAmount);
                }
            } else {
                System.out.println("Product not found by ID: " + productId);
            }
        }
        
        // If not found by ID, try to find by name
        if (!found) {
            System.out.println("Attempting to find product by name: " + productName);
            for (Product product : warehouse.getAvailableProducts()) {
                System.out.println("Checking warehouse product: " + product.getProductName() + " (ID: " + product.getProductId() + ")");
                
                if (product.getProductName().equals(productName)) {
                    found = true;
                    String pid = product.getProductId();
                    System.out.println("Found product by name: " + productName + ", ID: " + pid + 
                                   ", Current stock: " + warehouse.getProductAmount(pid));
                    
                    // If warehouse has enough stock, directly get from warehouse
                    if (warehouse.getProductAmount(pid) >= actualAmount) {
                        boolean updated = warehouse.decreaseStock(pid, actualAmount);
                        if (updated) {
                            System.out.println("Successfully decreased warehouse stock, remaining: " + warehouse.getProductAmount(pid));
                        } else {
                            System.out.println("Failed to decrease stock, possibly insufficient inventory");
                            // Create procurement request
                            createProcurementRequest(pid, productName, actualAmount, warehouse.getProductAmount(pid), requestId);
                        }
                    } else {
                        // If stock is insufficient, create procurement request
                        System.out.println("Insufficient stock to fulfill request. Current stock: " + warehouse.getProductAmount(pid) + 
                                       ", Requested quantity: " + actualAmount);
                        createProcurementRequest(pid, productName, actualAmount, warehouse.getProductAmount(pid), requestId);
                    }
                    break;
                }
            }
        }

        if (!found) {
            System.out.println("Product not found in warehouse: " + productName + ". Need to order from supplier.");
            JOptionPane.showMessageDialog(this,
                    "Product " + productName + " not found in warehouse inventory. A procurement order will be created.",
                    "Product Not Found",
                    JOptionPane.WARNING_MESSAGE);

            // Create a new product and add procurement request
            Product newProduct = new Product(
                    "NEW-" + System.currentTimeMillis(), // Temporary ID
                    productName,
                    order.getPurchaseCost(),
                    0, // Current quantity is 0
                    actualAmount / 2 // Set a reasonable warning threshold
            );
            
            createProcurementRequest(newProduct.getProductId(), productName, actualAmount, 0, requestId);
        }
    }
    
    // Add an auxiliary method to create procurement request
    private void createProcurementRequest(String productId, String productName, int requestedAmount, int currentAmount, String originalRequestId) {
        // Add procurement request
        Business.WorkQueue.ProcurementWorkRequest procRequest = new Business.WorkQueue.ProcurementWorkRequest();
        procRequest.setProductId(productId);
        procRequest.setProductName(productName);
        procRequest.setRequestedAmount(requestedAmount);
        procRequest.setCurrentAmount(currentAmount);
        procRequest.setMessage("PROC-" + originalRequestId + ": Procurement needed for merchant request");
        procRequest.setStatus("Pending");
        
        // Add procurement request to warehouse work queue
        warehouse.getWorkQueue().getWorkRequestList().add(procRequest);
        
        System.out.println("Created procurement request: " + procRequest.getMessage() + 
                       ", Requested quantity: " + requestedAmount + 
                       ", Current stock: " + currentAmount);
    }

    private void StatusjComboBoxActionPerformed(java.awt.event.ActionEvent evt) {
        loadMerchantRequests();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable RequestTable1;
    private javax.swing.JComboBox<String> StatusjComboBox;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnCheckOut;
    private javax.swing.JButton btnModifyQuantity;
    private javax.swing.JButton btnProcessOrder;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnRemoveOrderItem;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnreject;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblTitle1;
    private javax.swing.JTable tblCart;
    private javax.swing.JTextField txtNewQuantity;
    private javax.swing.JTextField txtSearchRequestID;
    // End of variables declaration//GEN-END:variables

    // Add get product price method
    private double getPriceForProduct(String productName) {
        // Get product price from warehouse
        for (Product product : warehouse.getAvailableProducts()) {
            if (product.getProductName().equals(productName)) {
                return product.getPrice(); // Use correct getPrice() method
            }
        }

        // If product not found in warehouse, try to get price from merchant work requests
        Business.EcoSystem system = Business.EcoSystem.getInstance();
        if (system.getWorkQueue() != null) {
            for (WorkRequest req : system.getWorkQueue().getWorkRequestList()) {
                if (req instanceof MerchantWorkRequest) {
                    MerchantWorkRequest merchantReq = (MerchantWorkRequest) req;
                    if (productName.equals(merchantReq.getProductName())) {
                        if (merchantReq.getPrice() > 0) {
                            return merchantReq.getPrice();
                        }
                    }
                }
            }
        }

        // If unable to get actual price, log warning and return default price
        System.out.println("Warning: Unable to get price for product '" + productName + "', using default price 100.0");
        return 100.0; // Default price, only as a fallback
    }
}
