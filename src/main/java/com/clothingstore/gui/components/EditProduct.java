package com.clothingstore.gui.components;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.clothingstore.bus.CategoryBUS;
import com.clothingstore.bus.ProductBUS;
import com.clothingstore.bus.SizeBUS;
import com.clothingstore.bus.SizeItemBUS;
import com.clothingstore.models.CategoryModel;
import com.clothingstore.models.ProductModel;
import com.clothingstore.models.SizeItemModel;
import com.clothingstore.models.SizeModel;

import services.Validation;

public class EditProduct extends JFrame {

    private ProductModel productModel;
    List<SizeModel> sizeModels = SizeBUS.getInstance().getAllModels();
    List<SizeItemModel> sizeItemModels;

    public EditProduct(ProductModel productModel) {
        this.productModel = productModel;
        sizeItemModels = SizeItemBUS.getInstance().getSizeItemsByProductId(productModel.getId());
        this.setBackground(Color.RED);
        initComponents();
        setSize(900, 500);
        setResizable(false);
        setTitle("Sửa sản phẩm");
        setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public void initComponents() {
        jLabelName = new JLabel("Tên");
        jLabelPrice = new JLabel("Giá bán");
        jLabelGender = new JLabel("Giới tính");
        jLabelCategory = new JLabel("Loại sản phẩm");
        jLabelQuantity = new JLabel("Số lượng");
        jTextFieldName = new JTextField();
        jTextFieldPrice = new JTextField();
        String[] genders = { "Nam", "Nữ" };
        comboBoxCategory = new JComboBox<>();
        comboBoxGender = new JComboBox<>(genders);
        buttonCancel = new JButton("Hủy");
        buttonSave = new JButton("Lưu thay đổi");
        jLabelTitle = new JLabel("Sửa Sản Phẩm");
        jLabelTitle.setFont(new Font("Arial", Font.BOLD, 20));
        jLabelTitle.setSize(910, 100);
        int margin = 20;
        jLabelTitle.setBorder(BorderFactory.createEmptyBorder(margin, margin, margin, margin));
        jLabelTitle.setOpaque(true);
        jLabelTitle.setBackground(Color.LIGHT_GRAY);
        sizeToTextFieldMap = new HashMap<>();

        jPanelImage = new JPanel();
        jPanelImage.setPreferredSize(new java.awt.Dimension(400, 400));
        jPanelImage.setBorder(new EmptyBorder(20, 30, 30, 30));
        imagePath = productModel.getImage();
        iconUploadLabel = new JLabel(new ImageIcon(imagePath));
        iconUploadLabel.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
        iconUploadLabel.setOpaque(true);
        ImageIcon originalIcon = new ImageIcon(imagePath);

        int maxImageWidth = 400;
        int maxImageHeight = 300;
        int originalWidth = originalIcon.getIconWidth();
        int originalHeight = originalIcon.getIconHeight();
        if (originalWidth > maxImageWidth || originalHeight > maxImageHeight) {
            double scale = Math.min((double) maxImageWidth / originalWidth, (double) maxImageHeight / originalHeight);
            Image scaledImage = originalIcon.getImage().getScaledInstance((int) (originalWidth * scale),
                    (int) (originalHeight * scale), Image.SCALE_SMOOTH);

            ImageIcon scaledIcon = new ImageIcon(scaledImage);

            iconUploadLabel.setIcon(scaledIcon);
        } else {
            iconUploadLabel.setIcon(originalIcon);
        }
        iconUploadLabel.addMouseListener(actionUploadImage);
        jPanelImage.setLayout(new BorderLayout());
        jPanelImage.add(iconUploadLabel, BorderLayout.CENTER);

        jPanelInforProduct = new JPanel();
        jPanelInforProduct.setLayout(new BorderLayout());
        jPanelInforProduct.setBorder(new EmptyBorder(30, 30, 30, 30));

        jPanelInfor = new JPanel();
        jPanelInfor.setLayout(new BoxLayout(jPanelInfor, BoxLayout.Y_AXIS));
        jTextFieldName.setPreferredSize(new java.awt.Dimension(300, 40));
        jTextFieldName.setText(productModel.getName());
        jTextFieldName.setBorder(null);
        jTextFieldName.addFocusListener(editText);

        jTextFieldPrice.setPreferredSize(new java.awt.Dimension(300, 40));
        jTextFieldPrice.setText(String.valueOf(productModel.getPrice()));
        jTextFieldPrice.setBorder(null);
        jTextFieldPrice.addFocusListener(editText);

        comboBoxGender.setPreferredSize(new java.awt.Dimension(300, 40));
        comboBoxGender.addFocusListener(editText);
        comboBoxGender.addActionListener(e -> {
            String selectedGender = (String) comboBoxGender.getSelectedItem();
            selectedGenderId = switch (selectedGender) {
                case "Nam" -> 1;
                case "Nữ" -> 0;
                default -> -1;
            };
        });

        int selectedGenderId = productModel.getGender();
        if (selectedGenderId == 1) {
            comboBoxGender.setSelectedItem("Nam");
        } else if (selectedGenderId == 0) {
            comboBoxGender.setSelectedItem("Nữ");
        } else {
            // selectedGender = "Khác"; // hoặc đặt giá trị mặc định khác
        }

        comboBoxCategory.setPreferredSize(new java.awt.Dimension(300, 40));
        comboBoxCategory.addFocusListener(editText);
        updateCategoryComboBox();
        comboBoxCategory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCategoryName = (String) comboBoxCategory.getSelectedItem();
                selectedCategoryId = CategoryBUS.getInstance().getCategoryIdByName(selectedCategoryName);
            }

        });
        CategoryModel selectedCategory = CategoryBUS.getInstance().getCategoryById(productModel.getCategoryId());
        if (selectedCategory != null) {
            comboBoxCategory.setSelectedItem(selectedCategory.getCategoryName());
        } else {

        }

        jPanelInfor.add(jTextFieldName);
        jPanelInfor.add(Box.createVerticalStrut(10));
        jPanelInfor.add(jTextFieldPrice);
        jPanelInfor.add(Box.createVerticalStrut(10));
        jPanelInfor.add(comboBoxGender);
        jPanelInfor.add(Box.createVerticalStrut(10));
        jPanelInfor.add(comboBoxCategory);
        jPanelInfor.add(Box.createVerticalStrut(10));
        jPanelInfor.setBackground(new java.awt.Color(173, 216, 230));

        int[] sizeIds = new int[sizeModels.size()];
        String[] sizeNames = new String[sizeModels.size()];
        int size = sizeModels.size();
        JPanel panel = new JPanel();
        panel.setBackground(new java.awt.Color(173, 216, 230));
        panel.setLayout(new GridLayout(size, 2, -120, 3)); // 5 rows, 2 columns
        panel.setBorder(new EmptyBorder(0, 100, 0, 0));

        for (int i = 0; i < sizeModels.size(); i++) {
            SizeModel sizeModel = sizeModels.get(i);
            sizeIds[i] = sizeModel.getId();
            sizeNames[i] = sizeModel.getSize();
        }

        Map<Integer, Integer> sizeIdToQuantityMap = new HashMap<>();
        for (SizeItemModel sizeItemModel : sizeItemModels) {
            sizeIdToQuantityMap.put(sizeItemModel.getSizeId(), sizeItemModel.getQuantity());

            System.out.println("ProId: " + sizeItemModel.getProductId());
            System.out.println("SizeId: " + sizeItemModel.getSizeId());
            System.out.println("Quantity: " + sizeItemModel.getQuantity());
        }

        for (int i = 0; i < sizeNames.length; i++) {
            JLabel label = new JLabel("Size " + sizeNames[i]);
            JTextField textField = new JTextField();
            textField.setBorder(new MatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
            sizeToTextFieldMap.put("Size " + sizeNames[i], textField);

            if (sizeIdToQuantityMap.containsKey(sizeIds[i])) {
                textField.setText(String.valueOf(sizeIdToQuantityMap.get(sizeIds[i])));
                System.out.println(sizeIdToQuantityMap.get(sizeIds[i]));
            }
            textField.addFocusListener(editText);

            panel.add(label);
            panel.add(textField);
        }

        jPanelQuantity = new JPanel();
        JScrollPane scrollPane = new JScrollPane(panel,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(400, 300));
        jPanelQuantity.add(scrollPane);
        scrollPane.setViewportView(panel);
        jPanelInfor.add(scrollPane);

        jPanelInforProduct.add(jPanelInfor, BorderLayout.CENTER);

        jPanelButton = new JPanel();
        jPanelButton.setBorder(new EmptyBorder(40, 0, 0, 0));
        jPanelButton.setLayout(new FlowLayout());
        buttonCancel.setPreferredSize(new java.awt.Dimension(100, 30));
        buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        buttonSave.setPreferredSize(new java.awt.Dimension(100, 30));
        buttonSave.addActionListener(saveButtonAction);
        jPanelButton.add(buttonSave);
        jPanelButton.add(buttonCancel);
        jPanelButton.setBackground(new java.awt.Color(173, 216, 230));

        jPanelInforProduct.add(jPanelButton, BorderLayout.SOUTH);
        jPanelInforProduct.setBackground(new java.awt.Color(173, 216, 230));

        jPanelLabel = new JPanel();
        jPanelLabel.setLayout(new BoxLayout(jPanelLabel, BoxLayout.Y_AXIS));
        jLabelName.setPreferredSize(new java.awt.Dimension(100, 40));
        jLabelPrice.setPreferredSize(new java.awt.Dimension(100, 40));
        jLabelGender.setPreferredSize(new java.awt.Dimension(100, 40));
        jLabelCategory.setPreferredSize(new java.awt.Dimension(100, 40));
        jLabelQuantity.setPreferredSize(new java.awt.Dimension(100, 40));

        jPanelLabel.add(jLabelName);
        jPanelLabel.add(Box.createVerticalStrut(10));
        jPanelLabel.add(jLabelPrice);
        jPanelLabel.add(Box.createVerticalStrut(10));
        jPanelLabel.add(jLabelGender);
        jPanelLabel.add(Box.createVerticalStrut(10));
        jPanelLabel.add(jLabelCategory);
        jPanelLabel.add(Box.createVerticalStrut(75));
        jPanelLabel.add(jLabelQuantity);
        jPanelLabel.add(Box.createVerticalStrut(30));
        jPanelLabel.setBackground(new java.awt.Color(173, 216, 230));
        jPanelInforProduct.add(jPanelLabel, BorderLayout.WEST);

        this.setLayout(new BorderLayout());
        this.add(jLabelTitle, BorderLayout.PAGE_START);
        add(jPanelImage, BorderLayout.WEST);
        add(jPanelInforProduct, BorderLayout.CENTER);
        pack();

    }

    private MouseListener actionUploadImage = new MouseListener() {

        @Override
        public void mouseClicked(java.awt.event.MouseEvent e) {
            JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            cancelUpload = new JLabel(new ImageIcon("src/main/java/resources/images/icon_cancel.png"));
            JFrame frame = new JFrame("Text Field with Image Upload");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 200);
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setAcceptAllFileFilterUsed(false);
            FileNameExtensionFilter imageFilter = new FileNameExtensionFilter("Image files", "jpg", "jpeg", "png",
                    "gif");
            fileChooser.addChoosableFileFilter(imageFilter);
            int result = fileChooser.showOpenDialog(frame);

            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                imagePath = selectedFile.getAbsolutePath();
                ImageIcon originalIcon = new ImageIcon(imagePath);
                int maxImageWidth = 400;
                int maxImageHeight = 300;

                int originalWidth = originalIcon.getIconWidth();
                int originalHeight = originalIcon.getIconHeight();

                if (originalWidth > maxImageWidth || originalHeight > maxImageHeight) {
                    double scale = Math.min((double) maxImageWidth / originalWidth,
                            (double) maxImageHeight / originalHeight);

                    Image scaledImage = originalIcon.getImage().getScaledInstance((int) (originalWidth * scale),
                            (int) (originalHeight * scale), Image.SCALE_SMOOTH);

                    ImageIcon scaledIcon = new ImageIcon(scaledImage);
                    iconUploadLabel.setIcon(scaledIcon);
                } else {
                    iconUploadLabel.setIcon(originalIcon);
                }
                edit = true;
                cancelUpload.addMouseListener(actionCancelImage);
                northPanel.add(cancelUpload);
                jPanelImage.add(northPanel, BorderLayout.NORTH);
                pack();
            }
        }

        @Override
        public void mousePressed(java.awt.event.MouseEvent e) {
            throw new UnsupportedOperationException("Unimplemented method 'mousePressed'");
        }

        @Override
        public void mouseReleased(java.awt.event.MouseEvent e) {
            throw new UnsupportedOperationException("Unimplemented method 'mouseReleased'");
        }

        @Override
        public void mouseEntered(java.awt.event.MouseEvent e) {
            iconUploadLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

        @Override
        public void mouseExited(java.awt.event.MouseEvent e) {
            iconUploadLabel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
    };

    private void updateCategoryComboBox() {
        List<CategoryModel> categories = CategoryBUS.getInstance().getAllCategories();
        comboBoxCategory.removeAllItems();
        for (CategoryModel category : categories) {
            comboBoxCategory.addItem(category.getCategoryName());
        }
    }

    private MouseListener actionCancelImage = new MouseListener() {

        @Override
        public void mouseClicked(java.awt.event.MouseEvent e) {
            iconUploadLabel.setIcon(new ImageIcon("src/main/java/resources/images/upload_image.png"));
            cancelUpload.setVisible(false);
        }

        @Override
        public void mousePressed(java.awt.event.MouseEvent e) {
        }

        @Override
        public void mouseReleased(java.awt.event.MouseEvent e) {
        }

        @Override
        public void mouseEntered(java.awt.event.MouseEvent e) {
            cancelUpload.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

        @Override
        public void mouseExited(java.awt.event.MouseEvent e) {
            cancelUpload.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }

    };

    private FocusListener editText = new FocusListener() {
        private String originalText;

        @Override
        public void focusGained(FocusEvent e) {
            originalText = ((JTextField) e.getComponent()).getText();
            if (showMessage) {
                int option = JOptionPane.showConfirmDialog(null, "Có phải bạn muốn sửa thông tin?", "Xác nhận",
                        JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    allowEdit = true;
                } else {
                    allowEdit = false;
                }
                showMessage = false;
            }
            if (allowEdit) {
                ((JTextField) e.getComponent()).setEditable(true);
            } else {
                ((JTextField) e.getComponent()).setEditable(false);
            }
        }

        @Override
        public void focusLost(FocusEvent e) {
            String newText = ((JTextField) e.getComponent()).getText();

            if (!originalText.equals(newText)) {
                edit = true;
            }
            showMessage = true;
        }
    };

    private ActionListener saveButtonAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (edit == true) {
                jTextFieldName.setBorder(null);
                jTextFieldPrice.setBorder(null);
                sizeSValue = sizeToTextFieldMap.get("Size S").getText();
                System.out.println(sizeSValue);
                sizeMValue = sizeToTextFieldMap.get("Size M").getText();
                sizeLValue = sizeToTextFieldMap.get("Size L").getText();
                sizeXLValue = sizeToTextFieldMap.get("Size XL").getText();
                sizeXXLValue = sizeToTextFieldMap.get("Size XXL").getText();
                if (jTextFieldName.getText().trim().isEmpty() || jTextFieldPrice.getText().trim().isEmpty()
                        || imagePath == null) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin", "Thông báo",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    if (Validation.isValidPrice(jTextFieldName.getText())) {
                        JOptionPane.showMessageDialog(null, "Tên sản phẩm không được để trống!", "Lỗi",
                                JOptionPane.ERROR_MESSAGE);
                        jTextFieldName.setBorder(BorderFactory.createLineBorder(Color.RED));
                        jTextFieldName.setText(null);
                    } else if (!Validation.isValidPrice(jTextFieldPrice.getText())) {
                        JOptionPane.showMessageDialog(null, "Giá bán không hợp lệ!", "Lỗi",
                                JOptionPane.ERROR_MESSAGE);
                        jTextFieldPrice.setBorder(BorderFactory.createLineBorder(Color.RED));
                        jTextFieldPrice.setText(null);
                    } else {
                        productModel.setName(jTextFieldName.getText());
                        productModel.setGender(selectedGenderId);
                        productModel.setCategoryId(selectedCategoryId);
                        productModel.setPrice(Double.parseDouble(jTextFieldPrice.getText()));
                        productModel.setImage(imagePath);

                        for (SizeItemModel sizeItemModel : sizeItemModels) {
                            if (sizeItemModel.getSizeId() == 1) {
                                sizeItemModel.setQuantity(Integer.parseInt(sizeSValue));
                            } else if (sizeItemModel.getSizeId() == 2) {
                                sizeItemModel.setQuantity(Integer.parseInt(sizeMValue));
                            } else if (sizeItemModel.getSizeId() == 3) {
                                sizeItemModel.setQuantity(Integer.parseInt(sizeLValue));
                            } else if (sizeItemModel.getSizeId() == 1) {
                                sizeItemModel.setQuantity(Integer.parseInt(sizeXLValue));
                            } else {
                                sizeItemModel.setQuantity(Integer.parseInt(sizeXXLValue));
                            }
                            SizeItemBUS.getInstance().updateModel(sizeItemModel);
                        }
                        int resuilt = ProductBUS.getInstance().updateModel(productModel);
                        if (resuilt == 1) {
                            JOptionPane.showMessageDialog(null, "Sửa thông tin sản phẩm thành công", "Thông báo",
                                    JOptionPane.INFORMATION_MESSAGE);
                            ProductBUS.getInstance().refreshData();
                            SizeItemBUS.getInstance().refreshData();
                            dispose();
                            HomePage.getInstance();
                        } else {
                            JOptionPane.showMessageDialog(null, "An error occurred. Please try again.",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Không có thông tin thay đổi", "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }

    };

    private JPanel jPanelQuantity;
    private JPanel jPanelImage;
    private JPanel jPanelLabel;
    private JPanel jPanelInforProduct;
    private JPanel jPanelInfor;
    private JPanel jPanelButton;
    private JLabel jLabelTitle;
    private JTextField jTextFieldName;
    private JTextField jTextFieldPrice;
    private JComboBox<String> comboBoxCategory;
    private JComboBox<String> comboBoxGender;
    private JButton buttonSave;
    private JButton buttonCancel;
    private JLabel iconUploadLabel;
    private int selectedCategoryId;
    private int selectedGenderId;
    private String imagePath;
    private JLabel cancelUpload;
    private JLabel jLabelName;
    private JLabel jLabelGender;
    private JLabel jLabelCategory;
    private JLabel jLabelPrice;
    private JLabel jLabelQuantity;
    private Map<String, JTextField> sizeToTextFieldMap;
    private String sizeSValue;
    private String sizeMValue;
    private String sizeLValue;
    private String sizeXLValue;
    private String sizeXXLValue;
    private boolean showMessage = true;
    private boolean allowEdit = false;
    private boolean edit = false;

}
