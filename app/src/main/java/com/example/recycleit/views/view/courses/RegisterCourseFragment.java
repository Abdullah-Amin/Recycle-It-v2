package com.example.recycleit.views.view.courses;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.recycleit.R;
import com.example.recycleit.databinding.FragmentRegisterCourseBinding;
import com.example.recycleit.views.model.firebase.CourseB;
import com.example.recycleit.views.model.firebase.RegisterCourses;


public class RegisterCourseFragment extends Fragment {
FragmentRegisterCourseBinding binding;
    CourseViewModel viewModel;
    private static final String TAG = "RegisterCourseFragment";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
binding=FragmentRegisterCourseBinding.inflate(inflater,container,false);
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
        if(binding.etdFirstName.getText().toString().isEmpty()||
                binding.etdLastName.getText().toString().isEmpty()||
                binding.etdEmail.getText().toString().isEmpty()||
                binding.etdPlace.getText().toString().isEmpty()
        ){
            Toast.makeText(requireContext(),"All field for register is required",Toast.LENGTH_LONG).show();
            Log.i(TAG, "onClick: All field for register is required");
            return;
        }
        RegisterCourses registerCourses= new RegisterCourses(binding.etdFirstName.getText().toString().trim(),
                binding.etdLastName.getText().toString().trim(),binding.etdEmail.getText().toString().trim(),binding.etdPlace.getText().toString());
        viewModel.registerForCourse(registerCourses);
        Toast.makeText(requireContext(),"The course register",Toast.LENGTH_LONG).show();
        Navigation.findNavController(v).navigate(R.id.action_registerCourseFragment_to_courseFragment);

    }

});
binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Navigation.findNavController(v).navigate(R.id.action_registerCourseFragment_to_courseFragment);

    }
});




    }


}