package com.hackerthon.leonardo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

        String targetUID = uid;

        List<Map<String, Object>> filteredData = allUsage.entrySet().stream()
                .filter(entry -> {
                    Map<String, Object> innerMap = (Map<String, Object>) entry.getValue();
                    if(targetUID.equals("hmLpjPDTXyPASLUdd0o1Q5zlHoy2")){
                        return true;
                    }
                    return innerMap.containsKey("UID") && innerMap.get("UID").equals(targetUID);
                })
                .map(entry -> (Map<String, Object>) entry.getValue())
                .collect(Collectors.toList());
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("usage", filteredData);

        return resultMap;
    }

    @Override
    public Map<String, Object> readAllUsage() {
        return firebaseService.readAllFromFirebase("usage");
    }
}
