package com.example.uscdoordrink;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.*;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

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
            this.name.setError("Name icannot be empty!");
            this.name.requestFocus();
            return;
        }

        if (password.length() < 8)
        {
            this.password.setError("Password should be 8 characters!");
            this.password.requestFocus();
            return;
        }

        // add the information into firebase
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    User user = new User(name, email, isMerchant);
                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(RegisterActivity.this, "User registered successful", Toast.LENGTH_LONG).show();

                                // Direct to Map view
                                startActivity(new Intent(RegisterActivity.this, mapViewActivity.class));


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
                    Toast.makeText(RegisterActivity.this, "Unable to Register!", Toast.LENGTH_LONG).show();
                }

            }
        });





    }
}