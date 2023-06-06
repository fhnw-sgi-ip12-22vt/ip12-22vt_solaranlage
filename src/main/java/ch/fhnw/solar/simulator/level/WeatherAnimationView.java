package ch.fhnw.solar.simulator.level;

import ch.fhnw.solar.simulator.Solarsimulator;
import javafx.animation.PathTransition;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Die Klasse WeatherAnimationView ist zuständig für die Darstellung der Wetteranimation auf dem Bildschirm.
 */
public class WeatherAnimationView extends Pane {
    // Konstanten für die Animation
    private final int sunRadius = 120;
    private final int yValue = 200;
    private final String[] bgColors = new String[20];
    private final Path path;
    private PathTransition pt;

    /**
     * Konstruktor für WeatherAnimationView, der der Standardbildschirmbreite einstellt.
     */
    public WeatherAnimationView() {
        this(1920);
    }

    /**
     * Konstruktor für WeatherAnimationView, der eine spezifische Bildschirmbreite akzeptiert.
     *
     * @param screenWidth Die Breite des Bildschirms, auf dem die Wetteranimation dargestellt wird.
     */
    public WeatherAnimationView(double screenWidth) {
        path = new Path();
        path.getElements().add(new MoveTo(-120, yValue));
        path.getElements().add(new QuadCurveTo(screenWidth / 2, -yValue,
            screenWidth + sunRadius, yValue));

        // Level Pfad
        String levelPath = "src/main/config/level1.properties";

        if (Solarsimulator.GAME_MODEL.getLevel() == 1) {
            levelPath = "src/main/config/level1.properties";
        } else if (Solarsimulator.GAME_MODEL.getLevel() == 2) {
            levelPath = "src/main/config/level2.properties";
        } else if (Solarsimulator.GAME_MODEL.getLevel() == 3) {
            levelPath = "src/main/config/level3.properties";
        }

        // Lade die Farben vom Property File
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(levelPath));
            for (int i = 1; i <= 20; i++) {
                bgColors[i - 1] = properties.getProperty("color" + i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        initializeSunPathTransition();
    }

    /**
     * Diese Methode pausiert die Wetteranimation.
     */
    public void pauseAnimation() {
        pt.pause();
    }

    /**
     * Diese Methode setzt die Wetteranimation fort.
     */
    public void resumeAnimation() {
        pt.playFrom(pt.getCurrentTime());
    }

    /**
     * Initialisiert den PathTransition für die Sonnenanimation.
     */
    private void initializeSunPathTransition() {
        pt = new PathTransition();
        pt.setDuration(Duration.seconds(100));
        pt.setNode(this);
        pt.setPath(path);
        pt.setCycleCount(1);
        pt.setAutoReverse(false);

        Platform.runLater(() -> {
            pt.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
                double progress = newValue.toSeconds() / 100.0; // Durch die Gesamtdauer des Pfadübergangs teilen.

                // Bestimme, in welchem Abschnitt des Bildschirms sich die Animation befindet.
                int section = (int) (progress * 20);

                if (section >= 0 && section < bgColors.length) {
                    if (getScene() != null && getScene().getRoot() != null) {
                        getScene().getRoot().setStyle("-fx-background-color: " + bgColors[section] + ";");
                    }
                }
            });

            pt.play();
        });
    }
}
