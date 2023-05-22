package com.hackerthon.leonardo.controllers.restful;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackerthon.leonardo.model.CustomerData;
import com.hackerthon.leonardo.services.FirebaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/firebase")

public class FirebaseController {

    private final FirebaseService firebaseService;

    @Autowired
    public FirebaseController(FirebaseService firebaseService) {
        this.firebaseService = firebaseService;
    }

    @PostMapping("/write")
    public Map<String, Object> writeToCustomer(@RequestBody CustomerData customer) throws JsonProcessingException {
        Map<String, Object> resultMap = new HashMap<>();

        resultMap.put("result", firebaseService.writeToFirebase("customer", customer.toMap()));
        return resultMap;
    }

    @PostMapping("/read")
    public Map<String, Object> readFromCustomer() throws ExecutionException, InterruptedException, JsonProcessingException {
        Map<String, Object> resultMap = new HashMap<>();

        resultMap.put("result", firebaseService.readAllFromFirebase("customer"));
        return resultMap;
    }
}
