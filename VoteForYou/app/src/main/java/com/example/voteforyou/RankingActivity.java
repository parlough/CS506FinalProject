package com.example.voteforyou;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class RankingActivity extends AppCompatActivity {

    //declare all of our variables for activity elements
    private Button ReviewBtn, SettingsBtn;
    private TextView candidateNameText;
    private TextView candidatePartyText;
    private ImageView candidatePicture;
    private String currentCandidate;
    private int opinion, currentCandidateIndex;

    //Candidate Indexes - used for ranking
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

    //hold the candidate information strings for displaying
    private List<String> candidateNames;
    private List<String> candidateParties;

    //Firebase Authentication object
    private FirebaseAuth mAuth;

    //execution starts here
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //default
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        //initialize activity elements
        ReviewBtn = (Button) findViewById(R.id.button_results);
        SettingsBtn = (Button) findViewById(R.id.button_settings);
        candidateNameText = (TextView) findViewById(R.id.candidateNameText);
        candidatePartyText = (TextView) findViewById(R.id.candidate_party);
        candidatePicture = (ImageView) findViewById(R.id.candidateImage);

        //go to review mode
        ReviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Direct user to review page
                Intent intent = new Intent(getApplicationContext(), ReviewActivity.class);
                startActivity(intent);
            }
        });

        //go to settings
        SettingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Direct user to settings page
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);
            }
        });

        mAuth = FirebaseAuth.getInstance();


        //our list to display candidate names
        candidateNames = new ArrayList<String>();
        //populate list
        candidateNames.add("Cory Booker");
        candidateNames.add("Donald Trump");
        candidateNames.add("Ted Cruz");
        candidateNames.add("Kamala Harris");
        candidateNames.add("Barack Obama");

        //temporary list to hold parties of candidates in same exact order
        candidateParties = new ArrayList<String>();
        candidateParties.add("Democrat");
        candidateParties.add("Republican");
        candidateParties.add("Republican");
        candidateParties.add("Democrat");
        candidateParties.add("Democrat");

        //set the first candidate state - cory booker
        currentCandidateIndex = 0;
        currentCandidate = candidateNames.get(currentCandidateIndex);
        candidateNameText.setText(currentCandidate);
        candidatePartyText.setText(candidateParties.get(currentCandidateIndex));
        candidatePicture.setImageResource(R.drawable.cory_booker);

    }

    //get the user's button press and record result
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

    //add pair to firebase
    private void addResponse(String currentCandidate, int opinion){
        String UID = mAuth.getUid();
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Users").child(UID).child("Responses").child(currentCandidate);
        RankPair rankPair = new RankPair(currentCandidate, opinion);
        dbRef.setValue(rankPair);
    }

    //get the next candidate to display and rank
    private void incrementCandidate(){
        //We only have 5 preloaded candidates
        if(currentCandidateIndex < 4){
            currentCandidateIndex++;
        }

        //set names
        currentCandidate = candidateNames.get(currentCandidateIndex);
        candidateNameText.setText(currentCandidate);
        candidatePartyText.setText(candidateParties.get(currentCandidateIndex));

        //update the candidate picture
        if(currentCandidateIndex == 1)  {

            candidatePicture.setImageResource(R.drawable.donald_trump);
        }

        //cruz picture
        else if(currentCandidateIndex == 2)  {

            candidatePicture.setImageResource(R.drawable.ted_cruz);
        }

        //harris picture
        else if(currentCandidateIndex == 3)  {

            candidatePicture.setImageResource(R.drawable.kamala_harris);
        }

        //obama picutre
        else if(currentCandidateIndex == 4)  {

            candidatePicture.setImageResource(R.drawable.barack_obama);
        }
    }
}
