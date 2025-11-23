# 📋 IMPLEMENTATION GUIDE - File yang Belum Lengkap

File-file berikut struktur dan template-nya sudah disediakan, kamu tinggal lengkapi isinya sesuai pattern yang sudah ada.

---

## 🔧 DAO Layer (src/dao/)

### RoomDAO.java
```java
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
        // SQL: SELECT * FROM rooms WHERE building_id = ?
        // Return List<Room>
    }
    
    // Get room by ID
    public Room getRoomById(int roomId) {
        // SQL: SELECT * FROM rooms WHERE room_id = ?
        // Return Room
    }
    
    // Add new room
    public boolean addRoom(Room room) {
        // SQL: INSERT INTO rooms (building_id, room_name, description) VALUES (?, ?, ?)
        // Return true jika success
    }
}
```

### RatingDAO.java
```java
package dao;

import config.DatabaseConfig;
import model.Rating;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RatingDAO {
    
    private Connection getConnection() throws SQLException {
        return DatabaseConfig.getInstance().getConnection();
    }
    
    // Submit rating baru
    public boolean submitRating(Rating rating) {
        // SQL: INSERT INTO ratings (user_id, room_id, building_id, rating, review) 
        //      VALUES (?, ?, ?, ?, ?)
        // Return true jika success
    }
    
    // Get ratings by room ID
    public List<Rating> getRatingsByRoomId(int roomId) {
        // SQL: SELECT r.*, u.username, u.faculty 
        //      FROM ratings r 
        //      JOIN users u ON r.user_id = u.user_id 
        //      WHERE r.room_id = ?
        // Return List<Rating> dengan username & faculty terisi
    }
    
    // Check if user sudah rating room ini
    public boolean hasUserRatedRoom(int userId, int roomId) {
        // SQL: SELECT COUNT(*) FROM ratings WHERE user_id = ? AND room_id = ?
        // Return true jika sudah pernah rating
    }
    
    // Get average rating for room
    public double getAverageRatingByRoom(int roomId) {
        // SQL: SELECT AVG(rating) FROM ratings WHERE room_id = ?
        // Return average rating (0.0 jika belum ada rating)
    }
}
```

### BuildingPhotoDAO.java
```java
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
        // SQL: SELECT * FROM building_photos 
        //      WHERE building_id = ? 
        //      ORDER BY photo_index ASC
        // Return List<BuildingPhoto>
    }
    
    // Add photo
    public boolean addPhoto(BuildingPhoto photo) {
        // SQL: INSERT INTO building_photos (building_id, photo_url, photo_index) 
        //      VALUES (?, ?, ?)
        // Return true jika success
    }
    
    // Delete photo
    public boolean deletePhoto(int photoId) {
        // SQL: DELETE FROM building_photos WHERE photo_id = ?
        // Return true jika success
    }
}
```

---

## 📊 Service Layer (src/service/)

### AuthService.java
```java
package service;

import dao.UserDAO;
import dao.AdminDAO;
import model.User;
import model.Admin;

public class AuthService {
    
    private UserDAO userDAO = new UserDAO();
    private AdminDAO adminDAO = new AdminDAO();
    
    // Login user
    public User loginUser(String username, String password) {
        return userDAO.login(username, password);
    }
    
    // Login admin
    public User loginAdmin(String username, String password) {
        Admin admin = adminDAO.login(username, password);
        if (admin != null) {
            return admin.toUser();
        }
        return null;
    }
    
    // Register user
    public boolean registerUser(String username, String password, String faculty) {
        // Validasi
        if (username == null || username.trim().isEmpty()) {
            return false;
        }
        if (userDAO.isUsernameExists(username)) {
            return false;
        }
        return userDAO.register(username, password, faculty);
    }
}
```

### BuildingService.java
```java
package service;

import dao.BuildingDAO;
import dao.RoomDAO;
import dao.BuildingPhotoDAO;
import model.Building;
import model.Room;
import model.BuildingPhoto;
import java.util.List;

public class BuildingService {
    
    private BuildingDAO buildingDAO = new BuildingDAO();
    private RoomDAO roomDAO = new RoomDAO();
    private BuildingPhotoDAO photoDAO = new BuildingPhotoDAO();
    
    // Get all buildings
    public List<Building> getAllBuildings() {
        return buildingDAO.getAllBuildings();
    }
    
    // Get building dengan rooms dan photos
    public Building getBuildingDetail(int buildingId) {
        Building building = buildingDAO.getBuildingById(buildingId);
        if (building != null) {
            // Load rooms
            List<Room> rooms = roomDAO.getRoomsByBuildingId(buildingId);
            building.setRooms(rooms);
            
            // Load photos
            List<BuildingPhoto> photos = photoDAO.getPhotosByBuildingId(buildingId);
            for (BuildingPhoto photo : photos) {
                building.addPhotoUrl(photo.getPhotoUrl());
            }
        }
        return building;
    }
    
    // Update building
    public boolean updateBuilding(Building building) {
        return buildingDAO.updateBuilding(building);
    }
    
    // Add photo
    public boolean addPhoto(int buildingId, String photoUrl, int photoIndex) {
        BuildingPhoto photo = new BuildingPhoto(buildingId, photoUrl, photoIndex);
        return photoDAO.addPhoto(photo);
    }
}
```

### RatingService.java
```java
package service;

import dao.RatingDAO;
import dao.RoomDAO;
import dao.BuildingDAO;
import model.Rating;
import model.Room;
import model.Building;
import model.User;
import java.util.List;

public class RatingService {
    
    private RatingDAO ratingDAO = new RatingDAO();
    private RoomDAO roomDAO = new RoomDAO();
    private BuildingDAO buildingDAO = new BuildingDAO();
    
    // Submit rating dengan validation
    public boolean submitRating(User user, int roomId, int rating, String review) {
        // Validasi 1: User sudah login (bukan guest)
        if (user.isGuest()) {
            System.out.println("Guest tidak bisa rating. Silakan login.");
            return false;
        }
        
        // Validasi 2: Admin tidak boleh rating
        if (user.isAdmin()) {
            System.out.println("Admin tidak boleh memberikan rating.");
            return false;
        }
        
        // Validasi 3: User sudah pernah rating room ini?
        if (ratingDAO.hasUserRatedRoom(user.getUserId(), roomId)) {
            System.out.println("Anda sudah memberikan rating untuk ruangan ini.");
            return false;
        }
        
        // Validasi 4: Check fakultas permission
        Room room = roomDAO.getRoomById(roomId);
        if (room == null) {
            return false;
        }
        
        Building building = buildingDAO.getBuildingById(room.getBuildingId());
        if (building == null) {
            return false;
        }
        
        // Jika gedung UMUM, semua fakultas bisa rating
        if (!building.isPublicFacility()) {
            // Jika gedung fakultas, hanya mahasiswa fakultas itu yang bisa rating
            if (!user.getFaculty().equals(building.getFaculty())) {
                System.out.println("Anda hanya bisa rating gedung fakultas Anda sendiri.");
                return false;
            }
        }
        
        // Submit rating
        Rating ratingObj = new Rating(user.getUserId(), roomId, building.getBuildingId(), rating, review);
        return ratingDAO.submitRating(ratingObj);
    }
    
    // Get ratings untuk room
    public List<Rating> getRoomRatings(int roomId) {
        return ratingDAO.getRatingsByRoomId(roomId);
    }
    
    // Check if user can rate
    public String canUserRate(User user, int roomId) {
        if (user.isGuest()) {
            return "Silakan login untuk memberikan rating.";
        }
        if (user.isAdmin()) {
            return "Admin tidak dapat memberikan rating.";
        }
        if (ratingDAO.hasUserRatedRoom(user.getUserId(), roomId)) {
            return "Anda sudah memberikan rating untuk ruangan ini.";
        }
        
        Room room = roomDAO.getRoomById(roomId);
        Building building = buildingDAO.getBuildingById(room.getBuildingId());
        
        if (building.isPublicFacility()) {
            return "OK"; // Fasilitas umum bisa dirating semua
        }
        
        if (user.getFaculty().equals(building.getFaculty())) {
            return "OK"; // Fakultas sama
        }
        
        return "Anda hanya dapat memberikan rating untuk gedung fakultas Anda.";
    }
}
```

---

## 🎮 Controller Layer (src/controller/)

### LoginController.java
```java
package controller;

import service.AuthService;
import model.User;

public class LoginController {
    
    private AuthService authService = new AuthService();
    
    // Handle login user
    public User handleUserLogin(String username, String password) {
        User user = authService.loginUser(username, password);
        if (user != null) {
            System.out.println("✓ Login berhasil: " + user.getUsername());
        } else {
            System.out.println("✗ Login gagal: Username atau password salah");
        }
        return user;
    }
    
    // Handle login admin
    public User handleAdminLogin(String username, String password) {
        User admin = authService.loginAdmin(username, password);
        if (admin != null) {
            System.out.println("✓ Login Admin berhasil: " + admin.getUsername());
        } else {
            System.out.println("✗ Login Admin gagal: Username atau password salah");
        }
        return admin;
    }
}
```

### MainController.java
```java
package controller;

import service.BuildingService;
import service.RatingService;
import model.Building;
import model.User;
import java.util.List;

public class MainController {
    
    private BuildingService buildingService = new BuildingService();
    private RatingService ratingService = new RatingService();
    
    // Get all buildings for map
    public List<Building> getBuildings() {
        return buildingService.getAllBuildings();
    }
    
    // Get building detail when clicked
    public Building getBuildingDetail(int buildingId) {
        return buildingService.getBuildingDetail(buildingId);
    }
    
    // Submit rating
    public boolean submitRating(User user, int roomId, int rating, String review) {
        return ratingService.submitRating(user, roomId, rating, review);
    }
}
```

### AdminController.java
```java
package controller;

import service.BuildingService;
import model.Building;

public class AdminController {
    
    private BuildingService buildingService = new BuildingService();
    
    // Update building info
    public boolean updateBuilding(Building building) {
        boolean success = buildingService.updateBuilding(building);
        if (success) {
            System.out.println("✓ Building updated: " + building.getBuildingName());
        }
        return success;
    }
    
    // Add photo
    public boolean addBuildingPhoto(int buildingId, String photoUrl, int photoIndex) {
        return buildingService.addPhoto(buildingId, photoUrl, photoIndex);
    }
}
```

---

## 🖼️ View Layer (src/view/)

Semua file view sudah ada skeleton-nya di folder `frontend/src/ui/`. Kamu tinggal:

1. **Copy** file-file dari `frontend/src/ui/` ke `src/view/`
2. **Update** import statements
3. **Integrate** dengan Controller

### Contoh Integration:

```java
// Di LoginFrame.java
import controller.LoginController;

public class LoginFrame extends JFrame {
    private LoginController controller = new LoginController();
    
    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        
        User user = controller.handleUserLogin(username, password);
        
        if (user != null) {
            dispose();
            new MainFrame(user);
        } else {
            JOptionPane.showMessageDialog(this, 
                "Login gagal! Username atau password salah.", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}
```

---

## 📝 TODO Checklist

### DAO Layer
- [ ] RoomDAO.java - Complete all methods
- [ ] RatingDAO.java - Complete all methods
- [ ] BuildingPhotoDAO.java - Complete all methods

### Service Layer
- [ ] AuthService.java - Implement validation
- [ ] BuildingService.java - Add error handling
- [ ] RatingService.java - Complete permission logic

### Controller Layer
- [ ] LoginController.java - Add error handling
- [ ] MainController.java - Integrate with views
- [ ] AdminController.java - Add validation

### View Layer
- [ ] LoginFrame.java - Complete UI + integrate controller
- [ ] MainFrame.java - Build main UI dengan MapPanel
- [ ] MapPanel.java - Render peta dengan clickable buildings
- [ ] BuildingDetailDialog.java - Show building info + rooms
- [ ] RatingDialog.java - Form untuk submit rating
- [ ] AdminManageFrame.java - Admin panel untuk manage buildings

---

## 🎨 UI Design Tips

### Color Palette (sesuai fakultas)
```java
// Faculty colors
public static final Color FIK_COLOR = new Color(42, 115, 50);    // Hijau
public static final Color FK_COLOR = new Color(220, 53, 69);      // Merah
public static final Color FH_COLOR = new Color(255, 193, 7);      // Kuning
public static final Color FEB_COLOR = new Color(0, 123, 255);     // Biru
public static final Color FISIP_COLOR = new Color(108, 117, 125); // Abu-abu gelap
public static final Color UMUM_COLOR = new Color(128, 128, 128);  // Abu-abu
public static final Color GUEST_COLOR = new Color(200, 200, 200); // Abu-abu terang
```

### Fonts
```java
Font titleFont = new Font("Arial", Font.BOLD, 24);
Font subtitleFont = new Font("Arial", Font.BOLD, 18);
Font normalFont = new Font("Arial", Font.PLAIN, 14);
Font smallFont = new Font("Arial", Font.PLAIN, 12);
```

---

## 🚀 Running the App

1. Compile semua file:
   ```bash
   javac -cp "lib/*" -d bin src/**/*.java src/**/**/*.java
   ```

2. Run aplikasi:
   ```bash
   java -cp "bin;lib/*" Main
   ```

3. Test sequence:
   - Splash screen muncul
   - Landing page (LOGIN/GUEST/ADMIN)
   - Login dengan user sample
   - Main frame dengan peta
   - Klik gedung
   - Lihat detail & submit rating

---

**Good luck! 🎉**
