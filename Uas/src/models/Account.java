package models;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import config.SQLite;

public class Account {
    public static void createTable() {
        String createTableSQL = """
                    CREATE TABLE IF NOT EXISTS akun (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        username VARCHAR(50) NOT NULL UNIQUE,
                        password VARCHAR(255) NOT NULL,
                        role TEXT NOT NULL DEFAULT 'USER' CHECK (role IN ('ADMIN', 'USER')),
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                    );
                """;

        try (Connection conn = SQLite.getKoneksi();
                Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
            System.out.println("Tabel 'akun' berhasil dibuat (jika belum ada).");

            // createDefaultAdminAccount(conn);
        } catch (SQLException e) {
            System.err.println("Error saat membuat tabel 'akun': " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        createTable();
    }
}
