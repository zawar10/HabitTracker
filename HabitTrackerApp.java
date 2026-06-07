import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class HabitTrackerApp extends JFrame {

    static ArrayList<String[]> habits = new ArrayList<>();

    private DefaultTableModel tableModel;
    private JTable habitTable;
    private JTextField nameField;
    private JLabel streakLabel;
    private JLabel progressLabel;

    public HabitTrackerApp() {
        setTitle("Habit Tracker");
        setSize(650, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        add(buildTopPanel(),    BorderLayout.NORTH);
        add(buildTablePanel(),  BorderLayout.CENTER);
        add(buildBottomPanel(), BorderLayout.SOUTH);

        loadSampleData();
        refreshTable();
    }

    private JPanel buildTopPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));

        JLabel title = new JLabel("🔥 Habit Tracker", SwingConstants.LEFT);
        title.setFont(new Font("Arial", Font.BOLD, 20));

        progressLabel = new JLabel("", SwingConstants.RIGHT);
        progressLabel.setFont(new Font("Arial", Font.PLAIN, 13));

        panel.add(title,         BorderLayout.WEST);
        panel.add(progressLabel, BorderLayout.EAST);
        return panel;
    }

    private JPanel buildTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        String[] columns = { "#", "Habit Name", "Streak", "Status" };
        tableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        habitTable = new JTable(tableModel);
        habitTable.setRowHeight(28);
        habitTable.getColumnModel().getColumn(0).setMaxWidth(40);
        habitTable.getColumnModel().getColumn(2).setMaxWidth(80);
        habitTable.getColumnModel().getColumn(3).setMaxWidth(90);
        habitTable.setFont(new Font("Arial", Font.PLAIN, 13));
        habitTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));

        panel.add(new JScrollPane(habitTable), BorderLayout.CENTER);
        return panel;
    }

    private JPanel buildBottomPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // ── Row 1: input field ──
        JPanel inputRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        inputRow.add(new JLabel("Habit name:"));
        nameField = new JTextField(20);
        inputRow.add(nameField);

        // ── Row 2: buttons ──
        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton addBtn    = new JButton("Add Habit");
        JButton doneBtn   = new JButton("Mark Done");
        JButton deleteBtn = new JButton("Delete");
        JButton resetBtn  = new JButton("Reset All");

        streakLabel = new JLabel("  Select a habit to see streak.");
        streakLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        streakLabel.setForeground(Color.DARK_GRAY);

        btnRow.add(addBtn);
        btnRow.add(doneBtn);
        btnRow.add(deleteBtn);
        btnRow.add(resetBtn);
        btnRow.add(streakLabel);

        panel.add(inputRow);
        panel.add(btnRow);

        addBtn.addActionListener(e -> handleAddHabit());
        doneBtn.addActionListener(e -> handleMarkDone());
        deleteBtn.addActionListener(e -> handleDelete());
        resetBtn.addActionListener(e -> handleReset());

        habitTable.getSelectionModel().addListSelectionListener(e -> {
            int row = habitTable.getSelectedRow();
            if (row >= 0 && row < habits.size()) {
                String streak = habits.get(row)[1];
                streakLabel.setText("  🔥 Streak: " + streak + " day(s)");
            }
        });

        nameField.addActionListener(e -> handleAddHabit());

        return panel;
    }

    private void handleAddHabit() {
        try {
            String name = nameField.getText().trim();

            if (name.isEmpty()) {
                throw new IllegalArgumentException("Habit name cannot be empty.");
            }
            if (name.length() > 50) {
                throw new IllegalArgumentException("Habit name too long (max 50 characters).");
            }
            if (isDuplicate(name)) {
                throw new IllegalArgumentException("\"" + name + "\" already exists.");
            }

            habits.add(new String[]{ name, "0", "Pending" });
            nameField.setText("");
            refreshTable();

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this,
                ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleMarkDone() {
        int row = habitTable.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this,
                "Please select a habit first.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String[] habit = habits.get(row);
        if (habit[2].equals("Done")) {
            JOptionPane.showMessageDialog(this,
                "Already marked done today!", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        habit[1] = String.valueOf(Integer.parseInt(habit[1]) + 1);
        habit[2] = "Done";
        refreshTable();
        streakLabel.setText("  🔥 Streak: " + habit[1] + " day(s)");
    }

    private void handleDelete() {
        int row = habitTable.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this,
                "Please select a habit to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
            "Delete \"" + habits.get(row)[0] + "\"?",
            "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            habits.remove(row);
            refreshTable();
            streakLabel.setText("  Select a habit to see streak.");
        }
    }

    private void handleReset() {
        if (habits.isEmpty()) return;

        int confirm = JOptionPane.showConfirmDialog(this,
            "Reset all habits to Pending for today?",
            "New Day Reset", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            for (String[] h : habits) {
                h[2] = "Pending";
            }
            refreshTable();
        }
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        int done = 0;
        for (int i = 0; i < habits.size(); i++) {
            String[] h = habits.get(i);
            tableModel.addRow(new Object[]{ i + 1, h[0], h[1] + " 🔥", h[2] });
            if (h[2].equals("Done")) done++;
        }
        updateProgress(done, habits.size());
    }

    private void updateProgress(int done, int total) {
        if (total == 0) {
            progressLabel.setText("No habits yet");
        } else {
            int pct = calculateProgress(done, total);
            progressLabel.setText("Today: " + done + "/" + total + " done  (" + pct + "%)");
        }
    }

    public static boolean isDuplicate(String name) {
        for (String[] h : habits) {
            if (h[0].equalsIgnoreCase(name)) return true;
        }
        return false;
    }

    public static int calculateProgress(int done, int total) {
        if (total == 0) return 0;
        return (int) Math.round((done * 100.0) / total);
    }

    public static int incrementStreak(int current) {
        return current + 1;
    }

    public static String validateHabitName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return "Habit name cannot be empty.";
        }
        if (name.trim().length() > 50) {
            return "Habit name too long (max 50 characters).";
        }
        return "valid";
    }

    private void loadSampleData() {
        habits.add(new String[]{ "Morning Run",     "6", "Done"    });
        habits.add(new String[]{ "Read 20 Pages",   "4", "Done"    });
        habits.add(new String[]{ "Drink 8 Glasses", "2", "Done"    });
        habits.add(new String[]{ "Meditate 10 min", "0", "Pending" });
        habits.add(new String[]{ "Write Journal",   "0", "Pending" });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new HabitTrackerApp().setVisible(true);
        });
    }
}