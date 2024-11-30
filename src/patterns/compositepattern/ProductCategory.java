package patterns.compositepattern;

import java.util.ArrayList;
import java.util.List;

public class ProductCategory extends ProductComponent {
    private String categoryName;
    private List<ProductComponent> components = new ArrayList<>();

    public ProductCategory(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public void add(ProductComponent component) {
        components.add(component);
    }

    @Override
    public void remove(ProductComponent component) {
        components.remove(component);
    }

    @Override
    public ProductComponent getChild(int i) {
        return (ProductComponent)components.get(i);
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
    public void display() {
        System.out.println("Category: " + categoryName);
        for (ProductComponent component : components) {
            component.display();
        }
    }
}
