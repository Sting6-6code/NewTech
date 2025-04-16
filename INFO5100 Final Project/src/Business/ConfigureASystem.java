package Business;

import Business.Employee.Employee;
import Business.Role.CustomerServiceRepRole;
import Business.Role.MerchantRole;
import Business.Role.ProcurementSpecialistRole;
//import Business.Role.LabManagerRole;
import Business.Role.SystemAdminRole;
import Business.UserAccount.UserAccount;

/**
 *
 * @author rrheg
 */
public class ConfigureASystem {
    
    public static EcoSystem configure(){
        
        EcoSystem system = EcoSystem.getInstance();
        
        //Create a network
        //create an enterprise
        //initialize some organizations
        //have some employees 
        //create user account
        
        
        Employee employee1 = system.getEmployeeDirectory().createEmployee("sysadmin");
        Employee employee2 = system.getEmployeeDirectory().createEmployee("customerservice");
        Employee employee3 = system.getEmployeeDirectory().createEmployee("merchant");
        Employee employee4 = system.getEmployeeDirectory().createEmployee("procurement");
        
        UserAccount ua = system.getUserAccountDirectory().createUserAccount("sysadmin", "sysadmin", employee1, new SystemAdminRole());
//      UserAccount labManager = system.getUserAccountDirectory().createUserAccount("labManager", "sysadmin", employee, new LabManagerRole());
        UserAccount customerservice = system.getUserAccountDirectory().createUserAccount("c","****",employee2, new CustomerServiceRepRole());
        UserAccount merchant = system.getUserAccountDirectory().createUserAccount("m","****",employee3, new MerchantRole());
        UserAccount procurement = system.getUserAccountDirectory().createUserAccount("p","****",employee4, new ProcurementSpecialistRole());
        

        return system;
    }
    
}
