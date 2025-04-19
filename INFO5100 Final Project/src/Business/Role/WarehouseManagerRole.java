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
import ui.WarehouseManager.WarehouseHP;


/**
 *
 * @author yushe
 */
public class WarehouseManagerRole extends Role {

    public WarehouseManagerRole() {
    }

    public JPanel createWorkArea(JPanel userProcessContainer, UserAccount account, Organization organization, Enterprise enterprise, EcoSystem business) {
        return new WarehouseManagerHomePage(
                userProcessContainer,
                ((WarehouseOrganization) organization).getWarehouse(),
                enterprise,
                account
        );
        ui.WarehouseManager.WarehouseHP warehouseHP = new ui.WarehouseManager.WarehouseHP(account.getUsername());
        return warehouseHP;
    }

}
