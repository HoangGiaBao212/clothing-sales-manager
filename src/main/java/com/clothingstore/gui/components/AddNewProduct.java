package com.clothingstore.gui.components;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.clothingstore.bus.CategoryBUS;
import com.clothingstore.bus.ProductBUS;
import com.clothingstore.models.CategoryModel;
import com.clothingstore.models.ProductModel;

import services.Validation;

public class AddNewProduct extends JFrame {

    private ProductModel productModel;

    public AddNewProduct() {
        this.setBackground(Color.RED);
        initComponents();
        setSize(800, 500);
        setTitle("Thêm sản phẩm");
        setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public void initComponents() {
        jTextFieldName = new JTextField();
        jTextFieldPrice = new JTextField();
        String[] genders = { "Giới tính *", "Nam", "Nữ" };
        comboBoxCategory = new JComboBox<>();
        comboBoxGender = new JComboBox<>(genders);
        buttonCancel = new JButton("Hủy");
        buttonSave = new JButton("Lưu");
        jLabelTitle = new JLabel("Thêm Sản Phẩm");
        jLabelTitle.setFont(new Font("Arial", Font.BOLD, 20));
        jLabelTitle.setSize(910, 100);
        int margin = 20;
        jLabelTitle.setBorder(BorderFactory.createEmptyBorder(margin, margin, margin, margin));
        jLabelTitle.setOpaque(true);
        jLabelTitle.setBackground(Color.LIGHT_GRAY);

        jPanelImage = new JPanel();
        jPanelImage.setPreferredSize(new java.awt.Dimension(400, 400));
        jPanelImage.setBorder(new EmptyBorder(20, 30, 30, 30));
        iconUploadLabel = new JLabel(new ImageIcon("src/main/java/resources/images/upload_image.png"));
        iconUploadLabel.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
        iconUploadLabel.setOpaque(true);
        iconUploadLabel.addMouseListener(actionUploadImage);
        jPanelImage.setLayout(new BorderLayout());
        jPanelImage.add(iconUploadLabel, BorderLayout.CENTER);

        jPanelInforProduct = new JPanel();
        jPanelInforProduct.setLayout(new BorderLayout());
        jPanelInforProduct.setBorder(new EmptyBorder(30, 30, 30, 30));

        jPanelInfor = new JPanel();
        jPanelInfor.setLayout(new BoxLayout(jPanelInfor, BoxLayout.Y_AXIS));
        jTextFieldName.setPreferredSize(new java.awt.Dimension(300, 40));
        jTextFieldName.setBorder(null);
        addPlaceholder(jTextFieldName, " Tên Sản Phẩm *");
        jTextFieldPrice.setPreferredSize(new java.awt.Dimension(300, 40));
        jTextFieldPrice.setBorder(null);
        addPlaceholder(jTextFieldPrice, " Giá Bán *");
        comboBoxGender.setPreferredSize(new java.awt.Dimension(300, 40));
        comboBoxGender.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedGender = (String) comboBoxGender.getSelectedItem();
                if ("Nam".equals(selectedGender)) {
                    selectedGenderId = 1;
                } else if ("Nữ".equals(selectedGender)) {
                    selectedGenderId = 0;
                } else {
                    selectedGenderId = -1;
                }
            }
        });
        comboBoxCategory.setPreferredSize(new java.awt.Dimension(300, 40));
        updateCategoryComboBox();
        comboBoxCategory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCategoryName = (String) comboBoxCategory.getSelectedItem();
                selectedCategoryId = CategoryBUS.getInstance().getCategoryIdByName(selectedCategoryName);
            }

        });

        jPanelInfor.add(Box.createVerticalStrut(20));
        jPanelInfor.add(jTextFieldName);
        jPanelInfor.add(Box.createVerticalStrut(10));
        jPanelInfor.add(jTextFieldPrice);
        jPanelInfor.add(Box.createVerticalStrut(10));
        jPanelInfor.add(comboBoxGender);
        jPanelInfor.add(Box.createVerticalStrut(10));
        jPanelInfor.add(comboBoxCategory);
        jPanelInfor.add(Box.createVerticalStrut(80));
        jPanelInfor.setBackground(new java.awt.Color(173, 216, 230));

        jPanelInforProduct.add(jPanelInfor, BorderLayout.CENTER);

        jPanelButton = new JPanel();
        jPanelButton.setLayout(new FlowLayout());
        buttonCancel.setPreferredSize(new java.awt.Dimension(100, 50));
        buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        buttonSave.setPreferredSize(new java.awt.Dimension(100, 50));
        buttonSave.addActionListener(saveButtonAction);
        jPanelButton.add(buttonSave);
        jPanelButton.add(buttonCancel);
        jPanelButton.setBackground(new java.awt.Color(173, 216, 230));

        jPanelInforProduct.add(jPanelButton, BorderLayout.SOUTH);
        jPanelInforProduct.setBackground(new java.awt.Color(173, 216, 230));

        this.setLayout(new BorderLayout());
        this.add(jLabelTitle, BorderLayout.PAGE_START);
        add(jPanelImage, BorderLayout.WEST);
        add(jPanelInforProduct, BorderLayout.CENTER);
        pack();
    }

    private static void addPlaceholder(JTextField textField, String placeholder) {
        textField.setText(placeholder);
        textField.setFont(new Font("Arial", Font.BOLD, 12));
        textField.setForeground(java.awt.Color.GRAY);

        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    // textField.setBorder(null);
                    textField.setForeground(Color.BLACK);
                }
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(Color.GRAY);
                }
            }
        });
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

                // Tạo ImageIcon từ đường dẫn ảnh
                ImageIcon originalIcon = new ImageIcon(imagePath);

                // Giảm kích thước ảnh nếu cần thiết
                int maxImageWidth = 400; // Đặt kích thước tối đa mong muốn
                int maxImageHeight = 300;

                int originalWidth = originalIcon.getIconWidth();
                int originalHeight = originalIcon.getIconHeight();

                if (originalWidth > maxImageWidth || originalHeight > maxImageHeight) {
                    // Tính toán tỷ lệ giảm kích thước
                    double scale = Math.min((double) maxImageWidth / originalWidth,
                            (double) maxImageHeight / originalHeight);

                    // Tạo ảnh mới với kích thước giảm
                    Image scaledImage = originalIcon.getImage().getScaledInstance((int) (originalWidth * scale),
                            (int) (originalHeight * scale), Image.SCALE_SMOOTH);

                    ImageIcon scaledIcon = new ImageIcon(scaledImage);
                    iconUploadLabel.setIcon(scaledIcon);

                } else {
                    // Nếu ảnh không cần giảm kích thước, hiển thị nguyên bản
                    iconUploadLabel.setIcon(originalIcon);
                }
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
        comboBoxCategory.addItem("Loại sản phẩm *");
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

    private ActionListener saveButtonAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            jTextFieldName.setBorder(null);
            jTextFieldPrice.setBorder(null);
            if (jTextFieldName.getText().trim().isEmpty() || jTextFieldPrice.getText().trim().isEmpty()
                    || selectedGenderId == -1 || selectedCategoryId == -1
                    || jTextFieldName.getText().equals(" Tên Sản Phẩm *")
                    || jTextFieldPrice.getText().equals(" Giá Bán *")
                    || imagePath == null) {
                JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin!", "Thông Báo",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                if (Validation.isValidPrice(jTextFieldName.getText())) {
                    JOptionPane.showMessageDialog(null, "Tên sản phẩm không hợp lệ!", "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    jTextFieldName.setBorder(BorderFactory.createLineBorder(Color.RED));
                    jTextFieldName.setText(null);
                } else if (!Validation.isValidPrice(jTextFieldPrice.getText())) {
                    JOptionPane.showMessageDialog(null, "Giá bán không hợp lệ!", "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    jTextFieldPrice.setBorder(BorderFactory.createLineBorder(Color.RED));
                    jTextFieldPrice.setText(null);
                } else {
                    productModel = new ProductModel();
                    productModel.setName(jTextFieldName.getText());
                    productModel.setGender(selectedGenderId);
                    productModel.setCategoryId(selectedCategoryId);
                    productModel.setPrice(Double.parseDouble(jTextFieldPrice.getText()));
                    productModel.setStatus(0);
                    productModel.setImage(imagePath);
                    int resuilt = ProductBUS.getInstance().addModel(productModel);
                    if (resuilt == 1) {
                        JOptionPane.showMessageDialog(null, "Thêm sản phẩm thành công!", "Thông báo",
                                JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Thêm thất bại. Thử lại!", "Lỗi",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }

    };

    private JPanel jPanelImage;
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
    private int selectedCategoryId = -1;
    private int selectedGenderId = -1;
    private String imagePath;
    private JLabel cancelUpload;

}
