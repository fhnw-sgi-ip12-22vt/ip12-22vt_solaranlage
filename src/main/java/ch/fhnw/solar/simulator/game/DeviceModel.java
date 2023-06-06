package ch.fhnw.solar.simulator.game;

/**
 * Die Klasse DeviceModel erstellt die Geräte (Devices) mit ihren jeweiligen Eigenschaften für das gesamte Spiel.
 * Die Eigenschaften, die sich in den Levels ändern, werden in der entsprechenden Klasse verwaltet.
 */
public class DeviceModel {
    private final int id;
    private final String name;
    private final DeviceType type;
    private final PowerPlan powerPlan;
    private final double standby;

    /**
     * Konstruktor zum Erstellen eines DeviceModel-Objekts mit den gegebenen Eigenschaften.
     *
     * @param id        Die ID des Geräts, um es zu identifizieren.
     * @param name      Der Name des Geräts.
     * @param type      Der Gerätetyp.
     * @param powerPlan Der Stromplan für das Gerät.
     * @param standby   Der Standby-Stromverbrauch des Geräts.
     */
    public DeviceModel(int id, String name, DeviceType type, PowerPlan powerPlan, double standby) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.powerPlan = powerPlan;
        this.standby = standby;
    }

    /**
     * Gibt die ID des Geräts zurück, wird für die
     * Erstellung des DeviceGoals verwendet.
     * 
     * @return ID des Geräts.
     */
    public int getId() {
        return id;
    }

    /**
     * Gibt den Namen des Geräts zurück.
     *
     * @return Der Name des Geräts.
     */
    public String getName() {
        return name;
    }

    /**
     * Gibt den Gerätetyp zurück.
     *
     * @return Der Gerätetyp.
     */
    public DeviceType getType() {
        return type;
    }

    /**
     * Gibt den Stromplan des Geräts zurück.
     *
     * @return Der Stromplan des Geräts.
     */
    public PowerPlan getPowerPlan() {
        return powerPlan;
    }

    /**
     * Gibt den Standby-Stromverbrauch des Geräts zurück.
     *
     * @return Der Standby-Stromverbrauch des Geräts.
     */
    public Double getStandby() {
        return standby;
    }

}
