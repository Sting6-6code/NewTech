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
public class SystemAdminRole extends Role{

    public SystemAdminRole() {
        
    }

    public JPanel createWorkArea(JPanel userProcessContainer, UserAccount account, Organization organization, Enterprise enterprise, EcoSystem business) {
        if (organization == null) {
            System.out.println("❌ SystemAdminRole received null Organization!");
            throw new IllegalArgumentException("SystemAdminRole requires a non-null Organization");
        }
        if (!(organization instanceof AdminOrganization)) {
            System.out.println("❌ SystemAdminRole received wrong Organization type: " + organization.getClass().getSimpleName());
            throw new IllegalArgumentException("SystemAdminRole requires an AdminOrganization");
        }
        AdminHP ahp = new ui.AdminRole.AdminHP(userProcessContainer, account, enterprise, organization, business);
        return ahp;
    }
    
}
