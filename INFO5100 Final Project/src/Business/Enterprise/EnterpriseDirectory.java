/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Enterprise;

import Business.Organization.OrganizationDirectory;
import java.util.ArrayList;

/**
 *
 * @author MyPC1
 */
public class EnterpriseDirectory {
    private ArrayList<Enterprise> enterpriseList;
   

    public ArrayList<Enterprise> getEnterpriseList() {
        return enterpriseList;
    }

    public void setEnterpriseList(ArrayList<Enterprise> enterpriseList) {
        this.enterpriseList = enterpriseList;
    }
    
    public EnterpriseDirectory(){
        enterpriseList=new ArrayList<Enterprise>();
    }
    
    //Create enterprise
    public Enterprise createAndAddEnterprise(String name,Enterprise.EnterpriseType type){
        Enterprise enterprise = null;
        if(type==Enterprise.EnterpriseType.RetailCorpEnterprise){
            enterprise=new RetailCorpEnterprise(name);
            enterpriseList.add(enterprise);
        }
        else if(type == Enterprise.EnterpriseType.LogisticsGroupEnterprise){
            enterprise = new LogisticsGroupEnterprise(name);
            enterpriseList.add(enterprise);
        }
        else if (type == Enterprise.EnterpriseType.WarehouseSupplier) {
        enterprise = new WarehouseSupplierEnterprise(name);
        enterpriseList.add(enterprise);
        }
        else if (type == Enterprise.EnterpriseType.Fintech) {
            enterprise = new FintechEnterprise(name);
            enterpriseList.add(enterprise);
        }
        return enterprise;
    }
}
