package dao;

import config.DatabaseConfig;
import model.Building;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO untuk Building
 * Handle database operations untuk buildings table
 */
public class BuildingDAO {
    
    private Connection getConnection() throws SQLException {
        return DatabaseConfig.getInstance().getConnection();
    }
    
    /**
     * Get all buildings
     */
    public List<Building> getAllBuildings() {
        List<Building> buildings = new ArrayList<>();
        String sql = "SELECT b.*, COALESCE(AVG(r.rating), 0) as avg_rating " +
                    "FROM buildings b " +
                    "LEFT JOIN rooms ro ON b.building_id = ro.building_id " +
                    "LEFT JOIN ratings r ON ro.room_id = r.room_id " +
                    "GROUP BY b.building_id";
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Building building = extractBuildingFromResultSet(rs);
                building.setAverageRating(rs.getDouble("avg_rating"));
                buildings.add(building);
            }
            
        } catch (SQLException e) {
            System.err.println("Error get all buildings: " + e.getMessage());
            e.printStackTrace();
        }
        
        return buildings;
    }
    
    /**
     * Get building by ID
     */
    public Building getBuildingById(int buildingId) {
        String sql = "SELECT * FROM buildings WHERE building_id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, buildingId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return extractBuildingFromResultSet(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("Error get building by ID: " + e.getMessage());
        }
        
        return null;
    }
    
    /**
     * Update building info
     */
    public boolean updateBuilding(Building building) {
        String sql = "UPDATE buildings SET description = ?, hours = ?, facilities = ? WHERE building_id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, building.getDescription());
            stmt.setString(2, building.getHours());
            stmt.setString(3, building.getFacilities());
            stmt.setInt(4, building.getBuildingId());
            
            int rows = stmt.executeUpdate();
            return rows > 0;
            
        } catch (SQLException e) {
            System.err.println("Error update building: " + e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Helper method untuk extract Building dari ResultSet
     */
    private Building extractBuildingFromResultSet(ResultSet rs) throws SQLException {
        Building building = new Building();
        building.setBuildingId(rs.getInt("building_id"));
        building.setBuildingName(rs.getString("building_name"));
        building.setDescription(rs.getString("description"));
        building.setHours(rs.getString("hours"));
        building.setFacilities(rs.getString("facilities"));
        building.setFaculty(rs.getString("faculty"));
        return building;
    }
}
