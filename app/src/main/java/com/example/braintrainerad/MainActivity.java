package com.example.braintrainerad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.database.Cursor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.braintrainerad.models.Scorecard;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

        FirebaseFirestore db;

        DatabaseHelper mydb = new DatabaseHelper(this);
        private Toolbar toolbar;
        Button startButton;
        TextView resultTextView;
        TextView pointsTextView;
        Button button0;
        Button button1;
        Button button2;
        Button button3;
        TextView sumTextView;
        TextView timerTextView;
        Button playAgainButton;
        RelativeLayout gameRelativeLayout;
        boolean active=true;
        DrawerLayout drawerLayout;
        NavigationView navigationView;
        EditText nameOfPlayer;
        String nameOfaPlayer;
        int sum;
        Spinner dropdown;
        String levelChoosen;
        String[] items;
        ArrayAdapter<String> adapter;
        int a;
        int b;
        TextView nameTv;
        TextView levelTv;
        String lastDigit;

        String StringOfIncorrecAns;

        ArrayList<Integer> answers = new ArrayList<Integer>();
        int locationOfCorrectAnswer;
        int score = 0;
        int numberOfQuestions = 0;

        public void viewData(){
            Cursor c = mydb.getScore();
            if(c.getCount() <= 0)
            {
                Toast.makeText(this, "No Records Found", Toast.LENGTH_SHORT).show();
            }
            else
            {
                StringBuffer buffer = new StringBuffer();
                while (c.moveToNext()){
                    buffer.append("No. "+ c.getString(0)+"\n");
                    buffer.append("Player Name:"+ c.getString(1)+"\n");
                    buffer.append("Score:"+ c.getString(2)+"\n\n");
                    showMsg("Data", buffer.toString());

                }
            }
        }
        public void showMsg(String title, String msg){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setTitle(title);
            builder.setMessage(msg);
            builder.show();
        }

        public void playAgain(View view) {

            active=true;
            score = 0;
            dropdown.setVisibility(View.INVISIBLE);
            numberOfQuestions = 0;
            toolbar.setTitle("let's start the game");
            button0.setVisibility(View.VISIBLE);
            button1.setVisibility(View.VISIBLE);
            button2.setVisibility(View.VISIBLE);
            button3.setVisibility(View.VISIBLE);
            sumTextView.setVisibility(View.VISIBLE);
            timerTextView.setText("30s");
            pointsTextView.setText("0/0");
            resultTextView.setText("");
            playAgainButton.setVisibility(View.INVISIBLE);

            generateQuestion();


            new CountDownTimer(30100, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {

                    timerTextView.setText(String.valueOf(millisUntilFinished / 1000) + "s");

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
                    toolbar.setTitle("Your Score");



                    boolean isInserted = mydb.insert(nameOfaPlayer, score);
                    if(isInserted == true)
                    {
                        Toast.makeText(MainActivity.this, "Score has been updated", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "Score has been updated", Toast.LENGTH_SHORT).show();
                    }
                    resultTextView.setText(nameOfaPlayer+" Your score: " + Integer.toString(score) + "/" + Integer.toString(numberOfQuestions));
                    addScore(nameOfaPlayer, score);
                    active=false;

                }
            }.start();
        }




        public void generateQuestion() {

            if(active) {
                Random rand = new Random();

                if(levelChoosen=="Easy"){

                    a = rand.nextInt(20)+1;
                    b = rand.nextInt(20)+1;

                }
                else if(levelChoosen=="Medium"){
                    a = rand.nextInt(80-40)+40+1;
                    b = rand.nextInt(80-40)+40+1;

                }
                else{
                    a = rand.nextInt(200-100)+100+1;
                    b = rand.nextInt(200-100)+100+1;
                }





                sum=a+b;
                lastDigit = Integer.toString(sum);

                sumTextView.setText(Integer.toString(a) + " + " + Integer.toString(b));

                locationOfCorrectAnswer = rand.nextInt(4);

                answers.clear();

                int incorrectAnswer;

                for (int i = 0; i < 4; i++) {

                    if (i == locationOfCorrectAnswer) {

                        answers.add(a + b);

                    } else {

                        incorrectAnswer = rand.nextInt((sum+7)-(sum-7))+(sum-7)+1;

                        while (incorrectAnswer == a + b || incorrectAnswer==0) {

                            incorrectAnswer = rand.nextInt((sum+7)-(sum-7))+(sum-7)+1;

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

        public void chooseAnswer(View view) {

            if (view.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer))) {

                score++;
                resultTextView.setText("Correct!");

            } else {

                resultTextView.setText("Wrong!");

            }

            numberOfQuestions++;
            pointsTextView.setText(Integer.toString(score) + "/" + Integer.toString(numberOfQuestions));
            generateQuestion();


        }

        public void start(View view) {



            levelChoosen = dropdown.getSelectedItem().toString();
            nameOfaPlayer = nameOfPlayer.getText().toString();
            if(nameOfaPlayer.isEmpty()||levelChoosen=="select the level--"){
                Toast.makeText(this, "please enter your name", Toast.LENGTH_SHORT).show();

            }
            else {
                //levelChoosen = dropdown.getSelectedItem().toString();
                startButton.setVisibility(View.INVISIBLE);
                gameRelativeLayout.setVisibility(RelativeLayout.VISIBLE);
                nameOfPlayer.setVisibility(View.INVISIBLE);
                playAgain(findViewById(R.id.playAgainButton));
                dropdown.setVisibility(View.INVISIBLE);
                nameTv.setVisibility(View.INVISIBLE);
                levelTv.setVisibility(View.INVISIBLE);
            }
        }

    public void addScore(String nameOfaPlayer, int score){
        Scorecard scorecard = new Scorecard(nameOfaPlayer, score);

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
            setContentView(R.layout.activity_main);

            db = FirebaseFirestore.getInstance();

            navigationView = findViewById(R.id.nav_view);
            drawerLayout =  findViewById(R.id.drawer_layout);
            toolbar=  (Toolbar) findViewById(R.id.toolBar);
            setSupportActionBar(toolbar);
            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.okay:
                            item.setChecked(true);
                            viewData();
                            drawerLayout.closeDrawers();
                            return true;


                    }
                    return false;
                }
            });

            startButton = (Button)findViewById(R.id.startButton);
            sumTextView = (TextView)findViewById(R.id.sumTextView);
            button0 = (Button)findViewById(R.id.button0);
            button1 = (Button)findViewById(R.id.button1);
            button2 = (Button)findViewById(R.id.button2);
            button3 = (Button)findViewById(R.id.button3);
            resultTextView = (TextView) findViewById(R.id.resultTextView);
            pointsTextView = (TextView) findViewById(R.id.pointsTextView);
            timerTextView = (TextView) findViewById(R.id.timerTextView);
            playAgainButton = (Button)findViewById(R.id.playAgainButton);
            gameRelativeLayout = (RelativeLayout) findViewById(R.id.gameRelativeLayout);
            nameOfPlayer = findViewById(R.id.nameOfPlayer);
            dropdown = findViewById(R.id.levelSpinner);
            nameTv=findViewById(R.id.nameTv);
            levelTv = findViewById(R.id.levelTv);
            items = new String[]{"select the level--","Easy", "Medium", "Hard"};
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
            dropdown.setAdapter(adapter);



        }

        private void setSupportActionBar(Toolbar toolbar) {

        }

        public void displayMessege(String messege){
            Toast.makeText(this, messege, Toast.LENGTH_SHORT).show();

        }
    }

//this is falak
//second time