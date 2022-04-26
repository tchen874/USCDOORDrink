package com.example.uscdoordrink;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserAboutUsActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    TextView userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_about_us);
        drawerLayout = findViewById(R.id.user_drawer_layout);
        userName = findViewById(R.id.userName);

        // Get name
        DatabaseReference userref = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        userref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //shows store name, address, and phone
                for (DataSnapshot s : snapshot.getChildren()){
                    if(s.getKey().toString().equals("name")) {
                        userName.setText(s.getValue().toString());
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void UserClickMenu(View view)
    {
        System.out.println("++++OPENED in menu");
        mapView.openDrawer(drawerLayout);
    }

    public void UserClickLogo(View view){
        mapView.closeDrawer(drawerLayout);
    }


    public void UserClickViewMap(View view)
    {
        mapView.redirectActivity(this, mapView.class);

    }

    public void UserClickProfile(View view)
    {
        mapView.redirectActivity(this, UserProfileActivity.class);

    }

    public void UserClickAboutUs(View view)
    {
        mapView.redirectActivity(this, UserAboutUsActivity.class);
    }
    public void UserClickMainOrderHistory(View view)
    {
        mapView.redirectActivity(this, mainTabLayout.class);
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
        mapView.closeDrawer(drawerLayout);
    }
}