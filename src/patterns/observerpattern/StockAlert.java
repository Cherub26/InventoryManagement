package patterns.observerpattern;

import patterns.compositepattern.Product;

import javax.swing.JOptionPane;

public class StockAlert implements Observer {
    private String alertName;

    public StockAlert(String alertName) {
        this.alertName = alertName;
    }

    public void update(Product product) {
        if (product.getStock() < 5) {
            JOptionPane.showMessageDialog(null,
                    "Alert: " + alertName + " - Product " + product.getName() + " is low on stock: " + product.getStock(),
                    "Low Stock Alert",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

}
