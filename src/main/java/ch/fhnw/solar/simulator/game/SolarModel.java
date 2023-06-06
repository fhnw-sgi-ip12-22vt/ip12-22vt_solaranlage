package ch.fhnw.solar.simulator.game;

import java.util.Map;

/**
 * Die Klasse SolarModel repräsentiert ein Modell einer Solaranlage und ihre Stromproduktion.
 * Sie speichert die Datenpunkte der Stromproduktion in Bezug auf verschiedene Parameter als Map.
 *
 * @param name       Der Name des SolarModells.
 * @param dataPoints Eine Map der Datenpunkte der Stromproduktion. Jeder Datenpunkt wird
 *                   durch ein Paar von Doubles repräsentiert, wobei der Schlüssel den
 *                   Parameter und der Wert die entsprechende Stromproduktion darstellt.
 */
public record SolarModel(String name, Map<Double, Double> dataPoints) {
}
