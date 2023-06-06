package ch.fhnw.solar.simulator.game;
import ch.fhnw.solar.simulator.Solarsimulator;

import java.io.IOException;

/**
 * GameController ist eine Klasse, die die Aktionen des Spiels steuert.
 * Sie verwaltet die Darstellung der Spielansichten.
 */
public class GameController {

    /**
     * Diese Methode setzt die Spielansicht, indem sie ein View-Objekt
     * und den Pfad zu der zugehörigen FXML-Datei erhält.
     *
     * @param view Die Ansicht, die gesetzt werden soll.
     * @param fxml Der Pfad zur FXML-Datei, die die Ansicht definiert.
     * @throws IOException Wenn, es ein Problem beim Laden der FXML-Datei gibt.
     */
    public void setView(GameView view, String fxml) throws IOException {
        Solarsimulator.GAME_MODEL.setView(view, fxml);
    }

    /**
     * Diese Methode setzt die Level ansicht des Spiels.
     *
     * @throws IOException Wenn, es ein Problem beim Laden der FXML-Datei gibt.
     */
    public void setViewLevel() throws IOException {
        setView(Solarsimulator.GAME_MODEL.getLevelView(), "view/LevelView.fxml");
    }

}
