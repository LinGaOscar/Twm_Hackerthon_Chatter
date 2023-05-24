package com.hackerthon.leonardo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UsageServiceImpl implements UsageService {

    private final FirebaseService firebaseService;

    @Autowired
    public UsageServiceImpl(FirebaseService firebaseService) {
        this.firebaseService = firebaseService;
    }

    @Override
    public Map<String, Object> addUsage(String apiName, Map<String, Object> data) {
        data.put("api", apiName);

        return firebaseService.creatToFirebase("usage", data);
    }

    @Override
    public Map<String, Object> readUsage(String uid) {
        Map<String, Object> allUsage = readAllUsage();

        return null;
    }

    @Override
    public Map<String, Object> readAllUsage() {
        return firebaseService.readAllFromFirebase("usage");
    }
}
