package patterns.compositeiterator;

import patterns.compositepattern.ProductCategory;
import patterns.compositepattern.ProductComponent;

import java.util.Iterator;
import java.util.Stack;

/*
 * CompositeIterator is an iterator that traverses a composite structure of ProductComponents.
 * It uses a stack to keep track of iterators for the current position in the composite structure.
 */
public class CompositeIterator implements Iterator<ProductComponent> {
    private Stack<Iterator<ProductComponent>> stack = new Stack<>();

    // Constructs a CompositeIterator with the given iterator.
    public CompositeIterator(Iterator<ProductComponent> iterator) {
        stack.push(iterator);
    }

    // Checks if there are more element to iterate over.
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

    // Returns the next element in the iteration.
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
