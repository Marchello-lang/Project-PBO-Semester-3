package view;

import model.User;
import javax.swing.*;
import java.awt.*;

/**
 * Landing Page - Pilihan LOGIN / GUEST / ADMIN
 */
public class LoginSelectionFrame extends JFrame {
    
    public LoginSelectionFrame() {
        setTitle("Campus Map - Welcome");
        setSize(550, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setLayout(new BorderLayout());
        
        // Header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(42, 115, 50));
        headerPanel.setPreferredSize(new Dimension(550, 120));
        headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 30));
        
        JLabel titleLabel = new JLabel("CAMPUS MAP");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        
        // Center panel dengan buttons
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(50, 80, 50, 80));
        
        // LOGIN Button (Mahasiswa)
        JButton loginButton = createStyledButton("LOGIN SEBAGAI MAHASISWA", new Color(42, 115, 50));
        loginButton.addActionListener(e -> {
            dispose();
            new LoginFrame("user");
        });
        
        // GUEST Button
        JButton guestButton = createStyledButton("MASUK SEBAGAI GUEST", new Color(116, 181, 116));
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
        
        // Add components
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        guestButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        centerPanel.add(loginButton);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(guestButton);
        centerPanel.add(Box.createVerticalStrut(15));
        centerPanel.add(adminLink);
        
        // Footer
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(Color.WHITE);
        JLabel footerLabel = new JLabel("© 2024 Campus Map - Java Swing MVC");
        footerLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        footerLabel.setForeground(Color.GRAY);
        footerPanel.add(footerLabel);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
        setVisible(true);
    }
    
    /**
     * Helper method untuk create styled button
     */
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 15));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setMaximumSize(new Dimension(380, 55));
        button.setPreferredSize(new Dimension(380, 55));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Hover effect
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
}