package patterns.factorymethodpattern;

import patterns.observerpattern.Observer;
import patterns.observerpattern.StockAlert;

/*
 * The AlertFactory class is a concrete implementation of the Factory class.
 * It creates instances of the Observer type, specifically StockAlert objects.
 */
public class AlertFactory extends Factory<Observer> {
    @Override
    public Observer create(String name) {
        return new StockAlert(name);
    }
} 