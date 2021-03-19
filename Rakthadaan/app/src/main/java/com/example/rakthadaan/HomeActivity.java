package com.example.rakthadaan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sharedPreferences = getSharedPreferences("usertype",MODE_PRIVATE);
    }

    public void receiver(View view) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("type","receiver");
        editor.commit();
        startActivity(new Intent(HomeActivity.this, PhnAuth.class));
    }

    public void donar(View view) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("type","donar");
        editor.commit();
        startActivity(new Intent(HomeActivity.this,LoginActivity.class));
    }
}