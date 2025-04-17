/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Customs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author zhuchenyan
 */
public class TaxReturnDirectory {
    private ArrayList<TaxReturn> taxReturnList;
    
    public TaxReturnDirectory() {
        taxReturnList = new ArrayList<>();
    }
    
    public ArrayList<TaxReturn> getTaxReturnList() {
        return taxReturnList;
    }
    
    public void addTaxReturn(TaxReturn taxReturn) {
        if (taxReturn != null) {
            taxReturnList.add(taxReturn);
        }
    }
    
    public void removeTaxReturn(TaxReturn taxReturn) {
        taxReturnList.remove(taxReturn);
    }
    
    public TaxReturn findTaxReturnById(String applicationId) {
        return taxReturnList.stream()
                .filter(taxReturn -> taxReturn.getApplicationId().equals(applicationId))
                .findFirst()
                .orElse(null);
    }
    
    public List<TaxReturn> getTaxReturnsByDeclarationId(String declarationId) {
        return taxReturnList.stream()
                .filter(taxReturn -> taxReturn.getDeclarationId().equals(declarationId))
                .collect(Collectors.toList());
    }
    
    // Get tax returns by status
    public List<TaxReturn> getTaxReturnsByStatus(String status) {
        return taxReturnList.stream()
                .filter(taxReturn -> taxReturn.getStatus().equals(status))
                .collect(Collectors.toList());
    }
    
    // Get pending tax returns
    public List<TaxReturn> getPendingTaxReturns() {
        return getTaxReturnsByStatus("Pending");
    }
    
    // Get approved tax returns
    public List<TaxReturn> getApprovedTaxReturns() {
        return getTaxReturnsByStatus("Approved");
    }
    
    // Get rejected tax returns
    public List<TaxReturn> getRejectedTaxReturns() {
        return getTaxReturnsByStatus("Rejected");
    }
    
    // Get tax returns by date range
    public List<TaxReturn> getTaxReturnsByDateRange(Date startDate, Date endDate) {
        return taxReturnList.stream()
                .filter(taxReturn -> {
                    Date submitDate = taxReturn.getSubmissionDate();
                    return submitDate.after(startDate) && submitDate.before(endDate);
                })
                .collect(Collectors.toList());
    }
    
    // Get count of tax returns by status
    public int getTaxReturnCountByStatus(String status) {
        return (int) taxReturnList.stream()
                .filter(taxReturn -> taxReturn.getStatus().equals(status))
                .count();
    }
    
    // Get total return amount for approved returns
    public double getTotalApprovedReturnAmount() {
        return taxReturnList.stream()
                .filter(taxReturn -> taxReturn.getStatus().equals("Approved"))
                .mapToDouble(TaxReturn::getReturnAmount)
                .sum();
    }
    
    // Get total pending return amount
    public double getTotalPendingReturnAmount() {
        return taxReturnList.stream()
                .filter(taxReturn -> taxReturn.getStatus().equals("Pending"))
                .mapToDouble(TaxReturn::getReturnAmount)
                .sum();
    }
    
    // Generate a new unique application ID
    public String generateNewApplicationId() {
        int nextId = taxReturnList.size() + 1;
        return String.format("TR%06d", nextId);
    }
    
    // Get recent tax returns (last 30 days)
    public List<TaxReturn> getRecentTaxReturns() {
        Date thirtyDaysAgo = new Date(System.currentTimeMillis() - 30L * 24 * 60 * 60 * 1000);
        return taxReturnList.stream()
                .filter(taxReturn -> taxReturn.getSubmissionDate().after(thirtyDaysAgo))
                .collect(Collectors.toList());
    }
}
