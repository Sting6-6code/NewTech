/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Logistics;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author zhuchenyan
 */
public class FinancialInfo implements Serializable {
    private double shippingCost;
    private double insuranceCost;
    private double customsDuties;
    private double taxes;
    private double otherCharges;
    private double totalCost;
    private String paymentStatus; // Unpaid, Partially Paid, Paid, Refunded
    private Date paidDate;
    private String invoiceNumber;
    
    public FinancialInfo() {
        this.shippingCost = 0;
        this.insuranceCost = 0;
        this.customsDuties = 0;
        this.taxes = 0;
        this.otherCharges = 0;
        this.totalCost = 0;
        this.paymentStatus = "Unpaid";
        this.paidDate = null;
        this.invoiceNumber = "";
    }
    
    // Getters and Setters
    public double getShippingCost() {
        return shippingCost;
    }
    
    public void setShippingCost(double shippingCost) {
        this.shippingCost = shippingCost;
    }
    
    public double getInsuranceCost() {
        return insuranceCost;
    }
    
    public void setInsuranceCost(double insuranceCost) {
        this.insuranceCost = insuranceCost;
    }
    
    public double getCustomsDuties() {
        return customsDuties;
    }
    
    public void setCustomsDuties(double customsDuties) {
        this.customsDuties = customsDuties;
    }
    
    public double getTaxes() {
        return taxes;
    }
    
    public void setTaxes(double taxes) {
        this.taxes = taxes;
    }
    
    public double getOtherCharges() {
        return otherCharges;
    }
    
    public void setOtherCharges(double otherCharges) {
        this.otherCharges = otherCharges;
    }
    
    public double getTotalCost() {
        return totalCost;
    }
    
    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
    
    public String getPaymentStatus() {
        return paymentStatus;
    }
    
    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
        
        // If status changed to "Paid" and no paid date, set it to now
        if ("Paid".equals(paymentStatus) && this.paidDate == null) {
            this.paidDate = new Date();
        }
    }
    
    public Date getPaidDate() {
        return paidDate;
    }
    
    public void setPaidDate(Date paidDate) {
        this.paidDate = paidDate;
    }
    
    public String getInvoiceNumber() {
        return invoiceNumber;
    }
    
    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }
    
    /**
     * Calculate the total cost from all components
     */
    public void calculateTotal() {
        this.totalCost = this.shippingCost + 
                         this.insuranceCost + 
                         this.customsDuties + 
                         this.taxes + 
                         this.otherCharges;
    }
}
