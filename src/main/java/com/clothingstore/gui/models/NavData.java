package com.clothingstore.gui.models;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import com.clothingstore.bus.ProductBUS;
import com.clothingstore.gui.components.Menu;
import com.clothingstore.gui.components.Products;
import com.clothingstore.models.ProductModel;

public class NavData {
    private String name;
    private ActionListener actionListener;
    private boolean isExpanding = true;
    private int menuWidth;
    private ArrayList<MenuData> menuData = new MenuData().getDataMenu();
    private Menu menu = Menu.getInstance(menuData);

    public NavData() {
    }

    public NavData(String name, ActionListener actionListener) {
        this.name = name;
        this.actionListener = actionListener;
    }

    public String getName() {
        return name;
    }

    public ActionListener getActionListener() {
        return actionListener;
    }

    public ArrayList<NavData> getData() {
        return new ArrayList<NavData>() {
            {
                add(new NavData("Tất cả", createAction(null, new String[] { "status" })));
                add(new NavData("Polo", createAction("1", new String[] { "category_id" })));
                add(new NavData("Áo sơ mi", createAction("2", new String[] { "category_id" })));
                add(new NavData("Áo thun", createAction("3", new String[] { "category_id" })));
                add(new NavData("Hoodie", createAction("4", new String[] { "category_id" })));
                add(new NavData("Jacket", createAction("5", new String[] { "category_id" })));
                add(new NavData("Quần Jean", createAction("6", new String[] { "category_id" })));
                add(new NavData("Quần dài", createAction("7", new String[] { "category_id" })));
                add(new NavData("Quần ngắn", createAction("8", new String[] { "category_id" })));
                add(new NavData("Quần Tây", createAction("9", new String[] { "category_id" })));
                add(new NavData("Khóa", createAction("0", new String[] { "status" })));
            }
        };
    }

    private ActionListener createAction(String id, String[] filter) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    List<ProductModel> productModels;
                    if (id == null) {
                        productModels = ProductBUS.getInstance().getAllModels();
                    } else {
                        productModels = ProductBUS.getInstance().searchModel(id, filter);
                    }
                    Products.getInstance().showProductsFromResult(productModels);
                } catch (Exception ex) {
                    handleException(ex);
                }
            }
        };
    }

    private void handleException(Exception ex) {
        JOptionPane.showMessageDialog(null, ex.getMessage(), "Error",
                JOptionPane.ERROR_MESSAGE);
    }

    public ActionListener MenuAction() {
        menuWidth = menu.getWidth();
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Timer timer;
                if (menuWidth > 0 && menuWidth < 200) {
                    return;
                } else {
                    if (isExpanding) {
                        timer = new Timer(10, createMenuTimerAction(10, 200, 200));
                    } else {
                        timer = new Timer(10, createMenuTimerAction(-10, 10, 150));
                    }

                    timer.start();
                    Products.getInstance().MenuOn(isExpanding);
                    isExpanding = !isExpanding;
                }
            }
        };
    }

    private ActionListener createMenuTimerAction(int step, int limit, int preferredHeight) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ((isExpanding && menuWidth <= limit) || (!isExpanding && menuWidth >= limit)) {
                    menuWidth += step;
                    menu.setPreferredSize(new Dimension(menuWidth, preferredHeight));
                    menu.repaint();
                    menu.revalidate();
                } else {
                    ((Timer) e.getSource()).stop();
                }
            }
        };
    }
}
