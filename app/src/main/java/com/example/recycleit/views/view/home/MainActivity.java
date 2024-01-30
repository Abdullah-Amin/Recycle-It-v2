package com.example.recycleit.views.view.home;



import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.recycleit.R;
import com.example.recycleit.databinding.ActivityMainBinding;
import com.example.recycleit.views.auth.SharedPreferenceManager;
import com.example.recycleit.views.auth.UserType;
import com.example.recycleit.views.view.auth.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (firebaseAuth.getCurrentUser() == null) {
            Intent intent=new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            return;
        }
        else {
               Toast.makeText(this,"welcome ",Toast.LENGTH_SHORT).show();
              }

        SharedPreferenceManager manager = new SharedPreferenceManager();

        String type = manager.getType(this);

        if (type.equals(UserType.GUEST.getType())){
            binding.bottomNav.getMenu().getItem(2).setEnabled(false);

        }

        // BottomNavigationView navView = binding.bottomNav;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        BottomNavigationView navView = binding.bottomNav;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_homeFragment, R.id.navigation_myBagFragment, R.id.navigation_favoriteFragment,
                R.id.navigation_userDetailsFragment)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
       NavigationUI.setupWithNavController(binding.bottomNav, navController);


    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //firebaseAuth.signOut();
        return;
    }
}
