package com.clothingstore.gui.admin.dashboard;

import java.awt.*;
import javax.swing.*;

public class Employee extends JPanel {
    public Employee(){
        initComponents("Tên", "Chức vụ", "Trạng thái");
        Name.setFont(new Font("Segoe UI", 1, 15)); 
        Role.setFont(new Font("Segoe UI", 1, 15)); 
        Status.setFont(new Font("Segoe UI", 1, 15));
        setBackground(new Color(0, 92, 230));
        
    }
    public Employee(String name, String role, String status){
        initComponents(name, role, status);
        Name.setFont(new Font("Segoe UI", 0, 14)); 
        Role.setFont(new Font("Segoe UI", 0, 14)); 
        Status.setFont(new Font("Segoe UI", 0, 14));
    }
    private void initComponents(String name, String role, String status){
        Name = new JLabel(name);
        Role = new JLabel(role);
        Status = new JLabel(status);
        
        setPreferredSize(new Dimension(40,40));
        setLayout(new GridLayout(1,3));
        setBorder(BorderFactory.createEmptyBorder(1, 3, 1, 3));
        add(Name);
        add(Role);
        add(Status);
    }
    private JLabel Name;
    private JLabel Role;
    private JLabel Status;
}
