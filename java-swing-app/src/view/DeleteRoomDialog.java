package view;

import controller.AdminController;
import model.Room;

import javax.swing.*;
import java.awt.*;

/**
 * Dialog konfirmasi untuk menghapus ruangan.
 */
public class DeleteRoomDialog extends JDialog {

    private final AdminController adminController;
    private final Room roomToDelete;
    private final BuildingDetailDialog parentDialog;

    public DeleteRoomDialog(BuildingDetailDialog parent, AdminController adminController, Room roomToDelete) {
        super(parent, "Konfirmasi Hapus Ruangan", true);
        this.parentDialog = parent;
        this.adminController = adminController;
        this.roomToDelete = roomToDelete;

        setSize(350, 150);
        setLocationRelativeTo(parentDialog);
        setLayout(new BorderLayout());

        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        // Message Panel
        JPanel messagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel messageLabel = new JLabel("Apakah Anda yakin ingin menghapus ruangan:");
        JLabel roomNameLabel = new JLabel("**" + roomToDelete.getRoomName() + "**?");
        roomNameLabel.setFont(new Font("Arial", Font.BOLD, 14));

        messagePanel.add(messageLabel);
        messagePanel.add(roomNameLabel);
        messagePanel.setBorder(BorderFactory.createEmptyBorder(15, 10, 5, 10));

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));

        JButton deleteButton = new JButton("Ya, Hapus");
        styleRedButton(deleteButton);
        deleteButton.addActionListener(e -> deleteRoom());

        JButton cancelButton = new JButton("Batal");
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(deleteButton);
        buttonPanel.add(cancelButton);

        add(messagePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Helper untuk style tombol merah (sama seperti di BuildingDetailDialog)
    private void styleRedButton(JButton b) {
        b.setFont(new Font("Arial", Font.BOLD, 14));
        b.setBackground(new Color(220,53,69));
        b.setForeground(Color.WHITE);
        b.setOpaque(true);
        b.setContentAreaFilled(true);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
    }

    private void deleteRoom() {
        // Panggil controller untuk delete
        boolean success = adminController.deleteRoom(roomToDelete.getRoomId());

        if (success) {
            JOptionPane.showMessageDialog(this,
                    "Ruangan **" + roomToDelete.getRoomName() + "** berhasil dihapus.",
                    "Sukses", JOptionPane.INFORMATION_MESSAGE);

            // Refresh tampilan BuildingDetailDialog setelah berhasil
            parentDialog.refreshDataAndUI();
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Gagal menghapus ruangan. Cek log.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}