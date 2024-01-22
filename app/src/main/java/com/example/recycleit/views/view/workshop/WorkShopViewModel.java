package com.example.recycleit.views.view.workshop;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.recycleit.views.model.WorkShop;
import com.example.recycleit.views.repository.WorkshopRepo;
import com.example.recycleit.views.view.profile.address.AddressViewModel;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;


public class WorkShopViewModel extends AddressViewModel {

    private WorkshopRepo  repository;
    private MutableLiveData<FirebaseUser> firebaseUserMutableLiveData;
    private MutableLiveData<Boolean> status;


    public WorkShopViewModel(@NonNull Application application) {
        super(application);

        repository= new WorkshopRepo(application);
        firebaseUserMutableLiveData=repository.getFirebaseUserMutableLiveData();


    }
    public void loadWorkShop(WorkShop workShop)
    {
        repository.uploadWorkshop(workShop);
    }
    public List<WorkShop> getData()
    {
       return repository.getDataFromFirebase();
    }


}
