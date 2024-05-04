package com.RouteBus.client.gateway;

import com.RouteBus.client.dto.StationDTO;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class StationGateway extends BaseGateway {

    private static final StationGateway INSTANCE = new StationGateway();

    private StationGateway() {
        super();
    }

    public static StationGateway getInstance() {
        return INSTANCE;
    }
	
    public List<StationDTO> getAllStations() {
        ResponseEntity<StationDTO[]> response = sendRequest("/Station/all", HttpMethod.GET, null, StationDTO[].class);
        return Arrays.asList(response.getBody());
    }

    public StationDTO getStationById(String name) {
        ResponseEntity<StationDTO> response = sendRequest("/Station/" + name, HttpMethod.GET, null, StationDTO.class);
        return response.getBody();
    }

    public boolean createStation(StationDTO station) {
        ResponseEntity<String> response = sendRequest("/Station/create", HttpMethod.POST, station, String.class);
        return response.getStatusCode().is2xxSuccessful();
    }

    public boolean updateStation(StationDTO station) {
        ResponseEntity<String> response = sendRequest("/Station/update", HttpMethod.PUT, station, String.class);
        return response.getStatusCode().is2xxSuccessful();
    }

    public boolean deleteStation(String name) {
        ResponseEntity<String> response = sendRequest("/Station/delete/" + name, HttpMethod.DELETE, null, String.class);
        return response.getStatusCode().is2xxSuccessful();
    }
}
