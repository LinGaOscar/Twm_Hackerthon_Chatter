package com.hackerthon.leonardo.services;

import com.google.cloud.firestore.Firestore;

import java.util.Map;

public interface FirebaseService {
    void writeToFirebase(Map<String, Object> data);
    Firestore readFromFirebase();
}
