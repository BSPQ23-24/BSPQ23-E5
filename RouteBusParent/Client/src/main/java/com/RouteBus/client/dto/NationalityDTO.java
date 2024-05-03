package com.RouteBus.client.dto;

public class NationalityDTO {
    private String name;
    private String language;

    public NationalityDTO() {}

    public NationalityDTO(String name) {
        this.name = name;
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
