package com.example.uscdoordrink;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class Cart extends AppCompatActivity implements View.OnClickListener, java.io.Serializable {
    Store store;
    String currentStoreid;
    Order order;
    ArrayList<Drink> cart;

    TextView storeName;
    TextView address;
    TextView Phonenumber;
    Button placeOrderButton;
    LinearLayout layout;
    DrawerLayout drawerLayout;
    ArrayList<ArrayList<String>> orders;
    ArrayList<ArrayList<String>> MerchantOrders;

    public Cart()
    {

    }
    public Cart(Store s)
    {
        store = s;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        System.out.println("stroe ifssssqqqqqq====");

        orders = new ArrayList<ArrayList<String>>();
        MerchantOrders = new ArrayList<ArrayList<String>>();



        cart = new ArrayList<>();

        Intent intent = this.getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        //from USER_STORE because once you click checkout button, you need to be able to know what order you've added
        //
        cart = (ArrayList<Drink>) args.getSerializable("CART");
//        cart = this.getIntent().getExtras().getParcelableArrayList("CART");
//        ArrayList<Drink> t = this.getIntent().getParcelableArrayList("CART");

        storeName = findViewById(R.id.cartStoreName);
        address = findViewById(R.id.cartStoreAddress);
        Phonenumber = findViewById(R.id.cartStorePhone);

        placeOrderButton = findViewById(R.id.PlaceOrder);
        placeOrderButton.setOnClickListener(this);

        layout = findViewById(R.id.cart_Drink_layout);
        drawerLayout = findViewById(R.id.user_drawer_layout);


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();


            if(extras == null) {
                currentStoreid= null;
//                storeName = null;
            } else {
                currentStoreid= extras.getString("UID_STRING");
            }
        } else {
            //when click map view needs to know what store it is
            // in user store, once user clicks add to cart she needs to know which store you're looking at
            currentStoreid= (String) savedInstanceState.getSerializable("UID_STRING");
//            strStoreName = (String) savedInstanceState.getSerializable("STORE_NAME");
//            order= (Order) savedInstanceState.getSerializable("CART");
        }
        //wanted to see if current store ID is being passed to cart class
        System.out.println("stroe ifssss====");
        System.out.println("stroe if====" + currentStoreid + cart.get(0).getPrice());
        loadView();
    }

    private void loadView() {

        //get dat reference
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Merchants").child(currentStoreid);
        ref.addValueEventListener(new ValueEventListener() {
            //getting data from the snapshot
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
                        address.setText(s.getValue().toString());
                    }
                    if(s.getKey().toString().equals("phoneNumber"))
                    {
                        Phonenumber.setText(s.getValue().toString());
                    }
                    if(s.getKey().toString().equals("orders"))
                    {
                        ArrayList<String> order = (ArrayList<String>) s.getValue();
                        MerchantOrders.add(order);

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //TODO CHANGE uid

        DatabaseReference userref = FirebaseDatabase.getInstance().getReference().child("Users").child("gUXAp4NhQfMBpBTTYV7bJeIQ0jx1").child("orders");
        userref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //shows store name, address, and phone

                for (DataSnapshot s : snapshot.getChildren()){
                    System.out.println("orderssss= " + s.getValue());
                    //getting values and adding it to the cart
                    ArrayList<String> l = (ArrayList<String>) s.getValue();
                    orders.add(l);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // drinks in the cart
        for(Drink d: cart)
        {
            //getting references to the view on screen sent over to activity
            //
            final View drinkView = getLayoutInflater().inflate(R.layout.activity_drink_in_cart,null,false);
            TextView drinkName = (TextView)drinkView.findViewById(R.id.user_drink_name);
            TextView drinkPrice = (TextView)drinkView.findViewById(R.id.user_drink_price);
            TextView drinkCaffeine = (TextView)drinkView.findViewById(R.id.user_drink_caffeine);
            ImageView imageRemove = (ImageView)drinkView.findViewById(R.id.user_image_close);
            imageRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    removedrink(drinkView);
                }
            });

            drinkName.setText(d.getName());
            drinkPrice.setText(String.valueOf(d.getPrice()));
            drinkCaffeine.setText(String.valueOf(d.getCaffeine()));
//            System.out.println("testing" + ((TextView)drinkView.getRootView().findViewById(R.id.drink_name)).getText().toString());
            layout.addView(drinkView);

        }

    }

    private void removedrink(View view) {
        System.out.println("Enetrein=");
        String drinkname = ((TextView)view.getRootView().findViewById(R.id.user_drink_name)).getText().toString();
        Double drinkprice = Double.valueOf(((TextView)view.getRootView().findViewById(R.id.user_drink_price)).getText().toString());
        Double drinkCaffeine = Double.valueOf(((TextView)view.getRootView().findViewById(R.id.user_drink_caffeine)).getText().toString());
//        Drink d = new Drink(drinkname, drinkprice, drinkCaffeine);

        for(Drink d : cart)
        {
            if(d.getName().contains(drinkname) && d.getPrice() == drinkprice && d.getCaffeine() == drinkCaffeine)
            {
                cart.remove(d);
                break;
            }
        }
//        cart.remove(d);

        System.out.println("my cart=" + cart.size());
        layout.removeView(view);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.PlaceOrder:
                // Set order to the database and direct to the delivery information
//                System.out.println("In the click the button");


                //TODO: Add the Hashmap<key: date, value= List of drink>
                //TODO change to the current user //FirebaseAuth.getInstance().getCurrentUser().getUid()
                // Sent order to merchant
                HashMap<ArrayList<String>, ArrayList<Drink>> orderMap = new HashMap<ArrayList<String>, ArrayList<Drink>>();


                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
                System.out.println("Date======= " + formatter.format(date).toString());
                SimpleDateFormat minformatter = new SimpleDateFormat("HH:mm::ss");

                String dateStr = formatter.format(date).toString();
                String timeStr = minformatter.format(date).toString();

                ArrayList<String> orderArray = new ArrayList<String>();
                orderArray.add(dateStr);
                orderArray.add(timeStr);

                for(int i = 0; i < cart.size(); i++)
                {
                    orderArray.add(cart.get(i).toString());
                }
//                ArrayList<ArrayList<String>> orders= new ArrayList<ArrayList<String>>();

                orders.add(orderArray);


//                orderMap.put(dateArray, cart);
                // Add to merchant database
                // {date, user uid, }
                ArrayList<String> temp = new ArrayList<>();

                temp.add(dateStr);
                temp.add(timeStr);

                temp.add("gUXAp4NhQfMBpBTTYV7bJeIQ0jx1");
                for(int i = 0; i < cart.size(); i++)
                {
                    temp.add(cart.get(i).toString());
                }
                MerchantOrders.add(temp);
                FirebaseDatabase.getInstance().getReference("Merchants").child(currentStoreid).child("orders")
                        .setValue(MerchantOrders);
                //TODO:FirebaseAuth.getInstance().getCurrentUser().getUid()
                FirebaseDatabase.getInstance().getReference("Users").child("gUXAp4NhQfMBpBTTYV7bJeIQ0jx1").child("orders")
                        .setValue(orders);

                // direct back to the Progress View;
                // Pass the cart information to user
                UserDeliveryProgress userdelivery = new UserDeliveryProgress();
                Intent intent = new Intent(Cart.this, userdelivery.getClass());
                intent.putExtra("UID_STRING", currentStoreid);
                intent.putExtra("ORDERTIME", dateStr + " " + timeStr);


                Bundle bundle = new Bundle();
                bundle.putSerializable("ORDERS", (Serializable) cart);
                intent.putExtra("BUNDLE", bundle);

                startActivity(intent);

//                startActivity(new Intent(this, UserDeliveryProgress.class));

                break;
        }
    }


    // Navigation Purpose
    // For navigation purpose
    public void UserClickMenu(View view)
    {
//        System.out.println("Why this is mot");
        UserNavigationActivity.openDrawer(drawerLayout);
    }


    public void UserClickLogo(View view){
        UserNavigationActivity.closeDrawer(drawerLayout);
    }


    public void UserClickProfile(View view)
    {
        UserNavigationActivity.redirectActivity(this, UserProfileActivity.class);
    }
    public void UserClickViewStore(View view)
    {
        UserNavigationActivity.redirectActivity(this, User_store.class);
    }

    public void ClickLogout(View view)
    {
        logout(this);
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
