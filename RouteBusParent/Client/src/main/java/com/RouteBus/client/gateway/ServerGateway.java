package com.RouteBus.client.gateway;

import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.RouteBus.client.dto.BusDTO;
import com.RouteBus.client.dto.RouteDTO;
import com.RouteBus.client.dto.StationDTO;
import com.RouteBus.client.dto.TicketDTO;
import com.RouteBus.client.dto.UserDTO;
import com.RouteBus.server.model.Bus;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

@SpringBootApplication
@Service
public class ServerGateway {

	private static final String SERVERURL = "http://localhost";
	private static final int SERVERPORT = 8080;
	private static ServerGateway instance;
	private static Gson gson = new Gson();
	@SuppressWarnings("unused")
	private RestTemplate restTemplate;
	
	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	public static void start() {
		SpringApplication.run(ServerGateway.class);
	}

	private ServerGateway() {
	}
	public static ServerGateway getInstance() {
		if(instance==null) {
			return new ServerGateway();
		}return instance;
	}

	@Autowired
	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public boolean checkUser(String email) {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(SERVERURL + ":" + SERVERPORT + "/checkUser/" + email)).build();
		try {
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			
			if (response.statusCode() == 200) {
				boolean result = gson.fromJson(response.body(), Boolean.class);
				return result;
			}
			System.out.format("- Error: %d\n", response.statusCode());
		} catch (Exception e) {
			System.out.format("- ERROR: %s\n", e.getMessage());
		}
		return false;
	}

	public UserDTO getUserByEmail(String email ) {
	    HttpClient client = HttpClient.newHttpClient();
	    String finalUri = String.format("%s:%d/user/email/%s", SERVERURL, SERVERPORT,email);
	    HttpRequest request = HttpRequest.newBuilder()
	            .uri(URI.create(finalUri))
	            .GET()
	            .build();
	    try {
	        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
	        if (response.statusCode() == 200) {
	            UserDTO resultado = gson.fromJson(response.body(), UserDTO.class);
	            return resultado;
	        }
	        System.out.format("- Error: %d\n", response.statusCode());
	    } catch (Exception e) {
	        System.out.format("- ERROR: %s\n", e.getMessage());
	    }

	    return null;
	}
	

	public boolean checkPassword2(String email, String password) {
	    HttpClient client = HttpClient.newHttpClient();
	    String formParams = String.format("gmail=%s&password=%s", email, password);
	    String finalUri = String.format("%s:%d/checkPassword", SERVERURL, SERVERPORT);
	    HttpRequest request = HttpRequest.newBuilder()
	            .uri(URI.create(finalUri))
	            .header("Content-Type", "application/json")
	            .POST(HttpRequest.BodyPublishers.ofString(formParams))
	            .build();

	    try {
	        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
	        if (response.statusCode() == 200) {
	            boolean resultado = gson.fromJson(response.body(), Boolean.class);
	            return resultado;
	        }
	        System.out.format("- Error: %d\n", response.statusCode());
	    } catch (Exception e) {
	        System.out.format("- ERROR: %s\n", e.getMessage());
	    }

	    return false;
	}
	public List<BusDTO> getAllBuses() {
	    HttpClient client = HttpClient.newHttpClient();
	    String finalUri = String.format("%s:%d/bus/all", SERVERURL, SERVERPORT);
	    HttpRequest request = HttpRequest.newBuilder()
	            .uri(URI.create(finalUri))
	            .header("Content-Type", "application/json")
	            .GET()
	            .build();
	    StringBuffer buffer = new StringBuffer();
	    try {
	        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
	        if (response.statusCode() == 200) {
	        	 if (response.statusCode() == 200) {                
	             	BusDTO[] buses = gson.fromJson(response.body(), BusDTO[].class);
	         		
	         		if (buses != null && buses.length > 0) {
	         			return Arrays.asList(buses);
	         		} else {
	         			buffer.append(" - No users found");
	         		}
	             }
	            return null;
	        }
	        System.out.format("- Error: %d\n", response.statusCode());
	    } catch (Exception e) {
	    	
	        System.out.format("- ERROR: %s\n", e.getMessage());
	    }

	    return null;
	}
	
	public List<RouteDTO> getAllRoutes() {
	    HttpClient client = HttpClient.newHttpClient();
	    String finalUri = String.format("%s:%d/route/all", SERVERURL, SERVERPORT);
	    HttpRequest request = HttpRequest.newBuilder()
	            .uri(URI.create(finalUri))
	            .header("Content-Type", "application/json")
	            .GET()
	            .build();
	    StringBuffer buffer = new StringBuffer();
	    try {
	        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
	        if (response.statusCode() == 200) {
	        	 if (response.statusCode() == 200) {                
	             	RouteDTO [] routes = gson.fromJson(response.body(), RouteDTO[].class);
	         		
	         		if (routes != null && routes.length > 0) {
	         			return Arrays.asList(routes);
	         		} else {
	         			buffer.append(" - No users found");
	         		}
	             }
	            return null;
	        }
	        System.out.format("- Error: %d\n", response.statusCode());
	    } catch (Exception e) {
	    	
	        System.out.format("- ERROR: %s\n", e.getMessage());
	    }

	    return null;
	}
	

	public boolean registerBus(BusDTO bus) {
	    HttpClient client = HttpClient.newHttpClient();
	    String busJson = gson.toJson(bus);
	    String finalUri = String.format("%s:%d/bus/create", SERVERURL, SERVERPORT);
	    HttpRequest request = HttpRequest.newBuilder()
	            .uri(URI.create(finalUri))
	            .header("Content-Type", "application/json")
	            .POST(HttpRequest.BodyPublishers.ofString(busJson))
	            .build();
	    try {
	        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
	        if (response.statusCode() == 200) {
	        	JsonReader reader = new JsonReader(new StringReader(response.body()));
	            reader.setLenient(true);
	            boolean resultado = gson.fromJson(reader, Boolean.class);
	            return resultado;
	        }
	        System.out.format("- Error: %d\n", response.statusCode());
	    } catch (Exception e) {
	    	
	        System.out.format("- ERROR: %s\n", e.getMessage());
	    }

	    return false;
	}
	public boolean updateBus(BusDTO bus) {
	    HttpClient client = HttpClient.newHttpClient();
	    String busJson = gson.toJson(bus);
	    String finalUri = String.format("%s:%d/bus/update/%d", SERVERURL, SERVERPORT,bus.getLicensePlate());
	    HttpRequest request = HttpRequest.newBuilder()
	            .uri(URI.create(finalUri))
	            .header("Content-Type", "application/json")
	            .PUT(HttpRequest.BodyPublishers.ofString(busJson))
	            .build();
	    try {
	        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
	        if (response.statusCode() == 200) {
	        	JsonReader reader = new JsonReader(new StringReader(response.body()));
	            reader.setLenient(true);
	            boolean resultado = gson.fromJson(reader, Boolean.class);
	            return resultado;
	        }
	        System.out.format("- Error: %d\n", response.statusCode());
	    } catch (Exception e) {
	    	
	        System.out.format("- ERROR: %s\n", e.getMessage());
	    }

	    return false;
	}
	
	public boolean deleteBus(int id) {
	    HttpClient client = HttpClient.newHttpClient();
	    String finalUri = String.format("%s:%d/bus/delete/%d", SERVERURL, SERVERPORT,id);
	    HttpRequest request = HttpRequest.newBuilder()
	            .uri(URI.create(finalUri))
	            .header("Content-Type", "application/json")
	            .DELETE()
	            .build();
	    try {
	        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
	        if (response.statusCode() == 200) {
	        	JsonReader reader = new JsonReader(new StringReader(response.body()));
	            reader.setLenient(true);
	            boolean resultado = gson.fromJson(reader, Boolean.class);
	            return resultado;
	        }
	        System.out.format("- Error: %d\n", response.statusCode());
	    } catch (Exception e) {
	    	
	        System.out.format("- ERROR: %s\n", e.getMessage());
	    }

	    return false;
	}
	public boolean deleteAllBus() {
	    HttpClient client = HttpClient.newHttpClient();
	    String finalUri = String.format("%s:%d/bus/delete/all", SERVERURL, SERVERPORT);
	    HttpRequest request = HttpRequest.newBuilder()
	            .uri(URI.create(finalUri))
	            .header("Content-Type", "application/json")
	            .DELETE()
	            .build();
	    try {
	        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
	        if (response.statusCode() == 200) {
	        	JsonReader reader = new JsonReader(new StringReader(response.body()));
	            reader.setLenient(true);
	            boolean resultado = gson.fromJson(reader, Boolean.class);
	            return resultado;
	        }
	        System.out.format("- Error: %d\n", response.statusCode());
	    } catch (Exception e) {
	    	
	        System.out.format("- ERROR: %s\n", e.getMessage());
	    }

	    return false;
	}
	public boolean addBusToRoute(int routeId, int busId) {
	    HttpClient client = HttpClient.newHttpClient();
	    String finalUri = String.format("%s:%d/route/%d/buses/%d", SERVERURL, SERVERPORT,routeId,busId);
	    HttpRequest request = HttpRequest.newBuilder()
	            .uri(URI.create(finalUri))
	            .header("Content-Type", "application/json")
	            .GET()
	            .build();
	    try {
	        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
	        if (response.statusCode() == 200) {
	        	JsonReader reader = new JsonReader(new StringReader(response.body()));
	            reader.setLenient(true);
	            boolean resultado = gson.fromJson(reader, Boolean.class);
	            return resultado;
	        }
	        System.out.format("- Error: %d\n", response.statusCode());
	    } catch (Exception e) {
	    	
	        System.out.format("- ERROR: %s\n", e.getMessage());
	    }

	    return false;
	}
	
	public boolean registerRoute(RouteDTO route) {
	    HttpClient client = HttpClient.newHttpClient();
	    String busJson = gson.toJson(route);
	    String finalUri = String.format("%s:%d/route/create", SERVERURL, SERVERPORT);
	    HttpRequest request = HttpRequest.newBuilder()
	            .uri(URI.create(finalUri))
	            .header("Content-Type", "application/json")
	            .POST(HttpRequest.BodyPublishers.ofString(busJson))
	            .build();
	    try {
	        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
	        if (response.statusCode() == 200) {
	        	JsonReader reader = new JsonReader(new StringReader(response.body()));
	            reader.setLenient(true);
	            boolean resultado = gson.fromJson(reader, Boolean.class);
	            return resultado;
	        }
	        System.out.format("- Error: %d\n", response.statusCode());
	    } catch (Exception e) {
	    	
	        System.out.format("- ERROR: %s\n", e.getMessage());
	    }

	    return false;
	}
	public boolean updateRoute(RouteDTO route) {
	    HttpClient client = HttpClient.newHttpClient();
	    String busJson = gson.toJson(route);
	    String finalUri = String.format("%s:%d/route/update/%d", SERVERURL, SERVERPORT,route.getName());
	    HttpRequest request = HttpRequest.newBuilder()
	            .uri(URI.create(finalUri))
	            .header("Content-Type", "application/json")
	            .PUT(HttpRequest.BodyPublishers.ofString(busJson))
	            .build();
	    try {
	        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
	        if (response.statusCode() == 200) {
	        	JsonReader reader = new JsonReader(new StringReader(response.body()));
	            reader.setLenient(true);
	            boolean resultado = gson.fromJson(reader, Boolean.class);
	            return resultado;
	        }
	        System.out.format("- Error: %d\n", response.statusCode());
	    } catch (Exception e) {
	    	
	        System.out.format("- ERROR: %s\n", e.getMessage());
	    }

	    return false;
	}
	
	public boolean deleteRoute(int id) {
	    HttpClient client = HttpClient.newHttpClient();
	    String finalUri = String.format("%s:%d/route/delete/%d", SERVERURL, SERVERPORT,id);
	    HttpRequest request = HttpRequest.newBuilder()
	            .uri(URI.create(finalUri))
	            .header("Content-Type", "application/json")
	            .DELETE()
	            .build();
	    try {
	        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
	        if (response.statusCode() == 200) {
	        	JsonReader reader = new JsonReader(new StringReader(response.body()));
	            reader.setLenient(true);
	            boolean resultado = gson.fromJson(reader, Boolean.class);
	            return resultado;
	        }
	        System.out.format("- Error: %d\n", response.statusCode());
	    } catch (Exception e) {
	    	
	        System.out.format("- ERROR: %s\n", e.getMessage());
	    }

	    return false;
	}
	public boolean deleteAllRoutes() {
	    HttpClient client = HttpClient.newHttpClient();
	    String finalUri = String.format("%s:%d/route/delete/all", SERVERURL, SERVERPORT);
	    HttpRequest request = HttpRequest.newBuilder()
	            .uri(URI.create(finalUri))
	            .header("Content-Type", "application/json")
	            .DELETE()
	            .build();
	    try {
	        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
	        if (response.statusCode() == 200) {
	        	JsonReader reader = new JsonReader(new StringReader(response.body()));
	            reader.setLenient(true);
	            boolean resultado = gson.fromJson(reader, Boolean.class);
	            return resultado;
	        }
	        System.out.format("- Error: %d\n", response.statusCode());
	    } catch (Exception e) {
	    	
	        System.out.format("- ERROR: %s\n", e.getMessage());
	    }

	    return false;
	}
	public boolean registerStation(StationDTO station) {
	    HttpClient client = HttpClient.newHttpClient();
	    String busJson = gson.toJson(station);
	    String finalUri = String.format("%s:%d/station/create", SERVERURL, SERVERPORT);
	    HttpRequest request = HttpRequest.newBuilder()
	            .uri(URI.create(finalUri))
	            .header("Content-Type", "application/json")
	            .POST(HttpRequest.BodyPublishers.ofString(busJson))
	            .build();
	    try {
	        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
	        if (response.statusCode() == 200) {
	        	JsonReader reader = new JsonReader(new StringReader(response.body()));
	            reader.setLenient(true);
	            boolean resultado = gson.fromJson(reader, Boolean.class);
	            return resultado;
	        }
	        System.out.format("- Error: %d\n", response.statusCode());
	    } catch (Exception e) {
	    	
	        System.out.format("- ERROR: %s\n", e.getMessage());
	    }

	    return false;
	}
	public boolean updateStation(StationDTO station) {
	    HttpClient client = HttpClient.newHttpClient();
	    String busJson = gson.toJson(station);
	    String finalUri = String.format("%s:%d/route/update/%d", SERVERURL, SERVERPORT,station.getName());
	    HttpRequest request = HttpRequest.newBuilder()
	            .uri(URI.create(finalUri))
	            .header("Content-Type", "application/json")
	            .PUT(HttpRequest.BodyPublishers.ofString(busJson))
	            .build();
	    try {
	        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
	        if (response.statusCode() == 200) {
	        	JsonReader reader = new JsonReader(new StringReader(response.body()));
	            reader.setLenient(true);
	            boolean resultado = gson.fromJson(reader, Boolean.class);
	            return resultado;
	        }
	        System.out.format("- Error: %d\n", response.statusCode());
	    } catch (Exception e) {
	    	
	        System.out.format("- ERROR: %s\n", e.getMessage());
	    }

	    return false;
	}
	
	public boolean deleteStation(int id) {
	    HttpClient client = HttpClient.newHttpClient();
	    String finalUri = String.format("%s:%d/station/delete/%d", SERVERURL, SERVERPORT,id);
	    HttpRequest request = HttpRequest.newBuilder()
	            .uri(URI.create(finalUri))
	            .header("Content-Type", "application/json")
	            .DELETE()
	            .build();
	    try {
	        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
	        if (response.statusCode() == 200) {
	        	JsonReader reader = new JsonReader(new StringReader(response.body()));
	            reader.setLenient(true);
	            boolean resultado = gson.fromJson(reader, Boolean.class);
	            return resultado;
	        }
	        System.out.format("- Error: %d\n", response.statusCode());
	    } catch (Exception e) {
	    	
	        System.out.format("- ERROR: %s\n", e.getMessage());
	    }

	    return false;
	}
	public boolean deleteAllStations() {
	    HttpClient client = HttpClient.newHttpClient();
	    String finalUri = String.format("%s:%d/station/delete/all", SERVERURL, SERVERPORT);
	    HttpRequest request = HttpRequest.newBuilder()
	            .uri(URI.create(finalUri))
	            .header("Content-Type", "application/json")
	            .DELETE()
	            .build();
	    try {
	        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
	        if (response.statusCode() == 200) {
	        	JsonReader reader = new JsonReader(new StringReader(response.body()));
	            reader.setLenient(true);
	            boolean resultado = gson.fromJson(reader, Boolean.class);
	            return resultado;
	        }
	        System.out.format("- Error: %d\n", response.statusCode());
	    } catch (Exception e) {
	    	
	        System.out.format("- ERROR: %s\n", e.getMessage());
	    }

	    return false;
	}
	
	public boolean registerTicket(TicketDTO ticket) {
	    HttpClient client = HttpClient.newHttpClient();
	    String busJson = gson.toJson(ticket);
	    String finalUri = String.format("%s:%d/ticket/create", SERVERURL, SERVERPORT);
	    HttpRequest request = HttpRequest.newBuilder()
	            .uri(URI.create(finalUri))
	            .header("Content-Type", "application/json")
	            .POST(HttpRequest.BodyPublishers.ofString(busJson))
	            .build();
	    try {
	        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
	        if (response.statusCode() == 200) {
	        	JsonReader reader = new JsonReader(new StringReader(response.body()));
	            reader.setLenient(true);
	            boolean resultado = gson.fromJson(reader, Boolean.class);
	            return resultado;
	        }
	        System.out.format("- Error: %d\n", response.statusCode());
	    } catch (Exception e) {
	    	
	        System.out.format("- ERROR: %s\n", e.getMessage());
	    }

	    return false;
	}
//	public boolean updateTicket(TicketDTO ticket) {
//	    HttpClient client = HttpClient.newHttpClient();
//	    String busJson = gson.toJson(ticket);
//	    String finalUri = String.format("%s:%d/route/update/%d", SERVERURL, SERVERPORT,ticket.getId());
//	    HttpRequest request = HttpRequest.newBuilder()
//	            .uri(URI.create(finalUri))
//	            .header("Content-Type", "application/json")
//	            .PUT(HttpRequest.BodyPublishers.ofString(busJson))
//	            .build();
//	    try {
//	        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//	        if (response.statusCode() == 200) {
//	        	JsonReader reader = new JsonReader(new StringReader(response.body()));
//	            reader.setLenient(true);
//	            boolean resultado = gson.fromJson(reader, Boolean.class);
//	            return resultado;
//	        }
//	        System.out.format("- Error: %d\n", response.statusCode());
//	    } catch (Exception e) {
//	    	
//	        System.out.format("- ERROR: %s\n", e.getMessage());
//	    }
//
//	    return false;
//	}
//	
	public boolean deleteTicket(Long id) {
	    HttpClient client = HttpClient.newHttpClient();
	    String finalUri = String.format("%s:%d/ticket/delete/%d", SERVERURL, SERVERPORT,id);
	    HttpRequest request = HttpRequest.newBuilder()
	            .uri(URI.create(finalUri))
	            .header("Content-Type", "application/json")
	            .DELETE()
	            .build();
	    try {
	        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
	        if (response.statusCode() == 200) {
	        	JsonReader reader = new JsonReader(new StringReader(response.body()));
	            reader.setLenient(true);
	            boolean resultado = gson.fromJson(reader, Boolean.class);
	            return resultado;
	        }
	        System.out.format("- Error: %d\n", response.statusCode());
	    } catch (Exception e) {
	    	
	        System.out.format("- ERROR: %s\n", e.getMessage());
	    }

	    return false;
	}
	public boolean deleteAllTickets() {
	    HttpClient client = HttpClient.newHttpClient();
	    String finalUri = String.format("%s:%d/route/delete/all", SERVERURL, SERVERPORT);
	    HttpRequest request = HttpRequest.newBuilder()
	            .uri(URI.create(finalUri))
	            .header("Content-Type", "application/json")
	            .DELETE()
	            .build();
	    try {
	        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
	        if (response.statusCode() == 200) {
	        	JsonReader reader = new JsonReader(new StringReader(response.body()));
	            reader.setLenient(true);
	            boolean resultado = gson.fromJson(reader, Boolean.class);
	            return resultado;
	        }
	        System.out.format("- Error: %d\n", response.statusCode());
	    } catch (Exception e) {
	    	
	        System.out.format("- ERROR: %s\n", e.getMessage());
	    }

	    return false;
	}
}
