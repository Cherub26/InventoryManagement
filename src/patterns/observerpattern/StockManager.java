package patterns.observerpattern;

import patterns.compositepattern.Product;

import java.util.ArrayList;
import java.util.List;

/*
 * The StockManager class implements the Subject interface.
 * It manages a list of observers and notifies them of changes in a Product.
 */
public class StockManager implements Subject {
    private List<Observer> observers = new ArrayList<>();

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    // Notifies all registered observers of a change in the Product.
    @Override
    public void notifyObservers(Product product) {
        for (Observer observer : observers) {
            observer.update(product);
        }
    }
}
