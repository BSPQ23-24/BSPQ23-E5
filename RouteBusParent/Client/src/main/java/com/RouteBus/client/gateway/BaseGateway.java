package com.RouteBus.client.gateway;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public abstract class BaseGateway {
    protected RestTemplate restTemplate;
    protected static final String BASE_URL = "http://localhost:8080";

    public BaseGateway() {
        this.restTemplate = new RestTemplate();
    }

    protected <T> ResponseEntity<T> sendRequest(String url, HttpMethod method, Object request, Class<T> responseType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<Object> entity = new HttpEntity<>(request, headers);
        URI uri = UriComponentsBuilder.fromHttpUrl(BASE_URL + url).build().toUri();
        return restTemplate.exchange(uri, method, entity, responseType);
    }
}
