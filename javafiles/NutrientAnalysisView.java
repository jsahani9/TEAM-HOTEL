
import javax.swing.*;

public class NutrientAnalysisView implements NutrientView {
    private JTextArea displayArea;

    public NutrientAnalysisView(JTextArea area) {
        this.displayArea = area;
    }

    public void update(String data) {
        displayArea.setText(data);
    }
}
