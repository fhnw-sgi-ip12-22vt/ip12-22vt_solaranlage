package ch.fhnw.solar.simulator.level;

/**
 * Die Time-Klasse bietet verschiedene statische Methoden und Variablen zur Zeitsteuerung und -konvertierung.
 */
public final class Time {

    /**
     * Anzahl der Millisekunden für die Dauer eines Frames.
     */
    public static final int FRAME_DURATION = 50;

    /**
     * Anzahl der Spiel-Millisekunden für die Dauer eines Frames.
     */
    public static final int STEP = 600 * FRAME_DURATION;
    // Anfangs-, End- und Schrittzeitpunkte als statische Variablen
    private static int step = hoursToMills(1.0);
    private static int start = hoursToMills(6.0);
    private static int end = hoursToMills(22.0);

    /**
     * Privater Konstruktor zur Vermeidung der Instantiierung.
     */
    private Time() {
        // Vermeidung der Instantiierung
    }

    /**
     * Konvertiert Stunden in Millisekunden.
     *
     * @param hours Die Anzahl der Stunden, die konvertiert werden sollen.
     * @return Die Anzahl der Millisekunden, die den gegebenen Stunden entsprechen.
     */
    public static int hoursToMills(double hours) {
        return minutesToMills((int) (60 * hours));
    }

    /**
     * Konvertiert Minuten in Millisekunden.
     *
     * @param minutes Die Anzahl der Minuten, die konvertiert werden sollen.
     * @return Die Anzahl der Millisekunden, die den gegebenen Minuten entsprechen.
     */
    public static int minutesToMills(int minutes) {
        return secondsToMills(60 * minutes);
    }

    /**
     * Konvertiert Sekunden in Millisekunden.
     *
     * @param seconds Die Anzahl der Sekunden, die konvertiert werden sollen.
     * @return Die Anzahl der Millisekunden, die den gegebenen Sekunden entsprechen.
     */
    public static int secondsToMills(int seconds) {
        return 1000 * seconds;
    }

    /**
     * Konvertiert Millisekunden in Stunden.
     *
     * @param mills Die Anzahl der Millisekunden, die konvertiert werden sollen.
     * @return Die Anzahl der Stunden, die den gegebenen Millisekunden entsprechen.
     */
    public static double millsToHours(int mills) {
        return mills / (60d * 60 * 1000);
    }

    /**
     * Gibt den Schrittwert zurück.
     *
     * @return Der aktuelle Schrittwert.
     */
    public static int getStep() {
        return step;
    }

    /**
     * Legt den Schrittwert fest.
     *
     * @param step Der neue Schrittwert.
     */
    public static void setStep(int step) {
        Time.step = step;
    }

    /**
     * Gibt den Startwert zurück.
     *
     * @return Der aktuelle Startwert.
     */
    public static int getStart() {
        return start;
    }

    /**
     * Legt den Startwert fest.
     *
     * @param start Der neue Startwert.
     */
    public static void setStart(int start) {
        Time.start = start;
    }

    /**
     * Gibt den Startwert in Stunden zurück.
     *
     * @return Der Startwert in Stunden.
     */
    public static double getStartAsHours() {
        return millsToHours(start);
    }

    /**
     * Gibt den Endwert zurück.
     *
     * @return Der aktuelle Endwert.
     */
    public static int getEnd() {
        return end;
    }

    /**
     * Legt den Endwert fest.
     *
     * @param end Der neue Endwert.
     */
    public static void setEnd(int end) {
        Time.end = end;
    }

    /**
     * Gibt den Endwert in Stunden zurück.
     *
     * @return Der Endwert in Stunden.
     */
    public static double getEndAsHours() {
        return millsToHours(end);
    }

}
