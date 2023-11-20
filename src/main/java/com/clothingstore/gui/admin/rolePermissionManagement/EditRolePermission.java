package com.clothingstore.gui.admin.rolePermissionManagement;

import javax.swing.*;

import com.clothingstore.bus.PermissionBUS;
import com.clothingstore.bus.PointBUS;
import com.clothingstore.bus.RolePermissionBUS;
import com.clothingstore.enums.RolePermissionStatus;
import com.clothingstore.models.PermissionModel;
import com.clothingstore.models.RolePermissionModel;
import com.clothingstore.models.UserModel;

import java.awt.*;
import java.util.*;
import java.util.List;

public class EditRolePermission extends JPanel {

    private JPanel mainPanel;
    private JLabel NamePanel;
    private JPanel Info;
    private JPanel Products;
    private JScrollPane Scroll;
    private JPanel Product;
    private JPanel HeaderProducts;
    private JButton updateRolePerButton;

    private String name;
    private String value;

    private UserModel userModel;
    private List<RolePermissionModel> rolePermissionList;
    private List<JCheckBox> checkBoxList;

    public EditRolePermission(UserModel userModel) {
        this.userModel = userModel;
        rolePermissionList = RolePermissionBUS.getInstance().searchRolePermission(String.valueOf(userModel.getId()),
                new String[] { "user_id" });
        initComponents();
        handleEvent();
    }

    private void handleEvent() {
        updateRolePerButton.addActionListener(e -> updateRolePermission());
    }

    private void updateRolePermission() {
        int option = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn cập nhật quyền không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
    
        if (option == JOptionPane.YES_OPTION) {
            for (int i = 0; i < checkBoxList.size(); i++) {
                JCheckBox checkBox = checkBoxList.get(i);
                RolePermissionModel rolePermissionModel = rolePermissionList.get(i);
    
                if (checkBox.isSelected()) {
                    rolePermissionModel.setRolePermissionStatus(RolePermissionStatus.ACTIVE);
                } else {
                    rolePermissionModel.setRolePermissionStatus(RolePermissionStatus.INACTIVE);
                }
                RolePermissionBUS.getInstance().updateRolePermission(rolePermissionModel);
            }
    
            JOptionPane.showMessageDialog(this, "Cập nhật quyền thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    

    public EditRolePermission(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public ArrayList<EditRolePermission> getData() {
        ArrayList<EditRolePermission> data = new ArrayList<EditRolePermission>() {
            {
                add(new EditRolePermission("Mã nhân viên", String.valueOf(userModel.getId())));
                add(new EditRolePermission("Tên nhân viên", userModel.getName()));
                add(new EditRolePermission("Số điện thoại", userModel.getPhone()));
                add(new EditRolePermission("Email", userModel.getEmail()));
            }
        };
        return data;
    }

    public void initComponents() {
        updateRolePerButton = new JButton("Update role permissions");
        NamePanel = new JLabel();
        Info = new JPanel();
        Products = new JPanel();
        Scroll = new JScrollPane();
        Product = new JPanel();
        HeaderProducts = new JPanel();
        checkBoxList = new ArrayList<>();
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        setLayout(new BorderLayout());
        setBackground(new Color(153, 194, 255));

        Scroll.setViewportView(mainPanel);
        Scroll.getVerticalScrollBar().setUnitIncrement(30);

        Info.setLayout(new GridLayout(0, 1));

        NamePanel.setText("-- Chi tiết --");
        NamePanel.setHorizontalAlignment(SwingConstants.CENTER);
        NamePanel.setVerticalAlignment(SwingConstants.CENTER);
        NamePanel.setFont(new Font("Segoe UI", 1, 17));
        NamePanel.setPreferredSize(new Dimension(150, 70));

        add(NamePanel, BorderLayout.NORTH);

        for (EditRolePermission UserDetail : getData()) {
            JPanel panel = new JPanel();
            panel.setBackground(Color.WHITE);
            panel.setPreferredSize(new Dimension(60, 60));
            panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
            panel.setLayout(new BorderLayout());

            JLabel Name = new JLabel(UserDetail.name);
            Name.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
            Name.setFont(new Font("Segoe UI", 1, 15));
            panel.add(Name, BorderLayout.WEST);

            JLabel Value = new JLabel(UserDetail.value);
            Value.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 19));
            Value.setFont(new Font("Segoe UI", 0, 15));
            panel.add(Value, BorderLayout.EAST);

            Info.add(panel);
        }
        Products.setLayout(new BorderLayout());

        HeaderProducts.setLayout(new BorderLayout());
        HeaderProducts.setPreferredSize(new Dimension(100, 40));
        JLabel idUser = new JLabel("Các quyền của người dùng có mã nhân viên: " + userModel.getId());
        idUser.setFont(new Font("Segoe UI", Font.BOLD, 20));
        HeaderProducts.add(idUser, BorderLayout.NORTH);
        Products.add(HeaderProducts, BorderLayout.NORTH);
        Product.setLayout(new GridLayout(rolePermissionList.size(), 1));
        for (RolePermissionModel rolePermissionModel : rolePermissionList) {
            PermissionModel permissionModel = PermissionBUS.getInstance()
                    .getModelById(rolePermissionModel.getPermissionId());
            JCheckBox checkBox = new JCheckBox("" + permissionModel.getPermissionName());
            checkBox.setSelected(rolePermissionModel.getRolePermissionStatus() == RolePermissionStatus.ACTIVE);
            checkBoxList.add(checkBox);
            Product.add(checkBox);
        }
        Products.add(Product, BorderLayout.CENTER);
        Products.add(updateRolePerButton, BorderLayout.SOUTH);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 10);

        mainPanel.add(Info, BorderLayout.CENTER);
        mainPanel.add(Products, BorderLayout.SOUTH);
        add(Scroll, BorderLayout.CENTER);
    }

}
