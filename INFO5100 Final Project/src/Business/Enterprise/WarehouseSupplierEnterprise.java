package Business.Enterprise;

import Business.Organization.Organization;
import java.util.ArrayList;

import Business.Role.Role;
import Business.Role.WarehouseManagerRole;

public class WarehouseSupplierEnterprise extends Enterprise {
    public WarehouseSupplierEnterprise(String name) {
        super(name, EnterpriseType.WarehouseSupplier);
    }

    @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(new WarehouseManagerRole());
        return roles;
    }
}