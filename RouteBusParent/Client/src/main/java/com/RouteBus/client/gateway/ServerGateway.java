package com.RouteBus.client.gateway;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

@SpringBootApplication
@Service
public class ServerGateway {

	private static final String SERVERURL = "http://localhost";
	private static final int SERVERPORT = 8888;
	
	private static Gson gson = new Gson();
	@SuppressWarnings("unused")
	private RestTemplate restTemplate;
	private static ServerGateway instance;

	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	public static void start() {
		SpringApplication.run(ServerGateway.class);
	}

	public ServerGateway() {
	}

	@Autowired
	public void setInstance(ServerGateway instance) {
		ServerGateway.instance = instance;
	}

	public static ServerGateway getInstance() {
		return instance;
	}

	@Autowired
	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public boolean checkUser(String email) {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(SERVERURL + ":" + SERVERPORT +"/" + "checkUser/" + email))
				.build();
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

	public boolean checkPassword(String email, String password) {
	    HttpClient client = HttpClient.newHttpClient();
	    String jsonBody = String.format("{\"Gmail\":\"%s\", \"password\":\"%s\"}", email, password);
	    String finalUri = String.format("%s:%d/checkPassword", SERVERURL, SERVERPORT);
	    System.out.println("Final URI: " + finalUri); // Para depuración
	    HttpRequest request = HttpRequest.newBuilder()
	            .uri(URI.create(finalUri))
	            .header("Content-Type", "application/json")
	            .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
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

}
