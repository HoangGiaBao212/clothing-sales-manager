package com.clothingstore.gui.admin.employees;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class ImageRender extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                   int row, int column) {
        JLabel label = new JLabel();

        if (value instanceof String && (((String) value).endsWith(".png") || ((String) value).endsWith(".jpg"))) {
            // Tạo hình ảnh từ chuỗi string
            ImageIcon originalIcon = new ImageIcon((String) value);

            // Thay đổi kích thước của Image thành 50x50 pixels
            Image originalImage = originalIcon.getImage();
            Image resizedImage = originalImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);

            // Tạo ImageIcon mới với kích thước 50x50
            ImageIcon resizedIcon = new ImageIcon(resizedImage);

            // Đặt alignment để căn giữa biểu tượng trong Jlabel
            label.setIcon(resizedIcon);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setVerticalAlignment(SwingConstants.CENTER);

            // Loại bỏ văn bản
            label.setText(null);
        } else {
            // Nếu không phải biểu tượng thì sử dụng giá trị mặc định
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }

        return label;
    }
}
