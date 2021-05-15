package com.example.rakthadaan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.example.rakthadaan.fragments.AboutusFragment;
import com.example.rakthadaan.fragments.DashboardFragment;
import com.example.rakthadaan.fragments.FeedbackFragment;
import com.example.rakthadaan.fragments.LogoutFragment;
import com.example.rakthadaan.fragments.NearestbloodbanksFragment;
import com.example.rakthadaan.fragments.NearesthospitalsFragment;
import com.example.rakthadaan.fragments.RaisedrequestsFragment;
import com.example.rakthadaan.fragments.RequestsFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.UUID;

public class NavigationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    FirebaseAuth auth;
    FragmentManager manager;
    FragmentTransaction transaction;
    private AppBarConfiguration mAppBarConfiguration;
    SharedPreferences preferences;
    //dialog flow

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        if (savedInstanceState == null) {
            DashboardFragment fragment = new DashboardFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.nav_host_fragment, fragment).commit();
        }
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        auth = FirebaseAuth.getInstance();
        getSupportActionBar();
        preferences = getSharedPreferences("uid",MODE_PRIVATE);
        ActionBarDrawerToggle drawerToggle =
                new ActionBarDrawerToggle(this,
                        drawerLayout, toolbar,
                        0, 0);

        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        drawerToggle.getDrawerArrowDrawable()
                .setColor(getResources().getColor(android.R.color.white));

        navigationView.setNavigationItemSelectedListener(NavigationActivity.this);
        View headerview = navigationView.getHeaderView(0);
        TextView tv = headerview.findViewById(R.id.textView);
        tv.setText(auth.getCurrentUser().getEmail());
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NavigationActivity.this,ProfileActivity.class));
            }
        });


   /*     FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        String sid = preferences.getString("id",null);
        Menu menu = navigationView.getMenu();
        MenuItem target1 = menu.findItem(R.id.nav_requests);
        MenuItem target2 = menu.findItem(R.id.nav_raisedrequests);
        MenuItem target3 = menu.findItem(R.id.nav_nearestbloodbanks);
        MenuItem target4= menu.findItem(R.id.nav_nearesthospitals);
        if (sid==null){
            target1.setEnabled(false);
            target2.setEnabled(false);
            target3.setEnabled(false);
            target4.setEnabled(false);
        }
    }


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item){

            manager = getSupportFragmentManager();
            transaction = manager.beginTransaction();

            switch (item.getItemId()) {
                case R.id.nav_dashboard:
                    DashboardFragment dash = new DashboardFragment();
                    transaction.replace(R.id.nav_host_fragment, dash);
                    transaction.commit();
                    drawerLayout.closeDrawers();
                    break;

                case R.id.nav_requests:
                    RequestsFragment requests = new RequestsFragment();
                    transaction.replace(R.id.nav_host_fragment, requests);
                    transaction.commit();
                    drawerLayout.closeDrawers();
                    break;
                case R.id.nav_raisedrequests:
                    RaisedrequestsFragment raised = new RaisedrequestsFragment();
                    transaction.replace(R.id.nav_host_fragment, raised);
                    transaction.commit();
                    drawerLayout.closeDrawers();
                    break;
                case R.id.nav_nearestbloodbanks:
                    NearestbloodbanksFragment bloodbanks = new NearestbloodbanksFragment();
                    transaction.replace(R.id.nav_host_fragment, bloodbanks);
                    transaction.commit();
                    drawerLayout.closeDrawers();
                    break;
                case R.id.nav_nearesthospitals:
                    NearesthospitalsFragment hospitals = new NearesthospitalsFragment();
                    transaction.replace(R.id.nav_host_fragment, hospitals);
                    transaction.commit();
                    drawerLayout.closeDrawers();
                    break;
                case R.id.nav_aboutus:
                    AboutusFragment about = new AboutusFragment();
                    transaction.replace(R.id.nav_host_fragment, about);
                    transaction.commit();
                    drawerLayout.closeDrawers();
                    break;
                case R.id.nav_feedback:
                    FeedbackFragment feed = new FeedbackFragment();
                    transaction.replace(R.id.nav_host_fragment, feed);
                    transaction.commit();
                    drawerLayout.closeDrawers();
                    break;
                case R.id.nav_logout:
                    auth.signOut();
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
                    break;
            }
            return false;
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }


    public void gotoprofile(MenuItem item) {
        String sid = preferences.getString("id", null);
        if (sid == null) {
            Snackbar.make(getWindow().getDecorView(),"Please Complete your registration",Snackbar.LENGTH_LONG).setAction("Register", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                }
            }).show();
        } else {
            startActivity(new Intent(NavigationActivity.this, ProfileActivity.class));
        }
    }
}