package com.example.uscdoordrink;
// Tiffany Chen


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
public class whiteBoxTesting{
    //public ActivityScenarioRule<MainActivity> mActivityRule = new ActivityScenarioRule<>(MainActivity.class);

    //For User testing purpose
    public String email;
    public String password;
    public String name;
    public User user;
    public FirebaseAuth auth;
    public FirebaseDatabase database;
    public String userUid;


    @Before
    public void setuo()
    {
        // For user
        email = "whiteboxtesting@gmail.com";
        password = "whiteBoxTesting";
        name = "whiteBoxTesting";
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        user = new User("whiteBoxTesting", "testing@gmail.com");
        auth.createUserWithEmailAndPassword(email, password);
        userUid = auth.getCurrentUser().getUid();


    }

    // This test is use to test if we create a User
    // will it be added to the User's Firebase Realtime Database
    @Test
    public void testUserInFirebase()
    {
        database.getReference().child("User").child(userUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild("name"))
                {
                    // Check to see if the name in the database matches with
                    // our user name that we created
                    assertEquals(snapshot.getValue().toString(), name);
                }
                if(snapshot.hasChild("email"))
                {
                    // Check to see if the name in the database matches with
                    // our user name that we created
                    assertEquals(snapshot.getValue().toString(), email);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //This test will test DrinkToList method in Drink Class
    @Test
    public void TestDrinkToList()
    {
        List<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
        Drink obj = new Drink();
        Drink d = new Drink("Boba Milk Tea", 5.0, 300.5);
        Drink d1 = new Drink("Milk Tea", 4.0, 134.9);
        Drink d2 = new Drink("Black Tea", 3.5, 120.1);

        // Create a drinkList
        List<Drink> drinkList = new ArrayList<>();
        drinkList.add(d);
        drinkList.add(d1);
        drinkList.add(d2);

        // Create a result ArrayList of List of strings to
        // see if calling DrinkToListFunction matches the result
        ArrayList<String> s = new ArrayList<String>(){
            {
                add("Boba Milk Tea");
                add("5.0");
                add("300.5");
            }
        };
        ArrayList<String> s1 = new ArrayList<String>(){
            {
                add("Milk Tea");
                add("4.0");
                add("134.9");
            }
        };

        ArrayList<String> s2 = new ArrayList<String>(){
            {
                add("Black Tea");
                add("3.5");
                add("120.1");
            }
        };
        result.add(s);
        result.add(s1);
        result.add(s2);

        assertEquals(result, obj.DrinkToList(drinkList));
    }

    // This test is to test AddDrink method in Menu class
    @Test
    public void testAddDrinkinOrder()
    {
        //set up menu object
        Menu menu = new Menu();
        Drink d = new Drink("Boba Milk Tea", 5.0, 300.5);
        Drink d1 = new Drink("Milk Tea", 4.0, 134.9);
        menu.addDrink(d);
        menu.addDrink(d1);
        ArrayList<Drink> result = new ArrayList<Drink>(){
            {
                add(d);
                add(d1);
            }
        };
        //combo... do operation and observe the result
        //check if menu was constructed properly be add drink
        assertEquals(menu.getDrinkList(), result);
    }

    // This test is to test RemoveDrink method in Menu class
    @Test
    public void testRemoveDrinkinOrder()
    {
        //do setup with some data
        Drink d = new Drink("Boba Milk Tea", 5.0, 300.5);
        Drink d1 = new Drink("Milk Tea", 4.0, 134.9);

        ArrayList<Drink> drinks = new ArrayList<Drink>(){
            {
                add(d);
                add(d1);
            }
        };
        Menu menu = new Menu(drinks);
        menu.removeDrink(d);
        ArrayList<Drink> result = new ArrayList<Drink>(){
            {
                add(d1);
            }
        };
        assertEquals(menu.getDrinkList(), result);
    }

    // This test is to test warnCaff method in Order class
    @Test
    public void testwarnCaff()
    {
        //for the same order, warnCaff 300.5 = false but warnCaff 405.1 is true
        Order order = new Order();
        double total = 300.5;
        assertEquals(order.warnCaff(total), false);
        total = 405.1;
        assertEquals(order.warnCaff(total), true);
    }

    //This test is to test addDiscount in Menu.java class

    @Test
    public void testAddDiscount() {

    }

    //This test is to test removeDiscount in Menu.java class

}
