package com.example.recycleit.views.view.myorder;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.recycleit.R;
import com.example.recycleit.databinding.FragmentMyOrderBinding;
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


public class MyOrderFragment extends Fragment {


    FragmentMyOrderBinding binding;

    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseFirestore store = FirebaseFirestore.getInstance();

    ArrayList<PostItem> list = new ArrayList<>();

    private static final String TAG = "MyOrderFragment";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMyOrderBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.imArrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().finish();
            }
        });

        store.collection("Recycle it database schema").document("Orders")
                .collection(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot snap :
                                    task.getResult().getDocuments()) {
                                Log.i(TAG, "onComplete: " + snap.toObject(PostItem.class));
                                PostItem postItem = snap.toObject(PostItem.class);
                                list.add(postItem);
                                MyPostAdapter adapter = new MyPostAdapter(list);
                                binding.recycler.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                                Log.i(TAG, "onComplete: " + Price.totalPrice);
                            }
                        } else {
                            Log.i(TAG, "onComplete: error");
                        }
                    }
                });
    }
}