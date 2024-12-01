package patterns.compositeiterator;

import patterns.compositepattern.ProductCategory;
import patterns.compositepattern.ProductComponent;

import java.util.Iterator;
import java.util.Stack;

public class CompositeIterator implements Iterator<ProductComponent> {
    private Stack<Iterator<ProductComponent>> stack = new Stack<>();

    public CompositeIterator(Iterator<ProductComponent> iterator) {
        stack.push(iterator);
    }

    @Override
    public boolean hasNext() {
        if (stack.isEmpty()) {
            return false;
        } else {
            Iterator<ProductComponent> iterator = stack.peek();
            if (!iterator.hasNext()) {
                stack.pop();
                return hasNext();
            } else {
                return true;
            }
        }
    }

    @Override
    public ProductComponent next() {
        if (hasNext()) {
            Iterator<ProductComponent> iterator = stack.peek();
            ProductComponent component = iterator.next();
            if (component instanceof ProductCategory) {
                stack.push(component.createIterator());
            }
            return component;
        } else {
            return null;
        }
    }
}
