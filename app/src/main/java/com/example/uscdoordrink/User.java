package com.example.uscdoordrink;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.utils.ViewTimeCycle;
import androidx.constraintlayout.motion.widget.ViewTransitionController;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.text.CollationElementIterator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class User {

    public String name;
    public String email;
    private List<String> merchant;
    private ArrayList<ArrayList<String>> MerchantOrders;
    private ArrayList<ArrayList<String>> UserOrders;

    private View Phonenumber;

    public User() {
        merchant = new ArrayList<String>();
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

   /* public List<String> getMerchant() {
        return merchant;
    }

    public void setMerchant(List<String> merchant) {
        this.merchant = merchant;
    }*/



    static void viewAccount(){
        //TODO
        //visiting your own profile

    }
    static void viewHistory(){
        //TODO
        //return UserOrders;
    }
/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        System.out.println("stroe ifssssqqqqqq====");

        ArrayList<ArrayList<String>> orders = new ArrayList<ArrayList<String>>();
        MerchantOrders = new ArrayList<ArrayList<String>>();


        ArrayList<Object> user = new ArrayList<>();

        Intent intent = this.getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        user = (ArrayList<Order>) args.getSerializable("CART");
//        cart = this.getIntent().getExtras().getParcelableArrayList("CART");
//        ArrayList<Drink> t = this.getIntent().getParcelableArrayList("CART");

        View storeName = findViewById(R.id.cartStoreName);
        View address = findViewById(R.id.cartStoreAddress);
        Phonenumber = findViewById(R.id.cartStorePhone);

        View placeOrderButton = findViewById(R.id.PlaceOrder);
        placeOrderButton.setOnClickListener(this);

        layout = findViewById(R.id.cart_Drink_layout);
        View drawerLayout = findViewById(R.id.user_drawer_layout);


        String currentStoreid;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();


            if(extras == null) {
                currentStoreid= null;
//                storeName = null;
            } else {
                currentStoreid= extras.getString("UID_STRING");
            }
        } else {
            currentStoreid= (String) savedInstanceState.getSerializable("UID_STRING");
//            strStoreName = (String) savedInstanceState.getSerializable("STORE_NAME");
//            order= (Order) savedInstanceState.getSerializable("CART");
        }
        System.out.println("stroe ifssss====");
        ViewTimeCycle cart;
        System.out.println("stroe if====" + currentStoreid + cart.get(0).getPrice());
        loadView();
    }

    private void loadView() {

        //get dat reference
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Merchants").child(currentStoreid);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //shows store name, address, and phone
                for (DataSnapshot s : snapshot.getChildren()){
                    if(s.getKey().toString().equals("storeName"))
                    {
                        CollationElementIterator storeName;
                        storeName.setText(s.getValue().toString());
                    }
                    if(s.getKey().toString().equals("address"))
                    {
                        CollationElementIterator address;
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
                    ArrayList<String> l = (ArrayList<String>) s.getValue();
                    ViewTransitionController orders;
                    orders.add(l);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // drinks in the cart
        for(ArrayList<String> d: UserOrders)
        {
            final View orderView = getLayoutInflater().inflate(R.layout.activity_user_order_history,null,false);
            TextView drinkName = (TextView)orderView.findViewById(R.id.user_drink_name);
            TextView drinkPrice = (TextView)orderView.findViewById(R.id.user_drink_price);
            TextView drinkCaffeine = (TextView)orderView.findViewById(R.id.user_drink_caffeine);
            ImageView imageRemove = (ImageView)orderView.findViewById(R.id.user_image_close);
            imageRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    removeorder(orderView);
                }
            });

            drinkName.setText(d.getName());
            drinkPrice.setText(String.valueOf(d.getPrice()));
            drinkCaffeine.setText(String.valueOf(d.getCaffeine()));
//            System.out.println("testing" + ((TextView)drinkView.getRootView().findViewById(R.id.drink_name)).getText().toString());
            layout.addView(orderView);

        }

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

                for(int i = 0; i < UserOrders.size(); i++)
                {
                    orderArray.add(UserOrders.get(i).toString());
                }
//                ArrayList<ArrayList<String>> orders= new ArrayList<ArrayList<String>>();

                UserOrders.add(orderArray);


//                orderMap.put(dateArray, cart);
                // Add to merchant database
                // {date, user uid, }
                ArrayList<String> temp = new ArrayList<>();

                temp.add(dateStr);
                temp.add(timeStr);

                temp.add("gUXAp4NhQfMBpBTTYV7bJeIQ0jx1");
                for(int i = 0; i < UserOrders.size(); i++)
                {
                    temp.add(UserOrders.get(i).toString());
                }
                MerchantOrders.add(temp);
                FirebaseDatabase.getInstance().getReference("Merchants").child(currentStoreid).child("orders")
                        .setValue(MerchantOrders);
                //TODO:FirebaseAuth.getInstance().getCurrentUser().getUid()
                FirebaseDatabase.getInstance().getReference("Users").child("gUXAp4NhQfMBpBTTYV7bJeIQ0jx1").child("orders")
                        .setValue(UserOrders);

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
    }*/
}
