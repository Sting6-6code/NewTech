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
    private String currentLocation;
    private String shipmentStatus;
    private Date estimatedDeliveryDate;
    private String sender;
    private String receiver;
    private double weight;
    private CustomsDeclaration customsDeclaration;
    private ArrayList<TrackingInfo> trackingHistory;
    private String orderId;
    private String productName;
    private int quantity;
    private PackageInfo packageInfo;
    private FinancialInfo financialInfo;

    // define shipmentStatus
    public static final String STATUS_PENDING = "Pending";           // 订单已创建，等待仓库处理
    public static final String STATUS_PROCESSING = "Processing";     // 仓库正在处理（拣货/包装）
    public static final String STATUS_SHIPPED = "Shipped";          // 已从仓库发出
    public static final String STATUS_IN_TRANSIT = "In Transit";    // 运输中
    public static final String STATUS_DELIVERING = "Delivering";    // 派送中
    public static final String STATUS_DELIVERED = "Delivered";      // 已送达
    public static final String STATUS_EXCEPTION = "Exception";      // 异常状态

    public Shipment() {
        this.trackingHistory = new ArrayList<>();
    }

    public Shipment(String shipmentId, String trackingNumber) {
        this.shipmentId = shipmentId;
        this.trackingNumber = trackingNumber;
        this.trackingHistory = new ArrayList<>();
        this.shipmentStatus = STATUS_PENDING;
        this.currentLocation = "Warehouse";
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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    public String getShipmentStatus() {
        return shipmentStatus;
    }

    public void setShipmentStatus(String shipmentStatus) {
        this.shipmentStatus = shipmentStatus;
        // 根据状态自动更新当前位置
        switch (shipmentStatus) {
            case STATUS_PENDING:
            case STATUS_PROCESSING:
                this.currentLocation = "Warehouse";
                break;
            case STATUS_SHIPPED:
                this.currentLocation = "Departed from Warehouse";
                break;
            case STATUS_IN_TRANSIT:
                // 保持当前位置不变，需要手动更新
                break;
            case STATUS_DELIVERING:
            case STATUS_DELIVERED:
                this.currentLocation = this.destination;
                break;
            case STATUS_EXCEPTION:
                // 保持当前位置不变，需要手动更新
                break;
        }

        // 添加跟踪记录
        addStatusTrackingInfo(shipmentStatus);
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    private void addStatusTrackingInfo(String newStatus) {
        TrackingInfo tracking = new TrackingInfo();
        tracking.setShipmentId(this.shipmentId);
        tracking.setTimestamp(new Date());
        tracking.setLocation(this.currentLocation);

        // 根据不同状态设置不同的描述
        switch (newStatus) {
            case STATUS_PENDING:
                tracking.setDescription("Order received, waiting for processing");
                break;
            case STATUS_PROCESSING:
                tracking.setDescription("Order is being processed in warehouse");
                break;
            case STATUS_SHIPPED:
                tracking.setDescription("Package has been shipped from warehouse");
                break;
            case STATUS_IN_TRANSIT:
                tracking.setDescription("Package is in transit at " + this.currentLocation);
                break;
            case STATUS_DELIVERING:
                tracking.setDescription("Package is out for delivery to " + this.destination);
                break;
            case STATUS_DELIVERED:
                tracking.setDescription("Package has been delivered to " + this.destination);
                break;
            case STATUS_EXCEPTION:
                tracking.setDescription("Exception occurred at " + this.currentLocation);
                break;
        }

        tracking.setStatus(newStatus);
        this.addTrackingInfo(tracking);
    }

    public void updateLocation(String newLocation) {
        this.currentLocation = newLocation;

        // 添加位置更新的跟踪记录
        TrackingInfo tracking = new TrackingInfo();
        tracking.setShipmentId(this.shipmentId);
        tracking.setTimestamp(new Date());
        tracking.setLocation(newLocation);
        tracking.setDescription("Package arrived at " + newLocation);
        tracking.setStatus(this.shipmentStatus);
        this.addTrackingInfo(tracking);
    }

    public PackageInfo getPackageInfo() {
        return packageInfo;
    }

    public void setPackageInfo(PackageInfo packageInfo) {
        this.packageInfo = packageInfo;
    }

    /**
     * Initialize package info with default values based on shipment data
     */
    public void initializePackageInfo() {
        if (this.packageInfo == null) {
            this.packageInfo = new PackageInfo();

            // Set default values based on existing product information
            this.packageInfo.setWeight(this.weight); // Use existing weight if available

            if (this.quantity > 0) {
                this.packageInfo.setItemCount(this.quantity);
                // If no weight was set, estimate based on quantity
                if (this.packageInfo.getWeight() <= 0) {
                    this.packageInfo.setWeight(this.quantity * 0.5); // Assume 0.5kg per item
                }
            }

            // Set default package type based on quantity
            if (this.quantity > 10) {
                this.packageInfo.setPackageType("Pallet");
            } else if (this.quantity > 3) {
                this.packageInfo.setPackageType("Box");
            } else {
                this.packageInfo.setPackageType("Envelope");
            }

            // Set default dimensions based on package type
            switch (this.packageInfo.getPackageType()) {
                case "Pallet":
                    this.packageInfo.setDimensions("100x100x120");
                    break;
                case "Box":
                    this.packageInfo.setDimensions("30x20x15");
                    break;
                case "Envelope":
                    this.packageInfo.setDimensions("35x25x2");
                    break;
                default:
                    this.packageInfo.setDimensions("20x15x10");
            }
        }
    }

    public FinancialInfo getFinancialInfo() {
        return financialInfo;
    }

    public void setFinancialInfo(FinancialInfo financialInfo) {
        this.financialInfo = financialInfo;
    }

    /**
     * Initialize financial info with default values based on shipment data
     */
    public void initializeFinancialInfo() {
        if (this.financialInfo == null) {
            this.financialInfo = new FinancialInfo();

            // Generate invoice number
            this.financialInfo.setInvoiceNumber("INV-" + System.currentTimeMillis());

            // Set default shipping cost based on shipping method
            if (this.shippingMethod != null) {
                switch (this.shippingMethod) {
                    case "Express":
                        this.financialInfo.setShippingCost(75.00);
                        this.financialInfo.setInsuranceCost(15.00);
                        break;
                    case "Air Freight":
                        this.financialInfo.setShippingCost(50.00);
                        this.financialInfo.setInsuranceCost(10.00);
                        break;
                    case "Sea Freight":
                        this.financialInfo.setShippingCost(30.00);
                        this.financialInfo.setInsuranceCost(5.00);
                        break;
                    case "Ground":
                        this.financialInfo.setShippingCost(20.00);
                        this.financialInfo.setInsuranceCost(3.00);
                        break;
                    default:
                        this.financialInfo.setShippingCost(25.00);
                }
            }

            // Estimate customs duties and taxes if international shipment
            if (this.destination != null
                    && !this.destination.contains("China")
                    && !this.destination.isEmpty()) {

                // Assume 10% customs duty for international shipments
                this.financialInfo.setCustomsDuties(10.00);
                this.financialInfo.setTaxes(5.00);
            }

            // Calculate total
            this.financialInfo.calculateTotal();
        }
    }

    @Override
    public String toString() {
        return shipmentId;
    }

}
