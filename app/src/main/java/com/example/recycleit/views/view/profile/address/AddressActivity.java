package com.example.recycleit.views.view.profile.address;


import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.recycleit.R;
import com.example.recycleit.databinding.ActivityAddressBinding;
import com.example.recycleit.views.adapter.AddressListAdapter;
import com.example.recycleit.views.model.firebase.Address;
import com.example.recycleit.views.view.auth.RegisterActivity;

public class AddressActivity extends AppCompatActivity {
  private   ActivityAddressBinding binding;

    private  AddressViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddressBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


//        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
//                .getInstance(this.getApplication())).get(AddressViewModel.class);



    }



}