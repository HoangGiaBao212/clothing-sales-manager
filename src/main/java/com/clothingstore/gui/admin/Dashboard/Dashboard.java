package com.clothingstore.gui.admin.dashboard;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import com.clothingstore.gui.models.MenuData;
import com.clothingstore.gui.components.Menu;
public class Dashboard extends JPanel {

    private static Dashboard instance;

    public static Dashboard getInstance() {
        if (instance == null) {
            instance = new Dashboard();
        }
        return instance;
    }

    public Dashboard(){
        initComponents();
    }
    public void initComponents(){
        mainPanel = new JPanel();

        setLayout(new BorderLayout());
        setSize(new Dimension(1130, 628));
        mainPanel.setBackground(new Color(179, 179, 255));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(1, 5, 1, 5));
        setLayout(new BorderLayout());

        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(Header.getInstance(), BorderLayout.NORTH);
        mainPanel.add(Content.getInstance(), BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);

        ArrayList<MenuData> data = new MenuData().getDataMenuByRolePermission();
        Menu.getInstance(data).setPreferredSize(new Dimension(150,150));
        add(Menu.getInstance(data), BorderLayout.WEST);
    }

    private JPanel mainPanel;
}
