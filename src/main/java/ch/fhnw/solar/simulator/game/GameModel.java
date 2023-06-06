package ch.fhnw.solar.simulator.game;

import ch.fhnw.solar.simulator.Solarsimulator;
import ch.fhnw.solar.simulator.game.PowerPlan.Period;
import ch.fhnw.solar.simulator.info.InfoController;
import ch.fhnw.solar.simulator.info.InfoModel;
import ch.fhnw.solar.simulator.level.LevelController;
import ch.fhnw.solar.simulator.level.LevelModel;
import ch.fhnw.solar.simulator.level.LevelView;
import ch.fhnw.solar.simulator.level.Time;
import ch.fhnw.solar.simulator.preScore.PreScoreController;
import ch.fhnw.solar.simulator.preScore.PreScoreModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

import static ch.fhnw.solar.simulator.game.PowerPlan.multiplePeriodsPower;
import static ch.fhnw.solar.simulator.game.PowerPlan.periodsPower;

/**
 * Diese Klasse dient als übergeordnetes Model im Model-View-Controller (MVC) Muster.
 * <p>
 * Sie erzeugt und verwaltet die anderen Model-, View- und Controller-Klassen: Information und Level.
 * <p>
 * Zusätzlich steuert sie, welche View momentan aktiv ist.
 */
public class GameModel {

    private static Properties property;
    private static String configPath;
    private final PreScoreController preScoreController;
    private final PreScoreModel preScoreModel;
    private LevelModel levelModel;
    private LevelController levelController;
    private LevelView levelView;
    private int level;

    /**
     * Erstellt ein neues GameModel, initialisiert die Info- und PreScore-MVC-Komponenten und lädt ein neues Level.
     *
     * @param fxmlLoader Der FXMLLoader, der zur Erzeugung von Views verwendet wird.
     * @param level      Das anfängliche Level, das geladen werden soll.
     */
    public GameModel(FXMLLoader fxmlLoader, int level) {
        preScoreModel = new PreScoreModel();
        preScoreController = new PreScoreController(preScoreModel);

        newLevel(level);
    }

    /**
     * Gibt die Properties zurück
     * 
     * @return Property-Daten
     */
    public static Properties getProperty() {
        return property;
    }

    /**
     * Erstellt ein neues Level, initialisiert die Geräte, die Sonnenenergieproduktion und die Level-MVC-Komponenten.
     *
     * @param level Das zu ladende Level.
     */
    public void newLevel(int level) {
        this.level = level;
        configPath = "src/main/config/level" + level + ".properties";
        initializePropertyFile();

        List<DeviceModel> availableDeviceModels = initializeDevices();
        SolarModel solarPower = initializeSolarPower();

        levelModel = new LevelModel(availableDeviceModels, solarPower);
        levelController = new LevelController(levelModel);
        levelView = new LevelView(levelModel, levelController);
    }

    /**
     * Initialisiert die Solarenergieproduktion basierend auf den Datenpunkten in der Eigenschaftsdatei.
     *
     * @return Ein SolarModel, das die Solarenergieproduktion repräsentiert.
     */
    private SolarModel initializeSolarPower() {

        Map<Double, Double> dataPoints = new HashMap<>();

        for (Object key : property.keySet()) {
            String keyString = (String) key;
            if (keyString.startsWith("dataPoint")) {
                String pair = property.getProperty(keyString);
                String[] keyAndValue = pair.split(";");
                if (keyAndValue.length == 2) {
                    dataPoints.put(Double.parseDouble(keyAndValue[0]), Double.parseDouble(keyAndValue[1]));
                }
            }
        }

        return new SolarModel("Solarstrom", dataPoints);
    }

    /**
     * Lädt die Eigenschaftsdatei für das aktuelle Level.
     */
    private void initializePropertyFile() {
        property = new Properties();
        try {
            FileInputStream input = new FileInputStream(configPath);
            property.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initialisiert Geräte basierend auf der Eigenschaftsdatei für das aktuelle Level.
     * Ein Gerät kann einen konstanten Energieverbrauch oder einen Energieverbrauch über
     * verschiedene Zeiträume aufweisen.
     *
     * @return Eine Liste der initialisierten Geräte.
     */
    private List<DeviceModel> initializeDevices() {
        List<DeviceModel> deviceSet = new ArrayList<>();

        // Definiert die Reihenfolge der Geräte entsprechend der Anordnung auf dem Spielbrett
        String[] deviceKeys =
            {"device5", "device2", "device12", "device4", "device1", "device3", "device6", "device10", "device9",
                "device11", "device8", "device7"};

        for (String keyString : deviceKeys) {
            if (property.containsKey(keyString)) {
                String pair = property.getProperty(keyString);
                String[] values = pair.split(";");
                String id = keyString.replaceAll("[^0-9]", "");
                if (values.length >= 7) {
                    List<Period> periods = new ArrayList<>();
                    for (int i = 5; i < values.length; i += 2) {
                        periods.add(new Period(Time.hoursToMills(Double.parseDouble(values[i])),
                            Double.parseDouble(values[i + 1])));
                    }
                    PowerPlan period;
                    if (Integer.parseInt(values[3]) > 1) {
                        period = multiplePeriodsPower(Double.valueOf(values[4]), Integer.parseInt(values[3]),
                            periods.toArray(new Period[0]));
                    } else {
                        period = periodsPower(Double.valueOf(values[4]), periods.toArray(new Period[0]));
                    }
                    deviceSet.add(new DeviceModel(Integer.valueOf(id),values[0], DeviceType.valueOf(values[1]), period,
                        Double.parseDouble(values[4])));
                }
            }
        }

        return deviceSet;
    }

    /**
     * Ändert die aktuell angezeigte View.
     *
     * @param gameView Das StackPane-Objekt, das die aktuelle View enthält.
     * @param fxml     Der Pfad zur FXML-Datei der neuen View.
     * @throws IOException Wenn, die FXML-Datei nicht geladen werden kann.
     */
    public void setView(StackPane gameView, String fxml) throws IOException {
        StackPane newGameView = FXMLLoader.load(Objects.requireNonNull(Solarsimulator.class.getResource(fxml)));
        gameView.getChildren().removeAll();
        gameView.getChildren().setAll(newGameView);
    }


    /**
     * Gibt das LevelModel Objekt zurück
     *
     * @return LevelModel Objekt
     */
    public LevelModel getLevelModel() {
        return levelModel;
    }

    /**
     * Gibt das LevelController Objekt zurück
     *
     * @return LevelController Objekt
     */
    public LevelController getLevelController() {
        return levelController;
    }

    /**
     * Gibt das LevelView Objekt zurück
     *
     * @return LevelView Objekt
     */
    public LevelView getLevelView() {
        return levelView;
    }

    /**
     * Gibt das PreScoreController Objekt zurück
     *
     * @return PreScoreController Objekt
     */
    public PreScoreController getPreScoreController() {
        return preScoreController;
    }

    /**
     * Gibt das PreScoreModel Objekt zurück
     *
     * @return PreScoreModel Objekt
     */
    public PreScoreModel getPreScoreModel() {
        return preScoreModel;
    }

    /**
     * Gibt das aktuelle Level zurück
     *
     * @return Level als int (1,2 oder 3)
     */
    public int getLevel() {
        return level;
    }

    /**
     * Setzt das aktuelle Level
     *
     * @param level Level als int (1,2 oder 3)
     */
    public void setLevel(int level) {
        this.level = level;
    }
}
