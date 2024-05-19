package com.RouteBus.client;

import javax.swing.SwingUtilities;
import com.RouteBus.client.gui.InitialWindow;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            InitialWindow initialWindow = new InitialWindow();
            initialWindow.setVisible(true);
        });
    }
}
