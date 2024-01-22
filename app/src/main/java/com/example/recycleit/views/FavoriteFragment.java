package com.example.recycleit.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.recycleit.databinding.FragmentFavoriteBinding;
import com.example.recycleit.views.adapter.FavoritesAdapter;
import com.example.recycleit.views.model.firebase.PostItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;


public class FavoriteFragment extends Fragment {

    private FragmentFavoriteBinding binding;

    FirebaseAuth auth = FirebaseAuth.getInstance();

    FirebaseFirestore store = FirebaseFirestore.getInstance();

    private ArrayList<PostItem> list = new ArrayList<>();

    private static final String TAG = "FavoriteFragment";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.favoriteRecycler.setAdapter(new FavoritesAdapter(list));
    }

    public ArrayList<PostItem> getFavoriteItems() {
        String userId = Objects.requireNonNull(auth.getCurrentUser()).getUid();

        store.collection("Recycle it database schema").document("Favorites")
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
}