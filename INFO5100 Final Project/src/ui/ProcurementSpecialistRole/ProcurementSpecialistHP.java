/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui.ProcurementSpecialistRole;

import Business.Enterprise.Enterprise;
import Business.Supplier.Supplier;
import Business.UserAccount.UserAccount;
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
public class ProcurementSpecialistHP extends javax.swing.JPanel {

    /**
     * Creates new form LogisticsCoordinatorHP
     */
    private Supplier supplier;
    private String username;
    private Enterprise enterprise;
    private UserAccount account;
    private JPanel userProcessContainer;
    
    public ProcurementSpecialistHP(String username) {
        initComponents();
        this.username = username;
        
        // Initialize panel layout
        ProcurementSpecialistWorkAreajPanel.setLayout(new CardLayout());
        jSplitPane2.setLeftComponent(ProcurementSpecialistPanel);
        jSplitPane2.setRightComponent(ProcurementSpecialistWorkAreajPanel);
        
        // Apply UI theme
        setupTheme();
    }
    
    // Add constructor with user process container
    public ProcurementSpecialistHP(JPanel userProcessContainer, String username) {
        initComponents();
        this.userProcessContainer = userProcessContainer;
        this.username = username;
        
        // Initialize panel layout
        ProcurementSpecialistWorkAreajPanel.setLayout(new CardLayout());
        jSplitPane2.setLeftComponent(ProcurementSpecialistPanel);
        jSplitPane2.setRightComponent(ProcurementSpecialistWorkAreajPanel);
        
        // Apply UI theme
        setupTheme();
    }
    
    /**
     * Apply consistent UI theme to all components
     */
    private void setupTheme() {
        // Set panel background color
        this.setBackground(new Color(240, 245, 255));
        ProcurementSpecialistPanel.setBackground(new Color(240, 245, 255));
        ProcurementSpecialistWorkAreajPanel.setBackground(new Color(240, 245, 255));
        
        // Style all buttons
        styleButton(btnMerchantRequest);
        styleButton(btnProfile1);
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
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(35, 100, 190)); // Lighter blue on hover
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(26, 79, 156)); // Back to normal
            }
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(13, 60, 130)); // Darker when pressed
            }
            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(35, 100, 190)); // Back to hover
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
    
    public void setEnterprise(Enterprise enterprise) {
        this.enterprise = enterprise;
    }
    
    public Enterprise getEnterprise() {
        return enterprise;
    }
    
    public void setAccount(UserAccount account) {
        this.account = account;
    }
    
    public UserAccount getAccount() {
        return account;
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
        ProcurementSpecialistPanel = new javax.swing.JPanel();
        btnMerchantRequest = new javax.swing.JButton();
        btnProfile1 = new javax.swing.JButton();
        ProcurementSpecialistWorkAreajPanel = new javax.swing.JPanel();

        btnMerchantRequest.setText("Merchant Request");
        btnMerchantRequest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMerchantRequestActionPerformed(evt);
            }
        });

        btnProfile1.setText("My Profile");
        btnProfile1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProfile1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ProcurementSpecialistPanelLayout = new javax.swing.GroupLayout(ProcurementSpecialistPanel);
        ProcurementSpecialistPanel.setLayout(ProcurementSpecialistPanelLayout);
        ProcurementSpecialistPanelLayout.setHorizontalGroup(
            ProcurementSpecialistPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ProcurementSpecialistPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ProcurementSpecialistPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnMerchantRequest, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnProfile1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        ProcurementSpecialistPanelLayout.setVerticalGroup(
            ProcurementSpecialistPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ProcurementSpecialistPanelLayout.createSequentialGroup()
                .addGap(303, 303, 303)
                .addComponent(btnMerchantRequest, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(78, 78, 78)
                .addComponent(btnProfile1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(2411, Short.MAX_VALUE))
        );

        jSplitPane2.setLeftComponent(ProcurementSpecialistPanel);

        javax.swing.GroupLayout ProcurementSpecialistWorkAreajPanelLayout = new javax.swing.GroupLayout(ProcurementSpecialistWorkAreajPanel);
        ProcurementSpecialistWorkAreajPanel.setLayout(ProcurementSpecialistWorkAreajPanelLayout);
        ProcurementSpecialistWorkAreajPanelLayout.setHorizontalGroup(
            ProcurementSpecialistWorkAreajPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1410, Short.MAX_VALUE)
        );
        ProcurementSpecialistWorkAreajPanelLayout.setVerticalGroup(
            ProcurementSpecialistWorkAreajPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2876, Short.MAX_VALUE)
        );

        jSplitPane2.setRightComponent(ProcurementSpecialistWorkAreajPanel);

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

    private void btnMerchantRequestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMerchantRequestActionPerformed
        // Verify account is not null
        if (account == null) {
            // If account is somehow still null, try to look it up
            for (Business.UserAccount.UserAccount ua : Business.EcoSystem.getInstance().getUserAccountDirectory().getUserAccountList()) {
                if (ua.getUsername().equals(this.username)) {
                    account = ua;
                    break;
                }
            }
            
            if (account == null) {
                javax.swing.JOptionPane.showMessageDialog(this, 
                    "Unable to get user account information, please login again.", 
                    "Error", 
                    javax.swing.JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        
        // Create merchant requests panel
        MerchantRequestsJPanel warehouseRequestsJPanel = new MerchantRequestsJPanel(ProcurementSpecialistWorkAreajPanel, enterprise, account);
        ProcurementSpecialistWorkAreajPanel.add("WarehouseRequestsJPanel", warehouseRequestsJPanel);
        CardLayout layout = (CardLayout) ProcurementSpecialistWorkAreajPanel.getLayout();
        layout.next(ProcurementSpecialistWorkAreajPanel);    
    }//GEN-LAST:event_btnMerchantRequestActionPerformed

    private void btnProfile1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProfile1ActionPerformed
        // Use the class account variable instead of looking it up
        if (account == null) {
            // If account is somehow still null, try to look it up
            for (Business.UserAccount.UserAccount ua : Business.EcoSystem.getInstance().getUserAccountDirectory().getUserAccountList()) {
                if (ua.getUsername().equals(this.username)) {
                    account = ua;
                    break;
                }
            }
            
            // If still null after lookup, show error
            if (account == null) {
                javax.swing.JOptionPane.showMessageDialog(this, 
                        "Unable to get user information, please login again.", 
                        "Error", 
                        javax.swing.JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        
        // Create ManageOwnProfile panel, pass work area panel and user account
        ManageOwnProfile manageOwnProfile = new ManageOwnProfile(ProcurementSpecialistWorkAreajPanel, account);
        ProcurementSpecialistWorkAreajPanel.add("ManageOwnProfile", manageOwnProfile);
        
        // Use CardLayout to display the panel
        CardLayout layout = (CardLayout) ProcurementSpecialistWorkAreajPanel.getLayout();
        layout.next(ProcurementSpecialistWorkAreajPanel);
    }//GEN-LAST:event_btnProfile1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ProcurementSpecialistPanel;
    private javax.swing.JPanel ProcurementSpecialistWorkAreajPanel;
    private javax.swing.JButton btnMerchantRequest;
    private javax.swing.JButton btnProfile1;
    private javax.swing.JSplitPane jSplitPane2;
    // End of variables declaration//GEN-END:variables
}
