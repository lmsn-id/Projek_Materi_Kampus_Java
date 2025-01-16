package koneksi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class MySQL {
    private static Connection koneksi;

    public static Connection getKoneksi() {
        String host = "jdbc:mysql://localhost:3306/faktur";
        String user = "root";
        String pass = "";

        try {
            koneksi = DriverManager.getConnection(host, user, pass);
            // JOptionPane.showMessageDialog(null, "Koneksi Berhasil");
        } catch (SQLException err) {
            JOptionPane.showMessageDialog(null, err.getMessage());
        }

        return koneksi;
    }

    public static void main(String[] args) {
        getKoneksi();
    }
}
