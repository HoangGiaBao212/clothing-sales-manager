package com.clothingstore.gui.components;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import services.Validation;

import javax.swing.*;

import com.clothingstore.bus.ProductBUS;
import com.clothingstore.bus.UserBUS;
import com.clothingstore.dao.UserDAO;
import com.clothingstore.models.ProductModel;
import com.clothingstore.models.UserModel;

public class FrameSetting extends JFrame {
  UserModel userModel = new UserModel();
  String[] name = { "Name", "Password", "Email", "Phone", "Gender", "Address", "Status" };
  String[] value = { "", "", "", "", "", "", "" };
  String[] genders = { "Gender *", "Male", "Female" };
  ArrayList<String> user = new ArrayList<>() ;
  private boolean dataChanged;

  public FrameSetting(UserModel userModel) {
    this.userModel = userModel;
    user.add(userModel.getName());
    user.add(userModel.getPassword());
    user.add(userModel.getEmail());
    user.add(userModel.getPhone());
    user.add(String.valueOf(userModel.getGender()));
    user.add(userModel.getAddress());
    user.add(""+userModel.getUserStatus());

    initComponents();
    setVisible(true);
    setLocationRelativeTo(null);
    this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
  }
  public void updateUserData(UserModel user) {
    user.setName(value[0]);
    user.setPassword(value[1]);
    user.setEmail(value[2]);
    user.setPhone(value[3]);
    
    try {
        user.setGender(Integer.parseInt(value[4]));
    } catch (NumberFormatException e) {
        // Handle the case where value[4] is not a valid integer
        System.out.println("Invalid gender value: " + value[4]);
    }
    
    user.setAddress(value[5]);
    user.setUserStatus(null);
  }
  public void initComponents() {
    Scroll = new JScrollPane();
    Panel = new JPanel();
    Content = new JPanel();
    NameLabel = new JLabel("Thông tin tài khoản");
    Buttons = new JPanel();
    ButtonBack = new JButton("Thoát");
    ButtonSave = new JButton("Lưu");
    jTextFieldName = new JTextField();
    jTextFieldPassword = new JTextField();
    jTextFieldEmail = new JTextField();
    jTextFieldPhone = new JTextField();
    jTextFieldAddress = new JTextField();
    //
    comboBoxGender = new JComboBox<>(genders);
    comboBoxGender.setPreferredSize(new java.awt.Dimension(300, 40));
    comboBoxGender.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedGender = (String) comboBoxGender.getSelectedItem();
            if ("Male".equals(selectedGender)) {
                selectedGenderId = 1;
            } else if ("Female".equals(selectedGender)) {
                selectedGenderId = 0;
            } else {
                selectedGenderId = -1;
            }
        }
    });
    jPanelInfor = new JPanel();
    jPanelInfor.setLayout(new BoxLayout(jPanelInfor, BoxLayout.Y_AXIS));
    jPanelInfor.add(comboBoxGender);
    jPanelInfor.add(Box.createVerticalStrut(10));
    setSize(800, 500);
    setPreferredSize(new Dimension(800, 500));
    setLayout(new BorderLayout());
    //

    add(NameLabel, BorderLayout.NORTH);

    Panel.setLayout(new BorderLayout());

    Content.setLayout(new GridLayout(0, 1));
    for (int i = 0; i < 7; i++) {
      final int index = i;
      JPanel IndexPanel = new JPanel();
      IndexPanel.setLayout(new BorderLayout());
      IndexPanel.setPreferredSize(new Dimension(40, 80));
      IndexPanel.setBorder(BorderFactory.createEmptyBorder(5, 40, 15, 45));

      JLabel Name = new JLabel(name[i]);
      JLabel Invalid = new JLabel();
      JTextField Value = new JTextField();

      Name.setBorder(BorderFactory.createEmptyBorder(1, 5, 1, 45));
      Name.setFont(new Font("Segoe UI", 1, 15)); 
      IndexPanel.add(Name, BorderLayout.NORTH);

      Value.setPreferredSize(new Dimension(150, 35));
      Value.setText(user.get(i));
      Value.addFocusListener(new FocusListener() {
        @Override
        public void focusGained(FocusEvent e) {
          if (Value.getText().equals(user.get(index))) {
            Value.setText("");
          }
        }
        
        @Override
        public void focusLost(FocusEvent e) {
          if (Value.getText().isEmpty()) {
            Value.setText(user.get(index));
          }
            value[index] = Value.getText();
        }
      });

      IndexPanel.add(Value, BorderLayout.CENTER);

      IndexPanel.add(Invalid, BorderLayout.SOUTH);
      Invalid.setForeground(Color.RED);

      Content.add(IndexPanel);
    }
    Panel.add(Content, BorderLayout.CENTER);

    Scroll.setViewportView(Panel);
    add(Scroll, BorderLayout.CENTER);


    ButtonSave.addActionListener(SaveAction);

    Buttons.setPreferredSize(new Dimension(60, 60));
    Buttons.setLayout(new GridBagLayout());
    Buttons.add(ButtonBack);
    Buttons.add(ButtonSave);
    add(Buttons, BorderLayout.SOUTH);
    //
    ButtonSave.addActionListener(SaveAction);
    ButtonBack.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        dispose();
    }
    });
  }
  
  public static void main(String[] args) {
    FrameSetting frameSetting = new FrameSetting(UserBUS.getInstance().getModelById(1));
    frameSetting.setVisible(true);
    
  }
  private ActionListener SaveAction = new ActionListener() {

    @Override
    public void actionPerformed(ActionEvent arg0) {
      for (int i = 0; i< 1; i++) {
        System.out.println(value[i]);
      
         }
        
        }
      
    };
  
 
  private JScrollPane Scroll;
  private JPanel Panel;
  private JPanel Content;
  private JLabel NameLabel;
  private JPanel Buttons;
  private JButton ButtonBack;
  private JButton ButtonSave;
  //
  private JTextField jTextFieldName;
  private JTextField jTextFieldPassword;
  private JTextField jTextFieldEmail;
  private JTextField jTextFieldPhone;
  private JTextField jTextFieldAddress;
  private JComboBox comboBoxGender;
  private int selectedGenderId = -1;
  private JPanel jPanelInfor;
  //
}





