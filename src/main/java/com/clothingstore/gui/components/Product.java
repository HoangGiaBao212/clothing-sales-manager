package com.clothingstore.gui.components;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

import com.clothingstore.bus.SizeItemBUS;
import com.clothingstore.models.OrderItemModel;
import com.clothingstore.models.ProductModel;
import com.clothingstore.models.SizeItemModel;

public class Product extends JPanel {
  private static int sizeSQuantity = 1;
  private static int sizeMQuantity = 1;
  private static int sizeLQuantity = 1;
  private static int sizeXLQuantity = 1;
  private static int sizeXXLQuantity = 1;
  public static List<OrderItemModel> cartItems = new ArrayList<>();
  DecimalFormat decimalFormat = new DecimalFormat("###,###");

  public Product(ProductModel productModel) {
    initComponents(productModel);
    addMouseListener(new ProductMouseAdapter(productModel));
  }

  private void initComponents(ProductModel productModel) {
    setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    setPreferredSize(new Dimension(199, 280));
    setLayout(new BorderLayout());

    JPanel header = new JPanel();
    header.setBackground(new Color(255, 255, 255));
    header.setLayout(new GridBagLayout());

    SwingWorker<ImageIcon, Void> worker = new SwingWorker<>() {
      @Override
      protected ImageIcon doInBackground() throws Exception {
        ImageIcon icon = new ImageIcon(productModel.getImage());
        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(180, 180, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
      }

      @Override
      protected void done() {
        try {
          ImageIcon scaledIcon = get();
          JLabel imageLabel = new JLabel(scaledIcon);
          imageLabel.setPreferredSize(new Dimension(180, 178));
          header.add(imageLabel, new GridBagConstraints());
          add(header, BorderLayout.CENTER);
          revalidate();
          repaint();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    };

    worker.execute();

    JPanel footer = new JPanel();
    footer.setBackground(new Color(255, 255, 255));
    footer.setPreferredSize(new Dimension(193, 65));
    footer.setLayout(new GridLayout(2, 1));

    JLabel nameLabel = new JLabel(productModel.getName());
    nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
    nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
    footer.add(nameLabel);

    JLabel priceLabel = new JLabel(String.valueOf(decimalFormat.format(productModel.getPrice())));
    priceLabel.setFont(new Font("Segoe UI", Font.ITALIC, 16));
    priceLabel.setForeground(new Color(240, 18, 18));
    priceLabel.setHorizontalAlignment(SwingConstants.CENTER);
    footer.add(priceLabel);

    add(footer, BorderLayout.SOUTH);
  }

  private static class ProductMouseAdapter extends MouseAdapter {
    private final ProductModel productModel;

    ProductMouseAdapter(ProductModel productModel) {
      this.productModel = productModel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
      List<SizeItemModel> sizeItemModels = new ArrayList<>(
          SizeItemBUS.getInstance().searchModel(String.valueOf(productModel.getId()), new String[] { "product_id" }));
      for (int i = 0; i < sizeItemModels.size(); i++) {
        if (sizeItemModels.get(i).getSizeId() == 1 && sizeItemModels.get(i).getQuantity() < 1) {
          sizeSQuantity = 0;
        }
        if (sizeItemModels.get(i).getSizeId() == 2 && sizeItemModels.get(i).getQuantity() < 1) {
          sizeMQuantity = 0;
        }
        if (sizeItemModels.get(i).getSizeId() == 3 && sizeItemModels.get(i).getQuantity() < 1) {
          sizeLQuantity = 0;
        }
        if (sizeItemModels.get(i).getSizeId() == 4 && sizeItemModels.get(i).getQuantity() < 1) {
          sizeXLQuantity = 0;
        }
        if (sizeItemModels.get(i).getSizeId() == 5 && sizeItemModels.get(i).getQuantity() < 1) {
          sizeXXLQuantity = 0;
        }

      }
      if (sizeSQuantity == 0 && sizeMQuantity == 0 && sizeLQuantity == 0 && sizeXLQuantity == 0
          && sizeXXLQuantity == 0) {
        JFrame jf = new JFrame();
        jf.setAlwaysOnTop(true);
        JOptionPane.showMessageDialog(jf, "Sản phẩm đã hết hàng!");
        sizeSQuantity = 1;
        sizeMQuantity = 1;
        sizeLQuantity = 1;
        sizeXLQuantity = 1;
        sizeXXLQuantity = 1;
        return;
      }
      if (productModel.getStatus() == 0) {
        JFrame jf = new JFrame();
        jf.setAlwaysOnTop(true);
        JOptionPane.showMessageDialog(jf, "Sản phẩm tạm thời không tồn tại!");
        return;
      }
      ProductDetail productDetail = new ProductDetail(productModel);
      productDetail.setVisible(true);
    }
  }
}
