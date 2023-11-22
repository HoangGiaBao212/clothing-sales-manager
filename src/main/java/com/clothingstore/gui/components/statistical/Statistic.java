package com.clothingstore.gui.components.statistical;

import javax.swing.border.*;

import com.clothingstore.bus.OrderBUS;
import com.clothingstore.bus.OrderItemBUS;
import com.clothingstore.bus.PointTransactionBUS;
import com.clothingstore.gui.models.NavData;
import com.clothingstore.models.OrderItemModel;
import com.clothingstore.models.OrderModel;
import com.clothingstore.models.PointTransactionModel;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DecimalFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;
import java.util.List;

public class Statistic extends JPanel {

    Color color = new Color(170, 205, 239);
    Color background = Color.WHITE;

    private int[] monthlyRevenue = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    private LocalDate date;
    private int invoiceQuantity ;
    private double totalMoney ;
    private int customerNew;
    private double totalMoneyCusNew;
    private int productQuantity;
    private int pointUsed;
    DecimalFormat decimalFormat = new DecimalFormat("#,##0");

    public Statistic() {
        this.date = LocalDate.now();
        GetData();
        initComponents();
        addDateChooserListener();
    }

    private void initComponents() {
        Header = new JPanel();
        NameLabel = new JLabel();
        Content = new JPanel();
        Date = new JLabel();
        Panel = new JPanel();
        General = new JPanel();
        GeneralHeader = new JPanel();
        GeneralContent = new JPanel();
        Detail = new JPanel();
        ChartPanel = new JPanel();
        Scroll = new JScrollPane();
        startDate = new JDateChooser();
        ButtonMenu = new JButton();
        ChartLabel = new JLabel("Thống kê theo giờ");

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        setBackground(color);

        Header.setLayout(new BorderLayout());
        Header.setBackground(color);
        Header.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 10));

        NameLabel.setFont(new Font("Segoe UI", 3, 21)); 
        NameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        NameLabel.setText("Báo cáo");
        Header.add(NameLabel, BorderLayout.CENTER); 

        Header.add(startDate, BorderLayout.LINE_END);

        ButtonMenu.setBackground(color);
        ButtonMenu.setIcon(new ImageIcon(getClass().getResource("/resources/icons/menu.png")));
        ButtonMenu.setBorder(null);
        ButtonMenu.addActionListener(new NavData().MenuAction());
        Header.add(ButtonMenu, BorderLayout.WEST);

        add(Header, BorderLayout.PAGE_START);

        Content.setLayout(new BorderLayout());
        Content.setBackground(background);

        Date.setText(String.valueOf(date));
        Date.setForeground(Color.DARK_GRAY);
        Date.setFont(new Font("Segoe UI", 3, 16));
        Content.add(Date, BorderLayout.NORTH);

        Panel.setBorder(BorderFactory.createEmptyBorder(1, 25, 1, 25));
        Panel.setLayout(new BorderLayout());
        Panel.setBackground(background);

        General.setLayout(new BorderLayout());
        General.setBorder(BorderFactory.createEmptyBorder(0,0,20,0));
        General.setBackground(background);

        GeneralHeader.setLayout(new BorderLayout());
        GeneralHeader.add(new GeneralPanel("Loại", "Số hóa đơn", "Tổng số tiền thu được", 19), BorderLayout.CENTER);
        General.add(GeneralHeader, BorderLayout.NORTH);

        GeneralContent.setLayout(new GridLayout(3,1,15,15));
        GeneralContent.setBackground(new Color(230, 243, 255));
        GeneralContent.add(new GeneralPanel("Tổng", String.valueOf(invoiceQuantity), String.valueOf(totalMoney),17));
        GeneralContent.add(new GeneralPanel("Khách hàng thân thiết", String.valueOf(invoiceQuantity-customerNew), String.valueOf(totalMoney-totalMoneyCusNew),17));
        GeneralContent.add(new GeneralPanel("Khách vãng lai", String.valueOf(customerNew), String.valueOf(totalMoneyCusNew),17));

        General.add(GeneralContent, BorderLayout.CENTER);
        Panel.add(General, BorderLayout.NORTH);

        Detail.setBorder(BorderFactory.createTitledBorder(null, "Chi tiết", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Segoe UI", 2, 18)));
        Detail.setPreferredSize(new Dimension(200,200));
        Detail.setLayout(new GridLayout(0, 2));
        Detail.setBackground(background);
        Detail.add(new DetailPanel("Tổng số hóa đơn", String.valueOf(invoiceQuantity)));

        if(String.valueOf((totalMoney/invoiceQuantity)).equals("NaN"))
            Detail.add(new DetailPanel("Trung bình mỗi hóa đơn", "0"));
        else
            Detail.add(new DetailPanel("Trung bình mỗi hóa đơn", String.valueOf((decimalFormat.format(totalMoney/invoiceQuantity)))));

        Detail.add(new DetailPanel("Số lượng sản phẩm", String.valueOf(productQuantity)));
        Detail.add(new DetailPanel("Số điểm thu về", String.valueOf(decimalFormat.format(pointUsed))));

        Panel.add(Detail, BorderLayout.CENTER);
        
        Content.add(Panel, BorderLayout.CENTER);

        ChartPanel.setPreferredSize(new Dimension(300,300));
        ChartPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 0, 50));
        ChartPanel.setLayout(new BorderLayout());
        ChartPanel.setBackground(background);
        ChartPanel.add(new Chart(date), BorderLayout.CENTER);

        ChartLabel.setFont(new Font("Segoe UI", 3, 17));
        ChartPanel.add(ChartLabel, BorderLayout.NORTH);
    
        Content.add(ChartPanel, BorderLayout.SOUTH);
        
        Scroll.setViewportView(Content);
        add(Scroll, BorderLayout.CENTER);
    }

    public void Refresh(){
        invoiceQuantity=0;
        totalMoney =0 ;
        customerNew = 0;
        totalMoneyCusNew = 0;
        revalidate();
        repaint();
    }

    private JPanel Content;
    private JLabel Date;
    private JPanel Detail;
    private JPanel General;
    private JPanel GeneralContent;
    private JPanel GeneralHeader;
    private JPanel Header;
    private JLabel NameLabel;
    private JPanel Panel;
    private JPanel ChartPanel;
    private JLabel ChartLabel;
    private JScrollPane Scroll;
    private JDateChooser startDate;
    private JButton ButtonMenu;

    public void setStartDate() {
        this.startDate = new JDateChooser();
    }

    private class GeneralPanel extends JPanel {
        public GeneralPanel(String text1, String text2, String text3, int font) {
            setLayout(new GridLayout(1,3));
            setBackground(new Color(230, 243, 255));
            setBorder(BorderFactory.createEmptyBorder(5,10,5,5));
            Color color = Color.red;
            
            int style = 0;
            if(font == 19){
                style = 1;
                color = Color.BLUE;
            }

            JLabel Label1 = new JLabel(text1);
            Label1.setFont(new Font("Segoe UI", style, font));
            Label1.setForeground(color);

            JLabel Label2 = new JLabel(text2);
            Label2.setFont(new Font("Segoe UI", style, font));
            Label2.setForeground(color);

            JLabel Label3 = new JLabel(text3);
            Label3.setFont(new Font("Segoe UI", style, font));
            Label3.setForeground(color);

            add(Label1);
            add(Label2);
            add(Label3);
        }
    }

    private class DetailPanel extends JPanel {
        public DetailPanel(String text1, String text2) {
            setLayout(new GridLayout(2,1));
            setBackground(background);

            JLabel Label1 = new JLabel(text1);
            Label1.setFont(new Font("Segoe UI", 3, 16));
            Label1.setForeground(new Color(153, 51, 255));

            JLabel Label2 = new JLabel(text2);
            Label2.setFont(new Font("Segoe UI", 0, 16));
            
            add(Label1);
            add(Label2);
        }
    }

    private class Chart extends JPanel{
        private int currentMonth = 0;

        private JPanel Panel;

        public Chart(LocalDate date) {
            for (int i = 0; i < 23; i++) {
                currentMonth++;
                repaint();
            }
            Panel = new JPanel();
            setLayout(new BorderLayout());
            setBackground(new Color(230, 243, 255));

            Panel.setLayout(new GridLayout(1, 12));
            Panel.setPreferredSize(new Dimension(50, 50));
            Panel.setBackground(new Color(153, 206, 255));

            for (int i = 2; i < 26; i++) {
                JLabel label = new JLabel(String.valueOf(i-2));
                label.setFont(new Font("Segoe UI", 1, 12));
                label.setForeground(Color.DARK_GRAY);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                Panel.add(label);
            }
            add(Panel, BorderLayout.SOUTH);
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            int width = getWidth();
            int height = getHeight();

            int barWidth = width / monthlyRevenue.length - 15; // trừ đi chiều dài panel
            int maxRevenue = getMaxRevenue();

            for (int i = 0; i <= currentMonth; i++) {
                int barHeight = (int) ((double) monthlyRevenue[i] / maxRevenue * (height - 50));
                int x = i * (barWidth + 15) + 15; 
                int y = height - barHeight - 30;
                g.setColor(new Color(255, 51, 51));

                g.fillRect(x, y, barWidth, barHeight);

                g.setColor(Color.black);
                    g.drawRect(x, y, barWidth, barHeight);

                String valueText = String.valueOf(decimalFormat.format(monthlyRevenue[i]));
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
    public void GetData(){
        List<OrderModel> list = new ArrayList<> (OrderBUS.getInstance().getAllModels());
        Collections.reverse(list);
        for(OrderModel orderModel : list){
            LocalDate orderDate = Instant.ofEpochMilli(orderModel.getOrderDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
            LocalTime orderTime = Instant.ofEpochMilli(orderModel.getOrderDate().getTime()).atZone(ZoneId.systemDefault()).toLocalTime();
            if(orderDate.compareTo(date) == 0){
                invoiceQuantity++;
                totalMoney += orderModel.getTotalPrice();
                List<OrderItemModel> orderItemList = OrderItemBUS.getInstance().searchModel(String.valueOf(orderModel.getId()), new String[]{"order_id"});

                for(OrderItemModel orderItem : orderItemList)
                    productQuantity+=orderItem.getQuantity(); 

                if(orderModel.getCustomerId()==1){
                    customerNew++;
                    totalMoneyCusNew += orderModel.getTotalPrice();
                }

                monthlyRevenue[Integer.valueOf(orderTime.getHour())] += orderModel.getTotalPrice();
            }
            else if(orderDate.compareTo(date) < 0){
                break;
            }
            for (PointTransactionModel point : PointTransactionBUS.getInstance().getAllModels()) {
                if (orderDate.getMonthValue() == date.getMonthValue() && orderDate.getYear() == date.getYear() && date.getDayOfMonth() == orderDate.getDayOfMonth()) {
                    pointUsed += point.getPointsUsed();
                }
            }        
        }
    }

    private void addDateChooserListener() {
        startDate.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ("date".equals(evt.getPropertyName())) {
                    Date selectedDate = (Date) evt.getNewValue();
                    LocalDate selectedLocalDate = selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    date = selectedLocalDate;
                    Refresh();
                    GetData();
                    removeAll();
                    initComponents();
                    addDateChooserListener();
                }
            }
        });
    }

}
