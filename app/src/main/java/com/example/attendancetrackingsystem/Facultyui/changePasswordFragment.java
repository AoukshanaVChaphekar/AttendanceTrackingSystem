package com.example.attendancetrackingsystem.Facultyui;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.attendancetrackingsystem.Facultyui.home.HomeFragment;
import com.example.attendancetrackingsystem.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class changePasswordFragment extends Fragment {

    EditText oldpassword;
    EditText newpassword;
    EditText confirmpassword;
    Button submit;

    DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.faculty_fragment_change_password, container, false);

        oldpassword=root.findViewById(R.id.oldPassword);
        newpassword=root.findViewById(R.id.newPassword);
        confirmpassword=root.findViewById(R.id.confirmPassword);
        submit=root.findViewById(R.id.submit);

        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        String email= firebaseUser.getEmail();

        databaseReference=FirebaseDatabase.getInstance().getReference().child("Faculty");

    submit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (oldpassword.getText().toString().trim().length() > 0 && newpassword.getText().toString().trim().length()>0 &&newpassword.getText().toString().equals(confirmpassword.getText().toString())) {
                AuthCredential credential = EmailAuthProvider.getCredential(email, oldpassword.getText().toString());

                firebaseUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            firebaseUser.updatePassword(newpassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(getContext(), "Something went wrong.Please try again", Toast.LENGTH_SHORT).show();
                                    } else {
                                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                                                {
                                                    String id=dataSnapshot.getKey();
                                                    Log.d("email",dataSnapshot.child("email").getValue().toString());
                                                    if(dataSnapshot.child("email").getValue().toString().equalsIgnoreCase(email))
                                                    {
                                                        databaseReference.child(id).child("password").setValue(newpassword.getText().toString());
                                                         }

                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {
                                                Log.d("Database error",error.getMessage());
                                            }
                                        });

                                        Toast.makeText(getContext(), "Password updated successfully.", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                        } else {
                            Toast.makeText(getContext(), "Authentication Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            else
            {
                Toast.makeText(getContext(), "Enter Correct Credentials", Toast.LENGTH_SHORT).show();
            }
        }
    });

        return root;
    }

}