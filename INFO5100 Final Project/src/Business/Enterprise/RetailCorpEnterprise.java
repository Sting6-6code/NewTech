/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Enterprise;

import Business.Role.Role;
import Business.Role.SystemAdminRole;
import java.util.ArrayList;

/**
 *
 * @author MyPC1
 */
public class RetailCorpEnterprise extends Enterprise {
    
    public RetailCorpEnterprise(String name){
        super(name,EnterpriseType.RetailCorpEnterprise);
    }
    @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(new SystemAdminRole());
        return roles;
    }
    
}
