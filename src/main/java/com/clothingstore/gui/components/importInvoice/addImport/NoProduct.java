package com.clothingstore.gui.components.importInvoice.addImport;

import javax.swing.*;
import java.awt.*;

public class NoProduct extends JPanel {

    public NoProduct() {
        setLayout(new BorderLayout());
        ImageIcon icon = new ImageIcon(getClass().getResource("/resources/icons/no-clothes.png"));
        Image image = icon.getImage();
        int iconWidth = 200;
        int iconHeight = 200;
        Image scaledImage = image.getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel iconLabel = new JLabel(scaledIcon);
        iconLabel.setHorizontalAlignment(JLabel.CENTER);
        JLabel noProductLabel = new JLabel("Không có sản phẩm nào.");
        noProductLabel.setHorizontalAlignment(JLabel.CENTER);
        Font labelFont = new Font("Arial", Font.BOLD, 16);
        noProductLabel.setFont(labelFont);
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(noProductLabel, BorderLayout.CENTER);
        contentPanel.add(iconLabel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);

        contentPanel.setBackground(new Color(179, 209, 255)); 

        setPreferredSize(new Dimension(200, 150));
    }
}
