package ch.fhnw.solar.simulator.level.objects;

import ch.fhnw.solar.simulator.game.DeviceModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * Diese Klasse erweitert die DeviceModel-Klasse und fügt zusätzliche Attribute und Methoden hinzu,
 * die für das Management von Geräten auf Level-Basis benötigt werden.
 */
public class DeviceLevelModel extends DeviceModel {

    private final double usageGoal; // Die vorgesehene Menge an Energie, die das Gerät verbrauchen soll (in Watt)
    // Status des Geräts: laufend oder nicht
    private final BooleanProperty running = new SimpleBooleanProperty(this, "running", false);
    // Der Zeitpunkt, an dem das Gerät zuletzt eingeschaltet wurde. 0 wenn, das Gerät ausgeschaltet ist
    private int startTime;
    private int usageDone; // Die bisher verbrauchte Energie (in Watt)
    private boolean standby; // Der Standby-Status des Geräts

    /**
     * Konstruktor für die Klasse DeviceLevelModel.
     *
     * @param device    Das DeviceModel, von dem dieses DeviceLevelModel abgeleitet ist.
     * @param usageGoal Die vorgesehene Menge an Energie, die das Gerät verbrauchen soll.
     */
    public DeviceLevelModel(DeviceModel device, double usageGoal) {
        super(device.getId(), device.getName(), device.getType(), device.getPowerPlan(), device.getStandby());
        this.startTime = 0;
        this.usageGoal = usageGoal;
        this.usageDone = 0;
    }

    /**
     * Gibt den Zeitpunkt der letzten Aktivierung des Geräts zurück.
     *
     * @return die Startzeit des Geräts.
     */
    public int getStartTime() {
        return startTime;
    }

    /**
     * Setzt den Zeitpunkt der letzten Aktivierung des Geräts.
     *
     * @param startTime Der neue Zeitpunkt der letzten Aktivierung des Geräts.
     */
    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    /**
     * Überprüft, ob das Gerät im Standby-Modus ist.
     *
     * @param power Die aktuelle Leistung des Geräts.
     */
    public void isOnStandby(Double power) {
        double tolerance = 0.1;
        this.standby = Math.abs(power - getStandby()) < tolerance;
    }

    /**
     * Gibt den Standby-Status des Geräts zurück.
     *
     * @return der Standby-Status des Geräts.
     */
    public boolean getIsOnStandby() {
        return standby;
    }

    /**
     * Setzt den Standby-Status des Geräts.
     *
     * @param standby Der neue Standby-Status des Geräts.
     */
    public void setIsOnStandby(Boolean standby) {
        this.standby = standby;
    }

    /**
     * Gibt den Betriebsstatus des Geräts zurück.
     *
     * @return der Betriebsstatus des Geräts.
     */
    public boolean isRunning() {
        return running.get();
    }

    /**
     * Setzt den Betriebsstatus des Geräts.
     *
     * @param running Der neue Betriebsstatus des Geräts.
     */
    public void setRunning(boolean running) {
        this.running.set(running);
    }

    /**
     * Gibt die JavaFX-Eigenschaft des Betriebsstatus des Geräts zurück.
     * Dies kann zum Binden an GUI-Elemente verwendet werden.
     *
     * @return Die JavaFX-Eigenschaft des Betriebsstatus des Geräts.
     */
    public BooleanProperty runningProperty() {
        return running;
    }

    /**
     * Gibt die bisher verbrauchte Energie des Geräts zurück.
     *
     * @return die bisher verbrauchte Energie des Geräts.
     */
    public int getUsageDone() {
        return usageDone;
    }

    /**
     * Setzt die bisher verbrauchte Energie des Geräts.
     *
     * @param usageDone Die neue Menge an verbrauchter Energie des Geräts.
     */
    public void setUsageDone(int usageDone) {
        this.usageDone = usageDone;
    }

    /**
     * Erhöht die bisher verbrauchte Energie des Geräts um einen bestimmten Betrag.
     *
     * @param usageIncrease Der Betrag, um den die verbrauchte Energie erhöht wird.
     */
    public void increaseUsageDone(int usageIncrease) {
        this.usageDone += usageIncrease;
    }

    /**
     * Gibt das Ziel des Energieverbrauchs des Geräts zurück.
     *
     * @return das Ziel des Energieverbrauchs des Geräts.
     */
    public double getUsageGoal() {
        return usageGoal;
    }

}
