package koneksi;

import java.sql.*;
import javax.swing.JOptionPane;

public class sqlite {
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
}
