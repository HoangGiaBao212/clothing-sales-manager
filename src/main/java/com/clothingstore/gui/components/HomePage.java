package com.clothingstore.gui.components;

import javax.swing.*;

import com.clothingstore.gui.admin.dashboard.Dashboard;
import com.clothingstore.gui.employee.Invoice;
import com.clothingstore.gui.employee.Navigation;
import com.clothingstore.gui.models.MenuData;
import com.clothingstore.models.UserModel;

import services.Authentication;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.util.ArrayList;

public class HomePage extends JFrame {

  private static HomePage instance;
  public Authentication authentication;
  UserModel currentUser;
  Menu menu;

  public static HomePage getInstance() {
    if (instance == null) {
      instance = new HomePage();
    }
    return instance;
  }

  public static void setInstance(HomePage instance) {
    HomePage.instance = instance;
  }

  public HomePage() {
    currentUser = Authentication.getCurrentUser();
    initComponent();
    setLocationRelativeTo(null);
    this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    this.addWindowStateListener(windowStateListener);
    this.addComponentListener(componentListener);
  }

  public void initComponent() {
    setSize(new Dimension(1200, 628));
    setLayout(new BorderLayout());
    setBackground(Color.BLACK);
    switch (currentUser.getRoleId()) {
      case 1:
        add(Dashboard.getInstance(), BorderLayout.CENTER);
        break;
      case 2:
        add(Products.getInstance(), BorderLayout.CENTER);
        add(Navigation.getInstance(), BorderLayout.SOUTH);
        break;
      case 3:
        add(Products.getInstance(), BorderLayout.CENTER);
        add(Invoice.getInstance(), BorderLayout.EAST);
        add(Navigation.getInstance(), BorderLayout.SOUTH);
        break;
      default:
        break;
    }
    
    add(Menu.getInstance(getDataMenu()), BorderLayout.WEST);
  }

  public ArrayList<MenuData> getDataMenu() {
    return new MenuData().getDataMenuByRolePermission();
  }

  public void Remove() {
    Container contentPane = HomePage.getInstance().getContentPane();
    Component centerComponent = ((BorderLayout) contentPane.getLayout()).getLayoutComponent(BorderLayout.CENTER);
    Component eastComponent = ((BorderLayout) contentPane.getLayout()).getLayoutComponent(BorderLayout.EAST);
    Component southComponent = ((BorderLayout) contentPane.getLayout()).getLayoutComponent(BorderLayout.SOUTH);
    if (centerComponent != null) {
      contentPane.remove(centerComponent);
      contentPane.revalidate();
      contentPane.repaint();
    }
    if (southComponent != null) {
      contentPane.remove(southComponent);
      contentPane.revalidate();
      contentPane.repaint();
    }
    if (eastComponent != null) {
      contentPane.remove(eastComponent);
      contentPane.revalidate();
      contentPane.repaint();
    }
  }

  public void Add(JPanel panel) {
    add(panel, BorderLayout.CENTER);
    revalidate();
    repaint();
  }

  public void Add(JPanel panel, JPanel panel2) {
    add(panel, BorderLayout.CENTER);
    add(panel2, BorderLayout.EAST);
    revalidate();
    repaint();
  }

  WindowStateListener windowStateListener = new WindowStateListener() {

    @Override
    public void windowStateChanged(WindowEvent arg0) {
      int column = (int) HomePage.getInstance().getSize().getWidth() / 280;
      Products.getInstance().ChangeLayout(column);
      revalidate();
      repaint();
    }

  };

  ComponentAdapter componentListener = new ComponentAdapter() {
    @Override
    public void componentResized(ComponentEvent e) {
      int column = (int) HomePage.getInstance().getSize().getWidth() / 250;
      double width = HomePage.getInstance().getSize().getWidth() / 250;
      if (width - (int) width > 0.5) {
        column = (int) width + 1;
      }
      if (currentUser.getRoleId() == 3)
        Products.getInstance().ChangeLayout(column - 1);
      else
        Products.getInstance().ChangeLayout(column);
      revalidate();
      repaint();
    }
  };

  public void closeHomePage() {
    this.removeAll();
    this.revalidate();
    this.repaint();
    dispose();
    instance = null;
    Menu.getInstance(null).closeMenuPanel();
  }
}
