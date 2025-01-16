import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import koneksi.MySQL;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class App extends JFrame {
    private JTextField txtFaktur, txtQty, txtDiskon, txtTotal, txtDate;
    private JComboBox<String> cmbUser, cmbCustomer, cmbProduct;
    private JTable table;
    private DefaultTableModel tableModel;

    private HashMap<String, Double> productPrices;

    public App() {
        setTitle("Faktur");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        productPrices = new HashMap<>();
        productPrices.put("DVD BLANK", 5000.0);
        productPrices.put("Mouse", 15000.0);
        productPrices.put("Keyboard", 25000.0);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel lblFaktur = new JLabel("FAKTUR:");
        lblFaktur.setBounds(20, 20, 60, 25);
        txtFaktur = new JTextField();
        txtFaktur.setBounds(90, 20, 150, 25);

        JLabel lblDate = new JLabel("Tanggal:");
        lblDate.setBounds(260, 20, 60, 25);

        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Jakarta"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MM/dd/yyyy");
        txtDate = new JTextField(now.format(formatter));
        txtDate.setBounds(330, 20, 150, 25);
        txtDate.setEditable(false);

        JLabel lblUser = new JLabel("USER:");
        lblUser.setBounds(500, 20, 60, 25);
        cmbUser = new JComboBox<>(new String[] { "U1", "U2", "U3" });
        cmbUser.setBounds(560, 20, 100, 25);

        JLabel lblCustomer = new JLabel("Customer:");
        lblCustomer.setBounds(20, 60, 80, 25);
        cmbCustomer = new JComboBox<>(new String[] { "Aan", "Lin", "Uun" });
        cmbCustomer.setBounds(90, 60, 150, 25);

        JLabel lblProduct = new JLabel("Product:");
        lblProduct.setBounds(20, 100, 80, 25);
        cmbProduct = new JComboBox<>(new String[] { "DVD BLANK", "Mouse", "Keyboard" });
        cmbProduct.setBounds(90, 100, 150, 25);

        JLabel lblQty = new JLabel("QTY:");
        lblQty.setBounds(260, 140, 40, 25);
        txtQty = new JTextField();
        txtQty.setBounds(330, 140, 150, 25);

        JLabel lblDiskon = new JLabel("Diskon:");
        lblDiskon.setBounds(500, 140, 60, 25);
        txtDiskon = new JTextField("0.0");
        txtDiskon.setBounds(560, 140, 100, 25);
        txtDiskon.setEditable(false);

        JLabel lblTotal = new JLabel("Total:");
        lblTotal.setBounds(500, 400, 60, 25);
        txtTotal = new JTextField("0.0");
        txtTotal.setBounds(560, 400, 100, 25);
        txtTotal.setEditable(false);

        tableModel = new DefaultTableModel(new Object[][] {},
                new String[] { "ID", "FAKTUR", "USER", "CUSTOMER", "PRODUCT", "QTY", "DISKON", "TOTAL" });
        table = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setBounds(20, 230, 740, 150);

        JButton btnSimpan = new JButton("Simpan");
        btnSimpan.setBounds(20, 180, 100, 30);
        JButton btnHapus = new JButton("Hapus");
        btnHapus.setBounds(130, 180, 100, 30);
        JButton btnEdit = new JButton("Edit");
        btnEdit.setBounds(240, 180, 100, 30);
        JButton btnKeluar = new JButton("Keluar");
        btnKeluar.setBounds(350, 180, 100, 30);

        btnSimpan.addActionListener(e -> simpanData());
        btnHapus.addActionListener(e -> hapusData());
        btnEdit.addActionListener(e -> editData());
        btnKeluar.addActionListener(e -> System.exit(0));

        panel.add(lblFaktur);
        panel.add(txtFaktur);
        panel.add(lblDate);
        panel.add(txtDate);
        panel.add(lblUser);
        panel.add(cmbUser);
        panel.add(lblCustomer);
        panel.add(cmbCustomer);
        panel.add(lblProduct);
        panel.add(cmbProduct);
        panel.add(lblQty);
        panel.add(txtQty);
        panel.add(lblDiskon);
        panel.add(txtDiskon);
        panel.add(btnSimpan);
        panel.add(btnHapus);
        panel.add(btnEdit);
        panel.add(btnKeluar);
        panel.add(tableScrollPane);
        panel.add(lblTotal);
        panel.add(txtTotal);

        add(panel);

        cmbProduct.addActionListener(e -> hitungDiskonDanTotal());
        txtQty.addCaretListener(e -> hitungDiskonDanTotal());

        loadData();
    }

    private void hitungDiskonDanTotal() {
        String product = (String) cmbProduct.getSelectedItem();
        String qtyText = txtQty.getText();
        if (product != null && !qtyText.isEmpty()) {
            try {
                int qty = Integer.parseInt(qtyText);
                double harga = productPrices.get(product);
                double total = harga * qty;
                double diskon = qty > 10 ? total * 0.1 : 0;
                txtDiskon.setText(String.valueOf(diskon));
                txtTotal.setText(String.valueOf(total - diskon));
            } catch (NumberFormatException ex) {
                txtDiskon.setText("0.0");
                txtTotal.setText("0.0");
            }
        }
    }

    private void simpanData() {
        String faktur = txtFaktur.getText();
        String user = (String) cmbUser.getSelectedItem();
        String customer = (String) cmbCustomer.getSelectedItem();
        String product = (String) cmbProduct.getSelectedItem();
        int qty = Integer.parseInt(txtQty.getText());
        double diskon = Double.parseDouble(txtDiskon.getText());
        double total = Double.parseDouble(txtTotal.getText());

        try (Connection conn = MySQL.getKoneksi()) {
            String sql = "INSERT INTO transaksi (faktur, user, customer, product, qty, diskon, total) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, faktur);
            stmt.setString(2, user);
            stmt.setString(3, customer);
            stmt.setString(4, product);
            stmt.setInt(5, qty);
            stmt.setDouble(6, diskon);
            stmt.setDouble(7, total);

            stmt.executeUpdate();
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                tableModel.addRow(
                        new Object[] { generatedKeys.getInt(1), faktur, user, customer, product, qty, diskon, total });
            }

            JOptionPane.showMessageDialog(this, "Data berhasil disimpan.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void hapusData() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            try (Connection conn = MySQL.getKoneksi()) {
                String sql = "DELETE FROM transaksi WHERE id = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, id);

                stmt.executeUpdate();
                tableModel.removeRow(selectedRow);
                JOptionPane.showMessageDialog(this, "Data berhasil dihapus.");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pilih data yang ingin dihapus terlebih dahulu.");
        }
    }

    private void editData() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            String faktur = txtFaktur.getText();
            String user = (String) cmbUser.getSelectedItem();
            String customer = (String) cmbCustomer.getSelectedItem();
            String product = (String) cmbProduct.getSelectedItem();
            int qty = Integer.parseInt(txtQty.getText());
            double diskon = Double.parseDouble(txtDiskon.getText());
            double total = Double.parseDouble(txtTotal.getText());

            try (Connection conn = MySQL.getKoneksi()) {
                String sql = "UPDATE transaksi SET faktur = ?, user = ?, customer = ?, product = ?, qty = ?, diskon = ?, total = ? WHERE id = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, faktur);
                stmt.setString(2, user);
                stmt.setString(3, customer);
                stmt.setString(4, product);
                stmt.setInt(5, qty);
                stmt.setDouble(6, diskon);
                stmt.setDouble(7, total);
                stmt.setInt(8, id);

                stmt.executeUpdate();

                tableModel.setValueAt(faktur, selectedRow, 1);
                tableModel.setValueAt(user, selectedRow, 2);
                tableModel.setValueAt(customer, selectedRow, 3);
                tableModel.setValueAt(product, selectedRow, 4);
                tableModel.setValueAt(qty, selectedRow, 5);
                tableModel.setValueAt(diskon, selectedRow, 6);
                tableModel.setValueAt(total, selectedRow, 7);

                JOptionPane.showMessageDialog(this, "Data berhasil diperbarui.");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pilih data yang ingin diperbarui terlebih dahulu.");
        }
    }

    private void loadData() {
        try (Connection conn = MySQL.getKoneksi()) {
            String sql = "SELECT * FROM transaksi";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                tableModel.addRow(new Object[] {
                        rs.getInt("id"),
                        rs.getString("faktur"),
                        rs.getString("user"),
                        rs.getString("customer"),
                        rs.getString("product"),
                        rs.getInt("qty"),
                        rs.getDouble("diskon"),
                        rs.getDouble("total")
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            App frame = new App();
            frame.setVisible(true);
        });
    }
}
