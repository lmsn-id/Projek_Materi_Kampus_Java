package Koneksi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class Connector {
    private static Connection koneksi;

    public static Connection getKoneksi() {
        String dbPath = "jdbc:sqlite:database.sqlite3";

        try {
            if (koneksi == null || koneksi.isClosed()) {
                koneksi = DriverManager.getConnection(dbPath);
                JOptionPane.showMessageDialog(null, "Koneksi ke SQLite Berhasil");
            }
        } catch (SQLException err) {
            JOptionPane.showMessageDialog(null, "Error: " + err.getMessage());
        }

        return koneksi;
    }

    public static void main(String[] args) {
        Connection conn = Connector.getKoneksi();
        if (conn != null) {
            System.out.println("Koneksi berhasil diakses melalui metode main.");
        }
    }
}
