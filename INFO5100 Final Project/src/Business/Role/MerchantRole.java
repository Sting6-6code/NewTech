/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Role;

import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Organization.Organization;
import Business.UserAccount.UserAccount;
import Business.Supplier.Supplier;
import javax.swing.JPanel;

/**
 *
 * @author wangsiting
 */
public class MerchantRole extends Role{
    
    // 添加静态供应商用于测试
    private static Supplier demoSupplier;
    private Supplier supplierInstance;
    
    public MerchantRole(){
        
    }
    
    // 设置和获取供应商的方法 - 静态方法
    public static void setDemoSupplier(Supplier supplier) {
        demoSupplier = supplier;
    }
    
    public static Supplier getDemoSupplier() {
        return demoSupplier;
    }
    
    // 实例方法 - 为特定角色设置供应商
    public void setSupplier(Supplier supplier) {
        this.supplierInstance = supplier;
    }
    
    public Supplier getSupplier() {
        return this.supplierInstance != null ? this.supplierInstance : demoSupplier;
    }

    public JPanel createWorkArea(JPanel userProcessContainer, UserAccount account, Organization organization, Enterprise enterprise, EcoSystem business) {
        System.out.println("Creating MerchantHP with username: " + 
            (account != null ? account.getUsername() : "null"));
            
        ui.MerchantRole.MerchantHP merchantHP = new ui.MerchantRole.MerchantHP(userProcessContainer, account.getUsername());
        
        // Set the account on the merchant homepage
        merchantHP.setAccount(account);
        
        // Initialize with the demo supplier if available
        if (demoSupplier != null) {
            merchantHP.setSupplier(demoSupplier);
        } else if (account != null && account.getEmployee() != null) {
            // Alternatively create a new supplier
            Supplier supplier = new Supplier();
            supplier.setSupplyName(account.getEmployee().getName() + "'s Supply");
            merchantHP.setSupplier(supplier);
            
            // Save it for future use
            demoSupplier = supplier;
        }
        
        return merchantHP;
    }
    
    @Override
    public String toString() {
        return "Merchant";
    }
}
