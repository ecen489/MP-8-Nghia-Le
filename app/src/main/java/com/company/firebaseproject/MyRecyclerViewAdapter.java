package com.company.firebaseproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.SimpsonsViewHolder> {

    private List<Grade> simpsonsList;
    private Context context;

    public MyRecyclerViewAdapter(Context context, List<Grade> simpsonsList) {
        this.context = context;
        this.simpsonsList = simpsonsList;
    }

    @NonNull
    @Override
    public SimpsonsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_row, parent, false);
        return new SimpsonsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SimpsonsViewHolder holder, int position) {
        Grade grade = simpsonsList.get(position);

        // Set student name according to ID
        if(grade.student_id == 123) {
            holder.textViewID.setText("Student: Bart");
        } else if (grade.student_id == 404) {
            holder.textViewID.setText("Student: Ralph");
        } else if (grade.student_id == 456) {
            holder.textViewID.setText("Student: Milhouse");
        } else {
            holder.textViewID.setText("Student: Lisa");
        }
        holder.textViewCourseName.setText("Course: " + grade.course_name);
        holder.textViewGrade.setText("Grade: " + grade.grade);
    }

    @Override
    public int getItemCount() {
        return simpsonsList.size();
    }

    class SimpsonsViewHolder extends RecyclerView.ViewHolder {

        TextView textViewID, textViewGrade, textViewCourseName;

        public SimpsonsViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewID = itemView.findViewById(R.id.text_view_ID);
            textViewGrade = itemView.findViewById(R.id.text_view_grade);
            textViewCourseName = itemView.findViewById(R.id.text_view_course_name);
        }
    }
}
