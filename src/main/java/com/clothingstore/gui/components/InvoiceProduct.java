package com.clothingstore.gui.components;

import javax.swing.*;
import org.netbeans.lib.awtextra.*;

import com.clothingstore.bus.OrderBUS;
import com.clothingstore.bus.OrderItemBUS;
import com.clothingstore.bus.ProductBUS;
import com.clothingstore.bus.SizeBUS;
import com.clothingstore.bus.SizeItemBUS;
import com.clothingstore.models.ImportItemsModel;
import com.clothingstore.models.OrderItemModel;
import com.clothingstore.models.OrderModel;
import com.clothingstore.models.ProductModel;
import com.clothingstore.models.SizeItemModel;
import com.clothingstore.models.SizeModel;

import java.awt.*;
import java.text.DecimalFormat;

public class InvoiceProduct extends JPanel {
  private ProductModel productModel;

  DecimalFormat decimalFormat = new DecimalFormat("###,###");
  String[] data;

  public InvoiceProduct(ImportItemsModel importItemsModel, int i) {
    productModel = ProductBUS.getInstance().getModelById(importItemsModel.getProduct_id());
    SizeModel sizeModel = SizeBUS.getInstance().getModelById(importItemsModel.getSize_id());
    data = new String[] {
        productModel.getName(),
        String.valueOf(i),
        String.valueOf(importItemsModel.getQuantity()),
        String.valueOf(sizeModel.getSize()),
        decimalFormat.format(importItemsModel.getPrice()),
        decimalFormat.format(importItemsModel.getPrice() * importItemsModel.getQuantity()),
    };
    initComponents(data);
  }

  public InvoiceProduct(OrderModel orderModel, OrderItemModel orderItemModel, int i) {
    productModel = ProductBUS.getInstance().getModelById(orderItemModel.getProductId());

    orderModel = OrderBUS.getInstance().getModelById(orderModel.getId());
    orderItemModel = OrderItemBUS.getInstance().getModelById(orderItemModel.getId());

    java.util.List<SizeItemModel> sizeItemModel = SizeItemBUS.getInstance()
        .searchModel(String.valueOf(orderItemModel.getProductId()), new String[] { "product_id" });
    SizeModel sizeModel = SizeBUS.getInstance().getModelById(sizeItemModel.get(0).getSizeId());
    com.clothingstore.gui.employee.InvoiceProduct invoiceProduct = new com.clothingstore.gui.employee.InvoiceProduct(
        productModel, sizeModel.getId(), orderItemModel.getQuantity());
    data = new String[] {
        String.valueOf(productModel.getName()),
        String.valueOf(i),
        String.valueOf(orderItemModel.getQuantity()),
        String.valueOf(sizeModel.getSize()),
        decimalFormat.format(productModel.getPrice() * invoiceProduct.getQuantity()),
        decimalFormat.format(invoiceProduct.getQuantity())
    };
    initComponents(data);
  }

  public InvoiceProduct(OrderItemModel orderItemModel, int i) {
    productModel = ProductBUS.getInstance().getModelById(orderItemModel.getProductId());
    java.util.List<SizeItemModel> sizeItemModel = SizeItemBUS.getInstance()
        .searchModel(String.valueOf(orderItemModel.getProductId()), new String[] { "product_id" });
    SizeModel sizeModel = SizeBUS.getInstance().getModelById(sizeItemModel.get(0).getSizeId());

    data = new String[] {
        String.valueOf(productModel.getName()),
        String.valueOf(i),
        String.valueOf(orderItemModel.getQuantity()),
        String.valueOf(sizeModel.getSize()),
        String.valueOf(decimalFormat.format(productModel.getPrice() * orderItemModel.getQuantity())),
        String.valueOf(orderItemModel.getQuantity())
    };
    initComponents(data);
  }

  public void initComponents(String[] data) {
    Header = new JPanel();
    Sr = new JLabel();
    Name = new JLabel();
    Panel1 = new JPanel();
    QuantityText = new JLabel();
    Panel2 = new JPanel();
    SizeText = new JLabel();
    Panel3 = new JPanel();
    PriceText = new JLabel();
    Panel4 = new JPanel();
    AmountText = new JLabel();
    Detail = new JPanel();

    setLayout(new BorderLayout(5, 5));
    setBackground(Color.WHITE);

    Header.setLayout(new AbsoluteLayout());
    Header.setBackground(Color.WHITE);

    Name.setText(data[0]);
    Name.setForeground(new Color(26, 101, 101));
    Name.setFont(new Font("Segoe UI", 3, 15));

    Sr.setText(data[1]);
    Sr.setFont(new Font("Segoe UI", 0, 14));

    Header.add(Sr, new AbsoluteConstraints(20, 10, 350, 20));
    Header.add(Name, new AbsoluteConstraints(50, 10, 450, 20));

    add(Header, BorderLayout.NORTH);

    Panel1.setRequestFocusEnabled(false);
    Panel1.setBackground(Color.WHITE);
    Panel1.setLayout(new BorderLayout());

    QuantityText.setFont(new Font("Segoe UI", 3, 13));
    QuantityText.setHorizontalAlignment(SwingConstants.CENTER);
    QuantityText.setText(data[2]);
    Panel1.add(QuantityText, BorderLayout.CENTER);

    Detail.add(Panel1);

    Panel2.setRequestFocusEnabled(false);
    Panel2.setLayout(new BorderLayout());
    Panel2.setBackground(Color.WHITE);

    SizeText.setFont(new Font("Segoe UI", 3, 13));
    SizeText.setHorizontalAlignment(SwingConstants.CENTER);
    SizeText.setText(data[3]);
    Panel2.add(SizeText, BorderLayout.CENTER);

    Detail.add(Panel2);

    Panel3.setRequestFocusEnabled(false);
    Panel3.setLayout(new BorderLayout());
    Panel3.setBackground(Color.WHITE);

    PriceText.setFont(new Font("Segoe UI", 3, 13));
    PriceText.setHorizontalAlignment(SwingConstants.CENTER);
    PriceText.setText(data[4]);
    Panel3.add(PriceText, BorderLayout.CENTER);

    Detail.add(Panel3);

    Panel4.setRequestFocusEnabled(false);
    Panel4.setLayout(new BorderLayout());
    Panel4.setBackground(Color.WHITE);

    AmountText.setFont(new Font("Segoe UI", 3, 13));
    AmountText.setHorizontalAlignment(SwingConstants.CENTER);
    AmountText.setText(data[5]);
    Panel4.add(AmountText, BorderLayout.CENTER);

    Detail.add(Panel4);

    Detail.setLayout(new GridLayout(1, 0, 5, 0));
    Detail.setBackground(Color.WHITE);
    add(Detail, BorderLayout.CENTER);
  }

  private JPanel Header;
  private JLabel Sr;
  private JLabel Name;
  private JLabel AmountText;
  private JPanel Panel1;
  private JPanel Panel2;
  private JPanel Panel3;
  private JPanel Panel4;
  private JLabel PriceText;
  private JLabel QuantityText;
  private JLabel SizeText;
  private JPanel Detail;
}
