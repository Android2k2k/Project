package com.example.rakthadaan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rakthadaan.fragments.LogoutFragment;

public class Splash extends AppCompatActivity {
    SharedPreferences preferences,preferences1,preferences2;
    ImageView ivTop,ivLogo,ivBottom;
    TextView textView;
    CharSequence charSequence;
    int index;
    long delay = 2000;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //fragmentTransaction.replace(R.id.frameLayout, new LogoutFragment()).commit();
        preferences=getSharedPreferences("dailog",MODE_PRIVATE);
        preferences1=getSharedPreferences("usertype",MODE_PRIVATE);
        preferences2=getSharedPreferences("Data",MODE_PRIVATE);
        String dailog = preferences.getString("dlg",null);
        String usertype = preferences1.getString("type",null);
        String profile = preferences2.getString("profile",null);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (dailog==null){
                    startActivity(new Intent(Splash.this, Slider.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    finish();
                }
                else if (dailog.equals("1")){
                    if(usertype == null) {
                        startActivity(new Intent(Splash.this,HomeActivity.class));
                        finish();
                    }
                    else if(usertype.equals("receiver")) {
                        startActivity(new Intent(Splash.this,PhnAuth.class));
                        finish();
                    }
                    else if(usertype.equals("donar")) {
                        startActivity(new Intent(Splash.this,LoginActivity.class));
                        finish();
                    }
                    else if(profile == null) {
                        startActivity(new Intent(Splash.this,ProfileActivity.class));
                        finish();
                    }
                    else if(profile.equals("1")) {
                        startActivity(new Intent(Splash.this,NavigationActivity.class));
                        finish();
                    }
                    else {
                        startActivity(new Intent(Splash.this, HomeActivity.class));
                    }
                }
            }
        }, 3000);

        ivLogo = findViewById(R.id.iv_logo);
        ivBottom= findViewById(R.id.iv_bottom);
        textView = findViewById(R.id.text_view);

        //set fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                ,WindowManager.LayoutParams.FLAG_FULLSCREEN)  ;

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