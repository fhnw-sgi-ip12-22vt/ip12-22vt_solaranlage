package ch.fhnw.solar.simulator.level;

import ch.fhnw.solar.simulator.Clickable;
import ch.fhnw.solar.simulator.Solarsimulator;
import ch.fhnw.solar.simulator.game.DeviceType;
import ch.fhnw.solar.simulator.game.GameController;
import ch.fhnw.solar.simulator.game.GameView;
import ch.fhnw.solar.simulator.level.objects.DeviceLevelModel;
import ch.fhnw.solar.simulator.level.objects.DeviceView;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Der LevelController ist die Hauptklasse für die Steuerung der Level im Solarsimulator.
 * Sie ist verantwortlich für die Initialisierung und Aktualisierung von Spielelementen wie
 * Spielansicht, Animationen, Geräte und Punktestand. Der LevelController erweitert den
 * GameController und implementiert Initialize, um die initiale Setup-Funktion bereitzustellen.
 */
public class LevelController extends GameController implements Initializable {

    // Definition der Pfade zu den Hintergrundbildern und Hausbildern
    private final URL bgPath1 = Solarsimulator.class.getResource("img/background-L1.png");
    private final URL bgPath2 = Solarsimulator.class.getResource("img/background-L2.png");
    private final URL bgPath3 = Solarsimulator.class.getResource("img/background-L3.png");
    private final URL hausPath1 = Solarsimulator.class.getResource("img/haus-L1.png");
    private final URL hausPath2 = Solarsimulator.class.getResource("img/haus-L2.png");
    private final URL hausPath3 = Solarsimulator.class.getResource("img/haus-L3.png");
    private final LevelModel levelModel;
    // Deklaration der FXML Elemente
    @FXML
    private GameView levelView;
    @FXML
    private Pane rainContainer;
    @FXML
    private WeatherAnimationView sunContainer;
    @FXML
    private BorderPane bgContainer;
    @FXML
    private Label levelTitle;
    @FXML
    private Label scoreLabel;
    @FXML
    private Label highScore;
    @FXML
    private Button levelButton;
    @FXML
    private Button helpButton;
    @FXML
    private Button pausePlayButton;
    @FXML
    private Button restartButton;
    @FXML
    private HBox devices;
    @FXML
    private PopupNotification popup;
    @FXML
    private Pane hausContainer;
    @FXML
    private GraphView graphContainer;
    // Andere Variablen
    private boolean isGraphPlaying = true;
    private int endScore;
    private DeviceLevelModel standbyDevice;
    private DevicesView deviceGoals;


    /**
     * Konstruktor für LevelController mit Standard-Level-Modell.
     */
    public LevelController() {
        this(Solarsimulator.GAME_MODEL.getLevelModel());
    }

    /**
     * Erstellt eine neue Instanz des LevelControllers mit gegebenem LevelModel.
     *
     * @param levelModel Das Modell, das den Zustand und die Logik des aktuellen Levels enthält.
     */
    public LevelController(LevelModel levelModel) {
        deviceGoals = new DevicesView();
        this.levelModel = levelModel;
    }

    /**
     * Die Methode initialisiert die LevelView mit entsprechenden Bildern, Animationen und Texten.
     * Sie aktualisiert auch die Anzeige von Gerätebuttons basierend auf den Status der Geräte und
     * überwacht die Zeit, um den Level abschluss und den Übergang zur nächsten Ansicht zu bestimmen.
     *
     * @param location  Der Speicherort, der zum Aufladen des Roots relativ ist,
     *                  oder null, wenn der Pfad nicht relativ ist.
     * @param resources Die Ressourcen, die zum Verknüpfen der Wurzel verwendet werden sollen.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        changeBackgroundImage();

        int level = Solarsimulator.GAME_MODEL.getLevel();
        levelTitle.setText(levelModel.getButtonText("levelTitleText" + level));

        if (level == 3) {
            levelTitle.setText(levelModel.getButtonText("levelTitleText3"));
            new RainAnimation(rainContainer);
        }

        scoreLabel.setText(levelModel.getButtonText("scoreText"));
        levelButton.setText(levelModel.getButtonText("levelButtonText"));
        helpButton.setText(levelModel.getButtonText("helpButtonText"));
        pausePlayButton.setText(levelModel.getButtonText("pauseButtonText"));
        restartButton.setText(levelModel.getButtonText("restartButtonText"));

        graphContainer.getTimeProperty().addListener((observable, oldValue, newValue) -> {
            for (DeviceLevelModel device : levelModel.getPartlyOnDevices()) {
                if (device.getIsOnStandby() && !popup.getLock()) {
                    standbyDevice = device;
                    popup.moveInsideAnimation(device.getName());
                } else if (device == standbyDevice && !standbyDevice.getIsOnStandby()) {
                    standbyDevice = null;
                    popup.moveOutsideAnimation();
                }
            }
            if (newValue.doubleValue() >= 22.0) {
                finishLevel();
            }
        });

        highScore.textProperty().bindBidirectional(graphContainer.getScoreProperty());
        devices.getChildren().add(deviceButtons());
        graphContainer.setDevices(deviceGoals);
        graphContainer.start();
    }

    /**
     * Diese Methode wechselt in die PreScoreView und startet die Punkteberechnung.
     * Wird aufgerufen, wenn das Level zu Ende ist.
     */
    private void finishLevel() {
        try {
            sunContainer.pauseAnimation();
            graphContainer.stop();
            Solarsimulator.GAME_MODEL.getPreScoreModel()
                .setMissingGoals(graphContainer.checkDeviceGoalsAtEnd());
            Solarsimulator.GAME_MODEL.getPreScoreModel().setEndScore((int) graphContainer.getScore());
            Thread.sleep(1500);
            Solarsimulator.GAME_MODEL.newLevel(Solarsimulator.GAME_MODEL.getLevel());
            Solarsimulator.GAME_MODEL.setView(levelView,  "view/PreScoreView.fxml");
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Diese Methode gibt den Endpunktestand des Spiels zurück.
     *
     * @return Der Endpunktestand des Spiels.
     */
    public int getEndScore() {
        return endScore;
    }

    /**
     * Diese Methode erzeugt und initialisiert die Buttons für die verfügbaren Geräte in der GridPane.
     * Jeder Button ist mit einem Gerät aus dem LevelModel verknüpft und registriert oder unregistriert das Gerät,
     * wenn darauf geklickt wird. Der Betriebsstatus des Geräts (laufen oder nicht) wird ebenfalls umgeschaltet.
     * Die Methode positioniert auch die Buttons in der GridPane basierend auf ihrer eindeutigen ID.
     *
     * @return Ein GridPane, das alle initialisierten Geräte-Buttons enthält.
     */
    private GridPane deviceButtons() {
        GridPane gridPane = new GridPane();
        AtomicInteger index = new AtomicInteger(1);
    
        levelModel.getAvailableDevices().forEach(d -> {
            DeviceView deviceGoal = null;
            if (d.getUsageGoal() > 0.0) {
                deviceGoal = new DeviceView(d);
                deviceGoals.addDeviceView(deviceGoal);
            }
    
            Button button = createDeviceButton(d, deviceGoal, index);
    
            d.runningProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    button.getStyleClass().add("btn-active");
                } else {
                    button.getStyleClass().remove("btn-active");
                }
            });
    
            Pair<Integer, Integer> position = getGridPositionFromButton(button);
            GridPane.setRowIndex(button, position.getKey());
            GridPane.setColumnIndex(button, position.getValue());
    
            gridPane.getChildren().add(button);
        });
    
        return gridPane;
    }
    
    /**
     * Erstellt für die Geräte den jeweiligen klickbaren Button
     * 
     * @param device Gerät, für welcher es erstellt wird
     * @param deviceGoal Geräteziel des Gerätes, welcher im Button drin ist
     * @param index Index des Gerätes
     * @return
     */
    private Button createDeviceButton(DeviceLevelModel device, DeviceView deviceGoal, AtomicInteger index) {
        Button button = Clickable.button(
                device.getName(),
                deviceGoal,
                e -> handleDeviceButtonClick(device),
                Node::disableProperty,
                () -> new SimpleBooleanProperty(false));
    
        button.setId("device-button-" + index.getAndIncrement());
        button.setMinHeight(64);
        button.setPrefSize(250, 0);
    
        return button;
    }
    
    /**
     * Hier wird definiert, was die Optionen sein sollen, wenn ein Gerät angeklickt wird.
     * An- und abschaltbare, wie auch nur anschaltbare Geräte werden hier unterschieden.
     * 
     * @param device Gerät für welches es konfiguriert wird
     */
    private void handleDeviceButtonClick(DeviceLevelModel device) {
        if (device.getType() == DeviceType.PARTLY_ON && !device.isRunning()) {
            levelModel.registerDevice(device, graphContainer.getTime());
        } else if (device.getType() == DeviceType.PARTLY_ON || device.getType() == DeviceType.FIXED_TIME_ON) {
            if (!device.isRunning()) {
                levelModel.registerDevice(device, graphContainer.getTime());
            } else {
                levelModel.unregisterDevice(device);
            }
        }
    }

    /**
     * Diese Methode bestimmt die Position eines Buttons im GridPane basierend auf seiner ID.
     * Die Button-ID wird dazu benutzt, den Index des Buttons zu ermitteln, der dann in eine
     * Zeilen- und Spaltennummer für das GridPane umgewandelt wird.
     *
     * @param button Der Button, für den die GridPane-Position bestimmt werden soll.
     * @return Ein Paar von Integer, das die Zeilen- und Spaltennummer des Buttons im GridPane darstellt.
     */
    private Pair<Integer, Integer> getGridPositionFromButton(Button button) {
        int btnIndex = Integer.parseInt(button.getId().split("-")[2]);
        int row, col;
    
        // Hier wird ein Mapping von Gerät zu Zeile und Spalte gemacht.
        // Bsp. Gerät 1 -> Zeile 0 und Spalte 0
        int[] rowIndexMapping = {0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 2, 2};
        int[] colIndexMapping = {0, 1, 2, 3, 4, 5, 0, 1, 3, 5, 0, 5};
    
        if (btnIndex >= 1 && btnIndex <= 12) {
            row = rowIndexMapping[btnIndex - 1];
            col = colIndexMapping[btnIndex - 1];
        } else {
            row = 0;
            col = 0;  // Default position
        }
    
        return new Pair<>(row, col);
    }

    /**
     * Diese Methode wird aufgerufen, wenn der LevelSelectView geöffnet werden soll.
     * Bevor der LevelSelectView geöffnet wird, werden, dass GrafContainer und das SunContainer gestoppt,
     * um sicherzustellen, dass keine Spielaktivitäten im Hintergrund laufen.
     * Danach wird der LevelSelectView geladen und angezeigt, und ein neues Level im Spielmodell wird erstellt.
     *
     * @throws IOException wenn, ein Fehler beim Laden des LevelSelectView auftritt.
     */
    @FXML
    private void openLevelView() throws IOException {
        graphContainer.stop();
        sunContainer.pauseAnimation();
        super.setView(levelView, "view/LevelSelectView.fxml");
        Solarsimulator.GAME_MODEL.newLevel(Solarsimulator.GAME_MODEL.getLevel());
    }

    /**
     * Diese Methode wird aufgerufen, wenn der InfoView geöffnet werden soll.
     * Bevor der InfoView geöffnet wird, werden, dass GrafContainer und das SunContainer gestoppt,
     * um sicherzustellen, dass keine Spielaktivitäten im Hintergrund laufen.
     * Danach wird der InfoView geladen und angezeigt, und ein neues Level im Spielmodell wird erstellt.
     *
     * @throws IOException wenn, ein Fehler beim Laden der InfoView auftritt.
     */
    @FXML
    private void openInfoView() throws IOException {
        graphContainer.stop();
        sunContainer.pauseAnimation();
        super.setView(levelView, "view/InfoView.fxml");
        Solarsimulator.GAME_MODEL.newLevel(Solarsimulator.GAME_MODEL.getLevel());
    }

    /**
     * Pausiert oder spielt das Diagramm ab, abhängig vom aktuellen Zustand (entweder abgespielt oder pausiert).
     * Wenn das Diagramm gerade abgespielt wird, wird es gestoppt und die Animation der Sonne wird pausiert.
     * Der Text des Pausen/Spiel-Buttons wird auf "Play" gesetzt.
     * Wenn das Diagramm gerade pausiert ist, wird es fortgesetzt und die Animation der Sonne wird wieder aufgenommen.
     * Der Text des Pausen/Spiel-Buttons wird auf "Pause" gesetzt.
     * Am Ende der Methode wird der Status von isGrafPlaying invertiert.
     * Diese Methode ist mit der FXML-Annotation markiert, was bedeutet, dass sie als
     * Handler für eine Aktion im FXML-Dokument verwendet wird.
     *
     * @throws IOException Wenn, ein Fehler beim Steuern des Diagramms oder der Sonnenanimation auftritt.
     */
    @FXML
    private void pausePlayGraph() throws IOException {
        if (isGraphPlaying) {
            graphContainer.stop();
            sunContainer.pauseAnimation();
            pausePlayButton.setText("Play");
        } else {
            graphContainer.resume();
            sunContainer.resumeAnimation();
            pausePlayButton.setText("Pause");
        }
        isGraphPlaying = !isGraphPlaying;
    }

    /**
     * Startet das Spiel neu. Beendet zuerst die aktuell laufenden Operationen,
     * darunter das Stoppen des GrafContainers und das Pausieren der Sonnen-Animation.
     * Erstellt danach ein neues Level basierend auf dem aktuellen Level und setzt die Ansicht auf die LevelView zurück.
     * Diese Methode ist mit der FXML-Annotation markiert, was bedeutet, dass sie als Handler
     * für eine Aktion im FXML-Dokument verwendet wird.
     *
     * @throws RuntimeException Wenn, ein Fehler beim Setzen der Ansicht auftritt.
     */
    @FXML
    private void restartGame() {
        try {
            graphContainer.stop();
            sunContainer.pauseAnimation();
            Solarsimulator.GAME_MODEL.newLevel(Solarsimulator.GAME_MODEL.getLevel());
            super.setView(levelView, "view/LevelView.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Diese Methode ermöglicht es, die Hintergrundbilder je nach Level zu ändern.
     */
    public void changeBackgroundImage() {
        if (Solarsimulator.GAME_MODEL.getLevel() == 1) {
            bgContainer.setStyle("-fx-background-image: url('" + bgPath1 + "');");
            hausContainer.setStyle("-fx-background-image: url('" + hausPath1 + "');");
        } else if (Solarsimulator.GAME_MODEL.getLevel() == 2) {
            bgContainer.setStyle("-fx-background-image: url('" + bgPath2 + "');");
            hausContainer.setStyle("-fx-background-image: url('" + hausPath2 + "');");
        } else if (Solarsimulator.GAME_MODEL.getLevel() == 3) {
            bgContainer.setStyle("-fx-background-image: url('" + bgPath3 + "');");
            hausContainer.setStyle("-fx-background-image: url('" + hausPath3 + "');");
        }
    }

}
