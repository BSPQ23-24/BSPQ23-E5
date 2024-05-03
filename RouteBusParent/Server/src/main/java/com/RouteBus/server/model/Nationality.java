package com.RouteBus.server.model;

import javax.persistence.*;

@Entity
@Table(name = "nationalities")
public class Nationality {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String language;

    public Nationality() {}

    public Nationality(String name, String language) {
        this.name = name;
        this.language = language;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getLanguage() {
    	return language;
    }
    
    public void setLanguage(String language) {
    	this.language = language;
    }
}
