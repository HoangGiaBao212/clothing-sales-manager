package com.clothingstore.gui.admin.Dashboard;

import java.awt.*;
import javax.swing.*;

public class Card extends JPanel {

    
    public Card( String icon, String name, int value, Color color, int percent) {
        initComponents(icon, name, value, color , percent);
    }

    private void initComponents(String icon, String name, int value, Color color, int percent) {

        Icon = new JLabel();
        MainPanel = new JPanel();
        NamePanel = new JLabel();
        Panel = new JPanel();
        Value = new JLabel();
        ProgressBar = new JProgressBar(0, 100);


        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setLayout(new BorderLayout());
        setBackground(color);
        
        Icon.setIcon(new ImageIcon(getClass().getResource("/config/icon/"+icon)));
        Icon.setHorizontalAlignment(SwingConstants.RIGHT);
        add(Icon, BorderLayout.PAGE_START);

        MainPanel.setLayout(new GridLayout(3, 0));
        MainPanel.setBackground(color);

        NamePanel.setFont(new Font("Segoe UI", 1, 16));
        NamePanel.setForeground(Color.WHITE);
        NamePanel.setText(name);
        MainPanel.add(NamePanel);

        Panel.setLayout(new BorderLayout());
        Panel.setBackground(color);
        Panel.setBorder(BorderFactory.createEmptyBorder(7,5,5,7));

        ProgressBar.setValue(percent);
        ProgressBar.setStringPainted(true);
        Panel.add(ProgressBar, BorderLayout.CENTER);
        MainPanel.add(Panel);

        Value.setFont(new Font("Segoe UI", 3, 16)); // NOI18N
        Value.setText(value+"");
        MainPanel.add(Value);

        add(MainPanel, BorderLayout.CENTER);
    }

    private JLabel Icon;
    private JPanel MainPanel;
    private JLabel NamePanel;
    private JLabel Value;
    private JPanel Panel;
    private JProgressBar ProgressBar;
    
}
