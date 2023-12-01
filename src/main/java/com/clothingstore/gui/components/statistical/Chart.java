package com.clothingstore.gui.components.statistical;

import com.clothingstore.bus.OrderBUS;
import com.clothingstore.models.OrderModel;
import org.junit.jupiter.api.Order;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.time.*;
import java.util.ArrayList;
import java.util.List;

public class Chart extends JPanel {
    //    private int[] monthlyRevenue = {1,2,3,4,5,6,7};
    public static int count = 0;
    private int currentMonth = 0;
    private List<Integer> monthlyRevenue = new ArrayList<>();
    DecimalFormat decimalFormat = new DecimalFormat("###,###");
    private JPanel Panel;


    public Chart() {
        for(int i = 0; i<6; i++){
            currentMonth++;
            repaint();
        }
        initComponents();
    }

    public void initComponents(){
        Panel = new JPanel();
        setLayout(new BorderLayout());

        setBackground(Color.WHITE);

        Panel.setLayout(new GridLayout(1,7));
        Panel.setPreferredSize(new Dimension(30,30));

//        LocalDate today = LocalDate.of(2023,10,24);
        LocalDate today = LocalDate.now();
        LocalDate lastDayOfWeek = today.plusDays(1);

        boolean checkCurrent = true;
        boolean checkDays = true;

        for(int i = 2; i<9;i++) {
            JLabel label = new JLabel(lastDayOfWeek.getDayOfWeek().toString());
            label.setFont(new Font("Segoe UI", 1, 12));
            label.setForeground(Color.DARK_GRAY);
            if (i != 8) {
                for(int k = 6 ; k >= 0 ; k--) {
                    LocalDate previousDate = today.minusDays(k);
                    checkDays = false;
                    for (OrderModel order : OrderBUS.getInstance().getAllModels()) {
                        LocalDate orderDate = Instant.ofEpochMilli(order.getOrderDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
                        if (orderDate.equals(previousDate)) {
                            monthlyRevenue.add(calculateTotalRevenue(orderDate));
                            checkDays = true;
                        }
                    }
                    if (!checkDays) {
                        monthlyRevenue.add(0);
                    }
                }
            }
            else {
                label.setForeground(new Color(255, 51, 0));
                /*checkCurrent = false;
                for (OrderModel order : OrderBUS.getInstance().getAllModels()) {
                    LocalDate orderDate = Instant.ofEpochMilli(order.getOrderDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
                    if (orderDate.isEqual(today)) {
                        monthlyRevenue.add(calculateTotalRevenue(orderDate));
                        checkCurrent = true;
                    }
                }*/
            }


            label.setHorizontalAlignment(SwingConstants.CENTER);
            lastDayOfWeek = lastDayOfWeek.plusDays(1);
            Panel.add(label);
        }
        add(Panel, BorderLayout.SOUTH);
    }


    public int calculateTotalRevenue(LocalDate currentDate) {
        int total = 0;
        List<OrderModel> list = OrderBUS.getInstance().getAllModels();
        boolean printed = false;

        for (OrderModel order : list) {
            LocalDate orderDate = Instant.ofEpochMilli(order.getOrderDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();

            if (orderDate.isEqual(currentDate)) {
                total += order.getTotalPrice();
                printed = true;
            }
        }

        return total;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = getWidth();
        int height = getHeight();



        int barWidth = width / monthlyRevenue.size() + 25; // trừ đi chiều dài panel
        int maxRevenue = getMaxRevenue();

        for (int i = 0; i <= currentMonth; i++) {
//            int barHeight = (int) ((double) monthlyRevenue.get(i) / maxRevenue * (height - 50));
            int barHeight = (int) ((double) monthlyRevenue.get(i) / maxRevenue * (height - 50));
            int x = i * (barWidth + 50) + 32; // 50 khoảng cách giữa các cột 25 là padding trái
            int y = height - barHeight - 30;

            if(i==6)
                g.setColor(new Color(255, 36, 0));
            else
                g.setColor(new Color(51, 102, 153));


            g.fillRect(x, y, barWidth, barHeight);

            g.setColor(Color.black);
            g.drawRect(x, y, barWidth, barHeight);

            String valueText = decimalFormat.format(monthlyRevenue.get(i));
            int textWidth = g.getFontMetrics().stringWidth(valueText);
            g.drawString(valueText, x + barWidth / 2 - textWidth / 2, y - 5);
        }
    }

    private int getMaxRevenue() {
        int max = Integer.MIN_VALUE;
        for (int revenue : monthlyRevenue) {
            if (revenue > max) {
                max = revenue;
            }
        }
        return max;
    }
}
