import java.util.List;

public class LowSodiumStrategy implements SwapStrategy {
    @Override
    public List<FoodSwap> getSwaps(String foodName, String nutrient) {
        return FoodSwapDatabase.getSwapsByNutrient(foodName, "Sodium", "LOW");
    }
}


