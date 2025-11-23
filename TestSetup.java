import java.io.File;

/**
 * Testing program untuk cek setup sebelum run main program
 */
public class TestSetup {
    
    private static final String BASE_PATH = "G:\\Ivana Tiara Harsono 2410511064\\RevaUPNVJ\\RevaUPNVJ\\src\\revaupnvj\\images\\";
    
    public static void main(String[] args) {
        System.out.println("====================================");
        System.out.println("    UPNVJ CAMPUS MAP - SETUP TEST");
        System.out.println("====================================\n");
        
        // Test 1: Check Base Path
        System.out.println("[1] Checking base path...");
        File baseDir = new File(BASE_PATH);
        if (baseDir.exists() && baseDir.isDirectory()) {
            System.out.println("    ✓ Base path exists: " + BASE_PATH);
        } else {
            System.out.println("    ✗ Base path NOT FOUND!");
            System.out.println("    Expected: " + BASE_PATH);
            System.out.println("    Action: Update BASE_PATH in all Java files");
            return;
        }
        
        // Test 2: Check Map Image
        System.out.println("\n[2] Checking map image...");
        File mapFile = new File(BASE_PATH + "MapColored.jpg");
        if (mapFile.exists()) {
            System.out.println("    ✓ Map image found: MapColored.jpg");
        } else {
            System.out.println("    ✗ Map image NOT FOUND!");
            System.out.println("    Expected: " + BASE_PATH + "MapColored.jpg");
            System.out.println("    Action: Upload MapColored.jpg to images folder");
        }
        
        // Test 3: Check MySQL Driver
        System.out.println("\n[3] Checking MySQL Driver...");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("    ✓ MySQL Driver loaded successfully");
        } catch (ClassNotFoundException e) {
            System.out.println("    ✗ MySQL Driver NOT FOUND!");
            System.out.println("    Action: Download and add mysql-connector-java.jar");
            System.out.println("    URL: https://dev.mysql.com/downloads/connector/j/");
        }
        
        // Test 4: Check Database Connection
        System.out.println("\n[4] Checking database connection...");
        if (DatabaseManager.testConnection()) {
            System.out.println("    ✓ Database connection successful");
            
            // Test 5: Count Buildings
            System.out.println("\n[5] Checking building data...");
            DatabaseManager.printAllBuildings();
        } else {
            System.out.println("    ✗ Database connection failed");
            System.out.println("    Action: Start MySQL and import setup_database.sql");
        }
        
        // Test 6: Check Folders
        System.out.println("\n[6] Checking building folders...");
        String[] testBuildings = {"1_GedungRektorat", "2_GedungBej", "19_GedungKHDewantara"};
        int foundFolders = 0;
        
        for (String folderName : testBuildings) {
            File folder = new File(BASE_PATH + folderName);
            if (folder.exists() && folder.isDirectory()) {
                File[] photos = folder.listFiles((dir, name) -> {
                    String lower = name.toLowerCase();
                    return lower.endsWith(".jpg") || lower.endsWith(".jpeg") || lower.endsWith(".png");
                });
                
                int photoCount = (photos != null) ? photos.length : 0;
                
                if (photoCount > 0) {
                    System.out.println("    ✓ " + folderName + " (" + photoCount + " photos)");
                    foundFolders++;
                } else {
                    System.out.println("    ! " + folderName + " (folder exists, no photos)");
                }
            } else {
                System.out.println("    ✗ " + folderName + " (not found)");
            }
        }
        
        if (foundFolders == 0) {
            System.out.println("\n    Action: Run FolderGenerator.java to create folders");
            System.out.println("    Then upload photos to folders");
        }
        
        // Final Summary
        System.out.println("\n====================================");
        System.out.println("           SETUP SUMMARY");
        System.out.println("====================================");
        
        boolean mapOk = mapFile.exists();
        boolean driverOk = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            driverOk = true;
        } catch (Exception e) {}
        
        boolean dbOk = DatabaseManager.testConnection();
        
        System.out.println("Map Image:      " + (mapOk ? "✓ OK" : "✗ Missing"));
        System.out.println("MySQL Driver:   " + (driverOk ? "✓ OK" : "✗ Missing"));
        System.out.println("Database:       " + (dbOk ? "✓ OK" : "✗ Not Connected"));
        System.out.println("Photo Folders:  " + foundFolders + "/3 sample folders ready");
        
        if (mapOk && driverOk && dbOk && foundFolders > 0) {
            System.out.println("\n✓✓✓ ALL CHECKS PASSED! ✓✓✓");
            System.out.println("You can now run CampusMap.java");
        } else {
            System.out.println("\n⚠ SETUP INCOMPLETE ⚠");
            System.out.println("Please fix the issues above before running main program");
        }
        
        System.out.println("====================================\n");
    }
}