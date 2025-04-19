package Business.Enterprise;

import java.util.ArrayList;

import Business.Role.Role;
import Business.Role.WarehouseManagerRole;

public class WarehouseSuplierEnterprise extends Enterprise {
    public WarehouseSuplierEnterprise(String name) {
        super(name, EnterpriseType.WarehouseSupplier);
    }

    @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(new WarehouseManagerRole());
        return roles;
    }
}