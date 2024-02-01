package com.example.recycleit.views.view.home;

import android.net.Uri;
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

import com.bumptech.glide.Glide;
import com.example.recycleit.R;
import com.example.recycleit.databinding.FragmentPostBinding;
import com.example.recycleit.views.auth.SharedPreferenceManager;
import com.example.recycleit.views.model.firebase.PostItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

public class PostFragment extends Fragment {


    FragmentPostBinding binding;
    SharedPreferenceManager manager=new SharedPreferenceManager();
    private static final String TAG = "PostFragment";
    FirebaseFirestore store=FirebaseFirestore.getInstance();
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
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


//     String m=   manager.getPostItem(requireContext()).split("\\{")[1];
//        m="\\{"+m;
      //  manager.get(requireContext());
        Log.i(TAG, "onViewCreated: "+manager.getPostItem(requireContext()));
        Gson gson = new Gson();
        PostItem item = gson.fromJson(manager.getPostItem(requireContext()), PostItem.class);
        Log.i(TAG, "onViewCreated: "+item.toString());

        binding.itHeader.setText(item.getItemName());
        binding.desc.setText(item.getDescription());
        binding.tvPrice.setText(item.getPrice());
        Glide
                .with(getContext())
                .load(Uri.parse(item.getItemImage()))
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

                store.collection("Recycle it database schema").document("Orders")
                        .collection(firebaseAuth.getCurrentUser().getUid()).document(
                                item.getItemName()
                                + item.getPrice()
                                +item.getDescription()
                        ).set(item)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(view.getContext(), "Added to cart successfully", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                Navigation.findNavController(v).navigate(R.id.action_postFragment2_to_navigation_homeFragment);
            }
        });
    }
}