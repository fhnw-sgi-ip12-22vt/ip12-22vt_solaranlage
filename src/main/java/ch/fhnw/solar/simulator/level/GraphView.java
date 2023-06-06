package ch.fhnw.solar.simulator.level;

import ch.fhnw.solar.simulator.Solarsimulator;
import ch.fhnw.solar.simulator.game.SolarModel;
import ch.fhnw.solar.simulator.level.objects.DeviceLevelModel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedAreaChart;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Die Klasse GraphView stellt die Visualisierung des Level verlaufs dar.
 * Es erbt von StackedAreaChart und erstellt ein gestapeltes Flächendiagramm für die Darstellung.
 */
public class GraphView extends StackedAreaChart<Number, Number> {

    private final LevelModel levelModel;
    private final Map<Series<Number, Number>, SolarModel> solarProduction;
    private final Map<Series<Number, Number>, DeviceLevelModel> seriesDeviceMap;
    private final StringProperty scoreProp;
    private final DoubleProperty timeProp;
    private Timeline timeLine;
    private int time = Time.getStart();
    private Double powerTotal;
    // Stellt den Strom und Stunde der produzierten Solarenergie dar
    private Double solarProductionCurrentStepPower = 0.0;
    private Double solarProductionNextStepPower = 0.0;
    private Double solarProductionCurrentStepTime = 0.0;
    private Double solarProductionNextStepTime = 0.0;
    private double score;
    private int counter;

    private DevicesView devices;

    /**
     * Standardkonstruktor, der das Level Modell aus dem Solarsimulator-Spielmodell nutzt.
     */
    public GraphView() {
        this(Solarsimulator.GAME_MODEL.getLevelModel());
    }

    /**
     * Konstruktor, der ein spezifisches Level modell erwartet.
     *
     * @param levelModel Das Level Modell, das visualisiert werden soll.
     */
    public GraphView(LevelModel levelModel) {
        super(numberAxis("Stunde", Time.getStartAsHours(), Time.getEndAsHours(), 1),
            numberAxis("Watt pro Stunde", 0, 5500, 500));

        devices = null;

        scoreProp = new SimpleStringProperty();
        timeProp = new SimpleDoubleProperty();

        setCreateSymbols(false);
        setAnimated(false);
        setLegendVisible(true);

        this.levelModel = levelModel;
        solarProduction = levelModel.getSolarPower().stream().collect(Collectors.toMap(d -> {
                var series = new Series<Number, Number>();
                series.setName(d.name());
                return series;
            }, d -> d,
            (u, v) -> u,
            () -> new TreeMap<>((s1, s2) -> s1.getName().compareTo(s2.getName()))
        ));

        seriesDeviceMap = levelModel.getAvailableDevices().stream().collect(Collectors.toMap(d -> {
                var series = new Series<Number, Number>();
                series.setName(d.getName());
                return series;
            }, d -> d,
            (u, v) -> u,
            () -> new TreeMap<>((s1, s2) -> s1.getName().compareTo(s2.getName()))
        ));

        getData().addAll(seriesDeviceMap.keySet());
        getData().addAll(solarProduction.keySet());

        score = 0;
    }

    /**
     * Hilfsmethode zur Erstellung einer Instanz von NumberAxis.
     *
     * @param label      Die Bezeichnung für die Achse.
     * @param lowerBound Der untere Grenzwert des Achsenbereichs.
     * @param upperBound Der obere Grenzwert des Achsenbereichs.
     * @param tickUnit   Die Einheit für die Teilstriche auf der Achse.
     * @return Eine Instanz von NumberAxis.
     */
    private static NumberAxis numberAxis(String label, double lowerBound, double upperBound, double tickUnit) {
        NumberAxis numberAxis = new NumberAxis(label, lowerBound, upperBound, tickUnit);
        numberAxis.setMinorTickVisible(false);
        return numberAxis;
    }

    /**
     * Setzt die Geräteansicht für diese Grafikansicht.
     *
     * @param devices Die Geräteansicht, die visualisiert werden soll.
     */
    public void setDevices(DevicesView devices) {
        this.devices = devices;
    }

    /**
     * Gibt die aktuelle Zeit aus
     *
     * @return Zeit als int
     */
    public int getTime() {
        return time;
    }

    /**
     * Startet den Zeitablauf im Level.
     * Initialisiert die Timeline und spielt sie ab.
     */
    public void start() {
        timeLine = new Timeline(new KeyFrame(Duration.millis(Time.FRAME_DURATION), e -> step()));
        timeLine.setCycleCount(Timeline.INDEFINITE);
        timeLine.play();

        for (DeviceLevelModel device : levelModel.getAlwaysOnDevices()) {
            levelModel.registerDevice(device, time);
        }
    }

    /**
     * Stoppt den Zeitablauf im Level.
     * Pausiert die Timeline.
     */
    public void stop() {
        timeLine.stop();
    }

    /**
     * Setzt den Zeitablauf im Level fort.
     * Spielt die Timeline ab der aktuellen Zeit ab.
     */
    public void resume() {
        timeLine.playFrom(timeLine.getCurrentTime());
    }

    /**
     * Führt einen Schritt in der Simulation aus.
     * Aktualisiert die Geräte, berechnet die Solarproduktion und aktualisiert den Punktestand.
     */
    public void step() {

        powerTotal = 0.0;

        for (DeviceLevelModel device : levelModel.getAvailableDevices()) {
            if (device.isRunning()) {
                device.increaseUsageDone(Time.STEP);
                int startTime = (int) device.getStartTime();
                device.isOnStandby(device.getPowerPlan().apply(time - startTime));
            } else {
                device.setIsOnStandby(false);
            }

        }

        devices.update();

        addDeviceSeriesPoints();
        addSolarProductionSeriesPoints();        

        if (counter % 4 == 0) {
            score += calculateScoreStep();
            scoreProp.set(String.valueOf((int) score));
        }
        counter++;
        time += Time.STEP;
        timeProp.set(Time.millsToHours(time));
    }

    /**
     * Fügt die Datenpunkte aller Geräte während eines Spieldurchlaufs hinzu
     */
    private void addDeviceSeriesPoints() {
        List<DeviceLevelModel> toBeRemoved = new ArrayList<>();
        Map<Series<Number, Number>, Data<Number, Number>> newData = new HashMap<>();

        seriesDeviceMap.forEach((series, deviceModel) -> {
            Double power;
            if (deviceModel == null) {
                power = 0d;
            } else {
                int startTime = (int) deviceModel.getStartTime();
                power = deviceModel.getPowerPlan().apply(time - startTime);
                if (power == null || power == 0 || !deviceModel.isRunning()) {
                    toBeRemoved.add(deviceModel);
                    power = 0d;
                }
            }
            powerTotal += power;
            newData.put(series, new Data<>(Time.millsToHours(time), power));
        });

        newData.forEach((s, d) -> s.getData().add(d));
        toBeRemoved.forEach(levelModel::unregisterDevice);
    }

    /**
     * Fügt die Datenpunkte der Solarproduktionwährend eines Spieldurchlaufs hinzu
     */
    private void addSolarProductionSeriesPoints() {
        Map<Series<Number, Number>, Data<Number, Number>> newData = new HashMap<>();

        solarProduction.forEach((series, solarModel) -> {
            series.getNode().lookup(".chart-series-area-fill").setStyle("-fx-fill: transparent;");
            series.getNode().lookup(".chart-series-area-line")
                .setStyle("-fx-stroke-width: 6; -fx-stroke: #0121C1;");
            if (solarModel.dataPoints().containsKey((Double) Time.millsToHours(time))) {
                setSolarProductionSteps(solarModel);
            }
            newData.put(series, new Data<>(Time.millsToHours(time), calculateCurrentSolarProduction()));
        });

        newData.forEach((s, d) -> s.getData().add(d));
    }

    /**
     * Überprüft die Geräteziele am Ende der Simulation.
     *
     * @return Die Anzahl der erfüllten Geräteziele.
     */
    public int checkDeviceGoalsAtEnd() {
        return devices.checkDeviceGoals();
    }

    /**
     * Berechnet den aktuellen Score, welcher in der Step-Methode dazu gerechnet wird.
     * Das überziehen des Solarstroms gibt doppelt so viele Minuspunkte, wie
     * noral Pluspunkte.
     *
     * @return Berechneter Score
     */
    private double calculateScoreStep() {
        double scoreStep = 0;
        double solarPower = calculateCurrentSolarProduction();
        if (solarPower >= 0) {
            scoreStep = solarPower / 60;
        } else {
            scoreStep = solarPower / 30;
        }
        return scoreStep;
    }

    /**
     * Holt sich die Werte der Datenpunkte von dem aktuellen Solarproduktionspunkt
     * und dem nächsten Punkt.
     *
     * @param solarModel SolarModel mit den Punkten wo die Stromproduktion durchgeht
     */
    private void setSolarProductionSteps(SolarModel solarModel) {
        if (solarProductionNextStepPower == 0.0) {
            solarProductionCurrentStepPower = solarModel.dataPoints().get((Double) Time.millsToHours(time));
            solarProductionCurrentStepTime = (Double) Time.millsToHours(time);
        } else {
            solarProductionCurrentStepPower = solarProductionNextStepPower;
            solarProductionCurrentStepTime = solarProductionNextStepTime;
        }
        for (int i = time + Time.STEP; Time.millsToHours(i) <= 22; i += Time.STEP) {
            if (solarModel.dataPoints().containsKey((Double) Time.millsToHours(i))) {
                solarProductionNextStepPower = solarModel.dataPoints().get(Time.millsToHours(i));
                solarProductionNextStepTime = (Double) Time.millsToHours(i);
                break;
            }
        }
    }

    /**
     * Berechnet die aktuelle Stromproduktion der Solaranlage auf den genauen Zeitpunkt
     *
     * @return Stromproduktion zur aktuellen Zeit
     */
    private Double calculateCurrentSolarProduction() {
        return solarProductionCurrentStepPower + (Time.millsToHours(time) - solarProductionCurrentStepTime)
            * (solarProductionNextStepPower - solarProductionCurrentStepPower)
            / (solarProductionNextStepTime - solarProductionCurrentStepTime) - powerTotal;
    }

    /**
     * Gibt den aktuellen Score zurück
     *
     * @return Score als double Wert
     */
    public double getScore() {
        return score;
    }

    /**
     * Gibt die StringProperty des Scores zurück
     *
     * @return Score als StringProperty
     */
    public StringProperty getScoreProperty() {
        return scoreProp;
    }

    /**
     * Gibt die DoubleProperty der aktuellen Zeit zurück
     *
     * @return Zeit als DoubleProperty
     */
    public DoubleProperty getTimeProperty() {
        return timeProp;
    }

    /**
     * Gibt die TimeLine zurück
     *
     * @return benutzte Timeline
     */
    public Timeline getTimeLine() {
        return timeLine;
    }

}
