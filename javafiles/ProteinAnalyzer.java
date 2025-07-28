import java.sql.*;

public class ProteinAnalyzer implements NutrientAnalyzer {
    @Override
    public String analyze(int foodId) {
        String result = "";
        try {
            // Load JDBC driver (required for some environments)
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/nutrisci_db", "root", "0767"
            );

            // 203 is typically the Nutrient_ID for Protein in CNF
            String sql = "SELECT NutrientValue FROM nutrientamount WHERE FoodID = ? AND NutrientID = 203";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, foodId);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                double val = rs.getDouble("NutrientValue");
                result = "Protein: " + val + " g";
            } else {
                result = "No protein data found for Food ID " + foodId;
            }

            conn.close();
        } catch (Exception e) {
            result = "Error: " + e.getMessage();
        }
        return result;
    }
}
