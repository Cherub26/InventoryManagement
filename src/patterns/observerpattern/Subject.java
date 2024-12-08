package patterns.observerpattern;

import patterns.compositepattern.Product;

/*
 * The Subject interface should be implemented by any class that wants to manage a list of observers.
 * Classes implementing this interface should define methods to register, remove, and notify observers.
 */
public interface Subject {
    void registerObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers(Product product);
}
