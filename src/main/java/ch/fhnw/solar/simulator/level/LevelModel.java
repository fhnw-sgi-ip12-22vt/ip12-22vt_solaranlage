package ch.fhnw.solar.simulator.level;

import ch.fhnw.solar.simulator.game.DeviceModel;
import ch.fhnw.solar.simulator.game.DeviceType;
import ch.fhnw.solar.simulator.game.GameModel;
import ch.fhnw.solar.simulator.game.PropertiesModel;
import ch.fhnw.solar.simulator.game.SolarModel;
import ch.fhnw.solar.simulator.level.objects.DeviceLevelModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Die Klasse LevelModel repräsentiert ein Spiel-Level im Solarsimulator.
 * Sie beinhaltet eine Liste der verfügbaren Geräte und verwaltet die Nutzung der Geräte während der Spielzeit.
 */
public class LevelModel extends PropertiesModel {
    private static Properties property;
    /**
     * Dauer des Levels in Sekunden.
     */
    private final int duration = 90;
    /**
     * Liste der verfügbaren Geräte in diesem Level.
     */
    private final List<DeviceLevelModel> availableDevices;
    /**
     * Set der verfügbaren Solarleistungen in diesem Level.
     */
    private final List<SolarModel> solarPower;
    private List<Double> deviceGoals;

    /**
     * Erstellt ein neues LevelModel mit den gegebenen verfügbaren Geräten und Solarleistung.
     *
     * @param availableDeviceModels die Liste der verfügbaren Geräte für dieses Level.
     * @param solarPower            die Solarleistung für dieses Level.
     */
    public LevelModel(List<DeviceModel> availableDeviceModels, SolarModel solarPower) {
        super("src/main/config/gameTextDe.properties");

        this.availableDevices = new ArrayList<>();
        this.solarPower = new ArrayList<>();
        property = GameModel.getProperty();
        deviceGoals = new ArrayList<>();

        Enumeration<?> keys = property.propertyNames();
        List<String> sortedKeys = Collections.list(keys).stream()
            .map(Object::toString)
            .filter(key -> key.startsWith("goal"))
            .sorted(Comparator.comparingInt(key -> {
                String numberString = key.substring(4); //Angenommen die Zahl kommt nach dem goal..
                return Integer.parseInt(numberString);
            }))
            .collect(Collectors.toList());

        for (String key : sortedKeys) {
            deviceGoals.add(Double.valueOf(property.getProperty(key)));
        }


        for (DeviceModel deviceModel : availableDeviceModels) {
            // -1 da goal1 auf Stelle 0 in der Liste ist
            this.availableDevices.add(new DeviceLevelModel(deviceModel, deviceGoals.get(deviceModel.getId() - 1)));
        }

        this.solarPower.add(solarPower);
    }

    /**
     * Registriert ein Gerät für dieses Level, mit einer angegebenen Startzeit.
     *
     * @param deviceModel das zu registrierende Gerät.
     * @param start       die Startzeit für das Gerät.
     */
    public void registerDevice(DeviceLevelModel deviceModel, int start) {
        deviceModel.setStartTime(start);
        deviceModel.setRunning(true);
        
    }

    /**
     * Entfernt die Registrierung eines Geräts von diesem Level.
     *
     * @param deviceModel das zu entfernende Gerät.
     */
    public void unregisterDevice(DeviceLevelModel deviceModel) {
        deviceModel.setRunning(false);
    }

    /**
     * Gibt einen Stream der laufenden Geräte zurück.
     *
     * @return einen Stream der laufenden Geräte.
     */
    public Stream<DeviceLevelModel> getRunningDevices() {
        return availableDevices.stream().filter(DeviceLevelModel::isRunning);
    }

    /**
     * Gibt eine Liste der verfügbaren Geräte zurück.
     *
     * @return eine Liste der verfügbaren Geräte.
     */

    public List<DeviceLevelModel> getAvailableDevices() {
        return this.availableDevices;
    }

    /**
     * Gibt eine Liste der Geräte zurück, welche nicht abgeschaltet
     * werden können
     * 
     * @return eine Liste der nicht abschaltbaren Geräte.
     */
    public List<DeviceLevelModel> getAlwaysOnDevices() {
        return this.availableDevices.stream()
                .filter(n -> n.getType() == DeviceType.ALWAYS_ON).toList();
    }

    /**
     * Gibt eine Liste der Geräte zurück, welche an- und abgeschaltet
     * werden können
     * 
     * @return eine Liste der an- und abschaltbaren Geräten
     */
    public List<DeviceLevelModel> getPartlyOnDevices() {
        return this.availableDevices.stream()
                .filter(n -> n.getType() == DeviceType.PARTLY_ON).toList();
    }

    /**
     * Gibt die Solarproduktionsdaten zurück
     * 
     * @return Solarproduktionsdaten
     */
    public List<SolarModel> getSolarPower() {
        return this.solarPower;
    }

    /**
     * Gibt die Dauer des Levels in Sekunden zurück.
     *
     * @return die Dauer des Levels in Sekunden.
     */
    public int getDuration() {
        return duration;
    }

}
