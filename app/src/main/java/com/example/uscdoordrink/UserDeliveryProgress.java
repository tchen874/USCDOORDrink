package com.example.uscdoordrink;

import static java.lang.Thread.sleep;

import android.app.Activity;
import android.app.AlarmManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.DataOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class UserDeliveryProgress extends AppCompatActivity implements java.io.Serializable{
    DrawerLayout drawerLayout;
    LinearLayout layout;
    TextView storeName;
    TextView storeAddress;
    TextView storePhone;
    TextView oderTime;
    Store store;
    String currentStoreid;
    ArrayList<Drink> orders;
    String orderTime;
    List<List<String>> orderArray;
    DatabaseReference userdatabaseRef;
    TextView userName;
    String travelTime;
    TextView orderStatus;
    TextView orderDeliveryTime;

    public UserDeliveryProgress()
    {

    }

    public UserDeliveryProgress(Store s)
    {
        store = s;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("Delivery Progress enter~~");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_delivery_progress);
        drawerLayout = findViewById(R.id.user_drawer_layout);
        userdatabaseRef = FirebaseDatabase.getInstance().getReference();

        layout = findViewById(R.id.deliveryLayout);
        storeName = findViewById(R.id.DelievryStoreName);
        storeAddress = findViewById(R.id.DelievryStoreAddress);
        storePhone = findViewById(R.id.DelievryStorePhone);
        oderTime = findViewById(R.id.Delievryordertime);
        orderStatus = findViewById(R.id.deliveryorderstatus);
        orderDeliveryTime = findViewById(R.id.deliveryodercompletetime);

        orders = new ArrayList<>();
        Intent intent = this.getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");

        //look at which activity started the intent that started the activity...
        //pass empty arraylist if no one has placed an order yet...
        orders = (ArrayList<Drink>) args.getSerializable("ORDERS");
        orderArray = (List<List<String>>) args.getSerializable("ORDERSDATABASE");


        userName = findViewById(R.id.userName);

        // Get name
        DatabaseReference userref = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        userref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //shows store name, address, and phone
                for (DataSnapshot s : snapshot.getChildren()){
                    if(s.getKey().toString().equals("name")) {
                        userName.setText(s.getValue().toString());
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        for(int i = 0; i < orderArray.size(); i++)
        {
            System.out.println("dta" + orderArray.get(i));
        }
//        userdatabaseRef.child("Users").child("j1auNqBOhAelkibxArBIUUMJrd92").child("orders").setValue(orderArray);
//        System.out.println("user databse ref= " + userdatabaseRef.getReference().toString());

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();


            if(extras == null) {
                currentStoreid= null;
                travelTime = null;
//                storeName = null;
            } else {
                currentStoreid= extras.getString("UID_STRING");
                orderTime = extras.getString("ORDERTIME");
                travelTime = extras.getString("TRAVEL_TIME");
            }
        } else {
            currentStoreid= (String) savedInstanceState.getSerializable("UID_STRING");
            orderTime = (String) savedInstanceState.getSerializable("ORDERTIME");
            travelTime = (String) savedInstanceState.getSerializable("TRAVEL_TIME");
//            strStoreName = (String) savedInstanceState.getSerializable("STORE_NAME");
//            order= (Order) savedInstanceState.getSerializable("CART");
        }

        travelTime = travelTime.replace(" min", "");



        System.out.println("Travel time in delivery::::" + travelTime);
        try {
            loadView();
        } catch (ParseException e) {
            e.printStackTrace();
        }

//        try {
//            Thread.sleep(50000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

//        try {
//            Thread.sleep(50000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        System.out.println("End delivery ");

    }

    //TODO expresso test by calling view
    //e
    private void loadView() throws ParseException {

        orderStatus.setText("Order Status: Complete");

//        orderDeliveryTime.setText();

        DatabaseReference userref = FirebaseDatabase.getInstance().getReference().child("Merchants").child(currentStoreid);
        System.out.println("Userref in delivery" + userref.toString());
        userref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //shows store name, address, and phone
                for (DataSnapshot s : snapshot.getChildren()){
                    if(s.getKey().toString().equals("storeName"))
                    {
                        storeName.setText("Store Name: " + s.getValue().toString());
                    }
                    if(s.getKey().toString().equals("address"))
                    {
                        storeAddress.setText("Address: " + s.getValue().toString());
                    }
                    if(s.getKey().toString().equals("phoneNumber"))
                    {
                        storePhone.setText("Phone: " + s.getValue().toString());
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        oderTime.setText("Order Time: " + orderTime);
        String[] tempstr= orderTime.split(" ");
        System.out.println("DElievry complete time" + tempstr[0] + " index1" + tempstr[1] );
        SimpleDateFormat df = new SimpleDateFormat("HH:mm::ss");
        Date d = df.parse(tempstr[1]);
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.MINUTE, (Integer.parseInt(travelTime) + 7));
        cal.add(Calendar.SECOND,  7);
        String newTime = df.format(cal.getTime());
        orderDeliveryTime.setText("Order Complete: " + tempstr[0] + " " + newTime);




        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 6);// for 6 hour
        calendar.set(Calendar.MINUTE, 0);// for 0 min
        calendar.set(Calendar.SECOND, 0);// for 0 sec
        System.out.println(calendar.getTime());


        for(int i = 0; i < orders.size(); i++)
        {
            final View MenuView = getLayoutInflater().inflate(R.layout.activity_drink_in_delivery,null,false);
            TextView drinkName = (TextView)MenuView.findViewById(R.id.drink_name);
            TextView drinkPrice = (TextView)MenuView.findViewById(R.id.drink_price);
            TextView drinkCaffeine = (TextView)MenuView.findViewById(R.id.drink_caffeine);
            System.out.println("Orders in delivery" + orders.get(i));
            drinkName.setText(orders.get(i).getName());
            drinkPrice.setText(String.valueOf(orders.get(i).getPrice()));
            drinkCaffeine.setText(String.valueOf(orders.get(i).getCaffeine()));

            layout.addView(MenuView);
        }
    }

    // For navigation purpose
    public void UserClickMenu(View view)
    {
        mapView.openDrawer(drawerLayout);
    }


    public void UserClickLogo(View view){
        mapView.closeDrawer(drawerLayout);
    }


    public void UserClickProfile(View view)
    {
        mapView.redirectActivity(this, UserProfileActivity.class);
    }

    public void ClickLogout(View view)
    {
        logout(this);
    }


    public void UserClickMainOrderHistory(View view)
    {
        mapView.redirectActivity(this, mainTabLayout.class);

    }

    public void UserClickAboutUs(View view)
    {
        mapView.redirectActivity(this, UserAboutUsActivity.class);
    }
    public void UserClickViewMap(View view)
    {
        mapView.redirectActivity(this, mapView.class);
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

    @Override
    protected void onPause()
    {
        super.onPause();
        mapView.closeDrawer(drawerLayout);
    }

}

