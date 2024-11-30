package patterns.observerpattern;

import patterns.compositepattern.Product;

public class StockAlert implements Observer {
    private String alertName;

    public StockAlert(String alertName) {
        this.alertName = alertName;
    }

    public void update(Product product) {
        if (product.getStock() < 5) {
            System.out.println("Alert: " + alertName + " - Product " + product.getName() + " is low on stock: " + product.getStock());
        }
    }

}
