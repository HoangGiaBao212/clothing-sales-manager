package com.clothingstore.gui.manager;

import java.awt.*;
import java.util.*;
import javax.swing.*;

import com.clothingstore.gui.models.MenuData;
import com.clothingstore.gui.components.Menu;

public class Main extends JFrame {

  private static Main instance;

  public static Main getInstance() {
    if (instance == null) {
      instance = new Main();
    }
    return instance;
  }

  public Main() {
    initComponents();
    setLocationRelativeTo(null);
    this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
  }

  public void initComponents() {
    mainPanel = new JPanel();

    setLayout(new BorderLayout());
    setSize(new Dimension(1130, 628));

    mainPanel.setLayout(new BorderLayout());
    add(mainPanel, BorderLayout.CENTER);

    ArrayList<MenuData> data = new MenuData().getDataManager();
    Menu.getInstance(data).setPreferredSize(new Dimension(150, 150));
    add(Menu.getInstance(data), BorderLayout.WEST);
  }

  private JPanel mainPanel;
}
