package models;

import config.SQLite;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class InsetData {

    public static void main(String[] args) {
        int id = 2;
        String username = "User";
        String password = "123";
        String role = "USER";
        String createdAt = getCurrentTimestamp();

        String insertQuery = "INSERT INTO akun (id, username, password, role, created_at) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = SQLite.getKoneksi();
                PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {

            pstmt.setInt(1, id);
            pstmt.setString(2, username);
            pstmt.setString(3, password);
            pstmt.setString(4, role);
            pstmt.setString(5, createdAt);

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Data berhasil disimpan ke tabel akun");
            } else {
                System.out.println("Data gagal disimpan.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static String getCurrentTimestamp() {
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Jakarta"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }
}
