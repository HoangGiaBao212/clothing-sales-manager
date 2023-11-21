package com.clothingstore.gui.login;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import com.clothingstore.bus.UserBUS;
import com.clothingstore.gui.components.HomePage;
import com.clothingstore.models.UserModel;
import services.Authentication;

public class Login extends JFrame {

  private static Login instance;

  private JPanel loginPanel;
  private JButton loginButton;
  private JLabel passwordLabel;
  private JLabel usernameLabel;
  private JPasswordField txtPassWord;
  private JTextField txtusername;

  private JLabel eyeLabel;
  private JLabel jLabel3;
  private JLabel jLabel4;

  public static Login getInstance() {
    if (instance == null) {
      instance = new Login();
    }
    return instance;
  }

  private Login() {
    initComponents();
    getRootPane().setDefaultButton(loginButton);
    setPreferredSize(new Dimension(1000, 550));
    setMinimumSize(new Dimension(1000, 550));
    setBackground(java.awt.Color.WHITE);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    pack();
    setLocationRelativeTo(null);
  }

  private void initComponents() {
    setLayout(new BorderLayout());

    JPanel leftPanel = new JPanel();
    leftPanel.setLayout(new BorderLayout());
    JLabel LogoLabel = new JLabel(new ImageIcon("src/main/java/resources/images/logo1.png"));
    leftPanel.add(LogoLabel, BorderLayout.CENTER);
    leftPanel.setBackground(java.awt.Color.WHITE);
    add(leftPanel, BorderLayout.WEST);

    loginPanel = new JPanel();
    loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.PAGE_AXIS));
    loginPanel.setBackground(new java.awt.Color(173, 216, 230));

    jLabel3 = new JLabel();
    jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 36));
    jLabel3.setForeground(new java.awt.Color(255, 255, 255));
    jLabel3.setAlignmentX(Component.CENTER_ALIGNMENT);
    jLabel3.setText("Login");
    loginPanel.add(jLabel3);

    jLabel4 = new JLabel();
    jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
    jLabel4.setForeground(new java.awt.Color(255, 255, 255));
    jLabel4.setAlignmentX(Component.CENTER_ALIGNMENT);
    jLabel4.setText("Hello! Let's get started");
    loginPanel.add(jLabel4);

    loginPanel.add(Box.createVerticalGlue());
    loginPanel.add(jLabel3);
    loginPanel.add(jLabel4);
    loginPanel.add(Box.createVerticalGlue());

    JPanel grUserName = new JPanel();
    grUserName.setLayout(new FlowLayout());
    grUserName.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 0));
    grUserName.setBackground(new java.awt.Color(173, 216, 230));
    grUserName.setPreferredSize(new Dimension(200, 0));
    usernameLabel = new JLabel();
    usernameLabel.setFont(new java.awt.Font("Segoe UI", 0, 13));
    usernameLabel.setForeground(java.awt.Color.BLACK);
    usernameLabel.setText("Username");
    txtusername = new JTextField();
    txtusername.setColumns(20);
    txtusername.setPreferredSize(new Dimension(200, 30));
    txtusername.setBorder(null);
    JLabel userLabel = new JLabel(new ImageIcon("src/main/java/resources/icons/icons8_user_20px_1.png"));
    userLabel.setBackground(java.awt.Color.BLACK);
    grUserName.add(usernameLabel);
    grUserName.add(txtusername);
    grUserName.add(userLabel);

    JPanel grPassWord = new JPanel();
    grPassWord.setLayout(new FlowLayout());
    grPassWord.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 0));
    grPassWord.setPreferredSize(new Dimension(200, 0));
    grPassWord.setBackground(new java.awt.Color(173, 216, 230));
    passwordLabel = new JLabel();
    passwordLabel.setText("Password");
    passwordLabel.setFont(new java.awt.Font("Segoe UI", 0, 13));
    passwordLabel.setForeground(java.awt.Color.BLACK);
    passwordLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    grPassWord.add(passwordLabel);
    txtPassWord = new JPasswordField();
    txtPassWord.setColumns(20);
    txtPassWord.setPreferredSize(new Dimension(200, 30));
    txtPassWord.setEchoChar('\u25cf');
    txtPassWord.setBorder(null);
    eyeLabel = new JLabel(new ImageIcon("src/main/java/resources/icons/icons8_invisible_20px_1.png"));
    eyeLabel.setBackground(java.awt.Color.BLACK);
    eyeLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        showPasswordButtonActionListener();
      }
    }); // sự kiện khi ấn nút mắt

    loginButton = new JButton("LOGIN");
    loginButton.setBackground(new java.awt.Color(255, 255, 255));
    loginButton.setFont(new java.awt.Font("Segoe UI", 0, 16));
    loginButton.setForeground(new java.awt.Color(0, 0, 128));
    loginButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    loginButton.setMargin(new Insets(6, 120, 6, 120));
    loginButton.addActionListener(loginButtonActionListener);

    grPassWord.add(txtPassWord);
    grPassWord.add(eyeLabel);

    loginPanel.add(grUserName);
    loginPanel.add(grPassWord);
    loginPanel.add(loginButton);
    loginPanel.add(Box.createVerticalGlue());

    add(loginPanel, BorderLayout.CENTER);
  }

  private void showPasswordButtonActionListener() {
    ImageIcon eyeOpenIcon = new ImageIcon("src/main/java/resources/icons/icons8_eye_20px_1.png");
    ImageIcon eyeCloseIcon = new ImageIcon("src/main/java/resources/icons/icons8_invisible_20px_1.png");

    if (txtPassWord != null) {
      if (txtPassWord.getEchoChar() == 0) {
        txtPassWord.setEchoChar('\u25cf');
        eyeLabel.setIcon(eyeCloseIcon);
      } else {
        txtPassWord.setEchoChar('\u0000');
        eyeLabel.setIcon(eyeOpenIcon);
      }
    }
  }

  private ActionListener loginButtonActionListener = e -> {
    try {
      String username = txtusername.getText();
      String password = String.valueOf(txtPassWord.getPassword());
      UserModel user = UserBUS.getInstance().login(username, password);
      Authentication.setCurrentUser(user);
      HomePage homePage = HomePage.getInstance();
      homePage.setVisible(true);
      dispose();
    } catch (Exception exception) {
      JOptionPane.showMessageDialog(
          null,
          exception.getMessage(),
          "Error",
          JOptionPane.ERROR_MESSAGE);
    }
    txtPassWord.setText("");
  };

}
