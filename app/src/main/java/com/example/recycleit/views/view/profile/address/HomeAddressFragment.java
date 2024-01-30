package com.example.recycleit.views.view.profile.address;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.recycleit.R;
import com.example.recycleit.databinding.FragmentHomeAddressBinding;
import com.example.recycleit.views.adapter.AddressListAdapter;


import com.example.recycleit.views.adapter.CourseAdapter;
import com.example.recycleit.views.auth.SharedPreferenceManager;

import com.example.recycleit.views.model.firebase.Address;
import com.example.recycleit.views.model.firebase.CourseB;
import com.example.recycleit.views.model.local.User;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;


public class HomeAddressFragment extends Fragment {
    FragmentHomeAddressBinding binding;
    private AddressViewModel viewModel;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private SharedPreferenceManager sharedPreferenceManager=new SharedPreferenceManager();

    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference root = db.getReference().child("address");
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    Address address = new Address();
    User user=new User();
    private CollectionReference usersCollection =
            firestore.collection("Recycle it database schema")
                    .document("address").collection(auth.getCurrentUser().getUid());

    private static final String TAG = "HomeAddressFragment";
    private RecyclerView recyclerView;
    private AddressListAdapter adapter;
    private ArrayList<Address> list=new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeAddressBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity(), ViewModelProvider.AndroidViewModelFactory
                .getInstance(getActivity().getApplication())).get(AddressViewModel.class);

        binding.btAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Navigation.findNavController(v).navigate(R.id.action_homeAddressFragment_to_shippingAddressFragment);



            }
        });

        recyclerView = binding.addRecycler;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        list = new ArrayList<>();
        adapter = new AddressListAdapter(list, requireContext());
        recyclerView.setAdapter(adapter);


//        root.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    Address address = dataSnapshot.getValue(Address.class);
//                    list.add(address);
//                    Log.i(TAG, "onDataChange: "+ address.toString());
//                }
////                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        usersCollection.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                adapter.notifyDataSetChanged();
                if (error != null) {
                    Toast.makeText(requireContext(), "" + error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    Log.i(TAG, "onEvent: error to create address");
                    return;
                }
                for (DocumentSnapshot snapshot : value.getDocuments()) {
                    address = snapshot.toObject(Address.class);
                    list.add(address);
                    Log.i(TAG, "onEvent: address created" + address.toString());
                    adapter.notifyDataSetChanged();

                }
            }
        });


        binding.imArrowBack.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        requireActivity().finish();
    }
});




    }
}