package com.example.uscdoordrink;
//Angela Sun
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.intent.Intents;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.rule.ActivityTestRule;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class AsBlackBoxRegisterTest {
    @Rule
    public ActivityTestRule<RegisterActivity> mActivityTestScenario = new ActivityTestRule<RegisterActivity>(RegisterActivity.class, true, false);
    private String name = "shore";
    private String username = "shore@gmail.com";
    private String password = "shorepassword";

    @Before
    public void setUp() throws Exception {

    }

    // can't register account that's already registered
    @Test
    public void testAlreadyRegistered() throws InterruptedException {
        mActivityTestScenario.launchActivity(null);

        Intents.init();
        // input already registered details
        Espresso.onView(withId(R.id.editTextTextPersonName3)).perform(typeText(name));
        Espresso.onView(withId(R.id.editTextTextEmailAddress5)).perform(typeText(username));
        Espresso.onView(withId(R.id.editTextTextPassword4)).perform(typeText(password));

        // close the keybard
        Espresso.closeSoftKeyboard();
        // click signup button
        Espresso.onView(withId(R.id.signupButton3)).perform(click());


        //check that sign up button is still on screen, meaning activity stays the same
        onView(withId(R.id.signupButton3)).check(matches(isDisplayed()));

        //give enough time before intent releases
        Thread.sleep(5000);
        Intents.release();

    }


    public void tearDown() throws Exception {
    }
}
