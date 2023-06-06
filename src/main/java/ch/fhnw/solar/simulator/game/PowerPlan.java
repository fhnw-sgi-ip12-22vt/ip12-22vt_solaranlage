package ch.fhnw.solar.simulator.game;

import java.util.function.Function;

/**
 * Der Power plan Interface stellt eine Funktion bereit, die zu jedem gegebenen Zeitpunkt
 * nach dem Einschalten eines Geräts (Device) die aktuelle Leistung ausgibt.
 */
public interface PowerPlan extends Function<Integer, Double> {


    /**
     * Erzeugt ein PowerPlan mit einer initialen Leistungsphase und einer anschließenden Standby-Leistung.
     *
     * @param startPower   Anfangsleistung in der initialen Phase
     * @param duration     Dauer der initialen Phase
     * @param standbyPower Standby-Leistung nach der initialen Phase
     * @return ein PowerPlan-Objekt, das zu Beginn die Anfangsleistung liefert und danach zur Standby-Leistung übergeht
     */
    static PowerPlan doubleLinearPower(double startPower, int duration, Double standbyPower) {
        return t -> t < duration ? startPower : standbyPower;
    }

    /**
     * Erzeugt ein PowerPlan mit mehreren Leistungsphasen und einer abschließenden Standby-Leistung.
     *
     * @param standbyPower finale Standby-Leistung
     * @param periods      Leistungsperioden, die der Power plan durchlaufen soll,
     * @return ein PowerPlan-Objekt, das in verschiedenen Phasen unterschiedliche Leistungen
     * liefert und am Ende zur Standby-Leistung übergeht
     */
    static PowerPlan periodsPower(Double standbyPower, Period... periods) {
        return t -> {
            for (Period period : periods) {
                if (t < period.endTime()) {
                    return period.power();
                }
            }
            return standbyPower;
        };
    }

    /**
     * Erzeugt ein PowerPlan mit mehreren Leistungsphasen, die eine bestimmte Anzahl an Durchläufen haben
     * und abschließend eine Standby-Leistung liefern.
     *
     * @param standbyPower finale Standby-Leistung
     * @param times        Anzahl der Durchläufe durch die Leistungsperioden
     * @param periods      Leistungsperioden, die der Power plan durchlaufen soll,
     * @return ein PowerPlan-Objekt, das mehrere Leistungsphasen mehrfach durchläuft
     * und am Ende zur Standby-Leistung übergeht
     */
    static PowerPlan multiplePeriodsPower(Double standbyPower, int times, Period... periods) {
        return t -> {
            int time = 0;
            for (int i = 0; i < times; i++) {
                for (Period period : periods) {
                    if (t < period.endTime() + time) {
                        return period.power();
                    }
                    time += period.endTime();
                }
            }
            return standbyPower;
        };
    }

    /**
     * Die Klasse Period repräsentiert eine Leistungsphase, die aus einer Dauer und einer Leistung besteht.
     *
     * @param endTime die Dauer der Leistungsphase
     * @param power   die Leistung während der Leistungsphase
     */
    record Period(int endTime, double power) {
    }

}
