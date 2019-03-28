package com.example.voteforyou;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {

    //declare the layout elements

    //the text boxes
    private EditText emailText;
    private EditText passwordText;

    //Firebase Authentication object
    private FirebaseAuth mAuth;

    //the button
    private Button submitButton;

    //code enters here upon instantiation
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //default
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //initialize the text boxes/button
        emailText = (EditText) findViewById(R.id.emailEnterText);
        passwordText = (EditText) findViewById(R.id.passwordEnterText);
        submitButton = (Button) findViewById(R.id.signInSubmitButton);

        mAuth = FirebaseAuth.getInstance();


        //declare activity for the submit button - call helper function
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                submitHelper();
            }
        });
    }
        private void submitHelper(){

            //Get login credentials from UI
            String email = emailText.getText().toString().trim();
            String password = passwordText.getText().toString().trim();

            //Email cannot be empty
            if(email.isEmpty()){
                emailText.setError("Enter an email address");
                emailText.requestFocus();
                return;
            }

            //Password cannot be empty
            if(password.isEmpty()){
                passwordText.setError("Enter a password");
                passwordText.requestFocus();
                return;
            }

            //Attempt to sign in user with credentials from UI
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                //Login was successful
                                Toast.makeText(getApplicationContext(), "Login is successful", Toast.LENGTH_LONG).show();
                                //Direct user into app
                                Intent intent = new Intent(getApplicationContext(), ReviewActivity.class);
                                startActivity(intent);
                            } else {
                                //Login was not successful
                                Toast.makeText(getApplicationContext(), "Login is not successful", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }




}
