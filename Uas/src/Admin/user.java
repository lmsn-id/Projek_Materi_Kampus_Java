package Admin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import config.SQLite;

public class user extends JPanel {
    private JTable userTable;
    private DefaultTableModel tableModel;

    public user() {
        setBackground(new Color(40, 44, 52));
        setLayout(new BorderLayout());

        JLabel label = new JLabel("User Management");
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 30));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[] { "ID", "Username", "Role" }, 0);
        userTable = new JTable(tableModel);
        userTable.setBackground(new Color(60, 63, 65));
        userTable.setForeground(Color.WHITE);
        userTable.setGridColor(new Color(100, 100, 100));
        userTable.setFillsViewportHeight(true);

        userTable.getTableHeader().setBackground(new Color(50, 50, 50));
        userTable.getTableHeader().setForeground(Color.WHITE);
        userTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));

        JScrollPane tableScrollPane = new JScrollPane(userTable);
        tableScrollPane.getViewport().setBackground(new Color(60, 63, 65));
        tableScrollPane.setBorder(null);
        tableScrollPane.setBackground(new Color(60, 63, 65));

        JPanel userPanel = new JPanel(new BorderLayout());
        userPanel.setBackground(new Color(60, 63, 65));
        userPanel.add(tableScrollPane, BorderLayout.CENTER);

        add(userPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(40, 44, 52));
        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        JButton refreshButton = new JButton("Refresh");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        add(buttonPanel, BorderLayout.SOUTH);

        loadUserData();

        addButton.addActionListener(this::handleAdd);
        updateButton.addActionListener(this::handleUpdate);
        deleteButton.addActionListener(this::handleDelete);
        refreshButton.addActionListener(_ -> loadUserData());
    }

    private void loadUserData() {
        tableModel.setRowCount(0);
        String query = "SELECT * FROM akun";
        try (Connection conn = SQLite.getKoneksi();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String role = rs.getString("role");
                tableModel.addRow(new Object[] { id, username, role });
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load data.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleAdd(ActionEvent e) {
        JTextField usernameField = new JTextField();
        JTextField roleField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        Object[] inputFields = {
                "Username:", usernameField,
                "Password:", passwordField,
                "Role:", roleField
        };

        int option = JOptionPane.showConfirmDialog(this, inputFields, "Add User", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String role = roleField.getText();

            if (username.isEmpty() || password.isEmpty() || role.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields must be filled out.", "Warning",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            String query = "INSERT INTO akun (username, password, role) VALUES (?, ?, ?)";
            try (Connection conn = SQLite.getKoneksi();
                    PreparedStatement stmt = conn.prepareStatement(query)) {

                stmt.setString(1, username);
                stmt.setString(2, password);
                stmt.setString(3, role);
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "User added successfully.");
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed to add user.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                loadUserData();
            } catch (Exception loadEx) {
                loadEx.printStackTrace();
                JOptionPane.showMessageDialog(this, "User added but failed to refresh data.", "Warning",
                        JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private void handleUpdate(ActionEvent e) {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user to update.", "Warning",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);
        String currentUsername = (String) tableModel.getValueAt(selectedRow, 1);
        String currentRole = (String) tableModel.getValueAt(selectedRow, 2);

        JTextField usernameField = new JTextField(currentUsername);
        JTextField roleField = new JTextField(currentRole);
        Object[] inputFields = {
                "Username:", usernameField,
                "Role:", roleField
        };

        int option = JOptionPane.showConfirmDialog(this, inputFields, "Update User", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String newUsername = usernameField.getText();
            String newRole = roleField.getText();

            String query = "UPDATE akun SET username = ?, role = ? WHERE id = ?";
            Connection conn = null;
            PreparedStatement stmt = null;

            try {
                conn = SQLite.getKoneksi();
                stmt = conn.prepareStatement(query);

                stmt.setString(1, newUsername);
                stmt.setString(2, newRole);
                stmt.setInt(3, id);

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "User updated successfully.");
                    loadUserData();
                } else {
                    JOptionPane.showMessageDialog(this, "No user found to update.", "Warning",
                            JOptionPane.WARNING_MESSAGE);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed to update user. Error: " + ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            } finally {
                try {
                    if (stmt != null) {
                        stmt.close();
                    }
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private void handleDelete(ActionEvent e) {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user to delete.", "Warning",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);
        int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this user?", "Confirm Delete",
                JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            String query = "DELETE FROM akun WHERE id = ?";
            Connection conn = null;
            PreparedStatement stmt = null;

            try {
                conn = SQLite.getKoneksi();
                stmt = conn.prepareStatement(query);

                stmt.setInt(1, id);
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "User deleted successfully.");
                    loadUserData();
                } else {
                    JOptionPane.showMessageDialog(this, "No user found to delete.", "Warning",
                            JOptionPane.WARNING_MESSAGE);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed to delete user. Error: " + ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            } finally {
                try {
                    if (stmt != null) {
                        stmt.close();
                    }
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

}
