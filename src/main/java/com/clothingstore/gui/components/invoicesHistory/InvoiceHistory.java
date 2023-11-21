package com.clothingstore.gui.components.invoicesHistory;

import java.awt.*;
import java.util.List;

import javax.swing.*;

import com.clothingstore.bus.OrderBUS;
import com.clothingstore.models.OrderModel;

public class InvoiceHistory extends JPanel {
    private static InvoiceHistory instance;

    List<OrderModel> orderList = OrderBUS.getInstance().getAllModels();

    public static InvoiceHistory getInstance() {
        if (instance == null) {
            instance = new InvoiceHistory();
        }
        return instance;
    }

    public InvoiceHistory() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        add(InvoiceList.getInstance(), BorderLayout.WEST);
        add(new InvoiceDetail(orderList.get(orderList.size()-1)), BorderLayout.CENTER);
    }

    public void Remove(){
        Container contentPane = InvoiceHistory.getInstance();
        Component centerComponent = ((BorderLayout) contentPane.getLayout()).getLayoutComponent(BorderLayout.CENTER);
        contentPane.remove(centerComponent);
        contentPane.revalidate();
        contentPane.repaint();
    }

}
