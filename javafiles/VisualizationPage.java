import javax.swing.*;
import java.awt.*;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

// Chart Factory Interface
interface ChartCreator {
    JFreeChart createChart(Map<String, Double> nutrientData);
}

// BarChartFactory using Factory Method
class BarChartFactory implements ChartCreator {
    @Override
    public JFreeChart createChart(Map<String, Double> nutrientData) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String, Double> entry : nutrientData.entrySet()) {
            dataset.addValue(entry.getValue(), "Nutrient", entry.getKey());
        }

        return ChartFactory.createBarChart(
                "Nutrient Summary",
                "Nutrient",
                "Amount (g or mg)",
                dataset
        );
    }
}

// Visualization Page
public class VisualizationPage extends JFrame {
    private final int userId;

    public VisualizationPage(Map<String, Double> nutrientData, int userId) {
        this.userId = userId;

        setTitle("Nutrient Visualization");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        ChartCreator factory = new BarChartFactory();
        JFreeChart chart = factory.createChart(nutrientData);
        ChartPanel chartPanel = new ChartPanel(chart);
        add(chartPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton backBtn = new JButton("Back to Main Menu");
        backBtn.addActionListener(e -> {
            dispose();
            new MainMenuPage(userId);
        });
        buttonPanel.add(backBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}
