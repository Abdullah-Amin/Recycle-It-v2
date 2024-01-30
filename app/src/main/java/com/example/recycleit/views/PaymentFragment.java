package com.example.recycleit.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.recycleit.R;
import com.example.recycleit.databinding.FragmentPaymentBinding;
import com.example.recycleit.views.auth.SharedPreferenceManager;
import com.example.recycleit.views.auth.UserType;

import java.util.Objects;

public class PaymentFragment extends Fragment {
    FragmentPaymentBinding binding;

    SharedPreferenceManager manager = new SharedPreferenceManager();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPaymentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.confirmationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Objects.equals(manager.getType(requireContext()), UserType.GUEST.getType())){
                    Toast.makeText(requireContext(), "Please create account before confirmation", Toast.LENGTH_SHORT).show();
                }else {

                }
            }
        });

        binding.imArrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            Navigation.findNavController(v).navigate(R.id.action_paymentFragment_to_navigation_myBagFragment);

            }
        });
    }
}