package com.example.uscdoordrink;
// Tiffany Chen

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

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestScenario = new ActivityTestRule<MainActivity>(MainActivity.class);
    private String email = "userBlackBox@gmail.com";
    private String password = "userBlackBox";

    @Before
    public void setUp() throws Exception {
    }
    // This test is use to test when user click enter the correct password
    // and email, will direct them to the view
    @Test
    public void testUserInputScenario() throws InterruptedException {
        Intents.init();
        // input the email and password in the edit text
        Espresso.onView(withId(R.id.editTextTextEmailAddress)).perform(typeText(email));
        Espresso.onView(withId(R.id.editTextTextPassword)).perform(typeText(password));
        // close the keybard
        Espresso.closeSoftKeyboard();
        // perform login button
        Espresso.onView(withId(R.id.loginButton)).perform(click());
        Thread.sleep(6000);
        // check the view direct to userNavigationView class
        //Espresso.onView(withId(R.id.user_drawer_layout)).check(matches(isDisplayed()));
        intended(hasComponent(UserNavigationActivity.class.getName()));
        Intents.release();
    }

    //When user click register, direct them to the register activity
    @Test
    public void testUserclickRegister() throws InterruptedException {
        Intents.init();
        // click on register button
        Espresso.onView(withId(R.id.noAccount)).perform(click());
        Thread.sleep(6000);
        // check the view direct to RegisterActivity class
        intended(hasComponent(RegisterActivity.class.getName()));
        Intents.release();
    }

    //When user click forgotPassword, direct them to the forgotPassword activity
    @Test
    public void testUserclickForgotPassword() throws InterruptedException {
        Intents.init();
        // click on register button
        Espresso.onView(withId(R.id.forgotPassword2)).perform(click());
        Thread.sleep(6000);
        // check the view direct to RegisterActivity class
        intended(hasComponent(ForgotPasswordActivity.class.getName()));
        Intents.release();
    }

    public void tearDown() throws Exception {
    }
}