package com.clothingstore.gui.components.customerList;

import java.awt.*;
import java.util.List;

import javax.swing.*;

import com.clothingstore.bus.CustomerBUS;
import com.clothingstore.models.CustomerModel;

public class Customers extends JPanel {
  private static Customers instance;
  List<CustomerModel> customerList = CustomerBUS.getInstance().getAllModels();

  public static Customers getInstance() {
    if (instance == null) {
      instance = new Customers();
    }
    return instance;
  }

  public Customers() {
    initComponents();
  }

  private void initComponents() {
    setLayout(new BorderLayout());
    add(CustomerList.getInstance(), BorderLayout.WEST);
    add(new CustomerDetail(customerList.get(customerList.size() - 1)), BorderLayout.CENTER);
  }

  public void Remove() {
    Container contentPane = Customers.getInstance();
    Component centerComponent = ((BorderLayout) contentPane.getLayout()).getLayoutComponent(BorderLayout.CENTER);
    contentPane.remove(centerComponent);
    contentPane.revalidate();
    contentPane.repaint();
  }

}
