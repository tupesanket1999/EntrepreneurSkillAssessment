package com.example.testapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ListQuestion extends AppCompatActivity {
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    LinearLayout horButton;
    Button end;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_question);
        end = findViewById(R.id.end);

        LinearLayout linearLayout = findViewById(R.id.listQuestion);

        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AnsStorage ansStorage = new AnsStorage(Answers.marks);
                DatabaseReference myRef = database.getReference("Users");
                myRef.child(user.getUid()).child("marks").setValue(ansStorage);


                Intent intent = new Intent(ListQuestion.this,Results.class);
                startActivity(intent);
            }
        });




        for (int i=0 ; i < 14 ; i++) {
            horButton = new LinearLayout(this);
            horButton.setOrientation(LinearLayout.HORIZONTAL);
            horButton.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            horButton.setGravity(Gravity.CENTER);
            for (int j = 0; j < 4; j++) {
                if(((j+1)+(4*i))<55){
                    Button btnTag = new Button(this);
                    btnTag.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    if(Answers.isSubmitted[(((j+1)+(4*i))-1)]){
                        btnTag.setBackgroundTintList(ContextCompat.getColorStateList(ListQuestion.this, R.color.colorPrimary));
                        btnTag.setTextColor(ContextCompat.getColorStateList(ListQuestion.this, R.color.white));
                    }
                    btnTag.setText("Q " + ((j+1)+(4*i)));
                    btnTag.setId(((j+1)+(4*i)));

                    final int finalI = i;
                    final int finalJ = j;
                    btnTag.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(ListQuestion.this,Quiz.class);
                            Quiz.count = ((finalJ +1)+(4* finalI))-1;
                            startActivity(intent);
                        }
                    });
                    horButton.addView(btnTag);
                }
            }
            linearLayout.addView(horButton);

        }


    }
}
