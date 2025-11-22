import view.SplashScreen;
import javax.swing.*;

/**
 * Main Entry Point untuk Campus Map Application
 * Pattern: MVC (Model-View-Controller)
 * 
 * @author Campus Map Team
 * @version 1.0
 */
public class Main {
    
    public static void main(String[] args) {
        // Set Look and Feel ke system default
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Error setting Look and Feel: " + e.getMessage());
        }
        
        // Run aplikasi di Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            System.out.println("===========================================");
            System.out.println("  CAMPUS MAP APPLICATION - STARTING...");
            System.out.println("===========================================");
            
            // Show splash screen
            new SplashScreen();
        });
    }
}
