package view;

import controller.LoginController;
import model.User;

import javax.swing.*;
import javax.swing.event.DocumentListener;

import java.awt.*;
import java.awt.event.*;

/**
 * Login Frame dengan UI modern (sesuai desain)
 */
public class LoginFrame extends JFrame {

    private LoginController controller = new LoginController();
    private String loginType;

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> facultyCombo;
    private Image backgroundImage;
    private float opacity = 1.0f;

    private JButton loginButton;

    public LoginFrame(String loginType) {
        this.loginType = loginType;

        setTitle("RevaUPN - Login");
        setSize(1280, 832);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Background
        JPanel bg = new JPanel() {
            Image img = new ImageIcon(getClass().getResource("/assets/Kampus.jpg")).getImage();
            float opacity = 0.5f;

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
                g2.drawImage(img, 0, 0, getWidth(), getHeight(), this);

                g2.dispose();
            }
        };

        bg.setOpaque(false);
        bg.setLayout(new GridBagLayout());
        add(bg);

        // Card putih (Login Box)
        JPanel card = new RoundedPanel(40);
        card.setPreferredSize(new Dimension(500, loginType.equals("admin") ? 450 : 450));
        card.setBackground(Color.WHITE);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        // Back icon
        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        backPanel.setOpaque(false);
        JLabel backIcon = new JLabel("←");
        backIcon.setFont(new Font("Arial", Font.BOLD, 26));
        backIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backIcon.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                dispose();
                new LoginSelectionFrame();
            }
        });
        backPanel.add(backIcon);

        // Title
        JLabel titleLabel = new JLabel("Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(new Color(42, 115, 50));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel descLabel = new JLabel("Masuk sebagai Pengguna");
        descLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        descLabel.setForeground(Color.DARK_GRAY);
        descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Username field
        usernameField = new ModernTextField("Username");

        // Password field
        passwordField = new ModernPasswordField("Password");

        // Faculty dropdown (hanya user)
        if (loginType.equals("user")) {
            String[] faculties = {"FIK", "FK", "FH", "FEB", "FISIP"};
            facultyCombo = new JComboBox<>(faculties);
            facultyCombo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
            facultyCombo.setFont(new Font("Arial", Font.PLAIN, 14));
        }

        // Login button (disabled dulu)
        loginButton = new JButton("Login");
        loginButton.setEnabled(false);
        loginButton.setBackground(new Color(180, 180, 180));
        loginButton.setForeground(Color.WHITE);
        loginButton.setContentAreaFilled(false);
        loginButton.setOpaque(true);
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.setPreferredSize(new Dimension(200, 45));
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.addActionListener(e -> handleLogin());

        // Listener untuk mengaktifkan tombol
        DocumentListener updateButtonState = new SimpleDocListener(() -> updateLoginButton()) {};
        usernameField.getDocument().addDocumentListener(updateButtonState);
        passwordField.getDocument().addDocumentListener(updateButtonState);
        if (loginType.equals("user")) {
            facultyCombo.addActionListener(e -> updateLoginButton());
        }

        // Tambahkan semua ke card
        card.add(backPanel);
        card.add(titleLabel);
        card.add(descLabel);
        card.add(Box.createVerticalStrut(30));

        card.add(usernameField);
        card.add(Box.createVerticalStrut(15));
        card.add(passwordField);
        card.add(Box.createVerticalStrut(15));

        if (loginType.equals("user")) {
            card.add(facultyCombo);
            card.add(Box.createVerticalStrut(25));
        }

        // Center button
        JPanel buttonWrapper = new JPanel();
        buttonWrapper.setOpaque(false);
        buttonWrapper.add(loginButton);

        card.add(buttonWrapper);

        // Add card to bg
        bg.add(card);

        setVisible(true);
    }

    private void updateLoginButton() {
        boolean filled =
                !usernameField.getText().trim().isEmpty() &&
                passwordField.getPassword().length > 0 &&
                (loginType.equals("admin") || facultyCombo.getSelectedItem() != null);

        if (filled) {
            loginButton.setBackground(new Color(42, 115, 50));
            loginButton.setEnabled(true);
        } else {
            loginButton.setBackground(new Color(180, 180, 180));
            loginButton.setEnabled(false);
        }
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        User user;
        if (loginType.equals("admin")) {
            user = controller.handleAdminLogin(username, password);
        } else {
            user = controller.handleUserLogin(username, password);
        }

        if (user != null) {
            JOptionPane.showMessageDialog(this, "Login berhasil!", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            new MainFrame(user);
        } else {
            JOptionPane.showMessageDialog(this, "Login gagal!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // -----------------------------
    // PANEL ROUNDED
    // -----------------------------
    class RoundedPanel extends JPanel {
        private int cornerRadius;

        public RoundedPanel(int radius) {
            this.cornerRadius = radius;
            setOpaque(false);
        }

        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
            g2.dispose();
        }
    }

    // -----------------------------
    // PLACEHOLDER TEXT FIELD
    // -----------------------------
    class ModernTextField extends JTextField {
        String placeholder;

        public ModernTextField(String placeholder) {
            this.placeholder = placeholder;
            setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
            setFont(new Font("Arial", Font.PLAIN, 14));
            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (getText().isEmpty()) {
                g.setColor(Color.DARK_GRAY);
                g.drawString(placeholder, 10, 28);
            }
        }
    }

    class ModernPasswordField extends JPasswordField {
        String placeholder;

        public ModernPasswordField(String placeholder) {
            this.placeholder = placeholder;
            setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
            setFont(new Font("Arial", Font.PLAIN, 14));
            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (getPassword().length == 0) {
                g.setColor(Color.DARK_GRAY);
                g.drawString(placeholder, 10, 28);
            }
        }
    }

    // Document Listener Helper
    abstract class SimpleDocListener implements javax.swing.event.DocumentListener {
        private Runnable changeCallback;
        public SimpleDocListener(Runnable callback) { this.changeCallback = callback; }
        public void changedUpdate(javax.swing.event.DocumentEvent e) { changeCallback.run(); }
        public void insertUpdate(javax.swing.event.DocumentEvent e) { changeCallback.run(); }
        public void removeUpdate(javax.swing.event.DocumentEvent e) { changeCallback.run(); }
    }
}
