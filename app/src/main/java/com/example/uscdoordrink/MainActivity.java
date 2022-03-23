package com.example.uscdoordrink;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;
import android.content.*;




public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    // Variable

    private TextView noAccount;
    private TextView forgotPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noAccount = (TextView) findViewById(R.id.noAccount);
        noAccount.setOnClickListener(this);

        forgotPassword = (TextView) findViewById(R.id.forgotPassword2);
        forgotPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.noAccount:
                startActivity(new Intent(this, RegisterActivity.class));
                break;

            case R.id.forgotPassword2:
                startActivity(new Intent(this, ForgotPasswordActivity.class));
                break;
        }
    }
}