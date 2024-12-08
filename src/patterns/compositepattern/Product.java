package patterns.compositepattern;


/* The Product class represents a leaf node in the Composite pattern.
 * It extends the ProductComponent class and provides implementations
 * for the methods defined in the ProductComponent class.
 */
public class Product extends ProductComponent {
    private String name;
    private int stock;

    public Product(String name, int stock) {
        this.name = name;
        this.stock = stock;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getStock() {
        return stock;
    }

    @Override
    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public void display() {
        System.out.println("Product: " + name + ", Stock: " + stock);
    }
}
