package ch.fhnw.solar.simulator.game;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * Die Klasse PropertiesModel kapselt die gemeinsamen Attribute
 * und Verhaltensweisen eines Spielmodells.
 * 
 * Sie verwaltet eine Map, die Schaltflächentexte aus einer Properties-Datei lädt,
 * und eine aktuelle Spielstufe.
 */
public class PropertiesModel {
    private Map<String, String> buttonTexts;
    private int currentLevel;

    /**
     * Erzeugt ein neues PropertiesModel und lädt Schaltflächentexte aus einer Properties-Datei.
     *
     * @param propertiesFilePath Der Pfad zur Properties-Datei.
     */
    public PropertiesModel(String propertiesFilePath) {
        Properties properties = new Properties();
        try {
            // Mit try-with-resources wird sichergestellt, dass der FileInputStream geschlossen wird
            try (FileInputStream fis = new FileInputStream(propertiesFilePath)) {
                properties.load(fis);
                buttonTexts = properties.stringPropertyNames().stream()
                        .collect(Collectors.toMap(key -> key, properties::getProperty));
                currentLevel = 0;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gibt den Text einer spezifischen Schaltfläche zurück.
     *
     * @param buttonName Der Name der Schaltfläche.
     * @return Der Text der Schaltfläche.
     */
    public String getButtonText(String buttonName) {
        return buttonTexts.get(buttonName);
    }

    /**
     * Gibt die aktuelle Spielstufe zurück.
     *
     * @return Die aktuelle Spielstufe.
     */
    public int getCurrentLevel() {
        return currentLevel;
    }

    /**
     * Setzt die aktuelle Spielstufe.
     *
     * @param currentLevel Die zu setzende Stufe.
     */
    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }
}
