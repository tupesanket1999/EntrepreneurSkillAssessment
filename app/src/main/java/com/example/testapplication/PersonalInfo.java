package com.example.testapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PersonalInfo extends AppCompatActivity {

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    Button button;
    TextView name , surname , clas , cast , dob , areaOfIntrest, familyIncom , annualIncome, estate ;
    RadioButton married,unmarried;
    boolean mri =false;
    boolean rur = false;
    RadioButton rural,urban;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
        button = findViewById(R.id.gto_quiz);
        name = findViewById(R.id.name);
        surname = findViewById(R.id.surname);
        clas = findViewById(R.id.classN);
        cast = findViewById(R.id.cast);
        dob = findViewById(R.id.dob);
        areaOfIntrest = findViewById(R.id.intrest);
        familyIncom = findViewById(R.id.income);
        annualIncome = findViewById(R.id.aincome);
        estate = findViewById(R.id.estate);
        married = findViewById(R.id.mr);
        unmarried = findViewById(R.id.umr);
        urban = findViewById(R.id.ur);
        rural = findViewById(R.id.rr);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(married.isChecked()){
                    mri = true;
                }
                if (rural.isChecked()){
                    rur = true;
                }

                User user1 = new User(name.getText().toString(),surname.getText().toString(),user.getEmail(),clas.getText().toString(),cast.getText().toString(),dob.getText().toString(),areaOfIntrest.getText().toString(),familyIncom.getText().toString(),annualIncome.getText().toString(),estate.getText().toString(),mri,rur);
                DatabaseReference myRef = database.getReference("Users");
                myRef.child(user.getUid()).child("info").setValue(user1);

                Intent intent = new Intent(PersonalInfo.this,Instructions.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
