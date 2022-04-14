package com.example.uscdoordrink;
//Tiffany Chen

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openContextualActionModeOverflowMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.init;
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


// This is test if the user is clicking the navigation with the same current view, it will stay in that page
public class testNagvigation extends TestCase{
    @Rule
    public ActivityTestRule<UserAboutUsActivity> mActivityTestScenario = new ActivityTestRule<UserAboutUsActivity>(UserAboutUsActivity.class, true, false);



    @Before
    public void setUp() throws Exception {
        super.setUp();
        Intents.init();
    }

    // Test to see that it's ok to click the current menu in the toolbar
    @Test
    public void testNavig() throws InterruptedException {
        mActivityTestScenario.launchActivity(null);
        //click menu
        Espresso.onView(withId(R.id.usertoolbarmenu)).perform(click());
        Thread.sleep(3000);

        // click view about us
        onView(withId(R.id.AboutUsButton)).perform(click());
        Thread.sleep(3000);

        // Check to see if it stay in the same page
        // To determine if our page changed
        onView(withId(R.id.aboutUsText)).check(matches(isDisplayed()));
        Thread.sleep(3000);
    }

    public void tearDown() throws Exception {
        Intents.release();
    }

}

