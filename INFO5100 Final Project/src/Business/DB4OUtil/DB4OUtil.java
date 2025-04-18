package Business.DB4OUtil;

import Business.ConfigureASystem;
import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Enterprise.LogisticsGroupEnterprise;
import Business.Logistics.Shipment;
import Business.Logistics.ShipmentDirectory;
import Business.Logistics.TrackingInfo;
import Business.Network.Network;
import Business.Organization.LogisticsOrganization;
import Business.Organization.Organization;
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.ta.TransparentPersistenceSupport;
import java.io.File;
import java.nio.file.Paths;

/**
 *
 * @author rrheg
 * @author Lingfeng
 */
public class DB4OUtil {

    private static final String FILENAME = Paths.get("Databank.db4o").toAbsolutePath().toString();// path to the data store
    private static DB4OUtil dB4OUtil;

    public synchronized static DB4OUtil getInstance() {
        if (dB4OUtil == null) {
            dB4OUtil = new DB4OUtil();
        }
        return dB4OUtil;
    }

    protected synchronized static void shutdown(ObjectContainer conn) {
        if (conn != null) {
            conn.close();
        }
    }

    private ObjectContainer createConnection() {
        try {
//            // 输出数据库文件路径
//            System.out.println("数据库文件路径: " + FILENAME);
//
//            // 检查数据库文件是否存在
//            File dbFile = new File(FILENAME);
//            if (dbFile.exists()) {
//                System.out.println("数据库文件已存在，大小: " + dbFile.length() + " 字节");
//            } else {
//                System.out.println("数据库文件不存在，将创建新文件");
//            }

            File file = new File(FILENAME);
            System.out.println("Database file path: " + file.getAbsolutePath());
            if (!file.exists()) {
                System.out.println("Database file does not exist, will create new file");
            } else {
                System.out.println("Database file exists, size: " + file.length() + " bytes");
            }

            EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
            config.common().add(new TransparentPersistenceSupport());
            //Controls the number of objects in memory
            config.common().activationDepth(Integer.MAX_VALUE);
            //Controls the depth/level of updation of Object
            config.common().updateDepth(Integer.MAX_VALUE);

            //Register your top most Class here
            config.common().objectClass(EcoSystem.class).cascadeOnUpdate(true); // Change to the object you want to save
            config.common().objectClass(Network.class).cascadeOnUpdate(true);
            config.common().objectClass(Enterprise.class).cascadeOnUpdate(true);
            config.common().objectClass(Organization.class).cascadeOnUpdate(true);
            config.common().objectClass(LogisticsOrganization.class).cascadeOnUpdate(true);
            config.common().objectClass(ShipmentDirectory.class).cascadeOnUpdate(true);
            config.common().objectClass(Shipment.class).cascadeOnUpdate(true);
            config.common().objectClass(TrackingInfo.class).cascadeOnUpdate(true);

            // 禁用枚举类型的序列化
            config.common().objectClass(Enum.class).storeTransientFields(false);

            ObjectContainer db = Db4oEmbedded.openFile(config, FILENAME);
            return db;
        } catch (Exception ex) {
            System.out.println("数据库连接错误: " + ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }

    public synchronized void storeSystem(EcoSystem system) {
        if (system == null) {
            System.out.println("错误: 尝试存储空系统");
            return;
        }

        try {
            ObjectContainer conn = createConnection();
            if (conn == null) {
                System.out.println("错误: 无法创建数据库连接");
                return;
            }

            System.out.println("正在存储系统，网络数量: "
                    + (system.getNetworkList() != null ? system.getNetworkList().size() : 0));

            conn.store(system);
            conn.commit();
            conn.close();
            System.out.println("系统数据已保存到数据库");
        } catch (Exception e) {
            System.out.println("存储系统时出错: " + e.getMessage());
            e.printStackTrace();
        }
    }

    
    public EcoSystem retrieveSystem() {

        ObjectContainer conn = createConnection();
    EcoSystem system;
    
    try {
        ObjectSet<EcoSystem> systems = conn.query(EcoSystem.class);
        System.out.println("Retrieved " + systems.size() + " systems from database");
        
        if (systems.size() == 0) {
            // Create a new system if none exists
            system = ConfigureASystem.configure();
            
            // Store the newly created system
            conn.store(system);
            conn.commit();
            System.out.println("Created new system and stored in database");
        } else {
            // Load the existing system
            system = systems.get(systems.size() - 1);
            System.out.println("Successfully loaded existing system");
            
            // Find LogisticsOrganization in loaded system
            LogisticsOrganization foundLogistics = findLogisticsOrganization(system);
            
            if (foundLogistics != null) {
                // Set the static reference
                ConfigureASystem.logisticsOrg = foundLogistics;
                System.out.println("Found LogisticsOrganization with " + 
                    (foundLogistics.getShipmentDirectory() != null ? 
                        foundLogistics.getShipmentDirectory().getShipments().size() : 0) + 
                    " shipments");
            } else {
                // Create a completely new system
                System.out.println("LogisticsOrganization not found, creating new system");
                system = ConfigureASystem.configure();
                
                // Store the newly created system
                conn.store(system);
                conn.commit();
                System.out.println("Created new system and stored in database");
            }
        }
    } catch (Exception ex) {
        System.out.println("Error retrieving system: " + ex.getMessage());
        ex.printStackTrace();
        system = ConfigureASystem.configure();
    } finally {
        conn.close();
    }
    

  
        return system;
//        try {
//            ObjectContainer conn = createConnection();
//            if (conn == null) {
//                System.out.println("错误: 无法创建数据库连接，返回新配置的系统");
//                return ConfigureASystem.configure();
//            }
//            
//            ObjectSet<EcoSystem> systems = conn.query(EcoSystem.class);
//            System.out.println("从数据库检索到 " + systems.size() + " 个系统记录");
//            
//            EcoSystem system;
//            if (systems.isEmpty()) {
//                System.out.println("数据库中没有系统记录，创建新系统");
//                system = ConfigureASystem.configure();
//            } else {
//                system = systems.get(systems.size() - 1);
//                
//                // 验证系统对象的完整性
//                if (system.getNetworkList() == null || system.getNetworkList().isEmpty()) {
//                    System.out.println("从数据库加载的系统不完整，重新配置系统");
//                    system = ConfigureASystem.configure();
//                } else {
//                    System.out.println("成功加载系统，网络数量: " + system.getNetworkList().size());
//                }
//            }
//            
//            conn.close();
//            return system;
//        } catch (Exception e) {
//            System.out.println("检索系统时出错: " + e.getMessage());
//            e.printStackTrace();
//            return ConfigureASystem.configure();
//        }

    }
    
    private LogisticsOrganization findLogisticsOrganization(EcoSystem system) {
    if (system.getNetworkList() != null) {
        for (Network network : system.getNetworkList()) {
            if (network.getEnterpriseDirectory() != null) {
                for (Enterprise enterprise : network.getEnterpriseDirectory().getEnterpriseList()) {
                    if (enterprise.getOrganizationDirectory() != null) {
                        for (Organization org : enterprise.getOrganizationDirectory().getOrganizationList()) {
                            if (org instanceof LogisticsOrganization) {
                                return (LogisticsOrganization) org;
                            }
                        }
                    }
                }
            }
        }
    }
    return null;
}
    
}
