package com.hackerthon.leonardo.services;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.database.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

@Service
public class FirebaseServiceImpl implements FirebaseService {

    private DatabaseReference databaseReference;

    @Value("${firebase.databaseUrl}")
    private String databaseUrl;

    @Value("${firebase.credentialsPath}")
    private String credentialsPath;

    @PostConstruct
    private void initializeFirebase() {
        try {
            FileInputStream serviceAccount = new FileInputStream(credentialsPath);

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl(databaseUrl)
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }
            databaseReference = FirebaseDatabase.getInstance().getReference();
            System.out.println("Database reference: " + databaseReference);
        } catch (IOException e) {
            // 处理初始化错误
        }
    }

    @Override
    public void writeToFirebase(Map<String, Object> data) {
        System.out.println(data);

        DatabaseReference dataRef = databaseReference.child("yourNode");
        dataRef.setValueAsync(data);
    }

    @Override
    public Firestore readFromFirebase() {
     return FirestoreClient.getFirestore();
    }
}
