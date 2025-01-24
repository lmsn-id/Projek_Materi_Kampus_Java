package Pages;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import config.SQLite;
import java.util.HashMap;

public class User extends JFrame {
    private DefaultTableModel tableModel;
    private JTable orderTable;
    private HashMap<String, Integer> barangHargaMap = new HashMap<>();
    private HashMap<String, Integer> barangStokMap = new HashMap<>();

    public User() {
        setTitle("Form Transaksi");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set a custom font for better readability
        Font defaultFont = new Font("Arial", Font.PLAIN, 12);
        UIManager.put("Label.font", defaultFont);
        UIManager.put("TextField.font", defaultFont);
        UIManager.put("Button.font", defaultFont);
        UIManager.put("ComboBox.font", defaultFont);
        UIManager.put("Table.font", defaultFont);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(mainPanel);

        JLabel titleLabel = new JLabel("Transaksi Penjualan", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(51, 51, 51));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10),
                BorderFactory.createLineBorder(new Color(200, 200, 200))));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Form components with improved styling
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel noTransaksiLabel = new JLabel("No Transaksi:");
        noTransaksiLabel.setFont(defaultFont);
        formPanel.add(noTransaksiLabel, gbc);

        gbc.gridx = 1;
        JTextField noTransaksiField = new JTextField(15);
        noTransaksiField.setEditable(false);
        noTransaksiField.setBackground(new Color(240, 240, 240));
        formPanel.add(noTransaksiField, gbc);

        setNoTransaksi(noTransaksiField);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Barang:"), gbc);
        gbc.gridx = 1;
        JComboBox<String> barangComboBox = new JComboBox<>();
        barangComboBox.setPreferredSize(new Dimension(200, 25));
        formPanel.add(barangComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Harga:"), gbc);
        gbc.gridx = 1;
        JTextField hargaField = new JTextField(15);
        hargaField.setEditable(false);
        hargaField.setBackground(new Color(240, 240, 240));
        formPanel.add(hargaField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Jumlah:"), gbc);
        gbc.gridx = 1;
        JTextField jumlahField = new JTextField(15);
        formPanel.add(jumlahField, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        JButton addOrderButton = new JButton("Tambahkan Pesanan");
        JButton deleteButton = new JButton("Hapus Data");

        // Style the buttons
        addOrderButton.setBackground(new Color(70, 130, 180));
        addOrderButton.setForeground(Color.WHITE);
        addOrderButton.setFocusPainted(false);

        deleteButton.setBackground(new Color(220, 53, 69));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);

        buttonPanel.add(addOrderButton);
        buttonPanel.add(deleteButton);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(buttonPanel, gbc);

        mainPanel.add(formPanel, BorderLayout.WEST);

        // Table setup with improved styling
        String[] columnNames = { "Barang", "Harga", "Jumlah", "Subtotal" };
        tableModel = new DefaultTableModel(columnNames, 0);
        orderTable = new JTable(tableModel);
        orderTable.setRowHeight(25);
        orderTable.setShowGrid(true);
        orderTable.setGridColor(new Color(200, 200, 200));

        JScrollPane tableScrollPane = new JScrollPane(orderTable);
        tableScrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        mainPanel.add(tableScrollPane, BorderLayout.CENTER);

        // Payment panel with improved styling
        JPanel paymentPanel = new JPanel(new GridBagLayout());
        paymentPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10),
                BorderFactory.createLineBorder(new Color(200, 200, 200))));
        GridBagConstraints paymentGbc = new GridBagConstraints();
        paymentGbc.anchor = GridBagConstraints.EAST;
        paymentGbc.insets = new Insets(5, 5, 5, 5);

        paymentGbc.gridx = 0;
        paymentGbc.gridy = 0;
        paymentPanel.add(new JLabel("Total Harga:"), paymentGbc);
        paymentGbc.gridx = 1;
        JTextField totalHargaField = new JTextField(15);
        totalHargaField.setEditable(false);
        totalHargaField.setBackground(new Color(240, 240, 240));
        paymentPanel.add(totalHargaField, paymentGbc);

        paymentGbc.gridx = 0;
        paymentGbc.gridy = 1;
        paymentPanel.add(new JLabel("Uang Pembayaran:"), paymentGbc);
        paymentGbc.gridx = 1;
        JTextField pembayaranField = new JTextField(15);
        paymentPanel.add(pembayaranField, paymentGbc);

        paymentGbc.gridx = 0;
        paymentGbc.gridy = 2;
        paymentPanel.add(new JLabel("Kembalian:"), paymentGbc);
        paymentGbc.gridx = 1;
        JTextField kembalianField = new JTextField(15);
        kembalianField.setEditable(false);
        kembalianField.setBackground(new Color(240, 240, 240));
        paymentPanel.add(kembalianField, paymentGbc);

        pembayaranField.addActionListener(_ -> {
            try {
                int totalHarga = Integer.parseInt(totalHargaField.getText());
                int pembayaran = Integer.parseInt(pembayaranField.getText());
                int kembalian = pembayaran - totalHarga;

                if (kembalian < 0) {
                    JOptionPane.showMessageDialog(null, "Pembayaran kurang!", "Error", JOptionPane.ERROR_MESSAGE);
                    kembalianField.setText("");
                    return;
                }

                kembalianField.setText(String.valueOf(kembalian));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Masukkan jumlah pembayaran yang valid!", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        paymentGbc.gridx = 2;
        paymentGbc.gridy = 0;
        paymentGbc.gridheight = 3;
        JButton saveOrderButton = new JButton("Simpan Pesanan");
        saveOrderButton.setBackground(new Color(40, 167, 69));
        saveOrderButton.setForeground(Color.WHITE);
        saveOrderButton.setFocusPainted(false);

        saveOrderButton.addActionListener(_ -> {
            if (pembayaranField.getText().isEmpty() || kembalianField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Masukkan pembayaran terlebih dahulu!", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            int totalHarga = Integer.parseInt(totalHargaField.getText());
            int pembayaran = Integer.parseInt(pembayaranField.getText());
            int kembalian = Integer.parseInt(kembalianField.getText());

            String noTransaksi = noTransaksiField.getText();

            new Struk(noTransaksi, tableModel, totalHarga, pembayaran, kembalian);
            dispose();
        });

        paymentPanel.add(saveOrderButton, paymentGbc);
        mainPanel.add(paymentPanel, BorderLayout.SOUTH);

        loadBarangData(barangComboBox);

        barangComboBox.addActionListener(_ -> {
            String selectedBarang = (String) barangComboBox.getSelectedItem();
            if (selectedBarang != null && barangHargaMap.containsKey(selectedBarang)) {
                hargaField.setText(String.valueOf(barangHargaMap.get(selectedBarang)));
            }
        });

        addOrderButton.addActionListener(_ -> {
            String barang = (String) barangComboBox.getSelectedItem();
            String jumlahText = jumlahField.getText();

            if (barang == null || jumlahText.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Lengkapi semua input!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int jumlah = Integer.parseInt(jumlahText);
                int stokTersedia = barangStokMap.getOrDefault(barang, 0);

                if (jumlah > stokTersedia) {
                    JOptionPane.showMessageDialog(null, "Stok tidak mencukupi! Sisa stok: " + stokTersedia, "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int harga = barangHargaMap.get(barang);
                int subtotal = harga * jumlah;

                tableModel.addRow(new Object[] { barang, harga, jumlah, subtotal });

                int total = 0;
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    total += (int) tableModel.getValueAt(i, 3);
                }
                totalHargaField.setText(String.valueOf(total));

                jumlahField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Jumlah harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        deleteButton.addActionListener(_ -> {
            int selectedRow = orderTable.getSelectedRow();
            if (selectedRow != -1) {
                tableModel.removeRow(selectedRow);

                int total = 0;
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    total += (int) tableModel.getValueAt(i, 3);
                }
                totalHargaField.setText(String.valueOf(total));
            } else {
                JOptionPane.showMessageDialog(null, "Pilih baris yang akan dihapus!", "Peringatan",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        setVisible(true);
    }

    private void setNoTransaksi(JTextField noTransaksiField) {
        String query = "SELECT MAX(CAST(SUBSTR(notransaksi, 6) AS INTEGER)) FROM transaksi";
        try (Connection conn = SQLite.getKoneksi();
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                Integer lastNoTransaksi = rs.getInt(1);
                String noTransaksi;

                if (lastNoTransaksi == null || lastNoTransaksi == 0) {
                    noTransaksi = "Nota-1";
                } else {
                    noTransaksi = "Nota-" + (lastNoTransaksi + 1);
                }

                noTransaksiField.setText(noTransaksi);
            } else {
                noTransaksiField.setText("Nota-1");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error saat mengambil nomor transaksi: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadBarangData(JComboBox<String> barangComboBox) {
        String query = "SELECT kodebarang, namabarang, harga, quantity FROM stokbarang";
        try (Connection conn = SQLite.getKoneksi();
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String namaBarang = rs.getString("namabarang");
                int harga = rs.getInt("harga");
                int stok = rs.getInt("quantity");

                barangComboBox.addItem(namaBarang);
                barangHargaMap.put(namaBarang, harga);
                barangStokMap.put(namaBarang, stok);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal memuat data barang: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // public static void main(String[] args) {
    // SwingUtilities.invokeLater(User::new);
    // }
}
