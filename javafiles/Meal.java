import java.time.LocalDateTime;
import java.util.Map;

public class Meal {
    private String mealName;
    private LocalDateTime mealTime;
    private Map<String, Integer> ingredients;
    private Map<String, Double> nutrientTotals;
    private String ingredientSummary;
    private double totalCalories;
    private double totalProtein;
    private double totalFat;
    private double totalCarbs;
    private int userId;

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }


    public Meal(Map<String, Integer> ingredients, Map<String, Double> nutrientTotals) {
        this.ingredients = ingredients;
        this.nutrientTotals = nutrientTotals;
    }

    public void setMealName(String name) {
        this.mealName = name;
    }

    public void setMealTime(LocalDateTime time) {
        this.mealTime = time;
    }

    public String getMealName() {
        return mealName;
    }

    public LocalDateTime getMealTime() {
        return mealTime;
    }

    public Map<String, Integer> getIngredients() {
        return ingredients;
    }

    public Map<String, Double> getNutrientTotals() {
        return nutrientTotals;
    }
    public void setIngredientSummary(String summary) {
        this.ingredientSummary = summary;
    }
    public String getIngredientSummary() {
        return ingredientSummary;
    }
    
    public void setTotalCalories(double totalCalories) {
        this.totalCalories = totalCalories;
    }
    public double getTotalCalories() {
        return totalCalories;
    }
    
    public void setTotalProtein(double totalProtein) {
        this.totalProtein = totalProtein;
    }
    public double getTotalProtein() {
        return totalProtein;
    }
    
    public void setTotalFat(double totalFat) {
        this.totalFat = totalFat;
    }
    public double getTotalFat() {
        return totalFat;
    }
    
    public void setTotalCarbs(double totalCarbs) {
        this.totalCarbs = totalCarbs;
    }
    public double getTotalCarbs() {
        return totalCarbs;
    }
    
}
