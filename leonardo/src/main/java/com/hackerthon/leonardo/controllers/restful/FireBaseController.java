package com.hackerthon.leonardo.controllers.restful;

import com.hackerthon.leonardo.services.FirebaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/firebase")

public class FireBaseController {

    @Autowired
    private FirebaseService firebaseService;

    @PostMapping("/write")
    public Map<String, Object> writeToFirebase(@RequestParam String username, @RequestParam String password) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("username", username);
        resultMap.put("password", password);
        firebaseService.writeToFirebase(resultMap);
        return resultMap;
    }

    @PostMapping("/read")
    public Map<String, Object> readFromFirebase() {
        Map<String, Object> resultMap = new HashMap<>();
        System.out.println(firebaseService.readFromFirebase().collection("data"));
//        resultMap.put("result", data);
        return resultMap;
    }
}
