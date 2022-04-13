package com.example.uscdoordrink;
//Angela Sun

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openContextualActionModeOverflowMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.core.AllOf.allOf;

import androidx.test.core.app.ApplicationProvider.*;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.Root;
import androidx.test.espresso.intent.Intents;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;

import android.content.ComponentName;
import android.view.View;

import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.rule.ActivityTestRule;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.testng.reporters.jq.Main;

public class asUserNavigationActivityTest {
    @Rule
    public ActivityTestRule<UserNavigationActivity> mActivityTestScenario = new ActivityTestRule<UserNavigationActivity>(UserNavigationActivity.class, true, false);
    //public ActivityScenarioRule<mapView> activityScenarioRule = new ActivityScenarioRule<>(mapView.class);
    private String useremail = "mapviewtest@gmail.com";
    private String userpassword = "mapviewtestpassword";
    private String merchantemail = "shore@gmail.com";
    private String merchantpassword = "shorepassword";


    @Before
    public void setUp() throws Exception {


    }

    // verify show map for user in mapview
    //bug found! wasn't loading correctly, in map view
    //fixed: was adding a null location to locationarraylist. added check if null before adding to list
    @Test
    public void testUserViewMap() throws InterruptedException {
//        ActivityScenario scenario = activityScenarioRule.getScenario();
        mActivityTestScenario.launchActivity(null);
        Intents.init();
        Thread.sleep(2000);
        //click menu
        onView(withId(R.id.usertoolbarmenu)).perform(click());
        Thread.sleep(2000);
//        onData(allOf()).atPosition(0).
//                onChildView(withId(R.id.rcOverflow)).
//                perform(click());
        // click view menu
        onView(withId(R.id.ViewMapButton)).perform(click());
        Thread.sleep(2000);
        //verify goes to user map view
        intended(hasComponent(mapView.class.getName()));
        Thread.sleep(2000);
        Intents.release();

    }




    public void tearDown() throws Exception {

    }

}
