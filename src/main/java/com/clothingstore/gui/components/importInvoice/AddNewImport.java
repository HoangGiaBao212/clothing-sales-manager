package com.clothingstore.gui.components.importInvoice;

import javax.swing.*;

import com.clothingstore.bus.ImportBUS;
import com.clothingstore.bus.ImportItemsBUS;
import com.clothingstore.bus.ProductBUS;
import com.clothingstore.bus.SizeItemBUS;
import com.clothingstore.gui.components.AddNewProduct;
import com.clothingstore.models.ImportItemsModel;
import com.clothingstore.models.ImportModel;
import com.clothingstore.models.ProductModel;
import com.clothingstore.models.SizeItemModel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AddNewImport extends JPanel {

    private static AddNewImport instance;

    private JButton addNewProductButton;
    private JButton cancelImportButton;
    private JPanel contentPanel;
    private JPanel footerBottomPanel;
    private JPanel footerPanel;
    private JPanel footerTopPanel;
    private JPanel headerPanel;
    private JLabel idEmpLabel;
    private JTextField idEmpTextField;
    private JLabel idProductLabel;
    private JTextField idProductTextField;

    private JButton refreshImportButton;
    private JScrollPane listImportItemScrollPane;
    private JLabel titleLabel;
    private JPanel listImportItemPanel;
    private JButton saveButton;
    private JPanel groupButton;
    private List<ImportItemProduct> importItemProducts = new ArrayList<>();

    private int quantityImportItem = 1;
    private static java.util.List<ImportItemsModel> importItemList = new ArrayList<>();
    private static java.util.List<SizeItemModel> sizeItemList = new ArrayList<>();
    private ImportModel importModel = new ImportModel();

    public static AddNewImport getInstance() {
        if (instance == null) {
            instance = new AddNewImport();
        }
        return instance;
    }

    public AddNewImport() {
        initComponents();
        handleEvent();
    }

    private void handleEvent() {
        idProductTextField.addActionListener(e -> addNewItemProduct());
        saveButton.addActionListener(e -> addNewImport());
        addNewProductButton.addActionListener(e -> addNewProduct());
        refreshImportButton.addActionListener(e -> refreshNewImport());
    }

    private void refreshNewImport() {
        int confirmation = JOptionPane.showConfirmDialog(
                this,
                "Do you want to refresh the import?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
            importItemProducts.clear();
            listImportItemPanel.removeAll();
            listImportItemPanel.revalidate();
            listImportItemPanel.repaint();

            quantityImportItem = 1;

            revalidate();
            repaint();

            JOptionPane.showMessageDialog(null, "Import refreshed successfully");
        }
    }

    private void addNewProduct() {
        new AddNewProduct();
    }

    private boolean isNumeric(String str) {
        try {
            int value = Integer.parseInt(str);
            return value > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void addNewImport() {
        String idEmpText = idEmpTextField.getText().trim();
        if (idEmpText.isEmpty()) {
            JOptionPane.showMessageDialog(idEmpTextField, "Id Employee cannot be empty");
            return;
        }

        if (!isNumeric(idEmpText)) {
            JOptionPane.showMessageDialog(idEmpTextField, "Id Employee must be a number greater than 0");
            return;
        }

        if (importItemList.size() <= 0) {
            JOptionPane.showMessageDialog(null, "Import Item List is empty");
            return;
        }

        int totalPrice = 0;
        for (ImportItemsModel importItemModel : importItemList) {
            totalPrice += importItemModel.getPrice() * importItemModel.getQuantity();
        }

        importModel = new ImportModel(0, Integer.parseInt(idEmpTextField.getText()), null, totalPrice);

        int confirmation = JOptionPane.showConfirmDialog(null, "Do you want to save this import?", "Confirmation",
                JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
            ImportBUS.getInstance().addModel(importModel);
            ImportBUS.getInstance().refreshData();
            java.util.List<ImportModel> importList = ImportBUS.getInstance().getAllModels();
            ImportModel latestImportModel = importList.get(importList.size() - 1);

            for (ImportItemsModel importItemsModel : importItemList) {
                importItemsModel.setImport_id(latestImportModel.getId());
                ImportItemsBUS.getInstance().addModel(importItemsModel);
            }

            java.util.List<SizeItemModel> sizeItemOldList = SizeItemBUS.getInstance().getAllModels();

            for (SizeItemModel sizeItemModel : sizeItemList) {
                boolean found = false;

                for (SizeItemModel oldSizeItemModel : sizeItemOldList) {
                    if (oldSizeItemModel.getProductId() == sizeItemModel.getProductId() &&
                            oldSizeItemModel.getSizeId() == sizeItemModel.getSizeId()) {

                        int newQuantity = oldSizeItemModel.getQuantity() + sizeItemModel.getQuantity();
                        oldSizeItemModel.setQuantity(newQuantity);
                        SizeItemBUS.getInstance().updateModel(oldSizeItemModel);
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    SizeItemBUS.getInstance().addModel(sizeItemModel);
                }
            }
            java.util.List<ProductModel> productList = ProductBUS.getInstance().getAllModels();
            for (ImportItemsModel importItemModel : importItemList) {
                for (ProductModel productModel : productList) {
                    if (importItemModel.getProduct_id() == productModel.getId()) {
                        productModel.setPrice(importItemModel.getPrice() * 0.1 + importItemModel.getPrice());
                        ProductBUS.getInstance().updateModel(productModel);
                    }
                }
            }
            idEmpTextField.setText("");
            sizeItemList.clear();
            importItemList.clear();
            listImportItemPanel.removeAll();
            listImportItemPanel.repaint();
            JOptionPane.showMessageDialog(null, "Create import successfully");
        } else {
            JOptionPane.showMessageDialog(null, "Create import canceled");
        }
    }

    private void addNewItemProduct() {
        try {
            int idProduct = Integer.parseInt(idProductTextField.getText());
            ProductModel productModel = ProductBUS.getInstance().getModelById(idProduct);

            if (productModel == null) {
                SwingUtilities.invokeLater(
                        () -> JOptionPane.showMessageDialog(idProductTextField, "No model found for id: " + idProduct));
            } else {
                Boolean check = true;
                for (ImportItemsModel importItemModel : importItemList) {
                    if (importItemModel.getProduct_id() == idProduct) {
                        JOptionPane.showMessageDialog(idProductTextField, "Product is already imported");
                        check = false;
                        break;
                    }
                }
                if (check) {
                    SwingUtilities.invokeLater(() -> {
                        ImportItemProduct newItem = new ImportItemProduct(productModel, quantityImportItem, this);
                        importItemProducts.add(newItem);
                        listImportItemPanel.add(newItem);
                        listImportItemScrollPane.setViewportView(listImportItemPanel);
                        quantityImportItem++;
                        idProductTextField.setText("");
                        revalidate();
                        repaint();
                    });
                }

            }
        } catch (NumberFormatException e) {
            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(idProductTextField,
                    "Invalid idProduct. Please enter a valid integer."));
        }
    }

    private void updateQuantityImportItem() {
        quantityImportItem = importItemProducts.size() + 1;
    }

    public void removeImportItem(ImportItemProduct importItemProduct) {
        listImportItemPanel.remove(importItemProduct);
        importItemProducts.remove(importItemProduct);
        listImportItemPanel.revalidate();
        listImportItemPanel.repaint();
        updateQuantityImportItem();
    }

    private void initComponents() {

        headerPanel = new JPanel();
        titleLabel = new JLabel();
        refreshImportButton = new JButton();
        cancelImportButton = new JButton();
        contentPanel = new JPanel();
        idEmpLabel = new JLabel();
        idEmpTextField = new JTextField();
        footerPanel = new JPanel();
        footerTopPanel = new JPanel();
        idProductLabel = new JLabel();
        idProductTextField = new JTextField();
        idProductTextField.setPreferredSize(new Dimension(100, 20));
        addNewProductButton = new JButton();
        footerBottomPanel = new JPanel();
        listImportItemScrollPane = new JScrollPane();
        saveButton = new JButton("Save");
        groupButton = new JPanel();
        groupButton.setLayout(new FlowLayout());
        listImportItemPanel = new JPanel();
        listImportItemPanel.setLayout(new BoxLayout(listImportItemPanel, BoxLayout.Y_AXIS));

        setLayout(new BorderLayout());

        titleLabel.setText("Add Import");
        headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 300, 20));
        headerPanel.add(titleLabel);

        refreshImportButton.setText("Refresh Import");
        headerPanel.add(refreshImportButton);

        cancelImportButton.setText("Cancel ");

        add(headerPanel, BorderLayout.PAGE_START);

        contentPanel.setLayout(new GridBagLayout());
        contentPanel.setPreferredSize(new Dimension(300, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.EAST;

        footerPanel.setLayout(new BoxLayout(footerPanel, BoxLayout.Y_AXIS));

        idEmpLabel.setText("Id Employee");
        footerTopPanel.add(idEmpLabel);
        idEmpTextField.setColumns(10);
        footerTopPanel.add(idEmpTextField);
        idProductLabel.setText("Id product");
        footerTopPanel.add(idProductLabel);
        footerTopPanel.add(idProductTextField);

        addNewProductButton.setText("Add new product");
        footerTopPanel.add(addNewProductButton);

        footerPanel.add(footerTopPanel);

        footerBottomPanel.setLayout(new BoxLayout(footerBottomPanel, BoxLayout.Y_AXIS));
        footerBottomPanel.add(new ImportItemAddHeader());

        listImportItemScrollPane.getVerticalScrollBar().setUnitIncrement(10);
        listImportItemScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        footerPanel.add(footerBottomPanel);
        listImportItemScrollPane.setViewportView(listImportItemPanel);
        footerBottomPanel.add(listImportItemScrollPane);

        listImportItemScrollPane.setPreferredSize(new Dimension(1000, 200));

        listImportItemScrollPane.setMaximumSize(new Dimension(1000, 200));

        footerPanel.add(footerBottomPanel);
        footerPanel.add(listImportItemScrollPane);
        groupButton.add(cancelImportButton);
        groupButton.add(saveButton);
        footerPanel.add(groupButton);

        add(footerPanel, BorderLayout.CENTER);
    }

    public static java.util.List<ImportItemsModel> getImportItemList() {
        return importItemList;
    }

    public static void setImportItemList(java.util.List<ImportItemsModel> importItemList) {
        AddNewImport.importItemList = importItemList;
    }

    public static java.util.List<SizeItemModel> getSizeItemList() {
        return sizeItemList;
    }

    public static void setSizeItemList(java.util.List<SizeItemModel> sizeItemList) {
        AddNewImport.sizeItemList = sizeItemList;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Clothing Store Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        AddNewImport addNewImportPanel = new AddNewImport();
        frame.add(addNewImportPanel);

        frame.setVisible(true);
    }

    public void updateSequenceNumbers(int deletedIndex) {
        Component[] components = listImportItemPanel.getComponents();

        for (int i = deletedIndex; i < components.length; i++) {
            if (i >= 0) {
                ImportItemProduct importItem = (ImportItemProduct) components[i];
                importItem.updateSequenceNumber(i + 1);
            }
        }
    }

    public List<ImportItemProduct> getListImportItemProducts() {
        return importItemProducts;
    }

}
