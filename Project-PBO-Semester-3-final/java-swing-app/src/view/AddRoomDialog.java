package view;

import controller.AdminController;
import model.Building;
import model.Room;

import javax.swing.*;
import java.awt.*;

/**
 * Dialog untuk menambah ruangan baru ke gedung.
 */
public class AddRoomDialog extends JDialog {

    private final AdminController adminController;
    private final Building building;
    private final BuildingDetailDialog parentDialog;

    private JTextField roomNameField;
    private JTextArea descriptionArea;
    private JButton saveButton;

    public AddRoomDialog(BuildingDetailDialog parent, AdminController adminController, Building building) {
        super(parent, "Tambah Ruangan Baru di " + building.getBuildingName(), true);
        this.parentDialog = parent;
        this.adminController = adminController;
        this.building = building;

        setSize(400, 350);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        initComponents();
        createLayout();
        addListeners();

        setVisible(true);
    }

    private void initComponents() {
        roomNameField = new JTextField(20);
        descriptionArea = new JTextArea(5, 20);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);

        saveButton = new JButton("Simpan");
        saveButton.setBackground(new Color(42, 115, 50)); // Warna hijau
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);
    }

    private void createLayout() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Label Nama Ruangan
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(new JLabel("Nama Ruangan:"), gbc);

        // Field Nama Ruangan
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        formPanel.add(roomNameField, gbc);

        // Label Deskripsi
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.weightx = 0;
        formPanel.add(new JLabel("Deskripsi:"), gbc);

        // Area Deskripsi
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        JScrollPane scrollPane = new JScrollPane(descriptionArea);
        formPanel.add(scrollPane, gbc);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(saveButton);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addListeners() {
        saveButton.addActionListener(e -> saveRoom());
    }

    private void saveRoom() {
        String roomName = roomNameField.getText().trim();
        String description = descriptionArea.getText().trim();

        if (roomName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama Ruangan tidak boleh kosong.",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 1. Buat objek Room
        Room newRoom = new Room();
        newRoom.setBuildingId(building.getBuildingId()); // Ambil ID Building dari objek yang di-pass
        newRoom.setRoomName(roomName);
        newRoom.setDescription(description);

        // 2. Panggil controller untuk menyimpan ke database
        boolean success = adminController.addRoom(newRoom);

        // 3. Tanggapan
        if (success) {
            JOptionPane.showMessageDialog(this,
                    "Ruangan **" + roomName + "** berhasil ditambahkan!",
                    "Sukses", JOptionPane.INFORMATION_MESSAGE);

            // Refresh tampilan BuildingDetailDialog setelah berhasil
            parentDialog.refreshDataAndUI();
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Gagal menambahkan ruangan. Cek log.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}