package com.example.recycleit.views.view.workshop;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.recycleit.R;
import com.example.recycleit.databinding.FragmentRegisterWorkshopBinding;
import com.example.recycleit.views.model.firebase.RegisterCourses;
import com.example.recycleit.views.view.courses.CourseViewModel;


public class RegisterWorkshopFragment extends Fragment {
    FragmentRegisterWorkshopBinding binding;
    private static final String TAG = "RegisterWorkshopFragmen";
    CourseViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentRegisterWorkshopBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
//        RegisterCourses registerCourses= new RegisterCourses(binding.etdFirstName.getText().toString().trim(),
//                binding.etdLastName.getText().toString().trim(),binding.etdEmail.getText().toString().trim(),binding.etdPlace.getText().toString());
//        viewModel.registerForCourse(registerCourses);
        Toast.makeText(requireContext(),"The course register",Toast.LENGTH_LONG).show();


        Navigation.findNavController(v).navigate(R.id.action_registerWorkshopFragment_to_listWorkshopFragment);

    }
});
binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Navigation.findNavController(v).navigate(R.id.action_registerWorkshopFragment_to_listWorkshopFragment);
    }
});


    }
}