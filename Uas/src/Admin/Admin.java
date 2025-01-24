package Admin;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import config.SQLite;

public class Admin extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;

    public Admin() {
        setTitle("Admin Dashboard");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Color darkBg = new Color(33, 37, 41);
        Color sidebarBg = new Color(22, 25, 28);
        Color cardBg = new Color(40, 44, 52);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(darkBg);

        JPanel sidebar = createSidebar(sidebarBg);
        mainPanel.add(sidebar, BorderLayout.WEST);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        JPanel dashboardPanel = createDashboardPanel(cardBg);
        JPanel userPagePanel = new user();
        JPanel stockPanel = new Stok();
        JPanel transaksi = new Transaksi();

        cardPanel.add(dashboardPanel, "Dashboard");
        cardPanel.add(userPagePanel, "User");
        cardPanel.add(stockPanel, "Stock");
        cardPanel.add(transaksi, "Transaksi");

        mainPanel.add(cardPanel, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
    }

    private JPanel createSidebar(Color bg) {
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(250, 0));
        sidebar.setBackground(bg);
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel logo = new JLabel("Bandai Namco");
        logo.setForeground(Color.WHITE);
        logo.setFont(new Font("Arial", Font.BOLD, 20));
        sidebar.add(logo);

        String[] menuItems = { "Dashboard", "User", "Stock", "Transaksi" };

        for (String item : menuItems) {
            JButton menuButton = new JButton(item);
            menuButton.setForeground(Color.WHITE);
            menuButton.setBackground(bg);
            menuButton.setBorderPainted(false);
            menuButton.setFocusPainted(false);
            menuButton.setAlignmentX(Component.LEFT_ALIGNMENT);
            menuButton.setMaximumSize(new Dimension(250, 35));
            sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
            sidebar.add(menuButton);

            menuButton.addActionListener(_ -> {
                cardLayout.show(cardPanel, item);
            });
        }

        return sidebar;
    }

    private JPanel createDashboardPanel(Color cardBg) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(cardBg);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel statsPanel = new JPanel(new GridLayout(1, 4, 15, 0));
        statsPanel.setOpaque(false);

        String usersCount = getUsersCount();
        String quantityCount = getQuantityCount();
        String stockCount = getStockCOunt();
        String transaksiCount = getTransaksiCount();

        statsPanel.add(createStatCard(usersCount, "Users", new Color(88, 95, 224)));
        statsPanel.add(createStatCard(quantityCount, "Total Quantity", new Color(66, 153, 225)));
        statsPanel.add(createStatCard(stockCount, "Stock Barang", new Color(236, 180, 47)));
        statsPanel.add(createStatCard(transaksiCount, "Jumlah Transaksi", new Color(225, 82, 82)));

        panel.add(statsPanel, BorderLayout.NORTH);

        JPanel graphPanel = new JPanel();
        graphPanel.setBackground(cardBg);
        graphPanel.setPreferredSize(new Dimension(0, 400));
        graphPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(graphPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createStatCard(String value, String label, Color bgColor) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(bgColor);
        card.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel valueLabel = new JLabel(value);
        valueLabel.setForeground(Color.WHITE);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JLabel titleLabel = new JLabel(label);
        titleLabel.setForeground(new Color(255, 255, 255, 180));

        card.add(valueLabel);
        card.add(Box.createRigidArea(new Dimension(0, 5)));
        card.add(titleLabel);
        card.add(Box.createRigidArea(new Dimension(0, 5)));

        return card;
    }

    private String getUsersCount() {
        int userCount = 0;
        String query = "SELECT COUNT(*) AS user_count FROM akun";
        try (Connection conn = SQLite.getKoneksi();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                userCount = rs.getInt("user_count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return String.valueOf(userCount);
    }

    private String getQuantityCount() {
        int totalQuantity = 0;
        String query = "SELECT SUM(quantity) AS total_quantity FROM stokbarang";

        try (Connection conn = SQLite.getKoneksi();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                totalQuantity = rs.getInt("total_quantity");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return String.valueOf(totalQuantity);
    }

    private String getStockCOunt() {
        int totalStock = 0;
        String query = "SELECT COUNT(*) AS total_stock FROM stokbarang";

        try (Connection conn = SQLite.getKoneksi();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                totalStock = rs.getInt("total_stock");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return String.valueOf(totalStock);
    }

    private String getTransaksiCount() {
        int totalTransaksi = 0;
        String query = "SELECT COUNT(*) AS total_transaksi FROM transaksi";

        try (Connection conn = SQLite.getKoneksi();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                totalTransaksi = rs.getInt("total_transaksi");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return String.valueOf(totalTransaksi);
    }

    // public static void main(String[] args) {
    // SwingUtilities.invokeLater(() -> {
    // Admin frame = new Admin();
    // frame.setVisible(true);
    // });
    // }
}
