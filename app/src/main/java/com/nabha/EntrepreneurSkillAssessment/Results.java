package com.nabha.EntrepreneurSkillAssessment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Results extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);


        Thread thread = new Thread(){
            @Override
            public void run(){
                try {
                    sleep(4000);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        thread.start();

    }

}