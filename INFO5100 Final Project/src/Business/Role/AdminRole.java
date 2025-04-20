/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Role;

import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Organization.AdminOrganization;
import Business.Organization.Organization;
import Business.UserAccount.UserAccount;

import javax.swing.JPanel;
import ui.AdminRole.AdminHP;

/**
 *
 * @author wangsiting
 */
public class AdminRole extends Role {

    public AdminRole() {
    }
    
    @Override
    public JPanel createWorkArea(JPanel userProcessContainer, UserAccount account, Organization organization, Enterprise enterprise, EcoSystem business) {
        if (organization == null || !(organization instanceof AdminOrganization)) {
            AdminOrganization ao = new AdminOrganization();
            return new ui.AdminRole.AdminHP(userProcessContainer, account, enterprise, ao, business);
        }
        AdminHP ahp = new ui.AdminRole.AdminHP(userProcessContainer, account, enterprise, (AdminOrganization) organization, business);
        return ahp;
    }
}

