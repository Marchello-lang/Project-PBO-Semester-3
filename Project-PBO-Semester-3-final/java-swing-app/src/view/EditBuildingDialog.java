package view;

import controller.AdminController;
import model.Building;

import javax.swing.*;
import java.awt.*;

/**
 * Dialog modal untuk edit gedung
 */
public class EditBuildingDialog extends JDialog { // <-- Perubahan nama class

    private AdminController controller;
    private Building building;

    private JTextArea descArea;
    private JTextArea hoursArea;
    private JTextArea facilitiesArea;

    public EditBuildingDialog(JFrame parent, AdminController controller, Building building) {
        super(parent, "Edit Gedung: " + building.getBuildingName(), true); // modal
        this.controller = controller;
        this.building = building;

        setSize(650, 520);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        // ===== Deskripsi =====
        JLabel descLabel = new JLabel("Deskripsi:");
        descLabel.setFont(new Font("Arial", Font.BOLD, 14));
        formPanel.add(descLabel);
        formPanel.add(Box.createVerticalStrut(5));

        descArea = new JTextArea(building.getDescription());
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        descArea.setFont(new Font("Arial", Font.PLAIN, 14));

        JScrollPane descScroll = new JScrollPane(descArea);
        descScroll.setPreferredSize(new Dimension(600, 80));
        descScroll.getViewport().setBackground(Color.WHITE);
        formPanel.add(descScroll);
        formPanel.add(Box.createVerticalStrut(20));

        // ===== Jam Operasional =====
        JLabel hoursLabel = new JLabel("Jam Operasional:");
        hoursLabel.setFont(new Font("Arial", Font.BOLD, 14));
        formPanel.add(hoursLabel);
        formPanel.add(Box.createVerticalStrut(5));

        hoursArea = new JTextArea(building.getHours());
        hoursArea.setLineWrap(true);
        hoursArea.setWrapStyleWord(true);
        hoursArea.setFont(new Font("Arial", Font.PLAIN, 14));

        JScrollPane hoursScroll = new JScrollPane(hoursArea);
        hoursScroll.setPreferredSize(new Dimension(600, 80));
        hoursScroll.getViewport().setBackground(Color.WHITE);
        formPanel.add(hoursScroll);
        formPanel.add(Box.createVerticalStrut(20));

        // ===== Fasilitas =====
        JLabel facLabel = new JLabel("Fasilitas gedung:");
        facLabel.setFont(new Font("Arial", Font.BOLD, 14));
        formPanel.add(facLabel);
        formPanel.add(Box.createVerticalStrut(5));

        facilitiesArea = new JTextArea(building.getFacilities());
        facilitiesArea.setLineWrap(true);
        facilitiesArea.setWrapStyleWord(true);
        facilitiesArea.setFont(new Font("Arial", Font.PLAIN, 14));

        JScrollPane facilitiesScroll = new JScrollPane(facilitiesArea);
        facilitiesScroll.setPreferredSize(new Dimension(600, 80));
        facilitiesScroll.getViewport().setBackground(Color.WHITE);
        formPanel.add(facilitiesScroll);
        formPanel.add(Box.createVerticalStrut(25));

        // ===== Tombol Simpan =====
        JButton saveButton = new JButton("Simpan");
        saveButton.setFont(new Font("Arial", Font.BOLD, 14));
        saveButton.setPreferredSize(new Dimension(110, 35));
        saveButton.setFocusPainted(false);

        saveButton.addActionListener(e -> saveBuilding());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(saveButton);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void saveBuilding() {
        // Update model building dengan data dari form
        building.setDescription(descArea.getText());
        building.setHours(hoursArea.getText());
        building.setFacilities(facilitiesArea.getText());

        // Panggil controller untuk menyimpan ke database
        boolean success = controller.updateBuilding(building);

        if (success) {
            JOptionPane.showMessageDialog(this, "Data gedung berhasil diperbarui!");

            // Opsional: Refresh BuildingDetailDialog jika ini dipanggil dari sana
            if (this.getParent() instanceof BuildingDetailDialog) {
                ((BuildingDetailDialog) this.getParent()).refreshDataAndUI();
            }

            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat menyimpan!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}