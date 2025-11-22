package view;

import controller.MainController;
import model.User;
import model.Building;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Main Frame - Tampilan utama dengan peta kampus
 */
public class MainFrame extends JFrame {
    
    private User currentUser;
    private MainController controller = new MainController();
    private List<Building> buildings;
    
    public MainFrame(User user) {
        this.currentUser = user;
        this.buildings = controller.getBuildings();
        
        setTitle("Campus Map - " + user.getUsername() + " (" + getRoleDisplay() + ")");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Header
        JPanel headerPanel = createHeaderPanel();
        
        // Map panel (center)
        JPanel mapPanel = createMapPanel();
        
        // Sidebar (info panel)
        JPanel sidebarPanel = createSidebarPanel();
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(mapPanel, BorderLayout.CENTER);
        mainPanel.add(sidebarPanel, BorderLayout.EAST);
        
        add(mainPanel);
        setVisible(true);
    }
    
    /**
     * Create header panel
     */
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(getFacultyColor());
        panel.setPreferredSize(new Dimension(1200, 70));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        // Title
        JLabel titleLabel = new JLabel("PETA KAMPUS INTERAKTIF");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        
        // User info
        JPanel userInfoPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        userInfoPanel.setOpaque(false);
        
        String userText = currentUser.getUsername();
        if (!currentUser.isGuest() && !currentUser.isAdmin()) {
            userText += " - " + currentUser.getFaculty();
        }
        
        JLabel userLabel = new JLabel(userText);
        userLabel.setFont(new Font("Arial", Font.BOLD, 14));
        userLabel.setForeground(Color.WHITE);
        
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBackground(Color.WHITE);
        logoutButton.setForeground(getFacultyColor());
        logoutButton.setFont(new Font("Arial", Font.BOLD, 12));
        logoutButton.setFocusPainted(false);
        logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                "Apakah Anda yakin ingin logout?",
                "Konfirmasi Logout",
                JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
                new LoginSelectionFrame();
            }
        });
        
        userInfoPanel.add(userLabel);
        userInfoPanel.add(Box.createHorizontalStrut(15));
        userInfoPanel.add(logoutButton);
        
        panel.add(titleLabel, BorderLayout.WEST);
        panel.add(userInfoPanel, BorderLayout.EAST);
        
        return panel;
    }
    
    /**
     * Create map panel (placeholder - implement actual map)
     */
    private JPanel createMapPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new GridBagLayout());
        
        // TODO: Implement actual map dengan clickable buildings
        // Untuk sementara, buat grid of buttons untuk setiap building
        
        JPanel buildingsGrid = new JPanel(new GridLayout(3, 4, 10, 10));
        buildingsGrid.setBackground(Color.WHITE);
        
        for (Building building : buildings) {
            JButton buildingButton = new JButton("<html><center>" + building.getBuildingName() + 
                                                 "<br>⭐ " + String.format("%.1f", building.getAverageRating()) +
                                                 "</center></html>");
            buildingButton.setFont(new Font("Arial", Font.BOLD, 12));
            buildingButton.setPreferredSize(new Dimension(150, 100));
            
            // Set color based on faculty
            Color buildingColor = getBuildingColor(building.getFaculty());
            buildingButton.setBackground(buildingColor);
            buildingButton.setForeground(Color.WHITE);
            buildingButton.setFocusPainted(false);
            buildingButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            
            buildingButton.addActionListener(e -> {
                // Show building detail
                Building detail = controller.getBuildingDetail(building.getBuildingId());
                new BuildingDetailDialog(this, detail, currentUser);
            });
            
            buildingsGrid.add(buildingButton);
        }
        
        panel.add(buildingsGrid);
        
        return panel;
    }
    
    /**
     * Create sidebar panel
     */
    private JPanel createSidebarPanel() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(250, 800));
        panel.setBackground(new Color(245, 245, 245));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));
        
        // Info text
        JLabel infoLabel = new JLabel("INFO");
        infoLabel.setFont(new Font("Arial", Font.BOLD, 18));
        infoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JTextArea infoText = new JTextArea();
        infoText.setEditable(false);
        infoText.setLineWrap(true);
        infoText.setWrapStyleWord(true);
        infoText.setFont(new Font("Arial", Font.PLAIN, 12));
        infoText.setBackground(new Color(245, 245, 245));
        
        String info = "";
        if (currentUser.isGuest()) {
            info = "Mode Guest:\n\n" +
                   "• Peta ditampilkan dengan warna abu-abu\n" +
                   "• Anda dapat melihat info gedung\n" +
                   "• Tidak dapat memberikan rating\n\n" +
                   "Silakan login untuk fitur lengkap.";
        } else if (currentUser.isAdmin()) {
            info = "Mode Admin:\n\n" +
                   "• Kelola info gedung\n" +
                   "• Upload foto\n" +
                   "• Edit deskripsi & fasilitas\n" +
                   "• Tidak dapat memberikan rating";
        } else {
            info = "Mode Mahasiswa:\n\n" +
                   "• Peta dengan warna " + currentUser.getFaculty() + "\n" +
                   "• Klik gedung untuk detail\n" +
                   "• Beri rating untuk:\n" +
                   "  - Gedung " + currentUser.getFaculty() + "\n" +
                   "  - Fasilitas umum";
        }
        
        infoText.setText(info);
        infoText.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Legend
        JLabel legendLabel = new JLabel("LEGENDA:");
        legendLabel.setFont(new Font("Arial", Font.BOLD, 14));
        legendLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        legendLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        
        panel.add(infoLabel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(infoText);
        panel.add(Box.createVerticalStrut(15));
        panel.add(legendLabel);
        
        // Add faculty colors
        String[] faculties = {"FIK", "FK", "FH", "FEB", "FISIP", "UMUM"};
        for (String faculty : faculties) {
            JPanel colorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            colorPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
            colorPanel.setBackground(new Color(245, 245, 245));
            
            JPanel colorBox = new JPanel();
            colorBox.setPreferredSize(new Dimension(20, 20));
            colorBox.setBackground(getBuildingColor(faculty));
            
            JLabel colorLabel = new JLabel(" " + faculty);
            colorLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            
            colorPanel.add(colorBox);
            colorPanel.add(colorLabel);
            panel.add(colorPanel);
        }
        
        return panel;
    }
    
    /**
     * Get user role display text
     */
    private String getRoleDisplay() {
        if (currentUser.isGuest()) return "Guest";
        if (currentUser.isAdmin()) return "Admin";
        return currentUser.getFaculty();
    }
    
    /**
     * Get faculty/user color
     */
    private Color getFacultyColor() {
        if (currentUser.isGuest()) {
            return new Color(128, 128, 128); // Abu-abu
        }
        if (currentUser.isAdmin()) {
            return new Color(42, 115, 50); // Hijau default
        }
        return getBuildingColor(currentUser.getFaculty());
    }
    
    /**
     * Get building color based on faculty
     */
    private Color getBuildingColor(String faculty) {
        if (currentUser.isGuest()) {
            return new Color(200, 200, 200); // Abu-abu terang untuk guest
        }
        
        switch (faculty) {
            case "FIK": return new Color(42, 115, 50);      // Hijau
            case "FK": return new Color(220, 53, 69);       // Merah
            case "FH": return new Color(255, 193, 7);       // Kuning
            case "FEB": return new Color(0, 123, 255);      // Biru
            case "FISIP": return new Color(108, 117, 125);  // Abu-abu gelap
            case "UMUM": return new Color(128, 128, 128);   // Abu-abu
            default: return Color.GRAY;
        }
    }
}
