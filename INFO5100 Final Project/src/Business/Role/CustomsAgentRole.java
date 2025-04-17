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
        CustomsLiaisonOfficeHP customsHP = new CustomsLiaisonOfficeHP(userProcessContainer, account, enterprise, (CustomsLiaisonOrganization)organization);
        return customsHP;
    }
    
    
    @Override
    public String toString() { 
        return "Customs Liaison Officer";
    }
}
