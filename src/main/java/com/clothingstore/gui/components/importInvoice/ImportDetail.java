package com.clothingstore.gui.components.importInvoice;

import javax.swing.*;

import com.clothingstore.bus.ImportItemsBUS;
import com.clothingstore.gui.components.InvoiceProduct;
import com.clothingstore.gui.employee.invoiceDetail.HeaderInvoice;
import com.clothingstore.models.ImportItemsModel;
import com.clothingstore.models.ImportModel;
import com.clothingstore.models.OrderItemModel;

import java.awt.*;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ImportDetail extends JPanel {

  private String name;
  private String value;
  private ImportModel importModel;
  private List<ImportItemsModel> importItemsList;
  private int productQuantity = 0;


  static DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");

  public ImportDetail(ImportModel importModel) {
    this.importModel = importModel;
    importItemsList = ImportItemsBUS.getInstance().searchModel(String.valueOf(importModel.getId()),
        new String[] { "import_id" });
    initComponents();
  }

  public ImportDetail(String name, String value) {
    this.name = name;
    this.value = value;
  }

  public ArrayList<ImportDetail> getData() {
    for(ImportItemsModel importItemsModel : importItemsList)
      productQuantity += importItemsModel.getQuantity();

    Timestamp timestamp = Timestamp.valueOf(importModel.getImportDate());
    ArrayList<ImportDetail> data = new ArrayList<ImportDetail>() {
      {
        add(new ImportDetail("Mã hóa đơn", "" + importModel.getId()));
        add(new ImportDetail("Ngày tạo", String.valueOf(timestamp)));
        add(new ImportDetail("Số sản phẩm", String.valueOf(productQuantity)));
        add(new ImportDetail("Tổng", "" + decimalFormat.format(importModel.getTotalPrice())));
      }
    };
    return data;
  }

  public void initComponents() {
    NamePanel = new JLabel();
    Info = new JPanel();
    Products = new JPanel();
    Scroll = new JScrollPane();
    Product = new JPanel();
    HeaderProducts = new JPanel();

    mainPanel = new JPanel();
    mainPanel.setLayout(new BorderLayout());

    setLayout(new BorderLayout());
    setBackground(new Color(153, 194, 255));

    Scroll.setViewportView(mainPanel);

    Info.setLayout(new GridLayout(0, 1));

    NamePanel.setText("-- Chi Tiết Hóa Đơn --");
    NamePanel.setHorizontalAlignment(SwingConstants.CENTER);
    NamePanel.setVerticalAlignment(SwingConstants.CENTER);
    NamePanel.setFont(new Font("Segoe UI", 1, 17));
    NamePanel.setPreferredSize(new Dimension(150, 70));

    add(NamePanel, BorderLayout.NORTH);

    for (ImportDetail ImportDetail : getData()) {
      JPanel panel = new JPanel();
      panel.setBackground(Color.WHITE);
      panel.setPreferredSize(new Dimension(60, 60));
      panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
      panel.setLayout(new BorderLayout());

      JLabel Name = new JLabel(ImportDetail.name);
      Name.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
      Name.setFont(new Font("Segoe UI", 1, 14));
      panel.add(Name, BorderLayout.WEST);

      JLabel Value = new JLabel(ImportDetail.value);
      Value.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 19));
      Value.setFont(new Font("Segoe UI", 0, 14));
      panel.add(Value, BorderLayout.EAST);

      Info.add(panel);
    }
    Products.setLayout(new BorderLayout());

    HeaderProducts.setLayout(new BorderLayout());
    HeaderProducts.setPreferredSize(new Dimension(100, 40));
    HeaderProducts.add(HeaderInvoice.getInstance(), BorderLayout.CENTER);

    Product.setLayout(new GridLayout(0, 1));

    int i = 0;
    for (ImportItemsModel importItemsModel : importItemsList) {
      InvoiceProduct product = new InvoiceProduct(importItemsModel, i += 1);
      Product.add(product);
    }
    Products.add(HeaderProducts, BorderLayout.NORTH);
    Products.add(Product, BorderLayout.CENTER);

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(0, 0, 0, 10);

    mainPanel.add(Info, BorderLayout.CENTER);
    mainPanel.add(Products, BorderLayout.SOUTH);
    Scroll.getVerticalScrollBar().setUnitIncrement(10);
    add(Scroll, BorderLayout.CENTER);
  }

  private JPanel mainPanel;
  private JLabel NamePanel;
  private JPanel Info;
  private JPanel Products;
  private JScrollPane Scroll;
  private JPanel Product;
  private JPanel HeaderProducts;
}
