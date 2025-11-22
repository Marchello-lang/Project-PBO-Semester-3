package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Model class untuk Building/Gedung
 * Represents: buildings table in database
 */
public class Building {
    private int buildingId;
    private String buildingName;
    private String description;
    private String hours;
    private String facilities;
    private String faculty; // FIK, FK, FH, FEB, FISIP, UMUM
    private double averageRating;
    private List<Room> rooms;
    private List<String> photoUrls;
    
    // Constructors
    public Building() {
        this.rooms = new ArrayList<>();
        this.photoUrls = new ArrayList<>();
        this.averageRating = 0.0;
    }
    
    public Building(int buildingId, String buildingName, String faculty) {
        this();
        this.buildingId = buildingId;
        this.buildingName = buildingName;
        this.faculty = faculty;
    }
    
    // Getters and Setters
    public int getBuildingId() {
        return buildingId;
    }
    
    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }
    
    public String getBuildingName() {
        return buildingName;
    }
    
    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getHours() {
        return hours;
    }
    
    public void setHours(String hours) {
        this.hours = hours;
    }
    
    public String getFacilities() {
        return facilities;
    }
    
    public void setFacilities(String facilities) {
        this.facilities = facilities;
    }
    
    public String getFaculty() {
        return faculty;
    }
    
    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }
    
    public double getAverageRating() {
        return averageRating;
    }
    
    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }
    
    public List<Room> getRooms() {
        return rooms;
    }
    
    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }
    
    public void addRoom(Room room) {
        this.rooms.add(room);
    }
    
    public List<String> getPhotoUrls() {
        return photoUrls;
    }
    
    public void setPhotoUrls(List<String> photoUrls) {
        this.photoUrls = photoUrls;
    }
    
    public void addPhotoUrl(String photoUrl) {
        this.photoUrls.add(photoUrl);
    }
    
    // Helper methods
    public boolean isPublicFacility() {
        return "UMUM".equals(faculty);
    }
    
    @Override
    public String toString() {
        return buildingName;
    }
}
