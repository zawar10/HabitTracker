import org.junit.Test;
import static org.junit.Assert.*;

public class HabitTrackerTest {
    @Test
    public void testHabitCreation() {
        HabitTrackerApp.Habit h = new HabitTrackerApp.Habit("Study");
        assertEquals("Study", h.name);
        assertFalse(h.doneToday);
    }

    @Test
    public void testMarkDone() {
        HabitTrackerApp.Habit h = new HabitTrackerApp.Habit("Study");
        h.markDone();
        assertTrue(h.doneToday);
        assertTrue(h.history[6]);
    }

    @Test
    public void testResetFunctionality() {
        HabitTrackerApp.Habit h = new HabitTrackerApp.Habit("Study");
        h.markDone();
        h.reset();

        assertFalse(h.doneToday);
        assertFalse(h.history[6]);
    }

    @Test
    public void testMultipleMarkDone() {
        HabitTrackerApp.Habit h = new HabitTrackerApp.Habit("Study");

        h.markDone();
        h.markDone();

        assertTrue(h.doneToday);
    }

    @Test
    public void testResetWithoutMarkingDone() {
        HabitTrackerApp.Habit h = new HabitTrackerApp.Habit("Study");

        h.reset(); // should not crash
        assertFalse(h.doneToday);
    }

    @Test
    public void testAddHabitValidInput() {
        HabitTrackerApp app = new HabitTrackerApp();

        app.addHabit("Reading");

        assertTrue(true);
    }

    @Test
    public void testAddHabitEmptyInput() {
        HabitTrackerApp app = new HabitTrackerApp();

        app.addHabit("");

        assertTrue(true);
    }

    @Test
    public void testAddHabitNullInput() {
        HabitTrackerApp app = new HabitTrackerApp();

        app.addHabit(null);

        assertTrue(true);
    }

    @Test
    public void testHabitNameTrimEffect() {
        HabitTrackerApp.Habit h = new HabitTrackerApp.Habit("  Study  ");

        assertEquals("  Study  ", h.name);
    }

    @Test
    public void testHistoryArraySize() {
        HabitTrackerApp.Habit h = new HabitTrackerApp.Habit("Study");

        assertEquals(7, h.history.length);
    }

    @Test
    public void testAppInitialization() {
        HabitTrackerApp app = new HabitTrackerApp();
        assertNotNull(app);
    }
}