package com.RouteBus.client.gateway;

import com.RouteBus.client.dto.RouteDTO;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class RouteGateway extends BaseGateway {

    private static final RouteGateway INSTANCE = new RouteGateway();

    private RouteGateway() {
        super();
    }

    public static RouteGateway getInstance() {
        return INSTANCE;
    }
    
    public List<RouteDTO> getAllRoutes() {
        ResponseEntity<RouteDTO[]> response = sendRequest("/Route/all", HttpMethod.GET, null, RouteDTO[].class);
        return Arrays.asList(response.getBody());
    }

    public RouteDTO getRouteById(String name) {
        ResponseEntity<RouteDTO> response = sendRequest("/Route/" + name, HttpMethod.GET, null, RouteDTO.class);
        return response.getBody();
    }

    public boolean createRoute(RouteDTO route) {
        ResponseEntity<String> response = sendRequest("/Route/create", HttpMethod.POST, route, String.class);
        return response.getStatusCode().is2xxSuccessful();
    }

    public boolean updateRoute(RouteDTO route) {
        ResponseEntity<String> response = sendRequest("/Route/update", HttpMethod.PUT, route, String.class);
        return response.getStatusCode().is2xxSuccessful();
    }

    public boolean deleteRoute(String name) {
        ResponseEntity<String> response = sendRequest("/Route/delete/" + name, HttpMethod.DELETE, null, String.class);
        return response.getStatusCode().is2xxSuccessful();
    }

    public List<RouteDTO> obtainRoutesByBus(String licensePlate) {
        String url = String.format("/Route/bus/%s/routes", licensePlate);
        ResponseEntity<RouteDTO[]> response = sendRequest(url, HttpMethod.GET, null, RouteDTO[].class);
        return Arrays.asList(response.getBody());
    }

    public List<RouteDTO> getRoutesByStations(String origin, String destination) {
        ResponseEntity<RouteDTO[]> response = sendRequest("/Route/stations?origin=" + origin + "&destination=" + destination, HttpMethod.GET, null, RouteDTO[].class);
        return response.getStatusCode().is2xxSuccessful() ? Arrays.asList(response.getBody()) : Collections.emptyList();
    }

}
