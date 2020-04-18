package com.nabha.EntrepreneurSkillAssessment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Dashboard extends AppCompatActivity {
    private LinearLayout loading,linearLayout;
    Button start,edit,contact,logout;
    TextView hi ;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want exit the app? ");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        builder.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        loading = findViewById(R.id.loading1);
        linearLayout = findViewById(R.id.dash);
        linearLayout.setAlpha(.8f);


        contact = findViewById(R.id.contact);
        start = findViewById(R.id.start);
        edit = findViewById(R.id.edit);
        logout = findViewById(R.id.logout);
        hi = findViewById(R.id.hi);


        DatabaseReference myRef = database.getReference("Users").child(user.getUid()).child("info");

        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    User userView = dataSnapshot.getValue(User.class);
                    hi.setText("Hi " + userView.Name + ",\nWelcome to the NABHA Entrepreneurial skill assessment scale ");
                    loading.setVisibility(View.GONE);
                    linearLayout.setAlpha(1);
                }else {
                    Intent intent = new Intent(Dashboard.this,PersonalInfo.class);
                    startActivity(intent);
                    finish();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference myRef = database.getReference("Users").child(user.getUid()).child("tests");
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){

                            AlertDialog.Builder builder = new AlertDialog.Builder(Dashboard.this);
                            builder.setMessage("Your test is submitted");

                            builder.setNegativeButton("close", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                }
                            });
                            builder.show();





                        }else{
                            Intent intent = new Intent(Dashboard.this,Instructions.class);
                            startActivity(intent);
                            finish();
                        }}


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this,PersonalInfo.class);
                startActivity(intent);
                finish();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(Dashboard.this,LoginScreen.class);
                startActivity(intent);
                finish();
            }
        });

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{"navnathtupe@unipune.ac.in"});

                email.putExtra(Intent.EXTRA_SUBJECT, "regarding entrepreneurial skill assessment scale application ");
                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, "Choose an Email client :"));
            }
        });

    }
}
