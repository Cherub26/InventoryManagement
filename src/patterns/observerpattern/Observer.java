package patterns.observerpattern;

import patterns.compositepattern.Product;

/*
 * The Observer interface should be implemented by any class that wants to be notified of changes in a Product.
 * Classes implementing this interface should define the update method to handle the notification.
 */
public interface Observer {
    void update(Product product);
}