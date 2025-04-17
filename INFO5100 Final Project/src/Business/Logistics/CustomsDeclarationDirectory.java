/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Logistics;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author zhuchenyan
 */
public class CustomsDeclarationDirectory {
    private ArrayList<CustomsDeclaration> customsDeclarationList;
    
    public CustomsDeclarationDirectory() {
        customsDeclarationList = new ArrayList<>();
    }
    
    public ArrayList<CustomsDeclaration> getCustomsDeclarationList() {
        return customsDeclarationList;
    }
    
    public void addCustomsDeclaration(CustomsDeclaration declaration) {
        if (declaration != null) {
            customsDeclarationList.add(declaration);
        }
    }
    
    public void removeCustomsDeclaration(CustomsDeclaration declaration) {
        customsDeclarationList.remove(declaration);
    }
    
    public CustomsDeclaration findDeclarationById(String declarationId) {
        return customsDeclarationList.stream()
                .filter(declaration -> declaration.getDeclarationId().equals(declarationId))
                .findFirst()
                .orElse(null);
    }
    
    public CustomsDeclaration findDeclarationByShipmentId(String shipmentId) {
        return customsDeclarationList.stream()
                .filter(declaration -> declaration.getShipmentId().equals(shipmentId))
                .findFirst()
                .orElse(null);
    }
    
    // Get declarations by status
    public List<CustomsDeclaration> getDeclarationsByStatus(String status) {
        return customsDeclarationList.stream()
                .filter(declaration -> declaration.getStatus().equals(status))
                .collect(Collectors.toList());
    }
    
    // Get pending declarations
    public List<CustomsDeclaration> getPendingDeclarations() {
        return getDeclarationsByStatus("Pending");
    }
    
    // Get approved declarations
    public List<CustomsDeclaration> getApprovedDeclarations() {
        return getDeclarationsByStatus("Approved");
    }
    
    // Get rejected declarations
    public List<CustomsDeclaration> getRejectedDeclarations() {
        return getDeclarationsByStatus("Rejected");
    }
    
    // Get declarations by date range
    public List<CustomsDeclaration> getDeclarationsByDateRange(Date startDate, Date endDate) {
        return customsDeclarationList.stream()
                .filter(declaration -> {
                    Date declDate = declaration.getDeclarationDate();
                    return declDate.after(startDate) && declDate.before(endDate);
                })
                .collect(Collectors.toList());
    }
    
    // Get declarations by customs office
    public List<CustomsDeclaration> getDeclarationsByCustomsOffice(String customsOffice) {
        return customsDeclarationList.stream()
                .filter(declaration -> declaration.getCustomsOffice().equals(customsOffice))
                .collect(Collectors.toList());
    }
    
    // Get declarations by consignor
    public List<CustomsDeclaration> getDeclarationsByConsignor(String consignor) {
        return customsDeclarationList.stream()
                .filter(declaration -> declaration.getConsignor().equals(consignor))
                .collect(Collectors.toList());
    }
    
    // Get declarations by consignee
    public List<CustomsDeclaration> getDeclarationsByConsignee(String consignee) {
        return customsDeclarationList.stream()
                .filter(declaration -> declaration.getConsignee().equals(consignee))
                .collect(Collectors.toList());
    }
    
    // Get count of declarations by status
    public int getDeclarationCountByStatus(String status) {
        return (int) customsDeclarationList.stream()
                .filter(declaration -> declaration.getStatus().equals(status))
                .count();
    }
    
    // Generate a new unique declaration ID
    public String generateNewDeclarationId() {
        int nextId = customsDeclarationList.size() + 1;
        return String.format("DECL%06d", nextId);
    }
    
    // Get recent declarations (last 30 days)
    public List<CustomsDeclaration> getRecentDeclarations() {
        Date thirtyDaysAgo = new Date(System.currentTimeMillis() - 30L * 24 * 60 * 60 * 1000);
        return customsDeclarationList.stream()
                .filter(declaration -> declaration.getDeclarationDate().after(thirtyDaysAgo))
                .collect(Collectors.toList());
    }
    
    // Get declarations requiring processing (pending over 48 hours)
    public List<CustomsDeclaration> getOverdueDeclarations() {
        Date fortyEightHoursAgo = new Date(System.currentTimeMillis() - 48L * 60 * 60 * 1000);
        return customsDeclarationList.stream()
                .filter(declaration -> 
                    declaration.getStatus().equals("Pending") &&
                    declaration.getDeclarationDate().before(fortyEightHoursAgo))
                .collect(Collectors.toList());
    }
    
    public List<CustomsDeclaration> getTaxReturnDeclarations() {
    return customsDeclarationList.stream()
            .filter(declaration -> 
                declaration.getStatus().equals("Approved") && 
                // 假设出口到特定国家的货物有资格获得退税
                (declaration.getDestinationCountry().equals("United States") ||
                 declaration.getDestinationCountry().equals("Mexico") ||
                 declaration.getDestinationCountry().equals("Canada")))
            .collect(Collectors.toList());
}
}
