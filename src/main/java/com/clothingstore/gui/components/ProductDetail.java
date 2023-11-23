package com.clothingstore.gui.components;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
import org.netbeans.lib.awtextra.*;

import com.clothingstore.bus.CategoryBUS;
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

public class ProductDetail extends JFrame {

  public Authentication authentication;
  static UserModel currentUser = Authentication.getCurrentUser();
  HomePage homePage = HomePage.getInstance();
  Color color = new Color(230, 240, 255);
  int selectedSizeId = -1;
  ProductModel productModel;
  int totalProduct = 0;
  private boolean isSizeSSelected;
  private boolean isSizeMSelected;
  private boolean isSizeLSelected;
  private boolean isSizeXLSelected;
  private boolean isSizeXXLSelected;

  public ProductDetail(ProductModel productModel) {
    setAlwaysOnTop(true);
    setSize(new Dimension(685, 390));
    setPreferredSize(new Dimension(685, 325));
    setResizable(false);
    getContentPane().setLayout(new AbsoluteLayout());
    getContentPane().setBackground(color);
    setLocationRelativeTo(null);

    initComponents(productModel);
  }

  ImportItemsModel importItemsModel = null;
  SizeItemModel sizeItemModel = null;
  SizeModel sizeModel = null;

  private void initComponents(ProductModel productModel) {
    this.productModel = productModel;
    ;
    List<SizeItemModel> sizeItemModels = new ArrayList<>();
    List<SizeModel> sizeModels = new ArrayList<>();

    sizeItemModels.addAll(SizeItemBUS.getInstance().getAllModels());
    sizeModels.addAll(SizeBUS.getInstance().getAllModels());

    Iterator<SizeItemModel> sizeItemIterator = sizeItemModels.iterator();
    while (sizeItemIterator.hasNext()) {
      SizeItemModel sizeItemModel = sizeItemIterator.next();
      if (sizeItemModel.getProductId() != productModel.getId()) {
        sizeItemIterator.remove();
      } else {
        totalProduct += sizeItemModel.getQuantity();
      }
    }

    Name = new JTextField();
    Price = new JLabel();
    Id = new JLabel();
    AmountText = new JLabel();
    CategoryText = new JLabel();
    Gender = new JLabel();
    Category = new JLabel();
    GenderText = new JLabel();
    SizePanel = new JPanel();
    buttonEdit = new JButton();
    ButtonAdd = new JButton();
    buttonDiscontinued = new JButton();
    buttonContinued = new JButton();
    Remaining = new JLabel();
    Spinner = new JSpinner();

    ImageIcon icon = new ImageIcon(productModel.getImage());
    Image image = icon.getImage();
    Image scaledImage = image.getScaledInstance(180, 180, java.awt.Image.SCALE_SMOOTH);
    ImageIcon scaledIcon = new ImageIcon(scaledImage);
    JLabel imageLabel = new JLabel(scaledIcon);
    getContentPane().add(imageLabel, new AbsoluteConstraints(10, 20, 190, 240));

    Name.setEditable(false);
    Name.setBackground(new Color(255, 255, 255));
    Name.setFont(new Font("Segoe UI", 1, 16));
    Name.setText(productModel.getName());
    Name.setAutoscrolls(false);
    getContentPane().add(Name, new AbsoluteConstraints(240, 20, 340, 40));

    Price.setFont(new Font("Segoe UI", 0, 21));
    Price.setForeground(new Color(255, 51, 51));
    Price.setText(productModel.getPrice() + "đ");
    getContentPane().add(Price, new AbsoluteConstraints(240, 65, 140, 30));

    Id.setFont(new Font("Segoe UI Semibold", 0, 13));
    Id.setHorizontalAlignment(SwingConstants.RIGHT);
    Id.setText(String.valueOf(productModel.getId()));
    getContentPane().add(Id, new AbsoluteConstraints(600, 10, 60, 20));

    AmountText.setFont(new Font("Segoe UI", 2, 15));
    AmountText.setForeground(new Color(102, 102, 102));
    AmountText.setHorizontalAlignment(SwingConstants.RIGHT);
    AmountText.setText("Số lượng:");
    getContentPane().add(AmountText, new AbsoluteConstraints(240, 165, 60, 20));

    CategoryText.setFont(new Font("Segoe UI", 2, 15));
    CategoryText.setForeground(new Color(102, 102, 102));
    CategoryText.setText("Loại sản phẩm:");
    getContentPane().add(CategoryText, new AbsoluteConstraints(240, 105, 70, -1));

    Gender.setFont(new Font("Segoe UI", 0, 16));
    if (productModel.getGender() == 1) {
      Gender.setText("Nam");
    } else {
      Gender.setText("Nữ");
    }

    getContentPane().add(Gender, new AbsoluteConstraints(320, 130, 130, 30));

    Category.setFont(new Font("Segoe UI", 0, 16));
    Category.setForeground(new Color(0, 51, 51));
    Category.setText(CategoryBUS.getInstance().getCategoryById(productModel.getCategoryId()).getCategoryName());
    getContentPane().add(Category, new AbsoluteConstraints(320, 105, 130, 20));

    GenderText.setFont(new Font("Segoe UI", 2, 15));
    GenderText.setForeground(new Color(102, 102, 102));
    GenderText.setHorizontalAlignment(SwingConstants.RIGHT);
    GenderText.setText("Giới tính:");
    getContentPane().add(GenderText, new AbsoluteConstraints(240, 130, 60, 30));

    SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 1, null, 1);
    Spinner = new JSpinner(spinnerModel);
    Spinner.setBackground(new Color(255, 204, 204));
    Spinner.setVisible(false);

    SizePanel.setBackground(new Color(204, 255, 204));
    SizePanel.setLayout(new java.awt.GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(0, 0, 0, 10);

    ButtonGroup sizeGroup = new ButtonGroup();
    Remaining.setFont(new Font("Segoe UI", 0, 15));
    Remaining.setForeground(new Color(102, 102, 102));

    JRadioButton sizeS = new JRadioButton("S");
    sizeS.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        spinnerModel.setValue(1);
        selectedSizeId = 1;
        isSizeSSelected = sizeS.isSelected();
        Spinner.setVisible(true);
        for (int i = 0; i < sizeItemModels.size(); i++) {
          if (sizeItemModels.get(i).getSizeId() == 1 && sizeItemModels.get(i).getQuantity() > 0) {
            Remaining.setText(sizeItemModels.get(i).getQuantity() + " sản phẩm có sẵn.");
            spinnerModel.setMaximum(sizeItemModels.get(i).getQuantity());
            break;
          } else if (sizeItemModels.get(i).getSizeId() == 1 && sizeItemModels.get(i).getQuantity() < 1) {
            JFrame jf = new JFrame();
            jf.setAlwaysOnTop(true);
            JOptionPane.showMessageDialog(jf, "Kích cỡ bạn vừa chọn đã hết hàng, vui lòng chọn kích cỡ khác!");
            sizeS.setSelected(false);
            Remaining.setText("");
            spinnerModel.setValue(0);
            Spinner.setVisible(false);
            revalidate();
            repaint();
            break;
          }
        }
      }
    });

    JRadioButton sizeM = new JRadioButton("M");
    sizeM.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
                spinnerModel.setValue(1);
        selectedSizeId = 2;
        isSizeMSelected = sizeM.isSelected();
        Spinner.setVisible(true);
        for (int i = 0; i < sizeItemModels.size(); i++) {
          if (sizeItemModels.get(i).getSizeId() == 2) {
            Remaining.setText(sizeItemModels.get(i).getQuantity() + " sản phẩm có sẵn.");
            spinnerModel.setMaximum(sizeItemModels.get(i).getQuantity());
            break;
          } else if (sizeItemModels.get(i).getSizeId() == 2 && sizeItemModels.get(i).getQuantity() < 1) {
            JFrame jf = new JFrame();
            jf.setAlwaysOnTop(true);
            JOptionPane.showMessageDialog(jf, "Kích cỡ bạn vừa chọn đã hết hàng, vui lòng chọn kích cỡ khác!");
            sizeM.setSelected(false);
            Remaining.setText("");
            spinnerModel.setValue(0);
            Spinner.setVisible(false);
            revalidate();
            repaint();
            break;
          }
        }
      }

    });
    JRadioButton sizeL = new JRadioButton("L");
    sizeL.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        spinnerModel.setValue(1);
        selectedSizeId = 3;
        isSizeLSelected = sizeL.isSelected();
        Spinner.setVisible(true);
        for (int i = 0; i < sizeItemModels.size(); i++) {
          if (sizeItemModels.get(i).getSizeId() == 3) {
            Remaining.setText(sizeItemModels.get(i).getQuantity() + " sản phẩm có sẵn.");
            spinnerModel.setMaximum(sizeItemModels.get(i).getQuantity());
            break;
          } else if (sizeItemModels.get(i).getSizeId() == 3 && sizeItemModels.get(i).getQuantity() < 1) {
            JFrame jf = new JFrame();
            jf.setAlwaysOnTop(true);
            JOptionPane.showMessageDialog(jf, "Kích cỡ bạn vừa chọn đã hết hàng, vui lòng chọn kích cỡ khác!");
            sizeL.setSelected(false);
            Remaining.setText("");
            spinnerModel.setValue(0);
            Spinner.setVisible(false);
            break;
          }
        }
      }

    });
    JRadioButton sizeXL = new JRadioButton("XL");
    sizeXL.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        spinnerModel.setValue(1);
        selectedSizeId = 4;
        isSizeXLSelected = sizeXL.isSelected();
        Spinner.setVisible(true);
        for (int i = 0; i < sizeItemModels.size(); i++) {
          if (sizeItemModels.get(i).getSizeId() == 4) {
            Remaining.setText(sizeItemModels.get(i).getQuantity() + " sản phẩm có sẵn.");
            spinnerModel.setMaximum(sizeItemModels.get(i).getQuantity());
            break;
          } else if (sizeItemModels.get(i).getSizeId() == 4 && sizeItemModels.get(i).getQuantity() < 1) {
            JFrame jf = new JFrame();
            jf.setAlwaysOnTop(true);
            JOptionPane.showMessageDialog(jf, "Kích cỡ bạn vừa chọn đã hết hàng, vui lòng chọn kích cỡ khác!");
            sizeXL.setSelected(false);
            Remaining.setText("");
            spinnerModel.setValue(0);
            Spinner.setVisible(false);
            revalidate();
            repaint();
            break;
          }
        }
      }
    });
    JRadioButton sizeXXL = new JRadioButton("XXL");
    sizeXXL.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        spinnerModel.setValue(1);
        selectedSizeId = 5;
        isSizeXXLSelected = sizeXXL.isSelected();
        Spinner.setVisible(true);
        for (int i = 0; i < sizeItemModels.size(); i++) {
          if (sizeItemModels.get(i).getSizeId() == 5) {
            Remaining.setText(sizeItemModels.get(i).getQuantity() + " sản phẩm có sẵn.");
            spinnerModel.setMaximum(sizeItemModels.get(i).getQuantity());
            break;
          } else if (sizeItemModels.get(i).getSizeId() == 5 && sizeItemModels.get(i).getQuantity() < 1) {
            JFrame jf = new JFrame();
            jf.setAlwaysOnTop(true);
            JOptionPane.showMessageDialog(jf, "Kích cỡ bạn vừa chọn đã hết hàng, vui lòng chọn kích cỡ khác!");
            sizeXXL.setSelected(false);
            Remaining.setText("");
            spinnerModel.setValue(0);
            Spinner.setVisible(false);
            revalidate();
            repaint();
            break;
          }
        }
      }

    });

    sizeGroup.add(sizeS);
    sizeGroup.add(sizeM);
    sizeGroup.add(sizeL);
    sizeGroup.add(sizeXL);
    sizeGroup.add(sizeXXL);

    SizePanel.add(sizeS);
    SizePanel.add(sizeM);
    SizePanel.add(sizeL);
    SizePanel.add(sizeXL);
    SizePanel.add(sizeXXL);

    getContentPane().add(SizePanel, new AbsoluteConstraints(250, 200, 250, 20));

    buttonEdit.setText("Sửa");
    buttonEdit.setPreferredSize(new Dimension(72, 28));
    buttonEdit.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        dispose();
        new EditProduct(productModel);
      }
    });

    if (currentUser.getRoleId() == 3) {
      ButtonAdd.setText("Thêm vào giỏ");
      ButtonAdd.setPreferredSize(new Dimension(94, 28));
      ButtonAdd.addActionListener(AddToCartAction);
      getContentPane().add(ButtonAdd, new AbsoluteConstraints(550, 250, -1, -1));

      getContentPane().add(Spinner, new AbsoluteConstraints(320, 165, 60, 20));

      getContentPane().add(Remaining, new AbsoluteConstraints(440, 165, 170, 17));
    } else {
      JLabel Total = new JLabel("( " + String.valueOf(totalProduct) + " )");
      Total.setFont(new Font("Segoe UI", 0, 15));
      Total.setForeground(new Color(102, 102, 102));
      getContentPane().add(Total, new AbsoluteConstraints(460, 165, 170, 17));
      sizeS.doClick();
      getContentPane().add(buttonEdit, new AbsoluteConstraints(250, 250, -1, -1));
      getContentPane().add(Remaining, new AbsoluteConstraints(320, 165, 170, 17));

      if (productModel.getStatus() != 0) {
        buttonDiscontinued.setText("Discontinued");
        buttonDiscontinued.setPreferredSize(new Dimension(94, 28));
        buttonDiscontinued.addActionListener(actionDiscontinued);
        getContentPane().add(buttonDiscontinued, new AbsoluteConstraints(380, 250, -1, -1));
      } else {
        buttonContinued.setText("Continued");
        buttonContinued.setPreferredSize(new Dimension(94, 28));
        buttonContinued.addActionListener(actionContinued);
        getContentPane().add(buttonContinued, new AbsoluteConstraints(380, 250, -1, -1));
      }
    }

    pack();
  }

  public ActionListener actionDiscontinued = new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
      JFrame jf = new JFrame();
      jf.setAlwaysOnTop(true);
      int choice = JOptionPane.showConfirmDialog(jf, "Bạn có muốn ngừng bán sản phẩm này?", "Xác nhận",
          JOptionPane.YES_NO_OPTION);
      if (choice == JOptionPane.YES_OPTION) {
        productModel.setStatus(0);
        ProductBUS.getInstance().updateModel(productModel);
        ProductBUS.getInstance().refreshData();
        HomePage.getInstance();
        setVisible(false);
      }
    }
  };

  public ActionListener actionContinued = new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
      JFrame jf = new JFrame();
      jf.setAlwaysOnTop(true);
      int choice = JOptionPane.showConfirmDialog(jf, "Bạn có muốn tiếp tục bán sản phẩm này?", "Xác nhận",
          JOptionPane.YES_NO_OPTION);
      if (choice == JOptionPane.YES_OPTION) {
        productModel.setStatus(1);
        ProductBUS.getInstance().updateModel(productModel);
        ProductBUS.getInstance().refreshData();
        setVisible(false);
      }
    }
  };

  private JLabel AmountText;
  private JButton ButtonAdd;
  private JButton buttonEdit;
  private JButton buttonDiscontinued;
  private JButton buttonContinued;
  private JLabel Category;
  private JLabel CategoryText;
  private JLabel Gender;
  private JLabel GenderText;
  private JLabel Id;
  private JTextField Name;
  private JLabel Price;
  private JLabel Remaining;
  private JPanel SizePanel;
  private JSpinner Spinner;

  private ActionListener AddToCartAction = new ActionListener() {

    @Override
    public void actionPerformed(ActionEvent e) {
      // đưa dữ liệu sản phẩm số lượng và size qua invoice
      if (isSizeSSelected == false && isSizeMSelected == false && isSizeLSelected == false && isSizeXLSelected == false
          && isSizeXXLSelected == false) {
        JFrame jf = new JFrame();
        jf.setAlwaysOnTop(true);
        JOptionPane.showMessageDialog(jf, "Vui lòng chọn size!");
        return;
      } else if ((isSizeSSelected || isSizeMSelected || isSizeLSelected || isSizeXLSelected || isSizeXXLSelected)
          && (int) Spinner.getValue() > 0) {
        Invoice.getInstance().addToCart(productModel, selectedSizeId, (int) Spinner.getValue());
      }
    }
  };
}