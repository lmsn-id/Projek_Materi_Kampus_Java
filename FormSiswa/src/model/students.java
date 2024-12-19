package model;

import java.sql.*;
import javax.swing.*;
import config.koneksi;

public class students {

    public static void migrasiData() {
        String createTabelSQL = """
                CREATE TABLE IF NOT EXISTS students (
                    nis int NOT NULL PRIMARY KEY,
                    nama varchar(100) NOT NULL,
                    jurusan varchar(100) NOT NULL,
                    jk varchar(16) NOT NULL,
                    alamat text NOT NULL
                );

                                    """;

        try (Connection conn = koneksi.getKoneksi();
                Statement stmt = conn.createStatement()) {
            stmt.execute(createTabelSQL);
            JOptionPane.showMessageDialog(null, "Tabel Students berhasil dibuat atau sudah ada.");
        } catch (SQLException err) {
            JOptionPane.showMessageDialog(null, "Error saat migrasi: " + err.getMessage());
        }
    }

    public static void main(String[] args) {
        migrasiData();
    }
}
