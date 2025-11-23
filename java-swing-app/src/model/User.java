package model;

/**
 * Model class untuk User (Mahasiswa)
 * Represents: users table in database
 */
public class User {
    private int userId;
    private String username;
    private String password;
    private String faculty; // FIK, FK, FH, FEB, FISIP
    private String role; // "user", "admin", "guest"
    
    // Constructors
    public User() {
        this.role = "user";
    }
    
    public User(int userId, String username, String faculty) {
        this.userId = userId;
        this.username = username;
        this.faculty = faculty;
        this.role = "user";
    }
    
    // Static factory method untuk Guest mode
    public static User createGuest() {
        User guest = new User();
        guest.setUserId(0);
        guest.setUsername("Guest");
        guest.setFaculty(null);
        guest.setRole("guest");
        return guest;
    }
    
    // Getters and Setters
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getFaculty() {
        return faculty;
    }
    
    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }
    
    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    // Helper methods
    public boolean isGuest() {
        return "guest".equals(role);
    }
    
    public boolean isAdmin() {
        return "admin".equals(role);
    }
    
    public boolean isUser() {
        return "user".equals(role);
    }
    
    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", faculty='" + faculty + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
