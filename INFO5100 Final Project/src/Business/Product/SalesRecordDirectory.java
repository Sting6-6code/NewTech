/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Product;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author wangsiting
 */
public class SalesRecordDirectory {
    
    private ArrayList<SalesRecord> salesRecordList;
    private static int counter = 1; // For generating unique sales record IDs
    
    public SalesRecordDirectory() {
        salesRecordList = new ArrayList<>();
    }
    
    public ArrayList<SalesRecord> getSalesRecordList() {
        return salesRecordList;
    }
    
    public void setSalesRecordList(ArrayList<SalesRecord> salesRecordList) {
        this.salesRecordList = salesRecordList;
    }
    
    public SalesRecord createSalesRecord(String productId, String productName, double price, 
            int quantity, Date saleDate, String customerId) {
        
        double totalAmount = price * quantity;
        SalesRecord salesRecord = new SalesRecord(
                counter++, 
                productId, 
                productName, 
                price, 
                quantity, 
                totalAmount, 
                saleDate, 
                customerId
        );
        
        salesRecordList.add(salesRecord);
        return salesRecord;
    }
    
    public void removeSalesRecord(SalesRecord salesRecord) {
        salesRecordList.remove(salesRecord);
    }
    
    public SalesRecord findSalesRecordById(int id) {
        for (SalesRecord salesRecord : salesRecordList) {
            if (salesRecord.getId() == id) {
                return salesRecord;
            }
        }
        return null;
    }
    
    public List<SalesRecord> findSalesRecordsByProductId(String productId) {
        return salesRecordList.stream()
                .filter(sr -> sr.getProductId().equals(productId))
                .collect(Collectors.toList());
    }
    
    public List<SalesRecord> findSalesRecordsByCustomerId(String customerId) {
        return salesRecordList.stream()
                .filter(sr -> sr.getCustomerId().equals(customerId))
                .collect(Collectors.toList());
    }
    
    public List<SalesRecord> findSalesRecordsInDateRange(Date startDate, Date endDate) {
        return salesRecordList.stream()
                .filter(sr -> !sr.getSaleDate().before(startDate) && !sr.getSaleDate().after(endDate))
                .collect(Collectors.toList());
    }
    
    public double getTotalSalesAmount() {
        return salesRecordList.stream()
                .mapToDouble(SalesRecord::getTotalAmount)
                .sum();
    }
    
    public int getTotalQuantitySold() {
        return salesRecordList.stream()
                .mapToInt(SalesRecord::getQuantity)
                .sum();
    }
} 