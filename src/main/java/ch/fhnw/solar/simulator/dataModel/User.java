package ch.fhnw.solar.simulator.dataModel;

/**
 * Repräsentiert einen Benutzer in der Solarsimulator Anwendung.
 * Ein Benutzer hat eine Position, einen Namen, Punkte und ein Level.
 */
public class User {
    private final String name; // Der Name des Benutzers
    private final int points; // Die Punkte des Benutzers
    private final int level; // Das Level des Benutzers
    private int position; // Die Position des Benutzers

    /**
     * Konstruiert ein Benutzer-Objekt mit gegebener Position, Name, Punkte und Level.
     *
     * @param position Die Position des Benutzers.
     * @param name     Der Name des Benutzers.
     * @param points   Die Punkte des Benutzers.
     * @param level    Das Level des Benutzers.
     */
    public User(int position, String name, int points, int level) {
        this.position = position;
        this.name = name;
        this.points = points;
        this.level = level;
    }

    /**
     * Gibt die Position des Benutzers zurück.
     *
     * @return Die Position des Benutzers.
     */
    public int getPosition() {
        return position;
    }

    /**
     * Setzt die Position des Benutzers.
     *
     * @param position Die neue Position des Benutzers.
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * Gibt den Namen des Benutzers zurück.
     *
     * @return Der Name des Benutzers.
     */
    public String getName() {
        return name;
    }

    /**
     * Gibt die Punkte des Benutzers zurück.
     *
     * @return Die Punkte des Benutzers.
     */
    public int getPoints() {
        return points;
    }

    /**
     * Gibt das Level des Benutzers zurück.
     *
     * @return Das Level des Benutzers.
     */
    public int getLevel() {
        return level;
    }
}
