-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               8.4.3 - MySQL Community Server - GPL
-- Server OS:                    Win64
-- HeidiSQL Version:             12.8.0.6908
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- Dumping structure for table revaupnvj.admins
DROP TABLE IF EXISTS `admins`;
CREATE TABLE IF NOT EXISTS `admins` (
  `admin_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`admin_id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table revaupnvj.admins: ~1 rows (approximately)
INSERT INTO `admins` (`admin_id`, `username`, `password`, `created_at`) VALUES
	(1, 'admin', 'admin123', '2025-11-26 01:06:34');

-- Dumping structure for table revaupnvj.buildings
DROP TABLE IF EXISTS `buildings`;
CREATE TABLE IF NOT EXISTS `buildings` (
  `building_id` int NOT NULL AUTO_INCREMENT,
  `building_number` varchar(10) DEFAULT NULL,
  `building_name` varchar(100) NOT NULL,
  `description` text,
  `hours` varchar(50) DEFAULT NULL,
  `facilities` text,
  `faculty` enum('FIK','FK','FH','FEB','FISIP','UMUM') DEFAULT 'UMUM',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`building_id`),
  UNIQUE KEY `building_name` (`building_name`),
  UNIQUE KEY `building_number` (`building_number`),
  KEY `idx_building_faculty` (`faculty`),
  KEY `idx_building_number` (`building_number`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table revaupnvj.buildings: ~23 rows (approximately)
INSERT INTO `buildings` (`building_id`, `building_number`, `building_name`, `description`, `hours`, `facilities`, `faculty`, `created_at`, `updated_at`) VALUES
	(1, '1', 'Gedung Rektorat', 'Gedung pusat administrasi dan pimpinan universitas', '08:00 - 17:00', 'Auditorium BTI, Bank BNI', 'UMUM', '2025-11-26 01:06:34', '2025-11-26 01:06:34'),
	(2, '2', 'Gedung BEJ (Bursa Efek Jakarta)', 'Gedung Fakultas Ekonomi dengan lab trading dan simulasi pasar modal', '07:00 - 20:00', 'Aula BEJ, Ubin Coklat', 'FEB', '2025-11-26 01:06:34', '2025-11-26 01:06:34'),
	(3, '3', 'Gedung Soepomo', 'Gedung Fakultas Hukum dengan ruang kelas modern', '07:00 - 21:00', 'Ruang Kelas', 'FH', '2025-11-26 01:06:34', '2025-11-26 01:06:34'),
	(4, '4', 'Gedung Muhammad Yamin', 'Gedung FISIP dengan berbagai lab media dan komunikasi', '07:00 - 21:00', 'Auditorium FISIP, Lab Diplomasi, Lab Film, Lab Fotografi, Lab Multimedia, Lab Podcast, Lab Politik, Lab Radio, Lab Big Data, Ruang Baca', 'FISIP', '2025-11-26 01:06:34', '2025-11-26 01:06:34'),
	(5, '5', 'Gedung Husni Tamrin', 'Gedung FEB', '07:00 - 21:00', 'Auditorium FISIP, Lab Diplomasi, Lab Film, Lab Fotografi, Lab Multimedia, Lab Podcast, Lab Politik, Lab Radio, Lab Big Data, Ruang Baca', 'FEB', '2025-11-26 01:06:34', '2025-11-26 01:06:34'),
	(6, '6', 'Gedung Wahidin Hudoso', 'Gedung FK', '00:00 - 24:00', 'Ruang Kelas', 'FK', '2025-11-26 01:06:34', '2025-11-26 01:06:34'),
	(7, '7', 'Lapangan Upacara', 'Lapangan terbuka untuk upacara dan kegiatan kemahasiswaan', '00:00 - 24:00', 'Lapangan Upacara', 'UMUM', '2025-11-26 01:06:34', '2025-11-26 01:06:34'),
	(8, '8', 'Gedung R.A. Kartini', 'Gedung Fakultas Hukum untuk program pascasarjana', '07:00 - 18:00', 'Lab Bahasa, Ruang Kelas Magister Doktor Hukum, Ruang Rapat Magister Hukum', 'FH', '2025-11-26 01:06:34', '2025-11-26 01:06:34'),
	(9, '9', 'Kantin Pratama', 'Kantin dengan berbagai pilihan makanan dan minuman', '06:00 - 18:00', 'Food Court, Area Makan', 'UMUM', '2025-11-26 01:06:34', '2025-11-26 01:06:34'),
	(10, '10', 'APU (Arsitektur Planologi UPNVJ)', 'Gedung program studi Arsitektur dan Planologi', '07:00 - 18:00', 'Studio Arsitektur, Ruang Kuliah', 'UMUM', '2025-11-26 01:06:34', '2025-11-26 01:06:34'),
	(11, '11', 'Kantin Utama', 'Kantin pusat kampus dengan banyak pilihan tenant', '06:00 - 20:00', 'Kantin Kanan, Kantin Kiri, Food Court', 'UMUM', '2025-11-26 01:06:34', '2025-11-26 01:06:34'),
	(12, '12', 'Gedung Soetomo', 'Gedung dengan perpustakaan dan ruang baca yang nyaman', '07:00 - 21:00', 'Perpustakaan, Ruang Baca', 'UMUM', '2025-11-26 01:06:34', '2025-11-26 01:06:34'),
	(13, '13', 'Masjid Manbaul Ulum', 'Masjid kampus untuk ibadah umat muslim', '04:00 - 22:00', 'Ruang Sholat, Tempat Wudhu, Perpustakaan Islam', 'UMUM', '2025-11-26 01:06:34', '2025-11-26 01:06:34'),
	(14, '14', 'Gedung Abdul Rachman Saleh', 'Gedung administrasi dan layanan mahasiswa', '08:00 - 17:00', 'Administrasi, Customer Service', 'UMUM', '2025-11-26 01:06:34', '2025-11-26 01:06:34'),
	(15, '15', 'Gedung Yos Sudarso', 'Gedung Fakultas Hukum dengan fasilitas praktik hukum lengkap', '07:00 - 20:00', 'Selasar, Lab Perancangan Kontrak, Peradilan Semu (Moot Court), Ruang Baca, Ruang Kelas, Ruang Podcast', 'FH', '2025-11-26 01:06:34', '2025-11-26 01:06:34'),
	(16, '16', 'Gedung KH Dewantara', 'Gedung Fakultas Ilmu Komputer dengan teknologi terkini', '07:00 - 22:00', 'Digital Library, Lab Cybersecurity, Lab Programming, Ruang Rapat, Selasar, Ruang Podcast', 'FIK', '2025-11-26 01:06:34', '2025-11-26 01:06:34'),
	(17, '17', 'Beskabean (Area Cafe)', 'Area cafe dan tempat nongkrong mahasiswa', '08:00 - 20:00', 'Cafe, Area Duduk Outdoor', 'UMUM', '2025-11-26 01:06:34', '2025-11-26 01:06:34'),
	(18, '18', 'Gedung Dewi Sartika', 'Gedung dengan fasilitas olahraga dan ruang kelas', '07:00 - 20:00', 'Mash Classroom, Area Olahraga, Ruang Kelas', 'UMUM', '2025-11-26 01:06:34', '2025-11-26 01:06:34'),
	(19, '19', 'Parkiran Beskabean', 'Area parkir dekat area kantin beskabean', '00:00 - 24:00', 'Area Parkir Motor', 'UMUM', '2025-11-26 01:06:34', '2025-11-26 01:06:34'),
	(20, '20', 'Gedung Cipto Mangunkusomo', 'Gedung FK', '00:00 - 24:00', 'Ruang Kelas', 'FK', '2025-11-26 01:06:34', '2025-11-26 01:06:34'),
	(21, '21', 'Parkiran Belakang', 'Area parkir belakang kampus dekat gedung fakultas', '00:00 - 24:00', 'Area Parkir Motor dan Mobil', 'UMUM', '2025-11-26 01:06:34', '2025-11-26 01:06:34'),
	(22, '22', 'Parkiran Kantin', 'Area parkir dekat kantin dengan fasilitas wall climbing', '00:00 - 24:00', 'Area Parkir, Wall Climbing', 'UMUM', '2025-11-26 01:06:34', '2025-11-26 01:06:34'),
	(23, '23', 'Lapangan Basket & Volley', 'Area olahraga outdoor untuk basket dan voli', '06:00 - 21:00', 'Lapangan Basket, Lapangan Volley', 'UMUM', '2025-11-26 01:06:34', '2025-11-26 01:06:34');

-- Dumping structure for view revaupnvj.building_details
DROP VIEW IF EXISTS `building_details`;
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `building_details` (
	`building_id` INT NOT NULL,
	`building_number` VARCHAR(1) NULL COLLATE 'utf8mb4_0900_ai_ci',
	`building_name` VARCHAR(1) NOT NULL COLLATE 'utf8mb4_0900_ai_ci',
	`description` TEXT NULL COLLATE 'utf8mb4_0900_ai_ci',
	`hours` VARCHAR(1) NULL COLLATE 'utf8mb4_0900_ai_ci',
	`facilities` TEXT NULL COLLATE 'utf8mb4_0900_ai_ci',
	`faculty` ENUM('FIK','FK','FH','FEB','FISIP','UMUM') NULL COLLATE 'utf8mb4_0900_ai_ci',
	`avg_rating` DECIMAL(14,4) NOT NULL,
	`total_ratings` BIGINT NOT NULL
) ENGINE=MyISAM;

-- Dumping structure for table revaupnvj.building_photos
DROP TABLE IF EXISTS `building_photos`;
CREATE TABLE IF NOT EXISTS `building_photos` (
  `photo_id` int NOT NULL AUTO_INCREMENT,
  `building_id` int NOT NULL,
  `photo_url` varchar(500) NOT NULL,
  `photo_index` int DEFAULT '0',
  `uploaded_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`photo_id`),
  KEY `building_id` (`building_id`),
  CONSTRAINT `building_photos_ibfk_1` FOREIGN KEY (`building_id`) REFERENCES `buildings` (`building_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table revaupnvj.building_photos: ~0 rows (approximately)

-- Dumping structure for table revaupnvj.building_ratings
DROP TABLE IF EXISTS `building_ratings`;
CREATE TABLE IF NOT EXISTS `building_ratings` (
  `rating_id` int NOT NULL AUTO_INCREMENT,
  `building_number` varchar(10) DEFAULT NULL,
  `user_id` int NOT NULL,
  `rating` int NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`rating_id`),
  UNIQUE KEY `unique_user_building` (`user_id`,`building_number`),
  KEY `idx_building_rating` (`building_number`),
  CONSTRAINT `building_ratings_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE,
  CONSTRAINT `building_ratings_chk_1` CHECK (((`rating` >= 1) and (`rating` <= 5)))
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table revaupnvj.building_ratings: ~8 rows (approximately)
INSERT INTO `building_ratings` (`rating_id`, `building_number`, `user_id`, `rating`, `created_at`) VALUES
	(1, '1', 1, 5, '2025-11-26 01:06:34'),
	(2, '1', 2, 4, '2025-11-26 01:06:34'),
	(3, '2', 1, 4, '2025-11-26 01:06:34'),
	(4, '4', 1, 5, '2025-11-26 01:06:34'),
	(5, '4', 2, 5, '2025-11-26 01:06:34'),
	(6, '19', 1, 5, '2025-11-26 01:06:34'),
	(7, '19', 2, 5, '2025-11-26 01:06:34'),
	(8, '21', 1, 4, '2025-11-26 01:06:34');

-- Dumping structure for table revaupnvj.facilities
DROP TABLE IF EXISTS `facilities`;
CREATE TABLE IF NOT EXISTS `facilities` (
  `facility_id` int NOT NULL AUTO_INCREMENT,
  `building_number` varchar(10) DEFAULT NULL,
  `building_id` int DEFAULT NULL,
  `facility_name` varchar(100) NOT NULL,
  `room_number` varchar(20) DEFAULT NULL,
  `description` text,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`facility_id`),
  KEY `building_id` (`building_id`),
  KEY `idx_facility_building` (`building_number`),
  CONSTRAINT `facilities_ibfk_1` FOREIGN KEY (`building_id`) REFERENCES `buildings` (`building_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table revaupnvj.facilities: ~51 rows (approximately)
INSERT INTO `facilities` (`facility_id`, `building_number`, `building_id`, `facility_name`, `room_number`, `description`, `created_at`) VALUES
	(1, '1', 1, 'Auditorium BTI', 'Lt.1', 'Auditorium untuk wisuda dan acara besar universitas dengan kapasitas 1000 orang', '2025-11-26 01:06:34'),
	(2, '1', 1, 'Bank BNI', 'Lt.1', 'Layanan perbankan untuk civitas akademika', '2025-11-26 01:06:34'),
	(3, '2', 2, 'Aula BEJ', 'Lt.2', 'Aula untuk kegiatan mahasiswa FEB dan seminar ekonomi', '2025-11-26 01:06:34'),
	(4, '2', 2, 'Area Ubin Coklat', 'Lt.1', 'Area berkumpul dan diskusi mahasiswa FEB', '2025-11-26 01:06:34'),
	(5, '3', 3, 'Ruang Kelas Soepomo', 'R.201', 'Ruang kelas dengan AC dan proyektor', '2025-11-26 01:06:34'),
	(6, '4', 4, 'Auditorium FISIP', 'Lt.1', 'Auditorium berkapasitas 500 orang untuk acara fakultas', '2025-11-26 01:06:34'),
	(7, '4', 4, 'Laboratorium Diplomasi', 'R.201', 'Lab untuk praktik diplomasi dan hubungan internasional', '2025-11-26 01:06:34'),
	(8, '4', 4, 'Lab Film & Sinematografi', 'R.202', 'Studio produksi film dengan kamera dan lighting profesional', '2025-11-26 01:06:34'),
	(9, '4', 4, 'Lab Fotografi', 'R.301', 'Studio foto dengan backdrop dan peralatan lengkap', '2025-11-26 01:06:34'),
	(10, '4', 4, 'Lab Multimedia', 'R.302', 'Lab editing video menggunakan Adobe Premiere dan After Effects', '2025-11-26 01:06:34'),
	(11, '4', 4, 'Studio Podcast FISIP', 'R.303', 'Studio podcast dengan mic condenser dan mixer audio', '2025-11-26 01:06:34'),
	(12, '4', 4, 'Lab Politik & Pemerintahan', 'R.203', 'Lab simulasi sistem pemerintahan dan kebijakan publik', '2025-11-26 01:06:34'),
	(13, '4', 4, 'Studio Radio Kampus', 'R.304', 'Studio radio dengan peralatan broadcasting profesional', '2025-11-26 01:06:34'),
	(14, '4', 4, 'Lab Big Data Analytics', 'R.401', 'Lab analisis data dengan software SPSS dan R Studio', '2025-11-26 01:06:34'),
	(15, '4', 4, 'Ruang Baca FISIP', 'Lt.1', 'Perpustakaan mini dengan koleksi buku komunikasi dan politik', '2025-11-26 01:06:34'),
	(16, '8', 5, 'Lab Bahasa', 'R.101', 'Lab bahasa dengan headset audio untuk listening practice', '2025-11-26 01:06:34'),
	(17, '8', 5, 'Ruang Kelas Magister Doktor Hukum', 'R.301', 'Ruang kuliah program pascasarjana hukum', '2025-11-26 01:06:34'),
	(18, '8', 5, 'Ruang Rapat Magister Hukum', 'R.401', 'Ruang untuk sidang tesis dan diskusi akademik', '2025-11-26 01:06:34'),
	(19, '13', 6, 'Perpustakaan Soetomo', 'Lt.1-2', 'Perpustakaan dengan ribuan koleksi buku dan jurnal', '2025-11-26 01:06:34'),
	(20, '13', 6, 'Ruang Baca', 'Lt.3', 'Ruang baca ber-AC dengan meja individual', '2025-11-26 01:06:34'),
	(21, '18', 7, 'Mash Classroom', 'R.101', 'Ruang kelas dengan smart board dan proyektor interaktif', '2025-11-26 01:06:34'),
	(22, '18', 7, 'Area Olahraga Indoor', 'Lt.2', 'Ruang olahraga untuk badminton dan futsal indoor', '2025-11-26 01:06:34'),
	(23, '18', 7, 'Ruang Kelas', 'R.201', 'Ruang perkuliahan umum', '2025-11-26 01:06:34'),
	(24, '19', 8, 'Digital Library', 'Lt.1', 'Perpustakaan digital dengan akses e-book dan jurnal internasional', '2025-11-26 01:06:34'),
	(25, '19', 8, 'Lab Cybersecurity', 'R.201', 'Lab keamanan siber dengan tools penetration testing', '2025-11-26 01:06:34'),
	(26, '19', 8, 'Lab Programming', 'R.202', 'Lab pemrograman dengan 50 unit PC Core i7 dan dual monitor', '2025-11-26 01:06:34'),
	(27, '19', 8, 'Ruang Rapat FIK', 'R.301', 'Ruang meeting dengan video conference facility', '2025-11-26 01:06:34'),
	(28, '19', 8, 'Selasar', 'Lt.1', 'Area outdoor untuk diskusi dan berkumpul', '2025-11-26 01:06:34'),
	(29, '19', 8, 'Studio Podcast FIK', 'R.302', 'Studio podcast dengan soundproof dan mic Shure SM7B', '2025-11-26 01:06:34'),
	(30, '21', 9, 'Selasar Yos Sudarso', 'Lt.1', 'Area tunggu dan berkumpul mahasiswa hukum', '2025-11-26 01:06:34'),
	(31, '21', 9, 'Lab Perancangan Kontrak', 'R.201', 'Lab untuk praktik drafting kontrak dan perjanjian', '2025-11-26 01:06:34'),
	(32, '21', 9, 'Peradilan Semu (Moot Court)', 'R.301', 'Ruang sidang untuk simulasi persidangan dengan tata ruang real court', '2025-11-26 01:06:34'),
	(33, '21', 9, 'Ruang Baca Hukum', 'Lt.3', 'Perpustakaan dengan koleksi peraturan dan yurisprudensi', '2025-11-26 01:06:34'),
	(34, '21', 9, 'Ruang Kelas Hukum', 'R.202', 'Ruang kuliah hukum dengan kapasitas 60 mahasiswa', '2025-11-26 01:06:34'),
	(35, '21', 9, 'Studio Podcast FH', 'R.302', 'Studio podcast untuk diskusi isu hukum terkini', '2025-11-26 01:06:34'),
	(36, '6', 10, 'Parkiran Mobil', 'Area Outdoor', 'Area parkir dengan kapasitas 200 mobil', '2025-11-26 01:06:34'),
	(37, '9', 11, 'Kantin Pratama', 'Lt.1', 'Kantin dengan 10 tenant makanan dan minuman', '2025-11-26 01:06:34'),
	(38, '10', 12, 'Studio Arsitektur', 'Lt.2', 'Studio untuk menggambar dan membuat maket', '2025-11-26 01:06:34'),
	(39, '11', 13, 'Area Parkir Kantin', 'Outdoor', 'Parkiran motor dekat kantin', '2025-11-26 01:06:34'),
	(40, '11', 13, 'Wall Climbing', 'Outdoor', 'Dinding panjat tebing outdoor setinggi 15 meter', '2025-11-26 01:06:34'),
	(41, '12', 14, 'Kantin Kanan', 'Area A', 'Food court sisi kanan dengan 15 tenant', '2025-11-26 01:06:34'),
	(42, '12', 14, 'Kantin Kiri', 'Area B', 'Food court sisi kiri dengan 12 tenant', '2025-11-26 01:06:34'),
	(43, '14', 15, 'Administrasi Akademik', 'Lt.1', 'Layanan administrasi mahasiswa', '2025-11-26 01:06:34'),
	(44, '15', 16, 'Lapangan Upacara', 'Outdoor', 'Lapangan untuk upacara bendera', '2025-11-26 01:06:34'),
	(45, '16', 17, 'Lapangan Basket', 'Outdoor', 'Court basket outdoor', '2025-11-26 01:06:34'),
	(46, '16', 17, 'Lapangan Volley', 'Outdoor', 'Court voli outdoor', '2025-11-26 01:06:34'),
	(47, '17', 18, 'Ruang Sholat Utama', 'Lt.1', 'Musholla dengan karpet Turki dan AC', '2025-11-26 01:06:34'),
	(48, '17', 18, 'Tempat Wudhu', 'Lt.1', 'Fasilitas wudhu pria dan wanita', '2025-11-26 01:06:34'),
	(49, '22', 19, 'Parkiran Belakang', 'Outdoor', 'Parkiran dekat gedung fakultas', '2025-11-26 01:06:34'),
	(50, '23', 20, 'Parkiran Beskabean', 'Outdoor', 'Parkiran motor dekat area cafe', '2025-11-26 01:06:34'),
	(51, '24', 21, 'Cafe Beskabean', 'Lt.1', 'Cafe dengan menu kopi dan dessert', '2025-11-26 01:06:34');

-- Dumping structure for table revaupnvj.facility_ratings
DROP TABLE IF EXISTS `facility_ratings`;
CREATE TABLE IF NOT EXISTS `facility_ratings` (
  `rating_id` int NOT NULL AUTO_INCREMENT,
  `facility_id` int DEFAULT NULL,
  `user_id` int NOT NULL,
  `rating` int DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`rating_id`),
  UNIQUE KEY `unique_user_facility` (`user_id`,`facility_id`),
  KEY `idx_facility_rating` (`facility_id`),
  CONSTRAINT `facility_ratings_ibfk_1` FOREIGN KEY (`facility_id`) REFERENCES `facilities` (`facility_id`) ON DELETE CASCADE,
  CONSTRAINT `facility_ratings_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE,
  CONSTRAINT `facility_ratings_chk_1` CHECK ((`rating` between 1 and 5))
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table revaupnvj.facility_ratings: ~10 rows (approximately)
INSERT INTO `facility_ratings` (`rating_id`, `facility_id`, `user_id`, `rating`, `created_at`) VALUES
	(1, 1, 1, 5, '2025-11-26 01:06:34'),
	(2, 2, 1, 4, '2025-11-26 01:06:34'),
	(3, 7, 1, 5, '2025-11-26 01:06:34'),
	(4, 7, 2, 5, '2025-11-26 01:06:34'),
	(5, 8, 1, 5, '2025-11-26 01:06:34'),
	(6, 9, 2, 4, '2025-11-26 01:06:34'),
	(7, 19, 1, 5, '2025-11-26 01:06:34'),
	(8, 19, 2, 5, '2025-11-26 01:06:34'),
	(9, 25, 1, 5, '2025-11-26 01:06:34'),
	(10, 25, 2, 4, '2025-11-26 01:06:34');

-- Dumping structure for table revaupnvj.ratings
DROP TABLE IF EXISTS `ratings`;
CREATE TABLE IF NOT EXISTS `ratings` (
  `rating_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `room_id` int NOT NULL,
  `building_id` int NOT NULL,
  `rating` int NOT NULL,
  `review` text,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`rating_id`),
  UNIQUE KEY `unique_user_room` (`user_id`,`room_id`),
  KEY `room_id` (`room_id`),
  KEY `idx_rating_building` (`building_id`),
  KEY `idx_rating_user` (`user_id`),
  CONSTRAINT `ratings_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE,
  CONSTRAINT `ratings_ibfk_2` FOREIGN KEY (`room_id`) REFERENCES `rooms` (`room_id`) ON DELETE CASCADE,
  CONSTRAINT `ratings_ibfk_3` FOREIGN KEY (`building_id`) REFERENCES `buildings` (`building_id`) ON DELETE CASCADE,
  CONSTRAINT `ratings_chk_1` CHECK (((`rating` >= 1) and (`rating` <= 5)))
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table revaupnvj.ratings: ~11 rows (approximately)
INSERT INTO `ratings` (`rating_id`, `user_id`, `room_id`, `building_id`, `rating`, `review`, `created_at`) VALUES
	(1, 1, 1, 1, 5, NULL, '2025-11-26 01:06:34'),
	(2, 2, 2, 1, 4, NULL, '2025-11-26 01:06:34'),
	(3, 1, 7, 4, 5, NULL, '2025-11-26 01:06:34'),
	(4, 2, 8, 4, 5, NULL, '2025-11-26 01:06:34'),
	(5, 1, 23, 8, 5, NULL, '2025-11-26 01:06:34'),
	(6, 2, 24, 8, 4, NULL, '2025-11-26 01:06:34'),
	(7, 1, 30, 9, 5, NULL, '2025-11-26 01:06:34'),
	(8, 2, 31, 9, 4, NULL, '2025-11-26 01:06:34'),
	(9, 1, 46, 16, 3, '', '2025-11-26 04:19:24'),
	(10, 1, 22, 7, 3, '', '2025-11-26 04:24:20'),
	(11, 1, 44, 16, 1, 'hai haodahodahdaohdoa', '2025-11-26 04:39:01');

-- Dumping structure for view revaupnvj.rating_details
DROP VIEW IF EXISTS `rating_details`;
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `rating_details` (
	`rating_id` INT NOT NULL,
	`rating` INT NOT NULL,
	`created_at` TIMESTAMP NULL,
	`username` VARCHAR(1) NOT NULL COLLATE 'utf8mb4_0900_ai_ci',
	`user_faculty` ENUM('FIK','FK','FH','FEB','FISIP') NOT NULL COLLATE 'utf8mb4_0900_ai_ci',
	`room_name` VARCHAR(1) NOT NULL COLLATE 'utf8mb4_0900_ai_ci',
	`building_name` VARCHAR(1) NOT NULL COLLATE 'utf8mb4_0900_ai_ci',
	`building_faculty` ENUM('FIK','FK','FH','FEB','FISIP','UMUM') NULL COLLATE 'utf8mb4_0900_ai_ci'
) ENGINE=MyISAM;

-- Dumping structure for table revaupnvj.rooms
DROP TABLE IF EXISTS `rooms`;
CREATE TABLE IF NOT EXISTS `rooms` (
  `room_id` int NOT NULL AUTO_INCREMENT,
  `building_id` int NOT NULL,
  `room_name` varchar(100) NOT NULL,
  `room_number` varchar(20) DEFAULT NULL,
  `description` text,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`room_id`),
  KEY `idx_room_building` (`building_id`),
  CONSTRAINT `rooms_ibfk_1` FOREIGN KEY (`building_id`) REFERENCES `buildings` (`building_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table revaupnvj.rooms: ~47 rows (approximately)
INSERT INTO `rooms` (`room_id`, `building_id`, `room_name`, `room_number`, `description`, `created_at`) VALUES
	(1, 1, 'Auditorium BTI', 'Lt.1', 'Auditorium besar untuk wisuda dan acara resmi universitas', '2025-11-26 01:06:34'),
	(2, 1, 'Bank BNI', 'Lt.1', 'Kantor cabang Bank BNI di kampus untuk layanan mahasiswa', '2025-11-26 01:06:34'),
	(3, 2, 'Aula BEJ', 'Lt.2', 'Aula untuk seminar dan workshop ekonomi', '2025-11-26 01:06:34'),
	(4, 2, 'Area Ubin Coklat', 'Lt.1', 'Area berkumpul mahasiswa FEB', '2025-11-26 01:06:34'),
	(5, 3, 'Ruang Kelas A', 'R.201', 'Ruang kelas kapasitas 60 mahasiswa', '2025-11-26 01:06:34'),
	(6, 3, 'Ruang Kelas B', 'R.301', 'Ruang kelas kapasitas 80 mahasiswa', '2025-11-26 01:06:34'),
	(7, 4, 'Auditorium FISIP', 'Lt.1', 'Auditorium besar untuk acara fakultas', '2025-11-26 01:06:34'),
	(8, 4, 'Lab Diplomasi', 'Lt.2', 'Lab untuk praktik diplomasi dan negosiasi internasional', '2025-11-26 01:06:34'),
	(9, 4, 'Lab Film', 'Lt.2', 'Lab produksi film dengan peralatan profesional', '2025-11-26 01:06:34'),
	(10, 4, 'Lab Fotografi', 'Lt.3', 'Studio fotografi dengan lighting dan backdrop', '2025-11-26 01:06:34'),
	(11, 4, 'Lab Multimedia', 'Lt.3', 'Lab editing video dan audio', '2025-11-26 01:06:34'),
	(12, 4, 'Lab Podcast', 'Lt.3', 'Studio podcast dengan soundproof room', '2025-11-26 01:06:34'),
	(13, 4, 'Lab Politik', 'Lt.2', 'Lab simulasi politik dan pemerintahan', '2025-11-26 01:06:34'),
	(14, 4, 'Lab Radio', 'Lt.3', 'Studio radio kampus', '2025-11-26 01:06:34'),
	(15, 4, 'Lab Big Data', 'Lt.4', 'Lab analisis data dan statistik sosial', '2025-11-26 01:06:34'),
	(16, 4, 'Ruang Baca FISIP', 'Lt.1', 'Perpustakaan mini fakultas', '2025-11-26 01:06:34'),
	(17, 5, 'Lab Bahasa', 'R.101', 'Lab bahasa dengan audio system', '2025-11-26 01:06:34'),
	(18, 5, 'Ruang Kelas Magister Doktor Hukum', 'R.301', 'Ruang kuliah program magister dan doktor', '2025-11-26 01:06:34'),
	(19, 5, 'Ruang Rapat Magister Hukum', 'R.401', 'Ruang diskusi dan sidang tesis', '2025-11-26 01:06:34'),
	(20, 6, 'Perpustakaan Soetomo', 'Lt.1-2', 'Perpustakaan dengan koleksi buku lengkap', '2025-11-26 01:06:34'),
	(21, 6, 'Ruang Baca', 'Lt.3', 'Ruang baca tenang dengan AC', '2025-11-26 01:06:34'),
	(22, 7, 'Mash Classroom', 'R.303', 'Ruang kelas modern dengan smart board', '2025-11-26 01:06:34'),
	(23, 7, 'Area Olahraga', 'Area Outdoor', 'Fasilitas olahraga indoor', '2025-11-26 01:06:34'),
	(24, 7, 'Ruang Kelas', 'R.201', 'Kelas kapasitas 100 mahasiswa', '2025-11-26 01:06:34'),
	(25, 8, 'Digital Library', 'Lt.1', 'Perpustakaan digital dengan e-resources', '2025-11-26 01:06:34'),
	(26, 8, 'Lab Cybersecurity', 'R.303', 'Lab keamanan siber dan ethical hacking', '2025-11-26 01:06:34'),
	(27, 8, 'Lab Programming', 'R.302', 'Lab pemrograman dengan 50 komputer', '2025-11-26 01:06:34'),
	(28, 8, 'Ruang Rapat FIK', 'Lt.1', 'Ruang meeting dan presentasi', '2025-11-26 01:06:34'),
	(29, 8, 'Selasar KH Dewantara', 'Lt.1', 'Area berkumpul dan diskusi outdoor', '2025-11-26 01:06:34'),
	(30, 8, 'Studio Podcast FIK', 'Lt.1', 'Studio podcast dengan peralatan lengkap', '2025-11-26 01:06:34'),
	(31, 9, 'Selasar Yos Sudarso', 'Lt.1', 'Koridor dan area tunggu mahasiswa', '2025-11-26 01:06:34'),
	(32, 9, 'Lab Perancangan Kontrak', 'R.201', 'Lab praktik pembuatan kontrak hukum', '2025-11-26 01:06:34'),
	(33, 9, 'Peradilan Semu (Moot Court)', 'R.301', 'Ruang sidang untuk simulasi persidangan', '2025-11-26 01:06:34'),
	(34, 9, 'Ruang Baca Hukum', 'Lt.3', 'Perpustakaan mini dengan koleksi hukum', '2025-11-26 01:06:34'),
	(35, 9, 'Ruang Kelas Hukum', 'R.202-401', 'Ruang perkuliahan reguler', '2025-11-26 01:06:34'),
	(36, 9, 'Studio Podcast FH', 'R.302', 'Studio podcast fakultas hukum', '2025-11-26 01:06:34'),
	(37, 10, 'Parkiran Mobil', 'Area Outdoor', 'Area parkir kendaraan roda empat', '2025-11-26 01:06:34'),
	(38, 11, 'Kantin Pratama', 'Lt.1', 'Area makan dengan berbagai tenant', '2025-11-26 01:06:34'),
	(39, 12, 'Studio Arsitektur', 'Lt.2', 'Studio gambar dan maket arsitektur', '2025-11-26 01:06:34'),
	(40, 13, 'Area Parkir Kantin', 'Outdoor', 'Parkiran dekat kantin', '2025-11-26 01:06:34'),
	(41, 13, 'Wall Climbing', 'Outdoor', 'Dinding panjat tebing setinggi 15 meter', '2025-11-26 01:06:34'),
	(42, 14, 'Kantin Kanan', 'Area A', 'Food court sisi kanan', '2025-11-26 01:06:34'),
	(43, 14, 'Kantin Kiri', 'Area B', 'Food court sisi kiri', '2025-11-26 01:06:34'),
	(44, 16, 'Ruang Sholat Pria', 'Lt.1', 'Musholla untuk jamaah pria', '2025-11-26 01:06:34'),
	(45, 16, 'Ruang Sholat Wanita', 'Lt.2', 'Musholla untuk jamaah wanita', '2025-11-26 01:06:34'),
	(46, 16, 'Tempat Wudhu', 'Lt.1', 'Fasilitas wudhu pria dan wanita', '2025-11-26 01:06:34'),
	(47, 19, 'Cafe Beskabean', 'Lt.1', 'Cafe dengan menu kopi dan snack', '2025-11-26 01:06:34');

-- Dumping structure for table revaupnvj.users
DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `faculty` enum('FIK','FK','FH','FEB','FISIP') NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`),
  KEY `idx_user_faculty` (`faculty`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table revaupnvj.users: ~7 rows (approximately)
INSERT INTO `users` (`user_id`, `username`, `password`, `faculty`, `created_at`) VALUES
	(1, 'mahasiswa_fik', 'password123', 'FIK', '2025-11-26 01:06:34'),
	(2, 'ivana', 'password123', 'FIK', '2025-11-26 01:06:34'),
	(3, 'mahasiswa_fk', 'password123', 'FK', '2025-11-26 01:06:34'),
	(4, 'budi', 'password123', 'FK', '2025-11-26 01:06:34'),
	(5, 'mahasiswa_fh', 'password123', 'FH', '2025-11-26 01:06:34'),
	(6, 'mahasiswa_feb', 'password123', 'FEB', '2025-11-26 01:06:34'),
	(7, 'mahasiswa_fisip', 'password123', 'FISIP', '2025-11-26 01:06:34');

-- Dumping structure for view revaupnvj.view_building_ratings
DROP VIEW IF EXISTS `view_building_ratings`;
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `view_building_ratings` (
	`building_number` VARCHAR(1) NULL COLLATE 'utf8mb4_0900_ai_ci',
	`building_name` VARCHAR(1) NOT NULL COLLATE 'utf8mb4_0900_ai_ci',
	`faculty` ENUM('FIK','FK','FH','FEB','FISIP','UMUM') NULL COLLATE 'utf8mb4_0900_ai_ci',
	`avg_rating` DECIMAL(14,4) NOT NULL,
	`total_votes` BIGINT NOT NULL
) ENGINE=MyISAM;

-- Dumping structure for view revaupnvj.view_facility_ratings
DROP VIEW IF EXISTS `view_facility_ratings`;
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `view_facility_ratings` (
	`facility_id` INT NOT NULL,
	`facility_name` VARCHAR(1) NOT NULL COLLATE 'utf8mb4_0900_ai_ci',
	`room_number` VARCHAR(1) NULL COLLATE 'utf8mb4_0900_ai_ci',
	`building_number` VARCHAR(1) NULL COLLATE 'utf8mb4_0900_ai_ci',
	`building_name` VARCHAR(1) NULL COLLATE 'utf8mb4_0900_ai_ci',
	`avg_rating` DECIMAL(14,4) NOT NULL,
	`total_votes` BIGINT NOT NULL
) ENGINE=MyISAM;

-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `building_details`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `building_details` AS select `b`.`building_id` AS `building_id`,`b`.`building_number` AS `building_number`,`b`.`building_name` AS `building_name`,`b`.`description` AS `description`,`b`.`hours` AS `hours`,`b`.`facilities` AS `facilities`,`b`.`faculty` AS `faculty`,coalesce(avg(`r`.`rating`),0) AS `avg_rating`,count(distinct `r`.`rating_id`) AS `total_ratings` from ((`buildings` `b` left join `rooms` `ro` on((`b`.`building_id` = `ro`.`building_id`))) left join `ratings` `r` on((`ro`.`room_id` = `r`.`room_id`))) group by `b`.`building_id`;

-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `rating_details`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `rating_details` AS select `r`.`rating_id` AS `rating_id`,`r`.`rating` AS `rating`,`r`.`created_at` AS `created_at`,`u`.`username` AS `username`,`u`.`faculty` AS `user_faculty`,`ro`.`room_name` AS `room_name`,`b`.`building_name` AS `building_name`,`b`.`faculty` AS `building_faculty` from (((`ratings` `r` join `users` `u` on((`r`.`user_id` = `u`.`user_id`))) join `rooms` `ro` on((`r`.`room_id` = `ro`.`room_id`))) join `buildings` `b` on((`r`.`building_id` = `b`.`building_id`)));

-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `view_building_ratings`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `view_building_ratings` AS select `b`.`building_number` AS `building_number`,`b`.`building_name` AS `building_name`,`b`.`faculty` AS `faculty`,ifnull(avg(`br`.`rating`),0) AS `avg_rating`,count(`br`.`rating_id`) AS `total_votes` from (`buildings` `b` left join `building_ratings` `br` on((`b`.`building_number` = `br`.`building_number`))) group by `b`.`building_number`,`b`.`building_name`,`b`.`faculty`;

-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `view_facility_ratings`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `view_facility_ratings` AS select `f`.`facility_id` AS `facility_id`,`f`.`facility_name` AS `facility_name`,`f`.`room_number` AS `room_number`,`f`.`building_number` AS `building_number`,`b`.`building_name` AS `building_name`,ifnull(avg(`fr`.`rating`),0) AS `avg_rating`,count(`fr`.`rating_id`) AS `total_votes` from ((`facilities` `f` left join `facility_ratings` `fr` on((`f`.`facility_id` = `fr`.`facility_id`))) left join `buildings` `b` on((`f`.`building_id` = `b`.`building_id`))) group by `f`.`facility_id`;

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
