/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Role;

import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Organization.Organization;
import Business.UserAccount.UserAccount;
import javax.swing.JPanel;
import ui.fintech.FintechHomepage;

/**
 *
 * @author yushe
 */
public class FintechRole extends Role {

    public FintechRole() {
    }
    
    @Override
    public JPanel createWorkArea(JPanel userProcessContainer, UserAccount account, Organization organization, Enterprise enterprise, EcoSystem business) {
//        if (organization == null || !(organization instanceof AdminOrganization)) {
//            AdminOrganization ao = new AdminOrganization();
//            return new ui.AdminRole.AdminHP(userProcessContainer, account, enterprise, ao, business);
//        }
        FintechHomepage fhp = new FintechHomepage(userProcessContainer, account, organization, enterprise, business);
        return fhp;
    }
    
    @Override
    public String toString() {
        return "Payment Team";
    }
}