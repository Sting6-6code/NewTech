/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Logistics;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author zhuchenyan
 */
public class Shipment {
    
    private String shipmentId;
    private String trackingNumber;
    private Date shipDate;
    private String shippingMethod;
    private String origin;
    private String destination;
    private String status;
    private Date estimatedDeliveryDate;
    private String sender;
    private String receiver;
    private double weight;
    private CustomsDeclaration customsDeclaration;
    private ArrayList<TrackingInfo> trackingHistory;
    
    public Shipment() {
        this.trackingHistory = new ArrayList<>();
    }
    
    public Shipment(String shipmentId, String trackingNumber) {
        this.shipmentId = shipmentId;
        this.trackingNumber = trackingNumber;
        this.trackingHistory = new ArrayList<>();
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

    public Date getShipDate() {
        return shipDate;
    }

    public void setShipDate(Date shipDate) {
        this.shipDate = shipDate;
    }
    
    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getEstimatedDeliveryDate() {
        return estimatedDeliveryDate;
    }

    public void setEstimatedDeliveryDate(Date estimatedDeliveryDate) {
        this.estimatedDeliveryDate = estimatedDeliveryDate;
    }
    
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
    
    public CustomsDeclaration getCustomsDeclaration() {
        return customsDeclaration;
    }

    public void setCustomsDeclaration(CustomsDeclaration customsDeclaration) {
        this.customsDeclaration = customsDeclaration;
    }
    
    public ArrayList<TrackingInfo> getTrackingHistory() {
        return trackingHistory;
    }
    
    // Method to add tracking info
    public void addTrackingInfo(TrackingInfo info) {
        if (info != null) {
            this.trackingHistory.add(info);
        }
    }
    
    @Override
    public String toString() {
        return shipmentId;
    }
    
}
