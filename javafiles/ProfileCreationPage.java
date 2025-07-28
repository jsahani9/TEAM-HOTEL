import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ProfileCreationPage extends JFrame {
    private int userId;

    public ProfileCreationPage(int userId) {
        this.userId = userId;

        setTitle("Create Profile");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(9, 2));

        JTextField nameField = new JTextField();
        JTextField ageField = new JTextField();
        JTextField genderField = new JTextField();
        JTextField weightField = new JTextField();
        JTextField heightField = new JTextField();
        JTextField activityField = new JTextField();
        JTextField goalField = new JTextField();

        add(new JLabel("Name:")); add(nameField);
        add(new JLabel("Age:")); add(ageField);
        add(new JLabel("Gender:")); add(genderField);
        add(new JLabel("Weight (kg):")); add(weightField);
        add(new JLabel("Height (cm):")); add(heightField);
        add(new JLabel("Activity Level:")); add(activityField);
        add(new JLabel("Goal:")); add(goalField);

        JButton createButton = new JButton("Create Profile");
        add(new JLabel("")); // spacer
        add(createButton);

        createButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = nameField.getText();
                    int age = Integer.parseInt(ageField.getText());
                    String gender = genderField.getText();
                    double weight = Double.parseDouble(weightField.getText());
                    double height = Double.parseDouble(heightField.getText());
                    String activity = activityField.getText();
                    String goal = goalField.getText();

                    Profile profile = new Profile(userId, name, age, gender, weight, height, activity, goal);

                    if (ProfileDAO.saveProfile(profile)) {
                        JOptionPane.showMessageDialog(null, "Profile created successfully!");
                        new MainMenuPage(userId).setVisible(true); // open next page
                        dispose(); // close profile creation page
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to create profile.");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Invalid input. Please try again.");
                }
            }
        });

        setVisible(true);
    }
}
