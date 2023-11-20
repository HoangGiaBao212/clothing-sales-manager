package com.clothingstore.gui.admin.userPermissionManagement;


import java.awt.*;
import java.util.List;

import javax.swing.*;

import com.clothingstore.bus.UserBUS;
import com.clothingstore.models.UserModel;

public class UserPermissionManagement extends JPanel {
  private static UserPermissionManagement instance;
  List<UserModel> userList = UserBUS.getInstance().getAllModels();

  public static UserPermissionManagement getInstance() {
    if (instance == null) {
      instance = new UserPermissionManagement();
    }
    return instance;
  }

  public UserPermissionManagement() {
    initComponents();
  }

  private void initComponents() {
    setLayout(new BorderLayout());
    add(UserList.getInstance(), BorderLayout.WEST);
    add(new EditUserPermission(userList.get(userList.size() - 1)), BorderLayout.CENTER);
  }

  public void Remove() {
    Container contentPane = UserPermissionManagement.getInstance();
    Component centerComponent = ((BorderLayout) contentPane.getLayout()).getLayoutComponent(BorderLayout.CENTER);
    contentPane.remove(centerComponent);
    contentPane.revalidate();
    contentPane.repaint();
  }

}
