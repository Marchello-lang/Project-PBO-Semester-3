package util;

import java.io.File;

/**
 * Image Path Configuration
 * Konfigurasi path untuk folder gambar gedung
 */
public class ImagePathConfig {

    /**
     * Base folder untuk semua gambar gedung
     * Path relative dari project root: src/assets/images/
     */
    public static final String BASE_IMAGE_PATH = "C:/Users/User/Downloads/Project-PBO-Semester-3-final/Project-PBO-Semester-3-final/java-swing-app/src/assets/images/";

    /**
     * Konstruktor folder gambar berdasarkan ID Gedung dan Nama Gedung
     * Format: [ID]_[NAMA_GEDUNG_TANPA_SPESIAL_CHAR]
     *
     * Contoh:
     * - ID=1, Nama="Gedung Rektorat" → "1_Rektorat"
     * - ID=2, Nama="Gedung BEJ" → "2_BEJ"
     * - ID=4, Nama="Gedung Muhammad Yamin" → "4_MuhammadYamin"
     */
    public static String getFolderName(int buildingId, String buildingName) {
        // Hapus kata "Gedung " di depan
        String name = buildingName.replaceAll("(?i)Gedung\\s+", "");

        // Hapus karakter spesial, ganti spasi dengan kosong
        name = name.replaceAll("[^a-zA-Z0-9]", "");

        // Format: [ID]_[NAMA]
        return buildingId + "_" + name;
    }

    /**
     * Get full path folder untuk gambar gedung tertentu
     *
     * @param buildingId ID gedung dari database
     * @param buildingName Nama gedung dari database
     * @return Full path folder, contoh: "src/assets/images/1_Rektorat/"
     */
    public static String getImageFolderPath(int buildingId, String buildingName) {
        String folderName = getFolderName(buildingId, buildingName);
        return BASE_IMAGE_PATH + folderName + "/";
    }

    /**
     * Check apakah folder gambar untuk gedung tersebut ada
     *
     * @param buildingId ID gedung
     * @param buildingName Nama gedung
     * @return true jika folder ada, false jika tidak
     */
    public static boolean isFolderExists(int buildingId, String buildingName) {
        String folderPath = getImageFolderPath(buildingId, buildingName);
        File folder = new File(folderPath);
        return folder.exists() && folder.isDirectory();
    }

    /**
     * Get list file gambar di folder gedung
     * Hanya file dengan extension: .jpg, .jpeg, .png
     *
     * @param buildingId ID gedung
     * @param buildingName Nama gedung
     * @return Array of File objects
     */
    public static File[] getImageFiles(int buildingId, String buildingName) {
        String folderPath = getImageFolderPath(buildingId, buildingName);
        File folder = new File(folderPath);

        if (!folder.exists() || !folder.isDirectory()) {
            System.err.println("❌ Folder not found: " + folderPath);
            return new File[0];
        }

        // Filter hanya file gambar
        File[] files = folder.listFiles((dir, name) -> {
            String lower = name.toLowerCase();
            return lower.endsWith(".jpg") ||
                    lower.endsWith(".jpeg") ||
                    lower.endsWith(".png");
        });

        return files != null ? files : new File[0];
    }

    /**
     * Print debug info - untuk testing
     */
    public static void printDebugInfo(int buildingId, String buildingName) {
        System.out.println("=== IMAGE PATH DEBUG ===");
        System.out.println("Building ID: " + buildingId);
        System.out.println("Building Name: " + buildingName);
        System.out.println("Folder Name: " + getFolderName(buildingId, buildingName));
        System.out.println("Full Path: " + getImageFolderPath(buildingId, buildingName));
        System.out.println("Folder Exists: " + isFolderExists(buildingId, buildingName));

        File[] images = getImageFiles(buildingId, buildingName);
        System.out.println("Image Count: " + images.length);

        if (images.length > 0) {
            System.out.println("Images found:");
            for (File img : images) {
                System.out.println("  - " + img.getName());
            }
        } else {
            System.out.println("❌ No images found in folder");
        }
        System.out.println("========================");
    }
}
