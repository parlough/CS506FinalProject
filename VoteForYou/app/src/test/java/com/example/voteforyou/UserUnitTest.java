package com.example.voteforyou;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;

@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class UserUnitTest {

    /**
     * This method tests User class behavior.
     */
    @Test
    public void userTest() {
        User user = new User();
        user = new User("Annie", "foo@bar.com");
        assertEquals(user.name, "Annie");
        assertEquals(user.email, "foo@bar.com");
    }
}
