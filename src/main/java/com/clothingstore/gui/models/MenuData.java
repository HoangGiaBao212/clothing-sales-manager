package com.clothingstore.gui.models;

import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.clothingstore.gui.admin.employees.Employees;
import com.clothingstore.gui.admin.roleManagement.RoleManagement;
import com.clothingstore.gui.components.HomePage;
import com.clothingstore.gui.components.Products;
import com.clothingstore.gui.components.customerList.Customers;
import com.clothingstore.gui.components.importInvoice.ImportHistory;
import com.clothingstore.gui.components.importInvoice.addImport.AddNewImport;
import com.clothingstore.gui.components.invoicesHistory.HistoryList;
import com.clothingstore.gui.components.invoicesHistory.InvoiceHistory;
import com.clothingstore.gui.components.statistical.Revenue;
import com.clothingstore.gui.employee.Invoice;
import com.clothingstore.gui.login.Login;
import com.clothingstore.models.UserModel;

import services.Authentication;

public class MenuData {
    private String name;
    private ActionListener actionListener;
    private ArrayList<MenuItemData> menuItemData;
    private String icon;
    public Authentication authentication;
    static UserModel currentUser = Authentication.getCurrentUser();

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

    public static ArrayList<MenuData> getDataMenu() {
        ArrayList<MenuData> data;
        switch (currentUser.getRoleId()) {
            case 1:
                data = MenuData.getDataAdmin();
                break;
            case 2:
                data = MenuData.getDataManager();
                break;
            case 3:
                data = MenuData.getDataEmployee();
                break;
            default:
                throw new IllegalArgumentException("User role is not supported");
        }
        return data;
    }

    public static ArrayList<MenuData> getDataEmployee() {
        ArrayList<MenuData> data = new ArrayList<>();

        data.add(new MenuData("Sản phẩm", null, ProductAction(), "products"));
        data.add(new MenuData("Hóa đơn", null, InvoiceHistoryAction(),"invoice"));
        data.add(new MenuData("Khách hàng",null,CustomerAction(),"customer"));
        data.add(new MenuData("Đăng xuất", null, LogoutAction(), "logout"));

        return data;
    }

    public static ArrayList<MenuData> getDataAdmin() {
        ArrayList<MenuData> data = new ArrayList<>();

        data.add(new MenuData("Sản phẩm", null, ProductAction(), "products"));
        data.add(new MenuData("Hóa đơn", null, InvoiceHistoryAction(),"invoice"));
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
                    //     add(new MenuItemData("Danh sách nhân viên", EmployeeAction()));
                    //     add(new MenuItemData("Thêm nhân viên", EmployeeAction()));

                    // }
                },
                EmployeeAction(), "employee"));
        data.add(new MenuData("Quản lý khách hàng",null,CustomerAction(), "customer"));
        data.add(new MenuData("Thống kê",null,RevenueAction(), "revenue"));
        data.add(new MenuData("Quản lý chức vụ", null, RoleAction(), "role"));
        data.add(new MenuData("Đăng xuất", null, LogoutAction(), "logout"));
        return data;
    }

    public static ArrayList<MenuData> getDataManager() {
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
        data.add(new MenuData("Quản lý khách hàng",null,CustomerAction(), "customer"));
        data.add(new MenuData("Đăng xuất", null, LogoutAction(), "logout"));
        return data;
    }

    private static ActionListener ProductAction() {
        return e -> {
            HomePage.getInstance().Remove();
            if (currentUser.getRoleId() == 3)
                HomePage.getInstance().Add(Products.getInstance(), Invoice.getInstance());
            else
                HomePage.getInstance().Add(Products.getInstance());

        };
    }

    private static ActionListener InvoiceHistoryAction() {
        return e -> {
            HistoryList.getInstance().setVisible(true);
            HomePage.getInstance().Remove();
            HomePage.getInstance().Add(InvoiceHistory.getInstance());

        };
    }

    private static ActionListener ImportAction() {
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

    private static ActionListener CustomerAction() {
        return e -> {
            HomePage.getInstance().Remove();
            HomePage.getInstance().Add(Customers.getInstance());
        };
    }

    private static ActionListener EmployeeAction() {
        return e -> {
            HomePage.getInstance().Remove();           
            HomePage.getInstance().Add(Employees.getInstance());
         
        };
    }

    private static ActionListener RoleAction() {
        return e -> {
            HomePage.getInstance().Remove();
            HomePage.getInstance().Add(RoleManagement.getInstance());
        };
    }

    private static ActionListener RevenueAction() {
        return e -> {
            HomePage.getInstance().Remove();
            HomePage.getInstance().Add(Revenue.getInstance());
        };

    }

    private static ActionListener LogoutAction() {
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
                Login.getInstance().setVisible(true);
            }
        };
    }
}
