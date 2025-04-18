/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Warehouse;

import Business.Product.Product;
import Business.WorkQueue.ProcurementWorkRequest;
import Business.WorkQueue.WorkQueue;
import Business.WorkQueue.WorkRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author yushe
 */
public class Warehouse {
//    private HashMap<Product, Integer> stock;
    private ArrayList<Stock> stock;
    private static Warehouse warehouse; // 单例模式
    private WorkQueue workQueue; // 
    
    private Warehouse() {
        stock = new ArrayList<>();
        workQueue = new WorkQueue();
        System.out.println("New Warehouse instance created");
    }
    
    // 单例模式获取实例
    public static Warehouse getInstance() {
        if (warehouse == null) {
            warehouse = new Warehouse();
            System.out.println("Created new Warehouse instance");
        }
        return warehouse;
    }

//    public HashMap<Product, Integer> getStock() {
//        return stock;
//    }
    
    
//    
//    public String getProductName(Product p) {
//        if (stock.containsKey(p)) {
//            return p.getProductName();
//        } else {
//            return "Product not in stock";
//        }
//    }
//    
//    public void addStock(Product product, int amount) {
//        stock.put(product, stock.getOrDefault(product, 0) + amount); // amount start from 0 if nothing added, start from stock.get(product) if in stock
//    }
//    
//    public int getProductAmount(Product product) {
//        return stock.getOrDefault(product, 0);
//    }

    public ArrayList<Stock> getStock() {
        return stock;
    }
    
    // 添加商品库存
    public void addStock(Product product, int amount) {
        if (product == null) {
            System.out.println("Error: Cannot add null product to stock");
            return;
        }
        
        System.out.println("Adding stock - Product: " + product.getProductName() + 
                          " (ID: " + product.getProductId() + "), Amount: " + amount);
        
        // 检查是否已存在该产品
        for (Stock s : stock) {
            if (s.getProduct().getProductId().equals(product.getProductId())) {
                // 已存在，只需更新数量
                int newAmount = s.getAmount() + amount;
                stock.remove(s);
                stock.add(new Stock(product, newAmount));
                return;
            }
        }
        
        // 不存在，添加新产品
        stock.add(new Stock(product, amount));
        System.out.println("Added new product to stock");
    }
    
    // 获取指定产品库存数量
    public int getProductAmount(String productId) {
        if (productId == null || productId.trim().isEmpty()) {
            System.out.println("Error: Invalid product ID");
            return 0;
        }
        
        for (Stock s : stock) {
            if (s.getProduct().getProductId().equals(productId)) {
                return s.getAmount();
            }
        }
        
        System.out.println("Product not found: " + productId);
        
        return 0;
    }
    
    // 获取所有可用(库存>0)的产品
    public List<Product> getAvailableProducts() {
        List<Product> products = new ArrayList<>();
        for (Stock s : stock) {
            if (s.getAmount() > 0) {
                products.add(s.getProduct());
            }
        }
        return products;
    }
    
    // 获取特定类别的所有可用产品
    public List<Product> getAvailableProductsByType(String type) {
        List<Product> products = new ArrayList<>();
        for (Stock s : stock) {
            if (s.getAmount() > 0 && s.getProduct().getProductName().equals(type)) {
                products.add(s.getProduct());
            }
        }
        return products;
    }
    
    // 获取所有产品类型的列表(不重复)
    public List<String> getAllProductTypes() {
        List<String> types = new ArrayList<>();
        for (Stock s : stock) {
            String type = s.getProduct().getProductName();
            if (!types.contains(type)) {
                types.add(type);
            }
        }
        return types;
    }
    
    // 根据ID查找产品
    public Product findProductById(String productId) {
        for (Stock s : stock) {
            if (s.getProduct().getProductId().equals(productId)) {
                return s.getProduct();
            }
        }
        return null;
    }
    
    // 减少库存
    public boolean decreaseStock(String productId, int amount) {
        if (productId == null || productId.trim().isEmpty()) {
            System.out.println("Error: Invalid product ID");
            return false;
        }
        
        System.out.println("Attempting to decrease stock - Product ID: " + productId + 
                          ", Amount: " + amount);
        
        for (Stock s : stock) {
            if (s.getProduct().getProductId().equals(productId)) {
                if (s.getAmount() >= amount) {
                    // 库存足够
                    int newAmount = s.getAmount() - amount;
                    Product product = s.getProduct();
                    stock.remove(s);
                    Stock newStock = new Stock(product, newAmount);
                    stock.add(newStock);
                    
                    System.out.println("Stock decreased successfully - New amount: " + newAmount);
                    
                    // 检查是否需要补货并创建采购请求
                    checkAndCreateProcurementRequest(newStock);
                    
                    return true;
                } else {
                    System.out.println("Insufficient stock - Required: " + amount + 
                                     ", Available: " + s.getAmount());
                    // 库存不足
                    return false;
                }
            }
        }
        System.out.println("Product not found: " + productId);
        return false;
    }
    
    // 获取工作队列
    public WorkQueue getWorkQueue() {
        return workQueue;
    }
    
    // 设置工作队列
    public void setWorkQueue(WorkQueue workQueue) {
        this.workQueue = workQueue;
    }
    
    // 检查所有库存并创建采购请求
    public void checkAllInventoryLevels() {
        for (Stock s : stock) {
            checkAndCreateProcurementRequest(s);
        }
    }
    
    // 检查并创建采购请求
    private void checkAndCreateProcurementRequest(Stock stock) {
        if (stock.needsRestock()) {
            // 检查是否已经存在该产品的未处理请求
            boolean requestExists = false;
            for (WorkRequest request : workQueue.getWorkRequestList()) {
                if (request instanceof ProcurementWorkRequest) {
                    ProcurementWorkRequest procRequest = (ProcurementWorkRequest) request;
                    if (procRequest.getProductId().equals(stock.getProduct().getProductId()) 
                            && !procRequest.isProcessed()) {
                        requestExists = true;
                        break;
                    }
                }
            }
            
            // 如果没有未处理的请求，创建新请求
            if (!requestExists) {
                ProcurementWorkRequest request = new ProcurementWorkRequest();
                request.setProductId(stock.getProduct().getProductId());
                request.setProductName(stock.getProduct().getProductName());
                request.setCurrentAmount(stock.getAmount());
                request.setRequestedAmount(calculateRecommendedAmount(stock));
                
                // 创建清晰的请求消息
                String requestMessage = String.format(
                    "REQ-%s: Low stock alert for %s (Current: %d, Threshold: %d)",
                    stock.getProduct().getProductId(),
                    stock.getProduct().getProductName(),
                    stock.getAmount(),
                    stock.getProduct().getWarningThreshold()
                );
                
                request.setMessage(requestMessage);
                request.setStatus("Pending");
                
                workQueue.getWorkRequestList().add(request);
                System.out.println("Created procurement request for " + stock.getProduct().getProductName() + 
                                  " (Current: " + stock.getAmount() + ", Requested: " + request.getRequestedAmount() + ")");
            }
        }
    }
    
    // 计算建议采购数量
    private int calculateRecommendedAmount(Stock stock) {
        // 简单计算: 警告阈值的2倍减去当前库存
        return (stock.getProduct().getWarningThreshold() * 2) - stock.getAmount();
    }
    
    // 获取所有采购请求
    public List<ProcurementWorkRequest> getProcurementRequests() {
        List<ProcurementWorkRequest> result = new ArrayList<>();
        for (WorkRequest request : workQueue.getWorkRequestList()) {
            if (request instanceof ProcurementWorkRequest) {
                result.add((ProcurementWorkRequest) request);
            }
        }
        return result;
    }
    
    // 获取未处理的采购请求
    public List<ProcurementWorkRequest> getPendingProcurementRequests() {
        List<ProcurementWorkRequest> pendingRequests = new ArrayList<>();
        for (WorkRequest request : workQueue.getWorkRequestList()) {
            if (request instanceof ProcurementWorkRequest) {
                ProcurementWorkRequest procRequest = (ProcurementWorkRequest) request;
                if (!procRequest.isProcessed()) {
                    pendingRequests.add(procRequest);
                }
            }
        }
        return pendingRequests;
    }
    
    // 处理采购请求
    public void processProcurementRequest(String requestId, int actualAmount) {
        for (WorkRequest request : workQueue.getWorkRequestList()) {
            if (request instanceof ProcurementWorkRequest) {
                ProcurementWorkRequest procRequest = (ProcurementWorkRequest) request;
                if (procRequest.getMessage().contains(requestId)) {
                    procRequest.setProcessed(true);
                    procRequest.setActualAmount(actualAmount);
                    procRequest.setStatus("Completed");
                    procRequest.setResolveDate(new Date());
                    
                    // 更新库存
                    Product product = findProductById(procRequest.getProductId());
                    if (product != null) {
                        addStock(product, actualAmount);
                    }
                    break;
                }
            }
        }
    }
    
    // 开始定期库存检查
    public void startPeriodicInventoryCheck(int intervalSeconds) {
        Thread inventoryCheckThread = new Thread(() -> {
            while (true) {
                try {
                    // 检查所有产品库存
                    System.out.println("[" + new Date() + "] Performing periodic inventory check...");
                    checkAllInventoryLevels();
                    
                    // 显示当前库存状态
                    System.out.println("Current inventory status:");
                    for (Stock s : stock) {
                        String status = s.needsRestock() ? "LOW STOCK" : "OK";
                        System.out.println("- " + s.getProduct().getProductName() + 
                                          " (ID: " + s.getProduct().getProductId() + 
                                          "): " + s.getAmount() + " units [" + status + "]");
                    }
                    
                    // 等待指定时间间隔
                    Thread.sleep(intervalSeconds * 1000);
                } catch (InterruptedException e) {
                    System.out.println("Inventory check thread interrupted!");
                    break;
                } catch (Exception e) {
                    System.out.println("Error in inventory check: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
        
        // 设置为守护线程，不阻止应用程序退出
        inventoryCheckThread.setDaemon(true);
        inventoryCheckThread.start();
        
        System.out.println("Started periodic inventory check (every " + intervalSeconds + " seconds)");
    }
    
    // 打印库存状态
    public void printInventoryStatus() {
        System.out.println("\n=== Warehouse Inventory Status ===");
        System.out.println("Total products in stock: " + stock.size());
        for (Stock s : stock) {
            System.out.println(String.format("- %s (ID: %s): %d units [%s]",
                s.getProduct().getProductName(),
                s.getProduct().getProductId(),
                s.getAmount(),
                s.getStockStatus()));
        }
        System.out.println("===============================\n");
    }
    
    // 检查产品是否存在
    public boolean productExists(String productId) {
        return findProductById(productId) != null;
    }

    // 获取库存总数
    public int getTotalInventoryCount() {
        int total = 0;
        for (Stock s : stock) {
            total += s.getAmount();
        }
        return total;
    }

    // 获取低库存产品列表
    public List<Product> getLowStockProducts() {
        List<Product> lowStockProducts = new ArrayList<>();
        for (Stock s : stock) {
            if (s.needsRestock()) {
                lowStockProducts.add(s.getProduct());
            }
        }
        return lowStockProducts;
    }

    // 批量添加库存
    public void addBulkStock(List<Product> products, List<Integer> amounts) {
        if (products.size() != amounts.size()) {
            System.out.println("Error: Products and amounts lists must be the same size");
            return;
        }
        
        for (int i = 0; i < products.size(); i++) {
            addStock(products.get(i), amounts.get(i));
        }
    }
    
}
