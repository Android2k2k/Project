package com.example.bloodproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    public void receiver(View view) {
        startActivity(new Intent(HomeActivity.this, ReceiverScheduleActivity.class));
        Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
    }

    public void donar(View view) {
        startActivity(new Intent(HomeActivity.this,LoginActivity.class));
    }
}