package com.hackerthon.leonardo.services;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Map;

public interface UserService {
    Map<String, Object> addUser(String email);

    Map<String, Object> readUserWithEmail(String email);

    Map<String, Object> readAllUser();

    Map<String, Object> updateUser(Map<String, Object> data);
}
