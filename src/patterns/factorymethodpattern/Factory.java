package patterns.factorymethodpattern;

import patterns.compositepattern.ProductComponent;

public abstract class Factory<T> {
    public abstract T create(String name);
}
