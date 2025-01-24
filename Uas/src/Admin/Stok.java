package Admin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.text.NumberFormat;
import java.util.Locale;

import config.SQLite;

public class Stok extends JPanel {
    private JTable stokTable;
    private DefaultTableModel tableModel;

    public Stok() {
        setBackground(new Color(40, 44, 52));
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Stok Management");
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 30));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[] { "ID", "Kode Barang", "Nama Barang", "Harga", "Quantity" }, 0);
        stokTable = new JTable(tableModel);

        stokTable.setBackground(new Color(60, 63, 65));
        stokTable.setForeground(Color.WHITE);
        stokTable.setGridColor(new Color(100, 100, 100));
        stokTable.setFillsViewportHeight(true);

        stokTable.getTableHeader().setBackground(new Color(50, 50, 50));
        stokTable.getTableHeader().setForeground(Color.WHITE);
        stokTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));

        JScrollPane tableScrollPane = new JScrollPane(stokTable);
        tableScrollPane.getViewport().setBackground(new Color(60, 63, 65));
        tableScrollPane.setBorder(null);
        tableScrollPane.setBackground(new Color(60, 63, 65));

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(new Color(60, 63, 65));
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);

        add(tablePanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(40, 44, 52));
        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        JButton refreshButton = new JButton("Refresh");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);

        add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(this::handleAdd);
        updateButton.addActionListener(this::handleUpdate);
        deleteButton.addActionListener(this::handleDelete);
        refreshButton.addActionListener(_ -> loadData());

        loadData();
    }

    private void loadData() {
        tableModel.setRowCount(0);
        String query = "SELECT * FROM stokbarang";
        try (Connection conn = SQLite.getKoneksi();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String kodeBarang = rs.getString("kodebarang");
                String namaBarang = rs.getString("namabarang");
                int harga = rs.getInt("harga");
                int quantity = rs.getInt("quantity");

                String formattedHarga = NumberFormat.getCurrencyInstance(Locale.of("id", "ID")).format(harga);

                tableModel.addRow(new Object[] { id, kodeBarang, namaBarang, formattedHarga, quantity });
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load data.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleAdd(ActionEvent e) {
        JTextField kodeBarangField = new JTextField();
        JTextField namaBarangField = new JTextField();
        JTextField hargaField = new JTextField();
        JTextField quantityField = new JTextField();
        Object[] inputFields = {
                "Kode Barang:", kodeBarangField,
                "Nama Barang:", namaBarangField,
                "Harga:", hargaField,
                "Quantity:", quantityField
        };

        int option = JOptionPane.showConfirmDialog(this, inputFields, "Add Stok", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String kodeBarang = kodeBarangField.getText();
            String namaBarang = namaBarangField.getText();
            int harga, quantity;

            try {
                harga = Integer.parseInt(hargaField.getText());
                quantity = Integer.parseInt(quantityField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Harga dan Quantity harus berupa angka.", "Warning",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (kodeBarang.isEmpty() || namaBarang.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Semua field harus diisi.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String query = "INSERT INTO stokbarang (kodebarang, namabarang, harga, quantity) VALUES (?, ?, ?, ?)";
            Connection conn = null;
            PreparedStatement stmt = null;

            try {
                conn = SQLite.getKoneksi();
                stmt = conn.prepareStatement(query);

                stmt.setString(1, kodeBarang);
                stmt.setString(2, namaBarang);
                stmt.setInt(3, harga);
                stmt.setInt(4, quantity);

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Stok berhasil ditambahkan.");
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(this, "Gagal menambahkan stok.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Gagal menambahkan stok. Error: " + ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            } finally {
                try {
                    if (stmt != null) {
                        stmt.close();
                    }
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private void handleUpdate(ActionEvent e) {
        int selectedRow = stokTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih stok yang ingin diubah.", "Warning",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);
        String currentKodeBarang = (String) tableModel.getValueAt(selectedRow, 1);
        String currentNamaBarang = (String) tableModel.getValueAt(selectedRow, 2);

        String hargaStr = ((String) tableModel.getValueAt(selectedRow, 3))
                .split(",")[0]
                .replaceAll("[Rp.\\s]", "");
        int currentHarga = Integer.parseInt(hargaStr);

        int currentQuantity = (int) tableModel.getValueAt(selectedRow, 4);

        JTextField kodeBarangField = new JTextField(currentKodeBarang);
        JTextField namaBarangField = new JTextField(currentNamaBarang);
        JTextField hargaField = new JTextField(String.valueOf(currentHarga));
        JTextField quantityField = new JTextField(String.valueOf(currentQuantity));
        Object[] inputFields = {
                "Kode Barang:", kodeBarangField,
                "Nama Barang:", namaBarangField,
                "Harga:", hargaField,
                "Quantity:", quantityField
        };

        int option = JOptionPane.showConfirmDialog(this, inputFields, "Update Stok", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String newKodeBarang = kodeBarangField.getText();
            String newNamaBarang = namaBarangField.getText();
            int newHarga, newQuantity;

            try {
                newHarga = Integer.parseInt(hargaField.getText());
                newQuantity = Integer.parseInt(quantityField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Harga dan Quantity harus berupa angka.", "Warning",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            String query = "UPDATE stokbarang SET kodebarang = ?, namabarang = ?, harga = ?, quantity = ? WHERE id = ?";
            Connection conn = null;
            PreparedStatement stmt = null;

            try {
                conn = SQLite.getKoneksi();
                stmt = conn.prepareStatement(query);

                stmt.setString(1, newKodeBarang);
                stmt.setString(2, newNamaBarang);
                stmt.setInt(3, newHarga);
                stmt.setInt(4, newQuantity);
                stmt.setInt(5, id);

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Stok berhasil diperbarui.");
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(this, "Gagal memperbarui stok. Stok tidak ditemukan.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Gagal memperbarui stok. Error: " + ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            } finally {
                try {
                    if (stmt != null) {
                        stmt.close();
                    }
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private void handleDelete(ActionEvent e) {
        int selectedRow = stokTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih stok yang ingin dihapus.", "Warning",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);
        int option = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus stok ini?", "Konfirmasi Hapus",
                JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            String query = "DELETE FROM stokbarang WHERE id = ?";
            Connection conn = null;
            PreparedStatement stmt = null;

            try {
                conn = SQLite.getKoneksi();
                stmt = conn.prepareStatement(query);

                stmt.setInt(1, id);
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Stok berhasil dihapus.");
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(this, "Gagal menghapus stok. Stok tidak ditemukan.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Gagal menghapus stok. Error: " + ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            } finally {

                try {
                    if (stmt != null) {
                        stmt.close();
                    }
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

}
