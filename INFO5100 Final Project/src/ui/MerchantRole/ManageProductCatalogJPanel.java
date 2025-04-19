package ui.MerchantRole;

import Business.Product.Product;
import Business.Supplier.Supplier;
import Business.Warehouse.Warehouse;
import org.netbeans.lib.awtextra.AbsoluteLayout;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import java.awt.CardLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import Business.WorkQueue.MerchantWorkRequest;

/**
 * 商家产品目录管理面板
 * @author Rushabh, wangsiting
 */
public class ManageProductCatalogJPanel extends javax.swing.JPanel {

    private JPanel userProcessContainer;
    private Supplier supplier;
    private Warehouse warehouse;

    /**
     * 创建商家产品目录管理面板
     * @param upc 用户处理容器
     * @param s 供应商对象
     */
    public ManageProductCatalogJPanel(JPanel upc, Supplier s) {
        initComponents();
        userProcessContainer = upc;
        supplier = s;
        warehouse = Warehouse.getInstance();

        System.out.println("初始化ManageProductCatalogJPanel...");
        System.out.println("Supplier: " + (supplier != null ? supplier.getSupplyName() : "null"));
        
        // 如果supplier为空，尝试从MerchantRole获取
        if (supplier == null) {
            System.out.println("警告: 提供的supplier为空，尝试从MerchantRole获取默认supplier");
            supplier = Business.Role.MerchantRole.getDemoSupplier();
            System.out.println("从MerchantRole获取supplier: " + (supplier != null ? supplier.getSupplyName() : "仍然为空"));
        }
        
        // 检查supplier的产品目录
        if (supplier != null && supplier.getProductCatalog() != null) {
            System.out.println("产品目录中有 " + supplier.getProductCatalog().size() + " 个产品");
            
            // 如果目录为空，添加示例产品
            if (supplier.getProductCatalog().isEmpty()) {
                System.out.println("产品目录为空，添加示例产品");
                createAndAddSampleProducts();
            }
        } else {
            System.out.println("警告: 产品目录为空或不可用");
            
            // 如果supplier不为空但目录为空，初始化目录
            if (supplier != null && supplier.getProductCatalog() == null) {
                supplier.setProductCatalog(new ArrayList<>());
                createAndAddSampleProducts();
            }
        }
        
        // 设置标题
        if (supplier != null) {
            lblTitle1.setText("Product Management System for " + supplier.getSupplyName());
        } else {
            lblTitle1.setText("Product Management System");
        }
        
        // 刷新表格
        refreshTable();
    }

    /**
     * 刷新产品表格
     */
    public void refreshTable() {
        DefaultTableModel model = (DefaultTableModel) tblProductCatalog.getModel();
        model.setRowCount(0);
        
        System.out.println("开始刷新产品表格...");
        
        if (supplier == null) {
            System.out.println("错误: supplier为空");
            return;
        }
        
        if (supplier.getProductCatalog() == null) {
            System.out.println("错误: 产品目录为空");
            return;
        }
        
        System.out.println("产品目录中产品数量: " + supplier.getProductCatalog().size());

        for (Product p : supplier.getProductCatalog()) {
            Object row[] = new Object[6];
            row[0] = p.getProductName();
            row[1] = p.getProductId();
            row[2] = p.getPrice();
            row[3] = p.getQuantity();
            row[4] = p.getStockStatus();
            row[5] = p.getLastUpdated();
            model.addRow(row);
            System.out.println("添加产品到表格: " + p.getProductName() + " (ID: " + p.getProductId() + ")");
        }
        
        System.out.println("表格刷新完成，行数: " + model.getRowCount());
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitle = new javax.swing.JLabel();
        btnView = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        lblProductId1 = new javax.swing.JLabel();
        txtId1 = new javax.swing.JTextField();
        btnSearch1 = new javax.swing.JButton();
        ViewProductDetails = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        lblTitle4 = new javax.swing.JLabel();
        lblProductName3 = new javax.swing.JLabel();
        txtProductNameView3 = new javax.swing.JTextField();
        lblProductId4 = new javax.swing.JLabel();
        txtIdView3 = new javax.swing.JTextField();
        lblProductPrice3 = new javax.swing.JLabel();
        txtPriceView3 = new javax.swing.JTextField();
        btnUpdate3 = new javax.swing.JButton();
        btnSave3 = new javax.swing.JButton();
        lblTitle1 = new javax.swing.JLabel();
        RequestProducts = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        lblProductName2 = new javax.swing.JLabel();
        txtProductNameView2 = new javax.swing.JTextField();
        lblProductPrice2 = new javax.swing.JLabel();
        txtPriceView2 = new javax.swing.JTextField();
        btnUpdate2 = new javax.swing.JButton();
        txtIdView2 = new javax.swing.JTextField();
        lblProductId3 = new javax.swing.JLabel();
        btnSave2 = new javax.swing.JButton();
        lblPID2 = new javax.swing.JLabel();
        txtProductId = new javax.swing.JTextField();
        lblPrice2 = new javax.swing.JLabel();
        btnAdd2 = new javax.swing.JButton();
        lblPN2 = new javax.swing.JLabel();
        lblAvaila2 = new javax.swing.JLabel();
        txtRequestQuantity = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        lblTitle3 = new javax.swing.JLabel();
        txtProductNameView4 = new javax.swing.JTextField();
        txtPriceView4 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProductCatalog = new javax.swing.JTable();
        btnRequestProducts = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(1450, 800));

        lblTitle.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblTitle.setText("Product Catalog:");

        btnView.setText("View Details");
        btnView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewActionPerformed(evt);
            }
        });

        btnBack.setText("<< Back");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        btnDelete.setText("ItemDownshelf");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnRefresh.setText("Refresh List");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        lblProductId1.setText("Product Id:");

        btnSearch1.setText("Search");
        btnSearch1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearch1ActionPerformed(evt);
            }
        });

        ViewProductDetails.setLayout(new java.awt.CardLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        lblTitle4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblTitle4.setText("View Product Details");

        lblProductName3.setText("Product Name:");

        txtProductNameView3.setEditable(false);
        txtProductNameView3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProductNameView3ActionPerformed(evt);
            }
        });

        lblProductId4.setText("Product ID:");

        txtIdView3.setEditable(false);

        lblProductPrice3.setText("Product Price:");

        txtPriceView3.setEditable(false);
        txtPriceView3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPriceView3ActionPerformed(evt);
            }
        });

        btnUpdate3.setText("Update Product");
        btnUpdate3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdate3ActionPerformed(evt);
            }
        });

        btnSave3.setText("Save");
        btnSave3.setEnabled(false);
        btnSave3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSave3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(107, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(100, 100, 100)
                        .addComponent(lblTitle4))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblProductName3)
                        .addGap(13, 13, 13)
                        .addComponent(txtProductNameView3, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(lblProductId4)
                        .addGap(15, 15, 15)
                        .addComponent(txtIdView3, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblProductPrice3)
                        .addGap(18, 18, 18)
                        .addComponent(txtPriceView3, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(100, 100, 100)
                        .addComponent(btnUpdate3, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(100, 100, 100)
                        .addComponent(btnSave3, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(103, 103, 103))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(lblTitle4)
                .addGap(8, 8, 8)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblProductName3)
                    .addComponent(txtProductNameView3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblProductId4)
                    .addComponent(txtIdView3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblProductPrice3)
                    .addComponent(txtPriceView3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(57, 57, 57)
                .addComponent(btnUpdate3)
                .addGap(17, 17, 17)
                .addComponent(btnSave3)
                .addContainerGap(38, Short.MAX_VALUE))
        );

        ViewProductDetails.add(jPanel1, "card2");

        lblTitle1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblTitle1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle1.setText("Product Management System");

        RequestProducts.setLayout(new java.awt.CardLayout());

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblProductName2.setText("Product Name:");
        jPanel3.add(lblProductName2, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 50, -1, -1));

        txtProductNameView2.setEditable(false);
        txtProductNameView2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProductNameView2ActionPerformed(evt);
            }
        });
        jPanel3.add(txtProductNameView2, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 50, 250, -1));

        lblProductPrice2.setText("Product Price:");
        jPanel3.add(lblProductPrice2, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 130, -1, -1));

        txtPriceView2.setEditable(false);
        txtPriceView2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPriceView2ActionPerformed(evt);
            }
        });
        jPanel3.add(txtPriceView2, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 130, 250, -1));

        btnUpdate2.setText("Update Product");
        btnUpdate2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdate2ActionPerformed(evt);
            }
        });
        jPanel3.add(btnUpdate2, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 210, 134, -1));

        txtIdView2.setEditable(false);
        jPanel3.add(txtIdView2, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 90, 250, -1));

        lblProductId3.setText("Product ID:");
        jPanel3.add(lblProductId3, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 90, -1, -1));

        btnSave2.setText("Save");
        btnSave2.setEnabled(false);
        btnSave2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSave2ActionPerformed(evt);
            }
        });
        jPanel3.add(btnSave2, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 250, 134, -1));

        lblPID2.setText("Product ID:");
        jPanel3.add(lblPID2, new org.netbeans.lib.awtextra.AbsoluteConstraints(78, 91, -1, 30));

        txtProductId.setEditable(false);
        jPanel3.add(txtProductId, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 95, 220, -1));

        lblPrice2.setText("Product Price:");
        jPanel3.add(lblPrice2, new org.netbeans.lib.awtextra.AbsoluteConstraints(61, 130, -1, 30));

        btnAdd2.setText("Request");
        btnAdd2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdd2ActionPerformed(evt);
            }
        });
        jPanel3.add(btnAdd2, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 250, -1, -1));

        lblPN2.setText("Product Name:");
        jPanel3.add(lblPN2, new org.netbeans.lib.awtextra.AbsoluteConstraints(61, 52, -1, 30));

        lblAvaila2.setText("Request Quantity:");
        jPanel3.add(lblAvaila2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 170, -1, 30));
        jPanel3.add(txtRequestQuantity, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 180, 220, -1));

        jLabel3.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        jLabel3.setText("Request Products");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 20, 220, -1));

        lblTitle3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblTitle3.setText("View Product Details");
        jPanel3.add(lblTitle3, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 20, -1, -1));

        txtProductNameView4.setEditable(false);
        txtProductNameView4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProductNameView4ActionPerformed(evt);
            }
        });
        jPanel3.add(txtProductNameView4, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 60, 220, -1));

        txtPriceView4.setEditable(false);
        txtPriceView4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPriceView4ActionPerformed(evt);
            }
        });
        jPanel3.add(txtPriceView4, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 140, 220, -1));

        RequestProducts.add(jPanel3, "card2");

        tblProductCatalog.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        tblProductCatalog.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Product Name", "Product ID", "Price", "Quantity", "Stock Status", "Last Updated"
            }
        ));
        jScrollPane1.setViewportView(tblProductCatalog);

        btnRequestProducts.setText("Request Products");
        btnRequestProducts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRequestProductsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblTitle1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(37, 37, 37))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())))
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(RequestProducts, javax.swing.GroupLayout.PREFERRED_SIZE, 521, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 131, Short.MAX_VALUE)
                .addComponent(ViewProductDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 564, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(200, 200, 200))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(lblTitle)
                        .addGap(12, 12, 12)
                        .addComponent(btnRefresh)
                        .addGap(78, 78, 78)
                        .addComponent(lblProductId1)
                        .addGap(18, 18, 18)
                        .addComponent(txtId1, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(btnSearch1))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnView, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnRequestProducts)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTitle1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnBack)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitle)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(btnRefresh))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(lblProductId1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(txtId1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(btnSearch1)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnView)
                    .addComponent(btnDelete)
                    .addComponent(btnRequestProducts))
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ViewProductDetails, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(RequestProducts, javax.swing.GroupLayout.PREFERRED_SIZE, 421, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(61, 61, 61))
        );
    }// </editor-fold>//GEN-END:initComponents
    private void btnViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewActionPerformed
        int selectedRowIndex = tblProductCatalog.getSelectedRow();
        if (selectedRowIndex < 0) {
            JOptionPane.showMessageDialog(null, "Pls select a row!!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 获取选中的产品
        Product p = supplier.getProductCatalog().get(selectedRowIndex);

        // 填充右侧ViewProductDetails面板里的控件
        txtProductNameView3.setText(p.getProductName());
        txtIdView3.setText(p.getProductId());
        txtPriceView3.setText(String.valueOf(p.getPrice()));

        // 切换到ViewProductDetails卡片
        CardLayout layout = (CardLayout) ViewProductDetails.getLayout();
        layout.show(ViewProductDetails, "card2");
    }//GEN-LAST:event_btnViewActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        userProcessContainer.remove(this);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.previous(userProcessContainer);
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int selectedRowIndex = tblProductCatalog.getSelectedRow();

        if (selectedRowIndex < 0) {
            JOptionPane.showMessageDialog(null, "Please select a row from the table first", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // 获取选中的产品并下架
        Product p = supplier.getProductCatalog().get(selectedRowIndex);
        p.downShelf();
        JOptionPane.showMessageDialog(null, "Product has been removed from shelf", "Success", JOptionPane.INFORMATION_MESSAGE);
        refreshTable();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        refreshTable();
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnSearch1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearch1ActionPerformed
        try {
            String productId = txtId1.getText();
            Product p = null;
            for (Product prod : supplier.getProductCatalog()) {
                if (prod.getProductId().equals(productId)) {
                    p = prod;
                    break;
                }
            }
            if (p != null) {
                txtProductNameView3.setText(p.getProductName());
                txtIdView3.setText(p.getProductId());
                txtPriceView3.setText(String.valueOf(p.getPrice()));
                CardLayout layout = (CardLayout) ViewProductDetails.getLayout();
                layout.show(ViewProductDetails, "card2");
            } else {
                JOptionPane.showMessageDialog(null, "Nothing found", "No result found matching your criteria!!", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Nothing found", "No result found matching your criteria!!", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnSearch1ActionPerformed

    private void txtProductNameView2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProductNameView2ActionPerformed
        // 无操作
    }//GEN-LAST:event_txtProductNameView2ActionPerformed

    private void txtPriceView2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPriceView2ActionPerformed
        // 无操作
    }//GEN-LAST:event_txtPriceView2ActionPerformed

    private void btnUpdate2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdate2ActionPerformed
        // 无操作
    }//GEN-LAST:event_btnUpdate2ActionPerformed

    private void btnSave2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSave2ActionPerformed
        // 无操作
    }//GEN-LAST:event_btnSave2ActionPerformed

    private void btnAdd2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdd2ActionPerformed
        // 处理请求按钮点击事件
        try {
            System.out.println("开始处理商品请求...");
            
            // 检查请求数量是否有效
            if (txtRequestQuantity.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "请输入请求数量", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int requestedAmount;
            try {
                requestedAmount = Integer.parseInt(txtRequestQuantity.getText().trim());
                if (requestedAmount <= 0) {
                    JOptionPane.showMessageDialog(null, "请输入大于0的数量", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "请输入有效的数字", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // 从界面获取产品信息
            String productId = txtIdView2.getText();
            String productName = txtProductNameView2.getText();
            
            // 检查产品信息是否为空
            if (productId == null || productId.trim().isEmpty()) {
                System.out.println("警告: 产品ID为空，尝试从隐藏字段获取");
                productId = txtProductId.getText();
            }
            
            if (productName == null || productName.trim().isEmpty()) {
                System.out.println("警告: 产品名称为空，尝试从隐藏字段获取");
                productName = txtProductNameView4.getText();
            }
            
            // 再次检查产品信息
            if (productId == null || productId.trim().isEmpty() || productName == null || productName.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "无法识别产品信息，请重新选择产品", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            System.out.println("请求信息 - 产品ID: " + productId + ", 产品名称: " + productName + ", 数量: " + requestedAmount);
            
            // 检查价格字段是否为空
            if (txtPriceView2.getText() == null || txtPriceView2.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "价格不能为空", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            double price = Double.parseDouble(txtPriceView2.getText().trim());
            System.out.println("产品价格: " + price);
            
            // 创建工作请求
            MerchantWorkRequest request = new MerchantWorkRequest();
            request.setProductId(productId);
            request.setProductName(productName);
            request.setPrice(price);
            request.setRequestedAmount(requestedAmount);
            request.setMessage("商家请求产品: " + productName + " (ID: " + productId + ")");
            request.setStatus("Pending"); // 设置初始状态为Pending
            request.setRequestDate(new Date()); // 设置请求日期
            
            System.out.println("已创建请求: " + request.getMessage() + ", 状态: " + request.getStatus());
            
            // 设置发送者，如果有用户账户的话
            if (Business.EcoSystem.getInstance().getUserAccountDirectory() != null &&
                !Business.EcoSystem.getInstance().getUserAccountDirectory().getUserAccountList().isEmpty()) {
                request.setSender(Business.EcoSystem.getInstance().getUserAccountDirectory().getUserAccountList().get(0));
                System.out.println("设置请求发送者: " + request.getSender().getUsername());
            } else {
                System.out.println("警告: 无法设置请求发送者 - 用户账户为空");
            }
            
            // 获取系统工作队列并添加请求
            Business.EcoSystem system = Business.EcoSystem.getInstance();
            if (system.getWorkQueue() == null) {
                System.out.println("系统工作队列为空，创建新队列");
                system.setWorkQueue(new Business.WorkQueue.WorkQueue());
            }
            system.getWorkQueue().getWorkRequestList().add(request);
            System.out.println("请求已添加到系统工作队列，当前队列大小: " + system.getWorkQueue().getWorkRequestList().size());
            
            // 更新产品状态为"已请求"
            boolean productFound = false;
            for (Product p : supplier.getProductCatalog()) {
                if (p.getProductId().equals(productId)) {
                    p.setStockStatus("Requested");
                    p.setLastUpdated(new Date());
                    productFound = true;
                    System.out.println("已更新产品 " + p.getProductName() + " 的状态为: Requested");
                    break;
                }
            }
            
            if (!productFound) {
                System.out.println("警告: 无法在产品目录中找到产品 ID: " + productId);
            }
            
            // 刷新表格显示更新后的状态
            refreshTable();
            
            // 显示成功消息
            JOptionPane.showMessageDialog(null,
                "已成功提交" + requestedAmount + "个" + productName + "的采购请求",
                "请求已提交",
                JOptionPane.INFORMATION_MESSAGE);
            
            // 清空请求面板
            txtRequestQuantity.setText("");
            System.out.println("产品请求处理完成");
            
        } catch (Exception e) {
            System.err.println("处理产品请求时出错: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "错误: " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnAdd2ActionPerformed

    private void txtProductNameView3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProductNameView3ActionPerformed
        // 无操作
    }//GEN-LAST:event_txtProductNameView3ActionPerformed

    private void txtPriceView3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPriceView3ActionPerformed
        // 无操作
    }//GEN-LAST:event_txtPriceView3ActionPerformed

    private void btnUpdate3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdate3ActionPerformed
        // 允许编辑产品名称和价格
        txtProductNameView3.setEditable(true);
        txtPriceView3.setEditable(true);
        btnSave3.setEnabled(true);
    }//GEN-LAST:event_btnUpdate3ActionPerformed

    private void btnSave3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSave3ActionPerformed
        // 保存修改后的产品信息
        int selectedRowIndex = tblProductCatalog.getSelectedRow();
        if (selectedRowIndex < 0) {
            JOptionPane.showMessageDialog(null, "Please select a row from the table first", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Product p = supplier.getProductCatalog().get(selectedRowIndex);
        p.setProductName(txtProductNameView3.getText());
        try {
            p.setPrice(Double.parseDouble(txtPriceView3.getText()));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid price format!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // 更新时间戳
        p.setLastUpdated(new Date());
        
        // 保存后禁止编辑
        txtProductNameView3.setEditable(false);
        txtPriceView3.setEditable(false);
        btnSave3.setEnabled(false);
        
        refreshTable();
        JOptionPane.showMessageDialog(null, "Product updated successfully!", "Info", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnSave3ActionPerformed

    private void btnRequestProductsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRequestProductsActionPerformed
        // 获取选中的产品行
        int selectedRowIndex = tblProductCatalog.getSelectedRow();
        
        // 检查是否有选中的行，以及supplier是否有产品目录
        if (selectedRowIndex < 0) {
            JOptionPane.showMessageDialog(null, "请选择一个产品进行请求", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        // 检查商家的产品目录是否为空
        if (supplier == null || supplier.getProductCatalog() == null || supplier.getProductCatalog().isEmpty()) {
            JOptionPane.showMessageDialog(null, "产品目录为空，请先添加产品", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            System.out.println("准备请求产品，选中行索引: " + selectedRowIndex);
            
            // 获取产品信息
            Product p = supplier.getProductCatalog().get(selectedRowIndex);
            String productId = p.getProductId();
            String productName = p.getProductName();
            double price = p.getPrice();
            
            System.out.println("获取到产品信息 - ID: " + productId + ", 名称: " + productName + ", 价格: " + price);
            
            // 在请求面板中显示产品信息
            txtProductNameView2.setText(productName);
            txtIdView2.setText(productId);
            txtPriceView2.setText(String.valueOf(price));
            
            // 确保价格字段不为空
            if (txtPriceView2.getText() == null || txtPriceView2.getText().trim().isEmpty()) {
                txtPriceView2.setText("0.0"); // 设置默认价格，防止空值异常
                System.out.println("警告: 产品价格为空，已设置默认值0.0");
            }
            
            // 保存产品名称和ID到隐藏字段，以备后续使用
            txtProductNameView4.setText(productName);
            txtProductId.setText(productId);
            
            // 切换到请求卡片视图
            CardLayout layout = (CardLayout) RequestProducts.getLayout();
            layout.show(RequestProducts, "card2");
            
            // 可选：如果产品库存低，建议请求数量
            if ("Low".equals(p.getStockStatus())) {
                txtRequestQuantity.setText("20"); // 建议订购量
                JOptionPane.showMessageDialog(null, 
                    "该产品库存不足，建议补充库存。", 
                    "库存状态", 
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                txtRequestQuantity.setText(""); // 清空请求数量
            }
            
        } catch (IndexOutOfBoundsException ex) {
            System.err.println("索引错误: " + ex.getMessage());
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "无法访问选定的产品，请刷新产品列表后重试。", 
                "错误", 
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            System.err.println("错误: " + ex.getMessage());
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "处理请求时出错: " + ex.getMessage(), 
                "错误", 
                JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnRequestProductsActionPerformed

    private void txtProductNameView4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProductNameView4ActionPerformed
        // 无操作
    }//GEN-LAST:event_txtProductNameView4ActionPerformed

    private void txtPriceView4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPriceView4ActionPerformed
        // 无操作
    }//GEN-LAST:event_txtPriceView4ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel RequestProducts;
    private javax.swing.JPanel ViewProductDetails;
    private javax.swing.JButton btnAdd2;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnRequestProducts;
    private javax.swing.JButton btnSave2;
    private javax.swing.JButton btnSave3;
    private javax.swing.JButton btnSearch1;
    private javax.swing.JButton btnUpdate2;
    private javax.swing.JButton btnUpdate3;
    private javax.swing.JButton btnView;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAvaila2;
    private javax.swing.JLabel lblPID2;
    private javax.swing.JLabel lblPN2;
    private javax.swing.JLabel lblPrice2;
    private javax.swing.JLabel lblProductId1;
    private javax.swing.JLabel lblProductId3;
    private javax.swing.JLabel lblProductId4;
    private javax.swing.JLabel lblProductName2;
    private javax.swing.JLabel lblProductName3;
    private javax.swing.JLabel lblProductPrice2;
    private javax.swing.JLabel lblProductPrice3;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblTitle1;
    private javax.swing.JLabel lblTitle3;
    private javax.swing.JLabel lblTitle4;
    private javax.swing.JTable tblProductCatalog;
    private javax.swing.JTextField txtId1;
    private javax.swing.JTextField txtIdView2;
    private javax.swing.JTextField txtIdView3;
    private javax.swing.JTextField txtPriceView2;
    private javax.swing.JTextField txtPriceView3;
    private javax.swing.JTextField txtPriceView4;
    private javax.swing.JTextField txtProductId;
    private javax.swing.JTextField txtProductNameView2;
    private javax.swing.JTextField txtProductNameView3;
    private javax.swing.JTextField txtProductNameView4;
    private javax.swing.JTextField txtRequestQuantity;
    // End of variables declaration//GEN-END:variables

    /**
     * 创建和添加示例产品到商家目录
     */
    private void createAndAddSampleProducts() {
        if (supplier == null) {
            System.out.println("错误: 无法添加示例产品，supplier为空");
            return;
        }
        
        System.out.println("开始创建示例产品...");
        
        // 创建示例产品 - 使用与仓库完全相同的产品ID和名称
        Product p1 = new Product("SP-001", "Apple iPhone 15 Pro", 799.99, 50, 20);
        Product p2 = new Product("SP-002", "Samsung Galaxy S23", 999.99, 40, 15);
        Product p3 = new Product("LP-001", "MacBook Pro 14\"", 1299.99, 30, 10);
        Product p4 = new Product("HP-001", "Apple AirPods Pro", 199.99, 70, 25);
        Product p5 = new Product("TB-001", "iPad Pro 12.9\"", 499.99, 40, 15);
        
        // 设置所有产品为上架状态
        p1.upShelf();
        p2.upShelf();
        p3.upShelf();
        p4.upShelf();
        p5.upShelf();
        
        // 设置最后更新时间
        Date now = new Date();
        p1.setLastUpdated(now);
        p2.setLastUpdated(now);
        p3.setLastUpdated(now);
        p4.setLastUpdated(now);
        p5.setLastUpdated(now);
        
        // 添加到商家目录
        supplier.addProduct(p1);
        supplier.addProduct(p2);
        supplier.addProduct(p3);
        supplier.addProduct(p4);
        supplier.addProduct(p5);
        
        // 添加一个低库存产品作为示例 - 使用与仓库完全相同的ID
        Product lowStockProduct = new Product("SP-003", "Google Pixel 7", 599.99, 5, 15);
        lowStockProduct.upShelf();
        lowStockProduct.setStockStatus("Low");
        lowStockProduct.setLastUpdated(now);
        supplier.addProduct(lowStockProduct);
        
        System.out.println("成功创建并添加 " + supplier.getProductCatalog().size() + " 个示例产品到商家目录");
    }
}

