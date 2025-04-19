/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui.MerchantRole;

import ui.ProcurementSpecialistRole.*;
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

/**
 *
 * @author wangsiting
 */
public class ViewMerchantRequestsJPanel extends javax.swing.JPanel {

    private OrderDirectory orderDirectory;
    private DefaultTableModel cartTableModel;
    private JPanel userProcessContainer;
    private Warehouse warehouse;
    private javax.swing.JTable tblCart;

    /**
     * Creates new form WarehouseRequestsJPanel
     */
    public ViewMerchantRequestsJPanel(JPanel userProcessContainer) {
        initComponents();
        this.userProcessContainer = userProcessContainer;
        orderDirectory = new OrderDirectory();
        //warehouse = Warehouse.getInstance();
        setupListeners();
        
        // 加载所有merchant采购请求
        loadMerchantRequests();
    }

    private void setupCartTable() {
        String[] columnNames = {"Product Name", "Purchase Cost", "Quantity", "Total Amount"};
        if (tblCart == null) {
            tblCart = new javax.swing.JTable();
            cartTableModel = new DefaultTableModel(columnNames, 0);
            tblCart.setModel(cartTableModel);
        } else {
            cartTableModel = (DefaultTableModel) tblCart.getModel();
            cartTableModel.setColumnIdentifiers(columnNames);
        }
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

        lblTitle.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setText("View Merchant Requests");

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnBack)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(179, Short.MAX_VALUE))
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
        String requestId = order.getRequestId();
        int actualAmount = order.getQuantity();
        
        warehouse.processProcurementRequest(requestId, actualAmount);
        
        System.out.println("Processed procurement request: " + requestId + 
                           " with actual amount: " + actualAmount);
    }

    private void StatusjComboBoxActionPerformed(java.awt.event.ActionEvent evt) {
        System.out.println("状态过滤变更为: " + StatusjComboBox.getSelectedItem().toString());
        loadMerchantRequests();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable RequestTable1;
    private javax.swing.JComboBox<String> StatusjComboBox;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSearch;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JTextField txtSearchRequestID;
    // End of variables declaration//GEN-END:variables

    
    // 添加构造函数后的初始化代码，确保组件监听器正确注册
    private void setupListeners() {
        System.out.println("设置监听器...");
        
        // 为状态下拉框添加动作监听器
        // 先移除所有现有监听器，避免重复注册
        for (java.awt.event.ActionListener al : StatusjComboBox.getActionListeners()) {
            StatusjComboBox.removeActionListener(al);
        }
        
        StatusjComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                System.out.println("状态下拉框选择变更: " + StatusjComboBox.getSelectedItem());
                StatusjComboBoxActionPerformed(evt);
            }
        });
        
        System.out.println("状态下拉框监听器已添加");
        
        // 为请求表添加鼠标点击监听器，以便显示详细信息
        RequestTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) { // 双击
                    int selectedRow = RequestTable1.getSelectedRow();
                    if (selectedRow >= 0) {
                        System.out.println("表格行双击: " + selectedRow);
                        displayRequestDetails(selectedRow);
                    }
                }
            }
        });
        
        System.out.println("表格鼠标监听器已添加");
        
        // 为搜索按钮添加额外的调试输出
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                System.out.println("搜索按钮点击, 搜索内容: " + txtSearchRequestID.getText());
                loadMerchantRequests();
            }
        });
        
        // 为刷新按钮添加额外的调试输出
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                System.out.println("刷新按钮点击");
                loadMerchantRequests();
            }
        });
    }
    
    // 显示请求详细信息
    private void displayRequestDetails(int selectedRow) {
        try {
            String requestId = RequestTable1.getValueAt(selectedRow, 0).toString();
            String productName = RequestTable1.getValueAt(selectedRow, 1).toString();
            int quantity = Integer.parseInt(RequestTable1.getValueAt(selectedRow, 2).toString());
            String status = RequestTable1.getValueAt(selectedRow, 4).toString();
            
            StringBuilder details = new StringBuilder();
            details.append("请求详情:\n\n");
            details.append("请求ID: ").append(requestId).append("\n");
            details.append("产品名称: ").append(productName).append("\n");
            details.append("请求数量: ").append(quantity).append("\n");
            details.append("状态: ").append(status).append("\n");
            
            // 查找请求对象获取更多信息
            MerchantWorkRequest foundRequest = findRequestById(requestId);
            if (foundRequest != null) {
                details.append("价格: $").append(foundRequest.getPrice()).append("\n");
                details.append("总金额: $").append(foundRequest.getPrice() * foundRequest.getRequestedAmount()).append("\n");
                
                if (foundRequest.getSender() != null) {
                    details.append("请求者: ").append(foundRequest.getSender().getUsername()).append("\n");
                }
                
                if (foundRequest.getReceiver() != null) {
                    details.append("处理者: ").append(foundRequest.getReceiver().getUsername()).append("\n");
                }
                
                if (foundRequest.getResolveDate() != null) {
                    details.append("完成日期: ").append(foundRequest.getResolveDate()).append("\n");
                }
            }
            
            JOptionPane.showMessageDialog(this, 
                    details.toString(), 
                    "请求详情", 
                    JOptionPane.INFORMATION_MESSAGE);
            
        } catch (Exception e) {
            System.err.println("显示请求详情时出错: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // 根据ID查找请求
    private MerchantWorkRequest findRequestById(String requestId) {
        // 移除前缀
        String productId = requestId.replace("REQ-", "");
        
        Business.EcoSystem system = Business.EcoSystem.getInstance();
        if (system.getWorkQueue() != null) {
            for (WorkRequest req : system.getWorkQueue().getWorkRequestList()) {
                if (req instanceof MerchantWorkRequest) {
                    MerchantWorkRequest merchantRequest = (MerchantWorkRequest) req;
                    if (merchantRequest.getProductId().equals(productId)) {
                        return merchantRequest;
                    }
                }
            }
        }
        return null;
    }
}
