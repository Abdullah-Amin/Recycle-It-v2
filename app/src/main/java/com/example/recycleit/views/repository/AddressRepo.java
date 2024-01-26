package com.example.recycleit.views.repository;

import static com.example.recycleit.views.repository.CourseRepo.generateIdBasedOnSeconds;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.recycleit.views.model.firebase.Address;
import com.example.recycleit.views.model.firebase.RegisterCourses;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class AddressRepo {
    private Application application;
    private MutableLiveData<FirebaseUser> firebaseUserMutableLiveData;

    private static final String TAG = "AddressRepo";
    FirebaseAuth auth=FirebaseAuth.getInstance();
    private FirebaseFirestore store=FirebaseFirestore.getInstance();
   //private CollectionReference reference = firebaseFirestore.collection("Recycle it database schema").document("address").getParent();


    public MutableLiveData<FirebaseUser> getFirebaseUserMutableLiveData() {
        return firebaseUserMutableLiveData;
    }

    public AddressRepo(Application application) {
        this.application = application;

    }
//    public void getAddress(){
//        reference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull  Task<QuerySnapshot> task) {
//                if (task.isSuccessful()){
//                    onFirestoreTaskComplete.addressDataLoaded(task.getResult()
//                            .toObjects(Address.class));
//                }else{
//                    onFirestoreTaskComplete.onError(task.getException());
//                }
//            }
//        });
//    }


    public void uploadAddress(Address address) {
        String generatedId = String.valueOf(generateIdBasedOnSeconds());
        store.collection("Recycle it database schema").document("address").collection(auth.getUid())
                .document(generatedId).set(address).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.i(TAG, "onComplete:  address added ");
                        } else {
                            Log.i(TAG, "onComplete:error " + task.getException().getLocalizedMessage());
                        }

                    }
                });

    }
    public void registerAddress(Address address) {
        address.setId(  String.valueOf(generateIdBasedOnSeconds()));
        store.collection("Recycle it database schema")
                .document("address")
                .collection(auth.getUid())
                .document(address.getId()).set(address).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.i(TAG, "onComplete:  address register ");
                        } else {
                            Log.i(TAG, "onComplete:error " + task.getException().getLocalizedMessage());
                        }

                    }
                });
    }

}
