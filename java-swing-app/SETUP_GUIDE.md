# 📚 CAMPUS MAP - Java Swing Desktop Application (MVC Pattern)

## 🎯 Deskripsi
Aplikasi Peta Kampus Interaktif menggunakan **Java Swing** (Desktop Application) dengan pattern **MVC** dan database **MySQL**.

**BUKAN WEB APP!** Ini adalah aplikasi desktop yang berjalan di komputer lokal.

---

## 📁 Struktur Project (MVC Pattern)

```
java-swing-app/
├── DATABASE_SCHEMA.sql          # SQL untuk create database & tables
├── SETUP_GUIDE.md              # File ini
├── lib/                        # Library JDBC Driver
│   └── mysql-connector-j-8.2.0.jar 
│
└── src/
    ├── Main.java               # Entry point aplikasi
    │
    ├── config/
    │   └── DatabaseConfig.java # Konfigurasi koneksi database
    │
    ├── model/                  # MODEL - Data classes
    │   ├── User.java
    │   ├── Admin.java
    │   ├── Building.java
    │   ├── Room.java
    │   ├── Rating.java
    │   └── BuildingPhoto.java
    │
    ├── dao/                    # DAO - Database Access Objects
    │   ├── UserDAO.java
    │   ├── AdminDAO.java
    │   ├── BuildingDAO.java
    │   ├── RoomDAO.java
    │   ├── RatingDAO.java
    │   └── BuildingPhotoDAO.java
    │
    ├── service/                # SERVICE - Business Logic
    │   ├── AuthService.java
    │   ├── BuildingService.java
    │   └── RatingService.java
    │
    ├── controller/             # CONTROLLER - Logic handlers
    │   ├── LoginController.java
    │   ├── MainController.java
    │   └── AdminController.java
    │
    └── view/                   # VIEW - UI Components (Java Swing)
        ├── SplashScreen.java
        ├── LoginSelectionFrame.java
        ├── LoginFrame.java
        ├── MainFrame.java
        ├── MapPanel.java
        ├── BuildingDetailDialog.java
        ├── RatingDialog.java
        └── AdminManageFrame.java
```

---

## 🛠️ Prerequisites

### 1. Install Software
- ✅ **Java JDK 17+** - [Download](https://www.oracle.com/java/technologies/downloads/)
- ✅ **MySQL 8.0+** - [Download](https://dev.mysql.com/downloads/mysql/)
- ✅ **MySQL JDBC Driver** - [Download](https://dev.mysql.com/downloads/connector/j/)
- ✅ **IDE** (Optional) - IntelliJ IDEA / Eclipse / NetBeans

### 2. Cek Instalasi
```bash
java -version       # Harus Java 17+
javac -version      # Harus javac 17+
mysql --version     # Harus MySQL 8.0+
```

---

## 🚀 Setup Step-by-Step

### STEP 1: Setup Database MySQL

#### 1.1. Start MySQL Server
```bash
# Windows
net start MySQL80

# Mac/Linux
sudo systemctl start mysql
```

#### 1.2. Create Database
```bash
mysql -u root -p < DATABASE_SCHEMA.sql
```

**ATAU** manual:
1. Buka MySQL Workbench / phpMyAdmin
2. Copy semua isi `DATABASE_SCHEMA.sql`
3. Paste dan Execute

#### 1.3. Verifikasi
```sql
mysql -u root -p
USE campus_map_db;
SHOW TABLES;
SELECT * FROM users;
SELECT * FROM buildings;
```

---

### STEP 2: Download MySQL JDBC Driver

#### 2.1. Download
- Kunjungi: https://dev.mysql.com/downloads/connector/j/
- Download versi **Platform Independent** (ZIP)
- Extract file ZIP

#### 2.2. Copy ke Project
1. Buat folder `lib` di root project
2. Copy file `mysql-connector-j-8.2.0.jar` ke folder `lib/`

```
java-swing-app/
├── lib/
│   └── mysql-connector-j-8.2.0.jar   ← Paste di sini
└── src/
```

---

### STEP 3: Konfigurasi Database Connection

Edit file `src/config/DatabaseConfig.java`:

```java
public class DatabaseConfig {
    private static final String URL = "jdbc:mysql://localhost:3306/campus_map_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = ""; // ← GANTI dengan password MySQL kamu!
}
```

**⚠️ PENTING:** Ganti `PASSWORD` sesuai password MySQL kamu!

---

### STEP 4: Compile & Run

#### Cara 1: Pakai Command Line

```bash
# Masuk ke folder project
cd java-swing-app

# Compile semua file Java
javac -cp "lib/*" -d bin src/**/*.java src/**/**/*.java

# Run aplikasi
java -cp "bin;lib/*" Main

# Mac/Linux pakai : instead of ;
java -cp "bin:lib/*" Main
```

#### Cara 2: Pakai IDE (IntelliJ IDEA)

1. Open IntelliJ IDEA
2. **File → Open** → Pilih folder `java-swing-app`
3. **File → Project Structure → Libraries**
4. Klik **+** → Java → Pilih `lib/mysql-connector-j-8.2.0.jar`
5. **Run → Run 'Main'**

#### Cara 3: Pakai IDE (Eclipse)

1. Open Eclipse
2. **File → Import → Existing Projects**
3. Pilih folder `java-swing-app`
4. **Right-click project → Build Path → Add External JARs**
5. Pilih `lib/mysql-connector-j-8.2.0.jar`
6. **Right-click Main.java → Run As → Java Application**

---

## 📝 Default Login Credentials

### 👨‍🎓 User Login
| Username | Password | Faculty |
|----------|----------|---------|
| mahasiswa_fik | password123 | FIK |
| mahasiswa_fk | password123 | FK |
| mahasiswa_fh | password123 | FH |
| mahasiswa_feb | password123 | FEB |
| mahasiswa_fisip | password123 | FISIP |

### 🔧 Admin Login
| Username | Password |
|----------|----------|
| admin | admin123 |

---

## 🎨 Fitur Aplikasi

### ✨ User Mode
1. **Splash Screen** → Tampil 2 detik saat buka aplikasi
2. **Landing Page** → Pilih: LOGIN / GUEST / Admin
3. **Login User** → Input username, password, pilih fakultas
4. **Map View** → Peta kampus dengan warna sesuai fakultas
5. **Building Detail** → Klik gedung untuk lihat detail & rating
6. **Submit Rating** → Beri rating & review (sesuai aturan fakultas)

### 🔧 Admin Mode
1. **Login Admin** → Input username & password
2. **Manage Buildings** → Pilih gedung untuk di-manage
3. **Edit Info** → Update deskripsi, jam buka, fasilitas
4. **Upload Photos** → Upload foto gedung (dari komputer atau URL)
5. **Manage Rooms** → Tambah/edit ruangan dalam gedung

### 🚶 Guest Mode
1. **View Map** → Lihat peta (warna abu-abu)
2. **View Details** → Lihat info gedung
3. **No Rating** → Tidak bisa kasih rating (harus login)

---

## 🔍 MVC Pattern Explained

### MODEL (src/model/)
```java
// Data classes yang represent database tables
User.java           → Table: users
Admin.java          → Table: admins
Building.java       → Table: buildings
Room.java           → Table: rooms
Rating.java         → Table: ratings
BuildingPhoto.java  → Table: building_photos
```

### VIEW (src/view/)
```java
// Java Swing UI components
SplashScreen.java           → Splash screen awal
LoginSelectionFrame.java    → Pilih LOGIN/GUEST/ADMIN
LoginFrame.java             → Form login
MainFrame.java              → Main window dengan map
MapPanel.java               → Panel untuk gambar peta
BuildingDetailDialog.java   → Dialog detail gedung
RatingDialog.java           → Dialog untuk rating
AdminManageFrame.java       → Frame untuk admin manage
```

### CONTROLLER (src/controller/)
```java
// Logic untuk handle user interaction
LoginController.java    → Handle login logic
MainController.java     → Handle main app logic
AdminController.java    → Handle admin operations
```

### DAO (src/dao/)
```java
// Database operations (CRUD)
UserDAO.java        → CRUD untuk users table
BuildingDAO.java    → CRUD untuk buildings table
RatingDAO.java      → CRUD untuk ratings table
// dll...
```

### SERVICE (src/service/)
```java
// Business logic layer
AuthService.java        → Authentication logic
BuildingService.java    → Building operations
RatingService.java      → Rating logic & validation
```

---

## 🐛 Troubleshooting

### Error: "ClassNotFoundException: com.mysql.cj.jdbc.Driver"
**Solusi:** JDBC driver belum di-add ke classpath
1. Download MySQL Connector/J
2. Copy `.jar` file ke folder `lib/`
3. Add ke classpath saat compile/run

### Error: "Access denied for user 'root'@'localhost'"
**Solusi:** Password MySQL salah
1. Buka `DatabaseConfig.java`
2. Ubah `PASSWORD` sesuai password MySQL kamu

### Error: "Communications link failure"
**Solusi:** MySQL server tidak running
```bash
# Start MySQL
net start MySQL80  # Windows
sudo systemctl start mysql  # Linux
```

### Error: Database tidak ada
**Solusi:** Run `DATABASE_SCHEMA.sql` lagi
```bash
mysql -u root -p < DATABASE_SCHEMA.sql
```

---

## 📦 Build JAR File (Executable)

### Cara 1: Command Line
```bash
# Compile
javac -cp "lib/*" -d bin src/**/*.java src/**/**/*.java

# Create manifest file
echo "Main-Class: Main" > manifest.txt
echo "Class-Path: lib/mysql-connector-j-8.2.0.jar" >> manifest.txt

# Create JAR
jar cvfm CampusMap.jar manifest.txt -C bin . lib

# Run JAR
java -jar CampusMap.jar
```

### Cara 2: Pakai IDE
**IntelliJ:**
1. **File → Project Structure → Artifacts**
2. **+ → JAR → From modules with dependencies**
3. Main Class: `Main`
4. **Build → Build Artifacts**

**Eclipse:**
1. **Right-click project → Export**
2. **Java → Runnable JAR file**
3. **Launch configuration: Main**

---

## 🔒 Security Notes

⚠️ **Untuk Production:**

1. **Password Hashing** - Saat ini password plain text (development only)
   ```java
   // TODO: Implement BCrypt atau SHA-256
   ```

2. **SQL Injection** - Sudah aman karena pakai PreparedStatement

3. **Database Credentials** - Jangan hardcode di code
   ```java
   // TODO: Pakai config file external atau environment variables
   ```

---

## 📚 Library & Teknologi

- ☕ **Java 17** - Programming Language
- 🖼️ **Java Swing** - GUI Framework
- 🗄️ **MySQL 8.0** - Database
- 🔌 **JDBC** - Database Connectivity
- 🏗️ **MVC Pattern** - Architecture Pattern

---

## ✅ Quick Start Checklist

- [ ] Java 17+ installed
- [ ] MySQL 8.0+ installed & running
- [ ] Database `campus_map_db` created
- [ ] Sample data inserted
- [ ] JDBC driver downloaded & placed in `lib/`
- [ ] `DatabaseConfig.java` configured
- [ ] Project compiled successfully
- [ ] Application running
- [ ] Test login dengan user sample

---

## 📞 Need Help?

1. Cek section **Troubleshooting**
2. Cek console untuk error messages
3. Pastikan MySQL running
4. Pastikan JDBC driver ada di classpath
5. Cek `DatabaseConfig.java` credentials

---

**🎉 Selamat Menggunakan Campus Map!**
