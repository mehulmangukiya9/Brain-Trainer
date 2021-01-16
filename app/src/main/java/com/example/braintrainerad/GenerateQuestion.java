package com.example.braintrainerad;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.braintrainerad.models.Scorecard;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class GenerateQuestion extends AppCompatActivity {

    //DatabaseHelper mydb = new DatabaseHelper(this);

    FirebaseFirestore db;

    DatabaseHelper mydb = new DatabaseHelper(this);

    TextView resultTextView;
    TextView pointsTextView;
    Button button0;
    Button button1;
    Button button2;
    Button button3;
    Toolbar toolbar;
    TextView sumTextView;
    TextView timerTextView;
    Button playAgainButton;

    //RelativeLayout gameRelativeLayout;
    boolean active=true;
    //int sum;

    int a;
    int b;
    String lastDigit;
    char lastD;
    int locationOfCorrectAnswer;
    int numberOfQuestions;
    String convert;
    char con;
    int score;
    ArrayList<Integer> answers = new ArrayList<>();
    Intent intent;
    ProgressBar progressBar;

    //new
    int c;
    int signA;
    int signB;
    int signC;
    int levelSum;
    int A;
    int B;
    int C;
    int[] bigNumb;

    int timer;


    String levelChosenIntent;
    String nameOfaPlayerIntent;

    public void playAgain1(View view) {

        active=true;
        score = 0;
        numberOfQuestions = 0;
        toolbar.setTitle("Questions has been started....");


        button0.setVisibility(View.VISIBLE);
        button1.setVisibility(View.VISIBLE);
        button2.setVisibility(View.VISIBLE);
        button3.setVisibility(View.VISIBLE);
        sumTextView.setVisibility(View.VISIBLE);

        timerTextView.setText("30s");
        pointsTextView.setText("0/0");
        resultTextView.setText("");
        playAgainButton.setVisibility(View.INVISIBLE);

        generateQuestion1();


        new CountDownTimer(timer, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {

                timerTextView.setText(String.valueOf(millisUntilFinished / 1000) + "s");
                progressBar.setProgress((int)millisUntilFinished/1000);
            }

            @Override
            public void onFinish() {
                button0.setVisibility(View.INVISIBLE);
                button1.setVisibility(View.INVISIBLE);
                button2.setVisibility(View.INVISIBLE);
                button3.setVisibility(View.INVISIBLE);
                sumTextView.setVisibility(View.INVISIBLE);
                playAgainButton.setVisibility(View.VISIBLE);
                timerTextView.setText("0s");
                toolbar.setTitle("Score Board");


                boolean isInserted = mydb.insert(nameOfaPlayerIntent, score);
                if(isInserted == true)
                {
                    Toast.makeText(GenerateQuestion.this, "Score has been updated", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(GenerateQuestion.this, "Score has been updated", Toast.LENGTH_SHORT).show();
                }


                progressBar.setProgress((timer-100)/1000);
                resultTextView.setText(nameOfaPlayerIntent+" Your score: " + Integer.toString(score) + "/" + Integer.toString(numberOfQuestions));
                addScore(nameOfaPlayerIntent, score);
                active=false;

            }
        }.start();
    }


    public void generateQuestion1() {
        if(active) {


            Random rand = new Random();


            if(levelChosenIntent.equals("Easy")){
                signA = rand.nextInt(2);
                a = rand.nextInt(20)+1;
                b = rand.nextInt(20)+1;

                A=Math.max(a,b);
                B=Math.min(a,b);

                if(signA==0){
                    sumTextView.setText(Integer.toString(A) + " + " + Integer.toString(B));
                    levelSum=A+B;
                }
                else {
                    sumTextView.setText(Integer.toString(A) + " - " + Integer.toString(B));
                    levelSum=A-B;
                }
            }


            else if(levelChosenIntent.equals("Medium")){

                signA=rand.nextInt(2);
                signB=rand.nextInt(2);

                a = rand.nextInt(20)+1;
                b = rand.nextInt(20)+1;
                c = rand.nextInt(20)+1;

//                bigNumb = new int[]{a,b,c};
//                Arrays.sort(bigNumb);

                A=a;
                B=b;
                C=c;

                if(signA==0&&signB==0){

                    sumTextView.setText(Integer.toString(A) + " + " + Integer.toString(B)+ " + " + Integer.toString(C));
                    levelSum=A+B+C;

                }
                else if (signA==0&&signB==1){

                    sumTextView.setText(Integer.toString(A) + " + " + Integer.toString(B)+ " - " + Integer.toString(C));
                    levelSum=A+B-C;


                }
                else{

                    sumTextView.setText(Integer.toString(A) + " - " + Integer.toString(B)+ " + " + Integer.toString(C));
                    levelSum=A-B+C;
                }

            }


            else{

                signA= rand.nextInt(3);
                signB= rand.nextInt(3);


                a = rand.nextInt(25)+1;
                b = rand.nextInt(25)+1;
                c = rand.nextInt(25)+1;
                A=a;
                B=b;
                C=c;

                if (signA==0&signB==1){

                    sumTextView.setText(Integer.toString(a) + " + " + Integer.toString(b)+ " - " + Integer.toString(c));
                    levelSum=A+B-C;

                }
                else if(signA==0&signB==2){
                    sumTextView.setText(Integer.toString(A) + " + " + Integer.toString(B)+ " * " + Integer.toString(C));
                    levelSum=A+B*C;
                }
                else if(signA==1&signB==0){

                    sumTextView.setText(Integer.toString(A) + " - " + Integer.toString(B)+ " + " + Integer.toString(C));
                    levelSum=A-B+C;
                }

                else if(signA==1&signB==2){
                    sumTextView.setText(Integer.toString(A) + " - " + Integer.toString(B)+ " * " + Integer.toString(C));
                    levelSum=A-B*C;

                }
                else if(signA==2&signB==0){
                    sumTextView.setText(Integer.toString(A) + " * " + Integer.toString(B)+ " + " + Integer.toString(C));
                    levelSum=A*B+C;
                }
                else{
                    sumTextView.setText(Integer.toString(A) + " * " + Integer.toString(B)+ " - " + Integer.toString(C));
                    levelSum=A*B-C;

                }



            }


            lastDigit = Integer.toString(levelSum);


            locationOfCorrectAnswer = rand.nextInt(4);

            answers.clear();

            int incorrectAnswer;

            for (int i = 0; i < 4; i++) {

                if (i == locationOfCorrectAnswer) {

                    answers.add(levelSum);

                } else {

                    incorrectAnswer = rand.nextInt((levelSum+6)-(levelSum-6))+(levelSum-6)+1;

                    while (incorrectAnswer == levelSum || incorrectAnswer==0) {

                        incorrectAnswer = rand.nextInt((levelSum+6)-(levelSum-6))+(levelSum-6)+1;

                    }

                    answers.add(incorrectAnswer);

                }

            }

            button0.setText(Integer.toString(answers.get(0)));
            button1.setText(Integer.toString(answers.get(1)));
            button2.setText(Integer.toString(answers.get(2)));
            button3.setText(Integer.toString(answers.get(3)));


        }


    }

    public void chooseAnswerFrom(View view) {

        if (view.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer))) {

            score++;
            resultTextView.setText("Correct!");

        } else {

            resultTextView.setText("Wrong!");

        }

        numberOfQuestions++;
        pointsTextView.setText(Integer.toString(score) + "/" + Integer.toString(numberOfQuestions));
        generateQuestion1();



    }



    public void addScore(String nameOfaPlayer, int score){
        Scorecard scorecard = new Scorecard(nameOfaPlayer, score, (timer-100)/1000);

        db.collection("scorecard")
                .add(scorecard)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(), "Score Added.", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Error"+e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

        Toast.makeText(getApplicationContext(), nameOfaPlayer,Toast.LENGTH_LONG).show();

    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_question);

        db = FirebaseFirestore.getInstance();

        sumTextView = findViewById(R.id.sumTV);
        button0 = (Button)findViewById(R.id.OptionButton0);
        button1 = (Button)findViewById(R.id.OptionButton1);
        button2 = (Button)findViewById(R.id.OptionButton2);
        button3 = (Button)findViewById(R.id.OptionButton3);

        resultTextView = (TextView) findViewById(R.id.resultTV);
        pointsTextView = (TextView) findViewById(R.id.pointsTV);
        timerTextView = (TextView) findViewById(R.id.timerTV);
        playAgainButton = (Button)findViewById(R.id.playAgainButton);
        //gameRelativeLayout = (RelativeLayout) findViewById(R.id.gameRelativeLayout);
        toolbar = findViewById(R.id.toolbar);


        intent = getIntent();
        levelChosenIntent = intent.getStringExtra("levelChosen");
        nameOfaPlayerIntent = intent.getStringExtra("nameOfaPlayer");
        timer = Integer.parseInt(intent.getStringExtra("howManySeconds"))*1000+100;

        progressBar = findViewById(R.id.progressBar);
        progressBar.setMax((timer-100)/1000);


        //Toast.makeText(this, levelChosenIntent +" "+ nameOfaPlayerIntent, Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, timer+"s", Toast.LENGTH_SHORT).show();


        playAgain1(findViewById(R.id.playAgainButton));

    }

}
