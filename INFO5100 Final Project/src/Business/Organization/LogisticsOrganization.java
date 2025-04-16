/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Organization;

import Business.Logistics.ShipmentDirectory;
import Business.Logistics.TaskDirectory;
import Business.Role.LogisticsCoordinatorRole;
import Business.Role.Role;
import java.util.ArrayList;

/**
 *
 * @author zhuchenyan
 */
public class LogisticsOrganization extends Organization {
    
    private ShipmentDirectory shipmentDirectory;
    private TaskDirectory taskDirectory;
    
    public LogisticsOrganization() {
        super("Logistics Organization");
        shipmentDirectory = new ShipmentDirectory();
        taskDirectory = new TaskDirectory();
    }
    
    public ShipmentDirectory getShipmentDirectory() {
        return shipmentDirectory;
    }
    
    public void setShipmentDirectory(ShipmentDirectory shipmentDirectory) {
        this.shipmentDirectory = shipmentDirectory;
    }
    
    public TaskDirectory getTaskDirectory() {
        return taskDirectory;
    }
    
    public void setTaskDirectory(TaskDirectory taskDirectory) {
        this.taskDirectory = taskDirectory;
    }
    
    @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(new LogisticsCoordinatorRole());
        // Add other logistics roles if needed
        return roles;
    }
    
}
