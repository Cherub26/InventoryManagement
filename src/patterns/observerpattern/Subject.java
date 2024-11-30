package patterns.observerpattern;

import patterns.compositepattern.Product;

import java.util.List;
import java.util.ArrayList;

public interface Subject {
    void registerObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers(Product product);
}
