package com.clothingstore.gui.admin.rolePermissionManagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class CheckBoxListExample {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Check Box List Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            String[] data = {"Option 1", "Option 2", "Option 3", "Option 4", "Option 5"};

            JPanel checkBoxPanel = new JPanel(new GridLayout(data.length, 1));

            List<JCheckBox> checkBoxList = new ArrayList<>();

            for (String option : data) {
                JCheckBox checkBox = new JCheckBox(option);
                checkBoxList.add(checkBox);
                checkBoxPanel.add(checkBox);
            }

            JButton showSelectedButton = new JButton("Show Selected");
            showSelectedButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    for (JCheckBox checkBox : checkBoxList) {
                        if (checkBox.isSelected()) {
                            System.out.println("Selected option: " + checkBox.getText());
                        }
                    }
                }
            });

            JPanel panel = new JPanel(new BorderLayout());
            panel.add(checkBoxPanel, BorderLayout.CENTER);
            panel.add(showSelectedButton, BorderLayout.SOUTH);

            frame.getContentPane().add(panel);
            frame.setSize(400, 300);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
