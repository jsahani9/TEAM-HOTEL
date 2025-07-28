import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class MainMenuPage extends JFrame {
    private final int userId;

    public MainMenuPage(int userId) {
        this.userId = userId;

        setTitle("Main Menu");
        setSize(400, 550); // Increased height to fit 6 buttons
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel headingLabel = new JLabel("Welcome to NutriSci!", SwingConstants.CENTER);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 18));

        JButton goalSetterButton = new JButton("Set Nutrition Goals");
        JButton mealLogButton = new JButton("Log a Meal");
        JButton nutrientAnalysisButton = new JButton("View Nutrient Analysis");
        JButton foodSwapButton = new JButton("Find Food Swaps");
        JButton visualizeButton = new JButton("Visualize Nutrients");
        JButton foodGuideButton = new JButton("Canada Food Guide Comparison"); // ✅ New

        // --- Button Actions ---
        goalSetterButton.addActionListener(e -> {
            dispose();
            new GoalSetterGUI(userId);
        });

        mealLogButton.addActionListener(e -> {
            dispose();
            new MealLoggingPage(userId);
        });

        nutrientAnalysisButton.addActionListener(e -> {
            Map<String, Double> nutrientTotals = NutrientAnalysisHelper.getMostRecentMealNutrients(userId);
            if (nutrientTotals == null || nutrientTotals.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please log a meal first.");
            } else {
                dispose();
                new NutrientAnalysisPage(nutrientTotals, userId);
            }
        });

        foodSwapButton.addActionListener(e -> {
            Map<String, Double> nutrientTotals = NutrientAnalysisHelper.getMostRecentMealNutrients(userId);
            if (nutrientTotals == null || nutrientTotals.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please log a meal first.");
            } else {
                dispose();
                new FoodSwapPage(nutrientTotals, userId);
            }
        });

        visualizeButton.addActionListener(e -> {
            Map<String, Double> nutrientTotals = NutrientAnalysisHelper.getMostRecentMealNutrients(userId);
            if (nutrientTotals == null || nutrientTotals.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please log a meal first.");
            } else {
                dispose();
                new VisualizationPage(nutrientTotals, userId);
            }
        });

        foodGuideButton.addActionListener(e -> {
            dispose();
            new MainWindow().setVisible(true); // ✅ Launch MainWindow (Canada Food Guide UI)
        });

        // Add buttons to panel
        JPanel buttonPanel = new JPanel(new GridLayout(6, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        buttonPanel.add(goalSetterButton);
        buttonPanel.add(mealLogButton);
        buttonPanel.add(nutrientAnalysisButton);
        buttonPanel.add(foodSwapButton);
        buttonPanel.add(visualizeButton);
        buttonPanel.add(foodGuideButton); // ✅ Add button

        add(headingLabel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainMenuPage(1));
    }
}
