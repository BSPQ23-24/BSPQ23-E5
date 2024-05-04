package com.RouteBus.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.RouteBus.server.dao.NationalityRepository;
import com.RouteBus.server.model.Nationality;

@SpringBootApplication
public class App {

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    private static final Nationality[] nationalities = {
            new Nationality("American", "en_US"),
            new Nationality("British", "en"),
            new Nationality("Chinese", "zh"),
            new Nationality("French", "fr"),
            new Nationality("German", "de"),
            new Nationality("Indian", "hi"),
            new Nationality("Japanese", "ja"),
            new Nationality("Russian", "ru"),
            new Nationality("Spanish", "es"),
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
    
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    CommandLineRunner demo(NationalityRepository nationalityRepository) {
        return (args) -> {
            if (nationalityRepository.count() == 0) {
                int count = 0;
                for (Nationality nationality : nationalities) {
                    if(nationalityRepository.save(nationality) != null)	count++;
                }
                logger.info(count + " nationalities loaded into the database.");
            } else {
                logger.info("Nationalities are already loaded.");
            }
        };
    }
    
    public static int getNumberOfNationalities() {
    	return nationalities.length;
    }
}
