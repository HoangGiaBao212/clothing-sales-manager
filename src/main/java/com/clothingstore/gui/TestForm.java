package com.clothingstore.gui;

import javax.security.auth.login.LoginException;
import javax.swing.*;

import com.clothingstore.bus.UserBUS;
import com.clothingstore.gui.components.HomePage;
import com.clothingstore.models.UserModel;
import services.Authentication;

public class TestForm {
  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
    } catch (Exception e) {
      e.printStackTrace();
    }

    SwingUtilities.invokeLater(() -> {
      UserModel user;
      try {
        user = UserBUS.getInstance().login("admin12345", "User12345");
        Authentication.setCurrentUser(user);
        HomePage.getInstance().reUI();
        HomePage homePage = HomePage.getInstance();
        homePage.setVisible(true);
      } catch (LoginException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    });
  }
}
