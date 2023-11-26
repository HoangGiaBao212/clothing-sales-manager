package com.clothingstore.gui.employee.invoiceDetail;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.netbeans.lib.awtextra.*;

import com.clothingstore.bus.CustomerBUS;
import com.clothingstore.bus.OrderBUS;
import com.clothingstore.bus.OrderItemBUS;
import com.clothingstore.bus.PaymentBUS;
import com.clothingstore.bus.PointBUS;
import com.clothingstore.bus.PointTransactionBUS;
import com.clothingstore.bus.SizeItemBUS;
import com.clothingstore.gui.components.InvoiceProduct;
import com.clothingstore.gui.employee.Invoice;
import com.clothingstore.models.CustomerModel;
import com.clothingstore.models.OrderItemModel;
import com.clothingstore.models.OrderModel;
import com.clothingstore.models.PaymentModel;
import com.clothingstore.models.PointModel;
import com.clothingstore.models.PointTransactionModel;
import com.clothingstore.models.SizeItemModel;
import services.Authentication;
import services.PDFWriter;
import services.Validation;

public class InvoiceDetail extends JFrame {
  private double totalInvoice = 0;
  private double change = 0;
  private double finalPrice = 0;
  private double point = 0;
  DecimalFormat decimalFormat = new DecimalFormat("0.####################");

  public InvoiceDetail(List<OrderItemModel> orderList) {
    revalidate();
    repaint();
    initComponents(orderList);
    setAlwaysOnTop(true);
    setLocationRelativeTo(null);
    CustomerInfo.setVisible(false);
  }

  private void initComponents(List<OrderItemModel> orderList) {
    GridBagConstraints gridBagConstraints;
    setPreferredSize(new Dimension(800, 600));

    Header = new JPanel();
    NameFrame = new JLabel();
    Content = new JPanel();
    ContentHeader = new JPanel();
    Scroll = new JScrollPane();
    Products = new JPanel();
    Footer = new JPanel();
    CustomerPanel = new JPanel();
    CashCheckBox = new JCheckBox();
    CreditCheckBox = new JCheckBox();
    RegularCus = new JCheckBox();
    WalkInCus = new JCheckBox();
    CustomerInfo = new JPanel();
    NameText = new JLabel();
    Point = new JCheckBox();
    PhoneText = new JLabel();
    Name = new JTextField();
    Phone = new JTextField();
    UsePoint = new JTextField();
    Buttons = new JPanel();
    ButtonExit = new JButton();
    ButtonPay = new JButton();
    PayPanel = new JPanel();
    TotalText = new JLabel();
    CusPayingText = new JLabel();
    ChangeText = new JLabel();
    Change = new JTextField();
    TotalInvoiceText = new JLabel();
    PointText = new JLabel();
    Total = new JTextField();
    CusPaying = new JTextField();
    TotalInvoice = new JTextField();
    Discount = new JTextField();
    Slogan = new JLabel("Respecting your customers is respecting yourself");

    Header.setLayout(new AbsoluteLayout());

    NameFrame.setFont(new Font("Segoe UI", 1, 24));
    NameFrame.setText("Chi tiết hóa đơn");

    Content.setLayout(new BorderLayout());

    ContentHeader.setBorder(BorderFactory.createEtchedBorder());
    ContentHeader.setPreferredSize(new Dimension(800, 40));
    ContentHeader.setLayout(new BorderLayout());

    Products.setBackground(new Color(255, 255, 255));
    Products.setLayout(new GridLayout(0, 1));

    int sr = 1;
    for (OrderItemModel orderItemModel : orderList) {
      InvoiceProduct product = new InvoiceProduct(orderItemModel, sr);
      sr++;
      product.setBackground(Color.WHITE);
      Products.add(product);
    }

    Scroll.setViewportView(Products);

    Footer.setLayout(new BorderLayout());

    CustomerPanel.setLayout(new AbsoluteLayout());

    RegularCus.setFont(new Font("Segoe UI", 1, 15));
    RegularCus.setForeground(new Color(0, 102, 102));
    RegularCus.setText("Khách hàng thân quen");
    RegularCus.addActionListener(RegularCusOnCheck);

    WalkInCus.setFont(new Font("Segoe UI", 1, 15));
    WalkInCus.setSelected(true);
    WalkInCus.setText("Khách vãng lai");
    WalkInCus.addActionListener(WalkInCusOnCheck);

    CustomerInfo.setLayout(new AbsoluteLayout());

    NameText.setFont(new Font("Segoe UI", 3, 14));
    NameText.setText("Tên:");
    Point.setFont(new Font("Segoe UI", 0, 14));
    Point.setForeground(new Color(255, 102, 0));
    Point.setText("0 Point");
    Point.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

        if (Point.isSelected()) {
          Discount.setText("" + point);
          Total.setText(totalInvoice - point + "");
        } else {
          revalidate();
          repaint();
          Discount.setText("" + 0);
          Total.setText(decimalFormat.format(totalInvoice) + "");
        }
      }
    });
    PhoneText.setFont(new Font("Segoe UI", 3, 14));
    PhoneText.setText("SĐT: ");

    Name.setFont(new Font("Segoe UI", 0, 14));
    Name.setHorizontalAlignment(JTextField.RIGHT);
    Name.setEditable(false);

    Phone.setFont(new Font("Segoe UI", 0, 14));
    Phone.addFocusListener(LostFocusPhone);
    Phone.setHorizontalAlignment(JTextField.RIGHT);

    UsePoint.setFont(new Font("Segoe UI", 0, 14));
    UsePoint.setHorizontalAlignment(JTextField.RIGHT);
    UsePoint.addFocusListener(LostFocusUsePoint);
    UsePoint.setEditable(false);
    Slogan.setFont(new Font("Segoe UI", 2, 13));

    Buttons.setBorder(BorderFactory.createEtchedBorder());
    Buttons.setBackground(new Color(51, 51, 204));
    Buttons.setLayout(new GridBagLayout());

    ButtonExit.setBackground(new Color(153, 255, 255));
    ButtonExit.setFont(new Font("Segoe UI", 1, 14));
    ButtonExit.setText("Thoát");
    ButtonExit.setPreferredSize(new Dimension(80, 30));
    ButtonExit.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        dispose();
      }
    });
    gridBagConstraints = new GridBagConstraints();
    gridBagConstraints.insets = new Insets(10, 10, 10, 40);

    ButtonPay.setBackground(new Color(153, 255, 255));
    ButtonPay.setFont(new Font("Segoe UI", 1, 14));
    ButtonPay.setText("Thanh toán");
    ButtonPay.setPreferredSize(new Dimension(80, 30));

    PayPanel.setPreferredSize(new Dimension(280, 230));
    PayPanel.setLayout(new AbsoluteLayout());

    TotalText.setFont(new Font("Segoe UI", 3, 14));
    TotalText.setForeground(new Color(255, 0, 51));
    TotalText.setHorizontalAlignment(SwingConstants.RIGHT);
    TotalText.setText("Tổng:");

    TotalInvoiceText.setFont(new Font("Segoe UI", 3, 14));
    TotalInvoiceText.setForeground(new Color(51, 51, 255));
    TotalInvoiceText.setHorizontalAlignment(SwingConstants.RIGHT);
    TotalInvoiceText.setText("Tổng hóa đơn:");

    PointText.setFont(new Font("Segoe UI", 3, 14));
    PointText.setForeground(new Color(51, 51, 255));
    PointText.setHorizontalAlignment(SwingConstants.RIGHT);
    PointText.setText("Điểm dùng:");

    Total.setFont(new Font("Segoe UI", 0, 15));
    Total.setForeground(new Color(255, 51, 51));
    Total.setEditable(false);

    TotalInvoice.setFont(new Font("Segoe UI", 0, 14));
    for (OrderItemModel orderItemModel : orderList) {
      totalInvoice = totalInvoice + orderItemModel.getPrice() * orderItemModel.getQuantity();
    }
    TotalInvoice.setText(decimalFormat.format(totalInvoice));
    TotalInvoice.setEditable(false);

    Discount.setFont(new Font("Segoe UI", 0, 14));
    Discount.setForeground(new Color(153, 0, 51));
    Discount.setText("");
    Discount.setEditable(false);

    CashCheckBox.setFont(new Font("Segoe UI", 1, 15));
    CashCheckBox.setForeground(new Color(0, 102, 102));
    CashCheckBox.setSelected(true);
    CashCheckBox.setText("Tiền mặt");
    CashCheckBox.addActionListener(CashOnCheck);

    CreditCheckBox.setFont(new Font("Segoe UI", 1, 15));
    CreditCheckBox.setForeground(new Color(0, 102, 102));
    CreditCheckBox.setText("Trực tuyến");
    CreditCheckBox.addActionListener(CreditOnCheck);

    CusPayingText.setFont(new Font("Segoe UI", 3, 14));
    CusPayingText.setForeground(new Color(51, 51, 255));
    CusPayingText.setHorizontalAlignment(SwingConstants.RIGHT);
    CusPayingText.setText("Tiền nhận: ");

    CusPaying.setFont(new Font("Segoe UI", 0, 14));
    CusPaying.setForeground(new Color(255, 51, 255));
    CusPaying.addFocusListener(LostFocusCustomerPay);

    ChangeText.setFont(new Font("Segoe UI", 3, 14));
    ChangeText.setForeground(new Color(51, 51, 255));
    ChangeText.setHorizontalAlignment(SwingConstants.RIGHT);
    ChangeText.setText("Tiền thừa: ");

    Change.setFont(new Font("Segoe UI", 0, 14));
    Change.setForeground(new Color(255, 51, 255));
    Change.setEditable(false);

    if (!Discount.getText().isBlank() || !Discount.getText().isEmpty()) {
      finalPrice = totalInvoice - Double.parseDouble(Discount.getText());
      Total.setText(decimalFormat.format(finalPrice));
    } else {
      finalPrice = totalInvoice;
      Total.setText(decimalFormat.format(finalPrice));
    }

    ButtonPay.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        boolean isRegularCustomer = RegularCus.isSelected();
        boolean isWalkinCustomer = WalkInCus.isSelected();
        boolean isCreditSelected = CreditCheckBox.isSelected();
        boolean isCashSelected = CashCheckBox.isSelected();
        if (CusPaying.getText().isBlank()) {
          showMessageDialog("Hãy nhập số tiền khách hàng đưa!");
          return;
        }
        if (isRegularCustomer && Phone.getText().isBlank()) {
          showMessageDialog("Số điện thoại bạn không thể bỏ trống!");
          return;
        } else if (isRegularCustomer && ((Phone.getText().isBlank() && Name.getText().isBlank())
            || (!Phone.getText().isBlank() && Name.getText().isBlank()))) {
          showMessageDialog("Tên khách hàng không thể bỏ trống");
          return;
        }

        if ((isWalkinCustomer && isCashSelected) || (isWalkinCustomer && isCreditSelected)) {
          processCashPayment(orderList, isRegularCustomer, isWalkinCustomer);
        } else if (isCashSelected) {
          processCashPayment(orderList, isRegularCustomer, isWalkinCustomer);
        } else if (isCreditSelected) {
          // Handle credit payment
          processCashPayment(orderList, isRegularCustomer, isWalkinCustomer);
        }

        clearCart(orderList);
      }

      private void processCashPayment(List<OrderItemModel> orderList, boolean isRegularCustomer,
          boolean isWalkinCustomer) {
        if (change >= 0) {
          // Payment successful
          handleSuccessfulPayment(orderList, isRegularCustomer, isWalkinCustomer);
        } else {
          // Payment amount insufficient
          showInsufficientPaymentError();
        }
      }

      private void handleSuccessfulPayment(List<OrderItemModel> orderList, boolean isRegularCustomer,
          boolean isWalkinCustomer) {
        JFrame jf = new JFrame();
        jf.setAlwaysOnTop(true);
        JOptionPane.showMessageDialog(jf, "Thanh toán thành công");
        dispose();

        int idCustomer = 1;
        if (isRegularCustomer) {
          idCustomer = getCustomerId();
        } else if (isWalkinCustomer) {
          idCustomer = 1;
        }
        createOrderAndPayment(orderList, idCustomer);
        updateSizeItemsAndPoints(orderList, idCustomer);
        exportReceiptToPDF(orderList, idCustomer);
        disposeWindow();
      }

      private int getCustomerId() {
        CustomerBUS.getInstance().refreshData();
        List<CustomerModel> customerList = new ArrayList<>(CustomerBUS.getInstance().getAllModels());
        for (int i = 0; i < customerList.size(); i++) {
          if (String.valueOf(Phone.getText()).equals(customerList.get(i).getPhone())) {
            return customerList.get(i).getId();
          } else {
            continue;
          }
        }
        return 1;
      }

      private void createOrderAndPayment(List<OrderItemModel> orderList, int idCustomer) {
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        OrderModel orderModel = new OrderModel(0, idCustomer, Authentication.getCurrentUser().getId(),
            currentTime, totalInvoice);
        OrderBUS.getInstance().addModel(orderModel);
        OrderBUS.getInstance().refreshData();
        revalidate();
        List<OrderModel> orderModels = OrderBUS.getInstance().getAllModels();
        boolean isCreditSelected = CreditCheckBox.isSelected();
        if (isCreditSelected) {
          PaymentModel paymentModel = new PaymentModel(0, orderModels.get(orderModels.size() - 1).getId(), 2,
              currentTime,
              orderModel.getTotalPrice());
          PaymentBUS.getInstance().addModel(paymentModel);
        } else {
          PaymentModel paymentModel = new PaymentModel(0, orderModels.get(orderModels.size() - 1).getId(), 1,
              currentTime,
              orderModel.getTotalPrice());
          PaymentBUS.getInstance().addModel(paymentModel);
        }
        for (OrderItemModel orderItemModel : orderList) {
          orderItemModel.setOrderId(orderModels.get(orderModels.size() - 1).getId());
          OrderItemBUS.getInstance().addModel(orderItemModel);
        }
      }

      private void updateSizeItemsAndPoints(List<OrderItemModel> orderList, int idCustomer) {
        updateSizeItemQuantity(orderList);
        // Update points for customer if available
        if (idCustomer != 1)
          updateCustomerPoints(idCustomer);
      }

      private void updateSizeItemQuantity(List<OrderItemModel> orderList) {
        List<SizeItemModel> sizeItemModels = SizeItemBUS.getInstance().getAllModels();

        for (OrderItemModel orderItemModel : orderList) {
          int orderProductId = orderItemModel.getProductId();
          int orderSizeId = orderItemModel.getSizeId();
          int orderQuantity = orderItemModel.getQuantity();

          // Find the matching SizeItemModel for the order item
          SizeItemModel matchingSizeItem = sizeItemModels.stream()
              .filter(sizeItem -> sizeItem.getProductId() == orderProductId &&
                  sizeItem.getSizeId() == orderSizeId)
              .findFirst()
              .orElse(null);

          if (matchingSizeItem != null) {
            int updatedQuantity = matchingSizeItem.getQuantity() - orderQuantity;
            matchingSizeItem.setQuantity(updatedQuantity);

            // Update the SizeItemModel in the database
            SizeItemBUS.getInstance().updateModel(matchingSizeItem);
          }
        }
      }

      private void updateCustomerPoints(int idCustomer) {
        CustomerBUS.getInstance().refreshData();
        PointBUS.getInstance().refreshData();
        PointTransactionBUS.getInstance().refreshData();

        String str = Discount.getText();
        if (str.isEmpty() || str.isBlank()) {
          str = "0";
        }
        double number = Double.parseDouble(str);
        int integerValue = (int) number;
        revalidate();
        List<PointModel> pointModels = new ArrayList<>(PointBUS.getInstance().getAllModels());
        for (int i = 0; i < pointModels.size(); i++) {
          if (pointModels.get(i).getCustomerId() == idCustomer) {
            if (String.valueOf(Discount.getText()) != "0" || String.valueOf(Discount.getText()) != "0.0") {
              pointModels.get(i)
                  .setPointsUsed(pointModels.get(i).getPointsUsed() + integerValue);
              pointModels.get(i).setPointsEarned(0);
            }
            pointModels.get(i).setPointsEarned(pointModels.get(i).getPointsEarned() + (int) totalInvoice / 100);

            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            PointTransactionModel pointTransactionModel = new PointTransactionModel(0, idCustomer, currentTime,
                pointModels.get(i).getPointsEarned() + (int) totalInvoice / 100,
                integerValue);
            PointTransactionBUS.getInstance().addModel(pointTransactionModel);
            PointBUS.getInstance().updateModel(pointModels.get(i));
            revalidate();
          }
        }

      }

      private void exportReceiptToPDF(List<OrderItemModel> orderList, int idCustomer) {
        JFrame jf = new JFrame();
        jf.setAlwaysOnTop(true);
        int choice = JOptionPane.showConfirmDialog(jf, "Bạn có muốn xuất hóa đơn không?");
        if (choice == JOptionPane.YES_OPTION) {
          JFrame jf1 = new JFrame();
          jf1.setAlwaysOnTop(true);
          JFileChooser fileChooser = new JFileChooser();
          jf1.add(fileChooser);
          fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
          FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF Files", "pdf");
          fileChooser.setFileFilter(filter);
          int result = fileChooser.showSaveDialog(null);
          if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String filePath = selectedFile.getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".pdf")) {
              filePath += ".pdf";
            }
            OrderBUS.getInstance().refreshData();
            List<OrderModel> orderModels = OrderBUS.getInstance().getAllModels();
            OrderModel orderModel = orderModels.get(orderModels.size() - 1);
            String totalText = Total.getText().toString();
            double totalValue = Double.parseDouble(totalText);
            PDFWriter.getInstance().exportReceiptToPDF(orderModel, filePath, totalValue);
          }
        } else {
          return;
        }
      }

      private void disposeWindow() {
        dispose();
        clearCart(orderList);
      }

      private void showInsufficientPaymentError() {
        showMessageDialog(
            "Số tiền khách trả ít hơn số tiền ở hóa đơn. Vui lòng kiểm tra lại.");
      }

      private void showMessageDialog(String message) {
        JFrame jf = new JFrame();
        jf.setAlwaysOnTop(true);
        JOptionPane.showMessageDialog(jf, message);
      }

      private void clearCart(List<OrderItemModel> orderList) {
        // Clear the cart by removing all items
        orderList.clear();
        Invoice.getInstance().clearAllItemsInCart();
        revalidate();
        repaint();
      }
    });

    Header.add(NameFrame, new AbsoluteConstraints(10, 0, 410, 30));
    getContentPane().add(Header, BorderLayout.PAGE_START);

    ContentHeader.add(HeaderInvoice.getInstance(), BorderLayout.CENTER);
    Content.add(ContentHeader, BorderLayout.PAGE_START);
    Content.add(Scroll, BorderLayout.CENTER);
    getContentPane().add(Content, BorderLayout.CENTER);

    CustomerPanel.add(RegularCus, new AbsoluteConstraints(210, 10, 190, -1));
    CustomerPanel.add(WalkInCus, new AbsoluteConstraints(30, 10, 170, -1));

    CustomerInfo.add(PhoneText, new AbsoluteConstraints(20, 20, -1, -1));
    CustomerInfo.add(Phone, new AbsoluteConstraints(80, 20, 140, 25));
    CustomerInfo.add(NameText, new AbsoluteConstraints(240, 20, -1, -1));
    CustomerInfo.add(Name, new AbsoluteConstraints(300, 20, 170, 25));
    CustomerInfo.add(Point, new AbsoluteConstraints(70, 50, -1, 20));
    CustomerInfo.add(UsePoint, new AbsoluteConstraints(190, 50, 150, 20));
    CustomerPanel.add(CustomerInfo, new AbsoluteConstraints(10, 40, 500, 120));

    CustomerPanel.add(Slogan, new AbsoluteConstraints(60, 0, 500, 120));

    Footer.add(CustomerPanel, BorderLayout.CENTER);

    Buttons.add(ButtonExit, gridBagConstraints);
    Buttons.add(ButtonPay, new GridBagConstraints());
    Footer.add(Buttons, BorderLayout.PAGE_END);

    PayPanel.add(TotalInvoiceText, new AbsoluteConstraints(10, 10, 130, 30));
    PayPanel.add(TotalInvoice, new AbsoluteConstraints(160, 10, 110, 30));
    PayPanel.add(PointText, new AbsoluteConstraints(10, 50, 130, 30));
    PayPanel.add(Discount, new AbsoluteConstraints(160, 50, 110, 30));
    PayPanel.add(TotalText, new AbsoluteConstraints(10, 90, 130, 30));
    PayPanel.add(Total, new AbsoluteConstraints(160, 90, 110, 30));
    PayPanel.add(CashCheckBox, new AbsoluteConstraints(55, 130, 90, 30));
    PayPanel.add(CreditCheckBox, new AbsoluteConstraints(170, 130, 110, 30));
    PayPanel.add(CusPayingText, new AbsoluteConstraints(0, 160, 130, 30));
    PayPanel.add(CusPaying, new AbsoluteConstraints(140, 160, 110, 30));
    PayPanel.add(ChangeText, new AbsoluteConstraints(0, 195, 130, 30));
    PayPanel.add(Change, new AbsoluteConstraints(140, 195, 110, 30));
    Footer.add(PayPanel, BorderLayout.EAST);

    getContentPane().add(Footer, BorderLayout.PAGE_END);
    pack();
  }

  private JButton ButtonExit;
  private JButton ButtonPay;
  private JPanel Buttons;
  private JPanel Content;
  private JPanel CustomerInfo;
  private JPanel CustomerPanel;
  private JTextField Discount;
  private JPanel Footer;
  private JPanel Products;
  private JPanel ContentHeader;
  private JPanel Header;
  private JTextField Name;
  private JLabel NameFrame;
  private JLabel NameText;
  private JPanel PayPanel;
  private JTextField Phone;
  private JLabel PhoneText;
  private JCheckBox Point;
  private JLabel PointText;
  private JLabel TotalInvoiceText;
  private JCheckBox CashCheckBox;
  private JCheckBox CreditCheckBox;
  private JTextField CusPaying;
  private JLabel CusPayingText;
  private JLabel ChangeText;
  private JTextField Change;
  private JCheckBox RegularCus;
  private JScrollPane Scroll;
  private JTextField Total;
  private JTextField TotalInvoice;
  private JLabel TotalText;
  private JTextField UsePoint;
  private JCheckBox WalkInCus;
  private JLabel Slogan;

  public ActionListener WalkInCusOnCheck = new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent arg0) {

      if (WalkInCus.isSelected()) {
        Point.setSelected(false);
        Discount.setText("" + 0);
        Total.setText(decimalFormat.format(totalInvoice));
        RegularCus.setSelected(false);
        UsePoint.setVisible(false);
        CustomerInfo.setVisible(false);
        Slogan.setVisible(true);
      }
    }
  };

  public ActionListener RegularCusOnCheck = new ActionListener() {

    @Override
    public void actionPerformed(ActionEvent arg0) {
      if (RegularCus.isSelected()) {
        WalkInCus.setSelected(false);
        CustomerInfo.setVisible(true);
        Slogan.setVisible(false);
      }
    }

  };

  public ActionListener CashOnCheck = new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent arg0) {
      CreditCheckBox.setSelected(false);
      PayPanel.setPreferredSize(new Dimension(280, 230));
      CusPayingText.setVisible(true);
      CusPaying.setVisible(true);
      ChangeText.setVisible(true);
      Change.setVisible(true);
      PayPanel.revalidate();
      PayPanel.repaint();
    }

  };
  public ActionListener CreditOnCheck = new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent arg0) {
      CashCheckBox.setSelected(false);
      PayPanel.setPreferredSize(new Dimension(280, 170));
      CusPayingText.setVisible(false);
      CusPaying.setVisible(false);
      ChangeText.setVisible(false);
      Change.setVisible(false);
      PayPanel.revalidate();
      PayPanel.repaint();
    }

  };
  public FocusListener LostFocusPhone = new FocusListener() {

    @Override
    public void focusGained(FocusEvent e) {
    }

    @Override
    public void focusLost(FocusEvent e) {
      if (Phone.getText().equals("0000000000") || !Validation.isValidPhoneNumber(Phone.getText().toString())
          || Phone.getText().isEmpty() || Phone.getText().isBlank()) {
        JFrame jf = new JFrame();
        jf.setAlwaysOnTop(true);
        JOptionPane.showMessageDialog(jf, "Số điện thoại không hợp lệ, vui lòng thử lại");
        Phone.setText("");
        return;
      }
      boolean isPointCheckboxSelected = Point.isSelected();
      CustomerBUS.getInstance().refreshData();
      CustomerModel customerModel = null;
      List<CustomerModel> customerList = new ArrayList<>(CustomerBUS.getInstance().getAllModels());
      for (CustomerModel customer : customerList) {
        if (String.valueOf(Phone.getText()).equals(customer.getPhone())) {
          customerModel = customer;
        } else {
          continue;
        }
      }
      if (customerModel != null) {
        Name.setText(customerModel.getCustomerName());
        point = PointBUS.getInstance()
            .searchModel(String.valueOf(customerModel.getId()), new String[] { "customer_id" })
            .get(0).getPointsEarned();
        Point.setText(point + " Point");
        if (isPointCheckboxSelected) {
          Discount.setText("" + PointBUS.getInstance()
              .searchModel(String.valueOf(customerModel.getId()), new String[] { "id" }).get(0)
              .getPointsEarned());
        }
        revalidate();
        repaint();
      } else {

        JFrame fr = new JFrame();
        fr.setAlwaysOnTop(true);
        int choice = JOptionPane.showConfirmDialog(fr,
            "Số điện thoại không tìm thấy. Bạn có muốn thêm vào khách hàng mới không?");
        if (choice == JOptionPane.YES_OPTION) {
          Name.setEditable(true);
          Name.addFocusListener(nameFocusListener);
          revalidate();
          repaint();
        } else if (choice == JOptionPane.NO_OPTION || choice == JOptionPane.CANCEL_OPTION) {
          Phone.setText("");
          WalkInCus.setSelected(true);
          RegularCus.setSelected(false);
          UsePoint.setVisible(false);
          CustomerInfo.setVisible(false);
          Slogan.setVisible(true);
        }
      }
    }
  };

  public FocusListener LostFocusCustomerPay = new FocusListener() {
    @Override
    public void focusGained(FocusEvent e) {
    }

    @Override
    public void focusLost(FocusEvent e) {
      if (!CusPaying.getText().isBlank() && !CusPaying.getText().isEmpty()) {
        double cusPaying = Double.parseDouble(CusPaying.getText());
        double totalReceipt = Double.parseDouble(Total.getText());
        change = cusPaying - totalReceipt;
        Change.setText(decimalFormat.format(change));
      }
    }
  };

  public FocusListener LostFocusUsePoint = new FocusListener() {
    @Override
    public void focusGained(FocusEvent e) {
    }

    @Override
    public void focusLost(FocusEvent e) {
      if (!Discount.getText().isEmpty() && !Discount.getText().isBlank()) {
        String point = Point.getText();
        String[] words = point.split(" ");
        if (Double.parseDouble(Discount.getText()) > Double.parseDouble(words[0])) {
          JOptionPane.showMessageDialog(null,
              "Điểm tích lũy bạn vừa nhập không thể lớn hơn điểm tích lũy của khách hàng.");
          Discount.setText("0");
        }
      }
    }
  };

  public FocusListener nameFocusListener = new FocusListener() {

    @Override
    public void focusGained(FocusEvent e) {
    }

    @Override
    public void focusLost(FocusEvent e) {
      if (Name.getText().isBlank()) {
        JFrame jf = new JFrame();
        jf.setAlwaysOnTop(true);
        JOptionPane.showMessageDialog(jf, "Tên khách hàng không thể bỏ trống");
        return;
      }
      CustomerModel customer = new CustomerModel(0, Name.getText().toString(), Phone.getText(), null);
      CustomerBUS.getInstance().addModel(customer);
      JFrame fr1 = new JFrame();
      fr1.setAlwaysOnTop(true);
      JOptionPane.showMessageDialog(fr1, "Đã thêm 1 khách hàng mới thành công.");
      CustomerBUS.getInstance().refreshData();
      List<CustomerModel> customerModels = CustomerBUS.getInstance().getAllModels();
      PointModel pointModel = new PointModel(0, customerModels.get(customerModels.size() - 1).getId(), 0, 0);
      PointBUS.getInstance().addModel(pointModel);
      PointBUS.getInstance().refreshData();
      revalidate();
      repaint();
    }
  };

}