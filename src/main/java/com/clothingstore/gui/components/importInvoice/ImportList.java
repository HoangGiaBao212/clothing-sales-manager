package com.clothingstore.gui.components.importInvoice;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import com.clothingstore.bus.ImportBUS;
import com.clothingstore.gui.models.NavData;
import com.clothingstore.models.ImportModel;
import com.toedter.calendar.JDateChooser;

public class ImportList extends JPanel {

    private JButton menuButton;
    private JButton searchButton;
    private JPanel headerPanel;
    private JPanel invoices;
    private JPanel nameheaderPanel;
    private JLabel namePanel;
    private JTextField searchValueTextField;
    private JPanel Panel;
    private JScrollPane Scroll;
    private JDateChooser startDate;
    private JDateChooser endDate;
    private JPanel fillPanel;
    private JButton filterButton;
    private JButton removeFilterButton;

    private static ImportList instance;
    private List<ImportModel> importList;

    public static ImportList getInstance() {
        if (instance == null) {
            instance = new ImportList();
        }
        return instance;
    }

    public ImportList() {
        initComponents();
        initData();
        handleEvent();
    }

    private void handleEvent() {
        removeFilterButton.setBounds(360, 75, 80, 30);
        removeFilterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                invoices.removeAll();
                startDate.setDate(null);
                endDate.setDate(null);
                importList = new ArrayList<>(ImportBUS.getInstance().getAllModels());
                Collections.reverse(importList);
                for (ImportModel importModel : importList) {
                    ImportInvoice invoice = new ImportInvoice();
                    invoice.setImportModel(importModel);
                    invoices.add(invoice);
                }
                CheckResponsive();
                Scroll.setViewportView(invoices);
            }
        });

        filterButton.setBounds(360, 75, 80, 30);
        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date fromDate = startDate.getDate();
                Date toDate = endDate.getDate();
                checkDate(fromDate, toDate);
                CheckResponsive();
                Scroll.setViewportView(invoices);
            }
        });

        startDate.setBounds(40, 75, 150, 30);
        startDate.setDateFormatString("yyyy-MM-dd");
        JTextField editor = (JTextField) startDate.getDateEditor();
        editor.setEditable(false);

        endDate.setBounds(200, 75, 150, 30);
        endDate.setDateFormatString("yyyy-MM-dd");
        JTextField editorEndDate = (JTextField) endDate.getDateEditor();
        editorEndDate.setEditable(false);

        searchButton.addActionListener(e -> {
            String searchValue = searchValueTextField.getText();
            try {
                List<ImportModel> searchResults = ImportBUS.getInstance().searchModel(searchValue,
                new String[] { "id" });
                invoices.removeAll();
                for (ImportModel importModel : searchResults) {
                    ImportInvoice invoice = new ImportInvoice();
                    invoice.setImportModel(importModel);
                    invoices.add(invoice);
                }
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
            Scroll.setViewportView(invoices);
        });

        searchValueTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchValueTextField.getText().equals("Tìm theo mã hóa đơn nhập")) {
                    searchValueTextField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchValueTextField.getText().isEmpty()) {
                    searchValueTextField.setText("Tìm theo mã hóa đơn nhập");
                }
            }
        });
    }

    private void initData() {

    }

    private void updateImportList(String value) {
        
    }

    private void initComponents() {

        headerPanel = new JPanel();
        nameheaderPanel = new JPanel();
        namePanel = new JLabel();
        menuButton = new JButton();
        Panel = new JPanel();
        searchButton = new JButton();
        searchValueTextField = new JTextField();
        invoices = new JPanel();
        Scroll = new JScrollPane();
        fillPanel = new JPanel();

        this.filterButton = new JButton("Filter");
        this.removeFilterButton = new JButton("Remove Filter");
        this.startDate = new JDateChooser();
        this.endDate = new JDateChooser();

        Color color = new Color(179, 209, 255);

        setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 4));
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(400, 170));
        setBackground(color);

        headerPanel.setLayout(new BorderLayout());
        headerPanel.setPreferredSize(new Dimension(400, 95));
        headerPanel.setBackground(color);

        nameheaderPanel.setLayout(new BorderLayout());

        namePanel.setFont(new Font("Segoe UI", 1, 18));
        namePanel.setHorizontalAlignment(SwingConstants.CENTER);
        namePanel.setText("Hoạt Động");
        nameheaderPanel.setBackground(color);
        nameheaderPanel.add(namePanel, BorderLayout.CENTER);

        menuButton.setIcon(new ImageIcon(getClass().getResource("/resources/icons/menu.png")));
        menuButton.setBackground(color);
        menuButton.setBorder(null);
        menuButton.addActionListener(new NavData().MenuAction());
        nameheaderPanel.add(menuButton, BorderLayout.LINE_START);

        headerPanel.add(nameheaderPanel, BorderLayout.NORTH);

        Panel.setBorder(BorderFactory.createEmptyBorder(4, 4, 7, 1));
        Panel.setLayout(new BorderLayout());
        Panel.setBackground(color);

        searchButton.setIcon(new ImageIcon(getClass().getResource("/resources/icons/search.png")));
        searchButton.setBorder(null);
        searchButton.setBackground(Color.WHITE);
        Panel.add(searchButton, BorderLayout.WEST);

        searchValueTextField.setBackground(new Color(242, 242, 242));
        searchValueTextField.setFont(new Font("Segoe UI", 0, 14));
        searchValueTextField.setText("Tìm theo mã hóa đơn nhập");
        searchValueTextField.setBackground(Color.WHITE);
        searchValueTextField.setBorder(BorderFactory.createEmptyBorder(1, 6, 1, 1));

        Panel.add(searchValueTextField, BorderLayout.CENTER);

        fillPanel.setBackground(color);
        fillPanel.add(startDate, BorderLayout.WEST);
        fillPanel.add(endDate, BorderLayout.CENTER);
        fillPanel.add(filterButton, BorderLayout.EAST);
        fillPanel.add(removeFilterButton, BorderLayout.EAST);
        Panel.add(fillPanel, BorderLayout.SOUTH);

        headerPanel.add(Panel, BorderLayout.SOUTH);

        add(headerPanel, BorderLayout.PAGE_START);

        invoices.setLayout(new GridLayout(0, 1));
        invoices.setBackground(color);
        importList = new ArrayList<>(ImportBUS.getInstance().getAllModels());
        Collections.reverse(importList);
        CheckResponsive();
        for (ImportModel importModel : importList) {
            ImportInvoice invoice = new ImportInvoice();
            invoice.setImportModel(importModel);
            invoices.add(invoice);
        }

        Scroll.setViewportView(invoices);
        Scroll.getVerticalScrollBar().setUnitIncrement(10);
        add(Scroll, BorderLayout.CENTER);
    }

    private void checkDate(Date fromDate, Date toDate) {
        try {
            ImportBUS importBUS = ImportBUS.getInstance();
            importList = new ArrayList<>(importBUS.searchDateToDate(fromDate, toDate));
            Collections.reverse(importList);
            invoices.removeAll();
            for (ImportModel importModel : importList) {
                ImportInvoice invoice = new ImportInvoice();
                invoice.setImportModel(importModel);
                invoices.add(invoice);
            }
            Scroll.setViewportView(invoices);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void CheckResponsive() {
        if (importList.size() > 10)
            invoices.setLayout(new GridLayout(0, 1));
        else
            invoices.setLayout(new GridLayout(10, 1));

    }
}
