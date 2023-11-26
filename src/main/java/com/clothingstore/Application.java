package com.clothingstore;

import javax.swing.*;
import com.clothingstore.gui.login.Login;

public class Application {
  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
    } catch (Exception e) {
      e.printStackTrace();
    }
    SwingUtilities.invokeLater(() -> {
      Login login = Login.getInstance();
      login.setVisible(true);
    });
  }
}