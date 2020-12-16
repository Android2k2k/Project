package com.example.rakthadaan;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.rakthadaan.databinding.ActivityRegisterBinding;
import com.example.rakthadaan.fragments.ProfileFragment;
import com.example.rakthadaan.fragments.RequestsFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.UUID;

public class RegisterActivity extends AppCompatActivity {
    ActivityRegisterBinding binding;
    DatabaseReference databaseReference;
//    StorageReference sRef;
//    Uri uri;
//    ProgressDialog dialog;
    SharedPreferences preferences;
    Random random = new Random();
    Calendar calendar;
    String date;
    RadioGroup rg;
    RadioButton rb;
    EditText etFname,etLname,etEmail,etMobile,etAge,etDate;
    EditText etPass;
    LinearLayout linearLayout;
    Spinner spinner;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_register);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_register);
        preferences = getSharedPreferences("Data",MODE_PRIVATE);
//        sRef = FirebaseStorage.getInstance().getReference().child(UUID.randomUUID().toString());
        databaseReference = FirebaseDatabase.getInstance().getReference("Profile");
//        dialog = new ProgressDialog(this);
//        dialog.setMessage("Uploading.....");
//        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        binding.
        etFname = findViewById(R.id.fname);
        etLname = findViewById(R.id.lname);
        etEmail = findViewById(R.id.email);
        etMobile = findViewById(R.id.mobile);
        etAge = findViewById(R.id.age);
        etPass = findViewById(R.id.pass);
        etDate = findViewById(R.id.date);

        rg = findViewById(R.id.rg1);
        rb = findViewById(R.id.rb1);
        linearLayout = findViewById(R.id.linear1);
        spinner = findViewById(R.id.spinner1);
        auth = FirebaseAuth.getInstance();

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
                Toast.makeText(RegisterActivity.this, " ", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //DateOfBirth
        calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        RegisterActivity.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month =month+1;
                        date = day+"/"+month+"/"+year;/**/
                        etDate.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();

            }
        });
    }

    public void register(View view) {
        final String umail = binding.email.getText().toString();
        final String upass = binding.pass.getText().toString();
        final String fname = binding.fname.getText().toString();
        final String lname = binding.lname.getText().toString();
        final String mobile = binding.mobile.getText().toString();
        final String bloodgroup = binding.spinner1.getSelectedItem().toString();
        final String age = binding.age.getText().toString();
        final String image = "https://www.vhv.rs/dpng/d/433-4336634_thumb-image-android-user-icon-png-transparent-png.png";
        final String address = "no address added";
        final int rating=0;
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
                        int id = rg.getCheckedRadioButtonId();
                        rb = findViewById(id);
                        String gender;
                        if (rb.getText().toString() == "Male") {
                            gender = binding.rb1.getText().toString();
                        }
                        else {
                            gender = binding.rb2.getText().toString();
                        }
                        MyModel myModel = new MyModel(fname,lname,umail,mobile,age,date,gender,bloodgroup,image,address,rating);
                        //year+fnamefirstletterand lastletter+random(5digits)
                        String uid = String.valueOf(calendar.get(Calendar.YEAR)).substring(2)+fname.charAt(0)+fname.charAt(fname.length()-1)+random.nextInt(5);
                        databaseReference.child(uid).setValue(myModel);
                        Toast.makeText(RegisterActivity.this, "Successfull", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
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