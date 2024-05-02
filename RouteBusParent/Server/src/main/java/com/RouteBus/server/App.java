package com.RouteBus.server;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.RouteBus.server.controller.BusServiceRestController;
import com.RouteBus.server.dao.BusRepository;
import com.RouteBus.server.dao.RouteRepository;
import com.RouteBus.server.dao.StationRepository;
import com.RouteBus.server.dao.TicketRepository;
import com.RouteBus.server.dao.UserRepository;
import com.RouteBus.server.model.Bus;
import com.RouteBus.server.model.Route;
import com.RouteBus.server.model.Station;
import com.RouteBus.server.model.Ticket;
import com.RouteBus.server.model.User;

import org.springframework.boot.CommandLineRunner;


@SpringBootApplication
public class App {

	private static final Logger log = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@Bean
	CommandLineRunner demo(UserRepository repository, RouteRepository routeRepository,BusRepository busRepository, TicketRepository ticketRepository, StationRepository stationRepository, BusServiceRestController busController) {
		return (args) -> {
			User diego = new User("Diego", "Merino", "diego.merino@opendeusto.es", "123");
			repository.save(diego);
			log.info("USER Services AVAILABLE ...");
			
			Route route= new Route(new HashSet<Station>(), new HashSet<Bus>(), 0);
			routeRepository.save(route);
			log.info(routeRepository.findById(1).toString());
			
			Bus bus= new Bus( new HashSet<Route>(),"Jose", 55);
			busRepository.save(bus);
			
			Station station= new Station("San Mames", "Bilbao" ,new HashSet<Route>());
			stationRepository.save(station);
			
			Ticket ticket= new Ticket(diego,"San Mames",1.50,new Date(System.currentTimeMillis()));
			ticketRepository.save(ticket);
//			busController.addBusToRoute(bus.getId(), route.getId());
//			busController.addStationToRoute(station.getId(), route.getId());
		};
	}
}