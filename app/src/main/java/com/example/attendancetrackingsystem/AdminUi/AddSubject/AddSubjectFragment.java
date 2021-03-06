package com.example.attendancetrackingsystem.AdminUi.AddSubject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendancetrackingsystem.Models.Subject;
import com.example.attendancetrackingsystem.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddSubjectFragment extends Fragment {


    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    FloatingActionButton floatingActionButton;
    ArrayList<Subject> subjectArrayList=new ArrayList<>();
    RecyclerView recyclerView;
    AddSubjectFragmentAdapter addSubjectFragmentAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.admin_fragment_subject, container, false);

        databaseReference=FirebaseDatabase.getInstance().getReference().child("Subject");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                subjectArrayList.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    String code=dataSnapshot.child("code").getValue().toString();
                    ArrayList<String> fid= (ArrayList<String>) dataSnapshot.child("fid").getValue();
                    subjectArrayList.add(new Subject(code,fid));

                }
                addSubjectFragmentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        recyclerView=root.findViewById(R.id.SrecyclerView);
        addSubjectFragmentAdapter=new AddSubjectFragmentAdapter(subjectArrayList);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(addSubjectFragmentAdapter);


        floatingActionButton=root.findViewById(R.id.fabSubjectButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),add_subject_activiity.class));

            }
        });
        return root;
    }
}