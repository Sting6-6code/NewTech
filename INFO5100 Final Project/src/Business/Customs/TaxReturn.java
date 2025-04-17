/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Customs;

import java.util.Date;

/**
 *
 * @author zhuchenyan
 */
public class TaxReturn {
    private String applicationId;
    private String declarationId;
    private String taxId;
    private double originalTaxAmount;
    private double returnAmount;
    private String returnReason;
    private String bankInfo;
    private String notes;
    private String status;  // "Pending", "Approved", "Rejected"
    private Date submissionDate;
    private String applicationType;
    
    public TaxReturn() {
        this.submissionDate = new Date();
        this.status = "Pending";
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getDeclarationId() {
        return declarationId;
    }

    public void setDeclarationId(String declarationId) {
        this.declarationId = declarationId;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public double getOriginalTaxAmount() {
        return originalTaxAmount;
    }

    public void setOriginalTaxAmount(double originalTaxAmount) {
        this.originalTaxAmount = originalTaxAmount;
    }

    public double getReturnAmount() {
        return returnAmount;
    }

    public void setReturnAmount(double returnAmount) {
        this.returnAmount = returnAmount;
    }

    public String getReturnReason() {
        return returnReason;
    }

    public void setReturnReason(String returnReason) {
        this.returnReason = returnReason;
    }

    public String getBankInfo() {
        return bankInfo;
    }

    public void setBankInfo(String bankInfo) {
        this.bankInfo = bankInfo;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(Date submissionDate) {
        this.submissionDate = submissionDate;
    }

    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }
    
    
}
