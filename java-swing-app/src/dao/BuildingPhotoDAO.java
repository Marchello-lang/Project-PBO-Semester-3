package dao;

import config.DatabaseConfig;
import model.BuildingPhoto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BuildingPhotoDAO {

    private Connection getConnection() throws SQLException {
        return DatabaseConfig.getInstance().getConnection();
    }

    // Get photos by building ID
    public List<BuildingPhoto> getPhotosByBuildingId(int buildingId) {
        List<BuildingPhoto> photos = new ArrayList<>();
        String sql = "SELECT photo_id, building_id, photo_url, photo_index FROM building_photos WHERE building_id = ? ORDER BY photo_index ASC";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, buildingId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    BuildingPhoto p = new BuildingPhoto(
                        rs.getInt("building_id"),
                        rs.getString("photo_url"),
                        rs.getInt("photo_index")
                    );
                    p.setPhotoId(rs.getInt("photo_id"));
                    photos.add(p);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return photos;
    }

    // Add photo
    public boolean addPhoto(BuildingPhoto photo) {
        String sql = "INSERT INTO building_photos (building_id, photo_url, photo_index) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, photo.getBuildingId());
            stmt.setString(2, photo.getPhotoUrl());
            stmt.setInt(3, photo.getPhotoIndex());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete photo
    public boolean deletePhoto(int photoId) {
        String sql = "DELETE FROM building_photos WHERE photo_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, photoId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
