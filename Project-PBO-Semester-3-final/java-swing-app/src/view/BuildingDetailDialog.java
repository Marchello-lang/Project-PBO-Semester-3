package view;

import model.Building;
import model.Room;
import model.User;
import controller.AdminController;
import dao.BuildingDAO;

import javax.swing.*;
import java.awt.*;

public class BuildingDetailDialog extends JDialog {
    private Building building;
    private User currentUser;
    private BuildingDAO buildingDAO = new BuildingDAO();

    public BuildingDetailDialog(JFrame parent, Building building, User user) {
        super(parent, building.getBuildingName(), false);
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

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(getBuildingColor());
        panel.setPreferredSize(new Dimension(700, 80));
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel nameLabel = new JLabel(building.getBuildingName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 24));
        nameLabel.setForeground(Color.WHITE);

        JLabel ratingLabel = new JLabel("⭐ " + String.format("%.1f", building.getAverageRating()) + " / 5.0");
        ratingLabel.setFont(new Font("Arial", Font.BOLD, 18));
        ratingLabel.setForeground(Color.WHITE);

        panel.add(nameLabel, BorderLayout.WEST);
        panel.add(ratingLabel, BorderLayout.EAST);

        return panel;
    }

    private JPanel createInfoPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Description
        addInfoSection(panel, "Deskripsi:", building.getDescription());
        addInfoSection(panel, "Jam Operasional:", building.getHours());
        addInfoSection(panel, "Fasilitas:", building.getFacilities());

        // Tombol slideshow
        JButton slideshowBtn = new JButton("Lihat Foto Gedung");
        styleGreenButton(slideshowBtn);
        slideshowBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        slideshowBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // ==================== FIX: Action button, bukan auto-run ====================
        slideshowBtn.addActionListener(e -> {
            SlideshowWindow win = new SlideshowWindow(building);
            win.setAlwaysOnTop(true);
            win.toFront();
        });

        panel.add(Box.createVerticalStrut(10));
        panel.add(slideshowBtn);
        panel.add(Box.createVerticalStrut(15));

        slideshowBtn.addActionListener(e -> {
            System.out.println("TOMBOL SLIDESHOW DIKLIK!"); // Tambahkan ini
            SlideshowWindow win = new SlideshowWindow(building);
            win.setAlwaysOnTop(true);
            win.toFront();
        });


        return panel;
    }

    private JPanel createRoomsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        DefaultListModel<String> listModel = new DefaultListModel<>();
        JList<String> roomsList = new JList<>(listModel);
        roomsList.setFont(new Font("Arial", Font.PLAIN, 14));

        if (building.getRooms() != null && !building.getRooms().isEmpty()) {
            for (Room room : building.getRooms()) {
                String info = room.getRoomName() + " - ⭐ " +
                        String.format("%.1f", room.getAverageRating());
                listModel.addElement(info);
            }
        } else {
            listModel.addElement("Belum ada data ruangan");
        }

        JScrollPane scrollPane = new JScrollPane(roomsList);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(Color.WHITE);

        JButton ratingButton = new JButton("Beri Rating");
        styleGreenButton(ratingButton);

        ratingButton.addActionListener(e -> {
            int selectedIndex = roomsList.getSelectedIndex();
            if (selectedIndex == -1 || building.getRooms().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Pilih ruangan dulu!",
                        "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Room selectedRoom = building.getRooms().get(selectedIndex);

            if (currentUser.isGuest()) {
                JOptionPane.showMessageDialog(this,
                        "Silakan login untuk memberi rating.",
                        "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            if (currentUser.isAdmin()) {
                JOptionPane.showMessageDialog(this,
                        "Admin tidak bisa memberi rating.",
                        "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            new RatingDialog(this, selectedRoom, currentUser);
        });

        bottomPanel.add(ratingButton);

        if (currentUser.isAdmin()) {

            JButton btnAdd = new JButton("Tambah");
            JButton btnEdit = new JButton("Edit");
            JButton btnDelete = new JButton("Hapus");

            styleBlueButton(btnAdd);
            styleBlueButton(btnEdit);
            styleRedButton(btnDelete);

            btnAdd.addActionListener(e -> {
                AdminController adminController = new AdminController();
                new AddRoomDialog(this, adminController, building);
            });

            btnEdit.addActionListener(e -> {
                int idx = roomsList.getSelectedIndex();
                if (idx == -1 || building.getRooms().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Pilih ruangan dulu!",
                            "Peringatan", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Room selectedRoom = building.getRooms().get(idx);

                AdminController adminController = new AdminController();
                new EditRoomDialog(this, adminController, selectedRoom);
            });

            btnDelete.addActionListener(e -> {
                int idx = roomsList.getSelectedIndex();
                if (idx == -1 || building.getRooms().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Pilih ruangan dulu!",
                            "Peringatan", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Room room = building.getRooms().get(idx);

                AdminController adminController = new AdminController();
                new DeleteRoomDialog(this, adminController, room);
            });

            bottomPanel.add(btnAdd);
            bottomPanel.add(btnEdit);
            bottomPanel.add(btnDelete);
        }

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void styleGreenButton(JButton b) {
        b.setFont(new Font("Arial", Font.BOLD, 14));
        b.setBackground(new Color(42,115,50));
        b.setForeground(Color.WHITE);
        b.setOpaque(true);
        b.setContentAreaFilled(true);
        b.setBorderPainted(false);
        b.putClientProperty("JButton.backgroundColor", new Color(42,115,50));
    }

    private void styleBlueButton(JButton b) {
        b.setFont(new Font("Arial", Font.BOLD, 14));
        b.setBackground(new Color(0,123,255));
        b.setForeground(Color.WHITE);
        b.setOpaque(true);
        b.setContentAreaFilled(true);
        b.setBorderPainted(false);
        b.putClientProperty("JButton.backgroundColor", new Color(0,123,255));
    }

    private void styleRedButton(JButton b) {
        b.setFont(new Font("Arial", Font.BOLD, 14));
        b.setBackground(new Color(220,53,69));
        b.setForeground(Color.WHITE);
        b.setOpaque(true);
        b.setContentAreaFilled(true);
        b.setBorderPainted(false);
        b.putClientProperty("JButton.backgroundColor", new Color(20,53,69));
    }

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

    public void refreshDataAndUI() {
        buildingDAO.reloadRooms(this.building);

        JTabbedPane tabbedPane = (JTabbedPane) ((BorderLayout) this.getContentPane().getLayout()).getLayoutComponent(BorderLayout.CENTER);
        if (tabbedPane == null) return;

        int roomsTabIndex = tabbedPane.indexOfTab("Ruangan & Rating");
        if (roomsTabIndex != -1) {
            tabbedPane.removeTabAt(roomsTabIndex);
            tabbedPane.insertTab("Ruangan & Rating", null, createRoomsPanel(), null, roomsTabIndex);
            tabbedPane.setSelectedIndex(roomsTabIndex);
        }

        this.revalidate();
        this.repaint();
    }

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
