/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Role;

import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Organization.CustomsLiaisonOrganization;
import Business.Organization.Organization;
import Business.UserAccount.UserAccount;
import javax.swing.JPanel;
import ui.CustomsAgentRole.CustomsLiaisonOfficeHP;

/**
 *
 * @author zhuchenyan
 */
public class CustomsAgentRole extends Role {
    
    public CustomsAgentRole() {
    }
    
    @Override
    public JPanel createWorkArea(JPanel userProcessContainer, UserAccount account, Organization organization, Enterprise enterprise, EcoSystem business) {
    System.out.println("Creating customs work area...");
    try {
        ui.CustomsAgentRole.CustomsLiaisonOfficeHP customsHP = new ui.CustomsAgentRole.CustomsLiaisonOfficeHP();
        System.out.println("Customs work area created successfully");
        return customsHP;
    } catch (Exception e) {
        System.out.println("Error creating customs work area: " + e.getMessage());
        e.printStackTrace();
        // 返回一个基本面板作为回退
        return new JPanel();
    }
    }
    
    
    @Override
    public String toString() { 
        return "Customs Liaison Officer";
    }
}
