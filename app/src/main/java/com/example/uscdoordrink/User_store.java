package com.example.uscdoordrink;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.MapView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class User_store extends AppCompatActivity implements View.OnClickListener, java.io.Serializable{

    private String currentStoreid;
    TextView storeName;
    String strStoreName;
    TextView storeAdress;
    TextView storePhone;
    Button checkoutButton;
    DrawerLayout drawerLayout;
    LinearLayout layoutList;
    List<Drink> menu;
    ArrayList<Drink> cart;
    DatabaseReference dref;
    ImageView addDrink;
    Store s;
    mapView map;
    public User_store()
    {
    }

    public User_store(Store s)
    {
        System.out.println("GET into sotre");
        this.s = s;
    }
    public void setStoreUId(String id)
    {
        this.currentStoreid = id;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_store);


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                currentStoreid= null;
                storeName = null;
            } else {
                currentStoreid= extras.getString("UID_STRING");
                strStoreName = extras.getString("STORE_NAME");
            }
        } else {
            currentStoreid= (String) savedInstanceState.getSerializable("UID_STRING");
            strStoreName = (String) savedInstanceState.getSerializable("STORE_NAME");
        }
        System.out.println("currentStoreid: " + currentStoreid);
        System.out.println("strStoreName: " + strStoreName);

        drawerLayout = findViewById(R.id.user_drawer_layout);
        storeName = findViewById(R.id.userStoreName);
        layoutList = findViewById(R.id.drink_layout_list);
        storeAdress = findViewById(R.id.userStoreAddress);
        storePhone = findViewById(R.id.userStorePhone);
        checkoutButton = findViewById(R.id.Checkout);
        checkoutButton.setOnClickListener(this);

        cart = new ArrayList<Drink>();
//        System.out.println("Current id" + s.getStoreUID());
//        String current = map.getStore().getStoreUID();
//        System.out.println("UID=" + current);

        // Initialize menu
//        menu = new ArrayList<>();
//        dref = FirebaseDatabase.getInstance().getReference().child("Merchants").child(currentStoreid).child("menu");
//        dref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot s: snapshot.getChildren()){
////                    System.out.println(s.getValue().toString());
//                    List<String> st = (List<String>) s.getValue();
////                    System.out.println("drink"+ st.get(0).toString()+ Double.parseDouble(st.get(1).toString())+ Double.parseDouble(st.get(2).toString()));
//                    Drink d = new Drink(st.get(0).toString(), Double.parseDouble(st.get(1).toString()), Double.parseDouble(st.get(2).toString()));
//                    menu.add(d);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        loadView();
    }
    //load store view
    private void loadView() {
        // TODO: FirebaseAuth.getInstance().getCurrentUser().getUid()
        //get dat reference
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Merchants").child(currentStoreid);
        ref.addValueEventListener(new ValueEventListener() {
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
                        storeAdress.setText(s.getValue().toString());
                    }
                    if(s.getKey().toString().equals("phoneNumber"))
                    {
                        storePhone.setText(s.getValue().toString());
                    }
                }
//                storeName.setText(snapshot.getValue().toString());
//                layoutList = findViewById(R.id.drink_layout_list);
//                storeAdress = findViewById(R.id.userStoreAddress);
//                storePhone = findViewById(R.id.userStorePhone);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ref.child("menu").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //show store menu
                for (DataSnapshot s : snapshot.getChildren()) {
                    List<String> st = (List<String>) s.getValue();
                    final View MenuView = getLayoutInflater().inflate(R.layout.drink,null,false);


                    TextView drinkName = (TextView)MenuView.findViewById(R.id.drink_name);
                    TextView drinkPrice = (TextView)MenuView.findViewById(R.id.drink_price);
                    TextView drinkCaffeine = (TextView)MenuView.findViewById(R.id.drink_caffeine);
                    ImageView imageAdd = (ImageView)MenuView.findViewById(R.id.image_add);

                    imageAdd.setOnClickListener(new View.OnClickListener() {
                        int count = 0;
                        @Override
                        public void onClick(View v) {
//                            for(View view: MenuView.getContext().toString())
//                            {
//                                System.out.println("id=" + v.getId());
//                            }
                            Double drinkPrice = Double.parseDouble(((TextView) v.getRootView().findViewById(R.id.drink_price)).getText().toString());
                            String drinkName = ((TextView) v.getRootView().findViewById(R.id.drink_name)).getText().toString();
                            Double drinkCaffein = Double.parseDouble(((TextView) v.getRootView().findViewById(R.id.drink_caffeine)).getText().toString());
                            Drink d = new Drink(drinkName, drinkPrice, drinkCaffein);
                            Toast.makeText(User_store.this, "Added to the cart!", Toast.LENGTH_LONG).show();
                            addToCart(d);
////                            removeView(MenuView);
//                            //TODO Add drink to the cart
                        }
                    });


                    drinkName.setText(st.get(0).toString());
                    drinkPrice.setText(st.get(1).toString());
                    drinkCaffeine.setText(st.get(2).toString());
                    layoutList.addView(MenuView);

                }
        }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.Checkout:
                //TODO Direct to the cart view

                Order o = new Order((ArrayList<Drink>) cart);
                Cart user_cart = new Cart(s);

                Intent intent = new Intent(User_store.this, user_cart.getClass());
                intent.putExtra("UID_STRING", currentStoreid);


                Bundle args = new Bundle();
                args.putSerializable("CART", (Serializable) cart);
                intent.putExtra("BUNDLE", args);

                startActivity(intent);
                break;
        }
    }

    private void addToCart(Drink drink) {
        cart.add(drink);
//        layoutList.addView(MenuView);
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
        UserNavigationActivity.redirectActivity(this, UserOrderHistoryActivity.class);
    }
    public void UserClickAboutUs(View view)
    {
        UserNavigationActivity.redirectActivity(this, UserAboutUsActivity.class);
    }
    public void UserClickViewMap(View view)
    {
        recreate();
    }
    public void UserClickDeliveryProgress(View view)
    {
        UserNavigationActivity.redirectActivity(this, UserDeliveryProgress.class);
    }
    public void UserClickViewStore(View view)
    {
        recreate();
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