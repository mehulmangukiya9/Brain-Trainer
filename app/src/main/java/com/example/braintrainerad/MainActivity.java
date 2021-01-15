package com.example.braintrainerad;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

    DrawerLayout drawer;
    Toolbar toolbar;
    TextView nameTv;
    EditText nameOfPlayer;
    TextView levelTv;
    Spinner levelSpinner;
    String nameOfaPlayer;
    Button startButton;
    String levelChosen;
    NavigationView nav_view;
    String[] items;
    ArrayAdapter<String> adapter;

    //new
    SeekBar secondSeekBar;
    TextView secondTv;
    TextView showSecond;
    String showSec;



    public void start1(View view) {


        levelChosen = levelSpinner.getSelectedItem().toString();
        nameOfaPlayer = nameOfPlayer.getText().toString();
        showSec = showSecond.getText().toString().substring(0,2);

        if(nameOfaPlayer.isEmpty()||levelChosen=="Select The Level--"){
            Toast.makeText(this, "please enter your name", Toast.LENGTH_SHORT).show();
        }

        else {
            //levelChoosen = dropdown.getSelectedItem().toString();

            Intent gotoGenerateQuestion= new Intent(this,GenerateQuestion.class);
            gotoGenerateQuestion.putExtra("levelChosen",levelChosen);
            gotoGenerateQuestion.putExtra("nameOfaPlayer",nameOfaPlayer);
            gotoGenerateQuestion.putExtra("howManySeconds",showSec);


            startActivity(gotoGenerateQuestion);

        }
    }




    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameTv = findViewById(R.id.nameTv);
        nameOfPlayer = findViewById(R.id.nameOfPlayer);
        levelTv = findViewById(R.id.levelTv);
        startButton = findViewById(R.id.startButton);
        nav_view = findViewById(R.id.nav_view);
        levelSpinner = findViewById(R.id.levelSpinner);


        items = new String[]{"Select The Level---","Easy", "Medium", "Hard"};
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, items);
        levelSpinner.setAdapter(adapter);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        toggle.syncState();



        //new functionality

        secondSeekBar = findViewById(R.id.secondSeekBar);
        showSecond = findViewById(R.id.showSeconds);
        secondTv=findViewById(R.id.secondTv);
        secondSeekBar.setMax(60);
        secondSeekBar.setMin(10);



        secondSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                showSecond.setText(i+"s");

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



    }



    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    }

//this is falak
//second time
//harsh patel