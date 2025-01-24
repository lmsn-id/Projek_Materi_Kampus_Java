package Pages;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONArray;
import javax.swing.table.DefaultTableModel;
import config.SQLite;

public class Struk extends JFrame {
    private String noTransaksi;
    private JSONArray barangArray;
    private JSONArray jumlahArray;
    private JSONArray hargaArray;
    private int totalHarga;
    private int pembayaran;
    private int kembalian;

    public Struk(String noTransaksi, DefaultTableModel tableModel, int totalHarga, int pembayaran, int kembalian) {
        this.noTransaksi = noTransaksi;
        this.totalHarga = totalHarga;
        this.pembayaran = pembayaran;
        this.kembalian = kembalian;

        barangArray = new JSONArray();
        jumlahArray = new JSONArray();
        hargaArray = new JSONArray();

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            barangArray.put(tableModel.getValueAt(i, 0));
            hargaArray.put(tableModel.getValueAt(i, 1));
            jumlahArray.put(tableModel.getValueAt(i, 2));
        }

        setTitle("Struk Pembelian");
        setSize(400, 600);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextArea receiptArea = new JTextArea();
        receiptArea.setEditable(false);
        receiptArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        receiptArea.append("=====================================\n");
        receiptArea.append("           STRUK PEMBELIAN          \n");
        receiptArea.append("=====================================\n\n");
        receiptArea.append("No. Transaksi: " + noTransaksi + "\n");
        receiptArea.append("=====================================\n");

        receiptArea.append("Detail Pesanan:\n");
        receiptArea.append("-------------------------------------\n");

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            receiptArea.append(String.format("%-20s %3sx %8s\n",
                    truncateString(tableModel.getValueAt(i, 0).toString(), 20),
                    tableModel.getValueAt(i, 2),
                    formatRupiah(Integer.parseInt(tableModel.getValueAt(i, 1).toString()))));
            receiptArea.append(String.format("%34s\n",
                    formatRupiah(Integer.parseInt(tableModel.getValueAt(i, 3).toString()))));
        }

        receiptArea.append("=====================================\n");
        receiptArea.append(String.format("Total     : %24s\n", formatRupiah(totalHarga)));
        receiptArea.append(String.format("Bayar     : %24s\n", formatRupiah(pembayaran)));
        receiptArea.append(String.format("Kembalian : %24s\n", formatRupiah(kembalian)));
        receiptArea.append("=====================================\n");
        receiptArea.append("          Terima Kasih             \n");
        receiptArea.append("=====================================\n");

        mainPanel.add(new JScrollPane(receiptArea), BorderLayout.CENTER);

        JButton confirmButton = new JButton("Konfirmasi Pesanan");
        confirmButton.addActionListener(_ -> {
            saveToDatabase();
            dispose();
            new User();
        });

        mainPanel.add(confirmButton, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    private String truncateString(String str, int length) {
        if (str.length() <= length)
            return str;
        return str.substring(0, length - 3) + "...";
    }

    private String formatRupiah(int amount) {
        return String.format("Rp%,d", amount).replace(",", ".");
    }

    private void saveToDatabase() {
        String sqlTransaksi = "INSERT INTO transaksi (notransaksi, nama, jumlah, harga) VALUES (?, ?, ?, ?)";
        String sqlUpdateStok = "UPDATE stokbarang SET quantity = quantity - ? WHERE namabarang = ?";

        try (Connection conn = SQLite.getKoneksi();
                PreparedStatement pstmtTransaksi = conn.prepareStatement(sqlTransaksi);
                PreparedStatement pstmtUpdateStok = conn.prepareStatement(sqlUpdateStok)) {

            pstmtTransaksi.setString(1, noTransaksi);
            pstmtTransaksi.setString(2, barangArray.toString());
            pstmtTransaksi.setString(3, jumlahArray.toString());

            JSONArray totalHargaArray = new JSONArray();
            for (int i = 0; i < barangArray.length(); i++) {
                int hargaSatuan = hargaArray.getInt(i);
                int jumlah = jumlahArray.getInt(i);
                int totalHargaItem = hargaSatuan * jumlah;
                totalHargaArray.put(totalHargaItem);
            }
            pstmtTransaksi.setString(4, totalHargaArray.toString());
            pstmtTransaksi.executeUpdate();

            for (int i = 0; i < barangArray.length(); i++) {
                String namaBarang = barangArray.getString(i);
                int jumlah = jumlahArray.getInt(i);

                String checkStockSql = "SELECT quantity FROM stokbarang WHERE namabarang = ?";
                try (PreparedStatement pstmtCheckStock = conn.prepareStatement(checkStockSql)) {
                    pstmtCheckStock.setString(1, namaBarang);
                    ResultSet rs = pstmtCheckStock.executeQuery();
                    if (rs.next()) {
                        int currentStock = rs.getInt("quantity");
                        if (currentStock >= jumlah) {
                            pstmtUpdateStok.setInt(1, jumlah);
                            pstmtUpdateStok.setString(2, namaBarang);
                            int affectedRows = pstmtUpdateStok.executeUpdate();
                            if (affectedRows > 0) {
                                System.out.println("Stok untuk " + namaBarang
                                        + " berhasil diperbarui. Jumlah yang dikurangi: " + jumlah);
                            } else {
                                System.out.println("Gagal memperbarui stok untuk " + namaBarang);
                            }
                        } else {
                            JOptionPane.showMessageDialog(this,
                                    "Stok tidak cukup untuk " + namaBarang,
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    } else {
                        System.out.println("Kode barang " + namaBarang + " tidak ditemukan di stok.");
                    }
                }
            }

            JOptionPane.showMessageDialog(this, "Pesanan berhasil disimpan!");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Error menyimpan transaksi: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}