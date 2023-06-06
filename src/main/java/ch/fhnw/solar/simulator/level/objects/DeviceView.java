package ch.fhnw.solar.simulator.level.objects;

import ch.fhnw.solar.simulator.level.Time;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;

/**
 * Diese Klasse übernimmt die Darstellung der Angaben zu den einzelnen Geräten,
 * während ein Level gespielt wird.
 * Sie zeigt konkret an:
 * - Ob das Gerät aktuell an- oder ausgeschaltet ist.
 * - Wie lange das Gerät noch laufen soll.
 * - Wie lange das Gerät bereits eingeschaltet war.
 * <p>
 * Die Darstellung wird auf dem Display realisiert, alternativ könnte auch eine
 * Implementierung mit LED-Stripes auf dem Spielgerät umgesetzt werden.
 */
public class DeviceView extends GridPane {
    // Gerätemodell auf Level-basis
    private final DeviceLevelModel device;
    // Steuerelemente für die Anzeige
    private Label goalRunning; // Label für die angepeilte Laufzeit des Geräts
    private ProgressBar progress; // Fortschrittsanzeige für das Gerät

    /**
     * Konstruktor für die Geräteansicht.
     *
     * @param device Das zu darstellende Gerätemodell.
     */
    public DeviceView(DeviceLevelModel device) {
        this.device = device;
        initializeControls();
        layoutControls();
    }

    /**
     * Initialisiert die Steuerelemente für die Darstellung.
     */
    private void initializeControls() {
        goalRunning = new Label(device.getUsageGoal() + "h");
        progress = new ProgressBar();
        update();
    }

    /**
     * Legt das Layout für die Steuerelemente fest.
     */
    private void layoutControls() {
        add(goalRunning, 0, 0);
        add(progress, 1, 0);
        getStyleClass().add("device-goals-element");
        goalRunning.setStyle("-fx-padding: 0 7 0 0; -fx-text-fill: white;");
        progress.setStyle("-fx-text-fill: white;");
    }

    /**
     * Aktualisiert die Anzeige.
     */
    public void update() {
        updateProgress();
    }

    /**
     * Gibt den aktuellen Fortschritt zurück.
     *
     * @return Der aktuelle Fortschritt.
     */
    public double getProgress() {
        return progress.getProgress();
    }

    /**
     * Aktualisiert die Fortschrittsanzeige.
     */
    private void updateProgress() {
        try {
            progress.setProgress((double) (Time.millsToHours(device.getUsageDone()) / device.getUsageGoal()));
        } catch (Exception e) {
            progress.setProgress(device.getUsageDone());
        }
    }
}
