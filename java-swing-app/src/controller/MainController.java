package controller;

import dao.BuildingDAO;
import dao.RoomDAO;
import dao.BuildingPhotoDAO;
import dao.RatingDAO;
import model.Building;
import model.Room;
import model.BuildingPhoto;
import model.User;

import java.util.List;

/**
 * Controller untuk Main application logic
 */
public class MainController {
    
    private BuildingDAO buildingDAO = new BuildingDAO();
    private RoomDAO roomDAO = new RoomDAO();
    private BuildingPhotoDAO photoDAO = new BuildingPhotoDAO();
    private RatingDAO ratingDAO = new RatingDAO();
    
    /**
     * Get all buildings untuk ditampilkan di map
     */
    public List<Building> getBuildings() {
        try {
            List<Building> buildings = buildingDAO.getAllBuildings();
            System.out.println("✓ Loaded " + buildings.size() + " buildings");
            return buildings;
        } catch (Exception e) {
            System.err.println("✗ Error loading buildings: " + e.getMessage());
            e.printStackTrace();
            return List.of(); // Return empty list
        }
    }
    
    /**
     * Get building detail dengan rooms dan photos
     */
    public Building getBuildingDetail(int buildingId) {
        try {
            Building building = buildingDAO.getBuildingById(buildingId);
            
            if (building != null) {
                // Load rooms
                List<Room> rooms = roomDAO.getRoomsByBuildingId(buildingId);
                building.setRooms(rooms);
                
                // Load average rating untuk setiap room
                for (Room room : rooms) {
                    double avgRating = ratingDAO.getAverageRatingByRoom(room.getRoomId());
                    room.setAverageRating(avgRating);
                }
                
                // Load photos
                List<BuildingPhoto> photos = photoDAO.getPhotosByBuildingId(buildingId);
                for (BuildingPhoto photo : photos) {
                    building.addPhotoUrl(photo.getPhotoUrl());
                }
                
                System.out.println("✓ Loaded detail for: " + building.getBuildingName());
            }
            
            return building;
            
        } catch (Exception e) {
            System.err.println("✗ Error loading building detail: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Submit rating dengan validasi
     */
    public boolean submitRating(User user, int roomId, int rating, String review) {
        try {
            // Validasi user
            if (user == null || user.isGuest()) {
                System.out.println("✗ Guest tidak bisa rating. Silakan login.");
                return false;
            }
            
            if (user.isAdmin()) {
                System.out.println("✗ Admin tidak boleh memberikan rating.");
                return false;
            }
            
            // Check if already rated
            if (ratingDAO.hasUserRatedRoom(user.getUserId(), roomId)) {
                System.out.println("✗ User sudah memberi rating untuk room ini.");
                return false;
            }
            
            // Get room & building info untuk validasi fakultas
            Room room = roomDAO.getRoomById(roomId);
            if (room == null) {
                System.out.println("✗ Room tidak ditemukan.");
                return false;
            }
            
            Building building = buildingDAO.getBuildingById(room.getBuildingId());
            if (building == null) {
                System.out.println("✗ Building tidak ditemukan.");
                return false;
            }
            
            // Validasi permission based on faculty
            if (!building.isPublicFacility()) {
                // Jika bukan fasilitas umum, cek fakultas
                if (!user.getFaculty().equals(building.getFaculty())) {
                    System.out.println("✗ User hanya bisa rating gedung fakultas sendiri.");
                    return false;
                }
            }
            
            // Submit rating
            model.Rating ratingObj = new model.Rating(
                user.getUserId(),
                roomId,
                building.getBuildingId(),
                rating,
                review
            );          
            boolean success = ratingDAO.submitRating(ratingObj);
            
            if (success) {
                System.out.println("✓ Rating submitted: " + rating + " stars for room " + roomId);
            }
            
            return success;
            
        } catch (Exception e) {
            System.err.println("✗ Error submitting rating: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // ---------------- Room CRUD ---------------- //
        public boolean addRoom(Room room) {
            return roomDAO.addRoom(room);
        }

        public boolean updateRoom(Room room) {
            return roomDAO.updateRoom(room);
        }

        public boolean deleteRoom(int roomId) {
            return roomDAO.deleteRoom(roomId);
        }

        public List<Room> getRoomsByBuilding(int buildingId) {
            return roomDAO.getRoomsByBuildingId(buildingId);
        }

            
}
