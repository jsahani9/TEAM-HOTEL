import java.sql.*;

public class CaloriesAnalyzer implements NutrientAnalyzer {
    @Override
    public String analyze(int foodId) {
        String result = "";
        try {
            // Load JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/nutrisci_db", "root", "0767"
            );

            String sql = "SELECT NutrientValue FROM nutrientamount WHERE FoodID = ? AND NutrientID = 208";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, foodId);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                double val = rs.getDouble("NutrientValue");
                result = "Calories: " + val + " kcal";
            } else {
                result = "No calorie data found for Food ID " + foodId;
            }

            conn.close();
        } catch (Exception e) {
            result = "Error: " + e.getMessage();
        }
        return result;
    }
}
