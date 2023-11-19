package com.clothingstore.gui.employee;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import org.netbeans.lib.awtextra.*;

import com.clothingstore.bus.ProductBUS;
import com.clothingstore.bus.SizeItemBUS;
import com.clothingstore.gui.components.Products;
import com.clothingstore.gui.employee.invoiceDetail.InvoiceDetail;
import com.clothingstore.models.OrderItemModel;
import com.clothingstore.models.ProductModel;
import com.clothingstore.models.SizeItemModel;

public class Invoice extends JPanel {
  private static Invoice instance;
  List<OrderItemModel> orderItemList = new ArrayList<>();
  List<OrderItemModel> backupOrderItemsList;

  public static Invoice getInstance() {
    if (instance == null) {
      instance = new Invoice();
    }
    return instance;
  }

  public static void setInstance(Invoice newInstance) {
    instance = newInstance;
  }

  public Invoice() {
    initComponents();
    backupOrderItemsList = new ArrayList<>(orderItemList);
    // updateCartUI(orderItemModel);
  }

  private void initComponents() {
    Header = new JPanel();
    Index = new JLabel();
    None = new JLabel();
    NameShop = new JTextField();
    Content = new JPanel();
    Scroll = new JScrollPane();
    Invoices = new JPanel();
    Footer = new JPanel();
    TextSum = new JLabel();
    Value = new JLabel();
    ButtonCancel = new JButton();
    ButtonPay = new JButton();

    Color color = new Color(128, 179, 255);

    setPreferredSize(new Dimension(300, 370));
    setLayout(new BorderLayout());

    Header.setBackground(new Color(0, 51, 128));
    Header.setLayout(new BorderLayout());

    Index.setFont(new Font("Segoe UI", 0, 14));
    Index.setHorizontalAlignment(SwingConstants.CENTER);
    Index.setText("45");
    Index.setPreferredSize(new Dimension(40, 50));
    Index.setForeground(Color.WHITE);

    None.setPreferredSize(new Dimension(40, 50));

    NameShop.setEditable(false);
    NameShop.setFont(new Font("Segoe UI", 1, 18));
    NameShop.setHorizontalAlignment(JTextField.CENTER);
    NameShop.setBackground(new Color(128, 179, 255));
    NameShop.setText("The Shop VIP");

    Content.setLayout(new BorderLayout());

    Invoices.setBackground(new Color(255, 255, 255));
    Invoices.setLayout(new GridLayout(10, 1));

    Scroll.setViewportView(Invoices);

    Footer.setPreferredSize(new Dimension(301, 120));
    Footer.setLayout(new AbsoluteLayout());
    Footer.setBackground(color);

    TextSum.setFont(new Font("Segoe UI", 1, 13));
    TextSum.setHorizontalAlignment(SwingConstants.CENTER);
    TextSum.setText("Tổng Thanh Toán");

    Value.setFont(new Font("Segoe UI", 0, 18));
    Value.setForeground(new Color(255, 51, 51));
    Value.setHorizontalAlignment(SwingConstants.CENTER);
    Value.setText("0");

    ButtonCancel.setText("Hủy");
    ButtonCancel.setBackground(Color.BLUE);
    ButtonCancel.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        int choice = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa toàn bộ sản phẩm khỏi giỏ hàng?",
            "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
          clearAllItemsInCart();
        } else {
          return;
        }
        if ((orderItemList.isEmpty() || orderItemList == null) && choice == JOptionPane.YES_OPTION) {
          JFrame jf = new JFrame();
          jf.setAlwaysOnTop(false);
          JOptionPane.showMessageDialog(jf, "Bạn không có sản phẩm nào trong giỏ hàng!");
          return;
        }
      }
    });

    ButtonCancel.setPreferredSize(new Dimension(72, 20));

    ButtonPay.setFont(new Font("Segoe UI", 0, 10));
    ButtonPay.setText("Thanh Toán");
    ButtonPay.setBackground(Color.BLUE);
    ButtonPay.setPreferredSize(new Dimension(72, 20));
    ButtonPay.addActionListener(PayAction);
    ButtonPay.setVisible(false);

    Header.add(Index, BorderLayout.LINE_START);
    Header.add(None, BorderLayout.LINE_END);
    Header.add(NameShop, BorderLayout.CENTER);
    add(Header, BorderLayout.PAGE_START);

    Content.add(Scroll, BorderLayout.CENTER);
    add(Content, BorderLayout.CENTER);

    Footer.add(TextSum, new AbsoluteConstraints(0, 20, 120, 30));
    Footer.add(Value, new AbsoluteConstraints(0, 40, 120, 30));
    Footer.add(ButtonCancel, new AbsoluteConstraints(40, 80, 90, 30));
    Footer.add(ButtonPay, new AbsoluteConstraints(170, 80, 90, 30));
    add(Footer, BorderLayout.SOUTH);

  }

  private JButton ButtonCancel;
  private JButton ButtonPay;
  private JPanel Content;
  private JPanel Footer;
  private JPanel Header;
  private JLabel Index;
  private JPanel Invoices;
  private JTextField NameShop;
  private JLabel None;
  private JScrollPane Scroll;
  private JLabel TextSum;
  private JLabel Value;
  private double totalPrice;

  public void addToCart(ProductModel productModel, int size, int quantity) {
    ButtonPay.setVisible(true);
    OrderItemModel orderItemModel = new OrderItemModel(0, 1, productModel.getId(), size, quantity,
        productModel.getPrice() * quantity);
    boolean found = false;

    for (OrderItemModel item : orderItemList) {
      if (item.getProductId() == orderItemModel.getProductId() && item.getSizeId() == orderItemModel.getSizeId()) {
        found = true;
        JFrame jf = new JFrame();
        jf.setAlwaysOnTop(true);
        JOptionPane.showMessageDialog(jf,
            "Sản phẩm này với size bạn chọn đã có trong giỏ hàng của bạn, vui lòng kiểm tra lại!");
        break;
      }
    }

    if (!found) {
      orderItemList.add(orderItemModel);
      InvoiceProduct invoiceProduct = new InvoiceProduct(productModel, size, quantity);
      Invoices.add(invoiceProduct);
      totalPrice += orderItemModel.getPrice() * orderItemModel.getQuantity();
      Value.setText(" " + totalPrice);
      revalidate();
      repaint();
    }

    revalidate();
    repaint();
  }

  public void deleteProductInCart(ProductModel productModel, int size, int quantity) {
    OrderItemModel orderItemModel = new OrderItemModel(0, 1, productModel.getId(), size, quantity,
        productModel.getPrice() * quantity);

    for (int i = 0; i < orderItemList.size(); i++) {
      if (orderItemList.get(i).getProductId() == orderItemModel.getProductId()
          && orderItemList.get(i).getSizeId() == orderItemModel.getSizeId()) {
        orderItemList.remove(i);

        // Find and remove the associated InvoiceProduct from Invoices panel
        Component[] components = Invoices.getComponents();
        for (Component component : components) {
          if (component instanceof InvoiceProduct) {
            InvoiceProduct invoiceProduct = (InvoiceProduct) component;
            if (invoiceProduct.getProductModel().getId() == productModel.getId()
                && invoiceProduct.getSizeId() == size && invoiceProduct.getQuantity() == quantity) {
              Invoices.remove(invoiceProduct);
              totalPrice = totalPrice - (productModel.getPrice() * quantity);
              Value.setText(" " + totalPrice);
              break;
            }
          }
        }

        revalidate();
        repaint();
        break;
      }
    }
  }

  private boolean cartChanged() {
    if (orderItemList.size() != backupOrderItemsList.size()) {
      return true; // If the sizes are different, there's a change
    }

    for (OrderItemModel newItem : orderItemList) {
      boolean found = false;
      for (OrderItemModel oldItem : backupOrderItemsList) {
        if (newItem.getProductId() == oldItem.getProductId() && newItem.getSizeId() == oldItem.getSizeId()) {
          found = true;
          if (newItem.getQuantity() != oldItem.getQuantity()) {
            return true; // Quantity changed for an existing item
          }
          break;
        }
      }
      if (!found) {
        return true; // New item added
      }
    }

    return false; // No changes found
  }

  private void updateCart() {
    Invoices.removeAll();
    totalPrice = 0;
    for (OrderItemModel orderItem : orderItemList) {
      ProductModel productModel = ProductBUS.getInstance().getModelById(orderItem.getProductId());
      InvoiceProduct invoiceProduct = new InvoiceProduct(productModel, orderItem.getSizeId(), orderItem.getQuantity());
      totalPrice += orderItem.getPrice() * orderItem.getQuantity();
      Value.setText(" " + totalPrice);
      Invoices.add(invoiceProduct);
    }

    revalidate();
    repaint();
  }

  public void updateQuantity(ProductModel productModel, int size, int quantity) {
    for (OrderItemModel orderItem : orderItemList) {
      if (orderItem.getProductId() == productModel.getId() && orderItem.getSizeId() == size) {
        orderItem.setQuantity(quantity);
      }
    }
    updateCart();
  }

  private ActionListener PayAction = new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
      if (!orderItemList.isEmpty() && orderItemList != null) {
        if (cartChanged()) {
          updateCart();
        }
        List<OrderItemModel> itemsToShow = new ArrayList<>(orderItemList);
        InvoiceDetail invoiceDetail = new InvoiceDetail(itemsToShow);
        invoiceDetail.setVisible(true);
      } else {
        JOptionPane.showMessageDialog(null, "Giỏ hàng của bạn không có sản phẩm nào, vui lòng kiểm tra lại!");
        ButtonPay.setVisible(false);
        return;
      }
    }
  };

  public void clearAllItemsInCart() {
    orderItemList.clear();
    totalPrice = 0;
    Value.setText("" + totalPrice);
    Invoices.removeAll();
    revalidate();
    repaint();
  }
}