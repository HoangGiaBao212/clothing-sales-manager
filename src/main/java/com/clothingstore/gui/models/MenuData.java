package com.clothingstore.gui.models;

import java.awt.BorderLayout;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.clothingstore.bus.PermissionBUS;
import com.clothingstore.bus.UserPermissionBUS;
import com.clothingstore.enums.UserPermissionStatus;
import com.clothingstore.gui.admin.dashboard.Dashboard;
import com.clothingstore.gui.admin.employees.Employees;
import com.clothingstore.gui.admin.roleManagement.RoleManagement;
import com.clothingstore.gui.admin.userPermissionManagement.UserPermissionManagement;
import com.clothingstore.gui.components.HomePage;
import com.clothingstore.gui.components.Menu;
import com.clothingstore.gui.components.Products;
import com.clothingstore.gui.components.ProductsHeader;
import com.clothingstore.gui.components.customerList.Customers;
import com.clothingstore.gui.components.importInvoice.ImportHistory;
import com.clothingstore.gui.components.importInvoice.addImport.AddNewImport;
import com.clothingstore.gui.components.invoicesHistory.InvoiceList;
import com.clothingstore.gui.components.invoicesHistory.InvoiceHistory;
import com.clothingstore.gui.components.statistical.Revenue;
import com.clothingstore.gui.components.statistical.Statistic;
import com.clothingstore.gui.employee.Invoice;
import com.clothingstore.gui.employee.Navigation;
import com.clothingstore.gui.login.Login;
import com.clothingstore.models.UserPermissionModel;
import com.clothingstore.models.PermissionModel;
import com.clothingstore.models.UserModel;
import services.Authentication;

public class MenuData {
    private String name;
    private ActionListener actionListener;
    private ArrayList<MenuItemData> menuItemData;
    private String icon;
    public Authentication authentication;
    private UserModel currentUser = Authentication.getCurrentUser();
    private List<UserPermissionModel> userPermissionList = new ArrayList<UserPermissionModel>();

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

        userPermissionList = UserPermissionBUS.getInstance().searchModel("" + currentUser.getId(),
                new String[] { "user_id" });
        ArrayList<MenuData> data = new ArrayList<>();
        if (currentUser.getRoleId() == 1)
            data.add(new MenuData("DashBoard", null, DashboardAction(), "dashboard"));
        for (UserPermissionModel userPermissionModel : userPermissionList) {
            PermissionModel permissionModel = PermissionBUS.getInstance().getModelById(userPermissionModel.getPermissionId());
            if (userPermissionModel.getStatus() == UserPermissionStatus.ACTIVE) {
                if (userPermissionModel.getPermissionId() == 1) {
                    data.add(new MenuData(permissionModel.getPermissionName(), null, ProductAction(), "products"));
                }

                if (userPermissionModel.getPermissionId() == 2) {
                    data.add(new MenuData(
                            permissionModel.getPermissionName(),
                            new ArrayList<MenuItemData>() {
                                {
                                    add(new MenuItemData("Danh sách phiếu nhập", ImportAction()));
                                    add(new MenuItemData("Thêm phiếu nhập", ImportAction()));

                                }
                            },
                            null, "import"));
                }
                if (userPermissionModel.getPermissionId() == 3) {
                    data.add(new MenuData(permissionModel.getPermissionName(), null, InvoiceHistoryAction(), "invoice"));
                }
                if (userPermissionModel.getPermissionId() == 4) {
                    data.add(new MenuData(
                            permissionModel.getPermissionName(),
                            new ArrayList<MenuItemData>() {
                                // {
                                // add(new MenuItemData("Danh sách nhân viên", EmployeeAction()));
                                // add(new MenuItemData("Thêm nhân viên", EmployeeAction()));

                                // }
                            },
                            EmployeeAction(), "employee"));

                }
                if (userPermissionModel.getPermissionId() == 5) {
                    data.add(new MenuData(permissionModel.getPermissionName(), null, CustomerAction(), "customer"));

                }
                if (userPermissionModel.getPermissionId() == 6) {
                    data.add(new MenuData(
                            permissionModel.getPermissionName(),
                            new ArrayList<MenuItemData>() {
                                {
                                    add(new MenuItemData("Thống kê ngày", StatisticAction()));
                                    add(new MenuItemData("Thống kê tháng", RevenueAction()));

                                }
                            },
                            null, "revenue"));

                }
                if (userPermissionModel.getPermissionId() == 7) {
                    data.add(new MenuData(
                            permissionModel.getPermissionName(),
                            new ArrayList<MenuItemData>() {
                                {
                                    add(new MenuItemData("Theo chức vụ", RoleAction()));
                                    add(new MenuItemData("Theo người dùng", RolePermissionAction()));

                                }
                            },
                            null, "role"));
                }
            }
        }
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
            HomePage.getInstance().add(Navigation.getInstance(), BorderLayout.SOUTH);

        };
    }

    private ActionListener InvoiceHistoryAction() {
        return e -> {
            InvoiceList.getInstance().setVisible(true);
            HomePage.getInstance().Remove();
            HomePage.getInstance().Add(InvoiceHistory.getInstance());

        };
    }

    private ActionListener ImportAction() {
        return e -> {
            HomePage.getInstance().Remove();
            HomePage homePage = HomePage.getInstance();
            if (e.getActionCommand().equals("Danh sách phiếu nhập")) {
                homePage.Add(ImportHistory.getInstance());
            } else if (e.getActionCommand().equals("Thêm phiếu nhập")) {
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
            HomePage.getInstance().Add(UserPermissionManagement.getInstance());
        };
    }

    private ActionListener RevenueAction() {
        return e -> {
            HomePage.getInstance().Remove();
            HomePage.getInstance().Add(Revenue.getInstance());
        };

    }

    private ActionListener StatisticAction() {
        return e -> {
            HomePage.getInstance().Remove();
            HomePage.getInstance().Add(new Statistic());
        };

    }

    private ActionListener DashboardAction() {
        return e -> {
            HomePage.getInstance().Remove();
            HomePage.getInstance().Add(Dashboard.getInstance());
        };

    }

    private ActionListener LogoutAction() {
        return e -> {
            int option = JOptionPane.showConfirmDialog(
                    null,
                    "Bạn có muốn đăng xuất không?",
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
