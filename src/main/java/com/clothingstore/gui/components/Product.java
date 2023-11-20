package com.clothingstore.gui.components;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import com.clothingstore.models.OrderItemModel;
import com.clothingstore.models.ProductModel;

public class Product extends JPanel {
  public static List<OrderItemModel> cartItems = new ArrayList<>();

  public Product(ProductModel productModel) {
    initComponents(productModel);
    addMouseListener(new ProductMouseAdapter(productModel));
  }

  private void initComponents(ProductModel productModel) {
    setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    setPreferredSize(new Dimension(199, 280));
    setLayout(new BorderLayout());

    JPanel header = new JPanel();
    header.setBackground(new Color(255, 255, 255));
    header.setLayout(new GridBagLayout());

    ImageIcon icon = new ImageIcon(productModel.getImage());
    Image image = icon.getImage();
    Image scaledImage = image.getScaledInstance(180, 180, Image.SCALE_SMOOTH);
    ImageIcon scaledIcon = new ImageIcon(scaledImage);
    JLabel imageLabel = new JLabel(scaledIcon);
    imageLabel.setPreferredSize(new Dimension(180, 178));
    header.add(imageLabel, new GridBagConstraints());
    add(header, BorderLayout.CENTER);

    JPanel footer = new JPanel();
    footer.setBackground(new Color(255, 255, 255));
    footer.setPreferredSize(new Dimension(193, 65));
    footer.setLayout(new GridLayout(2, 1));

    JLabel nameLabel = new JLabel(productModel.getName());
    nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
    nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
    footer.add(nameLabel);

    JLabel priceLabel = new JLabel(String.valueOf(productModel.getPrice()));
    priceLabel.setFont(new Font("Segoe UI", Font.ITALIC, 16));
    priceLabel.setForeground(new Color(240, 18, 18));
    priceLabel.setHorizontalAlignment(SwingConstants.CENTER);
    footer.add(priceLabel);

    add(footer, BorderLayout.SOUTH);
  }

  private static class ProductMouseAdapter extends MouseAdapter {
    private final ProductModel productModel;

    ProductMouseAdapter(ProductModel productModel) {
      this.productModel = productModel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
      ProductDetail productDetail = new ProductDetail(productModel);
      productDetail.setVisible(true);
    }
  }
}
