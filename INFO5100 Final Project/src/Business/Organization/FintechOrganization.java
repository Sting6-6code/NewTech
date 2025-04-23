/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Organization;


import Business.Logistics.ShipmentDirectory;
import Business.Order.OrderDirectory;
import Business.Role.FintechRole;
import Business.Role.Role;
import java.util.ArrayList;

/**
 *
 * @author yushe
 */
public class FintechOrganization extends Organization {
    
    private ShipmentDirectory sd;
    private OrderDirectory od;
    
    
    public FintechOrganization() {
        super(Type.Fintech.getValue());
    }
    
    @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roles = new ArrayList();
        roles.add(new FintechRole());
        return roles;
    }

    public void setSd(ShipmentDirectory sd) {
        this.sd = sd;
    }

    public void setOd(OrderDirectory od) {
        this.od = od;
    }
    
    
}
