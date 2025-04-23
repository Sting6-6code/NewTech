/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Enterprise;

import Business.Role.Role;
import Business.Role.MerchantRole;
import Business.Role.CustomerServiceRepRole;

import Business.Role.RetailAdminRole;
import java.util.ArrayList;
import Business.Organization.ECommercePlatformOrganization;


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
        roles.add(new MerchantRole());
        roles.add(new CustomerServiceRepRole());
        

        roles.add(new RetailAdminRole());

        return roles;
    }
    
}
