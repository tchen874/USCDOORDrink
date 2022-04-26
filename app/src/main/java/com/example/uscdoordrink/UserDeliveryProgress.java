package com.example.uscdoordrink;

import static java.lang.Thread.sleep;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

import java.util.ArrayList;
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
//                storeName = null;
            } else {
                currentStoreid= extras.getString("UID_STRING");
                orderTime = extras.getString("ORDERTIME");
            }
        } else {
            currentStoreid= (String) savedInstanceState.getSerializable("UID_STRING");
            orderTime = (String) savedInstanceState.getSerializable("ORDERTIME");
//            strStoreName = (String) savedInstanceState.getSerializable("STORE_NAME");
//            order= (Order) savedInstanceState.getSerializable("CART");
        }


        System.out.println("Delievry:::: " + orders.size() + currentStoreid);
        loadView();

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
    private void loadView() {

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

