package Business;

import Business.Employee.Employee;
import Business.Role.CustomerServiceRepRole;
import Business.Role.CustomsAgentRole;
import Business.Role.LogisticsCoordinatorRole;
import Business.Role.MerchantRole;
import Business.Role.ProcurementSpecialistRole;
import Business.Role.SystemAdminRole;
import Business.Role.WarehouseManagerRole;
import Business.Role.FintechRole;
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
import Business.DB4OUtil.DB4OUtil;
import Business.Enterprise.WarehouseSupplierEnterprise;
import Business.Logistics.CustomsDeclaration;
import Business.Logistics.CustomsDeclarationDirectory;
import Business.Logistics.Shipment;
import Business.Logistics.ShipmentDirectory;
import Business.Logistics.TrackingInfo;
import Business.Organization.CustomerExperienceOrganization;
import Business.Organization.CustomsLiaisonOrganization;
import Business.Organization.LogisticsOrganization;
import Business.Product.Product;
import Business.Warehouse.Warehouse;
import Business.Warehouse.Stock;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;
import Business.Product.SalesRecord;
import Business.Role.LogisticsEnterpriseAdminRole;
import Business.Role.RetailAdminRole;
import Business.WorkQueue.LogisticsWorkRequest;
import Business.WorkQueue.WorkRequest;
import ui.MerchantRole.RetailAdminHP;
import Business.Role.WarehouseSupplierAdminRole;


/**
 *
 * @author 
 */
public class ConfigureASystem {

    public static LogisticsOrganization logisticsOrg;

    public static EcoSystem configure() {

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
        Employee employee8 = system.getEmployeeDirectory().createEmployee("FinTechManager");
        Employee employee9 = system.getEmployeeDirectory().createEmployee("LogisticsAdmin");
        Employee employee10 = system.getEmployeeDirectory().createEmployee("RetailAdmin");
        Employee employee11 = system.getEmployeeDirectory().createEmployee("WarehouseAdmin");
        // Create user accounts
        UserAccount ua = system.getUserAccountDirectory().createUserAccount("sysadmin", "sysadmin", employee1, new SystemAdminRole());
        UserAccount customerservice = system.getUserAccountDirectory().createUserAccount("c", "****", employee2, new CustomerServiceRepRole());
        UserAccount merchant = system.getUserAccountDirectory().createUserAccount("m", "****", employee3, new MerchantRole());
        UserAccount procurement = system.getUserAccountDirectory().createUserAccount("p", "****", employee4, new ProcurementSpecialistRole());
        UserAccount customsagent = system.getUserAccountDirectory().createUserAccount("l", "****", employee5, new CustomsAgentRole());
        UserAccount logistics = system.getUserAccountDirectory().createUserAccount("t", "****", employee6, new LogisticsCoordinatorRole());
        UserAccount warehouse = system.getUserAccountDirectory().createUserAccount("w", "****", employee7, new WarehouseManagerRole());
        UserAccount fintech = system.getUserAccountDirectory().createUserAccount("pay", "****", employee8, new FintechRole());
        UserAccount logisticsAdmin = system.getUserAccountDirectory().createUserAccount("la", "****", employee9, new LogisticsEnterpriseAdminRole());
        UserAccount retailAdmin = system.getUserAccountDirectory().createUserAccount("ra", "****", employee10, new RetailAdminRole());
        UserAccount warehouseAdmin = system.getUserAccountDirectory().createUserAccount("wa", "****", employee11, new WarehouseSupplierAdminRole());

        // 初始化仓库和商品
        initializeWarehouse();

        // Initialize sample sales records
        initializeSalesRecords(system);

        // Create supplier and assign to merchant role
        Supplier techSupplier1 = new Supplier();
        techSupplier1.setSupplyName("TechGadgets Inc.");

        // Set merchant's demo supplier
        MerchantRole merchantRole = (MerchantRole) merchant.getRole();
        merchantRole.setSupplier(techSupplier1);

        // Also set the static version for backward compatibility
        MerchantRole.setDemoSupplier(techSupplier1);

        // 确保仓库中的所有产品都设置为上架状态
        Warehouse warehouseInstance = Warehouse.getInstance();

        try {
            System.out.println("正在为商家设置示例产品目录...");

            // 获取仓库中的所有产品
            List<Product> allProducts = new ArrayList<>();
            for (Stock stockItem : warehouseInstance.getStock()) {
                Product p = stockItem.getProduct();
                p.upShelf(); // 确保产品上架
                allProducts.add(p);
            }

            System.out.println("仓库中共有 " + allProducts.size() + " 个产品");

            // 确保有足够的产品添加到商家目录
            int productsToAdd = Math.min(10, allProducts.size());
            System.out.println("将添加 " + productsToAdd + " 个产品到商家目录");

            // 向商家目录添加产品
            for (int i = 0; i < productsToAdd; i++) {
                Product warehouseProduct = allProducts.get(i);

                // 克隆产品到商家目录，避免影响仓库库存
                Product merchantProduct = new Product(
                        warehouseProduct.getProductId(),
                        warehouseProduct.getProductName(),
                        warehouseProduct.getPrice(),
                        20, // 设置初始库存
                        10 // 设置警告阈值
                );
                merchantProduct.upShelf(); // 确保产品上架
                techSupplier1.addProduct(merchantProduct);

                System.out.println("已添加产品: " + merchantProduct.getProductName() + " 到商家目录");
            }

            // 添加一些低库存产品作为示例
            if (allProducts.size() > productsToAdd) {
                for (int i = productsToAdd; i < Math.min(productsToAdd + 3, allProducts.size()); i++) {
                    Product warehouseProduct = allProducts.get(i);

                    Product lowStockProduct = new Product(
                            warehouseProduct.getProductId(),
                            warehouseProduct.getProductName(),
                            warehouseProduct.getPrice(),
                            5, // 低库存
                            15 // 警告阈值大于库存量，触发低库存状态
                    );
                    lowStockProduct.upShelf();
                    lowStockProduct.setStockStatus("Low"); // 明确设置为低库存状态
                    techSupplier1.addProduct(lowStockProduct);

                    System.out.println("已添加低库存产品: " + lowStockProduct.getProductName() + " 到商家目录");
                }
            }

            System.out.println("商家目录设置完成，共 " + techSupplier1.getProductCatalog().size() + " 个产品");

        } catch (Exception e) {
            System.err.println("设置商家目录时出错: " + e.getMessage());
            e.printStackTrace();
        }

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

        // 创建仓库企业
        WarehouseSupplierEnterprise warehouseEnterprise = (WarehouseSupplierEnterprise) network.getEnterpriseDirectory()
                .createAndAddEnterprise("Warehouse Enterprise", Enterprise.EnterpriseType.WarehouseSupplier);

        // 创建仓库组织
        Organization warehouseOrg = warehouseEnterprise.getOrganizationDirectory()
                .createOrganization(Organization.Type.WarehouseSupplier);

        // 将仓库用户账户添加到仓库组织
        warehouseOrg.getUserAccountDirectory().getUserAccountList().add(warehouse);

        // 初始化仓库和商品
        initializeWarehouse();

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

                    // Create sample customs declarations
                    if (logisticsOrg.getCustomsDeclarationDirectory() != null) {
                        System.out.println("CustomsDeclarationDirectory exists, creating sample declarations");
                        createSampleCustomsDeclarations(logisticsOrg);
                    } else {
                        System.out.println("ERROR: CustomsDeclarationDirectory is null");
                    }
                } else {
                    System.out.println("ERROR: Created organization is not LogisticsOrganization");
                }
            } catch (Exception e) {
                System.out.println("Error creating LogisticsOrganization: " + e.getMessage());
                e.printStackTrace();
            }

            // 创建Logistics Admin.在创建和配置物流企业后，确保物流管理员与物流企业关联
            if (logisticsEnterprise != null) {
                // 创建Admin组织
                Organization adminOrg = logisticsEnterprise.getOrganizationDirectory()
                        .createOrganization(Organization.Type.Admin);

                // 将物流管理员账户添加到管理组织
                if (adminOrg != null) {
                    adminOrg.getUserAccountDirectory().getUserAccountList().add(logisticsAdmin);
                    System.out.println("物流管理员已添加到Admin组织");
                }else {
                    System.out.println("错误: 无法创建Admin组织");
                }
            }else {
                System.out.println("错误: 物流企业为null");
            }

            // Create customs liaison organization
            if (logisticsEnterprise != null) {
                try {
                    // Create customs liaison organization
                    Organization customsOrg = logisticsEnterprise.getOrganizationDirectory().createOrganization(Organization.Type.CustomsAgent);
                    if (customsOrg instanceof CustomsLiaisonOrganization) {
                        CustomsLiaisonOrganization customsLiaisonOrg = (CustomsLiaisonOrganization) customsOrg;

                        // Debug info
                        System.out.println("Successfully created CustomsLiaisonOrganization");

                        // Associate user account
                        customsLiaisonOrg.getUserAccountDirectory().getUserAccountList().add(customsagent);

                        // 创建示例海关数据
                        createSampleCustomsDeclarations(customsLiaisonOrg);
                        System.out.println("Created sample customs declarations");

                        // Use customs declarations from logistics organization if needed
                        if (logisticsOrg != null && logisticsOrg.getCustomsDeclarationDirectory() != null) {
                            customsLiaisonOrg.setCustomsDeclarationDirectory(logisticsOrg.getCustomsDeclarationDirectory());
                            System.out.println("Shared customs declarations with customs liaison organization");

                            createWorkRequestsForCustomsDeclarations(customsLiaisonOrg);
                        }
                    } else {
                        System.out.println("ERROR: Created organization is not CustomsLiaisonOrganization");
                    }
                } catch (Exception e) {
                    System.out.println("Error creating CustomsLiaisonOrganization: " + e.getMessage());
                    e.printStackTrace();
                }
            }

        }

        //debug
        // 在返回之前验证数据
        if (logisticsOrg != null && logisticsOrg.getShipmentDirectory() != null) {
            System.out.println("配置完成后的shipments数量: "
                    + logisticsOrg.getShipmentDirectory().getShipments().size());
        }

        // 在其他初始化方法之后添加
        initializeProductsWithFaker();

        return system;
    }

    // Method to access the logistics organization from anywhere in the application
    public static LogisticsOrganization getLogisticsOrganization() {
        return logisticsOrg;
    }

    private static void initializeWarehouse() {
        Warehouse warehouse = Warehouse.getInstance();

        // 创建示例产品并添加到仓库
        // 智能手机
        Product p1 = new Product("SP-001", "Apple iPhone 15 Pro", 799.99, 50, 20);
        Product p2 = new Product("SP-002", "Samsung Galaxy S23", 999.99, 40, 15);
        Product p3 = new Product("SP-003", "Google Pixel 7", 599.99, 60, 20);

        // 笔记本电脑
        Product p4 = new Product("LP-001", "MacBook Pro 14\"", 1299.99, 30, 10);
        Product p5 = new Product("LP-002", "Dell XPS 15", 1599.99, 25, 10);
        Product p6 = new Product("LP-003", "Lenovo ThinkPad X1", 899.99, 45, 15);

        // 耳机
        Product p7 = new Product("HP-001", "Apple AirPods Pro", 199.99, 70, 25);
        Product p8 = new Product("HP-002", "Sony WH-1000XM5", 299.99, 60, 20);

        // 智能手表
        Product p9 = new Product("SW-001", "Apple Watch Series 8", 349.99, 55, 20);
        Product p10 = new Product("SW-002", "Samsung Galaxy Watch 5", 249.99, 65, 25);

        // 平板电脑
        Product p11 = new Product("TB-001", "iPad Pro 12.9\"", 499.99, 40, 15);
        Product p12 = new Product("TB-002", "Samsung Galaxy Tab S8", 349.99, 50, 20);

        // 相机
        Product p13 = new Product("CM-001", "Canon EOS R6", 699.99, 35, 15);
        Product p14 = new Product("CM-002", "Sony Alpha a7 IV", 449.99, 45, 20);

        // 音箱
        Product p15 = new Product("SK-001", "Sonos One", 149.99, 80, 30);
        Product p16 = new Product("SK-002", "Bose SoundLink Revolve+", 249.99, 70, 25);

        // 充电宝
        Product p17 = new Product("PB-001", "Anker PowerCore 26800", 49.99, 100, 40);
        Product p18 = new Product("PB-002", "RAVPower 20000mAh", 79.99, 90, 35);

        // 显示器
        Product p19 = new Product("MN-001", "LG UltraGear 27\"", 299.99, 40, 15);
        Product p20 = new Product("MN-002", "Dell Alienware 34\" Curved", 399.99, 35, 15);

        // 键盘
        Product p21 = new Product("KB-001", "Logitech MX Keys", 89.99, 75, 30);
        Product p22 = new Product("KB-002", "Corsair K95 RGB Platinum", 129.99, 65, 25);

        // 将所有产品添加到仓库
        // 每种产品添加适量库存
        warehouse.addStock(p1, 50);
        warehouse.addStock(p2, 40);
        warehouse.addStock(p3, 60);
        warehouse.addStock(p4, 30);
        warehouse.addStock(p5, 25);
        warehouse.addStock(p6, 45);
        warehouse.addStock(p7, 70);
        warehouse.addStock(p8, 60);
        warehouse.addStock(p9, 55);
        warehouse.addStock(p10, 65);
        warehouse.addStock(p11, 40);
        warehouse.addStock(p12, 50);
        warehouse.addStock(p13, 35);
        warehouse.addStock(p14, 45);
        warehouse.addStock(p15, 80);
        warehouse.addStock(p16, 70);
        warehouse.addStock(p17, 100);
        warehouse.addStock(p18, 90);
        warehouse.addStock(p19, 40);
        warehouse.addStock(p20, 35);
        warehouse.addStock(p21, 75);
        warehouse.addStock(p22, 65);

        // 立即执行一次库存检查，生成初始采购请求
        warehouse.checkAllInventoryLevels();

        // 开始定期库存检查（每120秒检查一次）
        warehouse.startPeriodicInventoryCheck(120);

        // 确保所有产品设置为上架状态
        p1.upShelf();
        p2.upShelf();
        p3.upShelf();
        p4.upShelf();
        p5.upShelf();
        p6.upShelf();
        p7.upShelf();
        p8.upShelf();
        p9.upShelf();
        p10.upShelf();
        p11.upShelf();
        p12.upShelf();
        p13.upShelf();
        p14.upShelf();
        p15.upShelf();
        p16.upShelf();
        p17.upShelf();
        p18.upShelf();
        p19.upShelf();
        p20.upShelf();
        p21.upShelf();
        p22.upShelf();

        System.out.println("Warehouse initialized with " + warehouse.getStock().size() + " products");
        System.out.println("Generated " + warehouse.getProcurementRequests().size() + " procurement requests");

    }

    // Method to initialize sample sales record data
    private static void initializeSalesRecords(EcoSystem system) {
        System.out.println("Initializing sample sales records...");

        if (system == null || system.getSalesRecordDirectory() == null) {
            System.out.println("Warning: Cannot initialize sales records - system or sales record directory is null");
            return;
        }

        // Get current date for reference
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();

        // Create dates for previous months
        calendar.add(Calendar.MONTH, -1);
        Date oneMonthAgo = calendar.getTime();

        calendar.add(Calendar.MONTH, -1);
        Date twoMonthsAgo = calendar.getTime();

        calendar.add(Calendar.MONTH, -1);
        Date threeMonthsAgo = calendar.getTime();

        // Add sample sales records
        system.getSalesRecordDirectory().createSalesRecord("SP-001", "Apple iPhone 15 Pro", 799.99, 5, threeMonthsAgo, "CUST001");
        system.getSalesRecordDirectory().createSalesRecord("SP-001", "Apple iPhone 15 Pro", 799.99, 8, twoMonthsAgo, "CUST002");
        system.getSalesRecordDirectory().createSalesRecord("SP-001", "Apple iPhone 15 Pro", 799.99, 12, oneMonthAgo, "CUST003");
        system.getSalesRecordDirectory().createSalesRecord("SP-001", "Apple iPhone 15 Pro", 799.99, 10, currentDate, "CUST004");

        system.getSalesRecordDirectory().createSalesRecord("SP-002", "Samsung Galaxy S23", 999.99, 3, threeMonthsAgo, "CUST005");
        system.getSalesRecordDirectory().createSalesRecord("SP-002", "Samsung Galaxy S23", 999.99, 7, twoMonthsAgo, "CUST006");
        system.getSalesRecordDirectory().createSalesRecord("SP-002", "Samsung Galaxy S23", 999.99, 9, oneMonthAgo, "CUST007");
        system.getSalesRecordDirectory().createSalesRecord("SP-002", "Samsung Galaxy S23", 999.99, 6, currentDate, "CUST008");

        system.getSalesRecordDirectory().createSalesRecord("LP-001", "MacBook Pro 14\"", 1299.99, 2, threeMonthsAgo, "CUST009");
        system.getSalesRecordDirectory().createSalesRecord("LP-001", "MacBook Pro 14\"", 1299.99, 4, twoMonthsAgo, "CUST010");
        system.getSalesRecordDirectory().createSalesRecord("LP-001", "MacBook Pro 14\"", 1299.99, 5, oneMonthAgo, "CUST011");
        system.getSalesRecordDirectory().createSalesRecord("LP-001", "MacBook Pro 14\"", 1299.99, 3, currentDate, "CUST012");

        system.getSalesRecordDirectory().createSalesRecord("HP-001", "Apple AirPods Pro", 199.99, 10, threeMonthsAgo, "CUST013");
        system.getSalesRecordDirectory().createSalesRecord("HP-001", "Apple AirPods Pro", 199.99, 15, twoMonthsAgo, "CUST014");
        system.getSalesRecordDirectory().createSalesRecord("HP-001", "Apple AirPods Pro", 199.99, 20, oneMonthAgo, "CUST015");
        system.getSalesRecordDirectory().createSalesRecord("HP-001", "Apple AirPods Pro", 199.99, 18, currentDate, "CUST016");

        System.out.println("Created " + system.getSalesRecordDirectory().getSalesRecordList().size() + " sample sales records");
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

    public static void createSampleShipments(LogisticsOrganization logistics) {
        try {
            // 1. 验证物流组织
            if (logistics == null || logistics.getShipmentDirectory() == null) {
                System.out.println("Error: Invalid logistics organization or shipment directory");
                return;
            }
            ShipmentDirectory shipmentDir = logistics.getShipmentDirectory();

            // 2. 获取仓库实例并验证
            Warehouse warehouse = Warehouse.getInstance();
            if (warehouse == null) {
                System.out.println("Error: Warehouse instance is null");
                return;
            }
            System.out.println("Warehouse instance obtained successfully");

            // 3. 获取可用产品并验证
            List<Product> availableProducts = warehouse.getAvailableProducts();
            System.out.println("Available products in warehouse: "
                    + (availableProducts != null ? availableProducts.size() : 0));

            if (availableProducts == null || availableProducts.isEmpty()) {
                System.out.println("Error: No available products in warehouse");
                return;
            }

            // 4. 配送信息
            String[] destinations = {"New York", "Los Angeles", "Chicago", "Houston", "Phoenix"};
            String[] methods = {"Air Freight", "Sea Freight", "Ground", "Express"};
            String[] statuses = {
                Shipment.STATUS_PENDING,
                Shipment.STATUS_PROCESSING,
                Shipment.STATUS_SHIPPED,
                Shipment.STATUS_IN_TRANSIT,
                Shipment.STATUS_DELIVERING
            };

            // 5. 创建货件
            int successfulShipments = 0;
            for (int i = 0; i < Math.min(10, availableProducts.size()); i++) {
                try {
                    // 获取产品信息
                    Product product = availableProducts.get(i % availableProducts.size());
                    String productId = product.getProductId();

                    // 检查库存
                    int requestQuantity = 5; // 每个货件请求5个单位
                    int currentStock = warehouse.getProductAmount(productId);
                    System.out.println("Checking stock for " + product.getProductName()
                            + " (ID: " + productId + ") - Available: " + currentStock);

                    if (currentStock < requestQuantity) {
                        System.out.println("Insufficient stock for " + product.getProductName()
                                + " (Required: " + requestQuantity + ", Available: " + currentStock + ")");
                        continue;
                    }

                    // 创建货件
                    String shipmentId = "SHP" + String.format("%03d", i + 1);
                    String trackingNumber = "TRK" + String.format("%03d", i + 1);
                    Shipment shipment = shipmentDir.createShipment(shipmentId, trackingNumber);

                    // 设置基本信息
                    shipment.setShipDate(new Date());
                    // 选择发货方式
                    String shippingMethod = methods[i % methods.length];
                    shipment.setShippingMethod(shippingMethod);

                    shipment.setOrigin("Shanghai Warehouse");
                    shipment.setDestination(destinations[i % destinations.length]);
                    shipment.setShipmentStatus(statuses[i % statuses.length]);
                    shipment.setCurrentLocation("Shanghai Warehouse");

                    // 设置订单相关信息
                    shipment.setOrderId("ORD" + String.format("%03d", i + 1));
                    shipment.setProductName(product.getProductName());
                    shipment.setQuantity(requestQuantity);

                    // 设置预计送达时间
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(shipment.getShipDate());
                    switch (shippingMethod) {
                        case "Express":
                            cal.add(Calendar.DAY_OF_MONTH, 3);  // Express is 3 days
                            break;
                        case "Air Freight":
                            cal.add(Calendar.DAY_OF_MONTH, 7);  // Air Freight is 7 days
                            break;
                        case "Sea Freight":
                            cal.add(Calendar.DAY_OF_MONTH, 30); // Sea Freight is 30 days
                            break;
                        case "Ground":
                            cal.add(Calendar.DAY_OF_MONTH, 15); // Ground is 15 days
                            break;
                    }
                    shipment.setEstimatedDeliveryDate(cal.getTime());

                    // 从仓库减少库存
                    boolean stockUpdated = warehouse.decreaseStock(productId, requestQuantity);
                    if (!stockUpdated) {
                        System.out.println("Failed to update stock for " + product.getProductName());
                        continue;
                    }

                    // 添加跟踪记录
                    TrackingInfo trackInfo = new TrackingInfo();
                    trackInfo.setShipmentId(shipmentId);
                    trackInfo.setTimestamp(new Date());
                    trackInfo.setLocation("Shanghai Warehouse");
                    trackInfo.setDescription("Shipment created and inventory deducted");
                    trackInfo.setStatus("Completed");
                    shipment.addTrackingInfo(trackInfo);

                    successfulShipments++;
                    System.out.println("Successfully created shipment: " + shipmentId
                            + " for product: " + product.getProductName()
                            + " (Quantity: " + requestQuantity + ")"
                            + " with estimated delivery on: " + new SimpleDateFormat("yyyy-MM-dd").format(shipment.getEstimatedDeliveryDate()));

                } catch (Exception e) {
                    System.out.println("Error creating shipment: " + e.getMessage());
                    e.printStackTrace();
                }
            }

            // 6. 最终统计
            System.out.println("Shipment creation completed:");
            System.out.println("- Total successful shipments: " + successfulShipments);
            System.out.println("- Total shipments in directory: "
                    + shipmentDir.getShipments().size());

        } catch (Exception e) {
            System.out.println("Fatal error in createSampleShipments: " + e.getMessage());
            e.printStackTrace();
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

            // Calculate estimated delivery date based on shipping method and shipDate
            Calendar cal = Calendar.getInstance();
            cal.setTime(shipment.getShipDate());

            // For Express shipping, add 3 days
            cal.add(Calendar.DAY_OF_MONTH, 3);
            shipment.setEstimatedDeliveryDate(cal.getTime());

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
            System.out.println("Estimated Delivery Date: " + new SimpleDateFormat("yyyy-MM-dd").format(shipment.getEstimatedDeliveryDate()));
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

        // Calculate estimated delivery date based on shipping method and shipDate
        Calendar cal = Calendar.getInstance();
        cal.setTime(shipment.getShipDate());

        // For Express shipping, add 3 days
        cal.add(Calendar.DAY_OF_MONTH, 3);
        shipment.setEstimatedDeliveryDate(cal.getTime());
        System.out.println("In-Transit Shipment: Estimated Delivery Date: "
                + new SimpleDateFormat("yyyy-MM-dd").format(shipment.getEstimatedDeliveryDate()));

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

        // Calculate estimated delivery date based on shipping method and shipDate
        Calendar cal = Calendar.getInstance();
        cal.setTime(shipment.getShipDate());

        // For Standard (Ground) shipping, add 15 days
        cal.add(Calendar.DAY_OF_MONTH, 15);
        shipment.setEstimatedDeliveryDate(cal.getTime());

        System.out.println("Newly Shipped: Estimated Delivery Date: "
                + new SimpleDateFormat("yyyy-MM-dd").format(shipment.getEstimatedDeliveryDate()));

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

    public static void createSampleCustomsDeclarations(LogisticsOrganization logistics) {
        if (logistics == null) {
            System.out.println("Error: Cannot create sample customs declarations - logistics organization is null");
            return;
        }

        // 确保报关单目录存在
        CustomsDeclarationDirectory declarationDir;
        if (logistics.getCustomsDeclarationDirectory() == null) {
            declarationDir = new CustomsDeclarationDirectory();
            logistics.setCustomsDeclarationDirectory(declarationDir);
        } else {
            declarationDir = logistics.getCustomsDeclarationDirectory();
        }

        // 创建示例报关单1 - 草稿状态
        CustomsDeclaration declaration1 = new CustomsDeclaration();
        declaration1.setDeclarationId("CD202504120001");
        declaration1.setShipmentId("TRK001");  // 对应TRK001
        declaration1.setStatus("Draft");
        declaration1.setDeclarationDate(new Date());
        declaration1.setConsignor("Apple Store Shanghai");
        declaration1.setConsignee("John Smith");
        declaration1.setCountryOfOrigin("China");
        declaration1.setDestinationCountry("United States");
        declaration1.setCustomsOffice("Shanghai Customs");
        declaration1.setNotes("High-value electronics, handle with care");

        // 添加商品项目
        ArrayList<CustomsDeclaration.CustomsLineItem> items1 = new ArrayList<>();

        CustomsDeclaration.CustomsLineItem item1 = new CustomsDeclaration.CustomsLineItem();
        item1.setDescription("iPhone 15 Pro");
        item1.setHsCode("8517.12.00");
        item1.setQuantity(5);
        item1.setUnit("PCS");
        item1.setUnitValue(899.99);
        item1.setGrossWeight(0.5);
        items1.add(item1);

        CustomsDeclaration.CustomsLineItem item2 = new CustomsDeclaration.CustomsLineItem();
        item2.setDescription("MacBook Pro 14\"");
        item2.setHsCode("8471.30.00");
        item2.setQuantity(2);
        item2.setUnit("PCS");
        item2.setUnitValue(1999.99);
        item2.setGrossWeight(1.5);
        items1.add(item2);

        declaration1.setItems(items1);
        declarationDir.addCustomsDeclaration(declaration1);

        // 创建示例报关单2 - 已提交状态
        CustomsDeclaration declaration2 = new CustomsDeclaration();
        declaration2.setDeclarationId("CD202504150002");
        declaration2.setShipmentId("SHP002");  // 对应TRK002
        declaration2.setStatus("Submitted");

        // 设置日期 (5天前)
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -5);
        declaration2.setDeclarationDate(cal.getTime());

        // 设置提交日期 (3天前)
        cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -3);
        declaration2.setSubmissionDate(cal.getTime());

        declaration2.setConsignor("Samsung Electronics Co., Ltd.");
        declaration2.setConsignee("Jane Doe");
        declaration2.setCountryOfOrigin("South Korea");
        declaration2.setDestinationCountry("United States");
        declaration2.setCustomsOffice("Incheon Customs");
        declaration2.setNotes("Electronics with lithium batteries, declaration complete");

        // 添加商品项目
        ArrayList<CustomsDeclaration.CustomsLineItem> items2 = new ArrayList<>();

        CustomsDeclaration.CustomsLineItem item3 = new CustomsDeclaration.CustomsLineItem();
        item3.setDescription("Samsung Galaxy S23");
        item3.setHsCode("8517.12.00");
        item3.setQuantity(10);
        item3.setUnit("PCS");
        item3.setUnitValue(799.99);
        item3.setGrossWeight(0.4);
        items2.add(item3);

        CustomsDeclaration.CustomsLineItem item4 = new CustomsDeclaration.CustomsLineItem();
        item4.setDescription("Samsung TV QLED Q80T");
        item4.setHsCode("8528.72.00");
        item4.setQuantity(3);
        item4.setUnit("PCS");
        item4.setUnitValue(1499.99);
        item4.setGrossWeight(20.0);
        items2.add(item4);

        declaration2.setItems(items2);
        declarationDir.addCustomsDeclaration(declaration2);

        // 创建示例报关单3 - 已批准状态
        CustomsDeclaration declaration3 = new CustomsDeclaration();
        declaration3.setDeclarationId("CD202504010003");
        declaration3.setShipmentId("SHP003");  // 对应TRK003
        declaration3.setStatus("Approved");

        // 设置日期 (15天前)
        cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -15);
        declaration3.setDeclarationDate(cal.getTime());

        // 设置提交日期 (14天前)
        cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -14);
        declaration3.setSubmissionDate(cal.getTime());

        // 设置处理日期 (12天前)
        cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -12);
        declaration3.setProcessingDate(cal.getTime());

        declaration3.setConsignor("Sony Corporation");
        declaration3.setConsignee("Robert Wilson");
        declaration3.setCountryOfOrigin("Japan");
        declaration3.setDestinationCountry("United States");
        declaration3.setCustomsOffice("Tokyo Customs");
        declaration3.setNotes("Electronics and accessories, approved for import");

        // 添加商品项目
        ArrayList<CustomsDeclaration.CustomsLineItem> items3 = new ArrayList<>();

        CustomsDeclaration.CustomsLineItem item5 = new CustomsDeclaration.CustomsLineItem();
        item5.setDescription("Sony PlayStation 5");
        item5.setHsCode("9504.50.00");
        item5.setQuantity(20);
        item5.setUnit("PCS");
        item5.setUnitValue(499.99);
        item5.setGrossWeight(4.5);
        items3.add(item5);

        CustomsDeclaration.CustomsLineItem item6 = new CustomsDeclaration.CustomsLineItem();
        item6.setDescription("Sony WH-1000XM5 Headphones");
        item6.setHsCode("8518.30.00");
        item6.setQuantity(30);
        item6.setUnit("PCS");
        item6.setUnitValue(349.99);
        item6.setGrossWeight(0.25);
        items3.add(item6);

        declaration3.setItems(items3);
        declarationDir.addCustomsDeclaration(declaration3);

        // 创建示例报关单4 - 已提交状态
        CustomsDeclaration declaration4 = new CustomsDeclaration();
        declaration4.setDeclarationId("CD202504020004");
        declaration4.setShipmentId("SHP004");  // 对应TRK004
        declaration4.setStatus("Submitted");

        // 设置日期 (10天前)
        cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -10);
        declaration4.setDeclarationDate(cal.getTime());

        // 设置提交日期 (8天前)
        cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -8);
        declaration4.setSubmissionDate(cal.getTime());

        declaration4.setConsignor("Dell Technologies");
        declaration4.setConsignee("Michael Johnson");
        declaration4.setCountryOfOrigin("United States");
        declaration4.setDestinationCountry("China");
        declaration4.setCustomsOffice("Houston Customs");
        declaration4.setNotes("Computer equipment, awaiting customs clearance");

        // 添加商品项目
        ArrayList<CustomsDeclaration.CustomsLineItem> items4 = new ArrayList<>();

        CustomsDeclaration.CustomsLineItem item7 = new CustomsDeclaration.CustomsLineItem();
        item7.setDescription("Dell XPS 15 Laptop");
        item7.setHsCode("8471.30.00");
        item7.setQuantity(8);
        item7.setUnit("PCS");
        item7.setUnitValue(1599.99);
        item7.setGrossWeight(2.0);
        items4.add(item7);

        CustomsDeclaration.CustomsLineItem item8 = new CustomsDeclaration.CustomsLineItem();
        item8.setDescription("Dell UltraSharp Monitor");
        item8.setHsCode("8528.52.00");
        item8.setQuantity(5);
        item8.setUnit("PCS");
        item8.setUnitValue(399.99);
        item8.setGrossWeight(5.0);
        items4.add(item8);

        declaration4.setItems(items4);
        declarationDir.addCustomsDeclaration(declaration4);

        // 创建示例报关单5 - 已批准状态
        CustomsDeclaration declaration5 = new CustomsDeclaration();
        declaration5.setDeclarationId("CD202504030005");
        declaration5.setShipmentId("SHP005");  // 对应TRK005
        declaration5.setStatus("Information Needed");

        // 设置日期 (20天前)
        cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -20);
        declaration5.setDeclarationDate(cal.getTime());

        // 设置提交日期 (19天前)
        cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -19);
        declaration5.setSubmissionDate(cal.getTime());

        // 设置处理日期 (18天前)
        cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -18);
        declaration5.setProcessingDate(cal.getTime());

        declaration5.setConsignor("LG Electronics");
        declaration5.setConsignee("Sarah Williams");
        declaration5.setCountryOfOrigin("South Korea");
        declaration5.setDestinationCountry("Australia");
        declaration5.setCustomsOffice("Seoul Customs");
        declaration5.setNotes("Electronics shipment, customs cleared");

        // 添加商品项目
        ArrayList<CustomsDeclaration.CustomsLineItem> items5 = new ArrayList<>();

        CustomsDeclaration.CustomsLineItem item9 = new CustomsDeclaration.CustomsLineItem();
        item9.setDescription("LG OLED TV C2");
        item9.setHsCode("8528.72.00");
        item9.setQuantity(5);
        item9.setUnit("PCS");
        item9.setUnitValue(1299.99);
        item9.setGrossWeight(25.0);
        items5.add(item9);

        CustomsDeclaration.CustomsLineItem item10 = new CustomsDeclaration.CustomsLineItem();
        item10.setDescription("LG SoundBar S80QY");
        item10.setHsCode("8518.22.00");
        item10.setQuantity(5);
        item10.setUnit("PCS");
        item10.setUnitValue(399.99);
        item10.setGrossWeight(3.0);
        items5.add(item10);

        declaration5.setItems(items5);
        declarationDir.addCustomsDeclaration(declaration5);

        // 创建示例报关单6 - 草稿状态
        CustomsDeclaration declaration6 = new CustomsDeclaration();
        declaration6.setDeclarationId("CD202504100006");
        declaration6.setShipmentId("SHP006");  // 对应TRK006
        declaration6.setStatus("Draft");
        declaration6.setDeclarationDate(new Date());
        declaration6.setConsignor("Microsoft Corporation");
        declaration6.setConsignee("Thomas Anderson");
        declaration6.setCountryOfOrigin("United States");
        declaration6.setDestinationCountry("United Kingdom");
        declaration6.setCustomsOffice("Seattle Customs");
        declaration6.setNotes("Computer hardware, preparing documentation");

        // 添加商品项目
        ArrayList<CustomsDeclaration.CustomsLineItem> items6 = new ArrayList<>();

        CustomsDeclaration.CustomsLineItem item11 = new CustomsDeclaration.CustomsLineItem();
        item11.setDescription("Surface Pro 9");
        item11.setHsCode("8471.30.00");
        item11.setQuantity(12);
        item11.setUnit("PCS");
        item11.setUnitValue(1299.99);
        item11.setGrossWeight(0.9);
        items6.add(item11);

        CustomsDeclaration.CustomsLineItem item12 = new CustomsDeclaration.CustomsLineItem();
        item12.setDescription("Xbox Series X");
        item12.setHsCode("9504.50.00");
        item12.setQuantity(8);
        item12.setUnit("PCS");
        item12.setUnitValue(499.99);
        item12.setGrossWeight(4.5);
        items6.add(item12);

        declaration6.setItems(items6);
        declarationDir.addCustomsDeclaration(declaration6);

        // 创建示例报关单7 - 已提交状态
        CustomsDeclaration declaration7 = new CustomsDeclaration();
        declaration7.setDeclarationId("CD202504050007");
        declaration7.setShipmentId("SHP007");  // 对应TRK007
        declaration7.setStatus("Submitted");

        // 设置日期 (8天前)
        cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -8);
        declaration7.setDeclarationDate(cal.getTime());

        // 设置提交日期 (6天前)
        cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -6);
        declaration7.setSubmissionDate(cal.getTime());

        declaration7.setConsignor("Huawei Technologies");
        declaration7.setConsignee("Jessica Brown");
        declaration7.setCountryOfOrigin("China");
        declaration7.setDestinationCountry("Germany");
        declaration7.setCustomsOffice("Shenzhen Customs");
        declaration7.setNotes("Telecommunications equipment, awaiting approval");

        // 添加商品项目
        ArrayList<CustomsDeclaration.CustomsLineItem> items7 = new ArrayList<>();

        CustomsDeclaration.CustomsLineItem item13 = new CustomsDeclaration.CustomsLineItem();
        item13.setDescription("Huawei MatePad Pro");
        item13.setHsCode("8471.30.00");
        item13.setQuantity(20);
        item13.setUnit("PCS");
        item13.setUnitValue(599.99);
        item13.setGrossWeight(0.7);
        items7.add(item13);

        CustomsDeclaration.CustomsLineItem item14 = new CustomsDeclaration.CustomsLineItem();
        item14.setDescription("Huawei FreeBuds Pro");
        item14.setHsCode("8518.30.00");
        item14.setQuantity(50);
        item14.setUnit("PCS");
        item14.setUnitValue(199.99);
        item14.setGrossWeight(0.1);
        items7.add(item14);

        declaration7.setItems(items7);
        declarationDir.addCustomsDeclaration(declaration7);

        // 创建示例报关单8 - 已批准状态
        CustomsDeclaration declaration8 = new CustomsDeclaration();
        declaration8.setDeclarationId("CD202504010008");
        declaration8.setShipmentId("SHP008");  // 对应TRK008
        declaration8.setStatus("Approved");

        // 设置日期 (25天前)
        cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -25);
        declaration8.setDeclarationDate(cal.getTime());

        // 设置提交日期 (24天前)
        cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -24);
        declaration8.setSubmissionDate(cal.getTime());

        // 设置处理日期 (22天前)
        cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -22);
        declaration8.setProcessingDate(cal.getTime());

        declaration8.setConsignor("Xiaomi");
        declaration8.setConsignee("David Wilson");
        declaration8.setCountryOfOrigin("China");
        declaration8.setDestinationCountry("India");
        declaration8.setCustomsOffice("Beijing Customs");
        declaration8.setNotes("Mobile devices, approved for export");

        // 添加商品项目
        ArrayList<CustomsDeclaration.CustomsLineItem> items8 = new ArrayList<>();

        CustomsDeclaration.CustomsLineItem item15 = new CustomsDeclaration.CustomsLineItem();
        item15.setDescription("Xiaomi Mi 13");
        item15.setHsCode("8517.12.00");
        item15.setQuantity(50);
        item15.setUnit("PCS");
        item15.setUnitValue(699.99);
        item15.setGrossWeight(0.3);
        items8.add(item15);

        CustomsDeclaration.CustomsLineItem item16 = new CustomsDeclaration.CustomsLineItem();
        item16.setDescription("Xiaomi Band 7");
        item16.setHsCode("8517.62.00");
        item16.setQuantity(100);
        item16.setUnit("PCS");
        item16.setUnitValue(59.99);
        item16.setGrossWeight(0.05);
        items8.add(item16);

        declaration8.setItems(items8);
        declarationDir.addCustomsDeclaration(declaration8);

        // 创建示例报关单9 - 已提交状态
        CustomsDeclaration declaration9 = new CustomsDeclaration();
        declaration9.setDeclarationId("CD202504060009");
        declaration9.setShipmentId("SHP009");  // 对应TRK009
        declaration9.setStatus("Submitted");

        // 设置日期 (7天前)
        cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -7);
        declaration9.setDeclarationDate(cal.getTime());

        // 设置提交日期 (5天前)
        cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -5);
        declaration9.setSubmissionDate(cal.getTime());

        declaration9.setConsignor("Nintendo Co., Ltd.");
        declaration9.setConsignee("Richard Martin");
        declaration9.setCountryOfOrigin("Japan");
        declaration9.setDestinationCountry("France");
        declaration9.setCustomsOffice("Kyoto Customs");
        declaration9.setNotes("Gaming consoles and accessories, awaiting clearance");

        // 添加商品项目
        ArrayList<CustomsDeclaration.CustomsLineItem> items9 = new ArrayList<>();

        CustomsDeclaration.CustomsLineItem item17 = new CustomsDeclaration.CustomsLineItem();
        item17.setDescription("Nintendo Switch OLED");
        item17.setHsCode("9504.50.00");
        item17.setQuantity(30);
        item17.setUnit("PCS");
        item17.setUnitValue(349.99);
        item17.setGrossWeight(0.4);
        items9.add(item17);

        CustomsDeclaration.CustomsLineItem item18 = new CustomsDeclaration.CustomsLineItem();
        item18.setDescription("Nintendo Game Cartridges");
        item18.setHsCode("9504.50.00");
        item18.setQuantity(100);
        item18.setUnit("PCS");
        item18.setUnitValue(59.99);
        item18.setGrossWeight(0.01);
        items9.add(item18);

        declaration9.setItems(items9);
        declarationDir.addCustomsDeclaration(declaration9);

        // 创建示例报关单10 - 草稿状态
        CustomsDeclaration declaration10 = new CustomsDeclaration();
        declaration10.setDeclarationId("CD202504110010");
        declaration10.setShipmentId("SHP010");  // 对应TRK010
        declaration10.setStatus("Draft");
        declaration10.setDeclarationDate(new Date());
        declaration10.setConsignor("HP Inc.");
        declaration10.setConsignee("Emma Davis");
        declaration10.setCountryOfOrigin("United States");
        declaration10.setDestinationCountry("Brazil");
        declaration10.setCustomsOffice("Miami Customs");
        declaration10.setNotes("Computer equipment, preparing documentation");

        // 添加商品项目
        ArrayList<CustomsDeclaration.CustomsLineItem> items10 = new ArrayList<>();

        CustomsDeclaration.CustomsLineItem item19 = new CustomsDeclaration.CustomsLineItem();
        item19.setDescription("HP Spectre Laptop");
        item19.setHsCode("8471.30.00");
        item19.setQuantity(15);
        item19.setUnit("PCS");
        item19.setUnitValue(1399.99);
        item19.setGrossWeight(1.3);
        items10.add(item19);

        CustomsDeclaration.CustomsLineItem item20 = new CustomsDeclaration.CustomsLineItem();
        item20.setDescription("HP LaserJet Printer");
        item20.setHsCode("8443.31.00");
        item20.setQuantity(8);
        item20.setUnit("PCS");
        item20.setUnitValue(499.99);
        item20.setGrossWeight(8.5);
        items10.add(item20);

        declaration10.setItems(items10);
        declarationDir.addCustomsDeclaration(declaration10);

        System.out.println("Created " + declarationDir.getCustomsDeclarationList().size() + " sample customs declarations");
    }

    private static void createSampleCustomsDeclarations(CustomsLiaisonOrganization customsOrg) {
        if (customsOrg == null || customsOrg.getCustomsDeclarationDirectory() == null) {
            System.out.println("Error: Cannot create sample customs declarations - invalid organization");
            return;
        }

        CustomsDeclarationDirectory declarationDir = customsOrg.getCustomsDeclarationDirectory();

        // 创建示例报关单1 - 草稿状态（已存在）
        CustomsDeclaration declaration1 = new CustomsDeclaration();
        declaration1.setDeclarationId("CD202504120001");
        declaration1.setShipmentId("SHP001");
        declaration1.setStatus("Draft");
        declaration1.setDeclarationDate(new Date());
        declaration1.setConsignor("Apple Store Shanghai");
        declaration1.setConsignee("John Smith");
        declaration1.setCountryOfOrigin("China");
        declaration1.setDestinationCountry("United States");
        declaration1.setCustomsOffice("Shanghai Customs");
        declaration1.setNotes("High-value electronics, handle with care");

        // 添加商品项目
        ArrayList<CustomsDeclaration.CustomsLineItem> items1 = new ArrayList<>();

        CustomsDeclaration.CustomsLineItem item1 = new CustomsDeclaration.CustomsLineItem();
        item1.setDescription("iPhone 15 Pro");
        item1.setHsCode("8517.12.00");
        item1.setQuantity(5);
        item1.setUnit("PCS");
        item1.setUnitValue(899.99);
        item1.setGrossWeight(0.5);
        items1.add(item1);

        CustomsDeclaration.CustomsLineItem item2 = new CustomsDeclaration.CustomsLineItem();
        item2.setDescription("MacBook Pro 14\"");
        item2.setHsCode("8471.30.00");
        item2.setQuantity(2);
        item2.setUnit("PCS");
        item2.setUnitValue(1999.99);
        item2.setGrossWeight(1.5);
        items1.add(item2);

        declaration1.setItems(items1);
        declarationDir.addCustomsDeclaration(declaration1);

        // 创建示例报关单2 - 已提交状态（已存在）
        CustomsDeclaration declaration2 = new CustomsDeclaration();
        declaration2.setDeclarationId("CD202504150002");
        declaration2.setShipmentId("SHP002");
        declaration2.setStatus("Submitted");

        // 设置日期 (5天前)
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -5);
        declaration2.setDeclarationDate(cal.getTime());

        // 设置提交日期 (3天前)
        cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -3);
        declaration2.setSubmissionDate(cal.getTime());

        declaration2.setConsignor("Samsung Electronics Co., Ltd.");
        declaration2.setConsignee("Jane Doe");
        declaration2.setCountryOfOrigin("South Korea");
        declaration2.setDestinationCountry("United States");
        declaration2.setCustomsOffice("Incheon Customs");
        declaration2.setNotes("Electronics with lithium batteries, declaration complete");

        // 添加商品项目
        ArrayList<CustomsDeclaration.CustomsLineItem> items2 = new ArrayList<>();

        CustomsDeclaration.CustomsLineItem item3 = new CustomsDeclaration.CustomsLineItem();
        item3.setDescription("Samsung Galaxy S23");
        item3.setHsCode("8517.12.00");
        item3.setQuantity(10);
        item3.setUnit("PCS");
        item3.setUnitValue(799.99);
        item3.setGrossWeight(0.4);
        items2.add(item3);

        CustomsDeclaration.CustomsLineItem item4 = new CustomsDeclaration.CustomsLineItem();
        item4.setDescription("Samsung TV QLED Q80T");
        item4.setHsCode("8528.72.00");
        item4.setQuantity(3);
        item4.setUnit("PCS");
        item4.setUnitValue(1499.99);
        item4.setGrossWeight(20.0);
        items2.add(item4);

        declaration2.setItems(items2);
        declarationDir.addCustomsDeclaration(declaration2);

        // 创建示例报关单3 - 已批准状态（已存在）
        CustomsDeclaration declaration3 = new CustomsDeclaration();
        declaration3.setDeclarationId("CD202504010003");
        declaration3.setShipmentId("SHP003");
        declaration3.setStatus("Approved");

        // 设置日期 (15天前)
        cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -15);
        declaration3.setDeclarationDate(cal.getTime());

        // 设置提交日期 (14天前)
        cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -14);
        declaration3.setSubmissionDate(cal.getTime());

        // 设置处理日期 (12天前)
        cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -12);
        declaration3.setProcessingDate(cal.getTime());

        declaration3.setConsignor("Sony Corporation");
        declaration3.setConsignee("Robert Wilson");
        declaration3.setCountryOfOrigin("Japan");
        declaration3.setDestinationCountry("United States");
        declaration3.setCustomsOffice("Tokyo Customs");
        declaration3.setNotes("Electronics and accessories, approved for import");

        // 添加商品项目
        ArrayList<CustomsDeclaration.CustomsLineItem> items3 = new ArrayList<>();

        CustomsDeclaration.CustomsLineItem item5 = new CustomsDeclaration.CustomsLineItem();
        item5.setDescription("Sony PlayStation 5");
        item5.setHsCode("9504.50.00");
        item5.setQuantity(20);
        item5.setUnit("PCS");
        item5.setUnitValue(499.99);
        item5.setGrossWeight(4.5);
        items3.add(item5);

        CustomsDeclaration.CustomsLineItem item6 = new CustomsDeclaration.CustomsLineItem();
        item6.setDescription("Sony WH-1000XM5 Headphones");
        item6.setHsCode("8518.30.00");
        item6.setQuantity(30);
        item6.setUnit("PCS");
        item6.setUnitValue(349.99);
        item6.setGrossWeight(0.25);
        items3.add(item6);

        declaration3.setItems(items3);
        declarationDir.addCustomsDeclaration(declaration3);

        // 创建示例报关单4 - 已拒绝状态
        CustomsDeclaration declaration4 = new CustomsDeclaration();
        declaration4.setDeclarationId("CD202503250004");
        declaration4.setShipmentId("SHP004");
        declaration4.setStatus("Rejected");

        // 设置日期 (20天前)
        cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -20);
        declaration4.setDeclarationDate(cal.getTime());

        // 设置提交日期 (19天前)
        cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -19);
        declaration4.setSubmissionDate(cal.getTime());

        // 设置处理日期 (18天前)
        cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -18);
        declaration4.setProcessingDate(cal.getTime());

        declaration4.setConsignor("Dell Technologies");
        declaration4.setConsignee("Michael Johnson");
        declaration4.setCountryOfOrigin("United States");
        declaration4.setDestinationCountry("China");
        declaration4.setCustomsOffice("Shanghai Customs");
        declaration4.setNotes("Documentation incomplete, missing invoice details");

        // 添加商品项目
        ArrayList<CustomsDeclaration.CustomsLineItem> items4 = new ArrayList<>();

        CustomsDeclaration.CustomsLineItem item7 = new CustomsDeclaration.CustomsLineItem();
        item7.setDescription("Dell XPS 15 Laptop");
        item7.setHsCode("8471.30.00");
        item7.setQuantity(8);
        item7.setUnit("PCS");
        item7.setUnitValue(1599.99);
        item7.setGrossWeight(2.0);
        items4.add(item7);

        CustomsDeclaration.CustomsLineItem item8 = new CustomsDeclaration.CustomsLineItem();
        item8.setDescription("Dell UltraSharp Monitor");
        item8.setHsCode("8528.52.00");
        item8.setQuantity(5);
        item8.setUnit("PCS");
        item8.setUnitValue(399.99);
        item8.setGrossWeight(5.0);
        items4.add(item8);

        declaration4.setItems(items4);
        declarationDir.addCustomsDeclaration(declaration4);

        // 创建示例报关单5 - 草稿状态
        CustomsDeclaration declaration5 = new CustomsDeclaration();
        declaration5.setDeclarationId("CD202504180005");
        declaration5.setShipmentId("SHP005");
        declaration5.setStatus("Draft");

        // 设置日期 (2天前)
        cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -2);
        declaration5.setDeclarationDate(cal.getTime());

        declaration5.setConsignor("LG Electronics");
        declaration5.setConsignee("Sarah Williams");
        declaration5.setCountryOfOrigin("South Korea");
        declaration5.setDestinationCountry("Canada");
        declaration5.setCustomsOffice("Incheon Customs");
        declaration5.setNotes("Electronics shipment with mixed items");

        // 添加商品项目
        ArrayList<CustomsDeclaration.CustomsLineItem> items5 = new ArrayList<>();

        CustomsDeclaration.CustomsLineItem item9 = new CustomsDeclaration.CustomsLineItem();
        item9.setDescription("LG OLED TV C2");
        item9.setHsCode("8528.72.00");
        item9.setQuantity(5);
        item9.setUnit("PCS");
        item9.setUnitValue(1299.99);
        item9.setGrossWeight(25.0);
        items5.add(item9);

        CustomsDeclaration.CustomsLineItem item10 = new CustomsDeclaration.CustomsLineItem();
        item10.setDescription("LG SoundBar S80QY");
        item10.setHsCode("8518.22.00");
        item10.setQuantity(5);
        item10.setUnit("PCS");
        item10.setUnitValue(399.99);
        item10.setGrossWeight(3.0);
        items5.add(item10);

        declaration5.setItems(items5);
        declarationDir.addCustomsDeclaration(declaration5);

        // 创建示例报关单6 - 已提交状态
        CustomsDeclaration declaration6 = new CustomsDeclaration();
        declaration6.setDeclarationId("CD202504160006");
        declaration6.setShipmentId("SHP006");
        declaration6.setStatus("Submitted");

        // 设置日期 (4天前)
        cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -4);
        declaration6.setDeclarationDate(cal.getTime());

        // 设置提交日期 (3天前)
        cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -3);
        declaration6.setSubmissionDate(cal.getTime());

        declaration6.setConsignor("Huawei Technologies");
        declaration6.setConsignee("Thomas Anderson");
        declaration6.setCountryOfOrigin("China");
        declaration6.setDestinationCountry("Germany");
        declaration6.setCustomsOffice("Shenzhen Customs");
        declaration6.setNotes("Telecommunications equipment, awaiting approval");

        // 添加商品项目
        ArrayList<CustomsDeclaration.CustomsLineItem> items6 = new ArrayList<>();

        CustomsDeclaration.CustomsLineItem item11 = new CustomsDeclaration.CustomsLineItem();
        item11.setDescription("Huawei MatePad Pro");
        item11.setHsCode("8471.30.00");
        item11.setQuantity(20);
        item11.setUnit("PCS");
        item11.setUnitValue(599.99);
        item11.setGrossWeight(0.7);
        items6.add(item11);

        CustomsDeclaration.CustomsLineItem item12 = new CustomsDeclaration.CustomsLineItem();
        item12.setDescription("Huawei FreeBuds Pro");
        item12.setHsCode("8518.30.00");
        item12.setQuantity(50);
        item12.setUnit("PCS");
        item12.setUnitValue(199.99);
        item12.setGrossWeight(0.1);
        items6.add(item12);

        declaration6.setItems(items6);
        declarationDir.addCustomsDeclaration(declaration6);

        // 创建示例报关单7 - 已批准状态
        CustomsDeclaration declaration7 = new CustomsDeclaration();
        declaration7.setDeclarationId("CD202503100007");
        declaration7.setShipmentId("SHP007");
        declaration7.setStatus("Approved");

        // 设置日期 (25天前)
        cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -25);
        declaration7.setDeclarationDate(cal.getTime());

        // 设置提交日期 (24天前)
        cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -24);
        declaration7.setSubmissionDate(cal.getTime());

        // 设置处理日期 (22天前)
        cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -22);
        declaration7.setProcessingDate(cal.getTime());

        declaration7.setConsignor("Microsoft Corporation");
        declaration7.setConsignee("Jessica Brown");
        declaration7.setCountryOfOrigin("United States");
        declaration7.setDestinationCountry("Australia");
        declaration7.setCustomsOffice("Seattle Customs");
        declaration7.setNotes("Computer hardware and accessories, approved for export");

        // 添加商品项目
        ArrayList<CustomsDeclaration.CustomsLineItem> items7 = new ArrayList<>();

        CustomsDeclaration.CustomsLineItem item13 = new CustomsDeclaration.CustomsLineItem();
        item13.setDescription("Surface Pro 9");
        item13.setHsCode("8471.30.00");
        item13.setQuantity(12);
        item13.setUnit("PCS");
        item13.setUnitValue(1299.99);
        item13.setGrossWeight(0.9);
        items7.add(item13);

        CustomsDeclaration.CustomsLineItem item14 = new CustomsDeclaration.CustomsLineItem();
        item14.setDescription("Xbox Series X");
        item14.setHsCode("9504.50.00");
        item14.setQuantity(8);
        item14.setUnit("PCS");
        item14.setUnitValue(499.99);
        item14.setGrossWeight(4.5);
        items7.add(item14);

        declaration7.setItems(items7);
        declarationDir.addCustomsDeclaration(declaration7);

        // 创建示例报关单8 - 已拒绝状态
        CustomsDeclaration declaration8 = new CustomsDeclaration();
        declaration8.setDeclarationId("CD202503150008");
        declaration8.setShipmentId("SHP008");
        declaration8.setStatus("Rejected");

        // 设置日期 (30天前)
        cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -30);
        declaration8.setDeclarationDate(cal.getTime());

        // 设置提交日期 (29天前)
        cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -29);
        declaration8.setSubmissionDate(cal.getTime());

        // 设置处理日期 (28天前)
        cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -28);
        declaration8.setProcessingDate(cal.getTime());

        declaration8.setConsignor("Xiaomi");
        declaration8.setConsignee("David Wilson");
        declaration8.setCountryOfOrigin("China");
        declaration8.setDestinationCountry("India");
        declaration8.setCustomsOffice("Beijing Customs");
        declaration8.setNotes("Incorrect tariff classification, resubmission required");

        // 添加商品项目
        ArrayList<CustomsDeclaration.CustomsLineItem> items8 = new ArrayList<>();

        CustomsDeclaration.CustomsLineItem item15 = new CustomsDeclaration.CustomsLineItem();
        item15.setDescription("Xiaomi Mi 13");
        item15.setHsCode("8517.12.00");
        item15.setQuantity(50);
        item15.setUnit("PCS");
        item15.setUnitValue(699.99);
        item15.setGrossWeight(0.3);
        items8.add(item15);

        CustomsDeclaration.CustomsLineItem item16 = new CustomsDeclaration.CustomsLineItem();
        item16.setDescription("Xiaomi Band 7");
        item16.setHsCode("8517.62.00");
        item16.setQuantity(100);
        item16.setUnit("PCS");
        item16.setUnitValue(59.99);
        item16.setGrossWeight(0.05);
        items8.add(item16);

        declaration8.setItems(items8);
        declarationDir.addCustomsDeclaration(declaration8);

        // 创建示例报关单9 - 草稿状态
        CustomsDeclaration declaration9 = new CustomsDeclaration();
        declaration9.setDeclarationId("CD202504190009");
        declaration9.setShipmentId("SHP009");
        declaration9.setStatus("Draft");

        // 设置日期 (1天前)
        cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -1);
        declaration9.setDeclarationDate(cal.getTime());

        declaration9.setConsignor("Nintendo Co., Ltd.");
        declaration9.setConsignee("Richard Martin");
        declaration9.setCountryOfOrigin("Japan");
        declaration9.setDestinationCountry("France");
        declaration9.setCustomsOffice("Kyoto Customs");
        declaration9.setNotes("Gaming consoles and games, draft declaration");

        // 添加商品项目
        ArrayList<CustomsDeclaration.CustomsLineItem> items9 = new ArrayList<>();

        CustomsDeclaration.CustomsLineItem item17 = new CustomsDeclaration.CustomsLineItem();
        item17.setDescription("Nintendo Switch OLED");
        item17.setHsCode("9504.50.00");
        item17.setQuantity(30);
        item17.setUnit("PCS");
        item17.setUnitValue(349.99);
        item17.setGrossWeight(0.4);
        items9.add(item17);

        CustomsDeclaration.CustomsLineItem item18 = new CustomsDeclaration.CustomsLineItem();
        item18.setDescription("Nintendo Game Cartridges");
        item18.setHsCode("9504.50.00");
        item18.setQuantity(100);
        item18.setUnit("PCS");
        item18.setUnitValue(59.99);
        item18.setGrossWeight(0.01);
        items9.add(item18);

        declaration9.setItems(items9);
        declarationDir.addCustomsDeclaration(declaration9);

        // 创建示例报关单10 - 已批准状态
        CustomsDeclaration declaration10 = new CustomsDeclaration();
        declaration10.setDeclarationId("CD202503200010");
        declaration10.setShipmentId("SHP010");
        declaration10.setStatus("Approved");

        // 设置日期 (28天前)
        cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -28);
        declaration10.setDeclarationDate(cal.getTime());

        // 设置提交日期 (27天前)
        cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -27);
        declaration10.setSubmissionDate(cal.getTime());

        // 设置处理日期 (25天前)
        cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -25);
        declaration10.setProcessingDate(cal.getTime());

        declaration10.setConsignor("HP Inc.");
        declaration10.setConsignee("Emma Davis");
        declaration10.setCountryOfOrigin("United States");
        declaration10.setDestinationCountry("Brazil");
        declaration10.setCustomsOffice("Miami Customs");
        declaration10.setNotes("Computer equipment, approved for export to Brazil");

        // 添加商品项目
        ArrayList<CustomsDeclaration.CustomsLineItem> items10 = new ArrayList<>();

        CustomsDeclaration.CustomsLineItem item19 = new CustomsDeclaration.CustomsLineItem();
        item19.setDescription("HP Spectre Laptop");
        item19.setHsCode("8471.30.00");
        item19.setQuantity(15);
        item19.setUnit("PCS");
        item19.setUnitValue(1399.99);
        item19.setGrossWeight(1.3);
        items10.add(item19);

        CustomsDeclaration.CustomsLineItem item20 = new CustomsDeclaration.CustomsLineItem();
        item20.setDescription("HP LaserJet Printer");
        item20.setHsCode("8443.31.00");
        item20.setQuantity(8);
        item20.setUnit("PCS");
        item20.setUnitValue(499.99);
        item20.setGrossWeight(8.5);
        items10.add(item20);

        declaration10.setItems(items10);
        declarationDir.addCustomsDeclaration(declaration10);

        System.out.println("Created " + declarationDir.getCustomsDeclarationList().size() + " sample customs declarations");
    }

    // Add this to ConfigureASystem.java
    public static Enterprise findEnterpriseForOrganization(Organization organization) {
        try {
            // If the organization is null, return null
            if (organization == null) {
                System.out.println("Cannot find enterprise for null organization");
                return null;
            }

            // Get the EcoSystem
            EcoSystem system = EcoSystem.getInstance();
            if (system == null || system.getNetworkList() == null) {
                System.out.println("System or network list is null");
                return null;
            }

            // For logisticsOrg, we know it's created in the LogisticsGroupEnterprise
            if (organization instanceof LogisticsOrganization && organization == logisticsOrg) {
                // Find the LogisticsGroupEnterprise first
                for (Network network : system.getNetworkList()) {
                    if (network.getEnterpriseDirectory() == null) {
                        continue;
                    }

                    for (Enterprise enterprise : network.getEnterpriseDirectory().getEnterpriseList()) {
                        if (enterprise instanceof LogisticsGroupEnterprise) {
                            if (enterprise.getOrganizationDirectory().getOrganizationList().contains(organization)) {
                                return enterprise;
                            }
                        }
                    }
                }
            }

            // General case: search for any organization in any enterprise
            for (Network network : system.getNetworkList()) {
                if (network.getEnterpriseDirectory() == null) {
                    continue;
                }

                for (Enterprise enterprise : network.getEnterpriseDirectory().getEnterpriseList()) {
                    if (enterprise.getOrganizationDirectory() == null) {
                        continue;
                    }

                    for (Organization org : enterprise.getOrganizationDirectory().getOrganizationList()) {
                        if (org == organization) {
                            return enterprise;
                        }
                    }
                }
            }

            System.out.println("Could not find parent enterprise for organization: " + organization.getName());
            return null;
        } catch (Exception e) {
            System.out.println("Error in findEnterpriseForOrganization: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private static void createWorkRequestsForCustomsDeclarations(CustomsLiaisonOrganization customsOrg) {
        if (customsOrg == null || customsOrg.getCustomsDeclarationDirectory() == null) {
            System.out.println("Cannot create work requests: organization or declaration directory is null");
            return;
        }

        System.out.println("Starting work request creation for customs declarations...");
        System.out.println("Total declarations: " + customsOrg.getCustomsDeclarationDirectory().getCustomsDeclarationList().size());

        int count = 0;
        for (CustomsDeclaration declaration : customsOrg.getCustomsDeclarationDirectory().getCustomsDeclarationList()) {
            // Skip Draft status declarations
            if ("Draft".equals(declaration.getStatus())) {
                System.out.println("Skipping Draft status declaration: " + declaration.getDeclarationId());
                continue;
            }

            // Check if work request already exists
            boolean requestExists = false;
            for (WorkRequest request : customsOrg.getWorkQueue().getWorkRequestList()) {
                if (request instanceof LogisticsWorkRequest) {
                    LogisticsWorkRequest logRequest = (LogisticsWorkRequest) request;
                    if (logRequest.getDeclarationId().equals(declaration.getDeclarationId())) {
                        requestExists = true;
                        System.out.println("Work request already exists: " + declaration.getDeclarationId());
                        break;
                    }
                }
            }

            // If not exists, create new work request
            if (!requestExists) {
                LogisticsWorkRequest request = new LogisticsWorkRequest();

                // Set basic information
                request.setDeclarationId(declaration.getDeclarationId());
                request.setShipmentId(declaration.getShipmentId());
                request.setDeclarationType(declaration.getDeclarationType());
                request.setConsignor(declaration.getConsignor());
                request.setConsignee(declaration.getConsignee());
                request.setCountryOfOrigin(declaration.getCountryOfOrigin());
                request.setDestinationCountry(declaration.getDestinationCountry());
                request.setNotes(declaration.getNotes());

                // Set status and date
                request.setStatus(declaration.getStatus());
                request.setRequestDate(declaration.getSubmissionDate() != null
                        ? declaration.getSubmissionDate() : declaration.getDeclarationDate());

                // Add to work queue
                customsOrg.getWorkQueue().getWorkRequestList().add(request);
                count++;
                System.out.println("Created new work request: " + declaration.getDeclarationId());
            }
        }

        System.out.println("Created " + count + " work requests, work queue now has "
                + customsOrg.getWorkQueue().getWorkRequestList().size() + " requests");

        // Save to system
        EcoSystem system = EcoSystem.getInstance();
        DB4OUtil.getInstance().storeSystem(system);
    }

    public static void initializeProductsWithFaker() {
        try {
            com.github.javafaker.Faker faker = new com.github.javafaker.Faker();
            Business.Supplier.Supplier supplier = Business.Role.MerchantRole.getDemoSupplier();
            
            if (supplier == null) {
                System.out.println("无法获取供应商实例，初始化产品失败");
                return;
            }
            
            // 添加智能手机
            for (int i = 0; i < 5; i++) {
                String brand = faker.options().option("Apple", "Samsung", "Google", "Xiaomi", "Huawei");
                String model = faker.options().option("iPhone 15", "Galaxy S23", "Pixel 7", "Mi 13", "P50 Pro");
                String productId = "SP-" + String.format("%03d", i + 1);
                String productName = brand + " " + model;
                double price = 499.99 + faker.number().randomDouble(2, 100, 800);
                int quantity = faker.number().numberBetween(20, 100);
                int threshold = faker.number().numberBetween(10, 20);
                
                Business.Product.Product phone = new Business.Product.Product(
                    productId,
                    productName,
                    price,
                    quantity,
                    threshold
                );
                phone.updateStockStatus();
                phone.setLastUpdated(new java.util.Date());
                supplier.addProduct(phone);
            }
            
            // 添加笔记本电脑
            for (int i = 0; i < 5; i++) {
                String brand = faker.options().option("Apple", "Dell", "Lenovo", "HP", "Asus");
                String model = faker.options().option("MacBook Pro", "XPS 15", "ThinkPad X1", "Spectre", "ZenBook");
                String productId = "LP-" + String.format("%03d", i + 1);
                String productName = brand + " " + model;
                double price = 899.99 + faker.number().randomDouble(2, 200, 1200);
                int quantity = faker.number().numberBetween(15, 50);
                int threshold = faker.number().numberBetween(5, 15);
                
                Business.Product.Product laptop = new Business.Product.Product(
                    productId,
                    productName,
                    price,
                    quantity,
                    threshold
                );
                laptop.updateStockStatus();
                laptop.setLastUpdated(new java.util.Date());
                supplier.addProduct(laptop);
            }
            
            // 添加智能手表
            for (int i = 0; i < 3; i++) {
                String brand = faker.options().option("Apple", "Samsung", "Garmin", "Fitbit", "Huawei");
                String model = faker.options().option("Watch Series 8", "Galaxy Watch 5", "Fenix 7", "Sense", "Watch GT");
                String productId = "SW-" + String.format("%03d", i + 1);
                String productName = brand + " " + model;
                double price = 249.99 + faker.number().randomDouble(2, 50, 350);
                int quantity = faker.number().numberBetween(25, 80);
                int threshold = faker.number().numberBetween(10, 20);
                
                Business.Product.Product watch = new Business.Product.Product(
                    productId,
                    productName,
                    price,
                    quantity,
                    threshold
                );
                watch.updateStockStatus();
                watch.setLastUpdated(new java.util.Date());
                supplier.addProduct(watch);
            }
            
            // 添加耳机
            for (int i = 0; i < 3; i++) {
                String brand = faker.options().option("Apple", "Sony", "Bose", "Samsung", "Sennheiser");
                String model = faker.options().option("AirPods Pro", "WH-1000XM5", "QuietComfort", "Galaxy Buds", "Momentum");
                String productId = "HP-" + String.format("%03d", i + 1);
                String productName = brand + " " + model;
                double price = 149.99 + faker.number().randomDouble(2, 30, 250);
                int quantity = faker.number().numberBetween(30, 100);
                int threshold = faker.number().numberBetween(15, 25);
                
                Business.Product.Product headphone = new Business.Product.Product(
                    productId,
                    productName,
                    price,
                    quantity,
                    threshold
                );
                headphone.updateStockStatus();
                headphone.setLastUpdated(new java.util.Date());
                supplier.addProduct(headphone);
            }
            
            // 添加平板电脑
            for (int i = 0; i < 3; i++) {
                String brand = faker.options().option("Apple", "Samsung", "Microsoft", "Lenovo", "Huawei");
                String model = faker.options().option("iPad Pro", "Galaxy Tab S8", "Surface Pro", "Tab M10", "MatePad Pro");
                String productId = "TB-" + String.format("%03d", i + 1);
                String productName = brand + " " + model;
                double price = 349.99 + faker.number().randomDouble(2, 100, 600);
                int quantity = faker.number().numberBetween(20, 60);
                int threshold = faker.number().numberBetween(8, 18);
                
                Business.Product.Product tablet = new Business.Product.Product(
                    productId,
                    productName,
                    price,
                    quantity,
                    threshold
                );
                tablet.updateStockStatus();
                tablet.setLastUpdated(new java.util.Date());
                supplier.addProduct(tablet);
            }
            
            System.out.println("成功使用Faker初始化了" + supplier.getProductCatalog().size() + "个科技产品");
            
        } catch (Exception e) {
            System.err.println("使用Faker初始化产品时发生错误: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
