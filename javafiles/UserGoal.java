public class UserGoal {
    private final int userId;
    private final String nutrient;
    private final String direction; // Increase or Decrease
    private final String modifier;  // Slightly, By a lot, By grams, By percent
    private final String amount;    // Numeric value or empty

    public UserGoal(int userId, String nutrient, String direction, String modifier, String amount) {
        this.userId = userId;
        this.nutrient = nutrient;
        this.direction = direction;
        this.modifier = modifier;
        this.amount = amount;
    }

    // --- Getters ---
    public int getUserId() {
        return userId;
    }

    public String getNutrient() {
        return nutrient;
    }

    public String getDirection() {
        return direction;
    }

    public String getModifier() {
        return modifier;
    }

    public String getAmount() {
        return amount;
    }

    // --- Readable display for GUI ---
    public String toReadableString() {
        if ("By grams".equalsIgnoreCase(modifier)) {
            return String.format("%s %s by %s grams", direction, nutrient, amount);
        } else if ("By percent".equalsIgnoreCase(modifier)) {
            return String.format("%s %s by %s%%", direction, nutrient, amount);
        } else {
            return String.format("%s %s %s", direction, nutrient, modifier.toLowerCase());
        }
    }

    // --- Optional: for debugging/logging ---
    @Override
    public String toString() {
        return String.format("UserGoal{userId=%d, nutrient='%s', direction='%s', modifier='%s', amount='%s'}",
                userId, nutrient, direction, modifier, amount);
    }
}
