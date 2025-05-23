/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui.CustomsAgentRole;

import Business.UserAccount.UserAccount;
import java.awt.CardLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import ui.AdminRole.*;

/**
 *
 * @author yushe
 */
public class ManageOwnProfile extends javax.swing.JPanel {

    private JPanel userProcessContainer;
    private UserAccount userAccount;

    /**
     * Creates new form ManageOwnProfile
     */
    public ManageOwnProfile() {
        initComponents();
    }

    public ManageOwnProfile(JPanel userProcessContainer, UserAccount account) {
        initComponents();
        this.userProcessContainer = userProcessContainer;
        this.userAccount = account;

        // Set initial field values
        populateUserInfo();

        // Disable fields initially
        setFieldsEditable(false);
        btnSave.setEnabled(false);
        
        // Apply consistent UI styling
        setupTheme();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnBack = new javax.swing.JButton();
        lblUN = new javax.swing.JLabel();
        lblPW = new javax.swing.JLabel();
        txtUN = new javax.swing.JTextField();
        txtPW = new javax.swing.JTextField();
        btnUpdate = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();

        btnBack.setText("<< Back");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        lblUN.setText("Username:");

        lblPW.setText("Password:");

        txtUN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUNActionPerformed(evt);
            }
        });

        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(69, 69, 69)
                        .addComponent(btnBack))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGap(146, 146, 146)
                            .addComponent(btnUpdate)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSave))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGap(134, 134, 134)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblUN)
                                .addComponent(lblPW))
                            .addGap(18, 18, 18)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtPW, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtUN, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(140, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {txtPW, txtUN});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(btnBack)
                .addGap(81, 81, 81)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUN)
                    .addComponent(txtUN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPW)
                    .addComponent(txtPW, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(77, 77, 77)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUpdate)
                    .addComponent(btnSave))
                .addContainerGap(87, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        // TODO add your handling code here:
        // Return to previous panel
        userProcessContainer.remove(this);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.previous(userProcessContainer);
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        // Enable fields for editing
        setFieldsEditable(true);
        btnUpdate.setEnabled(false);
        btnSave.setEnabled(true);
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        String username = txtUN.getText().trim();
        String password = txtPW.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter username and password",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (password.length() < 4) {
            JOptionPane.showMessageDialog(this, "Password must be at least 4 characters",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Update user account
        userAccount.setUsername(username);
        userAccount.setPassword(password);

        // Disable editing and update button states
        setFieldsEditable(false);
        btnUpdate.setEnabled(true);
        btnSave.setEnabled(false);

        JOptionPane.showMessageDialog(this, "Profile updated successfully",
                "Success", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnSaveActionPerformed

    private void txtUNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUNActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUNActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JLabel lblPW;
    private javax.swing.JLabel lblUN;
    private javax.swing.JTextField txtPW;
    private javax.swing.JTextField txtUN;
    // End of variables declaration//GEN-END:variables

    private void populateUserInfo() {
        if (userAccount != null) {
            txtUN.setText(userAccount.getUsername());
            txtPW.setText(userAccount.getPassword());
        }
    }

    private void setFieldsEditable(boolean editable) {
        txtUN.setEditable(editable);
        txtPW.setEditable(editable);
    }
    
    /**
     * Apply consistent UI theme to all components
     */
    private void setupTheme() {
        // Set panel background colors
        this.setBackground(new java.awt.Color(240, 245, 255));
        
        // Add a title label for the profile management
        javax.swing.JLabel titleLabel = new javax.swing.JLabel("Manage Profile");
        titleLabel.setFont(new java.awt.Font("SansSerif", 1, 24));
        titleLabel.setForeground(new java.awt.Color(0, 102, 153));
        
        // Create a profile panel with shadow border
        javax.swing.JPanel profilePanel = new javax.swing.JPanel();
        profilePanel.setBackground(new java.awt.Color(255, 255, 255));
        profilePanel.setBorder(createShadowBorder());
        
        // Style form labels
        styleFormLabel(lblUN);
        styleFormLabel(lblPW);
        
        // Style text fields
        styleTextField(txtUN);
        styleTextField(txtPW);
        
        // Style buttons
        styleButton(btnBack);
        styleButton(btnUpdate);
        styleButton(btnSave);
        
        // Make the update button stand out
        btnUpdate.setBackground(new java.awt.Color(0, 153, 204));
        btnSave.setBackground(new java.awt.Color(0, 153, 102));
        
        // Add the components to the profile panel
        javax.swing.GroupLayout profilePanelLayout = new javax.swing.GroupLayout(profilePanel);
        profilePanel.setLayout(profilePanelLayout);
        
        // Add all components to the main panel
        this.removeAll();
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnBack)
                    .addComponent(titleLabel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.CENTER, layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(profilePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50))
        );
        
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(btnBack)
                .addGap(20, 20, 20)
                .addComponent(titleLabel)
                .addGap(30, 30, 30)
                .addComponent(profilePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(100, Short.MAX_VALUE))
        );
        
        // Set up the profile panel layout
        profilePanelLayout.setHorizontalGroup(
            profilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(profilePanelLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(profilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(profilePanelLayout.createSequentialGroup()
                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(profilePanelLayout.createSequentialGroup()
                        .addGroup(profilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblUN)
                            .addComponent(lblPW))
                        .addGap(30, 30, 30)
                        .addGroup(profilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtUN, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                            .addComponent(txtPW))))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        
        profilePanelLayout.setVerticalGroup(
            profilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(profilePanelLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(profilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUN)
                    .addComponent(txtUN, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(profilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPW)
                    .addComponent(txtPW, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addGroup(profilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(50, Short.MAX_VALUE))
        );
    }
    
    /**
     * Create a shadow border for panels
     */
    private javax.swing.border.Border createShadowBorder() {
        return javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 235, 245), 1),
                javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }
    
    /**
     * Style form labels with consistent formatting
     */
    private void styleFormLabel(javax.swing.JLabel label) {
        label.setFont(new java.awt.Font("SansSerif", 0, 16));
        label.setForeground(new java.awt.Color(51, 51, 51));
    }
    
    /**
     * Style text fields with consistent formatting
     */
    private void styleTextField(javax.swing.JTextField textField) {
        textField.setFont(new java.awt.Font("SansSerif", 0, 14));
        textField.setBackground(new java.awt.Color(245, 245, 250));
        textField.setForeground(new java.awt.Color(13, 25, 51));
        textField.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(90, 141, 224), 1),
                javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
    }
    
    /**
     * Style buttons with consistent formatting and hover effects
     */
    private void styleButton(javax.swing.JButton button) {
        button.setBackground(new java.awt.Color(26, 79, 156));
        button.setForeground(java.awt.Color.WHITE);
        button.setFont(new java.awt.Font("SansSerif", 1, 14));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        
        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (button.isEnabled()) {
                    java.awt.Color originalColor = button.getBackground();
                    button.setBackground(originalColor.brighter());
                }
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (button.isEnabled()) {
                    if (button == btnUpdate) {
                        button.setBackground(new java.awt.Color(0, 153, 204));
                    } else if (button == btnSave) {
                        button.setBackground(new java.awt.Color(0, 153, 102));
                    } else {
                        button.setBackground(new java.awt.Color(26, 79, 156));
                    }
                }
            }
        });
    }
}
