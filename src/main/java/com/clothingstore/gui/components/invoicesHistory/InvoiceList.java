package com.clothingstore.gui.components.invoicesHistory;

import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import javax.swing.*;

import com.clothingstore.bus.ImportBUS;
import com.clothingstore.bus.OrderBUS;
import com.clothingstore.gui.components.importInvoice.ImportInvoice;
import com.clothingstore.gui.models.NavData;
import com.clothingstore.models.ImportModel;
import com.clothingstore.models.OrderModel;
import com.toedter.calendar.JDateChooser;

public class InvoiceList extends JPanel {

  private JButton ButtonMenu;
  private JButton ButtonSearch;
  private JPanel Header;
  private JPanel Invoices;
  private JPanel NameHeader;
  private JLabel NamePanel;
  private JTextField SearchValue;
  private JPanel Panel;
  private JScrollPane Scroll;
  private JDateChooser startDate;
  private JDateChooser endDate;
  private JPanel fillPanel;
  private JButton filterButton;
  private JButton removeFilterButton;

  private static InvoiceList instance;
  Date currentDate = new Date();
  List<OrderModel> orderList;

  public static InvoiceList getInstance() {
    if (instance == null) {
      instance = new InvoiceList();
    }
    return instance;
  }

  public InvoiceList() {
    initComponents();
    handleEvent();
  }

  private void handleEvent() {
    ButtonSearch.addActionListener(e -> {
      String searchValue = SearchValue.getText();
      if (searchValue == null || searchValue.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Chưa nhập dữ liệu", "Lỗi",
            JOptionPane.ERROR_MESSAGE);
      } else {
        Invoices.removeAll();
        for (OrderModel orderModel : orderList) {
          if (orderModel.getId() == Integer.parseInt(searchValue)) {
            Invoice invoice = new Invoice(orderModel);
            Invoices.add(invoice);
          }
        }
        if (Invoices.getComponentCount() == 0) {
          JOptionPane.showMessageDialog(null, "Không có đơn nhập hàng nào có mã là: " + searchValue, "Lỗi",
              JOptionPane.ERROR_MESSAGE);
          for (OrderModel orderModel : orderList) {
            Invoice invoice = new Invoice(orderModel);
            Invoices.add(invoice);
          }
        }

        Scroll.setViewportView(Invoices);
      }
    });
  }

  private void initComponents() {

    Header = new JPanel();
    NameHeader = new JPanel();
    NamePanel = new JLabel();
    ButtonMenu = new JButton();
    Panel = new JPanel();
    ButtonSearch = new JButton();
    SearchValue = new JTextField();
    Invoices = new JPanel();
    Scroll = new JScrollPane();
    fillPanel = new JPanel();

    Color color = new Color(179, 209, 255);

    setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 4));
    setLayout(new BorderLayout());
    setPreferredSize(new Dimension(400, 170));
    setBackground(color);

    Header.setLayout(new BorderLayout());
    Header.setPreferredSize(new Dimension(400, 95));
    Header.setBackground(color);

    NameHeader.setLayout(new BorderLayout());

    NamePanel.setFont(new Font("Segoe UI", 1, 18)); // NOI18N
    NamePanel.setHorizontalAlignment(SwingConstants.CENTER);
    NamePanel.setText("Hoạt Động");
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
    SearchValue.setText("Tìm theo mã hóa đơn");
    SearchValue.setBackground(Color.WHITE);
    SearchValue.setBorder(BorderFactory.createEmptyBorder(1, 6, 1, 1));
    SearchValue.addFocusListener(new FocusListener() {
      @Override
      public void focusGained(FocusEvent e) {
        if (SearchValue.getText().equals("Tìm theo mã hóa đơn")) {
          SearchValue.setText("");
        }
      }

      @Override
      public void focusLost(FocusEvent e) {
        if (SearchValue.getText().isEmpty()) {
          SearchValue.setText("Tìm theo mã hóa đơn");
        }
      }
    });

    Panel.add(SearchValue, BorderLayout.CENTER);

    setStartDate();
    setEndDate();
    setFilterButton();
    setRemoveFilterButton();
    fillPanel.setBackground(color);
    fillPanel.add(startDate, BorderLayout.WEST);
    fillPanel.add(endDate, BorderLayout.CENTER);
    fillPanel.add(filterButton, BorderLayout.EAST);
    fillPanel.add(removeFilterButton, BorderLayout.EAST);
    Panel.add(fillPanel, BorderLayout.SOUTH);

    Header.add(Panel, BorderLayout.SOUTH);

    add(Header, BorderLayout.PAGE_START);

    Invoices.setLayout(new GridLayout(0, 1));
    Invoices.setBackground(color);
    orderList = new ArrayList<>(OrderBUS.getInstance().getAllModels());
    Collections.reverse(orderList);
    CheckResponsive();
    for (OrderModel orderModel : orderList) {
      Invoice invoice = new Invoice(orderModel);
      Invoices.add(invoice);
    }

    Scroll.setViewportView(Invoices);
    Scroll.getVerticalScrollBar().setUnitIncrement(10);
    add(Scroll, BorderLayout.CENTER);
  }

  public void setStartDate() {
    this.startDate = new JDateChooser();
    startDate.setBounds(40, 75, 150, 30);
    startDate.setDateFormatString("yyyy-MM-dd");
    JTextField editor = (JTextField) startDate.getDateEditor();
    editor.setEditable(false);
  }

  public void setEndDate() {
    this.endDate = new JDateChooser();
    endDate.setBounds(200, 75, 150, 30);
    endDate.setDateFormatString("yyyy-MM-dd");
    JTextField editor = (JTextField) endDate.getDateEditor();
    editor.setEditable(false);
  }

  public void setFilterButton() {
    this.filterButton = new JButton("Lọc");
    filterButton.setBounds(360, 75, 80, 30);
    filterButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        Date fromDate = startDate.getDate();
        Date toDate = endDate.getDate();
        checkDate(fromDate, toDate);
        CheckResponsive();
        Scroll.setViewportView(Invoices);
      }
    });
  }

  public void setRemoveFilterButton() {
    this.removeFilterButton = new JButton("Xóa");
    removeFilterButton.setBounds(360, 75, 80, 30);
    removeFilterButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        Invoices.removeAll();
        startDate.setDate(null);
        endDate.setDate(null);
        orderList = new ArrayList<>(OrderBUS.getInstance().getAllModels());
        Collections.reverse(orderList);
        for (OrderModel orderModel : orderList) {
          Invoice invoice = new Invoice(orderModel);
          Invoices.add(invoice);
        }
        CheckResponsive();
        Scroll.setViewportView(Invoices);
      }
    });
  }

  private void checkDate(Date fromDate, Date toDate) {
    try {
      orderList = new ArrayList<>(OrderBUS.getInstance().searchDateToDate(fromDate, toDate));
      Collections.reverse(orderList);
      Invoices.removeAll();
      for (OrderModel orderModel : orderList) {
        Invoice invoice = new Invoice(orderModel);
        Invoices.add(invoice);
      }
      Scroll.setViewportView(Invoices);
    } catch (IllegalArgumentException e) {
      JOptionPane.showMessageDialog(null, e.getMessage(), "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void CheckResponsive() {
    if (orderList.size() > 10)
      Invoices.setLayout(new GridLayout(0, 1));
    else
      Invoices.setLayout(new GridLayout(10, 1));

  }

}
