import java.sql.*;
import java.util.Scanner;

public class UserLoginMySQL {
    static final String DB_URL = "jdbc:mysql://localhost:3306/Nutrifit";
    static final String USER = "root";
    static final String PASS = "07671";  // Update with your root password

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            loginMenu();
        }
    }

    static void loginMenu() {
        System.out.println("===== LOGIN MENU =====");
        System.out.println("1. Sign Up");
        System.out.println("2. Login");
        System.out.println("3. Reset Password");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");

        int choice = sc.nextInt();
        sc.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                register();
                break;
            case 2:
                login();
                break;
            case 3:
                resetPassword();
                break;
            case 4:
                System.out.println("Goodbye!");
                System.exit(0);
            default:
                System.out.println("Invalid choice.");
        }
    }

    static void register() {
        System.out.print("Enter new username: ");
        String username = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String query = "INSERT INTO users (username, password) VALUES (?, ?)";
            PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int userId = rs.getInt(1);
                System.out.println("✅ User registered successfully. User ID: " + userId);

                if (!ProfileDAO.profileExists(userId)) {
                    new ProfileCreationPage(userId);
                } else {
                    new MainMenuPage(userId);
                }
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("❌ Username already exists.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void login() {
        System.out.print("Enter username: ");
        String username = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String query = "SELECT user_id FROM users WHERE username = ? AND password = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("user_id");
                System.out.println("✅ Login successful. User ID: " + userId);

                if (!ProfileDAO.profileExists(userId)) {
                    new ProfileCreationPage(userId);
                } else {
                    new MainMenuPage(userId);
                }
            } else {
                System.out.println("❌ Invalid username or password.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void resetPassword() {
        System.out.print("Enter your username: ");
        String username = sc.nextLine();
        System.out.print("Enter current password: ");
        String oldPassword = sc.nextLine();

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String checkQuery = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement check = conn.prepareStatement(checkQuery);
            check.setString(1, username);
            check.setString(2, oldPassword);
            ResultSet rs = check.executeQuery();

            if (rs.next()) {
                System.out.print("Enter new password: ");
                String newPassword = sc.nextLine();

                String updateQuery = "UPDATE users SET password = ? WHERE username = ?";
                PreparedStatement update = conn.prepareStatement(updateQuery);
                update.setString(1, newPassword);
                update.setString(2, username);
                update.executeUpdate();

                System.out.println("✅ Password updated successfully.");
            } else {
                System.out.println("❌ Invalid username or password.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
