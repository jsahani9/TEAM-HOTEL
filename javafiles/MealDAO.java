import java.sql.*;

public class MealDAO {
    private Connection conn;

    public MealDAO() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  // Ensure driver loads
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/nutrifit", "root", "0767");
    }

    public boolean saveMeal(Meal meal) {
        String query = "INSERT INTO MealLog (MealName, MealTime, IngredientSummary, TotalCalories, TotalProtein, TotalFat, TotalCarbs) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, meal.getMealName());
            ps.setTimestamp(2, Timestamp.valueOf(meal.getMealTime()));
            ps.setString(3, meal.getIngredientSummary());  // ✅ Corrected: Set as String

            ps.setDouble(4, meal.getNutrientTotals().getOrDefault("ENERGY (KILOCALORIES)", 0.0));
            ps.setDouble(5, meal.getNutrientTotals().getOrDefault("PROTEIN", 0.0));
            ps.setDouble(6, meal.getNutrientTotals().getOrDefault("FAT (TOTAL LIPIDS)", 0.0));
            ps.setDouble(7, meal.getNutrientTotals().getOrDefault("CARBOHYDRATE, TOTAL (BY DIFFERENCE)", 0.0));

            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

