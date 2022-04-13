package com.example.uscdoordrink;
//Angela Sun

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


    //add drink in order
    @Test
    public void testAddDrinkInOrder(){
        Order order = new Order();
        Drink d1 = new Drink("Wintermelon Milk Tea", 4.75, 150);
        Drink d2 = new Drink("Thai Tea", 3.50, 300);
        order.addDrink(d1);
        order.addDrink(d2);
        ArrayList<Drink> tempDrinkList = new ArrayList<Drink>(){
            {
                add(d1);
                add(d2);
            }
        };
        assertEquals("add drinks test", order.getOrderDrinks(), tempDrinkList);

    }

    //remove existing drink in order
    @Test
    public void testRemoveDrinkInOrder(){
        Order order = new Order();
        Drink d1 = new Drink("Wintermelon Milk Tea", 4.75, 150);
        Drink d2 = new Drink("Thai Tea", 3.50, 300);
        order.addDrink(d1);
        order.addDrink(d2);
        order.removeDrink(d1);
        ArrayList<Drink> tempDrinkList = new ArrayList<Drink>(){
            {
                add(d2);
            }
        };
        assertEquals("remove drinks test", order.getOrderDrinks(), tempDrinkList);
    }

    //remove drink in empty order
    //bug!! didn't check if drink was in order before removing
    //bug fixed :)
    @Test
    public void testRemoveEmptyDrinkInOrder(){
        Order order = new Order();
        Drink d1 = new Drink("Wintermelon Milk Tea", 4.75, 150);
        order.removeDrink(d1);

        ArrayList<Drink> tempDrinkList = new ArrayList<Drink>() {};
        assertEquals("remove empty drinks test", order.getOrderDrinks(), tempDrinkList);
    }

    //add order in store
    @Test
    public void testAddOrderInStore(){
        Store store = new Store();
        Order order1 = new Order();
        Order order2 = new Order();
        store.addOrder(order1);
        store.addOrder(order2);

        ArrayList<Order> ordersList = new ArrayList<Order>();
        ordersList.add(order1);
        ordersList.add(order2);
        assertEquals("add order to store", store.getStoreOrders(), ordersList);
    }

    //remove order in store
    @Test
    public void testRemoveOrderInStore(){
        Store store = new Store();
        Order order1 = new Order();
        Order order2 = new Order();
        store.addOrder(order1);
        store.addOrder(order2);
        store.removeOrder(order1);

        ArrayList<Order> ordersList = new ArrayList<Order>();
        ordersList.add(order2);
        assertEquals("remove order from store", store.getStoreOrders(), ordersList);
    }

    //remove order from empty store
    //bug found! need to check list has order before removing
    //bug fixed :)
    @Test
    public void testRemoveEmptyOrderInStore(){
        Store store = new Store();
        Order order1 = new Order();
        store.removeOrder(order1);

        ArrayList<Order> ordersList = new ArrayList<Order>();
        assertEquals("remove order from empty store", store.getStoreOrders(), ordersList);
    }


    public void tearDown() throws Exception {

    }
}
