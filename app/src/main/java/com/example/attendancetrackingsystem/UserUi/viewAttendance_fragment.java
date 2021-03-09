package com.example.attendancetrackingsystem.UserUi;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
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

public class viewAttendance_fragment extends Fragment {

    public static EditText start;
    public static EditText end;
    Button UgetAttendance;

    Spinner Uspinner;
    int Uposition;
    static ArrayList<String> subjectUser;

    DatabaseReference databaseReference;

    ArrayList<String> arrayList=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

      View root=inflater.inflate(R.layout.user_view_attendance_fragment, container, false);

        start=root.findViewById(R.id.UstartDate);
        end=root.findViewById(R.id.UendDate);
        UgetAttendance=root.findViewById(R.id.UgetAttendanceButton);
        Uspinner=root.findViewById(R.id.UspinnerViewAtt);

        get(new MyCallBack() {
            @Override
            public void OnCallBack(ArrayList<String> list) {
                subjectUser = list;

                String[] lists = new String[subjectUser.size() + 1];
                lists[0] = "Select Course";
                for (int i = 0; i < lists.length - 1; i++) {
                    lists[i + 1] = subjectUser.get(i);

                }

                ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getContext(),
                        android.R.layout.simple_spinner_item, lists);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                dataAdapter.notifyDataSetChanged();
                Uspinner.setAdapter(dataAdapter);

            }
        });
        Uspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Uposition=i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTruitonDatePickerDialog(view);
            }
        });
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(start.getText().toString().length()>0)
                    showToDatePickerDialog(view);
                else
                    Toast.makeText(getContext(),"Enter start date",Toast.LENGTH_SHORT).show();
            }
        });


        UgetAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (end.getText().toString().length() > 0 && Uposition!=0 && start.getText().toString().length()>0) {
                    String code = subjectUser.get(Uposition - 1);

                    Log.d("Code",code);

                    String sDate = start.getText().toString();
                    String eDate = end.getText().toString();
                    Intent intent = new Intent(getActivity(),viewAttendance.class);
                    intent.putExtra("code", code);
                    intent.putExtra("Start", sDate);
                    intent.putExtra("End", eDate);

                   startActivity(intent);
                }
                else
                {
                    Toast.makeText(getContext(),"Please fill details",Toast.LENGTH_SHORT).show();
                }
            }

        });

        return root;

    }


    public void showTruitonDatePickerDialog(View v) {
        DialogFragment newFragment = new viewAttendance_fragment.DatePickerFragment();
        newFragment.show(getParentFragmentManager(), "datePicker");
    }

    public void showToDatePickerDialog(View v) {
        DialogFragment newFragment = new viewAttendance_fragment.ToDatePickerFragment();
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

            datePickerDialog = new DatePickerDialog(getActivity(),R.style.DialogTheme,this, year,
                    month,day);
            return datePickerDialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            start.setText(day + "/" + (month+1)  + "/" + year);
        }

    }

    public static class ToDatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {
        // Calendar startDateCalendar=Calendar.getInstance();
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            String getfromdate = start.getText().toString().trim();
            String getfrom[] = getfromdate.split("/");
            int year,month,day;
            year= Integer.parseInt(getfrom[2]);
            month = Integer.parseInt(getfrom[1])-1;
            day = Integer.parseInt(getfrom[0]);
            final Calendar c = Calendar.getInstance();
            c.set(year,month,day);
            Log.d("Month", String.valueOf(month)) ;

            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),R.style.DialogTheme,this, year,month,day);
            datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
            return datePickerDialog;
        }
        public void onDateSet(DatePicker view, int year, int month, int day) {

            end.setText(day + "/" + (month+1)  + "/" + year);
        }
    }




    public void get(MyCallBack callBack)
    {

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
                    if(dataSnapshot.child("userMail").getValue().toString().equalsIgnoreCase(email))
                    {
                        arrayList= (ArrayList<String>) dataSnapshot.child("subjects").getValue();

                    }
                }

                callBack.OnCallBack(arrayList);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Error",error.getMessage());
            }
        });

    }
    private interface MyCallBack{
        void OnCallBack(ArrayList<String> list);
    }
}

