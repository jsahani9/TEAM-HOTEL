import java.util.HashMap;
import java.util.Map;

public class NutrientThresholds {
    public static Map<String, Double> getThresholds() {
        Map<String, Double> thresholds = new HashMap<>();
        thresholds.put("SODIUM", 2300.0);
        thresholds.put("PROTEIN", 50.0);
        thresholds.put("FAT (TOTAL LIPIDS)", 70.0);
        thresholds.put("CHOLESTEROL", 300.0);
        thresholds.put("SUGARS, TOTAL", 50.0);
        thresholds.put("ENERGY (KILOCALORIES)", 2000.0);
        thresholds.put("FATTY ACIDS, SATURATED, TOTAL", 20.0);
        // Add more if needed
        return thresholds;
    }
}
