package view;

import controller.AdminController;
import model.Room;

import javax.swing.*;
import java.awt.*;

/**
 * Dialog untuk mengedit ruangan yang sudah ada. (Khusus Edit)
 */
public class EditRoomDialog extends JDialog {

    private final AdminController adminController;
    private final Room roomToEdit;
    private final BuildingDetailDialog parentDialog;

    private JTextField roomNameField;
    private JTextArea descriptionArea;
    private JButton saveButton;

    public EditRoomDialog(BuildingDetailDialog parent, AdminController adminController, Room roomToEdit) {
        super(parent, "Edit Ruangan: " + roomToEdit.getRoomName(), true);
        this.parentDialog = parent;
        this.adminController = adminController;
        this.roomToEdit = roomToEdit;

        setupDialog();
    }

    private void setupDialog() {
        setSize(400, 350);
        setLocationRelativeTo(parentDialog);
        setLayout(new BorderLayout());

        initComponents();
        populateFields(); // Isi data dari roomToEdit
        createLayout();
        addListeners();

        setVisible(true);
    }

    private void populateFields() {
        roomNameField.setText(roomToEdit.getRoomName());
        descriptionArea.setText(roomToEdit.getDescription());
        saveButton.setText("Simpan Perubahan");
    }

    private void initComponents() {
        roomNameField = new JTextField(20);
        descriptionArea = new JTextArea(5, 20);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);

        saveButton = new JButton();
        saveButton.setFont(new Font("Arial", Font.BOLD, 14));
        saveButton.setBackground(new Color(42, 115, 50));
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
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST; gbc.weightx = 0;
        formPanel.add(new JLabel("Nama Ruangan:"), gbc);

        // Field Nama Ruangan
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0;
        formPanel.add(roomNameField, gbc);

        // Label Deskripsi
        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.NORTHWEST; gbc.weightx = 0;
        formPanel.add(new JLabel("Deskripsi:"), gbc);

        // Area Deskripsi
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1.0; gbc.weighty = 1.0; gbc.fill = GridBagConstraints.BOTH;
        JScrollPane scrollPane = new JScrollPane(descriptionArea);
        formPanel.add(scrollPane, gbc);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton cancelButton = new JButton("Batal");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 14));
        cancelButton.setBackground(new Color(42, 115, 50));
        cancelButton.setForeground(Color.WHITE); // Teks Hitam
        cancelButton.setOpaque(true);
        cancelButton.setContentAreaFilled(true);
        cancelButton.setBorderPainted(false);
        cancelButton.setFocusPainted(false);
        cancelButton.addActionListener(e -> dispose());
        buttonPanel.add(cancelButton);
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

        // MODE EDIT
        roomToEdit.setRoomName(roomName);
        roomToEdit.setDescription(description);

        boolean success = adminController.updateRoom(roomToEdit);

        if (success) {
            JOptionPane.showMessageDialog(this,
                    "Perubahan pada ruangan **" + roomName + "** berhasil disimpan!",
                    "Sukses", JOptionPane.INFORMATION_MESSAGE);

            parentDialog.refreshDataAndUI();
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Gagal menyimpan perubahan ruangan. Cek log.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}