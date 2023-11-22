package com.clothingstore.gui.admin.dashboard;

import java.awt.*;
import javax.swing.*;

import com.clothingstore.gui.models.NavData;
public class Header extends JPanel {

    private static Header instance;

    public static Header getInstance() {
        if (instance == null) {
            instance = new Header();
        }
        return instance;
    }
    public Header() {
        initComponents();
    }

    private void initComponents() {

        NamePanel = new JLabel();
        Panel = new JPanel();
        SearchPanel = new JPanel();
        Value = new JTextField();
        ButtonSearch = new JButton();
        ButtonMenu = new JButton();

        Color backgroundColor = new Color(179, 179, 255);

        setBorder(BorderFactory.createEmptyBorder(1, 5, 1, 1));
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(70,75));
        setBackground(backgroundColor);

        NamePanel.setText("   DashBoard");
        NamePanel.setFont(new Font("Segoe UI", 1, 16)); 
        add(NamePanel, BorderLayout.CENTER);

        ButtonMenu.setIcon(new ImageIcon(getClass().getResource("/resources/icons/menu.png")));
        ButtonMenu.setBackground(backgroundColor);
        ButtonMenu.setBorder(null);
        ButtonMenu.addActionListener(new NavData().MenuAction());
        add(ButtonMenu, BorderLayout.WEST);

        Panel.setBorder(BorderFactory.createEmptyBorder(22, 1, 22, 1));
        Panel.setPreferredSize(new Dimension(360, 80));
        Panel.setLayout(new GridLayout(1, 2));
        Panel.setBackground(backgroundColor);

        SearchPanel.setLayout(new BorderLayout());
        
        Value.setBorder(null);
        SearchPanel.add(Value, BorderLayout.CENTER);
        
        ButtonSearch.setIcon(new ImageIcon(getClass().getResource("/resources/icons/search.png"))); 
        ButtonSearch.setBorder(null);
        ButtonSearch.setBackground(Color.WHITE);
        SearchPanel.add(ButtonSearch, BorderLayout.EAST);
        
        Panel.add(SearchPanel);

        add(Panel, BorderLayout.EAST);
    }
    private JButton ButtonSearch;
    private JLabel NamePanel;
    private JPanel Panel;
    private JPanel SearchPanel;
    private JTextField Value;
    private JButton ButtonMenu;
}
