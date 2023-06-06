package ch.fhnw.solar.simulator.level;

import javafx.animation.PauseTransition;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * Diese Klasse ist zuständig für die Initialisierung und den Ablauf der Regenanimation
 */
public class RainAnimation {
    
    private Pane rainContainer;

    /**
     * Konstruktor, welcher die Regenanimation startet.
     * 
     * @param container Element, welcher die Regenanimation angehört
     */
    public RainAnimation(Pane container) {
        this.rainContainer = container;
        rainAnimation();
    }

    /**
     * Diese Methode steuert eine Regenanimation, die nur ausgeführt wird, wenn das Level auf 3 gesetzt ist.
     * Die Animation besteht aus drei Wiederholungen eines sichtbaren und unsichtbaren Zustands der Regencontainer.
     * Die Sichtbarkeit ändert sich nach einer festgelegten Startverzögerung und Dauer.
     */
    public void rainAnimation() {
        // Startverzögerung und Dauer festlegen
        int startDelay = 18;
        int duration = 12;

        for (int i = 0; i < 3; i++) {
            // Sichtbarkeit nach Startverzögerung in Sekunden setzen
            PauseTransition visiblePause = new PauseTransition(Duration.seconds(startDelay + duration * i * 2));
            visiblePause.setOnFinished(event -> rainContainer.setVisible(true));
            visiblePause.play();

            if(i == 1) {
                startDelay -= 6;
            }

            // Sichtbarkeit auf falsch setzen nach Startverzögerung + Dauer in Sekunden
            PauseTransition invisiblePause = new PauseTransition(Duration.seconds(startDelay + duration * (i * 2 + 1)));
            invisiblePause.setOnFinished(event -> rainContainer.setVisible(false));
            invisiblePause.play();
        }
        
    }

}
