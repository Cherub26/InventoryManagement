import patterns.compositeiterator.CompositeIterator;
import patterns.compositepattern.Product;
import patterns.compositepattern.ProductCategory;
import patterns.compositepattern.ProductComponent;
import patterns.singletonpattern.InventoryManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class InventoryUserInterface extends JFrame {
    private final InventoryManager manager;

    public InventoryUserInterface() {
        manager = InventoryManager.getInstance();
        setTitle("Inventory Management System");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(8, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 100, 50, 100));

        JButton addProductButton = new JButton("Add New Product");
        addProductButton.addActionListener(e -> addProduct());
        panel.add(addProductButton);

        JButton updateStockButton = new JButton("Update Stock");
        updateStockButton.addActionListener(e -> updateStock());
        panel.add(updateStockButton);

        JButton addStockButton = new JButton("Add Stock"); // New button
        addStockButton.addActionListener(e -> addStock());
        panel.add(addStockButton);

        JButton removeStockButton = new JButton("Remove Stock"); // New button
        removeStockButton.addActionListener(e -> removeStock());
        panel.add(removeStockButton);

        JButton getStockButton = new JButton("Get Stock of a Product");
        getStockButton.addActionListener(e -> getStock());
        panel.add(getStockButton);

        JButton listCategoriesButton = new JButton("List All Categories");
        listCategoriesButton.addActionListener(e -> listAllCategories());
        panel.add(listCategoriesButton);

        JButton listProductsButton = new JButton("List All Products");
        listProductsButton.addActionListener(e -> listAllProducts());
        panel.add(listProductsButton);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));
        panel.add(exitButton);

        add(panel);
    }

    private void addProduct() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        JTextField nameField = new JTextField();
        JTextField stockField = new JTextField();
        JTextField categoryField = new JTextField();

        panel.add(new JLabel("Product Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Stock Quantity:"));
        panel.add(stockField);
        panel.add(new JLabel("Category:"));
        panel.add(categoryField);

        int option = JOptionPane.showConfirmDialog(this, panel, "Add New Product", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            int stock = Integer.parseInt(stockField.getText());
            String category = categoryField.getText();

            ProductComponent categoryComponent = manager.findProductByName(category);
            if (categoryComponent != null) {
                manager.addProduct(categoryComponent, new Product(name, stock));
            }
        }
    }

    private void updateStock() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 5, 5)); // Reduced gaps to 5 pixels
        JLabel nameLabel = new JLabel("Product Name:");
        JTextField nameField = new JTextField();
        JLabel stockLabel = new JLabel("New Stock Quantity:");
        JTextField stockField = new JTextField();

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(stockLabel);
        panel.add(stockField);

        int option = JOptionPane.showConfirmDialog(this, panel, "Update Stock", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            int stock = Integer.parseInt(stockField.getText());

            ProductComponent product = manager.findProductByName(name);
            if (product != null) {
                manager.setStock(product, stock);
            }
        }
    }

    private void addStock() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 5, 5));
        JLabel nameLabel = new JLabel("Product Name:");
        JTextField nameField = new JTextField();
        JLabel stockLabel = new JLabel("Stock Quantity to Add:");
        JTextField stockField = new JTextField();

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(stockLabel);
        panel.add(stockField);

        int option = JOptionPane.showConfirmDialog(this, panel, "Add Stock", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            int stock = Integer.parseInt(stockField.getText());

            ProductComponent product = manager.findProductByName(name);
            if (product != null) {
                manager.addStock(product, stock);
            }
        }
    }

    private void removeStock() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 5, 5));
        JLabel nameLabel = new JLabel("Product Name:");
        JTextField nameField = new JTextField();
        JLabel stockLabel = new JLabel("Stock Quantity to Remove:");
        JTextField stockField = new JTextField();

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(stockLabel);
        panel.add(stockField);

        int option = JOptionPane.showConfirmDialog(this, panel, "Remove Stock", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            int stock = Integer.parseInt(stockField.getText());

            ProductComponent product = manager.findProductByName(name);
            if (product != null) {
                manager.removeStock(product, stock);
            }
        }
    }

    private void getStock() {
        String name = JOptionPane.showInputDialog(this, "Enter Product Name:");
        if (name != null) {
            ProductComponent product = manager.findProductByName(name);
            int stock = -1;
            if(product!=null){
                stock = manager.getStock(product);
            }
            JOptionPane.showMessageDialog(this, "Stock of " + name + ": " + stock);
        }
    }

    private void listAllCategories() {
        String[] columnNames = {"Category", "Total Stock"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        CompositeIterator iterator = new CompositeIterator(manager.getRootCategory().createIterator());
        Map<String, Integer> categoryStocks = new HashMap<>();

        String currentCategory = null;
        while (iterator.hasNext()) {
            ProductComponent component = iterator.next();
            if (component instanceof ProductCategory) {
                currentCategory = component.getName();
            } else if (component instanceof Product) {
                int stock = component.getStock();
                categoryStocks.put(currentCategory, categoryStocks.getOrDefault(currentCategory, 0) + stock);
            }
        }

        for (Map.Entry<String, Integer> entry : categoryStocks.entrySet()) {
            model.addRow(new Object[]{entry.getKey(), entry.getValue()});
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        JOptionPane.showMessageDialog(this, scrollPane, "Categories", JOptionPane.PLAIN_MESSAGE);
    }

    private void listAllProducts() {
        String[] columnNames = {"Category", "Product Name", "Stock"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        CompositeIterator iterator = new CompositeIterator(manager.getRootCategory().createIterator());

        String currentCategory = null;
        while (iterator.hasNext()) {
            ProductComponent component = iterator.next();
            if (component instanceof ProductCategory) {
                currentCategory = component.getName();
            } else if (component instanceof Product) {
                model.addRow(new Object[]{
                        currentCategory,
                        component.getName(),
                        component.getStock()
                });
            }
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        JOptionPane.showMessageDialog(null, scrollPane, "Products", JOptionPane.PLAIN_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InventoryUserInterface().setVisible(true));
    }
}
