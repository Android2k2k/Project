package com.example.bloodproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {
    RadioGroup radiog;
    RadioButton radiob1,radiob2;
    EditText etFname,etLname,etEmail,etMobile,etAge,etDate;
    EditText etPass;
    LinearLayout linearLayout;
    Spinner spinner;
    FirebaseAuth auth;
    EditText date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etFname = findViewById(R.id.fname);
        etLname = findViewById(R.id.lname);
        etEmail = findViewById(R.id.email);
        etMobile = findViewById(R.id.mobile);
        etAge = findViewById(R.id.age);
        etPass = findViewById(R.id.pass);
        etDate = findViewById(R.id.date);

        radiog = findViewById(R.id.rg1);
        radiob1 = findViewById(R.id.rb1);
        radiob2 = findViewById(R.id.rb2);
        linearLayout = findViewById(R.id.linear1);
        spinner = findViewById(R.id.spinner1);
        auth = FirebaseAuth.getInstance();
        DatePickerDialog datePickerDialog;

        //Spinner for Blood Group
        final ArrayList<String> type = new ArrayList<>();
        type.add("A+");
        type.add("O+");
        type.add("B+");
        type.add("AB+");
        type.add("A-");
        type.add("O-");
        type.add("B-");
        type.add("AB-");

        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,type);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(RegisterActivity.this, "You Selected"+type.get(i), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //DateOfBirth
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month =month+1;
                        String date = day+"/"+month+"/"+year;
                        etDate.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();

            }
        });

        //Gender
        radiog.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.rb1:
                        Toast.makeText(RegisterActivity.this, "Male", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.rb2:
                        Toast.makeText(RegisterActivity.this, "Female", Toast.LENGTH_SHORT).show();
                        break;
                }

            }
        });
    }

    public void register(View view) {
        String umail = etEmail.getText().toString();
        String upass = etPass.getText().toString();
        if(umail.isEmpty() | upass.isEmpty()){
            Toast.makeText(this, "Fill all the details", Toast.LENGTH_SHORT).show();
        }
        else if(upass.length() < 6){
            Toast.makeText(this, "Password must be 6 digits", Toast.LENGTH_SHORT).show();
        }
        else{
            auth.createUserWithEmailAndPassword(umail,upass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(RegisterActivity.this, "Successfull", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this,NavigationActivity.class));
                        finish();
                    }
                    else{
                        Toast.makeText(RegisterActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

}