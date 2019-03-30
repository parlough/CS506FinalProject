package com.example.voteforyou;

import android.content.Context;

import android.widget.EditText;
import android.widget.Button;

import com.google.firebase.FirebaseApp;
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
public class SignUpUnitTests {

    Context mContext;
    private FirebaseAuth mAuth;
    private Button button;

    @Before
    public void setup() {
        mContext = ApplicationProvider.getApplicationContext();
    }

    /**
     * Ensure that empty emails validate correctly.
     */

    @Test
    public void emailEmptyTest() {
        EditText email = new EditText(mContext);
        email.setText("");
        SignUpActivity myObjectUnderTest = new SignUpActivity();
        myObjectUnderTest.blankEmailResponse(email);
        CharSequence error = email.getError();
        assertThat(error, is((CharSequence) "Enter an email address"));
    }

    /**
     * Ensure emails have correct formatting.
     */

    @Test
    public void validEmailTest() {
        SignUpActivity myObjectUnderTest = new SignUpActivity();
        EditText email = new EditText(mContext);
        email.setText("foo");
        myObjectUnderTest.ensureValidEmail(email);
        CharSequence error = email.getError();
        assertThat(error, is((CharSequence) "Enter a valid email address"));

        // Note we need to re-initialize the signup activity for each task
        // to ensure we get a unique test each time.
        myObjectUnderTest = new SignUpActivity();
        email = new EditText(mContext);
        email.setText("foo@");
        myObjectUnderTest.ensureValidEmail(email);
        error = email.getError();
        assertThat(error, is((CharSequence) "Enter a valid email address"));

        myObjectUnderTest = new SignUpActivity();
        email = new EditText(mContext);
        email.setText("foo@bar");
        myObjectUnderTest.ensureValidEmail(email);
        error = email.getError();
        assertThat(error, is((CharSequence) "Enter a valid email address"));

        myObjectUnderTest = new SignUpActivity();
        email = new EditText(mContext);
        email.setText("@bar.com");
        myObjectUnderTest.ensureValidEmail(email);
        error = email.getError();
        assertThat(error, is((CharSequence) "Enter a valid email address"));

        myObjectUnderTest = new SignUpActivity();
        email = new EditText(mContext);
        email.setText("@bar");
        myObjectUnderTest.ensureValidEmail(email);
        error = email.getError();
        assertThat(error, is((CharSequence) "Enter a valid email address"));

        myObjectUnderTest = new SignUpActivity();
        email = new EditText(mContext);
        email.setText("bar.com");
        myObjectUnderTest.ensureValidEmail(email);
        error = email.getError();
        assertThat(error, is((CharSequence) "Enter a valid email address"));

        myObjectUnderTest = new SignUpActivity();
        email = new EditText(mContext);
        email.setText("foo@bar.com");
        myObjectUnderTest.ensureValidEmail(email);
        error = email.getError();
        assert (error == null);
    }

    /**
     * Ensure that passwords can't be empty.
     */

    @Test
    public void passwordEmptyTest() {
        EditText password = new EditText(mContext);
        password.setText("");
        SignUpActivity myObjectUnderTest = new SignUpActivity();
        myObjectUnderTest.blankPasswordResponse(password);
        CharSequence error = password.getError();
        assertThat(error, is((CharSequence) "Enter a password"));
    }

    /**
     * Ensure that passwords have the right length.
     */

    @Test
    public void validPasswordTest() {
        EditText password = new EditText(mContext);
        password.setText("ehlo");
        SignUpActivity myObjectUnderTest = new SignUpActivity();
        myObjectUnderTest.ensureCorrectPasswordLength(password);
        CharSequence error = password.getError();
        assertThat(error, is((CharSequence) "Minimum length of a password should be 6"));
    }

}
