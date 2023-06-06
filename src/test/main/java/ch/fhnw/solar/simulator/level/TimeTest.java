package ch.fhnw.solar.simulator.level;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testklasse für die Time-Klasse.
 */
public class TimeTest {

    /**
     * Überprüft, ob Millisekunden korrekt in Stunden umgewandelt werden.
     */
    @DisplayName("Milliseconds get converted to Hours")
    @Test
    public void testMillsToHours() {
        //given
        int mills = 21600000;

        //when
        double time = Time.millsToHours(mills);

        //then
        assertEquals(mills / (60d * 60 * 1000), time);
    }

    /**
     * Überprüft, ob Stunden korrekt in Millisekunden umgewandelt werden.
     */
    @DisplayName("Hours get converted to Milliseconds")
    @Test
    public void testHoursToMills() {
        //given
        double time = 7;

        //when
        int mills = Time.hoursToMills(time);

        //then
        assertEquals((int) (time * 60 * 60 * 1000), mills);
    }

    /**
     * Überprüft, ob die Startzeit korrekt gesetzt wird.
     */
    @DisplayName("Starttime gets set.")
    @Test
    public void testSetStart() {
        //given
        int startTime = Time.hoursToMills(9.5);

        //when
        Time.setStart(startTime);

        //then
        assertEquals(startTime, Time.getStart());
        assertEquals(Time.millsToHours(startTime), Time.getStartAsHours());
    }

    /**
     * Überprüft, ob die Endzeit korrekt gesetzt wird.
     */
    @DisplayName("Endtime gets set.")
    @Test
    public void testSetEnd() {
        //given
        int endTime = Time.hoursToMills(19.5);

        //when
        Time.setEnd(endTime);

        //then
        assertEquals(endTime, Time.getEnd());
        assertEquals(Time.millsToHours(endTime), Time.getEndAsHours());
    }

}
