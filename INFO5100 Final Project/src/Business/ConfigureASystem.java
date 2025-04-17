package Business;

import Business.Employee.Employee;
import Business.Role.CustomerServiceRepRole;
import Business.Role.CustomsAgentRole;
import Business.Role.LogisticsCoordinatorRole;
import Business.Role.MerchantRole;
import Business.Role.ProcurementSpecialistRole;
//import Business.Role.LabManagerRole;
import Business.Role.SystemAdminRole;
import Business.UserAccount.UserAccount;
import Business.Supplier.Supplier;
import Business.Product.Product;
import Business.Network.Network;
import Business.Enterprise.Enterprise;
import Business.Organization.Organization;

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
        Employee employee5 = system.getEmployeeDirectory().createEmployee("customsagent");
        Employee employee6 = system.getEmployeeDirectory().createEmployee("logistics");
        
        UserAccount ua = system.getUserAccountDirectory().createUserAccount("sysadmin", "sysadmin", employee1, new SystemAdminRole());
        UserAccount customerservice = system.getUserAccountDirectory().createUserAccount("c","****",employee2, new CustomerServiceRepRole());
        UserAccount merchant = system.getUserAccountDirectory().createUserAccount("m","****",employee3, new MerchantRole());
        UserAccount procurement = system.getUserAccountDirectory().createUserAccount("p","****",employee4, new ProcurementSpecialistRole());
        UserAccount customsagent = system.getUserAccountDirectory().createUserAccount("l","****", employee5, new CustomsAgentRole());
        UserAccount logistics = system.getUserAccountDirectory().createUserAccount("t","****", employee6, new LogisticsCoordinatorRole());
        
        
        // 
        Supplier techSupplier1 = new Supplier();
        
        techSupplier1.setSupplyName("TechGadgets Inc.");
        
        
        
//        // 创建几个示例产品
//        Product p1 = new Product("P001", "iPhone 14 Pro", 999.99, 50, 10);
//        Product p2 = new Product("P002", "Samsung Galaxy S22", 899.99, 45, 8);
//        Product p3 = new Product("P003", "MacBook Pro M2", 1999.99, 30, 5);
//        Product p4 = new Product("P004", "Dell XPS 15", 1599.99, 25, 5);
//        Product p5 = new Product("P005", "Sony WH-1000XM5", 349.99, 100, 20);
//        
//        
//        
//        //upshelf
//        p1.upShelf();
//        p2.upShelf();
//        p3.upShelf();
//        
//
//        
//        // 将产品添加到供应商目录
//        techSupplier1.addProduct(p1);
//        techSupplier1.addProduct(p2);
//        techSupplier1.addProduct(p3);
//        techSupplier1.addProduct(p4);
//        techSupplier1.addProduct(p5);

        
        


        



        // 将创建的示例供应商设置为MerchantRole的默认供应商
        MerchantRole.setDemoSupplier(techSupplier1);
        System.out.println("Demo supplier created with 5 products: " + techSupplier1.getSupplyName());

        
        Network network = system.createAndAddNetwork();
        network.setName("Main Network");
        
       
        
        return system;
    }
    
}
