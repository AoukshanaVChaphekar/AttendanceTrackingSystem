package com.example.attendancetrackingsystem.UserUi;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.attendancetrackingsystem.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class changePassword_fragment extends Fragment {

    EditText oldpassword;
    EditText newpassword;
    EditText confirmpassword;
    Button submit;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root=inflater.inflate(R.layout.user_change_password_fragment, container, false);;

        oldpassword=root.findViewById(R.id.UoldPassword);
        newpassword=root.findViewById(R.id.UnewPassword);
        confirmpassword=root.findViewById(R.id.UconfirmPassword);
        submit=root.findViewById(R.id.Usubmit);

        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        String email= firebaseUser.getEmail();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthCredential credential = EmailAuthProvider.getCredential(email,oldpassword.getText().toString());

                firebaseUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            firebaseUser.updatePassword(newpassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(!task.isSuccessful()){
                                        Toast.makeText(getContext(),"Something went wrong.Please try again",Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(getContext(),"Password updated successfully.",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }else {
                            Toast.makeText(getContext(),"Authentication Failed",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                NavHostFragment.findNavController(com.example.attendancetrackingsystem.UserUi.changePassword_fragment.this).navigateUp();
                //setEnabled(false); // call this to disable listener
                //remove(); // call to remove listener
                //Toast.makeText(getContext(), "Listing for back press from this fragment", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

}