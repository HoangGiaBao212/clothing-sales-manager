package com.clothingstore.gui.admin.dashboard;

import java.awt.*;
import javax.swing.*;

public class Invoice extends JPanel{

    Color color = new Color(204, 217, 255);
    
    public Invoice(String idInvoice, String date, String price){
        initComponents(idInvoice, date, price);
        IdInvoice.setFont(new Font("Segoe UI", 0, 15)); 
        Date.setFont(new Font("Segoe UI", 0, 15)); 
        Price.setFont(new Font("Segoe UI", 0, 15));
    }
    public Invoice(){
        initComponents("Mã hóa đơn", "Ngày tạo", "Tổng tiền");
        IdInvoice.setFont(new Font("Segoe UI", 1, 16)); 
        setBackground(color);
        Date.setFont(new Font("Segoe UI", 1, 16)); 
        Price.setFont(new Font("Segoe UI", 1, 16));
    }
    private void initComponents(String idInvoice, String date, String price){
        IdInvoice = new JLabel(idInvoice);
        Date = new JLabel(date);
        Price = new JLabel(price);

        setLayout(new GridLayout(1,3));
        setPreferredSize(new Dimension(40,40));
        setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 1));

        add(IdInvoice);
        add(Date);
        add(Price);

    }
    private JLabel IdInvoice;
    private JLabel Date;
    private JLabel Price;
}
