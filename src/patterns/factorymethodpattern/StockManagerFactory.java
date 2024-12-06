package patterns.factorymethodpattern;

import patterns.observerpattern.Subject;
import patterns.observerpattern.StockManager;

public class StockManagerFactory {
    public Subject createStockManager() {
        return new StockManager();
    }
} 