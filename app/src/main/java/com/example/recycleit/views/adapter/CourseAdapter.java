package com.example.recycleit.views.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recycleit.R;
import com.example.recycleit.views.model.firebase.CourseB;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    ArrayList<CourseB>courseList;
      Context context;

    public CourseAdapter(ArrayList<CourseB> courseList, Context context) {
        this.courseList = courseList;
        this.context = context;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_course,parent,false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {

        CourseB courseB=courseList.get(position);
        holder.courseName.setText(courseB.getCourseName());
        holder.courseDate.setText(courseB.getCourseDate());
        holder.courseState.setText(courseB.getCourseGoals());
        holder.goal_1.setText("1- "+courseB.getCourseGoals());



    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public static class CourseViewHolder extends RecyclerView.ViewHolder{
        Boolean state=true;
        TextView courseName,courseDate,courseState ;
        EditText goal_1,goal_2,goal_3;
        ConstraintLayout theGoals;
        ImageView displayMore;
        MaterialButton button;
        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            courseName=itemView.findViewById(R.id.tv_course);
            courseDate=itemView.findViewById(R.id.tv_calender);
            courseState=itemView.findViewById(R.id.tv_state);
              displayMore=itemView.findViewById(R.id.im_arrow_down);
              theGoals=itemView.findViewById(R.id.constraint_displaymore);
              goal_1=itemView.findViewById(R.id.firstGoalEt);
              goal_2=itemView.findViewById(R.id.secondGoalEt);
              goal_3=itemView.findViewById(R.id.thirdGoalEt);
           button=itemView.findViewById(R.id.registrationBtn);
            theGoals.setVisibility(View.GONE);
            displayMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "your click me well " + getAdapterPosition(), Toast.LENGTH_SHORT).show();

                   if(!state){
                       theGoals.setVisibility(View.VISIBLE);



                   }else {

                       theGoals.setVisibility(View.GONE);
                   }
                   state=!state;
                }

            });
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Navigation.findNavController(v).navigate(R.id.action_courseFragment_to_registerCourseFragment);
                }
            });
        }



    }
}
