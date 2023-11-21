package com.clothingstore.gui.admin.employees;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import com.clothingstore.bus.ProductBUS;
import com.clothingstore.bus.UserBUS;
import com.clothingstore.enums.UserStatus;
import com.clothingstore.gui.models.NavData;
import com.clothingstore.models.UserModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.table.DefaultTableCellRenderer;
import java.util.List;

public class Employees extends JPanel {
  private static Employees instance;
  private JTextField textField;
  private JPanel employeePanel;
  private JPanel topPanel;
  private JLabel lbl_title;
  private JLabel titleLabel;
  private JPanel panel;
  private JPanel centerPanel;
  private JLabel lblNewLabel_1;
  private JPanel bottomPanel;
  private JPanel panel_table;
  private JTable table;
  private JPanel panel_Model;
  private JButton btnAdd;
  private JScrollPane scrollPane;
  private JButton btnDelete;
  private JButton btnEdit;
  private UserBUS userBUS = UserBUS.getInstance();
  private ProductBUS productBUS = ProductBUS.getInstance();
  private boolean comboBoxSelected = false;
  private JLabel test;
  private JButton btnSearch;
  private String imagePath_2;
  private JButton btnMenu;
  private Container panel_1;

  Color color = new Color(0, 38, 77);

  public static Employees getInstance() {
    if (instance == null) {
      instance = new Employees();
    }
    return instance;
  }



  public Employees() {
    initComponents();
  }

  public void initComponents() {

    employeePanel = new JPanel();

    setLayout(new BorderLayout());
    setSize(new Dimension(1130, 628));
    employeePanel.setBackground(new Color(0, 38, 77));
    employeePanel.setBorder(BorderFactory.createEmptyBorder(1, 20, 1, 5));
    setLayout(new BorderLayout());

    employeePanel.setLayout(new BorderLayout());
    add(employeePanel, BorderLayout.CENTER);

    topPanel = new JPanel();
    topPanel.setPreferredSize(new Dimension(1000, 120));
    topPanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(255, 255, 255)));
    topPanel.setBackground(new Color(0, 38, 77));
    employeePanel.add(topPanel, BorderLayout.NORTH);
    topPanel.setLayout(new GridLayout(0, 1, 0, 0));

    panel_1 = new JPanel();
    panel_1.setBackground(new Color(0, 38, 77));
    topPanel.add(panel_1);
    panel_1.setLayout(new BorderLayout(0, 0));
    
    btnMenu = new JButton();
    btnMenu.setIcon(new ImageIcon(getClass().getResource("/resources/icons/menu.png")));
    btnMenu.setPreferredSize(new Dimension(70,30));
    btnMenu.setBorder(null);
    btnMenu.setBackground(color);
    btnMenu.addActionListener(new NavData().MenuAction());
    panel_1.add(btnMenu, BorderLayout.WEST);


    titleLabel = new JLabel("  Quản lý nhân viên");
    titleLabel.setForeground(new Color(255, 255, 255));
    titleLabel.setFont(new Font("Arial", Font.PLAIN, 30));
    topPanel.add(titleLabel);

    panel = new JPanel();
    panel.setPreferredSize(new Dimension(20, 20));
    panel.setBackground(new Color(0, 38, 77));
    topPanel.add(panel);

    centerPanel = new JPanel();
    centerPanel.setPreferredSize(new Dimension(60, 50));
    centerPanel.setBackground(new Color(0, 38, 77));
    employeePanel.add(centerPanel, BorderLayout.CENTER);
    GridBagLayout gbl_centerPanel = new GridBagLayout();
    gbl_centerPanel.columnWidths = new int[] { 86, 224, 0, 172, 144, 0, 0 };
    gbl_centerPanel.rowHeights = new int[] { 0, 36, 0, 0 };
    gbl_centerPanel.columnWeights = new double[] { 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE };
    gbl_centerPanel.rowWeights = new double[] { 1.0, 0.0, 1.0, Double.MIN_VALUE };
    centerPanel.setLayout(gbl_centerPanel);

    lblNewLabel_1 = new JLabel("Tìm kiếm");
    lblNewLabel_1.setForeground(new Color(255, 255, 255));
    lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 15));
    GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
    gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
    gbc_lblNewLabel_1.gridx = 0;
    gbc_lblNewLabel_1.gridy = 1;
    centerPanel.add(lblNewLabel_1, gbc_lblNewLabel_1);

    textField = new JTextField();
    GridBagConstraints gbc_textField = new GridBagConstraints();
    gbc_textField.insets = new Insets(0, 0, 5, 5);
    gbc_textField.fill = GridBagConstraints.BOTH;
    gbc_textField.gridx = 1;
    gbc_textField.gridy = 1;
    centerPanel.add(textField, gbc_textField);
    textField.setColumns(10);

    btnSearch = new JButton("Tìm kiếm");
    btnSearch.setPreferredSize(new Dimension(100, 30));
    GridBagConstraints gbc_btnSearch = new GridBagConstraints();
    gbc_btnSearch.insets = new Insets(0, 0, 5, 5);
    gbc_btnSearch.gridx = 2;
    gbc_btnSearch.gridy = 1;
    centerPanel.add(btnSearch, gbc_btnSearch);
    btnSearch.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        String searchValue = textField.getText() + "";

        // String[] columnNames =
        // {"id","username","password","email","name","phone","address","gender","image","role_id","status"};
        // String[] columnNames = {"id","username","status","name","email","phone",""};
        String[] columnNames = { "username", "password", "email", "image", "name", "phone", "address", "gender",
            "role_id", "status" };
        List<UserModel> searchResults = userBUS.getInstance().searchModel(searchValue, columnNames);
        showSearchResult(searchResults);
      }

    });

    textField.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
          String searchValue = textField.getText();
          String[] columnNames = { "username", "password", "email", "image", "name", "phone", "address", "gender",
              "role_id", "status" };
          List<UserModel> searchResults = userBUS.getInstance().searchModel(searchValue, columnNames);
          showSearchResult(searchResults);
        }
      }
    });

    bottomPanel = new JPanel();
    bottomPanel.setBackground(new Color(0, 38, 77));
    employeePanel.add(bottomPanel, BorderLayout.SOUTH);
    bottomPanel.setLayout(new BorderLayout());

    panel_table = new JPanel();
    panel_table.setForeground(new Color(0, 38, 77));
    panel_table.setBackground(new Color(0, 38, 77));
    panel_table.setBorder(
        new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
            "Danh sách nhân viên", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 255, 255)));
    panel_table.setPreferredSize(new Dimension(500, 400));
    panel_table.setLayout(new BorderLayout(0, 0));

    table = new JTable();
    table.setModel(new DefaultTableModel(
        new Object[][] {
        },
        new String[] {
            "ID", "Username", "Email", "Tên", "Số điện thoại", "Giới tính", "Hình ảnh", "Chức vụ", "Địa chỉ",
            "Tình trạng", "Reset"
        }) {
      @Override
      public boolean isCellEditable(int row, int column) {
        // Chỉ cho phép chỉnh sửa cột "Status"
        return column == getColumnCount() - 2;
      }
    }

    );
    scrollPane = new JScrollPane(table);
    table.getTableHeader().setReorderingAllowed(false);

    JTableHeader header = table.getTableHeader();
    DefaultTableCellRenderer centerHeaderRenderer = (DefaultTableCellRenderer) header.getDefaultRenderer();
    centerHeaderRenderer.setHorizontalAlignment(SwingConstants.CENTER);

    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

    for (int i = 0; i < table.getColumnCount(); i++) {
      table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
    }

    // set width
    table.setRowHeight(70);
    TableColumn idColumn = table.getColumnModel().getColumn(0);
    TableColumn genderColumn = table.getColumnModel().getColumn(5);
    TableColumn imageColumn = table.getColumnModel().getColumn(6);
    TableColumn roleColumn = table.getColumnModel().getColumn(7);
    TableColumn addressColumn = table.getColumnModel().getColumn(8);
    TableColumn statusColumn = table.getColumnModel().getColumn(9);
    TableColumn resetColumn = table.getColumnModel().getColumn(10);
    imageColumn.setPreferredWidth(40);
    idColumn.setPreferredWidth(10);
    genderColumn.setPreferredWidth(30);
    roleColumn.setPreferredWidth(30);
    statusColumn.setPreferredWidth(30);
    addressColumn.setPreferredWidth(110);
    resetColumn.setPreferredWidth(0);

    panel_table.add(scrollPane);
    bottomPanel.add(panel_table, BorderLayout.NORTH);

    panel_Model = new JPanel();
    panel_Model.setBackground(new Color(0, 38, 77));
    panel_Model.setBorder(
        new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
            "Các chức năng", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 255, 255)));
    panel_Model.setPreferredSize(new Dimension(200, 80));
    panel_table.add(panel_Model, BorderLayout.SOUTH);

    btnAdd = new JButton("Thêm nhân viên");
    btnAdd.setPreferredSize(new Dimension(200, 40));
    panel_Model.add(btnAdd);
    btnAdd.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        Add addEmployee = new Add();
        addEmployee.setVisible(true);

      }
    });

    btnDelete = new JButton("Xóa nhân viên");
    btnDelete.setPreferredSize(new Dimension(200, 40));
    panel_Model.add(btnDelete);
    btnDelete.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
          JOptionPane.showMessageDialog(null, "Bạn chưa chọn dòng muốn xóa", "Thông báo",
              JOptionPane.INFORMATION_MESSAGE);
          return;
        }

        int choice = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa nhân viên này?", "Confirm Deletion",
            JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
          int employeeId = (int) table.getModel().getValueAt(selectedRow, 0);
          deleteEmployee(employeeId);
          updateDTFromList();
        }
      }
    });

    btnEdit = new JButton("Sửa nhân viên");
    btnEdit.setPreferredSize(new Dimension(200, 40));
    panel_Model.add(btnEdit);
    btnEdit.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        int index = table.getSelectedRow();
        if (index == -1) {
          JOptionPane.showMessageDialog(null, "Bạn chưa chọn dòng muốn sửa", "Thông báo",
              JOptionPane.INFORMATION_MESSAGE);
          return;
        } else {
          int modelIndex = table.convertRowIndexToModel(index);
          int id = (int) table.getModel().getValueAt(modelIndex, 0);
          UserBUS.getInstance().refreshData();
          if (UserBUS.getInstance().getModelById(id).getUserStatus() == UserStatus.BANNED) {
            JOptionPane.showMessageDialog(null, "Tài khoản đang bị khóa, không thể chỉnh sửa");
            return;
          }

          showUpdateInfo(id);
        }

      }
    });

    JButton btnRefresh = new JButton("Refresh");
    btnRefresh.setPreferredSize(new Dimension(200, 40));
    panel_Model.add(btnRefresh);
    btnRefresh.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        updateDTFromList();
        textField.setText("");
      }
    });
    updateDTFromList();

    statusHandle(table);
    resetHandle(table);
  }

  public void updateDTFromList() {
    UserBUS.getInstance().refreshData();
    DefaultTableModel model_table = (DefaultTableModel) table.getModel();
    model_table.setRowCount(0);

    JComboBox<String> status = new JComboBox<String>();
    status.addItem("Active");
    status.addItem("Banned");

    DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
    renderer.setHorizontalAlignment(SwingConstants.CENTER);

    for (UserModel user : UserBUS.getInstance().getAllModels()) {
      String textGender = (user.getGender() == 1) ? "Nam" : "Nữ";
      String textRole = null;
      String imagePath = "src\\main\\java\\config\\image\\resetImg.jpg";

      if (user.getRoleId() == 1) {
        textRole = "Admin";
      } else if (user.getRoleId() == 2) {
        textRole = "Manager";
      } else if (user.getRoleId() == 3) {
        textRole = "Employee";
      }

      if (user.getUserStatus() == UserStatus.BANNED) {
        status.setSelectedItem("Banned");
      } else {
        status.setSelectedItem("Active");
      }

      model_table.addRow(new Object[] { user.getId(), user.getUsername() + "",
          user.getEmail() + "", user.getName() + "", user.getPhone() + "", textGender,
          user.getImage(), textRole, user.getAddress(), status.getSelectedItem(), imagePath });

    }

    table.getColumnModel().getColumn(6).setCellRenderer(renderer);
    table.getColumnModel().getColumn(6).setCellRenderer(new ImageRender());

    TableColumn statusColumn = table.getColumnModel().getColumn(9);
    statusColumn.setCellEditor(new DefaultCellEditor(status));

    table.getColumnModel().getColumn(10).setCellRenderer(renderer);
    table.getColumnModel().getColumn(10).setCellRenderer(new ImageRender());

  }

  public void deleteEmployee(int userID) {
    try {
      int deletedRow = UserBUS.getInstance().deleteModel(userID);
      if (deletedRow > 0) {
        JOptionPane.showMessageDialog(null, "Xóa nhân viên thành công");
      }
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Xóa nhân viên thất bại");
    }
  }

  public void showUpdateInfo(int employeeId) {
    Edit editEmployee = new Edit();
    UserModel userModel = userBUS.getInstance().getModelById(employeeId);
    userBUS.refreshData();

    editEmployee.textField_id.setText(String.valueOf(userModel.getId()));
    editEmployee.textField_username.setText(userModel.getUsername());
    editEmployee.textField_email.setText(userModel.getEmail());
    editEmployee.textField_name.setText(userModel.getName());
    editEmployee.textField_phone.setText(userModel.getPhone());
    editEmployee.textField_address.setText(userModel.getAddress());
    if (userModel.getRoleId() == 1) {
      JOptionPane.showMessageDialog(null, "Tài khoản admin không thể sửa");
      return;
    }

    if (userModel.getRoleId() == 2) {
      editEmployee.comboBox_role.setSelectedItem("manager");
    } else {
      editEmployee.comboBox_role.setSelectedItem("employee");
    }

    if (table.getModel().getValueAt(table.getSelectedRow(), 5).equals("Male")) {
      editEmployee.comboBox_role.setSelectedItem("Male");
    } else {
      editEmployee.comboBox_role.setSelectedItem("Female");
    }

    ImageIcon originalIcon = new ImageIcon(userModel.getImage());
    imagePath_2 = userModel.getImage();
    Image originalImage = originalIcon.getImage();
    Image resizedImage = originalImage.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
    ImageIcon resizedIcon = new ImageIcon(resizedImage);
    editEmployee.lbl_img.setIcon(resizedIcon);

    editEmployee.setVisible(true);
  }

  public String getImage() {
    return imagePath_2;
  }

  public void showSearchResult(List<UserModel> search) {
    DefaultTableModel model = (DefaultTableModel) table.getModel();
    model.setRowCount(0);

    for (UserModel user : search) {
      String gender = user.getGender() == 1 ? "Nam" : "Nữ";
      String role = null;
      String imagePath = "src\\main\\java\\config\\image\\resetImg.jpg";

      if (user.getRoleId() == 1) {
        role = "Admin";
      } else if (user.getRoleId() == 2) {
        role = "Manager";
      } else {
        role = "Employee";
      }
      model.addRow(new Object[] {
          user.getId(),
          user.getUsername(),
          user.getEmail(),
          user.getName(),
          user.getPhone(),
          gender,
          user.getImage(),
          role,
          user.getAddress(),
          user.getUserStatus(),
          imagePath
      });
    }
  }

  public void statusHandle(JTable table) {
    DefaultTableModel model = (DefaultTableModel) table.getModel();

    model.addTableModelListener(e -> {
      int row = e.getFirstRow();
      int column = e.getColumn();

      if (column == 9) {
        String selectedStatus = (String) table.getValueAt(row, column);

        int id = (int) table.getValueAt(row, 0);

        String currentStatus = UserBUS.getInstance().getModelById(id).getUserStatus().toString();

        if (currentStatus.toLowerCase().equals(selectedStatus.toLowerCase())) {
          JOptionPane.showMessageDialog(null, "Nhân viên hiện đang " + currentStatus);
          return;
        }

        if (selectedStatus.equals("Banned") && !currentStatus.equals("Banned")) {
          int choice = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn khóa tài khoản nhân viên này?",
              "Xác nhận", JOptionPane.YES_NO_CANCEL_OPTION);
          if (choice == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(null, "Khóa tài khoản thành công");
            UserBUS.getInstance().getModelById(id).setUserStatus(UserStatus.BANNED);
            UserBUS.getInstance().updateModel(UserBUS.getInstance().getModelById(id));
            updateDTFromList();
          } else if (choice == JOptionPane.NO_OPTION || choice == JOptionPane.CANCEL_OPTION
              || JOptionPane.CLOSED_OPTION == -1) {
            UserBUS.getInstance().getModelById(id).setUserStatus(UserStatus.ACTIVE);
            UserBUS.getInstance().updateModel(UserBUS.getInstance().getModelById(id));
            updateDTFromList();
          }
        } else if (selectedStatus.equals("Active") && !currentStatus.equals("Active")) {
          int choice = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn mở khóa tài khoản nhân viên này?",
              "Xác nhận", JOptionPane.YES_NO_CANCEL_OPTION);
          if (choice == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(null, "Mở khóa tài khoản thành công");
            UserBUS.getInstance().getModelById(id).setUserStatus(UserStatus.ACTIVE);
            UserBUS.getInstance().updateModel(UserBUS.getInstance().getModelById(id));
            updateDTFromList();
          } else if (choice == JOptionPane.NO_OPTION || choice == JOptionPane.CANCEL_OPTION
              || JOptionPane.CLOSED_OPTION == -1) {
            UserBUS.getInstance().getModelById(id).setUserStatus(UserStatus.BANNED);
            UserBUS.getInstance().updateModel(UserBUS.getInstance().getModelById(id));
            updateDTFromList();
          }
        }
      }
    });
  }

  public void resetHandle(JTable table) {
    table.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        int column = table.getColumnModel().getColumnIndex("Reset");

        if (table.columnAtPoint(e.getPoint()) == column) {
          int choice = JOptionPane.showConfirmDialog(null, "Bạn có muốn đặt lại mật khẩu mặc định không?");
          if (choice == 0) {
            int row = table.rowAtPoint(e.getPoint());
            int id = (int) table.getValueAt(row, 0);
            UserBUS.getInstance().getModelById(id)
                .setPassword("$2a$12$WXfAuG7UpCVbc3HdDx9q0e7IGZCLtgBRi09yhOcAzmGYTWwX6stqi");
            UserBUS.getInstance().updateModel(UserBUS.getInstance().getModelById(id));
            JOptionPane.showMessageDialog(null, "Reset về mật khẩu mặc định thành công");
          }
        }
      }
    });
  }

}
