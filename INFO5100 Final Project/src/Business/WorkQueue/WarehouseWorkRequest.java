/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.WorkQueue;

import java.util.Date;

/**
 *
 * @author zhuchenyan
 */
public class WarehouseWorkRequest extends WorkRequest {
    
    private String shipmentId;
    private String trackingNumber;
    private String productName;
    private int quantity;
    private String destination;
    private String shippingMethod;
    private Date estimatedDeliveryDate;
    
    public WarehouseWorkRequest() {
        super(); 
        this.setStatus("Pending");
    }
    
    // Getters and Setters
    public String getShipmentId() {
        return shipmentId;
    }
    
    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }
    
    public String getTrackingNumber() {
        return trackingNumber;
    }
    
    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }
    
    public String getProductName() {
        return productName;
    }
    
    public void setProductName(String productName) {
        this.productName = productName;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public String getDestination() {
        return destination;
    }
    
    public void setDestination(String destination) {
        this.destination = destination;
    }
    
    public String getShippingMethod() {
        return shippingMethod;
    }
    
    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }
    
    public Date getEstimatedDeliveryDate() {
        return estimatedDeliveryDate;
    }
    
    public void setEstimatedDeliveryDate(Date estimatedDeliveryDate) {
        this.estimatedDeliveryDate = estimatedDeliveryDate;
    }
    
    @Override
    public String toString() {
        return String.format("Shipment %s - %s to %s via %s", 
            shipmentId, productName, destination, shippingMethod);
    }
}