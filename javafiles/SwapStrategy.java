import java.util.List;

public interface SwapStrategy {
    List<FoodSwap> getSwaps(String foodName, String nutrientName);
}
