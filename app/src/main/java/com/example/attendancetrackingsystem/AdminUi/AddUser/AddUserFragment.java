package com.example.attendancetrackingsystem.AdminUi.AddUser;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendancetrackingsystem.Models.User;
import com.example.attendancetrackingsystem.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddUserFragment extends Fragment {


    FloatingActionButton floatingActionButton;
    RecyclerView recyclerView;
    UserFragmentAdapter adapter;
    ArrayList<User> stringArrayList=new ArrayList<>();

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.admin_fragment_user, container, false);
        //getDATA FROM USER
        database= FirebaseDatabase.getInstance();
        databaseReference=database.getReference().child("Users");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                stringArrayList.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    ArrayList<String> list=new ArrayList<>();
                    list.add(dataSnapshot.child("subjects").getValue().toString());
                    String uid=dataSnapshot.child("userid").getValue().toString();
                    stringArrayList.add(new User(uid,list));
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

            recyclerView=root.findViewById(R.id.recyclerView1);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter= new UserFragmentAdapter(stringArrayList);
        recyclerView.setAdapter(adapter);

        floatingActionButton = root.findViewById(R.id.fabButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), user_add.class);
                startActivity(intent);
            }
        });
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                //Do nothing
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
        callback.isEnabled();



        return root;

    }
}

