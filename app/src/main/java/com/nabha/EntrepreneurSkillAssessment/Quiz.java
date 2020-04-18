package com.nabha.EntrepreneurSkillAssessment;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextSwitcher;
import android.widget.TextView;

public class Quiz extends AppCompatActivity {

    private RadioButton [] radioButtons = new RadioButton[10];
    private TextView textView;
    private TextSwitcher questions;
    private TextSwitcher questionsM;
    Button submit,next,prev, fin;
    static int count=0;
    ProgressBar progressBar ;
    private int maxProgress = 54;
    private int progressStatus = 0;
    private Handler handler = new Handler();
    final Question question12 = new Question();

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want exit the test? ");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent=new Intent(Quiz.this,Dashboard.class);
                startActivity(intent);
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
        setContentView(R.layout.activity_quiz);





        findViews();
        setProgress();
        createRadio();
        setButtons();
        setQues();




        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animNext();
                count++;
                setQues();
                setRadio();
                setProgress();
                setButtons();
                }

        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAns();
                if(count<53){
                    animNext();
                    count++;
                    setQues();
                    setRadio();
                    setProgress();
                    setButtons();
                }else {
                    setButtons();
                    setTicks();
                    submit.setEnabled(false);
                    Intent intent = new Intent(Quiz.this,ListQuestion.class);
                    startActivity(intent);
                    finish();
                }


                }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animPrev();
                count--;
                setQues();
                setRadio();
                setProgress();
                setButtons();

            }
        });





        fin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Quiz.this, ListQuestion.class);
                startActivity(intent);
                finish();
            }
        });

    }




    public boolean isTicked(){
        boolean flag = false;
        for(int i=0 ; i < 10 ; i++){

            if(radioButtons[i].isChecked()){
                flag=true;
            }
        }
        return  flag;
    }

    public void setTicks(){
        if(Answers.isSubmitted[count]){
            radioButtons[Answers.marks[count]-1].setChecked(true);
        }
    }

    public int countChecked(){
        int check = 0;
        for (int i=0; i < 54 ; i++){
            if(Answers.isSubmitted[i]){
                check++;
            }
        }
        return check;
    }

    public void createRadio(){
        for(int i=0; i < 10 ; i++){
            final int finalI = i;
            radioButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    submit.setEnabled(true);
                    for (int j=0; j < 10 ; j++){
                        if(j== finalI){
                            radioButtons[j].setChecked(true);
                        }else {
                            radioButtons[j].setChecked(false);
                        }
                    }

                }
            });
        }
    }

    public void setRadio(){
        for (int i=0; i < 10 ; i++){
            radioButtons[i].setChecked(false);
        }
    }

    public void setButtons(){
        for (int i=0; i < 10 ; i++){
            if(radioButtons[i].isChecked()){
                submit.setEnabled(true);
            }else {
                submit.setEnabled(false);
            }
        }
        if(count<1){
            prev.setEnabled(false);
        }else {
            prev.setEnabled(true);
        }
        if (count>52){
            next.setEnabled(false);
        }else {
            next.setEnabled(true);
        }
        if (isTicked()){
            submit.setEnabled(true);
        }else {
            submit.setEnabled(false);
        }
    }

    public void setProgress(){
        textView.setText("("+countChecked()+"/"+"54)");
        if(countChecked()>0){
            progressBar.setProgress(countChecked());
            setTicks();
        }
    }

    public void animNext(){
        questions.setOutAnimation(Quiz.this, R.anim.slide_out_left);
        questionsM.setOutAnimation(Quiz.this, R.anim.slide_out_left);
        questions.setInAnimation(Quiz.this, R.anim.slide_in_right);
        questionsM.setInAnimation(Quiz.this, R.anim.slide_in_right);

    }

    public void animPrev(){
        questions.setOutAnimation(Quiz.this, android.R.anim.slide_out_right);
        questionsM.setOutAnimation(Quiz.this, android.R.anim.slide_out_right);
        questions.setInAnimation(Quiz.this, android.R.anim.slide_in_left);
        questionsM.setInAnimation(Quiz.this, android.R.anim.slide_in_left);
    }

    public void findViews (){
        progressBar = findViewById(R.id.prog);
        progressBar.setMax(54);
        radioButtons[0]=findViewById(R.id.r1);
        radioButtons[1]=findViewById(R.id.r2);
        radioButtons[2]=findViewById(R.id.r3);
        radioButtons[3]=findViewById(R.id.r4);
        radioButtons[4]=findViewById(R.id.r5);
        radioButtons[5]=findViewById(R.id.r6);
        radioButtons[6]=findViewById(R.id.r7);
        radioButtons[7]=findViewById(R.id.r8);
        radioButtons[8]=findViewById(R.id.r9);
        radioButtons[9]=findViewById(R.id.r10);
        fin = findViewById(R.id.finish);
        submit = findViewById(R.id.submit);
        prev = findViewById(R.id.prev);
        next = findViewById(R.id.next);
        questions = findViewById(R.id.question);
        questionsM = findViewById(R.id.questionM);



        textView = findViewById(R.id.qcount);
    }

    public void setQues(){
        questions.setText(question12.getQue(count));
        questionsM.setText(question12.getQueM(count));
    }
    public void setAns(){
        for (int i=0 ; i < 10 ; i++){
            if(radioButtons[i].isChecked()){
                Answers.marks[count]=i+1;
                Answers.isSubmitted[count]=true;
            }
        }

    }}
