package com.example.attendancetrackingsystem.UserUi;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import static java.security.AccessController.getContext;

public class getSujectUser {
    private static ArrayList<String> arrayList=new ArrayList<>();
    private static DatabaseReference databaseReference;
    public static ArrayList<String> get()
    {
        final ArrayList<String>[] list = new ArrayList[]{new ArrayList<>()};

        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        String email=firebaseUser.getEmail();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Users");
        databaseReference.keepSynced(true);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    if(dataSnapshot.child("userMail").getValue().toString().equals(email))
                    {
                      arrayList= (ArrayList<String>) dataSnapshot.child("subjects").getValue();
                      Log.d("Subjectss", String.valueOf(arrayList.size()));

                    }
                }
                list[0] =arrayList;
                    Log.d("Subject new", String.valueOf(list[0].size()));
                }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        Log.d("list", String.valueOf(list[0].size()));
        return arrayList;
    }

}
