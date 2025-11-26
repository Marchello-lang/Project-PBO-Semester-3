package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database Connection Utility
 * Konfigurasi koneksi ke MySQL RevaUPNVJ Database
 */
public class DatabaseConnection {

    // Database Configuration
    private static final String DB_URL = "jdbc:mysql://localhost:3306/revaupnvj";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";  // Ubah sesuai password MySQL Anda
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";

    // Static connection instance
    private static Connection connection = null;

    /**
     * Get database connection (Singleton Pattern)
     * Hanya membuat satu koneksi untuk seluruh aplikasi
     */
    public static Connection getConnection() {
        if (connection == null) {
            try {
                // Load MySQL JDBC Driver
                Class.forName(DB_DRIVER);

                // Buat koneksi baru
                connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                System.out.println("✅ Database Connected Successfully!");
                System.out.println("   URL: " + DB_URL);
                System.out.println("   User: " + DB_USER);

            } catch (ClassNotFoundException e) {
                System.err.println("❌ JDBC Driver not found!");
                System.err.println("   Error: " + e.getMessage());
                System.err.println("   Make sure mysql-connector-java JAR is in your project classpath");

            } catch (SQLException e) {
                System.err.println("❌ Failed to connect to database!");
                System.err.println("   Error: " + e.getMessage());
                System.err.println("   Check your database configuration:");
                System.err.println("   - MySQL server is running?");
                System.err.println("   - Database 'revaupnvj' exists?");
                System.err.println("   - Username/Password correct?");
            }
        }
        return connection;
    }

    /**
     * Close database connection
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                System.out.println("✅ Database Connection Closed");

            } catch (SQLException e) {
                System.err.println("❌ Error closing connection: " + e.getMessage());
            }
        }
    }

    /**
     * Test connection (untuk debugging)
     */
    public static void testConnection() {
        Connection conn = getConnection();
        if (conn != null) {
            System.out.println("✅ Connection test PASSED");
        } else {
            System.out.println("❌ Connection test FAILED");
        }
    }
}
