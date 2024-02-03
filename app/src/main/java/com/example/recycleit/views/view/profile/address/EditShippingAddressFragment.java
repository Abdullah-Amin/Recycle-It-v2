package com.example.recycleit.views.view.profile.address;


import static android.content.Intent.getIntent;
import static android.content.Intent.getIntentOld;
import static android.content.Intent.parseIntent;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.recycleit.R;
import com.example.recycleit.databinding.FragmentEditShippingAddressBinding;
import com.example.recycleit.views.auth.SharedPreferenceManager;
import com.example.recycleit.views.model.firebase.Address;
import com.example.recycleit.views.view.auth.ViewModelAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

public class EditShippingAddressFragment extends Fragment {
    FragmentEditShippingAddressBinding binding;
    SharedPreferenceManager sharedPreferenceManage = new SharedPreferenceManager();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();


    private AddressViewModel viewModel;

    private Address address = new Address();
    private static final String TAG = "EditShippingAddressFrag";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        viewModel = new ViewModelProvider(this , ViewModelProvider.AndroidViewModelFactory
//                .getInstance(getActivity().getApplication())).get(AddressViewModel.class);

        viewModel = new ViewModelProvider(
                this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity()
                .getApplication())).get(AddressViewModel.class);

        binding = FragmentEditShippingAddressBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
        binding.setAdd(viewModel);
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

        //edit data for address object
        binding.confirmationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                address.setFirstname(binding.firstNameET.getText().toString());
                address.setSecondName(binding.secondNameET.getText().toString());
                address.setPhone(binding.phoneNumberET.getText().toString());
                address.setCountry(binding.countryEt.getText().toString());
                address.setCity(binding.cityEt.getText().toString());
                address.setRegin(binding.blockNumberET.getText().toString());
                address.setStreet(binding.streetET.getText().toString());
                HashMap<String, Object> userHashMap = new HashMap<>();
                userHashMap.put("firstname", address.getFirstname());
                userHashMap.put("secondName", address.getSecondName());
                userHashMap.put("city", address.getCity());
                userHashMap.put("country", address.getCountry());
                userHashMap.put("phone", address.getPhone());
                userHashMap.put("post_id", address.getPost_id());
                userHashMap.put("regin", address.getRegin());
                userHashMap.put("street", address.getStreet());


                if (firebaseAuth.getCurrentUser().getUid() != null) {


                    //need to get id of item was click to update called key in adaptor

                    firebaseFirestore.collection("Recycle it database schema")
                            .document("address")
                            .collection(firebaseAuth.getUid())
                            .document(sharedPreferenceManage.getAddKey(requireContext()))
                            .update(userHashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.i(TAG, "onComplete:  yes updated");
                                        Toast.makeText(requireContext(), "Data updated. ", Toast.LENGTH_LONG).show();
                                        Navigation.findNavController(requireView()).navigate(R.id.action_editUserDataFragment_to_userDetailsFragment);

                                    } else {
                                        Log.i(TAG, "onComplete: " + task.getException().getLocalizedMessage().toString());
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.i(TAG, "onComplete: " + e.getLocalizedMessage().toString());

                                }
                            });
                }

                Navigation.findNavController(v).navigate(R.id.action_editShippingAddressFragment_to_shippingAddressFragment);


            }
        });
    }
}