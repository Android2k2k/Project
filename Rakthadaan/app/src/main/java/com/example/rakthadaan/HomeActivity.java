package com.example.rakthadaan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void receiver(View view) {
        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
    }

    public void donar(View view) {
        startActivity(new Intent(HomeActivity.this,LoginActivity.class));
    }
}