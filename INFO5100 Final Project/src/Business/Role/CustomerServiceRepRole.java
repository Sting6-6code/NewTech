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

/**
 *
 * @author wangsiting
 */
public class CustomerServiceRepRole extends Role {

    public CustomerServiceRepRole() {
    }

    public JPanel createWorkArea(JPanel userProcessContainer, UserAccount account, Organization organization, Enterprise enterprise, EcoSystem business) {
        ui.CustomerServiceRole.CustomerServiceHP customerServiceHP = new ui.CustomerServiceRole.CustomerServiceHP();
        return customerServiceHP;
    }
    
    
    
}
