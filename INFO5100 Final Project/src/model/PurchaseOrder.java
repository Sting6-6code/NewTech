/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author wangsiting
 */
public class PurchaseOrder {
    
    private String orderId;
    private String warehouseRequestId;
    private ArrayList<Product> products;
    private String status;  // "Pending", "Ordered", "Received"
    private Date orderDate;

    public PurchaseOrder(String orderId, String warehouseRequestId, 
                        ArrayList<Product> products) {
        this.orderId = orderId;
        this.warehouseRequestId = warehouseRequestId;
        this.products = products;
        this.status = "Pending";
        this.orderDate = new Date();
    }
    
    
    
    

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getWarehouseRequestId() {
        return warehouseRequestId;
    }

    public void setWarehouseRequestId(String warehouseRequestId) {
        this.warehouseRequestId = warehouseRequestId;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
    
    
    
}
