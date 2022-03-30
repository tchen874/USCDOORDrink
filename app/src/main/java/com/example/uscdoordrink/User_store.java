package com.example.uscdoordrink;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.MapView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;


public class User_store extends AppCompatActivity implements View.OnClickListener{

    private String currentStoreid;
    TextView storeName;
    String strStoreName;
    TextView storeAdress;
    TextView storePhone;
    Button checkoutButton;
    DrawerLayout drawerLayout;
    LinearLayout layoutList;
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
//        System.out.println("Current id" + s.getStoreUID());
//        String current = map.getStore().getStoreUID();
//        System.out.println("UID=" + current);
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
                        System.out.println("name=" + s.getKey().toString());
                        storeName.setText(s.getValue().toString());
                    }
                    if(s.getKey().toString().equals("address"))
                    {
                        System.out.println("address=" + s.getKey().toString());
                        storeAdress.setText(s.getValue().toString());
                    }
                    if(s.getKey().toString().equals("phoneNumber"))
                    {
                        System.out.println("phone=" + s.getKey().toString());
                        storePhone.setText(s.getValue().toString());
                    }
                    System.out.println("S=" + s.getValue().toString());
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
                        @Override
                        public void onClick(View v) {
                            System.out.println("in here");
//                            removeView(MenuView);
                            //TODO Add drink to the cart
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
                break;
        }
    }
}