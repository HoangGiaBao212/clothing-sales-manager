package com.clothingstore.gui.components.importInvoice;

import javax.swing.*;

import com.clothingstore.bus.ProductBUS;
import com.clothingstore.gui.employee.invoiceDetail.HeaderInvoice;
import com.clothingstore.models.ImportItemsModel;
import com.clothingstore.models.ImportModel;
import com.clothingstore.models.ProductModel;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class AddImport extends JFrame {

    String[] title = { "Employee Name", "Employee Id" };

    List<ImportItemsModel> importList = new ArrayList<>();

    public AddImport() {
        initComponents();
        setVisible(true);
        setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    private void initComponents() {
        Content = new JPanel();
        Header = new JPanel();
        EmployeeInfo = new JPanel();
        NameFrame = new JLabel("Add Import");
        EmployeeNameLabel = new JLabel("Employee Name");
        EmployeeName = new JTextField();
        EmployeeIdLabel = new JLabel("Employee Id");
        EmployeeId = new JTextField();
        ValueSearch = new JTextField();
        ButtonSearch = new JButton();
        NameProduct = new JLabel();
        IdProduct = new JLabel();
        Size = new JComboBox<>();
        Quantity = new JTextField();
        ButtonSave = new JButton("Save");
        Scroll = new JScrollPane();
        Products = new JPanel();

        setLayout(new BorderLayout());
        setSize(800, 500);
        setPreferredSize(new Dimension(800, 500));

        // employee info panel
        EmployeeInfo.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.EAST;
        c.gridwidth = 2;
        c.ipady = 30;
        c.gridx = 0;
        NameFrame.setFont(new Font("Segoe UI", 1, 18));
        EmployeeInfo.add(NameFrame, c);

        c.gridy = 1;
        c.ipady = 3;
        c.gridwidth = 1;
        c.ipadx = 15;
        EmployeeInfo.add(EmployeeNameLabel, c);

        c.gridx = 1;
        EmployeeName.setPreferredSize(new Dimension(130, 25));
        EmployeeInfo.add(EmployeeName, c);

        c.gridy = 2;
        c.gridx = 0;
        EmployeeInfo.add(EmployeeIdLabel, c);

        c.gridx = 1;
        EmployeeId.setPreferredSize(new Dimension(130, 25));
        EmployeeInfo.add(EmployeeId, c);

        Header.setLayout(new BorderLayout());
        Header.add(EmployeeInfo, BorderLayout.NORTH);
        Content.setLayout(new BorderLayout());
        Content.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        // search products panel
        JPanel Panel = new JPanel();
        Panel.setLayout(new GridBagLayout());

        ValueSearch.setPreferredSize(new Dimension(130, 25));
        Panel.add(ValueSearch);

        ButtonSearch.setIcon(new ImageIcon(getClass().getResource("/resources/icons/search.png")));
        ButtonSearch.setPreferredSize(new Dimension(25, 25));
        ButtonSearch.addActionListener(SearchProductAction);
        Panel.add(ButtonSearch);

        Header.add(Panel, BorderLayout.CENTER);

        // info new product

        JPanel Panel2 = new JPanel();

        Panel2.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        Panel2.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
        gbc.insets.left = 15;

        NameProduct.setPreferredSize(new Dimension(190, 25));
        Panel2.add(NameProduct, gbc);

        IdProduct.setPreferredSize(new Dimension(90, 25));
        Panel2.add(IdProduct, gbc);

        // Size.setModel(new DefaultComboBoxModel<>(new String[]{"S", "M", "L", "XL",
        // "XXL"}));

        Quantity.setPreferredSize(new Dimension(50, 25));

        ButtonSave.addActionListener(SaveProductAction);
        Header.add(Panel2, BorderLayout.SOUTH);

        // products
        Products.setLayout(new GridLayout(5, 1));
        HeaderInvoice EmployeeInfoHeader = new HeaderInvoice();
        EmployeeInfoHeader.setPreferredSize(new Dimension(30, 60));
        Products.add(EmployeeInfoHeader);

        Scroll.setViewportView(Products);
        Content.add(Scroll, BorderLayout.CENTER);

        add(Header, BorderLayout.NORTH);
        add(Content, BorderLayout.CENTER);

    }

    private JPanel Content;
    private JPanel Header;
    private JPanel EmployeeInfo;
    private JLabel NameFrame;
    private JLabel EmployeeNameLabel;
    private JTextField EmployeeName;
    private JLabel EmployeeIdLabel;
    private JTextField EmployeeId;
    private JTextField ValueSearch;
    private JButton ButtonSearch;
    private JLabel NameProduct;
    private JLabel IdProduct;
    private JComboBox<String> Size;
    private JTextField Quantity;
    private JButton ButtonSave;
    private JScrollPane Scroll;
    private JPanel Products;

    private ActionListener SaveProductAction = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            ImportModel importModel = new ImportModel(4, 5, null, 45.0);
            ImportItemsModel importItemsModel = new ImportItemsModel(1, importModel.getId(), 5, 1, 1, 45.6);

            Product product = new Product(importItemsModel, 1);
            product.setPreferredSize(new Dimension(30, 40));
            Products.add(product);
            importList.add(importItemsModel);
            revalidate();
            repaint();
        }

    };
    private ActionListener SearchProductAction = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            List<ProductModel> list = ProductBUS.getInstance().searchModel(ValueSearch.getText(),
                    new String[] { "id", "name" });
            ProductModel productModel = list.get(0);
        }
    };

    public static void main(String[] args) {
        AddImport addImport = new AddImport();
        addImport.setVisible(true);
    }
}
