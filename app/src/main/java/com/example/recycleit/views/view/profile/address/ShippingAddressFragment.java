package com.example.recycleit.views.view.profile.address;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.recycleit.R;
import com.example.recycleit.databinding.FragmentShippingAddressBinding;
import com.example.recycleit.views.model.firebase.Address;

public class ShippingAddressFragment extends Fragment {
FragmentShippingAddressBinding binding;
    private  AddressViewModel viewModel;
    private static final String TAG = "ShippingAddressFragment";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentShippingAddressBinding.inflate(inflater,container,false);
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
//              //  Log.i(TAG, "onClick: "+ viewModel.getAddress().getValue().getCity());
//                String firstName=binding.firstNameET.getText().toString().trim();
//                String secondName =binding.secondNameET.getText().toString().trim();
//                String phone =binding.phoneNumberET.getText().toString().trim();
//                String county=binding.countryEt.getText().toString().trim();
//                String street=binding.streetET.getText().toString().trim();
//                String city=binding.cityEt.getText().toString().trim();
//               String regin=binding.blockNumberET.getText().toString().trim();
//                String postID=binding.blockET.getText().toString().trim();
//                Log.i(TAG, "onClick: "+firstName +secondName);
//                Address address=new Address(firstName,secondName,phone,county,city,street,postID,regin);
                Address address=new Address("abdo","gamal","","","","","","");
    //viewModel.uploadAddressAbject(viewModel.getAddress().getValue());
                viewModel.uploadAddressAbject(address);

            }
        });
    }
}