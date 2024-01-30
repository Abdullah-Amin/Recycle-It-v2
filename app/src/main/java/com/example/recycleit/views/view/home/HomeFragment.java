package com.example.recycleit.views.view.home;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.recycleit.R;


import com.example.recycleit.databinding.FragmentHomeBinding;
import com.example.recycleit.views.adapter.HomePostAdapter;
import com.example.recycleit.views.auth.SharedPreferenceManager;
import com.example.recycleit.views.auth.UserType;
import com.example.recycleit.views.model.firebase.PostItem;
import com.example.recycleit.views.model.local.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private static final String TAG = "HomeFragment";
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private SharedPreferenceManager sharedPreferenceManager = new SharedPreferenceManager();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage2 = FirebaseStorage.getInstance();
    private CollectionReference postsCollection =
            db.collection("Recycle it database schema")
                    .document("Posts").collection("all posts");

    private ArrayList<PostItem> postList;
    HomePostAdapter adapter;

    private DocumentReference usersCollection = db.collection("Recycle it database schema").document("users").collection("regular").document(auth.getUid());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i(TAG, "onCreateView: " + postList);


        binding = FragmentHomeBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i("AuthRepo", "onViewCreated: " + sharedPreferenceManager.getType(requireContext()));
        if (sharedPreferenceManager.getType(requireContext()).equals(UserType.BUSINESS.getType())) {
        //    binding.all.setVisibility(View.INVISIBLE);
        } else if (sharedPreferenceManager.getType(requireContext()).equals(UserType.REGULAR.getType())) {
        } else {
            binding.greetingTxt.setText("welcome to our project Guest");
        }
        binding.searchBar.clearFocus();
        binding.searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                search(newText);
                return true;
            }
        });

        postList = new ArrayList<>();
        if (auth.getUid() != null) {
            usersCollection.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    if (error != null) {
                        Toast.makeText(requireContext(), error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        Log.i(TAG, "onEvent: " + error.getLocalizedMessage());
                        return;
                    }

                    binding.greetingTxt.setText("Hello, " + value.toObject(User.class).getName());
                    binding.addressTxt.setText("Saudi," + value.toObject(User.class).getCompany());
                    //   binding.txName.setText("Hello, "+viewModel.getUserName());

                }
            });


        } else {
        }


        postsCollection.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                if (error != null) {
                    Toast.makeText(requireContext(), error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    Log.i(TAG, "onEvent: error to create course");
                    return;
                }

                for (DocumentSnapshot snapshot : value.getDocuments()) {
                    postList.add(snapshot.toObject(PostItem.class));
                    Log.i(TAG, "onEvent: workshop created " + snapshot.toObject(PostItem.class));
                    adapter = new HomePostAdapter(postList);
                    binding.postsRecycler.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    Log.i(TAG, "onViewCreated: " + postList);

                }
            }
        });

      //  Log.i(TAG, "onViewCreated: after ---- " + postList);


    }

//    private void image() {
//        storage2.getReference().child("images profiles").child(auth.getCurrentUser().getUid())
//                .getDownloadUrl()
//                .addOnCompleteListener(new OnCompleteListener<Uri>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Uri> task) {
////                                        task.getResult().
//                        if (task.isSuccessful() && task.getResult() != null) {
//                            Glide
//                                    .with(requireActivity())
//                                    .load(task.getResult()).placeholder(R.drawable.girl)
//                                    .into(binding.profileImage);
//                        }
//                    }
//                });
//
//    }

    public void search(String textInput) {
        ArrayList<PostItem> postList1=new ArrayList<>();
        for (PostItem postItem:postList)
        {
            if(postItem.getCaption().toLowerCase().contains(textInput.toLowerCase()))
            {
                postList1.add(postItem);

            }
        }
        if(postList1.isEmpty())
            Toast.makeText(requireActivity(),"No Data Found",Toast.LENGTH_LONG).show();
        adapter.Search(postList1);
        adapter.notifyDataSetChanged();
    }
}