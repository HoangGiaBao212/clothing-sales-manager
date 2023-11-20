package com.clothingstore.gui.admin.rolePermissionManagement;


import java.awt.*;
import java.util.List;

import javax.swing.*;

import com.clothingstore.bus.UserBUS;
import com.clothingstore.models.UserModel;

public class RolePermissionManagement extends JPanel {
  private static RolePermissionManagement instance;
  List<UserModel> userList = UserBUS.getInstance().getAllModels();

  public static RolePermissionManagement getInstance() {
    if (instance == null) {
      instance = new RolePermissionManagement();
    }
    return instance;
  }

  public RolePermissionManagement() {
    initComponents();
  }

  private void initComponents() {
    setLayout(new BorderLayout());
    add(UserList.getInstance(), BorderLayout.WEST);
    add(new EditRolePermission(userList.get(userList.size() - 1)), BorderLayout.CENTER);
  }

  public void Remove() {
    Container contentPane = RolePermissionManagement.getInstance();
    Component centerComponent = ((BorderLayout) contentPane.getLayout()).getLayoutComponent(BorderLayout.CENTER);
    contentPane.remove(centerComponent);
    contentPane.revalidate();
    contentPane.repaint();
  }

}
