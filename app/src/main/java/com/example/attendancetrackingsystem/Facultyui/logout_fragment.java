package com.example.attendancetrackingsystem.Facultyui;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.attendancetrackingsystem.Facultyui.home.HomeFragment;
import com.example.attendancetrackingsystem.MainActivity;
import com.example.attendancetrackingsystem.R;
public class logout_fragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        startActivity(new Intent(getContext(), MainActivity.class));
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                NavHostFragment.findNavController(logout_fragment.this).navigateUp();
                //setEnabled(false); // call this to disable listener
                //remove(); // call to remove listener
                //Toast.makeText(getContext(), "Listing for back press from this fragment", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }
}