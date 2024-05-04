package com.RouteBus.client.gateway;

import com.RouteBus.client.dto.ScheduleDTO;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class ScheduleGateway extends BaseGateway {

    private static final ScheduleGateway INSTANCE = new ScheduleGateway();

    private ScheduleGateway() {
        super();
    }

    public static ScheduleGateway getInstance() {
        return INSTANCE;
    }
    
    public List<ScheduleDTO> getAllSchedules() {
        ResponseEntity<ScheduleDTO[]> response = sendRequest("/Schedule/all", HttpMethod.GET, null, ScheduleDTO[].class);
        return Arrays.asList(response.getBody());
    }

    public ScheduleDTO getScheduleById(String id) {
        ResponseEntity<ScheduleDTO> response = sendRequest("/Schedule/" + id, HttpMethod.GET, null, ScheduleDTO.class);
        return response.getBody();
    }

    public boolean createSchedule(ScheduleDTO schedule) {
        ResponseEntity<String> response = sendRequest("/Schedule/create", HttpMethod.POST, schedule, String.class);
        return response.getStatusCode().is2xxSuccessful();
    }

    public boolean updateSchedule(ScheduleDTO schedule) {
        ResponseEntity<String> response = sendRequest("/Schedule/update", HttpMethod.PUT, schedule, String.class);
        return response.getStatusCode().is2xxSuccessful();
    }

    public boolean deleteSchedule(String id) {
        ResponseEntity<String> response = sendRequest("/Schedule/delete/" + id, HttpMethod.DELETE, null, String.class);
        return response.getStatusCode().is2xxSuccessful();
    }
}
