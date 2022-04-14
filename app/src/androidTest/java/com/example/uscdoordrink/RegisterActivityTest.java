package com.example.uscdoordrink;

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

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class RegisterActivityTest extends TestCase {

    @Rule
    public ActivityTestRule<RegisterActivity> mActivityTestScenario = new ActivityTestRule<RegisterActivity>(RegisterActivity.class, true, true);
    public ActivityTestRule<RegisterActivity> mActivityTest = new ActivityTestRule<RegisterActivity>(RegisterActivity.class, true, true);

    private String userName = "UserTest";
    private String userEmail = "usertesting1@gmail.com";
    private String userPassword = "usertesting";

    private String merchantName = "MerchantTest";
    private String merchantEmail = "merchanttesting1@gmail.com";
    private String merchantPassword = "merchanttesting";

    @Before
    public void setUp() throws Exception {
        super.setUp();
        Intents.init();
    }



    // Test to see register User
    // Direct to merchantNavigationView
    // Found bugs!!!!! -> It direct to mapView instead of Merchant Navigation View
    //Fixed
    @Test
    public void testRegister() throws InterruptedException {
        mActivityTestScenario.launchActivity(null);

        // input the name, email and password in the edit text
        Espresso.onView(withId(R.id.editTextTextPersonName3)).perform(typeText(merchantName));
        Espresso.onView(withId(R.id.editTextTextEmailAddress5)).perform(typeText(merchantEmail));
        Espresso.onView(withId(R.id.editTextTextPassword4)).perform(typeText(merchantPassword));
        Espresso.onView(withId(R.id.isMerchantSwitch)).perform(click());

        // close the keybard
        Espresso.closeSoftKeyboard();
        // perform signup button
        Espresso.onView(withId(R.id.signupButton3)).perform(click());
        Thread.sleep(4000);
        // check the view direct to userNavigationView class
        intended(hasComponent(MerchantNavigationActivity.class.getName()));

        //Delete the new user we created in this test from firebaseAuth
        // so next time we call this function, we will able to register again
        final FirebaseUser merchant = FirebaseAuth.getInstance().getCurrentUser();
        merchant.delete();
        mActivityTestScenario.finishActivity();


    }
    @After
    public void tearDown() throws Exception {
        Intents.release();
    }
}