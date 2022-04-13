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

public class asMerchantNavigationActivityTest extends TestCase {
    @Rule
    public ActivityTestRule<MerchantNavigationActivity> mActivityTestScenario = new ActivityTestRule<MerchantNavigationActivity>(MerchantNavigationActivity.class, true, false);
    //public ActivityScenarioRule<mapView> activityScenarioRule = new ActivityScenarioRule<>(mapView.class);
    private String useremail = "mapviewtest@gmail.com";
    private String userpassword = "mapviewtestpassword";
    private String merchantemail = "shore@gmail.com";
    private String merchantpassword = "shorepassword";

    @Before
    public void setUp() throws Exception {

    }

    // test if merchant click view map on menu takes to the menu
    //bug found! same as user view map but in merchant_map_view, wasn't loading correctly
    //fixed: was adding a null location to locationarraylist. added check if null before adding to list
    @Test
    public void testMerchantViewMap() throws InterruptedException {
        mActivityTestScenario.launchActivity(null);
        Intents.init();
        Thread.sleep(2000);
        //click menu
        onView(withId(R.id.merchanttoolbarmenu)).perform(click());
        Thread.sleep(2000);
//        onData(allOf()).atPosition(0).
//                onChildView(withId(R.id.rcOverflow)).
//                perform(click());
        // click view menu
        onView(withId(R.id.viewMerchantMapButton)).perform(click());
        Thread.sleep(2000);
        //verify goes to user map view
        intended(hasComponent(Merchant_map_view.class.getName()));
        Intents.release();

    }

    public void tearDown() throws Exception {
    }
}