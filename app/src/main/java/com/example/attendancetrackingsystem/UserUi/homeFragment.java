package com.example.attendancetrackingsystem.UserUi;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

public class homeFragment extends Fragment {
    TextView name;
    TextView contact;

    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;

    ArrayList<Subject> subjectArrayList=new ArrayList<>();


    RecyclerView recyclerView;
    homeFragmentAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.user_fragment_home, container, false);

        name=root.findViewById(R.id.nameEditText);
        contact=root.findViewById(R.id.contactEditText);

        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        String email=firebaseUser.getEmail();

        databaseReference= FirebaseDatabase.getInstance().getReference().child("Users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String uemail=dataSnapshot.child("userMail").getValue().toString();
                    if(uemail.equalsIgnoreCase(email))
                    {
                        name.setText(dataSnapshot.child("fname").getValue().toString()+" "
                                +dataSnapshot.child("lname").getValue().toString());

                        contact.setText(dataSnapshot.child("phnNo").getValue().toString());
                        ArrayList<String> subjectCodeList= (ArrayList<String>) dataSnapshot.child("subjects").getValue();



                        databaseReference=FirebaseDatabase.getInstance().getReference().child("Subject");
                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                subjectArrayList.clear();
                                for(DataSnapshot dataSnapshot1:snapshot.getChildren())
                                {
                                    String code=dataSnapshot1.child("code").getValue().toString();
                                    if(subjectCodeList.contains(code))
                                    {
                                        String name=dataSnapshot1.child("name").getValue().toString();
                                        ArrayList<String> fids= (ArrayList<String>) dataSnapshot1.child("fid").getValue();


                                        subjectArrayList.add(new Subject(name,code,fids));
                                    }
                                }
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });





                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        recyclerView=root.findViewById(R.id.studentDataRv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter=new homeFragmentAdapter(subjectArrayList);

        recyclerView.setAdapter(adapter);
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