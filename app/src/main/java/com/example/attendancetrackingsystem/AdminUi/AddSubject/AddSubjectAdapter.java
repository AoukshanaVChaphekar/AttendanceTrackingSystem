package com.example.attendancetrackingsystem.AdminUi.AddSubject;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendancetrackingsystem.R;

import java.util.ArrayList;


public class AddSubjectAdapter extends RecyclerView.Adapter<AddSubjectAdapter.AddSubjectViewHolder> {
    ArrayList<String> idArrayList;
  public static ArrayList<String> selectedId=new ArrayList<>();
  private facultySelectedItemListener listener;
    public int mSelectedItem = -1;
    public  AddSubjectAdapter(ArrayList<String> arrayList,facultySelectedItemListener listener)
    {
        idArrayList=arrayList;
        this.listener=listener;

    }
    @NonNull
    @Override
    public AddSubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_add_subject_list_row,parent,false);
        AddSubjectViewHolder addSubjectViewHolder=new AddSubjectViewHolder(view);

    return addSubjectViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AddSubjectViewHolder holder, int position) {

        String currentId = idArrayList.get(position);
        holder.facultyId.setText(currentId);
        holder.radioButton.setChecked(position == mSelectedItem);

               if(holder.radioButton.isChecked())
                   selectedId.add(currentId);
               else
                   selectedId.remove(currentId);

                listener.onItemClicked(selectedId);



    }

    @Override
    public int getItemCount() {
        return idArrayList.size();
    }

    class AddSubjectViewHolder extends RecyclerView.ViewHolder
    {

        TextView facultyId;
        RadioButton radioButton;
        public AddSubjectViewHolder(@NonNull View itemView) {
            super(itemView);
            facultyId=itemView.findViewById(R.id.facultyIdText);
            radioButton=itemView.findViewById(R.id.radioButton);


            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
            int copyOfLastCheckedPosition = mSelectedItem;
            mSelectedItem=getAdapterPosition();
            notifyItemChanged(copyOfLastCheckedPosition);
            notifyItemChanged(mSelectedItem);


                }
            });
        }
    }

    public interface facultySelectedItemListener {
        void onItemClicked(ArrayList<String> list);
    }

}
