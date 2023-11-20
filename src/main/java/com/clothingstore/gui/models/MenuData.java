package com.clothingstore.gui.models;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.clothingstore.bus.RolePermissionBUS;
import com.clothingstore.enums.RolePermissionStatus;
import com.clothingstore.gui.admin.employees.Employees;
import com.clothingstore.gui.admin.roleManagement.RoleManagement;
import com.clothingstore.gui.admin.rolePermissionManagement.RolePermissionManagement;
import com.clothingstore.gui.components.HomePage;
import com.clothingstore.gui.components.Menu;
import com.clothingstore.gui.components.Products;
import com.clothingstore.gui.components.ProductsHeader;
import com.clothingstore.gui.components.customerList.Customers;
import com.clothingstore.gui.components.importInvoice.ImportHistory;
import com.clothingstore.gui.components.importInvoice.addImport.AddNewImport;
import com.clothingstore.gui.components.invoicesHistory.HistoryList;
import com.clothingstore.gui.components.invoicesHistory.InvoiceHistory;
import com.clothingstore.gui.components.statistical.Revenue;
import com.clothingstore.gui.employee.Invoice;
import com.clothingstore.gui.employee.Navigation;
import com.clothingstore.gui.login.Login;
import com.clothingstore.models.RolePermissionModel;
import com.clothingstore.models.UserModel;
import services.Authentication;

public class MenuData {
    private String name;
    private ActionListener actionListener;
    private ArrayList<MenuItemData> menuItemData;
    private String icon;
    public Authentication authentication;
    private UserModel currentUser = Authentication.getCurrentUser();
    private List<RolePermissionModel> rolePermissionList = new ArrayList<RolePermissionModel>();

    public MenuData() {

    }

    public MenuData(String name, ArrayList<MenuItemData> menuItemData, ActionListener actionListener, String icon) {
        this.name = name;
        this.actionListener = actionListener;
        this.menuItemData = menuItemData;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public ActionListener getActionListener() {
        return actionListener;
    }

    public ArrayList<MenuItemData> getItemData() {
        return menuItemData;
    }

    public String getIcon() {
        return icon;
    }

    public ArrayList<MenuData> getDataMenu() {
        ArrayList<MenuData> data;
        MenuData menuData = new MenuData();
        data = menuData.getDataMenuByRolePermission();
        return data;
    }

    public ArrayList<MenuData> getDataMenuByRolePermission() {
        rolePermissionList = RolePermissionBUS.getInstance().searchRolePermission("" + currentUser.getId(),
                new String[] { "user_id" });
        ArrayList<MenuData> data = new ArrayList<>();
        for (RolePermissionModel rolePermissionModel : rolePermissionList) {
            if (rolePermissionModel.getRolePermissionStatus() == RolePermissionStatus.ACTIVE) {
                if (rolePermissionModel.getPermissionId() == 1) {
                    data.add(new MenuData("Sản phẩm", null, ProductAction(), "products"));
                }

                if (rolePermissionModel.getPermissionId() == 2) {
                    data.add(new MenuData(
                            "Quản lý nhập hàng",
                            new ArrayList<MenuItemData>() {
                                {
                                    add(new MenuItemData("Danh sách hóa đơn", ImportAction()));
                                    add(new MenuItemData("Thêm hóa đơn", ImportAction()));

                                }
                            },
                            null, "import"));
                }
                if (rolePermissionModel.getPermissionId() == 3) {
                    data.add(new MenuData("Hóa đơn", null, InvoiceHistoryAction(), "invoice"));
                }
                if (rolePermissionModel.getPermissionId() == 4) {
                    data.add(new MenuData(
                            "Quản lý nhân viên",
                            new ArrayList<MenuItemData>() {
                                // {
                                // add(new MenuItemData("Danh sách nhân viên", EmployeeAction()));
                                // add(new MenuItemData("Thêm nhân viên", EmployeeAction()));

                                // }
                            },
                            EmployeeAction(), "employee"));

                }
                if (rolePermissionModel.getPermissionId() == 5) {
                    data.add(new MenuData("Quản lý khách hàng", null, CustomerAction(), "customer"));

                }
                if (rolePermissionModel.getPermissionId() == 6) {
                    data.add(new MenuData("Thống kê", null, RevenueAction(), "revenue"));

                }
                if (rolePermissionModel.getPermissionId() == 7) {
                    data.add(new MenuData("Quản lý chức vụ", null, RoleAction(), "role"));
                    data.add(new MenuData("Quản lý phân quyền ", null, RolePermissionAction(), "role"));
                }
            }
        }
        data.add(new MenuData("Đăng xuất", null, LogoutAction(), "logout"));
        return data;
    }

    public ArrayList<MenuData> getDataEmployee() {
        ArrayList<MenuData> data = new ArrayList<>();

        data.add(new MenuData("Sản phẩm", null, ProductAction(), "products"));
        data.add(new MenuData("Hóa đơn", null, InvoiceHistoryAction(), "invoice"));
        data.add(new MenuData("Khách hàng", null, CustomerAction(), "customer"));
        data.add(new MenuData("Đăng xuất", null, LogoutAction(), "logout"));

        return data;
    }

    public ArrayList<MenuData> getDataAdmin() {
        ArrayList<MenuData> data = new ArrayList<>();

        data.add(new MenuData("Sản phẩm", null, ProductAction(), "products"));
        data.add(new MenuData("Hóa đơn", null, InvoiceHistoryAction(), "invoice"));
        data.add(new MenuData(
                "Quản lý nhập hàng",
                new ArrayList<MenuItemData>() {
                    {
                        add(new MenuItemData("Danh sách hóa đơn", ImportAction()));
                        add(new MenuItemData("Thêm hóa đơn", ImportAction()));

                    }
                },
                null, "import"));
        data.add(new MenuData(
                "Quản lý nhân viên",
                new ArrayList<MenuItemData>() {
                    // {
                    // add(new MenuItemData("Danh sách nhân viên", EmployeeAction()));
                    // add(new MenuItemData("Thêm nhân viên", EmployeeAction()));

                    // }
                },
                EmployeeAction(), "employee"));
        data.add(new MenuData("Quản lý khách hàng", null, CustomerAction(), "customer"));
        data.add(new MenuData("Thống kê", null, RevenueAction(), "revenue"));
        data.add(new MenuData("Quản lý chức vụ", null, RoleAction(), "role"));
        data.add(new MenuData("Đăng xuất", null, LogoutAction(), "logout"));
        return data;
    }

    public ArrayList<MenuData> getDataManager() {
        ArrayList<MenuData> data = new ArrayList<>();

        data.add(new MenuData("Sản phẩm", null, ProductAction(), "products"));
        data.add(new MenuData("Hóa đơn", null, InvoiceHistoryAction(), "invoice"));
        data.add(new MenuData(
                "Quản lý nhập hàng",
                new ArrayList<MenuItemData>() {
                    {
                        add(new MenuItemData("Danh sách hóa đơn", ImportAction()));
                        add(new MenuItemData("Thêm hóa đơn", ImportAction()));

                    }
                },
                null, "import"));
        data.add(new MenuData("Quản lý khách hàng", null, CustomerAction(), "customer"));
        data.add(new MenuData("Đăng xuất", null, LogoutAction(), "logout"));
        return data;
    }

    private ActionListener ProductAction() {
        return e -> {
            HomePage.getInstance().Remove();
            if (currentUser.getRoleId() == 3)
                HomePage.getInstance().Add(Products.getInstance(), Invoice.getInstance());
            else
                HomePage.getInstance().Add(Products.getInstance());

        };
    }

    private ActionListener InvoiceHistoryAction() {
        return e -> {
            HistoryList.getInstance().setVisible(true);
            HomePage.getInstance().Remove();
            HomePage.getInstance().Add(InvoiceHistory.getInstance());

        };
    }

    private ActionListener ImportAction() {
        return e -> {
            HomePage.getInstance().Remove();
            HomePage homePage = HomePage.getInstance();

            if (e.getActionCommand().equals("Danh sách hóa đơn")) {
                homePage.Add(ImportHistory.getInstance());
            } else if (e.getActionCommand().equals("Thêm hóa đơn")) {
                homePage.Add(AddNewImport.getInstance());
            }

        };
    }

    private ActionListener CustomerAction() {
        return e -> {
            HomePage.getInstance().Remove();
            HomePage.getInstance().Add(Customers.getInstance());
        };
    }

    private ActionListener EmployeeAction() {
        return e -> {
            HomePage.getInstance().Remove();
            HomePage.getInstance().Add(Employees.getInstance());

        };
    }

    private ActionListener RoleAction() {
        return e -> {
            HomePage.getInstance().Remove();
            HomePage.getInstance().Add(RoleManagement.getInstance());
        };
    }

    private ActionListener RolePermissionAction() {
        return e -> {
            HomePage.getInstance().Remove();
            HomePage.getInstance().Add(RolePermissionManagement.getInstance());
        };
    }

    private ActionListener RevenueAction() {
        return e -> {
            HomePage.getInstance().Remove();
            HomePage.getInstance().Add(Revenue.getInstance());
        };

    }

    private ActionListener LogoutAction() {
        return e -> {
            int option = JOptionPane.showConfirmDialog(
                    null,
                    "Are you sure you want to logout?",
                    "Logout",
                    JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                Authentication.logout();
                HomePage.getInstance().dispose();
                HomePage.getInstance().setVisible(false);
                HomePage.setInstance(null);
                Products.setInstance(null);
                Navigation.setInstance(null);
                Invoice.setInstance(null);
                Menu.setInstance(null);
                ProductsHeader.setInstance(null);
                Login.getInstance().setVisible(true);
            }
        };
    }

}
