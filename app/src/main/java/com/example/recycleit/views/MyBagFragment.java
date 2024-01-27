package com.example.recycleit.views;

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
import com.example.recycleit.databinding.FragmentMyBagBinding;
import com.example.recycleit.views.adapter.FavoritesAdapter;
import com.example.recycleit.views.adapter.MyBagAdapter;
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
import java.util.Objects;


public class MyBagFragment extends Fragment {

    FragmentMyBagBinding binding;

    private static final String TAG = "MyBagFragment";

    FirebaseAuth auth = FirebaseAuth.getInstance();

    FirebaseFirestore store = FirebaseFirestore.getInstance();

    private ArrayList<PostItem> list = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMyBagBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getFavoriteItems();
        binding.favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_navigation_myBagFragment_to_navigation_favoriteFragment);
            }
        });
    }

    public ArrayList<PostItem> getFavoriteItems() {
        String userId = Objects.requireNonNull(auth.getCurrentUser()).getUid();
        list.clear();


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
                                MyBagAdapter adapter = new MyBagAdapter(list, new PriceI() {
                                    @Override
                                    public void getPrice(int price) {
                                        Log.i(TAG, "getPrice: " + price);
                                        binding.totalPrice.setText(price + " SAR");
                                        Log.i(TAG, "getPrice: total " + Price.totalPrice);
                                    }
                                });
                                binding.recycler.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                                Log.i(TAG, "onComplete: " + Price.totalPrice);
                            }
                        } else {
                            Log.i(TAG, "onComplete: error");
                        }

                    }
                });
        Log.i(TAG, "getHomeItems: " + list.toString());
        return list == null ? list = new ArrayList<>() : list;
    }
}