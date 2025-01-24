import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Admin.Admin;
import Pages.User;

import config.SQLite;

public class App extends JFrame {
    private static final String USERNAME_PLACEHOLDER = "Masukan username";
    private static final String PASSWORD_PLACEHOLDER = "Masukan kata sandi";
    private static final String EYE_ICON = "👁";

    public App() {
        setTitle("Login Page");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        setupHeaderPanel();
        setupFormPanel();
    }

    private void setupHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBackground(new Color(85, 73, 251));
        headerPanel.setPreferredSize(new Dimension(400, 150));

        JLabel logoLabel = new JLabel("Bandai Namco", SwingConstants.CENTER);
        logoLabel.setFont(new Font("SansSerif", Font.BOLD, 30));
        logoLabel.setForeground(Color.WHITE);
        headerPanel.add(logoLabel, BorderLayout.CENTER);

        JLabel curveDesign = new JLabel();
        curveDesign.setOpaque(false);
        headerPanel.add(curveDesign, BorderLayout.SOUTH);

        add(headerPanel, BorderLayout.NORTH);
    }

    private void setupFormPanel() {
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JLabel welcomeLabel = new JLabel("Welcome back !");
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(welcomeLabel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JTextField usernameField = createPlaceholderTextField(USERNAME_PLACEHOLDER);
        formPanel.add(usernameField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JPasswordField passwordField = createPasswordField();
        formPanel.add(passwordField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JPanel optionsPanel = createOptionsPanel();
        formPanel.add(optionsPanel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton loginButton = createLoginButton(usernameField, passwordField);
        formPanel.add(loginButton);
        formPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        add(formPanel, BorderLayout.CENTER);
    }

    private JTextField createPlaceholderTextField(String placeholder) {
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(300, 40));
        textField.setMaximumSize(new Dimension(300, 40));
        textField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));

        textField.setText(placeholder);
        textField.setForeground(Color.GRAY);

        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(Color.GRAY);
                }
            }
        });

        return textField;
    }

    private JPasswordField createPasswordField() {
        JPasswordField passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(300, 40));
        passwordField.setMaximumSize(new Dimension(300, 40));
        passwordField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));

        passwordField.setText(PASSWORD_PLACEHOLDER);
        passwordField.setForeground(Color.GRAY);
        passwordField.setEchoChar((char) 0);

        passwordField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(passwordField.getPassword()).equals(PASSWORD_PLACEHOLDER)) {
                    passwordField.setText("");
                    passwordField.setForeground(Color.BLACK);
                    passwordField.setEchoChar('•');
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (passwordField.getPassword().length == 0) {
                    passwordField.setText(PASSWORD_PLACEHOLDER);
                    passwordField.setForeground(Color.GRAY);
                    passwordField.setEchoChar((char) 0);
                }
            }

        });

        JButton toggleVisibilityButton = new JButton(EYE_ICON);
        toggleVisibilityButton.setFont(new Font("SansSerif", Font.PLAIN, 18));
        toggleVisibilityButton.setFocusPainted(false);
        toggleVisibilityButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        toggleVisibilityButton.setContentAreaFilled(false);
        toggleVisibilityButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        toggleVisibilityButton.addActionListener(_ -> {
            if (passwordField.getEchoChar() == '•') {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('•');
            }
        });

        return passwordField;
    }

    private JPanel createOptionsPanel() {
        JPanel optionsPanel = new JPanel(new BorderLayout());

        JCheckBox rememberMeCheckBox = new JCheckBox("Remember me");
        rememberMeCheckBox.setFont(new Font("SansSerif", Font.PLAIN, 12));
        optionsPanel.add(rememberMeCheckBox, BorderLayout.WEST);

        JLabel forgetPasswordLabel = new JLabel("Forget password?");
        forgetPasswordLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        forgetPasswordLabel.setForeground(Color.BLUE);
        forgetPasswordLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        optionsPanel.add(forgetPasswordLabel, BorderLayout.EAST);

        return optionsPanel;
    }

    private JButton createLoginButton(JTextField usernameField, JPasswordField passwordField) {
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        loginButton.setBackground(new Color(85, 73, 251));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setPreferredSize(new Dimension(300, 40));
        loginButton.setMaximumSize(new Dimension(300, 40));

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = String.valueOf(passwordField.getPassword());

                if (username.isEmpty() || password.isEmpty() || username.equals(USERNAME_PLACEHOLDER)) {
                    JOptionPane.showMessageDialog(App.this, "Please enter your username and password.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Connection conn = null;
                PreparedStatement stmt = null;
                ResultSet rs = null;

                try {
                    conn = SQLite.getKoneksi();
                    stmt = conn.prepareStatement("SELECT role FROM akun WHERE username = ? AND password = ?");
                    stmt.setString(1, username);
                    stmt.setString(2, password);

                    rs = stmt.executeQuery();

                    if (rs.next()) {
                        String role = rs.getString("role");
                        if ("ADMIN".equalsIgnoreCase(role)) {
                            JOptionPane.showMessageDialog(App.this, "Login successful! Redirecting to Admin page.",
                                    "Success", JOptionPane.INFORMATION_MESSAGE);
                            new Admin();
                            setVisible(false);
                        } else {
                            JOptionPane.showMessageDialog(App.this, "Login successful!", "Success",
                                    JOptionPane.INFORMATION_MESSAGE);
                            new User();
                            setVisible(false);
                        }
                    } else {
                        JOptionPane.showMessageDialog(App.this, "Invalid username or password.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(App.this, "Error: " + ex.getMessage(), "Error",
                            JOptionPane.ERROR_MESSAGE);
                } finally {
                    try {
                        if (rs != null)
                            rs.close();
                        if (stmt != null)
                            stmt.close();
                        if (conn != null && !conn.isClosed())
                            conn.close();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        return loginButton;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            App frame = new App();
            frame.setVisible(true);
        });
    }
}
