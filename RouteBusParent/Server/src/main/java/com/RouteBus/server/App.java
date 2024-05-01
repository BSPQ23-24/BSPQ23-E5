package com.RouteBus.server;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.RouteBus.server.dao.NationalityRepository;
import com.RouteBus.server.model.Nationality;

@SpringBootApplication
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@Bean
	CommandLineRunner demo(NationalityRepository nationalityRepository) {
		return (args) -> {
			String[] nationalities = {"American", "British", "Chinese", "French", "German", "Indian", "Japanese", "Russian", "Spanish"};

			if (nationalityRepository.count() == 0) {
				for (String name : nationalities) {
					Nationality nationality = new Nationality(name);
					nationalityRepository.save(nationality);
				}
				System.out.println("Nationalities loaded into the database.");
			} else {
				System.out.println("Nationalities are already loaded.");
			}
		};
	}
}
