package Business.Organization;

import java.util.ArrayList;

import Business.Role.Role;
import Business.Role.WarehouseManagerRole;
import Business.Warehouse.Warehouse;

public class WarehouseSupplierOrganization extends Organization {
    private Warehouse warehouse;
    
    public WarehouseSupplierOrganization() {
        super(Organization.Type.WarehouseSupplier.getValue());
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
