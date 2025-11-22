package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database Configuration & Connection Manager
 * Singleton Pattern untuk manage database connection
 */
public class DatabaseConfig {
    
    // Database credentials - GANTI sesuai konfigurasi MySQL kamu!
    private static final String URL = "jdbc:mysql://localhost:3306/revaupnvj?useSSL=false&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = ""; // ⚠️ GANTI dengan password MySQL kamu!
    
    // Driver class
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    
    // Singleton instance
    private static DatabaseConfig instance;
    private Connection connection;
    
    /**
     * Private constructor untuk Singleton pattern
     */
    private DatabaseConfig() {
        try {
            // Load MySQL JDBC Driver
            Class.forName(DRIVER);
            System.out.println("✓ MySQL JDBC Driver loaded successfully");
        } catch (ClassNotFoundException e) {
            System.err.println("✗ MySQL JDBC Driver not found!");
            System.err.println("  Make sure mysql-connector-j-8.x.x.jar is in lib/ folder");
            e.printStackTrace();
        }
    }
    
    /**
     * Get singleton instance
     */
    public static DatabaseConfig getInstance() {
        if (instance == null) {
            synchronized (DatabaseConfig.class) {
                if (instance == null) {
                    instance = new DatabaseConfig();
                }
            }
        }
        return instance;
    }
    
    /**
     * Get database connection
     * @return Connection object
     * @throws SQLException if connection fails
     */
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                System.out.println("✓ Connected to database: campus_map_db");
            } catch (SQLException e) {
                System.err.println("✗ Failed to connect to database!");
                System.err.println("  URL: " + URL);
                System.err.println("  Username: " + USERNAME);
                System.err.println("  Error: " + e.getMessage());
                throw e;
            }
        }
        return connection;
    }
    
    /**
     * Close database connection
     */
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("✓ Database connection closed");
            } catch (SQLException e) {
                System.err.println("✗ Error closing connection: " + e.getMessage());
            }
        }
    }
    
    /**
     * Test database connection
     * @return true if connection successful, false otherwise
     */
    public static boolean testConnection() {
        try {
            Connection conn = getInstance().getConnection();
            if (conn != null && !conn.isClosed()) {
                System.out.println("✓ Database connection test: SUCCESS");
                return true;
            }
        } catch (SQLException e) {
            System.err.println("✗ Database connection test: FAILED");
            System.err.println("  " + e.getMessage());
        }
        return false;
    }
}
