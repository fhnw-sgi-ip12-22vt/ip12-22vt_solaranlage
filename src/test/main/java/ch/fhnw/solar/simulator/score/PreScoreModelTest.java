package ch.fhnw.solar.simulator.score;

import ch.fhnw.solar.simulator.preScore.PreScoreModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Testklasse für das PreScoreModel.
 */
public class PreScoreModelTest {

    private PreScoreModel preScoreModel;

    /**
     * Setzt vor jedem Test ein neues PreScoreModel-Objekt auf.
     */
    @BeforeEach
    public void beforeEach() {
        preScoreModel = new PreScoreModel();
    }

    /**
     * Überprüft, ob der Endscore korrekt gesetzt wird, ohne Minuspunkte.
     */
    @DisplayName("Endscore gets set without minus Points.")
    @Test
    public void testGetEndscore() {
        //given

        //when
        preScoreModel.setEndScore(2000);

        //then
        assertEquals(2000, preScoreModel.getEndScore());
    }

    /**
     * Überprüft, ob der Endscore korrekt gesetzt wird, mit Minuspunkten.
     */
    @DisplayName("Endscore gets set with minus Points.")
    @Test
    public void testGetEndscoreWithMinusPoints() {
        //given

        //when
        preScoreModel.setMissingGoals(3);
        preScoreModel.setEndScore(2000);

        //then
        assertEquals(2000, preScoreModel.getEndScoreWithoutLostPoints());
        assertEquals(-4000, preScoreModel.getEndScore());
    }

    /**
     * Überprüft, ob ein zufälliger Name korrekt generiert wird.
     */
    @DisplayName("Random Name gets generated.")
    @Test
    public void testRandomName() {
        //given
        String randomName = null;

        //when
        try {
            randomName = preScoreModel.getRandomName();
        } catch (IOException e) {
            System.out.println("Random name could not get generated");
        }

        //then
        assertNotNull(randomName);
        assertTrue(!randomName.isEmpty());
    }

}
