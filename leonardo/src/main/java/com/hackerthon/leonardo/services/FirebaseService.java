package com.hackerthon.leonardo.services;

import java.util.Map;

public interface FirebaseService {

    Map<String, Object> creatToFirebase(String column, Map<String, Object> data);

    Map<String, Object> readAllFromFirebase(String column);

    Map<String, Object> readFromFirebase(String column, String id);

    Map<String, Object> updateToFireBase(String column, Map<String, Object> data);

}