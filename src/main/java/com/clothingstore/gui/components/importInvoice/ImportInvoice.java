package com.clothingstore.gui.components.importInvoice;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;

import javax.swing.*;

import com.clothingstore.models.ImportModel;

public class ImportInvoice extends JPanel {
    private ImportModel importModel;
    private JLabel idInvoiceLabel;
    private JLabel priceLabel;
    private JLabel dateLabel;
    private JLabel timeHourLabel;
    Color color = new Color(255,255,255);

    public ImportInvoice() {
        initComponents();
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ImportDetail importDetail = new ImportDetail(getImportModel());
                ImportHistory.getInstance().Remove();
                ImportHistory.getInstance().add(importDetail, BorderLayout.CENTER);
                ImportHistory.getInstance().revalidate();
                ImportHistory.getInstance().repaint(); 
            }
        });
    }

    public void setImportModel(ImportModel importModel) {
        this.importModel = importModel;
        initData();
    }

    public ImportModel getImportModel() {
        return importModel;
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(40, 60));
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        setBackground(color);

        JLabel icon = new JLabel();
        icon.setIcon(new ImageIcon(getClass().getResource("/resources/icons/coin.png")));
        icon.setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 0));
        add(icon, BorderLayout.LINE_START);

        JPanel detail = new JPanel();
        detail.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));
        detail.setBackground(color);
        detail.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 0));
        panel.setBackground(color);

        priceLabel = new JLabel();
        priceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        priceLabel.setFont(new Font("Segoe UI", 0, 14));
        priceLabel.setForeground(Color.RED);
        panel.add(priceLabel);

        idInvoiceLabel = new JLabel();
        idInvoiceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        idInvoiceLabel.setForeground(new Color(0, 128, 0));
        panel.add(idInvoiceLabel);

        detail.add(panel, BorderLayout.LINE_START);
        add(detail, BorderLayout.CENTER);

        JPanel time = new JPanel();
        time.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        time.setLayout(new GridLayout(2, 1));
        time.setBackground(color);

        dateLabel = new JLabel();
        dateLabel.setFont(new Font("Segoe UI", 0, 13));
        dateLabel.setForeground(new Color(102, 0, 51));
        time.add(dateLabel);

        timeHourLabel = new JLabel();
        timeHourLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timeHourLabel.setForeground(new Color(0, 0, 102));
        timeHourLabel.setFont(new Font("Segoe UI", 0, 12));
        time.add(timeHourLabel);

        add(time, BorderLayout.EAST);
    }

    private void initData() {
        idInvoiceLabel.setText(String.valueOf(importModel.getId()));
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        String formattedAmount = decimalFormat.format(importModel.getTotalPrice());
        priceLabel.setText(formattedAmount);
        String dateTimeString = String.valueOf(importModel.getImportDate());
        String[] parts = dateTimeString.split("T");
        if (parts.length == 2) {
            String datePart = parts[0];
            String timePart = parts[1];

            dateLabel.setText(datePart);
            timeHourLabel.setText(timePart);
        } else {
            dateLabel.setText("Invalid input format.");
        }
    }

}
