/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Order;

import java.util.Date;

/**
 *
 * @author zhuchenyan
 */
public class Order {
    private String requestId;
    private String productId; 
    private String productName;
    private double purchaseCost;
    private int quantity;
    private double totalAmount;
    private String supplier;
    private String status; // "Pending", "Processing", "Finished", "Rejected"
    private Date processDate;
    private Date createDate;
    private String supplierInfo;

    public Order() {
        this.status = "Pending";
        this.createDate = new Date();
    }

    // Getters and Setters
    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
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

    public double getPurchaseCost() {
        return purchaseCost;
    }

    public void setPurchaseCost(double purchaseCost) {
        this.purchaseCost = purchaseCost;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setProcessDate(Date date) {
        this.processDate = date;
    }

    public Date getProcessDate() {
        return processDate;
    }

    public void setCreateDate(Date date) {
        this.createDate = date;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setSupplierInfo(String supplierInfo) {
        this.supplierInfo = supplierInfo;
    }

    public String getSupplierInfo() {
        return supplierInfo;
    }
}
