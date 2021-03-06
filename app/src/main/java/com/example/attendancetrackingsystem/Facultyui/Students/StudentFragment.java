package com.example.attendancetrackingsystem.Facultyui.Students;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.attendancetrackingsystem.Facultyui.StudentList;
import com.example.attendancetrackingsystem.Facultyui.home.HomeFragment;
import com.example.attendancetrackingsystem.Models.Subject;
import com.example.attendancetrackingsystem.R;

import java.util.ArrayList;

public class StudentFragment extends Fragment {

    Spinner spinner;
    Button getSubject;
    ArrayList<Subject> subjectArrayList= HomeFragment.getSubName();

    int position;
    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.faculty_fragment_students, container, false);
        spinner = root.findViewById(R.id.spinner);
        getSubject=root.findViewById(R.id.getStudentButton);



        String[] list=new String[subjectArrayList.size()+1];
        list[0]="Select Course";
        for(int i=0;i<list.length-1;i++)
        {
            list[i+1]=subjectArrayList.get(i).getCode()+" - "+subjectArrayList.get(i).getName();
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item,list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner.setAdapter(dataAdapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    position=i;
                    if(position==0)
                    {
                        Toast.makeText(getContext(),"Select Subject",Toast.LENGTH_SHORT).show();
                    }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        getSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code=subjectArrayList.get(position-1).getCode();

                Intent intent=new Intent(getActivity(), StudentList.class);
                intent.putExtra("code",code);
                startActivity(intent);
            }
        });
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                NavHostFragment.findNavController(StudentFragment.this).navigateUp();
                //setEnabled(false); // call this to disable listener
                //remove(); // call to remove listener
                //Toast.makeText(getContext(), "Listing for back press from this fragment", Toast.LENGTH_SHORT).show();
            }
        });


       return root;

    }
}