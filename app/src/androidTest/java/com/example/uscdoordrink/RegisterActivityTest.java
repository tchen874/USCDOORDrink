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

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class RegisterActivityTest extends TestCase {

    @Rule
    public ActivityTestRule<RegisterActivity> mActivityTestScenario = new ActivityTestRule<RegisterActivity>(RegisterActivity.class, true, false);
    private String userName = "UserTest";
    private String userEmail = "usertesting@gmail.com";
    private String userPassword = "usertesting";

    private String merchantName = "MerchantTest";
    private String merchantEmail = "merchanttesting@gmail.com";
    private String merchantPassword = "merchanttesting";

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    // Test to see register User
    // Direct to userNavigationView
    // Found bugs!!!!! -> It direct to mapView instead of User Navigation View
    //Fixed
    @Test
    public void testRegisterUser() throws InterruptedException {
        mActivityTestScenario.launchActivity(null);

        Intents.init();
        // input the name, email and password in the edit text
        Espresso.onView(withId(R.id.editTextTextPersonName3)).perform(typeText(userName));
        Espresso.onView(withId(R.id.editTextTextEmailAddress5)).perform(typeText(userEmail));
        Espresso.onView(withId(R.id.editTextTextPassword4)).perform(typeText(userPassword));

        // close the keybard
        Espresso.closeSoftKeyboard();
        // perform signup button
        Espresso.onView(withId(R.id.signupButton3)).perform(click());
        Thread.sleep(7000);
        // check the view direct to userNavigationView class
        intended(hasComponent(UserNavigationActivity.class.getName()));
        Intents.release();

        //Delete the new user we created in this test from firebaseAuth
        // so next time we call this function, we will able to register again
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.delete();
    }

    // Test to see register Merchant
    // Direct to MerchantNavigationView
    @Test
    public void testRegisterMerchant() throws InterruptedException {
        mActivityTestScenario.launchActivity(null);
        Intents.init();
        // input the name, email and password in the edit text
        Espresso.onView(withId(R.id.editTextTextPersonName3)).perform(typeText(merchantName));
        Espresso.onView(withId(R.id.editTextTextEmailAddress5)).perform(typeText(merchantEmail));
        Espresso.onView(withId(R.id.editTextTextPassword4)).perform(typeText(merchantPassword));
        Espresso.onView(withId(R.id.isMerchantSwitch)).perform(click());

        // close the keybard
        Espresso.closeSoftKeyboard();
        // perform signup button
        Espresso.onView(withId(R.id.signupButton3)).perform(click());
        Thread.sleep(6000);
        // check the view direct to userNavigationView class
        intended(hasComponent(MerchantNavigationActivity.class.getName()));
        Intents.release();

        //Delete the new user we created in this test from firebaseAuth
        // so next time we call this function, we will able to register again
        final FirebaseUser merchant = FirebaseAuth.getInstance().getCurrentUser();
        merchant.delete();

    }

    public void tearDown() throws Exception {
    }
}