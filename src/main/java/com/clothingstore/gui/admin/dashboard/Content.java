package com.clothingstore.gui.admin.dashboard;

import java.awt.*;

import javax.swing.*;

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

        Color backgroundColor = new Color(0, 38, 77);
        setLayout(new BorderLayout());

        MainPanel.setLayout(new BorderLayout());
        Cards.setBackground(backgroundColor);
        Cards.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 8));

        Cards.setLayout(new GridLayout(1,4,20,20));
        for(int i = 0; i<4;i++){
            Card card = new Card("coin.png", "Total Order",334,Color.BLUE,2);
            Cards.add(card);
        }
        MainPanel.add(Cards, BorderLayout.NORTH);

        InvoicePanel.setLayout(new BorderLayout());
        InvoicePanel.setBackground(backgroundColor);

        InvoiceTitle.setFont(new Font("Segoe UI", 1, 16)); 
        InvoiceTitle.setForeground(Color.WHITE);
        InvoiceTitle.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 1));
        InvoicePanel.add(InvoiceTitle, BorderLayout.NORTH);
        
        Invoices.setLayout(new BorderLayout());

        InvoiceList.setLayout(new GridLayout(0,1));
        for(int i = 0; i< 11; i++){
            Invoice invoice = new Invoice();
            invoice.setBorder(BorderFactory.createEmptyBorder(1, 5, 1, 1));
            InvoiceList.add(invoice);
        }
        
        Scroll.setViewportView(InvoiceList);
        Invoices.add(Scroll, BorderLayout.CENTER);

        Invoice invoiceHeader = new Invoice("Id Invoice", "Date", "Price");
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
        for(int i = 0; i<15;i++){
            Employee employee = new Employee();
            EmployeeList.add(employee);
        }
        Scroll2.setViewportView(EmployeeList);
        Employee.add(Scroll2, BorderLayout.CENTER);

        add(Employee, BorderLayout.EAST);
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
