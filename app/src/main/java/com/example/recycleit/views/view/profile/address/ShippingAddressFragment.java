package com.example.recycleit.views.view.profile.address;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.recycleit.R;
import com.example.recycleit.databinding.FragmentShippingAddressBinding;
import com.example.recycleit.views.model.firebase.Address;

public class ShippingAddressFragment extends Fragment {
    FragmentShippingAddressBinding binding;
    private AddressViewModel viewModel;
    private static final String TAG = "ShippingAddressFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentShippingAddressBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(getActivity().getApplication())).get(AddressViewModel.class);

        binding.setViewModel(viewModel);

        binding.confirmationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Log.i(TAG, "onClick: "+ viewModel.getAddress().getValue().getCity());
                String id =viewModel.registerForAddress(new Address());
                String firstName = binding.firstNameET.getText().toString().trim();
                String secondName = binding.secondNameET.getText().toString().trim();
                String phone = binding.phoneNumberET.getText().toString().trim();
                String county = binding.countryEt.getText().toString().trim();
                String street = binding.streetET.getText().toString().trim();
                String city = binding.cityEt.getText().toString().trim();
                String postID = binding.blockNumberET.getText().toString().trim();
                String regin = binding.blockNumberET.getText().toString().trim();
                Address address = new Address(id,firstName, secondName, phone, county, city, street, postID, regin);

                //viewModel.uploadAddressAbject(viewModel.getAddress().getValue());
                if (!checkEmptyField()){
                    return;
                }
                   else {
                    viewModel.registerForAddress(address);
                    Toast.makeText(requireContext(), "address add", Toast.LENGTH_LONG).show();
                    Navigation.findNavController(v).navigate(R.id.action_shippingAddressFragment_to_homeAddressFragment);
                    Log.i(TAG, "onClick: "+address.getId());
                }



            }
        });
    }

    private Boolean checkEmptyField() {
        Boolean state = false;
        if (TextUtils.isEmpty(binding.firstNameET.getText())) {
            binding.firstNameET.setError("fill your first name");
           state=false;
        }
        if (TextUtils.isEmpty(binding.secondNameET.getText())) {
            binding.secondNameET.setError("fill your second name");
            state=false;
        }
        if (TextUtils.isEmpty(binding.phoneNumberET.getText())) {
            binding.phoneNumberET.setError("fill your phone Number");
            state=false;
        }
        if (TextUtils.isEmpty(binding.countryEt.getText())) {
            binding.countryEt.setError("fill your country ");
            state=false;
        }
        if (TextUtils.isEmpty(binding.cityEt.getText())) {
            binding.cityEt.setError("fill your city");
            state=false;
        }
        if (TextUtils.isEmpty(binding.streetET.getText())) {
            binding.streetET.setError("fill your street");
            state=false;
        }
        else {
            state = true;
        }

        return state;
    }
}