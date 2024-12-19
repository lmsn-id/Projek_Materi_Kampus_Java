package model;

import java.sql.*;
import javax.swing.JOptionPane;
import koneksi.sqlite;

public class migrate {

    public static void migrasi() {
        String createTableSQL = """
                    CREATE TABLE IF NOT EXISTS tbl_stok (
                        id_barang VARCHAR(15) NOT NULL PRIMARY KEY,
                        Nama_Barang VARCHAR(25) NOT NULL,
                        Stok_Barang VARCHAR(25) NOT NULL,
                        Harga DOUBLE NOT NULL
                    );
                    CREATE TABEL IF NOT EXISTS tbl_transaksi (
                        id_transaksi VARCHAR(15) NOT NULL PRIMARY KEY,
                        id_barang VARCHAR(15) NOT NULL,
                        Nama_Barang VARCHAR(25) NOT NULL,
                        Stok_Barang VARCHAR(25) NOT NULL,
                        Harga DOUBLE NOT NULL
                    )
                """;

        try (Connection conn = sqlite.getKoneksi();
                Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
            JOptionPane.showMessageDialog(null, "Tabel tbl_stok berhasil dibuat atau sudah ada.");
        } catch (SQLException err) {
            JOptionPane.showMessageDialog(null, "Error saat migrasi: " + err.getMessage());
        }
    }

    public static void main(String[] args) {
        migrasi();
    }
}
