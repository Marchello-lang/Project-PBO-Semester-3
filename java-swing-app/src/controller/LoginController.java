package controller;

import dao.UserDAO;
import dao.AdminDAO;
import model.User;
import model.Admin;

/**
 * Controller untuk handle Login logic
 */
public class LoginController {
    
    private UserDAO userDAO = new UserDAO();
    private AdminDAO adminDAO = new AdminDAO();
    
    /**
     * Handle login user
     * @return User object jika success, null jika gagal
     */
    public User handleUserLogin(String username, String password) {
        try {
            User user = userDAO.login(username, password);
            
            if (user != null) {
                System.out.println("✓ Login berhasil: " + user.getUsername() + " (" + user.getFaculty() + ")");
                return user;
            } else {
                System.out.println("✗ Login gagal: Username atau password salah");
                return null;
            }
            
        } catch (Exception e) {
            System.err.println("✗ Error saat login: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Handle login admin
     * @return User object dengan role admin jika success, null jika gagal
     */
    public User handleAdminLogin(String username, String password) {
        try {
            Admin admin = adminDAO.login(username, password);
            
            if (admin != null) {
                User userAdmin = admin.toUser();
                System.out.println("✓ Login Admin berhasil: " + userAdmin.getUsername());
                return userAdmin;
            } else {
                System.out.println("✗ Login Admin gagal: Username atau password salah");
                return null;
            }
            
        } catch (Exception e) {
            System.err.println("✗ Error saat login admin: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Register user baru
     * @return true jika success, false jika gagal
     */
    public boolean registerUser(String username, String password, String faculty) {
        try {
            // Validasi input
            if (username == null || username.trim().isEmpty()) {
                System.out.println("✗ Username tidak boleh kosong");
                return false;
            }
            
            if (password == null || password.trim().isEmpty()) {
                System.out.println("✗ Password tidak boleh kosong");
                return false;
            }
            
            // Check username exists
            if (userDAO.isUsernameExists(username)) {
                System.out.println("✗ Username sudah digunakan");
                return false;
            }
            
            // Register
            boolean success = userDAO.register(username, password, faculty);
            
            if (success) {
                System.out.println("✓ Registrasi berhasil: " + username);
            } else {
                System.out.println("✗ Registrasi gagal");
            }
            
            return success;
            
        } catch (Exception e) {
            System.err.println("✗ Error saat registrasi: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
