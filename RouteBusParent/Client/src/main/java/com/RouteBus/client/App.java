package com.RouteBus.client;

import com.RouteBus.client.controller.BusController;
import com.RouteBus.client.controller.RouteController;
import com.RouteBus.client.controller.ScheduleController;
import com.RouteBus.client.controller.StationController;
import com.RouteBus.client.controller.TicketController;
import com.RouteBus.client.controller.UserController;
import com.RouteBus.client.dto.UserDTO;
import com.RouteBus.client.dto.BusDTO;
import com.RouteBus.client.dto.NationalityDTO;
import com.RouteBus.client.dto.RouteDTO;
import com.RouteBus.client.dto.ScheduleDTO;
import com.RouteBus.client.dto.StationDTO;
import com.RouteBus.client.dto.TicketDTO;
import com.RouteBus.client.dto.TicketDTO.TicketStatus;
import com.RouteBus.client.gui.LoginWindow;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class App {
	public static void main(String[] args) {
		new LoginWindow();

//		testServerConnectivityForUser();
//      testServerConnectivityForBus();
//      testServerConnectivityForRoutes();
//      testServerConnectivityForSchedule();
//      testServerConnectivityForStation();
//      testServerConnectivityForTicket();
	}

	public static void testServerConnectivityForUser() {
		UserController controller = UserController.getInstance();

		NationalityDTO nationality = new NationalityDTO("American");
		UserDTO newUser = new UserDTO("Test", "User", "test@example.com", "password123", nationality, new Date(),
				UserDTO.UserRole.CUSTOMER, new HashSet<TicketDTO>());

		System.out.println("Testing user creation...");
		boolean createUserResult = controller.createUser(newUser);
		System.out.println("Create User Result: " + createUserResult);

		System.out.println("Testing getting all users...");
		List<UserDTO> users = controller.getAllUsers();
		if (users != null) {
			System.out.println("Number of users fetched: " + users.size());
		} else {
			System.out.println("Failed to fetch users");
		}

		System.out.println("Testing getting user by email...");
		UserDTO userByEmail = controller.getUserByEmail("test@example.com");
		System.out.println("Get User by Email Result: " + (userByEmail != null ? "Success" : "Failed"));

		System.out.println("Testing updating a user...");
		if (userByEmail != null) {
			userByEmail.setFirstName("Updated Test");
			boolean updateUserResult = controller.updateUser(userByEmail);
			System.out.println("Update User Result: " + updateUserResult);
		}

		System.out.println("Testing user check...");
		boolean checkUserResult = controller.checkUser("test@example.com");
		System.out.println("Check User Result: " + checkUserResult);

		System.out.println("Testing password check...");
		boolean checkPasswordResult = controller.checkPassword("test@example.com", "password123");
		System.out.println("Check Password Result: " + checkPasswordResult);

		System.out.println("Testing getting nationalities...");
		List<NationalityDTO> nationalities = controller.getNationalities();
		if (nationalities != null) {
			System.out.println("Number of nationalities fetched: " + nationalities.size());
		} else {
			System.out.println("Failed to fetch nationalities");
		}

		System.out.println("Testing deleting a user...");
		boolean deleteUserResult = controller.deleteUser("test@example.com");
		System.out.println("Delete User Result: " + deleteUserResult);
	}

	public static void testServerConnectivityForBus() {
		BusController busController = BusController.getInstance();

		System.out.println("Testing bus creation...");
		BusDTO newBus = new BusDTO("AB1234", 40, "Volvo", "9900");
		boolean createBusResult = busController.createBus(newBus);
		System.out.println("Create Bus Result: " + createBusResult);

		System.out.println("Testing getting all buses...");
		List<BusDTO> buses = busController.getAllBuses();
		if (buses != null) {
			System.out.println("Number of buses fetched: " + buses.size());
		} else {
			System.out.println("Failed to fetch buses");
		}

		System.out.println("Testing getting bus by license plate...");
		BusDTO busByLicensePlate = busController.getBusById("AB1234");
		System.out.println("Get Bus by License Plate Result: " + (busByLicensePlate != null ? "Success" : "Failed"));

		System.out.println("Testing updating a bus...");
		if (busByLicensePlate != null) {
			busByLicensePlate.setCapacity(45);
			boolean updateBusResult = busController.updateBus(busByLicensePlate);
			System.out.println("Update Bus Result: " + updateBusResult);
		}

		System.out.println("Testing deleting a bus...");
		boolean deleteBusResult = busController.deleteBus("AB1234");
		System.out.println("Delete Bus Result: " + deleteBusResult);
	}

	public static void testServerConnectivityForRoutes() {
		RouteController routeController = RouteController.getInstance();
		BusController busController = BusController.getInstance();
		StationController stationController = StationController.getInstance();

		// Crear y guardar un bus de prueba
		BusDTO newBus = new BusDTO("AB1234", 40, "Volvo", "9900");
		boolean createBusResult = busController.createBus(newBus);
		System.out.println("Create Bus Result: " + createBusResult);

		// Crear y guardar una estación de prueba
		StationDTO newStation = new StationDTO("Station123", "123 Main Street");
		boolean createStationResult = stationController.createStation(newStation);
		System.out.println("Create Station Result: " + createStationResult);

		System.out.println("Testing route creation...");
		RouteDTO newRoute = new RouteDTO("Route1", "Start Point", "End Point", 120.0);
		boolean createRouteResult = routeController.createRoute(newRoute);
		System.out.println("Create Route Result: " + createRouteResult);

		System.out.println("Testing getting all routes...");
		List<RouteDTO> routes = routeController.getAllRoutes();
		if (routes != null) {
			System.out.println("Number of routes fetched: " + routes.size());
		} else {
			System.out.println("Failed to fetch routes");
		}

		System.out.println("Testing getting route by name...");
		RouteDTO routeByName = routeController.getRouteById(newRoute.getName());
		System.out.println("Get Route by Name Result: " + (routeByName != null ? "Success" : "Failed"));

		System.out.println("Testing updating a route...");
		if (routeByName != null) {
			routeByName.setEndPoint("New End Point");
			boolean updateRouteResult = routeController.updateRoute(routeByName);
			System.out.println("Update Route Result: " + updateRouteResult);
		}

		System.out.println("Testing obtaining routes by bus...");
		List<RouteDTO> routesByBus = routeController.obtainRoutesByBus(newBus.getLicensePlate());
		System.out.println("Obtain Routes by Bus Result: " + (routesByBus != null ? "Success" : "Failed"));

		System.out.println("Testing deleting a route...");
		boolean deleteRouteResult = routeController.deleteRoute(newRoute.getName());
		System.out.println("Delete Route Result: " + deleteRouteResult);
	}

	public static void testServerConnectivityForSchedule() {
		ScheduleController scheduleController = ScheduleController.getInstance();

		// Creating a test route
		RouteDTO testRoute = new RouteDTO("TestRoute", "StartPoint", "EndPoint", 100.0);
		RouteController.getInstance().createRoute(testRoute);

		// Assuming constructors exist that take the necessary parameters
		ScheduleDTO newSchedule = new ScheduleDTO(testRoute, new Date(), new Date());

		System.out.println("Testing schedule creation...");
		boolean createScheduleResult = scheduleController.createSchedule(newSchedule);
		System.out.println("Create Schedule Result: " + createScheduleResult);

		System.out.println("Testing getting all schedules...");
		Set<ScheduleDTO> schedules = scheduleController.getAllSchedules();
		if (schedules != null) {
			System.out.println("Number of schedules fetched: " + schedules.size());
			if (!schedules.isEmpty()) {
				System.out.println("Testing updating a schedule...");
				ScheduleDTO firstSchedule = schedules.stream().findFirst().orElse(null);
				if (firstSchedule == null) {
					System.out.println("Failed to fetch schedules");
				} else {
					firstSchedule.setDepartureTime(new Date()); // Updating departure time as an example
					firstSchedule.setArrivalTime(new Date()); // Updating arrival time as an example
					boolean updateScheduleResult = scheduleController.updateSchedule(firstSchedule);
					System.out.println("Update Schedule Result: " + updateScheduleResult);
				}

			}

			if (!schedules.isEmpty()) {
				System.out.println("Testing deleting a schedule...");
				ScheduleDTO s = schedules.stream().findFirst().orElse(null);
				if (s == null) {
					System.out.println("Failed to fetch schedules");
				} else {
					String scheduleId = s.getId();
					boolean deleteScheduleResult = scheduleController.deleteSchedule(scheduleId);
					System.out.println("Delete Schedule Result: " + deleteScheduleResult);
				}
			}
		} else {
			System.out.println("Failed to fetch schedules");
		}
	}

	public static void testServerConnectivityForStation() {
		StationController stationController = StationController.getInstance();

		// Create a new Station
		StationDTO newStation = new StationDTO("Central Station", "123 Main St");
		System.out.println("Testing station creation...");
		boolean createStationResult = stationController.createStation(newStation);
		System.out.println("Create Station Result: " + createStationResult);

		// Fetch all Stations
		System.out.println("Testing getting all stations...");
		List<StationDTO> stations = stationController.getAllStations();
		if (stations != null) {
			System.out.println("Number of stations fetched: " + stations.size());
		} else {
			System.out.println("Failed to fetch stations");
		}

		// Get Station by Name
		System.out.println("Testing getting station by name...");
		StationDTO stationByName = stationController.getStationById("Central Station");
		System.out.println("Get Station by Name Result: " + (stationByName != null ? "Success" : "Failed"));

		// Update a Station
		System.out.println("Testing updating a station...");
		if (stationByName != null) {
			stationByName.setLocation("456 New Address St");
			boolean updateStationResult = stationController.updateStation(stationByName);
			System.out.println("Update Station Result: " + updateStationResult);
		}

		// Delete a Station
		System.out.println("Testing deleting a station...");
		boolean deleteStationResult = stationController.deleteStation("Central Station");
		System.out.println("Delete Station Result: " + deleteStationResult);
	}

	public static void testServerConnectivityForTicket() {
		TicketController ticketController = TicketController.getInstance();

		// Example information
		UserDTO exampleUser = new UserDTO("Diego", "Merino", "diego@example.com", "securepass", null,
				new java.util.Date());
		RouteDTO testRoute = new RouteDTO("ExampleRoute", "StartPoint", "EndPoint", 100.0);
		ScheduleDTO newSchedule = new ScheduleDTO(testRoute, new Date(), new Date());

		RouteController.getInstance().createRoute(testRoute);
		ScheduleController.getInstance().createSchedule(newSchedule);
		UserController.getInstance().createUser(exampleUser);

		// Create a new ticket
		TicketDTO newTicket = new TicketDTO(exampleUser, 25, 59.99, TicketStatus.RESERVED, newSchedule);
		exampleUser.setTickets(new HashSet<TicketDTO>(List.of(newTicket)));
		System.out.println("Testing ticket creation...");
		boolean createTicketResult = ticketController.createTicket(newTicket);
		System.out.println("Create Ticket Result: " + createTicketResult);

		// Fetch all tickets
		System.out.println("Testing getting all tickets...");
		List<TicketDTO> tickets = ticketController.getAllTickets();
		if (tickets != null && !tickets.isEmpty()) {
			System.out.println("Number of tickets fetched: " + tickets.size());

			// Get the first ticket to test updates and deletions
			TicketDTO ticketToUpdate = tickets.get(0);
			System.out.println("Testing updating a ticket...");
			ticketToUpdate.setPrice(99.99); // Update the price
			boolean updateTicketResult = ticketController.updateTicket(ticketToUpdate);
			System.out.println("Update Ticket Result: " + updateTicketResult);

			System.out.println("Testing deleting a ticket...");
			boolean deleteTicketResult = ticketController.deleteTicket(ticketToUpdate.getId());
			System.out.println("Delete Ticket Result: " + deleteTicketResult);
		} else {
			System.out.println("Failed to fetch tickets");
		}
	}
}