import java.io.File;
import java.sql.*;

/**
 * Helper class untuk auto-generate folder structure
 * Jalankan sekali untuk create semua folder gedung sesuai database
 */
public class FolderGenerator {
    
    private static final String BASE_PATH = "G:\\Ivana Tiara Harsono 2410511064\\RevaUPNVJ\\RevaUPNVJ\\src\\revaupnvj\\images\\";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/upnvj_map";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    
    public static void main(String[] args) {
        System.out.println("=================================");
        System.out.println("UPNVJ Folder Structure Generator");
        System.out.println("=================================\n");
        
        createFolderStructure();
        
        System.out.println("\n✓ Done! Sekarang upload foto ke folder yang sudah dibuat.");
    }
    
    public static void createFolderStructure() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            
            String query = "SELECT building_id, building_name FROM buildings ORDER BY building_id";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            int created = 0;
            int skipped = 0;
            
            while (rs.next()) {
                int id = rs.getInt("building_id");
                String name = rs.getString("building_name");
                
                // Format folder name
                String folderName = id + "_" + name.replaceAll("Gedung ", "")
                                                    .replaceAll("[^a-zA-Z0-9]", "");
                
                String fullPath = BASE_PATH + folderName;
                File folder = new File(fullPath);
                
                if (!folder.exists()) {
                    if (folder.mkdirs()) {
                        System.out.println("✓ Created: " + folderName);
                        created++;
                    } else {
                        System.out.println("✗ Failed: " + folderName);
                    }
                } else {
                    System.out.println("○ Exists:  " + folderName);
                    skipped++;
                }
            }
            
            rs.close();
            stmt.close();
            conn.close();
            
            System.out.println("\n--- Summary ---");
            System.out.println("Created: " + created);
            System.out.println("Skipped: " + skipped);
            System.out.println("Total:   " + (created + skipped));
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // Method untuk generate README.txt di setiap folder
    public static void generateReadmeFiles() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            
            String query = "SELECT building_id, building_name, facilities FROM buildings ORDER BY building_id";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next()) {
                int id = rs.getInt("building_id");
                String name = rs.getString("building_name");
                String facilities = rs.getString("facilities");
                
                String folderName = id + "_" + name.replaceAll("Gedung ", "")
                                                    .replaceAll("[^a-zA-Z0-9]", "");
                
                String fullPath = BASE_PATH + folderName;
                File folder = new File(fullPath);
                
                if (folder.exists()) {
                    // Create README.txt
                    File readme = new File(fullPath + "\\README.txt");
                    try {
                        java.io.FileWriter writer = new java.io.FileWriter(readme);
                        writer.write("GEDUNG: " + name + "\n");
                        writer.write("FASILITAS: " + facilities + "\n\n");
                        writer.write("Upload foto dengan format:\n");
                        writer.write("1_nama_fasilitas.jpg\n");
                        writer.write("2_nama_fasilitas.jpg\n");
                        writer.write("dst...\n");
                        writer.close();
                        System.out.println("✓ README created for: " + folderName);
                    } catch (Exception e) {
                        System.out.println("✗ Failed to create README for: " + folderName);
                    }
                }
            }
            
            rs.close();
            stmt.close();
            conn.close();
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    // Method untuk list semua foto yang ada
    public static void listAllPhotos() {
        System.out.println("\n=== EXISTING PHOTOS ===\n");
        
        File baseDir = new File(BASE_PATH);
        File[] folders = baseDir.listFiles(File::isDirectory);
        
        if (folders != null) {
            for (File folder : folders) {
                String folderName = folder.getName();
                if (folderName.matches("\\d+_.*")) { // Only gedung folders
                    File[] photos = folder.listFiles((dir, name) -> {
                        String lower = name.toLowerCase();
                        return lower.endsWith(".jpg") || 
                               lower.endsWith(".jpeg") || 
                               lower.endsWith(".png");
                    });
                    
                    System.out.println(folderName + ":");
                    if (photos != null && photos.length > 0) {
                        for (File photo : photos) {
                            System.out.println("  ✓ " + photo.getName());
                        }
                    } else {
                        System.out.println("  ✗ No photos yet");
                    }
                    System.out.println();
                }
            }
        }
    }
    
    // Method untuk validasi struktur folder
    public static void validateStructure() {
        System.out.println("\n=== VALIDATION ===\n");
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            
            String query = "SELECT building_id, building_name FROM buildings ORDER BY building_id";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            int valid = 0;
            int invalid = 0;
            
            while (rs.next()) {
                int id = rs.getInt("building_id");
                String name = rs.getString("building_name");
                
                String folderName = id + "_" + name.replaceAll("Gedung ", "")
                                                    .replaceAll("[^a-zA-Z0-9]", "");
                
                String fullPath = BASE_PATH + folderName;
                File folder = new File(fullPath);
                
                if (folder.exists() && folder.isDirectory()) {
                    File[] photos = folder.listFiles((dir, fname) -> {
                        String lower = fname.toLowerCase();
                        return lower.endsWith(".jpg") || 
                               lower.endsWith(".jpeg") || 
                               lower.endsWith(".png");
                    });
                    
                    int photoCount = (photos != null) ? photos.length : 0;
                    
                    if (photoCount > 0) {
                        System.out.println("✓ [" + id + "] " + name + " (" + photoCount + " photos)");
                        valid++;
                    } else {
                        System.out.println("! [" + id + "] " + name + " (folder exists, no photos)");
                        invalid++;
                    }
                } else {
                    System.out.println("✗ [" + id + "] " + name + " (folder not found)");
                    invalid++;
                }
            }
            
            rs.close();
            stmt.close();
            conn.close();
            
            System.out.println("\n--- Validation Summary ---");
            System.out.println("Complete: " + valid + "/24");
            System.out.println("Pending:  " + invalid + "/24");
            
            double percentage = (valid * 100.0) / 24;
            System.out.println("Progress: " + String.format("%.1f", percentage) + "%");
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}