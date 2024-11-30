package patterns.observerpattern;

import patterns.compositepattern.Product;

public interface Observer {
    void update(Product product);
}