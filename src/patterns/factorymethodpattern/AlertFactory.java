package patterns.factorymethodpattern;

import patterns.observerpattern.Observer;
import patterns.observerpattern.StockAlert;

public class AlertFactory extends Factory<Observer> {
    @Override
    public Observer create(String name) {
        return new StockAlert(name);
    }
} 