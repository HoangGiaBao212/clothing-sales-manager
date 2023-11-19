
package com.clothingstore.gui.components;

import java.awt.*;
import java.util.List;

import javax.swing.*;

import com.clothingstore.bus.ProductBUS;
import com.clothingstore.models.ProductModel;
import com.clothingstore.models.UserModel;

import services.Authentication;

public class Products extends JPanel {

  List<ProductModel> productList = ProductBUS.getInstance().getAllModels();
  static UserModel currentUser = Authentication.getCurrentUser();

  Boolean Visible = false;
  private static Products instance;

  int currentColumn = 1;

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
    Products.setLayout(new GridLayout(0, column));
    currentColumn = column;
  }

  public void MenuOn(Boolean isVisible) {
    if (isVisible)
      Products.setLayout(new GridLayout(0, currentColumn - 1));
    else
      Products.setLayout(new GridLayout(0, currentColumn));
  }

  private void initComponents() {

    Scroll = new JScrollPane();
    Products = new JPanel();

    setLayout(new BorderLayout());
    Products.setLayout(new GridLayout(0, currentColumn));
    Products.setBackground(new Color(170, 205, 239));

    for (ProductModel products : productList) {
      if (products.getStatus() == 1) {
        Product product = new Product(products);
        product.setBackground(new Color(170, 205, 239));
        Products.add(product);
      }
    }
    Scroll.setViewportView(Products);
    Scroll.getVerticalScrollBar().setUnitIncrement(30);
    add(ProductsHeader.getInstance(), BorderLayout.NORTH);
    add(Scroll, BorderLayout.CENTER);
  }

  private static JPanel Products;
  private JScrollPane Scroll;

  public void showProductsFromResult(List<ProductModel> productModels) {
    Products.removeAll();
    for (ProductModel products : productModels) {
      if (products.getStatus() == 1) {
        Product product = new Product(products);
        product.setBackground(new Color(170, 205, 239));
        Products.add(product);
      }
    }
    revalidate();
    repaint();
  }
}
