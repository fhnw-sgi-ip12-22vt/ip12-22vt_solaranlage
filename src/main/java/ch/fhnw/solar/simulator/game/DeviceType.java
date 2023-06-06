package ch.fhnw.solar.simulator.game;

/**
 * DeviceType ist eine Enumeration, die die verschiedenen Arten von Geräten (Devices) darstellt,
 * die im Spiel verwendet werden können.
 */
public enum DeviceType {

    /**
     * Dieser Gerätetyp ist immer aktiv und kann weder eingeschaltet noch ausgeschaltet werden.
     */
    ALWAYS_ON,

    /**
     * Dieser Gerätetyp ist für eine festgelegte Zeit aktiv,
     * kann eingeschaltet werden, kann jedoch nicht ausgeschaltet werden.
     */
    FIXED_TIME_ON,

    /**
     * Dieser Gerätetyp ist für eine festgelegte Zeit aktiv,
     * danach geht es in den Standby-Modus.
     * Es kann sowohl eingeschaltet als auch ausgeschaltet werden.
     */
    PARTLY_ON

}
