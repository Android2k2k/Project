package com.example.rakthadaan;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.DatePicker;
import android.widget.EditText;

import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import com.example.rakthadaan.databinding.ActivityRegisterBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class RegisterActivity extends AppCompatActivity {
    ActivityRegisterBinding binding;
    FusedLocationProviderClient fusedLocationProviderClient;
   // TextView tv;
    String locdata;
    DatabaseReference databaseReference;
    Random random = new Random();
    Calendar calendar;
    String date;
    RadioGroup rg;
    RadioButton rb;
    EditText etDate;
    Spinner spinner;
    FirebaseAuth auth;
    EditText pin;
    String ages;
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_register);
        databaseReference = FirebaseDatabase.getInstance().getReference("Profile");
        fusedLocationProviderClient = LocationServices
                .getFusedLocationProviderClient(this);
        rg = findViewById(R.id.rg1);
        spinner = findViewById(R.id.spinner1);

        auth = FirebaseAuth.getInstance();
      //  tv = findViewById(R.id.text);
        pin = findViewById(R.id.pin);
        preferences = getSharedPreferences("uid",MODE_PRIVATE);
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

        binding.date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        RegisterActivity.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month =month+1;
                        date = day+"/"+month+"/"+year;/**/
                        getAge(year, month, day);

                        binding.date.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });
    }
    private String getAge(int year, int month, int day){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        Integer ageInt = new Integer(age);
         ages = ageInt.toString();

        return ages;
    }

    public void getdevicelocation(View view) {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.
                PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(RegisterActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
        }else {
            fusedLocationProviderClient.getLastLocation()
                    .addOnCompleteListener(new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            Location loc = task.getResult();
                            Geocoder geocoder = new Geocoder(RegisterActivity.this,
                                    Locale.getDefault());
                            try {
                                List<Address> addresses = geocoder.getFromLocation(loc
                                        .getLatitude(), loc.getLongitude(), 1);
                                String latitude = String.valueOf(addresses.get(0).getLatitude());
                                String longitude = String.valueOf(addresses.get(0).getLongitude());
                                String countryname = addresses.get(0).getCountryName();
                                String locality = addresses.get(0).getLocality();
                                String addressline = addresses.get(0).getAddressLine(0);
                                String postalcode = addresses.get(0).getPostalCode();
                                locdata = latitude + "\n" + longitude + " \n" + countryname + "\n"
                                        + locality + "\n" + addressline;
                                pin.setText(postalcode);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        }
    }

    public void register(View view) {
        final String umail = binding.email.getText().toString();
        final String upass = binding.pass.getText().toString();
        final String fname = binding.fname.getText().toString();
        final String lname = binding.lname.getText().toString();
        final String mobile = binding.mobile.getText().toString();
        final String bloodgroup = binding.spinner1.getSelectedItem().toString();
        final String image = "https://www.vhv.rs/dpng/d/433-4336634_thumb-image-android-user-icon-png-transparent-png.png";
        final String address = locdata;
        final String pin = binding.pin.getText().toString();
        final String rating="0";
        final String comments = "No Comments";
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
                        String gender = rb.getText().toString();
                        String uid = String.valueOf(calendar.get(Calendar.YEAR)).substring(2)+fname.charAt(0)+fname.charAt(fname.length()-1)+random.nextInt(100000);
                        MyModel myModel = new MyModel(fname,lname,umail,mobile,ages,date,gender,bloodgroup,image,address,pin,comments,uid,rating);
                        //year+fnamefirstletterand lastletter+random(5digits)
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("id",uid);
                        editor.commit();
                        databaseReference.child(uid).setValue(myModel);

                        Toast.makeText(RegisterActivity.this, "Successfull", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, NavigationActivity.class));
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