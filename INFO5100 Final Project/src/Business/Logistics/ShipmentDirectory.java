/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Logistics;

import java.util.ArrayList;

/**
 *
 * @author zhuchenyan
 */
public class ShipmentDirectory {
 
    private ArrayList<Shipment> shipments;
    
    public ShipmentDirectory() {
        shipments = new ArrayList<>();
    }
    
    public ArrayList<Shipment> getShipments() {
        return shipments;
    }
    
    public Shipment createShipment(String shipmentId, String trackingNumber) {
        Shipment shipment = new Shipment(shipmentId, trackingNumber);
        shipments.add(shipment);
        return shipment;
    }
    
    public void removeShipment(Shipment shipment) {
        shipments.remove(shipment);
    }
    
    public Shipment findShipment(String shipmentId) {
        for (Shipment shipment : shipments) {
            if (shipment.getShipmentId().equals(shipmentId)) {
                return shipment;
            }
        }
        return null;
    }
    
    public Shipment findShipmentByTrackingNumber(String trackingNumber) {
        for (Shipment shipment : shipments) {
            if (shipment.getTrackingNumber().equals(trackingNumber)) {
                return shipment;
            }
        }
        return null;
    }
}
