package com.hackerthon.leonardo.controllers.restful;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController("/")
public class HealthCheckController {
    @GetMapping("/HealthCheck")
    public Map<String, Object> indexPage() {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> healthResultMap = new HashMap<>();
        healthResultMap.put("sate", "online");
        resultMap.put("result", healthResultMap);
        return resultMap;
    }

    @GetMapping("/exception")
    public String testException() {
        throw new RuntimeException("測試異常");
    }
}
