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
public class TrackingInfo {
    private String trackingId;
    private String shipmentId;
    private Date timestamp;
    private String location;
    private String description;
    private String status;
    private double latitude;
    private double longitude;
    
    public TrackingInfo() {
        this.timestamp = new Date(); // Default to current time
    }
    
    // Getters and Setters
    public String getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(String trackingId) {
        this.trackingId = trackingId;
    }

    public String getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    
    public void setLocationWithCoordinates(String location, double lat, double lng) {
        this.location = location;
        this.latitude = lat;
        this.longitude = lng;
    }
    
    @Override
    public String toString() {
        return timestamp + " - " + location + " - " + description;
    }
}
