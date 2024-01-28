package com.example.recycleit.views.view.profile.post;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.recycleit.R;
import com.example.recycleit.databinding.FragmentMyPostBinding;
import com.example.recycleit.views.adapter.MyBagAdapter;
import com.example.recycleit.views.adapter.MyPostAdapter;
import com.example.recycleit.views.model.Price;
import com.example.recycleit.views.model.PriceI;
import com.example.recycleit.views.model.firebase.PostItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class MyPostFragment extends Fragment {

    FragmentMyPostBinding binding;

    FirebaseAuth auth = FirebaseAuth.getInstance();

    FirebaseFirestore store = FirebaseFirestore.getInstance();

    private static final String TAG = "MyPostFragment";

    ArrayList<PostItem> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMyPostBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        binding.imGobackMypost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().finish();
                //   Navigation.findNavController(requireView()).navigate(R.id.action_myPostFragment_to_navigation_userDetailsFragment);


            }
        });

        store.collection("Recycle it database schema").document("My posts")
                .collection(auth.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot snap :
                                task.getResult().getDocuments()) {
                            if (task.isSuccessful()) {

                                Log.i(TAG, "onComplete: " + snap.toObject(PostItem.class));
                                PostItem postItem = snap.toObject(PostItem.class);
                                list.add(postItem);
                                Log.i(TAG, "onComplete: "+ list);
                                MyPostAdapter adapter = new MyPostAdapter(list);
                                binding.recycler.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            } else {
                                Log.i(TAG, "onComplete: error");
                            }
                        }
                    }
                });
    }
}
