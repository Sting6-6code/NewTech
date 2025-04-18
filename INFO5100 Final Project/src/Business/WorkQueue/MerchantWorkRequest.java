/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.WorkQueue;

import java.util.Date;

/**
 *
 * @author wangsiting
 */
public class MerchantWorkRequest extends WorkRequest {
    
    private String productId;
    private String productName;
    private int requestedAmount;
    private double price;
    private Date requestDate;
    
    public MerchantWorkRequest() {
        super();
        this.requestDate = new Date();
        this.setStatus("Pending"); // 初始状态为待处理
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
    
    public int getRequestedAmount() {
        return requestedAmount;
    }
    
    public void setRequestedAmount(int requestedAmount) {
        this.requestedAmount = requestedAmount;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    public Date getRequestDate() {
        return requestDate;
    }
    
    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }
    
    @Override
    public String toString() {
        return productName;
    }
}
