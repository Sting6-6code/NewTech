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
    
//    @Override
//    public JPanel createWorkArea(JPanel userProcessContainer, UserAccount account, Organization organization, Enterprise enterprise, EcoSystem business) {
//        ui.CustomsAgentRole.CustomsLiaisonOfficeHP customsHP = new ui.CustomsAgentRole.CustomsLiaisonOfficeHP();
//        return customsHP;
//    }
    
    @Override
    public JPanel createWorkArea(JPanel userProcessContainer, UserAccount account, Organization organization, Enterprise enterprise, EcoSystem business) {
    if (organization == null || !(organization instanceof CustomsLiaisonOrganization)) {
        return new ui.CustomsAgentRole.CustomsLiaisonOfficeHP();
    } else {
        return new ui.CustomsAgentRole.CustomsLiaisonOfficeHP(userProcessContainer, account, enterprise, (CustomsLiaisonOrganization) organization);
    }
    } 
    
//    @Override
//    public JPanel createWorkArea(JPanel userProcessContainer, UserAccount account, Organization organization, Enterprise enterprise, EcoSystem business) {
//        if (organization instanceof CustomsLiaisonOrganization) {
//            return new CustomsLiaisonOfficeHP(userProcessContainer, account, enterprise, (CustomsLiaisonOrganization) organization);
//        }
//        throw new IllegalArgumentException("Invalid organization type for Customs Agent Role");
//    }
    
    @Override
    public String toString() { 
        return "Customs Liaison Officer";
    }
}
