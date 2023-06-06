package ch.fhnw.solar.simulator.score;

import ch.fhnw.solar.simulator.Solarsimulator;
import ch.fhnw.solar.simulator.dataModel.User;
import ch.fhnw.solar.simulator.game.PropertiesModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.Properties;

/**
 * Die Klasse ScoreModel erweitert das PropertiesModel und repräsentiert die Datenstruktur für den Score.
 * Es enthält Informationen über den neuesten Benutzer und stellt Methoden
 * zum Laden von Benutzerdaten aus einer Eigenschaftsdatei bereit.
 */
public class ScoreModel extends PropertiesModel {
    // Hinzugefügtes Mitglied zur Speicherung des neuesten Benutzers
    private User latestUser;

    /**
     * Konstruktor für die ScoreModel Klasse.
     * Lädt die Eigenschaften aus der angegebenen Datei.
     */
    public ScoreModel() {
        super("src/main/config/gameTextDe.properties");
    }

    /**
     * Gibt den neuesten Benutzer zurück.
     *
     * @return der neueste Benutzer
     */
    public User getLatestUser() {
        return latestUser;
    }

    /**
     * Lädt Benutzerdaten aus einer Eigenschaftsdatei und gibt eine Liste der Benutzer zurück.
     *
     * @param propertiesFilePath Pfad zur Eigenschaftsdatei
     * @return eine ObservableList von Benutzern
     */
    public ObservableList<User> loadUsersFromPropertiesFile(String propertiesFilePath) {
        ObservableList<User> users = FXCollections.observableArrayList();
        // Initialisiere latestUser zu Beginn der Methode mit null
        latestUser = null;

        Properties properties = new Properties();
        try (InputStream input = new FileInputStream(propertiesFilePath)) {
            // Laden der Eigenschaften aus der Datei
            // Finden des neuesten Schlüssels in der Eigenschaftsdatei
            // Hinzufügen von Benutzern zur Liste und Festlegen des neuesten Benutzers
            // Sortieren der Benutzer nach Punkten und Festlegen ihrer Positionen
            properties.load(input);

            String latestKey = Collections.max(properties.stringPropertyNames());

            for (String key : properties.stringPropertyNames()) {
                String[] values = properties.getProperty(key).split(",");
                if (values.length == 2) {
                    String name = values[1];
                    int points = Integer.parseInt(values[0]);
                    int level = Solarsimulator.GAME_MODEL.getLevel();

                    User user = new User(0, name, points, level);
                    users.add(user);

                    // Wenn es der letzte Key ist, solle dies als 
                    // letzter User gespeichert werden
                    if (key.equals(latestKey)) {
                        latestUser = user;
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // Sortieren und Zuweisen der Positionen der Benutzer in der Liste
        users.sort(Comparator.comparing(User::getPoints).reversed());
        for (int i = 0; i < users.size(); i++) {
            users.get(i).setPosition(i + 1);
        }

        return users;
    }
}
