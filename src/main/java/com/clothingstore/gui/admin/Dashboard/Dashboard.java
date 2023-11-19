package com.clothingstore.gui.admin.Dashboard;

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
        mainPanel.setBackground(new Color(0, 38, 77));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(1, 20, 1, 5));
        setLayout(new BorderLayout());

        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(Header.getInstance(), BorderLayout.NORTH);
        mainPanel.add(Content.getInstance(), BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);

        ArrayList<MenuData> data = MenuData.getDataAdmin();
        Menu.getInstance(data).setPreferredSize(new Dimension(150,150));
        add(Menu.getInstance(data), BorderLayout.WEST);
    }

    private JPanel mainPanel;
}
