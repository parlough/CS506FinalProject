package com.example.voteforyou;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //remember if the user is signed in already
    private static boolean signedIn = false;

    //declare the layout elements
    private Button signInButton;
    private Button signUpButton;

    //enter here on start!
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //default
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get the buttons from layout
        signInButton = (Button) findViewById(R.id.signinbutton);
        signUpButton = (Button) findViewById(R.id.signupbutton);

        //set function for sign in button
        signInButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                openSignIn();
            }
        });


        //set function for sign up button
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openSignUp();
            }
        });
    }

    //helper for sign in button press
    public void openSignIn()    {

        Toast myToast = Toast.makeText(getApplicationContext(), "Got here.", Toast.LENGTH_SHORT);
        myToast.show();


        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);

    }

    //helper for sign up button press
    public void openSignUp()    {


        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }
}
