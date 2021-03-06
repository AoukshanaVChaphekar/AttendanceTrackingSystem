package com.example.attendancetrackingsystem.AdminUi.AddUser;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendancetrackingsystem.Models.Subject;
import com.example.attendancetrackingsystem.R;
import com.example.attendancetrackingsystem.AdminlistItemClicked;

import java.util.ArrayList;
import java.util.List;

public class UserAddAdapter extends RecyclerView.Adapter<UserAddAdapter.UserViewHolder> {


    ArrayList<Subject> subjectArrayList;
    ArrayList<String> selectedItems=new ArrayList<>();
    AdminlistItemClicked listener;
    public UserAddAdapter(AdminlistItemClicked listener, ArrayList<Subject> subjectArrayList)
    {
        this.listener=listener;
        this.subjectArrayList=subjectArrayList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_list_row,parent,false);
        final UserViewHolder userViewHolder=new UserViewHolder(view);

        return userViewHolder;
    }

    List<String> getSelectedItems()
    {
        return selectedItems;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        final Subject currentSubject = subjectArrayList.get(position);
        holder.subjectName.setText("Name:" + currentSubject.getName());
        holder.codeText.setText("Code: "+currentSubject.getCode());
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                    selectedItems.add(currentSubject.getCode());
                else
                    selectedItems.remove(currentSubject.getCode());

            }
        });

    }




    @Override
    public int getItemCount() {
        return subjectArrayList.size();
    }



    class UserViewHolder extends RecyclerView.ViewHolder
    {
        CheckBox checkBox;
        TextView subjectName;
        TextView codeText;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox=itemView.findViewById(R.id.checkbox1);
            subjectName=itemView.findViewById(R.id.CourseNameText);
            codeText=itemView.findViewById(R.id.CourseCodeText);
        }
    }
}
