import patterns.compositepattern.Product;
import patterns.compositepattern.ProductCategory;
import patterns.observerpattern.StockAlert;
import patterns.singletonpattern.InventoryManager;

public class Main {
    public static void main(String[] args) {
        InventoryManager inventoryManager = InventoryManager.getInstance();

        // Everything under here is testing code
        ProductCategory electronics = new ProductCategory("Electronics");
        ProductCategory clothing = new ProductCategory("Clothing");
        ProductCategory phones = new ProductCategory("Phones");
        ProductCategory laptops = new ProductCategory("Laptops");
        ProductCategory men = new ProductCategory("Men");
        ProductCategory women = new ProductCategory("Women");
        electronics.add(phones);
        electronics.add(laptops);
        clothing.add(men);
        clothing.add(women);
        electronics.add(new Product("testingProduct", 9));
        phones.add(new Product("iPhone", 50));
        phones.add(new Product("Samsung Galaxy", 30));
        laptops.add(new Product("MacBook Pro", 20));
        laptops.add(new Product("Dell XPS", 15));
        men.add(new Product("Men's T-Shirt", 100));
        men.add(new Product("Men's Jeans", 60));
        women.add(new Product("Women's Dress", 80));
        women.add(new Product("Women's Skirt", 40));
        inventoryManager.getRootCategory().add(electronics);
        inventoryManager.getRootCategory().add(clothing);
        inventoryManager.addAlert(new StockAlert("Stock Alert"));

        InventoryUserInterface.main(args);
    }
}