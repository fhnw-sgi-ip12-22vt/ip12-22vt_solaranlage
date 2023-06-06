package ch.fhnw.solar.simulator.preScore;

import ch.fhnw.solar.simulator.game.PropertiesModel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.Properties;
import java.util.Random;

/**
 * Die Klasse PreScoreModel repräsentiert das Datenmodell für die Vor-Score-Informationen im Spiel.
 * Sie speichert Informationen über das aktuelle Level, den Score und die fehlenden Ziele und stellt auch
 * Methoden zur Manipulation und zum Zugriff auf diese Daten zur Verfügung.
 */
public class PreScoreModel extends PropertiesModel {
    // Der Endscore des Spiels
    private int endScore;
    // Die Anzahl der fehlenden Ziele im Spiel
    private int missingGoals;

    /**
     * Konstruiert ein neues PreScoreModel, initialisiert das aktuelle Level auf 0
     * und lädt die Texte der Schaltflächen aus einer Eigenschaftsdatei.
     */
    public PreScoreModel() {
        super("src/main/config/gameTextDe.properties");
    }

    /**
     * Legt die Anzahl der fehlenden Ziele im Spiel fest.
     *
     * @param missingGoals die Anzahl der fehlenden Ziele
     */
    public void setMissingGoals(int missingGoals) {
        this.missingGoals = missingGoals;
    }

    /**
     * Berechnet die aufgrund fehlender Ziele verlorenen Punkte.
     *
     * @return die verlorenen Punkte
     */
    public int getLostPoints() {
        return 2000 * missingGoals;
    }

    /**
     * Berechnet den Endscore ohne Berücksichtigung der verlorenen Punkte.
     *
     * @return den Endscore ohne verlorene Punkte
     */
    public int getEndScoreWithoutLostPoints() {
        return endScore + getLostPoints();
    }

    /**
     * Gibt den finalen Endscore unter Berücksichtigung der verlorenen Punkte zurück.
     *
     * @return den finalen Endscore
     */
    public int getEndScore() {
        return endScore;
    }

    /**
     * Legt den Endscore des Spiels fest, wobei die aufgrund fehlender Ziele verlorenen Punkte berücksichtigt werden.
     *
     * @param endScore der initiale Endscore ohne die verlorenen Punkte
     */
    public void setEndScore(int endScore) {
        this.endScore = endScore - getLostPoints();
    }

    /**
     * Erzeugt einen zufälligen Namen aus einer Liste, die in einer Eigenschaftsdatei gespeichert ist.
     *
     * @return einen zufällig ausgewählten Namen
     * @throws IOException wenn beim Zugriff auf die Eigenschaftsdatei ein Problem auftritt
     */
    public String getRandomName() throws IOException {
        Properties properties = new Properties();
        // Using try-with-resources to ensure the FileInputStream is closed
        try (FileInputStream fis = new FileInputStream("src/main/config/names.properties")) {
            properties.load(fis);
            int index = new Random().nextInt(50) + 1;
            return properties.getProperty("name" + index);
        }
    }

    /**
     * Speichert die Benutzerdaten. Die Methode speichert das aktuelle Level, den Namen des Benutzers und den Endscore.
     *
     * @param level das aktuelle Level
     * @param name  der Name des Benutzers
     * @param endScore der Endscore des Spiels
     */
    public void saveUserData(int level, String name, int endScore) {
        Properties userData = new Properties();
        // Verwendung von Format zum Aufbau des Dateipfads.
        String userDataPath = String.format("src/main/config/userDataLevel%d.properties", level);

        try {
            // Verwendung von try-with-resources um sicherzustellen,
            // dass FileInputStream und FileOutputStream geschlossen werden
            try (FileInputStream inStream = new FileInputStream(userDataPath)) {
                userData.load(inStream);
            }

            String timestamp = Instant.now().toString();

            userData.setProperty(timestamp, endScore + "," + name);

            try (FileOutputStream outStream = new FileOutputStream(userDataPath)) {
                userData.store(outStream, null);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
