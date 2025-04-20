/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Organization;

import Business.Role.Role;
import Business.Role.WarehouseManagerRole;
import Business.Warehouse.Warehouse;
import java.util.ArrayList;

/**
 *
 * @author zhuchenyan
 */
public class WarehouseOrganization extends Organization {
    private Warehouse warehouse;
    
    public WarehouseOrganization() {
        super(Organization.Type.WarehouseSupplier.toString());  
        warehouse = Warehouse.getInstance();
    }
    
    @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(new WarehouseManagerRole());
        return roles;
    }
    
    public Warehouse getWarehouse() {
        return warehouse;
    }
}
