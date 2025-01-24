package config;

import java.sql.*;
import javax.swing.JOptionPane;

public class SQLite {
    private static Connection Koneksi;

    public static Connection getKoneksi() {
        String dbPath = "jdbc:sqlite:db.sqlite3";

        try {
            if (Koneksi == null || Koneksi.isClosed()) {
                Koneksi = DriverManager.getConnection(dbPath);
                // JOptionPane.showMessageDialog(null, "Koneksi ke SQLite Berhasil");
                System.out.println("Koneksi ke SQLite Berhasil");
            }
        } catch (SQLException err) {
            JOptionPane.showMessageDialog(null, "Error: " + err.getMessage());
        }

        return Koneksi;
    }

    public static void main(String[] args) {
        getKoneksi();
    }
}