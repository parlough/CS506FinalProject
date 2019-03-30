package com.example.voteforyou;

import android.content.Context;

import android.widget.EditText;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import androidx.test.core.app.ApplicationProvider;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class SignInUnitTests {

    Context mContext;
    private FirebaseAuth mAuth;
    private Button button;

    @Before
    public void setup() {
        mContext = ApplicationProvider.getApplicationContext();

    }

    /**
     * Ensure that emails can't be empty.
     */

    @Test
    public void emailEmptyMethod() {
        EditText email = new EditText(mContext);
        email.setText("");
        SignInActivity myObjectUnderTest = new SignInActivity();
        myObjectUnderTest.blankEmailResponse(email);
        CharSequence error = email.getError();
        assertThat(error, is((CharSequence) "Enter an email address"));
    }

    /**
     * Ensure that passwords can't be empty.
     */

    @Test
    public void passwordEmptyMethod() {
        EditText password = new EditText(mContext);
        password.setText("");
        SignInActivity myObjectUnderTest = new SignInActivity();
        myObjectUnderTest.blankPasswordResponse(password);
        CharSequence error = password.getError();
        assertThat(error, is((CharSequence) "Enter a password"));
    }

}
