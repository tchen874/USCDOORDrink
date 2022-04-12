package com.example.uscdoordrink;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.uscdoordrink", appContext.getPackageName());
    }


    @RunWith(AndroidJUnit4.class)
    public static class registerTest{
        //public ActivityScenarioRule<MainActivity> mActivityRule = new ActivityScenarioRule<>(MainActivity.class);
        public String email;
        public String password;
        public String name;
        public User user;
        public FirebaseAuth auth;
        public FirebaseDatabase database;

        @Before
        public void setuo()
        {
            name = "whiteBoxTesting";
            email = "whiteBoxTesting@gmail.com";
            password = "whiteBoxTesting";
            auth = FirebaseAuth.getInstance();
            database = FirebaseDatabase.getInstance();
            auth.createUserWithEmailAndPassword(email, password);
            user = new User("whiteBoxTesting", email);
        }


//        // Test to see if the user is in the firebase auth
//        // which it should be
//        @Test
//        public void testUserInFirebaseDatabase()
//        {
//        }
    }
}