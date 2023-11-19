package com.clothingstore.gui.components;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
import org.netbeans.lib.awtextra.*;

import com.clothingstore.bus.CategoryBUS;
import com.clothingstore.bus.ImportItemsBUS;
import com.clothingstore.bus.ProductBUS;
import com.clothingstore.bus.SizeBUS;
import com.clothingstore.bus.SizeItemBUS;
import com.clothingstore.gui.employee.Invoice;
import com.clothingstore.models.ImportItemsModel;
import com.clothingstore.models.ProductModel;
import com.clothingstore.models.SizeItemModel;
import com.clothingstore.models.SizeModel;
import com.clothingstore.models.UserModel;
import services.Authentication;

public class FrameSetting extends JFrame {
    private JTextField usernameTextField;
    private JTextField passwordTextField;
    private JTextField emailTextField;
    private JTextField nameTextField;
    private JTextField phoneTextField;
    private JTextField genderTextField;
    private JTextField imageTextField;
    private JTextField role_idTextField1;
    private JTextField addressTextField;
    private JTextField statusTextField;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private JButton button6;
    private JButton button7;
    private JButton button8;
    private JTextField created_atTextField;
    private JButton button9;

  public FrameSetting() {
    
    setAlwaysOnTop(true);
    setSize(new Dimension(685, 390));
    setPreferredSize(new Dimension(685, 325));
    setResizable(false);
    getContentPane().setLayout(new AbsoluteLayout());
  
    setLocationRelativeTo(null);

    initComponents();
  }

  ImportItemsModel importItemsModel = null;
  SizeItemModel sizeItemModel = null;
  SizeModel sizeModel = null;

  private void initComponents() {
     usernameTextField = new JTextField("username");
    passwordTextField = new JTextField("password");
    emailTextField = new JTextField("email");
    nameTextField= new JTextField("name");
    phoneTextField = new JTextField("phone");
    genderTextField = new JTextField("gender");
    imageTextField = new JTextField("image");
    role_idTextField1 = new JTextField("role_id");
    addressTextField = new JTextField("address");
    statusTextField = new JTextField("status");
    button1  = new JButton("Sua");
    button2 = new JButton("Sua");
    button3 = new JButton("Sua");
    button4 = new JButton("Sua");
    button5 = new JButton("Sua");
    button6 = new JButton("Sua");
    button7 = new JButton("Sua");
    button8 = new JButton("Sua");
     created_atTextField = new JTextField("Sua");
    button9 = new JButton("Sua");
    setLayout(new GridLayout(0,1));

  }

  public static void main(String[] args) {
    FrameSetting frameSetting = new FrameSetting();
    frameSetting.setVisible(true);
  }
}