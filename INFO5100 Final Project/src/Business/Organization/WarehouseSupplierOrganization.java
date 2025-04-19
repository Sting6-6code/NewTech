package Business.Organization;

import java.util.ArrayList;

import Business.Role.Role;
import Business.Role.WarehouseManagerRole;

public class WarehouseSupplierOrganization extends Organization {
    public WarehouseSupplierOrganization() {
        super(Organization.Type.WarehouseSupplier.getValue());
    }

    @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(new WarehouseManagerRole());
        return roles;
    }   
}
