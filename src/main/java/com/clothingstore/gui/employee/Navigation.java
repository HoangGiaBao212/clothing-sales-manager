
package com.clothingstore.gui.employee;

import com.clothingstore.gui.models.NavData;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

public class Navigation extends javax.swing.JPanel {

  ArrayList<NavData> data = new NavData().getData();

  private static Navigation instance;

  public static Navigation getInstance() {
    if (instance == null) {
      instance = new Navigation();
    }
    return instance;
  }

  public static void setInstance(Navigation newInstance) {
    instance = newInstance;
  }

  public Navigation() {
    initComponents();
  }

  private void initComponents() {
    setPreferredSize(new java.awt.Dimension(662, 50));
    setLayout(new java.awt.GridBagLayout());
    setBackground(new Color(102, 163, 255));

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 0, 5, 10);
    gbc.fill = GridBagConstraints.BOTH;
    gbc.weighty = 1.0;

    for (NavData navData : data) {
      JButton Button = new JButton();

      JLabel Text = new JLabel(navData.getName());
      Text.setFont(new java.awt.Font("Segoe UI", 1, 15));

      Button.add(Text);
      Button.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent arg0) {
          ActionListener listener = navData.getActionListener();
          listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
        }
      });
      add(Button, gbc);
    }

  }

}
