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
import ui.CustomerServiceRole.CustomerServiceHP;

/**
 *
 * @author wangsiting
 */
public abstract class Role {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public abstract JPanel createWorkArea(JPanel userProcessContainer, UserAccount account, 
                                          Organization organization, Enterprise enterprise, 
                                          EcoSystem business);
}
