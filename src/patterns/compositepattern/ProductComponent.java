package patterns.compositepattern;

import java.util.Iterator;

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

    public String getName() {
        throw new UnsupportedOperationException();
    }

    public void setName(String name) {
        throw new UnsupportedOperationException();
    }

    public int getStock() {
        throw new UnsupportedOperationException();
    }

    public void setStock(int stock) {
        throw new UnsupportedOperationException();
    }

    public boolean isInCategory(ProductComponent product) {
        throw new UnsupportedOperationException();
    }

    public Iterator<ProductComponent> createIterator() {
        throw new UnsupportedOperationException();
    }

    public abstract void display();
}
