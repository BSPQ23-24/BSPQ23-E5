package com.RouteBus.client.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@Service
public class ServerGateway{
		
	private RestTemplate restTemplate;
	private static ServerGateway instance;
	
	@Bean
	RestTemplate restTemplate() {
	      return new RestTemplate();
	}	
	
	@Value("${spring.server.url}")
	private String serverURL;
	
	@Value("${server.port}")
	private int serverPort;
	
	public static void start() {
		SpringApplication.run(ServerGateway.class);	
	}
		
	public ServerGateway () { }
	
	@Autowired
	public void setInstance(ServerGateway instance) {
		ServerGateway.instance = instance;
	 }
	public static ServerGateway getInstance() {
		return instance;
	}
	@Autowired
	public void setRestTemplate (RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

    public boolean checkUser(String userEmail) {
        String url = serverURL + ":" + serverPort + "/checkUser?email=" + userEmail;
        return restTemplate.getForObject(url, Boolean.class);
    }

    public boolean checkPassword(String userEmail, String password) {
        String url = serverURL + ":" + serverPort + "/checkPassword?email=" + userEmail + "&password=" + password;
        return restTemplate.getForObject(url, Boolean.class);
    }
}
