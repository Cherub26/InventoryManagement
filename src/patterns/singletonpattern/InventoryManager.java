package patterns.singletonpattern;

import patterns.compositepattern.Product;
import patterns.compositepattern.ProductCategory;
import patterns.compositepattern.ProductComponent;
import patterns.observerpattern.Observer;
import patterns.observerpattern.StockManager;
import patterns.observerpattern.Subject;

public class InventoryManager {
    private static final InventoryManager instance = new InventoryManager();
    private ProductComponent rootCategory;
    private Subject stockManager;

    private InventoryManager() {
        this.rootCategory = new ProductCategory("Products");
        this.stockManager = new StockManager();
    }

    public void addAlert(Observer alert) {
        stockManager.registerObserver(alert);
    }

    public static InventoryManager getInstance() {
        return instance;
    }

    public void displayInventory() {
        rootCategory.display();
    }

    public void addProduct(ProductComponent category, ProductComponent product) {
        try {
            category.add(product);
        } catch (UnsupportedOperationException e) {
            System.out.println("Cannot add product to a product");
        }
    }

    public void removeProduct(ProductComponent category, ProductComponent product) {
        try {
            if(category.isInCategory(product)){
                category.remove(product);
            }else{
                System.out.println("Product not found in category");
            }
        } catch (UnsupportedOperationException e) {
            System.out.println("Cannot remove product from a product");
        }
    }

    public void setStock(ProductComponent product, int stock) {
        try {
            product.setStock(stock);
            stockManager.notifyObservers((Product)product);
        } catch (UnsupportedOperationException e) {
            System.out.println("Cannot set stock for a category");
        }
    }

    public void addStock(ProductComponent product, int stock) {
        try {
            product.setStock(product.getStock() + stock);
            stockManager.notifyObservers((Product)product);
        } catch (UnsupportedOperationException e) {
            System.out.println("Cannot add stock to a category");
        }
    }

    public void removeStock(ProductComponent product, int stock) {
        try {
            if(product.getStock() >= stock){
                product.setStock(product.getStock() - stock);
                stockManager.notifyObservers((Product)product);
            }else{
                System.out.println("Not enough stock");
            }
        } catch (UnsupportedOperationException e) {
            System.out.println("Cannot remove stock from a category");
        }
    }
}