import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.time.LocalDateTime;
import java.util.*;
import java.sql.*;

public class MealLoggingPage extends JFrame {
    private int userId;
    private JTextField mealNameField;
    private JComboBox<String> foodSelector;
    private JTextField quantityField;
    private JTextArea nutrientDisplay;
    private JTable mealTable;
    private DefaultTableModel mealTableModel;
    private MealBuilder builder = new MealBuilder();
    private Map<String, Double> cumulativeNutrients = new HashMap<>();

    public MealLoggingPage(int userId) {
        this.userId = userId;

        setTitle("NutriSci - Meal Logging");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Meal Information"));

        inputPanel.add(new JLabel("Meal Name:"));
        mealNameField = new JTextField();
        inputPanel.add(mealNameField);

        inputPanel.add(new JLabel("Select Ingredient:"));
        List<String> ingredients = fetchCommonIngredientsFromDB();
        foodSelector = new JComboBox<>(ingredients.toArray(new String[0]));
        inputPanel.add(foodSelector);

        inputPanel.add(new JLabel("Quantity (g):"));
        quantityField = new JTextField();
        inputPanel.add(quantityField);

        add(inputPanel, BorderLayout.NORTH);

        mealTableModel = new DefaultTableModel(new String[]{"Ingredient", "Quantity (g)"}, 0);
        mealTable = new JTable(mealTableModel);
        add(new JScrollPane(mealTable), BorderLayout.CENTER);

        nutrientDisplay = new JTextArea(10, 20);
        nutrientDisplay.setEditable(false);
        nutrientDisplay.setBorder(BorderFactory.createTitledBorder("Cumulative Nutrient Summary"));
        add(new JScrollPane(nutrientDisplay), BorderLayout.EAST);

        JPanel btnPanel = new JPanel(new GridLayout(1, 3, 10, 10));

        JButton addBtn = new JButton("Add Ingredient");
        addBtn.addActionListener(e -> handleAddIngredient());

        JButton logBtn = new JButton("Log Meal");
        logBtn.addActionListener(e -> handleLogMeal());

        JButton nutrientPageBtn = new JButton("Go to Nutrient Analysis");
        nutrientPageBtn.addActionListener(e -> {
            if (cumulativeNutrients.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please log at least one meal first.");
            } else {
                dispose();
                new NutrientAnalysisPage(cumulativeNutrients, userId);
            }
        });

        JButton backBtn = new JButton("Back to Main Menu");
        backBtn.addActionListener(e -> {
            new MainMenuPage(userId);
            dispose();
        });

        btnPanel.add(addBtn);
        btnPanel.add(logBtn);
        btnPanel.add(nutrientPageBtn);
        btnPanel.add(backBtn);

        add(btnPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    private void handleAddIngredient() {
        String ingredient = (String) foodSelector.getSelectedItem();
        String quantityStr = quantityField.getText();

        try {
            int quantity = Integer.parseInt(quantityStr);
            builder.addIngredient(ingredient, quantity);
            mealTableModel.addRow(new Object[]{ingredient, quantity});
            updateNutrientInfo();
            quantityField.setText("");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid quantity. Please enter a number.");
        }
    }

    private void updateNutrientInfo() {
        StringBuilder sb = new StringBuilder("Cumulative Nutrients:\n");
        for (Map.Entry<String, Double> entry : cumulativeNutrients.entrySet()) {
            sb.append(entry.getKey()).append(": ")
              .append(String.format("%.2f", entry.getValue())).append("\n");
        }
        nutrientDisplay.setText(sb.toString());
    }

    private void handleLogMeal() {
        String mealName = mealNameField.getText();
        if (mealName.isEmpty() || builder.getIngredients().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Meal name and at least one ingredient are required.");
            return;
        }

        Meal meal = builder.build();
        meal.setMealName(mealName);
        meal.setMealTime(LocalDateTime.now());
        meal.setUserId(userId);

        try {
            MealDAO dao = new MealDAO();
            boolean success = dao.saveMeal(meal);

            if (success) {
                JOptionPane.showMessageDialog(this, "Meal saved successfully!");

                Map<String, Double> nutrients = builder.calculateTotalNutrients();
                for (Map.Entry<String, Double> entry : nutrients.entrySet()) {
                    cumulativeNutrients.merge(entry.getKey(), entry.getValue(), Double::sum);
                }

                builder = new MealBuilder();
                mealTableModel.setRowCount(0);
                mealNameField.setText("");
                quantityField.setText("");

                updateNutrientInfo();
            } else {
                JOptionPane.showMessageDialog(this, "Error saving meal.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error while saving meal.");
        }
    }

    private List<String> fetchCommonIngredientsFromDB() {
        List<String> ingredientList = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/nutrisci_db", "root", "0767")) {

            String sql = "SELECT FoodDescription FROM FoodName WHERE FoodDescription IN (" +
                         "'Cheese, cheddar', 'Chicken, broiler, meat only, roasted', 'Apple, raw, with skin', " +
                         "'Banana, raw', 'Carrot, raw', 'Potatoes, mashed, with milk and butter', 'Bread, white', " +
                         "'Egg, whole, cooked, scrambled or omelet', 'Rice, white, cooked', 'Milk, 2%')";

            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ingredientList.add(rs.getString("FoodDescription"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to fetch ingredients from database.");
        }
        return ingredientList;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MealLoggingPage(1));
    }
}
