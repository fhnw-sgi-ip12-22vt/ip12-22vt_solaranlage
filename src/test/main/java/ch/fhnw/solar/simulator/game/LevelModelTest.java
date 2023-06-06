package ch.fhnw.solar.simulator.game;

import ch.fhnw.solar.simulator.game.PowerPlan.Period;
import ch.fhnw.solar.simulator.level.LevelModel;
import ch.fhnw.solar.simulator.level.objects.DeviceLevelModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static ch.fhnw.solar.simulator.game.PowerPlan.periodsPower;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Testklasse für das LevelModel.
 */
public class LevelModelTest {

    private List<DeviceModel> availableDeviceModels;
    private SolarModel solarPower;

    private DeviceLevelModel device1;
    private LevelModel levelModel;
    private Properties properties;

    /**
     * Diese Methode wird vor jedem Test ausgeführt.
     * Sie initialisiert die Testdaten und mocks einige Abhängigkeiten.
     */
    @BeforeEach
    public void beforeEach() {
        try (MockedStatic<GameModel> mockGameModel = Mockito.mockStatic(GameModel.class)) {
            properties = new Properties();
            // Beispielgerät, um sicherzustellen, dass die Eigenschaften nicht leer sind.
            properties.setProperty("device1", "Gitarre;PARTLY_ON;0;0;10;1.0;20");
            properties.setProperty("goal1", "1.5");

            mockGameModel.when(GameModel::getProperty).thenReturn(properties);
            PowerPlan plan = periodsPower(0.0, new Period(1, 100.0));
            availableDeviceModels = new ArrayList<>();
            device1 = new DeviceLevelModel(new DeviceModel(1,"Tumbler",
                DeviceType.PARTLY_ON, plan, 0), 1.0);
            availableDeviceModels.add(device1);
            Map<Double, Double> dataPoints = new HashMap<>();
            solarPower = new SolarModel("Solarstrom", dataPoints);
            levelModel = new LevelModel(availableDeviceModels, solarPower);
        }

    }

    /**
     * Überprüft, ob das Gerät korrekt initialisiert wurde.
     */
    @DisplayName("Device is initialized.")
    @Test
    public void testInitializationDevice() {
        //given

        //when

        //then
        assertFalse(device1.isRunning());
    }

    /**
     * Überprüft, ob das Gerät korrekt registriert wurde.
     */
    @DisplayName("Device gets registered.")
    @Test
    public void testRegisterDevice() {
        //given

        //when
        levelModel.registerDevice(device1, 0);

        //then
        assertTrue(device1.isRunning());
    }

    /**
     * Überprüft, ob das Gerät korrekt abgemeldet wurde.
     */
    @DisplayName("Device gets unregistered.")
    @Test
    public void testUnregisterDevice() {
        //given

        //when
        levelModel.registerDevice(device1, 0);
        levelModel.unregisterDevice(device1);

        //then
        assertFalse(device1.isRunning());
    }

}
