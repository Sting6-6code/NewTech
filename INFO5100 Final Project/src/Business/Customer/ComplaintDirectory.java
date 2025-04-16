/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Customer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

/**
 *
 * @author zhuchenyan
 */
public class ComplaintDirectory {
    
    private ArrayList<CustomerComplaint> complaints;
    
    public ComplaintDirectory() {
        this.complaints = new ArrayList<>();
    }
    
    public ArrayList<CustomerComplaint> getComplaints() {
        return complaints;
    }
    
    public CustomerComplaint createComplaint(String complaintId, String customerId, String description) {
        CustomerComplaint complaint = new CustomerComplaint(complaintId, customerId, description);
        complaints.add(complaint);
        return complaint;
    }
    
    public void removeComplaint(CustomerComplaint complaint) {
        complaints.remove(complaint);
    }
    
    public CustomerComplaint findComplaintById(String complaintId) {
        for (CustomerComplaint complaint : complaints) {
            if (complaint.getComplaintId().equals(complaintId)) {
                return complaint;
            }
        }
        return null;
    }
    
    public ArrayList<CustomerComplaint> findComplaintsByCustomer(String customerId) {
        ArrayList<CustomerComplaint> result = new ArrayList<>();
        for (CustomerComplaint complaint : complaints) {
            if (complaint.getCustomerId().equals(customerId)) {
                result.add(complaint);
            }
        }
        return result;
    }
    
    public ArrayList<CustomerComplaint> getNewComplaints() {
        ArrayList<CustomerComplaint> result = new ArrayList<>();
        for (CustomerComplaint complaint : complaints) {
            if (complaint.getStatus().equals("New")) {
                result.add(complaint);
            }
        }
        return result;
    }
    
    public ArrayList<CustomerComplaint> getInProgressComplaints() {
        ArrayList<CustomerComplaint> result = new ArrayList<>();
        for (CustomerComplaint complaint : complaints) {
            if (complaint.getStatus().equals("In Progress")) {
                result.add(complaint);
            }
        }
        return result;
    }
    
    public ArrayList<CustomerComplaint> getResolvedComplaints() {
        ArrayList<CustomerComplaint> result = new ArrayList<>();
        for (CustomerComplaint complaint : complaints) {
            if (complaint.getStatus().equals("Resolved")) {
                result.add(complaint);
            }
        }
        return result;
    }
    
    public int getComplaintCount() {
        return complaints.size();
    }
    
    public int getNewComplaintCount() {
        int count = 0;
        for (CustomerComplaint complaint : complaints) {
            if (complaint.getStatus().equals("New")) {
                count++;
            }
        }
        return count;
    }
    
    public int getInProgressComplaintCount() {
        int count = 0;
        for (CustomerComplaint complaint : complaints) {
            if (complaint.getStatus().equals("In Progress")) {
                count++;
            }
        }
        return count;
    }
    
    public int getResolvedComplaintCount() {
        int count = 0;
        for (CustomerComplaint complaint : complaints) {
            if (complaint.getStatus().equals("Resolved")) {
                count++;
            }
        }
        return count;
    }
    
    public boolean updateComplaintStatus(String complaintId, String newStatus) {
        CustomerComplaint complaint = findComplaintById(complaintId);
        if (complaint != null) {
            complaint.setStatus(newStatus);
            return true;
        }
        return false;
    }
    
    // Sort complaints by date (most recent first)
    public ArrayList<CustomerComplaint> getRecentComplaints() {
        ArrayList<CustomerComplaint> sortedComplaints = new ArrayList<>(complaints);
        
        // Sort by date (most recent first)
        Collections.sort(sortedComplaints, new Comparator<CustomerComplaint>() {
            @Override
            public int compare(CustomerComplaint c1, CustomerComplaint c2) {
                return c2.getCreateDate().compareTo(c1.getCreateDate());
            }
        });
        
        return sortedComplaints;
    }
    
    // Get a limited number of most recent complaints
    public ArrayList<CustomerComplaint> getRecentComplaints(int limit) {
        ArrayList<CustomerComplaint> sortedComplaints = getRecentComplaints();
        
        if (sortedComplaints.size() <= limit) {
            return sortedComplaints;
        } else {
            return new ArrayList<>(sortedComplaints.subList(0, limit));
        }
    }
    
}
