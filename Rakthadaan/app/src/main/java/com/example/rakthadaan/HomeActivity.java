package com.example.rakthadaan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rakthadaan.fragments.NearestbloodbanksFragment;

public class HomeActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sharedPreferences = getSharedPreferences("usertype",MODE_PRIVATE);
//        setTitle("blood banks");
//        getActionBar().setIcon(R.drawable.ic_baseline_local_hospital_24);
//        getActionBar().setHomeButtonEnabled(true);
//        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu,menu);
        return super.onCreateOptionsMenu(menu);
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

    public void bloodbanks(MenuItem item) {


        startActivity(new Intent(HomeActivity.this,RegisterBloodBank.class));

    }
}