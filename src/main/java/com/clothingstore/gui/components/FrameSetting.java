package com.clothingstore.gui.components;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

import services.PasswordUtils;
import services.Validation;

import javax.swing.*;
import com.clothingstore.bus.UserBUS;
import com.clothingstore.models.UserModel;

public class FrameSetting extends JFrame {
  UserModel userModel = new UserModel();
  String[] name = { "Tên", "Mật khẩu", "Email", "Số điện thoại", "Giới tính", "Địa chỉ" };
  String[] value = { "", "", "", "", "", "", "" };
  String[] genders = { "Nam", "Nữ" };
  ArrayList<String> user = new ArrayList<>();

  public FrameSetting(UserModel userModel) {
    genderID = userModel.getGender();
    passWord = userModel.getPassword();
    this.userModel = userModel;
    user.add(userModel.getName());
    user.add(null);
    user.add(userModel.getEmail());
    user.add(userModel.getPhone());
    user.add(String.valueOf(userModel.getGender()));
    user.add(userModel.getAddress());
    this.setPreferredSize(new Dimension(300, 400));
    this.setSize(new Dimension(400, 600));
    initComponents();
    setVisible(true);
    setLocationRelativeTo(null);
    this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
  }

  public void initComponents() {
    Scroll = new JScrollPane();
    Panel = new JPanel();
    Content = new JPanel();
    NameLabel = new JLabel("Thông tin tài khoản");
    Buttons = new JPanel();
    ButtonBack = new JButton("Thoát");
    ButtonSave = new JButton("Lưu");
    comboBoxGender = new JComboBox<>(genders);
    comboBoxGender.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String selectedGender = (String) comboBoxGender.getSelectedItem();
        if ("Nam".equals(selectedGender)) {
          selectedGenderId = 1;
        } else if ("Nữ".equals(selectedGender)) {
          selectedGenderId = 0;
        } else {
          selectedGenderId = -1;
        }
      }
    });

    NameLabel.setFont(new Font("Segoe UI", 1, 18));
    add(NameLabel, BorderLayout.NORTH);

    Panel.setLayout(new BorderLayout());

    Content.setLayout(new GridLayout(0, 1));
    for (int i = 0; i < 6; i++) {
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
      if (i != 4) {
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
            } else {
              edit = true;
            }
            value[index] = Value.getText();
            if (index == 0) {
              checkValidation = Validation.isValidName(value[index]);
              if (checkValidation) {
                userModel.setName(value[index]);
              }
            } else if (index == 1) {
              checkValidation = Validation.isValidPassword(value[index]);
              if (checkValidation) {
                userModel.setPassword(PasswordUtils.hashPassword(value[index]));
              }
              if (Value.getText() == null || Value.getText().trim().isEmpty())
                checkValidation = true;
            } else if (index == 2) {
              checkValidation = Validation.isValidEmail(value[index]);
              if (checkValidation) {
                userModel.setEmail(value[index]);
              }
            } else if (index == 3) {
              checkValidation = Validation.isValidPhoneNumber(value[index]);
              if (checkValidation) {
                userModel.setPhone(value[index]);
              }
            } else if (index == 5) {
              checkValidation = Validation.isValidAddress(value[index]);
              if (checkValidation) {
                userModel.setAddress(value[index]);
              }
            }
            if (!checkValidation) {
              JOptionPane.showMessageDialog(null, name[index] + " không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
              Value.setBorder(BorderFactory.createLineBorder(Color.RED));
            } else {
              Value.setBorder(null);
            }
          }
        });
        IndexPanel.add(Value, BorderLayout.CENTER);
      } else {
        comboBoxGender.setPreferredSize(new java.awt.Dimension(300, 40));
        comboBoxGender.addActionListener(e -> {
          String selectedGender = (String) comboBoxGender.getSelectedItem();
          selectedGenderId = switch (selectedGender) {
            case "Nam" -> 1;
            case "Nữ" -> 0;
            default -> -1;
          };
        });

        int selectedGenderId = userModel.getGender();
        if (selectedGenderId == 1) {
          comboBoxGender.setSelectedItem("Nam");
        } else if (selectedGenderId == 0) {
          comboBoxGender.setSelectedItem("Nữ");
        } else {

        }
        IndexPanel.add(comboBoxGender, BorderLayout.CENTER);
      }
      IndexPanel.add(Invalid, BorderLayout.SOUTH);
      Invalid.setForeground(Color.RED);

      Content.add(IndexPanel);
    }
    Panel.add(Content, BorderLayout.CENTER);

    Scroll.setViewportView(Panel);

    add(Scroll, BorderLayout.CENTER);

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
      if (checkValidation) {
        if (edit == false && genderID == selectedGenderId) {
          JOptionPane.showMessageDialog(null, "Không có thông tin thay đổi", "Thông báo",
              JOptionPane.INFORMATION_MESSAGE);
        } else {
          String input = JOptionPane.showInputDialog(null, "Nhập mật khẩu");
          // int option = JOptionPane.showConfirmDialog(null, "Lưu thay đổi?", "Xác nhận",
          // JOptionPane.YES_NO_OPTION);
          Boolean resuilt = PasswordUtils.checkPassword(input, passWord);
          if (resuilt) {
            userModel.setGender(selectedGenderId);
            int result = UserBUS.getInstance().updateModel(userModel);
            if (result == 1) {
              JOptionPane.showMessageDialog(null, "Sửa thông tin tài khoản thành công", "Thông báo",
                  JOptionPane.INFORMATION_MESSAGE);
              UserBUS.getInstance().refreshData();
              dispose();
            }
          } else {
            JOptionPane.showMessageDialog(null, "Mật khẩu không đúng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
          }
        }
      } else {
        JOptionPane.showMessageDialog(null, "Thông tin không hợp lệ", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
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
  private JComboBox<String> comboBoxGender;
  private boolean edit = false;
  private boolean checkValidation = true;
  private int selectedGenderId;
  private int genderID;
  private String passWord;
}
