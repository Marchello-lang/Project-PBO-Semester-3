package dao;

import config.DatabaseConfig;
import model.Admin;

import java.sql.*;

/**
 * DAO untuk Admin
 * Handle database operations untuk admins table
 */
public class AdminDAO {
    
    private Connection getConnection() throws SQLException {
        return DatabaseConfig.getInstance().getConnection();
    }
    
    /**
     * Login admin - cek username & password
     */
    public Admin login(String username, String password) {
        String sql = "SELECT * FROM admins WHERE username = ? AND password = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            stmt.setString(2, password);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Admin admin = new Admin();
                admin.setAdminId(rs.getInt("admin_id"));
                admin.setUsername(rs.getString("username"));
                return admin;
            }
            
        } catch (SQLException e) {
            System.err.println("Error login admin: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * Get admin by ID
     */
    public Admin getAdminById(int adminId) {
        String sql = "SELECT * FROM admins WHERE admin_id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, adminId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Admin admin = new Admin();
                admin.setAdminId(rs.getInt("admin_id"));
                admin.setUsername(rs.getString("username"));
                return admin;
            }
            
        } catch (SQLException e) {
            System.err.println("Error get admin by ID: " + e.getMessage());
        }
        
        return null;
    }
}
