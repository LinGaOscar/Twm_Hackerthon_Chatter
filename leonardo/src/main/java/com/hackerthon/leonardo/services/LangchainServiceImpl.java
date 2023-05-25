package com.hackerthon.leonardo.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class LangchainServiceImpl implements LangchainService {

    @Value("${langchain.serviceUrl}")
    private String databaseUrl;

    private final RestTemplate restTemplate;

    public LangchainServiceImpl() {
        this.restTemplate = new RestTemplate();
    }

    private ResponseEntity<Map<String, Object>> sendRequest(String url, Map<String, Object> data) {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("data", data);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(dataMap, headers);
        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, new ParameterizedTypeReference<Map<String, Object>>() {
        });
    }

    @Override
    public Map<String, Object> healthCheck() {
        String url = databaseUrl + "/health_heck";
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<Map<String, Object>>() {
        });
        return response.getBody();
    }

    @Override
    public Map<String, Object> makeIntroduce(Map<String, Object> data) {
        String url = databaseUrl + "/make_introduce";
        ResponseEntity<Map<String, Object>> response = sendRequest(url, data);
        return response.getBody();
    }

    @Override
    public Map<String, Object> conversationHint(Map<String, Object> data) {
        String url = databaseUrl + "/conversation_hint";
        ResponseEntity<Map<String, Object>> response = sendRequest(url, data);
        return response.getBody();
    }

    @Override
    public Map<String, Object> personalGuide(Map<String, Object> data) {
        String url = databaseUrl + "/personal_guide";
        ResponseEntity<Map<String, Object>> response = sendRequest(url, data);
        return response.getBody();
    }
}
