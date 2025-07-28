import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GoalSetterGUI extends JFrame {
    private final GoalController controller;
    private final DefaultListModel<String> goalListModel = new DefaultListModel<>();
    private final JList<String> goalList = new JList<>(goalListModel);

    private final JComboBox<String> nutrientCombo;
    private final JComboBox<String> directionCombo;
    private final JComboBox<String> modifierCombo;

    private final int userId;

    public GoalSetterGUI(int userId) {
        this.userId = userId;
        this.controller = new GoalController(userId);

        setTitle("Set Your Nutrition Goal");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] nutrients = {
                "FAT (TOTAL LIPIDS)", "CHOLESTEROL", "SUGARS, TOTAL",
                "ENERGY (KILOCALORIES)", "FATTY ACIDS, SATURATED, TOTAL"
        };
        String[] directions = {"Increase", "Decrease"};
        String[] modifiers = {"Slightly", "By a lot", "By grams", "By percent"};

        nutrientCombo = new JComboBox<>(nutrients);
        directionCombo = new JComboBox<>(directions);
        modifierCombo = new JComboBox<>(modifiers);

        // Goal input panel
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Build Your Goal"));
        inputPanel.add(new JLabel("Nutrient:"));
        inputPanel.add(nutrientCombo);
        inputPanel.add(new JLabel("Direction:"));
        inputPanel.add(directionCombo);
        inputPanel.add(new JLabel("Modifier:"));
        inputPanel.add(modifierCombo);

        // Scrollable list for current goal (only one)
        JScrollPane scrollPane = new JScrollPane(goalList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Current Goal"));

        // Buttons
        JButton addGoalButton = new JButton("Add / Replace Goal");
        JButton removeGoalButton = new JButton("Remove Current Goal");
        JButton mealLoggingButton = new JButton("Go to Meal Logging");
        JButton mainMenuButton = new JButton("Main Menu");

        // Button actions
        addGoalButton.addActionListener(e -> addOrReplaceGoal());
        removeGoalButton.addActionListener(e -> removeSelectedGoal());
        mealLoggingButton.addActionListener(e -> {
            dispose();
            new MealLoggingPage(userId);
        });
        mainMenuButton.addActionListener(e -> {
            dispose();
            new MainMenuPage(userId);
        });

        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPanel.add(addGoalButton);
        buttonPanel.add(removeGoalButton);
        buttonPanel.add(mealLoggingButton);
        buttonPanel.add(mainMenuButton);

        // Layout
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
        loadPreviousGoals();
    }

    private void addOrReplaceGoal() {
        if (controller.getGoalCount() >= 1) {
            int option = JOptionPane.showConfirmDialog(this,
                    "You already have a goal set. Do you want to replace it?",
                    "Replace Goal", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.NO_OPTION) {
                return;
            } else {
                // Remove existing goal before adding new
                if (!controller.clearGoals()) {
                    showError("Failed to clear existing goal. Cannot add new goal.");
                    return;
                }
                goalListModel.clear();
            }
        }

        String nutrient = (String) nutrientCombo.getSelectedItem();
        String direction = (String) directionCombo.getSelectedItem();
        String modifier = (String) modifierCombo.getSelectedItem();

        // Amount mapping for modifiers; you can extend this logic as needed
        String amount = switch (modifier) {
            case "Slightly" -> "5";
            case "By a lot" -> "15";
            default -> "0"; // For "By grams" and "By percent", consider adding UI input for custom amounts later
        };

        UserGoal goal = new UserGoal(userId, nutrient, direction, modifier, amount);

        if (controller.addGoal(goal)) {
            goalListModel.addElement(goal.toReadableString());
            showMessage("Goal set successfully.");
        } else {
            showError("Failed to add goal.");
        }
    }

    private void removeSelectedGoal() {
        if (controller.getGoalCount() == 0) {
            showError("No goal to remove.");
            return;
        }

        ArrayList<UserGoal> currentGoals = controller.getGoals();
        UserGoal goalToRemove = currentGoals.get(0);  // Since only one goal exists
        boolean removed = UserGoalDAO.deleteSpecificGoal(goalToRemove);
        if (removed) {
            controller.removeGoal(0);
            goalListModel.clear();
            showMessage("Goal successfully removed.");
        } else {
            showError("Failed to remove goal from database.");
        }
    }

    private void loadPreviousGoals() {
        for (UserGoal g : controller.getGoals()) {
            goalListModel.addElement(g.toReadableString());
        }
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Message", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GoalSetterGUI(1));
    }
}
