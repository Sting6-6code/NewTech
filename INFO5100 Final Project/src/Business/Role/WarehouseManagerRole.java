/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Role;

import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Organization.Organization;
import Business.Organization.WarehouseSupplierOrganization;
import Business.UserAccount.UserAccount;
import Business.Warehouse.Warehouse;
import javax.swing.JPanel;
import ui.WarehouseManager.WarehouseHP;
import ui.WarehouseManager.WarehouseManagerHomePage;


/**
 *
 * @author yushe
 */
public class WarehouseManagerRole extends Role {

    public WarehouseManagerRole() {
    }

    @Override
    public JPanel createWorkArea(JPanel userProcessContainer, UserAccount account, 
            Organization organization, Enterprise enterprise, EcoSystem business) {
        return new WarehouseHP(account.getUsername());
        }

    }
