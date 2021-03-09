package com.example.attendancetrackingsystem.AdminUi.AddSubject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendancetrackingsystem.Models.Subject;
import com.example.attendancetrackingsystem.R;
import com.example.attendancetrackingsystem.UserUi.viewAttendance_fragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddSubjectFragment extends Fragment {


    private DatabaseReference databaseReference;
    FloatingActionButton floatingActionButton;
    ArrayList<Subject> subjectlist=new ArrayList<>();
    RecyclerView recyclerView;
    AddSubjectFragmentAdapter addSubjectFragmentAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.admin_fragment_subject, container, false);

        getCallBack(new CallBack() {
            @Override
            public void onCallBack(ArrayList<Subject> list) {
                subjectlist=list;
                Log.d("Size", String.valueOf(subjectlist.size()));
                recyclerView=root.findViewById(R.id.SrecyclerView);
                addSubjectFragmentAdapter=new AddSubjectFragmentAdapter(subjectlist);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(addSubjectFragmentAdapter);
            }
        });



        floatingActionButton=root.findViewById(R.id.fabSubjectButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),add_subject_activiity.class));

            }
        });
        return root;
    }
    public void getCallBack(CallBack callBack)
    {
        ArrayList<Subject> list=new ArrayList<>();
        databaseReference=FirebaseDatabase.getInstance().getReference().child("Subject");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    String code=dataSnapshot.child("code").getValue().toString();
                    ArrayList<String> facultyId= (ArrayList<String>) dataSnapshot.child("fid").getValue();
                    list.add(new Subject(code,facultyId));
                }
                callBack.onCallBack(list);
                Log.d("Size", String.valueOf(list.size()));
                addSubjectFragmentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public interface CallBack
    {
         void onCallBack(ArrayList<Subject> list);
    }

}