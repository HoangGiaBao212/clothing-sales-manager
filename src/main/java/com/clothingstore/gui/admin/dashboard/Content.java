package com.clothingstore.gui.admin.dashboard;

import java.awt.*;
import java.text.DecimalFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import javax.swing.*;

import org.apache.logging.log4j.util.Strings;

import com.clothingstore.bus.OrderBUS;
import com.clothingstore.bus.OrderItemBUS;
import com.clothingstore.bus.PointTransactionBUS;
import com.clothingstore.bus.UserBUS;
import com.clothingstore.enums.UserStatus;
import com.clothingstore.gui.components.customerList.Invoice;
import com.clothingstore.models.OrderItemModel;
import com.clothingstore.models.OrderModel;
import com.clothingstore.models.PointTransactionModel;
import com.clothingstore.models.UserModel;

public class Content extends JPanel {

    private static Content instance;

    public static Content getInstance() {
        if (instance == null) {
            instance = new Content();
        }
        return instance;
    }

    public Content() {
        initComponents();
    }

    private void initComponents() {

        MainPanel = new JPanel();
        Cards = new JPanel();
        Employee = new JPanel();
        InvoicePanel = new JPanel();
        InvoiceTitle = new JLabel("Invoice");
        Invoices = new JPanel();
        InvoiceList = new JPanel();
        Scroll = new JScrollPane();
        Scroll2 = new JScrollPane();
        EmployeeList = new JPanel();

        Color backgroundColor = new Color(179, 179, 255);
        setLayout(new BorderLayout());

        MainPanel.setLayout(new BorderLayout());
        Cards.setBackground(backgroundColor);
        Cards.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 8));

        Cards.setLayout(new GridLayout(1,4,20,20));
        
        Card card1 = new Card("cart.png", "Total Order",getTotalOrderCurrentMonth(), new Color(0, 230, 230), getPercentTotalOrder());
        Cards.add(card1);

        Card card2 = new Card("coin.png", "Total Revenue",getTotalRevenueCurrentMonth(), new Color(255, 77, 77), getPercentTotalRevenue());
        Cards.add(card2);

        Card card3 = new Card("clothing.png", "Products Sold",getTotalProductSoldCurrentMonth(), new Color(255, 128, 0), getPercentTotalProductSold());
        Cards.add(card3);

        Card card4 = new Card("coin.png", "Points Earned",getTotalPointsUsedCurrentMonth(), new Color(153, 51, 255), getPercentTotalPointsUsed());
        Cards.add(card4);
        
        MainPanel.add(Cards, BorderLayout.NORTH);

        InvoicePanel.setLayout(new BorderLayout());
        InvoicePanel.setBackground(backgroundColor);

        InvoiceTitle.setFont(new Font("Segoe UI", 1, 16)); 
        InvoiceTitle.setForeground(Color.WHITE);
        InvoiceTitle.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 1));
        InvoicePanel.add(InvoiceTitle, BorderLayout.NORTH);
        
        Invoices.setLayout(new BorderLayout());

        InvoiceList.setLayout(new GridLayout(0,1));
        
        LocalDate now = LocalDate.now();
        for(OrderModel order : OrderBUS.getInstance().getAllModels()) {
            LocalDate orderDate = Instant.ofEpochMilli(order.getOrderDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
            if(now.equals(orderDate)) {
            	double totalPrice = order.getTotalPrice();
            	
            	DecimalFormat decimalFormat = new DecimalFormat("###,###.00");
                String formattedTotalPrice = decimalFormat.format(totalPrice);
                
            	com.clothingstore.gui.admin.dashboard.Invoice invoice = new com.clothingstore.gui.admin.dashboard.Invoice(String.valueOf(order.getId()), order.getOrderDate().toString(), formattedTotalPrice);
                invoice.setPreferredSize(new Dimension(35,35));
                InvoiceList.add(invoice);
            }
        }
        
        Scroll.setViewportView(InvoiceList);
        Invoices.add(Scroll, BorderLayout.CENTER);

        com.clothingstore.gui.admin.dashboard.Invoice invoiceHeader = new com.clothingstore.gui.admin.dashboard.Invoice("Id Invoice", "Date", "Price");
        invoiceHeader.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 12));
        Invoices.add(invoiceHeader, BorderLayout.NORTH);
        
        InvoicePanel.add(Invoices, BorderLayout.CENTER);
        MainPanel.add(InvoicePanel, BorderLayout.CENTER);

        add(MainPanel, BorderLayout.CENTER);

        Employee.setLayout(new BorderLayout());
        Employee.setPreferredSize(new Dimension(250,250));

        Employee employeeHeader = new Employee("Name", "Role", "Status");
        Employee.add(employeeHeader, BorderLayout.NORTH);

        EmployeeList.setLayout(new GridLayout(0,1));
        
        for (UserModel user : UserBUS.getInstance().getAllModels()) {
        	String role = null;
        	if(user.getRoleId() == 1) {
        		role = "Admin";
        	}else if(user.getRoleId() == 2) {
        		role = "Manager";
        	}else {
        		role = "Employee";
        	}
        	if(user.getUserStatus() == UserStatus.ACTIVE) {
        		Employee employee = new Employee(user.getName(), role, user.getUserStatus().toString());
        		EmployeeList.add(employee);
        	}
		}
        Scroll2.setViewportView(EmployeeList);
        Employee.add(Scroll2, BorderLayout.CENTER);

        add(Employee, BorderLayout.EAST);
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

    private JPanel Cards;
    private JPanel MainPanel;
    private JPanel InvoicePanel;
    private JLabel InvoiceTitle;
    private JPanel Invoices;
    private JPanel InvoiceList;
    private JScrollPane Scroll;
    private JPanel Employee;
    private JPanel EmployeeList;
    private JScrollPane Scroll2;
}