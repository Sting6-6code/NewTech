/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Enterprise;

import Business.Role.CustomsAgentRole;
import Business.Role.LogisticsCoordinatorRole;
import Business.Role.LogisticsEnterpriseAdminRole;
import Business.Role.Role;
import java.util.ArrayList;

import java.util.ArrayList;
import Business.Role.Role;

/**
 *
 * @author zhuchenyan
 */
public class LogisticsGroupEnterprise extends Enterprise{
    
    public LogisticsGroupEnterprise(String name){
        super(name, EnterpriseType.LogisticsGroupEnterprise);
    }
    
    @Override
    public ArrayList<Role> getSupportedRole(){
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(new LogisticsCoordinatorRole());
        roles.add(new CustomsAgentRole());
        roles.add(new LogisticsEnterpriseAdminRole());
        return roles;
    }
}
