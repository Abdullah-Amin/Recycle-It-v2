package com.example.recycleit.views.view.profile.address;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.recycleit.databinding.FragmentEditShippingAddressBinding;
import com.example.recycleit.views.model.firebase.Address;
import com.example.recycleit.views.view.auth.ViewModelAuth;

public class EditShippingAddressFragment extends Fragment {
FragmentEditShippingAddressBinding binding;

private AddressViewModel viewModel;
    private static final String TAG = "EditShippingAddressFrag";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        viewModel = new ViewModelProvider(this , ViewModelProvider.AndroidViewModelFactory
//                .getInstance(getActivity().getApplication())).get(AddressViewModel.class);

        viewModel=new ViewModelProvider(
                this,ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity()
                .getApplication())).get(AddressViewModel.class);

        binding=FragmentEditShippingAddressBinding.inflate(inflater,container,false);
        binding.setLifecycleOwner(this);
        binding.setAdd(viewModel);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


binding.confirmationBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {


    }
});
    }
}