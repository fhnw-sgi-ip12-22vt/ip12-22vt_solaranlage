package ch.fhnw.solar.simulator.score;

import ch.fhnw.solar.simulator.Solarsimulator;
import ch.fhnw.solar.simulator.dataModel.User;
import ch.fhnw.solar.simulator.game.GameController;
import ch.fhnw.solar.simulator.game.GameView;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Der ScoreController ist die Controller-Klasse, die für die Verwaltung des Benutzer-Scores zuständig ist.
 */
public class ScoreController extends GameController implements Initializable {
    @FXML
    private GameView scoreView;
    @FXML
    private Label scoreTitleLabel;
    @FXML
    private Button playAgainButton;
    @FXML
    private Button chooseLevelButton;
    @FXML
    private Button upButton;
    @FXML
    private Button downButton;

    private ScoreModel scoreModel;

    @FXML
    private TableView<User> contactTable;
    @FXML
    private TableColumn<User, Integer> positionColumn;
    @FXML
    private TableColumn<User, String> nameColumn;
    @FXML
    private TableColumn<User, Integer> pointsColumn;
    @FXML
    private TableColumn<User, Integer> levelColumn;

    private int currentIndex = 0;   // Initialisierung des currentIndex auf 0

    /**
     * Standardkonstruktor.
     */
    public ScoreController() {

    }

    /**
     * Konstruktor mit ScoreModel Parameter.
     *
     * @param scoreModel Das zu verwendende ScoreModel.
     */
    public ScoreController(ScoreModel scoreModel) {
        this.scoreModel = scoreModel;
    }

    /**
     * Diese Methode wird aufgerufen, um einen Controller zu initialisieren,
     * nachdem sein Stamm-Element vollständig verarbeitet wurde.
     *
     * @param location  Der Ort, der zur Auflösung relativer Pfade für das Stamm-Objekt
     *                  verwendet wird, oder null, wenn der Ort nicht bekannt ist.
     * @param resources Die Ressourcen, die zur Lokalisierung des Stamm-Objekts verwendet wurden,
     *                  oder null, wenn das Stamm-Objekt nicht lokalisiert wurde.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialisierung und UI-Anpassung, Userdaten laden und anzeigen etc.
        scoreModel = new ScoreModel();

        scoreTitleLabel.setText(scoreModel.getButtonText("scoreTitle"));
        playAgainButton.setText(scoreModel.getButtonText("playAgainButtonText"));
        chooseLevelButton.setText(scoreModel.getButtonText("chooseLevelButtonText"));
        upButton.setText(scoreModel.getButtonText("upButtonText"));
        downButton.setText(scoreModel.getButtonText("downButtonText"));
        // Table
        positionColumn.setText(scoreModel.getButtonText("positionColumn"));
        nameColumn.setText(scoreModel.getButtonText("nameColumn"));
        pointsColumn.setText(scoreModel.getButtonText("pointsColumn"));
        levelColumn.setText(scoreModel.getButtonText("levelColumn"));

        positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        pointsColumn.setCellValueFactory(new PropertyValueFactory<>("points"));
        levelColumn.setCellValueFactory(new PropertyValueFactory<>("level"));

        // Setze hier den Pfad zu deiner Properties-Datei
        String propertiesFilePath = "src/main/config/userDataLevel1.properties";
        if (Solarsimulator.GAME_MODEL.getLevel() == 1) {
            propertiesFilePath = "src/main/config/userDataLevel1.properties";
        } else if (Solarsimulator.GAME_MODEL.getLevel() == 2) {
            propertiesFilePath = "src/main/config/userDataLevel2.properties";
        } else if (Solarsimulator.GAME_MODEL.getLevel() == 3) {
            propertiesFilePath = "src/main/config/userDataLevel3.properties";
        }

        ObservableList<User> list = scoreModel.loadUsersFromPropertiesFile(propertiesFilePath);
        contactTable.setItems(list);

        User lastUser = scoreModel.getLatestUser(); // Hole den neuesten Benutzer

        contactTable.setRowFactory(tv -> new TableRow<User>() {
            @Override
            protected void updateItem(User item, boolean empty) {
                super.updateItem(item, empty);

                if (item == lastUser) {
                    getStyleClass().add("current-user");
                } else {
                    getStyleClass().remove("current-user");
                }
            }
        });

        // Schaltflächen Hoch und Runter
        upButton.setOnAction(event -> {
            if (currentIndex > 0) {  // Überprüfen, ob currentIndex nicht am Anfang der Liste ist
                currentIndex--;  // currentIndex vermindern
                contactTable.scrollTo(currentIndex);
            }
        });

        downButton.setOnAction(event -> {
            // Überprüfen, ob currentIndex nicht am Ende der Liste ist
            if (currentIndex < contactTable.getItems().size() - 1) {
                currentIndex++;  // currentIndex erhöhen
                contactTable.scrollTo(currentIndex);
            }
        });
    }

    /**
     * Diese Methode wird verwendet, um die Spielansicht zu öffnen.
     *
     * @throws IOException Wenn, ein I/O-Fehler auftritt.
     */
    @FXML
    private void openGameView() throws IOException {
        super.setView(scoreView, "view/LevelView.fxml");
    }

    /**
     * Diese Methode wird verwendet, um die Level ansicht zu öffnen.
     *
     * @throws IOException Wenn, ein I/O-Fehler auftritt.
     */
    @FXML
    private void openLevelView() throws IOException {
        super.setView(scoreView, "view/LevelSelectView.fxml");
    }
}
