package com.example.recycleit.views.model;

import com.example.recycleit.views.auth.Status;

public class Price {

    private static Price instance;
    public static int totalPrice;
    public static Price getInstance() {
        // Check if the instance is null, and create it if needed
        if (instance == null) {
            instance = new Price();
        }
        return instance;
    }
}
