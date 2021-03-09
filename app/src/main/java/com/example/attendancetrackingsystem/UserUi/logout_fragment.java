package com.example.attendancetrackingsystem.UserUi;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.attendancetrackingsystem.MainActivity;
import com.example.attendancetrackingsystem.R;
public class logout_fragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //startActivity(new Intent(getContext(), MainActivity.class));
       // getActivity().finish();

        Intent loginscreen=new Intent(getContext(),MainActivity.class);
        loginscreen.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(loginscreen);
        getActivity().finish();
        return null;    }
}