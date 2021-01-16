package com.example.braintrainerad;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.braintrainerad.models.Scorecard;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class ScoreDataActivity extends AppCompatActivity {

    FirebaseFirestore db;
    TextView tvData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_data);

        db = FirebaseFirestore.getInstance();
        tvData = findViewById(R.id.tv_data);

        getAndSetData();
    }

    private void getAndSetData() {
        db.collection("scorecard")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            String resultStr="";

                            for (DocumentSnapshot documentSnapshot : task.getResult()){
                                Scorecard scorecard = documentSnapshot.toObject(Scorecard.class);
                                resultStr+=
                                        "Name: "+ scorecard.getName()
                                                +"\nTimer: "+ scorecard.getGameTime()
                                                +"\nScore: "+ scorecard.getScore()+"\n\n";
                            }
                            tvData.setText(resultStr);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Error"+e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
    }

}
