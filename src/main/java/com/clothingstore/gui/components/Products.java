package com.clothingstore.gui.components;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import com.clothingstore.bus.ProductBUS;
import com.clothingstore.models.ProductModel;

public class Products extends JPanel {

    private List<ProductModel> productList = new ArrayList<>();
    private static Products instance;
    private int currentColumn = 1;

    private static JPanel productsPanel;
    private JScrollPane scrollPane;

    public static Products getInstance() {
        if (instance == null) {
            instance = new Products();
        }
        return instance;
    }

    public static void setInstance(Products newInstance) {
        instance = newInstance;
    }

    public Products() {
        initComponents();
    }

    public void ChangeLayout(int column) {
        productsPanel.setLayout(new GridLayout(0, column));
        currentColumn = column;
    }

    public void MenuOn(Boolean isVisible) {
        if (isVisible)
            productsPanel.setLayout(new GridLayout(0, currentColumn - 1));
        else
            productsPanel.setLayout(new GridLayout(0, currentColumn));
    }

    private void initComponents() {
        scrollPane = new JScrollPane();
        productsPanel = new JPanel();

        setLayout(new BorderLayout());
        productsPanel.setLayout(new GridLayout(0, currentColumn));
        productsPanel.setBackground(new Color(170, 205, 239));

        SwingWorker<List<ProductModel>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<ProductModel> doInBackground() throws Exception {
                return ProductBUS.getInstance().getAllModels();
            }

            @Override
            protected void done() {
                try {
                    productList = get();
                    initProductsList(); // Call the method to update the UI
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        worker.execute();

        scrollPane.setViewportView(productsPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(30);
        add(ProductsHeader.getInstance(), BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void initProductsList() {
        productsPanel.removeAll();
        if (productList != null) {
            for (ProductModel product : productList) {
                if (product.getStatus() == 1) {
                    Product productComponent = new Product(product);
                    productComponent.setBackground(new Color(170, 205, 239));
                    productsPanel.add(productComponent);
                }
            }
        }
        revalidate();
        repaint();
    }

    public void showProductsFromResult(List<ProductModel> productModels) {
        productsPanel.removeAll();
        if (productModels != null) {
            for (ProductModel product : productModels) {
                if (product.getStatus() == 1) {
                    Product productComponent = new Product(product);
                    productComponent.setBackground(new Color(170, 205, 239));
                    productsPanel.add(productComponent);
                }
            }
        }
        revalidate();
        repaint();
    }
}
