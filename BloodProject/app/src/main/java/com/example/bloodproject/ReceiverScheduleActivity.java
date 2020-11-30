package com.example.bloodproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ReceiverScheduleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver_schedule);
    }

    public void urgent(View view) {
        startActivity(new Intent(ReceiverScheduleActivity.this, PhoneAuthActivity.class));
    }

    public void scheduled(View view) {
    }
}