import java.util.List;

public class HighProteinStrategy implements SwapStrategy {
    @Override
    public List<FoodSwap> getSwaps(String foodName, String nutrient) {
        return FoodSwapDatabase.getSwapsByNutrient(foodName, "Protein", "HIGH");
    }
}

