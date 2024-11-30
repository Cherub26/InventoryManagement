package patterns.factorymethodpattern;

import patterns.compositepattern.Product;
import patterns.compositepattern.ProductComponent;

public class ProductFactory extends Factory {
    @Override
    public ProductComponent create(String name) {
        return new Product(name, 0);
    }
}
