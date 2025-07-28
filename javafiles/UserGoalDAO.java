import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserGoalDAO {

    // Connect to Nutrifit database via centralized DatabaseConnector
    private static Connection getConnection() throws SQLException {
        return DatabaseConnector.getConnection();  // Ensure this points to Nutrifit DB
    }

    // Insert a new user goal
    public static boolean insertGoal(UserGoal goal) {
        String sql = "INSERT INTO UserGoals (user_id, nutrient, direction, modifier, amount) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, goal.getUserId());
            stmt.setString(2, goal.getNutrient());
            stmt.setString(3, goal.getDirection());
            stmt.setString(4, goal.getModifier());
            stmt.setString(5, goal.getAmount());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error inserting goal: " + e.getMessage());
            return false;
        }
    }

    // Retrieve all goals for a specific user
    public static List<UserGoal> getGoalsByUserId(int userId) {
        List<UserGoal> goals = new ArrayList<>();
        String sql = "SELECT nutrient, direction, modifier, amount FROM UserGoals WHERE user_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String nutrient = rs.getString("nutrient");
                String direction = rs.getString("direction");
                String modifier = rs.getString("modifier");
                String amount = rs.getString("amount");

                goals.add(new UserGoal(userId, nutrient, direction, modifier, amount));
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving goals: " + e.getMessage());
        }

        return goals;
    }

    // Delete all goals for a user
    public static boolean deleteGoalsByUserId(int userId) {
        String sql = "DELETE FROM UserGoals WHERE user_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting goals: " + e.getMessage());
            return false;
        }
    }

    // Delete a specific goal for a user based on nutrient
    public static boolean deleteSpecificGoal(UserGoal goal) {
        String sql = "DELETE FROM UserGoals WHERE user_id = ? AND nutrient = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, goal.getUserId());
            stmt.setString(2, goal.getNutrient());

            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting specific goal: " + e.getMessage());
            return false;
        }
    }
}
