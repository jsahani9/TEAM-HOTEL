import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class NutrientAnalysisHelper {

    public static Map<String, Double> getMostRecentMealNutrients(int userId) {
        Map<String, Double> nutrientMap = new HashMap<>();

        String sql = """
            SELECT n.NutrientName, SUM(na.Nutr_Val) AS TotalValue
            FROM MealLog ml
            JOIN Ingredient ing ON ml.meal_id = ing.meal_id
            JOIN NutrientAmount na ON ing.food_id = na.Nutr_No
            JOIN NutrientName n ON na.Nutr_No = n.Nutr_No
            WHERE ml.user_id = ?
            GROUP BY n.NutrientName
        """;

        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/nutrisci_db", "root", "0767");
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                nutrientMap.put(rs.getString("NutrientName"), rs.getDouble("TotalValue"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nutrientMap;
    }
}
