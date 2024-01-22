package com.example.recycleit.views.repository;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.example.recycleit.views.model.WorkShop;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class WorkshopRepo {
    private Application application;
    private static final String TAG = "WorkshopRepo";


    private FirebaseFirestore store=FirebaseFirestore.getInstance();
    private FirebaseAuth auth;
    private CollectionReference usersCollection = store.collection("Recycle it database schema");
    private MutableLiveData<FirebaseUser> firebaseUserMutableLiveData;
    public WorkshopRepo(Application application) {
        this.application = application;
        auth=FirebaseAuth.getInstance();
    }

    public MutableLiveData<FirebaseUser> getFirebaseUserMutableLiveData() {
        return firebaseUserMutableLiveData;
    }

    public void uploadWorkshop(WorkShop workShop) {
String workshopId=String.valueOf(System.currentTimeMillis());
        store.collection("Recycle it database schema").document("workshop").collection(auth.getUid()).document(workshopId)
                .set(workShop).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            Log.i(TAG, "onComplete:  workshop added ");
                        }
                        else
                        {
                            Log.i(TAG, "onComplete:error "+task.getException().getLocalizedMessage());
                        }

                    }
                });
    }
    public List<WorkShop> getDataFromFirebase()
    {

         WorkShop workShop ;
        List<WorkShop> list = null;
        usersCollection.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                if (error != null) {
                    Toast.makeText(application, "" + error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    Log.i(TAG, "onEvent: error to create workshop");

                }
                for (DocumentSnapshot snapshot : value.getDocuments()) {
                 WorkShop workShop  = snapshot.toObject(WorkShop.class);

                    list.add(workShop);
                    Log.i(TAG, "onEvent: workshop created" + workShop);
                }

            }
        });
return list;
    }



}
