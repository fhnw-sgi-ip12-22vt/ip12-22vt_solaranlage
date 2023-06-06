package ch.fhnw.solar.simulator.levelSelect;

import ch.fhnw.solar.simulator.Solarsimulator;
import ch.fhnw.solar.simulator.game.GameController;
import ch.fhnw.solar.simulator.game.GameView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Diese Klasse ist die Controller-Komponente des MVC-Musters für den Level-Auswahlbildschirm.
 * Sie behandelt Benutzerinteraktionen und aktualisiert die Ansicht und das Modell entsprechend.
 */
public class LevelSelectController extends GameController implements Initializable {
    @FXML
    private Label levelSelectTitleLabel;
    @FXML
    private GameView levelSelectView;
    @FXML
    private Button rightButton;
    @FXML
    private Button leftButton;
    @FXML
    private Button selectButton;
    @FXML
    private Button backButton;
    @FXML
    private Button level1Button;
    @FXML
    private Button level2Button;
    @FXML
    private Button level3Button;

    private List<Button> levelButtons;
    private LevelSelectModel levelSelectModel;

    /**
     * Diese Methode wird aufgerufen, nachdem alle mit @FXML annotierten Mitglieder injiziert wurden.
     * Es initialisiert das Modell und aktualisiert die Ansicht basierend auf dem Modellzustand.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        levelSelectModel = new LevelSelectModel();

        levelSelectTitleLabel.setText(levelSelectModel.getButtonText("levelSelectTitle"));

        level1Button.setText(levelSelectModel.getButtonText("level1ButtonText"));
        level2Button.setText(levelSelectModel.getButtonText("level2ButtonText"));
        level3Button.setText(levelSelectModel.getButtonText("level3ButtonText"));

        rightButton.setText(levelSelectModel.getButtonText("rightButtonText"));
        leftButton.setText(levelSelectModel.getButtonText("leftButtonText"));
        selectButton.setText(levelSelectModel.getButtonText("selectButtonText"));
        backButton.setText(levelSelectModel.getButtonText("backButtonText"));

        levelButtons = Arrays.asList(level1Button, level2Button, level3Button);

        selectButton(levelButtons.get(levelSelectModel.getCurrentLevel()));
    }

    /**
     * Wendet einen visuellen Stil auf einen Button an, um anzuzeigen,
     * dass er ausgewählt ist, und entfernt den Stil von allen anderen.
     *
     * @param button der zu wählende Button
     */
    private void selectButton(Button button) {
        // Entferne den Stil von allen Buttons
        for (Button levelButton : levelButtons) {
            levelButton.getStyleClass().remove("btn-active");
        }

        // Setze den Stil für die ausgewählte Buttons
        button.getStyleClass().add("btn-active");
    }

    /**
     * Wählt das nächste Level aus, falls vorhanden.
     */
    @FXML
    private void selectNext() {
        if (levelSelectModel.getCurrentLevel() < levelButtons.size() - 1) {
            levelSelectModel.setCurrentLevel(levelSelectModel.getCurrentLevel() + 1);
            selectButton(levelButtons.get(levelSelectModel.getCurrentLevel()));
        }
    }

    /**
     * Wählt das vorherige Level aus, falls vorhanden.
     */
    @FXML
    private void selectPrevious() {
        if (levelSelectModel.getCurrentLevel() > 0) {
            levelSelectModel.setCurrentLevel(levelSelectModel.getCurrentLevel() - 1);
            selectButton(levelButtons.get(levelSelectModel.getCurrentLevel()));
        }
    }

    /**
     * Legt das aktuelle Level im Spielmodell fest und öffnet die Level ansicht.
     */
    @FXML
    private void selectLevel() {
        Solarsimulator.GAME_MODEL.setLevel(levelSelectModel.getCurrentLevel() + 1);
        try {
            openLevelView();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Öffnet die Level ansicht, die dem Benutzer das aktuelle Level präsentiert.
     * Es weist das Spielmodell auch an, ein neues Level zu laden.
     *
     * @throws IOException Wenn die FXML-Datei für die Level ansicht nicht geladen werden kann
     */
    @FXML
    private void openLevelView() throws IOException {
        Solarsimulator.GAME_MODEL.newLevel(Solarsimulator.GAME_MODEL.getLevel());
        super.setView(levelSelectView, "view/LevelView.fxml");
    }
}
