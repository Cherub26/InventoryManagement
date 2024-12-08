package patterns.observerpattern;

import patterns.compositepattern.Product;
import javax.swing.JOptionPane;


/*
 * The StockAlert class implements the Observer interface.
 * It is used to display an alert when the stock of a Product is low.
 */
public class StockAlert implements Observer {
    private String alertName;

    public StockAlert(String alertName) {
        this.alertName = alertName;
    }

    /*
    * This method is called when the observed Product changes.
    * If the stock of the product is less than 5, it displays a low stock alert.
    */
    public void update(Product product) {
        if (product.getStock() < 5) {
            JOptionPane.showMessageDialog(null,
                    "Alert: " + alertName + " - Product " + product.getName() + " is low on stock: " + product.getStock(),
                    "Low Stock Alert",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
}
