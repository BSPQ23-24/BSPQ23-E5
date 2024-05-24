package com.RouteBus.client;

import com.RouteBus.client.gui.InitialWindow;
import javax.swing.SwingUtilities;

public class App {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			InitialWindow initialWindow = new InitialWindow();
			initialWindow.setVisible(true);
		});
	}
}