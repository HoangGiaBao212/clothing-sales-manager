package com.clothingstore.gui.components;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

import javax.swing.*;

import org.netbeans.lib.awtextra.*;

import com.clothingstore.bus.ProductBUS;
import com.clothingstore.gui.models.NavData;
import com.clothingstore.models.ProductModel;
import com.clothingstore.models.UserModel;

import services.Authentication;

public class ProductsHeader extends JPanel {

  private static ProductsHeader instance;

  UserModel currentUser = Authentication.getCurrentUser();

  public static ProductsHeader getInstance() {
    if (instance == null) {
      instance = new ProductsHeader();

    }
    return instance;
  }

  public static void setInstance(ProductsHeader instance) {
    ProductsHeader.instance = instance;
  }

  public ProductsHeader() {
    initComponents();
  }

  private void initComponents() {

    Panel = new JPanel();
    Value = new JTextField();
    ButtonSearch = new JButton();
    ButtonAdd = new JButton();
    ButtonMenu = new JButton();
    Color color = new Color(153, 194, 255);

    setBorder(BorderFactory.createEmptyBorder(1, 10, 1, 10));
    setPreferredSize(new Dimension(511, 50));
    setLayout(new BorderLayout());
    setBackground(color);

    Panel.setLayout(new AbsoluteLayout());
    Panel.setBackground(color);

    Value.setFont(new Font("Segoe UI", 0, 14));
    Value.setText("Tìm kiếm sản phẩm");
    Value.addFocusListener(new FocusListener() {
      @Override
      public void focusGained(FocusEvent e) {
        if (Value.getText().equals("Tìm kiếm sản phẩm")) {
          Value.setText("");
        }
      }

      @Override
      public void focusLost(FocusEvent e) {
        if (Value.getText().isEmpty()) {
          Value.setText("Tìm kiếm sản phẩm");
        }
      }
    });
    Panel.add(Value, new AbsoluteConstraints(210, 10, 170, 30));

    ButtonSearch.setIcon(new ImageIcon(getClass().getResource("/resources/icons/search.png")));
    ButtonSearch.setBorder(null);
    ButtonSearch.setBackground(Color.WHITE);
    Panel.add(ButtonSearch, new AbsoluteConstraints(380, 11, 28, 29));
    ButtonSearch.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        if (Value.getText().isEmpty() || Value.getText().isBlank() || Value.getText().equals("Tìm kiếm sản phẩm")) {
          JFrame jf = new JFrame();
          jf.setAlwaysOnTop(true);
          JOptionPane.showMessageDialog(jf, "Vui lòng nhập thông tin cần tìm!");
          return;
        }
        String input = Value.getText().toLowerCase();
        List<ProductModel> productModels = null;
        ProductModel productModel = ProductBUS.getInstance().getModelById(Integer.parseInt(Value.getText()));
        if (isInteger(input) == true && productModel != null) {
          ProductDetail productDetail = new ProductDetail(productModel);
          productDetail.setVisible(true);
          return;
        } else {
          productModels = ProductBUS.getInstance().searchModel(input, new String[] { "name" });
        }
        if (productModels == null || productModels.isEmpty()) {
          JFrame jf = new JFrame();
          JOptionPane.showMessageDialog(jf, "Không tìm thấy sản phẩm!");
          productModels = ProductBUS.getInstance().getAllModels();
        }
        Products.getInstance().showProductsFromResult(productModels);
        revalidate();
        repaint();
      }
    });
    ButtonAdd.setText("Thêm");
    ButtonAdd.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        AddNewProduct addProduct = new AddNewProduct();
        addProduct.setVisible(true);
      }

    });

    ButtonMenu.setIcon(new ImageIcon(getClass().getResource("/resources/icons/menu.png")));
    ButtonMenu.setBackground(color);
    ButtonMenu.setBorder(null);
    ButtonMenu.addActionListener(new NavData().MenuAction());

    if (currentUser.getRoleId() != 3) {
      Panel.add(ButtonAdd, new AbsoluteConstraints(440, 10, 80, 30));
    }
    add(ButtonMenu, BorderLayout.WEST);

    add(Panel, BorderLayout.LINE_END);
  }

  private boolean isInteger(String s) {
    try {
      Integer.parseInt(s);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  private JButton ButtonAdd;
  private JButton ButtonSearch;
  private JButton ButtonMenu;
  private JPanel Panel;
  private JTextField Value;
}
