package view;

import javax.swing.*;
import java.awt.*;

/**
 * Splash Screen - Tampilan awal saat aplikasi dibuka
 * Ditampilkan selama 2 detik sebelum ke LoginSelectionFrame
 */
public class SplashScreen extends JWindow {
    
    public SplashScreen() {
        // Create content panel
        JPanel content = new JPanel();
        content.setBackground(new Color(42, 115, 50)); // Warna hijau kampus
        content.setLayout(new BorderLayout());
        
        // Logo/Title
        JLabel title = new JLabel("CAMPUS MAP", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 48));
        title.setForeground(Color.WHITE);
        
        JLabel subtitle = new JLabel("Interactive Campus Navigation System", SwingConstants.CENTER);
        subtitle.setFont(new Font("Arial", Font.PLAIN, 18));
        subtitle.setForeground(Color.WHITE);
        
        // Loading indicator
        JLabel loading = new JLabel("Loading...", SwingConstants.CENTER);
        loading.setFont(new Font("Arial", Font.ITALIC, 14));
        loading.setForeground(Color.WHITE);
        
        // Add components
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(new Color(42, 115, 50));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(100, 50, 100, 50));
        
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        loading.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        centerPanel.add(title);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(subtitle);
        centerPanel.add(Box.createVerticalStrut(50));
        centerPanel.add(loading);
        
        content.add(centerPanel, BorderLayout.CENTER);
        
        // Footer
        JLabel footer = new JLabel("Version 1.0 - Java Swing MVC", SwingConstants.CENTER);
        footer.setFont(new Font("Arial", Font.PLAIN, 10));
        footer.setForeground(Color.WHITE);
        footer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        content.add(footer, BorderLayout.SOUTH);
        
        // Set content
        setContentPane(content);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setVisible(true);
        
        // Timer untuk auto-close setelah 2 detik
        Timer timer = new Timer(2000, e -> {
            dispose();
            new LoginSelectionFrame();
        });
        timer.setRepeats(false);
        timer.start();
    }
}
