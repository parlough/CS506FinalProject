package com.example.voteforyou;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    //declare the layout elements

    //the text boxes
    private EditText emailText;
    private EditText passwordText;
    private EditText nameText;

    //Firebase Authentication object
    private FirebaseAuth mAuth;

    //the button
    private Button submitButton;

    //code enters here upon instantiation
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //default
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //initialize the text boxes/button
        emailText = (EditText) findViewById(R.id.emailEnterText);
        passwordText = (EditText) findViewById(R.id.passwordEnterText);
        nameText = (EditText) findViewById(R.id.nameEnterText);
        submitButton = (Button) findViewById(R.id.signInSubmitButton);


        //Get Firebase authentication instance
        mAuth = FirebaseAuth.getInstance();


        //declare activity for the submit button - call helper function
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitHelper();
            }
        });
    }

    //handle button submit - get strings from edittexts, try to sign in, launch other activity
    private void submitHelper(){
        //Get the sign in credentials
        String email = emailText.getText().toString().trim();
        String password = passwordText.getText().toString().trim();

        //Email cannot be empty
        if(email.isEmpty()){
            emailText.setError("Enter an email address");
            emailText.requestFocus();
            return;
        }

        //Email must be in a standard email form
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailText.setError("Enter a valid email address");
            emailText.requestFocus();
            return;
        }

        //Password cannot be empty
        if(password.isEmpty()){
            passwordText.setError("Enter a password");
            passwordText.requestFocus();
            return;
        }

        //Password must be at least 6 characters long
        if(password.length() < 6){
            passwordText.setError("Minimum length of a password should be 6");
            passwordText.requestFocus();
            return;
        }

        //Email and Password must be valid. Attempt to login
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //Define a new user object with name and email values from UI
                            User user = new User(nameText.getText().toString().trim(), emailText.getText().toString().trim());

                            //Add the user to the database- UID is the key to join with user auth table
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(getApplicationContext(), "Store is successful", Toast.LENGTH_LONG).show();
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(), "Store is not successful", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                            Toast.makeText(getApplicationContext(), "Register is successful", Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(getApplicationContext(), ReviewActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Register is not successful", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}