package com.example.recycleit.views.view.courses;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.recycleit.R;
import com.example.recycleit.databinding.FragmentCourseBinding;
import com.example.recycleit.views.adapter.CourseAdapter;
import com.example.recycleit.views.auth.SharedPreferenceManager;
import com.example.recycleit.views.auth.UserType;
import com.example.recycleit.views.model.WorkShop;
import com.example.recycleit.views.model.firebase.CourseB;
import com.example.recycleit.views.model.local.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;


public class CourseFragment extends Fragment {


    private static final String TAG = "CourseFragment";
    private FragmentCourseBinding binding;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private SharedPreferenceManager sharedPreferenceManager=new SharedPreferenceManager();

    NavController navController;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference root = db.getReference().child("courses");
    private DatabaseReference rootuser = db.getReference().child(auth.getUid());
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    CourseB courseB = new CourseB();
    User user=new User();
    private CollectionReference usersCollection =
            firestore.collection("Recycle it database schema")
                    .document("Courses").collection(Objects.requireNonNull(auth.getUid()));
    CourseViewModel viewModel;


    private RecyclerView recyclerView;
    private CourseAdapter adapter;
    private ArrayList<CourseB> list;
    private ArrayList<User> list1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCourseBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity(), ViewModelProvider.AndroidViewModelFactory
                .getInstance(getActivity().getApplication())).get(CourseViewModel.class);

//        viewModel.getCourses();

        if(sharedPreferenceManager.getType(requireContext()).equals(UserType.BUSINESS.getType())){
            Log.i(TAG, "ttttttttt "+sharedPreferenceManager.getType(requireContext()));


        }
        else if(sharedPreferenceManager.getType(requireContext()).equals(UserType.REGULAR.getType()))
        {
            binding.fabCourse.setVisibility(View.INVISIBLE);
            Log.i(TAG, "ttttttttt "+sharedPreferenceManager.getType(requireContext()));

        }
        else {
            binding.fabCourse.setVisibility(View.INVISIBLE);
            Log.i(TAG, "ttttttttt "+sharedPreferenceManager.getType(requireContext()));

        }
        recyclerView = binding.recyclerview;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        list = new ArrayList<>();
        adapter = new CourseAdapter(list, requireContext());
        recyclerView.setAdapter(adapter);
        rootuser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    User user1=dataSnapshot.getValue(User.class);
                    list1.add(user1);
                    if(user1.getType().toString()=="regular")
                    {
                        Log.i(TAG, "onDataChange: dfdf");
                        binding.fabCourse.setVisibility(View.GONE);
                    }
                    else {
                        binding.fabCourse.setVisibility(View.GONE);
                        Log.i(TAG, "onDataChange: dfdf");

                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    CourseB courseB = dataSnapshot.getValue(CourseB.class);
                    list.add(courseB);
                    Log.i(TAG, "onDataChange: "+ courseB.toString());
                }
//                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        usersCollection.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                adapter.notifyDataSetChanged();
                if (error != null) {
                    Toast.makeText(requireContext(), "" + error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    Log.i(TAG, "onEvent: error to create course");
                    return;
                }
                for (DocumentSnapshot snapshot : value.getDocuments()) {
                    courseB = snapshot.toObject(CourseB.class);
                    list.add(courseB);
                    Log.i(TAG, "onEvent: workshop created" + courseB.getCourseDate());
                    adapter.notifyDataSetChanged();

                }
            }
        });


        binding.fabCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_courseFragment_to_addCoursesFragment);


            }
        });
        binding.imArrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_courseFragment_to_categoryFragment);
            }
        });
//        navController.navigate("courseFragment"){
//            popUpTo("navigation_homeFragment")
//        }


    }
}