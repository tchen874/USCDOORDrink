package com.example.uscdoordrink;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class merchantOrderHistoryActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    DrawerLayout drawerLayout;

    //initializing merchant order history requirements
    ListView hist_view; //the listview that I place the objects into
    Spinner dropdown; //the spinner where merchants can filter through the orders based on day, month, or year
    //Arraylist of strings that is the list items
    ArrayList<String> orders_list;
    //list that i originally get and then substring from to create other list
    ArrayList<String> prelimOrderList;
    String timePeriodSelection = "Day"; //default to displaying merchant orders sorted by day
    ArrayAdapter<String> listview_adapter;
    int groupNumber = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_order_history);

        drawerLayout = findViewById(R.id.drawer_layour);

        //Implementing what happens when the MerchantOrderHistoryActivity is called
        //later would like to place this information into a bar chart or type of graph!
        hist_view = (ListView) findViewById(R.id.merchOrderChart);
        //initializing both arrays of orders
        orders_list = new ArrayList<>();
        prelimOrderList = new ArrayList<>();
        //attempting to use same simple_list_item_1 because it is same three words to filter
        listview_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, orders_list);
        hist_view.setAdapter(listview_adapter);

        //get spinner from xml
        dropdown = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.date_choice_array, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(spinnerAdapter);
        //showed an error until I implemented "AdapterView.OnItemSelectedListener"
        dropdown.setOnItemSelectedListener(this);
        
        //call the function that actually implements everything
        loadView();

    }

    private void loadView() {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("Merchants").child(FirebaseAuth.getInstance().getUid());
        usersRef.child("orders").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                prelimOrderList = generateOrderList(snapshot);
                //addGroupDataToListView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }
    private ArrayList<String> generateOrderList(DataSnapshot snapshot) {
        String orderString = "";
        ArrayList<String> ordersList = new ArrayList<>();

        for(DataSnapshot i : snapshot.getChildren()){
            for(DataSnapshot s : i.getChildren()){
                if(s.getKey().equals(0)) {
                    orderString += "Date order purchased: ";
                }
                if (s.getKey().equals("1")) {
                    orderString += "Time order placed: ";
                }
                if (s.getKey().equals("2")) {

                    orderString += "Customer name: ";
                }
                if (s.getKey().equals("3")) {

                    orderString += "Name, price, and caffeine amount: ";
                }
                if(s.getKey().equals("2")){
                    String userIDNumber = s.getValue().toString();
                    System.out.println("User's name from firebase: " + FirebaseDatabase.getInstance().getReference("Users").child(userIDNumber).child("name"));
                    orderString += FirebaseDatabase.getInstance().getReference("Users").child(userIDNumber).child("name");;

                } else {
                    orderString += s.getValue();
                }
                orderString += " \n";

            }
        }




        return ordersList;
    }

    public void ClickMenu(View view)
    {
        Merchant_map_view.openDrawer(drawerLayout);
    }

    public void ClickLogo(View view)
    {
        Merchant_map_view.closeDrawer(drawerLayout);
    }

    public void ClickOrderHistory(View view)
    {
        recreate();
    }

    public void ClickProfile(View view)
    {
        Merchant_map_view.redirectActivity(this, ProfileActivity.class);

    }

    public void ClickEditMenu(View view)
    {
        Merchant_map_view.redirectActivity(this, MerchentEditMenu.class);
    }
    public void ClickViewMap(View view)
    {
        Merchant_map_view.redirectActivity(this, Merchant_map_view.class);

    }

    public void ClickLogout(View view)
    {
        Merchant_map_view.logout(this);
    }
    @Override
    protected void onPause()
    {
        super.onPause();
        Merchant_map_view.closeDrawer(drawerLayout);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}