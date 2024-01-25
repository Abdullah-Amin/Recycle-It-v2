package com.example.recycleit.views.view.profile.address;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.recycleit.views.model.firebase.Address;
import com.example.recycleit.views.model.firebase.RegisterCourses;
import com.example.recycleit.views.repository.AddressRepo;
import com.google.firebase.auth.FirebaseUser;

public class AddressViewModel extends AndroidViewModel{

     private AddressRepo repository;
    private MutableLiveData<Address> address;
    private MutableLiveData<FirebaseUser> userData;

    public AddressViewModel(@NonNull Application application) {
        super(application);

        repository = new AddressRepo(application);
        userData = repository.getFirebaseUserMutableLiveData();
//        address=getAddress();
    }

    public MutableLiveData<Address> getAddress() {
        return address;
    }

    public void uploadAddressAbject(Address address)
    {
        repository.uploadAddress(address);
    }

    public void registerForAddress(Address address)
    {
        repository.registerAddress(address);
    }
}
