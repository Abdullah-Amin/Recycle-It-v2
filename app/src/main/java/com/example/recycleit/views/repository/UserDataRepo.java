package com.example.recycleit.views.repository;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.example.recycleit.views.model.local.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;

public class UserDataRepo {
    private Application application;
    String name=new String("");
    private MutableLiveData<FirebaseUser> firebaseUserMutableLiveData;
    private static final String TAG = "UserDataRepo";
    private MutableLiveData<Boolean> statusOfUser;
    private FirebaseAuth auth;
    private FirebaseFirestore db ;

    private FirebaseFirestore store;


    public UserDataRepo(Application application) {
        this.application = application;
        auth = FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();



    }

    public MutableLiveData<FirebaseUser> getFirebaseUserMutableLiveData() {
        return firebaseUserMutableLiveData;

    }

    public void getUserData() {

    }

//    public String getUsername() {
//
//
//        usersCollection.addSnapshotListener(new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
//
//             name= ("Hello, "+value.toObject(User.class).getName());
//
//            }
//        });
//        return name;
//    }

    public void updateUI(User user) {

    }

    public void updateUserData(User user) {
        HashMap<String, Object> userHashMap = new HashMap<>();

        userHashMap.put(auth.getUid(),user );
        store.collection("Recycle it database schema")
                .document("users").collection("regular").document(auth.getUid()).update(userHashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.i(TAG, "onComplete:  yes updated");
                            Toast.makeText(application, "Your data updated. ", Toast.LENGTH_LONG).show();

                        } else {
                            Log.i(TAG, "onComplete: " + task.getException().getLocalizedMessage().toString());
                        }
                    }
                });
    }
}
