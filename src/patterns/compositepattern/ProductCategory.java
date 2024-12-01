package patterns.compositepattern;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProductCategory extends ProductComponent {
    private String categoryName;
    private List<ProductComponent> components = new ArrayList<>();

    public ProductCategory(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public void add(ProductComponent component) {
        if (component instanceof Product) {
            components.add(0, component); // Add Product at the beginning
        } else if (component instanceof ProductCategory) {
            components.add(component); // Add ProductCategory at the end
        }
    }

    @Override
    public void remove(ProductComponent component) {
        components.remove(component);
    }

    @Override
    public ProductComponent getChild(int i) {
        return components.get(i);
    }

    @Override
    public String getName() {
        return categoryName;
    }

    @Override
    public boolean isInCategory(ProductComponent product) {
        return components.contains(product);
    }

    @Override
    public Iterator<ProductComponent> createIterator() {
        return components.iterator();
    }

    @Override
    public void display() {
        System.out.println("Category: " + categoryName);
    }
}
