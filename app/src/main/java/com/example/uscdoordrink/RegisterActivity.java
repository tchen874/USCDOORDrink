package com.example.uscdoordrink;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
                break;
                
        }
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

    private void registerUser() {
        String name = this.name.getText().toString().trim();
        String email = this.email.getText().toString().trim();
        String password = this.password.getText().toString().trim();
        Boolean isMerchant = this.isMerchant.isChecked();
        // check to validate input
        // Check to see if the email is in the email pattern
        if(Patterns.EMAIL_ADDRESS.matcher(email).matches() == false)
        {
            this.email.setError("Email you entered is not valid!");
            this.email.requestFocus();
            return;
        }
        if(email.isEmpty() == true)
        {
            this.email.setError("Email cannot be empty!");
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

        // Check to see if name edit textField is empty or not
        if(name.isEmpty() == true)
        {
            this.name.setError("Name cannot be empty!");
            this.name.requestFocus();
            return;
        }

        if (password.length() < 8)
        {
            this.password.setError("Password should be 8 characters!");
            this.password.requestFocus();
            return;
        }
        System.out.println("length=" +password.length() + password);

        // add the information into firebase
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    // When is merchant register
                    if(isMerchant)
                    {
                        User user = new User(name, email);
                        System.out.println("Creting merchant account");
                        FirebaseDatabase.getInstance().getReference("Merchants")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(RegisterActivity.this, "Merchant registered successful", Toast.LENGTH_LONG).show();

                                    // Direct to merchant view
                                    startActivity(new Intent(RegisterActivity.this, MerchantNavigationActivity.class));


                                }
                                else
                                {
                                    Toast.makeText(RegisterActivity.this, "Unable to Register!", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                    else
                    {
                        User user = new User(name, email);
                        FirebaseDatabase.getInstance().getReference("Users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(RegisterActivity.this, "User registered successful", Toast.LENGTH_LONG).show();

                                    // Direct to Map view if is user, else direct to the merchant menu edit view
                                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");
                                    ref.addValueEventListener(new ValueEventListener() {
                                        Boolean isUser = false;
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            for(DataSnapshot s : snapshot.getChildren())
                                            {
                                                if(s.getValue().toString().contains(FirebaseAuth.getInstance().getCurrentUser().getEmail()))
                                                {
                                                    startActivity(new Intent(RegisterActivity.this, mapView.class));
                                                    isUser = true;
                                                    break;
                                                }
                                            }
                                            if(!isUser)
                                            {
                                                startActivity(new Intent(RegisterActivity.this, MerchantNavigationActivity.class));

                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });


                                }
                                else
                                {
                                    Toast.makeText(RegisterActivity.this, "Unable to Register!", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }

                }
                else
                {
                    Toast.makeText(RegisterActivity.this, "Unable to Register!", Toast.LENGTH_LONG).show();
                }

            }
        });





    }
}