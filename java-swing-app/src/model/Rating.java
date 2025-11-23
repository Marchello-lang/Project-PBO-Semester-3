package model;

import java.sql.Timestamp;

/**
 * Model class untuk Rating
 * Represents: ratings table in database
 */
public class Rating {
    private int ratingId;
    private int userId;
    private int roomId;
    private int buildingId;
    private int rating; // 1-5
    private String review;
    private Timestamp createdAt;
    
    // For display purposes
    private String username;
    private String faculty;
    private String roomName;
    
    // Constructors
    public Rating() {}
    
    public Rating(int userId, int roomId, int buildingId, int rating, String review) {
        this.userId = userId;
        this.roomId = roomId;
        this.buildingId = buildingId;
        this.rating = rating;
        this.review = review;
    }
    
    // Getters and Setters
    public int getRatingId() {
        return ratingId;
    }
    
    public void setRatingId(int ratingId) {
        this.ratingId = ratingId;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
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
    
    public int getRating() {
        return rating;
    }
    
    public void setRating(int rating) {
        this.rating = rating;
    }
    
    public String getReview() {
        return review;
    }
    
    public void setReview(String review) {
        this.review = review;
    }
    
    public Timestamp getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getFaculty() {
        return faculty;
    }
    
    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }
    
    public String getRoomName() {
        return roomName;
    }
    
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
}
