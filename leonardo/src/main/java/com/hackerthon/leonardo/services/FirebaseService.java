package com.hackerthon.leonardo.services;

public interface FirebaseService {
    void writeToFirebase(String data);
    String readFromFirebase();
}
