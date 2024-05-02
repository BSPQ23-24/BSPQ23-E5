package com.RouteBus.client.gateway;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import com.RouteBus.client.dto.UserDTO;
import java.util.Arrays;
import java.util.List;

@Component
public class UserGateway extends BaseGateway {
    private static final UserGateway INSTANCE = new UserGateway();

    private UserGateway() {
        super();
    }

    public static UserGateway getInstance() {
        return INSTANCE;
    }

    public List<UserDTO> getAllUsers() {
        ResponseEntity<UserDTO[]> response = sendRequest("/user/all", HttpMethod.GET, null, UserDTO[].class);
        return Arrays.asList(response.getBody());
    }

    public UserDTO getUserByEmail(String email) {
        ResponseEntity<UserDTO> response = sendRequest("/user/email/" + email, HttpMethod.GET, null, UserDTO.class);
        return response.getBody();
    }

    public boolean createUser(UserDTO user) {
        ResponseEntity<String> response = sendRequest("/user/create", HttpMethod.POST, user, String.class);
        return response.getStatusCode().is2xxSuccessful();
    }

    public boolean updateUser(String email, UserDTO user) {
        ResponseEntity<String> response = sendRequest("/user/update/" + email, HttpMethod.PUT, user, String.class);
        return response.getStatusCode().is2xxSuccessful();
    }

    public boolean deleteUser(String email) {
        ResponseEntity<String> response = sendRequest("/user/delete/" + email, HttpMethod.DELETE, null, String.class);
        return response.getStatusCode().is2xxSuccessful();
    }

    public boolean checkUser(String email) {
        ResponseEntity<Boolean> response = sendRequest("/user/check/" + email, HttpMethod.GET, null, Boolean.class);
        return response.getBody() != null && response.getBody();
    }

    public boolean checkPassword(String email, String password) {
        String url = String.format("/user/check-password?email=%s&password=%s", email, password);
        ResponseEntity<Boolean> response = sendRequest(url, HttpMethod.POST, null, Boolean.class);
        return response.getBody() != null && response.getBody();
    }
    
    public List<String> getNationalities() {
        ResponseEntity<String[]> response = sendRequest("/nationalities/all", HttpMethod.GET, null, String[].class);
        return Arrays.asList(response.getBody());
    }
}
