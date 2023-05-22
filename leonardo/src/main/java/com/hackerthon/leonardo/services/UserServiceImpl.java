package com.hackerthon.leonardo.services;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public class UserServiceImpl implements UserService {

    private final FirebaseService firebaseService;

    @Autowired
    public UserServiceImpl(FirebaseService firebaseService) {
        this.firebaseService = firebaseService;
    }

    @Override
    public Map<String, Object> addUser(String email) {
        Map<String, Object> tempMap = new HashMap<>();
        tempMap.put("email", email);
        return firebaseService.creatToFirebase("user", tempMap);
    }

    @Override
    public Map<String, Object> readUserWithEmail(String email) {
        String targetId = null;
        Map<String, Object> allUser = readAllUser();
        for (Map.Entry<String, Object> entry : allUser.entrySet()) {
            Map<String, Object> innerMap = (Map<String, Object>) entry.getValue();
            String tempName = (String) innerMap.get("email");
            if (tempName.equals(email)) {
                targetId = entry.getKey();
                break;
            }
        }

        if (targetId != null) {
            Map<String, Object> user = (Map<String, Object>) allUser.get(targetId);
            user.put("id", targetId);
            return user;
        } else {
            return null;
        }
    }

    @Override
    public Map<String, Object> readAllUser() {
        return firebaseService.readAllFromFirebase("user");
    }

    @Override
    public Map<String, Object> updateUser(Map<String, Object> data) {
        return firebaseService.updateToFireBase("user", data);
    }
}
