package com.example.uscdoordrink;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class notification extends Activity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_order_history);
        //textView = findViewById(R.id.Notification);
        //getting notification message
        String message = getIntent().getStringExtra("text");
        textView.setText(message);
    }
}