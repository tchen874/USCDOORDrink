package com.example.uscdoordrink;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.*;


import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private EditText name;
    private EditText password;
    private EditText email;
    private Switch isMerchant;
    private TextView uscDoorDrink;
    private TextView signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();

        uscDoorDrink = (TextView) findViewById(R.id.uscDoorDrink);
        uscDoorDrink.setOnClickListener(this);

        signup = (Button) findViewById(R.id.signupButton3);
        signup.setOnClickListener(this);

        name = (EditText) findViewById(R.id.editTextTextPersonName3);
        email = (EditText) findViewById(R.id.editTextTextEmailAddress5);
        password = (EditText) findViewById(R.id.editTextTextPassword4);
        isMerchant = (Switch) findViewById(R.id.isMerchantSwitch);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.uscDoorDrink:
                startActivity(new Intent(this, MainActivity.class));
                break;

            case R.id.signupButton3:
                registerUser();
                
                
        }
    }

    private void registerUser() {
        String name = this.name.getText().toString().trim();
        String email = this.email.getText().toString().trim();
        String password = this.email.getText().toString().trim();

        // check to validate input

        // Check to see if the email is in the email pattern
        if(Patterns.EMAIL_ADDRESS.matcher(email).matches() == false)
        {
            this.email.setError("Email you entered is not valid!");
            this.email.requestFocus();
            return;
        }

        // Check to see if name edit textField is empty or not
        if(name.isEmpty() == true)
        {
            this.name.setError("Name is not valid!");
            this.name.requestFocus();
            return;
        }

        // Check to see if password edit textField is empty or not
        if(password.isEmpty() == true)
        {
            this.password.setError("Password is not valid!");
            this.password.requestFocus();
            return;
        }





    }
}