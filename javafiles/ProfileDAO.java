import java.sql.*;

public class ProfileDAO {

    public static boolean saveProfile(Profile profile) {
        try (Connection conn = DatabaseConnector.getConnection()) {
            String sql = "INSERT INTO Profile (user_id, name, age, gender, weight, height, activity_level, goal) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, profile.getUserId());
            stmt.setString(2, profile.getName());
            stmt.setInt(3, profile.getAge());
            stmt.setString(4, profile.getGender());
            stmt.setDouble(5, profile.getWeight());
            stmt.setDouble(6, profile.getHeight());
            stmt.setString(7, profile.getActivityLevel());
            stmt.setString(8, profile.getGoal());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean profileExists(int userId) {
        try (Connection conn = DatabaseConnector.getConnection()) {
            String sql = "SELECT 1 FROM Profile WHERE user_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
