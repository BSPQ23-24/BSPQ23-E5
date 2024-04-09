package com.RouteBus.client;

import com.RouteBus.client.gateway.ServerGateway;

public class App {
	public static void main(String[] args) {
		new ServerGateway().checkPassword("diego.merino@opendeusto.es", "123");
	}
}
