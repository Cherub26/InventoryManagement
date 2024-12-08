package patterns.factorymethodpattern;

/*
 * The Factory class is an abstract class that defines a factory method for creating objects.
 * Subclasses should implement the create method to instantiate objects of a specific type.
 */
public abstract class Factory<T> {
    public abstract T create(String name);
}
