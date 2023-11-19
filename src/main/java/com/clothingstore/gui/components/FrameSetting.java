package com.clothingstore.gui.components;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

import javax.swing.*;

import com.clothingstore.bus.UserBUS;
import com.clothingstore.models.UserModel;

public class FrameSetting extends JFrame {
  UserModel userModel;
  String[] name = { "Name", "Password", "Email", "Phone", "Gender", "Address", "Status" };
  String[] value = { "", "", "", "", "", "", "" };
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
  public void updateUserData(UserModel userModel){
    
    userModel.setName(value[0]);
    userModel.setPassword(value[1]);
    userModel.setEmail(value[2]);
    userModel.setPhone(value[3]);
    try{
    userModel.setGender(Integer.valueOf(value[4]));
    }catch(NumberFormatException e){
        System.out.println("Invalid gender value: " + value[4]);
    }
    userModel.setAddress(value[5]);
    userModel.setUserStatus(null);
    
  }
  public void initComponents() {
    Scroll = new JScrollPane();
    Panel = new JPanel();
    Content = new JPanel();
    NameLabel = new JLabel("Thông tin tài khoản");
    Buttons = new JPanel();
    ButtonBack = new JButton("Thoát");
    ButtonSave = new JButton("Lưu");

    setSize(800, 500);
    setPreferredSize(new Dimension(800, 500));
    setLayout(new BorderLayout());

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

  }

  private JScrollPane Scroll;
  private JPanel Panel;
  private JPanel Content;
  private JLabel NameLabel;
  private JPanel Buttons;
  private JButton ButtonBack;
  private JButton ButtonSave;


  public static void main(String[] args) {
    FrameSetting frameSetting = new FrameSetting(UserBUS.getInstance().getModelById(1));
    frameSetting.setVisible(true);
    
  }
  private ActionListener SaveAction = new ActionListener() {

    @Override
    public void actionPerformed(ActionEvent arg0) {
      for (int i = 0; i< 7; i++) {
        System.out.println(value[i]);
        if(dataChanged){
        updateUserData(userModel);
      } else{
        dispose();
      }
      }
    }
    
  };

  
}