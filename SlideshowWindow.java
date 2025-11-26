import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SlideshowWindow extends JFrame {
    private JLabel imageLabel;
    private JLabel captionLabel;
    private JLabel descriptionLabel;
    private JLabel hoursLabel;
    private JLabel counterLabel;
    private List<String> imagePaths;
    private int currentIndex = 0;
    private String buildingNumber;
    private BuildingInfo buildingInfo;

    public SlideshowWindow(String buildingNumber) {
        this.buildingNumber = buildingNumber;
        
        // Load building info from database
        buildingInfo = DatabaseManager.getBuildingInfo(buildingNumber);
        
        setTitle(buildingInfo.name);
        setSize(1100, 800);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(0, 0));
        getContentPane().setBackground(Color.WHITE);

        // HEADER
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(42, 155, 50));
        header.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBackground(new Color(42, 155, 50));
        
        JLabel titleLabel = new JLabel(buildingInfo.name);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        hoursLabel = new JLabel("⏰ " + buildingInfo.hours);
        hoursLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        hoursLabel.setForeground(new Color(230, 255, 230));
        hoursLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        titlePanel.add(titleLabel);
        titlePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        titlePanel.add(hoursLabel);
        
        JButton closeBtn = new JButton("✕");
        closeBtn.setFont(new Font("Arial", Font.BOLD, 22));
        closeBtn.setForeground(Color.WHITE);
        closeBtn.setBackground(new Color(42, 155, 50));
        closeBtn.setBorder(BorderFactory.createEmptyBorder(5, 18, 5, 18));
        closeBtn.setFocusPainted(false);
        closeBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeBtn.addActionListener(e -> dispose());
        closeBtn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                closeBtn.setBackground(new Color(200, 50, 50));
            }
            public void mouseExited(MouseEvent e) {
                closeBtn.setBackground(new Color(42, 155, 50));
            }
        });
        
        header.add(titlePanel, BorderLayout.WEST);
        header.add(closeBtn, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        // CENTER - Image
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(30, 35, 25, 35));
        
        imageLabel = new JLabel("", JLabel.CENTER);
        imageLabel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 3),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        imageLabel.setPreferredSize(new Dimension(1000, 450));
        imageLabel.setBackground(new Color(248, 248, 248));
        imageLabel.setOpaque(true);
        centerPanel.add(imageLabel, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        // BOTTOM - Info & Controls
        JPanel bottomPanel = new JPanel(new BorderLayout(15, 15));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(15, 35, 30, 35));

        // Info Panel
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);
        
        captionLabel = new JLabel("", JLabel.CENTER);
        captionLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        captionLabel.setForeground(new Color(42, 155, 50));
        captionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        descriptionLabel = new JLabel("", JLabel.CENTER);
        descriptionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        descriptionLabel.setForeground(new Color(80, 80, 80));
        descriptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        infoPanel.add(captionLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        infoPanel.add(descriptionLabel);
        bottomPanel.add(infoPanel, BorderLayout.CENTER);

        // Navigation
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 12));
        navPanel.setBackground(Color.WHITE);
        
        JButton prevBtn = createNavButton("◄ Sebelumnya");
        prevBtn.addActionListener(e -> previousImage());
        
        counterLabel = new JLabel("1 / 1");
        counterLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        counterLabel.setForeground(new Color(100, 100, 100));
        
        JButton nextBtn = createNavButton("Selanjutnya ►");
        nextBtn.addActionListener(e -> nextImage());
        
        navPanel.add(prevBtn);
        navPanel.add(counterLabel);
        navPanel.add(nextBtn);
        bottomPanel.add(navPanel, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);

        // Load images
        loadImages();
        displayCurrentImage();
    }

    private JButton createNavButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        btn.setBackground(new Color(42, 155, 50));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(12, 30, 12, 30));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(35, 130, 40));
            }
            public void mouseExited(MouseEvent e) {
                btn.setBackground(new Color(42, 155, 50));
            }
        });
        return btn;
    }

    private void loadImages() {
        imagePaths = new ArrayList<>();
        
        // Path ke folder gedung
        String basePath = "G:\\Ivana Tiara Harsono 2410511064\\RevaUPNVJ\\RevaUPNVJ\\src\\revaupnvj\\images\\";
        String folderName = buildingNumber + "_" + getFolderName();
        String folderPath = basePath + folderName;
        
        System.out.println("Looking for photos in: " + folderPath);
        
        File folder = new File(folderPath);
        
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles((dir, name) -> {
                String lower = name.toLowerCase();
                return lower.endsWith(".jpg") || lower.endsWith(".jpeg") || lower.endsWith(".png");
            });
            
            if (files != null && files.length > 0) {
                // Sort files by name
                java.util.Arrays.sort(files);
                for (File file : files) {
                    imagePaths.add(file.getAbsolutePath());
                    System.out.println("  Found: " + file.getName());
                }
            } else {
                System.out.println("  No images found in folder");
            }
        } else {
            System.out.println("  Folder does not exist: " + folderPath);
        }
        
        if (imagePaths.isEmpty()) {
            imagePaths.add("placeholder");
        }
    }

    private String getFolderName() {
        // Ambil nama folder dari nama gedung (remove "Gedung" dan special chars)
        String name = buildingInfo.name
                        .replaceAll("Gedung ", "")
                        .replaceAll("\\(.*\\)", "")
                        .replaceAll("[^a-zA-Z0-9]", "")
                        .trim();
        return name;
    }

    private void displayCurrentImage() {
        if (imagePaths.isEmpty()) return;
        
        String imagePath = imagePaths.get(currentIndex);
        
        if (!imagePath.equals("placeholder")) {
            try {
                ImageIcon icon = new ImageIcon(imagePath);
                if (icon.getIconWidth() > 0) {
                    Image scaled = icon.getImage().getScaledInstance(980, 430, Image.SCALE_SMOOTH);
                    imageLabel.setIcon(new ImageIcon(scaled));
                    imageLabel.setText("");
                    
                    // Get filename for caption
                    File file = new File(imagePath);
                    String filename = file.getName();
                    
                    // Remove number prefix and extension
                    String caption = filename.substring(0, filename.lastIndexOf('.'))
                                           .replaceAll("^\\d+_", "")
                                           .replaceAll("_", " ")
                                           .trim();
                    captionLabel.setText(formatCaption(caption));
                } else {
                    showPlaceholder();
                }
            } catch (Exception e) {
                System.err.println("Error loading image: " + e.getMessage());
                showPlaceholder();
            }
        } else {
            showPlaceholder();
        }
        
        // Wrap description in HTML for better formatting
        String wrappedDesc = "<html><div style='text-align:center;width:950px;'>" 
                           + buildingInfo.description 
                           + "</div></html>";
        descriptionLabel.setText(wrappedDesc);
        
        counterLabel.setText((currentIndex + 1) + " / " + imagePaths.size());
    }

    private void showPlaceholder() {
        imageLabel.setIcon(null);
        imageLabel.setText("<html><div style='text-align:center;padding:80px;'>"
            + "<div style='font-size:64px;color:#cccccc;'>📷</div>"
            + "<div style='font-size:20px;color:#999999;margin-top:25px;'>Foto tidak tersedia</div>"
            + "<div style='font-size:14px;color:#bbbbbb;margin-top:10px;'>Upload foto ke folder " + buildingNumber + "_" + getFolderName() + "</div>"
            + "</div></html>");
        captionLabel.setText("Tidak ada gambar");
    }

    private String formatCaption(String text) {
        if (text.isEmpty()) return "Fasilitas " + (currentIndex + 1);
        
        // Capitalize first letter of each word
        String[] words = text.split(" ");
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            if (word.length() > 0) {
                result.append(Character.toUpperCase(word.charAt(0)))
                      .append(word.substring(1).toLowerCase())
                      .append(" ");
            }
        }
        return result.toString().trim();
    }

    private void nextImage() {
        if (imagePaths.isEmpty()) return;
        currentIndex = (currentIndex + 1) % imagePaths.size();
        displayCurrentImage();
    }

    private void previousImage() {
        if (imagePaths.isEmpty()) return;
        currentIndex = (currentIndex - 1 + imagePaths.size()) % imagePaths.size();
        displayCurrentImage();
    }
}