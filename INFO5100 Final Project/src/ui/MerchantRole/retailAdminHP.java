/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui.MerchantRole;

import ui.LogisticsRole.*;
import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Enterprise.LogisticsGroupEnterprise;
import Business.Enterprise.RetailCorpEnterprise;
import Business.Logistics.CustomsDeclaration;
import Business.Logistics.CustomsDeclarationDirectory;
import Business.Logistics.Shipment;
import Business.Logistics.ShipmentDirectory;
import Business.Organization.AdminOrganization;
import Business.Organization.LogisticsOrganization;
import Business.Organization.Organization;
import Business.Product.Product;
import Business.Product.ProductDirectory;
import Business.Supplier.Supplier;
import Business.UserAccount.UserAccount;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import ui.AdminRole.AddNewUser;
import ui.AdminRole.AdminHP;
import ui.AdminRole.AdminManageOwnProfile;

/**
 *
 * @author zhuchenyan
 */
public class RetailAdminHP extends AdminHP {

    private JPanel workArea;
    private UserAccount account;
    private Enterprise enterprise;
    private AdminOrganization adminOrg;
    private EcoSystem business;
    private RetailCorpEnterprise retailEnterprise;
    
    /**
     * Creates new form RetailAdminHP
     */
    public RetailAdminHP(JPanel jp, UserAccount ua, Enterprise e, AdminOrganization o, EcoSystem b) {
        super(jp, ua, e, o, b);
        
        this.workArea = jp;
        this.account = ua;
        this.enterprise = e;
        this.adminOrg = o;
        this.business = b;
        
        // Initialize retail enterprise
        if (e instanceof RetailCorpEnterprise) {
            this.retailEnterprise = (RetailCorpEnterprise) e;
        }
        
        initComponents();
        
        // Set welcome message for retail admin
        jLabel2.setText("Welcome Retail Admin!");
        
        // Apply theme to components
        setupTheme();
        
        // Set up button actions
        setupButtonActions();
        
        // Populate user table with data
        populateTable();
        
        // Add additional border styling to panels
        welcomeJPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 215, 235), 1));
        jPanel1.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 215, 235), 1),
            "Navigation",
            javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
            javax.swing.border.TitledBorder.DEFAULT_POSITION,
            new Font("Helvetica Neue", Font.BOLD, 14),
            new Color(26, 79, 156)
        ));
        jPanel2.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 215, 235), 1),
            "User Management",
            javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
            javax.swing.border.TitledBorder.DEFAULT_POSITION,
            new Font("Helvetica Neue", Font.BOLD, 14),
            new Color(26, 79, 156)
        ));
    }
    
    private void populateTable() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        
        // Add users from the enterprise to the table
        if (enterprise != null && enterprise.getUserAccountDirectory() != null) {
            for (UserAccount ua : enterprise.getUserAccountDirectory().getUserAccountList()) {
                model.addRow(new Object[] {
                    ua.getUsername(),
                    ua.getEmployee().getId(),
                    ua.getEmployee().getName(),
                    ua.getRole().toString(),
                    "********"
                });
            }
        }
    }
    
    /**
     * Set up button action listeners
     */
    private void setupButtonActions() {
        // Profile management button
        btnMyPro.addActionListener(evt -> btnMyProActionPerformed(evt));
        
        
        // User management buttons
        btnAddUser.addActionListener(evt -> btnAddUserActionPerformed(evt));
        btnUpdate.addActionListener(evt -> btnUpdateActionPerformed(evt));
        btnDeleteUser.addActionListener(evt -> btnDeleteUserActionPerformed(evt));
        btnRefresh.addActionListener(evt -> btnRefreshActionPerformed(evt));
        btnSearch.addActionListener(evt -> btnSearchActionPerformed(evt));
        
        // Navigation buttons
        btnCheckReport.addActionListener(evt -> btnViewSalesReportsActionPerformed(evt));
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        welcomeJPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        btnMyPro = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        btnCheckReport = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        txtSearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        lblUserManagement = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnAddUser = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDeleteUser = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(1450, 800));
        setMinimumSize(new java.awt.Dimension(1450, 800));

        welcomeJPanel.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Welcome Logistics Admin!");

        btnMyPro.setText("Manage My Profile");
        btnMyPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMyProActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout welcomeJPanelLayout = new javax.swing.GroupLayout(welcomeJPanel);
        welcomeJPanel.setLayout(welcomeJPanelLayout);
        welcomeJPanelLayout.setHorizontalGroup(
            welcomeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(welcomeJPanelLayout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 818, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 398, Short.MAX_VALUE)
                .addComponent(btnMyPro)
                .addGap(97, 97, 97))
        );
        welcomeJPanelLayout.setVerticalGroup(
            welcomeJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, welcomeJPanelLayout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, welcomeJPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnMyPro)
                .addGap(38, 38, 38))
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setText("Navigation");

        btnCheckReport.setText("Check Report");
        btnCheckReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCheckReportActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(jLabel3))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(btnCheckReport)))
                .addContainerGap(111, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel3)
                .addGap(39, 39, 39)
                .addComponent(btnCheckReport)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        btnSearch.setText("Search User");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        lblUserManagement.setText("User Management");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Username", "ID", "Employee Name", "Role", "Password"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        btnAddUser.setText("Add User");
        btnAddUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddUserActionPerformed(evt);
            }
        });

        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDeleteUser.setText("Delete");
        btnDeleteUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteUserActionPerformed(evt);
            }
        });

        btnRefresh.setText("Refresh List");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1095, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblUserManagement, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(96, 96, 96)
                                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(355, 355, 355)
                        .addComponent(btnAddUser)
                        .addGap(84, 84, 84)
                        .addComponent(btnUpdate)
                        .addGap(80, 80, 80)
                        .addComponent(btnDeleteUser)
                        .addGap(111, 111, 111)
                        .addComponent(btnRefresh)))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(lblUserManagement)
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearch))
                .addGap(42, 42, 42)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddUser)
                    .addComponent(btnUpdate)
                    .addComponent(btnDeleteUser)
                    .addComponent(btnRefresh))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(welcomeJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(welcomeJPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(17, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddUserActionPerformed
        // TODO add your handling code here:
        AddNewUser anu = new AddNewUser(workArea, account, enterprise, adminOrg, business);
        workArea.add("AddNewUser", anu);
        CardLayout l = (CardLayout) workArea.getLayout();
        l.show(workArea, "AddNewUser");
    }//GEN-LAST:event_btnAddUserActionPerformed

    private void btnMyProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMyProActionPerformed
        // TODO add your handling code here:
        AdminManageOwnProfile amop = new AdminManageOwnProfile(workArea, account);
        workArea.add("AdminManageOwn", amop);
        CardLayout l = (CardLayout) workArea.getLayout();
        l.show(workArea, "AdminManageOwn");
    }//GEN-LAST:event_btnMyProActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
        String searchText = txtSearch.getText().trim().toLowerCase();
        if (searchText.isEmpty()) {
            populateTable();
            return;
        }
        
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        
        // Search and add matching users to the table
        if (enterprise != null && enterprise.getUserAccountDirectory() != null) {
            for (UserAccount ua : enterprise.getUserAccountDirectory().getUserAccountList()) {
                if (ua.getUsername().toLowerCase().contains(searchText) || 
                    ua.getEmployee().getName().toLowerCase().contains(searchText) ||
                    ua.getRole().toString().toLowerCase().contains(searchText)) {
                    
                    model.addRow(new Object[] {
                        ua.getUsername(),
                        ua.getEmployee().getId(),
                        ua.getEmployee().getName(),
                        ua.getRole().toString(),
                        "********"
                    });
                }
            }
        }
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnViewProductsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewProductsActionPerformed
        // TODO add your handling code here:
        JPanel container = (JPanel) getParent();
        CardLayout layout = (CardLayout) container.getLayout();
        
        // 这里应该改为创建产品管理面板
        // 临时显示消息
//        javax.swing.JOptionPane.showMessageDialog(this, 
//                "Product Management feature will be implemented soon",
//                "Under Development", 
//                javax.swing.JOptionPane.INFORMATION_MESSAGE);
        
        // 实际实现时应该创建产品管理面板并显示:
        // ProductManagementPanel productPanel = new ProductManagementPanel(
        //        container, 
        //        account, 
        //        enterprise,
        //        this);
        // container.add("ProductManagementPanel", productPanel);
        // layout.show(container, "ProductManagementPanel");
    }//GEN-LAST:event_btnViewProductsActionPerformed

    private void btnViewSalesReportsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewSalesReportsActionPerformed
        // TODO add your handling code here:
        JPanel container = (JPanel) getParent();
        CardLayout layout = (CardLayout) container.getLayout();
        
        // 这里应该改为创建销售报告面板
        // 临时显示消息
//        javax.swing.JOptionPane.showMessageDialog(this, 
//                "Sales Reports feature will be implemented soon",
//                "Under Development", 
//                javax.swing.JOptionPane.INFORMATION_MESSAGE);
        
        // 实际实现时应该创建销售报告面板并显示:
        // SalesReportPanel reportPanel = new SalesReportPanel(
        //        container, 
        //        account, 
        //        enterprise,
        //        this);
        // container.add("SalesReportPanel", reportPanel);
        // layout.show(container, "SalesReportPanel");
    }//GEN-LAST:event_btnViewSalesReportsActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        // Get selected row from the table
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow < 0) {
            return;
        }
        
        // Get the user account from the selected row
        String username = (String) jTable1.getValueAt(selectedRow, 0);
        UserAccount selectedAccount = null;
        for (UserAccount ua : enterprise.getUserAccountDirectory().getUserAccountList()) {
            if (ua.getUsername().equals(username)) {
                selectedAccount = ua;
                break;
            }
        }
        
        if (selectedAccount != null) {
            // Open modify user panel - using AddNewUser with pre-selected data
            AddNewUser anu = new AddNewUser(workArea, account, enterprise, adminOrg, business);
            
            // Instead of calling populateUserInfo, pass the selected account directly
            // and set up the update mode
            workArea.add("ModifyUser", anu);
            CardLayout l = (CardLayout) workArea.getLayout();
            l.show(workArea, "ModifyUser");
            
            // After the panel is shown, you might need to manually set fields
            // Alternatively, create a new UpdateUserJPanel class specifically for updating
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteUserActionPerformed
        // TODO add your handling code here:
        // Get selected row from the table
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow < 0) {
            return;
        }
        
        // Get the user account from the selected row
        String username = (String) jTable1.getValueAt(selectedRow, 0);
        UserAccount ua = null;
        for (UserAccount account : enterprise.getUserAccountDirectory().getUserAccountList()) {
            if (account.getUsername().equals(username)) {
                ua = account;
                break;
            }
        }
        
        if (ua != null) {
            // Remove the user account
            enterprise.getUserAccountDirectory().getUserAccountList().remove(ua);
            // Refresh the table
            populateTable();
        }
    }//GEN-LAST:event_btnDeleteUserActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        // TODO add your handling code here:
        populateTable();
    }//GEN-LAST:event_btnRefreshActionPerformed

    
    private void btnCheckReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheckReportActionPerformed
        // TODO add your handling code here:
        JPanel container = (JPanel) getParent();
        CardLayout layout = (CardLayout) container.getLayout();
        
        // 创建销售报告面板
        SalesReport reportPanel = new SalesReport(container);
        container.add("SalesReportPanel", reportPanel);
        layout.show(container, "SalesReportPanel");
    }//GEN-LAST:event_btnCheckReportActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddUser;
    private javax.swing.JButton btnCheckReport;
    private javax.swing.JButton btnDeleteUser;
    private javax.swing.JButton btnMyPro;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblUserManagement;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JPanel welcomeJPanel;
    // End of variables declaration//GEN-END:variables

    
    /**
     * Apply theme to all components in RetailAdminHP
     */
    private void setupTheme() {
        // Set panel background color
        this.setBackground(new Color(240, 245, 255));
        welcomeJPanel.setBackground(new Color(240, 245, 255));
        jPanel1.setBackground(new Color(240, 245, 255));
        jPanel2.setBackground(new Color(240, 245, 255));
        
        // Style all buttons
        styleAllButtons();
        
        // Style table
        styleTable(jTable1, "jScrollPane1");
        
        // Style labels
        styleLabels();
        
        // Style text fields
        styleTextField(txtSearch);
    }
    
    /**
     * Apply consistent styling to all buttons
     */
    private void styleAllButtons() {
        styleButton(btnMyPro);
        
        styleButton(btnSearch);
        styleButton(btnAddUser);
        styleButton(btnUpdate);
        styleButton(btnDeleteUser);
        styleButton(btnRefresh);
        styleButton(btnCheckReport);
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
        button.setFont(new Font("Helvetica Neue", Font.BOLD, 14));
    }
    
    /**
     * Style table with consistent theme
     * @param table Table to style
     * @param scrollPaneName Name of the scrollpane
     */
    private void styleTable(JTable table, String scrollPaneName) {
        // Style table header
        if (table.getTableHeader() != null) {
            table.getTableHeader().setBackground(new Color(26, 79, 156));
            table.getTableHeader().setForeground(Color.WHITE);
            table.getTableHeader().setFont(new Font("Helvetica Neue", Font.BOLD, 14));
            table.getTableHeader().setBorder(BorderFactory.createLineBorder(new Color(13, 60, 130)));
        }
        
        // Style table - using darker colors for better visibility
        table.setBackground(new Color(240, 240, 250)); // Slightly darker background
        table.setForeground(new Color(0, 0, 0)); // Black text for maximum contrast
        table.setGridColor(new Color(180, 195, 235)); // Darker grid lines
        table.setSelectionBackground(new Color(90, 141, 224));
        table.setSelectionForeground(Color.WHITE);
        table.setFont(new Font("Helvetica Neue", Font.PLAIN, 14)); // Bold font for better visibility
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
    }
    
    /**
     * Style all labels with consistent theme
     */
    private void styleLabels() {
        // Style header label
        jLabel2.setForeground(new Color(26, 79, 156));
        jLabel2.setFont(new Font("Helvetica Neue", Font.BOLD, 24));
        
        // Hide navigation label since we're using titled border instead
        jLabel3.setVisible(false);
        
        // Hide user management label since we're using titled border instead
        lblUserManagement.setVisible(false);
    }
    
    /**
     * Style text field with consistent theme
     * @param textField TextField to style
     */
    private void styleTextField(JTextField textField) {
        textField.setBackground(Color.WHITE);
        textField.setForeground(new Color(26, 79, 156));
        textField.setFont(new Font("Helvetica Neue", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 195, 235), 1),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
    }

}
