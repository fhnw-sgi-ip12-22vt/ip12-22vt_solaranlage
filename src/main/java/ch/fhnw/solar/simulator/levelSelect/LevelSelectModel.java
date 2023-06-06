package ch.fhnw.solar.simulator.levelSelect;

import ch.fhnw.solar.simulator.game.PropertiesModel;

/**
 * Diese Klasse ist die Model-Komponente des MVC-Musters für den Level-Auswahlbildschirm.
 * Sie verwaltet den Zustand der Level auswahl und die Texte für die verschiedenen UI-Komponenten.
 */
public class LevelSelectModel extends PropertiesModel {

    /**
     * Konstruktor, welcher den Pfad definiert der Properties.
     */
    public LevelSelectModel() {
        super("src/main/config/gameTextDe.properties");
    }
}
