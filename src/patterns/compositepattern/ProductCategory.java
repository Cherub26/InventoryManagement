package patterns.compositepattern;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/*
 * The ProductCategory class represents a composite node in the Composite pattern.
 * It holds instances of ProductComponents in a List and can contain both Products and other ProductCategories.
 */
public class ProductCategory extends ProductComponent {
    private String categoryName;
    private List<ProductComponent> components = new ArrayList<>();

    public ProductCategory(String categoryName) {
        this.categoryName = categoryName;
    }

    /*
     * Adds a ProductComponent to the category.
     * Products are added at the beginning of the list, while ProductCategories are added at the end.
     */
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
    public void setName(String name) {
        this.categoryName = name;
    }

    @Override
    public boolean isInCategory(ProductComponent product) {
        return components.contains(product);
    }

    @Override
    public List<ProductComponent> getComponents() {
        return components;
    }

    /*
     * Checks if a certain ProductComponent is a subcategory of this ProductCategory.
     * This method performs a recursive search.
     */
    @Override
    public boolean isSubcategory(ProductComponent category) {
        for (ProductComponent component : components) {
            if (component == category) {
                return true;
            } else if (component instanceof ProductCategory) {
                if (component.isSubcategory(category)) { // Does this recursively until it finds the category
                    return true;
                }
            }
        }
        return false;
    }

    /*
     * Creates an iterator for the list of ProductComponents.
     * This is used in the CompositeIterator to iterate over the list of ProductComponents.
     */
    @Override
    public Iterator<ProductComponent> createIterator() {
        return components.iterator();
    }

    @Override
    public void display() {
        System.out.println("Category: " + categoryName);
    }
}
