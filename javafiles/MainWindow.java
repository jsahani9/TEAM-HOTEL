import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

// Strategy interface
interface FoodGuideStrategy {
    String compareFoodIntake(List<String> userFood, int servings);
}

// CFG 2007 Strategy
class CFG2007Strategy implements FoodGuideStrategy {
    @Override
    public String compareFoodIntake(List<String> userFood, int servings) {
        int recommendedServings = 5;
        return servings >= recommendedServings ?
                "Meets CFG 2007: Good job!" :
                "Below CFG 2007: Add " + (recommendedServings - servings) + " more servings.";
    }
}

// CFG 2019 Strategy
class CFG2019Strategy implements FoodGuideStrategy {
    @Override
    public String compareFoodIntake(List<String> userFood, int servings) {
        int recommendedServings = 7;
        return servings >= recommendedServings ?
                "Meets CFG 2019: Excellent!" :
                "Below CFG 2019: Add " + (recommendedServings - servings) + " more servings.";
    }
}

// Composite base class
abstract class FoodComponent {
    String name;

    public FoodComponent(String name) {
        this.name = name;
    }

    abstract void display(Graphics g, int x, int y);
}

// Leaf component
class FoodItem extends FoodComponent {
    public FoodItem(String name) {
        super(name);
    }

    @Override
    void display(Graphics g, int x, int y) {
        g.setColor(Color.GREEN);
        g.fillOval(x, y, 30, 30);
        g.setColor(Color.BLACK);
        g.drawString(name, x + 35, y + 20);
    }
}

// Composite component
class FoodCategory extends FoodComponent {
    List<FoodComponent> children = new ArrayList<>();

    public FoodCategory(String name) {
        super(name);
    }

    public void add(FoodComponent component) {
        children.add(component);
    }

    public void remove(FoodComponent component) {
        children.remove(component);
    }

    @Override
    void display(Graphics g, int x, int y) {
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(x, y, 150, 100);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, 150, 100);
        g.drawString(name, x + 10, y + 15);

        int childY = y + 30;
        for (FoodComponent child : children) {
            child.display(g, x + 10, childY);
            childY += 40;
        }
    }
}

// Canada Food Guide Visualizer Window
public class MainWindow extends JFrame {
    private FoodGuideStrategy strategy;
    private JTextField foodInput;
    private JTextField servingsInput;
    private JTextArea resultArea;
    private JPanel platePanel;
    private FoodCategory currentPlate;

    public MainWindow() {
        setTitle("Canada Food Guide Comparison");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLayout(new BorderLayout());

        // Input Panel
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(new JLabel("Enter Foods (comma-separated):"));
        foodInput = new JTextField(20);
        inputPanel.add(foodInput);

        inputPanel.add(new JLabel("Enter Servings:"));
        servingsInput = new JTextField(5);
        inputPanel.add(servingsInput);

        JComboBox<String> strategySelector = new JComboBox<>(new String[]{"CFG 2007", "CFG 2019"});
        strategySelector.addActionListener(e -> {
            String selected = (String) strategySelector.getSelectedItem();
            strategy = selected.equals("CFG 2007") ? new CFG2007Strategy() : new CFG2019Strategy();
        });
        inputPanel.add(strategySelector);

        JButton compareButton = new JButton("Compare");
        compareButton.addActionListener(e -> compareFood());
        inputPanel.add(compareButton);

        // Result TextArea
        resultArea = new JTextArea(5, 50);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);

        // Visualization Panel
        platePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                displayPlate(g);
            }
        };
        platePanel.setPreferredSize(new Dimension(600, 250));

        // Default Strategy
        strategy = new CFG2019Strategy();

        // Layout
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(platePanel, BorderLayout.SOUTH);
    }

    private void compareFood() {
        try {
            String[] foods = foodInput.getText().split(",");
            List<String> userFood = new ArrayList<>();
            for (String food : foods) userFood.add(food.trim());

            int servings = Integer.parseInt(servingsInput.getText());
            String result = strategy.compareFoodIntake(userFood, servings);
            resultArea.setText(result);

            updatePlate(userFood);
        } catch (NumberFormatException ex) {
            resultArea.setText("Please enter a valid number of servings.");
        }
    }

    private void updatePlate(List<String> userFood) {
        FoodCategory plate = new FoodCategory("Daily Plate");

        FoodCategory veggies = new FoodCategory("Vegetables");
        veggies.add(new FoodItem(userFood.size() > 0 ? userFood.get(0) : "None"));
        plate.add(veggies);

        FoodCategory proteins = new FoodCategory("Proteins");
        proteins.add(new FoodItem(userFood.size() > 1 ? userFood.get(1) : "None"));
        plate.add(proteins);

        FoodCategory grains = new FoodCategory("Whole Grains");
        grains.add(new FoodItem(userFood.size() > 2 ? userFood.get(2) : "None"));
        plate.add(grains);

        if (userFood.size() > 3) {
            FoodCategory others = new FoodCategory("Others");
            for (int i = 3; i < userFood.size(); i++) {
                others.add(new FoodItem(userFood.get(i)));
            }
            plate.add(others);
        }

        this.currentPlate = plate;
        platePanel.repaint();
    }

    private void displayPlate(Graphics g) {
        if (currentPlate != null) {
            int panelWidth = platePanel.getWidth();
            int panelHeight = platePanel.getHeight();
            int numCategories = currentPlate.children.size();
            int sectionWidth = 150;
            int sectionHeight = 100;
            int totalWidth = numCategories * (sectionWidth + 20) - 20;
            int startX = (panelWidth - totalWidth) / 2;
            int y = (panelHeight - sectionHeight) / 2;

            for (int i = 0; i < numCategories; i++) {
                FoodComponent child = currentPlate.children.get(i);
                child.display(g, startX + i * (sectionWidth + 20), y);
            }

            g.setColor(Color.WHITE);
            g.fillRect(startX, y - 40, 150, 30);
            g.setColor(Color.BLACK);
            g.drawRect(startX, y - 40, 150, 30);
            g.drawString("Daily Plate", startX + 10, y - 20);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainWindow window = new MainWindow();
            window.setVisible(true);
        });
    }
}
