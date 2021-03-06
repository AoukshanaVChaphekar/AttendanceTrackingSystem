package com.example.attendancetrackingsystem.AdminUi.AddFaculty;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendancetrackingsystem.Models.Faculty;
import com.example.attendancetrackingsystem.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddFacultyFragment extends Fragment {


    FloatingActionButton floatingActionButton;
    RecyclerView recyclerView;
    AddFacultyAdapter addFacultyAdapter;
    ArrayList<Faculty> arrayList=new ArrayList<>();
    DatabaseReference databaseReference;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.admin_fragment_faculty, container, false);


        databaseReference=FirebaseDatabase.getInstance().getReference().child("Faculty");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    String id=dataSnapshot.child("fid").getValue().toString();
                    String contact=dataSnapshot.child("mobNo").getValue().toString();

                    arrayList.add(new Faculty(id,contact));
                    }
                addFacultyAdapter.notifyDataSetChanged();

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        recyclerView=root.findViewById(R.id.FrecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        addFacultyAdapter=new AddFacultyAdapter(arrayList);
        recyclerView.setAdapter(addFacultyAdapter);




        floatingActionButton=root.findViewById(R.id.fabFacultyButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),AddFacultyActivity.class));
            }
        });

        return root;

    }
}
