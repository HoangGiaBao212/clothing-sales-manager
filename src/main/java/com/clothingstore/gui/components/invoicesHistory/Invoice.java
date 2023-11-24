package com.clothingstore.gui.components.invoicesHistory;

import java.awt.*;
import java.awt.event.MouseListener;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;

import com.clothingstore.bus.CustomerBUS;
import com.clothingstore.models.CustomerModel;
import com.clothingstore.models.OrderModel;

public class Invoice extends JPanel {

  DecimalFormat decimalFormat = new DecimalFormat("###,###");

  public Invoice(OrderModel orderModel) {
    initComponents(orderModel);
    this.addMouseListener(new MouseListener() {

      @Override
      public void mouseClicked(java.awt.event.MouseEvent e) {
        InvoiceDetail invoiceDetail = new InvoiceDetail(orderModel);
        InvoiceHistory.getInstance().Remove();
        InvoiceHistory.getInstance().add(invoiceDetail, BorderLayout.CENTER);
        InvoiceHistory.getInstance().revalidate();
        InvoiceHistory.getInstance().repaint();
      }

      @Override
      public void mousePressed(java.awt.event.MouseEvent e) {

      }

      @Override
      public void mouseReleased(java.awt.event.MouseEvent e) {

      }

      @Override
      public void mouseEntered(java.awt.event.MouseEvent e) {

      }

      @Override
      public void mouseExited(java.awt.event.MouseEvent e) {

      }

    });
  }

  private void initComponents(OrderModel orderModel) {

    Icon = new JLabel();
    Detail = new JPanel();
    Panel = new JPanel();
    Price = new JLabel();
    IdInvoice = new JLabel();
    CustomerInfo = new JPanel();
    CustomerName = new JLabel();
    Time = new JPanel();
    Date = new JLabel();
    TimeHour = new JLabel();

    Color color = new Color(255, 255, 255);

    setLayout(new BorderLayout());
    setPreferredSize(new Dimension(40, 60));
    setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
    setBackground(color);

    Icon.setIcon(new ImageIcon(getClass().getResource("/resources/icons/coin.png")));
    Icon.setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 0));
    add(Icon, BorderLayout.LINE_START);

    Detail.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));
    Detail.setBackground(color);
    Detail.setLayout(new BorderLayout());

    Panel.setLayout(new GridLayout(2, 0));
    Panel.setBackground(color);

    Price.setHorizontalAlignment(SwingConstants.CENTER);
    Price.setText(decimalFormat.format(orderModel.getTotalPrice()));
    Price.setFont(new Font("Segoe UI", 0, 14));
    Price.setForeground(Color.RED);
    Panel.add(Price);

    IdInvoice.setHorizontalAlignment(SwingConstants.CENTER);
    IdInvoice.setText(" " + orderModel.getId());
    IdInvoice.setForeground(new Color(0, 128, 0));
    Panel.add(IdInvoice);

    Detail.add(Panel, BorderLayout.LINE_START);

    CustomerInfo.setBorder(BorderFactory.createEmptyBorder(5, 12, 5, 5));
    CustomerInfo.setLayout(new GridLayout(2, 0));
    CustomerInfo.setBackground(color);
    CustomerModel customer = CustomerBUS.getInstance().getModelById(orderModel.getCustomerId());

    if(customer.getId() == 1){
      CustomerName.setText("Khách hàng*");
      CustomerName.setFont(new Font("Segoe UI", 3, 14));
    }
    else {
      CustomerName.setText(customer.getCustomerName());
      CustomerName.setFont(new Font("Segoe UI", 0, 14));
    }
    CustomerInfo.add(CustomerName);

    Detail.add(CustomerInfo, BorderLayout.CENTER);

    add(Detail, BorderLayout.CENTER);

    Time.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    Time.setLayout(new GridLayout(2, 1));
    Time.setBackground(color);

    Timestamp timestamp = orderModel.getOrderDate();
    if (timestamp != null) {
      Date date = new Date(timestamp.getTime());
      SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
      SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm"); // Corrected the time format pattern
      String dateString = dateFormat.format(date);
      String timeString = timeFormat.format(date);
      Date.setText(" " + dateString);
      Date.setFont(new Font("Segoe UI", 0, 13));
      Date.setForeground(new Color(102, 0, 51));
      Time.add(Date);

      TimeHour.setHorizontalAlignment(SwingConstants.CENTER);
      TimeHour.setText(" " + timeString);
      TimeHour.setForeground(new Color(0, 0, 102));
      TimeHour.setFont(new Font("Segoe UI", 0, 12));
      Time.add(TimeHour);
    }

    add(Time, BorderLayout.EAST);
  }

  private JLabel Date;
  private JPanel Detail;
  private JPanel CustomerInfo;
  private JLabel CustomerName;
  private JLabel Icon;
  private JLabel IdInvoice;
  private JPanel Panel;
  private JLabel Price;
  private JPanel Time;
  private JLabel TimeHour;
}
