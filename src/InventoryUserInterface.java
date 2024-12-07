import patterns.compositepattern.Product;
import patterns.compositepattern.ProductCategory;
import patterns.compositepattern.ProductComponent;
import patterns.singletonpattern.InventoryManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
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
        setLayout(new BorderLayout());

        // Header
        JLabel header = new JLabel("Inventory Management System", JLabel.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 24));
        header.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(header, BorderLayout.NORTH);

        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 40, 40));

        // Buttons panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6, 2, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Button customization
        String[] buttonTexts = {
                "Add New Product", "Add New Category",
                "Update Stock", "Add Stock",
                "Remove Stock", "Get Stock of a Product",
                "Change Product or Category Name", "Change Category of a Subcategory or a Product",
                "List All Categories", "List All Products",
                "Display Inventory Tree", "Exit"
        };

        Runnable[] actions = {
                this::addProduct, this::addCategory,
                this::updateStock, this::addStock,
                this::removeStock, this::getStock,
                this::changeName, this::changeCategory,
                this::listAllCategories, this::listAllProducts,
                this::displayTree, () -> System.exit(0)
        };

        for (int i = 0; i < buttonTexts.length; i++) {
            JButton button = createStyledButton(buttonTexts[i], actions[i]);
            buttonPanel.add(button);
        }

        mainPanel.add(buttonPanel);
        add(mainPanel, BorderLayout.CENTER);
    }

    private JButton createStyledButton(String text, Runnable action) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 18));
        button.setFocusPainted(false);
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createLineBorder(new Color(60, 110, 160), 2));
        button.setToolTipText("Click to " + text.toLowerCase());
        button.addActionListener(e -> action.run());
        return button;
    }

    private JTextField createPlaceholderField(String placeholder) {
        JTextField textField = new JTextField();
        textField.setFont(new Font("Arial", Font.ITALIC, 14));
        textField.setForeground(Color.GRAY);
        textField.setText(placeholder);

        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setFont(new Font("Arial", Font.PLAIN, 14));
                    textField.setForeground(Color.BLACK);
                }
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                if (textField.getText().isEmpty()) {
                    textField.setFont(new Font("Arial", Font.ITALIC, 14));
                    textField.setForeground(Color.GRAY);
                    textField.setText(placeholder);
                }
            }
        });

        return textField;
    }

    private void addProduct() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Title section
        JLabel titleLabel = new JLabel("Add a New Product", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titleLabel, BorderLayout.NORTH);

        // Input section
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        JTextField nameField = createPlaceholderField("");
        JTextField stockField = createPlaceholderField("");
        JTextField categoryField = createPlaceholderField("");

        inputPanel.add(new JLabel("Product Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Stock Quantity:"));
        inputPanel.add(stockField);
        inputPanel.add(new JLabel("Category:"));
        inputPanel.add(categoryField);

        panel.add(inputPanel, BorderLayout.CENTER);

        int option = JOptionPane.showConfirmDialog(this, panel, "Add New Product", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText().trim();
                int stock = Integer.parseInt(stockField.getText().trim());
                String category = categoryField.getText().trim();

                if (name.isEmpty() || category.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Fields cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (stock < 0) {
                    JOptionPane.showMessageDialog(this, "Stock quantity can't be lower than zero.", "Input Error", JOptionPane.ERROR_MESSAGE);
                } else if (manager.findProductByName(name) != null) {
                    JOptionPane.showMessageDialog(this, "Product or Category with this name already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (manager.findProductByName(category) == null) {
                    JOptionPane.showMessageDialog(this, "Category does not exist!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    manager.addProductByName(category, name, stock);
                    JOptionPane.showMessageDialog(this, "Product added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid stock quantity.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void addCategory() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Add a New Category", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        JTextField categoryNameField = createPlaceholderField("");
        JTextField parentCategoryField = createPlaceholderField("");

        inputPanel.add(new JLabel("Category Name:"));
        inputPanel.add(categoryNameField);
        inputPanel.add(new JLabel("Parent Category:"));
        inputPanel.add(parentCategoryField);
        inputPanel.add(new JLabel("")); // Empty label for spacing
        inputPanel.add(new JLabel("<html>Leave blank to make<br>the parent root category.</html>"));

        panel.add(inputPanel, BorderLayout.CENTER);

        int option = JOptionPane.showConfirmDialog(this, panel, "Add New Category", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
            String parentCategoryName = parentCategoryField.getText().trim();
            String categoryName = categoryNameField.getText().trim();

            if (categoryName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Category name cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (manager.findProductByName(categoryName) != null) {
                JOptionPane.showMessageDialog(this, "Category or Product with this name already exists!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                ProductComponent newCategory = new ProductCategory(categoryName);
                if (parentCategoryName.isEmpty()) {
                    manager.getRootCategory().add(newCategory);
                } else {
                    ProductComponent parentCategory = manager.findProductByName(parentCategoryName);
                    if (parentCategory instanceof ProductCategory) {
                        parentCategory.add(newCategory);
                    } else {
                        JOptionPane.showMessageDialog(this, "Parent category does not exist!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                JOptionPane.showMessageDialog(this, "Category added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void updateStock() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Update Stock Quantity", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        JTextField nameField = createPlaceholderField("");
        JTextField stockField = createPlaceholderField("");

        inputPanel.add(new JLabel("Product Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("New Stock Quantity:"));
        inputPanel.add(stockField);

        panel.add(inputPanel, BorderLayout.CENTER);

        int option = JOptionPane.showConfirmDialog(this, panel, "Update Stock", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText().trim();
                int stock = Integer.parseInt(stockField.getText().trim());

                if (stock < 0) {
                    JOptionPane.showMessageDialog(this, "Stock cannot be negative.", "Input Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    ProductComponent product = manager.findProductByName(name);
                    if (product instanceof Product) {
                        manager.setStockByName(name, stock);
                        JOptionPane.showMessageDialog(this, "Stock updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Product not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid stock quantity.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void addStock() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Add Stock to Product", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        JTextField nameField = createPlaceholderField("");
        JTextField stockField = createPlaceholderField("");

        inputPanel.add(new JLabel("Product Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Quantity to Add:"));
        inputPanel.add(stockField);

        panel.add(inputPanel, BorderLayout.CENTER);

        int option = JOptionPane.showConfirmDialog(this, panel, "Add Stock", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText().trim();
                int stock = Integer.parseInt(stockField.getText().trim());

                if (stock <= 0) {
                    JOptionPane.showMessageDialog(this, "Quantity to add must be greater than zero.", "Input Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    ProductComponent product = manager.findProductByName(name);
                    if (product instanceof Product) {
                        manager.addStockByName(name, stock);
                        JOptionPane.showMessageDialog(this, "Stock added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Product not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid stock quantity.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void removeStock() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Remove Stock from Product", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        JTextField nameField = createPlaceholderField("");
        JTextField stockField = createPlaceholderField("");

        inputPanel.add(new JLabel("Product Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Quantity to Remove:"));
        inputPanel.add(stockField);

        panel.add(inputPanel, BorderLayout.CENTER);

        int option = JOptionPane.showConfirmDialog(this, panel, "Remove Stock", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText().trim();
                int stock = Integer.parseInt(stockField.getText().trim());

                if (stock <= 0) {
                    JOptionPane.showMessageDialog(this, "Quantity to remove must be greater than zero.", "Input Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    ProductComponent product = manager.findProductByName(name);
                    if (product instanceof Product) {
                        boolean wasSuccess = manager.removeStock(product, stock);
                        if(wasSuccess){
                            JOptionPane.showMessageDialog(this, "Stock removed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        }else{
                            JOptionPane.showMessageDialog(this, "Not enough stock to remove.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Product not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid stock quantity.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void getStock() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Check Product Stock", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        JTextField nameField = createPlaceholderField("");

        inputPanel.add(new JLabel("Product Name:"));
        inputPanel.add(nameField);

        panel.add(inputPanel, BorderLayout.CENTER);

        int option = JOptionPane.showConfirmDialog(this, panel, "Get Stock", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            String name = nameField.getText().trim();

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Product name cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int stock = manager.getStockByName(name);
            if (stock == -1) {
                JOptionPane.showMessageDialog(this, "Product not found.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Stock of " + name + ": " + stock, "Product Stock", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void changeName() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Change Name", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        JTextField oldNameField = createPlaceholderField("");
        JTextField newNameField = createPlaceholderField("");

        inputPanel.add(new JLabel("Current Name:"));
        inputPanel.add(oldNameField);
        inputPanel.add(new JLabel("New Name:"));
        inputPanel.add(newNameField);

        panel.add(inputPanel, BorderLayout.CENTER);

        int option = JOptionPane.showConfirmDialog(this, panel, "Change Name", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
            String oldName = oldNameField.getText().trim();
            String newName = newNameField.getText().trim();

            if (oldName.isEmpty() || newName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Fields cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (manager.findProductByName(oldName) == null) {
                JOptionPane.showMessageDialog(this, "Category or Product with the current name does not exist!", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (manager.findProductByName(newName) != null && !newName.equalsIgnoreCase(oldName)) {
                JOptionPane.showMessageDialog(this, "Category or Product with the new name already exists!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                manager.changeProductNameByName(oldName, newName);
                JOptionPane.showMessageDialog(this, "Name changed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void changeCategory() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Change Category", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        JTextField nameField = createPlaceholderField("");
        JTextField newCategoryField = createPlaceholderField("");

        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("New Category:"));
        inputPanel.add(newCategoryField);
        inputPanel.add(new JLabel("")); // Empty label for spacing
        inputPanel.add(new JLabel("<html>Leave this field blank to change<br>the category the root category.</html>"));

        panel.add(inputPanel, BorderLayout.CENTER);

        int option = JOptionPane.showConfirmDialog(this, panel, "Change Category", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
            String name = nameField.getText().trim();
            String newCategoryName = newCategoryField.getText().trim();
            System.out.println(newCategoryName);

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name field cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (manager.findProductByName(name) == null) {
                JOptionPane.showMessageDialog(this, "Product or subcategory with the given name does not exist!", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (newCategoryName.isEmpty()) {
                manager.changeToRootCategory(name);
                JOptionPane.showMessageDialog(this, "Category changed to root category successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }else {
                ProductComponent newCategory = manager.findProductByName(newCategoryName);
                if(newCategory instanceof Product){
                    JOptionPane.showMessageDialog(this, "Cannot move a category into a product.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (newCategory == null) {
                    JOptionPane.showMessageDialog(this, "New category does not exist!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    boolean success = manager.changeProductCategoryByName(name, newCategory.getName());
                    if (success) {
                        JOptionPane.showMessageDialog(this, "Category changed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Cannot move a category into one of its own subcategories.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }

    private void listAllCategories() {
        String[] columnNames = {"Category", "Total Stock"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Allow editing only the "Category" column
                return column == 0;
            }
        };

        Map<String, Integer> categoryStocks = manager.getCategoryStocks();
        for (Map.Entry<String, Integer> entry : categoryStocks.entrySet()) {
            model.addRow(new Object[]{entry.getKey(), entry.getValue()});
        }

        JTable table = new JTable(model);
        table.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(500, 300));

        // Add listener to handle edits
        model.addTableModelListener(e -> {
            int row = e.getFirstRow();
            int column = e.getColumn();

            if (column == 0) {  // Category column
                String newCategoryName = model.getValueAt(row, column).toString();
                String oldCategoryName = categoryStocks.keySet().toArray(new String[0])[row];

                if(newCategoryName.equals(oldCategoryName)){
                    // The UI would bug out if you entered the same name so added this
                } else if (newCategoryName.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Category name cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    model.setValueAt(oldCategoryName, row, column); // Revert to the original value
                } else if (manager.findProductByName(newCategoryName) != null && !newCategoryName.equalsIgnoreCase(oldCategoryName)) {
                    // Second part of this && operation is for when we want to change the case of a character in the name so that it skips this even though the category name isn't null
                    JOptionPane.showMessageDialog(this, "Category name already exists.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    model.setValueAt(oldCategoryName, row, column); // Revert to the original value
                } else {
                    manager.changeProductNameByName(oldCategoryName, newCategoryName);
                }
            }
        });

        JOptionPane.showMessageDialog(this, scrollPane, "All Categories", JOptionPane.PLAIN_MESSAGE);
    }

    private void listAllProducts() {
        String[] columnNames = {"Category", "Product Name", "Stock"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Allow editing on the "Category" "Product Name" "Stock" columns
                return column == 0 || column == 1 || column == 2;
            }
        };

        List<Object[]> products = manager.getAllProducts();
        for (Object[] product : products) {
            model.addRow(product);
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        // Add listener to handle edits
        model.addTableModelListener(e -> {
            int row = e.getFirstRow();
            int column = e.getColumn();

            String categoryName = model.getValueAt(row, 0).toString();
            String productName = model.getValueAt(row, 1).toString();
            int stock = Integer.parseInt(model.getValueAt(row, 2).toString());

            if (column == 0) {  // Category column
                if (categoryName.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Category name cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    model.setValueAt(products.get(row)[0], row, column); // Revert to the original value
                } else if (manager.findProductByName(categoryName) == null) {
                    JOptionPane.showMessageDialog(this, "Category does not exist.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    model.setValueAt(products.get(row)[0], row, column); // Revert to the original value
                } else {
                    manager.changeProductCategoryByName(productName, categoryName);
                }
            } else if (column == 1) {  // Product Name column
                if (productName.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Product name cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    model.setValueAt(products.get(row)[1], row, column); // Revert to the original value
                } else {
                    manager.changeProductNameByName(products.get(row)[1].toString(), productName);
                }
            } else if (column == 2) {  // Stock column
                if (stock < 0) {
                    JOptionPane.showMessageDialog(this, "Stock cannot be negative.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    model.setValueAt(products.get(row)[2], row, column); // Revert to the original value
                } else {
                    manager.setStockByName(productName, stock);
                }
            }
        });

        JOptionPane.showMessageDialog(this, scrollPane, "Products", JOptionPane.PLAIN_MESSAGE);
    }

    private TreeModel buildTreeModel() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(manager.getRootCategory().getName());
        for (ProductComponent child : manager.getRootCategory().getComponents()) {
            addCategoriesToNode(root, child);
        }
        return new DefaultTreeModel(root);
    }

    private void addCategoriesToNode(DefaultMutableTreeNode parent, ProductComponent component) {
        if (component instanceof ProductCategory) {
            DefaultMutableTreeNode categoryNode = new DefaultMutableTreeNode(component.getName());
            parent.add(categoryNode);
            for (ProductComponent child : (component).getComponents()) {
                addCategoriesToNode(categoryNode, child);
            }
        } else if (component instanceof Product) {
            DefaultMutableTreeNode productNode = new DefaultMutableTreeNode(component.getName() + " (Stock: " + component.getStock() + ")");
            parent.add(productNode);
        }
    }

    private void displayTree() {
        JTree tree = new JTree(buildTreeModel());
        JScrollPane scrollPane = new JScrollPane(tree);
        scrollPane.setPreferredSize(new Dimension(500, 400));
        JOptionPane.showMessageDialog(this, scrollPane, "Inventory Tree", JOptionPane.PLAIN_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InventoryUserInterface().setVisible(true));
    }
}


