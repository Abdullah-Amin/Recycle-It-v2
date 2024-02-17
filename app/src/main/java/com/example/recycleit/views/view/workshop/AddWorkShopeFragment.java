package com.example.recycleit.views.view.workshop;

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
import com.example.recycleit.databinding.FragmentAddWorkShopeBinding;
import com.example.recycleit.views.model.WorkShop;
import com.example.recycleit.views.view.profile.address.AddressViewModel;


public class AddWorkShopeFragment extends Fragment {
FragmentAddWorkShopeBinding binding;
    private WorkShopViewModel viewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
binding= FragmentAddWorkShopeBinding.inflate(inflater,container,false) ;
    return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(getActivity().getApplication())).get(WorkShopViewModel.class);
            binding.buttonRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(binding.etdCourseName.getText().toString().isEmpty())
                    {
                        Toast.makeText(requireContext(),"fell workshop name ",Toast.LENGTH_LONG).show();
                        return;
                    }
                    if(binding.etdCourseDate.getText().toString().isEmpty())
                    {
                        Toast.makeText(requireContext(),"fell workshop date ",Toast.LENGTH_LONG).show();
                        return;
                    }
                    if(binding.etdCourseLocation.getText().toString().isEmpty())
                    {
                        Toast.makeText(requireContext(),"fell workshop location",Toast.LENGTH_LONG).show();
                        return;
                    }

//                    String state = "Online";
//                    if (binding.buttonCancel.getCheckedRadioButtonId() == R.id.radio_person){
//                        state = "In-Person";
//                    }else {
//                        state = "Online";
//                    }

                    WorkShop workShop= new WorkShop(binding.etdCourseName.getText().toString().trim(),
                            binding.etdCourseDate.getText().toString().trim(),binding.etdCourseLocation.getText().toString().trim(),binding.etdCourseDate2.getText().toString().trim());
                    viewModel.loadWorkShop(workShop);
                    Navigation.findNavController(v).navigate(R.id.action_addWorkShopeFragment_to_listWorkshopFragment);

                }
            });
            binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Navigation.findNavController(v).navigate(R.id.action_addWorkShopeFragment_to_listWorkshopFragment);

                }
            });
            binding.imArrowBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Navigation.findNavController(v).navigate(R.id.action_addWorkShopeFragment_to_listWorkshopFragment);
                }
            });
    }
}