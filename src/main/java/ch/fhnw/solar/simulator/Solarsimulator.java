package ch.fhnw.solar.simulator;

import ch.fhnw.solar.simulator.game.GameModel;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Startklasse, hier beginnt das Programm.
 * 
 * Die "SCENE" wird dem GameModel übergeben.
 * 
 * Baut die "Stages" mit JavaFX auf, erzeugt das Fenster im Vollbildmodus und
 * beendet das Programm,
 * wenn das Fenster geschlossen wird.
 */
public class Solarsimulator extends Application {
    private static final FXMLLoader FXML_LOADER =
            new FXMLLoader(Solarsimulator.class.getResource("view/LevelSelectView.fxml"));
    // Statisches Objekt zum Benutzen aus anderen Klassen heraus.
    public static final GameModel GAME_MODEL = new GameModel(getFxmlLoader(), 1);
    private static Stage primaryStage;

    /** 
     * Startermethode
     */
    public static void main(String[] args) {
        launch();
    }

    /**
     * Gibt den FXMLLoader zurück
     *
     * @return den FXMLLoader
     */
    public static FXMLLoader getFxmlLoader() {
        return FXML_LOADER;
    }

    /**
     * Gibt die primäre Stage zurück
     *
     * @return die primäre Stage
     */
    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Startet die Anwendung.
     *
     * @param primaryStage Die primäre Stage
     * @throws Exception Wenn ein Fehler beim Laden der Szene auftritt
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Solarsimulator.primaryStage = primaryStage;
        Scene scene = new Scene(FXML_LOADER.load(), 1920, 1080);
        primaryStage.setFullScreen(true);
        primaryStage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
