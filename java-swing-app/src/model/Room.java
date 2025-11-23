package model;

/**
 * Model class untuk Room/Ruangan
 * Represents: rooms table in database
 */
public class Room {
    private int roomId;
    private int buildingId;
    private String roomName;
    private String description;
    private double averageRating;
    
    // Constructors
    public Room() {
        this.averageRating = 0.0;
    }
    
    public Room(int roomId, String roomName, int buildingId) {
        this();
        this.roomId = roomId;
        this.roomName = roomName;
        this.buildingId = buildingId;
    }
    
    // Getters and Setters
    public int getRoomId() {
        return roomId;
    }
    
    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
    
    public int getBuildingId() {
        return buildingId;
    }
    
    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }
    
    public String getRoomName() {
        return roomName;
    }
    
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public double getAverageRating() {
        return averageRating;
    }
    
    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }
    
    @Override
    public String toString() {
        return roomName;
    }
}
