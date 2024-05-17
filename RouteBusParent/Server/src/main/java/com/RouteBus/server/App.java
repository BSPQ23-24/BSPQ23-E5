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
            cal.set(1981, Calendar.NOVEMBER, 25);
            User user2 = new User("Xabi", "Alonso", "xabi.alonso@gmail.com", "123", nationalities[1], cal.getTime());
            cal.set(2003, Calendar.SEPTEMBER, 6);
            User admin = new User("Diego", "Merino", "admin@routebus.es", "123", nationalities[1], cal.getTime(), UserRole.ADMIN, new HashSet<Ticket>());
            cal.set(2003, Calendar.NOVEMBER, 22);
            User user3 = new User("Hector", "Fernandez", "hector.f.c@opendeusto.es", "123", nationalities[1], cal.getTime());
            cal.set(1988, Calendar.AUGUST, 21);
            User user4 = new User("Robert", "Lewandowski", "rlewandowski@gmail.com", "123", nationalities[17], cal.getTime());
            cal.set(1988, Calendar.MARCH, 2);
            User user5 = new User("Cristiano", "Ronaldo", "cristiano.ronaldo@gmail.com", "123", nationalities[10], cal.getTime());
            cal.set(1981, Calendar.OCTOBER, 3);
            User user6 = new User("Zlatan", "Ibrahimović", "zlatan@gmail.com", "123", nationalities[12], cal.getTime());
            cal.set(1984, Calendar.MAY, 2);
            User user7 = new User("Andrés", "Iniesta", "andresiniesta@gmail.com", "123", nationalities[1], cal.getTime());
            cal.set(2000, Calendar.JULY, 21);
            User user8 = new User("Erling", "Haaland", "erling.haaland@example.com", "123", nationalities[26], cal.getTime());
            cal.set(1989, Calendar.MAY, 15);
            User user9 = new User("Zinedine", "Zidane", "zizou@gmail.com", "123", nationalities[4], cal.getTime());
            cal.set(2003, Calendar.JUNE, 29);
            User user10 = new User("Jude", "Bellingham", "jude.bellingham@gmail.com", "123", nationalities[2], cal.getTime());
            cal.set(1981, Calendar.JANUARY, 7);
            User user11 = new User("Iñaki", "Williams", "inaki.williams@example.com", "123", nationalities[1], cal.getTime());
            cal.set(2003, Calendar.MAY, 25);
            User user12 = new User("Unai", "Gómez", "unai.gomez@example.com", "123", nationalities[1], cal.getTime());

            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(admin);
            userRepository.save(user3);
            userRepository.save(user4);
            userRepository.save(user5);
            userRepository.save(user6);
            userRepository.save(user7);
            userRepository.save(user8);
            userRepository.save(user9);
            userRepository.save(user10);
            userRepository.save(user11);
            userRepository.save(user12);

         // Buses
            Bus bus1 = new Bus("AB123CD", 50, "Mercedes", "Sprinter");
            Bus bus2 = new Bus("EF456GH", 45, "Volvo", "B9R");
            Bus bus3 = new Bus("IJ789KL", 40, "Scania", "K360");
            Bus bus4 = new Bus("MN012OP", 55, "MAN", "Lion's Coach");
            Bus bus5 = new Bus("QR345ST", 60, "Irizar", "i6");
            Bus bus6 = new Bus("UV678WX", 50, "Setra", "S 431 DT");
            Bus bus7 = new Bus("YZ901AB", 53, "Mercedes", "Tourismo");
            Bus bus8 = new Bus("CD234EF", 48, "Neoplan", "Cityliner");
            Bus bus9 = new Bus("GH567IJ", 47, "Van Hool", "TX16 Astron");
            Bus bus10 = new Bus("KL890MN", 52, "Prevost", "H3-45");

            busRepository.save(bus1);
            busRepository.save(bus2);
            busRepository.save(bus3);
            busRepository.save(bus4);
            busRepository.save(bus5);
            busRepository.save(bus6);
            busRepository.save(bus7);
            busRepository.save(bus8);
            busRepository.save(bus9);
            busRepository.save(bus10);

         // Stations
            Station station1 = new Station("Madrid", "Puerta del Sol");
            Station station2 = new Station("Barcelona", "Plaça Catalunya");
            Station station3 = new Station("Valencia", "Estació del Nord");
            Station station4 = new Station("Sevilla", "Estación de Santa Justa");
            Station station5 = new Station("Zaragoza", "Estación de Delicias");
            Station station6 = new Station("Bilbao", "Termibus");
            Station station7 = new Station("Málaga", "Estación de Autobuses de Málaga");
            Station station8 = new Station("Alicante", "Estación de Autobuses de Alicante");
            Station station9 = new Station("Zamora", "Estación de Autobuses de Zamora");
            Station station10 = new Station("Granada", "Estación de Autobuses de Granada");
            Station station11 = new Station("Valladolid", "Estación de Autobuses de Valladolid");
            Station station12 = new Station("Murcia", "Estación de Autobuses de Murcia");
            Station station13 = new Station("Santander", "Estación de Autobuses de Santander");
            Station station14 = new Station("Gijón", "Estación de Autobuses de Gijón");
            Station station15 = new Station("San Sebastián", "Estación de Autobuses de San Sebastián");

            stationRepository.save(station1);
            stationRepository.save(station2);
            stationRepository.save(station3);
            stationRepository.save(station4);
            stationRepository.save(station5);
            stationRepository.save(station6);
            stationRepository.save(station7);
            stationRepository.save(station8);
            stationRepository.save(station9);
            stationRepository.save(station10);
            stationRepository.save(station11);
            stationRepository.save(station12);
            stationRepository.save(station13);
            stationRepository.save(station14);
            stationRepository.save(station15);


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
            Route route3 = new Route("Bilbao-Madrid", "Bilbao", "Madrid", 401.0);
            route3.setStations(route3Stations);
            route3.setBuses(Set.of(bus1));
            routeRepository.save(route3);

            Set<Station> route4Stations = new HashSet<>();
            route4Stations.add(station6);
            route4Stations.add(station7);
            Route route4 = new Route("Zaragoza-Málaga", "Zaragoza", "Málaga", 742.0);
            route4.setStations(route4Stations);
            route4.setBuses(Set.of(bus2));
            routeRepository.save(route4);

            Set<Station> route5Stations = new HashSet<>();
            route5Stations.add(station8);
            route5Stations.add(station9);
            Route route5 = new Route("Alicante-Zamora", "Alicante", "Zamora", 679.0);
            route5.setStations(route5Stations);
            route5.setBuses(Set.of(bus1));
            routeRepository.save(route5);

            Set<Station> route6Stations = new HashSet<>();
            route6Stations.add(station10);
            route6Stations.add(station11);
            Route route6 = new Route("Granada-Valladolid", "Granada", "Valladolid", 680.0);
            route6.setStations(route6Stations);
            route6.setBuses(Set.of(bus2));
            routeRepository.save(route6);

            Set<Station> route7Stations = new HashSet<>();
            route7Stations.add(station12);
            route7Stations.add(station13);
            Route route7 = new Route("Murcia-Santander", "Murcia", "Santander", 790.0);
            route7.setStations(route7Stations);
            route7.setBuses(Set.of(bus1));
            routeRepository.save(route7);

            Set<Station> route8Stations = new HashSet<>();
            route8Stations.add(station14);
            route8Stations.add(station15);
            Route route8 = new Route("Gijón-San Sebastián", "Gijón", "San Sebastián", 380.0);
            route8.setStations(route8Stations);
            route8.setBuses(Set.of(bus2));
            routeRepository.save(route8);

            Set<Station> route9Stations = new HashSet<>();
            route9Stations.add(station3);
            route9Stations.add(station1);
            Route route9 = new Route("Valencia-Madrid", "Valencia", "Madrid", 360.0);
            route9.setStations(route9Stations);
            route9.setBuses(Set.of(bus1));
            routeRepository.save(route9);

            Set<Station> route10Stations = new HashSet<>();
            route10Stations.add(station4);
            route10Stations.add(station5);
            Route route10 = new Route("Sevilla-Zaragoza", "Sevilla", "Zaragoza", 800.0);
            route10.setStations(route10Stations);
            route10.setBuses(Set.of(bus2));
            routeRepository.save(route10);
            
            Set<Station> route11Stations = new HashSet<>();
            route11Stations.add(station7);
            route11Stations.add(station8);
            Route route11 = new Route("Málaga-Alicante", "Málaga", "Alicante", 530.0);
            route11.setStations(route11Stations);
            route11.setBuses(Set.of(bus1));
            routeRepository.save(route11);
            
            Set<Station> route12Stations = new HashSet<>();
            route12Stations.add(station11);
            route12Stations.add(station10);
            Route route12 = new Route("Valladolid-Granada", "Valladolid", "Granada", 570.0);
            route12.setStations(route12Stations);
            route12.setBuses(Set.of(bus2));
            routeRepository.save(route12);

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

            cal.set(2024, Calendar.JUNE, 15, 10, 0);
            Date departureTime4 = cal.getTime();
            cal.set(2024, Calendar.JUNE, 15, 18, 0);
            Date arrivalTime4 = cal.getTime();

            cal.set(2024, Calendar.JUNE, 16, 6, 0);
            Date departureTime5 = cal.getTime();
            cal.set(2024, Calendar.JUNE, 16, 14, 0);
            Date arrivalTime5 = cal.getTime();

            cal.set(2024, Calendar.JUNE, 18, 8, 0);
            Date departureTime6 = cal.getTime();
            cal.set(2024, Calendar.JUNE, 18, 16, 0);
            Date arrivalTime6 = cal.getTime();
            
            cal.set(2024, Calendar.JUNE, 20, 9, 0);
            Date departureTime7 = cal.getTime();
            cal.set(2024, Calendar.JUNE, 20, 13, 0);
            Date arrivalTime7 = cal.getTime();

            cal.set(2024, Calendar.JUNE, 22, 11, 0);
            Date departureTime8 = cal.getTime();
            cal.set(2024, Calendar.JUNE, 22, 17, 0);
            Date arrivalTime8 = cal.getTime();

            cal.set(2024, Calendar.JUNE, 25, 7, 0);
            Date departureTime9 = cal.getTime();
            cal.set(2024, Calendar.JUNE, 25, 13, 0);
            Date arrivalTime9 = cal.getTime();
            
            cal.set(2024, Calendar.JUNE, 27, 6, 0);
            Date departureTime10 = cal.getTime();
            cal.set(2024, Calendar.JUNE, 27, 14, 0);
            Date arrivalTime10 = cal.getTime();

            cal.set(2024, Calendar.JUNE, 28, 5, 0);
            Date departureTime11 = cal.getTime();
            cal.set(2024, Calendar.JUNE, 28, 13, 0);
            Date arrivalTime11 = cal.getTime();

            cal.set(2024, Calendar.JUNE, 30, 8, 0);
            Date departureTime12 = cal.getTime();
            cal.set(2024, Calendar.JUNE, 30, 16, 0);
            Date arrivalTime12 = cal.getTime();
            
            Schedule schedule1 = new Schedule(route1, departureTime1, arrivalTime1);
            Schedule schedule2 = new Schedule(route2, departureTime2, arrivalTime2);
            Schedule schedule3 = new Schedule(route3, departureTime3, arrivalTime3);
            Schedule schedule4 = new Schedule(route4, departureTime4, arrivalTime4);
            Schedule schedule5 = new Schedule(route5, departureTime5, arrivalTime5);
            Schedule schedule6 = new Schedule(route6, departureTime6, arrivalTime6);
            Schedule schedule7 = new Schedule(route7, departureTime7, arrivalTime7);
            Schedule schedule8 = new Schedule(route8, departureTime8, arrivalTime8);
            Schedule schedule9 = new Schedule(route9, departureTime9, arrivalTime9);
            Schedule schedule10 = new Schedule(route10, departureTime10, arrivalTime10);
            Schedule schedule11 = new Schedule(route11, departureTime11, arrivalTime11);
            Schedule schedule12 = new Schedule(route12, departureTime12, arrivalTime12);
            
            scheduleRepository.save(schedule1);
            scheduleRepository.save(schedule2);
            scheduleRepository.save(schedule3);
            scheduleRepository.save(schedule4);
            scheduleRepository.save(schedule5);
            scheduleRepository.save(schedule6);
            scheduleRepository.save(schedule7);
            scheduleRepository.save(schedule8);
            scheduleRepository.save(schedule9);
            scheduleRepository.save(schedule10);
            scheduleRepository.save(schedule11);
            scheduleRepository.save(schedule12);

         // Tickets
            Ticket ticket1 = new Ticket(user1, 1, 50.0, TicketStatus.RESERVED, schedule1);
            Ticket ticket2 = new Ticket(user2, 2, 60.0, TicketStatus.PURCHASED, schedule2);
            Ticket ticket3 = new Ticket(user3, 3, 45.0, TicketStatus.PURCHASED, schedule3);
            Ticket ticket4 = new Ticket(user4, 4, 70.0, TicketStatus.RESERVED, schedule4);
            Ticket ticket5 = new Ticket(user5, 5, 55.0, TicketStatus.PURCHASED, schedule5);
            Ticket ticket6 = new Ticket(user6, 6, 65.0, TicketStatus.PURCHASED, schedule6);
            Ticket ticket7 = new Ticket(user7, 7, 40.0, TicketStatus.RESERVED, schedule7);
            Ticket ticket8 = new Ticket(user8, 8, 75.0, TicketStatus.PURCHASED, schedule8);
            Ticket ticket9 = new Ticket(user9, 9, 35.0, TicketStatus.RESERVED, schedule9);
            Ticket ticket10 = new Ticket(user10, 10, 80.0, TicketStatus.PURCHASED, schedule10);
            Ticket ticket11 = new Ticket(user11, 11, 50.0, TicketStatus.RESERVED, schedule11);
            Ticket ticket12 = new Ticket(user12, 12, 90.0, TicketStatus.PURCHASED, schedule12);

            // Save tickets
            ticketRepository.save(ticket1);
            ticketRepository.save(ticket2);
            ticketRepository.save(ticket3);
            ticketRepository.save(ticket4);
            ticketRepository.save(ticket5);
            ticketRepository.save(ticket6);
            ticketRepository.save(ticket7);
            ticketRepository.save(ticket8);
            ticketRepository.save(ticket9);
            ticketRepository.save(ticket10);
            ticketRepository.save(ticket11);
            ticketRepository.save(ticket12);

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
