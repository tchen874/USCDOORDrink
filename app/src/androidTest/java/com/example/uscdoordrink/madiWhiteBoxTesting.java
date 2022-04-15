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
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
    public void seperateOrderList() {
        //mimicking generateOrderList method

        String orderString1 = "Date order purchased: 2025/03/29\nTime order placed: XX::50::18\nNAME, price, and caffeine amount: drinkName='monkfruitjuice', price=29.0, caffiene=1000.0";
        String orderString2 = "Date order purchased: 2025/03/29\nTime order placed: XX::50::18\nNAME, price, and caffeine amount: drinkName='monkfruitjuice', price=29.0, caffiene=1000.0";
        String orderString = "Date order purchased: 2022/03/29\nTime order placed: 21::50::18\nName, price, and caffeine amount: drinkName='Starbucks', price=29.0, caffiene=1000.0";
        ArrayList<String> preliminary_orders_list = new ArrayList<>();
        preliminary_orders_list.add(orderString1);
        preliminary_orders_list.add(orderString);
        preliminary_orders_list.add(orderString2);

        int max = Integer.MIN_VALUE;

        HashMap<String, Integer> map = new HashMap<>();
        for (String order: preliminary_orders_list) {
            int indexOfDrinkName = order.indexOf("drinkName='");
            int startIndex = indexOfDrinkName + 10;
            int endIndex = order.indexOf(", price=");
            String recDrinkName = order.substring(startIndex, endIndex);
            if(!map.containsKey(recDrinkName)){
                map.put(recDrinkName, 1);
            } else {
                int numOccurences = map.get(recDrinkName);
                map.put(recDrinkName, ++numOccurences);
            }
        }
        HashMap<String, String> mapResultExpected = new HashMap<>();
        mapResultExpected.put("'Starbucks'=1", "'monkfruitjuice=2'");

        assertEquals(mapResultExpected, map);


    }

}
