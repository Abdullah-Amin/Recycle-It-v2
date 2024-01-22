package com.example.recycleit.views.auth;


import com.google.android.gms.common.api.Response;
import com.google.android.gms.common.api.Result;

public class Status {
    private static Status instance;
    private String success;
    private String error;
    private String load;
    public String state ="";
    public Status(String success, String error, String load) {
        this.success = success;
        this.error = error;
        this.load = load;
    }
    private Status() {

    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getLoad() {
        return load;
    }

    public void setLoad(String load) {
        this.load = load;
    }



    public static Status getInstance() {
        // Check if the instance is null, and create it if needed
        if (instance == null) {
            instance = new Status();
        }
        return instance;
    }


}


