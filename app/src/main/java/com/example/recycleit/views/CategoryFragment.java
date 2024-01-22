package com.example.recycleit.views;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.recycleit.databinding.FragmentCategoryBinding;
import com.example.recycleit.views.view.courses.CourseActivity;
import com.example.recycleit.views.view.workshop.WorkShopActivity;

public class CategoryFragment extends Fragment {

    FragmentCategoryBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCategoryBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoCourse();
            }
        });
        binding.buttonWorkshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToWorkshop();
            }
        });
    }

    private void goToWorkshop() {
        Intent intent = new Intent(requireContext(), WorkShopActivity.class);
        startActivity(intent);

    }

    private void gotoCourse() {
        Intent intent = new Intent(requireContext(), CourseActivity.class);
        startActivity(intent);
    }
}