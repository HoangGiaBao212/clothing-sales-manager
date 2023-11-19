package com.clothingstore.gui.components.customerList;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

import com.clothingstore.gui.components.HomePage;
import com.clothingstore.gui.components.invoicesHistory.InvoiceDetail;
import com.clothingstore.gui.components.invoicesHistory.InvoiceHistory;
import com.clothingstore.models.OrderModel;

public class Invoice extends JPanel {
  private Color background;

  public Invoice() {
    background = new Color(153, 179, 255);
    initComponents("ID", "Date", "Product Total", "Total Money");
  }

  public Invoice(OrderModel orderModel) { // dành cho data
    background = Color.WHITE;
    String id = String.valueOf(orderModel.getId());
    String date = String.valueOf(orderModel.getOrderDate());
    String productTotal = String.valueOf(orderModel.getCustomerId());
    String totalPrice = String.valueOf(orderModel.getTotalPrice());
    initComponents(id, date, productTotal, totalPrice);
    this.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        HomePage.getInstance().Remove();
        HomePage.getInstance().Add(new InvoiceDetail(orderModel));
      }
    });
  }

  private void initComponents(String id, String date, String quantity, String total) {

    Panel1 = new JPanel();
    Id = new JLabel();
    Panel2 = new JPanel();
    Date = new JLabel();
    Panel3 = new JPanel();
    ProductQuantity = new JLabel();
    Panel4 = new JPanel();
    Total = new JLabel();

    setLayout(new GridLayout(1, 0, 5, 0));
    setPreferredSize(new Dimension(100, 40));
    setBackground(background);

    Panel1.setPreferredSize(new Dimension(150, 150));
    Panel1.setRequestFocusEnabled(false);
    Panel1.setBackground(background);
    Panel1.setLayout(new BorderLayout());

    Id.setFont(new Font("Segoe UI", 3, 13));
    Id.setHorizontalAlignment(SwingConstants.CENTER);
    Id.setText(id);
    Panel1.add(Id, BorderLayout.CENTER);

    add(Panel1);

    Panel2.setPreferredSize(new Dimension(150, 150));
    Panel2.setRequestFocusEnabled(false);
    Panel2.setBackground(background);
    Panel2.setLayout(new BorderLayout());

    Date.setFont(new Font("Segoe UI", 3, 13));
    Date.setHorizontalAlignment(SwingConstants.CENTER);
    Date.setText(date);
    Panel2.add(Date, BorderLayout.CENTER);

    add(Panel2);

    Panel3.setPreferredSize(new Dimension(150, 150));
    Panel3.setRequestFocusEnabled(false);
    Panel3.setBackground(background);
    Panel3.setLayout(new BorderLayout());

    ProductQuantity.setFont(new Font("Segoe UI", 3, 13));
    ProductQuantity.setHorizontalAlignment(SwingConstants.CENTER);
    ProductQuantity.setText(quantity);
    Panel3.add(ProductQuantity, BorderLayout.CENTER);

    add(Panel3);

    Panel4.setPreferredSize(new Dimension(150, 150));
    Panel4.setRequestFocusEnabled(false);
    Panel4.setBackground(background);
    Panel4.setLayout(new BorderLayout());

    Total.setFont(new Font("Segoe UI", 3, 13));
    Total.setHorizontalAlignment(SwingConstants.CENTER);
    Total.setText(total);
    Panel4.add(Total, BorderLayout.CENTER);

    add(Panel4);
  }

  private JLabel Total;
  private JPanel Panel1;
  private JPanel Panel2;
  private JPanel Panel3;
  private JPanel Panel4;
  private JLabel ProductQuantity;
  private JLabel Id;
  private JLabel Date;
}
