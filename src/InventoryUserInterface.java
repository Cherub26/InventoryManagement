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
        buttonPanel.setLayout(new GridLayout(8, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Button customization
        String[] buttonTexts = {
                "Add New Product", "Update Stock", "Add Stock",
                "Remove Stock", "Get Stock of a Product",
                "List All Categories", "List All Products", "Exit"
        };

        Runnable[] actions = {
                this::addProduct, this::updateStock, this::addStock,
                this::removeStock, this::getStock, this::listAllCategories,
                this::listAllProducts, () -> System.exit(0)
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

    private void addProduct() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Title section
        JLabel titleLabel = new JLabel("Add a New Product", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titleLabel, BorderLayout.NORTH);

        // Input section
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        JTextField nameField = createPlaceholderField("Enter product name...");
        JTextField stockField = createPlaceholderField("Enter initial stock...");
        JTextField categoryField = createPlaceholderField("Enter product category...");

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
                } else {
                    manager.addProductByName(category, name, stock);
                    JOptionPane.showMessageDialog(this, "Product added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid stock quantity.", "Input Error", JOptionPane.ERROR_MESSAGE);
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
        JTextField nameField = createPlaceholderField("Enter product name...");
        JTextField stockField = createPlaceholderField("Enter new stock quantity...");

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

                manager.setStockByName(name, stock);
                JOptionPane.showMessageDialog(this, "Stock updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid stock quantity.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
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

    private void addStock() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Add Stock to Product", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        JTextField nameField = createPlaceholderField("Enter product name...");
        JTextField stockField = createPlaceholderField("Enter quantity to add...");

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

                manager.addStockByName(name, stock);
                JOptionPane.showMessageDialog(this, "Stock added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
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
        JTextField nameField = createPlaceholderField("Enter product name...");
        JTextField stockField = createPlaceholderField("Enter quantity to remove...");

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

                manager.removeStockByName(name, stock);
                JOptionPane.showMessageDialog(this, "Stock removed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
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
        JTextField nameField = createPlaceholderField("Enter product name...");

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
            JOptionPane.showMessageDialog(this, "Stock of " + name + ": " + stock, "Product Stock", JOptionPane.INFORMATION_MESSAGE);
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
        table.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(500, 300));

        JOptionPane.showMessageDialog(this, scrollPane, "All Categories", JOptionPane.PLAIN_MESSAGE);
    }

    private void listAllProducts() {
        String[] columnNames = {"Category", "Product Name", "Stock"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // This is to allow editing only the "Product Name" and "Stock" columns and not the "Category" column
                return column == 1 || column == 2;
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

            if (column == 1 || column == 2) {  // Product Name or Stock column
                String fieldCategory = model.getValueAt(row, 0).toString();
                String fieldName = model.getValueAt(row, 1).toString();
                int fieldStock = Integer.parseInt(model.getValueAt(row, 2).toString());

                String category = products.get(row)[0].toString();
                String productName = products.get(row)[1].toString();
                int stock = Integer.parseInt(products.get(row)[2].toString());

                if (column == 1) {
                    manager.changeProductNameByName(productName, fieldName);
                } else if (column == 2) {
                    manager.setStockByName(productName, fieldStock);
                }
            }
        });

        JOptionPane.showMessageDialog(this, scrollPane, "Products", JOptionPane.PLAIN_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InventoryUserInterface().setVisible(true));
    }
}
