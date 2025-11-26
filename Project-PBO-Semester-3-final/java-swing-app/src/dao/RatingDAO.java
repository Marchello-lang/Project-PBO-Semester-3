package dao;

import config.DatabaseConfig;
import model.Rating;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import util.DatabaseConnection;

public class RatingDAO {

    private Connection getConnection() throws SQLException {
        return DatabaseConfig.getInstance().getConnection();
    }

    // Submit rating baru
    public boolean submitRating(Rating rating) {
        String sql = "INSERT INTO ratings (user_id, room_id, building_id, rating, review) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, rating.getUserId());
            stmt.setInt(2, rating.getRoomId());
            stmt.setInt(3, rating.getBuildingId());
            stmt.setInt(4, rating.getRating());
            stmt.setString(5, rating.getReview());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get ratings by room ID
    public List<Rating> getRatingsByRoomId(int roomId) {
        List<Rating> ratings = new ArrayList<>();

        // pakai VIEW rating_details supaya dapat username & faculty & roomName
        String sql = "SELECT * FROM rating_details WHERE room_id = ? ORDER BY created_at DESC";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, roomId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Rating r = new Rating();
                    r.setRatingId(rs.getInt("rating_id"));
                    r.setRating(rs.getInt("rating"));
                    r.setReview(rs.getString("review"));
                    r.setCreatedAt(rs.getTimestamp("created_at"));
                    
                    // embed info display
                    r.setUsername(rs.getString("username"));
                    r.setFaculty(rs.getString("user_faculty"));
                    r.setRoomName(rs.getString("room_name"));

                    ratings.add(r);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ratings;
    }

    // Check if user sudah rating room ini
    public boolean hasUserRatedRoom(int userId, int roomId) {
        String sql = "SELECT COUNT(*) FROM ratings WHERE user_id = ? AND room_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, roomId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateUserRating(int userId, int roomId, int rating, String review) {
        String sql = "UPDATE ratings SET rating = ?, review = ?, createdat = CURRENT_TIMESTAMP WHERE userid = ? AND roomid = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, rating);
            stmt.setString(2, review);
            stmt.setInt(3, userId);
            stmt.setInt(4, roomId);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    // Get average rating for room
    public double getAverageRatingByRoom(int roomId) {
        String sql = "SELECT AVG(rating) FROM ratings WHERE room_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, roomId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next() && rs.getDouble(1) != 0) {
                    return rs.getDouble(1);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0.0;
    }
}
