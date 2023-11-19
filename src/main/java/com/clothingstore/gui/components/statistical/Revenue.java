package com.clothingstore.gui.components.statistical;

import java.awt.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import javax.swing.*;

import com.clothingstore.bus.OrderBUS;
import com.clothingstore.bus.OrderItemBUS;
import com.clothingstore.bus.PointTransactionBUS;
import com.clothingstore.gui.admin.dashboard.Card;
import com.clothingstore.gui.components.customerList.Invoice;
import com.clothingstore.models.OrderItemModel;
import com.clothingstore.models.OrderModel;
import com.clothingstore.models.PointTransactionModel;

public class Revenue extends JPanel {
    private static Revenue instance;

    public static Revenue getInstance() {
        if (instance == null) {
            instance = new Revenue();
        }
        return instance;
    }

    public Revenue(){
        initComponents();
    }

    private void initComponents(){
        NamePanel = new JLabel("Revenue Statistics");
        ChartPanel = new JPanel();
        Scroll = new JScrollPane();
        CardPanel = new JPanel();
        Invoice = new JPanel();
        setLayout(new BorderLayout());
        setBackground(Color.gray);

        ChartPanel.setPreferredSize(new Dimension(620,400));
        ChartPanel.setLayout(new BorderLayout());
        ChartPanel.setBorder(BorderFactory.createEmptyBorder(0,0,1,15));
        Chart chart = new Chart();
        ChartPanel.add(chart, BorderLayout.CENTER);

        CardPanel.setLayout(new GridLayout(1,4,25,25));
        CardPanel.setBorder(BorderFactory.createEmptyBorder(15,5,15,5));
        CardPanel.setPreferredSize(new Dimension(90,150));

        Card card1 = new Card("cart.png", "Total Order",getTotalOrderCurrentMonth(), new Color(0, 230, 230), getPercentTotalOrder());
        CardPanel.add(card1);

        Card card2 = new Card("coin.png", "Total Revenue",getTotalRevenueCurrentMonth(), new Color(255, 77, 77), getPercentTotalRevenue());
        CardPanel.add(card2);

        Card card3 = new Card("clothing.png", "Products Sold",getTotalProductSoldCurrentMonth(), new Color(255, 128, 0), getPercentTotalProductSold());
        CardPanel.add(card3);

        Card card4 = new Card("coin.png", "Points Earned",getTotalPointsUsedCurrentMonth(), new Color(153, 51, 255), getPercentTotalPointsUsed());
        CardPanel.add(card4);

        Invoice.setLayout(new GridLayout(10,1));
        Invoice invoiceHeader = new Invoice();
        Invoice.add(invoiceHeader);

//        for(int i = 0; i< 10; i++){
//            Invoice invoice = new Invoice("001","002","5","3000.000");
//            invoice.setPreferredSize(new Dimension(35,35));
//            Invoice.add(invoice);
//        }

//        LocalDate now = LocalDate.of(2023, 10, 16);
        LocalDate now = LocalDate.now();
        for(OrderModel order : OrderBUS.getInstance().getAllModels()) {
            LocalDate orderDate = Instant.ofEpochMilli(order.getOrderDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
            if(now.equals(orderDate)) {
                Invoice invoice = new Invoice(order);
                invoice.setPreferredSize(new Dimension(35,35));
                Invoice.add(invoice);
            }
        }

        Scroll.setViewportView(Invoice);

        NamePanel.setFont(new Font("Segoe UI", 1, 17));
        ChartPanel.add(NamePanel, BorderLayout.NORTH);

        add(CardPanel, BorderLayout.NORTH);
        add(ChartPanel, BorderLayout.WEST);
        add(Scroll, BorderLayout.CENTER);
    }

    public int getTotalOrderCurrentMonth() {
        LocalDate now = LocalDate.now();
        int totalOrders = 0;

        for (OrderModel order : OrderBUS.getInstance().getAllModels()) {
            LocalDate orderDate = Instant.ofEpochMilli(order.getOrderDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
            if (orderDate.getMonthValue() == now.getMonthValue() && orderDate.getYear() == now.getYear()) {
                totalOrders++;
            }
        }
        return totalOrders;
    }

    public int getTotalRevenueCurrentMonth() {
        LocalDate now = LocalDate.now();
        int totalOrders = 0;

        for (OrderModel order : OrderBUS.getInstance().getAllModels()) {
            LocalDate orderDate = Instant.ofEpochMilli(order.getOrderDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
            if (orderDate.getMonthValue() == now.getMonthValue() && orderDate.getYear() == now.getYear()) {
                totalOrders += order.getTotalPrice();
            }
        }
        return totalOrders;
    }

    public int getTotalProductSoldCurrentMonth() {
        LocalDate now = LocalDate.now();
        int totalSold = 0;
        int currentOrderID = 0;
        for (OrderModel order : OrderBUS.getInstance().getAllModels()) {
            LocalDate orderDate = Instant.ofEpochMilli(order.getOrderDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
            currentOrderID = order.getId();
            for (OrderItemModel orderItem : OrderItemBUS.getInstance().getAllModels()) {
                if (orderItem.getOrderId() == currentOrderID && orderDate.getMonthValue() == now.getMonthValue() && orderDate.getYear() == now.getYear()) {
                    totalSold += orderItem.getQuantity();
                }
            }
        }
        return totalSold;
    }


    public int getTotalPointsUsedCurrentMonth() {
        LocalDate now = LocalDate.now();
        int totalUsed = 0;

        for (PointTransactionModel point : PointTransactionBUS.getInstance().getAllModels()) {
            LocalDate orderDate = Instant.ofEpochMilli(point.getTransactionDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
            if (orderDate.getMonthValue() == now.getMonthValue() && orderDate.getYear() == now.getYear()) {
                totalUsed += point.getPointsUsed();
            }
        }
        return totalUsed;
    }

    public double getPercentTotalOrder() {
        int totalOrdersCurrentMonth = 0;
        int totalOrdersPreviousMonth = 0;
        double percentTotal = 0;
        LocalDate orderDateCurrent = LocalDate.now();
        LocalDate orderDatePreviousMonth = orderDateCurrent.minusMonths(1);

        for (OrderModel order : OrderBUS.getInstance().getAllModels()) {
            LocalDate orderDate = Instant.ofEpochMilli(order.getOrderDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
            if (orderDate.getMonth() == orderDateCurrent.getMonth() && orderDate.getYear() == orderDateCurrent.getYear()) {
                totalOrdersCurrentMonth++;
            } else if (orderDate.getMonth() == orderDatePreviousMonth.getMonth() && orderDate.getYear() == orderDatePreviousMonth.getYear()) {
                totalOrdersPreviousMonth++;
            }
        }
        percentTotal = ((totalOrdersCurrentMonth - totalOrdersPreviousMonth)/totalOrdersPreviousMonth) * 100;

        return percentTotal;
    }

    public double getPercentTotalRevenue() {
        int totalRevenueCurrentMonth = 0;
        int totalRevenuePreviousMonth = 0;
        double percentTotal = 0;
        LocalDate orderDateCurrent = LocalDate.now();
        LocalDate orderDatePreviousMonth = orderDateCurrent.minusMonths(1);

        for (OrderModel order : OrderBUS.getInstance().getAllModels()) {
            LocalDate orderDate = Instant.ofEpochMilli(order.getOrderDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
            if (orderDate.getMonth() == orderDateCurrent.getMonth() && orderDate.getYear() == orderDateCurrent.getYear()) {
                totalRevenueCurrentMonth += order.getTotalPrice();
            } else if (orderDate.getMonth() == orderDatePreviousMonth.getMonth() && orderDate.getYear() == orderDatePreviousMonth.getYear()) {
                totalRevenuePreviousMonth += order.getTotalPrice();
            }
        }
        percentTotal = ((totalRevenueCurrentMonth - totalRevenuePreviousMonth)/totalRevenuePreviousMonth) * 100;


        return percentTotal;
    }

    public double getPercentTotalProductSold() {
        double totalCurrent = 0;
        double totalPrevious = 0;
        LocalDate orderDateCurrent = LocalDate.now();
        LocalDate orderDatePreviousMonth = orderDateCurrent.minusMonths(1);
        List<OrderModel> list = OrderBUS.getInstance().getAllModels();
        List<OrderItemModel> listItem = OrderItemBUS.getInstance().getAllModels();
        int currentOrderID = 0;

        for (OrderModel order : list) {
            LocalDate orderDate = Instant.ofEpochMilli(order.getOrderDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
            if(orderDateCurrent.getMonth() == orderDate.getMonth() && orderDateCurrent.getYear() == orderDate.getYear()) {
                currentOrderID = order.getId();
                for (OrderItemModel orderItem  : listItem) {
                    if(currentOrderID == orderItem.getOrderId()) {
                        totalCurrent += orderItem.getQuantity();
                    }
                }
            }else if(orderDate.getMonth() == orderDatePreviousMonth.getMonth() && orderDate.getYear() == orderDatePreviousMonth.getYear()) {
                totalPrevious += listItem.get(order.getId()-1).getQuantity();
            }
        }

        double total = ((totalCurrent - totalPrevious)/totalPrevious) * 100;

        return total;
    }

    public double getPercentTotalPointsUsed() {
        int totalPointsCurrentMonth = 0;
        int totalPointsPreviousMonth = 0;
        double percentTotal = 0;
        LocalDate orderDateCurrent = LocalDate.now();
        LocalDate orderDatePreviousMonth = orderDateCurrent.minusMonths(1);

        for (PointTransactionModel point : PointTransactionBUS.getInstance().getAllModels()) {
            LocalDate orderDate = Instant.ofEpochMilli(point.getTransactionDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
            if (orderDate.getMonth() == orderDateCurrent.getMonth() && orderDate.getYear() == orderDateCurrent.getYear()) {
                totalPointsCurrentMonth += point.getPointsUsed();
            } else if (orderDate.getMonth() == orderDatePreviousMonth.getMonth() && orderDate.getYear() == orderDatePreviousMonth.getYear()) {
                totalPointsPreviousMonth += point.getPointsUsed();
            }
        }

        percentTotal = ((totalPointsCurrentMonth - totalPointsPreviousMonth)/totalPointsPreviousMonth) * 100;
        return percentTotal;
    }




    private JLabel NamePanel;
    private JPanel CardPanel;
    private JPanel ChartPanel;
    private JScrollPane Scroll;
    private JPanel Invoice;
}
