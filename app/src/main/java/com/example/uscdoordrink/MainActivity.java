package com.example.uscdoordrink;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.*;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import android.content.*;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;


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
                    // To the map view page if is user

                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");
                    ref.addValueEventListener(new ValueEventListener() {
                        Boolean isUser = false;
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot s : snapshot.getChildren())
                            {
                                System.out.println("s= " + s.getValue().toString());
                                System.out.println("firebase= " + FirebaseAuth.getInstance().getCurrentUser().getEmail());

                                if(s.getValue().toString().toLowerCase(Locale.ROOT).contains(FirebaseAuth.getInstance().getCurrentUser().getEmail().toLowerCase(Locale.ROOT)))
                                {
                                    System.out.println("here ");
                                    startActivity(new Intent(MainActivity.this, UserNavigationActivity.class));
                                    isUser = true;
                                    break;
                                }
                            }
                            if(!isUser)
                            {
                                startActivity(new Intent(MainActivity.this, MerchantNavigationActivity.class));


                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                else
                {
                    Toast.makeText(MainActivity.this, MainActivity.this.getString(R.string.toast_text), Toast.LENGTH_LONG).show();

                }
            }
        });

    }
}