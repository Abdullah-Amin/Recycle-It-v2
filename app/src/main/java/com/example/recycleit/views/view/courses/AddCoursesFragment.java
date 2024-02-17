package com.example.recycleit.views.view.courses;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.recycleit.R;
import com.example.recycleit.databinding.FragmentAddCoursesBinding;
import com.example.recycleit.views.model.WorkShop;
import com.example.recycleit.views.model.firebase.CourseB;
import com.example.recycleit.views.view.workshop.WorkShopViewModel;


public class AddCoursesFragment extends Fragment {

    FragmentAddCoursesBinding binding;

    CourseViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddCoursesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(getActivity().getApplication())).get(CourseViewModel.class);


        binding.buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (binding.etdCourseName.getText().toString().isEmpty()) {
                    Toast.makeText(requireContext(), "fell course name ", Toast.LENGTH_LONG).show();
                    return;
                }
                if (binding.etdCourseName.getText().toString().isEmpty()) {
                    Toast.makeText(requireContext(), "fell course date ", Toast.LENGTH_LONG).show();
                    return;
                }
                if (binding.etdDateCourse.getText().toString().isEmpty()) {
                    Toast.makeText(requireContext(), "fell course location", Toast.LENGTH_LONG).show();
                    return;
                }
                String state = "Online";
                if (binding.radioGroup.getCheckedRadioButtonId() == R.id.radio_person){
                    state = "In-Person";
                }else {
                    state = "Online";
                }

                CourseB courseB = new CourseB(binding.etdCourseName.getText().toString().trim(),
                        binding.etdDateCourse.getText().toString().trim(), state, binding.etdGoalCourse.getText().toString().trim());
                viewModel.loadcourse(courseB);
                Toast.makeText(requireContext(), "The course created", Toast.LENGTH_LONG).show();
                Navigation.findNavController(v).navigate(R.id.action_addCoursesFragment_to_courseFragment);

            }
        });
        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_addCoursesFragment_to_courseFragment);
            }
        });

        binding.imArrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_addCoursesFragment_to_courseFragment);
            }
        });
    }
}