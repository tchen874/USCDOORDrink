package com.example.uscdoordrink;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class UserAboutUsActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_about_us);
        drawerLayout = findViewById(R.id.user_drawer_layout);
    }

    public void UserClickMenu(View view)
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
        UserNavigationActivity.redirectActivity(this, UserProfileActivity.class);

    }

    public void UserClickAboutUs(View view)
    {
        UserNavigationActivity.redirectActivity(this, UserAboutUsActivity.class);
    }
    public void UserClickOrderHistory(View view)
    {
        UserNavigationActivity.redirectActivity(this, UserOrderHistoryActivity.class);
    }
    public void UserClickDeliveryProgress(View view)
    {
        UserNavigationActivity.redirectActivity(this, UserDeliveryProgress.class);
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