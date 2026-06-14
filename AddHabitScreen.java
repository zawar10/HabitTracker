import javax.swing.*;
import java.awt.*;

public class AddHabitScreen extends JFrame {

    public AddHabitScreen(HabitTrackerApp app) {

        setTitle("Add New Habit");
        setSize(320, 180);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        getContentPane().setBackground(Color.WHITE);

        JLabel label = new JLabel("Enter Habit Name:");
        JTextField input = new JTextField(20);

        JButton save = new JButton("Save");
        JButton cancel = new JButton("Cancel");

        add(label);
        add(input);
        add(save);
        add(cancel);

        save.addActionListener(e -> {
            app.addHabit(input.getText());
            app.refreshTable();
            dispose();
        });

        cancel.addActionListener(e -> dispose());

        setVisible(true);
    }
}