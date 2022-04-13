package com.example.uscdoordrink;
// Angela Sun

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import androidx.test.core.app.ApplicationProvider.*;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.Root;
import androidx.test.espresso.intent.Intents;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;

import android.view.View;

import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.rule.ActivityTestRule;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class asBlackBoxMainTests {

    private String email = "shore@gmail.com";
    private String password = "shorepassword";
    private String invalidpassword = "invalidpassword";
    private String newpassword = "newpasswordshore";

    @Rule
    public ActivityTestRule<MainActivity> mMainActivity = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Before
    public void setUp() throws Exception {

    }


    //verify user can't log in with valid username and invalid password
    @Test
    public void testInvalidPassword() throws InterruptedException {

        // input email and password
        onView(withId(R.id.editTextTextEmailAddress)).perform(typeText(email));
        onView(withId(R.id.editTextTextPassword)).perform(typeText(invalidpassword));
        // close keyboard
        Espresso.closeSoftKeyboard();

        // click login button
        onView(withId(R.id.loginButton)).perform(click());

        //check that log in button is still on screen, meaning activity stays the same
        // can't check for another intent since no intent started lol :(
        onView(withId(R.id.loginButton)).check(matches(isDisplayed()));

    }


    //verify user can't log in with blank email field
    @Test
    public void testBlankEmail() throws InterruptedException{

        //should get "Email cannot be empty!" alert

        //fill password field and leave email field empty
        onView(withId(R.id.editTextTextPassword)).perform(typeText(password));
        // click login button
        onView(withId(R.id.loginButton)).perform(click());

        //check that log in button is still on screen, meaning activity stays the same
        onView(withId(R.id.loginButton)).check(matches(isDisplayed()));
    }

    //verify user can't log in with blank password field
    @Test
    public void testBlankPassword() throws InterruptedException{

        //should get "Password cannot be empty!" alert

        //fill email field and leave password field empty
        onView(withId(R.id.editTextTextEmailAddress)).perform(typeText(email));
        // click login button
        onView(withId(R.id.loginButton)).perform(click());
        //check that log in button is still on screen, meaning activity stays the same
        onView(withId(R.id.loginButton)).check(matches(isDisplayed()));
    }




    public void tearDown() throws Exception {
    }
}

