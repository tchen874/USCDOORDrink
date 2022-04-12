package com.example.uscdoordrink;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserOrderHistoryActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ListView history_view;
    Spinner dropdown;
    //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    ArrayList<String> orders_list;
    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String> adapter;










    //old stuff
    DrawerLayout drawerLayout;
    //ListView history;
    Store store;
    //            TextView storeName = (TextView)drinkView.findViewById(R.id.user_drink_name);
    //
    String currentStoreid;
    //ArrayList<ArrayList<String>> orders;
    Button placeOrderButton;
    LinearLayout layout;
    ArrayList<ArrayList<String>> MerchantOrders;

    ArrayList<String> orderHistory;
    //arraylist of order objects that includes
    //ArrayList<Drink> drinks, Customer customer, Merchant seller, Location loc, double caff
    ArrayList<Order> copy_orders;
    //ask tiffany how to go about getting date + time?
   // dont need to de


    //since I'm not passing in ArrayList<Orders>... how else am I going to do OnComplete method


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_order_history);

        //TODO here create notification channel

        drawerLayout = findViewById(R.id.user_drawer_layout);
        //System.out.println("store if=");

        history_view = (ListView) findViewById(R.id.order_chart);
        orders_list = new ArrayList<>();

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                orders_list);

        history_view.setAdapter(adapter);

        //MerchantOrders = new ArrayList<ArrayList<String>>();

        //layout = findViewById(R.id.cart_Drink_layout);

        //get the spinner from the xml.
        Spinner dropdown = findViewById(R.id.spinner1);
        //create a list of items for the spinner.
        //String[] items = new String[]{"day", "month", "year"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.date_choice_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);

        //testing
        //System.out.println("TESTING!!!!! LOOK AT ME");
        //DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        //System.out.println("Usersref : "  + usersRef.getKey());
        //Log.d("Usersref : ", usersRef.getKey());
        //Log.d("orders : ", usersRef.child("orders").child("2").get);
        //Log.d("orders : ", usersRef.child("orders").child("2").get().toString());
        //Log.d("orders: ", usersRef.toString());

        //testing@gmail.com
        //testing123
        loadView();

    }


    // For navigation purpose
    public void UserClickMenu(View view)
    {
        System.out.println("Why this is mot");
        UserNavigationActivity.openDrawer(drawerLayout);
    }
    public void UserClickLogo(View view){
        UserNavigationActivity.closeDrawer(drawerLayout);
    }
    public void UserClickProfile(View view)
    {
        UserNavigationActivity.redirectActivity(this, UserProfileActivity.class);
    }

    public void ClickLogout(View view)
    {
        logout(this);
    }
    public void UserClickOrderHistory(View view)
    {
        recreate();
    }
    public void UserClickAboutUs(View view)
    {
        UserNavigationActivity.redirectActivity(this, UserAboutUsActivity.class);
    }
    public void UserClickViewMap(View view)
    {
        UserNavigationActivity.redirectActivity(this, mapView.class);
    }
    public void UserClickViewStore(View view)
    {
        UserNavigationActivity.redirectActivity(this, User_store.class);
    }

    public static void logout(Activity activity)
    {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(activity);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to log out?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                activity.finishAffinity();
                FirebaseAuth.getInstance().signOut();
                activity.startActivity(new Intent(activity, MainActivity.class));
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.show();
    }
    public void UserClickDeliveryProgress(View view)
    {
        recreate();

    }

    @Override
    protected void onPause()
    {
        super.onPause();
        UserNavigationActivity.closeDrawer(drawerLayout);
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel

    }

    String val;

    private void display_database_data() {

        //TODO change to the current user //String id = FirebaseAuth.getInstance().getCurrentUser().getUid()
//        //DatabaseReference userref = FirebaseDatabase.getInstance().getReference().child("Users").child(id).child("orders");
//        String val;
//        DatabaseReference userref = FirebaseDatabase.getInstance().getReference().child("Users").child("gUXAp4NhQfMBpBTTYV7bJeIQ0jx1").child("orders");
//        //OnCompleteListener<DataSnapshot> task;
//
//
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//
//        db.collection("Users").   child("gUXAp4NhQfMBpBTTYV7bJeIQ0jx1").child("orders")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                               document.
//                            }
//                        } else {
//                            //not sure what to do
//                        }
//                    }
//                });

    }

    private void loadView() {

        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        usersRef.child("orders").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String orderString = "";
                for(DataSnapshot i : snapshot.getChildren()) {
                    for (DataSnapshot s : i.getChildren()) {
                        if (s.getKey().equals("0")) {
                            orderString += "Date order purchased: ";
                        }
                        if (s.getKey().equals("1")) {
                            orderString += "Time order placed: ";
                        }
                        if (s.getKey().equals("2")) {

                            orderString += "Name, price, and caffiene amount: ";
                        }
                        orderString += s.getValue();
                        orderString += " \n";
                    }
                    orders_list.add(orderString);
                    orderString = "";
                }

                adapter.notifyDataSetChanged();



                //instantiate user object with snapshot to set up for getting orders later
                // Customer cust = snapshot.getValue(Customer.class);
                //get an array here that holds orders
               /* history = (ListView) findViewById(R.id.order_chart);
                orders = new ArrayList<>();

                //once we have the data, how are we going to throw it to this method?
                //
                //Customer cust1 = new Customer();
                orders.add("Cust name");
                orders.add("Cust email");

                for (DataSnapshot s : snapshot.getChildren()){
                    List<String> st = (List<String>) s.getValue();

                    if(s.getKey().toString().equals("orders"))
                    {
                        //get value at index?
                       // for(Order o : s){

                        //}
                        //orders.add(s.getValue().toString());
                    }
                }

                adapter.notifyDataSetChanged();
                history.setAdapter(adapter);
*/


                //cust.setCurrOrders();
                //System.out.println(cust.getcurrOrders());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }



        @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {

        //filters by data of user input
        String selected = (String)parent.getItemAtPosition(position);

        //after they select we do the work
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        //System.out.println(usersRef);
        Query q = usersRef.orderByKey();
        ArrayList<String> listItems = new ArrayList<String>();
        //attach listener to query to get the data

            usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //instantiate user object with snapshot to set up for getting orders later
               // Customer cust = snapshot.getValue(Customer.class);
                //get an array here that holds orders
               /* history = (ListView) findViewById(R.id.order_chart);
                orders = new ArrayList<>();

                //once we have the data, how are we going to throw it to this method?
                //
                //Customer cust1 = new Customer();
                orders.add("Cust name");
                orders.add("Cust email");

                for (DataSnapshot s : snapshot.getChildren()){
                    List<String> st = (List<String>) s.getValue();

                    if(s.getKey().toString().equals("orders"))
                    {
                        //get value at index?
                       // for(Order o : s){

                        //}
                        //orders.add(s.getValue().toString());
                    }
                }

                adapter.notifyDataSetChanged();
                history.setAdapter(adapter);
*/


                //cust.setCurrOrders();
                //System.out.println(cust.getcurrOrders());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //testing
        //System.out.println("TESTING!!!!! LOOK AT ME");
        //System.out.println("Usersref : "  + usersRef.getKey());
        Log.d("Usersref : ", usersRef.getKey());
        Log.d("orders : ", usersRef.child("orders").child("2").get().toString());
        //testing@gmail.com
        //testing123



    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    //structuring user order data as hashmap




}