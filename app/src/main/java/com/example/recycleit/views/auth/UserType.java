package com.example.recycleit.views.auth;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public enum UserType {
    GUEST("GUEST"),REGULAR("REGULAR"),BUSINESS("BUSINESS");
private String type;
    UserType(String type) {
        this.type=type;
    }

    public String getType() {
        return type;
    }


}
