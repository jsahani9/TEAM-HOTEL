import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class UserLoginGUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/Nutrifit";
    private static final String USER = "root";
    private static final String PASS = "0767"; // replace with your actual password

    public UserLoginGUI() {
        setTitle("NutriSci - Login");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(350, 220);
        setLocationRelativeTo(null);


        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10)); 
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 


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

    private void loginUser() {//with the use of AI
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String query = "SELECT user_id FROM users WHERE username=? AND password=?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("user_id");
                JOptionPane.showMessageDialog(this, "Login successful!");

                if (!ProfileDAO.profileExists(userId)) {
                    new ProfileCreationPage(userId);
                } else {
                    new MainMenuPage(userId);
                }

                dispose(); // close login window
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error during login.");
        }
    }

    private void registerUser() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String query = "INSERT INTO users (username, password) VALUES (?, ?)";
            PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int userId = rs.getInt(1);
                JOptionPane.showMessageDialog(this, "Sign-up successful!");

                if (!ProfileDAO.profileExists(userId)) {
                    new ProfileCreationPage(userId);
                } else {
                    new MainMenuPage(userId);
                }

                dispose(); // close login window
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            JOptionPane.showMessageDialog(this, "Username already exists.");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error during sign-up.");
        }
    }

    private void resetPassword() {
        String username = usernameField.getText();
        String newPassword = new String(passwordField.getPassword());

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String query = "UPDATE users SET password=? WHERE username=?";
            PreparedStatement ps = conn.prepareStatement(query);
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
            JOptionPane.showMessageDialog(this, "Error during password reset.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(UserLoginGUI::new);
    }
}
