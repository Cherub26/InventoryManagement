package patterns.singletonpattern;

import patterns.compositeiterator.CompositeIterator;
import patterns.compositepattern.Product;
import patterns.compositepattern.ProductCategory;
import patterns.compositepattern.ProductComponent;
import patterns.observerpattern.Observer;
import patterns.observerpattern.StockManager;
import patterns.observerpattern.Subject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public ProductComponent getRootCategory() {
        return rootCategory;
    }

    public void addProduct(ProductComponent category, ProductComponent product) {
        try {
            category.add(product);
        } catch (UnsupportedOperationException e) {
            System.out.println("Cannot add product to a product");
        }
    }

    public void addProductByName(String categoryName, String productName, int stock) {
        ProductComponent category = findProductByName(categoryName);
        if (category != null) {
            addProduct(category, new Product(productName, stock));
        }
    }

    public void changeProductName(ProductComponent product, String name) {
        try {
            product.setName(name);
        } catch (UnsupportedOperationException e) {
            System.out.println("Failed to change the name");
        }
    }

    public void changeProductNameByName(String name, String newname){
        ProductComponent product = findProductByName(name);
        if(product != null){
            changeProductName(product, newname);
        }
    }

    public void removeProductFromCategory(ProductComponent category, ProductComponent product) {
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

    public void setStockByName(String name, int stock) {
        ProductComponent product = findProductByName(name);
        if (product != null) {
            setStock(product, stock);
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

    public void addStockByName(String name, int stock) {
        ProductComponent product = findProductByName(name);
        if (product != null) {
            addStock(product, stock);
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

    public boolean removeStockByName(String name, int stock) {
        ProductComponent product = findProductByName(name);
        if (product != null) {
            if (product.getStock() >= stock) {
                removeStock(product, stock);
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public int getStock(ProductComponent product) {
        try {
            return product.getStock();
        } catch (UnsupportedOperationException e) {
            System.out.println("Cannot get stock for a category");
            return -1;
        }
    }

    public ProductComponent findProductByName(String name) {
        CompositeIterator iterator = new CompositeIterator(rootCategory.createIterator());
        while (iterator.hasNext()) {
            ProductComponent component = iterator.next();
            if (component.getName().equalsIgnoreCase(name)) {
                return component;
            }
        }
        return null;
    }

    public int getStockByName(String name) {
        try {
            ProductComponent product = findProductByName(name);
            if (product != null) {
                return product.getStock();
            } else {
                System.out.println("Product not found");
                return -1;
            }
        } catch (UnsupportedOperationException e) {
            System.out.println("Cannot get stock for a category");
            return -1;
        }
    }

    public Map<String, Integer> getCategoryStocks() {
        Map<String, Integer> categoryStocks = new HashMap<>();
        CompositeIterator iterator = new CompositeIterator(rootCategory.createIterator());

        String currentCategory = null;
        while (iterator.hasNext()) {
            ProductComponent component = iterator.next();
            if (component instanceof ProductCategory) {
                currentCategory = component.getName();
            } else if (component instanceof Product) {
                int stock = component.getStock();
                categoryStocks.put(currentCategory, categoryStocks.getOrDefault(currentCategory, 0) + stock);
            }
        }
        return categoryStocks;
    }

    public List<Object[]> getAllProducts() {
        List<Object[]> products = new ArrayList<>();
        CompositeIterator iterator = new CompositeIterator(rootCategory.createIterator());

        String currentCategory = null;
        while (iterator.hasNext()) {
            ProductComponent component = iterator.next();
            if (component instanceof ProductCategory) {
                currentCategory = component.getName();
            } else if (component instanceof Product) {
                products.add(new Object[]{
                        currentCategory,
                        component.getName(),
                        component.getStock()
                });
            }
        }
        return products;
    }

    public void addCategory(String parentCategoryName, String newCategoryName) {
        ProductCategory newCategory = new ProductCategory(newCategoryName);
        if (parentCategoryName == null || parentCategoryName.isEmpty()) {
            rootCategory.add(newCategory);
        } else {
            ProductComponent parentCategory = findProductByName(parentCategoryName);
            if (parentCategory!=null) {
                try{
                    parentCategory.add(newCategory);
                }catch(UnsupportedOperationException e){
                    rootCategory.add(newCategory);
                }
            }else{
                rootCategory.add(newCategory);
            }
        }
    }
}
