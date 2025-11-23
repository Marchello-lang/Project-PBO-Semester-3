package dao;

import config.DatabaseConfig;
import model.Room;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO {

    private Connection getConnection() throws SQLException {
        return DatabaseConfig.getInstance().getConnection();
    }

    // Get rooms by building ID
    public List<Room> getRoomsByBuildingId(int buildingId) {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT room_id, room_name, building_id, description FROM rooms WHERE building_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, buildingId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int roomId = rs.getInt("room_id");
                    String roomName = rs.getString("room_name");
                    int bId = rs.getInt("building_id");

                    Room r = new Room(roomId, roomName, bId);
                    r.setDescription(rs.getString("description"));

                    rooms.add(r);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rooms;
    }

    // Get room by ID
    public Room getRoomById(int roomId) {
        String sql = "SELECT room_id, room_name, building_id, description FROM rooms WHERE room_id = ?";
        Room r = null;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, roomId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String roomName = rs.getString("room_name");
                    int buildingId = rs.getInt("building_id");

                    r = new Room(roomId, roomName, buildingId);
                    r.setDescription(rs.getString("description"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return r;
    }

    // Add new room
    public boolean addRoom(Room room) {
        String sql = "INSERT INTO rooms (building_id, room_name, description) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, room.getBuildingId());
            stmt.setString(2, room.getRoomName());
            stmt.setString(3, room.getDescription());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update room
    public boolean updateRoom(Room room) {
        String sql = "UPDATE rooms SET room_name = ?, description = ?, building_id = ? WHERE room_id = ?";

        try (Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, room.getRoomName());
            stmt.setString(2, room.getDescription());
            stmt.setInt(3, room.getBuildingId());
            stmt.setInt(4, room.getRoomId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete room
    public boolean deleteRoom(int roomId) {
        String sql = "DELETE FROM rooms WHERE room_id = ?";

        try (Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, roomId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
