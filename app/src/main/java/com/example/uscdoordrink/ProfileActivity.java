package com.example.uscdoordrink;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is for Merchant's Prfile View
 * Merchant will able to edit their store information
 */
public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    DrawerLayout drawerLayout;
    EditText name;
    EditText storeName;
    EditText street;
    EditText city;
    EditText zipCode;
    EditText phoneNumber;
    EditText state;
    Button updateButton;
    TextView merchantName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        drawerLayout = findViewById(R.id.drawer_layour);
        name = findViewById(R.id.name);
        storeName = findViewById(R.id.storeName);
        street = findViewById(R.id.street);
        city = findViewById(R.id.city);
        state = findViewById(R.id.state);
        zipCode = findViewById(R.id.zipCode);
        phoneNumber = findViewById(R.id.phoneNumber);

        updateButton = (Button) findViewById(R.id.updateButton);
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
            case R.id.updateButton:
//                System.out.println("update profile");
                update();
                //make toast when update button clicked
                Toast.makeText(getApplicationContext(),"Updated Profile!", Toast.LENGTH_LONG).show();
                break;
        }
    }

    // Load the profile information from database to view
    private void loadView()
    {
        // TODO: Change the uid into current user uid
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Merchants").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        //Set name
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.hasChild("name"))
                {
                    ref.child("name").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            EditText name= (EditText)findViewById(R.id.name);
                            name.setText(snapshot.getValue().toString());

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }


                if(snapshot.hasChild("storeName"))
                {
                    ref.child("storeName").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            EditText storeName= (EditText)findViewById(R.id.storeName);
                            storeName.setText(snapshot.getValue().toString());

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

                if(snapshot.hasChild("address"))
                {
                    ref.child("address").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            EditText street= (EditText)findViewById(R.id.street);
                            EditText city= (EditText)findViewById(R.id.city);
                            EditText state= (EditText)findViewById(R.id.state);
                            EditText zipCode= (EditText)findViewById(R.id.zipCode);
                            String[] temp = snapshot.getValue().toString().split(", ");
                            street.setText(temp[0]);
                            city.setText(temp[1]);
                            state.setText(temp[2]);
                            zipCode.setText(temp[3]);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

                if(snapshot.hasChild("phoneNumber"))
                {
                    ref.child("phoneNumber").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            EditText phone= (EditText)findViewById(R.id.phoneNumber);
                            phone.setText(snapshot.getValue().toString());

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void update()
    {
        String name = ((EditText)findViewById(R.id.name)).getText().toString().trim();
        String storeName = ((EditText)findViewById(R.id.storeName)).getText().toString().trim();
        String street = ((EditText)findViewById(R.id.street)).getText().toString().trim();
        String city = ((EditText)findViewById(R.id.city)).getText().toString().trim();
        String state = ((EditText)findViewById(R.id.state)).getText().toString().trim();
        String zipCode = ((EditText)findViewById(R.id.zipCode)).getText().toString().trim();
        String phone = ((EditText)findViewById(R.id.phoneNumber)).getText().toString().trim();
        System.out.println(name + " " + storeName + " " + street + " " + city + " " + state + " " + zipCode + " " + phone);

        // check to see that every field is ented
        if(name.isEmpty() || storeName.isEmpty() || street.isEmpty() || city.isEmpty() || state.isEmpty() || zipCode.isEmpty() || phone.isEmpty())
        {
            Toast.makeText(ProfileActivity.this, "Fields cannot be empty", Toast.LENGTH_LONG).show();
            return;
        }

        String address = street + ", " + city + ", " + state + ", " + zipCode;
        // TODO: Validate address


        // Check to see if the phone is valid
        if(Patterns.PHONE.matcher(phone).matches() == false)
        {
            Toast.makeText(ProfileActivity.this, "The phone number is not valid", Toast.LENGTH_LONG).show();
            return;
        }

        // Put the store information into firebase
        // TODO: Convert back to for current user
        //FirebaseDatabase.getInstance().getReference("Merchants/" + FirebaseAuth.getInstance().getCurrentUser().getUid()).child("menu")
        ////                .setValue(d.DrinkToList(drinksList));

        FirebaseDatabase.getInstance().getReference("Merchants").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("name")
                .setValue(name).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                System.out.println("Set name in profile");
                if(!task.isSuccessful())
                {
                    Toast.makeText(ProfileActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();

                }
            }
        });
        FirebaseDatabase.getInstance().getReference("Merchants").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("storeName")
                .setValue(storeName).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(!task.isSuccessful())
                {
                    Toast.makeText(ProfileActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();

                }
            }
        });
        FirebaseDatabase.getInstance().getReference("Merchants").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("phoneNumber")
                .setValue(phone).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(!task.isSuccessful())
                {
                    Toast.makeText(ProfileActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();

                }
            }
        });
        FirebaseDatabase.getInstance().getReference("Merchants").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("address")
                .setValue(address).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(!task.isSuccessful())
                {
                    Toast.makeText(ProfileActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();

                }
            }
        });
    }

    public void ClickMenu(View view)
    {
        Merchant_map_view.openDrawer(drawerLayout);
    }

    public void ClickLogo(View view)
    {
        Merchant_map_view.closeDrawer(drawerLayout);
    }

    public void ClickOrderHistory(View view)
    {
        Merchant_map_view.redirectActivity(this, merchantOrderHistoryActivity.class);
    }
    public void ClickProfile(View view)
    {
        recreate();
    }

    public void ClickEditMenu(View view)
    {
        Merchant_map_view.redirectActivity(this, MerchentEditMenu.class);
    }
    public void ClickViewMap(View view)
    {
        Merchant_map_view.redirectActivity(this, Merchant_map_view.class);
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