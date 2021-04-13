package com.example.rakthadaan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rakthadaan.databinding.ActivityRegisterBinding;
import com.example.rakthadaan.databinding.ActivityRegisterBloodBankBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class RegisterBloodBank extends AppCompatActivity {
    ActivityRegisterBloodBankBinding bloodBankBinding;
   // TextView tv;
    EditText pin;
    Button submit;
    DatabaseReference databaseReference;
    FusedLocationProviderClient fusedLocationProviderClient;
    FirebaseAuth auth;
    String bbloc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bloodBankBinding = DataBindingUtil.setContentView(this,R.layout.activity_register_blood_bank);
        databaseReference = FirebaseDatabase.getInstance().getReference("BloodBank");
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        auth = FirebaseAuth.getInstance();
        submit =  findViewById(R.id.button);
        pin = findViewById(R.id.bpin);
        //tv = findViewById(R.id.tv);
    }

    public void gdl(View view) {
        if (ActivityCompat.checkSelfPermission(RegisterBloodBank.this,
                Manifest.permission.ACCESS_FINE_LOCATION)==
                PackageManager.PERMISSION_GRANTED){
            getLocation();
        }else {
            ActivityCompat.requestPermissions(RegisterBloodBank.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
        }
    }
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.
                PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation()
                .addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location loc= task.getResult();
                        Geocoder geocoder=new Geocoder(RegisterBloodBank.this,
                                Locale.getDefault());
                        try {
                            List<Address> addresses = geocoder.getFromLocation(loc
                                    .getLatitude(),loc.getLongitude(),1);
                            String latitude = String.valueOf(addresses.get(0).getLatitude());
                            String longitude = String.valueOf(addresses.get(0).getLongitude());
                            String countryname = addresses.get(0).getCountryName();
                            String locality = addresses.get(0).getLocality();
                            String addressline = addresses.get(0).getAddressLine(0);
                            String postalcode = addresses.get(0).getPostalCode();
                           bbloc = (latitude+"\n"+longitude+" \n"+countryname+"\n"
                                    +locality+"\n"+addressline);
                            pin.setText(postalcode);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public void submit(View view) {
        final String bbname = bloodBankBinding.bname.getText().toString();
        final String bbpname = bloodBankBinding.bpname.getText().toString();
        final String bbmobile = bloodBankBinding.bpmobile.getText().toString();
        final String baddress = bbloc;
        final String bbpin = bloodBankBinding.bpin.getText().toString();
        final String bbpwd = bloodBankBinding.bpwd.getText().toString();
        final String bbemail = bloodBankBinding.bpemail.getText().toString();
        if(bbemail.isEmpty() | bbpwd.isEmpty()) {
            Toast.makeText(this, "Fill all the details", Toast.LENGTH_SHORT).show();
        }
        else if(bbpwd.length() < 6) {
            Toast.makeText(this, "Password must be 6 digits", Toast.LENGTH_SHORT).show();
        }
        auth.createUserWithEmailAndPassword(bbemail,bbpwd).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    MyModel2 myModel2 = new MyModel2(bbname,bbpname,bbmobile,baddress,bbpin,bbpwd);
                    //year+fnamefirstletterand lastletter+random(5digits)
                    //String uid = String.valueOf(bbmobile);
                    String uid = bbname.charAt(0) + bbname.charAt(bbname.length() - 1) + bbpin;
                    databaseReference.child(uid).setValue(myModel2);
                    Toast.makeText(RegisterBloodBank.this, "Successfull", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterBloodBank.this, NavigationActivity.class));
                    finish();
                }
                else{
                    Toast.makeText(RegisterBloodBank.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}