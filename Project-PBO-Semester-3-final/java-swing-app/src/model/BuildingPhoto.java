package model;

/**
 * Model class untuk Building Photo
 * Represents: building_photos table in database
 */
public class BuildingPhoto {
    private int photoId;
    private int buildingId;
    private String photoUrl;
    private int photoIndex; // 0-5 (urutan foto)
    
    // Constructors
    public BuildingPhoto() {}
    
    public BuildingPhoto(int buildingId, String photoUrl, int photoIndex) {
        this.buildingId = buildingId;
        this.photoUrl = photoUrl;
        this.photoIndex = photoIndex;
    }
    
    // Getters and Setters
    public int getPhotoId() {
        return photoId;
    }
    
    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }
    
    public int getBuildingId() {
        return buildingId;
    }
    
    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }
    
    public String getPhotoUrl() {
        return photoUrl;
    }
    
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
    
    public int getPhotoIndex() {
        return photoIndex;
    }
    
    public void setPhotoIndex(int photoIndex) {
        this.photoIndex = photoIndex;
    }
}
