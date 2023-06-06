package ch.fhnw.solar.simulator.preScore;

import ch.fhnw.solar.simulator.Solarsimulator;
import ch.fhnw.solar.simulator.game.GameController;
import ch.fhnw.solar.simulator.game.GameView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Diese Klasse ist der Controller-Komponente des MVC-Musters für den Pre-Score-Bildschirm.
 * Sie behandelt Benutzerinteraktionen und aktualisiert die Ansicht und das Modell entsprechend.
 */
public class PreScoreController extends GameController implements Initializable {

    /**
     * Die PreScoreModel-Instanz, die für die Verwaltung der Anwendungsdaten verwendet wird.
     */
    private final PreScoreModel preScoreModel;
    /**
     * Die GameView-Instanz, die die Info-Ansicht in der Anwendung darstellt.
     */
    @FXML
    private GameView preScoreView;
    @FXML
    private Label preScoreTitleLabel;
    @FXML
    private Label scoreLabel;
    @FXML
    private Label pointsLabelTop;
    @FXML
    private Button confirmButton;
    @FXML
    private Button randomNameButton;
    @FXML
    private Label pointsLabel;
    @FXML
    private Label scoreValueAtEnd;
    @FXML
    private Label missingGoalsLabel;
    @FXML
    private Label pointsLost;
    @FXML
    private Label endPointsLabel;
    @FXML
    private Label scoreValue;
    @FXML
    private Label levelLabel;
    @FXML
    private Label selectedLevelLabel;
    @FXML
    private Label randomNameLabel;
    @FXML
    private Label nameLabel;

    /**
     * Konstruktor, ohne Parameter für Initialisierung von FXML
     */
    public PreScoreController() {
        this(Solarsimulator.GAME_MODEL.getPreScoreModel());
    }

    /**
     * Konsturktor, welcher das PreScoreModel definiert.
     * 
     * @param preScoreModel Verbundenes PreScoreModel
     */
    public PreScoreController(PreScoreModel preScoreModel) {
        this.preScoreModel = preScoreModel;
    }

    /**
     * Diese Methode wird nach der Initialisierung aller @FXML markierten Mitglieder aufgerufen.
     * Sie initialisiert das Modell und aktualisiert die Ansicht basierend auf dem Modellzustand.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Data
        pointsLabelTop.setText(String.valueOf(preScoreModel.getEndScore()));
        scoreValueAtEnd.setText(String.valueOf(preScoreModel.getEndScoreWithoutLostPoints()));
        pointsLost.setText(String.valueOf(preScoreModel.getLostPoints()));
        scoreValue.setText(String.valueOf(preScoreModel.getEndScore()));
        selectedLevelLabel.setText(String.valueOf(Solarsimulator.GAME_MODEL.getLevel()));

        // Buttons
        confirmButton.setText(preScoreModel.getButtonText("confirmButtonText"));
        randomNameButton.setText(preScoreModel.getButtonText("randomNameButtonText"));
        // Labels
        preScoreTitleLabel.setText(preScoreModel.getButtonText("preScoreTitle"));
        scoreLabel.setText(preScoreModel.getButtonText("scoreText"));
        pointsLabel.setText(preScoreModel.getButtonText("pointsLabel"));
        missingGoalsLabel.setText(preScoreModel.getButtonText("missingGoalsLabel"));
        endPointsLabel.setText(preScoreModel.getButtonText("endPointsLabel"));
        levelLabel.setText(preScoreModel.getButtonText("levelLabel"));
        randomNameLabel.setText(preScoreModel.getButtonText("randomNameLabel"));


        try {
            nameLabel.setText(preScoreModel.getRandomName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Öffnet die ScoreView, welche dem Benutzer die erreichte Punktzahl anzeigt.
     *
     * @param event das auslösende Ereignis
     * @throws IOException wenn die FXML-Datei für die ScoreView nicht geladen werden kann
     */
    @FXML
    private void openScoreView(ActionEvent event) throws IOException {
        // Sichert die User Daten
        preScoreModel.saveUserData(Solarsimulator.GAME_MODEL.getLevel(),
            nameLabel.getText(), preScoreModel.getEndScore());
        super.setView(preScoreView, "view/ScoreView.fxml");
    }

    /**
     * Generiert und setzt einen zufälligen Namen.
     *
     * @param event das auslösende Ereignis
     */
    @FXML
    private void setRandomName(ActionEvent event) {
        try {
            nameLabel.setText(preScoreModel.getRandomName());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
