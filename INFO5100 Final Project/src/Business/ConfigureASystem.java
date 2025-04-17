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
            // Create logistics organization
            Organization logisticsOrg = logisticsEnterprise.getOrganizationDirectory().createOrganization(Organization.Type.Logistics);
            if (logisticsOrg instanceof LogisticsOrganization) {
                LogisticsOrganization logisticsOrganization = (LogisticsOrganization) logisticsOrg;
                createSampleShipments(logisticsOrganization);
            }
            
            // Create customs organization
            Organization customsOrg = logisticsEnterprise.getOrganizationDirectory().createOrganization(Organization.Type.CustomsAgent);
            if (customsOrg != null) {
                customsOrg.getUserAccountDirectory().getUserAccountList().add(customsagent);
            }
        }
        
        
        
        return system;
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
    
    private static void createSampleShipments(LogisticsOrganization logistics) {
        if (logistics == null || logistics.getShipmentDirectory() == null) {
            System.out.println("Error: Cannot create sample shipments - invalid organization");
            return;
        }
        
        ShipmentDirectory shipmentDir = logistics.getShipmentDirectory();
        
        // Create sample shipments
        Shipment shipment1 = shipmentDir.createShipment("SHP001", "TRK001");
        setupDeliveredShipment(shipment1);
        
        Shipment shipment2 = shipmentDir.createShipment("SHP002", "TRK002");
        setupInTransitShipment(shipment2);
        
        Shipment shipment3 = shipmentDir.createShipment("SHP003", "TRK003");
        setupNewlyShippedShipment(shipment3);
    }
    
    private static void setupDeliveredShipment(Shipment shipment) {
        // 设置基本信息
        shipment.setOrderId("ORD001");
        shipment.setProductName("iPhone 15");
        shipment.setQuantity(1);
        shipment.setShipDate(new Date(System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000)); // 7天前
        shipment.setShippingMethod("Express");
        shipment.setOrigin("Shanghai Warehouse");
        shipment.setDestination("Boston, MA");
        shipment.setCurrentLocation("Boston, MA");
        shipment.setSender("Apple Store Shanghai");
        shipment.setReceiver("John Smith");
        shipment.setWeight(0.5);
        
        // 添加跟踪记录
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
    }
    
    private static void setupInTransitShipment(Shipment shipment) {
        // 设置基本信息
        shipment.setOrderId("ORD002");
        shipment.setProductName("MacBook Pro");
        shipment.setQuantity(1);
        shipment.setShipDate(new Date(System.currentTimeMillis() - 3 * 24 * 60 * 60 * 1000)); // 3天前
        shipment.setShippingMethod("Express");
        shipment.setOrigin("Shanghai Warehouse");
        shipment.setDestination("New York, NY");
        shipment.setCurrentLocation("Los Angeles, CA");
        shipment.setSender("Apple Store Shanghai");
        shipment.setReceiver("Jane Doe");
        shipment.setWeight(2.5);
        
        // 添加跟踪记录
        addTrackingInfo(shipment, -3, "Shanghai Warehouse", 31.2304, 121.4737,
                "Package received at warehouse", Shipment.STATUS_PENDING);
        
        addTrackingInfo(shipment, -2, "Shanghai International Airport", 31.1443, 121.8083,
                "Package departed from Shanghai", Shipment.STATUS_SHIPPED);
        
        addTrackingInfo(shipment, 0, "Los Angeles International Airport", 33.9416, -118.4085,
                "Package in transit", Shipment.STATUS_IN_TRANSIT);
    }
    
    private static void setupNewlyShippedShipment(Shipment shipment) {
        // 设置基本信息
        shipment.setOrderId("ORD003");
        shipment.setProductName("iPad Pro");
        shipment.setQuantity(1);
        shipment.setShipDate(new Date()); // 今天
        shipment.setShippingMethod("Standard");
        shipment.setOrigin("Shanghai Warehouse");
        shipment.setDestination("Chicago, IL");
        shipment.setCurrentLocation("Shanghai International Airport");
        shipment.setSender("Apple Store Shanghai");
        shipment.setReceiver("Bob Wilson");
        shipment.setWeight(1.0);
        
        // 添加跟踪记录
        addTrackingInfo(shipment, 0, "Shanghai Warehouse", 31.2304, 121.4737,
                "Package received and processed", Shipment.STATUS_PENDING);
        
        addTrackingInfo(shipment, 0, "Shanghai International Airport", 31.1443, 121.8083,
                "Package ready for departure", Shipment.STATUS_SHIPPED);
    }
    
    private static void addTrackingInfo(Shipment shipment, int daysOffset, 
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
