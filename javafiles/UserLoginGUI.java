import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class UserLoginGUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/Nutrifit";
    private static final String USER = "root";
    private static final String PASS = "0767"; // updated password

    public UserLoginGUI() {
        setTitle("Nutrifit Login");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(350, 200);
        setLocationRelativeTo(null);


        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10)); // adds horizontal and vertical spacing
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // margin around the grid


        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        JButton loginBtn = new JButton("Login");
        loginBtn.addActionListener(e -> loginUser());
        panel.add(loginBtn);

        JButton signupBtn = new JButton("Sign Up");
        signupBtn.addActionListener(e -> registerUser());
        panel.add(signupBtn);

        JButton resetBtn = new JButton("Reset Password");
        resetBtn.addActionListener(e -> resetPassword());
        panel.add(resetBtn);

        add(panel);
        setVisible(true);
    }

    private void loginUser() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE username=? AND password=?");
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Login successful!");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void registerUser() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO users VALUES (?, ?)");
            ps.setString(1, username);
            ps.setString(2, password);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Sign-up successful!");
        } catch (SQLIntegrityConstraintViolationException e) {
            JOptionPane.showMessageDialog(this, "Username already exists.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void resetPassword() {
        String username = usernameField.getText();
        String newPassword = new String(passwordField.getPassword());

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            PreparedStatement ps = conn.prepareStatement("UPDATE users SET password=? WHERE username=?");
            ps.setString(1, newPassword);
            ps.setString(2, username);
            int updated = ps.executeUpdate();
            if (updated > 0) {
                JOptionPane.showMessageDialog(this, "Password updated.");
            } else {
                JOptionPane.showMessageDialog(this, "User not found.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UserLoginGUI());
    }
}
