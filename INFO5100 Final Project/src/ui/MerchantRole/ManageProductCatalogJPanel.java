package ui.MerchantRole;

import Business.Product.Product;
import Business.Supplier.Supplier;
import org.netbeans.lib.awtextra.AbsoluteLayout;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import java.awt.CardLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author Rushabh
 */
public class ManageProductCatalogJPanel extends javax.swing.JPanel {

    /**
     * Creates new form ManageProductCatalogJPanel
     */
    private JPanel userProcessContainer;
    private Supplier supplier;

    public ManageProductCatalogJPanel(JPanel upc, Supplier s) {
        initComponents();
        userProcessContainer = upc;
        supplier = s;
        lblSupplier.setText("Supplier : " + s.getSupplyName());
        refreshTable();
    }

    public void refreshTable() {
        DefaultTableModel model = (DefaultTableModel) tblProductCatalog.getModel();
        model.setRowCount(0);

        for (Product p : supplier.getProductCatalog().getProductcatalog()) {
            Object row[] = new Object[6];  // Changed to 6 to match table columns
            row[0] = p.getName();  // Assuming Product has getName() method
            row[1] = p.getId();    // Assuming Product has getId() method
            row[2] = p.getPrice();
            row[3] = p.getQuantity(); // Assuming Product has getQuantity() method
            row[4] = p.getStockStatus(); // Assuming Product has getStockStatus() method
            row[5] = p.getLastUpdated(); // Assuming Product has getLastUpdated() method
            model.addRow(row);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitle = new javax.swing.JLabel();
        lblSupplier = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProductCatalog = new javax.swing.JTable();
        btnView = new javax.swing.JButton();
        btnCreate = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        lblProductId1 = new javax.swing.JLabel();
        txtId1 = new javax.swing.JTextField();
        btnSearch1 = new javax.swing.JButton();
        itemupshelf = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        lblProductName2 = new javax.swing.JLabel();
        txtProductNameView2 = new javax.swing.JTextField();
        lblProductPrice2 = new javax.swing.JLabel();
        txtPriceView2 = new javax.swing.JTextField();
        btnUpdate2 = new javax.swing.JButton();
        txtIdView2 = new javax.swing.JTextField();
        lblProductId3 = new javax.swing.JLabel();
        btnSave2 = new javax.swing.JButton();
        ComboBoxProduct2 = new javax.swing.JComboBox<>();
        lblPID2 = new javax.swing.JLabel();
        txtProductId = new javax.swing.JTextField();
        lblPrice2 = new javax.swing.JLabel();
        txtProductPrice2 = new javax.swing.JTextField();
        btnAdd2 = new javax.swing.JButton();
        lblPN2 = new javax.swing.JLabel();
        lblAvaila2 = new javax.swing.JLabel();
        txtThresholdStock = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        lblTitle3 = new javax.swing.JLabel();
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

        setPreferredSize(new java.awt.Dimension(1450, 800));

        lblTitle.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblTitle.setText("Product Catalog:");

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

        btnView.setText("View Details");
        btnView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewActionPerformed(evt);
            }
        });

        btnCreate.setText("ItemUpshelf");
        btnCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateActionPerformed(evt);
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

        itemupshelf.setLayout(new java.awt.CardLayout());

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

        ComboBoxProduct2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        ComboBoxProduct2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboBoxProduct2ActionPerformed(evt);
            }
        });
        jPanel3.add(ComboBoxProduct2, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 56, 220, -1));

        lblPID2.setText("Product ID:");
        jPanel3.add(lblPID2, new org.netbeans.lib.awtextra.AbsoluteConstraints(78, 91, -1, 30));

        txtProductId.setEditable(false);
        jPanel3.add(txtProductId, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 95, 220, -1));

        lblPrice2.setText("Product Price:");
        jPanel3.add(lblPrice2, new org.netbeans.lib.awtextra.AbsoluteConstraints(61, 130, -1, 30));

        txtProductPrice2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProductPrice2ActionPerformed(evt);
            }
        });
        jPanel3.add(txtProductPrice2, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 134, 220, -1));

        btnAdd2.setText("Add Product");
        btnAdd2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdd2ActionPerformed(evt);
            }
        });
        jPanel3.add(btnAdd2, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 250, -1, -1));

        lblPN2.setText("Product Name:");
        jPanel3.add(lblPN2, new org.netbeans.lib.awtextra.AbsoluteConstraints(61, 52, -1, 30));

        lblAvaila2.setText("Stock Warning Threshold:");
        jPanel3.add(lblAvaila2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, -1, 30));
        jPanel3.add(txtThresholdStock, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 180, 220, -1));

        jLabel3.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        jLabel3.setText("            ItemUpshelf");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 20, 220, -1));

        lblTitle3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblTitle3.setText("View Product Details");
        jPanel3.add(lblTitle3, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 20, -1, -1));

        itemupshelf.add(jPanel3, "card2");

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
            .addGap(0, 560, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
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
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 279, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
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
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        ViewProductDetails.add(jPanel1, "card2");

        lblTitle1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblTitle1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle1.setText("Product Management System");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                        .addComponent(btnSearch1)
                        .addGap(6, 6, 6)
                        .addComponent(lblSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1381, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnCreate, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(46, 46, 46)
                                .addComponent(btnView, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(43, 43, 43)
                                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(itemupshelf, javax.swing.GroupLayout.PREFERRED_SIZE, 549, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 276, Short.MAX_VALUE)
                        .addComponent(ViewProductDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 564, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(37, 37, 37))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(lblTitle1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(lblTitle1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
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
                        .addComponent(btnSearch1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(lblSupplier)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCreate)
                    .addComponent(btnView)
                    .addComponent(btnDelete))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ViewProductDetails, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(itemupshelf, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(120, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    private void btnViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewActionPerformed

        int selectedRowIndex = tblProductCatalog.getSelectedRow();
        if (selectedRowIndex < 0) {
            JOptionPane.showMessageDialog(null, "Pls select a row!!", "Warning", JOptionPane.WARNING_MESSAGE);
        }
        
        Product p = (Product) tblProductCatalog.getValueAt(selectedRowIndex, 0);
        
        ViewProductDetailJPanel vpdjp = new ViewProductDetailJPanel(userProcessContainer, p);
        userProcessContainer.add("ViewProductDetailJPanel", vpdjp);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.next(userProcessContainer);
    }//GEN-LAST:event_btnViewActionPerformed

    private void btnCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateActionPerformed

        CreateNewProductJPanel cnpjp = new CreateNewProductJPanel(userProcessContainer, supplier);
        userProcessContainer.add("CreateNewProductJPanel", cnpjp);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.next(userProcessContainer);
    }//GEN-LAST:event_btnCreateActionPerformed

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
        Product s = (Product) tblProductCatalog.getValueAt(selectedRowIndex, 0);
        supplier.getProductCatalog().removeProduct(s);
        refreshTable();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnSearch1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearch1ActionPerformed

        try{
            Product p;
            int productId = Integer.parseInt(txtId.getText());
            p = supplier.getProductCatalog().searchProduct(productId);
            if(p!=null){
                SearchResultJPanel vpdjp = new SearchResultJPanel(userProcessContainer, p);
                userProcessContainer.add("SearchResultJPanel", vpdjp);
                CardLayout layout = (CardLayout)userProcessContainer.getLayout();
                layout.next(userProcessContainer);
            }
            else{
                JOptionPane.showMessageDialog(null, "Nothing found", "No result found matching your criteria!!", JOptionPane.WARNING_MESSAGE);
                //return;
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Nothing found", "No result found matching your criteria!!", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnSearch1ActionPerformed

    private void txtProductNameView2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProductNameView2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProductNameView2ActionPerformed

    private void txtPriceView2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPriceView2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPriceView2ActionPerformed

    private void btnUpdate2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdate2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUpdate2ActionPerformed

    private void btnSave2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSave2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSave2ActionPerformed

    private void ComboBoxProduct2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboBoxProduct2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboBoxProduct2ActionPerformed

    private void txtProductPrice2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProductPrice2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProductPrice2ActionPerformed

    private void btnAdd2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdd2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAdd2ActionPerformed

    private void txtProductNameView3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProductNameView3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProductNameView3ActionPerformed

    private void txtPriceView3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPriceView3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPriceView3ActionPerformed

    private void btnUpdate3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdate3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUpdate3ActionPerformed

    private void btnSave3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSave3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSave3ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ComboBoxProduct2;
    private javax.swing.JPanel ViewProductDetails;
    private javax.swing.JButton btnAdd2;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnCreate;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSave2;
    private javax.swing.JButton btnSave3;
    private javax.swing.JButton btnSearch1;
    private javax.swing.JButton btnUpdate2;
    private javax.swing.JButton btnUpdate3;
    private javax.swing.JButton btnView;
    private javax.swing.JPanel itemupshelf;
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
    private javax.swing.JLabel lblSupplier;
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
    private javax.swing.JTextField txtProductId;
    private javax.swing.JTextField txtProductNameView2;
    private javax.swing.JTextField txtProductNameView3;
    private javax.swing.JTextField txtProductPrice2;
    private javax.swing.JTextField txtThresholdStock;
    // End of variables declaration//GEN-END:variables
}
