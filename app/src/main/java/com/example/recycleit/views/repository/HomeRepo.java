package com.example.recycleit.views.repository;

import android.app.Application;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.bumptech.glide.Glide;
import com.example.recycleit.views.auth.Status;
import com.example.recycleit.views.model.firebase.PostItem;
import com.example.recycleit.views.model.local.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeRepo {

    private Application application;
    private static final String TAG = "HomeRepo";
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private FirebaseFirestore store = FirebaseFirestore.getInstance();
    Boolean state = false;

    private List<PostItem> list = new ArrayList<>();

    public List<PostItem> getHomeItems() {
        String userId = Objects.requireNonNull(auth.getCurrentUser()).getUid();

        store.collection("Recycle it database schema").document("Posts")
                .collection("all posts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (DocumentSnapshot snap :
                                    task.getResult().getDocuments()) {
                                Log.i(TAG, "onComplete: "+ snap);
                                PostItem postItem = snap.toObject(PostItem.class);
                                list.add(postItem);
                            }
                        }else {
                            Log.i(TAG, "onComplete: error");
                        }

                    }
                });
        Log.i(TAG, "getHomeItems: "+ list.toString());
        return list == null ? list = new ArrayList<>() : list;
    }

    public void uploadPost(PostItem postItem){
//        storage.getReference().child("images profiles").child(auth.getCurrentUser().getUid())
//                .getDownloadUrl()
//                .addOnCompleteListener(new OnCompleteListener<Uri>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Uri> task) {
////                                        task.getResult().
//                        if (task.isSuccessful() && task.getResult() != null) {
//
//                        }
//                    }
//                });


        store.collection("Recycle it database schema").document("Posts")
                .collection("all posts").add(postItem)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
//                        Toast.makeText(application, "Post uploaded successfully", Toast.LENGTH_SHORT).show();
                        Status.getInstance().state = "success";
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i(TAG, "onFailure: " + e.getLocalizedMessage());
                        Status.getInstance().state = "success";
                    }
                });

        store.collection("Recycle it database schema").document("My posts")
                .collection(auth.getUid()).add(postItem)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
//                        Toast.makeText(application, "Post uploaded successfully", Toast.LENGTH_SHORT).show();
                        Status.getInstance().state = "success";
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i(TAG, "onFailure: " + e.getLocalizedMessage());
                        Status.getInstance().state = "success";
                    }
                });
    }
}
