package com.nabha.EntrepreneurSkillAssessment;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class PersonalInfo extends AppCompatActivity {

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    Button button;
    TextView name , surname , clas , caste , dob , areaOfIntrest, familyIncom , annualIncome, estate,dobH , phone;
    TextInputLayout nameH, surnameH,clasH,areaOfIntrestH, familyIncomH , annualIncomeH, estateH, phoneH;
    RadioButton married,unmarried;
    RadioGroup mgroup,ugroup;
    LinearLayout uhint,mhint;
    Spinner spinner;
    boolean mri =false;
    boolean rur = false;
    RadioButton rural,urban;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
        button = findViewById(R.id.gto_quiz);
        final Calendar myCalendar = Calendar.getInstance();

        spinner = findViewById(R.id.spinner);
        name = findViewById(R.id.name);
        surname = findViewById(R.id.surname);
        clas = findViewById(R.id.classN);
        dob = findViewById(R.id.dob);
        areaOfIntrest = findViewById(R.id.intrest);
        familyIncom = findViewById(R.id.income);
        annualIncome = findViewById(R.id.aincome);
        estate = findViewById(R.id.estate);
        married = findViewById(R.id.mr);
        unmarried = findViewById(R.id.umr);
        urban = findViewById(R.id.ur);
        rural = findViewById(R.id.rr);
        mgroup = findViewById(R.id.mgroup);
        ugroup = findViewById(R.id.ugroup);
        phone = findViewById(R.id.phone);
        phoneH=findViewById(R.id.phoneHint);


        nameH = findViewById(R.id.nameHint);
        surnameH = findViewById(R.id.surnameHint);
        clasH =findViewById(R.id.classNHint);
        areaOfIntrestH = findViewById(R.id.intrestHint);
        familyIncomH = findViewById(R.id.incomeHint);
        annualIncomeH = findViewById(R.id.aincomeHint);
        estateH = findViewById(R.id.estateHint);
        dobH=findViewById(R.id.dobH);
        mhint=findViewById(R.id.mLayout);
        uhint = findViewById(R.id.uLayout);



        DatabaseReference myRef = database.getReference("Users").child(user.getUid()).child("info");



        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    User userView = dataSnapshot.getValue(User.class);
                    name.setText(userView.Name);
                    surname.setText(userView.Surname);
                    clas.setText(userView.Class);
                    phone.setText(userView.Phone);


                    String[] castes = {"OPEN", "OBC","SBC" ,"SEBC","SC", "ST" , "VJ/NT", "other"};

                    for(int i=0; i < castes.length ; i++){
                        if(castes[i].equals(userView.Caste)){
                            spinner.setSelection(i);
                        }
                    }

                    dob.setText(userView.Dob);
                    areaOfIntrest.setText(userView.AnnualIncom);
                    familyIncom.setText(userView.FamilyIncom);
                    annualIncome.setText(userView.AnnualIncom);
                    estate.setText(userView.Estate);

                    if (userView.MaritalStatus=="Married"){
                        married.setChecked(true);
                    }else {
                        unmarried.setChecked(true);
                    }

                    if (userView.Area=="Rural"){
                        rural.setChecked(true);
                    }else {
                        urban.setChecked(true);
                    }
                }
                }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                dob.setText(dayOfMonth+"/"+(month+1)+"/"+year);
            }

        };

        dob.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(PersonalInfo.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onClick(View v) {
                if(!(name.getText().toString().isEmpty() || phone.getText().toString().isEmpty() || surname.getText().toString().isEmpty() || user.getEmail().isEmpty() || clas.getText().toString().isEmpty() || spinner.getSelectedItem().toString().isEmpty() || dob.getText().toString().isEmpty() || areaOfIntrest.getText().toString().isEmpty() || familyIncom.getText().toString().isEmpty() || annualIncome.getText().toString().isEmpty() || estate.getText().toString().isEmpty() || (mgroup.getCheckedRadioButtonId() == -1) ||  (ugroup.getCheckedRadioButtonId() == -1))){



                    String mstatus;
                    String astatus;

                    if(married.isChecked()){
                        mstatus="Married";
                    }else {
                        mstatus="Single";
                    }
                    if (rural.isChecked()){
                        astatus="Rural";
                    }else {
                        astatus="Urban";
                    }

                    User user1 = new User(name.getText().toString(),surname.getText().toString(),user.getEmail(),clas.getText().toString(),spinner.getSelectedItem().toString(),dob.getText().toString(),areaOfIntrest.getText().toString(),familyIncom.getText().toString(),annualIncome.getText().toString(),estate.getText().toString(),mstatus,astatus,phone.getText().toString());
                    DatabaseReference myRef = database.getReference("Users");
                    myRef.child(user.getUid()).child("info").setValue(user1);
                    myRef.child(user.getUid()).child("UUID").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    Toast.makeText(getApplicationContext(), "registered successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PersonalInfo.this,Dashboard.class);
                    startActivity(intent);
                    finish();

                }else{
                    Toast.makeText(getApplicationContext(), "all fields are mandatory.", Toast.LENGTH_SHORT).show();

                    if(name.getText().toString().isEmpty()){
                        nameH.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));

                    }else {
                        nameH.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
                    }
                    if(surname.getText().toString().isEmpty()){
                        surnameH.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                    }else {
                        surnameH.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
                    }
                    if(clas.getText().toString().isEmpty()) {
                        clasH.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                    }else {
                        clasH.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
                    }
                    if(areaOfIntrest.getText().toString().isEmpty()){
                        areaOfIntrestH.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                    }else {
                        areaOfIntrestH.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
                    }
                    if(familyIncom.getText().toString().isEmpty()){
                        familyIncomH.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                    }else {
                        familyIncomH.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
                    }
                    if(annualIncome.getText().toString().isEmpty()){
                        annualIncomeH.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                    }else {
                        annualIncomeH.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
                    }
                    if(estate.getText().toString().isEmpty()){
                        estateH.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                    }else {
                        estateH.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
                    }

                    if(phone.getText().toString().isEmpty()){
                        phoneH.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                    }else {
                        estateH.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
                    }


                    if(dob.getText().toString().isEmpty()){
                        dobH.setTextColor(getResources().getColor(R.color.red));
                    }else {
                        dobH.setTextColor(getResources().getColor(R.color.colorAccent));
                    }
                    if(mgroup.getCheckedRadioButtonId() == -1){
                        mhint.setBackground(getResources().getDrawable(R.drawable.border_for_radio_un));
                    }else {
                        mhint.setBackground(getResources().getDrawable(R.drawable.border_for_radio));
                    }
                    if(ugroup.getCheckedRadioButtonId() == -1){
                        uhint.setBackground(getResources().getDrawable(R.drawable.border_for_radio_un));
                    }else {
                        uhint.setBackground(getResources().getDrawable(R.drawable.border_for_radio));
                    }

                }

            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(PersonalInfo.this,Dashboard.class);
        startActivity(intent);
        finish();
    }
}
