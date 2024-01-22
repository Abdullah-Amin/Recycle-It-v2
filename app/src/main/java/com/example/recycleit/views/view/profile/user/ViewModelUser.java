package com.example.recycleit.views.view.profile.user;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.recycleit.views.model.local.User;
import com.example.recycleit.views.repository.UserDataRepo;
import com.google.firebase.auth.FirebaseUser;

public class ViewModelUser  extends AndroidViewModel {


private UserDataRepo repository;
    private MutableLiveData<FirebaseUser> firebaseUserMutableLiveData;


    public ViewModelUser(@NonNull Application application) {
        super(application);
        repository=new UserDataRepo(application);
        firebaseUserMutableLiveData=repository.getFirebaseUserMutableLiveData();

    }


//    public String getUserName()
//    {
//    return  repository.getUsername();
//
//
//    }
    public void updateUserName(User user)
    {
        repository.updateUI(user);
    }
    public void updateUserData(User user)
    {
        repository.updateUserData(user);
    }
}
