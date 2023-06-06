package ch.fhnw.solar.simulator;

import javafx.beans.property.Property;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

import java.util.concurrent.Callable;

import ch.fhnw.solar.simulator.level.objects.DeviceView;

/**
 * Die Klasse Clickable dient zur Generierung von interaktiven Buttons.
 */
public final class Clickable {
    // Hinzufügen eines privaten Konstruktors, um eine Instanziierung zu verhindern
    private Clickable() {
        // Verhindert die Instanziierung
    }

    /**
     * Erstellt einen Button mit einem spezifizierten Label und EventHandler.
     *
     * @param labelText das Label des Buttons
     * @param deviceView definiertes Ziel für Device, falls vorhanden
     * @param handler der EventHandler des Buttons
     * @return ein Button-Objekt
     */
    public static Button button(String labelText, DeviceView deviceView, EventHandler<ActionEvent> handler) {
        var button = new Button();
        GridPane gridPane = new GridPane();
        Label label = new Label(labelText);
        label.setStyle("-fx-text-fill: white;");
        gridPane.add(label, 0, 0);
        if(deviceView != null) {
            gridPane.add(deviceView, 0, 1);
        }
        button.setGraphic(gridPane);
        button.setOnAction(handler);
        return button;
    }

    /**
     * Erstellt einen Button mit einem spezifizierten Label und EventHandler.
     * Eine spezielle Eigenschaft des Buttons wird
     * mithilfe von Factory-Methoden erstellt und an einen bestimmten Wert gebunden.
     *
     * @param <T> der Typ der zu bindenden Eigenschaft des Buttons
     * @param <R> der erzeugte Typ, der hier jedoch nicht verwendet wird
     *           (falls R nicht verwendet wird, sonst erklären Sie den Zweck)
     * @param label das Label, das auf dem erstellten Button angezeigt wird
     * @param deviceGoal den zu erreichenden Zielverbrauch falls vorhanden
     * @param handler der EventHandler, der auf Aktionen auf dem Button reagiert
     * @param buttonPropertyFactory die Factory-Methode, die die zu bindende Eigenschaft des Buttons erzeugt
     * @param valueFactory die Factory-Methode, die den Wert erzeugt, an den die Button-Eigenschaft gebunden wird
     * @return das erstellte und konfigurierte Button-Objekt
     */
    public static <T, R> Button button(
            String label,
            DeviceView deviceGoal,
            EventHandler<ActionEvent> handler,
            Callback<Button, Property<T>> buttonPropertyFactory,
            Callable<Property<T>> valueFactory) {
        var button = button(label, deviceGoal, handler);
        try {
            buttonPropertyFactory.call(button).bind(valueFactory.call());
        } catch (Exception e) {
            // Wenn es zu einer Ausnahme kommt, loggen wir die Fehlermeldung.
            System.err.println("Fehler beim Binden der Button-Eigenschaft: " + e.getMessage());
            e.printStackTrace();
        }
        return button;
    }

}
