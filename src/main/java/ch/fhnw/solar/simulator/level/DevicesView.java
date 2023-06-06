package ch.fhnw.solar.simulator.level;

import ch.fhnw.solar.simulator.level.objects.DeviceView;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

/**
 * Diese Klasse verwaltet alle DeviceGoals (DeviceView) und aktualisiert diese.
 */
public class DevicesView extends VBox {

    // Liste der Geräteansichten
    private final List<DeviceView> devices = new ArrayList<>();

    /**
     * Fügt eine DeviceView der Liste hinzu, welche alle DeviceGoals verwaltet
     * 
     * @param deviceView Das hinzuzufügende DeviceGoal
     */
    public void addDeviceView(DeviceView deviceView) {
        devices.add(deviceView);
    }

    /**
     * Prüft die Ziele der einzelnen Geräte und gibt die Anzahl der Geräte zurück, deren Ziel noch nicht erreicht wurde.
     *
     * @return Anzahl der Geräte, deren Ziel noch nicht erreicht wurde.
     */
    public int checkDeviceGoals() {
        int counter = 0;
        for (DeviceView deviceView : devices) {
            if (deviceView.getProgress() < 1.0) {
                counter++;
            }
        }
        return counter;
    }

    /**
     * Aktualisiert alle Geräteansichten.
     */
    public void update() {
        for (DeviceView deviceView : devices) {
            deviceView.update();
        }
    }

}
