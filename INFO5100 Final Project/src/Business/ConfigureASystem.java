package Business;

import Business.Employee.Employee;
import Business.Role.CustomerServiceRepRole;
import Business.Role.CustomsAgentRole;
import Business.Role.LogisticsCoordinatorRole;
import Business.Role.MerchantRole;
import Business.Role.ProcurementSpecialistRole;
import Business.Role.SystemAdminRole;
import Business.UserAccount.UserAccount;
import Business.Supplier.Supplier;
import Business.Network.Network;
import Business.Enterprise.Enterprise;
import Business.Enterprise.RetailCorpEnterprise;
import Business.Enterprise.LogisticsGroupEnterprise;
import Business.Organization.Organization;
import Business.Role.AdminRole;
import Business.Customer.ComplaintDirectory;
import Business.Customer.CustomerComplaint;
import Business.Logistics.Shipment;
import Business.Logistics.ShipmentDirectory;
import Business.Logistics.TrackingInfo;
import Business.Organization.CustomerExperienceOrganization;
import Business.Organization.LogisticsOrganization;
import java.util.Date;

/**
 *
 * @author rrheg
 */
public class ConfigureASystem {
    
    public static LogisticsOrganization logisticsOrg;
    
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
        Employee employee7 = system.getEmployeeDirectory().createEmployee("warehouse");
        Employee employee8 = system.getEmployeeDirectory().createEmployee("fintech");
        
        // Create user accounts
        UserAccount ua = system.getUserAccountDirectory().createUserAccount("sysadmin", "sysadmin", employee1, new SystemAdminRole());
        UserAccount customerservice = system.getUserAccountDirectory().createUserAccount("c","****",employee2, new CustomerServiceRepRole());
        UserAccount merchant = system.getUserAccountDirectory().createUserAccount("m","****",employee3, new MerchantRole());
        UserAccount procurement = system.getUserAccountDirectory().createUserAccount("p","****",employee4, new ProcurementSpecialistRole());
        UserAccount customsagent = system.getUserAccountDirectory().createUserAccount("l","****", employee5, new CustomsAgentRole());
        UserAccount logistics = system.getUserAccountDirectory().createUserAccount("t","****", employee6, new LogisticsCoordinatorRole());
        
        // Create supplier
        Supplier techSupplier1 = new Supplier();
        techSupplier1.setSupplyName("TechGadgets Inc.");
        MerchantRole.setDemoSupplier(techSupplier1);
        
        // Create network
        Network network = system.createAndAddNetwork();
        network.setName("Main Network");
        
        // Create retail enterprise
        Enterprise retailEnterprise = network.getEnterpriseDirectory().createAndAddEnterprise("RetailCorp", Enterprise.EnterpriseType.RetailCorpEnterprise);
        if (retailEnterprise == null) {
            retailEnterprise = new RetailCorpEnterprise("RetailCorp");
            network.getEnterpriseDirectory().getEnterpriseList().add(retailEnterprise);
        }
        
        // Create logistics enterprise
        Enterprise logisticsEnterprise = network.getEnterpriseDirectory().createAndAddEnterprise("LogisticsGroup", Enterprise.EnterpriseType.LogisticsGroupEnterprise);
        if (logisticsEnterprise == null) {
            logisticsEnterprise = new LogisticsGroupEnterprise("LogisticsGroup");
            network.getEnterpriseDirectory().getEnterpriseList().add(logisticsEnterprise);
        }
        
        // Create customer experience organization
        if (retailEnterprise != null && retailEnterprise.getOrganizationDirectory() != null) {
            CustomerExperienceOrganization customerExpOrg = (CustomerExperienceOrganization) retailEnterprise.getOrganizationDirectory().createOrganization(Organization.Type.CustomerExperience);
            
            if (customerExpOrg != null) {
                // Associate customer service account with the organization
                customerExpOrg.getUserAccountDirectory().getUserAccountList().add(customerservice);
                
                // Create demo complaints
                ComplaintDirectory complaintDirectory = customerExpOrg.getComplaintDirectory();
                if (complaintDirectory != null) {
                    createDemoComplaints(complaintDirectory);
                }
            }
        }
        
        // Create logistics organizations
        if (logisticsEnterprise != null) {
        try {
            // Create logistics organization
            Organization logOrg = logisticsEnterprise.getOrganizationDirectory().createOrganization(Organization.Type.Logistics);
            if (logOrg instanceof LogisticsOrganization) {
                logisticsOrg = (LogisticsOrganization) logOrg;
                
                // Debug info
                System.out.println("Successfully created LogisticsOrganization");
                
                // Associate user account
                logisticsOrg.getUserAccountDirectory().getUserAccountList().add(logistics);
                
                // Create sample shipments with extra validation
                if (logisticsOrg.getShipmentDirectory() != null) {
                    System.out.println("ShipmentDirectory exists, creating sample shipments");
                    createSampleShipments(logisticsOrg);
                    System.out.println("Created " + logisticsOrg.getShipmentDirectory().getShipments().size() + " sample shipments");
                } else {
                    System.out.println("ERROR: ShipmentDirectory is null");
                }
            } else {
                System.out.println("ERROR: Created organization is not LogisticsOrganization");
            }
        } catch (Exception e) {
            System.out.println("Error creating LogisticsOrganization: " + e.getMessage());
            e.printStackTrace();
        }
    
        }
        
        //debug
        // 在返回之前验证数据
        if (logisticsOrg != null && logisticsOrg.getShipmentDirectory() != null) {
            System.out.println("配置完成后的shipments数量: " + 
                logisticsOrg.getShipmentDirectory().getShipments().size());
        }
        
        return system;
    }
    
    // Method to access the logistics organization from anywhere in the application
    public static LogisticsOrganization getLogisticsOrganization() {
        return logisticsOrg;
    }
    
    // Create demo complaints
    private static void createDemoComplaints(ComplaintDirectory complaintDirectory) {
        // Product issues
        CustomerComplaint complaint1 = complaintDirectory.createComplaint("C001", "CUST001", "iPhone 14 Pro screen has scratches, requesting replacement");
        CustomerComplaint complaint2 = complaintDirectory.createComplaint("C002", "CUST002", "Samsung Galaxy S22 battery life is shorter than advertised");
        CustomerComplaint complaint3 = complaintDirectory.createComplaint("C003", "CUST001", "MacBook Pro overheating issues during normal use");
        
        // Logistics issues
        CustomerComplaint complaint4 = complaintDirectory.createComplaint("C004", "CUST003", "Package delayed by 5 days, affecting usage plans");
        CustomerComplaint complaint5 = complaintDirectory.createComplaint("C005", "CUST004", "Package damaged, requesting compensation");
        CustomerComplaint complaint6 = complaintDirectory.createComplaint("C006", "CUST002", "Unable to track package, no status updates");
        
        // Refund issues
        CustomerComplaint complaint7 = complaintDirectory.createComplaint("C007", "CUST005", "Refund not received after 10 days of request");
        CustomerComplaint complaint8 = complaintDirectory.createComplaint("C008", "CUST001", "Partial refund received, missing coupon amount");
        
        // Customer service issues
        CustomerComplaint complaint9 = complaintDirectory.createComplaint("C009", "CUST003", "Customer service rep was rude and refused to help");
        CustomerComplaint complaint10 = complaintDirectory.createComplaint("C010", "CUST005", "Multiple attempts to contact support with no response");
        
        // Set different statuses
        complaintDirectory.updateComplaintStatus("C001", "In Progress");
        complaintDirectory.updateComplaintStatus("C002", "In Progress");
        complaintDirectory.updateComplaintStatus("C003", "Resolved");
        complaintDirectory.updateComplaintStatus("C005", "In Progress");
        complaintDirectory.updateComplaintStatus("C006", "In Progress");
        complaintDirectory.updateComplaintStatus("C007", "Resolved");
        complaintDirectory.updateComplaintStatus("C009", "Resolved");
    }
    
//    private static void createSampleShipments(LogisticsOrganization logistics) {
//        // debug
//        System.out.println("\n=== Starting to create sample shipments ===");
//    System.out.println("LogisticsOrganization: " + (logistics != null ? "exists" : "null"));
//    if (logistics != null) {
//        System.out.println("ShipmentDirectory: " + (logistics.getShipmentDirectory() != null ? "exists" : "null"));
//    }
//    
//    if (logistics == null || logistics.getShipmentDirectory() == null) {
//        System.out.println("Error: Cannot create sample shipments - invalid organization");
//        return;
//    }
//    
//    ShipmentDirectory shipmentDir = logistics.getShipmentDirectory();
//    System.out.println("Initial number of shipments: " + shipmentDir.getShipments().size());
//    
//    try {
//        // Create sample shipments
//        System.out.println("\nCreating first shipment...");
//        Shipment shipment1 = new Shipment("SHP001", "TRK001");
//        setupDeliveredShipment(shipment1);
//        shipmentDir.getShipments().add(shipment1);
//        System.out.println("Shipment 1 created successfully:");
//        System.out.println("- ID: " + shipment1.getShipmentId());
//        System.out.println("- Status: " + shipment1.getShipmentStatus());
//        System.out.println("- Number of tracking records: " + shipment1.getTrackingHistory().size());
//        
//        System.out.println("\nCreating second shipment...");
//        Shipment shipment2 = new Shipment("SHP002", "TRK002");
//        setupInTransitShipment(shipment2);
//        shipmentDir.getShipments().add(shipment2);
//        System.out.println("Shipment 2 created successfully:");
//        System.out.println("- ID: " + shipment2.getShipmentId());
//        System.out.println("- Status: " + shipment2.getShipmentStatus());
//        System.out.println("- Number of tracking records: " + shipment2.getTrackingHistory().size());
//        
//        System.out.println("\nCreating third shipment...");
//        Shipment shipment3 = new Shipment("SHP003", "TRK003");
//        setupNewlyShippedShipment(shipment3);
//        shipmentDir.getShipments().add(shipment3);
//        System.out.println("Shipment 3 created successfully:");
//        System.out.println("- ID: " + shipment3.getShipmentId());
//        System.out.println("- Status: " + shipment3.getShipmentStatus());
//        System.out.println("- Number of tracking records: " + shipment3.getTrackingHistory().size());
//        
//        System.out.println("\n=== Sample shipments creation completed ===");
//        System.out.println("Final number of shipments: " + shipmentDir.getShipments().size());
//        
//    } catch (Exception e) {
//        System.out.println("Error occurred while creating sample shipments: " + e.getMessage());
//        e.printStackTrace();
//    }
//    }
    
    public static void createSampleShipments(LogisticsOrganization logistics) {
    if (logistics == null || logistics.getShipmentDirectory() == null) {
        System.out.println("Error: Cannot create sample shipments - invalid organization");
        return;
    }
    
    ShipmentDirectory shipmentDir = logistics.getShipmentDirectory();
    
    // 创建示例货件
    String[] destinations = {"New York", "Los Angeles", "Chicago", "Houston", "Phoenix"};
    String[] methods = {"Air Freight", "Sea Freight", "Ground"};
    String[] statuses = {
        Shipment.STATUS_PENDING,
        Shipment.STATUS_PROCESSING,
        Shipment.STATUS_SHIPPED,
        Shipment.STATUS_IN_TRANSIT,
        Shipment.STATUS_DELIVERING
    };
    
    for (int i = 1; i <= 10; i++) {
        String shipmentId = "SHP" + String.format("%03d", i);
        String trackingNumber = "TRK" + String.format("%03d", i);
        
        Shipment shipment = shipmentDir.createShipment(shipmentId, trackingNumber);
        
        // 设置基本信息
        shipment.setShipDate(new Date());
        shipment.setShippingMethod(methods[i % methods.length]);
        shipment.setOrigin("Shanghai Warehouse");
        shipment.setDestination(destinations[i % destinations.length]);
        shipment.setShipmentStatus(statuses[i % statuses.length]);
        shipment.setCurrentLocation("Shanghai Warehouse");
        
        // 设置订单相关信息
        shipment.setOrderId("ORD" + String.format("%03d", i));
        shipment.setProductName("Product " + i);
        shipment.setQuantity(i * 10);
        
        // 添加跟踪记录
        TrackingInfo trackInfo = new TrackingInfo();
        trackInfo.setShipmentId(shipmentId);
        trackInfo.setTimestamp(new Date());
        trackInfo.setLocation("Shanghai Warehouse");
        trackInfo.setDescription("Shipment created");
        trackInfo.setStatus("Completed");
        shipment.addTrackingInfo(trackInfo);
        
        System.out.println("Created sample shipment: " + shipmentId);
    }
}
    
    public static void setupDeliveredShipment(Shipment shipment) {
        try {
        // Set basic info
        shipment.setOrderId("ORD001");
        shipment.setProductName("iPhone 15");
        shipment.setQuantity(1);
        shipment.setShipDate(new Date(System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000)); // 7 days ago
        shipment.setShippingMethod("Express");
        shipment.setOrigin("Shanghai Warehouse");
        shipment.setDestination("Boston, MA");
        shipment.setCurrentLocation("Boston, MA");
        shipment.setSender("Apple Store Shanghai");
        shipment.setReceiver("John Smith");
        shipment.setWeight(0.5);
        
        // Add tracking records
        addTrackingInfo(shipment, -7, "Shanghai Warehouse", 31.2304, 121.4737,
                "Package received at warehouse", Shipment.STATUS_PENDING);
        
        addTrackingInfo(shipment, -6, "Shanghai International Airport", 31.1443, 121.8083,
                "Package departed from Shanghai", Shipment.STATUS_SHIPPED);
        
        addTrackingInfo(shipment, -4, "Los Angeles International Airport", 33.9416, -118.4085,
                "Package arrived at transit point", Shipment.STATUS_IN_TRANSIT);
        
        addTrackingInfo(shipment, -2, "Boston Logan Airport", 42.3656, -71.0096,
                "Package arrived at destination city", Shipment.STATUS_IN_TRANSIT);
        
        addTrackingInfo(shipment, -1, "Boston Local Facility", 42.3601, -71.0589,
                "Out for delivery", Shipment.STATUS_DELIVERING);
        
        addTrackingInfo(shipment, 0, "Boston, MA", 42.3601, -71.0589,
                "Package delivered", Shipment.STATUS_DELIVERED);
                
        System.out.println("Successfully set up delivered shipment with all information");
    } catch (Exception e) {
        System.out.println("Error occurred while setting up delivered shipment: " + e.getMessage());
        e.printStackTrace();
    }
    }
    
    public static void setLogisticsOrganization(LogisticsOrganization org) {
    logisticsOrg = org;
}
    
    public static void setupInTransitShipment(Shipment shipment) {
        // Set basic info
        shipment.setOrderId("ORD002");
        shipment.setProductName("MacBook Pro");
        shipment.setQuantity(1);
        shipment.setShipDate(new Date(System.currentTimeMillis() - 3 * 24 * 60 * 60 * 1000)); // 3 days ago
        shipment.setShippingMethod("Express");
        shipment.setOrigin("Shanghai Warehouse");
        shipment.setDestination("New York, NY");
        shipment.setCurrentLocation("Los Angeles, CA");
        shipment.setSender("Apple Store Shanghai");
        shipment.setReceiver("Jane Doe");
        shipment.setWeight(2.5);
        
        // Add tracking records
        addTrackingInfo(shipment, -3, "Shanghai Warehouse", 31.2304, 121.4737,
                "Package received at warehouse", Shipment.STATUS_PENDING);
        
        addTrackingInfo(shipment, -2, "Shanghai International Airport", 31.1443, 121.8083,
                "Package departed from Shanghai", Shipment.STATUS_SHIPPED);
        
        addTrackingInfo(shipment, 0, "Los Angeles International Airport", 33.9416, -118.4085,
                "Package in transit", Shipment.STATUS_IN_TRANSIT);
    }
    
    public static void setupNewlyShippedShipment(Shipment shipment) {
        // Set basic info
        shipment.setOrderId("ORD003");
        shipment.setProductName("iPad Pro");
        shipment.setQuantity(1);
        shipment.setShipDate(new Date()); // Today
        shipment.setShippingMethod("Standard");
        shipment.setOrigin("Shanghai Warehouse");
        shipment.setDestination("Chicago, IL");
        shipment.setCurrentLocation("Shanghai International Airport");
        shipment.setSender("Apple Store Shanghai");
        shipment.setReceiver("Bob Wilson");
        shipment.setWeight(1.0);
        
        // Add tracking records
        addTrackingInfo(shipment, 0, "Shanghai Warehouse", 31.2304, 121.4737,
                "Package received and processed", Shipment.STATUS_PENDING);
        
        addTrackingInfo(shipment, 0, "Shanghai International Airport", 31.1443, 121.8083,
                "Package ready for departure", Shipment.STATUS_SHIPPED);
    }
    
    public static void addTrackingInfo(Shipment shipment, int daysOffset, 
            String location, double latitude, double longitude, 
            String description, String status) {
        
        TrackingInfo tracking = new TrackingInfo();
        tracking.setShipmentId(shipment.getShipmentId());
        tracking.setTimestamp(new Date(System.currentTimeMillis() + daysOffset * 24 * 60 * 60 * 1000));
        tracking.setLocation(location);
        tracking.setLatitude(latitude);
        tracking.setLongitude(longitude);
        tracking.setDescription(description);
        tracking.setStatus(status);
        
        shipment.addTrackingInfo(tracking);
        shipment.setShipmentStatus(status);
    }
    
    
    
    
        
}
