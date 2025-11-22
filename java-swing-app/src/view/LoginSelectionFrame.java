package view;

import model.User;
import javax.swing.*;
import java.awt.*;

/**
 * Landing Page - Pilihan LOGIN / GUEST / ADMIN
 */
public class LoginSelectionFrame extends JFrame {

    public LoginSelectionFrame() {
        setTitle("RevaUPNVJ - Login");
        setSize(1280, 832);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setLayout(new BorderLayout());    

        
        // ========================= HEADER =========================
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(42, 115, 50));
        headerPanel.setPreferredSize(new Dimension(1280, 70));
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

        // Title text
        JLabel titleLabel = new JLabel("RevaUPNVJ");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);

        // Load logo
        ImageIcon icon = new ImageIcon(getClass().getResource("/assets/UPN.PNG"));
        Image img = icon.getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(img);

        JLabel iconLabel = new JLabel(scaledIcon);

        // Add to panel
        headerPanel.add(iconLabel);
        headerPanel.add(titleLabel);
        
        // ========================= CENTER CONTENT =========================
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Color.WHITE);

        // Logo besar tengah
        Image scaledBig = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        JLabel bigLogo = new JLabel(new ImageIcon(scaledBig));
        bigLogo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Judul besar
        JLabel judul = new JLabel("TENTANG APLIKASI");
        judul.setFont(new Font("Arial", Font.BOLD, 30));
        judul.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Deskripsi
        JLabel desc = new JLabel(
            "<html>"
                + "<div style='text-align:center; width:600px;'>"
                + "Aplikasi RevaUPNVJ hadir sebagai platform untuk melihat dan memberikan "
                + "ulasan terhadap fasilitas di setiap fakultas. Tujuan RevaUPNVJ untuk "
                + "menghadirkan informasi fasilitas kampus yang akurat, transparan, dan mudah diakses."
                + "</div>"
            + "</html>"
        );
        desc.setFont(new Font("Arial", Font.PLAIN, 16));
        desc.setAlignmentX(Component.CENTER_ALIGNMENT);
        desc.setBorder(BorderFactory.createEmptyBorder(20, 250, 20, 250));

        //Button Size
        Dimension btnSize = new Dimension(260, 50);

        // Button LOGIN Mahasiswa
        JButton loginButton = createStyledButton("LOGIN", new Color(42, 115, 50));
        loginButton.setAlignmentX(CENTER_ALIGNMENT);
        loginButton.setPreferredSize(btnSize);
        loginButton.setMaximumSize(btnSize);
        loginButton.addActionListener(e -> {
            dispose();
            new LoginFrame("user");
        });


        // Button Guest
        JButton guestButton = createOutlineButton("GUEST", new Color(42, 115, 50));
        guestButton.setAlignmentX(CENTER_ALIGNMENT);
        guestButton.setPreferredSize(btnSize);
        guestButton.setMaximumSize(btnSize);
        guestButton.addActionListener(e -> {
            dispose();
            User guest = User.createGuest();
            new MainFrame(guest);
        });
        

        // Admin link
        JButton adminLink = new JButton("*login sebagai admin");
        adminLink.setFont(new Font("Arial", Font.PLAIN, 12));
        adminLink.setForeground(new Color(0, 123, 255));
        adminLink.setBorderPainted(false);
        adminLink.setContentAreaFilled(false);
        adminLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        adminLink.setAlignmentX(Component.CENTER_ALIGNMENT);
        adminLink.addActionListener(e -> {
            dispose();
            new LoginFrame("admin");
        });

        // Add ke center panel
        centerPanel.add(Box.createVerticalStrut(70));
        centerPanel.add(bigLogo);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(judul);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(desc);
        centerPanel.add(Box.createVerticalStrut(50));
        centerPanel.add(loginButton);
        centerPanel.add(Box.createVerticalStrut(15));
        centerPanel.add(guestButton);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(adminLink);
        centerPanel.add(Box.createVerticalGlue());

        // ========================= FOOTER =========================
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(Color.WHITE);
        JLabel footerLabel = new JLabel("© 2025 RevaUPNVJ - Java Swing MVC");
        footerLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        footerLabel.setForeground(Color.GRAY);
        footerPanel.add(footerLabel);

        // Add semua ke main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }


    // ===================== BUTTON FILLED =====================
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    // ===================== BUTTON OUTLINE =====================
    private JButton createOutlineButton(String text, Color borderColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setForeground(borderColor);
        button.setBorder(BorderFactory.createLineBorder(borderColor, 2, true));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }
}
