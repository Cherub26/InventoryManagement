package patterns.factorymethodpattern;

import patterns.observerpattern.Subject;
import patterns.observerpattern.StockManager;

public class StockManagerFactory extends Factory<Subject> {
    @Override
    public Subject create(String name) {
        return new StockManager();
    }
} 