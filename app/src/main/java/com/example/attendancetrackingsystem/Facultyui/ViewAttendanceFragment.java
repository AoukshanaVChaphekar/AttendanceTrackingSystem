package com.example.attendancetrackingsystem.Facultyui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.attendancetrackingsystem.Facultyui.home.HomeFragment;
import com.example.attendancetrackingsystem.MainActivity;
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
import java.util.Calendar;
import java.util.Date;

public class ViewAttendanceFragment extends Fragment {

   public static EditText startDate;
  public static EditText endDate;
    RelativeLayout startLayout;
    RelativeLayout endLayout;


    Button getAttendance;


   static ArrayList<Subject > subjects= HomeFragment.getSubName();
    Spinner spinner;
    int position;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root= inflater.inflate(R.layout.faculty_fragment_view_attendance, container, false);
        startDate=root.findViewById(R.id.startDate);
        endDate=root.findViewById(R.id.endDate);
        startLayout=root.findViewById(R.id.startLayout);
        endLayout=root.findViewById(R.id.endLayout);
        getAttendance=root.findViewById(R.id.getAttendanceButton);


        spinner=root.findViewById(R.id.spinnerViewAtt);

        String[] lists=new String[subjects.size()+1];
        lists[0]="Select Course";
        for(int i=0;i<lists.length-1;i++)
        {
            lists[i+1]=subjects.get(i).getCode()+" - "+subjects.get(i).getName();
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item,lists);
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


        //Date


        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTruitonDatePickerDialog(view);
            }
        });
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToDatePickerDialog(view);
            }
        });


        getAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code=subjects.get(position-1).getCode();
                String sDate=startDate.getText().toString();
                String eDate=endDate.getText().toString();
                Intent intent=new Intent(getActivity(), viewAttendanceCourseWise.class);
                intent.putExtra("code",code);
                intent.putExtra("Start",sDate);
                intent.putExtra("End",eDate);

                startActivity(intent);
            }
        });

        return root;

    }


    public void showTruitonDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getParentFragmentManager(), "datePicker");
    }

    public void showToDatePickerDialog(View v) {
        DialogFragment newFragment = new ToDatePickerFragment();
        newFragment.show(getParentFragmentManager(), "datePicker");
    }

    public static class DatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog;
            Log.d("Month", String.valueOf(month)) ;

            datePickerDialog = new DatePickerDialog(getActivity(),this, year,
                    month,day);
            return datePickerDialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            startDate.setText(day + "/" + (month+1)  + "/" + year);
        }

    }

    public static class ToDatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {
        // Calendar startDateCalendar=Calendar.getInstance();
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            String getfromdate = startDate.getText().toString().trim();
            String getfrom[] = getfromdate.split("/");
            int year,month,day;
            year= Integer.parseInt(getfrom[2]);
            month = Integer.parseInt(getfrom[1])-1;
            day = Integer.parseInt(getfrom[0]);
            final Calendar c = Calendar.getInstance();
            c.set(year,month,day);
            Log.d("Month", String.valueOf(month)) ;

            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),this, year,month,day);
            datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
            return datePickerDialog;
        }
        public void onDateSet(DatePicker view, int year, int month, int day) {

            endDate.setText(day + "/" + (month+1)  + "/" + year);
        }
    }

}