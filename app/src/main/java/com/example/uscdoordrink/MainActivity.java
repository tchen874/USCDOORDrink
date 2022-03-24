package com.example.uscdoordrink;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;
import android.content.*;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    // Variable
    private TextView noAccount;
    private TextView forgotPassword;
    private EditText email;
    private EditText password;
    private Button login;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noAccount = (TextView) findViewById(R.id.noAccount);
        noAccount.setOnClickListener(this);

        forgotPassword = (TextView) findViewById(R.id.forgotPassword2);
        forgotPassword.setOnClickListener(this);

        login = (Button) findViewById(R.id.loginButton);
        login.setOnClickListener(this);

        email = (EditText) findViewById(R.id.editTextTextEmailAddress);
        email.setOnClickListener(this);

        password = (EditText) findViewById(R.id.editTextTextPassword);
        password.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();


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

            case R.id.loginButton:
                login();
                break;
        }
    }

    private void login(){
        String email = this.email.getText().toString().trim();
        String password = this.password.getText().toString().trim();

        System.out.println(email + password);

        if(email.isEmpty() == true)
        {
            this.email.setError("Email cannot be empty!");
            this.email.requestFocus();
            return;
        }

        // Check to see if the email is in the email pattern
        if(Patterns.EMAIL_ADDRESS.matcher(email).matches() == false)
        {
            this.email.setError("Email you entered is not valid!");
            this.email.requestFocus();
            return;
        }

        // Check to see if password edit textField is empty or not
        if(password.isEmpty() == true)
        {
            this.password.setError("Password cannot be empty!");
            this.password.requestFocus();
            return;
        }

        if (password.length() < 8)
        {
            this.password.setError("Password should be 8 characters!");
            this.password.requestFocus();
            return;
        }

        //Check with firebase
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    // To the map view page
                    startActivity(new Intent(MainActivity.this, mapView.class));
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Unable to Login!", Toast.LENGTH_LONG).show();

                }
            }
        });

    }
}