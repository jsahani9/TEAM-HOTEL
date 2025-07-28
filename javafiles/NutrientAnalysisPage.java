import javax.swing.*;
import java.awt.BorderLayout;
import java.util.*;

public class NutrientAnalysisPage extends JFrame implements DataSubject {
    private JTextField foodIdField;
    private JComboBox<String> analyzerChoice;
    private JTextArea resultArea;
    private NutrientAnalyzer analyzer;
    private List<NutrientView> observers = new ArrayList<>();
    private int userId;

    // --- Constructor 1: Manual food ID input + observer/strategy ---
    public NutrientAnalysisPage() {
        setTitle("Nutrient Analysis");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 400);
        setLayout(new BorderLayout());

        // Top panel for manual entry
        JPanel topPanel = new JPanel();
        foodIdField = new JTextField(10);
        analyzerChoice = new JComboBox<>(new String[]{"Calories", "Protein"});
        JButton analyzeBtn = new JButton("Analyze");

        topPanel.add(new JLabel("Food ID:"));
        topPanel.add(foodIdField);
        topPanel.add(analyzerChoice);
        topPanel.add(analyzeBtn);

        add(topPanel, BorderLayout.NORTH);

        // Result display
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);

        // Observer pattern
        NutrientAnalysisView nav = new NutrientAnalysisView(resultArea);
        registerObserver(nav);

        // Button to go back
        JButton backButton = new JButton("Back to Main Menu");
        backButton.addActionListener(e -> dispose());

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);

        // Analysis logic
        analyzeBtn.addActionListener(e -> {
            try {
                int foodId = Integer.parseInt(foodIdField.getText().trim());
                String choice = (String) analyzerChoice.getSelectedItem();
                if ("Calories".equals(choice)) {
                    analyzer = new CaloriesAnalyzer();
                } else if ("Protein".equals(choice)) {
                    analyzer = new ProteinAnalyzer();
                }

                String result = analyzer.analyze(foodId);
                notifyObservers(result);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid food ID.");
            }
        });

        setVisible(true);
    }

    // --- Constructor 2: Auto-analysis from logged nutrients ---
    public NutrientAnalysisPage(Map<String, Double> nutrientTotals, int userId) {
        this.userId = userId;
        setTitle("Nutrient Analysis (Auto)");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 400);
        setLayout(new BorderLayout());

        // Text Area
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);

        // Nutrient display
        StringBuilder sb = new StringBuilder("Auto Nutrient Analysis:\n\n");
        for (Map.Entry<String, Double> entry : nutrientTotals.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        resultArea.setText(sb.toString());

        // Determine worst nutrient (exceeds threshold)
        String worstNutrient = null;
        double maxExcess = 0;
        Map<String, Double> thresholds = NutrientThresholds.getThresholds();

        for (Map.Entry<String, Double> entry : nutrientTotals.entrySet()) {
            String nutrient = entry.getKey();
            double value = entry.getValue();

            if (thresholds.containsKey(nutrient)) {
                double threshold = thresholds.get(nutrient);
                double excess = value - threshold;
                if (excess > maxExcess) {
                    maxExcess = excess;
                    worstNutrient = nutrient;
                }
            }
        }

        // Strategy setup
        FoodSwapContext context = new FoodSwapContext();
        if ("SODIUM".equalsIgnoreCase(worstNutrient)) {
            context.setStrategy(new LowSodiumStrategy());
        } else if ("PROTEIN".equalsIgnoreCase(worstNutrient)) {
            context.setStrategy(new HighProteinStrategy());
        }

        // Buttons
        JButton backButton = new JButton("Back to Main Menu");
        backButton.addActionListener(e -> {
            new MainMenuPage(userId);  // ✅ Navigate to Main Menu
            dispose();                 // ✅ Close current page
        });

        JButton foodSwapButton = new JButton("Suggest Food Swaps");
        foodSwapButton.addActionListener(e -> {
            dispose();
            new FoodSwapPage(nutrientTotals, userId);  // Forward data to FoodSwapPage
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(backButton);
        bottomPanel.add(foodSwapButton);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    // --- Observer pattern ---
    public void registerObserver(NutrientView observer) {
        observers.add(observer);
    }

    public void removeObserver(NutrientView observer) {
        observers.remove(observer);
    }

    public void notifyObservers(String data) {
        for (NutrientView view : observers) {
            view.update(data);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new NutrientAnalysisPage());
    }
}
