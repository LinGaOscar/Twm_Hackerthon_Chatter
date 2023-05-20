package com.hackerthon.leonardo.services;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;

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

            FirebaseApp.initializeApp(options);
            databaseReference = FirebaseDatabase.getInstance().getReference();
        } catch (IOException e) {
            // 处理初始化错误
        }
    }

    @Override
    public void writeToFirebase(String data) {
        DatabaseReference dataRef = databaseReference.child("yourNode");
        dataRef.setValueAsync(data);
    }

    @Override
    public String readFromFirebase() {
        DatabaseReference dataRef = databaseReference.child("yourNode");

        final String[] result = new String[1];
        dataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                result[0] = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // 处理读取取消错误
            }
        });

        return result[0];
    }
}
