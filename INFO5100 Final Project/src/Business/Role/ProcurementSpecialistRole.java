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
public class ProcurementSpecialistRole extends Role {
    
    public ProcurementSpecialistRole(){
        
    }

    public JPanel createWorkArea(JPanel userProcessContainer, UserAccount account, Organization organization, Enterprise enterprise, EcoSystem business) {
        ui.ProcurementSpecialistRole.ProcurementSpecialistHP procurementSpecialistHP = new ui.ProcurementSpecialistRole.ProcurementSpecialistHP(userProcessContainer, account.getUsername());
        // Set enterprise and account for procurement specialist
        procurementSpecialistHP.setEnterprise(enterprise);
        procurementSpecialistHP.setAccount(account);
        return procurementSpecialistHP;
    }
    
    @Override
    public String toString() {
        return "Procurement Specialist";
    }
}