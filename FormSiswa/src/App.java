import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class App extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private JTextField nisField, namaField;
    private JComboBox<String> jurusanBox, jkBox;
    private JTextArea alamatArea;

    public App() {
        setTitle("Form Input Data Siswa");
        setSize(900, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        add(mainPanel);

        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Form Siswa"));
        mainPanel.add(inputPanel, BorderLayout.WEST);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("NIS:"), gbc);

        gbc.gridx = 1;
        nisField = new JTextField(15);
        inputPanel.add(nisField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Nama:"), gbc);

        gbc.gridx = 1;
        namaField = new JTextField(15);
        inputPanel.add(namaField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("Jurusan:"), gbc);

        gbc.gridx = 1;
        jurusanBox = new JComboBox<>(
                new String[] { "Rekayasa Perangkat Lunak", "Multimedia", "Teknik Komputer Jaringan",
                        "Otomatisasi Tata Kelola Perkantoran" });
        inputPanel.add(jurusanBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(new JLabel("JK:"), gbc);

        gbc.gridx = 1;
        jkBox = new JComboBox<>(new String[] { "Laki-laki", "Perempuan" });
        inputPanel.add(jkBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        inputPanel.add(new JLabel("Alamat:"), gbc);

        gbc.gridx = 1;
        alamatArea = new JTextArea(3, 15);
        JScrollPane alamatScrollPane = new JScrollPane(alamatArea);
        inputPanel.add(alamatScrollPane, gbc);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 4, 5, 0));
        JButton simpanButton = new JButton("Simpan");
        JButton hapusButton = new JButton("Hapus");
        JButton updateButton = new JButton("Update");
        JButton resetButton = new JButton("Reset");

        buttonPanel.add(simpanButton);
        buttonPanel.add(hapusButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(resetButton);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        inputPanel.add(buttonPanel, gbc);

        model = new DefaultTableModel();
        table = new JTable(model);

        model.addColumn("NIS");
        model.addColumn("Nama");
        model.addColumn("Jurusan");
        model.addColumn("Jenis Kelamin");
        model.addColumn("Alamat");

        JScrollPane tableScrollPane = new JScrollPane(table);
        mainPanel.add(tableScrollPane, BorderLayout.CENTER);

        simpanButton.addActionListener(e -> simpanData());
        hapusButton.addActionListener(e -> hapusData());
        updateButton.addActionListener(e -> updateData());
        resetButton.addActionListener(e -> resetForm());

        loadData();
    }

    private void simpanData() {
        try (Connection conn = config.koneksi.getKoneksi()) {
            String query = "INSERT INTO students (nis, nama, jurusan, jk, alamat) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, nisField.getText());
            stmt.setString(2, namaField.getText());
            stmt.setString(3, jurusanBox.getSelectedItem().toString());
            stmt.setString(4, jkBox.getSelectedItem().toString());
            stmt.setString(5, alamatArea.getText());
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Data berhasil disimpan!");
            loadData();
            resetForm();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void hapusData() {
        try (Connection conn = config.koneksi.getKoneksi()) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String nis = model.getValueAt(selectedRow, 0).toString();
                String query = "DELETE FROM students WHERE nis = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, nis);
                stmt.executeUpdate();

                JOptionPane.showMessageDialog(this, "Data berhasil dihapus!");
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Pilih data yang akan dihapus!");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void updateData() {
        try (Connection conn = config.koneksi.getKoneksi()) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String nis = model.getValueAt(selectedRow, 0).toString();
                String query = "UPDATE students SET nama = ?, jurusan = ?, jk = ?, alamat = ? WHERE nis = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, namaField.getText());
                stmt.setString(2, jurusanBox.getSelectedItem().toString());
                stmt.setString(3, jkBox.getSelectedItem().toString());
                stmt.setString(4, alamatArea.getText());
                stmt.setString(5, nis);
                stmt.executeUpdate();

                JOptionPane.showMessageDialog(this, "Data berhasil diupdate!");
                loadData();
                resetForm();
            } else {
                JOptionPane.showMessageDialog(this, "Pilih data yang akan diupdate!");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void loadData() {
        try (Connection conn = config.koneksi.getKoneksi()) {
            String query = "SELECT * FROM students";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            model.setRowCount(0);
            while (rs.next()) {
                model.addRow(new Object[] {
                        rs.getString("nis"),
                        rs.getString("nama"),
                        rs.getString("jurusan"),
                        rs.getString("jk"),
                        rs.getString("alamat")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void resetForm() {
        nisField.setText("");
        namaField.setText("");
        jurusanBox.setSelectedIndex(0);
        jkBox.setSelectedIndex(0);
        alamatArea.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            App frame = new App();
            frame.setVisible(true);
        });
    }
}
