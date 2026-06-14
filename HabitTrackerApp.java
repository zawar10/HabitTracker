import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class HabitTrackerApp extends JFrame {

    private List<Habit> habits = new ArrayList<>();

    private JTable table;
    private DefaultTableModel model;

    public HabitTrackerApp() {
        setTitle("Habit Tracker System");
        setSize(820, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        initUI();
        setVisible(true);
    }

    private void initUI() {

        // TOP PANEL
        JPanel top = new JPanel();
        top.setBackground(new Color(235, 235, 235));
        top.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel title = new JLabel("Habit Tracker Dashboard");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        top.add(title);

        model = new DefaultTableModel(new Object[]{"Habit", "Status"}, 0);
        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);

        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(Color.WHITE);

        JButton addBtn = new JButton("Add Habit");
        JButton deleteBtn = new JButton("Delete");
        JButton doneBtn = new JButton("Mark Done");
        JButton resetBtn = new JButton("Reset Day");

        btnPanel.add(addBtn);
        btnPanel.add(deleteBtn);
        btnPanel.add(doneBtn);
        btnPanel.add(resetBtn);

        addBtn.addActionListener(e -> new AddHabitScreen(this));
        deleteBtn.addActionListener(e -> deleteHabit());
        doneBtn.addActionListener(e -> markDone());
        resetBtn.addActionListener(e -> resetDay());

        add(top, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);
    }

    public void addHabit(String name) {
        try {
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Habit name cannot be empty");
            }

            habits.add(new Habit(name.trim()));
            refreshTable();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteHabit() {
        try {
            int row = table.getSelectedRow();
            if (row == -1) throw new Exception("Please select a habit to delete");

            habits.remove(row);
            refreshTable();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void markDone() {
        try {
            int row = table.getSelectedRow();
            if (row == -1) throw new Exception("Select a habit first");

            habits.get(row).markDone();
            refreshTable();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void resetDay() {
        for (Habit h : habits) {
            h.reset();
        }
        refreshTable();
    }

    public void refreshTable() {
        model.setRowCount(0);

        for (Habit h : habits) {
            model.addRow(new Object[]{
                    h.name,
                    h.doneToday ? "Done" : "Pending"
            });
        }
    }

    public static class Habit {
        String name;
        boolean doneToday;
        boolean[] history = new boolean[7];

        public Habit(String name) {
            this.name = name;
        }

        public void markDone() {
            doneToday = true;
            history[6] = true;
        }

        public void reset() {
            System.arraycopy(history, 1, history, 0, 6);
            history[6] = false;
            doneToday = false;
        }
    }

    public static void main(String[] args) {
        new HabitTrackerApp();
    }
}