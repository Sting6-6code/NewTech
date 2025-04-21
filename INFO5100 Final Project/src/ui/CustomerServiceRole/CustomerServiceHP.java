/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui.CustomerServiceRole;


import Business.Supplier.Supplier;
import ui.CustomerServiceRole.ComplaintManagementJpanel;
//import ui.CustomerServiceRole.CustomerComplaintContent;
import ui.CustomerServiceRole.ManageOwnProfile;
import java.awt.CardLayout;
import Business.EcoSystem;
import javax.swing.JPanel;
import javax.swing.JOptionPane;

/**
 *
 * @author zhuchenyan
 */
public class CustomerServiceHP extends javax.swing.JPanel {

    /**
     * Creates new form LogisticsCoordinatorHP
     */
    private Supplier supplier;
    private String username;
    private Business.UserAccount.UserAccount account;
    
    public CustomerServiceHP() {
        initComponents();
        // Set CardLayout for ComplaintWorkAreajPanel
        ComplaintWorkAreajPanel.setLayout(new CardLayout());
        setupTheme();
        adjustPanelSize();
    }
    
    // Constructor with username
    public CustomerServiceHP(String username) {
        initComponents();
        this.username = username;
        // Set CardLayout for ComplaintWorkAreajPanel
        ComplaintWorkAreajPanel.setLayout(new CardLayout());
        setupTheme();
        adjustPanelSize();
    }
    
    /**
     * Adjust panel size to be more compact
     */
    private void adjustPanelSize() {
        // Set main panel preferred size
        this.setPreferredSize(new java.awt.Dimension(1000, 700));
        
        // Set right panel preferred size
        ComplaintWorkAreajPanel.setPreferredSize(new java.awt.Dimension(800, 700));
        
        // Set proper divider location
        jSplitPane1.setDividerLocation(180);
    }
    
    /**
     * Apply consistent UI theme to all components
     */
    private void setupTheme() {
        // Set background colors
        this.setBackground(new java.awt.Color(240, 245, 255));
        ComplaintPanel.setBackground(new java.awt.Color(26, 79, 156));
        ComplaintWorkAreajPanel.setBackground(new java.awt.Color(240, 245, 255));
        
        // Style buttons
        styleMenuButton(btnComplaintManagement);
        styleMenuButton(btnProfile);
        
        // Style the split pane
        jSplitPane1.setDividerSize(1);
        jSplitPane1.setDividerLocation(180);
        jSplitPane1.setBorder(null);
        
        // Add title to left panel
        javax.swing.JLabel titleLabel = new javax.swing.JLabel("Customer Service");
        titleLabel.setFont(new java.awt.Font("SansSerif", 1, 18));
        titleLabel.setForeground(java.awt.Color.WHITE);
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        
        // Recreate the layout for ComplaintPanel
        javax.swing.GroupLayout ComplaintPanelLayout = new javax.swing.GroupLayout(ComplaintPanel);
        ComplaintPanel.setLayout(ComplaintPanelLayout);
        ComplaintPanelLayout.setHorizontalGroup(
            ComplaintPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ComplaintPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ComplaintPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(titleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                    .addComponent(btnComplaintManagement, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnProfile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        ComplaintPanelLayout.setVerticalGroup(
            ComplaintPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ComplaintPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(titleLabel)
                .addGap(40, 40, 40)
                .addComponent(btnComplaintManagement, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(btnProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        
        // Update main layout to handle the proper size
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1000, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
        );
    }
    
    /**
     * Style menu buttons with consistent formatting and hover effects
     */
    private void styleMenuButton(javax.swing.JButton button) {
        button.setBackground(new java.awt.Color(51, 102, 153));
        button.setForeground(java.awt.Color.WHITE);
        button.setFont(new java.awt.Font("SansSerif", 1, 14));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        button.setIconTextGap(15);
        button.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 15, 10, 10));
        
        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (button.isEnabled()) {
                    button.setBackground(new java.awt.Color(0, 153, 204));
                }
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (button.isEnabled()) {
                    button.setBackground(new java.awt.Color(51, 102, 153));
                }
            }
        });
    }
    
    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
    
    public Supplier getSupplier() {
        return supplier;
    }
    
    public void setAccount(Business.UserAccount.UserAccount account) {
        this.account = account;
    }
    
    public Business.UserAccount.UserAccount getAccount() {
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

        jSplitPane1 = new javax.swing.JSplitPane();
        ComplaintPanel = new javax.swing.JPanel();
        btnComplaintManagement = new javax.swing.JButton();
        btnProfile = new javax.swing.JButton();
        ComplaintWorkAreajPanel = new javax.swing.JPanel();

        btnComplaintManagement.setText("Complaint Management");
        btnComplaintManagement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnComplaintManagementActionPerformed(evt);
            }
        });

        btnProfile.setText("My Profile");
        btnProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProfileActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ComplaintPanelLayout = new javax.swing.GroupLayout(ComplaintPanel);
        ComplaintPanel.setLayout(ComplaintPanelLayout);
        ComplaintPanelLayout.setHorizontalGroup(
            ComplaintPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ComplaintPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(ComplaintPanelLayout.createSequentialGroup()
                .addComponent(btnComplaintManagement)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        ComplaintPanelLayout.setVerticalGroup(
            ComplaintPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ComplaintPanelLayout.createSequentialGroup()
                .addGap(303, 303, 303)
                .addComponent(btnComplaintManagement)
                .addGap(98, 98, 98)
                .addComponent(btnProfile)
                .addContainerGap(2441, Short.MAX_VALUE))
        );

        ComplaintPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnComplaintManagement, btnProfile});

        jSplitPane1.setLeftComponent(ComplaintPanel);

        javax.swing.GroupLayout ComplaintWorkAreajPanelLayout = new javax.swing.GroupLayout(ComplaintWorkAreajPanel);
        ComplaintWorkAreajPanel.setLayout(ComplaintWorkAreajPanelLayout);
        ComplaintWorkAreajPanelLayout.setHorizontalGroup(
            ComplaintWorkAreajPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1397, Short.MAX_VALUE)
        );
        ComplaintWorkAreajPanelLayout.setVerticalGroup(
            ComplaintWorkAreajPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2888, Short.MAX_VALUE)
        );

        jSplitPane1.setRightComponent(ComplaintWorkAreajPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnComplaintManagementActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnComplaintManagementActionPerformed
        // TODO add your handling code here:
        ComplaintManagementJpanel complaintManagementJpanel = new ComplaintManagementJpanel(ComplaintWorkAreajPanel);
        ComplaintWorkAreajPanel.add("ComplaintManagementJPanel", complaintManagementJpanel);
        CardLayout layout = (CardLayout) ComplaintWorkAreajPanel.getLayout();
        layout.next(ComplaintWorkAreajPanel);    
    }//GEN-LAST:event_btnComplaintManagementActionPerformed

    private void btnProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProfileActionPerformed
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
        
        // Create ManageOwnProfile panel with account data
        ManageOwnProfile manageOwnProfile = new ManageOwnProfile(ComplaintWorkAreajPanel, account);
        ComplaintWorkAreajPanel.add("ManageOwnProfile", manageOwnProfile);
        CardLayout layout = (CardLayout) ComplaintWorkAreajPanel.getLayout();
        layout.next(ComplaintWorkAreajPanel);  
    }//GEN-LAST:event_btnProfileActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ComplaintPanel;
    private javax.swing.JPanel ComplaintWorkAreajPanel;
    private javax.swing.JButton btnComplaintManagement;
    private javax.swing.JButton btnProfile;
    private javax.swing.JSplitPane jSplitPane1;
    // End of variables declaration//GEN-END:variables
}
