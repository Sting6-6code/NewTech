/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Role;

import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Enterprise.LogisticsGroupEnterprise;
import Business.Network.Network;
import Business.Organization.AdminOrganization;
import Business.Organization.Organization;
import Business.UserAccount.UserAccount;

import javax.swing.JPanel;
import ui.LogisticsRole.LogisticsAdminHP;

/**
 *
 * @author zhuchenyan
 */
public class LogisticsEnterpriseAdminRole extends AdminRole {
    public LogisticsEnterpriseAdminRole() {
        
    }
    
    @Override
    public JPanel createWorkArea(JPanel userProcessContainer, UserAccount account, Organization organization, Enterprise enterprise, EcoSystem business) {
        // 添加调试信息
        System.out.println("LogisticsEnterpriseAdminRole.createWorkArea被调用");
        System.out.println("Enterprise类型: " + (enterprise != null ? enterprise.getClass().getName() : "null"));
        System.out.println("Organization类型: " + (organization != null ? organization.getClass().getName() : "null"));
        
        // 如果企业为null，从系统中查找LogisticsGroupEnterprise
    LogisticsGroupEnterprise logisticsEnterprise = null;
    AdminOrganization adminOrg = null;
    
    if (enterprise == null || !(enterprise instanceof LogisticsGroupEnterprise)) {
        // 从系统中查找物流企业
        for (Network network : business.getNetworkList()) {
            for (Enterprise ent : network.getEnterpriseDirectory().getEnterpriseList()) {
                if (ent instanceof LogisticsGroupEnterprise) {
                    logisticsEnterprise = (LogisticsGroupEnterprise) ent;
                    
                    // 查找AdminOrganization
                    for (Organization org : logisticsEnterprise.getOrganizationDirectory().getOrganizationList()) {
                        if (org instanceof AdminOrganization) {
                            adminOrg = (AdminOrganization) org;
                            break;
                        }
                    }
                    break;
                }
            }
            if (logisticsEnterprise != null) break;
        }
    } else {
        logisticsEnterprise = (LogisticsGroupEnterprise) enterprise;
        // 查找AdminOrganization
        if (organization instanceof AdminOrganization) {
            adminOrg = (AdminOrganization) organization;
        } else {
            for (Organization org : logisticsEnterprise.getOrganizationDirectory().getOrganizationList()) {
                if (org instanceof AdminOrganization) {
                    adminOrg = (AdminOrganization) org;
                    break;
                }
            }
        }
    }
    
    // 如果仍然找不到AdminOrg，创建一个新的
    if (adminOrg == null) {
        adminOrg = new AdminOrganization();
    }
    
    if (logisticsEnterprise != null) {
        System.out.println("找到LogisticsGroupEnterprise，创建LogisticsAdminHP");
        return new LogisticsAdminHP(userProcessContainer, account, logisticsEnterprise, adminOrg, business);
    } else {
        System.out.println("未找到LogisticsGroupEnterprise，返回默认AdminHP");
        AdminRole adminRole = new AdminRole();
        return adminRole.createWorkArea(userProcessContainer, account, organization, enterprise, business);
    }
    }
    
    @Override
    public String toString() {
        return "Logistics Enterprise Admin";
    }
}
