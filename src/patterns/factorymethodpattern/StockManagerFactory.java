package patterns.factorymethodpattern;

import patterns.observerpattern.Subject;
import patterns.observerpattern.StockManager;

/*
 * The StockManagerFactory class is a concrete implementation of the Factory class.
 * It creates instances of the Subject type, specifically StockManager objects.
 */
public class StockManagerFactory extends Factory<Subject> {
    @Override
    public Subject create(String name) {
        return new StockManager();
    }
} 