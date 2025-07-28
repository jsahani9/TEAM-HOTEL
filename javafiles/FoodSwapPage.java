import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.List;
import java.util.*;

public class FoodSwapPage extends JFrame {
    private final int userId;
    private final Map<String, Double> loggedNutrients;
    private final DefaultTableModel tableModel;
    private final JTable swapTable;
    private final FoodSwapContext context = new FoodSwapContext();

    public FoodSwapPage(Map<String, Double> loggedNutrients, int userId) {
        this.loggedNutrients = loggedNutrients;
        this.userId = userId;

        setTitle("Food Swap Suggestions");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new String[]{"Suggested Food", "Nutrient Value"}, 0);
        swapTable = new JTable(tableModel);
        add(new JScrollPane(swapTable), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton backButton = new JButton("Back to Main Menu");
        backButton.addActionListener(e -> {
            dispose();
            new MainMenuPage(userId);
        });

        JButton visualizeButton = new JButton("Visualize Nutrients");
        visualizeButton.addActionListener(e -> {
            dispose();
            new VisualizationPage(loggedNutrients, userId);
        });

        buttonPanel.add(backButton);
        buttonPanel.add(visualizeButton);

        add(buttonPanel, BorderLayout.SOUTH);

        loadAndSuggestSwaps();
        setVisible(true);
    }

    private void loadAndSuggestSwaps() {
        UserGoal goal = fetchUserGoal();
        if (goal == null) {
            JOptionPane.showMessageDialog(this, "No nutrition goal found. Please set a goal first.");
            return;
        }

        String goalNutrient = goal.getNutrient();
        String goalDirection = goal.getDirection();

        if ("Increase".equalsIgnoreCase(goalDirection)) {
            context.setStrategy(new HighNutrientStrategy());
        } else if ("Decrease".equalsIgnoreCase(goalDirection)) {
            context.setStrategy(new LowNutrientStrategy());
        } else {
            JOptionPane.showMessageDialog(this, "Unknown goal direction: " + goalDirection);
            return;
        }

        String refFood = selectReferenceFood(goalNutrient);
        List<FoodSwap> swaps = context.getSwapSuggestions(refFood, goalNutrient);

        tableModel.setRowCount(0);
        for (FoodSwap fs : swaps) {
            tableModel.addRow(new Object[]{fs.getSuggestedFood(), fs.getNutrientValue()});
        }

        setTitle(String.format("Food Swaps to %s %s (Based on %s)", goalDirection, goalNutrient, refFood));
    }

    private UserGoal fetchUserGoal() {
        GoalController controller = new GoalController(userId);
        List<UserGoal> goals = controller.getGoals();
        return goals.isEmpty() ? null : goals.get(0);
    }

    private String selectReferenceFood(String nutrient) {
        Map<String, String> fallbackFoods = Map.of(
            "SODIUM", "Cheese, brick",
            "PROTEIN", "Chicken, broiler, meat only, roasted",
            "CHOLESTEROL", "Egg, whole, cooked",
            "FAT (TOTAL LIPIDS)", "Butter, regular",
            "SUGARS, TOTAL", "Apple, raw, with skin",
            "ENERGY (KILOCALORIES)", "Bread, white",
            "FATTY ACIDS, SATURATED, TOTAL", "Butter, regular"
        );

        return fallbackFoods.getOrDefault(nutrient.toUpperCase(), "Cheese, brick");
    }

    public static void main(String[] args) {
        Map<String, Double> exampleNutrients = new HashMap<>();
        exampleNutrients.put("SODIUM", 5000.0);
        exampleNutrients.put("PROTEIN", 60.0);
        exampleNutrients.put("FAT (TOTAL LIPIDS)", 150.0);

        new FoodSwapPage(exampleNutrients, 1);
    }
}
