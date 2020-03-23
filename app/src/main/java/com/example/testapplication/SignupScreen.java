package com.example.testapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class SignupScreen extends AppCompatActivity {
    private FirebaseAuth mAuth;
    public EditText email;
    public EditText password;
    public EditText passwordRe;
    private Button signup;
    private LinearLayout loading;
    private LinearLayout linearLayout;
    private TextView clickhere;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_screen);
        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.emailSignup);
        password = findViewById(R.id.passwordSignup);
        passwordRe = findViewById(R.id.passwordReSignup);
        signup = findViewById(R.id.signup);
        signup.setOnClickListener(emailVerify);
        clickhere = findViewById(R.id.gotologin);
        linearLayout=findViewById(R.id.backSignup);
        clickhere.setOnClickListener(login);
        loading = findViewById(R.id.loadingSignup);
        loading.setVisibility(View.GONE);
    }

    private boolean isValidEmailId(String email){

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }

    View.OnClickListener login = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(SignupScreen.this,LoginScreen.class);
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
                String TAG = " >>";

                String passwords = password.getText().toString();
                String passwordsRe = passwordRe.getText().toString();
                Log.d(TAG, "VALUE OF EMAIL IS " + emailAddress + passwords + passwordsRe );
                if(passwords.equals(passwordsRe)){

                    mAuth.createUserWithEmailAndPassword(emailAddress,passwords)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Toast.makeText(getApplicationContext(), "sign up successful", Toast.LENGTH_SHORT).show();
                                        loading.setVisibility(View.GONE);
                                        linearLayout.setAlpha(1);
                                        Intent intent = new Intent(SignupScreen.this,LoginScreen.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_SHORT).show();
                                        loading.setVisibility(View.GONE);
                                        linearLayout.setAlpha(1);
                                    }
                                    // ...
                                }
                            });
                }else {
                    Toast.makeText(getApplicationContext(), "Password Does not match", Toast.LENGTH_SHORT).show();
                    loading.setVisibility(View.GONE);
                    linearLayout.setAlpha(1);
                }

            }else{
                Toast.makeText(getApplicationContext(), "Invalid Email Address.", Toast.LENGTH_SHORT).show();
                loading.setVisibility(View.GONE);
                linearLayout.setAlpha(1);
            }

        }
    };
}
