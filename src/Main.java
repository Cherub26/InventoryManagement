import patterns.compositepattern.ProductComponent;
import patterns.singletonpattern.InventoryManager;
import patterns.factorymethodpattern.Factory;
import patterns.factorymethodpattern.ProductCategoryFactory;
import patterns.factorymethodpattern.ProductFactory;
import patterns.factorymethodpattern.AlertFactory;
import patterns.observerpattern.Observer;

// The class that is used to run the program with initial products and categories
public class Main {
    public static void main(String[] args) {
        InventoryManager inventoryManager = InventoryManager.getInstance();
        Factory<ProductComponent> categoryFactory = new ProductCategoryFactory();
        Factory<ProductComponent> productFactory = new ProductFactory();
        Factory<Observer> alertFactory = new AlertFactory();

        // Create categories and subcategories
        ProductComponent electronics = categoryFactory.create("Electronics");
        ProductComponent clothing = categoryFactory.create("Clothing");
        ProductComponent phones = categoryFactory.create("Phones");
        ProductComponent laptops = categoryFactory.create("Laptops");
        ProductComponent men = categoryFactory.create("Men");
        ProductComponent women = categoryFactory.create("Women");
        ProductComponent homeAppliances = categoryFactory.create("Home Appliances");
        ProductComponent kitchen = categoryFactory.create("Kitchen");
        ProductComponent livingRoom = categoryFactory.create("Living Room");
        ProductComponent sports = categoryFactory.create("Sports");
        ProductComponent outdoor = categoryFactory.create("Outdoor");
        ProductComponent indoor = categoryFactory.create("Indoor");

        // Add subcategories to categories
        electronics.add(phones);
        electronics.add(laptops);
        clothing.add(men);
        clothing.add(women);
        homeAppliances.add(kitchen);
        homeAppliances.add(livingRoom);
        sports.add(outdoor);
        sports.add(indoor);

        // Add products to subcategories with stock
        ProductComponent iPhone = productFactory.create("iPhone");
        iPhone.setStock(50);
        phones.add(iPhone);

        ProductComponent samsungGalaxy = productFactory.create("Samsung Galaxy");
        samsungGalaxy.setStock(30);
        phones.add(samsungGalaxy);

        ProductComponent macBookPro = productFactory.create("MacBook Pro");
        macBookPro.setStock(20);
        laptops.add(macBookPro);

        ProductComponent dellXPS = productFactory.create("Dell XPS");
        dellXPS.setStock(15);
        laptops.add(dellXPS);

        ProductComponent mensTShirt = productFactory.create("Men's T-Shirt");
        mensTShirt.setStock(100);
        men.add(mensTShirt);

        ProductComponent mensJeans = productFactory.create("Men's Jeans");
        mensJeans.setStock(60);
        men.add(mensJeans);

        ProductComponent womensDress = productFactory.create("Women's Dress");
        womensDress.setStock(80);
        women.add(womensDress);

        ProductComponent womensSkirt = productFactory.create("Women's Skirt");
        womensSkirt.setStock(70);
        women.add(womensSkirt);

        ProductComponent blender = productFactory.create("Blender");
        blender.setStock(40);
        kitchen.add(blender);

        ProductComponent microwave = productFactory.create("Microwave");
        microwave.setStock(25);
        kitchen.add(microwave);

        ProductComponent sofa = productFactory.create("Sofa");
        sofa.setStock(10);
        livingRoom.add(sofa);

        ProductComponent coffeeTable = productFactory.create("Coffee Table");
        coffeeTable.setStock(20);
        livingRoom.add(coffeeTable);

        ProductComponent tent = productFactory.create("Tent");
        tent.setStock(15);
        outdoor.add(tent);

        ProductComponent campingChair = productFactory.create("Camping Chair");
        campingChair.setStock(30);
        outdoor.add(campingChair);

        ProductComponent yogaMat = productFactory.create("Yoga Mat");
        yogaMat.setStock(50);
        indoor.add(yogaMat);

        ProductComponent dumbbells = productFactory.create("Dumbbells");
        dumbbells.setStock(100);
        indoor.add(dumbbells);

        // Add categories to root
        inventoryManager.getRootCategory().add(electronics);
        inventoryManager.getRootCategory().add(clothing);
        inventoryManager.getRootCategory().add(homeAppliances);
        inventoryManager.getRootCategory().add(sports);

        // Add observer
        Observer stockAlert = alertFactory.create("Stock Alert");
        inventoryManager.addAlert(stockAlert);

        InventoryUserInterface.main(args);
    }
}