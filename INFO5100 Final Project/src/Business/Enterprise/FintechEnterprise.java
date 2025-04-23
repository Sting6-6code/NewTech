/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Enterprise;

import Business.Role.FintechRole;
import Business.Role.Role;
import java.util.ArrayList;

/**
 *
 * @author alanshi
 */
public class FintechEnterprise extends Enterprise {

    public FintechEnterprise(String name) {
        super(name, EnterpriseType.LogisticsGroupEnterprise);
    }

    @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(new FintechRole());
        return roles;
    }

    

    
}
