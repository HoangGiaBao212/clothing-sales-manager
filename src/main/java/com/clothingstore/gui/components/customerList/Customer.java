package com.clothingstore.gui.components.customerList;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.clothingstore.bus.UserBUS;
import com.clothingstore.enums.UserStatus;
import com.clothingstore.gui.admin.employees.ImageRender;
import com.clothingstore.models.CustomerModel;
import com.clothingstore.models.UserModel;

public class Customer extends JPanel {

  public Customer(CustomerModel customerModel) {
    initComponents(customerModel);
    this.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        CustomerDetail customerDetail = new CustomerDetail(customerModel);
        // Customers.getInstance().Remove();
        Customers.getInstance().add(customerDetail, BorderLayout.CENTER);
        Customers.getInstance().revalidate();
        Customers.getInstance().repaint();
      }
    });
  }

  private void initComponents(CustomerModel customerModel) {

    Icon = new JLabel();
    Panel = new JPanel();
    Name = new JLabel();
    Id = new JLabel();
    Phone = new JLabel();

    Color color = new Color(204, 224, 255);

    setLayout(new BorderLayout());
    setPreferredSize(new Dimension(40, 60));
    setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
    setBackground(color);

    Icon.setIcon(new ImageIcon(getClass().getResource("/resources/icons/menu/customer.png")));
    Icon.setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 0));
    add(Icon, BorderLayout.LINE_START);

    Panel.setLayout(new GridLayout(2, 0));
    Panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));
    Panel.setBackground(color);

    Name.setHorizontalAlignment(SwingConstants.LEFT);
    Name.setText(customerModel.getCustomerName());
    Name.setFont(new Font("Segoe UI", 0, 15));
    Name.setForeground(Color.RED);
    Panel.add(Name);

    Id.setHorizontalAlignment(SwingConstants.CENTER);
    Id.setText("" + customerModel.getId());
    Id.setForeground(new Color(0, 128, 0));
    Id.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    add(Id, BorderLayout.EAST);

    add(Panel, BorderLayout.CENTER);

    Phone.setText("" + customerModel.getPhone());
    Phone.setFont(new Font("Segoe UI", 0, 13));
    Phone.setForeground(new Color(102, 0, 51));
    Panel.add(Phone);

  }

  private JLabel Phone;
  private JLabel Icon;
  private JLabel Id;
  private JPanel Panel;
  private JLabel Name;
  private JTable table;

  public void updateDTFromList() {
    UserBUS.getInstance().refreshData();
    DefaultTableModel model_table = (DefaultTableModel) table.getModel();
    model_table.setRowCount(0);

    DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
    renderer.setHorizontalAlignment(SwingConstants.CENTER);

    for (UserModel user : UserBUS.getInstance().getAllModels()) {
      if (user.getUserStatus() == UserStatus.ACTIVE) {
        model_table.addRow(new Object[] { user.getId(), user.getUsername() + "", user.getPassword() + "",
            user.getEmail() + "", user.getName() + "", user.getPhone() + "", user.getGender() + "",
            user.getImage(), user.getRoleId(), user.getAddress(), user.getUserStatus() });
      }
    }
    // for (ProductModel user : ProductBUS.getInstance().getAllModels()) {
    // model_table.addRow(new Object[] { user.getId(), user.getName() + "",
    // user.getCategoryId() + "", user.getImage() + "",
    // user.getGender() + "" });
    // }
    table.getColumnModel().getColumn(7).setCellRenderer(renderer);
    table.getColumnModel().getColumn(7).setCellRenderer(new ImageRender());
  }
}
