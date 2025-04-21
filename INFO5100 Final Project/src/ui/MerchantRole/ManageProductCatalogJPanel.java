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
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.JTableHeader;
import java.awt.Component;

/**
 * Merchant Product Catalog Management Panel
 * @author Rushabh, wangsiting
 */
public class ManageProductCatalogJPanel extends javax.swing.JPanel {

    private JPanel userProcessContainer;
    private Supplier supplier;
    private Warehouse warehouse;

    /**
     * Create merchant product catalog management panel
     * @param upc User process container
     * @param s Supplier object
     */
    public ManageProductCatalogJPanel(JPanel upc, Supplier s) {
        initComponents();
        userProcessContainer = upc;
        supplier = s;
        warehouse = Warehouse.getInstance();

        System.out.println("Initializing ManageProductCatalogJPanel...");
        System.out.println("Supplier: " + (supplier != null ? supplier.getSupplyName() : "null"));
        
        // If supplier is null, try to get from MerchantRole
        if (supplier == null) {
            System.out.println("Warning: Provided supplier is null, attempting to get default supplier from MerchantRole");
            supplier = Business.Role.MerchantRole.getDemoSupplier();
            System.out.println("Supplier from MerchantRole: " + (supplier != null ? supplier.getSupplyName() : "Still null"));
        }
        
        // Check supplier's product catalog
        if (supplier != null && supplier.getProductCatalog() != null) {
            System.out.println("Product catalog contains " + supplier.getProductCatalog().size() + " products");
            
            // If catalog is empty, add sample products
            if (supplier.getProductCatalog().isEmpty()) {
                System.out.println("Product catalog is empty, adding sample products");
                createAndAddSampleProducts();
            }
        } else {
            System.out.println("Warning: Product catalog is empty or unavailable");
            
            // If supplier is not null but catalog is null, initialize catalog
            if (supplier != null && supplier.getProductCatalog() == null) {
                supplier.setProductCatalog(new ArrayList<>());
                createAndAddSampleProducts();
            }
        }
        
        // Set title
        if (supplier != null) {
            lblTitle1.setText("Product Management System for " + supplier.getSupplyName());
        } else {
            lblTitle1.setText("Product Management System");
        }
        
        // Refresh table
        refreshTable();
        
        // Apply UI theme
        setupTheme();
    }
    
    /**
     * Apply consistent UI theme to all components
     */
    private void setupTheme() {
        // Set panel background color
        this.setBackground(new Color(240, 245, 255));
        ViewProductDetails.setBackground(new Color(240, 245, 255));
        RequestProducts.setBackground(new Color(240, 245, 255));
        jPanel1.setBackground(new Color(240, 245, 255));
        jPanel3.setBackground(new Color(240, 245, 255));
        
        // Style all buttons
        styleAllButtons();
        
        // Style all text fields
        styleAllTextFields();
        
        // Style all labels
        styleAllLabels();
        
        // Style table
        styleTable(tblProductCatalog);
    }
    
    /**
     * Apply consistent styling to all buttons
     */
    private void styleAllButtons() {
        // Style action buttons
        styleButton(btnView);
        styleButton(btnBack);
        styleButton(btnDelete);
        styleButton(btnRefresh);
        styleButton(btnSearch1);
        styleButton(btnRequestProducts);
        
        // Style detail panel buttons
        styleButton(btnUpdate2);
        styleButton(btnSave2);
        styleButton(btnAdd2);
        styleButton(btnUpdate3);
        styleButton(btnSave3);
    }
    
    /**
     * Apply consistent styling to a button
     * @param button Button to style
     */
    private void styleButton(JButton button) {
        button.setBackground(new Color(26, 79, 156)); // Medium blue
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        // Add a subtle border with rounded corners
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(13, 60, 130), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
    }
    
    /**
     * Apply consistent styling to all text fields
     */
    private void styleAllTextFields() {
        // Style search fields
        styleTextField(txtId1);
        
        // Style product details fields
        styleTextField(txtIdView2);
        styleTextField(txtIdView3);
        styleTextField(txtProductNameView2);
        styleTextField(txtProductNameView3);
        styleTextField(txtProductNameView4);
        styleTextField(txtPriceView2);
        styleTextField(txtPriceView3);
        styleTextField(txtPriceView4);
        styleTextField(txtProductId);
        styleTextField(txtRequestQuantity);
    }
    
    /**
     * Apply consistent styling to a text field
     * @param textField TextField to style
     */
    private void styleTextField(JTextField textField) {
        textField.setBackground(new Color(245, 245, 250)); // Light gray-white background
        textField.setForeground(new Color(13, 25, 51));    // Dark blue text
        textField.setCaretColor(new Color(26, 79, 156));   // Medium blue cursor
        textField.setBorder(BorderFactory.createLineBorder(new Color(90, 141, 224), 1));
        textField.setFont(new java.awt.Font("Helvetica Neue", java.awt.Font.PLAIN, 14));
    }
    
    /**
     * Apply consistent styling to all labels
     */
    private void styleAllLabels() {
        // Style title labels
        styleTitleLabel(lblTitle);
        styleTitleLabel(lblTitle1);
        styleTitleLabel(lblTitle3);
        styleTitleLabel(lblTitle4);
        styleTitleLabel(lblTitle5);
        
        // Style regular labels
        styleLabel(lblProductId1);
        styleLabel(lblProductName2);
        styleLabel(lblProductName3);
        styleLabel(lblProductId3);
        styleLabel(lblProductId4);
        styleLabel(lblProductPrice2);
        styleLabel(lblProductPrice3);
        styleLabel(lblPID2);
        styleLabel(lblPrice2);
        styleLabel(lblPN2);
        styleLabel(lblAvaila2);
        
    }
    
    /**
     * Apply title label styling
     * @param label Label to style
     */
    private void styleTitleLabel(JLabel label) {
        label.setForeground(new Color(26, 79, 156));
        label.setFont(new java.awt.Font("Helvetica Neue", java.awt.Font.BOLD, 20));
    }
    
    /**
     * Apply regular label styling
     * @param label Label to style
     */
    private void styleLabel(JLabel label) {
        label.setForeground(new Color(26, 79, 156));
        label.setFont(new java.awt.Font("Helvetica Neue", java.awt.Font.PLAIN, 14));
    }
    
    /**
     * Style table with consistent theme
     * @param table Table to style
     */
    private void styleTable(JTable table) {
        // Style table header
        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(26, 79, 156));
        header.setForeground(Color.WHITE);
        header.setFont(new java.awt.Font("Helvetica Neue", java.awt.Font.BOLD, 14));
        header.setBorder(new LineBorder(new Color(13, 60, 130)));
        
        // Style table - using darker colors for better visibility
        table.setBackground(new Color(240, 240, 250)); // Slightly darker background
        table.setForeground(new Color(0, 0, 0)); // Black text for maximum contrast
        table.setGridColor(new Color(180, 195, 235)); // Darker grid lines
        table.setSelectionBackground(new Color(90, 141, 224));
        table.setSelectionForeground(Color.WHITE);
        table.setFont(new java.awt.Font("Helvetica Neue", java.awt.Font.BOLD, 14)); // Bold font for better visibility
        table.setRowHeight(30); // Increase row height for better readability
        
        // Set alternating row colors with more contrast
        table.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? new Color(240, 240, 250) : new Color(220, 220, 235));
                    c.setForeground(new Color(0, 0, 0)); // Ensure text is always black for maximum contrast
                }
                return c;
            }
        });
        
        // Style scroll pane
        jScrollPane1.setBorder(new LineBorder(new Color(26, 79, 156)));
    }

    /**
     * Refresh product table
     */
    public void refreshTable() {
        DefaultTableModel model = (DefaultTableModel) tblProductCatalog.getModel();
        model.setRowCount(0);
        
        System.out.println("Starting to refresh product table...");
        
        if (supplier == null) {
            System.out.println("Error: supplier is null");
            return;
        }
        
        if (supplier.getProductCatalog() == null) {
            System.out.println("Error: Product catalog is empty");
            return;
        }
        
        System.out.println("Number of products in catalog: " + supplier.getProductCatalog().size());

        for (Product p : supplier.getProductCatalog()) {
            Object row[] = new Object[6];
            row[0] = p.getProductName();
            row[1] = p.getProductId();
            row[2] = p.getPrice();
            row[3] = p.getQuantity();
            row[4] = p.getStockStatus();
            row[5] = p.getLastUpdated();
            model.addRow(row);
            System.out.println("Adding product to table: " + p.getProductName() + " (ID: " + p.getProductId() + ")");
        }
        
        System.out.println("Table refresh completed, row count: " + model.getRowCount());
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
        lblTitle5 = new javax.swing.JLabel();
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

        lblTitle5.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        lblTitle5.setText("Request Products");
        jPanel3.add(lblTitle5, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 20, 220, -1));

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
        // No operation
    }//GEN-LAST:event_btnUpdate2ActionPerformed

    private void btnSave2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSave2ActionPerformed
        // No operation
    }//GEN-LAST:event_btnSave2ActionPerformed

    private void btnAdd2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdd2ActionPerformed
        // Handle request button click event
        try {
            System.out.println("Processing product request...");
            
            // Check if request quantity is valid
            if (txtRequestQuantity.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter the request quantity", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int requestedAmount;
            try {
                requestedAmount = Integer.parseInt(txtRequestQuantity.getText().trim());
                if (requestedAmount <= 0) {
                    JOptionPane.showMessageDialog(null, "Please enter a quantity greater than 0", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid number", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Get product information from the UI
            String productId = txtIdView2.getText();
            String productName = txtProductNameView2.getText();
            
            // Check if product information is empty
            if (productId == null || productId.trim().isEmpty()) {
                System.out.println("Product ID is empty, trying to get from hidden field");
                productId = txtProductId.getText();
            }
            
            if (productName == null || productName.trim().isEmpty()) {
                System.out.println("Product name is empty, trying to get from hidden field");
                productName = txtProductNameView4.getText();
            }
            
            // Check product information again
            if (productId == null || productId.trim().isEmpty() || productName == null || productName.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Unable to identify product information, please select a product again", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
                System.out.println( "Product information - Product ID: " + productId + ", Product Name: " + productName + ", Quantity: " + requestedAmount);
            
            // Check if price field is empty
            if (txtPriceView2.getText() == null || txtPriceView2.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Price cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            double price = Double.parseDouble(txtPriceView2.getText().trim());
            System.out.println("Product price: " + price);
            
            // Create work request
            MerchantWorkRequest request = new MerchantWorkRequest();
            request.setProductId(productId);
            request.setProductName(productName);
            request.setPrice(price);
            request.setRequestedAmount(requestedAmount);
            request.setMessage("Merchant requests product: " + productName + " (ID: " + productId + ")");
            request.setStatus("Pending"); // Set initial status to Pending
            request.setRequestDate(new Date()); // Set request date
            
            System.out.println("Request created: " + request.getMessage() + ", Status: " + request.getStatus());
            
            // Set sender if user account exists
            if (Business.EcoSystem.getInstance().getUserAccountDirectory() != null &&
                !Business.EcoSystem.getInstance().getUserAccountDirectory().getUserAccountList().isEmpty()) {
                request.setSender(Business.EcoSystem.getInstance().getUserAccountDirectory().getUserAccountList().get(0));
                System.out.println("Set request sender: " + request.getSender().getUsername());
            } else {
                System.out.println("Warning: Unable to set request sender - User account is empty");
            }
            
            // Get system work queue and add request
            Business.EcoSystem system = Business.EcoSystem.getInstance();
            if (system.getWorkQueue() == null) {
                System.out.println("System work queue is empty, creating new queue");
                system.setWorkQueue(new Business.WorkQueue.WorkQueue());
            }
            system.getWorkQueue().getWorkRequestList().add(request);
            System.out.println("Request added to system work queue, current queue size: " + system.getWorkQueue().getWorkRequestList().size());
            
            // Update product status to "Requested"
            boolean productFound = false;
            for (Product p : supplier.getProductCatalog()) {
                if (p.getProductId().equals(productId)) {
                    p.setStockStatus("Requested");
                    p.setLastUpdated(new Date());
                    productFound = true;
                    System.out.println("Product " + p.getProductName() + " status updated to: Requested");
                    break;
                }
            }
            
            if (!productFound) {
                System.out.println("Warning: Unable to find product in product catalog with ID: " + productId);
            }
            
            // Refresh table to display updated status
            refreshTable();
            
            // Display success message
            JOptionPane.showMessageDialog(null,
                "Successfully submitted a purchase request for " + requestedAmount + " " + productName,
                "Request submitted",
                JOptionPane.INFORMATION_MESSAGE);
            
            // Clear request panel
            txtRequestQuantity.setText("");
            System.out.println("Product request processing completed");
            
        } catch (Exception e) {
            System.err.println("Error processing product request: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnAdd2ActionPerformed

    private void txtProductNameView3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProductNameView3ActionPerformed
        // No operation
    }//GEN-LAST:event_txtProductNameView3ActionPerformed

    private void txtPriceView3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPriceView3ActionPerformed
        // No operation
    }//GEN-LAST:event_txtPriceView3ActionPerformed

    private void btnUpdate3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdate3ActionPerformed
        // Allow editing of product name and price
        txtProductNameView3.setEditable(true);
        txtPriceView3.setEditable(true);
        btnSave3.setEnabled(true);
    }//GEN-LAST:event_btnUpdate3ActionPerformed

    private void btnSave3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSave3ActionPerformed
        // Save modified product information
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
        
        // Update timestamp
        p.setLastUpdated(new Date());
        
        // Disable editing after saving
        txtProductNameView3.setEditable(false);
        txtPriceView3.setEditable(false);
        btnSave3.setEnabled(false);
        
        refreshTable();
        JOptionPane.showMessageDialog(null, "Product updated successfully!", "Info", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnSave3ActionPerformed

    private void btnRequestProductsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRequestProductsActionPerformed
        // Get selected product row
        int selectedRowIndex = tblProductCatalog.getSelectedRow();
        
        // Check if a row is selected and if supplier has a product catalog
        if (selectedRowIndex < 0) {
            JOptionPane.showMessageDialog(null, "Please select a product to request", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        // Check if merchant's product catalog is empty
        if (supplier == null || supplier.getProductCatalog() == null || supplier.getProductCatalog().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Product catalog is empty, please add products first", "Info", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            System.out.println("Preparing to request product, selected row index: " + selectedRowIndex);
            
            // Get product information
            Product p = supplier.getProductCatalog().get(selectedRowIndex);
            String productId = p.getProductId();
            String productName = p.getProductName();
            double price = p.getPrice();
            
            System.out.println("Got product information - ID: " + productId + ", Name: " + productName + ", Price: " + price);
            
            // Display product information in the request panel
            txtProductNameView2.setText(productName);
            txtIdView2.setText(productId);
            txtPriceView2.setText(String.valueOf(price));
            
            // Ensure price field is not empty
            if (txtPriceView2.getText() == null || txtPriceView2.getText().trim().isEmpty()) {
                txtPriceView2.setText("0.0"); // Set default price to prevent null exception
                System.out.println("Product price is empty, default value 0.0 set");
            }
            
            // Save product name and ID to hidden fields for later use
            txtProductNameView4.setText(productName);
            txtProductId.setText(productId);
            
            // Switch to request card view
            CardLayout layout = (CardLayout) RequestProducts.getLayout();
            layout.show(RequestProducts, "card2");
            
            // Optional: If product stock is low, suggest request quantity
            if ("Low".equals(p.getStockStatus())) {
                txtRequestQuantity.setText("20"); // Suggested order quantity
                JOptionPane.showMessageDialog(null, 
                    "The product is low in stock, please replenish the stock.", 
                    "Stock status", 
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                txtRequestQuantity.setText(""); // Clear request quantity
            }
            
        } catch (IndexOutOfBoundsException ex) {
            System.err.println("Index error: " + ex.getMessage());
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "Unable to access selected product, please refresh product list and try again.", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            System.err.println("Error: " + ex.getMessage());
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "Error processing request: " + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnRequestProductsActionPerformed

    private void txtProductNameView4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProductNameView4ActionPerformed
        // No operation
    }//GEN-LAST:event_txtProductNameView4ActionPerformed

    private void txtPriceView4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPriceView4ActionPerformed
        // No operation
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
    private javax.swing.JLabel lblTitle5;
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
     * Create and add sample products to merchant catalog
     */
    private void createAndAddSampleProducts() {
        if (supplier == null) {
            System.out.println("Error: Unable to add sample products, supplier is null");
            return;
        }
        
        System.out.println("Starting to create sample products...");
        
        // Create sample products - using the same product IDs and names as the warehouse
        Product p1 = new Product("SP-001", "Apple iPhone 15 Pro", 799.99, 50, 20);
        Product p2 = new Product("SP-002", "Samsung Galaxy S23", 999.99, 40, 15);
        Product p3 = new Product("LP-001", "MacBook Pro 14\"", 1299.99, 30, 10);
        Product p4 = new Product("HP-001", "Apple AirPods Pro", 199.99, 70, 25);
        Product p5 = new Product("TB-001", "iPad Pro 12.9\"", 499.99, 40, 15);
        
        // Set all products to active (upShelf) status
        p1.upShelf();
        p2.upShelf();
        p3.upShelf();
        p4.upShelf();
        p5.upShelf();
        
        // Set last updated time
        Date now = new Date();
        p1.setLastUpdated(now);
        p2.setLastUpdated(now);
        p3.setLastUpdated(now);
        p4.setLastUpdated(now);
        p5.setLastUpdated(now);
        
        // Add to merchant catalog
        supplier.addProduct(p1);
        supplier.addProduct(p2);
        supplier.addProduct(p3);
        supplier.addProduct(p4);
        supplier.addProduct(p5);
        
        // Add a low stock product as an example - using the same ID as the warehouse
        Product lowStockProduct = new Product("SP-003", "Google Pixel 7", 599.99, 5, 15);
        lowStockProduct.upShelf();
        lowStockProduct.setStockStatus("Low");
        lowStockProduct.setLastUpdated(now);
        supplier.addProduct(lowStockProduct);
        
        System.out.println("Successfully created and added " + supplier.getProductCatalog().size() + " sample products to merchant catalog");
    }
}

