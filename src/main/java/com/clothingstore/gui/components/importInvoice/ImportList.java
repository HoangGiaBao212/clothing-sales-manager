package com.clothingstore.gui.components.importInvoice;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import com.clothingstore.bus.ImportBUS;
import com.clothingstore.gui.models.NavData;
import com.clothingstore.models.ImportModel;

public class ImportList extends JPanel {

    private static ImportList instance;

    public static ImportList getInstance() {
        if (instance == null) {
            instance = new ImportList();
        }
        return instance;
    }

    public ImportList() {
        initComponents();
        initData();
    }

    private void initComponents() {

        Header = new JPanel();
        NameHeader = new JPanel();
        NamePanel = new JLabel();
        ButtonMenu = new JButton();
        Panel = new JPanel();
        ButtonSearch = new JButton();
        SearchValue = new JTextField();
        invoices = new JPanel();
        Scroll = new JScrollPane();

        Color color = new Color(179, 209, 255);

        setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 4));
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(322, 170));
        setBackground(color);

        Header.setLayout(new BorderLayout());
        Header.setPreferredSize(new Dimension(250, 65));
        Header.setBackground(color);

        NameHeader.setLayout(new BorderLayout());

        NamePanel.setFont(new Font("Segoe UI", 1, 18));
        NamePanel.setHorizontalAlignment(SwingConstants.CENTER);
        NamePanel.setText("Hóa đơn nhập hàng");
        NameHeader.setBackground(color);
        NameHeader.add(NamePanel, BorderLayout.CENTER);

        ButtonMenu.setBackground(color);
        ButtonMenu.setIcon(new ImageIcon(getClass().getResource("/resources/icons/menu.png")));
        ButtonMenu.setBorder(null);
        ButtonMenu.addActionListener(new NavData().MenuAction());
        NameHeader.add(ButtonMenu, BorderLayout.LINE_START);

        Header.add(NameHeader, BorderLayout.NORTH);

        Panel.setBorder(BorderFactory.createEmptyBorder(4, 4, 7, 1));
        Panel.setLayout(new BorderLayout());
        Panel.setBackground(color);

        ButtonSearch.setIcon(new ImageIcon(getClass().getResource("/resources/icons/search.png")));
        ButtonSearch.setBorder(null);
        ButtonSearch.setBackground(Color.WHITE);
        Panel.add(ButtonSearch, BorderLayout.WEST);

        SearchValue.setBackground(new Color(242, 242, 242));
        SearchValue.setFont(new Font("Segoe UI", 0, 14));
        SearchValue.setText("Tìm theo mã hóa đơn");
        SearchValue.setBackground(Color.white);
        SearchValue.setBorder(BorderFactory.createEmptyBorder(1, 6, 1, 1));
        Panel.add(SearchValue, BorderLayout.CENTER);

        Header.add(Panel, BorderLayout.SOUTH);

        add(Header, BorderLayout.PAGE_START);

        invoices.setLayout(new GridLayout(0, 1));
        invoices.setBackground(color);
    }

    private void initData() {
        ArrayList<ImportModel> importList = new ArrayList<ImportModel>();
        importList.addAll(ImportBUS.getInstance().getAllModels());

        for (ImportModel importModel : importList) {
            ImportInvoice importInvoice = new ImportInvoice();
            importInvoice.setImportModel(importModel);
            invoices.add(importInvoice);
        }

        Scroll.getVerticalScrollBar().setUnitIncrement(10); 
        Scroll.setViewportView(invoices);
        add(Scroll, BorderLayout.CENTER);
        
    }

    private JButton ButtonMenu;
    private JButton ButtonSearch;
    private JPanel Header;
    private JPanel invoices;
    private JPanel NameHeader;
    private JLabel NamePanel;
    private JTextField SearchValue;
    private JPanel Panel;
    private JScrollPane Scroll;
}
