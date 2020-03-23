package com.example.testapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class LoginScreen extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText email;
    private Button button;
    private EditText password;
    private LinearLayout loading;
    private LinearLayout linearLayout;
    private Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        loading = findViewById(R.id.loading);

        loading.setVisibility(View.GONE);

        mAuth = FirebaseAuth.getInstance();

        linearLayout = findViewById(R.id.back);

        email =findViewById(R.id.email);
        password=findViewById(R.id.password);
        button =findViewById(R.id.login);
        button.setOnClickListener(emailVerify);
        signup = findViewById(R.id.gotosignup);
        signup.setOnClickListener(startAct);


    }


    private boolean isValidEmailId(String email){

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }

    View.OnClickListener startAct = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(LoginScreen.this,SignupScreen.class);
            startActivity(intent);
            finish();
        }
    };

    View.OnClickListener emailVerify = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            loading.setVisibility(View.VISIBLE);
            linearLayout.setAlpha(.8f);

            if(isValidEmailId(email.getText().toString().trim())){

                String emailAddress = email.getText().toString();
                String passwords = password.getText().toString();

                mAuth.signInWithEmailAndPassword(emailAddress,passwords)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "logged IN", Toast.LENGTH_SHORT).show();
                                    loading.setVisibility(View.GONE);
                                    linearLayout.setAlpha(1);
                                    Intent intent = new Intent(LoginScreen.this,PersonalInfo.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_SHORT).show();
                                    loading.setVisibility(View.GONE);
                                    linearLayout.setAlpha(1);
                                }
                            }
                        });

            }else{
                Toast.makeText(getApplicationContext(), "Invalid Email Address.", Toast.LENGTH_SHORT).show();
                loading.setVisibility(View.GONE);
                linearLayout.setAlpha(1);
            }
        }
    };
}


