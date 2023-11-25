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
    ArrayList<NavData> data = new ArrayList<NavData>() {
      {
        add(new NavData("Tất cả", AllAction()));
        add(new NavData("Polo", PoloAction()));
        add(new NavData("Áo sơ mi", ShirtAction()));
        add(new NavData("Áo thun", TeeAction()));
        add(new NavData("Hoodie", HoodieAction()));
        add(new NavData("Jacket", JacketAction()));
        add(new NavData("Quần Jean", JeanAction()));
        add(new NavData("Quần dài", LongPantAction()));
        add(new NavData("Quần ngắn", ShortAction()));
        add(new NavData("Quần Tây", TrouserAction()));
        add(new NavData("Khóa", BlockAction()));
      }
    };
    return data;
  }

  public static ActionListener AllAction() {
    return new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        List<ProductModel> productModels = ProductBUS.getInstance().getAllModels();
        Products.getInstance().showProductsFromResult(productModels);
      }
    };
  }

  public ActionListener PoloAction() {
    return new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        List<ProductModel> productModels = ProductBUS.getInstance().searchModel("1", new String[] { "category_id" });
        Products.getInstance().showProductsFromResult(productModels);
      }
    };
  }

  public ActionListener ShirtAction() {
    return new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        List<ProductModel> productModels = ProductBUS.getInstance().searchModel("2", new String[] { "category_id" });
        Products.getInstance().showProductsFromResult(productModels);
      }
    };
  }

  public ActionListener TeeAction() {
    return new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        List<ProductModel> productModels = ProductBUS.getInstance().searchModel("3", new String[] { "category_id" });
        Products.getInstance().showProductsFromResult(productModels);
      }
    };
  }

  public ActionListener HoodieAction() {
    return new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        List<ProductModel> productModels = ProductBUS.getInstance().searchModel("4", new String[] { "category_id" });
        Products.getInstance().showProductsFromResult(productModels);
      }
    };
  }

  public ActionListener JacketAction() {
    return new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        List<ProductModel> productModels = ProductBUS.getInstance().searchModel("5", new String[] { "category_id" });
        Products.getInstance().showProductsFromResult(productModels);
      }
    };
  }

  public ActionListener JeanAction() {
    return new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        List<ProductModel> productModels = ProductBUS.getInstance().searchModel("6", new String[] { "category_id" });
        Products.getInstance().showProductsFromResult(productModels);
      }
    };
  }

  public ActionListener LongPantAction() {
    return new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        List<ProductModel> productModels = ProductBUS.getInstance().searchModel("7", new String[] { "category_id" });
        Products.getInstance().showProductsFromResult(productModels);
      }
    };
  }

  public ActionListener ShortAction() {
    return new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        List<ProductModel> productModels = ProductBUS.getInstance().searchModel("8", new String[] { "category_id" });
        Products.getInstance().showProductsFromResult(productModels);
      }
    };
  }

  public ActionListener TrouserAction() {
    return new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        List<ProductModel> productModels = ProductBUS.getInstance().searchModel("9", new String[] { "category_id" });
        Products.getInstance().showProductsFromResult(productModels);
      }
    };
  }

  public ActionListener BlockAction() {
    return new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        List<ProductModel> productModels = ProductBUS.getInstance().searchModel("0", new String[] { "status" });
        Products.getInstance().showProductsFromResult(productModels);
      }
    };
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
