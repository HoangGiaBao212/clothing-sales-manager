package com.clothingstore.gui.admin.dashboard;

import java.awt.*;
import javax.swing.*;
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
        ProfilePanel = new JPanel();
        Profile = new JComboBox<>();

        Color backgroundColor = new Color(0, 38, 77);

        setBorder(BorderFactory.createEmptyBorder(1, 20, 1, 1));
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(70,75));
        setBackground(backgroundColor);

        NamePanel.setText("DashBoard");
        NamePanel.setFont(new Font("Segoe UI", 1, 16)); 
        NamePanel.setForeground(Color.WHITE);
        add(NamePanel, BorderLayout.LINE_START);

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
        
        ProfilePanel.setBorder(BorderFactory.createEmptyBorder(1, 35, 1, 20));
        ProfilePanel.setBackground(backgroundColor);
        ProfilePanel.setLayout(new BorderLayout());

        // Profile.setModel(new DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        Profile.setModel(new DefaultComboBoxModel<>(new Icon[]{(new ImageIcon(getClass().getResource("/resources/icons/search.png")))}));
        ProfilePanel.add(Profile, BorderLayout.CENTER);

        Panel.add(ProfilePanel);

        add(Panel, BorderLayout.EAST);
    }
    private JButton ButtonSearch;
    private JComboBox<Icon> Profile;
    private JLabel NamePanel;
    private JPanel Panel;
    private JPanel SearchPanel;
    private JPanel ProfilePanel;
    private JTextField Value;
}
