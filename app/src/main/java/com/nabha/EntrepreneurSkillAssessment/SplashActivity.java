package com.nabha.EntrepreneurSkillAssessment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {
    RelativeLayout relativeLayout ;
    private FirebaseAuth mAuth;
    private ImageView img;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY);
        setContentView(R.layout.activity_splash);

        img = findViewById(R.id.logo);
        relativeLayout = findViewById(R.id.lay);

        int currentNightMode = getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK;
        switch (currentNightMode) {
            case Configuration.UI_MODE_NIGHT_YES:
                img.setColorFilter(getResources().getColor(R.color.logo_dark));
                // Night mode is active, we're at night!
            case Configuration.UI_MODE_NIGHT_UNDEFINED:
                img.setColorFilter(getResources().getColor(R.color.logo_dark));
                // We don't know what mode we're in, assume notnight
        }


        Animation animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
        relativeLayout.startAnimation(animFadeIn);
        thread.start();
    }


        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(4000);
                    mAuth = FirebaseAuth.getInstance();
                    FirebaseUser currentUser = mAuth.getCurrentUser();

                    if (currentUser != null) {
                        Intent intent = new Intent(SplashActivity.this, Dashboard.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent homeIntent = new Intent(SplashActivity.this, LoginScreen.class);
                        startActivity(homeIntent);
                        finish();
                    }


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
}


