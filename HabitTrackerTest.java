import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class HabitTrackerTest {

    @Before
    public void setUp() {
        HabitTrackerApp.habits.clear();
    }

    @Test
    public void testProgress_allDone() {
        assertEquals(100, HabitTrackerApp.calculateProgress(5, 5));
    }

    @Test
    public void testProgress_noneDone() {
        assertEquals(0, HabitTrackerApp.calculateProgress(0, 5));
    }

    @Test
    public void testProgress_someDone() {
        assertEquals(60, HabitTrackerApp.calculateProgress(3, 5));
    }

    @Test
    public void testProgress_zeroTotal() {
        assertEquals(0, HabitTrackerApp.calculateProgress(0, 0));
    }

    @Test
    public void testStreak_fromZero() {
        assertEquals(1, HabitTrackerApp.incrementStreak(0));
    }

    @Test
    public void testStreak_continuing() {
        assertEquals(7, HabitTrackerApp.incrementStreak(6));
    }

    @Test
    public void testStreak_neverNegative() {
        assertTrue(HabitTrackerApp.incrementStreak(0) > 0);
    }


    @Test
    public void testValidate_normalName() {
        assertEquals("valid", HabitTrackerApp.validateHabitName("Morning Run"));
    }

    @Test
    public void testValidate_emptyString() {
        assertNotEquals("valid", HabitTrackerApp.validateHabitName(""));
    }

    @Test
    public void testValidate_nullInput() {
        assertNotEquals("valid", HabitTrackerApp.validateHabitName(null));
    }

    @Test
    public void testValidate_tooLong() {
        String longName = "A".repeat(51);
        assertNotEquals("valid", HabitTrackerApp.validateHabitName(longName));
    }

    @Test
    public void testValidate_exactLimit() {
        String okName = "A".repeat(50);
        assertEquals("valid", HabitTrackerApp.validateHabitName(okName));
    }

    @Test
    public void testValidate_whitespaceOnly() {
        assertNotEquals("valid", HabitTrackerApp.validateHabitName("   "));
    }

    @Test
    public void testDuplicate_notFound() {
        assertFalse(HabitTrackerApp.isDuplicate("Morning Run"));
    }

    @Test
    public void testDuplicate_found() {
        HabitTrackerApp.habits.add(new String[]{ "Morning Run", "0", "Pending" });
        assertTrue(HabitTrackerApp.isDuplicate("Morning Run"));
    }

    @Test
    public void testDuplicate_caseInsensitive() {
        HabitTrackerApp.habits.add(new String[]{ "Morning Run", "0", "Pending" });
        assertTrue(HabitTrackerApp.isDuplicate("morning run"));
    }

    @Test
    public void testDuplicate_differentName() {
        HabitTrackerApp.habits.add(new String[]{ "Morning Run", "0", "Pending" });
        assertFalse(HabitTrackerApp.isDuplicate("Evening Walk"));
    }
}