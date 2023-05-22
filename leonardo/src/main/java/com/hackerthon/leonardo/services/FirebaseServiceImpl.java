package com.hackerthon.leonardo.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class FirebaseServiceImpl implements FirebaseService {

    @Value("${firebase.databaseUrl}")
    private String databaseUrl;

    private final RestTemplate restTemplate;

    public FirebaseServiceImpl() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    public Map<String, Object> creatToFirebase(String column, Map<String, Object> data) {
        String url = databaseUrl + column + ".json";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(data, headers);

        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, new ParameterizedTypeReference<Map<String, Object>>() {
        });
        Map<String, Object> responseBody = response.getBody();
        String customerId = (String) responseBody.get("name");
        return this.readFromFirebase(column, customerId);
    }

    @Override
    public Map<String, Object> readAllFromFirebase(String column) {
        String url = databaseUrl + column + ".json";

        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<Map<String, Object>>() {
        });
        return response.getBody();
    }

    @Override
    public Map<String, Object> readFromFirebase(String column, String id) {
        String url = databaseUrl + column + "/" + id + ".json";

        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<Map<String, Object>>() {
        });
        Map<String, Object> responseBody = response.getBody();
        responseBody.put("id", id);
        return responseBody;
    }

    @Override
    public Map<String, Object> updateToFireBase(String column, Map<String, Object> data) {
        String id = (String) data.get("id");
        String url = databaseUrl + column + "/" + id + ".json";

        data.remove(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(data, headers);
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, new ParameterizedTypeReference<Map<String, Object>>() {
        });
        Map<String, Object> responseBody = response.getBody();
        responseBody.put("id", id);
        return responseBody;
    }
}
