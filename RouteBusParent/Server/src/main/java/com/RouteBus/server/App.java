package com.RouteBus.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.RouteBus.server.dao.*;
import com.RouteBus.server.model.*;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class App {

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    private static final Nationality[] nationalities = {
            new Nationality("American", "en_US"),
            new Nationality("Spanish", "es"),
            new Nationality("British", "en"),
            new Nationality("Chinese", "zh"),
            new Nationality("French", "fr"),
            new Nationality("German", "de"),
            new Nationality("Indian", "hi"),
            new Nationality("Japanese", "ja"),
            new Nationality("Russian", "ru"),
            new Nationality("Italian", "it"),
            new Nationality("Portuguese", "pt"),
            new Nationality("Dutch", "nl"),
            new Nationality("Swedish", "sv"),
            new Nationality("Korean", "ko"),
            new Nationality("Arabic", "ar"),
            new Nationality("Turkish", "tr"),
            new Nationality("Greek", "el"),
            new Nationality("Polish", "pl"),
            new Nationality("Vietnamese", "vi"),
            new Nationality("Thai", "th"),
            new Nationality("Indonesian", "id"),
            new Nationality("Finnish", "fi"),
            new Nationality("Czech", "cs"),
            new Nationality("Danish", "da"),
            new Nationality("Hungarian", "hu"),
            new Nationality("Malay", "ms"),
            new Nationality("Norwegian", "no"),
            new Nationality("Romanian", "ro"),
            new Nationality("Slovak", "sk")
        };
    
    private static int numberOfLoadedNationalities = 0;
    
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    CommandLineRunner demo(BusRepository busRepository, 
                           NationalityRepository nationalityRepository,
                           RouteRepository routeRepository, 
                           ScheduleRepository scheduleRepository, 
                           StationRepository stationRepository, 
                           TicketRepository ticketRepository, 
                           UserRepository userRepository) {
        return (args) -> {
            // Nationalities
        	if (nationalityRepository.count() == 0) {
                numberOfLoadedNationalities = 0;
                for (Nationality nationality : nationalities) {
                    if(nationalityRepository.save(nationality) != null)	numberOfLoadedNationalities++;
                }
                logger.info(numberOfLoadedNationalities + " nationalities loaded into the database out of " + nationalities.length);
            } else {
                logger.info("Nationalities are already loaded.");
            }

            // Users
            Calendar cal = Calendar.getInstance();
            cal.set(2003, Calendar.SEPTEMBER, 6);
            User user1 = new User("Diego", "Merino", "diego.merino@opendeusto.es", "123", nationalities[1], cal.getTime());
            cal.set(1990, Calendar.MARCH, 25);
            User user2 = new User("Jane", "Smith", "jane.smith@example.com", "123", nationalities[0], cal.getTime());
            cal.set(2003, Calendar.SEPTEMBER, 6);
            User admin = new User("Diego", "Merino", "admin@routebus.es", "123", nationalities[1], cal.getTime(), UserRole.ADMIN, new HashSet<Ticket>());
            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(admin);

            // Buses
            Bus bus1 = new Bus("AB123CD", 50, "Mercedes", "Sprinter");
            Bus bus2 = new Bus("EF456GH", 45, "Volvo", "B9R");
            busRepository.save(bus1);
            busRepository.save(bus2);

            // Stations
            Station station1 = new Station("Madrid", "Puerta del Sol");
            Station station2 = new Station("Barcelona", "Plaça Catalunya");
            Station station3 = new Station("Valencia", "Estació del Nord");
            Station station4 = new Station("Sevilla", "Estación de Santa Justa");
            Station station5 = new Station("Zaragoza", "Estación de Delicias");
            stationRepository.save(station1);
            stationRepository.save(station2);
            stationRepository.save(station3);
            stationRepository.save(station4);
            stationRepository.save(station5);

            // Routes
            Set<Station> route1Stations = new HashSet<>();
            route1Stations.add(station1);
            route1Stations.add(station2);
            Route route1 = new Route("Madrid-Barcelona", "Madrid", "Barcelona", 620.0);
            route1.setStations(route1Stations);
            route1.setBuses(Set.of(bus1));
            routeRepository.save(route1);

            Set<Station> route2Stations = new HashSet<>();
            route2Stations.add(station3);
            route2Stations.add(station4);
            Route route2 = new Route("Valencia-Sevilla", "Valencia", "Sevilla", 650.0);
            route2.setStations(route2Stations);
            route2.setBuses(Set.of(bus2));
            routeRepository.save(route2);

            Set<Station> route3Stations = new HashSet<>();
            route3Stations.add(station5);
            route3Stations.add(station1);
            Route route3 = new Route("Zaragoza-Madrid", "Zaragoza", "Madrid", 320.0);
            route3.setStations(route3Stations);
            route3.setBuses(Set.of(bus1));
            routeRepository.save(route3);

            // Schedules
            cal.set(2024, Calendar.JUNE, 10, 8, 0);
            Date departureTime1 = cal.getTime();
            cal.set(2024, Calendar.JUNE, 10, 14, 0);
            Date arrivalTime1 = cal.getTime();

            cal.set(2024, Calendar.JUNE, 12, 9, 0);
            Date departureTime2 = cal.getTime();
            cal.set(2024, Calendar.JUNE, 12, 15, 0);
            Date arrivalTime2 = cal.getTime();

            cal.set(2024, Calendar.JUNE, 13, 7, 0);
            Date departureTime3 = cal.getTime();
            cal.set(2024, Calendar.JUNE, 13, 11, 0);
            Date arrivalTime3 = cal.getTime();

            Schedule schedule1 = new Schedule(route1, departureTime1, arrivalTime1);
            Schedule schedule2 = new Schedule(route2, departureTime2, arrivalTime2);
            Schedule schedule3 = new Schedule(route3, departureTime3, arrivalTime3);
            scheduleRepository.save(schedule1);
            scheduleRepository.save(schedule2);
            scheduleRepository.save(schedule3);

            // Tickets
            Ticket ticket1 = new Ticket(user1, 1, 50.0, TicketStatus.RESERVED, schedule1);
            Ticket ticket2 = new Ticket(user2, 2, 60.0, TicketStatus.PURCHASED, schedule2);
            Ticket ticket3 = new Ticket(user1, 3, 45.0, TicketStatus.PURCHASED, schedule3);
            ticketRepository.save(ticket1);
            ticketRepository.save(ticket2);
            ticketRepository.save(ticket3);

            logger.info("Data loading complete.");
        };
    }
    
    public static int getNumberOfNationalities() {
    	return nationalities.length;
    }
    
    public static int getNumberOfLoadedNationalities() {
    	return numberOfLoadedNationalities;
    }
}
