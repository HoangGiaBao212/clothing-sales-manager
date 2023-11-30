package com.clothingstore.gui.employee;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.netbeans.lib.awtextra.*;

import com.clothingstore.bus.ProductBUS;
import com.clothingstore.bus.SizeItemBUS;
import com.clothingstore.models.ProductModel;
import com.clothingstore.models.SizeItemModel;

public class InvoiceProduct extends JPanel {

  private ProductModel productModel;
  private int sizeId;
  private int quantity;
  DecimalFormat decimalFormat = new DecimalFormat("###,###");

  public InvoiceProduct(ProductModel productModel, int size, int quantity) {
    this.productModel = productModel;
    this.sizeId = size;
    this.quantity = quantity;
    initComponents(productModel, size, quantity);
  }

  public ProductModel getProductModel() {
    return productModel;
  }

  public int getSizeId() {
    return sizeId;
  }

  public int getQuantity() {
    return quantity;
  }

  private void initComponents(ProductModel productModel, int size, int quantity) {
    java.util.List<SizeItemModel> sizeItemModels = new ArrayList<>(SizeItemBUS.getInstance()
        .searchModel(String.valueOf(productModel.getId()), new String[] { "product_id" }));
    java.util.Iterator<SizeItemModel> iterator = sizeItemModels.iterator();
    while (iterator.hasNext()) {
      SizeItemModel sizeItemModel = iterator.next();
      if (sizeItemModel.getSizeId() != size) {
        iterator.remove(); // Remove the item that doesn't match the condition
      }
    }

    Name = new JLabel();
    Size = new JLabel();
    Price = new JLabel();
    ButtonDel = new JButton();

    setBorder(BorderFactory.createEtchedBorder());
    setMinimumSize(new Dimension(220, 40));
    setPreferredSize(new Dimension(220, 65));
    setLayout(new AbsoluteLayout());
    setBackground(new Color(179, 209, 255));

    Name.setFont(new Font("Segoe UI", 3, 14));
    Name.setText("" + productModel.getName());

    add(Name, new AbsoluteConstraints(5, 6, 230, 30));

    Size.setFont(new Font("Segoe UI", 1, 14));
    Size.setForeground(Color.BLUE);
    if (size == 1) {
      Size.setText("( S )");
    } else if (size == 2) {
      Size.setText("( M )");
    } else if (size == 3) {
      Size.setText("( L )");
    } else if (size == 4) {
      Size.setText("( XL )");
    } else if (size == 5) {
      Size.setText("( XXL )");
    }
    add(Size, new AbsoluteConstraints(35, 28, 85, 30));

    spinner = new JSpinner(new SpinnerNumberModel(quantity, 1, sizeItemModels.get(0).getQuantity(), 1));
    spinner.setBackground(new Color(255, 204, 204));
    add(spinner, new AbsoluteConstraints(90, 34, 55, 20));
    spinner.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent e) {
        // double totalPrice =
        // ProductBUS.getInstance().getModelById(productModel.getId()).getPrice()
        // * (int) spinner.getValue();
        // Price.setText("" + totalPrice);
        Invoice.getInstance().updateQuantity(productModel, size, (int) spinner.getValue());
      }
    });
    Price.setFont(new Font("Segoe UI", 0, 15));
    Price.setForeground(new Color(255, 0, 0));

    double totalPrice = ProductBUS.getInstance().getModelById(productModel.getId()).getPrice()
        * (int) spinner.getValue();
    Price.setText(decimalFormat.format(totalPrice));
    add(Price, new AbsoluteConstraints(160, 28, 90, 28));

    ImageIcon originalIcon = new ImageIcon(getClass().getResource("/resources/icons/delete.png"));
    Image originalImage = originalIcon.getImage();
    Image scaledImage = originalImage.getScaledInstance(17, 17, java.awt.Image.SCALE_REPLICATE);
    ImageIcon scaledIcon = new ImageIcon(scaledImage);

    ButtonDel.setIcon(scaledIcon);
    add(ButtonDel, new AbsoluteConstraints(240, 22, 25, 25));
    ButtonDel.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JFrame jf = new JFrame();
        jf.setAlwaysOnTop(true);
        int choice = JOptionPane.showConfirmDialog(jf, "Bạn có muốn xóa sản phẩm này không?");
        if (choice == JOptionPane.YES_OPTION) {
          Invoice.getInstance().deleteProductInCart(productModel, size, quantity);
          revalidate();
          repaint();
        }
      }

    });
  }

  private JLabel Name;
  private JLabel Price;
  private JLabel Size;
  private JButton ButtonDel;
  public JSpinner spinner;
}
