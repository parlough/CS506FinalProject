package com.example.voteforyou;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Collections;


public class ReviewActivity extends AppCompatActivity {

    //declare list of candidates and issues
    private ListView candidatesListView;
    private ListView issuesListView;

    //declare buttons
    private Button rankingButton1;
    private Button settingsButton1;

    //Declare Adapters
    ArrayAdapter<RankPair> candidateAdapter;
    ArrayAdapter<String> issueAdapter;

    //Declare ArrayLists
    ArrayList<RankPair> candidateNames = new ArrayList<RankPair>();

    //Declare Database Reference
    DatabaseReference dbReference;

    //Declare firebase Auth object
    FirebaseAuth mAuth;


    //execution begins here upon page loading
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //default activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        mAuth = FirebaseAuth.getInstance();

        //////////////////////handle candidates list///////////////////////
        //our list to display candidates
        //final List<String> candidateNames = new ArrayList<String>();


//        //populate list
//        candidateNames.add("Cory Booker");
//        candidateNames.add("Donald Trump");
//        candidateNames.add("Ted Cruz");
//        candidateNames.add("Kamala Harris");

        //set up to display the candidate list
        candidatesListView = (ListView) findViewById(R.id.candidatesList);
        candidateAdapter = new ArrayAdapter<RankPair>(ReviewActivity.this, android.R.layout.simple_list_item_1, candidateNames);
        candidatesListView.setAdapter(candidateAdapter);

        String UID = mAuth.getUid();
        dbReference = FirebaseDatabase.getInstance().getReference("Users").child(UID).child("Responses");

        dbReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String name = dataSnapshot.child("candidateName").getValue(String.class);
                int rank = dataSnapshot.child("ranking").getValue(int.class);
                RankPair rankPair = new RankPair(name, rank);
                candidateNames.add(rankPair);
                Collections.sort(candidateNames, new Comparator<RankPair>() {
                    @Override
                    public int compare(RankPair obj1, RankPair obj2) {
                        int rank1, rank2;

                        rank1 = obj1.getRanking();
                        rank2 = obj2.getRanking();

                        if (rank1 > rank2) {
                            return 1;
                        } else if (rank1 < rank2){
                            return -1;
                        } else {
                            return 0;
                        }
                    }
                });
                Collections.reverse(candidateNames);
                candidateAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        //handle clicking functionality
        candidatesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                RankPair clickedCandidateName = candidateNames.get(position);

                Toast myToast = Toast.makeText(getApplicationContext(), clickedCandidateName.getCandidateName(), Toast.LENGTH_SHORT);
                myToast.show();
            }
        });



        //////////////////handle issues names/////////////////////////////
        final List<String> rankedIssuesNames = new ArrayList<String>();

        //populate
        rankedIssuesNames.add("Gun control");
        rankedIssuesNames.add("Abortion");
        rankedIssuesNames.add("Foreign policy");
        rankedIssuesNames.add("Economy");

        //set up to view
        issuesListView = (ListView) findViewById(R.id.issuesList);
        issueAdapter = new ArrayAdapter<String>(ReviewActivity.this, android.R.layout.simple_list_item_1, rankedIssuesNames);
        issuesListView.setAdapter(issueAdapter);

        //////////////////////////handle buttons////////////////////////

        //initialize
        rankingButton1 = (Button) findViewById(R.id.rankingButton);
        settingsButton1 = (Button) findViewById(R.id.settingsButton);

        //set function for ranking button
        rankingButton1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                openRanking();
            }
        });


        //set function for sign up button
        settingsButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openSettings();
            }
        });

        populateCandidateList();

    }



    //helper for opening ranking mode
    public void openRanking()   {

        Toast myToast = Toast.makeText(getApplicationContext(), "Ranking mode.", Toast.LENGTH_SHORT);
        myToast.show();

        //Direct user to ranking page
        Intent intent = new Intent(getApplicationContext(), RankingActivity.class);
        startActivity(intent);
    }



    //helper for opening settings
    public void openSettings()  {
        Toast myToast = Toast.makeText(getApplicationContext(), "Settings mode.", Toast.LENGTH_SHORT);
        myToast.show();

        //Direct user to ranking page
        Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
        startActivity(intent);
    }

    private void populateCandidateList(){

    }
}
