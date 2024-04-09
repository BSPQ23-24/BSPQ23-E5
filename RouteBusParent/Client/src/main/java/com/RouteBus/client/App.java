package com.RouteBus.client;

import com.RouteBus.client.gateway.ServerGateway;
import com.RouteBus.client.gui.LoginWindow;

public class App {
	public static void main(String[] args) {
		new LoginWindow(new ServerGateway());
//		System.out.println(new ServerGateway().checkPassword("diego.merino@opendeusto.es", "123"));
//		System.out.println(new ServerGateway().checkUser("diego.merino@opendeusto.es"));
	}
}
