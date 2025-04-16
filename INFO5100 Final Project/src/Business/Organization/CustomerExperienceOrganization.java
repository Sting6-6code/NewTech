/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Organization;

import Business.Customer.ComplaintDirectory;
import Business.Role.CustomerServiceRepRole;
import Business.Role.Role;
import java.util.ArrayList;

/**
 *
 * @author wangsiting
 */
public class CustomerExperienceOrganization extends Organization{
    private ComplaintDirectory complaintDirectory;
    
    
    public CustomerExperienceOrganization() {
        super(Organization.Type.CustomerExperience.getValue());
        complaintDirectory = new ComplaintDirectory();
    }
    
    public ComplaintDirectory getComplaintDirectory() {
        return complaintDirectory;
    }

    public void setComplaintDirectory(ComplaintDirectory complaintDirectory) {
        this.complaintDirectory = complaintDirectory;
    }
    
@Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roles = new ArrayList();
        roles.add(new CustomerServiceRepRole());
        return roles;
    }
    
    
    

    
    
}
