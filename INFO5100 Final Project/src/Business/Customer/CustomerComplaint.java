/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Customer;

import java.util.Date;

/**
 *
 * @author wangsiting
 */
public class CustomerComplaint {
    private String complaintId;
    private String customerId;
    private String description;
    private String status;  // "New", "In Progress", "Resolved"
    private Date createDate;

    public CustomerComplaint(String complaintId, String customerId, 
                           String description) {
        this.complaintId = complaintId;
        this.customerId = customerId;
        this.description = description;
        this.status = "New";
        this.createDate = new Date();
    }

    public String getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(String complaintId) {
        this.complaintId = complaintId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
    
    
}
