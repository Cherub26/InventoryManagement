package patterns.observerpattern;

import patterns.compositepattern.Product;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public void notifyObservers(Product product) {
        for (Observer observer : observers) {
            observer.update(product);
        }
    }
}
