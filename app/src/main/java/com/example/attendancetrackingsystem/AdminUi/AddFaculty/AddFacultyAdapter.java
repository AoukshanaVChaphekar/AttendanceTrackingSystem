package com.example.attendancetrackingsystem.AdminUi.AddFaculty;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendancetrackingsystem.Models.Faculty;
import com.example.attendancetrackingsystem.R;

import java.util.ArrayList;

public class AddFacultyAdapter extends RecyclerView.Adapter<AddFacultyAdapter.FacultyViewHolder> {

    ArrayList<Faculty> facultyArrayList;

    public AddFacultyAdapter(ArrayList<Faculty> facultyArrayList)
    {
        this.facultyArrayList=facultyArrayList;
    }

    @NonNull
    @Override
    public FacultyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_faculty_list_row,parent,false);
        FacultyViewHolder  facultyViewHolder =new FacultyViewHolder(view);

        return facultyViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FacultyViewHolder holder, int position) {
        Faculty faculty=facultyArrayList.get(position);
        holder.id.setText("Id: "+faculty.getFid());
        holder.contact.setText("Contact:"+faculty.getMobNo());

    }

    @Override
    public int getItemCount() {
        return facultyArrayList.size();
    }

    class FacultyViewHolder extends RecyclerView.ViewHolder
    {
        TextView id;
        TextView contact;
        public FacultyViewHolder(@NonNull View itemView) {
            super(itemView);
            id=itemView.findViewById(R.id.FId);
            contact=itemView.findViewById(R.id.Fcontact);
        }
    }
}
