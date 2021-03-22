package com.example.bloodproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bloodproject.fragments.LogoutFragment;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    ImageView ivTop,ivLogo,ivBottom;
    TextView textView;
    CharSequence charSequence;
    int index;
    long delay = 2000;
    Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, new LogoutFragment()).commit();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this, HomeActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        }, 4000);
        ivLogo = findViewById(R.id.iv_logo);
        ivBottom= findViewById(R.id.iv_bottom);
        textView = findViewById(R.id.text_view);

        //set fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                ,WindowManager.LayoutParams.FLAG_FULLSCREEN)  ;
        //initialize top animation

        //start top animation
        //initialize object animator
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(
                ivLogo,
                PropertyValuesHolder.ofFloat("scaleX",1.2f),
                PropertyValuesHolder.ofFloat("scaleY",1.2f)
        );
        //set duration
        objectAnimator.setDuration(500);
        //set repeat count
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        //set repeat mode
        objectAnimator.setRepeatCount(ValueAnimator.REVERSE);
        //start animator
        objectAnimator.start();

        animateText("Rakthadaan");

        Animation animation2 = AnimationUtils.loadAnimation(this,
                R.anim.bottom_wave);
        ivBottom.setAnimation(animation2);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            textView.setText(charSequence.subSequence(0,index++));
            if(index<=charSequence.length()){
                handler.postDelayed(runnable,delay);
            }
        }
    };
    public void animateText(CharSequence cs){
        charSequence = cs;
        index = 0;
        textView.setText("Rakthadaan");
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable,delay);
    }

}