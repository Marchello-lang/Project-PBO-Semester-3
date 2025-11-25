package view;

import controller.MainController;
import model.Building;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class MainFrame extends JFrame {

    private User currentUser;
    private MainController controller = new MainController();
    private List<Building> buildings;

    private Image mapImage;
    private JPanel mapPanel;

    public MainFrame(User user) {
        this.currentUser = user;
        this.buildings = controller.getBuildings();

        setTitle("RevaUPNVJ - " + user.getUsername());
        setSize(1400, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        loadMapImage();

        JPanel mainPanel = new JPanel(new BorderLayout());

        mainPanel.add(createHeaderPanel(), BorderLayout.NORTH);
        mainPanel.add(createMapPanelWithButtons(), BorderLayout.CENTER);
        mainPanel.add(createSidebarPanel(), BorderLayout.EAST);

        add(mainPanel);
        setVisible(true);
    }

    /* -------------------------------------------------------------
       LOAD MAP IMAGE
    ------------------------------------------------------------- */
    private void loadMapImage() {
        try {
            mapImage = new ImageIcon(getClass().getResource("/assets/MapColored.jpg")).getImage();
        } catch (Exception e) {
            System.out.println("Error loading map image: " + e.getMessage());
        }
    }

    /* -------------------------------------------------------------
       MAP PANEL (CampusMap digabung disini)
    ------------------------------------------------------------- */
    private JPanel createMapPanelWithButtons() {

        mapPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (mapImage != null) {
                    g.drawImage(mapImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        mapPanel.setLayout(null);
        mapPanel.setBackground(Color.WHITE);

        // Tambahkan tombol gedung dari database
        addDatabaseBuildingButtons();

        return mapPanel;
    }

    /* -------------------------------------------------------------
       MENAMBAHKAN TOMBOL GEDUNG (koordinat dari CampusMap)
    ------------------------------------------------------------- */
    private void addDatabaseBuildingButtons() {

        int[][] coords = {
                // id    x   y   w   h
                {1, 190, 267, 50, 35},
                {2, 227, 187, 50, 35},
                {3, 260, 284, 50, 35},
                {4, 370, 94, 50, 35},
                {5, 390, 182, 50, 35},
                {6, 404, 257, 50, 35},
                {7, 500, 273, 60, 40},
                {8, 355, 365, 50, 35},
                {9, 272, 430, 50, 35},
                {10, 229, 542, 50, 35},
                {11, 405, 496, 50, 35},
                {12, 437, 415, 50, 35},
                {13, 545, 382, 50, 35},
                {14, 538, 148, 50, 35},
                {15, 618, 284, 50, 35},
                {16, 636, 416, 50, 35},
                {17, 762, 416, 50, 35},
                {18, 503, 496, 50, 35},
                {19, 830, 381, 60, 35},
                {20, 680, 158, 60, 35},
                {21, 796, 278, 50, 35},
                {22, 338, 478, 50, 35},
                {23, 485, 383, 50, 35},
        };

        for (Building b : buildings) {
            int id = b.getBuildingId();

            if (id < 1 || id > coords.length) continue;

            int[] c = coords[id - 1];
            JButton btn = createBuildingButton(b, c[1], c[2], c[3], c[4]);

            mapPanel.add(btn);
        }
    }

    /* -------------------------------------------------------------
       BUTTON GEDUNG (gabungan UI CampusMap + data MainFrame)
    ------------------------------------------------------------- */
    private JButton createBuildingButton(Building b, int x, int y, int w, int h) {

        JButton btn = new JButton(String.valueOf(b.getBuildingId()));
        btn.setBounds(x, y, w, h);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setOpaque(true);
        btn.setBackground(new Color(255, 255, 255, 230));
        btn.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(42, 155, 50, 230));
                btn.setForeground(Color.WHITE);
            }
            public void mouseExited(MouseEvent e) {
                btn.setBackground(new Color(255, 255, 255, 230));
                btn.setForeground(Color.BLACK);
            }
        });

        btn.addActionListener(e -> {
            // buka detail gedung versi database
            Building detail = controller.getBuildingDetail(b.getBuildingId());
            new BuildingDetailDialog(this, detail, currentUser);
        });

        return btn;
    }

    /* -------------------------------------------------------------
       HEADER PANEL (tetap sama)
    ------------------------------------------------------------- */
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(42, 115, 50));
        panel.setPreferredSize(new Dimension(1400, 70));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel title = new JLabel("RevaUPNVJ");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setForeground(Color.WHITE);

        panel.add(title, BorderLayout.WEST);

        return panel;
    }

    /* -------------------------------------------------------------
       SIDEBAR PANEL (tetap sama)
    ------------------------------------------------------------- */
    /**
     * Sidebar panel (mengambil versi CampusMap)
     */
    private JPanel createSidebarPanel() {
        JPanel sidebar = new JPanel(new BorderLayout());
        sidebar.setPreferredSize(new Dimension(380, 0));
        sidebar.setBackground(Color.WHITE);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(Color.WHITE);
        content.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // TITLE
        JLabel title = new JLabel("UPNVJ");
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setForeground(new Color(42, 155, 50));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(title);
        content.add(Box.createRigidArea(new Dimension(0, 15)));

        // DESCRIPTION
        JTextArea desc = new JTextArea(
                "UPNVJ adalah Perguruan Tinggi Negeri (PTN) yang berlokasi di Jakarta, " +
                        "resmi sejak tahun 2014. UPNVJ identik dengan sebutan 'Kampus Bela Negara', " +
                        "berfokus pada pengembangan akademik sambil menanamkan nilai-nilai kebangsaan.\n\n" +
                        "UPNVJ menawarkan berbagai disiplin ilmu, mulai dari teknik, kesehatan, " +
                        "hingga ilmu sosial dan ekonomi. UPNVJ menghasilkan lulusan unggul, " +
                        "berintegritas, dan siap menjadi agen perubahan."
        );

        desc.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        desc.setLineWrap(true);
        desc.setWrapStyleWord(true);
        desc.setEditable(false);
        desc.setOpaque(false);
        desc.setForeground(new Color(60, 60, 60));
        desc.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(desc);
        content.add(Box.createRigidArea(new Dimension(0, 30)));

        // DIVIDER
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 2));
        separator.setAlignmentX(Component.LEFT_ALIGNMENT);
        separator.setBackground(new Color(220, 220, 220));
        content.add(separator);
        content.add(Box.createRigidArea(new Dimension(0, 25)));

        // LEGEND TITLE
        JLabel legendTitle = new JLabel("LEGENDA");
        legendTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        legendTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(legendTitle);
        content.add(Box.createRigidArea(new Dimension(0, 15)));

        // LEGEND ITEMS (warna fakultas)
        content.add(createLegendItem("Rektorat", new Color(42, 155, 50)));
        content.add(createLegendItem("Fakultas Kedokteran", new Color(0, 138, 61)));
        content.add(createLegendItem("FEB", new Color(255, 211, 0)));
        content.add(createLegendItem("FISIP", new Color(67, 8, 132)));
        content.add(createLegendItem("Fakultas Hukum", new Color(173, 0, 0)));
        content.add(createLegendItem("FIK", new Color(255, 101, 1)));
        content.add(createLegendItem("Fasilitas Umum", new Color(168, 168, 168)));
        content.add(createLegendItem("Gedung Wirausaha", new Color(63, 218, 223)));
        content.add(createLegendItem("Kantin", new Color(228, 227, 134)));
        content.add(createLegendItem("Perpustakaan", new Color(255, 107, 107)));

        content.add(Box.createVerticalGlue());

        JScrollPane scrollPane = new JScrollPane(content);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        sidebar.add(scrollPane, BorderLayout.CENTER);
        return sidebar;
    }

    private JPanel createLegendItem(String name, Color color) {
        JPanel item = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 6));
        item.setBackground(Color.WHITE);
        item.setAlignmentX(Component.LEFT_ALIGNMENT);
        item.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));

        JPanel colorBox = new JPanel();
        colorBox.setBackground(color);
        colorBox.setPreferredSize(new Dimension(30, 24));
        colorBox.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150), 1));

        JLabel label = new JLabel(name);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        item.add(colorBox);
        item.add(label);
        return item;
    }

}
