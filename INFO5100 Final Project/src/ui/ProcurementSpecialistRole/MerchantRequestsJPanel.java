/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui.ProcurementSpecialistRole;

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

/**
 *
 * @author wangsiting
 */
public class MerchantRequestsJPanel extends javax.swing.JPanel {

    private OrderDirectory orderDirectory;
    private DefaultTableModel cartTableModel;
    private JPanel userProcessContainer;
    private Warehouse warehouse;

    /**
     * Creates new form WarehouseRequestsJPanel
     */
    public MerchantRequestsJPanel(JPanel userProcessContainer) {
        initComponents();
        this.userProcessContainer = userProcessContainer;
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
        System.out.println("开始加载商家请求表格...");
        
        // 从EcoSystem获取商家请求
        List<WorkRequest> requests = new ArrayList<>();
        Business.EcoSystem system = Business.EcoSystem.getInstance();
        if (system.getWorkQueue() != null) {
            System.out.println("系统工作队列存在，请求总数: " + system.getWorkQueue().getWorkRequestList().size());
            for (WorkRequest req : system.getWorkQueue().getWorkRequestList()) {
                if (req instanceof MerchantWorkRequest) {
                    MerchantWorkRequest merchantReq = (MerchantWorkRequest) req;
                    // 确保至少有产品ID才添加请求
                    if (merchantReq.getProductId() != null && !merchantReq.getProductId().trim().isEmpty()) {
                        requests.add(req);
                        System.out.println("找到有效商家请求: 产品=" + merchantReq.getProductName() + 
                                         ", ID=" + merchantReq.getProductId() + 
                                         ", 状态=" + merchantReq.getStatus());
                    } else {
                        System.out.println("跳过无效请求: 产品ID为空");
                    }
                }
            }
        } else {
            System.err.println("错误: 系统工作队列为空");
        }
        
        if (requests.isEmpty()) {
            System.out.println("未找到有效商家请求，显示表格为空!");
            return;
        }
        
        System.out.println("共找到 " + requests.size() + " 个有效商家请求");
        
        // 过滤请求（根据下拉框状态）
        String selectedStatus = StatusjComboBox.getSelectedItem().toString();
        String searchId = txtSearchRequestID.getText().trim();
        
        System.out.println("筛选条件 - 状态: " + selectedStatus + ", 搜索文本: " + searchId);
        
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
            boolean statusMatch = "All".equals(selectedStatus) || selectedStatus.isEmpty() || 
                           merchantRequest.getStatus() == null || merchantRequest.getStatus().equals(selectedStatus);
            
            // 根据ID或产品名称筛选
            boolean searchMatch = searchId.isEmpty() || 
                           requestId.toLowerCase().contains(searchId.toLowerCase()) || 
                           (merchantRequest.getProductName() != null && 
                            merchantRequest.getProductName().toLowerCase().contains(searchId.toLowerCase()));
            
            System.out.println("检查请求 " + requestId + " - 状态匹配: " + statusMatch + ", 搜索匹配: " + searchMatch);
            
            if (statusMatch && searchMatch) {
                // 防止空值引起的错误
                String displayProductName = merchantRequest.getProductName() != null ? 
                                          merchantRequest.getProductName() : "未命名产品";
                String displayStatus = merchantRequest.getStatus() != null ? 
                                     merchantRequest.getStatus() : "Pending";
                
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
                
                System.out.println("添加到表格: " + displayProductName + 
                                 ", 状态: " + displayStatus +
                                 ", 请求数量: " + merchantRequest.getRequestedAmount());
            }
        }
        
        System.out.println("表格加载完成，共添加了 " + addedRows + " 行数据");
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

        // 处理结账逻辑
        for (Order order : orderDirectory.getOrderList()) {
            order.setStatus("Completed");
            // 添加订单处理时间
            order.setProcessDate(new java.util.Date());
            // 通知仓库
            notifyWarehouse(order);
        }

        // 清空购物车
        orderDirectory = new OrderDirectory();
        updateCartTable();
        
        // 刷新请求列表
        loadMerchantRequests();
        
        JOptionPane.showMessageDialog(this, "Orders have been processed successfully!");
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
        // 从EcoSystem获取工作队列
        Business.EcoSystem system = Business.EcoSystem.getInstance();
        List<WorkRequest> requests = new ArrayList<>();
        if (system.getWorkQueue() != null) {
            for (WorkRequest req : system.getWorkQueue().getWorkRequestList()) {
                if (req instanceof MerchantWorkRequest) {
                    requests.add(req);
                }
            }
        }
        
        for (WorkRequest request : requests) {
            MerchantWorkRequest merchantRequest = (MerchantWorkRequest) request;
            if (merchantRequest.getMessage().contains(requestId)) {
                merchantRequest.setStatus(newStatus);
                break;
            }
        }
        
        loadMerchantRequests(); // 刷新表格
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
        for (Product product : warehouse.getAvailableProducts()) {
            if (product.getProductName().equals(productName)) {
                found = true;
                // 如果仓库有足够库存，直接从仓库获取
                if (warehouse.getProductAmount(product.getProductId()) >= actualAmount) {
                    warehouse.decreaseStock(product.getProductId(), actualAmount);
                    System.out.println("Fulfilled merchant request: " + requestId + 
                                   " for " + productName + " with " + actualAmount + " units from warehouse");
                } else {
                    // 如果库存不足，创建采购请求
                    System.out.println("Insufficient stock for merchant request: " + requestId + 
                                   ". Creating procurement request for " + productName);
                    // 增加一个新的采购请求
                    Business.WorkQueue.ProcurementWorkRequest procRequest = new Business.WorkQueue.ProcurementWorkRequest();
                    procRequest.setProductId(product.getProductId());
                    procRequest.setProductName(productName);
                    procRequest.setRequestedAmount(actualAmount);
                    procRequest.setCurrentAmount(warehouse.getProductAmount(product.getProductId()));
                    procRequest.setMessage("PROC-" + requestId + ": Procurement needed for merchant request");
                    procRequest.setStatus("Pending");
                    
                    // 添加采购请求到仓库的工作队列
                    warehouse.getWorkQueue().getWorkRequestList().add(procRequest);
                }
                break;
            }
        }
        
        if (!found) {
            System.out.println("Product not found in warehouse: " + productName + ". Need to order from supplier.");
            JOptionPane.showMessageDialog(this, 
                "Product " + productName + " not found in warehouse inventory. A procurement order will be created.", 
                "Product Not Found", 
                JOptionPane.WARNING_MESSAGE);
            
            // 创建一个新的产品并添加采购请求
            Product newProduct = new Product(
                "NEW-" + System.currentTimeMillis(),  // 临时ID
                productName,
                order.getPurchaseCost(),
                0,  // 当前数量为0
                actualAmount / 2  // 设置一个合理的警告阈值
            );
            
            // 添加采购请求
            Business.WorkQueue.ProcurementWorkRequest procRequest = new Business.WorkQueue.ProcurementWorkRequest();
            procRequest.setProductId(newProduct.getProductId());
            procRequest.setProductName(productName);
            procRequest.setRequestedAmount(actualAmount);
            procRequest.setCurrentAmount(0);
            procRequest.setMessage("PROC-NEW-" + requestId + ": New product procurement needed for merchant request");
            procRequest.setStatus("Pending");
            
            // 添加采购请求到仓库的工作队列
            warehouse.getWorkQueue().getWorkRequestList().add(procRequest);
        }
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

    // 添加获取产品价格的方法
    private double getPriceForProduct(String productName) {
        // 从仓库获取产品价格
        for (Product product : warehouse.getAvailableProducts()) {
            if (product.getProductName().equals(productName)) {
                return product.getPrice(); // 使用正确的getPrice()方法
            }
        }
        
        // 如果在仓库中找不到产品，尝试从商家工作请求中获取价格
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
        
        // 如果无法获取实际价格，记录警告并返回默认价格
        System.out.println("警告: 无法获取产品 '" + productName + "' 的价格，使用默认价格100.0");
        return 100.0; // 默认价格，仅作为备选
    }
}
