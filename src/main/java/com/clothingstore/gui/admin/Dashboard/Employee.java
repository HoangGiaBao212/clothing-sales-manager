package com.clothingstore.gui.admin.Dashboard;

import java.awt.*;
import javax.swing.*;

public class Employee extends JPanel {
    public Employee(){
        initComponents("Huỳnh Triều", "Admin", "ACTIVE");
    }
    public Employee(String name, String role, String status){
        initComponents(name, role, status);
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
