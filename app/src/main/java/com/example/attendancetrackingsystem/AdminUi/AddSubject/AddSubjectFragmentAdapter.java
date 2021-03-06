package com.example.attendancetrackingsystem.AdminUi.AddSubject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendancetrackingsystem.Models.Subject;
import com.example.attendancetrackingsystem.R;

import java.util.ArrayList;

public class AddSubjectFragmentAdapter extends RecyclerView.Adapter<AddSubjectFragmentAdapter.AddSubjectFragmentViewholder> {

    ArrayList<Subject>subjectArrayList;
    public  AddSubjectFragmentAdapter(ArrayList<Subject>arrayList)
    {
        subjectArrayList=arrayList;
    }

    @NonNull
    @Override
    public AddSubjectFragmentViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_list_row3,parent,false);
        AddSubjectFragmentViewholder addSubjectFragmentViewholder=new AddSubjectFragmentViewholder(view);
        return addSubjectFragmentViewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull AddSubjectFragmentViewholder holder, int position) {
            Subject subject=subjectArrayList.get(position);
            holder.code.setText("Code:"+subject.getCode());
            holder.facultyId.setText("FacultyId:"+subject.getFid());
    }

    @Override
    public int getItemCount() {
        return subjectArrayList.size();
    }

    class AddSubjectFragmentViewholder extends RecyclerView.ViewHolder
    {
        TextView code;
        TextView facultyId;

        public AddSubjectFragmentViewholder(@NonNull View itemView) {
            super(itemView);
            code=itemView.findViewById(R.id.code);
            facultyId=itemView.findViewById(R.id.facultyid);
        }
    }
}
