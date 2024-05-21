package com.RouteBus.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.RouteBus.server.dao.*;
import com.RouteBus.server.model.*;

import java.util.*;

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

    private static final int numberOfSchedulesPerRoute = 5;
    private static final int scheduleIntervalDays = 2;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    CommandLineRunner demo(BusRepository busRepository, NationalityRepository nationalityRepository,
                           RouteRepository routeRepository, ScheduleRepository scheduleRepository,
                           StationRepository stationRepository, TicketRepository ticketRepository,
                           UserRepository userRepository) {
        return (args) -> {
            if (nationalityRepository.count() == 0) {
                List<Nationality> nationalitiesList = Arrays.asList(nationalities);
                nationalityRepository.saveAll(nationalitiesList);
                logger.info("{} nationalities loaded into the database.", nationalitiesList.size());
            } else {
                logger.info("Nationalities are already loaded.");
            }

            // Load Users
            Calendar cal = Calendar.getInstance();
            cal.set(2003, Calendar.SEPTEMBER, 6);
            List<User> users = Arrays.asList(
                new User("Diego", "Merino", "admin@routebus.es", "123", nationalities[1], cal.getTime(), UserRole.ADMIN, new HashSet<>()),
                new User("Diego", "Merino", "diego.merino@opendeusto.es", "123", nationalities[1], cal.getTime()),
                new User("Xabi", "Alonso", "xabi.alonso@gmail.com", "123", nationalities[1], cal.getTime()),
                new User("Hector", "Fernandez", "hector.f.c@opendeusto.es", "123", nationalities[1], cal.getTime()),
                new User("Robert", "Lewandowski", "rlewandowski@gmail.com", "123", nationalities[17], cal.getTime()),
                new User("Cristiano", "Ronaldo", "cristiano.ronaldo@gmail.com", "123", nationalities[10], cal.getTime()),
                new User("Zlatan", "Ibrahimovic", "zlatan@gmail.com", "123", nationalities[12], cal.getTime()),
                new User("Andres", "Iniesta", "andresiniesta@gmail.com", "123", nationalities[1], cal.getTime()),
                new User("Erling", "Haaland", "erling.haaland@example.com", "123", nationalities[26], cal.getTime()),
                new User("Zinedine", "Zidane", "zizou@gmail.com", "123", nationalities[4], cal.getTime()),
                new User("Jude", "Bellingham", "jude.bellingham@gmail.com", "123", nationalities[2], cal.getTime()),
                new User("Inaki", "Williams", "inaki.williams@example.com", "123", nationalities[1], cal.getTime()),
                new User("Unai", "Gomez", "unai.gomez@example.com", "123", nationalities[1], cal.getTime())
            );

            userRepository.saveAll(users);

            // Load Buses
            List<Bus> buses = Arrays.asList(
                new Bus("AB123CD", 50, "Mercedes", "Sprinter"),
                new Bus("EF456GH", 45, "Volvo", "B9R"),
                new Bus("IJ789KL", 40, "Scania", "K360"),
                new Bus("MN012OP", 55, "MAN", "Lion's Coach"),
                new Bus("QR345ST", 60, "Irizar", "i6"),
                new Bus("UV678WX", 50, "Setra", "S 431 DT"),
                new Bus("YZ901AB", 53, "Mercedes", "Tourismo"),
                new Bus("CD234EF", 48, "Neoplan", "Cityliner"),
                new Bus("GH567IJ", 47, "Van Hool", "TX16 Astron"),
                new Bus("KL890MN", 52, "Prevost", "H3-45")
            );

            busRepository.saveAll(buses);

            // Load Stations
            List<Station> stations = Arrays.asList(
                new Station("Madrid", "Puerta del Sol"),
                new Station("Barcelona", "Placa Catalunya"),
                new Station("Valencia", "Estacio del Nord"),
                new Station("Sevilla", "Estacion de Santa Justa"),
                new Station("Zaragoza", "Estacion de Delicias"),
                new Station("Bilbao", "Termibus"),
                new Station("Malaga", "Estacion de Autobuses de Malaga"),
                new Station("Alicante", "Estacion de Autobuses de Alicante"),
                new Station("Zamora", "Estacion de Autobuses de Zamora"),
                new Station("Granada", "Estacion de Autobuses de Granada"),
                new Station("Valladolid", "Estacion de Autobuses de Valladolid"),
                new Station("Murcia", "Estacion de Autobuses de Murcia"),
                new Station("Santander", "Estacion de Autobuses de Santander"),
                new Station("Gijon", "Estacion de Autobuses de Gijon"),
                new Station("San Sebastian", "Estacion de Autobuses de San Sebastian")
            );

            stationRepository.saveAll(stations);

            // Load Routes
            List<Route> routes = Arrays.asList(
                new Route("Madrid-Barcelona", "Madrid", "Barcelona", 620.0, new HashSet<>(Arrays.asList(stations.get(0), stations.get(1))), new HashSet<>(Arrays.asList(buses.get(0), buses.get(1), buses.get(2)))),
                new Route("Valencia-Sevilla", "Valencia", "Sevilla", 650.0, new HashSet<>(Arrays.asList(stations.get(2), stations.get(3))), new HashSet<>(Arrays.asList(buses.get(3), buses.get(4)))),
                new Route("Bilbao-Madrid", "Bilbao", "Madrid", 401.0, new HashSet<>(Arrays.asList(stations.get(4), stations.get(0))), new HashSet<>(Arrays.asList(buses.get(5), buses.get(6)))),
                new Route("Zaragoza-Malaga", "Zaragoza", "Malaga", 742.0, new HashSet<>(Arrays.asList(stations.get(5), stations.get(6))), new HashSet<>(Arrays.asList(buses.get(7), buses.get(8), buses.get(9)))),
                new Route("Alicante-Zamora", "Alicante", "Zamora", 679.0, new HashSet<>(Arrays.asList(stations.get(7), stations.get(8))), new HashSet<>(Arrays.asList(buses.get(0), buses.get(1)))),
                new Route("Granada-Valladolid", "Granada", "Valladolid", 680.0, new HashSet<>(Arrays.asList(stations.get(9), stations.get(10))), new HashSet<>(Arrays.asList(buses.get(2), buses.get(3)))),
                new Route("Murcia-Santander", "Murcia", "Santander", 790.0, new HashSet<>(Arrays.asList(stations.get(11), stations.get(12))), new HashSet<>(Arrays.asList(buses.get(4), buses.get(5)))),
                new Route("Gijon-San_Sebastian", "Gijon", "San Sebastian", 380.0, new HashSet<>(Arrays.asList(stations.get(13), stations.get(14))), new HashSet<>(Arrays.asList(buses.get(6), buses.get(7)))),
                new Route("Valencia-Madrid", "Valencia", "Madrid", 360.0, new HashSet<>(Arrays.asList(stations.get(2), stations.get(0))), new HashSet<>(Arrays.asList(buses.get(8)))),
                new Route("Sevilla-Zaragoza", "Sevilla", "Zaragoza", 800.0, new HashSet<>(Arrays.asList(stations.get(3), stations.get(4))), new HashSet<>(Arrays.asList(buses.get(9)))),
                new Route("Malaga-Alicante", "Malaga", "Alicante", 530.0, new HashSet<>(Arrays.asList(stations.get(6), stations.get(7))), new HashSet<>(Arrays.asList(buses.get(0), buses.get(1), buses.get(2)))),
                new Route("Valladolid-Granada", "Valladolid", "Granada", 570.0, new HashSet<>(Arrays.asList(stations.get(10), stations.get(9))), new HashSet<>(Arrays.asList(buses.get(3), buses.get(4), buses.get(5))))
            );

            routeRepository.saveAll(routes);

            Calendar startCalendar = Calendar.getInstance();
            startCalendar.set(2024, Calendar.JUNE, 10, 8, 0);

            List<Schedule> allSchedules = new ArrayList<>();
            List<Ticket> allTickets = new ArrayList<>();

            for (Route route : routes) {
                Set<Schedule> routeSchedules = new HashSet<>();
                for (int i = 0; i < numberOfSchedulesPerRoute; i++) {
                    Date departureTime = startCalendar.getTime();
                    startCalendar.add(Calendar.HOUR, 3); // assuming a 3-hour travel time for simplicity
                    Date arrivalTime = startCalendar.getTime();
                    Schedule schedule = new Schedule(route, departureTime, arrivalTime);
                    routeSchedules.add(schedule);
                    allSchedules.add(schedule);

                    startCalendar.add(Calendar.HOUR, -6); // reset to departure time
                    startCalendar.add(Calendar.DAY_OF_YEAR, scheduleIntervalDays);

                    for (int seatNumber = 1; seatNumber <= route.getBuses().stream().mapToInt(Bus::getCapacity).sum(); seatNumber++) {
                        User user = users.get(seatNumber % users.size());
                        double price = 50.0 + (seatNumber % 100);
                        TicketStatus status = (seatNumber % 2 == 0) ? TicketStatus.RESERVED : TicketStatus.PURCHASED;
                        Ticket ticket = new Ticket(user, seatNumber, price, status, schedule);
                        allTickets.add(ticket);
                    }
                }
                route.setSchedules(routeSchedules);
            }

            scheduleRepository.saveAll(allSchedules);
            ticketRepository.saveAll(allTickets);
            routeRepository.saveAll(routes);

            logger.info("Data loading complete.");
        };
    }
}
