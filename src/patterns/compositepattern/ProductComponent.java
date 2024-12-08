package patterns.compositepattern;

import java.util.Iterator;
import java.util.List;

// This class is the Component in the Composite pattern
public abstract class ProductComponent {
    public void add(ProductComponent component) {
        throw new UnsupportedOperationException();
    }

    public void remove(ProductComponent component) {
        throw new UnsupportedOperationException();
    }

    public ProductComponent getChild(int i) {
        throw new UnsupportedOperationException();
    }

    public abstract String getName();

    public abstract void setName(String name);

    public int getStock() {
        throw new UnsupportedOperationException();
    }

    public void setStock(int stock) {
        throw new UnsupportedOperationException();
    }

    public boolean isInCategory(ProductComponent product) {
        throw new UnsupportedOperationException();
    }

    public List<ProductComponent> getComponents() {
        throw new UnsupportedOperationException();
    }

    public Iterator<ProductComponent> createIterator() {
        throw new UnsupportedOperationException();
    }

    public boolean isSubcategory(ProductComponent category) {
        throw new UnsupportedOperationException();
    }

    public abstract void display();
}
