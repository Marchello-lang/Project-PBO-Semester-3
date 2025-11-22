package view;

import controller.LoginController;
import model.User;

import javax.swing.*;
import java.awt.*;

/**
 * Login Frame - Form login untuk User atau Admin
 */
public class LoginFrame extends JFrame {
    
    private LoginController controller = new LoginController();
    private String loginType; // "user" atau "admin"
    
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> facultyComboBox;
    
    public LoginFrame(String loginType) {
        this.loginType = loginType;
        
        String title = loginType.equals("admin") ? "Login Admin" : "Login Mahasiswa";
        setTitle("Campus Map - " + title);
        setSize(450, loginType.equals("admin") ? 350 : 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(42, 115, 50));
        headerPanel.setPreferredSize(new Dimension(450, 80));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        
        // Form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        
        // Username
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        usernameField = new JTextField();
        usernameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        
        // Password
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        passwordField = new JPasswordField();
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        
        // Faculty (hanya untuk user)
        JLabel facultyLabel = null;
        if (loginType.equals("user")) {
            facultyLabel = new JLabel("Fakultas:");
            facultyLabel.setFont(new Font("Arial", Font.BOLD, 14));
            
            String[] faculties = {"FIK", "FK", "FH", "FEB", "FISIP"};
            facultyComboBox = new JComboBox<>(faculties);
            facultyComboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
            facultyComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        }
        
        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(Color.WHITE);
        
        JButton loginButton = new JButton("LOGIN");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBackground(new Color(42, 115, 50));
        loginButton.setForeground(Color.WHITE);
        loginButton.setPreferredSize(new Dimension(120, 40));
        loginButton.setFocusPainted(false);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.addActionListener(e -> handleLogin());
        
        JButton backButton = new JButton("KEMBALI");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setBackground(Color.GRAY);
        backButton.setForeground(Color.WHITE);
        backButton.setPreferredSize(new Dimension(120, 40));
        backButton.setFocusPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(e -> {
            dispose();
            new LoginSelectionFrame();
        });
        
        buttonPanel.add(loginButton);
        buttonPanel.add(backButton);
        
        // Add components to form
        formPanel.add(usernameLabel);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(usernameField);
        formPanel.add(Box.createVerticalStrut(15));
        
        formPanel.add(passwordLabel);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(passwordField);
        formPanel.add(Box.createVerticalStrut(15));
        
        if (loginType.equals("user")) {
            formPanel.add(facultyLabel);
            formPanel.add(Box.createVerticalStrut(5));
            formPanel.add(facultyComboBox);
            formPanel.add(Box.createVerticalStrut(20));
        } else {
            formPanel.add(Box.createVerticalStrut(20));
        }
        
        formPanel.add(buttonPanel);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        
        add(mainPanel);
        setVisible(true);
        
        // Enter key untuk login
        getRootPane().setDefaultButton(loginButton);
    }
    
    /**
     * Handle login action
     */
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Username dan password tidak boleh kosong!",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        User user = null;
        
        if (loginType.equals("admin")) {
            user = controller.handleAdminLogin(username, password);
        } else {
            user = controller.handleUserLogin(username, password);
        }
        
        if (user != null) {
            JOptionPane.showMessageDialog(this,
                "Login berhasil! Selamat datang, " + user.getUsername(),
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
            
            dispose();
            new MainFrame(user);
        } else {
            JOptionPane.showMessageDialog(this,
                "Login gagal! Username atau password salah.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            passwordField.setText("");
        }
    }
}
