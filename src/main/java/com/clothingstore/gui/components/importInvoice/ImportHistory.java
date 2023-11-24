package com.clothingstore.gui.components.importInvoice;

import java.awt.*;
import java.util.List;

import javax.swing.*;

import com.clothingstore.bus.ImportBUS;
import com.clothingstore.models.ImportModel;

public class ImportHistory extends JPanel {
    private static ImportHistory instance;

    private static List<ImportModel> importList = ImportBUS.getInstance().getAllModels();

    public static ImportHistory getInstance() {
        if (instance == null) {
            instance = new ImportHistory();
        }
        return instance;
    }

    public ImportHistory() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        add(ImportList.getInstance(), BorderLayout.WEST);
        add(new ImportDetail(importList.get(importList.size()-1)), BorderLayout.CENTER);
    }

    public void Remove(){
        Container contentPane = ImportHistory.getInstance();
        Component centerComponent = ((BorderLayout) contentPane.getLayout()).getLayoutComponent(BorderLayout.CENTER);
        contentPane.remove(centerComponent);
        contentPane.revalidate();
        contentPane.repaint();
    }
}
