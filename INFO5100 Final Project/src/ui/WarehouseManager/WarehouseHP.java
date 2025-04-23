/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui.WarehouseManager;

import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Network.Network;
import Business.Supplier.Supplier;
import Business.Warehouse.Warehouse;
import java.awt.CardLayout;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Component;
import java.awt.Font;

/**
 *
 * @author zhuchenyan
 */
public class WarehouseHP extends javax.swing.JPanel {

    /**
     * Creates new form LogisticsCoordinatorHP
     */
    private Supplier supplier;
    private String username;

    public WarehouseHP(String username) {
        initComponents();
        this.username = username;

        // Initialize panel layout
        WarehouseWorkAreajPanel.setLayout(new CardLayout());
        jSplitPane2.setLeftComponent(WarehousePanel);
        jSplitPane2.setRightComponent(WarehouseWorkAreajPanel);
        
        // Set title for the warehouse panel
        JLabel titleLabel = new JLabel("Warehouse Manager");
        titleLabel.setFont(new Font("Helvetica Neue", Font.BOLD, 24));
        titleLabel.setForeground(new Color(26, 79, 156));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        
        // Add the title label to the top of the left panel
        WarehousePanel.add(titleLabel, 0);
        
        // Apply UI theme
        setupTheme();
    }
    
    /**
     * Apply consistent UI theme to all components
     */
    private void setupTheme() {
        // Set panel background color
        this.setBackground(new Color(240, 245, 255));
        WarehousePanel.setBackground(new Color(240, 245, 255));
        WarehouseWorkAreajPanel.setBackground(new Color(240, 245, 255));
        
        // Style all buttons
        styleButton(btnProcurementRequest);
        styleButton(btnProfile1);
        styleButton(btnWarehouseManagement);
        styleButton(btnComplaintBox);
        
        // Set consistent width for all buttons in the control panel
        int buttonWidth = 200;
        btnProcurementRequest.setPreferredSize(new java.awt.Dimension(buttonWidth, 52));
        btnWarehouseManagement.setPreferredSize(new java.awt.Dimension(buttonWidth, 52));
        btnComplaintBox.setPreferredSize(new java.awt.Dimension(buttonWidth, 52));
        btnProfile1.setPreferredSize(new java.awt.Dimension(buttonWidth, 52));
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
        
        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(46, 109, 196)); // Lighter blue on hover
            }
            
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(26, 79, 156)); // Return to original color
            }
            
            public void mousePressed(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(13, 60, 130)); // Darker blue when pressed
            }
            
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(46, 109, 196)); // Return to hover color if still hovering
            }
        });
    }
    
    /**
     * Apply consistent styling to a text field
     * @param textField TextField to style
     */
    private void styleTextField(JTextField textField) {
        textField.setBackground(new Color(245, 245, 250)); // Light gray-white background
        textField.setForeground(new Color(13, 25, 51)); // Dark blue text
        textField.setCaretColor(new Color(26, 79, 156)); // Medium blue cursor
        textField.setBorder(BorderFactory.createLineBorder(new Color(90, 141, 224), 1));
        textField.setFont(new Font("Helvetica Neue", Font.PLAIN, 14));
    }
    
    /**
     * Apply title label styling
     * @param label Label to style
     */
    private void styleTitleLabel(JLabel label) {
        label.setForeground(new Color(26, 79, 156));
        label.setFont(new Font("Helvetica Neue", Font.BOLD, 20));
    }
    
    /**
     * Apply regular label styling
     * @param label Label to style
     */
    private void styleLabel(JLabel label) {
        label.setForeground(new Color(26, 79, 156));
        label.setFont(new Font("Helvetica Neue", Font.PLAIN, 14));
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane2 = new javax.swing.JSplitPane();
        WarehousePanel = new javax.swing.JPanel();
        btnProcurementRequest = new javax.swing.JButton();
        btnProfile1 = new javax.swing.JButton();
        btnWarehouseManagement = new javax.swing.JButton();
        btnComplaintBox = new javax.swing.JButton();
        WarehouseWorkAreajPanel = new javax.swing.JPanel();

        btnProcurementRequest.setText("Procurement Request");
        btnProcurementRequest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProcurementRequestActionPerformed(evt);
            }
        });

        btnProfile1.setText("My Profile");
        btnProfile1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProfile1ActionPerformed(evt);
            }
        });

        btnWarehouseManagement.setText("Management");
        btnWarehouseManagement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnWarehouseManagementActionPerformed(evt);
            }
        });

        btnComplaintBox.setText("Complaint Box");
        btnComplaintBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnComplaintBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout WarehousePanelLayout = new javax.swing.GroupLayout(WarehousePanel);
        WarehousePanel.setLayout(WarehousePanelLayout);
        WarehousePanelLayout.setHorizontalGroup(
            WarehousePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(WarehousePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(WarehousePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, WarehousePanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnProfile1, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(WarehousePanelLayout.createSequentialGroup()
                        .addGroup(WarehousePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnWarehouseManagement, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnProcurementRequest, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnComplaintBox, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        WarehousePanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnComplaintBox, btnProcurementRequest, btnProfile1, btnWarehouseManagement});

        WarehousePanelLayout.setVerticalGroup(
            WarehousePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(WarehousePanelLayout.createSequentialGroup()
                .addGap(272, 272, 272)
                .addComponent(btnProcurementRequest, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(btnWarehouseManagement, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(btnComplaintBox, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(btnProfile1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(2303, Short.MAX_VALUE))
        );

        WarehousePanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnComplaintBox, btnProcurementRequest, btnProfile1, btnWarehouseManagement});

        jSplitPane2.setLeftComponent(WarehousePanel);

        javax.swing.GroupLayout WarehouseWorkAreajPanelLayout = new javax.swing.GroupLayout(WarehouseWorkAreajPanel);
        WarehouseWorkAreajPanel.setLayout(WarehouseWorkAreajPanelLayout);
        WarehouseWorkAreajPanelLayout.setHorizontalGroup(
            WarehouseWorkAreajPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1391, Short.MAX_VALUE)
        );
        WarehouseWorkAreajPanelLayout.setVerticalGroup(
            WarehouseWorkAreajPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2876, Short.MAX_VALUE)
        );

        jSplitPane2.setRightComponent(WarehouseWorkAreajPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane2)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane2)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnProcurementRequestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProcurementRequestActionPerformed
        // Create procurement request panel
        ProcurementRequestsJPanel procurementRequestsJPanel = new ProcurementRequestsJPanel(
            WarehouseWorkAreajPanel, 
            Business.Warehouse.Warehouse.getInstance()
        );
        WarehouseWorkAreajPanel.add("ProcurementRequestsJPanel", procurementRequestsJPanel);
        CardLayout layout = (CardLayout) WarehouseWorkAreajPanel.getLayout();
        layout.next(WarehouseWorkAreajPanel);
    }//GEN-LAST:event_btnProcurementRequestActionPerformed

    private void btnProfile1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProfile1ActionPerformed
        // Get current user account from EcoSystem
        System.out.println("WarehouseHP: Profile button clicked, username: " + this.username);
        
        Business.UserAccount.UserAccount account = null;
        for (Business.UserAccount.UserAccount ua : Business.EcoSystem.getInstance().getUserAccountDirectory().getUserAccountList()) {
            if (ua.getUsername().equals(this.username)) {
                account = ua;
                break;
            }
        }

        if (account == null) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Unable to obtain user information, please log in again.",
                    "Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Create ManageOwnProfile panel, pass work area panel and user account
        System.out.println("WarehouseHP: Create a ManageOwnProfile using an account: " + account.getUsername());
        ManageOwnProfile manageOwnProfile = new ManageOwnProfile(WarehouseWorkAreajPanel, account);
        WarehouseWorkAreajPanel.add("ManageOwnProfile", manageOwnProfile);

        // Use CardLayout to display the panel
        CardLayout layout = (CardLayout) WarehouseWorkAreajPanel.getLayout();
        layout.next(WarehouseWorkAreajPanel);
    }//GEN-LAST:event_btnProfile1ActionPerformed

    private void btnWarehouseManagementActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnWarehouseManagementActionPerformed
        // TODO add your handling code here:
        // Get current user account
        Business.UserAccount.UserAccount account = null;
        for (Business.UserAccount.UserAccount ua : Business.EcoSystem.getInstance().getUserAccountDirectory().getUserAccountList()) {
            if (ua.getUsername().equals(this.username)) {
                account = ua;
                break;
            }
        }

        if (account == null) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Unable to obtain user information, please log in again.",
                    "Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get enterprise instance
        Enterprise enterprise = null;
        EcoSystem system = Business.EcoSystem.getInstance();

        // Iterate through network and enterprise directory to find warehouse enterprise
        for (Network network : system.getNetworkList()) {
            for (Enterprise e : network.getEnterpriseDirectory().getEnterpriseList()) {
                if (e.getEnterpriseType().equals(Enterprise.EnterpriseType.WarehouseSupplier)) {
                    enterprise = e;
                    break;
                }
            }
            if (enterprise != null) {
                break;
            }
        }

        if (enterprise == null) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Unable to obtain warehouse enterprise information.",
                    "Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Create WarehouseManagerHomePage instance, pass more parameters
        WarehouseManagerHomePage warehouseManagerHomePage = new WarehouseManagerHomePage(
                WarehouseWorkAreajPanel,
                Business.Warehouse.Warehouse.getInstance(),
                enterprise, // Add enterprise parameter
                account // Add user account parameter
        );

        WarehouseWorkAreajPanel.add("WarehouseManagerHomePage", warehouseManagerHomePage);
        CardLayout layout = (CardLayout) WarehouseWorkAreajPanel.getLayout();
        layout.next(WarehouseWorkAreajPanel);
    }//GEN-LAST:event_btnWarehouseManagementActionPerformed

    private void btnComplaintBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnComplaintBoxActionPerformed
        // TODO add your handling code here:
        WarehouseCustomerComplaintContent warehouseCustomerComplaintContent = new WarehouseCustomerComplaintContent(WarehouseWorkAreajPanel, 
                Business.Warehouse.Warehouse.getInstance());
        WarehouseWorkAreajPanel.add("WarehouseCustomerComplaintContent", warehouseCustomerComplaintContent);
        CardLayout layout = (CardLayout) WarehouseWorkAreajPanel.getLayout();
        layout.next(WarehouseWorkAreajPanel);

    }//GEN-LAST:event_btnComplaintBoxActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel WarehousePanel;
    private javax.swing.JPanel WarehouseWorkAreajPanel;
    private javax.swing.JButton btnComplaintBox;
    private javax.swing.JButton btnProcurementRequest;
    private javax.swing.JButton btnProfile1;
    private javax.swing.JButton btnWarehouseManagement;
    private javax.swing.JSplitPane jSplitPane2;
    // End of variables declaration//GEN-END:variables
}
