package com.example.recycleit.views.view.workshop;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.recycleit.R;
import com.example.recycleit.databinding.FragmentListWorkshopBinding;
import com.example.recycleit.views.adapter.CourseAdapter;
import com.example.recycleit.views.adapter.WorkshopAdaptor;
import com.example.recycleit.views.model.WorkShop;
import com.example.recycleit.views.model.firebase.CourseB;
import com.example.recycleit.views.view.courses.CourseViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SnapshotMetadata;

import java.util.ArrayList;


public class ListWorkshopFragment extends Fragment {


    FragmentListWorkshopBinding binding;
    private static final String TAG = "ListWorkshopFragment";
    private WorkShopViewModel viewModel;
    private RecyclerView recyclerView;
    private WorkshopAdaptor adapter;
    private WorkShop workShop=new WorkShop();

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth auth=FirebaseAuth.getInstance();

    private CollectionReference usersCollection =
            db.collection("Recycle it database schema").document("workshop").collection(auth.getUid());
    private ArrayList<WorkShop> list=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentListWorkshopBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity(), ViewModelProvider.AndroidViewModelFactory
                .getInstance(getActivity().getApplication())).get(WorkShopViewModel.class);

        recyclerView = binding.recyclerview;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        list = new ArrayList<>();
        adapter = new WorkshopAdaptor(list, requireContext());
        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    //  WorkShop workShop = new WorkShop("ali","ali","ali","ali");
//        list.add(workShop);
      usersCollection.addSnapshotListener(new EventListener<QuerySnapshot>() {
          @Override
          public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

              if (error != null) {
                  Toast.makeText(requireContext(), "" + error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                  Log.i(TAG, "onEvent: error to create workshop");
                  return;
              }
              for (DocumentSnapshot snapshot : value.getDocuments()) {
                 workShop= snapshot.toObject(WorkShop.class);
                  list.add(workShop);
                  Log.i(TAG, "onEvent: workshop created" + workShop.getWorkshopName());
                  adapter.notifyDataSetChanged();
              }
          }
      });



        binding.workshopFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_listWorkshopFragment_to_addWorkShopeFragment);

            }
        });
        binding.imArrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_listWorkshopFragment_to_categoryFragment2);


            }
        });

    }


}