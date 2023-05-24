package com.hackerthon.leonardo.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
        String customerFireKey = (String) responseBody.get("name");
        return this.readFromFirebase(column, customerFireKey);
    }

    @Override
    public Map<String, Object> readAllFromFirebase(String column) {
        String url = databaseUrl + column + ".json";

        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<Map<String, Object>>() {
        });
        return response.getBody();
    }

    @Override
    public Map<String, Object> readFromFirebase(String column, String customerFireKey) {
        String url = databaseUrl + column + "/" + customerFireKey + ".json";

        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<Map<String, Object>>() {
        });
        Map<String, Object> responseBody = response.getBody();
        responseBody.put("key", customerFireKey);
        return responseBody;
    }

    @Override
    public Map<String, Object> updateToFireBase(String column, Map<String, Object> data) {
        String customerFireKey = (String) data.get("key");
        String url = databaseUrl + column + "/" + customerFireKey + ".json";

        data.remove("key");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(data, headers);
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, new ParameterizedTypeReference<Map<String, Object>>() {
        });
        Map<String, Object> responseBody = response.getBody();
        responseBody.put("key", customerFireKey);
        return responseBody;
    }
}
