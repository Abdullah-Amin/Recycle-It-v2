package com.example.recycleit.views.view.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.recycleit.R;
import com.example.recycleit.databinding.FragmentPostBinding;
import com.example.recycleit.views.auth.SharedPreferenceManager;

public class PostFragment extends Fragment {


    FragmentPostBinding binding;
    SharedPreferenceManager manager=new SharedPreferenceManager();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
binding=FragmentPostBinding.inflate(inflater,container,false);
    return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.itHeader.setText(manager.getAddPostDescr(getContext()));
        binding.tvPrice.setText(manager.getAddPostPrice(getContext()));
        Glide
                .with(getContext())
                .load(manager.getAddPostImage(getContext()))
                .placeholder(R.drawable.wwwww)
                .into(binding.imagePindind);
        binding.imArrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_postFragment2_to_navigation_homeFragment);

            }
        });
        binding.imHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(requireActivity(),"soon we will add this feature  ",Toast.LENGTH_LONG);
            }
        });
        binding.imagePindind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Navigation.findNavController(v).navigate(R.id.action_postFragment2_to_navigation_homeFragment);
                Toast.makeText(requireActivity(),"soon we will add this feature  ",Toast.LENGTH_LONG);
            }
        });
        binding.addToBag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //send data to your bag
                Toast.makeText(requireActivity(),"post add ",Toast.LENGTH_LONG);


                Navigation.findNavController(v).navigate(R.id.action_postFragment2_to_navigation_homeFragment);
            }
        });
    }
}