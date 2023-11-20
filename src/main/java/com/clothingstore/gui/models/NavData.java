package com.clothingstore.gui.models;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import javax.swing.*;

import com.clothingstore.bus.ProductBUS;
import com.clothingstore.gui.components.Menu;
import com.clothingstore.gui.components.Products;
import com.clothingstore.models.ProductModel;

public class NavData {
  private String name;
  private ActionListener actionListener;
  private static boolean isExpanding = true;
  private static int menuWidth;
  private static ArrayList<MenuData> menuData = MenuData.getDataMenu();
  private static Menu menu = Menu.getInstance(menuData);

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

  public static ArrayList<NavData> getData() {
    ArrayList<NavData> data = new ArrayList<NavData>() {
      {
        add(new NavData("Menu", MenuAction()));
        add(new NavData("All", allBuActionListener()));
        add(new NavData("Shirt", shirtButtonActionListener()));
        add(new NavData("Polo", poloButtonActionListener()));
        add(new NavData("Short", shortButActionListener()));
        add(new NavData("Jean", jActionListener()));
        add(new NavData("More", moreProductsActionListener()));
      }
    };
    return data;
  }

  public static ActionListener allBuActionListener() {
    return new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        List<ProductModel> productModels = ProductBUS.getInstance().getAllModels();
        Products.getInstance().showProductsFromResult(productModels);
      }
    };
  }

  public static ActionListener shirtButtonActionListener() {
    return new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        List<ProductModel> productModels = ProductBUS.getInstance().searchModel("2", new String[] { "category_id" });
        Products.getInstance().showProductsFromResult(productModels);
      }
    };
  }

  public static ActionListener poloButtonActionListener() {
    return new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        List<ProductModel> productModels = ProductBUS.getInstance().searchModel("1", new String[] { "category_id" });
        Products.getInstance().showProductsFromResult(productModels);
      }
    };
  }

  public static ActionListener shortButActionListener() {
    return new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        List<ProductModel> productModels = ProductBUS.getInstance().searchModel("8", new String[] { "category_id" });
        Products.getInstance().showProductsFromResult(productModels);
      }
    };
  }

  public static ActionListener jActionListener() {
    return new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        List<ProductModel> productModels = ProductBUS.getInstance().searchModel("6", new String[] { "category_id" });
        Products.getInstance().showProductsFromResult(productModels);
      }
    };
  }

  public static ActionListener moreProductsActionListener() {
    return e -> {
      List<ProductModel> productModels = new ArrayList<>(ProductBUS.getInstance().getAllModels());
      List<Integer> categoryIds = List.of(3, 4, 5, 7, 9);
      productModels.removeIf(model -> !categoryIds.contains(model.getCategoryId()));
      Products.getInstance().showProductsFromResult(productModels);
  };
  }

  public static ActionListener MenuAction() {
    menuWidth = menu.getWidth();
    return new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        Timer timer;
        if (menuWidth > 0 && menuWidth < 200) {
          return;
        } else {
          if (isExpanding) {
            timer = new Timer(10, new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                if (menuWidth <= 200) {
                  menuWidth += 10;
                  menu.setPreferredSize(new Dimension(menuWidth, 200));
                  menu.repaint();
                  menu.revalidate();
                } else {
                  ((Timer) e.getSource()).stop();
                }
              }
            });
          } else {
            timer = new Timer(10, new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                if (menuWidth >= 10) {
                  menuWidth -= 10;
                  menu.setPreferredSize(new Dimension(menuWidth, 150));
                  menu.repaint();
                  menu.revalidate();
                } else {
                  ((Timer) e.getSource()).stop();
                }
              }
            });
          }

          timer.start();
          Products.getInstance().MenuOn(isExpanding);
          isExpanding = !isExpanding;
        }
      }
    };
  }
}
