package com.example.uscdoordrink;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MerchantNavigationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_navigation);

        // Initialize and assign variable
        BottomNavigationView nagv = (BottomNavigationView) findViewById(R.id.button_nagv);
        nagv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.Merchant_Profile:
                        startActivity(new Intent(MerchantNavigationActivity.this, ProfileActivity.class));
                        break;

//                    case R.id.Merchant_History:
//                        startActivity(new Intent(MerchantNavigationActivity.this, MenuActivity.class));
//
//                        break;

                    case R.id.Menu:
                        startActivity(new Intent(MerchantNavigationActivity.this, MenuActivity.class));
                        break;

                }
                return false;
            }
        });
    }
}