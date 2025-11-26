package view;

import model.Building;
import util.ImagePathConfig;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SlideshowWindow extends JFrame {
    private JLabel imageLabel;
    private JLabel captionLabel;
    private JLabel descriptionLabel;
    private JLabel hoursLabel;
    private JLabel counterLabel;
    private List<String> imagePaths;
    private int currentIndex = 0;
    private Building building;
    private JButton prevBtn;
    private JButton nextBtn;

    public SlideshowWindow(Building building) {
        super("Foto Gedung");
        this.building = building;

        setTitle(building.getBuildingName());
        setSize(1024, 720);
        setMinimumSize(new Dimension(900, 600));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // === HEADER BAR ===
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(42, 155, 50));
        header.setBorder(BorderFactory.createEmptyBorder(18, 28, 12, 28));

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBackground(new Color(42, 155, 50));
        JLabel titleLabel = new JLabel(building.getBuildingName());
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 30));
        titleLabel.setForeground(Color.WHITE);

        hoursLabel = new JLabel("⏰ " + building.getHours());
        hoursLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        hoursLabel.setForeground(new Color(230, 255, 230));
        titlePanel.add(titleLabel);
        titlePanel.add(Box.createRigidArea(new Dimension(0, 4)));
        titlePanel.add(hoursLabel);

        // CLOSE BUTTON - MERAH SOLID
        JButton closeBtn = new JButton("✕");
        closeBtn.setFont(new Font("Arial", Font.BOLD, 28));
        closeBtn.setForeground(Color.WHITE);
        closeBtn.setBackground(new Color(223, 44, 44)); // #DF2C2C
        closeBtn.setOpaque(true);
        closeBtn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        closeBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeBtn.setFocusPainted(true);
        closeBtn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                closeBtn.setBackground(new Color(180, 31, 31)); // lebih gelap
            }
            public void mouseExited(MouseEvent e) {
                closeBtn.setBackground(new Color(223, 44, 44));
            }
        });
        closeBtn.addActionListener(e -> dispose());

        header.add(titlePanel, BorderLayout.WEST);
        header.add(closeBtn, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        // IMAGE CENTER
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(15, 24, 5, 24));
        imageLabel = new JLabel("", JLabel.CENTER);
        imageLabel.setPreferredSize(new Dimension(830, 390));
        imageLabel.setOpaque(true);
        imageLabel.setBackground(new Color(245, 247, 240));
        centerPanel.add(imageLabel, new GridBagConstraints());
        add(centerPanel, BorderLayout.CENTER);

        // BOTTOM PANEL
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(16, 24, 32, 24));

        captionLabel = new JLabel("", JLabel.CENTER);
        captionLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        captionLabel.setForeground(new Color(30, 90, 48));
        captionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        descriptionLabel = new JLabel("", JLabel.CENTER);
        descriptionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        descriptionLabel.setForeground(new Color(83, 83, 90));
        descriptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        bottomPanel.add(captionLabel);
        bottomPanel.add(Box.createRigidArea(new Dimension(0, 7)));
        bottomPanel.add(descriptionLabel);

        // NAVIGATION - HIJAU SOLID
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 38, 18));
        navPanel.setBackground(Color.WHITE);

        Color green = new Color(44, 173, 96); // #2CAD60
        prevBtn = createNavButton("◄ SEBELUMNYA", green);
        prevBtn.addActionListener(e -> previousImage());
        prevBtn.setEnabled(true); // Selalu aktif!

        counterLabel = new JLabel("1 / 1");
        counterLabel.setFont(new Font("Segoe UI", Font.BOLD, 21));
        counterLabel.setForeground(green);
        counterLabel.setBorder(BorderFactory.createEmptyBorder(0,14,0,14));

        nextBtn = createNavButton("SELANJUTNYA ►", green);
        nextBtn.addActionListener(e -> nextImage());
        nextBtn.setEnabled(true); // Selalu aktif!

        navPanel.add(prevBtn);
        navPanel.add(counterLabel);
        navPanel.add(nextBtn);

        bottomPanel.add(Box.createRigidArea(new Dimension(0, 18)));
        bottomPanel.add(navPanel);

        add(bottomPanel, BorderLayout.SOUTH);

        // Isi foto dan tampilkan foto pertama
        loadImages();
        displayCurrentImage();

        setVisible(true);
    }

    // TOMBOL NAVIGASI HIJAU SOLID, SELALU ENABLED
    private JButton createNavButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 17));
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(true);
        btn.setOpaque(true);
        btn.setBorder(BorderFactory.createEmptyBorder(12, 38, 12, 38));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(198, 48));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(bgColor.darker());
            }
            public void mouseExited(MouseEvent e) {
                btn.setBackground(bgColor);
            }
        });
        btn.setEnabled(true); // pastikan tombol enabled
        return btn;
    }

    private void loadImages() {
        imagePaths = new ArrayList<>();
        String folderPath = ImagePathConfig.getImageFolderPath(
                building.getBuildingId(),
                building.getBuildingName()
        );
        File folder = new File(folderPath);
        if (folder.exists() && folder.isDirectory()) {
            File[] files = ImagePathConfig.getImageFiles(
                    building.getBuildingId(),
                    building.getBuildingName()
            );
            if (files != null && files.length > 0) {
                java.util.Arrays.sort(files);
                for (File file : files) {
                    imagePaths.add(file.getAbsolutePath());
                }
            }
        }
        if (imagePaths.isEmpty()) {
            imagePaths.add("placeholder");
        }
    }

    private void displayCurrentImage() {
        if (imagePaths.isEmpty()) return;
        String imagePath = imagePaths.get(currentIndex);
        if (!imagePath.equals("placeholder")) {
            try {
                ImageIcon icon = new ImageIcon(imagePath);
                if (icon.getIconWidth() > 0) {
                    Image scaled = icon.getImage().getScaledInstance(800, 380, Image.SCALE_SMOOTH);
                    imageLabel.setIcon(new ImageIcon(scaled));
                    imageLabel.setText("");
                    File file = new File(imagePath);
                    String filename = file.getName();
                    String caption = filename.substring(0, filename.lastIndexOf('.'))
                            .replaceAll("^\\d+_", "")
                            .replaceAll("_", " ")
                            .trim();
                    captionLabel.setText(formatCaption(caption));
                } else {
                    showPlaceholder();
                }
            } catch (Exception e) {
                showPlaceholder();
            }
        } else {
            showPlaceholder();
        }
        String wrappedDesc = "<html>enter>" + building.getDescription() + "</center></html>";
        descriptionLabel.setText(wrappedDesc);
        counterLabel.setText((currentIndex + 1) + " / " + imagePaths.size());

        // PASTIKAN tombol navigasi SELALU ENABLED di setiap update view!
        prevBtn.setEnabled(true);
        nextBtn.setEnabled(true);
    }

    private void showPlaceholder() {
        imageLabel.setIcon(null);
        imageLabel.setText(
                "<html>enter>📷<br>Foto tidak tersedia<br><span style='font-size:13px'>Upload ke folder " +
                        ImagePathConfig.getFolderName(building.getBuildingId(), building.getBuildingName()) +
                        "</span></center></html>");
        captionLabel.setText("Tidak ada gambar");
    }

    private String formatCaption(String text) {
        if (text.isEmpty()) return "Fasilitas " + (currentIndex + 1);
        String[] words = text.split(" ");
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                result.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1).toLowerCase())
                        .append(" ");
            }
        }
        return result.toString().trim();
    }

    private void nextImage() {
        if (imagePaths.isEmpty()) return;
        currentIndex = (currentIndex + 1) % imagePaths.size();
        displayCurrentImage();
    }

    private void previousImage() {
        if (imagePaths.isEmpty()) return;
        currentIndex = (currentIndex - 1 + imagePaths.size()) % imagePaths.size();
        displayCurrentImage();
    }
}
