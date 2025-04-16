/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Product;

import java.util.Date;

/**
 *
 * @author archil
 */
// Product.java - 产品基本模型
public class Product {
    private String productId;
    private String productName;
    private double price;
    private int quantity;
    private String stockStatus; // "Normal", "Low", "Excess"，"DOWNSHELF"
    private Date lastUpdated;
    private int warningThreshold; // 库存预警阈值
    private boolean onShelf; // 是否上架

    // 构造函数
    public Product(String productId, String productName, double price, int quantity, 
                  int warningThreshold) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.warningThreshold = warningThreshold;
        this.lastUpdated = new Date();
        updateStockStatus();
        this.onShelf = false;
    }

    // 更新库存状态
    public void updateStockStatus() {
        if (quantity <= warningThreshold) {
            stockStatus = "Low";
        } else if (quantity > warningThreshold * 3) { // 假设超过阈值3倍为过量
            stockStatus = "Excess";
        } else {
            stockStatus = "Normal";
        }
    }

    // 更新库存数量
    public void updateQuantity(int newQuantity) {
        this.quantity = newQuantity;
        this.lastUpdated = new Date();
        updateStockStatus();
    }

    // 商品上架
    public void upShelf() {
        this.onShelf = true;
    }

    // 商品下架
    public void downShelf() {
        this.setStockStatus("DOWNSHELF");
    }

    
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStockStatus() {
        return stockStatus;
    }

    public void setStockStatus(String stockStatus) {
        this.stockStatus = stockStatus;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public int getWarningThreshold() {
        return warningThreshold;
    }

    public void setWarningThreshold(int warningThreshold) {
        this.warningThreshold = warningThreshold;
    }

    public boolean isOnShelf() {
        return onShelf;
    }

    public void setOnShelf(boolean onShelf) {
        this.onShelf = onShelf;
    }

    public Object getName() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Object getId() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}




