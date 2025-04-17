/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Role;

import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Logistics.CustomsDeclaration;
import Business.Logistics.Shipment;
import Business.Logistics.ShipmentDirectory;
import Business.Logistics.TrackingInfo;
import Business.Organization.LogisticsOrganization;
import Business.Organization.Organization;
import Business.UserAccount.UserAccount;
import ui.LogisticsRole.LogisticsCoordinatorHP;

import javax.swing.JPanel;
import java.util.Date;

/**
 *
 * @author zhuchenyan
 */
public class LogisticsCoordinatorRole extends Role {
    
    public LogisticsCoordinatorRole() {
    }
    
//    @Override
//    public JPanel createWorkArea(JPanel userProcessContainer, UserAccount account, Organization organization, Enterprise enterprise, EcoSystem business) {
//        ui.LogisticsRole.LogisticsCoordinatorHP logisticsHP = new ui.LogisticsRole.LogisticsCoordinatorHP();
//        return logisticsHP;
//    }
    
//    @Override
//    public JPanel createWorkArea(JPanel userProcessContainer, UserAccount account, Organization organization, Enterprise enterprise, EcoSystem business) {
//        return new LogisticsCoordinatorHP(userProcessContainer, account, enterprise, (LogisticsOrganization)organization);
//    }
//    
    @Override
    public JPanel createWorkArea(JPanel userProcessContainer, UserAccount account, Organization organization, Enterprise enterprise, EcoSystem business) {
    if (organization == null || !(organization instanceof LogisticsOrganization)) {
        // 如果 organization 为空或类型不匹配,创建一个新的 LogisticsOrganization
        LogisticsOrganization logisticsOrg = new LogisticsOrganization();
        return new LogisticsCoordinatorHP(userProcessContainer, account, enterprise, logisticsOrg);
    } else {
        // 如果 organization 存在且类型正确,直接使用
        return new LogisticsCoordinatorHP(userProcessContainer, account, enterprise, (LogisticsOrganization)organization);
    }
}
    
    /**
     * 创建新的货件
     */
    public Shipment createShipment(LogisticsOrganization organization, String shipmentId, 
                                String trackingNumber, Date shipDate, String shippingMethod,
                                String origin, String destination, String sender, String receiver,
                                double weight) {
        
        ShipmentDirectory directory = organization.getShipmentDirectory();
        Shipment shipment = directory.createShipment(shipmentId, trackingNumber);
        
        // 设置基本信息
        shipment.setShipDate(shipDate);
        shipment.setShippingMethod(shippingMethod);
        shipment.setOrigin(origin);
        shipment.setDestination(destination);
        shipment.setSender(sender);
        shipment.setReceiver(receiver);
        shipment.setWeight(weight);
        shipment.setShipmentStatus("Pending");
        
        // 计算预计送达日期 (简单估计：海运30天，空运7天，其他15天)
        Date estimatedDate = new Date(shipDate.getTime());
        if ("Sea Freight".equalsIgnoreCase(shippingMethod)) {
            estimatedDate.setTime(shipDate.getTime() + 30L * 24 * 60 * 60 * 1000);
        } else if ("Air Freight".equalsIgnoreCase(shippingMethod)) {
            estimatedDate.setTime(shipDate.getTime() + 7L * 24 * 60 * 60 * 1000);
        } else {
            estimatedDate.setTime(shipDate.getTime() + 15L * 24 * 60 * 60 * 1000);
        }
        shipment.setEstimatedDeliveryDate(estimatedDate);
        
        // 创建第一条跟踪记录
        TrackingInfo initialTrack = new TrackingInfo();
        initialTrack.setShipmentId(shipmentId);
        initialTrack.setTimestamp(shipDate);
        initialTrack.setLocation(origin);
        initialTrack.setDescription("Shipment created");
        initialTrack.setStatus("Completed");
        shipment.addTrackingInfo(initialTrack);
        
        return shipment;
    }
    
    /**
     * 更新货件状态并添加跟踪信息
     */
    public void updateShipmentStatus(Shipment shipment, String newStatus, 
                                  String location, String description) {
        if (shipment != null) {
            // 更新货件状态
            shipment.setShipmentStatus(newStatus);
            
            // 添加新的跟踪记录
            TrackingInfo tracking = new TrackingInfo();
            tracking.setShipmentId(shipment.getShipmentId());
            tracking.setTimestamp(new Date()); // 当前时间
            tracking.setLocation(location);
            tracking.setDescription(description);
            tracking.setStatus("Completed");
            shipment.addTrackingInfo(tracking);
        }
    }
    
    /**
     * 创建海关申报单
     */
    public CustomsDeclaration createCustomsDeclaration(Shipment shipment, String declarationId,
                                                   String consignor, String consignee,
                                                   String countryOfOrigin, String destinationCountry) {
        if (shipment != null) {
            CustomsDeclaration declaration = new CustomsDeclaration();
            declaration.setDeclarationId(declarationId);
            declaration.setShipmentId(shipment.getShipmentId());
            declaration.setDeclarationDate(new Date());
            declaration.setStatus("Pending");
            declaration.setConsignor(consignor);
            declaration.setConsignee(consignee);
            declaration.setCountryOfOrigin(countryOfOrigin);
            declaration.setDestinationCountry(destinationCountry);
            
            // 关联到货件
            shipment.setCustomsDeclaration(declaration);
            
            return declaration;
        }
        return null;
    }
    
    /**
     * 添加商品项到海关申报单
     */
    public void addItemToDeclaration(CustomsDeclaration declaration, String description,
                                  String hsCode, int quantity, String unit,
                                  double unitValue, double grossWeight) {
        if (declaration != null) {
            CustomsDeclaration.CustomsLineItem item = new CustomsDeclaration.CustomsLineItem();
            item.setDescription(description);
            item.setHsCode(hsCode);
            item.setQuantity(quantity);
            item.setUnit(unit);
            item.setUnitValue(unitValue);
            item.setGrossWeight(grossWeight);
            
            declaration.addItem(item);
        }
    }
    
    /**
     * 提交海关申报单
     */
    public void submitCustomsDeclaration(CustomsDeclaration declaration) {
        if (declaration != null) {
            declaration.setStatus("Submitted");
            declaration.setCustomsOffice("Shanghai Customs"); // 默认海关办公室
            // 记录到业务系统
            // ...
        }
    }
    
    /**
     * 搜索货件
     */
    public Shipment searchShipment(LogisticsOrganization organization, String trackingNumber) {
        return organization.getShipmentDirectory().findShipmentByTrackingNumber(trackingNumber);
    }
    
    /**
     * 生成物流报告
     */
    public String generateShipmentReport(Shipment shipment) {
        if (shipment == null) {
            return "Invalid shipment";
        }
        
        StringBuilder report = new StringBuilder();
        report.append("SHIPMENT REPORT\n");
        report.append("==============\n\n");
        report.append("Shipment ID: ").append(shipment.getShipmentId()).append("\n");
        report.append("Tracking Number: ").append(shipment.getTrackingNumber()).append("\n");
        report.append("Shipping Date: ").append(shipment.getShipDate()).append("\n");
        report.append("Method: ").append(shipment.getShippingMethod()).append("\n");
        report.append("Origin: ").append(shipment.getOrigin()).append("\n");
        report.append("Destination: ").append(shipment.getDestination()).append("\n");
        report.append("Current Status: ").append(shipment.getShipmentStatus()).append("\n");
        report.append("Estimated Delivery: ").append(shipment.getEstimatedDeliveryDate()).append("\n\n");
        
        report.append("Sender: ").append(shipment.getSender()).append("\n");
        report.append("Receiver: ").append(shipment.getReceiver()).append("\n");
        report.append("Weight: ").append(shipment.getWeight()).append(" kg\n\n");
        
        // 添加海关信息
        CustomsDeclaration customsDec = shipment.getCustomsDeclaration();
        if (customsDec != null) {
            report.append("CUSTOMS INFORMATION\n");
            report.append("-------------------\n");
            report.append("Declaration ID: ").append(customsDec.getDeclarationId()).append("\n");
            report.append("Declaration Date: ").append(customsDec.getDeclarationDate()).append("\n");
            report.append("Status: ").append(customsDec.getStatus()).append("\n\n");
        }
        
        // 添加跟踪历史
        report.append("TRACKING HISTORY\n");
        report.append("---------------\n");
        for (TrackingInfo info : shipment.getTrackingHistory()) {
            report.append(info.getTimestamp()).append(" | ");
            report.append(info.getLocation()).append(" | ");
            report.append(info.getDescription()).append("\n");
        }
        
        return report.toString();
    }
    
    /**
     * 检查是否存在需要处理的紧急货件
     */
    public int countPendingUrgentShipments(LogisticsOrganization organization) {
        int count = 0;
        for (Shipment shipment : organization.getShipmentDirectory().getShipments()) {
            if ("Urgent".equals(shipment.getShipmentStatus()) || "Priority".equals(shipment.getShipmentStatus())) {
                count++;
            }
        }
        return count;
    }
    
    /**
     * 获取待处理的海关申报单数量
     */
    public int countPendingCustomsDeclarations(LogisticsOrganization organization) {
        int count = 0;
        for (Shipment shipment : organization.getShipmentDirectory().getShipments()) {
            CustomsDeclaration declaration = shipment.getCustomsDeclaration();
            if (declaration != null && "Pending".equals(declaration.getStatus())) {
                count++;
            }
        }
        return count;
    }
    
    @Override
    public String toString() {
        return "Logistics Coordinator";
    }
}
