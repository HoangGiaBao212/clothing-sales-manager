package com.clothingstore.gui.components;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
import org.netbeans.lib.awtextra.*;

import com.clothingstore.bus.CategoryBUS;
import com.clothingstore.bus.ImportItemsBUS;
import com.clothingstore.bus.ProductBUS;
import com.clothingstore.bus.SizeBUS;
import com.clothingstore.bus.SizeItemBUS;
import com.clothingstore.gui.employee.Invoice;
import com.clothingstore.models.ImportItemsModel;
import com.clothingstore.models.ProductModel;
import com.clothingstore.models.SizeItemModel;
import com.clothingstore.models.SizeModel;
import com.clothingstore.models.UserModel;
import services.Authentication;

public class FrameSetting extends JFrame {


  public FrameSetting() {
    setAlwaysOnTop(true);
    setSize(new Dimension(685, 390));
    setPreferredSize(new Dimension(685, 325));
    setResizable(false);
    getContentPane().setLayout(new AbsoluteLayout());
  
    setLocationRelativeTo(null);

    initComponents();
  }

  ImportItemsModel importItemsModel = null;
  SizeItemModel sizeItemModel = null;
  SizeModel sizeModel = null;

  private void initComponents() {
    
  }

  public static void main(String[] args) {
    FrameSetting frameSetting = new FrameSetting();
    frameSetting.setVisible(true);
  }
}