package com.example.uscdoordrink;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.AppCompatImageView;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfileActivity extends AppCompatActivity implements View.OnClickListener{

    DrawerLayout drawerLayout;
    EditText name;
    EditText email;
    EditText phoneNumber;
    Button updateButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        drawerLayout = findViewById(R.id.user_drawer_layout);
        name = findViewById(R.id.nameuser);
        email = findViewById(R.id.useremail);
        phoneNumber = findViewById(R.id.phoneUser);
        updateButton = findViewById(R.id.userUpdateButton);
        updateButton.setOnClickListener(this);

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
    private void loadView() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Merchants").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild("name"))
                {
                    ref.child("name").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            EditText name= (EditText)findViewById(R.id.nameuser);
                            name.setText(snapshot.getValue().toString());

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

                if(snapshot.hasChild("email"))
                {
                    ref.child("email").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            EditText email= (EditText)findViewById(R.id.useremail);
                            email.setText(snapshot.getValue().toString());

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
                            EditText phoneNumber= (EditText)findViewById(R.id.phoneUser);
                            phoneNumber.setText(snapshot.getValue().toString());

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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.userUpdateButton:
//                System.out.println("update profile");
                update();
                break;
        }
    }

    private void update() {
        String name = ((EditText)findViewById(R.id.nameuser)).getText().toString().trim();
        String phone = ((EditText)findViewById(R.id.phoneUser)).getText().toString().trim();
        String email = ((EditText)findViewById(R.id.useremail)).getText().toString().trim();
        // check to see that every field is ented
        if(name.isEmpty() || email.isEmpty() || phone.isEmpty())
        {
            Toast.makeText(UserProfileActivity.this, "Fields cannot be empty", Toast.LENGTH_LONG).show();
            return;
        }

        // Check if email is in form
        if(Patterns.EMAIL_ADDRESS.matcher(email).matches() == false)
        {
            Toast.makeText(UserProfileActivity.this, "Email is invalid!", Toast.LENGTH_LONG).show();
            return;
        }

        // Check to see if the phone is valid
        if(Patterns.PHONE.matcher(phone).matches() == false)
        {
            Toast.makeText(UserProfileActivity.this, "The phone number is not valid", Toast.LENGTH_LONG).show();
            return;
        }


        FirebaseDatabase.getInstance().getReference("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("name")
                .setValue(name).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(!task.isSuccessful())
                {
                    Toast.makeText(UserProfileActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();

                }
            }
        });

        FirebaseDatabase.getInstance().getReference("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("email")
                .setValue(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(!task.isSuccessful())
                {
                    Toast.makeText(UserProfileActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();

                }
            }
        });

        FirebaseDatabase.getInstance().getReference("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("phoneNumber")
                .setValue(phone).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(!task.isSuccessful())
                {
                    Toast.makeText(UserProfileActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();

                }
            }
        });

    }
    public void ClickMenu(View view)
    {
        System.out.println("++++OPENED in menu");
        UserNavigationActivity.openDrawer(drawerLayout);
    }

    public void UserClickLogo(View view){
        UserNavigationActivity.closeDrawer(drawerLayout);
    }


    public void UserClickViewMap(View view)
    {
        UserNavigationActivity.redirectActivity(this, mapView.class);

    }

    public void UserClickProfile(View view)
    {
        recreate();
    }

    public void ClickLogout(View view)
    {
        logout(this);
    }
    public static void logout(Activity activity)
    {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(activity);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to log out?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                activity.finishAffinity();
                FirebaseAuth.getInstance().signOut();
                activity.startActivity(new Intent(activity, MainActivity.class));
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.show();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        UserNavigationActivity.closeDrawer(drawerLayout);
    }

}