/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Logistics;

import java.util.Date;

/**
 *
 * @author zhuchenyan
 */
public class Task {
 
    private String description;
    private String priority;  // "High", "Medium", "Low"
    private Date dueDate;
    private String status;    // "Not Started", "In Progress", "Pending", "Completed"
    private String assignedTo;
    private Date createdDate;
    
    public Task(String description, String priority, Date dueDate) {
        this.description = description;
        this.priority = priority;
        this.dueDate = dueDate;
        this.status = "Not Started";
        this.createdDate = new Date();
    }
    
    // Getters and Setters
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getPriority() {
        return priority;
    }
    
    public void setPriority(String priority) {
        this.priority = priority;
    }
    
    public Date getDueDate() {
        return dueDate;
    }
    
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getAssignedTo() {
        return assignedTo;
    }
    
    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }
    
    public Date getCreatedDate() {
        return createdDate;
    }
}
