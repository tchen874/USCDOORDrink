package com.example.uscdoordrink;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout layoutList;
    Button addButton;
    Button updateButton;

    ArrayList<Drink> drinksList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuview);

        layoutList = findViewById(R.id.layout_list);
        addButton = findViewById(R.id.button_add);
        addButton.setOnClickListener(this);

        updateButton = findViewById(R.id.button_update);
        updateButton.setOnClickListener(this);
        loadView();

//        activity_menuview.scrollToPosition(items.size() - 1);
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
                break;
        }


    }

    // Load the drinks in the menu from database to view
    private void loadView()
    {
        final View MenuView = getLayoutInflater().inflate(R.layout.activity_merchant_menu,null,false);

        // TODO: Change the uid into current user uid
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Merchants").child("KwI7QB3InRNMI59wyCdoHVTNuLG2").child("menu");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot s : snapshot.getChildren()) {
                    List<String> st = (List<String>) s.getValue();

                    EditText drinkName = (EditText)MenuView.findViewById(R.id.edit_drink_name);
                    EditText drinkPrice = (EditText)MenuView.findViewById(R.id.edit_price);
                    EditText drinkCaffeine = (EditText)MenuView.findViewById(R.id.edit_caffeine);

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

//        System.out.println("id========= " + view.toString());
        //drinksList.remove(view.getId());
        layoutList.removeView(view);
    }
    private void update(){

        System.out.println("=========" + layoutList.getChildCount());
        drinksList.clear();
        for(int i = 0; i < layoutList.getChildCount(); i++)
        {
            View view = layoutList.getChildAt(i);
            String drinkName = ((EditText)view.findViewById(R.id.edit_drink_name)).getText().toString().trim();
            String drinkPrice = ((EditText)view.findViewById(R.id.edit_price)).getText().toString().trim();
            String drinkCaffeine = ((EditText)view.findViewById(R.id.edit_caffeine)).getText().toString().trim();

            System.out.println("=========:" + drinkName + ":" + drinkPrice + ":"+ drinkCaffeine);
            // check to see if user enterd all the requirement field
            if(drinkName.isEmpty() || drinkPrice.isEmpty() || drinkCaffeine.isEmpty())
            {
                System.out.println("UPDATED");
                Toast.makeText(MenuActivity.this, "Fields cannot be empty", Toast.LENGTH_LONG).show();
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
                Toast.makeText(MenuActivity.this, "Price fields needs to be number", Toast.LENGTH_LONG).show();
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
                Toast.makeText(MenuActivity.this, "Caffeine fields needs to be number", Toast.LENGTH_LONG).show();
                return;
            }

            System.out.println("VALIDATEDDD!!!!!!!");

            Drink d = new Drink(drinkName, Double.valueOf(drinkPrice), Double.valueOf(drinkCaffeine));
            drinksList.add(d);
        }
        Drink d = new Drink();
        List<ArrayList<String>> listOfDrinkList = d.DrinkToList(drinksList);
        // Store the menu on firebaseDatabase
        FirebaseDatabase.getInstance().getReference("Merchants/KwI7QB3InRNMI59wyCdoHVTNuLG2").child("menu")
                .setValue(listOfDrinkList);
        //TODO: Change back to user id
//        FirebaseDatabase.getInstance().getReference("Merchants/" + FirebaseAuth.getInstance().getCurrentUser().getUid()).child("menu")
//                .setValue(d.DrinkToList(drinksList));

    }
}