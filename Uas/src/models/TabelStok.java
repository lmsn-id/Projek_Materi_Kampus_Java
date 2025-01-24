package models;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import config.SQLite;

public class TabelStok {
    public static void createTable() {
        String createTableSQL = """
                    CREATE TABLE IF NOT EXISTS stokbarang (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        kodebarang VARCHAR(10) NOT NULL UNIQUE,
                        namabarang VARCHAR(50) NOT NULL,
                        harga INTEGER NOT NULL,
                        quantity INTEGER NOT NULL,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                    );
                """;

        try (Connection conn = SQLite.getKoneksi();
                Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
            System.out.println("Tabel 'stokbarang' berhasil dibuat (jika belum ada).");

            // createDefaultAdminAccount(conn);
        } catch (SQLException e) {
            System.err.println("Error saat membuat tabel 'stokbarang': " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        createTable();
    }
}
