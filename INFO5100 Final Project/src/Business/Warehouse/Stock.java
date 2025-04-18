/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Warehouse;

import Business.Product.Product;
import java.util.HashMap;
import java.util.Date;

/**
 *
 * @author yushe
 */
public class Stock<K, V> {
//    private HashMap<Product, Integer> stockLevel;
//    
//    public Stock() {
//        stockLevel = new HashMap<>();
//    }
//    
//    public Stock(Product p, int amount) {
//        stockLevel = new HashMap<>();
//        stockLevel.put(p, amount);
//    }
//    
//    public int getAmount(Product p) {
//        if (stockLevel.containsKey(p)) {
//            return stockLevel.get(p);
//        }
//        return 0;
//    }
//    
//    public Product getProduct(Product p) {
//        if (stockLevel.containsKey(p)) {
//            return stockLevel.
//        }
//    }
   
    private Product product;
    private int amount;
    private Date lastUpdated;

    public Stock(Product p, int i) {
        this.product = p;
        this.amount = i;
        this.lastUpdated = new Date();
    }

    public Product getProduct() { return product; }
    public int getAmount() { return amount; }
    public Date getLastUpdated() { return lastUpdated; }
    
    public void setAmount(int amount) {
        this.amount = amount;
        this.lastUpdated = new Date();
    }
    
    public void addAmount(int quantity) {
        this.amount += quantity;
        this.lastUpdated = new Date();
    }
    
    public boolean reduceAmount(int quantity) {
        if (this.amount >= quantity) {
            this.amount -= quantity;
            this.lastUpdated = new Date();
            return true;
        }
        return false;
    }
    
    public String getStockStatus() {
        if (amount <= 0) return "Sold Out";
        else if (amount < 20) return "Low Stock";
        else if (amount <= 60) return "In Stock";
        else return "Excess";
    }
    
    // 检查产品是否可用于上架
    public boolean isAvailableForShelf() {
        return amount > 0;
    }
    
    // 检查是否需要补货
    public boolean needsRestock() {
        return amount < product.getWarningThreshold();
    }
    
    @Override
    public String toString() {
        return product.toString() + " (Stock: " + amount + ")";
    }
}
