package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SplashScreen extends JFrame {

    private float alpha = 0.0f;       // transparansi logo
    private boolean fadingIn = true;  // status animasi

    public SplashScreen() {

        // Panel background
        JPanel content = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
            }
        };
        content.setBackground(Color.WHITE);
        content.setLayout(new GridBagLayout()); // biar logo otomatis center
        setContentPane(content);

        // Load logo
        ImageIcon icon = new ImageIcon(getClass().getResource("/assets/UPN.PNG"));
        Image scaled = icon.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);

        // Custom JLabel untuk menggambar logo dengan alpha (transparansi)
        JLabel fadeLogo = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
                g2.drawImage(scaled, 0, 0, getWidth(), getHeight(), null);
                g2.dispose();
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(180, 180);
            }
        };

        content.add(fadeLogo);

        // Animasi fade
        Timer fadeTimer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fadingIn) {
                    alpha += 0.05f;
                    if (alpha >= 1f) {
                        alpha = 1f;
                        fadingIn = false;
                    }
                } else {
                    alpha -= 0.05f;
                    if (alpha <= 0f) {
                        alpha = 0f;
                        fadingIn = true;
                    }
                }
                fadeLogo.repaint();
            }
        });

        fadeTimer.start();

        // Splash screen window configs
        setSize(1280, 832);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);

        // Auto close splash
        Timer closeTimer = new Timer(2000, e -> {
            fadeTimer.stop();
            dispose();
            new LoginSelectionFrame();
        });
        closeTimer.setRepeats(false);
        closeTimer.start();
    }
}
