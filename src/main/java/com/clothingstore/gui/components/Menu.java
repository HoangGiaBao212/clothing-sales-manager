package com.clothingstore.gui.components;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

import com.clothingstore.bus.RoleBUS;
import com.clothingstore.bus.UserBUS;
import com.clothingstore.gui.models.MenuData;
import com.clothingstore.gui.models.MenuItemData;
import com.clothingstore.models.UserModel;

import services.Authentication;

public class Menu extends JPanel {
    private static Menu instance;

    private JPanel Panel;
    private JPanel Header;
    private JLabel NameUser;
    private JLabel RoleUser;
    private JLabel Icon;
    private JButton SettingButton;

    JPopupMenu popupMenu;
    JMenuItem menuItem;
    UserModel currentUser = Authentication.getCurrentUser();

    public static Menu getInstance(ArrayList<MenuData> data) {
        if (instance == null) {
            instance = new Menu(data);
        }
        return instance;
    }

    public Menu(ArrayList<MenuData> data) {
        initComponents(data);
    }

    public void initComponents(ArrayList<MenuData> data) {
        Panel = new JPanel();
        Header = new JPanel();
        NameUser = new JLabel();
        RoleUser = new JLabel();
        Icon = new JLabel();
        SettingButton = new JButton();
        setPreferredSize(new Dimension(0,150));
        setLayout(new BorderLayout());
        
        Panel.setBackground(new Color(0, 26, 51));
        Panel.setLayout(new GridLayout(10, 1, 10, 5));

        for (MenuData menuData : data) {

            ArrayList<MenuItemData> dataMenuItem = menuData.getItemData();

            JButton menuButton = new JButton(menuData.getName());
            menuButton.addActionListener(menuData.getActionListener());
            menuButton.setFont(new Font("Segoe UI", 0, 13));
            menuButton.setPreferredSize(new Dimension(50, 50));
            menuButton.setBackground(new Color(153, 153, 255));
            menuButton.setBorder(BorderFactory.createEmptyBorder(5,32,5,5));
            menuButton.setBorderPainted(false);
            menuButton.setOpaque(true);
            menuButton.setIcon(new ImageIcon(getClass().getResource("/resources/icons/menu/"+menuData.getIcon()+".png")));
            menuButton.setHorizontalAlignment(SwingConstants.LEFT);

            JPopupMenu popupMenu = new JPopupMenu();

            if (dataMenuItem != null) {
                for (MenuItemData option : dataMenuItem) {
                    JMenuItem menuItem = new JMenuItem(option.getName());
                    menuItem.setPreferredSize(new Dimension(170, 45));
                    menuItem.setBackground(new Color(204, 204, 255));
                    menuItem.setHorizontalAlignment(SwingConstants.CENTER);
                    menuItem.setContentAreaFilled(false);
                    menuItem.setOpaque(true);

                    menuItem.addActionListener(option.getActionListener());
                    popupMenu.add(new JSeparator());
                    popupMenu.add(menuItem);
                }
            }

            menuButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    popupMenu.show(menuButton, menuButton.getWidth(), 0);
                }
            });
            Panel.add(menuButton);
        }
        add(Panel, BorderLayout.CENTER);

        Header.setPreferredSize(new Dimension(90,90));
        Header.setBorder(BorderFactory.createEmptyBorder(0,5,0,5));
        Header.setLayout(new BorderLayout());
        Header.setBackground(new Color(102, 102, 255));

        NameUser.setText(currentUser.getName());
        NameUser.setFont(new Font("Segoe UI", 1, 15));
        NameUser.setHorizontalAlignment(SwingConstants.CENTER);
        Header.add(NameUser, BorderLayout.CENTER);

        RoleUser.setText(RoleBUS.getInstance().getModelById(currentUser.getRoleId()).getName());
        RoleUser.setFont(new Font("Segoe UI", 2, 13));
        RoleUser.setForeground(Color.DARK_GRAY);
        RoleUser.setHorizontalAlignment(SwingConstants.RIGHT);
        Header.add(RoleUser, BorderLayout.SOUTH);

        Icon.setIcon(new ImageIcon(getClass().getResource("/resources/icons/menu/user.png")));
        Header.add(Icon, BorderLayout.WEST);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(102, 102, 255));
        panel.setLayout(new BorderLayout());

        SettingButton.setIcon(new ImageIcon(getClass().getResource("/resources/icons/menu/setting.png")));
        SettingButton.setBorder(null);
        SettingButton.setBackground(new Color(102, 102, 255));
        SettingButton.setPreferredSize(new Dimension(20,20));
        SettingButton.addActionListener(SettingAction);
        panel.add(SettingButton, BorderLayout.EAST);

        Header.add(panel, BorderLayout.NORTH);
        add(Header, BorderLayout.NORTH);
    }
    private ActionListener SettingAction = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent arg0) {
             FrameSetting frameSetting = new FrameSetting(currentUser);
             frameSetting.setVisible(true);
        }
        
    };
}
