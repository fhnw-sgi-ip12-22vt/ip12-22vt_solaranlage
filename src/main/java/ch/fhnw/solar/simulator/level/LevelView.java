package ch.fhnw.solar.simulator.level;

import ch.fhnw.solar.simulator.game.GameView;

/**
 * Die Klasse LevelView stellt das grafische Benutzerinterface (GUI) für ein Level dar.
 * Sie erweitert die Klasse GameView und enthält Referenzen auf das LevelModel und den LevelController.
 */
public class LevelView extends GameView {

    /**
     * Das Model des Levels.
     */
    private LevelModel model;
    /**
     * Der Controller des Levels.
     */
    private LevelController ctrl;

    /**
     * Erstellt eine neue LevelView mit einem gegebenen LevelModel und LevelController.
     *
     * @param model das Model des Levels.
     * @param ctrl der Controller des Levels.
     */
    public LevelView(LevelModel model, LevelController ctrl) {
        this.model = model;
        this.ctrl = ctrl;
    }

    /**
     * Gibt den LevelController dieser LevelView zurück.
     *
     * @return der LevelController dieser LevelView.
     */
    public LevelController getCtrl() {
        return ctrl;
    }

}
