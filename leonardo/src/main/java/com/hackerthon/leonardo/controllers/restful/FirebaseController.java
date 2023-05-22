package com.hackerthon.leonardo.controllers.restful;

import com.hackerthon.leonardo.model.CustomerData;
import com.hackerthon.leonardo.services.FirebaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/firebase")

public class FirebaseController {

    private final FirebaseService firebaseService;

    @Autowired
    public FirebaseController(FirebaseService firebaseService) {
        this.firebaseService = firebaseService;
    }

    @PostMapping("/write")
    public Map<String, Object> creatToFirebase(@RequestBody CustomerData customer) {
        Map<String, Object> resultMap = new HashMap<>();

        resultMap.put("result", firebaseService.creatToFirebase("customer", customer.toMap()));
        return resultMap;
    }

    @PostMapping("/read")
    public Map<String, Object> readFromCustomer() {
        Map<String, Object> resultMap = new HashMap<>();

        resultMap.put("result", firebaseService.readAllFromFirebase("customer"));
        return resultMap;
    }

    @PostMapping("/update")
    public Map<String, Object> updateToFireBase(@RequestBody Map<String, Object> data) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("result", firebaseService.updateToFireBase("customer", data));
        return resultMap;
    }
}
