package com.example.recycleit.views.view.profile.post;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.recycleit.R;
import com.example.recycleit.databinding.FragmentMyPostBinding;


public class MyPostFragment extends Fragment {

    FragmentMyPostBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
binding=FragmentMyPostBinding.inflate(inflater,container,false);
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
    }
}