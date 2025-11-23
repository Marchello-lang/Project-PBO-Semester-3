-- ============================================
-- CAMPUS MAP APPLICATION - MySQL Database Schema
-- ============================================

-- Buat Database
CREATE DATABASE IF NOT EXISTS RevaUPNVJ;
USE RevaUPNVJ;

-- ============================================
-- Table: users (untuk login mahasiswa)
-- ============================================
CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    faculty ENUM('FIK', 'FK', 'FH', 'FEB', 'FISIP') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================
-- Table: admins (untuk login admin)
-- ============================================
CREATE TABLE admins (
    admin_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================
-- Table: buildings (data gedung/fasilitas)
-- ============================================
CREATE TABLE buildings (
    building_id INT PRIMARY KEY AUTO_INCREMENT,
    building_name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    hours VARCHAR(50),
    facilities TEXT,
    faculty ENUM('FIK', 'FK', 'FH', 'FEB', 'FISIP', 'UMUM') DEFAULT 'UMUM',
    -- UMUM untuk fasilitas umum (kantin, perpustakaan, dll)
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- ============================================
-- Table: building_photos (foto gedung)
-- ============================================
CREATE TABLE building_photos (
    photo_id INT PRIMARY KEY AUTO_INCREMENT,
    building_id INT NOT NULL,
    photo_url VARCHAR(500) NOT NULL,
    photo_index INT DEFAULT 0, -- urutan foto (0-5)
    uploaded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (building_id) REFERENCES buildings(building_id) ON DELETE CASCADE
);

-- ============================================
-- Table: rooms (ruangan dalam gedung)
-- ============================================
CREATE TABLE rooms (
    room_id INT PRIMARY KEY AUTO_INCREMENT,
    building_id INT NOT NULL,
    room_name VARCHAR(100) NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (building_id) REFERENCES buildings(building_id) ON DELETE CASCADE
);

-- ============================================
-- Table: ratings (rating untuk gedung/ruangan)
-- ============================================
CREATE TABLE ratings (
    rating_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    room_id INT NOT NULL,
    building_id INT NOT NULL,
    rating INT NOT NULL CHECK (rating >= 1 AND rating <= 5),
    review TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (room_id) REFERENCES rooms(room_id) ON DELETE CASCADE,
    FOREIGN KEY (building_id) REFERENCES buildings(building_id) ON DELETE CASCADE,
    UNIQUE KEY unique_user_room (user_id, room_id) -- user hanya bisa rate 1x per room
);

-- ============================================
-- INSERT DATA DEFAULT
-- ============================================

-- Insert Admin Default (password: admin123)
INSERT INTO admins (username, password) VALUES 
('admin', 'admin123'); -- Di production, hash password dengan BCrypt!

-- Insert Sample Users
INSERT INTO users (username, password, faculty) VALUES 
('mahasiswa_fik', 'password123', 'FIK'),
('mahasiswa_fk', 'password123', 'FK'),
('mahasiswa_fh', 'password123', 'FH'),
('mahasiswa_feb', 'password123', 'FEB'),
('mahasiswa_fisip', 'password123', 'FISIP');

-- Insert Buildings
INSERT INTO buildings (building_name, description, hours, facilities, faculty) VALUES 
-- Fakultas
('Gedung FIK', 'Gedung Fakultas Ilmu Komputer dengan lab modern', '07.00 - 21.00', 'Lab Komputer, Ruang Kelas, Wifi', 'FIK'),
('Gedung FK', 'Gedung Fakultas Kedokteran', '08.00 - 20.00', 'Lab Anatomi, Ruang Praktek, Perpustakaan', 'FK'),
('Gedung FH', 'Gedung Fakultas Hukum', '08.00 - 17.00', 'Ruang Sidang, Perpustakaan Hukum', 'FH'),
('Gedung FEB', 'Gedung Fakultas Ekonomi dan Bisnis', '07.00 - 21.00', 'Ruang Kuliah, Lab Akuntansi', 'FEB'),
('Gedung FISIP', 'Gedung Fakultas Ilmu Sosial dan Politik', '08.00 - 20.00', 'Ruang Diskusi, Ruadio Kampus', 'FISIP'),

-- Fasilitas Umum
('Perpustakaan Pusat', 'Perpustakaan utama universitas', '08.00 - 22.00', 'Ruang Baca, Koleksi Buku, Wifi, AC', 'UMUM'),
('Kantin Utama', 'Kantin dengan berbagai pilihan makanan', '06.00 - 20.00', 'Food Court, Area Duduk', 'UMUM'),
('Masjid Kampus', 'Tempat ibadah umat muslim', '04.00 - 22.00', 'Tempat Wudhu, Perpustakaan Islam', 'UMUM'),
('Gedung Rektorat', 'Pusat administrasi universitas', '08.00 - 17.00', 'Ruang Rapat, Administrasi', 'UMUM'),
('Lapangan Olahraga', 'Area olahraga dan kegiatan kemahasiswaan', '06.00 - 18.00', 'Lapangan Futsal, Basket, Jogging Track', 'UMUM');

-- Insert Sample Rooms untuk FIK
INSERT INTO rooms (building_id, room_name, description) VALUES 
(1, 'Lab Pemrograman 1', 'Lab untuk mata kuliah pemrograman dasar'),
(1, 'Lab Jaringan', 'Lab untuk praktikum jaringan komputer'),
(1, 'Ruang Kelas 301', 'Ruang kelas lantai 3');

-- Insert Sample Rooms untuk Perpustakaan
INSERT INTO rooms (building_id, room_name, description) VALUES 
(6, 'Ruang Baca Utama', 'Ruang baca besar dengan AC'),
(6, 'Ruang Diskusi', 'Ruang untuk diskusi kelompok');

-- Insert Sample Rooms untuk Kantin
INSERT INTO rooms (building_id, room_name, description) VALUES 
(7, 'Food Court', 'Area makan dengan berbagai tenant'),
(7, 'Warung Nasi', 'Warung nasi tradisional');

-- ============================================
-- VIEWS (untuk kemudahan query)
-- ============================================

-- View: Detail Building dengan Average Rating
CREATE VIEW building_details AS
SELECT 
    b.building_id,
    b.building_name,
    b.description,
    b.hours,
    b.facilities,
    b.faculty,
    COALESCE(AVG(r.rating), 0) as avg_rating,
    COUNT(DISTINCT r.rating_id) as total_ratings
FROM buildings b
LEFT JOIN rooms ro ON b.building_id = ro.building_id
LEFT JOIN ratings r ON ro.room_id = r.room_id
GROUP BY b.building_id;

-- View: Rating Detail dengan User Info
CREATE VIEW rating_details AS
SELECT 
    r.rating_id,
    r.rating,
    r.review,
    r.created_at,
    u.username,
    u.faculty as user_faculty,
    ro.room_name,
    b.building_name,
    b.faculty as building_faculty
FROM ratings r
JOIN users u ON r.user_id = u.user_id
JOIN rooms ro ON r.room_id = ro.room_id
JOIN buildings b ON r.building_id = b.building_id;

-- ============================================
-- INDEXES (untuk performa)
-- ============================================

CREATE INDEX idx_building_faculty ON buildings(faculty);
CREATE INDEX idx_user_faculty ON users(faculty);
CREATE INDEX idx_rating_building ON ratings(building_id);
CREATE INDEX idx_rating_user ON ratings(user_id);
CREATE INDEX idx_room_building ON rooms(building_id);

-- ============================================
-- STORED PROCEDURES
-- ============================================

-- Procedure: Check if user can rate a room
DELIMITER $$
CREATE PROCEDURE can_user_rate_room(
    IN p_user_id INT,
    IN p_room_id INT,
    OUT p_can_rate BOOLEAN,
    OUT p_message VARCHAR(255)
)
BEGIN
    DECLARE v_user_faculty VARCHAR(10);
    DECLARE v_building_faculty VARCHAR(10);
    DECLARE v_already_rated INT;
    
    -- Get user faculty
    SELECT faculty INTO v_user_faculty FROM users WHERE user_id = p_user_id;
    
    -- Get building faculty
    SELECT b.faculty INTO v_building_faculty 
    FROM buildings b
    JOIN rooms r ON b.building_id = r.building_id
    WHERE r.room_id = p_room_id;
    
    -- Check if already rated
    SELECT COUNT(*) INTO v_already_rated 
    FROM ratings 
    WHERE user_id = p_user_id AND room_id = p_room_id;
    
    -- Logic
    IF v_already_rated > 0 THEN
        SET p_can_rate = FALSE;
        SET p_message = 'Anda sudah memberi rating untuk ruangan ini';
    ELSEIF v_building_faculty = 'UMUM' THEN
        SET p_can_rate = TRUE;
        SET p_message = 'Fasilitas umum dapat dirating oleh semua fakultas';
    ELSEIF v_user_faculty = v_building_faculty THEN
        SET p_can_rate = TRUE;
        SET p_message = 'Anda dapat memberi rating untuk gedung fakultas Anda';
    ELSE
        SET p_can_rate = FALSE;
        SET p_message = 'Anda hanya dapat memberi rating untuk gedung fakultas Anda sendiri';
    END IF;
END$$
DELIMITER ;

-- ============================================
-- QUERY EXAMPLES (untuk testing)
-- ============================================

-- Get all buildings dengan rating
-- SELECT * FROM building_details;

-- Get ratings untuk gedung tertentu
-- SELECT * FROM rating_details WHERE building_name = 'Gedung FIK';

-- Check average rating per room
-- SELECT 
--     r.room_name,
--     b.building_name,
--     AVG(rt.rating) as avg_rating,
--     COUNT(rt.rating_id) as total_ratings
-- FROM rooms r
-- JOIN buildings b ON r.building_id = b.building_id
-- LEFT JOIN ratings rt ON r.room_id = rt.room_id
-- GROUP BY r.room_id;

-- ============================================
-- NOTES
-- ============================================
-- 1. Password di-store plain text untuk development
--    Untuk production, gunakan BCrypt atau Argon2 untuk hash password!
-- 
-- 2. Struktur database ini mendukung:
--    - Multi-user dengan fakultas berbeda
--    - Multi-building dengan fakultas atau umum
--    - Rating system dengan constraint fakultas
--    - Photo management untuk setiap gedung
--    - Room-based rating system
--
-- 3. Relasi:
--    - buildings -> rooms (1:N)
--    - buildings -> building_photos (1:N)
--    - rooms -> ratings (1:N)
--    - users -> ratings (1:N)
--
-- 4. Business Rules:
--    - User fakultas X hanya bisa rating gedung fakultas X
--    - User any fakultas bisa rating gedung UMUM
--    - Admin tidak bisa rating, hanya manage data
--    - User hanya bisa rating 1x per room
