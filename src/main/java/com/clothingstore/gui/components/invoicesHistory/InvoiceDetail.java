package com.clothingstore.gui.components.invoicesHistory;

import javax.swing.*;

import com.clothingstore.bus.CustomerBUS;
import com.clothingstore.bus.OrderItemBUS;
import com.clothingstore.bus.PaymentBUS;
import com.clothingstore.bus.PaymentMethodBUS;
import com.clothingstore.bus.UserBUS;
import com.clothingstore.gui.components.InvoiceProduct;
import com.clothingstore.gui.employee.invoiceDetail.HeaderInvoice;
import com.clothingstore.gui.models.NavData;
import com.clothingstore.models.CustomerModel;
import com.clothingstore.models.OrderItemModel;
import com.clothingstore.models.OrderModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDetail extends JPanel {

  private String name;
  private String value;
  private int quantity = 0;

  OrderModel orderModel;
  List<OrderItemModel> orderItemModels;

  public InvoiceDetail(OrderModel orderModel) {
    this.orderModel = orderModel;
    orderItemModels = OrderItemBUS.getInstance().searchModel(String.valueOf(orderModel.getId()),
        new String[] { "order_id" });
    initComponents();
  }

  public InvoiceDetail(OrderModel orderModel, int i) {
    this.orderModel = orderModel;
    orderItemModels = OrderItemBUS.getInstance().searchModel(String.valueOf(orderModel.getId()),
        new String[] { "order_id" });
    initComponents();
    setMenu();
  }

  public InvoiceDetail(String name, String value) {
    this.name = name;
    this.value = value;
  }

  public List<InvoiceDetail> getData() {
    String nameCustomer;
    String phone;
    CustomerModel customerModel = CustomerBUS.getInstance().getModelById(orderModel.getCustomerId());
    if(customerModel.getId() == 1){
      nameCustomer = "null";
      phone = "null";
    }
    else {
      nameCustomer = customerModel.getCustomerName();
      phone = customerModel.getPhone();
    }
    for(OrderItemModel orderItemModel: orderItemModels)
      quantity += orderItemModel.getQuantity();
    return new ArrayList<InvoiceDetail>() {
      {
        add(new InvoiceDetail("Mã hóa đơn", "" + orderModel.getId()));
        add(new InvoiceDetail("Nhân viên phụ trách", UserBUS.getInstance().getModelById(orderModel.getUserId()).getName()));
        add(new InvoiceDetail("Ngày tạo", "" + orderModel.getOrderDate()));
        add(new InvoiceDetail("Tổng tiền", "" + orderModel.getTotalPrice()));
        add(new InvoiceDetail("Phương thức thanh toán", "" + PaymentMethodBUS.getInstance().getModelById(PaymentBUS.getInstance().searchModel(String.valueOf(orderModel.getId()), new String[]{"order_id"}).get(0).getPaymentMethodId()).getMethodName()));
        add(new InvoiceDetail("Tên khách hàng",nameCustomer));
        add(new InvoiceDetail("Số điện thoại", phone));
        add(new InvoiceDetail("Số lượng sản phẩm", String.valueOf(quantity)));
      }
    };
  }

  public void initComponents() {
    NameLabel = new JLabel();
    Info = new JPanel();
    Products = new JPanel();
    Scroll = new JScrollPane();
    Product = new JPanel();
    HeaderProducts = new JPanel();
    HeaderPanel = new JPanel();

    mainPanel = new JPanel();
    mainPanel.setLayout(new BorderLayout());

    setLayout(new BorderLayout());
    setBackground(new Color(153, 194, 255));

    Scroll.setViewportView(mainPanel);
    Scroll.getVerticalScrollBar().setUnitIncrement(10);
    Info.setLayout(new GridLayout(0, 1));

    HeaderPanel.setLayout(new BorderLayout());
    HeaderPanel.setBackground(new Color(153, 194, 255));
    NameLabel.setText("-- Chi Tiết Hóa Đơn --");
    NameLabel.setHorizontalAlignment(SwingConstants.CENTER);
    NameLabel.setVerticalAlignment(SwingConstants.CENTER);
    NameLabel.setFont(new Font("Segoe UI", 1, 17));
    NameLabel.setPreferredSize(new Dimension(150, 70));
    HeaderPanel.add(NameLabel, BorderLayout.CENTER);

    add(HeaderPanel, BorderLayout.NORTH);
    for (InvoiceDetail invoiceDetail : getData()) {
      JPanel panel = new JPanel();
      panel.setBackground(Color.WHITE);
      panel.setPreferredSize(new Dimension(60, 60));
      panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
      panel.setLayout(new BorderLayout());

      JLabel Name = new JLabel(invoiceDetail.name);
      Name.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
      Name.setFont(new Font("Segoe UI", 1, 15));
      panel.add(Name, BorderLayout.WEST);

      JLabel Value = new JLabel(invoiceDetail.value);
      Value.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 19));
      Value.setFont(new Font("Segoe UI", 0, 15));
      panel.add(Value, BorderLayout.EAST);

      Info.add(panel);
    }
    Products.setLayout(new BorderLayout());

    HeaderProducts.setLayout(new BorderLayout());
    HeaderProducts.setPreferredSize(new Dimension(100, 40));
    HeaderProducts.add(HeaderInvoice.getInstance(), BorderLayout.CENTER);

    Product.setLayout(new GridLayout(orderItemModels.size(), 1));
    for (int i = 0; i < orderItemModels.size(); i++) {
      InvoiceProduct product = new InvoiceProduct(orderModel, orderItemModels.get(i), i +1);
      Product.add(product);
    }

    Products.add(HeaderProducts, BorderLayout.NORTH);
    Products.add(Product, BorderLayout.CENTER);

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(0, 0, 0, 10);

    mainPanel.add(Info, BorderLayout.CENTER);
    mainPanel.add(Products, BorderLayout.SOUTH);
    add(Scroll, BorderLayout.CENTER);
  }

  private JPanel mainPanel;
  private JPanel HeaderPanel;
  private JLabel NameLabel;
  private JPanel Info;
  private JPanel Products;
  private JScrollPane Scroll;
  private JPanel Product;
  private JPanel HeaderProducts;

  private void setMenu(){
    JButton ButtonMenu = new JButton();
    ButtonMenu.setIcon(new ImageIcon(getClass().getResource("/resources/icons/menu.png")));
    ButtonMenu.setBackground(new Color(153, 194, 255));
    ButtonMenu.setBorder(null);
    ButtonMenu.addActionListener(new NavData().MenuAction());
    HeaderPanel.add(ButtonMenu, BorderLayout.WEST);
  }
}
