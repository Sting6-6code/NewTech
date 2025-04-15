package Business.Product;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 * @author wangsiting
 */
public class SalesReportModel {
    
    private List<SalesRecord> salesRecords;
    private Date startDate;
    private Date endDate;
    
    public SalesReportModel() {
        this.salesRecords = new ArrayList<>();
    }
    
    public SalesReportModel(List<SalesRecord> salesRecords, Date startDate, Date endDate) {
        this.salesRecords = salesRecords;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    
    /**
     * 获取在日期范围内的销售记录
     * @return 过滤后的销售记录列表
     */
    private List<SalesRecord> getFilteredSalesRecords() {
        if (salesRecords == null || startDate == null || endDate == null) {
            return new ArrayList<>();
        }
        
        List<SalesRecord> filtered = new ArrayList<>();
        for (SalesRecord record : salesRecords) {
            if (record.getSaleDate().compareTo(startDate) >= 0 && 
                record.getSaleDate().compareTo(endDate) <= 0) {
                filtered.add(record);
            }
        }
        return filtered;
    }
    
    /**
     * 获取按月份汇总的销售数据
     */
    public Map<String, Double> getMonthlySales() {
        Map<String, Double> monthlySales = new TreeMap<>(); // 使用TreeMap保证月份顺序
        
        List<SalesRecord> filtered = getFilteredSalesRecords();
        if (filtered.isEmpty()) {
            return monthlySales;
        }
        
        SimpleDateFormat monthFormat = new SimpleDateFormat("yyyy-MM");
        
        for (SalesRecord record : filtered) {
            String monthKey = monthFormat.format(record.getSaleDate());
            monthlySales.merge(monthKey, record.getTotalAmount(), Double::sum);
        }
        
        return monthlySales;
    }
    
    /**
     * 获取总销售额
     */
    public double getTotalSales() {
        List<SalesRecord> filtered = getFilteredSalesRecords();
        return filtered.stream()
                .mapToDouble(SalesRecord::getTotalAmount)
                .sum();
    }
    
    /**
     * 获取按产品汇总的销售数据
     */
    public Map<String, Double> getSalesByProduct() {
        Map<String, Double> productSales = new HashMap<>();
        
        List<SalesRecord> filtered = getFilteredSalesRecords();
        for (SalesRecord record : filtered) {
            productSales.merge(record.getProductId(), record.getTotalAmount(), Double::sum);
        }
        
        return productSales;
    }
    
    /**
     * 获取销售数量最多的前N个产品
     */
    public Map<String, Integer> getTopSellingProducts(int n) {
        Map<String, Integer> productQuantities = new HashMap<>();
        
        List<SalesRecord> filtered = getFilteredSalesRecords();
        for (SalesRecord record : filtered) {
            productQuantities.merge(record.getProductId(), record.getQuantity(), Integer::sum);
        }
        
        // 转换为列表并排序
        List<Map.Entry<String, Integer>> sortedProducts = new ArrayList<>(productQuantities.entrySet());
        sortedProducts.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));
        
        // 获取前N个
        Map<String, Integer> topN = new LinkedHashMap<>();
        for (int i = 0; i < Math.min(n, sortedProducts.size()); i++) {
            Map.Entry<String, Integer> entry = sortedProducts.get(i);
            topN.put(entry.getKey(), entry.getValue());
        }
        
        return topN;
    }
    
    /**
     * 获取每日销售额
     */
    public Map<Date, Double> getDailySales() {
        Map<Date, Double> dailySales = new TreeMap<>(); // 使用TreeMap保证日期顺序
        
        List<SalesRecord> filtered = getFilteredSalesRecords();
        if (filtered.isEmpty()) {
            return dailySales;
        }
        
        Calendar calendar = Calendar.getInstance();
        for (SalesRecord record : filtered) {
            // 移除时间部分，只保留日期
            calendar.setTime(record.getSaleDate());
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            Date saleDate = calendar.getTime();
            
            dailySales.merge(saleDate, record.getTotalAmount(), Double::sum);
        }
        
        return dailySales;
    }
    
    /**
     * 获取平均每日销售额
     */
    public double getAverageDailySales() {
        Map<Date, Double> dailySales = getDailySales();
        if (dailySales.isEmpty()) {
            return 0.0;
        }
        
        double totalSales = dailySales.values().stream().mapToDouble(Double::doubleValue).sum();
        return totalSales / dailySales.size();
    }
    
    // Getters and Setters
    public List<SalesRecord> getSalesRecords() {
        return salesRecords;
    }
    
    public void setSalesRecords(List<SalesRecord> salesRecords) {
        this.salesRecords = salesRecords;
    }
    
    public Date getStartDate() {
        return startDate;
    }
    
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    
    public Date getEndDate() {
        return endDate;
    }
    
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}