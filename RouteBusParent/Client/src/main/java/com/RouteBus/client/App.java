package com.RouteBus.client;

import java.util.HashSet;

import com.RouteBus.client.gateway.ServerGateway;
import com.RouteBus.client.gui.LoginWindow;
import com.RouteBus.client.model.Bus;
import com.RouteBus.client.model.Route;

public class App {
	public static void main(String[] args) {
		
		new LoginWindow(new ServerGateway());
		System.out.println(new ServerGateway().registerBus(new Bus(new HashSet<Route>(), "Diego", 29)));
		System.out.println(new ServerGateway().getAllBuses());
		System.out.println(new ServerGateway().getAllRoutes());
		System.out.println(new ServerGateway().checkPassword("diego.merino@opendeusto.es", "123"));
		System.out.println(new ServerGateway().checkUser("diego.merino@opendeusto.es"));
	}
}
