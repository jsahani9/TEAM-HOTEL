import java.sql.*;
import java.util.ArrayList;

public class GoalController {
    private final int userId;

    public GoalController(int userId) {
        this.userId = userId;
    }

    // Adds or replaces the existing goal with the new one (only 1 goal allowed)
    public boolean addGoal(UserGoal goal) {
        // Clear existing goal(s) first before adding new
        if (!clearGoals()) {
            System.err.println("Failed to clear existing goals before adding new.");
            return false;
        }

        String sql = "INSERT INTO UserGoals (user_id, nutrient, direction, modifier, amount) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setString(2, goal.getNutrient());
            stmt.setString(3, goal.getDirection());
            stmt.setString(4, goal.getModifier());
            stmt.setString(5, goal.getAmount());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            System.err.println("Error inserting goal: " + e.getMessage());
            return false;
        }
    }

    // Retrieve all goals for the user (should be 0 or 1)
    public ArrayList<UserGoal> getGoals() {
        ArrayList<UserGoal> goals = new ArrayList<>();
        String sql = "SELECT nutrient, direction, modifier, amount FROM UserGoals WHERE user_id = ?";

        try (Connection conn = DatabaseConnector.getConnection();
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

    // Checks if a nutrient goal exists (should be at most one)
    public boolean containsNutrient(String nutrient) {
        return getGoals().stream()
                .anyMatch(goal -> goal.getNutrient().equalsIgnoreCase(nutrient));
    }

    // Remove goal by index (should be 0 only)
    public void removeGoal(int index) {
        ArrayList<UserGoal> goals = getGoals();
        if (index >= 0 && index < goals.size()) {
            UserGoal goalToRemove = goals.get(index);
            String sql = "DELETE FROM UserGoals WHERE user_id = ? AND nutrient = ?";

            try (Connection conn = DatabaseConnector.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, userId);
                stmt.setString(2, goalToRemove.getNutrient());
                stmt.executeUpdate();

            } catch (SQLException e) {
                System.err.println("Error removing goal: " + e.getMessage());
            }
        }
    }

    // Returns count of goals (0 or 1)
    public int getGoalCount() {
        return getGoals().size();
    }

    // Clears all goals for this user (used before adding/replacing goal)
    public boolean clearGoals() {
        String sql = "DELETE FROM UserGoals WHERE user_id = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted >= 0;  // 0 rows deleted means no goal existed, so also success

        } catch (SQLException e) {
            System.err.println("Error clearing goals: " + e.getMessage());
            return false;
        }
    }
}
