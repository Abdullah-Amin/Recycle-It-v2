package com.example.recycleit.views.view.auth;

import android.app.Application;
import android.content.Context;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.recycleit.views.model.local.User;
import com.example.recycleit.views.repository.AuthRepo;
import com.google.firebase.auth.FirebaseUser;

public class ViewModelAuth extends AndroidViewModel {
    private AuthRepo repository;
    private MutableLiveData<FirebaseUser> userData;
    private MutableLiveData<Boolean> status;
    private MutableLiveData<CheckBox> check;

    public ViewModelAuth(@NonNull Application application) {
        super(application);
        repository = new AuthRepo(application);
        userData = repository.getFirebaseUserMutableLiveData();
        status = repository.getStatus();
        check = repository.getCheck();
    }

    public MutableLiveData<FirebaseUser> getUserData() {
        return userData;
    }

    public MutableLiveData<Boolean> getStatus() {
        return repository.getStatus();
    }

    public MutableLiveData<CheckBox> getCheck() {
        return check;
    }

    public void register(String email, String pass) {

        repository.register(email, pass);
        //  status.postValue(Status.getInstance());

    }

    public void signIn(Context context, String email, String pass) {

        repository.login(context, email, pass);
    }

    public void signOut() {
        repository.signOut();
    }

    public void loadUserData(User user) {
        repository.upload(user);
    }

    public Boolean checkField(EditText editText) {
        return repository.checkField(editText);
    }

    public void signInAnonymously(FirebaseUser firebaseUser) {
        repository.signInAnonymously();
    }
    public void resetPassword(String email)
    {
        repository.resetPassword(email);
    }
}
