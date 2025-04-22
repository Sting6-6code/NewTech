/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Logistics;

import java.io.Serializable;

/**
 *
 * @author zhuchenyan
 */
public class PackageInfo implements Serializable {
    private double weight; // in kg
    private String dimensions; // in format LxWxH cm
    private String packageType; // Box, Envelope, Pallet, etc.
    private int itemCount;
    private String specialHandling; // special handling instructions
    private String notes;
    
    public PackageInfo() {
        this.weight = 0;
        this.dimensions = "";
        this.packageType = "Box";
        this.itemCount = 0;
        this.specialHandling = "";
        this.notes = "";
    }
    
    // Getters and Setters
    public double getWeight() {
        return weight;
    }
    
    public void setWeight(double weight) {
        this.weight = weight;
    }
    
    public String getDimensions() {
        return dimensions;
    }
    
    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }
    
    public String getPackageType() {
        return packageType;
    }
    
    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }
    
    public int getItemCount() {
        return itemCount;
    }
    
    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }
    
    public String getSpecialHandling() {
        return specialHandling;
    }
    
    public void setSpecialHandling(String specialHandling) {
        this.specialHandling = specialHandling;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
}
