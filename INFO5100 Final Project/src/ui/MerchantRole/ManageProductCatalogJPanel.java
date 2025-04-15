package ui.MerchantRole;

import Business.Product.Product;
//import model.Supplier;
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
            Object row[] = new Object[4];
            row[0] = p;
            row[1] = p.getModelNumber();
            row[2] = p.getPrice();
            row[3] = p.getAvail();
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
        jPanel2 = new javax.swing.JPanel();
        lblProductName1 = new javax.swing.JLabel();
        txtProductNameView1 = new javax.swing.JTextField();
        lblProductPrice1 = new javax.swing.JLabel();
        txtPriceView1 = new javax.swing.JTextField();
        btnUpdate1 = new javax.swing.JButton();
        txtIdView1 = new javax.swing.JTextField();
        lblProductId2 = new javax.swing.JLabel();
        btnSave1 = new javax.swing.JButton();
        ComboBoxProduct1 = new javax.swing.JComboBox<>();
        lblPID1 = new javax.swing.JLabel();
        txtId2 = new javax.swing.JTextField();
        lblPrice1 = new javax.swing.JLabel();
        txtPrice1 = new javax.swing.JTextField();
        btnAdd1 = new javax.swing.JButton();
        lblPN1 = new javax.swing.JLabel();
        lblAvaila1 = new javax.swing.JLabel();
        txtAva1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        lblTitle2 = new javax.swing.JLabel();

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

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblProductName1.setText("Product Name:");
        jPanel2.add(lblProductName1, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 50, -1, -1));

        txtProductNameView1.setEditable(false);
        txtProductNameView1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProductNameView1ActionPerformed(evt);
            }
        });
        jPanel2.add(txtProductNameView1, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 50, 250, -1));

        lblProductPrice1.setText("Product Price:");
        jPanel2.add(lblProductPrice1, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 130, -1, -1));

        txtPriceView1.setEditable(false);
        txtPriceView1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPriceView1ActionPerformed(evt);
            }
        });
        jPanel2.add(txtPriceView1, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 130, 250, -1));

        btnUpdate1.setText("Update Product");
        btnUpdate1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdate1ActionPerformed(evt);
            }
        });
        jPanel2.add(btnUpdate1, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 210, 134, -1));

        txtIdView1.setEditable(false);
        jPanel2.add(txtIdView1, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 90, 250, -1));

        lblProductId2.setText("Product ID:");
        jPanel2.add(lblProductId2, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 90, -1, -1));

        btnSave1.setText("Save");
        btnSave1.setEnabled(false);
        btnSave1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSave1ActionPerformed(evt);
            }
        });
        jPanel2.add(btnSave1, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 250, 134, -1));

        ComboBoxProduct1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        ComboBoxProduct1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboBoxProduct1ActionPerformed(evt);
            }
        });
        jPanel2.add(ComboBoxProduct1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 56, 220, -1));

        lblPID1.setText("Product ID:");
        jPanel2.add(lblPID1, new org.netbeans.lib.awtextra.AbsoluteConstraints(78, 91, -1, 30));

        txtId2.setEditable(false);
        jPanel2.add(txtId2, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 95, 220, -1));

        lblPrice1.setText("Product Price:");
        jPanel2.add(lblPrice1, new org.netbeans.lib.awtextra.AbsoluteConstraints(61, 130, -1, 30));

        txtPrice1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrice1ActionPerformed(evt);
            }
        });
        jPanel2.add(txtPrice1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 134, 220, -1));

        btnAdd1.setText("Add Product");
        btnAdd1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdd1ActionPerformed(evt);
            }
        });
        jPanel2.add(btnAdd1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 250, -1, -1));

        lblPN1.setText("Product Name:");
        jPanel2.add(lblPN1, new org.netbeans.lib.awtextra.AbsoluteConstraints(61, 52, -1, 30));

        lblAvaila1.setText("Stock Warning Threshold:");
        jPanel2.add(lblAvaila1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, -1, 30));
        jPanel2.add(txtAva1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 180, 220, -1));

        jLabel2.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        jLabel2.setText("            ItemUpshelf");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 20, 220, -1));

        lblTitle2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblTitle2.setText("View Product Details");
        jPanel2.add(lblTitle2, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 20, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addComponent(btnCreate, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(btnView, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 1389, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(btnBack)
                .addGap(6, 6, 6)
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCreate)
                    .addComponent(btnView)
                    .addComponent(btnDelete))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 94, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52))
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

    private void txtProductNameView1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProductNameView1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProductNameView1ActionPerformed

    private void txtPriceView1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPriceView1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPriceView1ActionPerformed

    private void btnUpdate1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdate1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUpdate1ActionPerformed

    private void btnSave1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSave1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSave1ActionPerformed

    private void txtPrice1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrice1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrice1ActionPerformed

    private void btnAdd1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdd1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAdd1ActionPerformed

    private void ComboBoxProduct1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboBoxProduct1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboBoxProduct1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ComboBoxProduct1;
    private javax.swing.JButton btnAdd1;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnCreate;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSave1;
    private javax.swing.JButton btnSearch1;
    private javax.swing.JButton btnUpdate1;
    private javax.swing.JButton btnView;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAvaila1;
    private javax.swing.JLabel lblPID1;
    private javax.swing.JLabel lblPN1;
    private javax.swing.JLabel lblPrice1;
    private javax.swing.JLabel lblProductId1;
    private javax.swing.JLabel lblProductId2;
    private javax.swing.JLabel lblProductName1;
    private javax.swing.JLabel lblProductPrice1;
    private javax.swing.JLabel lblSupplier;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblTitle2;
    private javax.swing.JTable tblProductCatalog;
    private javax.swing.JTextField txtAva1;
    private javax.swing.JTextField txtId1;
    private javax.swing.JTextField txtId2;
    private javax.swing.JTextField txtIdView1;
    private javax.swing.JTextField txtPrice1;
    private javax.swing.JTextField txtPriceView1;
    private javax.swing.JTextField txtProductNameView1;
    // End of variables declaration//GEN-END:variables
}
