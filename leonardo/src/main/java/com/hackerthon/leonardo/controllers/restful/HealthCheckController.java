package com.hackerthon.leonardo.controllers.restful;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class HealthCheckController {
    @Value("${firebase.databaseUrl}")
    private String databaseUrl;

    @Value("${firebase.credentialsPath}")
    private String credentialsPath;
    @GetMapping("/healthCheck")
    public Map<String, Object> healthCheck() {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> healthResultMap = new HashMap<>();
        healthResultMap.put("sate", "online");
        healthResultMap.put("databaseUrl", databaseUrl);
        healthResultMap.put("credentialsPath", credentialsPath);
        resultMap.put("result", healthResultMap);
        return resultMap;
    }

    @GetMapping("/exception")
    public String testException() {
        throw new RuntimeException("測試異常");
    }
}
