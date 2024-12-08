package patterns.factorymethodpattern;

import patterns.compositepattern.ProductCategory;
import patterns.compositepattern.ProductComponent;

/*
 * The ProductCategoryFactory class is a concrete implementation of the Factory class.
 * It creates instances of the ProductComponent type, specifically ProductCategory objects.
 */
public class ProductCategoryFactory extends Factory<ProductComponent> {
    @Override
    public ProductComponent create(String categoryName) {
        return new ProductCategory(categoryName);
    }
}
