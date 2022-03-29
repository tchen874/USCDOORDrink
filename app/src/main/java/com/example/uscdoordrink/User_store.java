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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class User_store extends AppCompatActivity implements View.OnClickListener{

    private String currentStoreid;
    TextView storeName;
    TextView storeAdress;
    TextView storePhone;
    Button checkoutButton;
    DrawerLayout drawerLayout;
    LinearLayout layoutList;
    ImageView addDrink;
    List<View> viewlist;
    List<Drink> drinks;
    Store s;
    mapView map;
    Order userOrder;

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
        
        drawerLayout = findViewById(R.id.user_drawer_layout);
        
        storeName = findViewById(R.id.userStoreName);
        layoutList = findViewById(R.id.drink_layout_list);
        storeAdress = findViewById(R.id.userStoreAddress);
        storePhone = findViewById(R.id.userStorePhone);
        
        checkoutButton = findViewById(R.id.Checkout);
        checkoutButton.setOnClickListener(this);
        viewlist = new ArrayList<>();
        drinks = new ArrayList<Drink>();
        userOrder = new Order();
//        System.out.println("Current id" + s.getStoreUID());
//        String current = map.getStore().getStoreUID();
//        System.out.println("UID=" + current);
        loadView();
        
    }

    private void loadView() {
        // TODO: FirebaseAuth.getInstance().getCurrentUser().getUid()
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Merchants").child("nRd94qH2L4cDKMtGbYVDfsUkED83").child("menu");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot s : snapshot.getChildren()) {
                    List<String> st = (List<String>) s.getValue();
                    final View MenuView = getLayoutInflater().inflate(R.layout.drink,null,false);


                    TextView drinkName = (TextView)MenuView.findViewById(R.id.drink_name);
                    TextView drinkPrice = (TextView)MenuView.findViewById(R.id.drink_price);
                    TextView drinkCaffeine = (TextView)MenuView.findViewById(R.id.drink_caffeine);
//                    String name = drinkName.getText().toString();
//                    Double price = Double.parseDouble(drinkPrice.getText().toString());
//                    Double caffeine = Double.parseDouble(drinkCaffeine.getText().toString());
                    ImageView imageAdd = (ImageView)MenuView.findViewById(R.id.image_add);
                    imageAdd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int count = 0;
                            System.out.println("Drink=" + drinks.get(count));

                            for(View v1: viewlist)
                            {
                                if(v1 == v)
                                {
//                                    addDrinkToOrder(drinks.get(count));
                                    System.out.println("Drink=" + drinks.get(count));
                                }
                                else
                                {
                                    count++;
                                }
                            }
                            System.out.println("in here");
//
//                            addDrinkToOrder();
                        }
                    });


                    drinkName.setText(st.get(0).toString());
                    drinkPrice.setText(st.get(1).toString());
                    drinkCaffeine.setText(st.get(2).toString());
                    String name = drinkName.getText().toString();
                    Double price = Double.parseDouble(drinkPrice.getText().toString());
                    Double caffeine = Double.parseDouble(drinkCaffeine.getText().toString());
                    Drink d = new Drink(name, price, caffeine);
                    layoutList.addView(MenuView);
                    drinks.add(d);
                    viewlist.add(MenuView);

                }
        }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void addDrinkToOrder() {

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