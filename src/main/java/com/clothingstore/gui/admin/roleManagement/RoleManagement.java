package com.clothingstore.gui.admin.roleManagement;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import com.clothingstore.bus.PermissionBUS;
import com.clothingstore.bus.RolePermissionBUS;
import com.clothingstore.gui.models.NavData;
import com.clothingstore.models.PermissionModel;
import com.clothingstore.models.RolePermissionModel;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;

public class RoleManagement extends JPanel {
    private static RoleManagement instance;
    private JPanel employeePanel;
    private JPanel topPanel;
    private JLabel lbl_title;
    private JLabel titleLabel;
    private JPanel panel;
    private JPanel centerPanel;
    private JPanel bottomPanel;
    private JPanel panel_table;
    private JPanel panel_Model;
    private JButton btnAdd;
    private JButton btnDelete;
    private JButton btnSearch;
    private JPanel panel_Role;
    private JPanel adminPanel;
    private JPanel panel_manager;
    private JPanel panel_employee;
    private JPanel Panel_roleAdmin;
    private JPanel panel_titleAdmin;
    private JLabel lblNewLabel;
    private JTable table_admin;
    private JScrollPane scrollPane_admin;
    private JScrollPane scrollPane_manager;
    private JTable table_manager;
    private JPanel Panel_roleManager;
    private JPanel panel_titleManager;
    private JLabel lblNewLabel_manager;
    private JPanel Panel_roleEmployee;
    private JPanel panel_titleEmployee;
    private JLabel lblNewLabel_employee;
    private JTable table_employee;
    private JScrollPane scrollPane_employee;
    private RolePermissionBUS rolePermissionBUS = RolePermissionBUS.getInstance();
    private PermissionBUS permissionBUS = PermissionBUS.getInstance();
    JTable test = new JTable();
    private JButton btnEdit;
    private JButton btnRefresh;
    private JPanel panel_1;
    private JButton btnMenu;
    Color color = new Color(0, 38, 77);

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                RoleManagement frame = new RoleManagement();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static RoleManagement getInstance() {
        if (instance == null) {
            instance = new RoleManagement();
        }
        return instance;
    }

    public RoleManagement() {
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

        // ArrayList<MenuData> data = MenuData.getDataAdmin();
        // Menu.getInstance(data).setPreferredSize(new Dimension(150,150));
        // add(Menu.getInstance(data), BorderLayout.WEST);

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
        btnMenu.setPreferredSize(new Dimension(70, 30));
        btnMenu.setBorder(null);
        btnMenu.setBackground(color);
        btnMenu.addActionListener(new NavData().MenuAction());
        panel_1.add(btnMenu, BorderLayout.WEST);

        titleLabel = new JLabel("   Quản lý phân quyền");
        titleLabel.setForeground(new Color(255, 255, 255));
        titleLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
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

        bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(0, 38, 77));
        employeePanel.add(bottomPanel, BorderLayout.CENTER);
        bottomPanel.setLayout(new BorderLayout());

        panel_table = new JPanel();
        panel_table.setForeground(new Color(0, 38, 77));
        panel_table.setBackground(new Color(0, 38, 77));
        panel_table.setBorder(new TitledBorder(
                new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
                "Danh sách người dùng", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 255, 255)));
        panel_table.setPreferredSize(new Dimension(600, 400));
        panel_table.setLayout(new BorderLayout());
        bottomPanel.add(panel_table, BorderLayout.CENTER);

        panel_Role = new JPanel();
        panel_Role.setBackground(new Color(0, 38, 77));
        panel_Role.setLayout(new GridLayout(1, 0));
        panel_table.add(panel_Role, BorderLayout.CENTER);

        // admin
        adminPanel = new JPanel();
        panel_Role.add(adminPanel);
        adminPanel.setLayout(new BorderLayout(0, 0));

        Panel_roleAdmin = new JPanel();
        Panel_roleAdmin.setPreferredSize(new Dimension(50, 50));
        adminPanel.add(Panel_roleAdmin, BorderLayout.CENTER);
        Panel_roleAdmin.setLayout(new GridLayout(1, 0, 0, 0));

        panel_titleAdmin = new JPanel();
        panel_titleAdmin.setBackground(new Color(0, 38, 77));
        panel_titleAdmin.setPreferredSize(new Dimension(50, 50));
        adminPanel.add(panel_titleAdmin, BorderLayout.NORTH);

        lblNewLabel = new JLabel("ADMIN");
        lblNewLabel.setForeground(new Color(255, 255, 255));
        lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        panel_titleAdmin.add(lblNewLabel);

        table_admin = new JTable();
        table_admin.setForeground(new Color(0, 0, 160));
        table_admin.setModel(new DefaultTableModel(
                new Object[][] {
                },
                new String[] {
                        "ID", "Tên quyền"
                }));
        table_admin.setRowHeight(40);
        scrollPane_admin = new JScrollPane(table_admin);

        Panel_roleAdmin.add(scrollPane_admin);

        // manager
        panel_manager = new JPanel();
        panel_manager.setBackground(new Color(128, 128, 128));
        panel_manager.setLayout(new BorderLayout(0, 0));
        panel_Role.add(panel_manager);

        Panel_roleManager = new JPanel();
        Panel_roleManager.setPreferredSize(new Dimension(50, 50));
        panel_manager.add(Panel_roleManager, BorderLayout.CENTER);
        Panel_roleManager.setLayout(new GridLayout(1, 0, 0, 0));

        panel_titleManager = new JPanel();
        panel_titleManager.setBackground(new Color(0, 38, 77));
        panel_titleManager.setPreferredSize(new Dimension(50, 50));
        panel_manager.add(panel_titleManager, BorderLayout.NORTH);

        lblNewLabel_manager = new JLabel("MANAGER");
        lblNewLabel_manager.setForeground(new Color(255, 255, 255));
        lblNewLabel_manager.setHorizontalAlignment(SwingConstants.LEFT);
        lblNewLabel_manager.setFont(new Font("Tahoma", Font.BOLD, 20));
        panel_titleManager.add(lblNewLabel_manager);

        table_manager = new JTable();
        table_manager.setForeground(new Color(0, 0, 160));
        table_manager.setModel(new DefaultTableModel(
                new Object[][] {
                },
                new String[] {
                        "ID", "Tên quyền"
                }));
        table_manager.setRowHeight(40);
        scrollPane_manager = new JScrollPane(table_manager);

        Panel_roleManager.add(scrollPane_manager);

        // employee
        panel_employee = new JPanel();
        panel_employee.setBackground(new Color(128, 0, 255));
        panel_Role.add(panel_employee);
        panel_employee.setLayout(new BorderLayout(0, 0));

        Panel_roleEmployee = new JPanel();
        Panel_roleEmployee.setPreferredSize(new Dimension(50, 50));
        panel_employee.add(Panel_roleEmployee, BorderLayout.CENTER);
        Panel_roleEmployee.setLayout(new GridLayout(1, 0, 0, 0));

        panel_titleEmployee = new JPanel();
        panel_titleEmployee.setBackground(new Color(0, 38, 77));
        panel_titleEmployee.setPreferredSize(new Dimension(50, 50));
        panel_employee.add(panel_titleEmployee, BorderLayout.NORTH);

        lblNewLabel_employee = new JLabel("EMPLOYEE");
        lblNewLabel_employee.setForeground(new Color(255, 255, 255));
        lblNewLabel_employee.setHorizontalAlignment(SwingConstants.LEFT);
        lblNewLabel_employee.setFont(new Font("Tahoma", Font.BOLD, 20));
        panel_titleEmployee.add(lblNewLabel_employee);

        table_employee = new JTable();
        table_employee.setForeground(new Color(0, 0, 160));
        table_employee.setModel(new DefaultTableModel(
                new Object[][] {
                },
                new String[] {
                        "ID", "Tền quyền"
                }));
        table_employee.setRowHeight(40);
        scrollPane_employee = new JScrollPane(table_employee);

        Panel_roleEmployee.add(scrollPane_employee);

        panel_Model = new JPanel();
        panel_Model.setBackground(new Color(0, 38, 77));
        panel_Model.setBorder(new TitledBorder(
                new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
                "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 255, 255)));
        panel_Model.setPreferredSize(new Dimension(200, 80));
        panel_table.add(panel_Model, BorderLayout.SOUTH);
        bottomPanel.add(panel_table, BorderLayout.CENTER);

        btnAdd = new JButton("Thêm");
        btnAdd.setPreferredSize(new Dimension(200, 40));
        panel_Model.add(btnAdd);
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Add addEmployee = new Add();
                addEmployee.setVisible(true);

            }
        });

        btnDelete = new JButton("Xóa");
        btnDelete.setPreferredSize(new Dimension(200, 40));
        panel_Model.add(btnDelete);
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa phân quyền này", "Confirm Deletion",
                        JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    int selectedRowID = handleDeleteButtonClickDelete();
                    deleteRole(selectedRowID);
                    updateRoleFromList();
                    table_admin.clearSelection();
                    table_manager.clearSelection();
                    table_employee.clearSelection();
                }

            }
        });

        btnEdit = new JButton("Sửa");
        btnEdit.setPreferredSize(new Dimension(200, 40));
        panel_Model.add(btnEdit);
        btnEdit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRowID = handleDeleteButtonClickDelete();
                showUpdateInfo(selectedRowID);
                table_admin.clearSelection();
                table_manager.clearSelection();
                table_employee.clearSelection();

            }
        });

        btnRefresh = new JButton("Làm mới");
        btnRefresh.setPreferredSize(new Dimension(200, 40));
        panel_Model.add(btnRefresh);
        btnRefresh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateRoleFromList();

            }
        });

        updateRoleFromList();

    }

    public void updateRoleAdminFromList() {
        rolePermissionBUS.refreshData();
        DefaultTableModel model_tableAdmin = (DefaultTableModel) table_admin.getModel();
        model_tableAdmin.setRowCount(0);

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);

        Set<String> addedPermissions = new HashSet<>();

        for (RolePermissionModel role : rolePermissionBUS.getAllModels()) {
            if (role.getRoleId() == 1) {
                for (PermissionModel permission : permissionBUS.getAllModels()) {
                    if (role.getPermissionId() == permission.getId()) {
                        if (!addedPermissions.contains(permission.getPermissionName())) {
                            model_tableAdmin.addRow(new Object[] { role.getId(), permission.getPermissionName() });
                            addedPermissions.add(permission.getPermissionName());
                        }
                    }
                }
            }
        }
    }

    private int handleDeleteButtonClickDelete() {
        int selectedRowAdmin = table_admin.getSelectedRow();
        int selectedRowManager = table_manager.getSelectedRow();
        int selectedRowEmployee = table_employee.getSelectedRow();

        if (selectedRowAdmin != -1) {
            int adminId = (int) table_admin.getValueAt(selectedRowAdmin, 0);
            return adminId;

        } else if (selectedRowManager != -1) {
            int managerId = (int) table_manager.getValueAt(selectedRowManager, 0);
            return managerId;

        } else if (selectedRowEmployee != -1) {
            int employeeId = (int) table_employee.getValueAt(selectedRowEmployee, 0);
            return employeeId;

        }
        return 0;
    }

    private int handleDeleteButtonClickEdit() {
        int selectedRowAdmin = table_admin.getSelectedRow();
        int selectedRowManager = table_manager.getSelectedRow();
        int selectedRowEmployee = table_employee.getSelectedRow();

        if (selectedRowAdmin != -1) {
            int adminId = (int) table_admin.getValueAt(selectedRowAdmin, 0);
            return adminId;

        } else if (selectedRowManager != -1) {
            int managerId = (int) table_manager.getValueAt(selectedRowManager, 0);
            return managerId;

        } else if (selectedRowEmployee != -1) {
            int employeeId = (int) table_employee.getValueAt(selectedRowEmployee, 0);
            return employeeId;

        }
        return 0;
    }

    public void updateRoleManagerFromList() {
        rolePermissionBUS.refreshData();
        DefaultTableModel model_tableManager = (DefaultTableModel) table_manager.getModel();
        model_tableManager.setRowCount(0);

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);

        Set<String> addedPermissions = new HashSet<>();

        for (RolePermissionModel role : rolePermissionBUS.getAllModels()) {
            if (role.getRoleId() == 2) {
                for (PermissionModel permission : permissionBUS.getAllModels()) {
                    if (role.getPermissionId() == permission.getId()) {
                        if (!addedPermissions.contains(permission.getPermissionName())) {
                            model_tableManager.addRow(new Object[] { role.getId(), permission.getPermissionName() });
                            addedPermissions.add(permission.getPermissionName());
                        }
                    }
                }
            }
        }

    }

    public void updateRoleEmployeeFromList() {
        rolePermissionBUS.refreshData();
        DefaultTableModel model_tableEmployee = (DefaultTableModel) table_employee.getModel();
        model_tableEmployee.setRowCount(0);

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);

        Set<String> addedPermissions = new HashSet<>();

        for (RolePermissionModel role : rolePermissionBUS.getAllModels()) {
            if (role.getRoleId() == 3) {
                for (PermissionModel permission : permissionBUS.getAllModels()) {
                    if (role.getPermissionId() == permission.getId()) {
                        if (!addedPermissions.contains(permission.getPermissionName())) {
                            model_tableEmployee.addRow(new Object[] { role.getId(), permission.getPermissionName() });
                            addedPermissions.add(permission.getPermissionName());
                        }
                    }
                }
            }
        }
    }

    public void updateRoleFromList() {
        updateRoleAdminFromList();
        updateRoleEmployeeFromList();
        updateRoleManagerFromList();
    }

    public void deleteRole(int id) {
        try {
            int deletedRow = RolePermissionBUS.getInstance().deleteRolePermission(id);
            if (deletedRow > 0) {
                JOptionPane.showMessageDialog(null, "Xóa role thành công");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Xóa role thất bại");
        }
    }

    public void showUpdateInfo(int id) {
        Edit editRole = new Edit();
        RolePermissionModel rolePermissionModel = RolePermissionBUS.getInstance().getRolePermissionById(id);
        if(rolePermissionModel == null){
            JOptionPane.showMessageDialog(null,"Bạn chưa chọn dòng muốn sửa");
            return;
        }
        rolePermissionBUS.refreshData();

        editRole.textField_id.setText(String.valueOf(rolePermissionModel.getId()));
        editRole.comboBox_role.setSelectedItem(rolePermissionModel.getRoleId());
        String permissionName = null;
        int permissionID = rolePermissionModel.getPermissionId();
        for (PermissionModel permission : permissionBUS.getAllModels()) {
            if (permissionID == permission.getId()) {
                permissionName = permission.getPermissionName();
                break;
            }
        }
        editRole.comboBox_permission.setSelectedItem(permissionName);

        editRole.setVisible(true);
    }
}
