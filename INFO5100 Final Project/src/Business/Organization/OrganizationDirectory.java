/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Organization;

import Business.Organization.Organization.Type;
import java.util.ArrayList;

/**
 *
 * @author raunak
 */
public class OrganizationDirectory {
    
    private ArrayList<Organization> organizationList;

    public OrganizationDirectory() {
        organizationList = new ArrayList();
    }

    public ArrayList<Organization> getOrganizationList() {
        return organizationList;
    }
    
    public Organization createOrganization(Type type){
        Organization organization = null;
    if (type.getValue().equals(Type.CustomerExperience.getValue())){
        organization = new CustomerExperienceOrganization();
        organizationList.add(organization);
    }
    else if (type.getValue().equals(Type.Ecommerce.getValue())){
        organization = new ECommercePlatformOrganization();
        organizationList.add(organization);
    }
    else if (type.getValue().equals(Type.Logistics.getValue())){
        organization = new LogisticsOrganization();
        organizationList.add(organization);
        System.out.println("Created LogisticsOrganization from factory method");
    }
    else if (type.getValue().equals(Type.CustomsAgent.getValue())){
        organization = new CustomsLiaisonOrganization();
        organizationList.add(organization);
    }
    else if (type.getValue().equals(Type.Warehouse.getValue())){
        organization = new WarehouseOrganization();
        organizationList.add(organization);
    }
    return organization;
    }
}