package com.example.recycleit.views.repository;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.recycleit.views.model.WorkShop;
import com.example.recycleit.views.model.firebase.CourseB;
import com.example.recycleit.views.model.firebase.Courses;
import com.example.recycleit.views.model.firebase.RegisterCourses;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.Instant;

public class CourseRepo {

    private Application application;

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private static final String TAG = "CourseRepo";
    private FirebaseFirestore store = FirebaseFirestore.getInstance();
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference root = db.getReference().child("user");
    private MutableLiveData<FirebaseUser> firebaseUserMutableLiveData;

    public CourseRepo(Application application) {
        this.application = application;

    }

    public MutableLiveData<FirebaseUser> getFirebaseUserMutableLiveData() {
        return firebaseUserMutableLiveData;
    }

    public void uploadCourse(CourseB course) {
        String generatedId = String.valueOf(generateIdBasedOnSeconds());
        store.collection("Recycle it database schema").document("Courses").collection(auth.getUid())
                .document(generatedId).set(course).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.i(TAG, "onComplete:  courses added ");
                        } else {
                            Log.i(TAG, "onComplete:error " + task.getException().getLocalizedMessage());
                        }

                    }
                });
    }


    public static long generateIdBasedOnSeconds() {
        long currentTimestampSeconds = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            currentTimestampSeconds = Instant.now().getEpochSecond();
        }

        // You can use additional logic to modify or manipulate the timestamp as needed
        // For simplicity, this example just returns the current timestamp as the ID
        return currentTimestampSeconds;
    }

    public void registerCourse(RegisterCourses course) {
        String generatedId = String.valueOf(generateIdBasedOnSeconds());
        store.collection("Recycle it database schema")
                .document("RegisterCourses")
                .collection(auth.getUid())
                .document(generatedId).set(course).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.i(TAG, "onComplete:  courses register ");
                        } else {
                            Log.i(TAG, "onComplete:error " + task.getException().getLocalizedMessage());
                        }

                    }
                });
    }


}