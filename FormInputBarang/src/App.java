import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import koneksi.sqlite;

public class App extends JFrame {

    private JTable tabel;
    private DefaultTableModel model;

    private JTextField txtKode, txtNama, txtStok, txtHarga;
    private JButton btnSimpan, btnUpdate, btnDelete;

    public App() {
        setTitle("Data Barang");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        model = new DefaultTableModel();
        tabel = new JTable(model);
        JPanel panelUtama = new JPanel(new BorderLayout());
        add(panelUtama);

        model.addColumn("Kode Barang");
        model.addColumn("Nama Barang");
        model.addColumn("Stok Barang");
        model.addColumn("Harga");

        loadData();

        JScrollPane scrollPane = new JScrollPane(tabel);
        panelUtama.add(scrollPane, BorderLayout.CENTER);

        JPanel panelInput = new JPanel(new GridLayout(6, 2, 5, 5));
        panelInput.setBorder(BorderFactory.createTitledBorder("Input Data"));

        panelInput.add(new JLabel("Kode Barang:"));
        txtKode = new JTextField();
        panelInput.add(txtKode);

        panelInput.add(new JLabel("Nama Barang:"));
        txtNama = new JTextField();
        panelInput.add(txtNama);

        panelInput.add(new JLabel("Stok Barang:"));
        txtStok = new JTextField();
        panelInput.add(txtStok);

        panelInput.add(new JLabel("Harga:"));
        txtHarga = new JTextField();
        panelInput.add(txtHarga);

        btnSimpan = new JButton("Simpan");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");

        panelInput.add(btnSimpan);
        panelInput.add(btnUpdate);
        panelInput.add(btnDelete);

        panelUtama.add(panelInput, BorderLayout.NORTH);

        btnSimpan.addActionListener(e -> saveData());
        btnUpdate.addActionListener(e -> updateData());
        btnDelete.addActionListener(e -> deleteData());
        tabel.getSelectionModel().addListSelectionListener(e -> populateFields());
    }

    private void loadData() {
        model.setRowCount(0); // Bersihkan tabel sebelum memuat data
        Connection conn = sqlite.getKoneksi();
        String sql = "SELECT * FROM tbl_stok";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Object[] row = new Object[4];
                row[0] = rs.getString("id_Barang");
                row[1] = rs.getString("Nama_Barang");
                row[2] = rs.getInt("Stok_Barang");
                row[3] = rs.getInt("Harga");
                model.addRow(row);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat data: " + e.getMessage());
        }
    }

    private void saveData() {
        Connection conn = sqlite.getKoneksi();
        String sql = "INSERT INTO tbl_stok (id_Barang, Nama_Barang, Stok_Barang, Harga) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, txtKode.getText());
            pstmt.setString(2, txtNama.getText());
            pstmt.setInt(3, Integer.parseInt(txtStok.getText()));
            pstmt.setInt(4, Integer.parseInt(txtHarga.getText()));
            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Data berhasil disimpan!");
            loadData();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan data: " + e.getMessage());
        }
    }

    private void updateData() {
        Connection conn = sqlite.getKoneksi();
        String sql = "UPDATE tbl_stok SET Nama_Barang = ?, Stok_Barang = ?, Harga = ? WHERE id_Barang = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, txtNama.getText());
            pstmt.setInt(2, Integer.parseInt(txtStok.getText()));
            pstmt.setInt(3, Integer.parseInt(txtHarga.getText()));
            pstmt.setString(4, txtKode.getText());
            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Data berhasil diupdate!");
            loadData();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal mengupdate data: " + e.getMessage());
        }
    }

    private void deleteData() {
        Connection conn = sqlite.getKoneksi();
        String sql = "DELETE FROM tbl_stok WHERE id_Barang = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, txtKode.getText());
            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Data berhasil dihapus!");
            loadData();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal menghapus data: " + e.getMessage());
        }
    }

    private void populateFields() {
        int selectedRow = tabel.getSelectedRow();
        if (selectedRow != -1) {
            txtKode.setText(model.getValueAt(selectedRow, 0).toString());
            txtNama.setText(model.getValueAt(selectedRow, 1).toString());
            txtStok.setText(model.getValueAt(selectedRow, 2).toString());
            txtHarga.setText(model.getValueAt(selectedRow, 3).toString());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            App frame = new App();
            frame.setVisible(true);
        });
    }
}
