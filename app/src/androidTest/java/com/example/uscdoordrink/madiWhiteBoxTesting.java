package com.example.uscdoordrink;

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
       // email = "whiteboxtesting@gmail.com";
       // password = "whiteBoxTesting";
       // name = "whiteBoxTesting";
       // auth = FirebaseAuth.getInstance();
       // database = FirebaseDatabase.getInstance();
      //  user = new User("whiteBoxTesting", "testing@gmail.com");
      //  auth.createUserWithEmailAndPassword(email, password);
      //  userUid = auth.getCurrentUser().getUid();


    }


    //first test is is preliminary_orders_list is what we want it to be
    //we expect that it is all of the orders from the database
    //will add to database what we want
    @Test
    public void testGenerateOrderList(){
        //create user
        //create orders
        //DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        //DataSnapshot snap = usersRef.child("orders");

        //go to database and call method and get the list

    }




    @Test
    public void testAddDiscount() {
        //create drinks with fake var

        //create discounts with fake var


        //create arraylist of drinks
        //create arraylist of discounts

        //instantiate a new menu and pass in drink array
    }

    //This test is to test removeDiscount in Menu.java class

}
