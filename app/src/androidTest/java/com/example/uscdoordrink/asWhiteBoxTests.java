package com.example.uscdoordrink;
//angela sun

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
public class asWhiteBoxTests {
    //test merchant stuff
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
        //ArrayList<Drink> drinks = new ArrayList<Drink>();

    }

//    public void addDrink(Drink drink){
//        this.orderDrinks.add(drink);
//    }
//
//    //remove drink from cart
//    public void removeDrink(Drink drink){
//        this.orderDrinks.remove(drink);
//    }

    //add drink in order
    @Test
    public void testAddDrink(){
        Order order = new Order();
        Drink d = new Drink("Wintermelon Milk Tea", 4.75, 150);
        Drink d1 = new Drink("Thai Tea", 3.50, 300);


    }



    //remove drink in order


    //add order in store

    //remove order in store

    //orders to hashmap


    public void tearDown() throws Exception {

    }
}
