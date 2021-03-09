package com.example.attendancetrackingsystem.Facultyui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendancetrackingsystem.Models.Subject;
import com.example.attendancetrackingsystem.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    homeFragmentAdapter homeFragmentAdapter;
    static ArrayList<Subject > subject=new ArrayList<>();
    ArrayList<Subject> subjectArrayList=new ArrayList<>();

    FirebaseDatabase database;
    DatabaseReference databaseReference;

    String fid;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.faculty_fragment_home, container, false);


        database = FirebaseDatabase.getInstance();


        FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        String email=firebaseUser.getEmail();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Faculty");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1:snapshot.getChildren())
                {
                    if(snapshot1.child("email").getValue().toString().equalsIgnoreCase(email))
                    {
                        fid=snapshot1.child("fid").getValue().toString();
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReference=FirebaseDatabase.getInstance().getReference().child("Subject");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                subject.clear();
                subjectArrayList.clear();
                for(DataSnapshot snapshot1:snapshot.getChildren())
                {
                    ArrayList<String> fids= (ArrayList<String>) snapshot1.child("fid").getValue();
                    if(fids.contains(fid))
                    {
                        String subCode=snapshot1.child("code").getValue().toString();
                        String subName=snapshot1.child("name").getValue().toString();
                        subjectArrayList.add(new Subject(subName,subCode));
                        subject.add(new Subject(subName,subCode));
                    }
                }
                homeFragmentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        recyclerView=root.findViewById(R.id.FrecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        homeFragmentAdapter=new homeFragmentAdapter(subjectArrayList);
        recyclerView.setAdapter(homeFragmentAdapter);
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
    public static ArrayList<Subject> getSubName()
    {
        return  subject;
    }
}