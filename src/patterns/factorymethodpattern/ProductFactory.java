package patterns.factorymethodpattern;

import patterns.compositepattern.Product;
import patterns.compositepattern.ProductComponent;

/*
 * The ProductFactory class is a concrete implementation of the Factory class.
 * It creates instances of the ProductComponent type, specifically Product objects.
 */
public class ProductFactory extends Factory<ProductComponent> {
    @Override
    public ProductComponent create(String name) {
        return new Product(name, 0);
    }
}
