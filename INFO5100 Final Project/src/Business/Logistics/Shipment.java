/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Logistics;

/**
 *
 * @author zhuchenyan
 */
public class Shipment {
    private String shipmentId;
    private String trackingNumber;
    
    public Shipment(String shipmentId, String trackingNumber) {
        this.shipmentId = shipmentId;
        this.trackingNumber = trackingNumber;
    }
    
    public String getShipmentId() {
        return shipmentId;
    }
    
    public String getTrackingNumber() {
        return trackingNumber;
    }
}
