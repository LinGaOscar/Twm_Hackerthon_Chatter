package com.hackerthon.leonardo.services;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Map;

public interface FirebaseService {

    Map<String, Object> writeToFirebase(String column, Map<String, Object> data) throws JsonProcessingException;

    Map<String, Object> readAllFromFirebase(String column) throws JsonProcessingException;

    Map<String, Object> readFromFirebase(String column, String id) throws JsonProcessingException;

}