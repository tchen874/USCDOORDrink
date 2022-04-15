package com.example.uscdoordrink;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.intent.Intents;
import androidx.test.rule.ActivityTestRule;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class madiOrderHistNavigationActivityTest {

    @Rule
   // public ActivityTestRule<MainActivity> mMainActivity = new ActivityTestRule<MainActivity>(MainActivity.class);
    //public ActivityTestRule<UserNavigationActivity> madiActivityTestScenario = new ActivityTestRule<UserNavigationActivity>(UserNavigationActivity.class, true, false);
    //public ActivityScenarioRule<mapView> activityScenarioRule = new ActivityScenarioRule<>(mapView.class);
    public ActivityTestRule<MainActivity> mActivityTestScenario = new ActivityTestRule<MainActivity>(MainActivity.class);
    private String email = "testing@gmail.com";
    private String password = "testing123";

    //merchant account
    private String merchantemail = "shore@gmail.com";
    private String merchantpassword = "shorepassword";


    @Before
    public void setUp() throws Exception {
        Intents.init();
    }

    // test if user click view UserOrderHistoryActivity takes to the menu
    //bug found! same as user view map but in merchant_map_view, wasn't loading correctly
    //fixed: was adding a null location to locationarraylist. added check if null before adding to list
  /*  @Test
    public void testUserOrderHistoryActivity() throws InterruptedException {

        // input email and password
        Espresso.onView(withId(R.id.editTextTextEmailAddress)).perform(typeText(email));
        Espresso.onView(withId(R.id.editTextTextPassword)).perform(typeText(password));
        // close keyboard
        Espresso.closeSoftKeyboard();

        // click login button
        Espresso.onView(withId(R.id.loginButton)).perform(click());
        Thread.sleep(6000); //takes time to load!

        //madiActivityTestScenario.launchActivity(null);
        //click menu
        onView(withId(R.id.usertoolbarmenu)).perform(click());
        Thread.sleep(2000);
//        onData(allOf()).atPosition(0).
//                onChildView(withId(R.id.rcOverflow)).
//                perform(click());
        // click view menu
        //onView(withId(R.id.user_drawer_layout)).perform(click());
        //Thread.sleep(2000);
        //verify goes to user order hist view

        intended(hasComponent(UserNavigationActivity.class.getName()));
        //Intents.release();

    }*/
    @Test
    public void LoginWithUserNavHistoryActivity() throws InterruptedException {

        // input email and password
        Espresso.onView(withId(R.id.editTextTextEmailAddress)).perform(typeText(email));
        Espresso.onView(withId(R.id.editTextTextPassword)).perform(typeText(password));
        // close keyboard
        Espresso.closeSoftKeyboard();

        // click login button
        Espresso.onView(withId(R.id.loginButton)).perform(click());
        Thread.sleep(6000); //takes time to load!

        //click menu
        onView(withId(R.id.usertoolbarmenu)).perform(click());
        Thread.sleep(2000);

        intended(hasComponent(UserNavigationActivity.class.getName()));
        Intents.release();

    }
    @Test
    public void testInvalidUsername() throws InterruptedException {

        // input email and password
        onView(withId(R.id.editTextTextEmailAddress)).perform(typeText("username@fakeEmail.com"));
        onView(withId(R.id.editTextTextPassword)).perform(typeText(password));
        // close keyboard
        Espresso.closeSoftKeyboard();

        // click login button
        onView(withId(R.id.loginButton)).perform(click());

        //check that log in button is still on screen, meaning activity stays the same
        // can't check for another intent since no intent started lol :(
        onView(withId(R.id.loginButton)).check(matches(isDisplayed()));
        Intents.release();


    }

    @Test
    public void LoginWithUserAccessingMerchantMenu() throws InterruptedException {

        // input user email and password
        Espresso.onView(withId(R.id.editTextTextEmailAddress)).perform(typeText(email));
        Espresso.onView(withId(R.id.editTextTextPassword)).perform(typeText(password));
        // close keyboard
        Espresso.closeSoftKeyboard();

        // click login button
        Espresso.onView(withId(R.id.loginButton)).perform(click());
        Thread.sleep(6000); //takes time to load!

        //click Merchant menu but logged in as user... should not work!
        onView(withId(R.id.merchanttoolbarmenu)).check(doesNotExist());
        Thread.sleep(2000);
        Intents.release();
    }

    @Test
    public void LoginWithMerchantAccessingUserMenu() throws InterruptedException {

        // input user email and password
        Espresso.onView(withId(R.id.editTextTextEmailAddress)).perform(typeText(merchantemail));
        Espresso.onView(withId(R.id.editTextTextPassword)).perform(typeText(merchantpassword));
        // close keyboard
        Espresso.closeSoftKeyboard();

        // click login button
        Espresso.onView(withId(R.id.loginButton)).perform(click());
        Thread.sleep(6000); //takes time to load!

        //click Merchant menu but logged in as user... should not work!
        onView(withId(R.id.usertoolbarmenu)).check(doesNotExist());
        Thread.sleep(2000);
        Intents.release();
    }

    @Test
    public void LoginWithMerchantAccessingUserMapView() throws InterruptedException {

        // input user email and password
        Espresso.onView(withId(R.id.editTextTextEmailAddress)).perform(typeText(merchantemail));
        Espresso.onView(withId(R.id.editTextTextPassword)).perform(typeText(merchantpassword));
        // close keyboard
        Espresso.closeSoftKeyboard();

        // click login button
        Espresso.onView(withId(R.id.loginButton)).perform(click());
        Thread.sleep(3000); //takes time to load!

        //click Merchant menu but logged in as user... should not work!
        onView(withId(R.id.merchanttoolbarmenu)).perform(click());
        Thread.sleep(2000);

        // click view menu
        onView(withId(R.id.ViewMapButton)).check(doesNotExist());
        Thread.sleep(2000);
        Intents.release();
    }

    public void tearDown() throws Exception {
    }
}

