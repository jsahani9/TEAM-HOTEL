import java.util.ArrayList;
import java.util.List;

public class FoodSwapContext {
    private SwapStrategy strategy;

    public void setStrategy(SwapStrategy strategy) {
        this.strategy = strategy;
    }

    public List<FoodSwap> getSwapSuggestions(String foodName, String nutrientName) {
        if (strategy == null) return new ArrayList<>();
        return strategy.getSwaps(foodName, nutrientName);
    }
}

