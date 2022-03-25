package com.example.uscdoordrink;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TableLayout;

public class MerchantMenuActivity extends AppCompatActivity {

    private EditText storeName;
    private EditText storeAddress;
    private EditText storePhoneNumber;
    private TableLayout menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuview);
    }
}