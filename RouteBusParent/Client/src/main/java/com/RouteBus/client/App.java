package com.RouteBus.client;

import javax.swing.SwingUtilities;

import com.RouteBus.client.gui.LoginWindow;

public class App {
	public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginWindow loginWindow = new LoginWindow();
            loginWindow.setVisible(true);
        });
	}
}