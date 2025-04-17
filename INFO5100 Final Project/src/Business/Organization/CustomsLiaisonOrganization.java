/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Organization;

import Business.Customs.TaxReturnDirectory;
import Business.Logistics.CustomsDeclarationDirectory;
import Business.Role.CustomsAgentRole;
import Business.Role.Role;
import java.util.ArrayList;

/**
 *
 * @author zhuchenyan
 */
public class CustomsLiaisonOrganization extends Organization {
    private TaxReturnDirectory taxReturnDirectory;
    private CustomsDeclarationDirectory customsDeclarationDirectory;
    
    public CustomsLiaisonOrganization() {
        super(Organization.Type.CustomsAgent.getValue());
        taxReturnDirectory = new TaxReturnDirectory();
        customsDeclarationDirectory = new CustomsDeclarationDirectory();
    }
    
    public TaxReturnDirectory getTaxReturnDirectory() {
        return taxReturnDirectory;
    }
    
    public CustomsDeclarationDirectory getCustomsDeclarationDirectory() {
        return customsDeclarationDirectory;
    }
    
    public void setCustomsDeclarationDirectory(CustomsDeclarationDirectory customsDeclarationDirectory) {
        this.customsDeclarationDirectory = customsDeclarationDirectory;
    }
    
    @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(new CustomsAgentRole());
        return roles;
    }
}
