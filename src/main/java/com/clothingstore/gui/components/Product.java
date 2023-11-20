package com.clothingstore.gui.components;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.*;
import com.clothingstore.models.OrderItemModel;
import com.clothingstore.models.ProductModel;

public class Product extends JPanel {
  public static java.util.List<OrderItemModel> cartItems = new ArrayList<>();

  public Product(ProductModel productModel) {
    initComponents(productModel);
    addMouseListener(new MouseListener() {
      @Override
      public void mouseClicked(MouseEvent arg0) {
        ProductDetail productDetail = new ProductDetail(productModel);
        productDetail.setVisible(true);
      }

      @Override
      public void mouseEntered(MouseEvent arg0) {
      }

      @Override
      public void mouseExited(MouseEvent arg0) {
      }

      @Override
      public void mousePressed(MouseEvent arg0) {
      }

      @Override
      public void mouseReleased(MouseEvent arg0) {
      }
    });
  }

  private void initComponents(ProductModel productModel) {

    Header = new JPanel();
    Image = new JLabel();
    Footer = new JPanel();
    Name = new JLabel();
    Price = new JLabel();

    setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    setPreferredSize(new Dimension(199, 280));
    setLayout(new BorderLayout());

    Header.setBackground(new Color(255, 255, 255));
    Header.setLayout(new GridBagLayout());

    ImageIcon icon = new ImageIcon(productModel.getImage());
    Image image = icon.getImage();
    Image scaledImage = image.getScaledInstance(180, 180, java.awt.Image.SCALE_SMOOTH);
    ImageIcon scaledIcon = new ImageIcon(scaledImage);
    Image.setIcon(scaledIcon);
    Image.setPreferredSize(new Dimension(180, 178));
    Header.add(Image, new GridBagConstraints());
    add(Header, BorderLayout.CENTER);

    Footer.setBackground(new Color(255, 255, 255));
    Footer.setPreferredSize(new Dimension(193, 65));
    Footer.setLayout(new GridLayout(2, 1));

    Name.setFont(new Font("Segoe UI", 1, 18)); // NOI18N
    Name.setHorizontalAlignment(SwingConstants.CENTER);
    Name.setText(productModel.getName());
    Footer.add(Name);

    Price.setFont(new Font("Segoe UI", 2, 16)); // NOI18N
    Price.setForeground(new Color(240, 18, 18));
    Price.setHorizontalAlignment(SwingConstants.CENTER);
    Price.setText(String.valueOf(productModel.getPrice()));
    Footer.add(Price);

    add(Footer, BorderLayout.SOUTH);
  }

  private JPanel Footer;
  private JPanel Header;
  private JLabel Image;
  private JLabel Name;
  private JLabel Price;
}
