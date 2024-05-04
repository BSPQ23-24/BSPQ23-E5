package com.RouteBus.client.gateway;

import com.RouteBus.client.dto.BusDTO;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class BusGateway extends BaseGateway {
	
    private static final BusGateway INSTANCE = new BusGateway();

    private BusGateway() {
        super();
    }

    public static BusGateway getInstance() {
        return INSTANCE;
    }
    
    public List<BusDTO> getAllBuses() {
        ResponseEntity<BusDTO[]> response = sendRequest("/bus/all", HttpMethod.GET, null, BusDTO[].class);
        return Arrays.asList(response.getBody());
    }

    public BusDTO getBusById(String licensePlate) {
        ResponseEntity<BusDTO> response = sendRequest("/bus/" + licensePlate, HttpMethod.GET, null, BusDTO.class);
        return response.getBody();
    }

    public boolean createBus(BusDTO bus) {
        ResponseEntity<String> response = sendRequest("/bus/create", HttpMethod.POST, bus, String.class);
        return response.getStatusCode().is2xxSuccessful();
    }

    public boolean updateBus(BusDTO bus) {
        ResponseEntity<String> response = sendRequest("/bus/update", HttpMethod.PUT, bus, String.class);
        return response.getStatusCode().is2xxSuccessful();
    }

    public boolean deleteBus(String licensePlate) {
        ResponseEntity<String> response = sendRequest("/bus/delete/" + licensePlate, HttpMethod.DELETE, null, String.class);
        return response.getStatusCode().is2xxSuccessful();
    }
}
