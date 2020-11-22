package com.example.bloodproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class Display extends AppCompatActivity {
    ImageView iv;
    TextView tv,tv1,tv2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        iv = findViewById(R.id.iv);
        tv = findViewById(R.id.tv1);
        tv1 = findViewById(R.id.tv2);
        tv2 = findViewById(R.id.tv3);
        tv.setText(getIntent().getStringExtra("fname"));
        tv1.setText(getIntent().getStringExtra("fcost"));
        tv2.setText(getIntent().getStringExtra("ftype"));
        Glide.with(this).load(getIntent().getStringExtra("ilink"))
                .placeholder(R.drawable.ic_launcher_background)
                .into(iv);
    }
}
