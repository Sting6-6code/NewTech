package Business.Role;

import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Organization.Organization;
import Business.Organization.AdminOrganization;
import Business.UserAccount.UserAccount;
import javax.swing.JPanel;
import ui.WarehouseManager.WarehouseSupplierAdminHP;

/**
 * Role for managing the warehouse supplier enterprise
 */
public class WarehouseSupplierAdminRole extends AdminRole {
    
    @Override
    public JPanel createWorkArea(JPanel userProcessContainer, UserAccount account, 
            Organization organization, Enterprise enterprise, EcoSystem business) {
        // 检查organization是否为null或不是AdminOrganization类型
        AdminOrganization adminOrg;
        if (organization == null || !(organization instanceof AdminOrganization)) {
            // 创建新的AdminOrganization
            adminOrg = new AdminOrganization();
            System.out.println("WarehouseSupplierAdminRole: Creating new AdminOrganization");
        } else {
            adminOrg = (AdminOrganization)organization;
        }
        
        return new WarehouseSupplierAdminHP(userProcessContainer, account, enterprise, adminOrg, business);
    }
    
    @Override
    public String toString() {
        return "Warehouse Supplier Admin";
    }
} 