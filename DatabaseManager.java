import java.sql.*;

public class DatabaseManager {
    
    // Database Configuration - SESUAIKAN DENGAN LARAGON ANDA
    private static final String DB_URL = "jdbc:mysql://localhost:3306/upnvj_map";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = ""; // Default Laragon kosong
    
    // Test koneksi database
    public static boolean testConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("✓ Database connected successfully!");
            conn.close();
            return true;
        } catch (ClassNotFoundException e) {
            System.err.println("✗ MySQL Driver not found!");
            System.err.println("  Download: https://dev.mysql.com/downloads/connector/j/");
            System.err.println("  Add JAR to Build Path");
            return false;
        } catch (SQLException e) {
            System.err.println("✗ Database connection failed!");
            System.err.println("  Error: " + e.getMessage());
            System.err.println("  Check: MySQL is running, database exists, credentials correct");
            return false;
        }
    }
    
    // Get building info from database
    public static BuildingInfo getBuildingInfo(String buildingNumber) {
        BuildingInfo info = new BuildingInfo();
        
        String query = "SELECT building_name, description, hours, facilities, faculty " +
                      "FROM buildings WHERE building_id = ?";
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, Integer.parseInt(buildingNumber));
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                info.name = rs.getString("building_name");
                info.description = rs.getString("description");
                info.hours = rs.getString("hours");
                info.facilities = rs.getString("facilities");
                info.faculty = rs.getString("faculty");
                
                System.out.println("✓ Loaded data for: " + info.name);
            } else {
                // Default jika tidak ada data
                System.out.println("! No data found for building " + buildingNumber);
                info.name = "Gedung " + buildingNumber;
                info.description = "Informasi gedung tidak tersedia di database";
                info.hours = "08.00 - 16.00";
                info.facilities = "-";
                info.faculty = "UMUM";
            }
            
            rs.close();
            stmt.close();
            conn.close();
            
        } catch (ClassNotFoundException e) {
            System.err.println("✗ MySQL Driver not found!");
            setDefaultInfo(info, buildingNumber);
        } catch (SQLException e) {
            System.err.println("✗ Database error: " + e.getMessage());
            setDefaultInfo(info, buildingNumber);
        } catch (NumberFormatException e) {
            System.err.println("✗ Invalid building number: " + buildingNumber);
            setDefaultInfo(info, buildingNumber);
        }
        
        return info;
    }
    
    // Set default info jika database gagal
    private static void setDefaultInfo(BuildingInfo info, String buildingNumber) {
        info.name = "Gedung " + buildingNumber;
        info.description = "Koneksi database gagal. Pastikan MySQL aktif dan database 'upnvj_map' sudah dibuat.";
        info.hours = "-";
        info.facilities = "-";
        info.faculty = "UMUM";
    }
    
    // Get all buildings (optional, untuk testing)
    public static void printAllBuildings() {
        String query = "SELECT * FROM buildings ORDER BY building_id";
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            System.out.println("\n========== DATA GEDUNG ==========");
            int count = 0;
            while (rs.next()) {
                count++;
                System.out.printf("%2d. %-30s (%s)\n", 
                    rs.getInt("building_id"), 
                    rs.getString("building_name"),
                    rs.getString("faculty")
                );
            }
            System.out.println("================================");
            System.out.println("Total: " + count + " buildings\n");
            
            rs.close();
            stmt.close();
            conn.close();
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    // Test method - jalankan untuk cek koneksi
    public static void main(String[] args) {
        System.out.println("=== DATABASE CONNECTION TEST ===\n");
        
        if (testConnection()) {
            printAllBuildings();
            
            // Test single building
            System.out.println("Testing single building query...");
            BuildingInfo info = getBuildingInfo("1");
            System.out.println("\nBuilding Info:");
            System.out.println("Name: " + info.name);
            System.out.println("Hours: " + info.hours);
            System.out.println("Faculty: " + info.faculty);
            System.out.println("\n✓ All tests passed!");
        } else {
            System.out.println("\n✗ Connection test failed!");
            System.out.println("\nTroubleshooting:");
            System.out.println("1. Make sure Laragon/XAMPP MySQL is running");
            System.out.println("2. Import setup_database.sql");
            System.out.println("3. Add MySQL Connector JAR to Build Path");
        }
    }
}

// Class untuk menyimpan info gedung
class BuildingInfo {
    String name;
    String description;
    String hours;
    String facilities;
    String faculty;
    
    public BuildingInfo() {
        this.name = "";
        this.description = "";
        this.hours = "";
        this.facilities = "";
        this.faculty = "";
    }
    
    @Override
    public String toString() {
        return "BuildingInfo{" +
               "name='" + name + '\'' +
               ", hours='" + hours + '\'' +
               ", faculty='" + faculty + '\'' +
               '}';
    }
}