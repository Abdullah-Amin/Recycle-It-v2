package com.example.recycleit.views.model.firebase;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.recycleit.views.model.local.User;
import com.example.recycleit.views.repository.UserDataRepo;

public class ViewModelUsers extends ViewModel {

    private UserDataRepo repository;

    private MutableLiveData<User> user;
    private MutableLiveData<Boolean> loggedStatus;
    public ViewModelUsers(@NonNull Application application) {

        repository=new UserDataRepo(application);
        user=new MutableLiveData<>();
    }


}
