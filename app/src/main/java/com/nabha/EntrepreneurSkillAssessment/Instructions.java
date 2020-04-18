package com.nabha.EntrepreneurSkillAssessment;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.nabha.EntrepreneurSkillAssessment.R;

public class Instructions extends AppCompatActivity {
    @Override
    public void onBackPressed() {
        Intent intent=new Intent(Instructions.this,Dashboard.class);
        startActivity(intent);
        finish();
    }
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);

        button = findViewById(R.id.start_quiz);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Instructions.this,Quiz.class);
                startActivity(intent);
                Answers.marks = new int[54];
                Answers.isSubmitted= new boolean[54];
                Quiz.count=0;
                finish();
            }
        });
    }
}
