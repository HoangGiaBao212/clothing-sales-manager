package com.clothingstore.gui.admin.userPermissionManagement;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.*;

import com.clothingstore.bus.UserBUS;
import com.clothingstore.gui.models.NavData;
import com.clothingstore.models.UserModel;

public class UserList extends JPanel {

  private static UserList instance;

  public static UserList getInstance() {
    if (instance == null) {
      instance = new UserList();
    }
    return instance;
  }

  public UserList() {
    initComponents();
  }

  private void initComponents() {

    Header = new JPanel();
    NameHeader = new JPanel();
    NamePanel = new JLabel();
    ButtonMenu = new JButton();
    Panel = new JPanel();
    ButtonSearch = new JButton();
    SearchValue = new JTextField();
    Users = new JPanel();
    Scroll = new JScrollPane();

    Color color = new Color(179, 209, 255);

    setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 4));
    setLayout(new BorderLayout());
    setPreferredSize(new Dimension(322, 170));
    setBackground(color);

    Header.setLayout(new BorderLayout());
    Header.setPreferredSize(new Dimension(250, 65));
    Header.setBackground(color);

    NameHeader.setLayout(new BorderLayout());

    NamePanel.setFont(new Font("Segoe UI", 1, 18)); // NOI18N
    NamePanel.setHorizontalAlignment(SwingConstants.CENTER);
    NamePanel.setText("User List");
    NameHeader.setBackground(color);
    NameHeader.add(NamePanel, BorderLayout.CENTER);

    ButtonMenu.setIcon(new ImageIcon(getClass().getResource("/resources/icons/menu.png")));
    ButtonMenu.setBackground(color);
    ButtonMenu.setBorder(null);
    ButtonMenu.addActionListener(new NavData().MenuAction());
    NameHeader.add(ButtonMenu, BorderLayout.LINE_START);

    Header.add(NameHeader, BorderLayout.NORTH);

    Panel.setBorder(BorderFactory.createEmptyBorder(4, 4, 7, 1));
    Panel.setLayout(new BorderLayout());
    Panel.setBackground(color);

    ButtonSearch.setIcon(new ImageIcon(getClass().getResource("/resources/icons/search.png"))); // NOI18N
    ButtonSearch.setBorder(null);
    ButtonSearch.setBackground(Color.WHITE);
    Panel.add(ButtonSearch, BorderLayout.WEST);

    SearchValue.setBackground(new Color(242, 242, 242));
    SearchValue.setFont(new Font("Segoe UI", 0, 14)); 
    SearchValue.setText("Tìm theo mã nhân viên");
    SearchValue.setBackground(Color.white);
    SearchValue.setBorder(BorderFactory.createEmptyBorder(1, 6, 1, 1));
    SearchValue.addFocusListener(new FocusListener() {
      @Override
      public void focusGained(FocusEvent e) {
        if (SearchValue.getText().equals("Tìm theo mã nhân viên")) {
          SearchValue.setText("");
        }
      }

      @Override
      public void focusLost(FocusEvent e) {
        if (SearchValue.getText().isEmpty()) {
          SearchValue.setText("Tìm theo mã nhân viên");
        }
      }
    });

    Panel.add(SearchValue, BorderLayout.CENTER);

    Header.add(Panel, BorderLayout.SOUTH);

    add(Header, BorderLayout.PAGE_START);

    Users.setLayout(new GridLayout(0, 1));
    Users.setBackground(color);

    UserBUS.getInstance().refreshData();
    List<UserModel> userList = new ArrayList<>(UserBUS.getInstance().getAllModels());
    userList.remove(0);
    Collections.reverse(userList);
    for (UserModel userModel : userList) {
      User user = new User(userModel);
      Users.add(user);
    }

    Scroll.setViewportView(Users);
    Scroll.getVerticalScrollBar().setUnitIncrement(30);
    add(Scroll, BorderLayout.CENTER);
  }

  private JButton ButtonMenu;
  private JButton ButtonSearch;
  private JPanel Header;
  private JPanel Users;
  private JPanel NameHeader;
  private JLabel NamePanel;
  private JTextField SearchValue;
  private JPanel Panel;
  private JScrollPane Scroll;
}
    