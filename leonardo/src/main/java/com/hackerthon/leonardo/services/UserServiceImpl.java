package com.hackerthon.leonardo.services;

import com.hackerthon.leonardo.model.LoginModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    private final FirebaseService firebaseService;

    @Autowired
    public UserServiceImpl(FirebaseService firebaseService) {
        this.firebaseService = firebaseService;
    }

    @Override
    public Map<String, Object> addUser(String id, LoginModel loginModel) {
        String column = "user";
        Map<String, Object> userModel = new HashMap<>();
        userModel.put("UID", id);
        userModel.put("password", loginModel.getPassword());
        userModel.put("email", loginModel.getEmail());

        return firebaseService.creatToFirebase(column, userModel);
    }

    @Override
    public Map<String, Object> readUserWithEmail(String email) {
        String customerFireKey = null;
        Map<String, Object> allUser = readAllUser();
        for (Map.Entry<String, Object> entry : allUser.entrySet()) {
            Map<String, Object> innerMap = (Map<String, Object>) entry.getValue();
            String tempName = (String) innerMap.get("email");
            if (tempName.equals(email)) {
                customerFireKey = entry.getKey();
                break;
            }
        }

        if (customerFireKey != null) {
            Map<String, Object> userData = (Map<String, Object>) allUser.get(customerFireKey);
            userData.put("key", customerFireKey);
            return userData;
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
