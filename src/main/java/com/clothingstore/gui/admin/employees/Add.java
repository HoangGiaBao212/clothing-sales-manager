package com.clothingstore.gui.admin.employees;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.clothingstore.bus.RoleBUS;
import com.clothingstore.bus.RolePermissionBUS;
import com.clothingstore.bus.UserBUS;
import com.clothingstore.bus.UserPermissionBUS;
import com.clothingstore.enums.UserPermissionStatus;
import com.clothingstore.models.RoleModel;
import com.clothingstore.models.RolePermissionModel;
import com.clothingstore.models.UserModel;
import com.clothingstore.models.UserPermissionModel;

public class Add extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    public JTextField textField_id;
    public JTextField textField_phone;
    public JTextField textField_username;
    public JTextField textField_email;
    public JTextField textField_name;
    public JTextField textField_address;
    private Employees employeeGUI = new Employees();
    private RoleBUS roleBus = RoleBUS.getInstance();
    private List<RolePermissionModel> rolePermissionList = RolePermissionBUS.getInstance().getAllModels();
    private JComboBox<String> comboBox_gender;
    private JComboBox<String> comboBox_role;
    private JLabel lbl_img;
    private String imagePath;
    private JPanel panel_image;

    public Add() {
        initComponents();
    }

    public void initComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 647, 577);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setLocationRelativeTo(null);
        setResizable(false);

        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(500, 50));
        panel.setBackground(new Color(0, 38, 77));
        contentPane.add(panel, BorderLayout.NORTH);

        JLabel lblNewLabel = new JLabel("Thêm nhân viên");
        lblNewLabel.setForeground(new Color(255, 255, 255));
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        panel.add(lblNewLabel);

        JPanel panel_1 = new JPanel();
        contentPane.add(panel_1, BorderLayout.CENTER);
        panel_1.setLayout(new GridLayout(1, 0, 0, 0));

        JPanel panel_3 = new JPanel();
        panel_3.setBorder(new TitledBorder(null, "Thông tin nhân viên",
                TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel_1.add(panel_3);
        GridBagLayout gbl_panel_3 = new GridBagLayout();
        gbl_panel_3.columnWidths = new int[] { 78, 180, 0, 75, 166, 0, 0, 0 };
        gbl_panel_3.rowHeights = new int[] { 0, 43, 37, 39, 36, 41, 55, 0, 0 };
        gbl_panel_3.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
        gbl_panel_3.rowWeights = new double[] { 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
        panel_3.setLayout(gbl_panel_3);

        JLabel lbl_id = new JLabel("ID");
        GridBagConstraints gbc_lbl_id = new GridBagConstraints();
        gbc_lbl_id.insets = new Insets(0, 0, 5, 5);
        gbc_lbl_id.gridx = 0;
        gbc_lbl_id.gridy = 1;
        panel_3.add(lbl_id, gbc_lbl_id);

        textField_id = new JTextField();
        textField_id.setEditable(false);
        GridBagConstraints gbc_textField_id = new GridBagConstraints();
        gbc_textField_id.insets = new Insets(0, 0, 5, 5);
        gbc_textField_id.fill = GridBagConstraints.BOTH;
        gbc_textField_id.gridx = 1;
        gbc_textField_id.gridy = 1;
        panel_3.add(textField_id, gbc_textField_id);
        textField_id.setColumns(10);

        JLabel lbl_phone = new JLabel("Số điện thoại");
        GridBagConstraints gbc_lbl_phone = new GridBagConstraints();
        gbc_lbl_phone.insets = new Insets(0, 0, 5, 5);
        gbc_lbl_phone.gridx = 3;
        gbc_lbl_phone.gridy = 1;
        panel_3.add(lbl_phone, gbc_lbl_phone);

        textField_phone = new JTextField();
        textField_phone.setColumns(10);
        GridBagConstraints gbc_textField_phone = new GridBagConstraints();
        gbc_textField_phone.insets = new Insets(0, 0, 5, 5);
        gbc_textField_phone.fill = GridBagConstraints.BOTH;
        gbc_textField_phone.gridx = 4;
        gbc_textField_phone.gridy = 1;
        panel_3.add(textField_phone, gbc_textField_phone);

        JLabel lbl_username = new JLabel("Username");
        GridBagConstraints gbc_lbl_username = new GridBagConstraints();
        gbc_lbl_username.insets = new Insets(0, 0, 5, 5);
        gbc_lbl_username.gridx = 0;
        gbc_lbl_username.gridy = 2;
        panel_3.add(lbl_username, gbc_lbl_username);

        textField_username = new JTextField();
        textField_username.setColumns(10);
        GridBagConstraints gbc_textField_username = new GridBagConstraints();
        gbc_textField_username.insets = new Insets(0, 0, 5, 5);
        gbc_textField_username.fill = GridBagConstraints.BOTH;
        gbc_textField_username.gridx = 1;
        gbc_textField_username.gridy = 2;
        panel_3.add(textField_username, gbc_textField_username);

        JLabel lbl_gender = new JLabel("Giới tính");
        GridBagConstraints gbc_lbl_gender = new GridBagConstraints();
        gbc_lbl_gender.insets = new Insets(0, 0, 5, 5);
        gbc_lbl_gender.gridx = 3;
        gbc_lbl_gender.gridy = 2;
        panel_3.add(lbl_gender, gbc_lbl_gender);

        comboBox_gender = new JComboBox<>();
        comboBox_gender.setModel(new DefaultComboBoxModel<>(new String[] { "Nam", "Nữ" }));
        GridBagConstraints gbc_comboBox_role = new GridBagConstraints();
        gbc_comboBox_role.insets = new Insets(0, 0, 5, 5);
        gbc_comboBox_role.fill = GridBagConstraints.BOTH;
        gbc_comboBox_role.gridx = 4;
        gbc_comboBox_role.gridy = 2;
        panel_3.add(comboBox_gender, gbc_comboBox_role);

        JLabel lbl_email = new JLabel("Email");
        GridBagConstraints gbc_lbl_email = new GridBagConstraints();
        gbc_lbl_email.insets = new Insets(0, 0, 5, 5);
        gbc_lbl_email.gridx = 0;
        gbc_lbl_email.gridy = 3;
        panel_3.add(lbl_email, gbc_lbl_email);

        textField_email = new JTextField();
        textField_email.setColumns(10);
        GridBagConstraints gbc_textField_email = new GridBagConstraints();
        gbc_textField_email.insets = new Insets(0, 0, 5, 5);
        gbc_textField_email.fill = GridBagConstraints.BOTH;
        gbc_textField_email.gridx = 1;
        gbc_textField_email.gridy = 3;
        panel_3.add(textField_email, gbc_textField_email);

        JLabel lbl_role = new JLabel("Chức vụ");
        GridBagConstraints gbc_lbl_role = new GridBagConstraints();
        gbc_lbl_role.insets = new Insets(0, 0, 5, 5);
        gbc_lbl_role.gridx = 3;
        gbc_lbl_role.gridy = 3;
        panel_3.add(lbl_role, gbc_lbl_role);

        comboBox_role = new JComboBox<>();
        for (RoleModel role : roleBus.getAllModels()) {
            if (role.getId() > 1) {
                comboBox_role.addItem(role.getName());
            }
        }
        GridBagConstraints gbc_comboBox_1 = new GridBagConstraints();
        gbc_comboBox_1.insets = new Insets(0, 0, 5, 5);
        gbc_comboBox_1.fill = GridBagConstraints.BOTH;
        gbc_comboBox_1.gridx = 4;
        gbc_comboBox_1.gridy = 3;
        panel_3.add(comboBox_role, gbc_comboBox_1);

        JLabel lbl_name = new JLabel("Tên nhân viên");
        GridBagConstraints gbc_lbl_name = new GridBagConstraints();
        gbc_lbl_name.insets = new Insets(0, 0, 5, 5);
        gbc_lbl_name.gridx = 0;
        gbc_lbl_name.gridy = 4;
        panel_3.add(lbl_name, gbc_lbl_name);

        textField_name = new JTextField();
        textField_name.setColumns(10);
        GridBagConstraints gbc_textField_name = new GridBagConstraints();
        gbc_textField_name.insets = new Insets(0, 0, 5, 5);
        gbc_textField_name.fill = GridBagConstraints.BOTH;
        gbc_textField_name.gridx = 1;
        gbc_textField_name.gridy = 4;
        panel_3.add(textField_name, gbc_textField_name);

        JLabel lbl_address = new JLabel("Địa chỉ");
        GridBagConstraints gbc_lbl_address = new GridBagConstraints();
        gbc_lbl_address.insets = new Insets(0, 0, 5, 5);
        gbc_lbl_address.gridx = 3;
        gbc_lbl_address.gridy = 4;
        panel_3.add(lbl_address, gbc_lbl_address);

        textField_address = new JTextField();
        GridBagConstraints gbc_textField_address = new GridBagConstraints();
        gbc_textField_address.insets = new Insets(0, 0, 5, 5);
        gbc_textField_address.fill = GridBagConstraints.BOTH;
        gbc_textField_address.gridx = 4;
        gbc_textField_address.gridy = 4;
        panel_3.add(textField_address, gbc_textField_address);
        textField_address.setColumns(10);

        JLabel lbl_image = new JLabel("Hình ảnh");
        GridBagConstraints gbc_lbl_image = new GridBagConstraints();
        gbc_lbl_image.insets = new Insets(0, 0, 5, 5);
        gbc_lbl_image.gridx = 3;
        gbc_lbl_image.gridy = 5;
        panel_3.add(lbl_image, gbc_lbl_image);

        panel_image = new JPanel();
        panel_image.setBorder(new LineBorder(new Color(0, 0, 0)));
        GridBagConstraints gbc_panel_image = new GridBagConstraints();
        gbc_panel_image.gridheight = 2;
        gbc_panel_image.insets = new Insets(0, 0, 5, 5);
        gbc_panel_image.fill = GridBagConstraints.BOTH;
        gbc_panel_image.gridx = 4;
        gbc_panel_image.gridy = 5;
        panel_3.add(panel_image, gbc_panel_image);
        panel_image.setLayout(new GridLayout(0, 1, 0, 0));

        lbl_img = new JLabel("");
        lbl_img.setHorizontalAlignment(SwingConstants.CENTER);
        panel_image.add(lbl_img);

        JButton btn_Upload = new JButton("Tải hình ảnh");
        GridBagConstraints gbc_btn_Upload = new GridBagConstraints();
        gbc_btn_Upload.fill = GridBagConstraints.BOTH;
        gbc_btn_Upload.insets = new Insets(0, 0, 5, 5);
        gbc_btn_Upload.gridx = 5;
        gbc_btn_Upload.gridy = 5;
        panel_3.add(btn_Upload, gbc_btn_Upload);
        btn_Upload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setAcceptAllFileFilterUsed(false);
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "JPG & GIF Images", "jpg", "gif", "png");
                fileChooser.setFileFilter(filter);
                int returnVal = fileChooser.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                    imagePath = filePath;
                    ImageIcon originalIcon = new ImageIcon(filePath);
                    Image originalImage = originalIcon.getImage();
                    Image resizedImage = originalImage.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
                    ImageIcon resizedIcon = new ImageIcon(resizedImage);
                    lbl_img.setIcon(resizedIcon);
                }
            }
        });

        JPanel panel_Model = new JPanel();
        panel_Model.setPreferredSize(new Dimension(500, 100));
        panel_Model.setBorder(new TitledBorder(null, "Các chức năng",
                TitledBorder.LEADING, TitledBorder.TOP, null, null));
        contentPane.add(panel_Model, BorderLayout.SOUTH);

        JButton btnAdd = new JButton("Thêm");
        btnAdd.setPreferredSize(new Dimension(100, 30));
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addEmployee();
                employeeGUI.updateDTFromList();
            }
        });
        panel_Model.add(btnAdd);

        JButton btnReset = new JButton("Reset");
        btnReset.setPreferredSize(new Dimension(100, 30));
        btnReset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }

        });
        panel_Model.add(btnReset);

        JButton btnCancel = new JButton("Hủy");
        btnCancel.setPreferredSize(new Dimension(100, 30));
        panel_Model.add(btnCancel);
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Window window = SwingUtilities.getWindowAncestor((Component) e.getSource());
                if (window != null) {
                    window.dispose();
                }
            }
        });

        JPanel panel_4 = new JPanel();
        panel_Model.add(panel_4);
        panel_4.setLayout(new GridLayout(1, 0, 0, 0));
    }

    public void clearForm() {
        textField_id.setText("");
        textField_username.setText("");
        textField_email.setText("");
        textField_name.setText("");
        textField_phone.setText("");
        comboBox_gender.setSelectedItem("Nam");
        comboBox_role.setSelectedIndex(-1);
        textField_address.setText("");
        lbl_img.setIcon(null);
    }

    public void addEmployee() {
        String username = textField_username.getText() + "";
        String email = textField_email.getText() + "";
        String name = textField_name.getText() + "";
        String phone = textField_phone.getText() + "";
        String genderCombobox = comboBox_gender.getSelectedItem() + "";
        String role = comboBox_role.getSelectedItem() + "";
        int roleID = role.toLowerCase().equals("manager") ? 2 : 3;
        String address = textField_address.getText() + "";

        int gender = genderCombobox.equals("Nam") ? 1 : 0;

        UserModel newEmployee = new UserModel();
        newEmployee.setUsername(username);
        newEmployee.setPassword("User12345");
        newEmployee.setEmail(email);
        newEmployee.setName(name);
        newEmployee.setPhone(phone);
        newEmployee.setGender(gender);
        newEmployee.setRoleId(roleID);
        newEmployee.setAddress(address);
        newEmployee.setImage(imagePath);
        try {
            int newUserID = UserBUS.getInstance().addModel(newEmployee);
            if (newUserID == 1) {
                JOptionPane.showMessageDialog(null, "Thêm thành công", "Success", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
                setVisible(false);
            }
            UserBUS.getInstance().refreshData();
            List<UserModel> allUserModels = UserBUS.getInstance().getAllModels();
            UserModel lastUserAdded = allUserModels.get(allUserModels.size() - 1);
            for (int i = 1; i <= 7; i++) {
                UserPermissionModel userPermissionModel = new UserPermissionModel();
                userPermissionModel.setUserId(lastUserAdded.getId());
                userPermissionModel.setPermissionId(i);
                userPermissionModel.setStatus(UserPermissionStatus.INACTIVE);
                for (RolePermissionModel rolePermissionModel : rolePermissionList) {
                    if (rolePermissionModel.getPermissionId() == i && rolePermissionModel.getRoleId() == roleID) {
                        userPermissionModel.setStatus(UserPermissionStatus.ACTIVE);
                    }
                }
                UserPermissionBUS.getInstance().addModel(userPermissionModel);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
