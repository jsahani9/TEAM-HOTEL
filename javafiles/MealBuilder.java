import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDateTime;

public class MealBuilder {
    private final Map<String, Integer> ingredients = new HashMap<>();

    public void addIngredient(String food, int quantity) {
        ingredients.put(food, quantity);
    }

    public Map<String, Integer> getIngredients() {
        return ingredients;
    }

    public Map<String, Double> calculateTotalNutrients() {
        Map<String, Double> totals = new HashMap<>();

        try (Connection conn = DBConnection.getConnection()) {
            for (Map.Entry<String, Integer> entry : ingredients.entrySet()) {
                String foodName = entry.getKey();
                int quantity = entry.getValue();

                String query = "SELECT nn.NutrientName, na.NutrientValue " +
                               "FROM NutrientAmount na " +
                               "JOIN FoodName fn ON na.FoodID = fn.FoodID " +
                               "JOIN nutrientname nn ON na.NutrientID = nn.NutrientCode " +
                               "WHERE fn.FoodDescription = ?";

                try (PreparedStatement ps = conn.prepareStatement(query)) {
                    ps.setString(1, foodName); // exact match

                    try (ResultSet rs = ps.executeQuery()) {
                        while (rs.next()) {
                            String nutrient = rs.getString("NutrientName");
                            double valuePer100g = rs.getDouble("NutrientValue");
                            double adjustedValue = valuePer100g * quantity / 100.0;

                            totals.merge(nutrient, adjustedValue, Double::sum);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totals;
    }

    public Meal build() {
        Map<String, Double> nutrientTotals = calculateTotalNutrients();
        Meal meal = new Meal(ingredients, nutrientTotals);

        // Auto-set name and timestamp
        meal.setMealName("My Meal");
        meal.setMealTime(LocalDateTime.now());

        // Ingredient summary
        String ingredientSummary = String.join(", ", ingredients.keySet());
        meal.setIngredientSummary(ingredientSummary);

        // Set key macronutrients
        meal.setTotalCalories(nutrientTotals.getOrDefault("ENERGY (KILOCALORIES)", 0.0));
        meal.setTotalProtein(nutrientTotals.getOrDefault("PROTEIN", 0.0));
        meal.setTotalFat(nutrientTotals.getOrDefault("FAT (TOTAL LIPIDS)", 0.0));
        meal.setTotalCarbs(nutrientTotals.getOrDefault("CARBOHYDRATE, TOTAL (BY DIFFERENCE)", 0.0));

        return meal;
    }
}
