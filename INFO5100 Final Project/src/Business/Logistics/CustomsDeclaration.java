/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Logistics;

import Business.Product.Product;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author zhuchenyan
 */
public class CustomsDeclaration {

    private String declarationId;
    private String shipmentId;
    private Date declarationDate;
    private String status;// "Pending", "Approved", "Rejected"
    private Date submissionDate;
    private String declarationNumber;
    private String consignor;
    private String consignee;
    private String countryOfOrigin;
    private String destinationCountry;
    private ArrayList<CustomsLineItem> items;
    private String customsOffice;
    private Date processingDate;
    private String notes;
    private String declarationType;
    private String hsCode;
    

    public CustomsDeclaration() {
        this.declarationDate = new Date();
        this.status = "Pending";
        this.items = new ArrayList<>();
    }

    public CustomsDeclaration(String declarationNumber) {
        this.declarationNumber = declarationNumber;
        this.status = "Pending";
        this.submissionDate = new Date();
        this.items = new ArrayList<>();
    }

    // 从仓库产品创建报关单项目的方法
    public void addItemFromWarehouseProduct(Product product, int quantity) {
        if (product == null) {
            return;
        }

        CustomsLineItem item = new CustomsLineItem();
        item.setDescription(product.getProductName());
        item.setQuantity(quantity);
        item.setUnit("PCS"); // 或根据产品类型设置不同单位
        item.setUnitValue(product.getPrice());
        item.setTotalValue(product.getPrice() * quantity);
        // 假设产品重量为0.5kg,可以根据实际情况修改
        item.setGrossWeight(0.5 * quantity);

        // 添加到报关单
        this.addItem(item);
    }

    // Getters and Setters
    public String getDeclarationId() {
        return declarationId;
    }

    public void setDeclarationId(String declarationId) {
        this.declarationId = declarationId;
    }

    public String getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }

    public Date getDeclarationDate() {
        return declarationDate;
    }

    public void setDeclarationDate(Date declarationDate) {
        this.declarationDate = declarationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getConsignor() {
        return consignor;
    }

    public void setConsignor(String consignor) {
        this.consignor = consignor;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    public String getDestinationCountry() {
        return destinationCountry;
    }

    public void setDestinationCountry(String destinationCountry) {
        this.destinationCountry = destinationCountry;
    }

    public ArrayList<CustomsLineItem> getItems() {
        return items;
    }

    public void addItem(CustomsLineItem item) {
        if (item != null) {
            this.items.add(item);
        }
    }

    public String getCustomsOffice() {
        return customsOffice;
    }

    public void setCustomsOffice(String customsOffice) {
        this.customsOffice = customsOffice;
    }

    public Date getProcessingDate() {
        return processingDate;
    }

    public void setProcessingDate(Date processingDate) {
        this.processingDate = processingDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(Date submissionDate) {
        this.submissionDate = submissionDate;
    }

    public void setDeclarationNumber(String declarationNumber) {
        this.declarationNumber = declarationNumber;
    }

    public void setItems(ArrayList<CustomsLineItem> items) {
        this.items = items;
    }

    public String getDeclarationNumber() {
        return declarationNumber;
    }

    public String getDeclarationType() {
        return declarationType;
    }

    public void setDeclarationType(String declarationType) {
        this.declarationType = declarationType;
    }

    public String getHsCode() {
        return hsCode;
    }

    public void setHsCode(String hsCode) {
        this.hsCode = hsCode;
    }

    @Override
    public String toString() {
        return declarationId;
    }

    // Inner class for customs line items
    public static class CustomsLineItem {

        private String description;
        private String hsCode;
        private int quantity;
        private String unit;
        private double unitValue;
        private double totalValue;
        private double grossWeight;

        // Getters and Setters
        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getHsCode() {
            return hsCode;
        }

        public void setHsCode(String hsCode) {
            this.hsCode = hsCode;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public double getUnitValue() {
            return unitValue;
        }

        public void setUnitValue(double unitValue) {
            this.unitValue = unitValue;
            this.totalValue = this.quantity * unitValue;
        }

        public double getTotalValue() {
            return totalValue;
        }

        public void setTotalValue(double totalValue) {
            this.totalValue = totalValue;
        }

        public double getGrossWeight() {
            return grossWeight;
        }

        public void setGrossWeight(double grossWeight) {
            this.grossWeight = grossWeight;
        }
    }
}
