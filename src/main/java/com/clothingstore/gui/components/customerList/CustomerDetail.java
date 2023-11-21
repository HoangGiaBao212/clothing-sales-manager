package com.clothingstore.gui.components.customerList;

import javax.swing.*;

import com.clothingstore.bus.OrderBUS;
import com.clothingstore.bus.PointBUS;
import com.clothingstore.bus.PointTransactionBUS;
import com.clothingstore.models.CustomerModel;
import com.clothingstore.models.OrderModel;
import com.clothingstore.models.PointModel;
import com.clothingstore.models.PointTransactionModel;

import java.awt.*;
import java.util.*;
import java.util.List;


public class CustomerDetail extends JPanel {

    private String name;
    private String value;
    CustomerModel customerModel;
    PointModel pointModel;

    public CustomerDetail(CustomerModel customerModel){
        this.customerModel= customerModel;
        this.pointModel = PointBUS.getInstance().searchModel(String.valueOf(customerModel.getId()), new String[]{"customer_id"}).get(0);
        initComponents();
    }
    
    public CustomerDetail(String name, String value) {
        this.name = name;
        this.value = value;
    }
    
    public  ArrayList<CustomerDetail> getData() {

        PointTransactionModel point = PointTransactionBUS.getInstance().searchModel(String.valueOf(customerModel.getId()), new String[]{"customer_id"}).get(0);
        ArrayList<CustomerDetail> data = new ArrayList<CustomerDetail>() {{
            add(new CustomerDetail("Mã khách hàng",String.valueOf(customerModel.getId())));
            add(new CustomerDetail("Tên khách hàng", customerModel.getCustomerName()));
            add(new CustomerDetail("Số điện thoại", customerModel.getPhone()));
            add(new CustomerDetail("Email",customerModel.getEmail() ));
            add(new CustomerDetail("Điểm tích lũy", String.valueOf(point.getPointsEarned())));
            add(new CustomerDetail("Điểm đã dùng", String.valueOf(point.getPointsUsed())));
            add(new CustomerDetail("Tổng số hóa đơn", String.valueOf(OrderBUS.getInstance().searchModel(String.valueOf(customerModel.getId()), new String[]{"customer_id"}).size())));
        }};
        return data;
    }

    public void initComponents(){
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
        Scroll.getVerticalScrollBar().setUnitIncrement(30);

        Info.setLayout(new GridLayout(0, 1));

        NamePanel.setText("-- Chi tiết --");
        NamePanel.setHorizontalAlignment(SwingConstants.CENTER);
        NamePanel.setVerticalAlignment(SwingConstants.CENTER);
        NamePanel.setFont(new Font("Segoe UI", 1, 17));
        NamePanel.setPreferredSize(new Dimension(150,70));

        add(NamePanel,BorderLayout.NORTH);

        for(CustomerDetail CustomerDetail : getData()){
            JPanel panel = new JPanel();
            panel.setBackground(Color.WHITE);
            panel.setPreferredSize(new Dimension(60, 60));
            panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
            panel.setLayout(new BorderLayout());

            JLabel Name = new JLabel(CustomerDetail.name);
            Name.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
            Name.setFont(new Font("Segoe UI", 1, 15));
            panel.add(Name, BorderLayout.WEST);

            JLabel Value = new JLabel(CustomerDetail.value);
            Value.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 19));
            Value.setFont(new Font("Segoe UI", 0, 15));
            panel.add(Value, BorderLayout.EAST);

            Info.add(panel);
        }
        Products.setLayout(new BorderLayout());

        HeaderProducts.setLayout(new BorderLayout());
        HeaderProducts.setPreferredSize(new Dimension(100,40));
        Invoice headerInvoice = new Invoice();
        HeaderProducts.add(headerInvoice, BorderLayout.CENTER);

        List<OrderModel> listOrder = OrderBUS.getInstance().searchModel(String.valueOf(customerModel.getId()), new String[]{"customer_id"});
        Product.setLayout(new GridLayout(listOrder.size(),1));
        if(!listOrder.isEmpty()){
            for(OrderModel orderModel : listOrder){
                Invoice invoice = new Invoice(orderModel);
                Product.add(invoice);
            }
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
    private JLabel NamePanel;
    private JPanel Info;
    private JPanel Products;
    private JScrollPane Scroll;
    private JPanel Product;
    private JPanel HeaderProducts;
}
