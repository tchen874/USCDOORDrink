package com.example.uscdoordrink;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.intent.Intents;
import androidx.test.rule.ActivityTestRule;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class madiOrderHistNavigationActivityTest {

    @Rule
    public ActivityTestRule<UserNavigationActivity> madiActivityTestScenario = new ActivityTestRule<UserNavigationActivity>(UserNavigationActivity.class, true, false);
    //public ActivityScenarioRule<mapView> activityScenarioRule = new ActivityScenarioRule<>(mapView.class);
    private String useremail = "userhistoryviewtest@gmail.com";
    private String userpassword = "userhistorytestpassword";
    private String merchantemail = "userhistoryviewtestmerchant@gmail.com";
    private String merchantpassword = "userhistoryviewtestmerchantpassword";

    @Before
    public void setUp() throws Exception {

    }

    // test if user click view UserOrderHistoryActivity takes to the menu
    //bug found! same as user view map but in merchant_map_view, wasn't loading correctly
    //fixed: was adding a null location to locationarraylist. added check if null before adding to list
    @Test
    public void testUserOrderHistoryActivity() throws InterruptedException {
        madiActivityTestScenario.launchActivity(null);
        Intents.init();
        Thread.sleep(2000);
        //click menu
        onView(withId(R.id.usertoolbarmenu)).perform(click());
        Thread.sleep(2000);
//        onData(allOf()).atPosition(0).
//                onChildView(withId(R.id.rcOverflow)).
//                perform(click());
        // click view menu
        onView(withId(R.id.UserOrderHistID)).perform(click());
        Thread.sleep(2000);
        //verify goes to user order hist view
        intended(hasComponent(UserOrderHistoryActivity.class.getName()));
        Intents.release();

    }

    public void tearDown() throws Exception {
    }
}

