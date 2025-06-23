import java.sql.*;
import java.util.Scanner;
 // ---------------------------------------
// INTRUCTIONS :- 
// create a database in mySQL named Nutrifit, and create a table named users
// create 2 tables with this command 
// ""CREATE TABLE users (
//username VARCHAR(50) PRIMARY KEY,
//password VARCHAR(100)
//)"";


public class UserLoginMySQL {
	static final String DB_URL = "jdbc:mysql://localhost:3306/Nutrifit"; // make sure you ahve a database named Nutrifit
    static final String USER = "root";
    static final String PASS = "07671";  // use your own password ( root pass :] )
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        loginMenu();
    }

    
    static void loginMenu() {
        System.out.println("===== LOGIN MENU =====");
        System.out.println("1. Sign up");
        System.out.println("2. Login");
        System.out.println("3. Reset Password");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");
        int choice = sc.nextInt();
        sc.nextLine();  

        switch (choice) {
            case 1 : register();
            case 2 : login();
            case 3 : resetPassword();
            default : System.out.println("Goodbye!");
        }
    }

    static void register() {
        System.out.print("Enter new username: ");
        String username = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();
    
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            conn.setAutoCommit(true); // Optional, for safety
            System.out.println("✅ Connected to database successfully.");
    
            String query = "INSERT INTO users (username, password) VALUES (?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.executeUpdate();
    
            System.out.println("✅ Insert query executed.");
            System.out.println("User registered successfully.");
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Username already exists.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        loginMenu();
    }
    

    static void login() {
        System.out.print("Enter username: ");
        String username = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("Login successful. Welcome, " + username + "!");
            } else {
                System.out.println("Invalid credentials.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        loginMenu();
    }

    static void resetPassword() {
        System.out.print("Enter existing username: ");
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
                System.out.println("Password updated successfully.");
            } else {
                System.out.println("Invalid username or password.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        loginMenu();
    }
}
