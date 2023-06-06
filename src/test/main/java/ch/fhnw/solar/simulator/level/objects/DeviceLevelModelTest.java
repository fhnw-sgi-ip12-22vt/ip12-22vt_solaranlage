package ch.fhnw.solar.simulator.level.objects;

import ch.fhnw.solar.simulator.game.DeviceModel;
import ch.fhnw.solar.simulator.game.DeviceType;
import ch.fhnw.solar.simulator.game.PowerPlan;
import ch.fhnw.solar.simulator.game.PowerPlan.Period;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static ch.fhnw.solar.simulator.game.PowerPlan.periodsPower;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Testklasse für das DeviceLevelModel.
 */
public class DeviceLevelModelTest {

    private PowerPlan plan;
    private DeviceModel testDeviceModel;
    private DeviceLevelModel testDevice;

    /**
     * Diese Methode wird vor jedem Test ausgeführt.
     * Sie initialisiert die Testdaten.
     */
    @BeforeEach
    public void beforeEach() {
        plan = periodsPower(0.0, new Period(1, 100.0));
        testDeviceModel = new DeviceModel(1,"Tumbler", DeviceType.PARTLY_ON, plan, 0.0);
        testDevice = new DeviceLevelModel(testDeviceModel, 1.5);
    }

    /**
     * Überprüft, ob das Gerät korrekt initialisiert wurde.
     */
    @DisplayName("Device gets initialized.")
    @Test
    public void testDeviceGetters() {
        //given

        //when

        //then
        assertEquals("Tumbler", testDevice.getName());
        assertEquals(plan, testDevice.getPowerPlan());
        assertEquals(DeviceType.PARTLY_ON, testDevice.getType());
        assertEquals(0.0, testDevice.getStandby());
        assertEquals(1.5, testDevice.getUsageGoal());
    }

    /**
     * Überprüft, ob das Gerät im Standby-Modus ist.
     */
    @DisplayName("Device is on Standby.")
    @Test
    public void testDeviceIsOnStandby() {
        //given

        //when
        testDevice.setIsOnStandby(true);

        //then
        assertTrue(testDevice.getIsOnStandby());
    }

    /**
     * Überprüft, ob das Gerät läuft.
     */
    @DisplayName("Device is Running.")
    @Test
    public void testDeviceRunning() {
        //given

        //when
        testDevice.setRunning(true);

        //then
        assertTrue(testDevice.isRunning());
    }

    /**
     * Überprüft, ob das Gerät sein Nutzungsziel erhöht.
     */
    @DisplayName("Device starts to reach his usageGoal.")
    @Test
    public void testDeviceIncreasesUsageDone() {
        //given

        //when
        testDevice.increaseUsageDone(600);

        //then
        assertEquals(600, testDevice.getUsageDone());
    }

}
