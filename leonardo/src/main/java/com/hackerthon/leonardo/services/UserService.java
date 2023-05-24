package com.hackerthon.leonardo.services;

import com.hackerthon.leonardo.model.LoginModel;

import java.util.Map;

public interface UserService {
    Map<String, Object> addUser(String id, LoginModel loginModel);

    Map<String, Object> readUserWithEmail(String email);

    Map<String, Object> readAllUser();

    Map<String, Object> updateUser(Map<String, Object> data);
}
