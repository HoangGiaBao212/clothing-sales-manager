package com.clothingstore.gui.admin.employees;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.clothingstore.bus.RoleBUS;
import com.clothingstore.bus.UserBUS;
import com.clothingstore.enums.UserStatus;
import com.clothingstore.models.RoleModel;
import com.clothingstore.models.UserModel;

public class Edit extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    public JTextField textField_id;
    public JTextField textField_phone;
    public JTextField textField_username;
    public JTextField textField_email;
    public JTextField textField_name;
    public JTextField textField_address;
    public JComboBox comboBox_role;
    public JComboBox comboBox_gender;
    private RoleBUS roleBus = RoleBUS.getInstance();
    private UserBUS userBus = UserBUS.getInstance();
    public JLabel lbl_img;
    private String imagePath;
    private boolean isImageChanged = false;

    public Edit(){
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
        panel.setPreferredSize(new Dimension(500,50));
        panel.setBackground(new Color(0, 38, 77));
        contentPane.add(panel, BorderLayout.NORTH);

        JLabel lblNewLabel = new JLabel("Sửa nhân viên");
        lblNewLabel.setForeground(new Color(255, 255, 255));
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        panel.add(lblNewLabel);

        JPanel panel_1 = new JPanel();
        contentPane.add(panel_1, BorderLayout.CENTER);
        panel_1.setLayout(new GridLayout(1, 0, 0, 0));

        JPanel panel_3 = new JPanel();
        panel_3.setForeground(new Color(255, 255, 255));
        panel_3.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Employee information", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 255, 255)));
        panel_1.add(panel_3);
        GridBagLayout gbl_panel_3 = new GridBagLayout();
        gbl_panel_3.columnWidths = new int[]{78, 180, 0, 75, 166, 0, 0, 0};
        gbl_panel_3.rowHeights = new int[]{0, 43, 37, 39, 36, 41, 51, 0, 0};
        gbl_panel_3.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
        gbl_panel_3.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
        panel_3.setLayout(gbl_panel_3);

        JLabel lbl_ID = new JLabel("ID");
        lbl_ID.setForeground(new Color(0, 0, 0));
        GridBagConstraints gbc_lbl_ID = new GridBagConstraints();
        gbc_lbl_ID.insets = new Insets(0, 0, 5, 5);
        gbc_lbl_ID.gridx = 0;
        gbc_lbl_ID.gridy = 1;
        panel_3.add(lbl_ID, gbc_lbl_ID);

        textField_id = new JTextField();
        textField_id.setEditable(false);
        GridBagConstraints gbc_textField_id = new GridBagConstraints();
        gbc_textField_id.insets = new Insets(0, 0, 5, 5);
        gbc_textField_id.fill = GridBagConstraints.BOTH;
        gbc_textField_id.gridx = 1;
        gbc_textField_id.gridy = 1;
        panel_3.add(textField_id, gbc_textField_id);
        textField_id.setColumns(10);

        JLabel lbl_Phone = new JLabel("Số điện thoại");
        lbl_Phone.setForeground(new Color(0, 0, 0));
        GridBagConstraints gbc_lbl_Phone = new GridBagConstraints();
        gbc_lbl_Phone.insets = new Insets(0, 0, 5, 5);
        gbc_lbl_Phone.gridx = 3;
        gbc_lbl_Phone.gridy = 1;
        panel_3.add(lbl_Phone, gbc_lbl_Phone);

        textField_phone = new JTextField();
        textField_phone.setColumns(10);
        GridBagConstraints gbc_textField_phone = new GridBagConstraints();
        gbc_textField_phone.insets = new Insets(0, 0, 5, 5);
        gbc_textField_phone.fill = GridBagConstraints.BOTH;
        gbc_textField_phone.gridx = 4;
        gbc_textField_phone.gridy = 1;
        panel_3.add(textField_phone, gbc_textField_phone);

        JLabel lbl_Username = new JLabel("Username");
        lbl_Username.setForeground(new Color(0, 0, 0));
        GridBagConstraints gbc_lbl_Username = new GridBagConstraints();
        gbc_lbl_Username.insets = new Insets(0, 0, 5, 5);
        gbc_lbl_Username.gridx = 0;
        gbc_lbl_Username.gridy = 2;
        panel_3.add(lbl_Username, gbc_lbl_Username);

        textField_username = new JTextField();
        textField_username.setColumns(10);
        GridBagConstraints gbc_textField_username = new GridBagConstraints();
        gbc_textField_username.insets = new Insets(0, 0, 5, 5);
        gbc_textField_username.fill = GridBagConstraints.BOTH;
        gbc_textField_username.gridx = 1;
        gbc_textField_username.gridy = 2;
        panel_3.add(textField_username, gbc_textField_username);

        JLabel lbl_Gender = new JLabel("Giới tính");
        lbl_Gender.setForeground(new Color(0, 0, 0));
        GridBagConstraints gbc_lbl_Gender = new GridBagConstraints();
        gbc_lbl_Gender.insets = new Insets(0, 0, 5, 5);
        gbc_lbl_Gender.gridx = 3;
        gbc_lbl_Gender.gridy = 2;
        panel_3.add(lbl_Gender, gbc_lbl_Gender);

        comboBox_gender = new JComboBox();
        comboBox_gender.setModel(new DefaultComboBoxModel(new String[] {"Nam", "Nữ"}));
        GridBagConstraints gbc_comboBox_role = new GridBagConstraints();
        gbc_comboBox_role.insets = new Insets(0, 0, 5, 5);
        gbc_comboBox_role.fill = GridBagConstraints.BOTH;
        gbc_comboBox_role.gridx = 4;
        gbc_comboBox_role.gridy = 2;
        panel_3.add(comboBox_gender, gbc_comboBox_role);

        JLabel lbl_Email = new JLabel("Email");
        lbl_Email.setForeground(new Color(0, 0, 0));
        GridBagConstraints gbc_lbl_Email = new GridBagConstraints();
        gbc_lbl_Email.insets = new Insets(0, 0, 5, 5);
        gbc_lbl_Email.gridx = 0;
        gbc_lbl_Email.gridy = 3;
        panel_3.add(lbl_Email, gbc_lbl_Email);

        textField_email = new JTextField();
        textField_email.setColumns(10);
        GridBagConstraints gbc_textField_email = new GridBagConstraints();
        gbc_textField_email.insets = new Insets(0, 0, 5, 5);
        gbc_textField_email.fill = GridBagConstraints.BOTH;
        gbc_textField_email.gridx = 1;
        gbc_textField_email.gridy = 3;
        panel_3.add(textField_email, gbc_textField_email);

        JLabel lbl_Role = new JLabel("Chức vụ");
        lbl_Role.setForeground(new Color(0, 0, 0));
        GridBagConstraints gbc_lbl_Role = new GridBagConstraints();
        gbc_lbl_Role.insets = new Insets(0, 0, 5, 5);
        gbc_lbl_Role.gridx = 3;
        gbc_lbl_Role.gridy = 3;
        panel_3.add(lbl_Role, gbc_lbl_Role);

        comboBox_role= new JComboBox();
        GridBagConstraints gbc_comboBox_1 = new GridBagConstraints();
        gbc_comboBox_1.insets = new Insets(0, 0, 5, 5);
        gbc_comboBox_1.fill = GridBagConstraints.BOTH;
        gbc_comboBox_1.gridx = 4;
        gbc_comboBox_1.gridy = 3;
        panel_3.add(comboBox_role, gbc_comboBox_1);
        for (RoleModel role : roleBus.getAllModels()) {
            if(role.getId() > 1) {
                comboBox_role.addItem(role.getName());
            }
        }

        JLabel lbl_Name = new JLabel("Tên nhân viên");
        lbl_Name.setForeground(new Color(0, 0, 0));
        GridBagConstraints gbc_lbl_Name = new GridBagConstraints();
        gbc_lbl_Name.insets = new Insets(0, 0, 5, 5);
        gbc_lbl_Name.gridx = 0;
        gbc_lbl_Name.gridy = 4;
        panel_3.add(lbl_Name, gbc_lbl_Name);

        textField_name = new JTextField();
        textField_name.setColumns(10);
        GridBagConstraints gbc_textField_name = new GridBagConstraints();
        gbc_textField_name.insets = new Insets(0, 0, 5, 5);
        gbc_textField_name.fill = GridBagConstraints.BOTH;
        gbc_textField_name.gridx = 1;
        gbc_textField_name.gridy = 4;
        panel_3.add(textField_name, gbc_textField_name);

        JLabel lbl_Address = new JLabel("Địa chỉ");
        lbl_Address.setForeground(new Color(0, 0, 0));
        GridBagConstraints gbc_lbl_Address = new GridBagConstraints();
        gbc_lbl_Address.insets = new Insets(0, 0, 5, 5);
        gbc_lbl_Address.gridx = 3;
        gbc_lbl_Address.gridy = 4;
        panel_3.add(lbl_Address, gbc_lbl_Address);

        textField_address = new JTextField();
        GridBagConstraints gbc_textField_address = new GridBagConstraints();
        gbc_textField_address.insets = new Insets(0, 0, 5, 5);
        gbc_textField_address.fill = GridBagConstraints.BOTH;
        gbc_textField_address.gridx = 4;
        gbc_textField_address.gridy = 4;
        panel_3.add(textField_address, gbc_textField_address);
        textField_address.setColumns(10);

        JLabel lbl_Image = new JLabel("Hình ảnh");
        lbl_Image.setForeground(new Color(0, 0, 0));
        GridBagConstraints gbc_lbl_Image = new GridBagConstraints();
        gbc_lbl_Image.insets = new Insets(0, 0, 5, 5);
        gbc_lbl_Image.gridx = 3;
        gbc_lbl_Image.gridy = 5;
        panel_3.add(lbl_Image, gbc_lbl_Image);

        JPanel panel_image = new JPanel();
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

        JButton btn_upload = new JButton("Tải hình ảnh");
        GridBagConstraints gbc_btn_upload = new GridBagConstraints();
        gbc_btn_upload.fill = GridBagConstraints.BOTH;
        gbc_btn_upload.insets = new Insets(0, 0, 5, 5);
        gbc_btn_upload.gridx = 5;
        gbc_btn_upload.gridy = 5;
        panel_3.add(btn_upload, gbc_btn_upload);
        btn_upload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser file = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "JPG & GIF Images", "jpg", "gif", "png");
                file.setFileFilter(filter);
                int returnVal = file.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    String filePath = file.getSelectedFile().getAbsolutePath();
                    imagePath = filePath;
                    ImageIcon originalIcon = new ImageIcon(filePath);
                    Image originalImage = originalIcon.getImage();
                    Image resizedImage = originalImage.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
                    ImageIcon resizedIcon = new ImageIcon(resizedImage);
                    lbl_img.setIcon(resizedIcon);

                    isImageChanged = true;
                }
            }
        });

        JPanel panel_Model = new JPanel();
        panel_Model.setPreferredSize(new Dimension(500,100));
        panel_Model.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Button List", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        contentPane.add(panel_Model, BorderLayout.SOUTH);

        JButton btnAdd = new JButton("Sửa");
        btnAdd.setPreferredSize(new Dimension(100,30));
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateEmployee();
            }
        });
        panel_Model.add(btnAdd);

        JButton btnCancel = new JButton("Hủy");
        btnCancel.setPreferredSize(new Dimension(100,30));
        panel_Model.add(btnCancel);
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Window window = SwingUtilities.getWindowAncestor((Component)e.getSource());
                if (window != null) {
                    window.dispose();
                }
            }
        });

        JPanel panel_4 = new JPanel();
        panel_Model.add(panel_4);
        panel_4.setLayout(new GridLayout(1, 0, 0, 0));
    }

    public void updateEmployee() {
        int id = Integer.parseInt(textField_id.getText()+"");
        String username = textField_username.getText()+"";
        String email = textField_email.getText()+"";
        String name = textField_name.getText()+"";
        String phone = textField_phone.getText()+"";
        String genderCombobox = comboBox_gender.getSelectedItem()+"";
        String role = comboBox_role.getSelectedItem()+"";
        int roleID = role.toLowerCase().equals("manager") ? 2 : 3;
        String address = textField_address.getText()+"";

        if(imagePath == null) {
            imagePath = Employees.getInstance().getImage();
        }

        int gender = genderCombobox.equals("Nam") ? 1 : 0;
        UserModel userModel = new UserModel(id,username, "User12345", email, name, phone, address, gender, imagePath , roleID, UserStatus.ACTIVE);
        int updatedRows = userBus.updateModel(userModel);
        if(updatedRows > 0) {
            JOptionPane.showMessageDialog(null, "Update thành công");

        }else {
            JOptionPane.showMessageDialog(null, "Update thất bại");
        }
    }

}
