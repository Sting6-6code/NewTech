/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Logistics;

import Business.Product.Product;
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

    public void addShipment(Shipment shipment) {
        if (shipment != null && !shipments.contains(shipment)) {
            shipments.add(shipment);
        }
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

    public Shipment findShipmentByOrderId(String orderId) {
        for (Shipment shipment : shipments) {
            if (shipment.getOrderId() != null && shipment.getOrderId().equals(orderId)) {
                return shipment;
            }
        }
        return null;
    }

    // 从仓库创建货件
    public Shipment createShipmentFromWarehouse(String orderId, Product product, int quantity) {
        // 生成新的货件ID和跟踪号
        String shipmentId = "SHP" + String.format("%06d", shipments.size() + 1);
        String trackingNumber = "TRK" + String.format("%06d", shipments.size() + 1);

        // 创建新货件
        Shipment shipment = new Shipment(shipmentId, trackingNumber);

        // 设置货件基本信息
        shipment.setOrderId(orderId);
        shipment.setProductName(product.getProductName());
        shipment.setQuantity(quantity);
        shipment.setShipmentStatus(Shipment.STATUS_PENDING);
        shipment.setCurrentLocation("Warehouse");
        shipment.setOrigin("Warehouse");

        // 添加到货件列表
        shipments.add(shipment);

        return shipment;
    }
}
