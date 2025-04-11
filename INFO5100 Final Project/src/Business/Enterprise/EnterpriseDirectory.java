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
        Enterprise enterprise=null;
<<<<<<< HEAD
        if(type==Enterprise.EnterpriseType.Hospital){
//            enterprise=new HospitalEnterprise(name);
            enterpriseList.add(enterprise);
        }
=======
//        if(type==Enterprise.EnterpriseType.Hospital){
//            enterprise=new HospitalEnterprise(name);
//            enterpriseList.add(enterprise);
//        }
>>>>>>> f6192ac37608c4cc926617e1b37a663c2456afd2
        return enterprise;
    }
}
