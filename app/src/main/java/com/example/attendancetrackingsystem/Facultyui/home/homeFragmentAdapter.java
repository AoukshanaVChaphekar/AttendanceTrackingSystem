package com.example.attendancetrackingsystem.Facultyui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendancetrackingsystem.Models.Subject;
import com.example.attendancetrackingsystem.R;

import java.util.ArrayList;

public class homeFragmentAdapter extends RecyclerView.Adapter<homeFragmentAdapter.homeViewHolder> {

    ArrayList<Subject> subjectArrayList;

    public  homeFragmentAdapter(ArrayList<Subject> s)
    {
        subjectArrayList=s;
    }

    @NonNull
    @Override
    public homeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.faculty_home_fragment_list_row,parent,false);
        homeViewHolder homeViewHolder=new homeViewHolder(view);

        return homeViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull homeViewHolder holder, int position) {
        Subject subject=subjectArrayList.get(position);
        holder.code.setText(subject.getCode());
        holder.courseName.setText("Course Name:"+subject.getName());
    }

    @Override
    public int getItemCount() {
        return subjectArrayList.size();
    }

    class homeViewHolder extends RecyclerView.ViewHolder
    {
        TextView code;
        TextView courseName;
        public homeViewHolder(@NonNull View itemView) {
            super(itemView);

            code=itemView.findViewById(R.id.codeTextView);
            courseName=itemView.findViewById(R.id.courseNameTextView);
        }
    }
}
