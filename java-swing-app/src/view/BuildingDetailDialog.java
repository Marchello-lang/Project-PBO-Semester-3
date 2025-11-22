package view;

import model.Building;
import model.Room;
import model.User;

import javax.swing.*;
import java.awt.*;

/**
 * Building Detail Dialog - Tampilkan detail gedung saat diklik
 */
public class BuildingDetailDialog extends JDialog {
    
    private Building building;
    private User currentUser;
    
    public BuildingDetailDialog(JFrame parent, Building building, User user) {
        super(parent, building.getBuildingName(), true);
        this.building = building;
        this.currentUser = user;
        
        setSize(700, 600);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = createHeaderPanel();
        
        // Content (tabs)
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Info", createInfoPanel());
        tabbedPane.addTab("Ruangan & Rating", createRoomsPanel());
        
        // Buttons
        JPanel buttonPanel = createButtonPanel();
        
        add(headerPanel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        setVisible(true);
    }
    
    /**
     * Create header panel
     */
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(getBuildingColor());
        panel.setPreferredSize(new Dimension(700, 80));
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        // Building name
        JLabel nameLabel = new JLabel(building.getBuildingName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 24));
        nameLabel.setForeground(Color.WHITE);
        
        // Rating
        JLabel ratingLabel = new JLabel("⭐ " + String.format("%.1f", building.getAverageRating()) + " / 5.0");
        ratingLabel.setFont(new Font("Arial", Font.BOLD, 18));
        ratingLabel.setForeground(Color.WHITE);
        
        panel.add(nameLabel, BorderLayout.WEST);
        panel.add(ratingLabel, BorderLayout.EAST);
        
        return panel;
    }
    
    /**
     * Create info panel
     */
    private JPanel createInfoPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Description
        addInfoSection(panel, "Deskripsi:", building.getDescription());
        
        // Hours
        addInfoSection(panel, "Jam Operasional:", building.getHours());
        
        // Facilities
        addInfoSection(panel, "Fasilitas:", building.getFacilities());
        
        // Photos (placeholder)
        JLabel photosLabel = new JLabel("Foto Gedung:");
        photosLabel.setFont(new Font("Arial", Font.BOLD, 14));
        photosLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(photosLabel);
        panel.add(Box.createVerticalStrut(5));
        
        JPanel photosPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        photosPanel.setBackground(Color.WHITE);
        photosPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));
        
        for (int i = 0; i < 6; i++) {
            JPanel photoPlaceholder = new JPanel();
            photoPlaceholder.setBackground(new Color(200, 200, 200));
            photoPlaceholder.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            
            JLabel photoLabel = new JLabel("Foto " + (i + 1), SwingConstants.CENTER);
            photoLabel.setForeground(Color.GRAY);
            photoPlaceholder.add(photoLabel);
            
            photosPanel.add(photoPlaceholder);
        }
        
        panel.add(photosPanel);
        
        return panel;
    }
    
    /**
     * Create rooms panel dengan rating
     */
    private JPanel createRoomsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        // Rooms list
        DefaultListModel<String> listModel = new DefaultListModel<>();
        
        if (building.getRooms() != null && !building.getRooms().isEmpty()) {
            for (Room room : building.getRooms()) {
                String roomInfo = room.getRoomName() + " - ⭐ " + 
                                 String.format("%.1f", room.getAverageRating());
                listModel.addElement(roomInfo);
            }
        } else {
            listModel.addElement("Belum ada data ruangan");
        }
        
        JList<String> roomsList = new JList<>(listModel);
        roomsList.setFont(new Font("Arial", Font.PLAIN, 14));
        roomsList.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JScrollPane scrollPane = new JScrollPane(roomsList);
        
        // Rating button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(Color.WHITE);
        
        JButton ratingButton = new JButton("Beri Rating");
        ratingButton.setFont(new Font("Arial", Font.BOLD, 14));
        ratingButton.setBackground(new Color(42, 115, 50));
        ratingButton.setForeground(Color.WHITE);
        ratingButton.setFocusPainted(false);
        ratingButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        ratingButton.addActionListener(e -> {
            int selectedIndex = roomsList.getSelectedIndex();
            if (selectedIndex == -1) {
                JOptionPane.showMessageDialog(this,
                    "Pilih ruangan terlebih dahulu!",
                    "Peringatan",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            Room selectedRoom = building.getRooms().get(selectedIndex);
            
            // Check permission
            if (currentUser.isGuest()) {
                JOptionPane.showMessageDialog(this,
                    "Silakan login untuk memberikan rating!",
                    "Info",
                    JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            if (currentUser.isAdmin()) {
                JOptionPane.showMessageDialog(this,
                    "Admin tidak dapat memberikan rating!",
                    "Info",
                    JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            // Open rating dialog
            new RatingDialog(this, selectedRoom, currentUser);
        });
        
        bottomPanel.add(ratingButton);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    /**
     * Create button panel
     */
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JButton closeButton = new JButton("Tutup");
        closeButton.setFont(new Font("Arial", Font.BOLD, 14));
        closeButton.setPreferredSize(new Dimension(100, 35));
        closeButton.setFocusPainted(false);
        closeButton.addActionListener(e -> dispose());
        
        panel.add(closeButton);
        
        return panel;
    }
    
    /**
     * Helper untuk add info section
     */
    private void addInfoSection(JPanel panel, String title, String content) {
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JTextArea contentArea = new JTextArea(content != null ? content : "-");
        contentArea.setEditable(false);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setFont(new Font("Arial", Font.PLAIN, 13));
        contentArea.setBackground(Color.WHITE);
        contentArea.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(contentArea);
        panel.add(Box.createVerticalStrut(15));
    }
    
    /**
     * Get building color
     */
    private Color getBuildingColor() {
        switch (building.getFaculty()) {
            case "FIK": return new Color(42, 115, 50);
            case "FK": return new Color(220, 53, 69);
            case "FH": return new Color(255, 193, 7);
            case "FEB": return new Color(0, 123, 255);
            case "FISIP": return new Color(108, 117, 125);
            case "UMUM": return new Color(128, 128, 128);
            default: return Color.GRAY;
        }
    }
}
