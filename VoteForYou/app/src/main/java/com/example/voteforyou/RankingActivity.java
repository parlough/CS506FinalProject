package com.example.voteforyou;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class RankingActivity extends AppCompatActivity {

    private Button ReviewBtn, SettingsBtn;
    private TextView candidateNameText;
    private String currentCandidate;
    private int opinion, currentCandidateIndex;

    //Candidate Indexes
    public static final int OPINION_GREAT = 5;
    public static final int OPINION_GOOD = 4;
    public static final int OPINION_AVERAGE = 3;
    public static final int OPINION_LACKING = 2;
    public static final int OPINION_TERRIBLE = 1;

    //Ranking Contants
    public static final int Cory_Booker = 0;
    public static final int Donald_Trump = 1;
    public static final int Ted_Cruz = 2;
    public static final int Kamala_Harris = 3;
    public static final int Barack_Obama = 4;

    private List<String> candidateNames;

    //Firebase Authentication object
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        ReviewBtn = (Button) findViewById(R.id.button_results);
        SettingsBtn = (Button) findViewById(R.id.button_settings);
        candidateNameText = (TextView) findViewById(R.id.candidateNameText);

        ReviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Direct user to review page
                Intent intent = new Intent(getApplicationContext(), ReviewActivity.class);
                startActivity(intent);
            }
        });

        SettingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Direct user to settings page
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);
            }
        });

        mAuth = FirebaseAuth.getInstance();

        //our list to display candidates
        candidateNames = new ArrayList<String>();
        //populate list
        candidateNames.add("Cory Booker");
        candidateNames.add("Donald Trump");
        candidateNames.add("Ted Cruz");
        candidateNames.add("Kamala Harris");
        candidateNames.add("Barack Obama");

        currentCandidateIndex = 0;
        currentCandidate = candidateNames.get(currentCandidateIndex);
        candidateNameText.setText(currentCandidate);



    }

    public void opinionResponse(View v){
        switch(v.getId()) {
            case R.id.button_great:
                opinion = OPINION_GREAT;
                break;
            case R.id.button_good:
                opinion = OPINION_GOOD;
                break;
            case R.id.button_average:
                opinion = OPINION_AVERAGE;
                break;
            case R.id.button_lacking:
                opinion = OPINION_LACKING;
                break;
            case R.id.button_terrible:
                opinion = OPINION_TERRIBLE;
                break;
        }

        addResponse(currentCandidate, opinion);

        incrementCandidate();
    }

    private void addResponse(String currentCandidate, int opinion){
        String UID = mAuth.getUid();
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Users").child(UID).child("Responses").child(currentCandidate);
        RankPair rankPair = new RankPair(currentCandidate, opinion);
        dbRef.setValue(rankPair);
    }

    private void incrementCandidate(){
        //We only have 5 preloaded candidates
        if(currentCandidateIndex < 4){
            currentCandidateIndex++;
        }
        currentCandidate = candidateNames.get(currentCandidateIndex);
        candidateNameText.setText(currentCandidate);

    }

}
