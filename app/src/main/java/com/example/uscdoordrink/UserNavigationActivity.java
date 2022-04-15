package com.example.uscdoordrink;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;

public class UserNavigationActivity extends AppCompatActivity {
//    implements OnMapReadyCallback
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_navigation);
        drawerLayout = findViewById(R.id.user_drawer_layout);

        // Build the map
        // get SupportMapFragment and request notification when the map is ready to be used.
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
    }

//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        LatLng uscLoc = new LatLng(34.0224, 118.2851);
//        googleMap.addMarker(new MarkerOptions()
//                .position(uscLoc)
//                .title("Marker at USC"));
//    }

    public void UserClickMenu(View view)
    {
        openDrawer(drawerLayout);
    }

    public static void openDrawer(DrawerLayout drawerLayout)
    {
        // open the drawer layout
        drawerLayout.openDrawer(GravityCompat.START);

    }

    public void UserClickLogo(View view){
        closeDrawer(drawerLayout);
    }

    public static void closeDrawer(DrawerLayout drawerLayout)
    {
        // If the drawer is open, then close it
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void UserClickViewMap(View view)
    {
        redirectActivity(this, mapView.class);
    }
//    public void UserClickViewStore(View view)
//    {
//        redirectActivity(this, class);
//    }
//
    public void UserClickDeliveryProgress(View view)
    {
        redirectActivity(this, UserDeliveryProgress.class);
    }

    public void UserClickOrderHistory(View view)
    {
        redirectActivity(this, UserOrderHistoryActivity.class);
    }

    public void UserClickAboutUs(View view)
    {
        redirectActivity(this, UserAboutUsActivity.class);
    }

    public void UserClickProfile(View view)
    {
        redirectActivity(this, UserProfileActivity.class);
    }

    public void ClickLogout(View view)
    {
        logout(this);
    }
    public static void logout(Activity activity)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
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
    public static void redirectActivity(Activity activity, Class aclass)
    {
        Intent intent = new Intent(activity, aclass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        closeDrawer(drawerLayout);
    }

}