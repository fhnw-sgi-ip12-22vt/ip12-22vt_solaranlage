package ch.fhnw.solar.simulator.level;

import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.util.Duration;

/**
 * Die Klasse PopupNotification repräsentiert eine Popup-Benachrichtigung, die in der Benutzeroberfläche angezeigt wird.
 * Sie erbt von der JavaFX-Klasse Label und hat Methoden zum Anzeigen und Verstecken der Benachrichtigung.
 */
public class PopupNotification extends Label {

    /**
     * Konstanten zur Festlegung der Dauer der Animation und der Größe des Popups.
     */
    private static final Duration ANIMATION_DURATION = Duration.millis(1800);
    private static final double POPUP_WIDTH = 430;
    private static final double POPUP_HEIGHT = 90;
    /**
     * Flag zur Steuerung der Bewegung der Animation.
     */
    private boolean lock;

    /**
     * Erzeugt eine neue Popup-Benachrichtigung.
     */
    public PopupNotification() {
        createPopupLabel();
    }

    /**
     * Erstellt das Popup-Label.
     */
    private void createPopupLabel() {
        setPrefSize(POPUP_WIDTH, POPUP_HEIGHT);
        setTranslateX(POPUP_WIDTH);
        setPadding(new Insets(2));
        getStyleClass().add("popup");
    }

    /**
     * Führt eine Animation aus, die das Popup-Label nach außen bewegt.
     */
    public void moveOutsideAnimation() {
        TranslateTransition translateTransition = new TranslateTransition(ANIMATION_DURATION, this);
        translateTransition.setFromX(0);
        translateTransition.setToX(POPUP_WIDTH);
        translateTransition.play();
        new Thread(() -> {
            try {
                Thread.sleep((long) ANIMATION_DURATION.toMillis());
                lock = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * Führt eine Animation aus, die das Popup-Label nach innen bewegt.
     *
     * @param device der Name des Geräts, das in der Nachricht angezeigt werden soll.
     */
    public void moveInsideAnimation(String device) {
        lock = true;
        setText("Achtung " + device + " läuft momentan im Standby\nschalte ihn ab um Strom zu sparen!");
        TranslateTransition translateTransition = new TranslateTransition(ANIMATION_DURATION, this);
        translateTransition.setFromX(POPUP_WIDTH);
        translateTransition.setToX(0);
        translateTransition.play();
    }

    /**
     * Gibt den aktuellen Zustand des Locks zurück.
     *
     * @return den aktuellen Zustand des Locks.
     */
    public boolean getLock() {
        return lock;
    }
}
