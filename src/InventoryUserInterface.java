import patterns.singletonpattern.InventoryManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
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

        JButton addStockButton = new JButton("Add Stock");
        addStockButton.addActionListener(e -> addStock());
        panel.add(addStockButton);

        JButton removeStockButton = new JButton("Remove Stock");
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

            manager.addProductByName(category, name, stock);
        }
    }

    private void updateStock() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 5, 5));
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

            manager.setStockByName(name, stock);
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

            manager.addStockByName(name, stock);
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

            manager.removeStockByName(name, stock);
        }
    }

    private void getStock() {
        String name = JOptionPane.showInputDialog(this, "Enter Product Name:");
        if (name != null) {
            int stock = manager.getStockByName(name);
            JOptionPane.showMessageDialog(this, "Stock of " + name + ": " + stock);
        }
    }

    private void listAllCategories() {
        String[] columnNames = {"Category", "Total Stock"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        Map<String, Integer> categoryStocks = manager.getCategoryStocks();

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
        List<Object[]> products = manager.getAllProducts();

        for (Object[] product : products) {
            model.addRow(product);
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        JOptionPane.showMessageDialog(null, scrollPane, "Products", JOptionPane.PLAIN_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InventoryUserInterface().setVisible(true));
    }
}
