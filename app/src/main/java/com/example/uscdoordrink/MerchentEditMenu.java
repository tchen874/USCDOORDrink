package com.example.uscdoordrink;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MerchentEditMenu extends AppCompatActivity implements View.OnClickListener{

    DrawerLayout drawerLayout;
    LinearLayout layoutList;
    Button addButton;
    Button updateButton;
    ArrayList<Drink> drinksList = new ArrayList<>();
    Menu menu;
    TextView merchantName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchent_edit_menu);

        menu = new Menu();
        drawerLayout = findViewById(R.id.drawer_layour);
        layoutList = findViewById(R.id.menu_layout_list);
        addButton = findViewById(R.id.button_add);
        addButton.setOnClickListener(this);

        updateButton = findViewById(R.id.button_update);
        updateButton.setOnClickListener(this);

        merchantName = findViewById(R.id.merchantusername);
        DatabaseReference userref = FirebaseDatabase.getInstance().getReference().child("Merchants").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        userref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //shows store name, address, and phone
                for (DataSnapshot s : snapshot.getChildren()){
                    if(s.getKey().toString().equals("name")) {
                        merchantName.setText(s.getValue().toString());
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        loadView();
    }
    // Use to dismiss the keyboard when not inputting
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.button_add:
                addView();

                break;

            case R.id.button_update:
                update();
                //add toast to update
                Toast.makeText(getApplicationContext(),"Updated Menu!", Toast.LENGTH_LONG).show();
                break;
        }


    }

    // Load the drinks in the menu from database to view
    private void loadView()
    {
//        final View MenuView = getLayoutInflater().inflate(R.layout.activity_merchant_menu,null,false);

        // TODO: Change the uid into current user uid
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Merchants").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("menu");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int count = 0;
                for (DataSnapshot s : snapshot.getChildren()) {
                    if(count >= drinksList.size())
                    {
                        List<String> st = (List<String>) s.getValue();
                        final View MenuView = getLayoutInflater().inflate(R.layout.activity_merchant_menu,null,false);


                        EditText drinkName = (EditText)MenuView.findViewById(R.id.edit_drink_name);
                        EditText drinkPrice = (EditText)MenuView.findViewById(R.id.edit_price);
                        EditText drinkCaffeine = (EditText)MenuView.findViewById(R.id.edit_caffeine);

                        ImageView imageClose = (ImageView)MenuView.findViewById(R.id.image_remove);
                        imageClose.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                System.out.println("in here");
                                String name = ((EditText)v.findViewById(R.id.edit_drink_name)).getText().toString();
                                Double price = Double.valueOf(((EditText)v.findViewById(R.id.edit_price)).getText().toString());
                                Double caffeine = Double.valueOf(((EditText)v.findViewById(R.id.edit_caffeine)).getText().toString());
                                menu.removeDrink(new Drink(name, price, caffeine));
                                removeView(MenuView);
                            }
                        });

                        drinkName.setText(st.get(0).toString());
                        drinkPrice.setText(st.get(1).toString());
                        drinkCaffeine.setText(st.get(2).toString());
                        Drink d = new Drink(st.get(0).toString(), Double.valueOf(st.get(1).toString()), Double.valueOf(st.get(2).toString()));
                        menu.addDrink(d);
                        layoutList.addView(MenuView);

                    }
                    else
                    {
                        count++;
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void addView() {

        final View MenuView = getLayoutInflater().inflate(R.layout.activity_merchant_menu,null,false);

        EditText drinkName = (EditText)MenuView.findViewById(R.id.edit_drink_name);
        EditText drinkPrice = (EditText)MenuView.findViewById(R.id.edit_price);
        EditText drinkCaffeine = (EditText)MenuView.findViewById(R.id.edit_caffeine);
        ImageView imageClose = (ImageView)MenuView.findViewById(R.id.image_remove);

        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("in here");
                removeView(MenuView);
            }
        });

        layoutList.addView(MenuView);

    }

    private void removeView(View view){

        //drinksList.remove(view.getId());

        layoutList.removeView(view);
    }
    private void update(){

        int ordiginalSize = drinksList.size();
        drinksList.clear();
        for(int i = 0; i < layoutList.getChildCount(); i++)
        {
            View view = layoutList.getChildAt(i);
            String drinkName = ((EditText)view.findViewById(R.id.edit_drink_name)).getText().toString().trim();
            String drinkPrice = ((EditText)view.findViewById(R.id.edit_price)).getText().toString().trim();
            String drinkCaffeine = ((EditText)view.findViewById(R.id.edit_caffeine)).getText().toString().trim();

            // check to see if user enterd all the requirement field
            if(drinkName.isEmpty() || drinkPrice.isEmpty() || drinkCaffeine.isEmpty())
            {
                Toast.makeText(MerchentEditMenu.this, "Fields cannot be empty", Toast.LENGTH_LONG).show();
                return;
            }

            // Validate the drinkPrice needs to be number
            try
            {
                Double.parseDouble(drinkPrice);
            }
            catch(NumberFormatException e)
            {
                //not a double
                Toast.makeText(MerchentEditMenu.this, "Price fields needs to be number", Toast.LENGTH_LONG).show();
                return;
            }

            // Validate the drinkCaffeine needs to be number
            try
            {
                Double.parseDouble(drinkCaffeine);
            }
            catch(NumberFormatException e)
            {
                //not a double
                Toast.makeText(MerchentEditMenu.this, "Caffeine fields needs to be number", Toast.LENGTH_LONG).show();
                return;
            }

            System.out.println("VALIDATEDDD!!!!!!!");

            Drink d = new Drink(drinkName, Double.valueOf(drinkPrice), Double.valueOf(drinkCaffeine));
            drinksList.add(d);
        }
        Drink d = new Drink();
        List<ArrayList<String>> listOfDrinkList = d.DrinkToList(drinksList);
        System.out.println("drink size=" + listOfDrinkList.size());
        // Store the menu on firebaseDatabase
        //TODO: Covert the path to the current user!!!!
//        FirebaseDatabase.getInstance().getReference("Merchants/nRd94qH2L4cDKMtGbYVDfsUkED83").child("menu").removeValue();
        FirebaseDatabase.getInstance().getReference("Merchants").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("menu")
                .setValue(listOfDrinkList);
        //TODO: Change back to user id
//        FirebaseDatabase.getInstance().getReference("Merchants/" + FirebaseAuth.getInstance().getCurrentUser().getUid()).child("menu")
//                .setValue(d.DrinkToList(drinksList));

    }

    public void ClickMenu(View view)
    {
        Merchant_map_view.openDrawer(drawerLayout);
    }

    public void ClickLogo(View view)
    {
        Merchant_map_view.closeDrawer(drawerLayout);
    }

    public void ClickViewMap(View view)
    {
        Merchant_map_view.redirectActivity(this, Merchant_map_view.class);
    }
    public void ClickOrderHistory(View view)
    {
        Merchant_map_view.redirectActivity(this, merchantOrderHistoryActivity.class);
    }
    public void ClickProfile(View view)
    {
        Merchant_map_view.redirectActivity(this, ProfileActivity.class);

    }

    public void ClickEditMenu(View view)
    {
        recreate();
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

}