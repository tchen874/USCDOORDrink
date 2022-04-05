package com.example.uscdoordrink;

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

public class UserDeliveryProgress  extends AppCompatActivity implements java.io.Serializable{
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
    public UserDeliveryProgress()
    {

    }

    public UserDeliveryProgress(Store s)
    {
        store = s;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_delivery_progress);
        drawerLayout = findViewById(R.id.user_drawer_layout);

        layout = findViewById(R.id.deliveryLayout);
        storeName = findViewById(R.id.DelievryStoreName);
        storeAddress = findViewById(R.id.DelievryStoreAddress);
        storePhone = findViewById(R.id.DelievryStorePhone);
        oderTime = findViewById(R.id.Delievryordertime);

        orders = new ArrayList<>();
        Intent intent = this.getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        orders = (ArrayList<Drink>) args.getSerializable("ORDERS");


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
            orderTime= (String) savedInstanceState.getSerializable("ORDERTIME");
//            strStoreName = (String) savedInstanceState.getSerializable("STORE_NAME");
//            order= (Order) savedInstanceState.getSerializable("CART");
        }


        System.out.println("Delievry:::: " + orders.size() + currentStoreid);
        loadView();

    }

    private void loadView() {

        DatabaseReference userref = FirebaseDatabase.getInstance().getReference().child("Merchants").child(currentStoreid);
        userref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //shows store name, address, and phone
                for (DataSnapshot s : snapshot.getChildren()){
                    if(s.getKey().toString().equals("storeName"))
                    {
                        storeName.setText(s.getValue().toString());
                    }
                    if(s.getKey().toString().equals("address"))
                    {
                        storeAddress.setText(s.getValue().toString());
                    }
                    if(s.getKey().toString().equals("phoneNumber"))
                    {
                        storePhone.setText(s.getValue().toString());
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
            System.out.println("Oders" + orders.get(i));
            drinkName.setText(orders.get(i).getName());
            drinkPrice.setText(String.valueOf(orders.get(i).getPrice()));
            drinkCaffeine.setText(String.valueOf(orders.get(i).getCaffeine()));

            layout.addView(MenuView);
        }




    }


    // For navigation purpose
    public void UserClickMenu(View view)
    {
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

    public void UserClickViewStore(View view)
    {
        UserNavigationActivity.redirectActivity(this, User_store.class);
    }
    public void UserClickOrderHistory(View view)
    {
        UserNavigationActivity.redirectActivity(this, UserOrderHistoryActivity.class);

    }
    public void UserClickDeliveryProgress(View view)
    {
        recreate();

    }
    public void UserClickAboutUs(View view)
    {
        UserNavigationActivity.redirectActivity(this, UserAboutUsActivity.class);
    }
    public void UserClickViewMap(View view)
    {
        UserNavigationActivity.redirectActivity(this, UserDeliveryProgress.class);
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
        UserNavigationActivity.closeDrawer(drawerLayout);
    }

}

