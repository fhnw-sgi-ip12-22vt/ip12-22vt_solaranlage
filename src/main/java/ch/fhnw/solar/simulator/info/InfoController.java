package ch.fhnw.solar.simulator.info;

import ch.fhnw.solar.simulator.Solarsimulator;
import ch.fhnw.solar.simulator.game.GameController;
import ch.fhnw.solar.simulator.game.GameView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Die Klasse InfoController verwaltet die Info-Ansicht der Solar Simulator-Anwendung.
 * Sie erweitert GameController und implementiert das Initialize Interface für die Initialisierung.
 */
public class InfoController extends GameController implements Initializable {
    /**
     * Die InfoModel Instanz wird zur Verwaltung der Daten der Anwendung verwendet.
     */
    @FXML
    private InfoModel infoModel;
    @FXML
    private GameView infoView;
    @FXML
    private Label infoTitleLabel;
    @FXML
    private Button backButton;
    @FXML
    private Button upButton;
    @FXML
    private Button downButton;
    @FXML
    private Label welcomeLabel;
    @FXML
    private Label gameExplanation1Label;
    @FXML
    private Label rulesTitleLabel;
    @FXML
    private ScrollPane infoScrollPane;
    @FXML
    private Label rule1Label;
    @FXML
    private Label rule2Label;
    @FXML
    private Label rule3Label;
    @FXML
    private Label rule4Label;
    @FXML
    private Label rule5Label;
    @FXML
    private Label rule6Label;
    @FXML
    private Label rule7Label;
    @FXML
    private Label rule8Label;
    @FXML
    private Label closingLabel;

    /**
     * Standardkonstruktor für die Klasse InfoController.
     */
    public InfoController() {
    }

    /**
     * Konstruktor für die Klasse InfoController mit InfoModel-Parameter.
     *
     * @param infoModel Die InfoModel-Instanz, die vom Controller verwaltet wird.
     */
    public InfoController(InfoModel infoModel) {
        this.infoModel = infoModel;
    }

    /**
     * Die Methode initialize wird aufgerufen, nachdem alle @FXML annotierten Mitglieder injiziert wurden.
     * Hier wird sie verwendet, um den Text für Labels und Buttons aus der InfoModel-Instanz zu setzen.
     *
     * @param location  Der Standort, der zur Auflösung relativer Pfade für das Root-Objekt
     *                  verwendet wird, oder null, wenn der Standort nicht bekannt ist.
     * @param resources Die Ressourcen, die zur Lokalisierung des Root-Objekts verwendet werden,
     *                  oder null, wenn das Root-Objekt nicht lokalisiert wurde.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        infoModel = new InfoModel();

        infoTitleLabel.setText(infoModel.getButtonText("infoTitle"));
        backButton.setText(infoModel.getButtonText("backButtonText"));
        upButton.setText(infoModel.getButtonText("upButtonText"));
        downButton.setText(infoModel.getButtonText("downButtonText"));

        welcomeLabel.setText(infoModel.getButtonText("welcomeText"));
        gameExplanation1Label.setText(infoModel.getButtonText("gameExplanation1"));
        rulesTitleLabel.setText(infoModel.getButtonText("rulesTitle"));
        rule1Label.setText(infoModel.getButtonText("rule1"));
        rule2Label.setText(infoModel.getButtonText("rule2"));
        rule3Label.setText(infoModel.getButtonText("rule3"));
        rule4Label.setText(infoModel.getButtonText("rule4"));
        rule5Label.setText(infoModel.getButtonText("rule5"));
        rule6Label.setText(infoModel.getButtonText("rule6"));
        rule7Label.setText(infoModel.getButtonText("rule7"));
        rule8Label.setText(infoModel.getButtonText("rule8"));
        closingLabel.setText(infoModel.getButtonText("closingText"));

        // Nach oben scrollen
        upButton.setOnAction(event -> {
            //  Ändern Sie diesen Wert, um die Scroll geschwindigkeit anzupassen
            double newValue = infoScrollPane.getVvalue() - 0.1;
            // Stellen Sie sicher, dass der Wert nicht unter 0 sinkt
            infoScrollPane.setVvalue(Math.max(newValue, 0));
        });

        // Nach unten scrollen
        downButton.setOnAction(event -> {
            double newValue =
                infoScrollPane.getVvalue() + 0.1;  // Ändern Sie diesen Wert, um die Scroll geschwindigkeit anzupassen
            infoScrollPane.setVvalue(Math.min(newValue, 1));  // Stellen Sie sicher, dass der Wert nicht über 1 steigt
        });
    }

    /**
     * Die Methode openLevelView wird verwendet, um die Level-Ansicht in der Anwendung zu öffnen.
     * Sie ruft auch die Methode newLevel auf der GAME_MODEL-Instanz auf, um ein neues Level zu laden.
     *
     * @throws IOException wenn die FXML-Datei für die Level-Ansicht nicht geladen werden kann.
     */
    @FXML
    private void openLevelView() throws IOException {
        Solarsimulator.GAME_MODEL.newLevel(Solarsimulator.GAME_MODEL.getLevel());
        super.setView(infoView, "view/LevelView.fxml");
    }
}
