/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui.WarehouseManager;

import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Enterprise.WarehouseSupplierEnterprise;
import Business.Organization.AdminOrganization;
import Business.Organization.Organization;
import Business.UserAccount.UserAccount;
import Business.Warehouse.Warehouse;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import ui.AdminRole.AddNewUser;
import ui.AdminRole.AdminHP;
import ui.AdminRole.AdminManageOwnProfile;

/**
 *
 * @author sting
 */
public class WarehouseSupplierAdminHP extends AdminHP {

    private JPanel workArea;
    private UserAccount account;
    private Enterprise enterprise;
    private AdminOrganization adminOrg;
    private EcoSystem business;
    private WarehouseSupplierEnterprise warehouseEnterprise;
    private JLabel welcomeLabel;
    
    /**
     * Creates new form WarehouseSupplierAdminHP
     */
    public WarehouseSupplierAdminHP(JPanel jp, UserAccount ua, Enterprise e, AdminOrganization o, EcoSystem b) {
        super(jp, ua, e, o, b); // 调用父类构造函数
        
        this.workArea = jp;
        this.account = ua;
        this.enterprise = e;
        this.adminOrg = o;
        this.business = b;
        
        // Initialize warehouse enterprise
        if (e instanceof WarehouseSupplierEnterprise) {
            this.warehouseEnterprise = (WarehouseSupplierEnterprise) e;
        }
        
        // 添加自己的欢迎标签
        welcomeLabel = new JLabel("Welcome Warehouse Supplier Admin!");
        welcomeLabel.setFont(new Font("Segoe UI", 0, 24));
        this.add(welcomeLabel);
        
        // 添加仓库管理特定的功能按钮
        addWarehouseManagementButtons();
    }
    
    private void addWarehouseManagementButtons() {
        // 创建仓库管理按钮
        JButton btnManageInventory = new JButton("Manage Inventory");
        JButton btnManageProcurement = new JButton("Manage Procurement");
        
        // 设置按钮样式
        styleButton(btnManageInventory);
        styleButton(btnManageProcurement);
        
        // 添加事件监听
        btnManageInventory.addActionListener(evt -> btnManageInventoryActionPerformed(evt));
        btnManageProcurement.addActionListener(evt -> btnManageProcurementActionPerformed(evt));
        
        // 添加到面板
        // 注意：在实际使用中可能需要修改添加位置，这里仅作示例
        this.add(btnManageInventory);
        this.add(btnManageProcurement);
    }
    
    private void btnManageInventoryActionPerformed(java.awt.event.ActionEvent evt) {
        // 打开库存管理面板
        WarehouseManagerHomePage warehousePage = new WarehouseManagerHomePage(
                workArea, 
                warehouseEnterprise.getOrganizationDirectory().getOrganizationList()
                    .stream()
                    .filter(org -> org.getName().equals(Organization.Type.WarehouseSupplier.getValue()))
                    .findFirst()
                    .map(org -> ((Business.Organization.WarehouseSupplierOrganization) org).getWarehouse())
                    .orElse(Business.Warehouse.Warehouse.getInstance()),
                enterprise,
                account);
        
        workArea.add("WarehouseManagementPage", warehousePage);
        CardLayout layout = (CardLayout) workArea.getLayout();
        layout.show(workArea, "WarehouseManagementPage");
    }
    
    private void btnManageProcurementActionPerformed(java.awt.event.ActionEvent evt) {
        // 打开采购请求管理面板
        Warehouse warehouse = Business.Warehouse.Warehouse.getInstance();
        ProcurementRequestsJPanel procurementPanel = new ProcurementRequestsJPanel(workArea, warehouse);
        workArea.add("ProcurementRequestsPanel", procurementPanel);
        CardLayout layout = (CardLayout) workArea.getLayout();
        layout.show(workArea, "ProcurementRequestsPanel");
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
}
