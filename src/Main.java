import patterns.compositepattern.ProductComponent;
import patterns.compositepattern.Product;
import patterns.compositepattern.ProductCategory;
import patterns.observerpattern.StockAlert;
import patterns.singletonpattern.InventoryManager;
import patterns.factorymethodpattern.Factory;
import patterns.factorymethodpattern.ProductCategoryFactory;
import patterns.factorymethodpattern.ProductFactory;
import patterns.factorymethodpattern.AlertFactory;
import patterns.observerpattern.Observer;

public class Main {
    public static void main(String[] args) {
        InventoryManager inventoryManager = InventoryManager.getInstance();
        Factory<ProductComponent> categoryFactory = new ProductCategoryFactory();
        Factory<ProductComponent> productFactory = new ProductFactory();
        Factory<Observer> alertFactory = new AlertFactory();

        // Kategorileri factory ile oluştur
        ProductComponent electronics = categoryFactory.create("Electronics");
        ProductComponent clothing = categoryFactory.create("Clothing");
        ProductComponent phones = categoryFactory.create("Phones");
        ProductComponent laptops = categoryFactory.create("Laptops");
        ProductComponent men = categoryFactory.create("Men");
        ProductComponent women = categoryFactory.create("Women");
        electronics.add(phones);
        electronics.add(laptops);
        clothing.add(men);
        clothing.add(women);
        electronics.add(productFactory.create("testingProduct"));
        phones.add(productFactory.create("iPhone"));
        phones.add(productFactory.create("Samsung Galaxy"));
        laptops.add(productFactory.create("MacBook Pro"));
        laptops.add(productFactory.create("Dell XPS"));
        men.add(productFactory.create("Men's T-Shirt"));
        men.add(productFactory.create("Men's Jeans"));
        women.add(productFactory.create("Women's Dress"));
        women.add(productFactory.create("Women's Skirt"));
        inventoryManager.getRootCategory().add(electronics);
        inventoryManager.getRootCategory().add(clothing);
        Observer stockAlert = alertFactory.create("Stock Alert");
        inventoryManager.addAlert(stockAlert);

        InventoryUserInterface.main(args);
    }
}