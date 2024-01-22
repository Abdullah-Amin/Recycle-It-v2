package com.example.recycleit.views.view.profile.about;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.recycleit.R;
import com.example.recycleit.databinding.FragmentAboutBinding;
import com.example.recycleit.views.view.profile.post.MyPostActivity;
import com.example.recycleit.views.view.profile.user.UserDetailsFragment;


public class AboutFragment extends Fragment {
    FragmentAboutBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       binding=FragmentAboutBinding.inflate(inflater,container,false);
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
    }
}