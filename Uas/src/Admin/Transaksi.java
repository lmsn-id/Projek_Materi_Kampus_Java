package Admin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import config.SQLite;

public class Transaksi extends JPanel {
    private JTable transaksiTable;
    private DefaultTableModel tableModel;

    public Transaksi() {
        setBackground(new Color(40, 44, 52));
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Transaction Management");
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 30));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[] { "No. Transaksi", "Nama", "Jumlah", "Harga", "Tanggal" }, 0);
        transaksiTable = new JTable(tableModel);
        transaksiTable.setBackground(new Color(60, 63, 65));
        transaksiTable.setForeground(Color.WHITE);
        transaksiTable.setGridColor(new Color(100, 100, 100));
        transaksiTable.setFillsViewportHeight(true);

        transaksiTable.getTableHeader().setBackground(new Color(50, 50, 50));
        transaksiTable.getTableHeader().setForeground(Color.WHITE);
        transaksiTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));

        JScrollPane tableScrollPane = new JScrollPane(transaksiTable);
        tableScrollPane.getViewport().setBackground(new Color(60, 63, 65));
        tableScrollPane.setBorder(null);
        tableScrollPane.setBackground(new Color(60, 63, 65));

        JPanel transaksiPanel = new JPanel(new BorderLayout());
        transaksiPanel.setBackground(new Color(60, 63, 65));
        transaksiPanel.add(tableScrollPane, BorderLayout.CENTER);

        add(transaksiPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(40, 44, 52));
        JButton refreshButton = new JButton("Refresh");

        buttonPanel.add(refreshButton);
        add(buttonPanel, BorderLayout.SOUTH);

        loadTransaksiData();

        refreshButton.addActionListener(this::handleRefresh);
    }

    private void loadTransaksiData() {
        tableModel.setRowCount(0); // Clear existing data
        String query = "SELECT * FROM transaksi"; // Adjust the query as needed
        try (Connection conn = SQLite.getKoneksi();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String noTransaksi = rs.getString("notransaksi");
                String nama = rs.getString("nama");
                String jumlah = rs.getString("jumlah");
                String harga = rs.getString("harga");
                String tanggal = rs.getString("created_at"); // Assuming this is the column name for the date

                tableModel.addRow(new Object[] { noTransaksi, nama, jumlah, harga, tanggal });
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load data.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleRefresh(ActionEvent e) {
        loadTransaksiData();
    }
}