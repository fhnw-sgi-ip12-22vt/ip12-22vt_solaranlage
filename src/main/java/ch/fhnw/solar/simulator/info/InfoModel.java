package ch.fhnw.solar.simulator.info;

import ch.fhnw.solar.simulator.game.PropertiesModel;

/**
 * Die Klasse InfoModel verwaltet die Informationen für die Info-Ansicht der Solar Simulator Anwendung.
 * Sie lädt die Texteigenschaften aus einer Datei und speichert sie in einer Map.
 * Außerdem verwaltet sie das aktuelle Level des Spiels.
 * <p>
 * Diese Klasse erbt von der PropertiesModel Klasse, die allgemeine Funktionen zur Verwaltung
 * von Eigenschaften bereitstellt.
 */
public class InfoModel extends PropertiesModel {
    /**
     * Konstruktor für die Klasse InfoModel.
     * Beim Erstellen einer Instanz dieser Klasse wird der Pfad zur Eigenschaftsdatei an den Superkonstruktor übergeben.
     * Diese Eigenschaftsdatei enthält die spezifischen Textinformationen für die Info-Ansicht.
     */
    public InfoModel() {
        super("src/main/config/gameTextDe.properties");
    }
}
