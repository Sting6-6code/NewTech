/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Organization;

import Business.Role.AdminRole;
import Business.Role.Role;
import Business.UserAccount.UserAccountDirectory;
import java.util.ArrayList;

/**
 *
 * @author raunak
 */
public class AdminOrganization extends Organization{

    UserAccountDirectory uad;
    
    public AdminOrganization() {
        super(Type.Admin.getValue());
    }
    
    @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roles = new ArrayList();
        roles.add(new AdminRole());
        return roles;
    }

    public void setUad(UserAccountDirectory uad) {
        this.uad = uad;
    }
    
    
     
}
