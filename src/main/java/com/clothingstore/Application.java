package com.clothingstore;
    
import javax.swing.*;

import com.clothingstore.gui.admin.Dashboard.Dashboard;
import com.clothingstore.gui.employee.HomePage;
import com.clothingstore.gui.login.Login;
import com.clothingstore.gui.manager.Main;

public class Application {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            /*-----employee----*/
            // HomePage homePage = HomePage.getInstance();
            // homePage.setVisible(true);
            /*-----admin----*/
            // Dashboard dashboard = Dashboard.getInstance();
            // dashboard.setVisible(true);
            /*-----manager----*/
            // Main main = Main.getInstance();
            // main.setVisible(true);
            Login login = Login.getInstance();
            login.setVisible(true);
        });
    }
}
