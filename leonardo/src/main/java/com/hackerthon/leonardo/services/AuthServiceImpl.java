package com.hackerthon.leonardo.services;

import com.hackerthon.leonardo.model.LoginModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    @Value("${firebase.authKry}")
    private String authKry;

    @Value("${firebase.loginUrl}")
    private String loginUrl;

    @Value("${firebase.signupUrl}")
    private String signupUrl;

    private final RestTemplate restTemplate;
    private final HttpHeaders headers;
    private final Map<String, Object> payMap;

    public AuthServiceImpl() {
        this.restTemplate = new RestTemplate();
        this.headers = new HttpHeaders();
        this.headers.setContentType(MediaType.APPLICATION_JSON);
        this.payMap = new HashMap<>();
        this.payMap.put("returnSecureToken", true);
    }

    @Override
    public Map<String, Object> login(LoginModel loginModel) {
        String url = loginUrl + authKry;

        updatePayMap(loginModel.getEmail(), loginModel.getPassword());

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(payMap, headers);

        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, new ParameterizedTypeReference<Map<String, Object>>() {
        });
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            // 返回錯誤訊息或拋出異常
            return null;
        }
    }

    @Override
    public Map<String, Object> signup(LoginModel loginModel) {
        String url = signupUrl + authKry;
        updatePayMap(loginModel.getEmail(), loginModel.getPassword());

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(payMap, headers);

        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, new ParameterizedTypeReference<Map<String, Object>>() {
        });
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            // 返回錯誤訊息或拋出異常
            return null;
        }
    }

    private void updatePayMap(String email, String password) {
        payMap.put("email", email);
        payMap.put("password", password);
    }
}
