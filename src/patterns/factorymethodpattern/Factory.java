package patterns.factorymethodpattern;

import patterns.compositepattern.ProductComponent;

public abstract class Factory {
    public abstract ProductComponent create(String name);
}
