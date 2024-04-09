package com.RouteBus.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.RouteBus.server.dao.UserRepository;
import com.RouteBus.server.model.User;

import org.springframework.boot.CommandLineRunner;


@SpringBootApplication
public class App {

	private static final Logger log = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@Bean
	CommandLineRunner demo(UserRepository repository) {
		return (args) -> {
			User diego = new User("Diego", "Merino", "diego.merino@opendeusto.es", "123");
			repository.save(diego);
			log.info("USER Services AVAILABLE ...");
		};
	}
}