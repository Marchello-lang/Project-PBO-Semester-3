package view;

import controller.MainController;
import model.Room;
import model.User;

import javax.swing.*;
import java.awt.*;

/**
 * Rating Dialog - Form untuk submit rating
 */
public class RatingDialog extends JDialog {
    
    private Room room;
    private User user;
    private MainController controller = new MainController();
    
    private JSlider ratingSlider;
    private JLabel ratingValueLabel;
    private JTextArea reviewArea;
    
    public RatingDialog(JDialog parent, Room room, User user) {
        super(parent, "Beri Rating - " + room.getRoomName(), true);
        this.room = room;
        this.user = user;
        
        setSize(500, 400);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(42, 115, 50));
        headerPanel.setPreferredSize(new Dimension(500, 60));
        
        JLabel titleLabel = new JLabel("Rating untuk " + room.getRoomName());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        
        // Content
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        // Rating slider
        JLabel ratingLabel = new JLabel("Pilih Rating (1-5):");
        ratingLabel.setFont(new Font("Arial", Font.BOLD, 14));
        ratingLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JPanel ratingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ratingPanel.setBackground(Color.WHITE);
        ratingPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        
        ratingSlider = new JSlider(1, 5, 3);
        ratingSlider.setMajorTickSpacing(1);
        ratingSlider.setPaintTicks(true);
        ratingSlider.setPaintLabels(true);
        ratingSlider.setSnapToTicks(true);
        ratingSlider.setPreferredSize(new Dimension(300, 50));
        
        ratingValueLabel = new JLabel("⭐ 3");
        ratingValueLabel.setFont(new Font("Arial", Font.BOLD, 24));
        ratingValueLabel.setForeground(new Color(255, 193, 7));
        
        ratingSlider.addChangeListener(e -> {
            int value = ratingSlider.getValue();
            String stars = "";
            for (int i = 0; i < value; i++) {
                stars += "⭐";
            }
            ratingValueLabel.setText(stars + " " + value);
        });
        
        ratingPanel.add(ratingSlider);
        ratingPanel.add(ratingValueLabel);
        
        // Review text area
        JLabel reviewLabel = new JLabel("Review (opsional):");
        reviewLabel.setFont(new Font("Arial", Font.BOLD, 14));
        reviewLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        reviewArea = new JTextArea(5, 30);
        reviewArea.setLineWrap(true);
        reviewArea.setWrapStyleWord(true);
        reviewArea.setFont(new Font("Arial", Font.PLAIN, 13));
        reviewArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        JScrollPane reviewScroll = new JScrollPane(reviewArea);
        reviewScroll.setAlignmentX(Component.LEFT_ALIGNMENT);
        reviewScroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        
        contentPanel.add(ratingLabel);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(ratingPanel);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(reviewLabel);
        contentPanel.add(Box.createVerticalStrut(5));
        contentPanel.add(reviewScroll);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(Color.WHITE);
        
        JButton submitButton = new JButton("Kirim Rating");
        submitButton.setFont(new Font("Arial", Font.BOLD, 14));
        submitButton.setBackground(new Color(42, 115, 50));
        submitButton.setForeground(Color.WHITE);
        submitButton.setPreferredSize(new Dimension(150, 40));
        submitButton.setFocusPainted(false);
        submitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        submitButton.addActionListener(e -> submitRating());
        
        JButton cancelButton = new JButton("Batal");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 14));
        cancelButton.setBackground(Color.GRAY);
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setPreferredSize(new Dimension(100, 40));
        cancelButton.setFocusPainted(false);
        cancelButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cancelButton.addActionListener(e -> dispose());
        
        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);
        
        add(headerPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        setVisible(true);
    }
    
    /**
     * Submit rating
     */
    private void submitRating() {
        int rating = ratingSlider.getValue();
        String review = reviewArea.getText().trim();
        
        boolean success = controller.submitRating(user, room.getRoomId(), rating, review);
        
        if (success) {
            JOptionPane.showMessageDialog(this,
                "Rating berhasil dikirim!\nTerima kasih atas feedback Anda.",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                "Gagal mengirim rating.\nPastikan Anda memiliki izin untuk rating ruangan ini.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}
