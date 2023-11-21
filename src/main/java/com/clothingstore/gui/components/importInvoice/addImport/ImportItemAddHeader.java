package com.clothingstore.gui.components.importInvoice.addImport;

import java.awt.*;
import javax.swing.*;

public class ImportItemAddHeader extends JPanel {

    private JLabel deleteLabel;
    private JLabel idLabel;
    private JLabel nameLabel;
    private JLabel priceLabel;
    private JLabel sizeLabel;
    private JLabel sttLabel;
    private JLabel totalPriceLabel;

    public ImportItemAddHeader() {
        initComponents();
    }

    private void initComponents() {

        sttLabel = new JLabel();
        idLabel = new JLabel();
        nameLabel = new JLabel();
        sizeLabel = new JLabel();
        priceLabel = new JLabel();
        totalPriceLabel = new JLabel();
        deleteLabel = new JLabel();

        setLayout(new FlowLayout(FlowLayout.CENTER, 42, 5));

        sttLabel.setText("Stt");
        add(sttLabel);

        idLabel.setText("Mã");
        add(idLabel);

        nameLabel.setText("Tên");
        nameLabel.setPreferredSize(new Dimension(100, 20));
        add(nameLabel);

        sizeLabel.setText("Kích cỡ & số lượng");
        sizeLabel.setPreferredSize(new Dimension(100, 20));
        add(sizeLabel);

        priceLabel.setText("Giá");
        add(priceLabel);

        totalPriceLabel.setText("Tổng giá");
        add(totalPriceLabel);

        deleteLabel.setText("Xóa");
        add(deleteLabel);
        setBackground(new Color(153, 179, 255));
        setPreferredSize(new Dimension(100, -100));
    }

}
