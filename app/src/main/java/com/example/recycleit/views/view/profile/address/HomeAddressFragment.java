package com.example.recycleit.views.view.profile.address;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
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
import com.example.recycleit.views.adapter.WorkshopAdaptor;
import com.example.recycleit.views.model.WorkShop;
import com.example.recycleit.views.model.firebase.Address;
import com.example.recycleit.views.view.workshop.WorkShopViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class HomeAddressFragment extends Fragment {

    private AddressViewModel viewModel;
    private RecyclerView recyclerView;
    private AddressListAdapter adapter;
    private Address address=new Address();
    private static final String TAG = "HomeAddressFragment";
    private FirebaseAuth auth=FirebaseAuth.getInstance();

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private CollectionReference usersCollection = db.collection("Recycle it database schema")
            .document("address").collection(auth.getUid());
    private ArrayList<Address> list=new ArrayList<>();

    FragmentHomeAddressBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       binding=FragmentHomeAddressBinding.inflate(inflater,container,false) ;
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
        adapter.notifyDataSetChanged();
        usersCollection.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value !=null)
                {
                    Toast.makeText(requireContext(), "" + error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    Log.i(TAG, "onEvent: error to create address");
                    return;
                }
                for (DocumentSnapshot snapshot : value.getDocuments()) {
                    address= snapshot.toObject(Address.class);
                    list.add(address);
                    Log.i(TAG, "onEvent: address created" + address.getFirstname());
                    adapter.notifyDataSetChanged();
                }
            }
        });



    }
}