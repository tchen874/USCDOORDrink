package com.example.uscdoordrink;
//Madison Brading
import org.junit.Test;

import androidx.annotation.NonNull;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.apache.tools.ant.taskdefs.condition.Or;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;


@RunWith(AndroidJUnit4.class)
public class madiWhiteBoxTesting {

    //This test is to test addDiscount in Menu.java class
    //For User testing purpose
    public String email;
    public String password;
    public String name;
    public User user;
    public FirebaseAuth auth;
    public FirebaseDatabase database;
    public String userUid;


    @Before
    public void setup()
    {
        // For user
        email = "whiteboxtesting@gmail.com";
        password = "whiteBoxTesting";
        name = "whiteBoxTesting";
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        user = new User("whiteBoxTesting", "testing@gmail.com");
        auth.createUserWithEmailAndPassword(email, password);
        userUid = "Wj1qHzXETpdoSpyvjoIhn08v3f92";
    }


    //first test is is preliminary_orders_list is what we want it to be
    //we expect that it is all of the orders from the database
    //will add to database what we want
    //mimics how i add orders into snapshot and then compare
    @Test
    public void testGenerateOrderList(){
        ArrayList<String> snapshot = new ArrayList<>();
        ArrayList<Integer> keys = new ArrayList<>();
        keys.add(0);
        keys.add(1);
        keys.add(2);

        for(int i = 0; i < 3; i++){
            if(keys.get(i)==0){
                snapshot.add("Date order purchased: ");
            }
            if(keys.get(i)==1){
                snapshot.add("Time order placed: ");
            }
            if(keys.get(i)==2){
                snapshot.add("Name, price, and caffeine amount: ");
            }

        }

        String orderString = "";
        String key = "day";
        ArrayList<String> ordersList = new ArrayList<>();
        ordersList.add("Date order purchased: ");
        ordersList.add("Time order placed: ");
        ordersList.add("Name, price, and caffeine amount: ");

        assertEquals(ordersList, snapshot);


    }

    //method to test whether we are properly getting the date out
    // of the orderlist we created from datasnapshot
    @Test
    public void testGetDateFromOrder(){
        //same implementation method getDateFromOrder(String order) where we have an order
        String orderString = "Date order purchased: 2022/03/29\nTime order placed: 21::50::18\nName, price, and caffeine amount: drinkName='Starbucks', price=29.0, caffiene=1000.0";
        orderString = orderString.substring(22, 32);


        String resultString = orderString;

        String expectedString = "2022/03/29";
        assertEquals(expectedString, resultString);

    }


    //This test is to test addDiscount in Menu.java class

    @Test
    public void testAddDiscount() {
        //create drinks with fake var
        Menu menu = new Menu();
        Drink d = new Drink("Starbucks frapp", 6.0, 300.9);
        Drink d1 = new Drink("blueberry smoothie", 3.7, 120.4);
        menu.addDrink(d);
        menu.addDrink(d1);
        //create discounts with fake var

        Double discount = .30;
        Double discount1 = .60;
        menu.addDiscount(discount);
        menu.addDiscount(discount1);

        //create arraylist of drinks
        //create arraylist of discounts
        ArrayList<Drink> resultDrink = new ArrayList<Drink>(){
            {
                add(d);
                add(d1);
            }
        };
        ArrayList<Double> resultDiscount = new ArrayList<Double>(){
            {
                add(discount);
                add(discount1);
            }
        };
        assertEquals(menu.getDiscountList(), resultDiscount);
        //instantiate a new menu and pass in drink array
    }

    //This test is to test removeDiscount in Menu.java class
    @Test
    public void testRemoveDiscount() {
        //create drinks with fake var
        //Menu menu = new Menu();
        Drink d = new Drink("Starbucks frapp", 6.0, 300.9);
        Drink d1 = new Drink("blueberry smoothie", 3.7, 120.4);

        Double discount2 = .30;
        Double discount3 = .60;

        ArrayList<Drink> drinks = new ArrayList<Drink>(){
            {
                add(d);
                add(d1);
            }
        };

        ArrayList<Double> discounts = new ArrayList<Double>(){
            {
                add(discount2);
                add(discount3);
            }
        };

        Menu menu = new Menu(drinks, discounts);

        menu.removeDiscount(discount2);

        ArrayList<Double> result = new ArrayList<Double>(){
            {
                add(discount3);
            }
        };


        assertEquals(menu.getDiscountList(), result);
        //instantiate a new menu and pass in drink array
    }

    //This test is to test onItemSelected in Menu.java class
    @Test
    public void onItemSelectedTest() {
        Store store = new Store();
        Order order1 = new Order();
        store.removeOrder(order1);

        ArrayList<Order> ordersList = new ArrayList<Order>();
        assertEquals("remove order from empty store", store.getStoreOrders(), ordersList);

    }

}
