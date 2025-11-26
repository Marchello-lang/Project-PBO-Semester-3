import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class CampusMap extends JFrame {
    private Image mapImage;
    private JPanel mapPanel;

    public CampusMap() {
        setTitle("Peta Gedung UPNVJ");
        setSize(1400, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(0, 0));
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);

        // MAIN CONTAINER
        JPanel mainContainer = new JPanel(new BorderLayout(0, 0));
        mainContainer.setBackground(Color.WHITE);

        // MAP PANEL
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
        mapPanel.setBackground(new Color(245, 245, 245));
        
        // Load map image
        try {
            mapImage = new ImageIcon("G:\\Ivana Tiara Harsono 2410511064\\RevaUPNVJ\\RevaUPNVJ\\src\\revaupnvj\\images\\MapColored.jpg").getImage();
        } catch (Exception e) {
            System.out.println("Error loading map: " + e.getMessage());
        }

        // BUILDING BUTTONS
        addBuildingButtons();
        
        mainContainer.add(mapPanel, BorderLayout.CENTER);

        // VERTICAL DIVIDER
        JSeparator divider = new JSeparator(SwingConstants.VERTICAL);
        divider.setPreferredSize(new Dimension(3, 0));
        divider.setBackground(new Color(200, 200, 200));
        divider.setForeground(new Color(200, 200, 200));
        mainContainer.add(divider, BorderLayout.EAST);

        add(mainContainer, BorderLayout.CENTER);

        // SIDEBAR
        add(createSidebar(), BorderLayout.EAST);
    }

    private void addBuildingButtons() {
    // Koordinat Disesuaikan agar lebih tepat sesuai tata letak peta
    mapPanel.add(createBuildingButton("1", 120, 240, 50, 35)); // Lebih ke kiri atas
    mapPanel.add(createBuildingButton("2", 260, 160, 50, 35));
    mapPanel.add(createBuildingButton("3", 290, 270, 50, 35));
    mapPanel.add(createBuildingButton("4", 440, 70, 50, 35));
    mapPanel.add(createBuildingButton("5", 380, 170, 50, 35)); // Digeser ke kiri
    mapPanel.add(createBuildingButton("6", 380, 240, 50, 35));
    mapPanel.add(createBuildingButton("7", 510, 270, 60, 40));
    mapPanel.add(createBuildingButton("8", 370, 360, 50, 35));
    mapPanel.add(createBuildingButton("9", 330, 430, 50, 35));
    mapPanel.add(createBuildingButton("10", 240, 540, 50, 35));
    mapPanel.add(createBuildingButton("11", 400, 490, 50, 35));
    mapPanel.add(createBuildingButton("12", 460, 430, 50, 35));
    mapPanel.add(createBuildingButton("13", 530, 370, 50, 35));
    mapPanel.add(createBuildingButton("14", 610, 150, 50, 35)); // Lebih ke atas
    mapPanel.add(createBuildingButton("15", 590, 280, 50, 35));
    mapPanel.add(createBuildingButton("16", 590, 430, 50, 35));
    mapPanel.add(createBuildingButton("17", 680, 360, 50, 35)); // Digeser ke kanan
    mapPanel.add(createBuildingButton("18", 620, 510, 50, 35));
    mapPanel.add(createBuildingButton("19", 750, 400, 60, 35));
    mapPanel.add(createBuildingButton("20", 830, 160, 60, 35));
    mapPanel.add(createBuildingButton("21", 740, 280, 50, 35));
    mapPanel.add(createBuildingButton("22", 880, 280, 50, 35));
    mapPanel.add(createBuildingButton("23", 850, 400, 50, 35));
    mapPanel.add(createBuildingButton("24", 920, 440, 50, 35));
}

    private JPanel createSidebar() {
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
        JTextArea desc = new JTextArea("UPNVJ adalah Perguruan Tinggi Negeri (PTN) yang berlokasi di Jakarta, resmi sejak tahun 2014. UPNVJ identik dengan sebutan 'Kampus Bela Negara', berfokus pada pengembangan akademik sambil menanamkan nilai-nilai kebangsaan yang kuat.\n\nInstitusi ini menawarkan berbagai disiplin ilmu, mulai dari teknik, kesehatan, hingga ilmu sosial dan ekonomi. UPNVJ berupaya menghasilkan lulusan yang unggul, berintegritas, dan siap menjadi agen perubahan positif bagi bangsa.");
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

        // LEGEND ITEMS
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

    private JButton createBuildingButton(String num, int x, int y, int w, int h) {
        JButton btn = new JButton(num);
        btn.setBounds(x, y, w, h);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setOpaque(true);
        btn.setBackground(new Color(255, 255, 255, 230));
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(50, 50, 50), 2),
            BorderFactory.createEmptyBorder(2, 2, 2, 2)
        ));
        btn.setFocusPainted(false);
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
        
        btn.addActionListener(e -> new SlideshowWindow(num).setVisible(true));
        return btn;
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new CampusMap().setVisible(true);
        });
    }
}