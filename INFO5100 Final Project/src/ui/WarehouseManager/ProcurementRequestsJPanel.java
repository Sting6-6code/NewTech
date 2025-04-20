/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui.WarehouseManager;

import ui.ProcurementSpecialistRole.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.CardLayout;
import Business.Order.Order;
import Business.Order.OrderDirectory;
import Business.Warehouse.Warehouse;
import Business.WorkQueue.ProcurementWorkRequest;
import Business.WorkQueue.WorkRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author wangsiting
 */
public class ProcurementRequestsJPanel extends javax.swing.JPanel {

    private OrderDirectory orderDirectory;
    private DefaultTableModel cartTableModel;
    private JPanel userProcessContainer;
    private Warehouse warehouse;

    /**
     * Creates new form WarehouseRequestsJPanel
     */
    public ProcurementRequestsJPanel(JPanel userProcessContainer) {
        initComponents();
        this.userProcessContainer = userProcessContainer;
        orderDirectory = new OrderDirectory();
        warehouse = Warehouse.getInstance();
        setupCartTable();

        // Load all warehouse procurement requests
        loadWarehouseRequests();
    }

    private void setupCartTable() {
        String[] columnNames = {"Product Name", "Purchase Cost", "Quantity", "Total Amount"};
        cartTableModel = (DefaultTableModel) RequestTable1.getModel();
        cartTableModel.setColumnIdentifiers(columnNames);
    }

    private void loadWarehouseRequests() {
        // Clear the table
        DefaultTableModel model = (DefaultTableModel) RequestTable1.getModel();
        model.setRowCount(0);

        // Get all procurement requests
        List<ProcurementWorkRequest> allRequests = new ArrayList<>();
        
        // Create a set to track unique request IDs
        Set<String> processedRequestIds = new java.util.HashSet<>();

        // 1. Get requests from warehouse
        List<ProcurementWorkRequest> warehouseRequests = warehouse.getProcurementRequests();
        if (warehouseRequests != null) {
            for (ProcurementWorkRequest req : warehouseRequests) {
                // Add only if this request ID hasn't been seen before
                String requestId = req.getProductId();
                if (requestId != null && !processedRequestIds.contains(requestId)) {
                    processedRequestIds.add(requestId);
                    allRequests.add(req);
                }
            }
        }

        // 2. Get requests from system work queue
        Business.EcoSystem system = Business.EcoSystem.getInstance();
        if (system.getWorkQueue() != null) {
            for (WorkRequest req : system.getWorkQueue().getWorkRequestList()) {
                if (req instanceof ProcurementWorkRequest) {
                    ProcurementWorkRequest procReq = (ProcurementWorkRequest) req;
                    // Add only if this request ID hasn't been seen before
                    String requestId = procReq.getProductId();
                    if (requestId != null && !processedRequestIds.contains(requestId)) {
                        processedRequestIds.add(requestId);
                        allRequests.add(procReq);
                    }
                }
            }
        }

        if (allRequests.isEmpty()) {
            System.out.println("No procurement requests found!");
            return;
        }

        System.out.println("Found " + allRequests.size() + " unique procurement requests");

        // Filter requests (based on dropdown status)
        String selectedStatus = StatusjComboBox.getSelectedItem().toString();
        String searchId = txtSearchRequestID.getText().trim();

        // If search box contains default prompt text, treat as empty search
        if ("Saerch Request ID...".equals(searchId)) {
            searchId = "";
        }

        for (ProcurementWorkRequest request : allRequests) {
            // Filter by status
            boolean statusMatch = "All".equals(selectedStatus)
                    || selectedStatus.isEmpty()
                    || request.getStatus() == null
                    || request.getStatus().equals(selectedStatus);

            // Filter by ID or product info
            boolean searchMatch = searchId.isEmpty()
                    || (request.getMessage() != null && request.getMessage().contains(searchId))
                    || (request.getProductId() != null && request.getProductId().contains(searchId))
                    || (request.getProductName() != null && request.getProductName().contains(searchId));

            if (statusMatch && searchMatch) {
                // Create table row
                Object[] row = new Object[5];
                // Table columns: Request ID, Product Name, Quantity, Update Date, Status
                row[0] = "REQ-" + request.getProductId(); // Request ID
                row[1] = request.getProductName();        // Product Name
                row[2] = request.getRequestedAmount();    // Requested Quantity
                row[3] = request.getRequestDate();        // Update Date
                row[4] = request.getStatus();             // Status

                model.addRow(row);

                System.out.println("Added request to table: " + request.getProductName()
                        + ", Status: " + request.getStatus()
                        + ", Requested: " + request.getRequestedAmount());
            }
        }
    }

    private void refreshRequestTable() {
        loadWarehouseRequests();
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
        RecReqJScrollPane = new javax.swing.JScrollPane();
        RequestTable1 = new javax.swing.JTable();
        btnProcessOrder = new javax.swing.JButton();
        btnreject = new javax.swing.JButton();

        lblTitle.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setText("Procurement Specialist Requests Management");

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
                "Procurement Request ID", "Product Name", "Quantity", "Update Date", "Status"
            }
        ));
        RecReqJScrollPane.setViewportView(RequestTable1);

        btnProcessOrder.setText("Process Order");
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 1476, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(RecReqJScrollPane)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnProcessOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(52, 52, 52)
                                .addComponent(btnreject, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                .addComponent(btnBack)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(RecReqJScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnProcessOrder)
                    .addComponent(btnreject))
                .addGap(20, 20, 20))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed

        userProcessContainer.remove(this);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.previous(userProcessContainer);
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        loadWarehouseRequests();
    }//GEN-LAST:event_btnSearchActionPerformed

    private void txtSearchRequestIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchRequestIDActionPerformed
        loadWarehouseRequests();
    }//GEN-LAST:event_txtSearchRequestIDActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        loadWarehouseRequests();
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

    private void btnProcessOrderActionPerformed(java.awt.event.ActionEvent evt) {
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

        // 设置价格（假设一个估计价格）
        double estimatedPrice = 100.0; // 示例价格
        order.setPurchaseCost(estimatedPrice);
        order.setTotalAmount(estimatedPrice * quantity);

        // 添加到购物车
        orderDirectory.addOrder(order);
        updateCartTable();

        // 将请求状态更新为处理中
        updateRequestStatus(requestId, "Processing");

        JOptionPane.showMessageDialog(this,
                "Request added to processing cart",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
    }

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
        List<ProcurementWorkRequest> requests = warehouse.getProcurementRequests();
        for (ProcurementWorkRequest request : requests) {
            if (request.getMessage().contains(requestId)) {
                request.setStatus(newStatus);
                break;
            }
        }
        loadWarehouseRequests(); // Refresh the table
    }

    private void notifyWarehouse(Order order) {
        // 处理订单并更新库存
        String requestId = order.getRequestId();
        int actualAmount = order.getQuantity();

        // 调用仓库处理请求的方法
        warehouse.processProcurementRequest(requestId, actualAmount);

        System.out.println("Processed procurement request: " + requestId
                + " with actual amount: " + actualAmount);
    }

    private void StatusjComboBoxActionPerformed(java.awt.event.ActionEvent evt) {
        loadWarehouseRequests();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane RecReqJScrollPane;
    private javax.swing.JTable RequestTable1;
    private javax.swing.JComboBox<String> StatusjComboBox;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnProcessOrder;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnreject;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JTextField txtSearchRequestID;
    // End of variables declaration//GEN-END:variables
}
