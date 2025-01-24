package models;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import config.SQLite;

public class Transaksi {
    public static void createTable() {
        String createTableSQL = """
                    CREATE TABLE IF NOT EXISTS transaksi (
                        notransaksi TEXT PRIMARY KEY,
                        nama TEXT NOT NULL,
                        jumlah TEXT NOT NULL,
                        harga TEXT NOT NULL,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                    );
                """;

        try (Connection conn = SQLite.getKoneksi();
                Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
            System.out.println("Tabel 'transaksi' berhasil dibuat (jika belum ada).");
        } catch (SQLException e) {
            System.err.println("Error saat membuat tabel 'transaksi': " + e.getMessage());
        }
    }

    public static void dropTable() {
        String dropTableSQL = "DROP TABLE IF EXISTS transaksi;";

        try (Connection conn = SQLite.getKoneksi();
                Statement stmt = conn.createStatement()) {
            stmt.execute(dropTableSQL);
            System.out.println("Tabel 'transaksi' berhasil dihapus (jika ada).");
        } catch (SQLException e) {
            System.err.println("Error saat menghapus tabel 'transaksi': " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        createTable();
        // dropTable();
    }
}