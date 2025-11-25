CREATE DATABASE IF NOT EXISTS revaupnvj;
USE revaupnvj;

CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    faculty ENUM('FIK', 'FK', 'FH', 'FEB', 'FISIP') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE admins (
    admin_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE buildings (
    building_id INT PRIMARY KEY AUTO_INCREMENT,
    building_number VARCHAR(10) UNIQUE,
    building_name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    hours VARCHAR(50),
    facilities TEXT,
    faculty ENUM('FIK', 'FK', 'FH', 'FEB', 'FISIP', 'UMUM') DEFAULT 'UMUM',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE building_photos (
    photo_id INT PRIMARY KEY AUTO_INCREMENT,
    building_id INT NOT NULL,
    photo_url VARCHAR(500) NOT NULL,
    photo_index INT DEFAULT 0,
    uploaded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (building_id) REFERENCES buildings(building_id) ON DELETE CASCADE
);

CREATE TABLE rooms (
    room_id INT PRIMARY KEY AUTO_INCREMENT,
    building_id INT NOT NULL,
    room_name VARCHAR(100) NOT NULL,
    room_number VARCHAR(20),
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (building_id) REFERENCES buildings(building_id) ON DELETE CASCADE
);

CREATE TABLE ratings (
    rating_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    room_id INT NOT NULL,
    building_id INT NOT NULL,
    rating INT NOT NULL CHECK (rating >= 1 AND rating <= 5),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (room_id) REFERENCES rooms(room_id) ON DELETE CASCADE,
    FOREIGN KEY (building_id) REFERENCES buildings(building_id) ON DELETE CASCADE,
    UNIQUE KEY unique_user_room (user_id, room_id)
);

CREATE TABLE building_ratings (
    rating_id INT PRIMARY KEY AUTO_INCREMENT,
    building_number VARCHAR(10),
    user_id INT NOT NULL,
    rating INT NOT NULL CHECK (rating >= 1 AND rating <= 5),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    UNIQUE KEY unique_user_building (user_id, building_number)
);

CREATE TABLE facilities (
    facility_id INT AUTO_INCREMENT PRIMARY KEY,
    building_number VARCHAR(10),
    building_id INT,
    facility_name VARCHAR(100) NOT NULL,
    room_number VARCHAR(20),
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (building_id) REFERENCES buildings(building_id) ON DELETE CASCADE
);

CREATE TABLE facility_ratings (
    rating_id INT AUTO_INCREMENT PRIMARY KEY,
    facility_id INT,
    user_id INT NOT NULL,
    rating INT CHECK (rating BETWEEN 1 AND 5),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (facility_id) REFERENCES facilities(facility_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    UNIQUE KEY unique_user_facility (user_id, facility_id)
);

INSERT INTO admins (username, password) VALUES
('admin', 'admin123');

INSERT INTO users (username, password, faculty) VALUES
('mahasiswa_fik', 'password123', 'FIK'),
('ivana', 'password123', 'FIK'),
('mahasiswa_fk', 'password123', 'FK'),
('budi', 'password123', 'FK'),
('mahasiswa_fh', 'password123', 'FH'),
('mahasiswa_feb', 'password123', 'FEB'),
('mahasiswa_fisip', 'password123', 'FISIP');

INSERT INTO buildings (building_number, building_name, description, hours, facilities, faculty) VALUES
('1', 'Gedung Rektorat', 'Gedung pusat administrasi dan pimpinan universitas', '08:00 - 17:00', 'Auditorium BTI, Bank BNI', 'UMUM'),
('2', 'Gedung BEJ (Bursa Efek Jakarta)', 'Gedung Fakultas Ekonomi dengan lab trading dan simulasi pasar modal', '07:00 - 20:00', 'Aula BEJ, Ubin Coklat', 'FEB'),
('3', 'Gedung Soepomo', 'Gedung Fakultas Hukum dengan ruang kelas modern', '07:00 - 21:00', 'Ruang Kelas', 'FH'),
('4', 'Gedung Muhammad Yamin', 'Gedung FISIP dengan berbagai lab media dan komunikasi', '07:00 - 21:00', 'Auditorium FISIP, Lab Diplomasi, Lab Film, Lab Fotografi, Lab Multimedia, Lab Podcast, Lab Politik, Lab Radio, Lab Big Data, Ruang Baca', 'FISIP'),
('5', 'Gedung Husni Tamrin', 'Gedung FEB', '07:00 - 21:00', 'Auditorium FISIP, Lab Diplomasi, Lab Film, Lab Fotografi, Lab Multimedia, Lab Podcast, Lab Politik, Lab Radio, Lab Big Data, Ruang Baca', 'FEB'),
('6', 'Gedung Wahidin Hudoso', 'Gedung FK', '00:00 - 24:00', 'Ruang Kelas', 'FK'),
('7', 'Lapangan Upacara', 'Lapangan terbuka untuk upacara dan kegiatan kemahasiswaan', '00:00 - 24:00', 'Lapangan Upacara', 'UMUM'),
('8', 'Gedung R.A. Kartini', 'Gedung Fakultas Hukum untuk program pascasarjana', '07:00 - 18:00', 'Lab Bahasa, Ruang Kelas Magister Doktor Hukum, Ruang Rapat Magister Hukum', 'FH'),
('9', 'Kantin Pratama', 'Kantin dengan berbagai pilihan makanan dan minuman', '06:00 - 18:00', 'Food Court, Area Makan', 'UMUM'),
('10', 'APU (Arsitektur Planologi UPNVJ)', 'Gedung program studi Arsitektur dan Planologi', '07:00 - 18:00', 'Studio Arsitektur, Ruang Kuliah', 'UMUM'),
('11', 'Kantin Utama', 'Kantin pusat kampus dengan banyak pilihan tenant', '06:00 - 20:00', 'Kantin Kanan, Kantin Kiri, Food Court', 'UMUM'),
('12', 'Gedung Soetomo', 'Gedung dengan perpustakaan dan ruang baca yang nyaman', '07:00 - 21:00', 'Perpustakaan, Ruang Baca', 'UMUM'),
('13', 'Masjid Manbaul Ulum', 'Masjid kampus untuk ibadah umat muslim', '04:00 - 22:00', 'Ruang Sholat, Tempat Wudhu, Perpustakaan Islam', 'UMUM'),
('14', 'Gedung Abdul Rachman Saleh', 'Gedung administrasi dan layanan mahasiswa', '08:00 - 17:00', 'Administrasi, Customer Service', 'UMUM'),
('15', 'Gedung Yos Sudarso', 'Gedung Fakultas Hukum dengan fasilitas praktik hukum lengkap', '07:00 - 20:00', 'Selasar, Lab Perancangan Kontrak, Peradilan Semu (Moot Court), Ruang Baca, Ruang Kelas, Ruang Podcast', 'FH'),
('16', 'Gedung KH Dewantara', 'Gedung Fakultas Ilmu Komputer dengan teknologi terkini', '07:00 - 22:00', 'Digital Library, Lab Cybersecurity, Lab Programming, Ruang Rapat, Selasar, Ruang Podcast', 'FIK'),
('17', 'Beskabean (Area Cafe)', 'Area cafe dan tempat nongkrong mahasiswa', '08:00 - 20:00', 'Cafe, Area Duduk Outdoor', 'UMUM'),
('18', 'Gedung Dewi Sartika', 'Gedung dengan fasilitas olahraga dan ruang kelas', '07:00 - 20:00', 'Mash Classroom, Area Olahraga, Ruang Kelas', 'UMUM'),
('19', 'Parkiran Beskabean', 'Area parkir dekat area kantin beskabean', '00:00 - 24:00', 'Area Parkir Motor', 'UMUM'),
('20', 'Gedung Cipto Mangunkusomo', 'Gedung FK', '00:00 - 24:00', 'Ruang Kelas', 'FK'),
('21', 'Parkiran Belakang', 'Area parkir belakang kampus dekat gedung fakultas', '00:00 - 24:00', 'Area Parkir Motor dan Mobil', 'UMUM'),
('22', 'Parkiran Kantin', 'Area parkir dekat kantin dengan fasilitas wall climbing', '00:00 - 24:00', 'Area Parkir, Wall Climbing', 'UMUM'),
('23', 'Lapangan Basket & Volley', 'Area olahraga outdoor untuk basket dan voli', '06:00 - 21:00', 'Lapangan Basket, Lapangan Volley', 'UMUM');


INSERT INTO rooms (building_id, room_name, room_number, description) VALUES
(1, 'Auditorium BTI', 'Lt.1', 'Auditorium besar untuk wisuda dan acara resmi universitas'),
(1, 'Bank BNI', 'Lt.1', 'Kantor cabang Bank BNI di kampus untuk layanan mahasiswa'),
(2, 'Aula BEJ', 'Lt.2', 'Aula untuk seminar dan workshop ekonomi'),
(2, 'Area Ubin Coklat', 'Lt.1', 'Area berkumpul mahasiswa FEB'),
(3, 'Ruang Kelas A', 'R.201', 'Ruang kelas kapasitas 60 mahasiswa'),
(3, 'Ruang Kelas B', 'R.301', 'Ruang kelas kapasitas 80 mahasiswa'),
(4, 'Auditorium FISIP', 'Lt.1', 'Auditorium besar untuk acara fakultas'),
(4, 'Lab Diplomasi', 'Lt.2', 'Lab untuk praktik diplomasi dan negosiasi internasional'),
(4, 'Lab Film', 'Lt.2', 'Lab produksi film dengan peralatan profesional'),
(4, 'Lab Fotografi', 'Lt.3', 'Studio fotografi dengan lighting dan backdrop'),
(4, 'Lab Multimedia', 'Lt.3', 'Lab editing video dan audio'),
(4, 'Lab Podcast', 'Lt.3', 'Studio podcast dengan soundproof room'),
(4, 'Lab Politik', 'Lt.2', 'Lab simulasi politik dan pemerintahan'),
(4, 'Lab Radio', 'Lt.3', 'Studio radio kampus'),
(4, 'Lab Big Data', 'Lt.4', 'Lab analisis data dan statistik sosial'),
(4, 'Ruang Baca FISIP', 'Lt.1', 'Perpustakaan mini fakultas'),
(5, 'Lab Bahasa', 'R.101', 'Lab bahasa dengan audio system'),
(5, 'Ruang Kelas Magister Doktor Hukum', 'R.301', 'Ruang kuliah program magister dan doktor'),
(5, 'Ruang Rapat Magister Hukum', 'R.401', 'Ruang diskusi dan sidang tesis'),
(6, 'Perpustakaan Soetomo', 'Lt.1-2', 'Perpustakaan dengan koleksi buku lengkap'),
(6, 'Ruang Baca', 'Lt.3', 'Ruang baca tenang dengan AC'),
(7, 'Mash Classroom', 'R.303', 'Ruang kelas modern dengan smart board'),
(7, 'Area Olahraga', 'Area Outdoor', 'Fasilitas olahraga indoor'),
(7, 'Ruang Kelas', 'R.201', 'Kelas kapasitas 100 mahasiswa'),
(8, 'Digital Library', 'Lt.1', 'Perpustakaan digital dengan e-resources'),
(8, 'Lab Cybersecurity', 'R.303', 'Lab keamanan siber dan ethical hacking'),
(8, 'Lab Programming', 'R.302', 'Lab pemrograman dengan 50 komputer'),
(8, 'Ruang Rapat FIK', 'Lt.1', 'Ruang meeting dan presentasi'),
(8, 'Selasar KH Dewantara', 'Lt.1', 'Area berkumpul dan diskusi outdoor'),
(8, 'Studio Podcast FIK', 'Lt.1', 'Studio podcast dengan peralatan lengkap'),
(9, 'Selasar Yos Sudarso', 'Lt.1', 'Koridor dan area tunggu mahasiswa'),
(9, 'Lab Perancangan Kontrak', 'R.201', 'Lab praktik pembuatan kontrak hukum'),
(9, 'Peradilan Semu (Moot Court)', 'R.301', 'Ruang sidang untuk simulasi persidangan'),
(9, 'Ruang Baca Hukum', 'Lt.3', 'Perpustakaan mini dengan koleksi hukum'),
(9, 'Ruang Kelas Hukum', 'R.202-401', 'Ruang perkuliahan reguler'),
(9, 'Studio Podcast FH', 'R.302', 'Studio podcast fakultas hukum'),
(10, 'Parkiran Mobil', 'Area Outdoor', 'Area parkir kendaraan roda empat'),
(11, 'Kantin Pratama', 'Lt.1', 'Area makan dengan berbagai tenant'),
(12, 'Studio Arsitektur', 'Lt.2', 'Studio gambar dan maket arsitektur'),
(13, 'Area Parkir Kantin', 'Outdoor', 'Parkiran dekat kantin'),
(13, 'Wall Climbing', 'Outdoor', 'Dinding panjat tebing setinggi 15 meter'),
(14, 'Kantin Kanan', 'Area A', 'Food court sisi kanan'),
(14, 'Kantin Kiri', 'Area B', 'Food court sisi kiri'),
(16, 'Ruang Sholat Pria', 'Lt.1', 'Musholla untuk jamaah pria'),
(16, 'Ruang Sholat Wanita', 'Lt.2', 'Musholla untuk jamaah wanita'),
(16, 'Tempat Wudhu', 'Lt.1', 'Fasilitas wudhu pria dan wanita'),
(19, 'Cafe Beskabean', 'Lt.1', 'Cafe dengan menu kopi dan snack');

INSERT INTO facilities (building_number, building_id, facility_name, room_number, description) VALUES
('1', 1, 'Auditorium BTI', 'Lt.1', 'Auditorium untuk wisuda dan acara besar universitas dengan kapasitas 1000 orang'),
('1', 1, 'Bank BNI', 'Lt.1', 'Layanan perbankan untuk civitas akademika'),
('2', 2, 'Aula BEJ', 'Lt.2', 'Aula untuk kegiatan mahasiswa FEB dan seminar ekonomi'),
('2', 2, 'Area Ubin Coklat', 'Lt.1', 'Area berkumpul dan diskusi mahasiswa FEB'),
('3', 3, 'Ruang Kelas Soepomo', 'R.201', 'Ruang kelas dengan AC dan proyektor'),
('4', 4, 'Auditorium FISIP', 'Lt.1', 'Auditorium berkapasitas 500 orang untuk acara fakultas'),
('4', 4, 'Laboratorium Diplomasi', 'R.201', 'Lab untuk praktik diplomasi dan hubungan internasional'),
('4', 4, 'Lab Film & Sinematografi', 'R.202', 'Studio produksi film dengan kamera dan lighting profesional'),
('4', 4, 'Lab Fotografi', 'R.301', 'Studio foto dengan backdrop dan peralatan lengkap'),
('4', 4, 'Lab Multimedia', 'R.302', 'Lab editing video menggunakan Adobe Premiere dan After Effects'),
('4', 4, 'Studio Podcast FISIP', 'R.303', 'Studio podcast dengan mic condenser dan mixer audio'),
('4', 4, 'Lab Politik & Pemerintahan', 'R.203', 'Lab simulasi sistem pemerintahan dan kebijakan publik'),
('4', 4, 'Studio Radio Kampus', 'R.304', 'Studio radio dengan peralatan broadcasting profesional'),
('4', 4, 'Lab Big Data Analytics', 'R.401', 'Lab analisis data dengan software SPSS dan R Studio'),
('4', 4, 'Ruang Baca FISIP', 'Lt.1', 'Perpustakaan mini dengan koleksi buku komunikasi dan politik'),
('8', 5, 'Lab Bahasa', 'R.101', 'Lab bahasa dengan headset audio untuk listening practice'),
('8', 5, 'Ruang Kelas Magister Doktor Hukum', 'R.301', 'Ruang kuliah program pascasarjana hukum'),
('8', 5, 'Ruang Rapat Magister Hukum', 'R.401', 'Ruang untuk sidang tesis dan diskusi akademik'),
('13', 6, 'Perpustakaan Soetomo', 'Lt.1-2', 'Perpustakaan dengan ribuan koleksi buku dan jurnal'),
('13', 6, 'Ruang Baca', 'Lt.3', 'Ruang baca ber-AC dengan meja individual'),
('18', 7, 'Mash Classroom', 'R.101', 'Ruang kelas dengan smart board dan proyektor interaktif'),
('18', 7, 'Area Olahraga Indoor', 'Lt.2', 'Ruang olahraga untuk badminton dan futsal indoor'),
('18', 7, 'Ruang Kelas', 'R.201', 'Ruang perkuliahan umum'),
('19', 8, 'Digital Library', 'Lt.1', 'Perpustakaan digital dengan akses e-book dan jurnal internasional'),
('19', 8, 'Lab Cybersecurity', 'R.201', 'Lab keamanan siber dengan tools penetration testing'),
('19', 8, 'Lab Programming', 'R.202', 'Lab pemrograman dengan 50 unit PC Core i7 dan dual monitor'),
('19', 8, 'Ruang Rapat FIK', 'R.301', 'Ruang meeting dengan video conference facility'),
('19', 8, 'Selasar', 'Lt.1', 'Area outdoor untuk diskusi dan berkumpul'),
('19', 8, 'Studio Podcast FIK', 'R.302', 'Studio podcast dengan soundproof dan mic Shure SM7B'),
('21', 9, 'Selasar Yos Sudarso', 'Lt.1', 'Area tunggu dan berkumpul mahasiswa hukum'),
('21', 9, 'Lab Perancangan Kontrak', 'R.201', 'Lab untuk praktik drafting kontrak dan perjanjian'),
('21', 9, 'Peradilan Semu (Moot Court)', 'R.301', 'Ruang sidang untuk simulasi persidangan dengan tata ruang real court'),
('21', 9, 'Ruang Baca Hukum', 'Lt.3', 'Perpustakaan dengan koleksi peraturan dan yurisprudensi'),
('21', 9, 'Ruang Kelas Hukum', 'R.202', 'Ruang kuliah hukum dengan kapasitas 60 mahasiswa'),
('21', 9, 'Studio Podcast FH', 'R.302', 'Studio podcast untuk diskusi isu hukum terkini'),
('6', 10, 'Parkiran Mobil', 'Area Outdoor', 'Area parkir dengan kapasitas 200 mobil'),
('9', 11, 'Kantin Pratama', 'Lt.1', 'Kantin dengan 10 tenant makanan dan minuman'),
('10', 12, 'Studio Arsitektur', 'Lt.2', 'Studio untuk menggambar dan membuat maket'),
('11', 13, 'Area Parkir Kantin', 'Outdoor', 'Parkiran motor dekat kantin'),
('11', 13, 'Wall Climbing', 'Outdoor', 'Dinding panjat tebing outdoor setinggi 15 meter'),
('12', 14, 'Kantin Kanan', 'Area A', 'Food court sisi kanan dengan 15 tenant'),
('12', 14, 'Kantin Kiri', 'Area B', 'Food court sisi kiri dengan 12 tenant'),
('14', 15, 'Administrasi Akademik', 'Lt.1', 'Layanan administrasi mahasiswa'),
('15', 16, 'Lapangan Upacara', 'Outdoor', 'Lapangan untuk upacara bendera'),
('16', 17, 'Lapangan Basket', 'Outdoor', 'Court basket outdoor'),
('16', 17, 'Lapangan Volley', 'Outdoor', 'Court voli outdoor'),
('17', 18, 'Ruang Sholat Utama', 'Lt.1', 'Musholla dengan karpet Turki dan AC'),
('17', 18, 'Tempat Wudhu', 'Lt.1', 'Fasilitas wudhu pria dan wanita'),
('22', 19, 'Parkiran Belakang', 'Outdoor', 'Parkiran dekat gedung fakultas'),
('23', 20, 'Parkiran Beskabean', 'Outdoor', 'Parkiran motor dekat area cafe'),
('24', 21, 'Cafe Beskabean', 'Lt.1', 'Cafe dengan menu kopi dan dessert');

INSERT INTO ratings (user_id, room_id, building_id, rating) VALUES
(1, 1, 1, 5),
(2, 2, 1, 4),
(1, 7, 4, 5),
(2, 8, 4, 5),
(1, 23, 8, 5),
(2, 24, 8, 4),
(1, 30, 9, 5),
(2, 31, 9, 4);

INSERT INTO building_ratings (building_number, user_id, rating) VALUES
('1', 1, 5),
('1', 2, 4),
('2', 1, 4),
('4', 1, 5),
('4', 2, 5),
('19', 1, 5),
('19', 2, 5),
('21', 1, 4);

INSERT INTO facility_ratings (facility_id, user_id, rating) VALUES
(1, 1, 5),
(2, 1, 4),
(7, 1, 5),
(7, 2, 5),
(8, 1, 5),
(9, 2, 4),
(19, 1, 5),
(19, 2, 5),
(25, 1, 5),
(25, 2, 4);

CREATE VIEW building_details AS
SELECT 
    b.building_id,
    b.building_number,
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

CREATE VIEW view_building_ratings AS
SELECT 
    b.building_number,
    b.building_name,
    b.faculty,
    IFNULL(AVG(br.rating), 0) as avg_rating,
    COUNT(br.rating_id) as total_votes
FROM buildings b
LEFT JOIN building_ratings br ON b.building_number = br.building_number
GROUP BY b.building_number, b.building_name, b.faculty;

CREATE VIEW view_facility_ratings AS
SELECT 
    f.facility_id,
    f.facility_name,
    f.room_number,
    f.building_number,
    b.building_name,
    IFNULL(AVG(fr.rating), 0) as avg_rating,
    COUNT(fr.rating_id) as total_votes
FROM facilities f
LEFT JOIN facility_ratings fr ON f.facility_id = fr.facility_id
LEFT JOIN buildings b ON f.building_id = b.building_id
GROUP BY f.facility_id;

CREATE VIEW rating_details AS
SELECT 
    r.rating_id,
    r.rating,
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

CREATE INDEX idx_building_faculty ON buildings(faculty);
CREATE INDEX idx_building_number ON buildings(building_number);
CREATE INDEX idx_user_faculty ON users(faculty);
CREATE INDEX idx_rating_building ON ratings(building_id);
CREATE INDEX idx_rating_user ON ratings(user_id);
CREATE INDEX idx_room_building ON rooms(building_id);
CREATE INDEX idx_facility_building ON facilities(building_number);
CREATE INDEX idx_building_rating ON building_ratings(building_number);
CREATE INDEX idx_facility_rating ON facility_ratings(facility_id);
