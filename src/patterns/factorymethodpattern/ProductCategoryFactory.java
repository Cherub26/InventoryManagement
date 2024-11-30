package patterns.factorymethodpattern;

import patterns.compositepattern.ProductCategory;
import patterns.compositepattern.ProductComponent;

public class ProductCategoryFactory extends Factory{
    @Override
    public ProductComponent create(String categoryName) {
        return new ProductCategory(categoryName);
    }
}
