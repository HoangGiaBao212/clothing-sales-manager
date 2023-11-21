package com.clothingstore.gui.components.customerList;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.*;

import com.clothingstore.bus.CustomerBUS;
import com.clothingstore.gui.models.NavData;
import com.clothingstore.models.CustomerModel;

public class CustomerList extends JPanel {

  private static CustomerList instance;

  public static CustomerList getInstance() {
    if (instance == null) {
      instance = new CustomerList();
    }
    return instance;
  }

  public CustomerList() {
    initComponents();
  }

  private void initComponents() {

    Header = new JPanel();
    NameHeader = new JPanel();
    NamePanel = new JLabel();
    ButtonMenu = new JButton();
    Panel = new JPanel();
    ButtonSearch = new JButton();
    SearchValue = new JTextField();
    Customers = new JPanel();
    Scroll = new JScrollPane();

    Color color = new Color(179, 209, 255);

    setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 4));
    setLayout(new BorderLayout());
    setPreferredSize(new Dimension(322, 170));
    setBackground(color);

    Header.setLayout(new BorderLayout());
    Header.setPreferredSize(new Dimension(250, 65));
    Header.setBackground(color);

    NameHeader.setLayout(new BorderLayout());

    NamePanel.setFont(new Font("Segoe UI", 1, 18)); // NOI18N
    NamePanel.setHorizontalAlignment(SwingConstants.CENTER);
    NamePanel.setText("Danh sách khách hàng");
    NameHeader.setBackground(color);
    NameHeader.add(NamePanel, BorderLayout.CENTER);

    ButtonMenu.setIcon(new ImageIcon(getClass().getResource("/resources/icons/menu.png")));
    ButtonMenu.setBackground(color);
    ButtonMenu.setBorder(null);
    ButtonMenu.addActionListener(new NavData().MenuAction());
    NameHeader.add(ButtonMenu, BorderLayout.LINE_START);

    Header.add(NameHeader, BorderLayout.NORTH);

    Panel.setBorder(BorderFactory.createEmptyBorder(4, 4, 7, 1));
    Panel.setLayout(new BorderLayout());
    Panel.setBackground(color);

    ButtonSearch.setIcon(new ImageIcon(getClass().getResource("/resources/icons/search.png"))); // NOI18N
    ButtonSearch.setBorder(null);
    ButtonSearch.setBackground(Color.WHITE);
    Panel.add(ButtonSearch, BorderLayout.WEST);

    SearchValue.setBackground(new Color(242, 242, 242));
    SearchValue.setFont(new Font("Segoe UI", 0, 14)); // NOI18N
    SearchValue.setText("Tìm theo mã khách hàng");
    SearchValue.setBackground(Color.white);
    SearchValue.setBorder(BorderFactory.createEmptyBorder(1, 6, 1, 1));
    SearchValue.addFocusListener(new FocusListener() {
      @Override
      public void focusGained(FocusEvent e) {
        if (SearchValue.getText().equals("Tìm theo mã khách hàng")) {
          SearchValue.setText("");
        }
      }

      @Override
      public void focusLost(FocusEvent e) {
        if (SearchValue.getText().isEmpty()) {
          SearchValue.setText("Tìm theo mã khách hàng");
        }
      }
    });

    Panel.add(SearchValue, BorderLayout.CENTER);

    Header.add(Panel, BorderLayout.SOUTH);

    add(Header, BorderLayout.PAGE_START);

    Customers.setLayout(new GridLayout(0, 1));
    Customers.setBackground(color);

    List<CustomerModel> customerList = new ArrayList<>(CustomerBUS.getInstance().getAllModels());
    customerList.remove(0);
    Collections.reverse(customerList);
    for (CustomerModel customerModel : customerList) {
      Customer customer = new Customer(customerModel);
      Customers.add(customer);
    }

    Scroll.setViewportView(Customers);
    Scroll.getVerticalScrollBar().setUnitIncrement(30);
    add(Scroll, BorderLayout.CENTER);
  }

  private JButton ButtonMenu;
  private JButton ButtonSearch;
  private JPanel Header;
  private JPanel Customers;
  private JPanel NameHeader;
  private JLabel NamePanel;
  private JTextField SearchValue;
  private JPanel Panel;
  private JScrollPane Scroll;
}
