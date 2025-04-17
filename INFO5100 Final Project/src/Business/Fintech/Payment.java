/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Fintech;

import Business.Logistics.Shipment;


public class Payment<K, V> {
    private Shipment shipment;
    private double cost;
    private static int paymentID = 1;
    public Payment(Shipment s, double cost) {
        shipment = s;
        this.cost = cost;
        paymentID++;
    }

    public Shipment getShipment() {
        return shipment;
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public static int getPaymentID() {
        return paymentID;
    }
    
    @Override
    public String toString() {
        return String.valueOf(paymentID);
    }
}
